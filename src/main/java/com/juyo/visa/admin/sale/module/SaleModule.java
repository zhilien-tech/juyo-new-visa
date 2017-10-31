/**
 * SaleModule.java
 * com.juyo.visa.admin.sale.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.sale.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;

import com.juyo.visa.admin.sale.service.SaleViewService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   ...
 * @Date	 XXXX年XX月XX日 	 
 */
@IocBean
public class SaleModule {

	@Inject
	private SaleViewService saleViewService;

	/**
	 * 跳转到list页面
	 */
	@At("admin/sale/list")
	@GET
	@Ok("jsp")
	public Object list() {
		return null;
	}
}
