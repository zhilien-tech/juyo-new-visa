/**
 * LoginService.java
 * com.juyo.visa.admin.login.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.login.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Lists;
import com.juyo.visa.admin.company.service.CompanyViewService;
import com.juyo.visa.admin.login.form.LoginForm;
import com.juyo.visa.admin.user.service.UserViewService;
import com.juyo.visa.common.access.AccessConfig;
import com.juyo.visa.common.access.sign.MD5;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.UserLoginEnum;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TFunctionEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.redis.RedisDao;
import com.uxuexi.core.web.base.service.BaseService;
import com.we.business.sms.SMSService;
import com.we.business.sms.enums.SmsType;
import com.we.business.sms.impl.HuaxinSMSServiceImpl;

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
	@Inject
	private CompanyViewService companyViewService;
	@Inject
	private RedisDao redisDao;

	SMSService smsService = new HuaxinSMSServiceImpl(redisDao);

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

	/**
	 * 用户登录
	 * <p>
	 * TODO工作人员登录（密码登录）
	 *
	 * @param form
	 * @param session
	 * @param req
	 * @return TODO form 用户登录信息、session 用户session req request
	 */
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

		TUserEntity user = userViewService.findUser(loginName, passwd, form.getIsTourist());
		if (user == null) {
			form.setErrMsg("用户名或密码错误");
			return false;
		} else {
			if (CommonConstants.DATA_STATUS_VALID != user.getIsDisable()) {
				form.setErrMsg("账号未激活");
				return false;
			}
			int userType = user.getUserType();
			Sql companySql = Sqls.create(sqlManager.get("select_login_company"));
			companySql.params().set("userid", user.getId());
			List<TCompanyEntity> companyLst = DbSqlUtil.query(dbDao, TCompanyEntity.class, companySql);
			if (UserLoginEnum.ADMIN.intKey() != userType && companyLst.size() != 1) {
				throw new IllegalArgumentException("用户必须且只能在一家公司就职");
			}
			TCompanyEntity company = companyLst.get(0);
			session.setAttribute(USER_COMPANY_KEY, company); //公司
			//用户所包含的功能
			List<TFunctionEntity> allUserFunction = Lists.newArrayList();
			if (!Util.isEmpty(user) && CommonConstants.SUPER_ADMIN.equals(user.getName())) {
				//超级管理员
				allUserFunction = dbDao.query(TFunctionEntity.class, Cnd.NEW().orderBy("sort", "asc"), null);
			} else if (UserLoginEnum.SQ_COMPANY_ADMIN.intKey() == userType
					|| UserLoginEnum.DJ_COMPANY_ADMIN.intKey() == userType) {
				//公司管理员
				allUserFunction = companyViewService.getCompanyFunctions(company.getId());
			} else {
				//普通用户
				allUserFunction = userViewService.getUserFunctions(user.getId());
			}
			//控制页面跳转
			if (UserLoginEnum.ADMIN.intKey() == userType) {
				//平台管理员跳转页面
				form.setReturnUrl(">>:/admin/Company/list.html");
			} else if (UserLoginEnum.SQ_COMPANY_ADMIN.intKey() == userType
					|| UserLoginEnum.DJ_COMPANY_ADMIN.intKey() == userType) {
				//公司管理员条跳转页面
				form.setReturnUrl(">>:/admin/Company/list.html");
			} else {
				//普通员工跳转页面
				form.setReturnUrl(">>:/admin/Company/list.html");
			}
			//将用户权限保存到session中
			//session.setAttribute(FUNCTION_MAP_KEY, functionMap); //功能
			//session.setAttribute(MENU_KEY, menus); //菜单
			session.setAttribute(AUTHS_KEY, allUserFunction); //所有功能
			session.setAttribute(LOGINUSER, user);
			session.setAttribute(IS_LOGIN_KEY, true);
			session.setAttribute("currentPageIndex", 0);
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

	/**
	 * TODO 发送手机验证码
	 * <p>
	 * TODO 发送手机短信验证码（验证码长度5位数，有效期5分钟，每个用户每个业务每日最多获取5条）
	 *
	 * @param mobilenum TODO 手机号
	 */
	public String sendValidateCode(String mobilenum) {
		String result = "error";
		try {
			smsService.sendCaptcha(SmsType.LOGIN, mobilenum);
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * TODO 短信登录
	 * <p>
	 * TODO 游客短信验证码登录
	 *
	 * @param form
	 * @param session
	 * @return TODO 验证码信息   session
	 */
	public boolean messageLogin(LoginForm form, HttpSession session) {
		String captcha = smsService.getCaptcha(SmsType.LOGIN, form.getLoginName());
		if (!Util.isEmpty(captcha)) {
			form.setErrMsg("验证码已失效，请重新获取");
			return false;
		} else if (!"captcha".equals(form.getValidateCode())) {
			form.setErrMsg("验证码错误");
			return false;
		}
		TUserEntity user = dbDao.fetch(
				TUserEntity.class,
				Cnd.where("name", "=", form.getLoginName()).and("userType", "=",
						UserLoginEnum.TOURIST_IDENTITY.intKey()));
		if (Util.isEmpty(user)) {
			form.setErrMsg("该游客不存在");
			return false;
		} else {
			if (CommonConstants.DATA_STATUS_VALID != user.getIsDisable()) {
				form.setErrMsg("游客已被锁定，请联系管理员");
				return false;
			} else {
				//登陆成功
				Sql companySql = Sqls.create(sqlManager.get("select_login_company"));
				companySql.params().set("userid", user.getId());
				List<TCompanyEntity> companyLst = DbSqlUtil.query(dbDao, TCompanyEntity.class, companySql);
				if (companyLst.size() != 1) {
					throw new IllegalArgumentException("用户必须且只能在一家公司就职");
				}
				TCompanyEntity company = companyLst.get(0);
				session.setAttribute(USER_COMPANY_KEY, company); //公司
				List<TFunctionEntity> allUserFunction = userViewService.getUserFunctions(user.getId());
				session.setAttribute(AUTHS_KEY, allUserFunction); //所有功能
				session.setAttribute(LOGINUSER, user);
				session.setAttribute(IS_LOGIN_KEY, true);
				session.setAttribute("currentPageIndex", 0);
				//跳转到办理中的签证页面
				form.setReturnUrl(">>:/admin/operationsArea/desktop.html");
			}
		}
		return true;
	}
}
