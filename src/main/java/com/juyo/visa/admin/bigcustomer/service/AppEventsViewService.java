package com.juyo.visa.admin.bigcustomer.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.POST;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.bigcustomer.form.SignUpEventForm;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.orderUS.service.OrderUSViewService;
import com.juyo.visa.admin.user.form.ApplicantUser;
import com.juyo.visa.admin.user.service.UserViewService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.UserLoginEnum;
import com.juyo.visa.common.enums.visaProcess.VisaCountryEnum;
import com.juyo.visa.common.enums.visaProcess.VisaProcess_US_Enum;
import com.juyo.visa.common.enums.visaProcess.VisaStatusEnum;
import com.juyo.visa.entities.TAppEventsIntroduceEntity;
import com.juyo.visa.entities.TAppStaffAddressEntity;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TAppStaffEventsEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.entities.TUserJobEntity;
import com.juyo.visa.forms.TAppEventsForm;
import com.juyo.visa.forms.TAppStaffBasicinfoAddForm;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
public class AppEventsViewService extends BaseService<TAppStaffBasicinfoEntity> {
	private static final Log log = Logs.get();

	@Inject
	private BigCustomerViewService bigCustomerViewService;

	@Inject
	private OrderUSViewService orderUSViewService;

	@Inject
	private UserViewService userViewService;

	private final static String DEFAULT_PASSWORD = "000000";

	/**
	 * 
	 * 大客户 首页 列表页
	 *
	 * @param queryForm
	 * @param session
	 * @return
	 */
	public Object listData(TAppEventsForm queryForm, HttpSession session) {

		//当前登录公司Id
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Integer comId = loginCompany.getId();

		Map<String, Object> map = listPage4Datatables(queryForm);
		return map;
	}

	/**
	 * 
	 * TODO打开 活动报名页
	 *
	 * @param eventId 活动id
	 * @param wechatToken 微信个人Token
	 * @param session
	 * @return 
	 */
	public Object toSignUpEventPage(Integer eventId, String wechatToken, HttpSession session) {

		//查询是否报名
		String sqlStr = sqlManager.get("appevents_staff_whether_signup");
		Sql sql = Sqls.create(sqlStr);
		Cnd cnd = Cnd.NEW();
		cnd.and("tasb.wechattoken", "=", wechatToken);
		cnd.and("tase.eventsId", "=", eventId);
		List<Record> list = dbDao.query(sql, cnd, null);

		Map<String, Object> map = MapUtil.map();
		Record record = new Record();
		if (!Util.isEmpty(eventId)) {
			//活动详情
			String eventSqlStr = sqlManager.get("appevents_detail_by_eventId");
			Sql eventSql = Sqls.create(eventSqlStr);
			eventSql.setParam("eventId", eventId);
			record = dbDao.fetch(eventSql);
		}
		map.put("eventId", eventId);
		map.put("event", record);
		map.put("wechatToken", wechatToken);
		map.put("isSignUp", !Util.isEmpty(list));

		return map;
	}

	/**
	 * 
	 * App 首页活动 详情页
	 * 
	 * @param eventId
	 * @param session
	 * @return 
	 */
	public Object appEventDetails(Integer eventId, HttpSession session) {
		Record record = new Record();
		if (!Util.isEmpty(eventId)) {
			//活动详情
			String sqlStr = sqlManager.get("appevents_detail_by_eventId");
			Sql sql = Sqls.create(sqlStr);
			sql.setParam("eventId", eventId);
			record = dbDao.fetch(sql);

			//活动介绍
			List<TAppEventsIntroduceEntity> eventIntroduceList = dbDao.query(TAppEventsIntroduceEntity.class,
					Cnd.where("eventsId", "=", eventId), null);
			record.put("eventIntroduceList", eventIntroduceList);
		}

		return record;
	}

	/**
	 * 
	 * 报名活动
	 *
	 * @param eventId 活动id
	 * @param session 
	 * @return 
	 */
	public Object signUpEvents(Integer eventId, HttpSession session) {
		//当前登录用户Id
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer loginUserId = loginUser.getId();

		if (!Util.isEmpty(eventId)) {
			TAppStaffEventsEntity ase = new TAppStaffEventsEntity();
			ase.setEventsId(eventId);
			ase.setStaffId(loginUserId);
			dbDao.insert(ase);
			return JsonUtil.toJson("报名成功");
		} else {
			return JsonUtil.toJson("报名失败");
		}
	}

	/**
	 * 通过公众号，进行报名活动
	 * 
	 * 注：活动详情为图片
	 */
	public Object signUpEventByPublicNum(SignUpEventForm form, HttpSession session) {

		String firstname = form.getFirstname();
		String lastname = form.getLastname();
		Map<String, String> jo = new HashMap<String, String>();
		//校验姓名是否合格
		if (!(isChineseStr(firstname) && isChineseStr(lastname))) {
			//输入非法
			jo.put("flag", "2");
			jo.put("msg", "姓名必须为中文");
			return jo;

		} else {
			//当前登录用户Id
			TUserEntity loginUser = LoginUtil.getLoginUser(session);
			//Integer loginUserId = loginUser.getId();
			//添加人员
			TAppStaffBasicinfoAddForm staffForm = new TAppStaffBasicinfoAddForm();
			staffForm.setFirstname(form.getFirstname());
			staffForm.setLastname(form.getLastname());
			//旧用户无openid时 根据手机验证是否已经登陆
			staffForm.setTelephone(form.getTelephone());
			staffForm.setEmail(form.getEmail());
			if (!Util.isEmpty(form.getWeChatToken())) {
				staffForm.setWechattoken(form.getWeChatToken());
			}
			//默认签证状态为办理中
			staffForm.setVisastatus(VisaStatusEnum.HANDLING_VISA.intKey());

			//进行校验 更新 回显  还是新增 --------------------------------------重点
			Map<String, String> map = (Map<String, String>) bigCustomerViewService.addStaff(staffForm, session);
			//---------------------------------------------------------------------
			//只进行更新操作
			//map.flag 3代表回显 1代表更新 0代表新增 2代表违法
			if (map.get("flag").equals("0")) {
				String staffIdStr = map.get("staffId");
				Integer staffId = Integer.valueOf(staffIdStr);
				Integer eventId = form.getEventId();
				//人员报名活动
				TAppStaffEventsEntity staffEventEntity = new TAppStaffEventsEntity();
				staffEventEntity.setEventsId(eventId);
				staffEventEntity.setStaffId(staffId);

				TAppStaffEventsEntity insertEntity = dbDao.insert(staffEventEntity);

				//添加订单
				orderUSViewService.addOrderByStuffId(staffId, loginUser.getId());

				//用户登录，添加游客信息
				TAppStaffBasicinfoEntity staffInfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, Long.valueOf(staffId));
				Integer loginUserId = (Integer) addLoginUser(staffInfo);

				dbDao.update(TAppStaffBasicinfoEntity.class, Chain.make("userid", loginUserId),
						Cnd.where("id", "=", staffId));
				jo.put("flag", "0");
			} else if (map.get("flag").equals("3")) {
				return map;
			}
		}
		return jo;
	}

	/**
	 * 用户登录添加游客
	 */
	public Object addLoginUser(TAppStaffBasicinfoEntity applicant) {
		//游客登录
		Integer userId = null;
		ApplicantUser applicantUser = new ApplicantUser();
		applicantUser.setMobile(applicant.getTelephone());
		//applicantUser.setOpid(applicant.getOpid());
		applicantUser.setPassword(DEFAULT_PASSWORD);
		applicantUser.setUsername(applicant.getFirstname() + applicant.getLastname());
		if (!Util.isEmpty(applicant.getTelephone())) {
			TUserEntity userEntity = dbDao.fetch(TUserEntity.class, Cnd.where("mobile", "=", applicant.getTelephone())
					.and("userType", "=", UserLoginEnum.BIG_TOURIST_IDENTITY.intKey()));
			if (Util.isEmpty(userEntity)) {
				TUserEntity tUserEntity = addApplicantUser(applicantUser);
				userId = tUserEntity.getId();
				applicant.setUserid(userId);
			} else {
				userEntity.setName(applicantUser.getUsername());
				userEntity.setMobile(applicant.getTelephone());
				userEntity.setPassword(applicantUser.getPassword());
				userEntity.setOpId(applicantUser.getOpid());
				userEntity.setUpdateTime(new Date());
				userId = userEntity.getId();
				applicant.setUserid(userId);
				dbDao.update(userEntity);
			}
		}
		return userId;
	}

	/**
	 * TODO 添加申请人
	 * <p>
	 * TODO 添加申请人（大客户游客）到用户表   以便登录
	 *
	 * @param applyuser
	 * @return TODO 添加申请人
	 */
	public TUserEntity addApplicantUser(ApplicantUser applyuser) {
		//用户信息
		TUserEntity user = new TUserEntity();
		user.setComId(CommonConstants.BIG_COMPANY_TOURIST_ID);
		user.setName(applyuser.getUsername());
		user.setMobile(applyuser.getMobile());
		user.setPassword(applyuser.getPassword());
		user.setDepartmentId(CommonConstants.BIG_DEPARTMENT_TOURIST_ID);
		user.setIsDisable(IsYesOrNoEnum.YES.intKey());
		user.setJobId(CommonConstants.BIG_JOB_TOURIST_ID);
		user.setOpId(applyuser.getOpid());
		user.setUserType(UserLoginEnum.BIG_TOURIST_IDENTITY.intKey());
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		//添加用户
		TUserEntity insertuser = dbDao.insert(user);
		//用户就职信息
		TUserJobEntity userjob = new TUserJobEntity();
		userjob.setEmpId(insertuser.getId());
		userjob.setComJobId(CommonConstants.BIG_COMPANY_JOB_ID);
		userjob.setStatus(IsYesOrNoEnum.YES.intKey());
		dbDao.insert(userjob);
		return insertuser;
	}

	/**
	 * 
	 * 获取已报名活动的人员信息
	 *
	 * @param eventId
	 * @return 
	 */
	public Object getStaffInfoByEventId(Integer eventId) {
		Record record = new Record();
		if (!Util.isEmpty(eventId)) {
			//活动详情
			String sqlStr = sqlManager.get("appevents_staffs_infos_by_eventId");
			Sql sql = Sqls.create(sqlStr);
			sql.setParam("eventId", eventId);
			record = dbDao.fetch(sql);
		}

		return record;
	}

	/**
	 * 
	 * 签证办理流程 
	 *
	 * @param visaCountry 签证国
	 * @return 
	 */
	public Object getVisaProcessByCountry(Integer visaCountry) {

		Map<String, String> map = Maps.newHashMap();
		if (Util.eq(VisaCountryEnum.USA.intKey(), visaCountry)) {
			//美国签证流程
			map = EnumUtil.enum2(VisaProcess_US_Enum.class);
		} else if (Util.eq(VisaCountryEnum.JAPAN.intKey(), visaCountry)) {
			//日本签证流程 TODO

		}
		return map;
	}

	/**
	 * 
	 * 根据人员id，获取相关活动的进度列表
	 * <p>
	 * TODO *************************重要***********************
	 * 		1.进度页列表，签证办理状态
	 * 		2.活动相关报名人员查询
	 * 		3.报名活动 和 订单关联，需要获取订单相关信息（比如 订单号、订单状态、订单申请人等）
	 *
	 * @param staffId 人员Id
	 * @return 
	 */
	public Object getProcessListByStaffId(Integer staffId) {
		List<Record> records = new ArrayList<Record>();
		if (!Util.isEmpty(staffId)) {
			String sqlStr = sqlManager.get("appevents_process_list_by_staffId");
			Sql sql = Sqls.create(sqlStr);
			sql.setParam("staffId", staffId);
			records = dbDao.query(sql, null, null);
			for (Record record : records) {
				//签证国家
				int visacountry = record.getInt("visaCountry");
				for (VisaCountryEnum country : VisaCountryEnum.values()) {
					if (visacountry == country.intKey()) {
						record.set("visaCountry", country.value());
						break;
					}
				}
			}

		}
		return records;
	}

	/**
	 * 
	 * 查看报名活动的进度详情
	 * <p>
	 * TODO	************************待完善******************************
	 * 		1.根据订单id，查询当前订单状态----签证进度状态
	 * 		2.查询签证进度流程
	 * 
	 * @param visaCountry 签证国家
	 * @param orderId 订单id
	 * @return 
	 */
	public Object getMyProcessDetails(Integer visaCountry, Integer orderId) {
		Map<String, String> map = Maps.newHashMap();
		if (Util.eq(VisaCountryEnum.USA.intKey(), visaCountry)) {
			//美国签证流程
			map = EnumUtil.enum2(VisaProcess_US_Enum.class);
		} else if (Util.eq(VisaCountryEnum.JAPAN.intKey(), visaCountry)) {
			//日本签证流程 TODO

		}

		//TODO 获取订单状态

		//TODO 进度页渲染操作

		return map;
	}

	/**
	 * 
	 * 获取 我的申请人列表
	 *
	 * @param userId 用户id
	 * @return 
	 */
	public Object getAppStaffLists(Integer userId) {

		String sqlStr = sqlManager.get("appevents_staff_list_by_userId");
		Sql sql = Sqls.create(sqlStr);
		sql.setParam("userId", userId);

		//获取申请人列表页
		List<Record> records = dbDao.query(sql, null, null);

		return records;
	}

	/**
	 * 
	 * 获取申请人基本信息
	 * <p>
	 * TODO
	 *
	 * @param staffId 人员id
	 * @return 
	 */
	public Object getStaffBaseInfos(Integer staffId) {
		String sqlStr = sqlManager.get("appevents_staff_baseInfo_by_staffId");
		Sql sql = Sqls.create(sqlStr);
		sql.setParam("staffId", staffId);
		Record record = dbDao.fetch(sql);
		return record;
	}

	/**
	 * 申请人地址管理
	 * <p>
	 * TODO
	 */
	@At
	@POST
	public Object getStaffAddressInfos(Integer staffId) {
		List<TAppStaffAddressEntity> list = dbDao.query(TAppStaffAddressEntity.class,
				Cnd.where("staffId", "=", staffId), null);
		return list;
	}

	public Object checkUserLogin(String openid) {
		Map<String, Object> result = Maps.newHashMap();
		//根据staffId查询大客户人员基本信息表
		TAppStaffBasicinfoEntity basicInfo = dbDao.fetch(TAppStaffBasicinfoEntity.class,
				Cnd.where("wechattoken", "=", openid));

		if (!Util.isEmpty(basicInfo)) {
			System.out.println("zhuceOK");
			//用户已注册
			result.put("flag", "1");
			//openid
			result.put("openid", openid);
			//姓
			result.put("firstname", basicInfo.getFirstname());
			//名
			result.put("lastname", basicInfo.getLastname());
			//电话
			result.put("telephone", basicInfo.getTelephone());
			//邮箱
			result.put("email", basicInfo.getEmail());
		}
		return result;

	}

	public Object addOrder(SignUpEventForm form, HttpSession session) {

		//校验姓名是否合格
		String firstname = form.getFirstname();
		String lastname = form.getLastname();

		if (!(isChineseStr(firstname) && isChineseStr(lastname))) {
			return null;

		} else {
			//当前登录用户Id
			TUserEntity loginUser = LoginUtil.getLoginUser(session);
			//Integer loginUserId = loginUser.getId();

			//添加人员
			TAppStaffBasicinfoAddForm staffForm = new TAppStaffBasicinfoAddForm();
			//staffForm.setUserid(loginUserId);
			staffForm.setFirstname(form.getFirstname());
			staffForm.setLastname(form.getLastname());
			//旧用户无openid时 根据手机验证是否已经登陆
			staffForm.setTelephone(form.getTelephone());
			staffForm.setEmail(form.getEmail());
			if (!Util.isEmpty(form.getWeChatToken())) {
				staffForm.setWechattoken(form.getWeChatToken());
			}
			//默认签证状态为办理中
			staffForm.setVisastatus(VisaStatusEnum.HANDLING_VISA.intKey());
			Map<String, String> map = (Map<String, String>) bigCustomerViewService.addOrderStaff(staffForm, session);
			//只进行更新操作

			String staffIdStr = map.get("staffId");
			Integer staffId = Integer.valueOf(staffIdStr);
			Integer eventId = form.getEventId();

			//人员报名活动
			TAppStaffEventsEntity staffEventEntity = new TAppStaffEventsEntity();
			staffEventEntity.setEventsId(eventId);
			staffEventEntity.setStaffId(staffId);

			TAppStaffEventsEntity insertEntity = dbDao.insert(staffEventEntity);

			//添加订单
			orderUSViewService.addOrderByStuffId(staffId, loginUser.getId());

			//用户登录，添加游客信息
			TAppStaffBasicinfoEntity staffInfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, Long.valueOf(staffId));
			Integer loginUserId = (Integer) addLoginUser(staffInfo);

			dbDao.update(TAppStaffBasicinfoEntity.class, Chain.make("userid", loginUserId),
					Cnd.where("id", "=", staffId));

			return JsonResult.success("添加成功");
		}

	}

	public boolean isChineseStr(String str) {
		String reg = "[\\u4e00-\\u9fa5]+";
		boolean isChinese = str.matches(reg);
		return isChinese;
	}
}
