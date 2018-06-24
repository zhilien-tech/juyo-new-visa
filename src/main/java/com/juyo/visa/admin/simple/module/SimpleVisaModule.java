/**
 * SimpleVisaModule.java
 * com.juyo.visa.admin.simple.module
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.simple.module;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.order.form.VisaEditDataForm;
import com.juyo.visa.admin.simple.form.AddOrderForm;
import com.juyo.visa.admin.simple.form.GenerrateTravelForm;
import com.juyo.visa.admin.simple.form.ListDataForm;
import com.juyo.visa.admin.simple.service.SimpleVisaService;
import com.juyo.visa.common.enums.JpOrderSimpleEnum;
import com.juyo.visa.forms.TApplicantForm;
import com.juyo.visa.forms.TApplicantPassportForm;
import com.uxuexi.core.common.util.EnumUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年1月22日 	 
 */
@IocBean
@At("admin/simple")
public class SimpleVisaModule {

	@Inject
	private SimpleVisaService simpleVisaService;

	private static final String VISAINFO_WEBSPCKET_ADDR = "visainfowebsocket";

	/**
	 * 跳转到列表
	 */
	@At
	@Ok("jsp")
	public Object list(HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("orderstatus", EnumUtil.enum2(JpOrderSimpleEnum.class));
		String localAddr = request.getServerName();
		int localPort = request.getServerPort();
		result.put("localAddr", localAddr);
		result.put("localPort", localPort);
		result.put("websocketaddr", VISAINFO_WEBSPCKET_ADDR);
		return result;
	}

	/**
	 *获取列表数据 
	 */
	@At
	@POST
	public Object listData(@Param("..") ListDataForm form, HttpServletRequest request) {
		return simpleVisaService.ListData(form, request);
	}

	/**
	 * 添加订单
	 */
	@At
	@Ok("jsp")
	public Object addOrder(HttpServletRequest request) {
		return simpleVisaService.addOrder(request);
	}

	/**
	 * 生成行程安排
	 */
	@At
	@POST
	public Object generateTravelPlan(HttpServletRequest request, @Param("..") GenerrateTravelForm form) {
		return simpleVisaService.generateTravelPlan(request, form);
	}

	/**
	 * 跳转到添加申请人页面
	 * 
	 */
	@At
	@Ok("jsp")
	public Object addApplicant(@Param("orderid") Integer orderid, HttpServletRequest request) {
		return simpleVisaService.addApplicant(orderid, request);
	}

	/**
	 * 下单保存
	 */
	@At
	@POST
	public Object saveAddOrderinfo(@Param("..") AddOrderForm form, HttpServletRequest request) {
		return simpleVisaService.saveAddOrderinfo(form, request);
	}

	/**
	 * 跳转到编辑订单页面
	 */
	@At
	@Ok("jsp")
	public Object editOrder(HttpServletRequest request, @Param("orderid") Integer orderid) {
		return simpleVisaService.editOrder(request, orderid);
	}

	/**
	 * 获取客户公司信息
	 */
	@At
	@POST
	public Object getCustomerinfoById(@Param("customerid") Long customerid) {
		return simpleVisaService.getCustomerinfoById(customerid);
	}

	/**
	 * 获取客户金额
	 */
	@At
	@POST
	public Object getCustomerAmount(@Param("customerid") Integer customerid, @Param("visatype") Integer visatype) {
		return simpleVisaService.getCustomerAmount(customerid, visatype);
	}

	/**
	 * 保存申请人
	 */
	@At
	@POST
	public Object saveApplicantInfo(HttpServletRequest request, @Param("..") TApplicantForm form) {
		return simpleVisaService.saveApplicantInfo(request, form);
	}

	/**
	 * 跳转到护照信息页面
	 */
	@At
	@Ok("jsp")
	public Object passportInfo(@Param("applicantid") Integer applicantid, @Param("orderid") Integer orderid,
			HttpServletRequest request) {
		return simpleVisaService.passportInfo(applicantid, orderid, request);
	}

	/**
	 * 保存护照信息
	 */
	@At
	@POST
	public Object saveEditPassport(@Param("..") TApplicantPassportForm form, HttpServletRequest request) {
		return simpleVisaService.saveEditPassport(form, request);
	}

	/**
	 * 跳转到基本信息编辑页面
	 */
	@At
	@Ok("jsp")
	public Object updateApplicant(@Param("applicantid") Integer applicantid, @Param("orderid") Integer orderid,
			HttpServletRequest request) {
		return simpleVisaService.updateApplicant(applicantid, orderid, request);
	}

	/**
	 * 跳转到签证信息页面
	 */
	@At
	@Ok("jsp")
	public Object visaInfo(@Param("applicantid") Integer applicantid, @Param("orderid") Integer orderid,
			HttpServletRequest request) {
		return simpleVisaService.visaInfo(applicantid, orderid, request);
	}

	/**
	 * 保存签证信息
	 */
	@At
	@POST
	public Object saveEditVisa(@Param("..") VisaEditDataForm form, HttpServletRequest request) {
		return simpleVisaService.saveEditVisa(form, request);
	}

	/**
	 * 取消订单
	 */
	@At
	@POST
	public Object cancelOrder(@Param("orderid") Long orderid) {
		return simpleVisaService.cancelOrder(orderid);
	}

	/**
	 * 添加护照
	 */
	@At
	@Ok("jsp")
	public Object addPassport(@Param("orderid") Integer orderid, HttpServletRequest request) {
		return simpleVisaService.addPassport(orderid, request);
	}

	/**
	 * 改变签证类型
	 */
	@At
	@POST
	public Object changeVisatype(@Param("orderid") int orderid, @Param("visatype") int visatype) {
		return simpleVisaService.changeVisatype(orderid, visatype);
	}
}
