/**
 * SaleModule.java
 * com.juyo.visa.admin.sale.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.order.module;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.order.form.OrderEditDataForm;
import com.juyo.visa.admin.order.form.OrderJpForm;
import com.juyo.visa.admin.order.form.VisaEditDataForm;
import com.juyo.visa.admin.order.service.OrderJpViewService;
import com.juyo.visa.common.enums.CustomerTypeEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.common.enums.ShareTypeEnum;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TApplicantForm;
import com.juyo.visa.forms.TApplicantPassportForm;
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
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		sqlParamForm.setComId(loginCompany.getId());
		sqlParamForm.setUserId(loginUser.getId());
		sqlParamForm.setUserType(loginUser.getUserType());
		return saleViewService.listData(sqlParamForm, session);
	}

	/**
	 * 跳转到'编辑操作'的录入数据页面
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
	public Object addApplicantSale(@Param("id") Integer orderid) {
		Map<String, Object> result = MapUtil.map();
		result.put("orderid", orderid);
		return result;
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
	 * 下单 添加申请人时刷新表格获取申请人信息
	 */
	@At
	@POST
	public Object getApplicant(@Param("applicantId") String applicantIds, HttpSession session) {
		return saleViewService.getApplicants(applicantIds, session);
	}

	/**
	 * 删除申请人
	 */
	@At
	@POST
	public Object deleteApplicant(@Param("applicantId") Integer id) {
		return saleViewService.deleteApplicant(id);
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

	/**
	 * 护照信息修改
	 */
	@At
	@GET
	@Ok("jsp")
	public Object passportInfo(@Param("id") Integer id) {
		return saleViewService.getEditPassport(id);
	}

	/**
	 * 修改护照信息后保存
	 */
	@At
	@POST
	public Object saveEditPassport(@Param("..") TApplicantPassportForm passportForm, HttpSession session) {
		return saleViewService.saveEditPassport(passportForm, session);
	}

	/**
	 * 签证信息修改
	 */
	@At
	@GET
	@Ok("jsp")
	public Object visaInfo(@Param("id") Integer id, @Param("orderid") Integer orderid) {
		return saleViewService.getVisaInfo(id, orderid);
	}

	/**
	 * 客户信息获取申请人下拉
	 */
	@At
	@POST
	public Object getLinkNameSelect(@Param("linkman") String linkman, HttpSession session) {
		return saleViewService.getLinkman(linkman, session);
	}

	/**
	 * 客户信息获取电话下拉
	 */
	@At
	@POST
	public Object getPhoneNumSelect(@Param("mobile") String mobile, HttpSession session) {
		return saleViewService.getPhoneNumSelect(mobile, session);
	}

	/**
	 * 客户信息获取公司全称下拉
	 */
	@At
	@POST
	public Object getcompNameSelect(@Param("compName") String compName, HttpSession session) {
		return saleViewService.getcompNameSelect(compName, session);
	}

	/**
	 * 客户信息获取公司简称下拉
	 */
	@At
	@POST
	public Object getComShortNameSelect(@Param("comShortName") String comShortName, HttpSession session) {
		return saleViewService.getComShortNameSelect(comShortName, session);
	}

	/**
	 * 客户信息获取邮箱下拉
	 */
	@At
	@POST
	public Object getEmailSelect(@Param("email") String email, HttpSession session) {
		return saleViewService.getEmailSelect(email, session);
	}

	/**
	 * 根据customerId获取客户信息
	 */
	@At
	@POST
	public Object getCustomerById(@Param("id") Integer id, HttpSession session) {
		return saleViewService.getCustomerById(id, session);
	}

	/**
	 * 身份证正面上传、扫描
	 */
	@At
	@Ok("json")
	@AdaptBy(type = UploadAdaptor.class)
	public Object IDCardRecognition(@Param("image") File file, HttpServletRequest request, HttpServletResponse response) {
		return saleViewService.IDCardRecognition(file, request, response);
	}

	/**
	 * 身份证背面上传、扫描
	 */
	@At
	@Ok("json")
	@AdaptBy(type = UploadAdaptor.class)
	public Object IDCardRecognitionBack(@Param("image") File file, HttpServletRequest request,
			HttpServletResponse response) {
		return saleViewService.IDCardRecognitionBack(file, request, response);
	}

	/**
	 * 护照上传、扫描
	 */
	@At
	@Ok("json")
	@AdaptBy(type = UploadAdaptor.class)
	public Object passportRecognition(@Param("image") File file, HttpServletRequest request,
			HttpServletResponse response) {
		return saleViewService.passportRecognitionBack(file, request, response);
	}

	/**
	 * 分享
	 */
	@At
	@GET
	@Ok("jsp")
	public Object share(@Param("id") Integer id) {
		Map<String, Object> result = MapUtil.map();
		result.put("shareTypeEnum", EnumUtil.enum2(ShareTypeEnum.class));
		result.put("orderId", id);
		return result;
	}

	/**
	 * 跳转到日志页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object log(@Param("id") Integer id) {
		Map<String, Object> result = MapUtil.map();
		result.put("orderid", id);
		return result;
	}

	/**
	 * 获取日志信息
	 */
	@At
	@POST
	public Object getLogs(@Param("orderid") Integer orderid) {
		return saleViewService.getLogs(orderid);
	}

	/**
	 * 签证信息修改保存
	 */
	@At
	@POST
	public Object saveEditVisa(@Param("..") VisaEditDataForm visaForm) {
		return saleViewService.saveEditVisa(visaForm);
	}

	/**
	 * 获取分享申请人
	 */
	@At
	@POST
	public Object getShare(@Param("orderid") Integer id) {
		return saleViewService.getShare(id);
	}

	/**
	 * 初审按钮
	 */
	@At
	@POST
	public Object firtTrialJp(@Param("orderId") Integer id) {
		return saleViewService.firtTrialJp(id);
	}
}
