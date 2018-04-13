/**
 * TripAirlineModule.java
 * com.juyo.visa.admin.visajp.module
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.visajp.form.FlightSelectParam;
import com.juyo.visa.admin.visajp.service.TripAirlineService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年1月11日 	 
 */
@IocBean
@At("admin/tripairline")
public class TripAirlineModule {

	@Inject
	private TripAirlineService tripAirlineService;

	@At
	@POST
	public Object getTripAirlineSelect(@Param("..") FlightSelectParam param) {
		return tripAirlineService.getTripAirlineSelect(param);
	}

	/**
	 * 查询接口数据到缓存
	 */
	@At
	@POST
	public Object getAirLineByInterfate(@Param("..") FlightSelectParam param) {
		return tripAirlineService.getAirLineByInterfate(param);
	}
}
