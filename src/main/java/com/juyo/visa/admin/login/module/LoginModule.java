/**
 * LoginModule.java
 * com.juyo.visa.admin.login.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.login.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ViewModel;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.login.form.LoginForm;
import com.juyo.visa.admin.login.service.LoginService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年10月19日 	 
 */
@IocBean
@At("/admin")
@Filters({//@By(type = AuthFilter.class)
})
public class LoginModule {

	private static final Log log = Logs.get();
	@Inject
	private LoginService loginService;

	/**
	 * 跳转到list页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object login() {
		return null;
	}

	/**
	 * 登录
	 * @param username 用户名
	 * @param password 密码
	 * @param session session会话对象
	 * @return 
	*/
	@At
	@POST
	@Filters
	@Ok("re")
	//登录成功返回主页,失败返回登录页
	public Object login(@Param("..") final LoginForm form, final HttpSession session, final HttpServletRequest req,
			ViewModel model) {
		loginService.login(form, session, req);
		model.setv("errMsg", form.getErrMsg());
		return form.getReturnUrl();
	}

	/**
	 * 登出
	 * <p>
	 * 退出登录
	 *
	 * @param session 
	 */
	@At
	@Filters
	@Ok(">>:/")
	public void logout(final HttpSession session) {
		loginService.logout(session);
	}
}
