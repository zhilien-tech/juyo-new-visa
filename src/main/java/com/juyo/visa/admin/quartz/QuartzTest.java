/**
 * package-info.java
 * com.juyo.visa.admin.quartz
 * Copyright (c) 2018, 北京科技有限公司版权所有.
 */

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   刘旭利
 * @Date	 2018年12月4日
 */

package com.juyo.visa.admin.quartz;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.integration.quartz.annotation.Scheduled;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.orderUS.service.OrderUSViewService;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
@Scheduled(cron = "0 /1 * * * ? *")
//每隔一分钟执行一次， 秒 分 时 天 月 每周 年(可选)
//直接使用注解来声明cron
public class QuartzTest extends BaseService<TOrderJpEntity> implements Job {

	@Inject
	private OrderUSViewService orderUSViewService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
		System.out.println("Just do it");
		System.out.println(sdf.format(new Date()));

		//用来存放信息
		Map<String, Object> map = Maps.newTreeMap();
		//查询是否有需要自动填表的订单
		String sqlstring = sqlManager.get("select_simulate_jp_order");
		Sql sql = Sqls.create(sqlstring);
		//查询发招宝中、提交中、变更中、取消中的订单
		Integer[] orderstatus = { JPOrderStatusEnum.READYCOMMING.intKey(), JPOrderStatusEnum.BIANGENGZHONG.intKey(),
				JPOrderStatusEnum.QUXIAOZHONG.intKey(), JPOrderStatusEnum.COMMITING.intKey(),
				JPOrderStatusEnum.AUTO_FILL_FORM_ING.intKey() };
		List<TOrderEntity> query = dbDao.query(TOrderEntity.class, Cnd.where("status", "in", orderstatus), null);
		if (!Util.isEmpty(query) && query.size() > 0) {
			TOrderEntity order = query.get(0);
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class,
					Cnd.where("orderId", "=", order.getId().longValue()));
			Date zhaobaotime = orderjp.getZhaobaotime();
			//相差几分钟
			long differMin = getDatePoor(new Date(), zhaobaotime);
			if (differMin > 5) {//超过5分钟发短信
				System.out.println("orderid=====:" + order.getId());
				System.out.println("时间差differMin:" + differMin);
				System.out.println("超过5分钟了=====");
				try {
					if (Util.eq("cookie expired", orderjp.getErrormsg())) {
						sendSMS(order.getOrderNum(), "cookie过期");
					} else {
						for (JPOrderStatusEnum jpenum : JPOrderStatusEnum.values()) {
							if (order.getStatus() == jpenum.intKey()) {
								sendSMS(order.getOrderNum(), jpenum.value());
							}
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	//发送短信
	public Object sendSMS(String ordernum, String orderstatus) throws IOException {
		List<String> readLines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream(
				"messagetmp/autofilljp_sms.txt"));
		StringBuilder tmp = new StringBuilder();
		for (String line : readLines) {
			tmp.append(line);
		}
		String telephone = "15600027715";
		String result = "";
		String smsContent = tmp.toString();
		smsContent = smsContent.replace("${ordernum}", ordernum).replace("${orderstatus}", orderstatus);
		System.out.println("短信分享内容：" + smsContent);
		result = orderUSViewService.sendSMS(telephone, smsContent);

		return result;

	}

	public static long getDatePoor(Date endDate, Date nowDate) {

		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		//return day + "天" + hour + "小时" + min + "分钟";

		// 计算差多少秒//输出结果
		long sec = diff % nd % nh % nm / ns;
		long result = (day * 24 * 60 * 60 + hour * 60 * 60 + min * 60 + sec) / 60;
		return result;
	}

}