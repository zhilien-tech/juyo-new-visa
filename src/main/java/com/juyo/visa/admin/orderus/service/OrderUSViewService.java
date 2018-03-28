/**
 * SaleViewService.java
 * com.juyo.visa.admin.sale.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.orderus.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import com.juyo.visa.common.enums.USOrderStatusEnum;
import com.juyo.visa.entities.TAppStaffOrderUsEntity;
import com.juyo.visa.entities.TOrderUsEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 	 
 */
@IocBean
public class OrderUSViewService extends BaseService<TOrderUsEntity> {

	//根据人员id添加订单
	public Object addOrderByStuffId(Integer staffId) {

		TOrderUsEntity orderUs = new TOrderUsEntity();
		String orderNum = generateOrderNumByDate();
		Date nowDate = DateUtil.nowDate();
		orderUs.setOrdernumber(orderNum);
		orderUs.setStatus(USOrderStatusEnum.PLACE_ORDER.intKey());//下单
		orderUs.setCreatetime(nowDate);
		orderUs.setUpdatetime(nowDate);
		TOrderUsEntity order = dbDao.insert(orderUs);

		//更新人员-订单关系表
		if (!Util.isEmpty(order)) {
			Integer orderId = order.getId();
			TAppStaffOrderUsEntity staffOrder = new TAppStaffOrderUsEntity();
			staffOrder.setOrderid(orderId);
			staffOrder.setStaffid(staffId);
			dbDao.insert(staffOrder);
		}

		return JsonResult.success("添加成功");

	}

	//生成订单号
	public String generateOrderNumByDate() {
		SimpleDateFormat smf = new SimpleDateFormat("yyMMdd");
		String format = smf.format(new Date());
		String sqlString = sqlManager.get("orderUS_orderNum_nowDate");
		Sql sql = Sqls.create(sqlString);
		List<Record> query = dbDao.query(sql, null, null);
		int sum = 1;
		if (!Util.isEmpty(query) && query.size() > 0) {
			String string = query.get(0).getString("ordernumber");
			int a = Integer.valueOf(string.substring(9, string.length()));
			sum += a;
		}
		String sum1 = "";
		if (sum / 10 == 0) {
			sum1 = "000" + sum;
		} else if (sum / 100 == 0) {
			sum1 = "00" + sum;

		} else if (sum / 1000 == 0) {
			sum1 = "0" + sum;
		} else {
			sum1 = "" + sum;

		}
		String orderNum = format + "-US" + sum1;

		return orderNum;
	}
}
