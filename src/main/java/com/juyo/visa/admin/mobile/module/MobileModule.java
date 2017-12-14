/**
 * MobileModule.java
 * com.juyo.visa.admin.mobile
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.mobile.module;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.juyo.visa.admin.mobile.form.MainApplicantForm;
import com.juyo.visa.admin.mobile.form.MarryImageForm;
import com.juyo.visa.admin.mobile.form.MobileApplicantForm;
import com.juyo.visa.admin.mobile.form.WealthInfoForm;
import com.juyo.visa.admin.mobile.form.WorkInfoForm;
import com.juyo.visa.admin.mobile.service.MobileService;
import com.juyo.visa.entities.TApplicantLowerEntity;
import com.juyo.visa.entities.TApplicantPassportLowerEntity;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年12月11日 	 
 */
@IocBean
@Filters
@At("/admin/mobile")
public class MobileModule {

	@Inject
	private MobileService mobileService;

	/**
	 * 申请人回显数据接口
	 */
	@At
	@POST
	public Object applicatinfo(@Param("..") MobileApplicantForm form) {
		return mobileService.applicatinfo(form);
	}

	/**
	 * 保存基本信息
	 */
	@At
	@POST
	public Object saveApplicatinfo(@Param("..") MobileApplicantForm form,
			@Param("..") TApplicantLowerEntity applicantEntity) {
		return mobileService.saveApplicatinfo(form, applicantEntity);
	}

	/**
	 * 护照信息回显数据
	 */
	@At
	@POST
	public Object passportinfo(@Param("..") MobileApplicantForm form) {
		return mobileService.passportinfo(form);
	}

	/**
	 * 保存护照信息
	 */
	@At
	@POST
	public Object savePassportInfo(@Param("..") TApplicantPassportLowerEntity passportinfo) {
		return mobileService.savePassportInfo(passportinfo);
	}

	/**
	 * 签证信息页面数据
	 */
	@At
	@POST
	public Object getVisaInfoData(@Param("..") MobileApplicantForm form) {
		return mobileService.getVisaInfoData(form);
	}

	/**
	 * 上传结婚照、离婚照片。。。。
	 */
	@At
	@AdaptBy(type = UploadAdaptor.class)
	public Object uploadPic(@Param("image") File file, HttpServletRequest request, HttpServletResponse response) {
		return mobileService.uploadPic(file, request, response);
	}

	/**
	 * 保存上传的照片信息
	 */
	@At
	@POST
	public Object saveCardInfo(@Param("..") MarryImageForm form) {
		return mobileService.saveCardInfo(form);
	}

	/**
	 * 获取申请人信息数据
	 */
	@At
	@POST
	public Object getApplicantData(@Param("applicantid") Integer applicantid) {
		return mobileService.getApplicantData(applicantid);
	}

	/**
	 * 保存主申请人数据
	 */
	@At
	@POST
	public Object saveMainApplicant(@Param("..") MainApplicantForm form) {
		return mobileService.saveMainApplicant(form);
	}

	/**
	 * 获取申请人的职业或教育信息
	 */
	@At
	@POST
	public Object getJobOrEducationData(@Param("applicantid") Long applicantid) {
		return mobileService.getJobOrEducationData(applicantid);
	}

	/**
	 * 保存申请人的职业教育信息
	 */
	@At
	@POST
	public Object saveJobOrEducationData(@Param("..") WorkInfoForm form) {
		return mobileService.saveJobOrEducationData(form);
	}

	/**
	 * 获取财产信息页面数据
	 */
	@At
	@POST
	public Object getWealthData(@Param("applicantid") Integer applicantid) {
		return mobileService.getWealthData(applicantid);
	}

	/**
	 * 保存财产信息
	 */
	@At
	@POST
	public Object saveWealthData(@Param("..") WealthInfoForm form) {
		return mobileService.saveWealthData(form);
	}
}
