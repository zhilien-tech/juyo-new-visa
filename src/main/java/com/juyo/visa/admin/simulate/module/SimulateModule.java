/**
 * SimulateModule.java
 * com.juyo.visa.admin.simulate
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.simulate.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;

import com.juyo.visa.admin.simulate.service.SimulateJapanService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年11月9日 	 
 */
@At("admin/simulate")
@IocBean
@Filters
public class SimulateModule {

	@Inject
	private SimulateJapanService simulateJapanService;

	@At
	@GET
	public Object fetchJapanOrder() {
		return simulateJapanService.fetchJapanOrder();
	}
}
