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

import org.apache.commons.io.IOUtils;
import org.nutz.dao.Cnd;
import org.nutz.integration.quartz.annotation.Scheduled;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.juyo.visa.admin.orderUS.service.OrderUSViewService;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.redis.RedisDao;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
@Scheduled(cron = "0 /2 6-23 * * ? *")
//每天的早上6点到晚上12点，每隔一分钟执行一次， 秒 分 时 天 月 每周 年(可选)
//直接使用注解来声明cron
public class QuartzTest extends BaseService<TOrderJpEntity> implements Job {

	@Inject
	private OrderUSViewService orderUSViewService;
	@Inject
	private RedisDao redisDao;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		/*
		在别的类中调用
		QuartzTest wxTokenJob = Mvcs.getIoc().get(QuartzTest.class);
		try {
			wxTokenJob.execute(null);
		} catch (JobExecutionException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}*/

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		System.out.println("Just do it");
		System.out.println(sdf.format(new Date()));

		/*JobDetail jobDetail = context.getJobDetail();
		jobDetail.getJobDataMap().put("myjob", "testjob");

		String object = context.getJobDetail().getJobDataMap().getString("myjob");
		System.out.println(object);

		Trigger trigger = context.getTrigger();
		System.out.println(trigger);

		Scheduler scheduler = context.getScheduler();
		try {
			List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
			for (String string : triggerGroupNames) {
				System.out.println(string);
			}

		} catch (SchedulerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}*/

		//查询发招宝中、提交中、变更中、取消中的订单,注意作废的订单不需要
		Integer[] orderstatus = { JPOrderStatusEnum.READYCOMMING.intKey(), JPOrderStatusEnum.BIANGENGZHONG.intKey(),
				JPOrderStatusEnum.QUXIAOZHONG.intKey(), JPOrderStatusEnum.COMMITING.intKey(),
				JPOrderStatusEnum.AUTO_FILL_FORM_ING.intKey() };
		List<TOrderEntity> orderList = dbDao.query(TOrderEntity.class,
				Cnd.where("status", "in", orderstatus).and("isDisabled", "=", 0), null);
		//判断是否有满足需要的订单
		if (!Util.isEmpty(orderList) && orderList.size() > 0) {
			//取第一个
			TOrderEntity order = orderList.get(0);

			System.out.println("发现疑似有问题的订单，订单号为:" + order.getOrderNum());

			//先查询缓存，如果缓存中有，说明已经发过短信，就不要再发了
			//String string = redisDao.get("autofillJP" + String.valueOf(order.getId()));
			//System.out.println("查询redis缓存内容：" + string);
			//缓存中有，暂不处理
			/*if (!Util.isEmpty(string)) {
				System.out.println("订单号为" + order.getOrderNum() + "的订单已经发送过短信了");
			} else {*/
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class,
					Cnd.where("orderId", "=", order.getId().longValue()));
			Date zhaobaotime = orderjp.getZhaobaotime();
			if (Util.isEmpty(zhaobaotime)) {
				//没有发招宝时间说明点发招宝的时候发生错误，暂不处理
				System.out.println("没有发招宝时间，可能是测试数据吧~~~");

			} else {
				//相差几分钟
				long differMin = getDatePoor(new Date(), zhaobaotime);
				int mincount = 0;
				//订单状态为发招宝中时相差时间2分钟以上发短信
				if (order.getStatus() == JPOrderStatusEnum.READYCOMMING.intKey()
						|| order.getStatus() == JPOrderStatusEnum.BIANGENGZHONG.intKey()
						|| order.getStatus() == JPOrderStatusEnum.QUXIAOZHONG.intKey()
						|| order.getStatus() == JPOrderStatusEnum.AUTO_FILL_FORM_ING.intKey()) {
					mincount = 2;
				} else if (order.getStatus() == JPOrderStatusEnum.COMMITING.intKey()) {//订单状态为提交中时，时间差为5分钟以上发短信
					mincount = 5;
				}
				if (differMin > mincount) {//超过特定时间发短信
					System.out.println("orderid=====:" + order.getId());
					System.out.println("时间差differMin:" + differMin);
					try {
						//cookie过期的情况
						if (Util.eq("cookie expired", orderjp.getErrormsg())) {
							sendSMS(order.getOrderNum(), "cookie过期");
						} else {
							//将订单状态放在短信中
							for (JPOrderStatusEnum jpenum : JPOrderStatusEnum.values()) {
								if (order.getStatus() == jpenum.intKey()) {
									sendSMS(order.getOrderNum(), jpenum.value());
								}
							}
						}
						//将该订单放入缓存中，以记录此订单是否发过短信，防止重复发送短信
						//redisDao.set("autofillJP" + String.valueOf(order.getId()), order.getOrderNum());
						//设置过期时间为1天
						//redisDao.expire("autofillJP" + String.valueOf(order.getId()), 60 * 60 * 24);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			//}
		} else {
			System.out.println("没有疑似有问题的订单☺");
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
		String telephone2 = "18612131435";
		String result = "";
		String smsContent = tmp.toString();
		smsContent = smsContent.replace("${ordernum}", ordernum).replace("${orderstatus}", orderstatus);
		System.out.println("短信分享内容：" + smsContent);
		result = orderUSViewService.sendSMS(telephone, smsContent);
		result = orderUSViewService.sendSMS(telephone2, smsContent);

		return result;

	}

	/**
	 * 计算两个时间相差多少分钟
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param endDate
	 * @param nowDate
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
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