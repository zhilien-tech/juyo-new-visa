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
	public Object savePassportInfo(@Param("..") TApplicantPassportLowerEntity passportinfo) {
		return simpleMobileService.savePassportInfo(passportinfo);
	}

	/**
	 * 保存签证信息
	 */
	@At
	@POST
	public Object saveVisaInfo(@Param("applicantid") Integer applicantid, @Param("marrystatus") Integer marrystatus) {
		return simpleMobileService.saveVisaInfo(applicantid, marrystatus);
	}
}
