package com.juyo.visa.admin.myData.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.myData.service.MyDataService;
import com.juyo.visa.admin.order.form.VisaEditDataForm;

/**
 * 我的资料Module
 * <p>
 *
 * @author   
 * @Date	 2017年12月08日 	 
 */
@IocBean
@At("/admin/myData")
@Filters
public class MyDataModule {

	@Inject
	private MyDataService myDataService;

	/**
	 * 获取申请人基本信息
	 */
	@At
	@Ok("jsp")
	public Object basicInfo(HttpSession session, HttpServletRequest request) {
		return myDataService.getBasicInfo(session, request);
	}

	/**
	 * 获取申请人护照信息
	 */
	@At
	@Ok("jsp")
	public Object passportInfo(HttpSession session, HttpServletRequest request) {
		return myDataService.getPassportInfo(session, request);
	}

	/**
	 * 已有签证
	 */
	@At
	@Ok("jsp")
	public Object visaInput(HttpSession session) {
		return myDataService.visaInput(session);
	}

	/**
	 * 签证信息国家国旗页
	 */
	@At
	@Ok("jsp")
	public Object visaCountry() {
		return null;
	}

	/**
	 * 签证信息
	 */
	@At
	@GET
	@Ok("jsp")
	public Object visaInfo(HttpSession session, HttpServletRequest request) {
		return myDataService.getVisaInfo(session, request);
	}

	/**
	 * 签证信息修改保存
	 */
	@At
	@POST
	public Object saveEditVisa(@Param("..") VisaEditDataForm visaForm, HttpSession session) {
		return myDataService.saveEditVisa(visaForm, session);
	}

	/**
	 * 常用联系人
	 */
	@At
	@GET
	@Ok("jsp")
	public Object topContacts(HttpSession session, HttpServletRequest request) {
		return null;
	}

	/**
	 * 账户安全
	 */
	@At
	@GET
	@Ok("jsp")
	public Object safety(HttpSession session, HttpServletRequest request) {
		return null;
	}

}
