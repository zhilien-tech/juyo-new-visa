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

import com.juyo.visa.admin.order.form.OrderEditDataForm;
import com.juyo.visa.admin.order.form.OrderJpForm;
import com.juyo.visa.admin.order.service.OrderJpViewService;
import com.juyo.visa.common.enums.CustomerTypeEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.forms.TApplicantForm;
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
	public Object order(@Param("id") Integer orderid, HttpSession session) {
		return saleViewService.addOrder(orderid, session);
	}

	/**
	 * 下单
	 */
	@At
	@GET
	@Ok("jsp")
	public Object addOrder(HttpSession session) {
		return saleViewService.addOrder(session);
	}

	/**
	 * 添加申请人页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object addApplicant() {
		return null;
	}

	/**
	 * 保存申请人
	 */
	@At
	@POST
	public Object saveAddApplicant(@Param("..") TApplicantForm applicantForm, HttpSession session) {
		return saleViewService.addApplicant(applicantForm, session);
	}

	/**
	 * 修改申请人基本信息
	 */
	@At
	@GET
	@Ok("jsp")
	public Object updateApplicant(@Param("id") Integer applicantId) {
		return saleViewService.updateApplicant(applicantId);
	}

	/**
	 * 保存申请人修改信息
	 */
	@At
	@POST
	public Object saveEditApplicant(@Param("..") TApplicantForm applicantForm, HttpSession session) {
		return saleViewService.saveEditApplicant(applicantForm, session);
	}

	/**
	 * 保存修改
	 */
	@At
	@POST
	public Object order(@Param("..") OrderEditDataForm orderInfo, @Param("customerinfo") String customerInfo,
			final HttpSession session) {
		return saleViewService.saveOrder(orderInfo, customerInfo, session);
	}

	/**
	 * 下单保存
	 */
	@At
	@POST
	public Object saveAddOrderinfo(@Param("..") OrderEditDataForm orderInfo, final HttpSession session) {
		return saleViewService.saveAddOrderinfo(orderInfo, session);
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
	 * 修改申请人信息后获取新的申请人列表
	 */
	@At
	@POST
	public Object getEditApplicant(@Param("orderid") Integer orderid) {
		return saleViewService.getEditApplicant(orderid);
	}
}
