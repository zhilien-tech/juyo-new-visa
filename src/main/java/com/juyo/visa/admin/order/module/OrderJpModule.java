/**
 * SaleModule.java
 * com.juyo.visa.admin.sale.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.order.module;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.order.form.OrderJpForm;
import com.juyo.visa.admin.order.service.OrderJpViewService;
import com.juyo.visa.common.enums.CustomerTypeEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.MapUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   ...
 * @Date	 XXXX年XX月XX日 	 
 */
@IocBean
@At("/admin/orderJp")
public class OrderJpModule {

	@Inject
	private OrderJpViewService saleViewService;

	/**
	 * 跳转到list页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object list() {
		Map<String, Object> result = MapUtil.map();
		result.put("customerTypeEnum", EnumUtil.enum2(CustomerTypeEnum.class));
		result.put("mainSaleVisaTypeEnum", EnumUtil.enum2(MainSaleVisaTypeEnum.class));
		return result;
	}

	/**
	 * 加载list页面
	 */
	@At
	@POST
	public Object listData(@Param("..") final OrderJpForm sqlParamForm, HttpSession session) {
		return saleViewService.listData(sqlParamForm, session);
	}
}
