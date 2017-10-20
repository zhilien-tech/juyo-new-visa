/**
 * LoginService.java
 * com.juyo.visa.admin.login.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.login.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.juyo.visa.admin.login.form.LoginForm;
import com.juyo.visa.admin.user.service.UserViewService;
import com.juyo.visa.common.access.AccessConfig;
import com.juyo.visa.common.access.sign.MD5;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年10月19日 	 
 */
@IocBean
public class LoginService extends BaseService<TUserEntity> {

	public static final String IS_LOGIN_KEY = "isLogin";
	public static final String AUTHS_KEY = "auths";
	public static final String MENU_KEY = "menus";
	public static final String FUNCTION_MAP_KEY = "functionMap";
	public static final String USER_COMPANY_KEY = "user_company";
	public static final String LOGINUSER = "loginuser";

	@Inject
	private UserViewService userViewService;

	/**
	 * 获取当前登录用户
	 * <p>
	 * TODO获取当前登录用户
	 *
	 * @param session
	 * @return TODO获取当前登录用户
	 */
	public static TUserEntity getLoginUser(final HttpSession session) {
		TUserEntity user = null;
		Object loginuser = session.getAttribute(LOGINUSER);
		if (loginuser instanceof TUserEntity) {
			user = (TUserEntity) loginuser;
		}
		return user;
	}

	/**
	 * 获取当前公司
	 * <p>
	 * TODO获取当前登录用户所在公司
	 *
	 * @param session
	 * @return TODO获取当前登录用户所在的公司
	 */
	public static TCompanyEntity getLoginCompany(final HttpSession session) {
		TCompanyEntity company = null;
		Object logincompany = session.getAttribute(LOGINUSER);
		if (logincompany instanceof TCompanyEntity) {
			company = (TCompanyEntity) logincompany;
		}
		return company;
	}

	public boolean login(final LoginForm form, final HttpSession session, final HttpServletRequest req) {

		form.setReturnUrl("jsp:admin.login");
		String loginName = form.getLoginName();
		if (Util.isEmpty(loginName)) {
			form.setErrMsg("用户名不能为空");
			return false;
		}

		String password = form.getPassword();
		if (Util.isEmpty(password)) {
			form.setErrMsg("密码不能为空");
			return false;
		}

		String recode = (String) session.getAttribute(CommonConstants.CONFIRMCODE);
		if (Util.isEmpty(recode)) {
			form.setErrMsg(null);
			return false;
		}

		String vCode = form.getValidateCode();
		if (Util.isEmpty(vCode) || !recode.equalsIgnoreCase(vCode)) {
			form.setErrMsg("验证码不正确");
			return false;
		}

		String passwd = MD5.sign(form.getPassword(), AccessConfig.password_secret, AccessConfig.INPUT_CHARSET);

		TUserEntity user = userViewService.findUser(loginName, passwd);
		if (user == null) {
			form.setErrMsg("用户名或密码错误");
			return false;
		} else {
			if (CommonConstants.DATA_STATUS_VALID != user.getIsDisable()) {
				form.setErrMsg("账号未激活");
				return false;
			}
			int userType = user.getUserType();
		}
		return true;
	}

	/**
	 * 登出
	 * <p>
	 * TODO退出登录
	 *
	 * @param session 需要清除session的内容
	 */
	public void logout(final HttpSession session) {
		session.removeAttribute(USER_COMPANY_KEY);
		session.removeAttribute(FUNCTION_MAP_KEY);
		session.removeAttribute(MENU_KEY);
		session.removeAttribute(AUTHS_KEY);
		session.removeAttribute(LOGINUSER);
		session.removeAttribute(IS_LOGIN_KEY);
		session.invalidate();
	}
}
