/**
 * SimulateJapanModule.java
 * com.juyo.visa.admin.simulate.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.simulate.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.juyo.visa.common.util.ResultObject;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderTripJpEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年11月9日 	 
 */
@IocBean
public class SimulateJapanService extends BaseService<TOrderJpEntity> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResultObject fetchJapanOrder() {

		//日本订单信息
		List<TOrderJpEntity> orderlist = dbDao.query(TOrderJpEntity.class, Cnd.where("visastatus", "=", "5"), null);
		if (!Util.isEmpty(orderlist) && orderlist.size() > 0) {
			DateFormat dateFormat = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
			TOrderJpEntity jporder = orderlist.get(0);
			//日本订单id
			Integer orderjpid = jporder.getId();
			Integer orderId = jporder.getOrderId();
			//用来存放信息
			Map<String, Object> map = Maps.newTreeMap();
			//订单信息
			TOrderEntity orderinfo = null;
			if (!Util.isEmpty(orderId)) {
				orderinfo = dbDao.fetch(TOrderEntity.class, orderId.longValue());
				//日本出行信息
				TOrderTripJpEntity ordertripjp = dbDao.fetch(TOrderTripJpEntity.class,
						Cnd.where("orderId", "=", orderjpid));
				if (!Util.isEmpty(ordertripjp)) {
					Date goDate = ordertripjp.getGoDate();
					if (!Util.isEmpty(goDate)) {
						map.put("entryDate", dateFormat.format(goDate));
					}
					Date returnDate = ordertripjp.getReturnDate();
					if (!Util.isEmpty(returnDate)) {
						map.put("leaveDate", dateFormat.format(returnDate));
					}
				}
			}
			//放置Map
			if (!Util.isEmpty(map)) {
				ResultObject result = ResultObject.success(map);
				result.addAttribute("oid", orderjpid);
				return result;
			}
		}
		return ResultObject.fail("暂无可执行的任务");
	}

}
