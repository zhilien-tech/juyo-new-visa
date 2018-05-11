/**
 * LoginService.java
 * com.juyo.visa.admin.login.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
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
import com.juyo.visa.admin.login.enums.LoginTypeEnum;
import com.juyo.visa.admin.login.form.LoginForm;
import com.juyo.visa.admin.user.service.UserViewService;
import com.juyo.visa.common.access.AccessConfig;
import com.juyo.visa.common.access.sign.MD5;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
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

		if (form.getIsTourist() == IsYesOrNoEnum.YES.intKey()) {
			form.setReturnUrl("jsp:tlogin");
		} else {
			form.setReturnUrl("jsp:admin.login");
		}
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
					|| UserLoginEnum.DJ_COMPANY_ADMIN.intKey() == userType
					|| UserLoginEnum.JJ_COMPANY_ADMIN.intKey() == userType
					|| UserLoginEnum.BIG_COMPANY_ADMIN.intKey() == userType
					|| UserLoginEnum.BAOYING_US.intKey() == userType
					|| UserLoginEnum.BIG_COMPANY_CUSTOMER.intKey() == userType) {
				//公司管理员
				allUserFunction = companyViewService.getCompanyFunctions(company.getId());
			} else {
				//普通用户
				allUserFunction = userViewService.getUserFunctions(user.getId());
			}
			//控制页面跳转
			form.setReturnUrl(">>:/public/menu.html");
			if (UserLoginEnum.ADMIN.intKey() == userType) {
				//平台管理员跳转页面
				form.setMainurl("/admin/company/list.html");
			} else if (UserLoginEnum.SQ_COMPANY_ADMIN.intKey() == userType
					|| UserLoginEnum.JJ_COMPANY_ADMIN.intKey() == userType) {
				//公司管理员条跳转页面(权限管理)
				form.setMainurl("/admin/authority/list.html");
			} else if (UserLoginEnum.DJ_COMPANY_ADMIN.intKey() == userType) {
				//地接社公司管理员
				form.setMainurl("/admin/JapanDijie/japanList.html");
			} else if (UserLoginEnum.JJ_COMPANY_ADMIN.intKey() == userType) {
				//送签社精简公司管理员
				form.setMainurl("/admin/simple/list.html");
			} else if (UserLoginEnum.BIG_COMPANY_ADMIN.intKey() == userType) {
				//大客户公司管理员
				form.setMainurl("/admin/baoying/listUS.html");
			} /*else if (UserLoginEnum.TOURIST_IDENTITY.intKey() == userType) {
				//游客跳转的页面
				form.setMainurl("/admin/myVisa/visaList.html");
				}*/else if (UserLoginEnum.BIG_COMPANY_CUSTOMER.intKey() == userType) {
				//大客户跳转的页面
				form.setMainurl("/admin/bigCustomer/list.html");
			} else if (UserLoginEnum.BIG_TOURIST_IDENTITY.intKey() == userType
					|| UserLoginEnum.TOURIST_IDENTITY.intKey() == userType) {
				//大客户游客跳转的页面
				form.setMainurl("/admin/pcVisa/toReload.html");
			} else if (UserLoginEnum.BAOYING_US.intKey() == userType) {
				//大客户管理员跳转的页面
				form.setMainurl("/admin/baoying/listUS.html");
			} else {
				//功能列表为空
				if (Util.isEmpty(allUserFunction)) {
					form.setErrMsg("未设置权限");
					return false;
				}
				//普通员工跳转页面（默认第一个功能）
				String url = "";
				for (TFunctionEntity tFunctionEntity : allUserFunction) {
					if (!Util.isEmpty(tFunctionEntity.getUrl())) {
						url = tFunctionEntity.getUrl();
						break;
					}
				}
				//所分配功能的URL地址全部为空
				if (Util.isEmpty(url)) {
					form.setErrMsg("未设置功能URL");
					return false;
				}
				form.setMainurl(url);
			}
			//将用户权限保存到session中
			//session.setAttribute(FUNCTION_MAP_KEY, functionMap); //功能
			//session.setAttribute(MENU_KEY, menus); //菜单
			session.setAttribute(AUTHS_KEY, allUserFunction); //所有功能
			session.setAttribute(LOGINUSER, user);
			session.setAttribute(IS_LOGIN_KEY, true);
			session.setAttribute("currentPageIndex", 0);
			//设置session过期时间为24小时
			session.setMaxInactiveInterval(60 * 60 * 10);
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
	public Object logout(final HttpSession session, Integer logintype) {
		session.removeAttribute(USER_COMPANY_KEY);
		//session.removeAttribute(FUNCTION_MAP_KEY);
		//session.removeAttribute(MENU_KEY);
		session.removeAttribute(AUTHS_KEY);
		session.removeAttribute(LOGINUSER);
		session.removeAttribute(IS_LOGIN_KEY);
		session.invalidate();
		if (!Util.isEmpty(logintype) && LoginTypeEnum.TOURST.intKey() == logintype) {
			return ">>:/tlogin.html";
		} else {
			return ">>:/";
		}
	}

	/**
	 * TODO 发送手机验证码
	 * <p>
	 * TODO 发送手机短信验证码（验证码长度5位数，有效期5分钟，每个用户每个业务每日最多获取5条）
	 *
	 * @param mobilenum TODO 手机号
	 */
	public String sendValidateCode(String mobilenum) {
		String result = "发送失败";
		try {
			SMSService smsService = new HuaxinSMSServiceImpl(redisDao);
			smsService.sendCaptcha(SmsType.LOGIN, mobilenum);
			result = "发送成功";
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
		form.setReturnUrl("jsp:admin.tlogin");
		if (Util.isEmpty(form.getLoginName())) {
			form.setMessageErrMsg("手机号不能为空");
			return false;
		}
		if (Util.isEmpty(form.getValidateCode())) {
			form.setMessageErrMsg("验证码不能为空");
			return false;
		}
		SMSService smsService = new HuaxinSMSServiceImpl(redisDao);
		String captcha = smsService.getCaptcha(SmsType.LOGIN, form.getLoginName());
		if (Util.isEmpty(captcha)) {
			form.setMessageErrMsg("验证码已失效，请重新获取");
			return false;
		} else if (!captcha.equals(form.getValidateCode())) {
			form.setMessageErrMsg("验证码错误");
			return false;
		}
		Integer[] usertypes = { UserLoginEnum.TOURIST_IDENTITY.intKey(), UserLoginEnum.BIG_TOURIST_IDENTITY.intKey() };
		TUserEntity user = dbDao.fetch(TUserEntity.class,
				Cnd.where("mobile", "=", form.getLoginName()).and("userType", "in", usertypes));
		if (Util.isEmpty(user)) {
			form.setMessageErrMsg("该游客不存在");
			return false;
		} else {
			if (CommonConstants.DATA_STATUS_VALID != user.getIsDisable()) {
				form.setMessageErrMsg("游客已被锁定，请联系管理员");
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
				//设置session过期时间为24小时
				session.setMaxInactiveInterval(60 * 60 * 10);
				form.setReturnUrl(">>:/public/menu.html");

				Integer userType = user.getUserType();
				if (UserLoginEnum.TOURIST_IDENTITY.intKey() == userType) {
					//跳转到办理中的签证页面
					form.setMainurl("/admin/myVisa/visaList.html");
				} else if (UserLoginEnum.BIG_TOURIST_IDENTITY.intKey() == userType) {
					//美国 办理中签证页面
					form.setMainurl("/admin/pcVisa/visaList.html");
				}

			}
		}
		return true;
	}

	/**
	 * TODO 验证游客是否存在
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param mobile
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object validateMobile(String mobile) {
		boolean result = false;
		TUserEntity user = dbDao.fetch(TUserEntity.class, Cnd.where("mobile", "=", mobile));
		if (!Util.isEmpty(user)) {
			result = true;
		}
		return result;

	}
}
