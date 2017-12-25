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

import com.juyo.visa.admin.login.enums.LoginTypeEnum;
import com.juyo.visa.admin.login.form.LoginForm;
import com.juyo.visa.admin.login.service.LoginService;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.entities.TUserEntity;

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
public class LoginModule {

	private static final Log log = Logs.get();
	@Inject
	private LoginService loginService;

	/**
	 * 跳转到list页面
	 */
	@At
	@GET
	@Filters
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
	public Object login(@Param("..") final LoginForm form, final HttpSession session, HttpServletRequest req,
			ViewModel model) {
		loginService.login(form, session, req);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userType = loginUser.getUserType();
		model.setv("errMsg", form.getErrMsg());
		model.setv("mainurl", form.getMainurl());
		session.setAttribute("mainurl", form.getMainurl());
		session.setAttribute("logintype", LoginTypeEnum.WORK.intKey());
		session.setAttribute("userType", userType);
		return form.getReturnUrl();
	}

	/**
	 * 游客登录
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
	public Object tlogin(@Param("..") final LoginForm form, final HttpSession session, HttpServletRequest req,
			ViewModel model) {
		loginService.login(form, session, req);
		model.setv("errMsg", form.getErrMsg());
		model.setv("passwordlogin", 1);
		model.setv("mainurl", form.getMainurl());
		session.setAttribute("mainurl", form.getMainurl());
		session.setAttribute("logintype", LoginTypeEnum.TOURST.intKey());
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
	//@Ok(">>:/")
	@Ok("re")
	public Object logout(final HttpSession session, @Param("logintype") Integer logintype) {
		return loginService.logout(session, logintype);
	}

	/**
	 * 发送手机验证码
	 */
	@At
	@Filters
	@POST
	public Object sendValidateCode(@Param("mobile") final String mobilenum) {
		return loginService.sendValidateCode(mobilenum);
	}

	/**
	 * 短信验证码登录
	 */
	@At
	@Filters
	@POST
	@Ok("re")
	public Object messageLogin(@Param("..") final LoginForm form, final HttpSession session,
			HttpServletRequest request, ViewModel model) {
		loginService.messageLogin(form, session);
		model.setv("errMsg", form.getErrMsg());
		model.setv("messageErrMsg", form.getMessageErrMsg());
		model.setv("mainurl", form.getMainurl());
		session.setAttribute("mainurl", form.getMainurl());
		session.setAttribute("logintype", LoginTypeEnum.TOURST.intKey());
		return form.getReturnUrl();
	}

	/**
	 * 验证游客是否存在
	 */
	@At
	@Filters
	@POST
	public Object validateMobile(@Param("mobile") String mobile) {
		return loginService.validateMobile(mobile);
	}
}
