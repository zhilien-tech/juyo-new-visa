package com.juyo.visa.admin.bigcustomer.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ViewModel;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.bigcustomer.service.AppLoginViewService;
import com.juyo.visa.admin.login.enums.LoginTypeEnum;
import com.juyo.visa.admin.login.form.LoginForm;

@IocBean
@Filters
@At("/admin/applogin")
public class AppLoginModule {

	private static final Log log = Logs.get();

	@Inject
	private AppLoginViewService appLoginViewService;

	/**
	 * 发送手机验证码
	 */
	@At
	@Filters
	@POST
	public Object sendValidateCode(@Param("mobile") final String mobilenum) {
		return appLoginViewService.sendValidateCode(mobilenum);
	}

	/**
	 * 短信验证码登录
	 */
	@At
	@Filters
	@POST
	public Object messageLogin(@Param("..") final LoginForm form, final HttpSession session,
			HttpServletRequest request) {
		return appLoginViewService.messageLogin(form, session);
	}

	/**
	 * 验证游客是否存在
	 */
	@At
	@Filters
	@POST
	public Object validateMobile(@Param("mobile") String mobile) {
		return appLoginViewService.validateMobile(mobile);
	}
}
