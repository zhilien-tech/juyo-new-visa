/**
 * SimpleMobileModule.java
 * com.juyo.visa.admin.simple.module
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.simple.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.mobile.form.MobileApplicantForm;
import com.juyo.visa.admin.simple.service.SimpleMobileService;
import com.juyo.visa.entities.TApplicantLowerEntity;
import com.juyo.visa.entities.TApplicantPassportLowerEntity;
import com.juyo.visa.entities.TApplicantVisaOtherInfoEntity;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年2月1日 	 
 */
@At("/admin/simplemobile")
@IocBean
@Filters
public class SimpleMobileModule {

	@Inject
	private SimpleMobileService simpleMobileService;

	/**
	 * 保存基本信息
	 */
	@At
	@POST
	public Object saveApplicatinfo(@Param("..") MobileApplicantForm form,
			@Param("..") TApplicantLowerEntity applicantEntity) {
		return simpleMobileService.saveApplicatinfo(form, applicantEntity);
	}

	/**
	 * 保存护照信息
	 */
	@At
	@POST
	public Object savePassportInfo(@Param("..") TApplicantPassportLowerEntity passportinfo,
			@Param("..") MobileApplicantForm form) {
		return simpleMobileService.savePassportInfo(passportinfo, form);
	}

	/**
	 * 保存签证信息
	 */
	@At
	@POST
	public Object saveVisaInfo(@Param("applicantid") Integer applicantid, @Param("marrystatus") Integer marrystatus) {
		return simpleMobileService.saveVisaInfo(applicantid, marrystatus);
	}

	/**
	 * 获取签证其他信息数据
	 */
	@At
	@POST
	public Object getVisaOtherInfoData(@Param("applicantid") Integer applicantid) {
		return simpleMobileService.getVisaOtherInfoData(applicantid);
	}

	/**
	 * 保存酒店信息
	 */
	@At
	@POST
	public Object saveHotelInfo(@Param("..") TApplicantVisaOtherInfoEntity form) {
		return simpleMobileService.saveHotelInfo(form);
	}

	/**
	 * 保存在日担保人信息
	 */
	@At
	@POST
	public Object saveVouchInfo(@Param("..") TApplicantVisaOtherInfoEntity form) {
		return simpleMobileService.saveVouchInfo(form);
	}

	/**
	 * 保存邀请人信息
	 */
	@At
	@POST
	public Object saveInviteInfo(@Param("..") TApplicantVisaOtherInfoEntity form) {
		return simpleMobileService.saveInviteInfo(form);
	}

	/**
	 * 保存其他信息
	 */
	@At
	@POST
	public Object saveOtherInfo(@Param("..") TApplicantVisaOtherInfoEntity form) {
		return simpleMobileService.saveOtherInfo(form);
	}

	@At
	@POST
	public Object isPassporturl(@Param("applicantid") int applicantid) {
		return simpleMobileService.isPassporturl(applicantid);
	}

	@At
	@POST
	public Object isCardurl(@Param("applicantid") int applicantid) {
		return simpleMobileService.isCardurl(applicantid);
	}

}
