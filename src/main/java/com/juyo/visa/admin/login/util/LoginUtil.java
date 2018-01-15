/**
 * LoginUtil.java
 * com.juyo.visa.admin.login
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.login.util;

import javax.servlet.http.HttpSession;

import com.juyo.visa.admin.login.service.LoginService;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TUserEntity;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年10月25日 	 
 */
public class LoginUtil {

	/**
	 * 获取当前登录用户
	 * <p>
	 * TODO获取当前登录用户
	 *
	 * @param session
	 * @return TODO获取当前登录用户
	 */
	public static synchronized TUserEntity getLoginUser(final HttpSession session) {
		TUserEntity user = null;
		Object loginuser = session.getAttribute(LoginService.LOGINUSER);
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
	public static synchronized TCompanyEntity getLoginCompany(final HttpSession session) {
		TCompanyEntity company = null;
		Object logincompany = session.getAttribute(LoginService.USER_COMPANY_KEY);
		if (logincompany instanceof TCompanyEntity) {
			company = (TCompanyEntity) logincompany;
		}
		return company;
	}
}
