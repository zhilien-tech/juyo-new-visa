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

import com.juyo.visa.admin.order.form.OrderJpAddForm;
import com.juyo.visa.admin.order.form.OrderJpForm;
import com.juyo.visa.admin.order.form.OrderJpUpdateForm;
import com.juyo.visa.admin.order.service.OrderJpViewService;
import com.juyo.visa.common.enums.CustomerTypeEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.forms.TApplicantForm;
import com.juyo.visa.forms.TCustomerForm;
import com.juyo.visa.forms.TOrderBackmailForm;
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

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object order(@Param("id") Integer orderid) {
		return saleViewService.addOrder(orderid);
	}

	/**
	 * 添加申请人页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object addApplicant() {
		System.out.println("-------------");
		return saleViewService.test();
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object order(@Param("customerInfo") TCustomerForm customerInfo,
			@Param("orderInfo") OrderJpAddForm orderInfo, @Param("applicantInfo") TApplicantForm applicantInfo,
			@Param("backmailInfo") TOrderBackmailForm backmailInfo, final HttpSession session) {
		return saleViewService.saveOrder(customerInfo, orderInfo, applicantInfo, backmailInfo, session);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@POST
	public Object getOrder(@Param("id") Integer orderid) {
		return saleViewService.fetchOrder(orderid);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object updateOrder(@Param("..") OrderJpUpdateForm updateForm) {
		return saleViewService.updateOrder(updateForm);
	}

}
