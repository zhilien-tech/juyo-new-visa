/**
 * SimulateJapanModule.java
 * com.juyo.visa.admin.simulate.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.simulate.service;

import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.juyo.visa.common.util.ResultObject;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
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

	@SuppressWarnings("rawtypes")
	public ResultObject fetchJapanOrder() {

		List<TOrderJpEntity> orderlist = dbDao.query(TOrderJpEntity.class, Cnd.where("status", "=", "5"), null);
		if (!Util.isEmpty(orderlist) && orderlist.size() > 0) {
			TOrderJpEntity jporder = orderlist.get(0);
			Integer orderId = jporder.getOrderId();
			//订单信息
			TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderId.longValue());
			Map<String, Object> map = Maps.newTreeMap();

		}
		return ResultObject.fail("暂无可执行的任务");
	}

}
