/**
 * OrderUSModule.java
 * com.juyo.visa.admin.orderUS.module
 * Copyright (c) 2018, 北京科技有限公司版权所有.
 */

package com.juyo.visa.admin.orderUS.module;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.mobile.form.BasicinfoUSForm;
import com.juyo.visa.admin.mobile.form.FamilyinfoUSForm;
import com.juyo.visa.admin.mobile.form.PassportinfoUSForm;
import com.juyo.visa.admin.mobile.form.TravelinfoUSForm;
import com.juyo.visa.admin.mobile.form.WorkandeducateinfoUSForm;
import com.juyo.visa.admin.orderUS.service.AutofillService;
import com.juyo.visa.admin.orderUS.service.NeworderUSViewService;
import com.juyo.visa.admin.orderUS.service.OrderUSViewService;

/**
 * 美国订单US
 *
 * @author   
 * @Date	 2018年3月30日 	 
 */
@IocBean
@At("/admin/neworderUS")
public class NeworderUSModule {

	@Inject
	private OrderUSViewService orderUSViewService;

	@Inject
	private NeworderUSViewService neworderUSViewService;

	@Inject
	private AutofillService autofillService;

	/**
	 * 跳转到拍摄资料页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object tofillimage(@Param("staffid") int staffid, HttpServletRequest request) {
		return neworderUSViewService.tofillimage(staffid, request);
	}

	/**
	 * 跳转到基本信息
	 */
	@At
	@GET
	@Ok("jsp")
	public Object toBasicinfo(@Param("staffid") int staffid) {
		return neworderUSViewService.toBasicinfo(staffid);
	}

	/**
	 * 保存基本信息
	 */
	@At
	@POST
	public Object saveBasicinfo(@Param("..") BasicinfoUSForm form) {
		return neworderUSViewService.saveBasicinfo(form);
	}

	/**
	 * 跳转到护照信息
	 */
	@At
	@GET
	@Ok("jsp")
	public Object toPassportinfo(@Param("staffid") int staffid) {
		return neworderUSViewService.toPassportinfo(staffid);
	}

	/**
	 * 保存护照信息
	 */
	@At
	@POST
	public Object savePassportinfo(@Param("..") PassportinfoUSForm form) {
		return neworderUSViewService.savePassportinfo(form);
	}

	/**
	 * 跳转到家庭信息
	 */
	@At
	@GET
	@Ok("jsp")
	public Object toFamilyinfo(@Param("staffid") int staffid) {
		return neworderUSViewService.toFamilyinfo(staffid);
	}

	/**
	 * 保存家庭信息
	 */
	@At
	@POST
	public Object saveFamilyinfo(@Param("..") FamilyinfoUSForm form) {
		return neworderUSViewService.saveFamilyinfo(form);
	}

	/**
	 * 跳转到职业与教育信息
	 */
	@At
	@GET
	@Ok("jsp")
	public Object toWorkandeducation(@Param("staffid") int staffid) {
		return neworderUSViewService.toWorkandeducation(staffid);
	}

	/**
	 * 保存职业与教育信息
	 */
	@At
	@POST
	public Object saveWorkandeducation(@Param("..") WorkandeducateinfoUSForm form) {
		return neworderUSViewService.saveWorkandeducation(form);
	}

	/**
	 * 跳转到旅行信息
	 */
	@At
	@GET
	@Ok("jsp")
	public Object toTravelinfo(@Param("staffid") int staffid) {
		return neworderUSViewService.toTravelinfo(staffid);
	}

	/**
	 * 保存旅行信息
	 */
	@At
	@POST
	public Object saveTravelinfo(@Param("..") TravelinfoUSForm form) {
		return neworderUSViewService.saveTravelinfo(form);
	}

	/**
	 * 国家模糊查询
	 */
	@At
	@POST
	public Object selectCountry(@Param("searchstr") String searchstr) {
		return neworderUSViewService.selectCountry(searchstr);
	}

	/**
	 * 美国州模糊查询
	 */
	@At
	@POST
	public Object selectUSstate(@Param("searchstr") String searchstr) {
		return neworderUSViewService.selectUSstate(searchstr);
	}

}
