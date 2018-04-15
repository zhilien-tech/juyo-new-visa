package com.juyo.visa.admin.bigcustomer.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.login.form.LoginForm;
import com.juyo.visa.admin.user.service.UserViewService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.UserLoginEnum;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
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

@IocBean
public class AppLoginViewService extends BaseService<TAppStaffBasicinfoEntity> {
	private static final Log log = Logs.get();

	public static final String IS_LOGIN_KEY = "isLogin";
	public static final String AUTHS_KEY = "auths";
	public static final String MENU_KEY = "menus";
	public static final String FUNCTION_MAP_KEY = "functionMap";
	public static final String USER_COMPANY_KEY = "user_company";
	public static final String LOGINUSER = "loginuser";
	
	@Inject
	private RedisDao redisDao;
	
	@Inject
	private UserViewService userViewService;

	/**
	 * TODO 短信登录
	 * <p>
	 * TODO 游客短信验证码登录
	 *
	 * @param form
	 * @param session
	 * @return TODO 验证码信息   session
	 */
	public Object messageLogin(LoginForm form, HttpSession session) {
		boolean isLogin = false;
		Map<String, Object> result = Maps.newHashMap();
		form.setUserType(UserLoginEnum.BIG_TOURIST_IDENTITY.intKey());
		form.setReturnUrl(">>:/appmobileus/progress.html");
		if (Util.isEmpty(form.getLoginName())) {
			form.setMessageErrMsg("手机号不能为空");
			result.put("isLogin", isLogin);
			result.put("loginData", form);
			return result;
		}
		if (Util.isEmpty(form.getValidateCode())) {
			form.setMessageErrMsg("验证码不能为空");
			result.put("isLogin", isLogin);
			result.put("loginData", form);
			return result;
		}
		SMSService smsService = new HuaxinSMSServiceImpl(redisDao);
		String captcha = smsService.getCaptcha(SmsType.LOGIN, form.getLoginName());
		if (Util.isEmpty(captcha)) {
			form.setMessageErrMsg("验证码已失效，请重新获取");
			result.put("isLogin", isLogin);
			result.put("loginData", form);
			return result;
		} else if (!captcha.equals(form.getValidateCode())) {
			form.setMessageErrMsg("验证码错误");
			result.put("isLogin", isLogin);
			result.put("loginData", form);
			return result;
		}
		Integer[] usertypes = {UserLoginEnum.BIG_TOURIST_IDENTITY.intKey() };
		TUserEntity user = dbDao.fetch(TUserEntity.class,
				Cnd.where("mobile", "=", form.getLoginName()).and("userType", "in", usertypes));
		if (Util.isEmpty(user)) {
			form.setMessageErrMsg("该游客不存在");
			result.put("isLogin", isLogin);
			result.put("loginData", form);
			return result;
		} else {
			if (CommonConstants.DATA_STATUS_VALID != user.getIsDisable()) {
				form.setMessageErrMsg("游客已被锁定，请联系管理员");
				result.put("isLogin", isLogin);
				result.put("loginData", form);
				return result;
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
				form.setReturnUrl("/appmobileus/touristlogin.html");
				Integer userType = user.getUserType();
				if (UserLoginEnum.BIG_TOURIST_IDENTITY.intKey() == userType) {
					//美国 进度页
					form.setMainurl("/appmobileus/progress.html");
					isLogin = true;
				}

			}
		}
		result.put("isLogin", isLogin);
		result.put("loginData", form);
		
		return result;
	}

	/**
	 * TODO 验证游客是否存在
	 * <p>
	 *
	 * @param mobile
	 * @return 
	 */
	public Object validateMobile(String mobile) {
		boolean result = false;
		TUserEntity user = dbDao.fetch(TUserEntity.class, Cnd.where("mobile", "=", mobile).and("userType", "=", UserLoginEnum.BIG_TOURIST_IDENTITY.intKey()));
		if (!Util.isEmpty(user)) {
			result = true;
		}
		return result;

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
}
