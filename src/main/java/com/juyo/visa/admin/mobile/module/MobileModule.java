/**
 * MobileModule.java
 * com.juyo.visa.admin.mobile
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.mobile.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.mobile.form.MobileApplicantForm;
import com.juyo.visa.admin.mobile.service.MobileService;

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
}
