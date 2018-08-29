/**
 * SimpleVisaService.java
 * com.juyo.visa.admin.simple.service
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.simple.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import com.juyo.visa.admin.changePrincipal.service.ChangePrincipalViewService;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.order.form.VisaEditDataForm;
import com.juyo.visa.admin.order.service.OrderJpViewService;
import com.juyo.visa.admin.simple.entity.StatisticsEntity;
import com.juyo.visa.admin.simple.entity.WealthEntity;
import com.juyo.visa.admin.simple.form.AddOrderForm;
import com.juyo.visa.admin.simple.form.BasicinfoForm;
import com.juyo.visa.admin.simple.form.GenerrateTravelForm;
import com.juyo.visa.admin.simple.form.ListDataForm;
import com.juyo.visa.admin.user.form.ApplicantUser;
import com.juyo.visa.admin.user.service.UserViewService;
import com.juyo.visa.admin.visajp.form.FlightSelectParam;
import com.juyo.visa.admin.visajp.service.TripAirlineService;
import com.juyo.visa.admin.visajp.service.VisaJapanService;
import com.juyo.visa.admin.weixinToken.service.WeXinTokenViewService;
import com.juyo.visa.common.base.QrCodeService;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.ApplicantInfoTypeEnum;
import com.juyo.visa.common.enums.ApplicantJpWealthEnum;
import com.juyo.visa.common.enums.BoyOrGirlEnum;
import com.juyo.visa.common.enums.CollarAreaEnum;
import com.juyo.visa.common.enums.CompanyTypeEnum;
import com.juyo.visa.common.enums.CustomerTypeEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderProcessTypeEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.JobStatusEnum;
import com.juyo.visa.common.enums.JobStatusFreeEnum;
import com.juyo.visa.common.enums.JobStatusPreschoolEnum;
import com.juyo.visa.common.enums.JobStatusRetirementEnum;
import com.juyo.visa.common.enums.JobStatusStudentEnum;
import com.juyo.visa.common.enums.JobStatusWorkingEnum;
import com.juyo.visa.common.enums.JpOrderSimpleEnum;
import com.juyo.visa.common.enums.MainApplicantRelationEnum;
import com.juyo.visa.common.enums.MainApplicantRemarkEnum;
import com.juyo.visa.common.enums.MainOrViceEnum;
import com.juyo.visa.common.enums.MainSalePayTypeEnum;
import com.juyo.visa.common.enums.MainSaleTripTypeEnum;
import com.juyo.visa.common.enums.MainSaleUrgentEnum;
import com.juyo.visa.common.enums.MainSaleUrgentTimeEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.common.enums.MarryStatusEnum;
import com.juyo.visa.common.enums.PassportTypeEnum;
import com.juyo.visa.common.enums.SimpleVisaTypeEnum;
import com.juyo.visa.common.enums.TrialApplicantStatusEnum;
import com.juyo.visa.common.newairline.ResultflyEntity;
import com.juyo.visa.common.ocr.HttpUtils;
import com.juyo.visa.common.util.HttpUtil;
import com.juyo.visa.common.util.SpringContextUtil;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantFrontPaperworkJpEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TApplicantPassportEntity;
import com.juyo.visa.entities.TApplicantUnqualifiedEntity;
import com.juyo.visa.entities.TApplicantVisaJpEntity;
import com.juyo.visa.entities.TApplicantVisaOtherInfoEntity;
import com.juyo.visa.entities.TApplicantVisaPaperworkJpEntity;
import com.juyo.visa.entities.TApplicantWealthJpEntity;
import com.juyo.visa.entities.TApplicantWorkJpEntity;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TCompanyOfCustomerEntity;
import com.juyo.visa.entities.TCustomerEntity;
import com.juyo.visa.entities.TCustomerVisainfoEntity;
import com.juyo.visa.entities.TFlightEntity;
import com.juyo.visa.entities.THotelEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderLogsEntity;
import com.juyo.visa.entities.TOrderTravelplanJpEntity;
import com.juyo.visa.entities.TOrderTripJpEntity;
import com.juyo.visa.entities.TScenicEntity;
import com.juyo.visa.entities.TUncommoncharacterEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TApplicantForm;
import com.juyo.visa.forms.TApplicantPassportForm;
import com.juyo.visa.websocket.VisaInfoWSHandler;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年1月22日 	 
 */
@IocBean
public class SimpleVisaService extends BaseService<TOrderJpEntity> {

	private static final Log log = Logs.get();
	//基本信息连接websocket的地址
	private static final String SIMPLE_WEBSOCKET_ADDR = "simpleinfowebsocket";

	@Inject
	private QrCodeService qrCodeService;
	@Inject
	private TripAirlineService tripAirlineService;
	@Inject
	private UserViewService userViewService;
	@Inject
	private OrderJpViewService orderJpViewService;
	@Inject
	private VisaJapanService visaJapanService;
	@Inject
	private ChangePrincipalViewService changePrincipalViewService;
	@Inject
	private WeXinTokenViewService weXinTokenViewService;
	@Inject
	private UploadService qiniuUploadService;//文件上传

	private VisaInfoWSHandler visaInfoWSHandler = (VisaInfoWSHandler) SpringContextUtil.getBean("myVisaInfoHander",
			VisaInfoWSHandler.class);

	private static final String VISAINFO_WEBSPCKET_ADDR = "visainfowebsocket";
	private static String WX_B_CODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN"; //不限次数 scene长度为32个字符

	public Object toList(HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		JSONArray ja = new JSONArray();

		//送签社下拉
		if (loginCompany.getComType().equals(CompanyTypeEnum.SONGQIAN.intKey())
				|| loginCompany.getComType().equals(CompanyTypeEnum.SONGQIANSIMPLE.intKey())) {
			//如果公司自己有指定番号，说明有送签资质，也需要出现在下拉中
			if (!Util.isEmpty(loginCompany.getCdesignNum())) {
				ja.add(loginCompany);
			}
			List<TCompanyOfCustomerEntity> list = dbDao.query(TCompanyOfCustomerEntity.class,
					Cnd.where("comid", "=", loginCompany.getId()), null);
			for (TCompanyOfCustomerEntity tCompanyOfCustomerEntity : list) {
				JSONObject jo = new JSONObject();
				Integer sendcomid = tCompanyOfCustomerEntity.getSendcomid();

				TCompanyEntity sendCompany = dbDao.fetch(TCompanyEntity.class,
						Cnd.where("id", "=", sendcomid).and("cdesignNum", "!=", ""));

				ja.add(sendCompany);
			}
		}
		result.put("songqianlist", ja);

		//员工下拉
		Integer comid = loginCompany.getId();
		Integer adminId = loginCompany.getAdminId();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userType = loginUser.getUserType();//当前登录用户类型

		//查询拥有某个权限模块的工作人员
		String sqlstr = sqlManager.get("logs_user_select_list");
		Sql sql = Sqls.create(sqlstr);
		Cnd cnd = Cnd.NEW();
		cnd.and("tcf.comid", "=", comid);
		cnd.and("tu.id", "!=", adminId);
		for (JPOrderProcessTypeEnum typeEnum : JPOrderProcessTypeEnum.values()) {
			if (1 == typeEnum.intKey()) {
				cnd.and(" tf.funName", "=", typeEnum.value());
			}
		}
		List<Record> employees = dbDao.query(sql, cnd, null);
		Cnd usercnd = Cnd.NEW();
		usercnd.and("comId", "=", loginCompany.getId());
		if (!loginCompany.getAdminId().equals(loginUser.getId())) {
			usercnd.and("id", "!=", loginCompany.getAdminId());
		}
		if (loginCompany.getComType().equals(CompanyTypeEnum.SONGQIANSIMPLE.intKey())) {

			List<TUserEntity> companyuser = dbDao.query(TUserEntity.class, usercnd, null);
			List<Record> companyusers = Lists.newArrayList();
			for (TUserEntity tUserEntity : companyuser) {
				Record record = new Record();
				record.put("userid", tUserEntity.getId());
				record.put("username", tUserEntity.getName());
				companyusers.add(record);
			}
			employees = companyusers;
		}

		result.put("employees", employees);
		result.put("orderstatus", EnumUtil.enum2(JpOrderSimpleEnum.class));
		String localAddr = request.getServerName();
		int localPort = request.getServerPort();
		result.put("localAddr", localAddr);
		result.put("localPort", localPort);
		result.put("websocketaddr", VISAINFO_WEBSPCKET_ADDR);
		return result;
	}

	/**
	 *列表页数据展示
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object ListData(ListDataForm form, HttpServletRequest request) {

		HttpSession session = request.getSession();
		//获取当前公司
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//获取当前用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		loginUser.getId();
		form.setUserid(loginUser.getId());
		form.setCompanyid(loginCompany.getId());
		form.setAdminId(loginCompany.getAdminId());
		Map<String, Object> result = Maps.newHashMap();
		Sql sql = form.sql(sqlManager);

		Integer pageNumber = form.getPageNumber();
		System.out.println(pageNumber);
		Integer pageSize = form.getPageSize();

		Pager pager = new OffsetPager((pageNumber - 1) * pageSize, pageSize);
		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));

		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		//主sql数据
		List<Record> totallist = (List<Record>) sql.getResult();
		int orderscount = totallist.size();
		int peopletotal = 0;
		int disableorder = 0;
		int disablepeople = 0;
		int zhaobaoorder = 0;
		int zhaobaopeople = 0;
		for (Record record : totallist) {
			//作废单子、人数
			if (Util.eq(1, record.get("isdisabled"))) {
				disableorder++;
				if (!Util.eq(0, record.get("peoplenumber"))) {
					disablepeople += record.getInt("peoplenumber");
				}
			}

			//收费单子，人数
			if (Util.eq(1, record.get("zhaobaoupdate"))) {
				zhaobaoorder++;
				if (!Util.eq(0, record.get("peoplenumber"))) {
					zhaobaopeople += record.getInt("peoplenumber");
				}
			}

			//单子人数
			if (!Util.eq(0, record.get("peoplenumber"))) {
				peopletotal += record.getInt("peoplenumber");
			}
		}

		sql.setPager(pager);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		//主sql数据
		List<Record> list = (List<Record>) sql.getResult();

		for (Record record : list) {

			if (!Util.isEmpty(record.get("errorMsg"))) {
				String errorMsg = (String) record.get("errorMsg");
				if (errorMsg.contains("氏名")) {
					record.set("errormsg", "第" + (Integer.valueOf(errorMsg.substring(0, 1)) - 1) + "个人姓名填写错误");
				}
				if (errorMsg.contains("居住地域")) {
					record.set("errormsg", "第" + (Integer.valueOf(errorMsg.substring(0, 1)) - 1) + "个人居住地填写错误");
				}
			}

			if (!Util.isEmpty(record.get("visatype"))) {
				Integer visatype = (Integer) record.get("visatype");
				for (SimpleVisaTypeEnum visatypenum : SimpleVisaTypeEnum.values()) {
					if (visatype == visatypenum.intKey()) {
						record.set("visatype", visatypenum.value());
					}
				}
			}

			Integer orderid = (Integer) record.get("id");
			String sqlStr = sqlManager.get("get_japan_visa_list_data_apply");
			Sql applysql = Sqls.create(sqlStr);
			List<Record> query = dbDao.query(applysql, Cnd.where("taoj.orderId", "=", orderid), null);
			for (Record apply : query) {

				if (!Util.isEmpty(apply.get("province"))) {
					String province = (String) apply.get("province");
					if (province.endsWith("省") || province.endsWith("市")) {
						apply.put("province", province.substring(0, province.length() - 1));
					}
					if (province.length() > 3 && province.endsWith("自治区")) {
						apply.put("province", province.substring(0, province.length() - 3));
					}
				}

				Integer dataType = (Integer) apply.get("dataType");
				for (JobStatusEnum dataTypeEnum : JobStatusEnum.values()) {
					if (!Util.isEmpty(dataType) && dataType.equals(dataTypeEnum.intKey())) {
						apply.put("dataType", dataTypeEnum.value());
					}
				}
				String data = "";
				String blue = "";
				if (!Util.isEmpty(apply.get("blue"))) {
					blue = (String) apply.get("blue");
				}
				String black = "";
				if (!Util.isEmpty(apply.get("black"))) {
					black = (String) apply.get("black");
				}
				if (Util.isEmpty(blue)) {
					data = black;
				} else {
					data = blue;
					if (!Util.isEmpty(black)) {
						data += "、";
						data += black;
					}
				}
				apply.put("data", data);
			}
			record.put("everybodyInfo", query);
			//签证状态
			Integer visastatus = record.getInt("japanState");
			for (JPOrderStatusEnum visaenum : JPOrderStatusEnum.values()) {
				if (visaenum.intKey() == visastatus) {
					record.put("visastatus", visaenum.value());
				}
			}
		}

		StatisticsEntity entity = new StatisticsEntity();
		entity.setDisableorder(disableorder);
		entity.setDisablepeople(disablepeople);
		entity.setOrderscount(orderscount);
		entity.setPeopletotal(peopletotal);
		entity.setZhaobaoorder(zhaobaoorder);
		entity.setZhaobaopeople(zhaobaopeople);
		result.put("entity", entity);
		result.put("pagetotal", pager.getPageCount());
		result.put("visaJapanData", list);
		return result;
	}

	/**
	 * 跳转到添加订单页面
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addOrder(HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		HttpSession session = request.getSession();
		result.put("collarAreaEnum", EnumUtil.enum2(CollarAreaEnum.class));
		result.put("customerTypeEnum", EnumUtil.enum2(CustomerTypeEnum.class));
		result.put("mainSaleUrgentEnum", EnumUtil.enum2(MainSaleUrgentEnum.class));
		result.put("mainSaleUrgentTimeEnum", EnumUtil.enum2(MainSaleUrgentTimeEnum.class));
		result.put("mainSaleTripTypeEnum", EnumUtil.enum2(MainSaleTripTypeEnum.class));
		result.put("mainSalePayTypeEnum", EnumUtil.enum2(MainSalePayTypeEnum.class));
		result.put("mainSaleVisaTypeEnum", EnumUtil.enum2(SimpleVisaTypeEnum.class));

		return result;
	}

	/**
	 * 获取景点
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param scenicname
	 * @param cityid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getScenicSelect(String scenicname, int cityid, int planid, int visatype) {
		List<TScenicEntity> scenics = Lists.newArrayList();
		TOrderTravelplanJpEntity plan = dbDao.fetch(TOrderTravelplanJpEntity.class, planid);
		Integer orderid = plan.getOrderId();
		List<TOrderTravelplanJpEntity> planlist = dbDao.query(TOrderTravelplanJpEntity.class,
				Cnd.where("orderId", "=", orderid).orderBy("outDate", "ASC"), null);
		int days = 0;
		if (planlist.size() % 2 == 0) {//为2的倍数，则最后是三天，否则为两天
			days = planlist.size() - 3;
		} else {
			days = planlist.size() - 2;
		}

		String contains = isContains(visatype);

		if (planlist.get(1).getCityId() == planlist.get(2).getCityId()) {//第二天和第三天是同一个城市，说明出行抵达城市和返回出发城市一致，这时只刷新景点
			//获取城市所有的景区
			Cnd cnd = Cnd.NEW();
			/*cnd.and("name", "like", "%" + Strings.trim(scenicname) + "%");
			if (!Util.isEmpty(cityid)) {
				cnd.and("cityId", "=", cityid);
			}*/
			cnd.and("cityId", "=", cityid);
			scenics = dbDao.query(TScenicEntity.class, cnd, null);
		} else {

			TOrderTravelplanJpEntity formerPlan = dbDao.fetch(TOrderTravelplanJpEntity.class,
					Cnd.where("orderId", "=", orderid).and("day", "=", Integer.valueOf(plan.getDay()) - 1));
			if (plan.getCityId() == formerPlan.getCityId()) {//如果城市一样，说明没去别的地方，刷新景点
				//获取城市所有的景区
				Cnd cnd = Cnd.NEW();
				cnd.and("cityId", "=", cityid);
				scenics = dbDao.query(TScenicEntity.class, cnd, null);
			} else {
				List<TOrderTravelplanJpEntity> nowplanList = dbDao.query(TOrderTravelplanJpEntity.class,
						Cnd.where("orderId", "=", orderid).and("cityId", "=", plan.getCityId()), null);
				List<TOrderTravelplanJpEntity> lastplanList = dbDao.query(
						TOrderTravelplanJpEntity.class,
						Cnd.where("orderId", "=", orderid).and("cityId", "=",
								planlist.get(planlist.size() - 1).getCityId()), null);

				if (nowplanList.size() == 1) {
					scenics = visaJapanService.countryAirline(formerPlan.getCityId(), plan.getCityId(), 1);

				} else if (lastplanList.size() == 2 && Integer.valueOf(plan.getDay()) == planlist.size() - 1) {
					scenics = visaJapanService.countryAirline(formerPlan.getCityId(), plan.getCityId(), 1);

				} else {
					scenics = visaJapanService.countryAirline(formerPlan.getCityId(), plan.getCityId(), 2);

				}

			}

		}

		return scenics;
	}

	public String isContains(int visatype) {
		String result = "";
		ArrayList<Integer> visatypeList = new ArrayList<>();
		visatypeList.add(3);
		visatypeList.add(4);
		visatypeList.add(5);
		visatypeList.add(8);
		visatypeList.add(9);
		visatypeList.add(10);
		visatypeList.add(11);
		visatypeList.add(12);
		visatypeList.add(13);
		if (visatypeList.contains(visatype)) {
			result = "true";
		} else {
			result = "false";
		}
		return result;
	}

	/**
	 * 获取酒店
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param scenicname
	 * @param cityid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getHotelSelect(String hotelname, int cityid) {
		Cnd cnd = Cnd.NEW();
		/*cnd.and("name", "like", "%" + Strings.trim(hotelname) + "%");
		if (!Util.isEmpty(cityid)) {
			cnd.and("cityId", "=", cityid);
		}*/
		cnd.and("cityId", "=", cityid);
		List<THotelEntity> hotels = dbDao.query(THotelEntity.class, cnd, null);
		return hotels;
	}

	/**
	 * 生成行程安排
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param request
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	/*public Object generateTravelPlan(HttpServletRequest request, GenerrateTravelForm form) {
		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Map<String, Object> result = Maps.newHashMap();
		if (Util.isEmpty(form.getGoDepartureCity())) {
			result.put("message", "请选择出发城市");
			return result;
		}
		if (Util.isEmpty(form.getGoArrivedCity())) {
			result.put("message", "请选择抵达城市");
			return result;
		}
		if (Util.isEmpty(form.getGoDate())) {
			result.put("message", "请选择出发日期");
			return result;
		}
		if (Util.isEmpty(form.getReturnDate())) {
			result.put("message", "请选择返回日期");
			return result;
		}
		if (Util.isEmpty(form.getGoFlightNum())) {
			result.put("message", "请选择出发航班号");
			return result;
		}
		if (Util.isEmpty(form.getReturnFlightNum())) {
			result.put("message", "请选择返回航班号");
			return result;
		}
		if (Util.isEmpty(form.getVisatype())) {
			result.put("message", "请选择签证类型");
			return result;
		}
		int daysBetween = DateUtil.daysBetween(form.getGoDate(), form.getReturnDate());
		if (daysBetween < 4) {
			result.put("message", "停留天数必须大于4天");
			return result;
		}
		//返回时的出发城市
		Integer returnDepartureCity = form.getReturnDepartureCity();
		TCityEntity returngoCity = dbDao.fetch(TCityEntity.class, returnDepartureCity.longValue());

		//出发城市
		Integer goDepartureCity = form.getGoDepartureCity();
		TCityEntity goCity = dbDao.fetch(TCityEntity.class, goDepartureCity.longValue());
		String province = goCity.getProvince();
		if (province.endsWith("省") || province.endsWith("市")) {
			province = province.substring(0, province.length() - 1);
		}
		//出发航班
		String goFlightNum = form.getGoFlightNum();
		String firstday = " "
				+ province
				+ "から"
				+ goFlightNum.substring(goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")) + 1,
						goFlightNum.indexOf(" ", goFlightNum.indexOf(" ") + 1))
				+ "便にて"
				+ goFlightNum.substring(goFlightNum.indexOf("-", goFlightNum.lastIndexOf("-")) + 1,
						goFlightNum.indexOf(" ", goFlightNum.indexOf(" "))) + "へ" + "\n 到着後、ホテルへ";

		//返回航班
		String returnFlightNum = form.getReturnFlightNum();
		String lastday = " "
				+ returnFlightNum.substring(0, returnFlightNum.indexOf("-", returnFlightNum.indexOf("-")))
				+ "から"
				+ returnFlightNum.substring(returnFlightNum.indexOf(" ", returnFlightNum.indexOf(" ")) + 1,
						returnFlightNum.indexOf(" ", returnFlightNum.indexOf(" ") + 1)) + "便にて帰国";

		FlightSelectParam param = null;
		//根据签证类型来决定前两天的城市
		Integer visatype = form.getVisatype();

		//获取前两天城市
		TCityEntity city = dbDao.fetch(TCityEntity.class, form.getGoArrivedCity().longValue());
		//获取前两天城市所有的酒店
		List<THotelEntity> hotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", form.getGoArrivedCity()),
				null);
		//获取前两天城市所有的景区
		List<TScenicEntity> scenics = dbDao.query(TScenicEntity.class,
				Cnd.where("cityId", "=", form.getGoArrivedCity()), null);
		if (scenics.size() < 2) {
			result.put("message", "没有更多的景区");
			return result;
		}
		//获取后两天城市
		TCityEntity lastcity = dbDao.fetch(TCityEntity.class, form.getReturnDepartureCity().longValue());
		//获取后两天酒店
		List<THotelEntity> lasthotels = dbDao.query(THotelEntity.class,
				Cnd.where("cityId", "=", form.getReturnDepartureCity()), null);
		//获取后两天景区
		List<TScenicEntity> lastscenics = dbDao.query(TScenicEntity.class,
				Cnd.where("cityId", "=", form.getReturnDepartureCity()), null);
		if (lastscenics.size() < 2) {
			result.put("message", "没有更多的景区");
			return result;
		}
		Integer orderjpid = form.getOrderid();
		Integer orderid = null;
		if (Util.isEmpty(orderjpid)) {
			//如果订单不存在，创建订单
			Map<String, Integer> generrateorder = generrateorder(loginUser, loginCompany);
			orderid = generrateorder.get("orderid");
			orderjpid = generrateorder.get("orderjpid");
		} else {
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
			orderid = orderjp.getOrderId();
		}
		//需要生成的travelplan
		List<TOrderTravelplanJpEntity> travelplans = Lists.newArrayList();
		//生成行程安排历史信息
		//		List<TOrderTravelplanHisJpEntity> travelplansHis = Lists.newArrayList();

		//在一个城市只住一家酒店
		Random random = new Random();
		int hotelindex = random.nextInt(hotels.size());
		int lasthotelindex = random.nextInt(lasthotels.size());

		if (visatype == 6 || visatype == 1 || visatype == 14 || visatype == 2 || visatype == 7) {//除去东北六县
			//如果去程抵达城市和返回出发城市一样，则什么都不需要分
			if (form.getGoArrivedCity() == form.getReturnDepartureCity()) {
				for (int i = 0; i <= daysBetween; i++) {
					TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
					travelplan.setCityId(form.getGoArrivedCity());
					travelplan.setDay(String.valueOf(i + 1));
					travelplan.setOrderId(orderjpid);
					travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
					travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
					travelplan.setCityName(city.getCity());
					travelplan.setCreateTime(new Date());

					//酒店
					if (i != daysBetween) {
						THotelEntity hotel = hotels.get(hotelindex);
						travelplan.setHotel(hotel.getId());
					}
					if (i > 0 && i != daysBetween) {
						//景区
						if (scenics.size() == 0) {
							scenics = dbDao.query(TScenicEntity.class,
									Cnd.where("cityId", "=", form.getGoArrivedCity()), null);
						}
						int scenicindex = random.nextInt(scenics.size());
						TScenicEntity scenic = scenics.get(scenicindex);
						scenics.remove(scenic);
						travelplan.setScenic(scenic.getName());
					}
					if (i == 0) {//第一天
						travelplan.setScenic(firstday);
					}
					if (i == daysBetween) {//最后一天
						travelplan.setScenic(lastday);
					}
					travelplans.add(travelplan);
				}
			} else {
				//为什么要<=，因为最后一天也要玩
				//if (visatype == 6 || visatype == 1 || visatype == 14) {//普通三年多次，日本单次和普通五年多次一样
				//前两天
				for (int i = 0; i < 2; i++) {
					TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
					//			TOrderTravelplanHisJpEntity travelPlanHis = new TOrderTravelplanHisJpEntity();
					travelplan.setCityId(form.getGoArrivedCity());
					travelplan.setCityName(city.getCity());
					travelplan.setDay(String.valueOf(i + 1));
					travelplan.setOrderId(orderjpid);
					travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
					travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());

					travelplan.setCreateTime(new Date());

					if (i != daysBetween) {
						THotelEntity hotel = hotels.get(hotelindex);
						travelplan.setHotel(hotel.getId());
					}
					if (i == 0) {
						travelplan.setScenic(firstday);
					}
					if (i > 0 && i != daysBetween) {
						//景区
						int scenicindex = random.nextInt(scenics.size());
						TScenicEntity scenic = scenics.get(scenicindex);
						scenics.remove(scenic);
						travelplan.setScenic(scenic.getName());
					}
					//第三天，去返回时即第二行的出发城市，通过日本国内航线或新干线
					if (i == 2) {
						travelplan.setScenic("");
						THotelEntity hotel = lasthotels.get(lasthotelindex);
						travelplan.setHotel(hotel.getId());
					}
					travelplans.add(travelplan);
				}
				//除去开始的前两天和最后四天，如果天数为2的倍数，则中间多2的倍数个随机城市，有余数则最后变为4天
				int subday = daysBetween - 4;
				int[] randomArray = new int[20];
				if (subday % 2 == 0) {
					int totalstyle = subday / 2;
					//intArray为所有有景点的城市并且出去东北六县的Id组成的数组
					int[] intArray = generrateCityArray();
					intArray = getCitysArray(intArray, form.getGoArrivedCity(), form.getReturnDepartureCity());
					//randomArray为获取的不重复随机数
					if (intArray.length < totalstyle) {
						result.put("message", "没有更多的城市");
						return result;
					} else {
						randomArray = getRandomArray(intArray, totalstyle);
					}

					//去程时抵达城市为冲绳时数据处理
					int[] csArray = { 51, 22, 85, 58, 66 };
					int[] citysArray = getCitysArray(csArray, form.getGoArrivedCity(), form.getReturnDepartureCity());
					int[] csrandomArray = getRandomArray(citysArray, 1);
					int cscityid = csrandomArray[0];
					TCityEntity cscity = dbDao.fetch(TCityEntity.class, cscityid);
					List<TScenicEntity> csScenics = dbDao.query(TScenicEntity.class,
							Cnd.where("cityId", "=", cscityid), null);
					if (csScenics.size() < 1) {
						result.put("message", "没有更多的景点");
						return result;
					}
					List<THotelEntity> csHotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", cscityid),
							null);
					if (csHotels.size() < 1) {
						result.put("message", "没有更多的酒店");
						return result;
					}
					int cshotel = random.nextInt(csHotels.size());

					for (int i = 2; i < daysBetween - 2; i++) {

						int firstcityid = randomArray[((i - 2) / 2)];
						int lastcityid = 0;
						if (i >= 4) {
							lastcityid = randomArray[((i - 4) / 2)];
						}
						TCityEntity fcity = dbDao.fetch(TCityEntity.class, firstcityid);
						TCityEntity lcity = null;
						if (lastcityid != 0) {
							lcity = dbDao.fetch(TCityEntity.class, lastcityid);
						}

						List<THotelEntity> fhotels = dbDao.query(THotelEntity.class,
								Cnd.where("cityId", "=", firstcityid), null);
						if (fhotels.size() == 0) {
							result.put("message", "没有更多的酒店");
							return result;
						}
						List<TScenicEntity> fscenics = dbDao.query(TScenicEntity.class,
								Cnd.where("cityId", "=", firstcityid), null);
						int fhotelindex = random.nextInt(fhotels.size());

						//tripAirlineService.getTripAirlineSelect(param);

						//第三天
						if (i < 4) {
							TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
							if (form.getGoArrivedCity() == 77) {//如果去程的抵达城市为冲绳，则第三天所去的城市在固定的五个城市中随机:大阪，东京，名古屋，广岛，长崎
								travelplan.setCityId(cscityid);
								travelplan.setCityName(cscity.getCity());

								//酒店和航班取返程即第二行的出发城市
								//酒店
								if (i == 2) {
									THotelEntity hotel = csHotels.get(cshotel);
									travelplan.setHotel(hotel.getId());
									//酒店历史信息
									//				travelPlanHis.setHotel(hotel.getName());
								}
								if (i == 2) {
									String countryAirline = countryAirline(form.getGoArrivedCity(), cscityid, 1);
									travelplan.setScenic(countryAirline);
								}
								if (i == 3) {
									//景区
									int scenicindex = random.nextInt(csScenics.size());
									TScenicEntity scenic = csScenics.get(scenicindex);
									//csScenics.remove(scenic);
									travelplan.setScenic(scenic.getName());
								}
							} else {
								travelplan.setCityId(firstcityid);
								travelplan.setCityName(fcity.getCity());
								//酒店和航班取返程即第二行的出发城市
								//酒店
								if (i == 2) {
									THotelEntity hotel = fhotels.get(fhotelindex);
									travelplan.setHotel(hotel.getId());
									//酒店历史信息
									//				travelPlanHis.setHotel(hotel.getName());
								}
								if (i == 2) {
									String countryAirline = countryAirline(form.getGoArrivedCity(), firstcityid, 2);
									travelplan.setScenic(countryAirline);
								}
								if (i == 3) {
									//景区
									int scenicindex = random.nextInt(csScenics.size());
									TScenicEntity scenic = csScenics.get(scenicindex);
									csScenics.remove(scenic);
									travelplan.setScenic(scenic.getName());
								}

							}
							travelplan.setDay(String.valueOf(i + 1));
							travelplan.setOrderId(orderjpid);
							travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
							travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
							travelplan.setCreateTime(new Date());
							travelplans.add(travelplan);
						} else {
							TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
							travelplan.setCityId(firstcityid);
							travelplan.setDay(String.valueOf(i + 1));
							travelplan.setOrderId(orderjpid);
							travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
							travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
							travelplan.setCityName(fcity.getCity());
							travelplan.setCreateTime(new Date());
							//酒店和航班取返程即第二行的出发城市
							//酒店
							if (i % 2 == 0) {
								THotelEntity hotel = fhotels.get(fhotelindex);
								travelplan.setHotel(hotel.getId());
								//酒店历史信息
								//				travelPlanHis.setHotel(hotel.getName());
							}
							if (i % 2 == 0) {
								if (i == 4) {
									if (form.getGoArrivedCity() == 77) {
										String countryAirline = countryAirline(cscityid, firstcityid, 2);
										travelplan.setScenic(countryAirline);
									} else {
										String countryAirline = countryAirline(lcity.getId(), firstcityid, 2);
										travelplan.setScenic(countryAirline);
									}
								}
							} else {
								//景区
								if (fscenics.size() == 0) {//如果随机完所有的，则重新查一次
									fscenics = dbDao.query(TScenicEntity.class, Cnd.where("cityId", "=", firstcityid),
											null);
								}
								int scenicindex = random.nextInt(fscenics.size());
								TScenicEntity scenic = fscenics.get(scenicindex);
								fscenics.remove(scenic);
								travelplan.setScenic(scenic.getName());
							}
							travelplans.add(travelplan);
						}
					}

					//最后四天
					for (int i = daysBetween - 2; i <= daysBetween; i++) {
						TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
						travelplan.setCityId(form.getReturnDepartureCity());
						travelplan.setDay(String.valueOf(i + 1));
						travelplan.setOrderId(orderjpid);
						travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
						travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
						travelplan.setCityName(returngoCity.getCity());
						travelplan.setCreateTime(new Date());
						//酒店和航班取返程即第二行的出发城市
						//酒店
						if (i != daysBetween) {
							THotelEntity hotel = lasthotels.get(lasthotelindex);
							travelplan.setHotel(hotel.getId());
							//酒店历史信息
							//				travelPlanHis.setHotel(hotel.getName());
						}
						if (i == daysBetween) {
							travelplan.setScenic(lastday);
						} else if (i == daysBetween - 2) {
							if (daysBetween == 4) {//如果是4则说明中间没有随机城市
								if (form.getGoArrivedCity() == 77) {
									String countryAirline = countryAirline(form.getGoArrivedCity(),
											form.getReturnDepartureCity(), 1);
									travelplan.setScenic(countryAirline);
								} else {
									String countryAirline = countryAirline(form.getGoArrivedCity(),
											form.getReturnDepartureCity(), 2);
									travelplan.setScenic(countryAirline);
								}
							} else if (daysBetween == 6) {
								String countryAirline = countryAirline(cscityid, form.getReturnDepartureCity(), 2);
								travelplan.setScenic(countryAirline);
							} else {
								String countryAirline = countryAirline(randomArray[totalstyle - 1],
										form.getReturnDepartureCity(), 2);
								travelplan.setScenic(countryAirline);
							}
						} else {
							//景区
							if (scenics.size() == 0) {
								scenics = dbDao.query(TScenicEntity.class,
										Cnd.where("cityId", "=", form.getGoArrivedCity()), null);
							}
							int scenicindex = random.nextInt(scenics.size());
							TScenicEntity scenic = scenics.get(scenicindex);
							scenics.remove(scenic);
							travelplan.setScenic(scenic.getName());
						}
						travelplans.add(travelplan);
					}
				} else {
					int totalstyle = subday / 2;
					//intArray为所有有景点的城市并且出去东北六县的Id组成的数组
					int[] intArray = generrateCityArray();
					intArray = getCitysArray(intArray, form.getGoArrivedCity(), form.getReturnDepartureCity());
					//randomArray为获取的不重复随机数
					if (intArray.length < totalstyle) {
						result.put("message", "没有更多的城市");
						return result;
					} else {
						randomArray = getRandomArray(intArray, totalstyle);
					}
					//去程时抵达城市为冲绳时数据处理
					int[] csArray = { 51, 22, 85, 58, 66 };
					int[] citysArray = getCitysArray(csArray, form.getGoArrivedCity(), form.getReturnDepartureCity());
					int[] csrandomArray = getRandomArray(citysArray, 1);
					int cscityid = csrandomArray[0];
					TCityEntity cscity = dbDao.fetch(TCityEntity.class, cscityid);
					List<TScenicEntity> csScenics = dbDao.query(TScenicEntity.class,
							Cnd.where("cityId", "=", cscityid), null);
					if (csScenics.size() < 1) {
						result.put("message", "没有更多的景点");
						return result;
					}
					List<THotelEntity> csHotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", cscityid),
							null);
					if (csHotels.size() < 1) {
						result.put("message", "没有更多的酒店");
						return result;
					}
					int cshotel = random.nextInt(csHotels.size());

					for (int i = 2; i < daysBetween - 3; i++) {

						int firstcityid = randomArray[((i - 2) / 2)];
						TCityEntity lcity = null;
						if (i > 3) {
							int lastcityid = randomArray[((i - 4) / 2)];
							lcity = dbDao.fetch(TCityEntity.class, lastcityid);
						}
						TCityEntity fcity = dbDao.fetch(TCityEntity.class, firstcityid);

						List<THotelEntity> fhotels = dbDao.query(THotelEntity.class,
								Cnd.where("cityId", "=", firstcityid), null);
						if (fhotels.size() == 0) {
							result.put("message", "没有更多的酒店");
							return result;
						}
						List<TScenicEntity> fscenics = dbDao.query(TScenicEntity.class,
								Cnd.where("cityId", "=", firstcityid), null);
						int fhotelindex = random.nextInt(fhotels.size());

						//tripAirlineService.getTripAirlineSelect(param);

						//第三天
						if (i < 4) {
							TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
							if (form.getGoArrivedCity() == 77) {//如果去程的抵达城市为冲绳，则第三天所去的城市在固定的五个城市中随机:大阪，东京，名古屋，广岛，长崎

								travelplan.setCityId(cscityid);
								travelplan.setCityName(cscity.getCity());

								//酒店和航班取返程即第二行的出发城市
								//酒店
								if (i == 2) {
									THotelEntity hotel = csHotels.get(cshotel);
									travelplan.setHotel(hotel.getId());
									//酒店历史信息
									//				travelPlanHis.setHotel(hotel.getName());
								}
								if (i == 2) {
									String countryAirline = countryAirline(form.getGoArrivedCity(), cscityid, 1);
									travelplan.setScenic(countryAirline);
								}
								if (i == 3) {
									//景区
									int scenicindex = random.nextInt(csScenics.size());
									TScenicEntity scenic = csScenics.get(scenicindex);
									//csScenics.remove(scenic);
									travelplan.setScenic(scenic.getName());
								}
							} else {
								travelplan.setCityId(firstcityid);
								travelplan.setCityName(fcity.getCity());
								//酒店和航班取返程即第二行的出发城市
								//酒店
								if (i == 2) {
									THotelEntity hotel = fhotels.get(fhotelindex);
									travelplan.setHotel(hotel.getId());
									//酒店历史信息
									//				travelPlanHis.setHotel(hotel.getName());
								}
								if (i == 2) {
									String countryAirline = countryAirline(form.getGoArrivedCity(), firstcityid, 2);
									travelplan.setScenic(countryAirline);
								}
								if (i == 3) {
									//景区
									int scenicindex = random.nextInt(fscenics.size());
									TScenicEntity scenic = fscenics.get(scenicindex);
									fscenics.remove(scenic);
									travelplan.setScenic(scenic.getName());
								}
							}

							travelplan.setDay(String.valueOf(i + 1));
							travelplan.setOrderId(orderjpid);
							travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
							travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
							travelplan.setCreateTime(new Date());

							travelplans.add(travelplan);
						} else {
							TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
							travelplan.setCityId(fcity.getId());
							travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
							travelplan.setDay(String.valueOf(i + 1));
							travelplan.setOrderId(orderjpid);
							travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
							travelplan.setCityName(fcity.getCity());
							travelplan.setCreateTime(new Date());
							//酒店和航班取返程即第二行的出发城市
							//酒店
							if (i % 2 == 0) {
								THotelEntity hotel = fhotels.get(fhotelindex);
								travelplan.setHotel(hotel.getId());
								//酒店历史信息
								//				travelPlanHis.setHotel(hotel.getName());
							}
							if (i % 2 == 0) {
								if (i == 4) {
									if (form.getGoArrivedCity() == 77) {
										String countryAirline = countryAirline(cscityid, firstcityid, 2);
										travelplan.setScenic(countryAirline);
									} else {
										String countryAirline = countryAirline(lcity.getId(), firstcityid, 2);
										travelplan.setScenic(countryAirline);
									}
								}
							} else {
								//景区
								if (fscenics.size() == 0) {
									fscenics = dbDao.query(TScenicEntity.class, Cnd.where("cityId", "=", firstcityid),
											null);
								}
								int scenicindex = random.nextInt(fscenics.size());
								TScenicEntity scenic = fscenics.get(scenicindex);
								fscenics.remove(scenic);
								travelplan.setScenic(scenic.getName());
							}
							travelplans.add(travelplan);
						}
					}

					//最后三天
					for (int i = daysBetween - 3; i <= daysBetween; i++) {
						TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
						//			TOrderTravelplanHisJpEntity travelPlanHis = new TOrderTravelplanHisJpEntity();
						travelplan.setCityId(form.getReturnDepartureCity());
						travelplan.setDay(String.valueOf(i + 1));
						travelplan.setOrderId(orderjpid);
						travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
						travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
						travelplan.setCityName(returngoCity.getCity());
						travelplan.setCreateTime(new Date());
						//酒店和航班取返程即第二行的出发城市
						//酒店
						if (i != daysBetween) {
							THotelEntity hotel = lasthotels.get(lasthotelindex);
							travelplan.setHotel(hotel.getId());
							//酒店历史信息
							//				travelPlanHis.setHotel(hotel.getName());
						}
						if (i == daysBetween) {
							travelplan.setScenic(lastday);
						} else if (i == daysBetween - 3) {
							if (daysBetween == 5) {//如果是5的话，则说明中间没有随机城市
								if (form.getGoArrivedCity() == 77) {
									String countryAirline = countryAirline(form.getGoArrivedCity(),
											form.getReturnDepartureCity(), 1);
									travelplan.setScenic(countryAirline);
								} else {
									String countryAirline = countryAirline(form.getGoArrivedCity(),
											form.getReturnDepartureCity(), 2);
									travelplan.setScenic(countryAirline);
								}
							} else if (daysBetween == 7) {
								String countryAirline = countryAirline(cscityid, form.getReturnDepartureCity(), 2);
								travelplan.setScenic(countryAirline);
							} else {
								String countryAirline = countryAirline(randomArray[totalstyle - 1],
										form.getReturnDepartureCity(), 2);
								travelplan.setScenic(countryAirline);
							}
						} else {
							//景区
							int scenicindex = random.nextInt(scenics.size());
							TScenicEntity scenic = scenics.get(scenicindex);
							scenics.remove(scenic);
							travelplan.setScenic(scenic.getName());
						}
						travelplans.add(travelplan);
					}
				}

			}
		} else {//东北六县第三天要去对应的签证类型城市，不管去程抵达城市和返程出发城市是否一样，中间都随机
				//前两天
			for (int i = 0; i < 2; i++) {
				TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
				//			TOrderTravelplanHisJpEntity travelPlanHis = new TOrderTravelplanHisJpEntity();
				travelplan.setCityId(form.getGoArrivedCity());
				travelplan.setCityName(city.getCity());
				travelplan.setDay(String.valueOf(i + 1));
				travelplan.setOrderId(orderjpid);
				travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
				travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());

				travelplan.setCreateTime(new Date());

				if (i != daysBetween) {
					THotelEntity hotel = hotels.get(hotelindex);
					travelplan.setHotel(hotel.getId());
				}
				if (i == 0) {
					travelplan.setScenic(firstday);
				}
				if (i > 0 && i != daysBetween) {
					//景区
					int scenicindex = random.nextInt(scenics.size());
					TScenicEntity scenic = scenics.get(scenicindex);
					scenics.remove(scenic);
					travelplan.setScenic(scenic.getName());
				}
				//第三天，去返回时即第二行的出发城市，通过日本国内航线或新干线
				if (i == 2) {
					travelplan.setScenic("");
					THotelEntity hotel = lasthotels.get(lasthotelindex);
					travelplan.setHotel(hotel.getId());
				}
				travelplans.add(travelplan);
			}
			//除去开始的前两天和最后四天，如果天数为2的倍数，则中间多2的倍数个随机城市，有余数则最后变为4天
			int subday = daysBetween - 3;
			int[] randomArray = new int[20];

			//第三天去的城市签证类型城市
			int threeCityid = 0;
			if (visatype == 3 || visatype == 8) {//宫城
				threeCityid = 91;
			}
			if (visatype == 4 || visatype == 10) {//岩手
				threeCityid = 92;
			}
			if (visatype == 5 || visatype == 9) {//福岛
				threeCityid = 30;
			}
			if (visatype == 11) {//青森
				threeCityid = 25;
			}
			if (visatype == 12) {//秋田
				threeCityid = 612;
			}
			if (visatype == 13) {//山形
				threeCityid = 613;
			}

			TCityEntity threeCity = dbDao.fetch(TCityEntity.class, threeCityid);
			List<THotelEntity> threeHotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", threeCityid),
					null);
			if (threeHotels.size() < 1) {
				result.put("message", "没有更多的酒店");
				return result;
			}
			List<TScenicEntity> threeScenics = dbDao.query(TScenicEntity.class, Cnd.where("cityId", "=", threeCityid),
					null);
			if (threeScenics.size() < 1) {
				result.put("message", "没有更多的景点");
				return result;
			}
			int threehotel = random.nextInt(threeHotels.size());

			if (subday % 2 == 0) {
				int totalstyle = subday / 2;
				//intArray为所有有景点的城市并且除去东北六县的Id组成的数组
				int[] intArray = generrateCityArray();
				intArray = getCitysArray(intArray, form.getGoArrivedCity(), form.getReturnDepartureCity());
				//randomArray为获取的不重复随机数
				if (intArray.length < totalstyle) {
					result.put("message", "没有更多的城市");
					return result;
				} else {
					if (totalstyle == 0) {
						totalstyle = 1;
					}
					randomArray = getRandomArray(intArray, totalstyle);
				}

				for (int i = 2; i < daysBetween - 2; i++) {

					int firstcityid = 0;
					int lastcityid = 0;
					if (threeCityid == form.getGoArrivedCity()) {
						firstcityid = randomArray[((i - 2) / 2)];
						if (i >= 4) {
							lastcityid = randomArray[((i - 4) / 2)];
						}
					} else {
						if (i >= 3) {
							firstcityid = randomArray[((i - 3) / 2)];
						}
						if (i >= 5) {
							lastcityid = randomArray[((i - 5) / 2)];
						}
					}

					TCityEntity fcity = null;
					List<THotelEntity> fhotels = null;
					List<TScenicEntity> fscenics = null;
					int fhotelindex = 0;
					if (firstcityid != 0) {
						fcity = dbDao.fetch(TCityEntity.class, firstcityid);
						fhotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", firstcityid), null);
						if (fhotels.size() == 0) {
							result.put("message", "没有更多的酒店");
							return result;
						}
						fscenics = dbDao.query(TScenicEntity.class, Cnd.where("cityId", "=", firstcityid), null);
						fhotelindex = random.nextInt(fhotels.size());
					}
					TCityEntity lcity = null;
					if (lastcityid != 0) {
						lcity = dbDao.fetch(TCityEntity.class, lastcityid);
					}

					//tripAirlineService.getTripAirlineSelect(param);

					//第三天
					if (i < 4) {
						TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
						if (threeCityid == form.getGoArrivedCity()) {
							travelplan.setCityId(firstcityid);
							travelplan.setCityName(fcity.getCity());
						} else {
							if (i == 2) {
								travelplan.setCityId(threeCityid);
								travelplan.setCityName(threeCity.getCity());
							} else {
								travelplan.setCityId(firstcityid);
								travelplan.setCityName(fcity.getCity());
							}
						}
						//酒店和航班取返程即第二行的出发城市
						if (i == 2) {
							//酒店
							if (threeCityid == form.getGoArrivedCity()) {
								THotelEntity hotel = fhotels.get(fhotelindex);
								travelplan.setHotel(hotel.getId());
							} else {
								THotelEntity hotel = threeHotels.get(threehotel);
								travelplan.setHotel(hotel.getId());
							}

							//景区
							if (form.getGoArrivedCity() != threeCityid) {
								String countryAirline = countryAirline(form.getGoArrivedCity(), threeCityid, 2);
								int nextInt = random.nextInt(threeScenics.size());
								TScenicEntity scenic = threeScenics.get(nextInt);
								countryAirline = countryAirline + "。" + scenic.getName();
								travelplan.setScenic(countryAirline);
							} else {
								String countryAirline = countryAirline(form.getGoArrivedCity(), firstcityid, 2);
								travelplan.setScenic(countryAirline);
							}
						}
						if (i == 3) {
							//景区
							if (form.getGoArrivedCity() != threeCityid) {
								String countryAirline = countryAirline(threeCityid, firstcityid, 2);
								travelplan.setScenic(countryAirline);

							} else {
								int scenicindex = random.nextInt(fscenics.size());
								TScenicEntity scenic = fscenics.get(scenicindex);
								fscenics.remove(scenic);
								travelplan.setScenic(scenic.getName());
							}

							//酒店
							if (form.getGoArrivedCity() != threeCityid) {
								THotelEntity hotel = fhotels.get(fhotelindex);
								travelplan.setHotel(hotel.getId());
							} else {
								THotelEntity hotel = fhotels.get(fhotelindex);
								travelplan.setHotel(hotel.getId());
							}
						}

						travelplan.setDay(String.valueOf(i + 1));
						travelplan.setOrderId(orderjpid);
						travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
						travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
						travelplan.setCreateTime(new Date());
						travelplans.add(travelplan);
					} else {
						TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
						travelplan.setCityId(firstcityid);
						travelplan.setDay(String.valueOf(i + 1));
						travelplan.setOrderId(orderjpid);
						travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
						travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
						travelplan.setCityName(fcity.getCity());
						travelplan.setCreateTime(new Date());
						//酒店和航班取返程即第二行的出发城市
						//酒店
						if (i % 2 == 1) {
							THotelEntity hotel = fhotels.get(fhotelindex);
							travelplan.setHotel(hotel.getId());
							//酒店历史信息
							//				travelPlanHis.setHotel(hotel.getName());
						}
						if (i % 2 == 1) {
							if (i == 3) {
								String countryAirline = countryAirline(threeCityid, firstcityid, 2);
								travelplan.setScenic(countryAirline);
							} else {
								String countryAirline = countryAirline(lastcityid, firstcityid, 2);
								travelplan.setScenic(countryAirline);
							}
						} else {
							//景区
							if (fscenics.size() == 0) {//如果随机完所有的，则重新查一次
								fscenics = dbDao
										.query(TScenicEntity.class, Cnd.where("cityId", "=", firstcityid), null);
							}
							int scenicindex = random.nextInt(fscenics.size());
							TScenicEntity scenic = fscenics.get(scenicindex);
							fscenics.remove(scenic);
							travelplan.setScenic(scenic.getName());
						}
						travelplans.add(travelplan);
					}
				}

				//最后三天
				for (int i = daysBetween - 2; i <= daysBetween; i++) {
					TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
					travelplan.setCityId(form.getReturnDepartureCity());
					travelplan.setDay(String.valueOf(i + 1));
					travelplan.setOrderId(orderjpid);
					travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
					travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
					travelplan.setCityName(returngoCity.getCity());
					travelplan.setCreateTime(new Date());
					//酒店和航班取返程即第二行的出发城市
					//酒店
					if (i != daysBetween) {
						THotelEntity hotel = lasthotels.get(lasthotelindex);
						travelplan.setHotel(hotel.getId());
						//酒店历史信息
						//				travelPlanHis.setHotel(hotel.getName());
					}
					if (i == daysBetween) {
						travelplan.setScenic(lastday);
					} else if (i == daysBetween - 2) {
						if (daysBetween == 5) {//如果是5则说明中间只有签证类型城市没有随机城市
							String countryAirline = countryAirline(threeCityid, form.getReturnDepartureCity(), 2);
							int nextInt = random.nextInt(threeScenics.size());
							TScenicEntity scenic = threeScenics.get(nextInt);
							countryAirline = countryAirline + "。" + scenic.getName();
							travelplan.setScenic(countryAirline);
						} else if (daysBetween == 3) {
							String countryAirline = countryAirline(form.getGoArrivedCity(),
									form.getReturnDepartureCity(), 2);
							travelplan.setScenic(countryAirline);
						} else {
							String countryAirline = countryAirline(randomArray[totalstyle - 2],
									form.getReturnDepartureCity(), 2);
							travelplan.setScenic(countryAirline);
						}
					} else {
						//景区
						if (scenics.size() == 0) {
							scenics = dbDao.query(TScenicEntity.class,
									Cnd.where("cityId", "=", form.getGoArrivedCity()), null);
						}
						int scenicindex = random.nextInt(scenics.size());
						TScenicEntity scenic = scenics.get(scenicindex);
						scenics.remove(scenic);
						travelplan.setScenic(scenic.getName());
					}
					travelplans.add(travelplan);
				}
			} else {
				int totalstyle = subday / 2;
				//intArray为所有有景点的城市并且出去东北六县的Id组成的数组
				int[] intArray = generrateCityArray();
				intArray = getCitysArray(intArray, form.getGoArrivedCity(), form.getReturnDepartureCity());
				//randomArray为获取的不重复随机数
				if (intArray.length < totalstyle) {
					result.put("message", "没有更多的城市");
					return result;
				} else {
					if (totalstyle == 0) {
						totalstyle = 1;
					}
					randomArray = getRandomArray(intArray, totalstyle);
				}

				for (int i = 2; i < daysBetween - 1; i++) {

					int firstcityid = 0;
					int lastcityid = 0;
					if (threeCityid == form.getGoArrivedCity()) {
						firstcityid = randomArray[((i - 2) / 2)];
						if (i >= 4) {
							lastcityid = randomArray[((i - 4) / 2)];
						}
					} else {
						if (i >= 3) {
							firstcityid = randomArray[((i - 3) / 2)];
						}
						if (i >= 5) {
							lastcityid = randomArray[((i - 5) / 2)];
						}
					}

					TCityEntity fcity = null;
					List<THotelEntity> fhotels = null;
					List<TScenicEntity> fscenics = null;
					int fhotelindex = 0;
					if (firstcityid != 0) {
						fcity = dbDao.fetch(TCityEntity.class, firstcityid);
						fhotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", firstcityid), null);
						if (fhotels.size() == 0) {
							result.put("message", "没有更多的酒店");
							return result;
						}
						fscenics = dbDao.query(TScenicEntity.class, Cnd.where("cityId", "=", firstcityid), null);
						fhotelindex = random.nextInt(fhotels.size());
					}
					TCityEntity lcity = null;
					if (lastcityid != 0) {
						lcity = dbDao.fetch(TCityEntity.class, lastcityid);
					}

					//第三天
					if (i < 4) {
						TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
						if (threeCityid == form.getGoArrivedCity()) {
							travelplan.setCityId(firstcityid);
							travelplan.setCityName(fcity.getCity());
						} else {
							if (i == 2) {
								travelplan.setCityId(threeCityid);
								travelplan.setCityName(threeCity.getCity());
							} else {
								travelplan.setCityId(firstcityid);
								travelplan.setCityName(fcity.getCity());
							}
						}
						//酒店和航班取返程即第二行的出发城市
						if (i == 2) {
							//酒店
							if (threeCityid == form.getGoArrivedCity()) {
								THotelEntity hotel = fhotels.get(fhotelindex);
								travelplan.setHotel(hotel.getId());
							} else {
								THotelEntity hotel = threeHotels.get(threehotel);
								travelplan.setHotel(hotel.getId());
							}

							//景区
							if (form.getGoArrivedCity() != threeCityid) {
								String countryAirline = countryAirline(form.getGoArrivedCity(), threeCityid, 2);
								int nextInt = random.nextInt(threeScenics.size());
								TScenicEntity scenic = threeScenics.get(nextInt);
								countryAirline = countryAirline + "。" + scenic.getName();
								travelplan.setScenic(countryAirline);
							} else {
								String countryAirline = countryAirline(form.getGoArrivedCity(), firstcityid, 2);
								travelplan.setScenic(countryAirline);
							}
						}
						if (i == 3) {
							//景区
							if (form.getGoArrivedCity() != threeCityid) {
								String countryAirline = countryAirline(threeCityid, firstcityid, 2);
								travelplan.setScenic(countryAirline);

							} else {
								int scenicindex = random.nextInt(fscenics.size());
								TScenicEntity scenic = fscenics.get(scenicindex);
								fscenics.remove(scenic);
								travelplan.setScenic(scenic.getName());
							}

							//酒店
							if (form.getGoArrivedCity() != threeCityid) {
								THotelEntity hotel = fhotels.get(fhotelindex);
								travelplan.setHotel(hotel.getId());
							} else {
								THotelEntity hotel = fhotels.get(fhotelindex);
								travelplan.setHotel(hotel.getId());
							}
						}

						travelplan.setDay(String.valueOf(i + 1));
						travelplan.setOrderId(orderjpid);
						travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
						travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
						travelplan.setCreateTime(new Date());
						travelplans.add(travelplan);
					} else {
						TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
						travelplan.setCityId(fcity.getId());
						travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
						travelplan.setDay(String.valueOf(i + 1));
						travelplan.setOrderId(orderjpid);
						travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
						travelplan.setCityName(fcity.getCity());
						travelplan.setCreateTime(new Date());
						//酒店和航班取返程即第二行的出发城市
						//酒店
						if (i % 2 == 1) {
							THotelEntity hotel = fhotels.get(fhotelindex);
							travelplan.setHotel(hotel.getId());
							//酒店历史信息
							//				travelPlanHis.setHotel(hotel.getName());
						}
						if (i % 2 == 1) {
							if (i == 3) {
								String countryAirline = countryAirline(threeCityid, firstcityid, 2);
								travelplan.setScenic(countryAirline);
							} else {
								String countryAirline = countryAirline(lastcityid, firstcityid, 2);
								travelplan.setScenic(countryAirline);
							}
						} else {
							//景区
							if (fscenics.size() == 0) {//如果随机完所有的，则重新查一次
								fscenics = dbDao
										.query(TScenicEntity.class, Cnd.where("cityId", "=", firstcityid), null);
							}
							int scenicindex = random.nextInt(fscenics.size());
							TScenicEntity scenic = fscenics.get(scenicindex);
							fscenics.remove(scenic);
							travelplan.setScenic(scenic.getName());
						}
						travelplans.add(travelplan);
					}
				}

				//最后两天
				for (int i = daysBetween - 1; i <= daysBetween; i++) {
					TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
					//			TOrderTravelplanHisJpEntity travelPlanHis = new TOrderTravelplanHisJpEntity();
					travelplan.setCityId(form.getReturnDepartureCity());
					travelplan.setDay(String.valueOf(i + 1));
					travelplan.setOrderId(orderjpid);
					travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
					travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
					travelplan.setCityName(returngoCity.getCity());
					travelplan.setCreateTime(new Date());
					//酒店和航班取返程即第二行的出发城市
					//酒店
					if (i != daysBetween) {
						THotelEntity hotel = lasthotels.get(lasthotelindex);
						travelplan.setHotel(hotel.getId());
						//酒店历史信息
						//				travelPlanHis.setHotel(hotel.getName());
					}
					if (i == daysBetween) {
						travelplan.setScenic(lastday);
					} else if (i == daysBetween - 3) {
						if (daysBetween == 5) {//如果是5的话，则说明中间没有随机城市
							if (form.getGoArrivedCity() == form.getReturnDepartureCity()) {
								if (scenics.size() == 0) {
									scenics = dbDao.query(TScenicEntity.class,
											Cnd.where("cityId", "=", form.getGoArrivedCity()), null);
								}
								int scenicindex = random.nextInt(scenics.size());
								TScenicEntity scenic = scenics.get(scenicindex);
								scenics.remove(scenic);
								travelplan.setScenic(scenic.getName());
							} else {
								String countryAirline = countryAirline(form.getGoArrivedCity(),
										form.getReturnDepartureCity(), 2);
								travelplan.setScenic(countryAirline);
							}

						} else {
							String countryAirline = countryAirline(randomArray[totalstyle - 1],
									form.getReturnDepartureCity(), 2);
							travelplan.setScenic(countryAirline);
						}
					} else {
						if (daysBetween == 4) {
							if (i == 3) {
								String countryAirline = countryAirline(threeCityid, form.getReturnDepartureCity(), 2);
								int nextInt = random.nextInt(lastscenics.size());
								TScenicEntity scenic = lastscenics.get(nextInt);
								countryAirline = countryAirline + "。" + scenic.getName();
								travelplan.setScenic(countryAirline);
							}
						} else {
							//景区
							String countryAirline = countryAirline(randomArray[totalstyle - 1],
									form.getReturnDepartureCity(), 2);
							int nextInt = random.nextInt(lastscenics.size());
							TScenicEntity scenic = lastscenics.get(nextInt);
							countryAirline = countryAirline + "。" + scenic.getName();
							travelplan.setScenic(countryAirline);

						}
					}
					travelplans.add(travelplan);
				}
			}
		}

		List<TOrderTravelplanJpEntity> before = dbDao.query(TOrderTravelplanJpEntity.class,
				Cnd.where("orderid", "=", orderjpid), null);
		//更新行程安排
		dbDao.updateRelations(before, travelplans);
		//更新历史行程安排
		//		dbDao.updateRelations(beforeHis, travelplansHis);
		result.put("status", "success");
		result.put("orderid", orderjpid);
		result.put("data", getTravelPlanByOrderId(orderjpid));
		result.put("orderjpid", orderjpid);
		return result;
	}*/

	public Object generateTravelPlan(HttpServletRequest request, GenerrateTravelForm form) {
		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Map<String, Object> result = Maps.newHashMap();
		if (Util.isEmpty(form.getGoDepartureCity())) {
			result.put("message", "请选择出发城市");
			return result;
		}
		if (Util.isEmpty(form.getGoArrivedCity())) {
			result.put("message", "请选择抵达城市");
			return result;
		}
		if (Util.isEmpty(form.getGoDate())) {
			result.put("message", "请选择出发日期");
			return result;
		}
		if (Util.isEmpty(form.getReturnDate())) {
			result.put("message", "请选择返回日期");
			return result;
		}
		if (Util.isEmpty(form.getGoFlightNum())) {
			result.put("message", "请选择出发航班号");
			return result;
		}
		if (Util.isEmpty(form.getReturnFlightNum())) {
			result.put("message", "请选择返回航班号");
			return result;
		}
		if (Util.isEmpty(form.getVisatype())) {
			result.put("message", "请选择签证类型");
			return result;
		}
		int daysBetween = DateUtil.daysBetween(form.getGoDate(), form.getReturnDate());
		if (daysBetween < 4) {
			result.put("message", "停留天数必须大于4天");
			return result;
		}
		//返回时的出发城市
		Integer returnDepartureCity = form.getReturnDepartureCity();
		TCityEntity returngoCity = dbDao.fetch(TCityEntity.class, returnDepartureCity.longValue());

		//出发城市
		Integer goDepartureCity = form.getGoDepartureCity();
		TCityEntity goCity = dbDao.fetch(TCityEntity.class, goDepartureCity.longValue());
		String province = goCity.getProvince();
		if (province.endsWith("省") || province.endsWith("市")) {
			province = province.substring(0, province.length() - 1);
		}
		//出发航班
		String goFlightNum = form.getGoFlightNum();
		String firstday = " "
				+ province
				+ "から"
				+ goFlightNum.substring(goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")) + 1,
						goFlightNum.indexOf(" ", goFlightNum.indexOf(" ") + 1))
				+ "便にて"
				+ goFlightNum.substring(goFlightNum.indexOf("-", goFlightNum.lastIndexOf("-")) + 1,
						goFlightNum.indexOf(" ", goFlightNum.indexOf(" "))) + "へ" + "\n 到着後、ホテルへ";

		//返回航班
		String returnFlightNum = form.getReturnFlightNum();
		String lastday = " "
				+ returnFlightNum.substring(0, returnFlightNum.indexOf("-", returnFlightNum.indexOf("-")))
				+ "から"
				+ returnFlightNum.substring(returnFlightNum.indexOf(" ", returnFlightNum.indexOf(" ")) + 1,
						returnFlightNum.indexOf(" ", returnFlightNum.indexOf(" ") + 1)) + "便にて帰国";

		FlightSelectParam param = null;
		//根据签证类型来决定前两天的城市
		Integer visatype = form.getVisatype();

		//获取前两天城市
		TCityEntity city = dbDao.fetch(TCityEntity.class, form.getGoArrivedCity().longValue());
		//获取前两天城市所有的酒店
		List<THotelEntity> hotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", form.getGoArrivedCity()),
				null);
		//获取前两天城市所有的景区
		List<TScenicEntity> scenics = dbDao.query(TScenicEntity.class,
				Cnd.where("cityId", "=", form.getGoArrivedCity()), null);
		if (scenics.size() < 2) {
			result.put("message", "没有更多的景区");
			return result;
		}
		//获取后两天城市
		TCityEntity lastcity = dbDao.fetch(TCityEntity.class, form.getReturnDepartureCity().longValue());
		//获取后两天酒店
		List<THotelEntity> lasthotels = dbDao.query(THotelEntity.class,
				Cnd.where("cityId", "=", form.getReturnDepartureCity()), null);
		//获取后两天景区
		List<TScenicEntity> lastscenics = dbDao.query(TScenicEntity.class,
				Cnd.where("cityId", "=", form.getReturnDepartureCity()), null);
		if (lastscenics.size() < 2) {
			result.put("message", "没有更多的景区");
			return result;
		}
		Integer orderjpid = form.getOrderid();
		Integer orderid = null;
		if (Util.isEmpty(orderjpid)) {
			//如果订单不存在，创建订单
			Map<String, Integer> generrateorder = generrateorder(loginUser, loginCompany);
			orderid = generrateorder.get("orderid");
			orderjpid = generrateorder.get("orderjpid");
		} else {
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
			orderid = orderjp.getOrderId();
		}
		//需要生成的travelplan
		List<TOrderTravelplanJpEntity> travelplans = Lists.newArrayList();
		//生成行程安排历史信息
		//		List<TOrderTravelplanHisJpEntity> travelplansHis = Lists.newArrayList();

		//在一个城市只住一家酒店
		Random random = new Random();
		int hotelindex = random.nextInt(hotels.size());
		int lasthotelindex = random.nextInt(lasthotels.size());

		if (visatype == 6 || visatype == 1 || visatype == 14 || visatype == 2 || visatype == 7) {//除去东北六县
			//如果去程抵达城市和返回出发城市一样，则什么都不需要分
			if (form.getGoArrivedCity() == form.getReturnDepartureCity()) {
				for (int i = 0; i <= daysBetween; i++) {
					TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
					travelplan.setCityId(form.getGoArrivedCity());
					travelplan.setDay(String.valueOf(i + 1));
					travelplan.setOrderId(orderjpid);
					travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
					travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
					travelplan.setCityName(city.getCity());
					travelplan.setCreateTime(new Date());

					//酒店
					if (i != daysBetween) {
						THotelEntity hotel = hotels.get(hotelindex);
						travelplan.setHotel(hotel.getId());
					}
					if (i > 0 && i != daysBetween) {
						//景区
						if (scenics.size() == 0) {
							scenics = dbDao.query(TScenicEntity.class,
									Cnd.where("cityId", "=", form.getGoArrivedCity()), null);
						}
						int scenicindex = random.nextInt(scenics.size());
						TScenicEntity scenic = scenics.get(scenicindex);
						scenics.remove(scenic);
						travelplan.setScenic(scenic.getName());
					}
					if (i == 0) {//第一天
						travelplan.setScenic(firstday);
					}
					if (i == daysBetween) {//最后一天
						travelplan.setScenic(lastday);
					}
					travelplans.add(travelplan);
				}
			} else {
				//为什么要<=，因为最后一天也要玩
				//if (visatype == 6 || visatype == 1 || visatype == 14) {//普通三年多次，日本单次和普通五年多次一样
				//前两天
				for (int i = 0; i < 2; i++) {
					TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
					travelplan.setCityId(form.getGoArrivedCity());
					travelplan.setCityName(city.getCity());
					travelplan.setDay(String.valueOf(i + 1));
					travelplan.setOrderId(orderjpid);
					travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
					travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());

					travelplan.setCreateTime(new Date());

					if (i != daysBetween) {
						THotelEntity hotel = hotels.get(hotelindex);
						travelplan.setHotel(hotel.getId());
					}
					if (i == 0) {
						travelplan.setScenic(firstday);
					}
					if (i > 0 && i != daysBetween) {
						//景区
						int scenicindex = random.nextInt(scenics.size());
						TScenicEntity scenic = scenics.get(scenicindex);
						scenics.remove(scenic);
						travelplan.setScenic(scenic.getName());
					}
					travelplans.add(travelplan);
				}
				//除去开始的前两天和最后两天天，如果天数为2的倍数，则中间多2的倍数个随机城市，有余数则最后变为3天
				int subday = 0;
				if (daysBetween % 2 == 0) {
					subday = daysBetween - 4;
				} else {
					subday = daysBetween - 3;
				}
				Map<String, List<Integer>> citysandDates = null;
				List<Integer> citysList = null;
				List<Integer> datesList = null;
				//最多随机几个城市
				int totalstyle = subday / 2;
				//intArray为所有有景点的城市并且出去东北六县的Id组成的数组
				int[] intArray = generrateCityArray();
				intArray = getCitysArray(intArray, form.getGoArrivedCity(), form.getReturnDepartureCity());
				//randomArray为获取的不重复随机数
				int j = 0;
				if (subday != 0) {
					//随机城市和天数
					citysandDates = getRandomCity(intArray, totalstyle, subday);
					datesList = citysandDates.get("days");
					citysList = citysandDates.get("citys");
					for (int i = 2; i < 2 + subday; i++) {
						TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();

						if (i == getNum(datesList, j) + 2) {
							j++;
							if (i == 2) {
								String countryAirline = countryAirline(form.getGoArrivedCity(), citysList.get(j - 1), 1);
								travelplan.setScenic(countryAirline);
							} else {
								String countryAirline = countryAirline(citysList.get(j - 2), citysList.get(j - 1), 1);
								travelplan.setScenic(countryAirline);
							}

							List<THotelEntity> nowhotels = dbDao.query(THotelEntity.class,
									Cnd.where("cityId", "=", citysList.get(j - 1)), null);
							int scenicindex = random.nextInt(nowhotels.size());
							THotelEntity hotel = nowhotels.get(scenicindex);
							travelplan.setHotel(hotel.getId());

						} else {
							TCityEntity nowcity = dbDao.fetch(TCityEntity.class, citysList.get(j - 1).longValue());
							List<THotelEntity> nowhotels = dbDao.query(THotelEntity.class,
									Cnd.where("cityId", "=", citysList.get(j - 1)), null);
							List<TScenicEntity> nowscenics = dbDao.query(TScenicEntity.class,
									Cnd.where("cityId", "=", citysList.get(j - 1)), null);
							int scenicindex = random.nextInt(nowscenics.size());
							TScenicEntity scenic = nowscenics.get(scenicindex);
							nowscenics.remove(scenic);
							travelplan.setScenic(scenic.getName());

						}

						TCityEntity nowcity = dbDao.fetch(TCityEntity.class, citysList.get(j - 1).longValue());
						List<THotelEntity> nowhotels = dbDao.query(THotelEntity.class,
								Cnd.where("cityId", "=", citysList.get(j - 1)), null);
						List<TScenicEntity> nowscenics = dbDao.query(TScenicEntity.class,
								Cnd.where("cityId", "=", citysList.get(j - 1)), null);
						travelplan.setCityId(citysList.get(j - 1));
						travelplan.setCityName(nowcity.getCity());
						travelplan.setDay(String.valueOf(i + 1));
						travelplan.setOrderId(orderjpid);
						travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
						travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
						travelplan.setCreateTime(new Date());

						travelplans.add(travelplan);

					}
				} else {

				}

				if (daysBetween % 2 == 0) {
					//最后三天
					for (int i = daysBetween - 2; i <= daysBetween; i++) {
						TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
						travelplan.setCityId(form.getReturnDepartureCity());
						travelplan.setDay(String.valueOf(i + 1));
						travelplan.setOrderId(orderjpid);
						travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
						travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
						travelplan.setCityName(returngoCity.getCity());
						travelplan.setCreateTime(new Date());
						//酒店和航班取返程即第二行的出发城市
						//酒店
						if (i != daysBetween) {
							THotelEntity hotel = lasthotels.get(lasthotelindex);
							travelplan.setHotel(hotel.getId());
							//酒店历史信息
							//				travelPlanHis.setHotel(hotel.getName());
						}
						if (i == daysBetween) {
							travelplan.setScenic(lastday);
						} else if (i == daysBetween - 2) {
							if (daysBetween == 4) {
								String countryAirline = countryAirline(form.getGoArrivedCity(),
										form.getReturnDepartureCity(), 2);
								travelplan.setScenic(countryAirline);
							} else {
								String countryAirline = countryAirline(citysList.get(j - 1),
										form.getReturnDepartureCity(), 2);
								travelplan.setScenic(countryAirline);
							}
						} else {
							//景区
							if (scenics.size() == 0) {
								scenics = dbDao.query(TScenicEntity.class,
										Cnd.where("cityId", "=", form.getGoArrivedCity()), null);
							}
							int scenicindex = random.nextInt(scenics.size());
							TScenicEntity scenic = scenics.get(scenicindex);
							scenics.remove(scenic);
							travelplan.setScenic(scenic.getName());
						}
						travelplans.add(travelplan);
					}
				} else {
					//最后两天
					for (int i = daysBetween - 1; i <= daysBetween; i++) {
						TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
						travelplan.setCityId(form.getReturnDepartureCity());
						travelplan.setDay(String.valueOf(i + 1));
						travelplan.setOrderId(orderjpid);
						travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
						travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
						travelplan.setCityName(returngoCity.getCity());
						travelplan.setCreateTime(new Date());
						//酒店和航班取返程即第二行的出发城市
						//酒店
						if (i != daysBetween) {
							THotelEntity hotel = lasthotels.get(lasthotelindex);
							travelplan.setHotel(hotel.getId());
							//酒店历史信息
							//				travelPlanHis.setHotel(hotel.getName());
						}
						if (i == daysBetween) {
							travelplan.setScenic(lastday);
						} else {
							String countryAirline = countryAirline(citysList.get(j - 1), form.getReturnDepartureCity(),
									2);
							int nextInt = random.nextInt(lastscenics.size());
							countryAirline = countryAirline + "。" + lastscenics.get(nextInt).getName();
							travelplan.setScenic(countryAirline);
						}
						travelplans.add(travelplan);
					}
				}
			}

		} else {//东北六县第三天要去对应的签证类型城市，不管去程抵达城市和返程出发城市是否一样，中间都随机
			//前两天
			for (int i = 0; i < 2; i++) {
				TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
				travelplan.setCityId(form.getGoArrivedCity());
				travelplan.setCityName(city.getCity());
				travelplan.setDay(String.valueOf(i + 1));
				travelplan.setOrderId(orderjpid);
				travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
				travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());

				travelplan.setCreateTime(new Date());

				if (i != daysBetween) {
					THotelEntity hotel = hotels.get(hotelindex);
					travelplan.setHotel(hotel.getId());
				}
				if (i == 0) {
					travelplan.setScenic(firstday);
				}
				if (i > 0 && i != daysBetween) {
					//景区
					int scenicindex = random.nextInt(scenics.size());
					TScenicEntity scenic = scenics.get(scenicindex);
					scenics.remove(scenic);
					travelplan.setScenic(scenic.getName());
				}
				travelplans.add(travelplan);
			}

			//第三天去的城市签证类型城市
			int threeCityid = 0;
			if (visatype == 3 || visatype == 8) {//宫城
				threeCityid = 91;
			}
			if (visatype == 4 || visatype == 10) {//岩手
				threeCityid = 92;
			}
			if (visatype == 5 || visatype == 9) {//福岛
				threeCityid = 30;
			}
			if (visatype == 11) {//青森
				threeCityid = 25;
			}
			if (visatype == 12) {//秋田
				threeCityid = 612;
			}
			if (visatype == 13) {//山形
				threeCityid = 613;
			}

			TCityEntity threeCity = dbDao.fetch(TCityEntity.class, threeCityid);
			List<THotelEntity> threeHotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", threeCityid),
					null);
			if (threeHotels.size() < 1) {
				result.put("message", "没有更多的酒店");
				return result;
			}
			List<TScenicEntity> threeScenics = dbDao.query(TScenicEntity.class, Cnd.where("cityId", "=", threeCityid),
					null);
			if (threeScenics.size() < 1) {
				result.put("message", "没有更多的景点");
				return result;
			}
			int threehotel = random.nextInt(threeHotels.size());

			//第三天
			TOrderTravelplanJpEntity thravelplan = new TOrderTravelplanJpEntity();
			thravelplan.setCityId(threeCityid);
			thravelplan.setCityName(threeCity.getCity());
			thravelplan.setDay(String.valueOf(3));
			thravelplan.setOrderId(orderjpid);
			thravelplan.setOutDate(DateUtil.addDay(form.getGoDate(), 2));
			thravelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
			thravelplan.setCreateTime(new Date());
			//酒店
			THotelEntity thotel = threeHotels.get(threehotel);
			thravelplan.setHotel(thotel.getId());
			//景区
			String threeScenic = countryAirline(form.getGoArrivedCity(), threeCityid, 1);
			int threeScenicIndex = random.nextInt(threeScenics.size());
			TScenicEntity tScenicEntity = threeScenics.get(threeScenicIndex);

			threeScenic = threeScenic + "。" + tScenicEntity.getName();
			thravelplan.setScenic(threeScenic);

			travelplans.add(thravelplan);

			//除去开始的前两天和最后两天天，如果天数为2的倍数，则中间多2的倍数个随机城市，有余数则最后变为3天
			int subday = 0;
			if (daysBetween % 2 == 0) {
				subday = daysBetween - 4;
			} else {
				subday = daysBetween - 5;
			}
			Map<String, List<Integer>> citysandDates = null;
			List<Integer> citysList = null;
			List<Integer> datesList = null;
			//最多随机几个城市
			int totalstyle = subday / 2;
			//intArray为所有有景点的城市并且出去东北六县的Id组成的数组
			int[] intArray = generrateCityArray();
			intArray = getCitysArray(intArray, form.getGoArrivedCity(), form.getReturnDepartureCity());
			//randomArray为获取的不重复随机数
			//随机城市和天数

			int j = 0;
			if (subday != 0) {
				citysandDates = getRandomCity(intArray, totalstyle, subday);
				datesList = citysandDates.get("days");
				citysList = citysandDates.get("citys");
				for (int i = 3; i < 3 + subday; i++) {
					TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();

					if (i == getNum(datesList, j) + 3) {
						j++;
						if (i == 3) {
							String countryAirline = countryAirline(threeCityid, citysList.get(j - 1), 1);
							travelplan.setScenic(countryAirline);
						} else {
							String countryAirline = countryAirline(citysList.get(j - 2), citysList.get(j - 1), 1);
							travelplan.setScenic(countryAirline);
						}

						List<THotelEntity> nowhotels = dbDao.query(THotelEntity.class,
								Cnd.where("cityId", "=", citysList.get(j - 1)), null);
						int nowhotelindex = random.nextInt(nowhotels.size());
						THotelEntity hotel = nowhotels.get(nowhotelindex);
						travelplan.setHotel(hotel.getId());

					} else {
						List<TScenicEntity> nowscenics = dbDao.query(TScenicEntity.class,
								Cnd.where("cityId", "=", citysList.get(j - 1)), null);
						int scenicindex = random.nextInt(nowscenics.size());
						TScenicEntity scenic = nowscenics.get(scenicindex);
						nowscenics.remove(scenic);
						travelplan.setScenic(scenic.getName());

					}

					TCityEntity nowcity = dbDao.fetch(TCityEntity.class, citysList.get(j - 1).longValue());
					travelplan.setCityId(citysList.get(j - 1));
					travelplan.setCityName(nowcity.getCity());
					travelplan.setDay(String.valueOf(i + 1));
					travelplan.setOrderId(orderjpid);
					travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
					travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
					travelplan.setCreateTime(new Date());

					travelplans.add(travelplan);

				}
			} else {

			}

			if (daysBetween % 2 == 1) {
				//最后三天
				for (int i = daysBetween - 2; i <= daysBetween; i++) {
					TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
					travelplan.setCityId(form.getReturnDepartureCity());
					travelplan.setDay(String.valueOf(i + 1));
					travelplan.setOrderId(orderjpid);
					travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
					travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
					travelplan.setCityName(returngoCity.getCity());
					travelplan.setCreateTime(new Date());
					//酒店和航班取返程即第二行的出发城市
					//酒店
					if (i != daysBetween) {
						THotelEntity hotel = lasthotels.get(lasthotelindex);
						travelplan.setHotel(hotel.getId());
						//酒店历史信息
						//				travelPlanHis.setHotel(hotel.getName());
					}
					if (i == daysBetween) {
						travelplan.setScenic(lastday);
					} else if (i == daysBetween - 2) {
						if (daysBetween == 5) {
							String countryAirline = countryAirline(threeCityid, form.getReturnDepartureCity(), 2);
							travelplan.setScenic(countryAirline);

						} else {
							String countryAirline = countryAirline(citysList.get(j - 1), form.getReturnDepartureCity(),
									2);
							travelplan.setScenic(countryAirline);
						}
					} else {
						//景区
						if (scenics.size() == 0) {
							scenics = dbDao.query(TScenicEntity.class,
									Cnd.where("cityId", "=", form.getGoArrivedCity()), null);
						}
						int scenicindex = random.nextInt(scenics.size());
						TScenicEntity scenic = scenics.get(scenicindex);
						scenics.remove(scenic);
						travelplan.setScenic(scenic.getName());
					}
					travelplans.add(travelplan);
				}
			} else {
				//最后两天
				for (int i = daysBetween - 1; i <= daysBetween; i++) {
					TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
					travelplan.setCityId(form.getReturnDepartureCity());
					travelplan.setDay(String.valueOf(i + 1));
					travelplan.setOrderId(orderjpid);
					travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
					travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
					travelplan.setCityName(returngoCity.getCity());
					travelplan.setCreateTime(new Date());
					//酒店和航班取返程即第二行的出发城市
					//酒店
					if (i != daysBetween) {
						THotelEntity tHotelEntity = lasthotels.get(lasthotelindex);
						travelplan.setHotel(tHotelEntity.getId());
						//酒店历史信息
						//				travelPlanHis.setHotel(hotel.getName());
					}
					if (i == daysBetween) {
						travelplan.setScenic(lastday);
					} else {
						if (daysBetween == 4) {
							String countryAirline = countryAirline(threeCityid, form.getReturnDepartureCity(), 2);
							int nextInt = random.nextInt(lastscenics.size());
							countryAirline = countryAirline + "。" + lastscenics.get(nextInt).getName();
							travelplan.setScenic(countryAirline);
						} else {
							String countryAirline = countryAirline(citysList.get(j - 1), form.getReturnDepartureCity(),
									2);
							int nextInt = random.nextInt(lastscenics.size());
							countryAirline = countryAirline + "。" + lastscenics.get(nextInt).getName();
							travelplan.setScenic(countryAirline);
						}
					}
					travelplans.add(travelplan);
				}
			}

		}

		List<TOrderTravelplanJpEntity> before = dbDao.query(TOrderTravelplanJpEntity.class,
				Cnd.where("orderid", "=", orderjpid), null);
		//更新行程安排
		dbDao.updateRelations(before, travelplans);
		//更新历史行程安排
		//		dbDao.updateRelations(beforeHis, travelplansHis);
		result.put("status", "success");
		result.put("orderid", orderjpid);
		result.put("data", getTravelPlanByOrderId(orderjpid));
		result.put("orderjpid", orderjpid);
		return result;
	}

	//获取日本国内航班或新干线的第一天
	public String countryAirline(int gocityid, int arrcityid, int flag) {
		FlightSelectParam param = new FlightSelectParam();
		TCityEntity gocity = dbDao.fetch(TCityEntity.class, gocityid);
		TCityEntity arrcity = dbDao.fetch(TCityEntity.class, arrcityid);
		String firstday = gocity.getCity() + "から" + arrcity.getCity() + "まで新幹線で";
		/*param.setGocity((long) gocityid);
		param.setArrivecity((long) arrcityid);
		param.setDate("");
		param.setFlight("");
		List<ResultflyEntity> tripAirlineSelect = tripAirlineService.getTripAirlineSelect(param);
		Random random = new Random();
		String firstday = "";
		if (tripAirlineSelect.size() > 0) {
			int n = random.nextInt(tripAirlineSelect.size());
			ResultflyEntity resultflyEntity = tripAirlineSelect.get(n);
			firstday += gocity.getCity() + "から" + resultflyEntity.getFlightnum() + "便にて"
					+ resultflyEntity.getArrflightname() + "へ到着後、ホテルへ" + ",";
		}
		if (flag == 2) {
			firstday += gocity.getCity() + "から" + arrcity.getCity() + "まで新幹線で" + ",";
		}

		//随机获取一个
		String[] airlines = firstday.split(",");
		int num = (int) (Math.random() * airlines.length);
		return airlines[num];*/
		return firstday;
	}

	public Map<String, Integer> generrateorder(TUserEntity user, TCompanyEntity company) {
		Map<String, Integer> result = Maps.newHashMap();
		//如果订单不存在，则先创建订单
		TOrderEntity orderinfo = new TOrderEntity();
		orderinfo.setComId(company.getId());
		orderinfo.setUserId(user.getId());
		orderinfo.setOrderNum(generrateOrdernum());
		orderinfo.setStatus(JPOrderStatusEnum.PLACE_ORDER.intKey());
		orderinfo.setZhaobaocomplete(IsYesOrNoEnum.NO.intKey());
		orderinfo.setZhaobaoupdate(IsYesOrNoEnum.NO.intKey());
		orderinfo.setIsDisabled(IsYesOrNoEnum.NO.intKey());
		orderinfo.setCreateTime(new Date());
		orderinfo.setUpdateTime(new Date());
		TOrderEntity orderinsert = dbDao.insert(orderinfo);
		changePrincipalViewService.ChangePrincipal(orderinsert.getId(), JPOrderProcessTypeEnum.SALES_PROCESS.intKey(),
				user.getId());
		result.put("orderid", orderinsert.getId());
		TOrderJpEntity orderjp = new TOrderJpEntity();
		orderjp.setOrderId(orderinsert.getId());
		orderjp.setVisaType(MainSaleVisaTypeEnum.SINGLE.intKey());
		orderjp.setIsVisit(IsYesOrNoEnum.NO.intKey());
		TOrderJpEntity orderjpinsert = dbDao.insert(orderjp);
		Integer orderjpid = orderjpinsert.getId();
		result.put("orderjpid", orderjpid);
		return result;
	}

	public static int[] getRandomArray(int[] paramArray, int count) {
		if (paramArray.length < count) {
			return paramArray;
		}
		int[] newArray = new int[count];
		Random random = new Random();
		int temp = 0;//接收产生的随机数
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= count; i++) {
			temp = random.nextInt(paramArray.length);//将产生的随机数作为被抽数组的索引
			if (!(list.contains(temp))) {
				newArray[i - 1] = paramArray[temp];
				list.add(temp);
			} else {
				i--;
			}
		}
		return newArray;
	}

	//生成不重复的随机数，并除去去程的抵达城市和返程的出发城市
	public Map<String, List<Integer>> getRandomCity(int[] paramArray, int count, int days) {
		Map<String, List<Integer>> result = Maps.newHashMap();
		Random random = new Random();
		if (count == 0) {
			count = 1;
		}
		int newCount = 0;
		if (count <= paramArray.length) {
			newCount = random.nextInt(count) + 1;
		} else {
			newCount = random.nextInt(paramArray.length) + 1;

		}
		int[] newArray = new int[newCount];

		int temp = 0;//接收产生的随机数
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= newCount; i++) {
			temp = random.nextInt(paramArray.length);//将产生的随机数作为被抽数组的索引
			if (!(list.contains(temp))) {
				newArray[i - 1] = paramArray[temp];
				list.add(temp);
			} else {
				i--;
			}
		}

		System.out.println(newArray.length + "----");
		List<Integer> randomDates = getRandomDates(newArray, days);
		System.out.println(randomDates.size() + "!!!!");

		List<Integer> cityidList = Ints.asList(newArray);
		System.out.println(cityidList.size() + "++++++");

		for (int i = 0; i < cityidList.size(); i++) {
			List<TScenicEntity> scenics = dbDao.query(TScenicEntity.class, Cnd.where("cityId", "=", cityidList.get(i)),
					null);
			System.out.println(scenics.size());
			System.out.println(randomDates.size() + "======");
			if (randomDates.size() > 0) {
				if (randomDates.get(i) > scenics.size()) {
					randomDates.clear();
					return getRandomCity(paramArray, count, days);
				}
			}
		}
		result.put("citys", cityidList);
		result.put("days", randomDates);
		return result;
	}

	//随机天数
	public List<Integer> getRandomDates(int[] paramArray, int days) {
		Random random = new Random();
		List<Integer> numbers = new ArrayList<Integer>();
		int sum = 0;
		while (true) {
			int n = random.nextInt(days - 1) + 2;
			sum += n;
			numbers.add(n);

			if (numbers.size() > paramArray.length || sum > days) {
				numbers.clear();
				sum = 0;
			}

			if (numbers.size() == paramArray.length && sum == days) {
				break;
			}
		}
		System.out.println(numbers);

		return numbers;
	}

	public int getNum(List<Integer> datesList, int n) {
		int num = 0;
		if (datesList.size() >= 1) {
			for (int i = 0; i < n; i++) {
				num += datesList.get(i);
			}
		}
		return num;
	}

	//获取城市id数组
	/*public int[] generrateCityArray() {
		String sixCityid = "";
		ArrayList<String> cityList = new ArrayList<>();
		cityList.add("冲绳");
		cityList.add("宫城");
		cityList.add("福岛");
		cityList.add("岩手");
		cityList.add("青森");
		cityList.add("秋田");
		cityList.add("山形");
		for (String string : cityList) {
			TCityEntity city = dbDao.fetch(TCityEntity.class, Cnd.where("city", "like", "%" + string + "%"));
			sixCityid += city.getId() + ",";
		}
		List<TScenicEntity> scenics = dbDao.query(TScenicEntity.class,
				Cnd.where("cityId", "not in", sixCityid.substring(0, sixCityid.length() - 1)).groupBy("cityId"), null);
		int[] cityArray = new int[scenics.size()];
		for (int i = 0; i < scenics.size(); i++) {
			cityArray[i] = scenics.get(i).getCityId();
		}
		return cityArray;
	}*/
	public int[] generrateCityArray() {
		int[] cityArray = { 22, 51, 38, 50, 85, 53, 37 };
		/*String sixCityid = "";
		ArrayList<String> cityList = new ArrayList<>();
		cityList.add("冲绳");
		cityList.add("宫城");
		cityList.add("福岛");
		cityList.add("岩手");
		cityList.add("青森");
		cityList.add("秋田");
		cityList.add("山形");
		for (String string : cityList) {
			TCityEntity city = dbDao.fetch(TCityEntity.class, Cnd.where("city", "like", "%" + string + "%"));
			sixCityid += city.getId() + ",";
		}
		List<TScenicEntity> scenics = dbDao.query(TScenicEntity.class,
				Cnd.where("cityId", "not in", sixCityid.substring(0, sixCityid.length() - 1)).groupBy("cityId"), null);
		int[] cityArray = new int[scenics.size()];
		for (int i = 0; i < scenics.size(); i++) {
			cityArray[i] = scenics.get(i).getCityId();
		}*/
		return cityArray;
	}

	//生成附近城市的Id数组,要去除掉去程的抵达城市、返程的出发城市以及东北六县
	public static int[] getCitysArray(int[] paramArray, int arrcityid, int returngocityid) {

		for (int i = 0; i < paramArray.length; i++) {
			if (paramArray[i] == arrcityid) {
				paramArray = ArrayUtils.remove(paramArray, i);
			}
			if (paramArray[i] == returngocityid) {
				paramArray = ArrayUtils.remove(paramArray, i);
			}
		}

		return paramArray;
	}

	private String generrateOrdernum() {
		//生成订单号
		SimpleDateFormat smf = new SimpleDateFormat("yyMMdd");
		String format = smf.format(new Date());
		String sqlString = sqlManager.get("orderJp_ordernum");
		Sql sql = Sqls.create(sqlString);
		List<Record> query = dbDao.query(sql, null, null);
		int sum = 1;
		if (!Util.isEmpty(query) && query.size() > 0) {
			String string = query.get(0).getString("orderNum");
			int a = Integer.valueOf(string.substring(9, string.length()));
			sum += a;
		}
		String sum1 = "";
		if (sum / 10 == 0) {
			sum1 = "000" + sum;
		} else if (sum / 100 == 0) {
			sum1 = "00" + sum;

		} else if (sum / 1000 == 0) {
			sum1 = "0" + sum;
		} else {
			sum1 = "" + sum;

		}
		return format + "-JP" + sum1;
	}

	/**
	 * 获取行程安排数据
	 * <p>
	 * TODO 通过订单id获取行程安排数据
	 *
	 * @param orderid
	 * @return TODO 获取行程安排数据
	 * @throws ParseException 
	 */
	public Object getTravelPlanByOrderId(Integer orderid) {
		String sqlString = sqlManager.get("get_travel_plan_by_orderid");
		Sql sql = Sqls.create(sqlString);
		sql.setParam("orderid", orderid);
		List<Record> travelplans = dbDao.query(sql, null, null);
		DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		int count = 1;
		String prehotelname = "";
		for (Record record : travelplans) {
			Date outdate = (Date) record.get("outdate");
			record.put("outdate", format.format(outdate));
			//酒店名字
			if (!Util.isEmpty(record.get("hotelname"))) {
				String hotelname = (String) record.get("hotelname");
				if (count > 1) {
					if (hotelname.equals(prehotelname)) {
						record.put("hotelname", "連泊");
						record.put("hoteladdress", "");
						record.put("hotelmobile", "");
					}
				}
				prehotelname = hotelname;
			}
			//hotelid
			if (Util.isEmpty(record.get("hotel"))) {
				record.get("day");
				TOrderTravelplanJpEntity fetch = dbDao.fetch(TOrderTravelplanJpEntity.class,
						Cnd.where("orderId", "=", orderid)
								.and("day", "=", Long.valueOf((String) record.get("day")) - 1));
				TOrderTravelplanJpEntity plan = dbDao.fetch(TOrderTravelplanJpEntity.class,
						Cnd.where("orderId", "=", orderid).and("day", "=", Long.valueOf((String) record.get("day"))));
				plan.setHotel(fetch.getHotel());
				dbDao.update(plan);
			}
			count++;
		}
		return travelplans;
	}

	/**
	 * 跳转到添加申请人页面
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid
	 * @param request 
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addApplicant(Integer orderid, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String, Object> result = Maps.newHashMap();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		result.put("boyOrGirlEnum", EnumUtil.enum2(BoyOrGirlEnum.class));
		result.put("orderid", orderid);
		String localAddr = request.getServerName();
		int localPort = request.getServerPort();
		result.put("localAddr", localAddr);
		result.put("localPort", localPort);
		result.put("websocketaddr", SIMPLE_WEBSOCKET_ADDR);
		String qrurl = "http://" + localAddr + ":" + localPort + "/simplemobile/info.html";
		if (Util.isEmpty(orderid)) {
			qrurl += "?comid=" + loginCompany.getId() + "&userid=" + loginUser.getId() + "&sessionid="
					+ session.getId();
		} else {
			qrurl += "?comid=" + loginCompany.getId() + "&userid=" + loginUser.getId() + "&orderid=" + orderid
					+ "&sessionid=" + session.getId();
		}
		String qrCode = qrCodeService.encodeQrCode(request, qrurl);
		result.put("qrCode", qrCode);
		result.put("sessionid", session.getId());
		return result;
	}

	/**
	 * 保存下单信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveAddOrderinfo(AddOrderForm form, HttpServletRequest request) {
		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer orderjpid = form.getOrderid();
		Integer orderid = null;
		//获取订单id信息
		if (Util.isEmpty(orderjpid)) {
			Map<String, Integer> generrateorder = generrateorder(loginUser, loginCompany);
			orderid = generrateorder.get("orderid");
			orderjpid = generrateorder.get("orderjpid");
			orderJpViewService.insertLogs(orderid, JpOrderSimpleEnum.PLACE_ORDER.intKey(), session);

		} else {
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
			orderid = orderjp.getOrderId();
			//根据订单ID查询
			TOrderLogsEntity logs = dbDao.fetch(TOrderLogsEntity.class, Cnd.where("orderid", "=", orderid.longValue()));
			if (Util.isEmpty(logs)) {
				orderJpViewService.insertLogs(orderid, JpOrderSimpleEnum.PLACE_ORDER.intKey(), session);
			}
			if (JpOrderSimpleEnum.PLACE_ORDER.intKey() != 1) {
				orderJpViewService.insertLogs(orderid, JpOrderSimpleEnum.PLACE_ORDER.intKey(), session);
			}
		}
		TOrderJpEntity orderjpinfo = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderid.longValue());
		//创建客户历史信息实体
		//保存客户信息
		if (!Util.isEmpty(form.getCustomerType())) {
			if (form.getCustomerType().equals(CustomerTypeEnum.ZHIKE.intKey())) {
				if (!Util.isEmpty(form.getZhikecustomid())) {
					TCustomerEntity customer = dbDao.fetch(TCustomerEntity.class, form.getZhikecustomid().longValue());
					customer.setName(form.getCompName2());
					customer.setShortname(form.getComShortName2());
					customer.setPayType(form.getPayType());
					customer.setSource(CustomerTypeEnum.ZHIKE.intKey());
					dbDao.update(customer);
				} else {
					TCustomerEntity customer = new TCustomerEntity();
					customer.setName(form.getCompName2());
					customer.setShortname(form.getComShortName2());
					customer.setPayType(form.getPayType());
					customer.setSource(CustomerTypeEnum.ZHIKE.intKey());
					TCustomerEntity insertcustom = dbDao.insert(customer);
					List<TCustomerVisainfoEntity> customervisas = Lists.newArrayList();
					for (MainSaleVisaTypeEnum mainSaleVisaTypeEnum : MainSaleVisaTypeEnum.values()) {
						TCustomerVisainfoEntity cusvisa = new TCustomerVisainfoEntity();
						cusvisa.setVisatype(mainSaleVisaTypeEnum.key());
						cusvisa.setCustomerid(insertcustom.getId());
						if (!Util.isEmpty(form.getVisatype()) && form.getVisatype().equals(mainSaleVisaTypeEnum.key())) {
							cusvisa.setAmount(form.getAmount());
						}
						customervisas.add(cusvisa);
					}
					//dbDao.query(TCustomerVisainfoEntity.class, Cnd.where("customerid", "=", insertcustom), null);
					orderinfo.setCustomerId(insertcustom.getId());
				}
			} else {
				orderinfo.setCustomerId(form.getCustomerid());
			}
		}
		orderinfo.setCityId(form.getCityid());
		orderinfo.setUrgentType(form.getUrgentType());
		orderinfo.setUrgentDay(form.getUrgentDay());
		orderinfo.setSendVisaDate(form.getSendvisadate());
		/*String sendvisadate = form.getSendvisadate();
		if (!Util.isEmpty(sendvisadate)) {
			String[] split = sendvisadate.split(" - ");
			orderinfo.setSendVisaDate(DateUtil.string2Date(split[0], DateUtil.FORMAT_YYYY_MM_DD));
			orderinfo.setSendvisaenddate(DateUtil.string2Date(split[1], DateUtil.FORMAT_YYYY_MM_DD));
		} else {
			orderinfo.setSendVisaDate(null);
			orderinfo.setSendvisaenddate(null);
		}*/
		orderinfo.setOutVisaDate(form.getOutvisadate());
		/*String outvisadate = form.getOutvisadate();
		if (!Util.isEmpty(outvisadate)) {
			String[] split = outvisadate.split(" - ");
			orderinfo.setOutVisaDate(DateUtil.string2Date(split[0], DateUtil.FORMAT_YYYY_MM_DD));
			orderinfo.setOutvisaenddate(DateUtil.string2Date(split[1], DateUtil.FORMAT_YYYY_MM_DD));
		} else {
			orderinfo.setOutVisaDate(null);
			orderinfo.setOutvisaenddate(null);
		}*/
		orderinfo.setGoTripDate(form.getGoDate());
		orderinfo.setStayDay(form.getStayday());
		orderinfo.setBackTripDate(form.getReturnDate());
		orderinfo.setSendVisaNum(form.getSendvisanum());

		orderinfo.setIsDisabled(IsYesOrNoEnum.NO.intKey());
		orderinfo.setUpdateTime(new Date());

		dbDao.update(orderinfo);
		//更新日本订单表
		orderjpinfo.setVisaType(form.getVisatype());
		orderjpinfo.setAmount(form.getAmount());
		dbDao.update(orderjpinfo);
		//保存或者更新客户历史表-----------------------------------------------------------------
		//		TCustomerHisEntity customerHis = dbDao.fetch(TCustomerHisEntity.class, Cnd.where("orderId", "=", orderid));
		//		if (Util.isEmpty(customerHis)) {
		//			customerHis = new TCustomerHisEntity();
		//
		//		}
		//		//用户id
		//		customerHis.setUserId(loginUser.getId());
		//		//订单Id
		//		customerHis.setOrderId(orderid);
		//		//公司Id
		//		customerHis.setCompId(loginUser.getComId());
		//		//公司名称
		//		customerHis.setName(form.getCompName());
		//		//公司简称
		//		customerHis.setShortname(form.getComShortName());
		//		//客户来源 CustomerTypeEnum
		//		Integer customerType = form.getCustomerType();
		//		for (CustomerTypeEnum pmEnum : CustomerTypeEnum.values())
		//			if (!Util.isEmpty(customerType) && customerType == pmEnum.intKey()) {
		//				customerHis.setSource(pmEnum.value());
		//				break;
		//			}
		//		//支付方式 MainSalePayTypeEnum
		//		Integer payType = form.getPayType();
		//		for (MainSalePayTypeEnum pmEnum : MainSalePayTypeEnum.values())
		//			if (!Util.isEmpty(payType) && payType == pmEnum.intKey()) {
		//				customerHis.setPayType(pmEnum.value());
		//				break;
		//			}
		//		//是否来自客户信息  是（直客）
		//		if (customerType == 4) {
		//			customerHis.setIsCustomerAdd("是");
		//		} else {
		//			customerHis.setIsCustomerAdd("否");
		//		}
		//		//手机
		//		customerHis.setMobile(loginUser.getMobile());
		//		//email
		//		customerHis.setEmail(loginUser.getEmail());
		//		if (!Util.isEmpty(customerHis.getId())) {
		//			customerHis.setUpdateTime(new Date());
		//			dbDao.update(customerHis);
		//		} else {
		//			customerHis.setCreateTime(new Date());
		//			dbDao.insert(customerHis);
		//		}

		//出行信息---------------------------------------------------------------------------
		TOrderTripJpEntity orderjptrip = dbDao.fetch(TOrderTripJpEntity.class, Cnd.where("orderId", "=", orderjpid));
		if (Util.isEmpty(orderjptrip)) {
			orderjptrip = new TOrderTripJpEntity();
		}
		orderjptrip.setTripPurpose(form.getTripPurpose());
		orderjptrip.setTripType(form.getTriptype());
		orderjptrip.setGoDate(form.getGoDate());
		orderjptrip.setReturnDate(form.getReturnDate());
		orderjptrip.setGoDepartureCity(form.getGoDepartureCity());
		orderjptrip.setGoArrivedCity(form.getGoArrivedCity());
		orderjptrip.setGoFlightNum(form.getGoFlightNum());
		orderjptrip.setReturnDepartureCity(form.getReturnDepartureCity());
		orderjptrip.setReturnArrivedCity(form.getReturnArrivedCity());
		orderjptrip.setReturnFlightNum(form.getReturnFlightNum());
		orderjptrip.setOrderId(orderjpid);
		if (!Util.isEmpty(orderjptrip.getId())) {
			dbDao.update(orderjptrip);
		} else {
			dbDao.insert(orderjptrip);
		}
		//		//出行历史信息
		//		TOrderTripHisJpEntity tOrderTripHis = dbDao.fetch(TOrderTripHisJpEntity.class,
		//				Cnd.where("orderId", "=", orderid));
		//		if (Util.isEmpty(tOrderTripHis)) {
		//			tOrderTripHis = new TOrderTripHisJpEntity();
		//		}
		//		//订单Id
		//		tOrderTripHis.setOrderId(orderid);
		//		//行程类型
		//		if (form.getTriptype() == 1) {
		//			tOrderTripHis.setTripType("往返");
		//		} else {
		//			tOrderTripHis.setTripType("多程");
		//		}
		//		//出行目的
		//		tOrderTripHis.setTripPurpose(form.getTripPurpose());
		//		//出发日期 去程
		//		tOrderTripHis.setGoDate(form.getGoDate());
		//		//出发城市 去程
		//		TCityEntity goDepartureCity = dbDao.fetch(TCityEntity.class, Cnd.where("id", "", form.getGoDepartureCity()));
		//		tOrderTripHis.setGoDepartureCity(goDepartureCity.getCity());
		//		//抵达城市 去程
		//		TCityEntity goArrivedCity = dbDao.fetch(TCityEntity.class, Cnd.where("id", "", form.getGoArrivedCity()));
		//		tOrderTripHis.setGoArrivedCity(goArrivedCity.getCity());
		//		//航班号
		//		tOrderTripHis.setGoFlightNum(form.getGoFlightNum());
		//		//出发日期
		//		tOrderTripHis.setReturnDate(form.getReturnDate());
		//		//出发城市 返程
		//		TCityEntity returnDepartureCity = dbDao.fetch(TCityEntity.class,
		//				Cnd.where("id", "", form.getReturnDepartureCity()));
		//		tOrderTripHis.setReturnDepartureCity(returnDepartureCity.getCity());
		//		//返回城市 返程
		//		TCityEntity returnArrivedCity = dbDao
		//				.fetch(TCityEntity.class, Cnd.where("id", "", form.getReturnArrivedCity()));
		//		tOrderTripHis.setReturnArrivedCity(returnArrivedCity.getCity());
		//		//航班号 返程
		//		tOrderTripHis.setReturnFlightNum(form.getReturnFlightNum());
		//		//新增 or 更新
		//		if (!Util.isEmpty(tOrderTripHis.getId())) {
		//			tOrderTripHis.setUpdateTime(new Date());
		//			dbDao.update(tOrderTripHis);
		//		} else {
		//			tOrderTripHis.setCreateTime(new Date());
		//			dbDao.insert(tOrderTripHis);
		//		}

		//消息通知
		/*try {
			visaInfoWSHandler.broadcast(new TextMessage(""));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return null;
	}

	/**
	 * 跳转到编辑订单页面
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param request
	 * @param orderid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object editOrder(HttpServletRequest request, Integer orderid) {

		Map<String, Object> result = Maps.newHashMap();
		TOrderJpEntity orderjpinfo = dbDao.fetch(TOrderJpEntity.class, orderid.longValue());
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderjpinfo.getOrderId().longValue());
		TCustomerEntity customerinfo = new TCustomerEntity();
		if (!Util.isEmpty(orderinfo.getCustomerId())) {
			customerinfo = dbDao.fetch(TCustomerEntity.class, orderinfo.getCustomerId().longValue());
		}
		TOrderTripJpEntity tripinfo = dbDao.fetch(TOrderTripJpEntity.class, Cnd.where("orderid", "=", orderid));
		List<TCityEntity> citylist = Lists.newArrayList();
		List<TFlightEntity> flightlist = Lists.newArrayList();
		List<ResultflyEntity> gotripAirlineSelect = Lists.newArrayList();
		List<ResultflyEntity> returntripAirlineSelect = Lists.newArrayList();

		if (!Util.isEmpty(tripinfo)) {
			/*FlightSelectParam param = new FlightSelectParam();
			param.setGocity(tripinfo.getGoDepartureCity().longValue());
			param.setArrivecity(tripinfo.getGoArrivedCity().longValue());
			param.setDate("2018-06-19");
			param.setFlight("");
			gotripAirlineSelect = tripAirlineService.getTripAirlineSelect(param);

			param.setGocity(tripinfo.getReturnDepartureCity().longValue());
			param.setArrivecity(tripinfo.getReturnArrivedCity().longValue());
			param.setDate("2018-06-19");
			param.setFlight("");
			returntripAirlineSelect = tripAirlineService.getTripAirlineSelect(param);*/
			Integer[] cityids = { tripinfo.getGoDepartureCity(), tripinfo.getGoArrivedCity(),
					tripinfo.getReturnDepartureCity(), tripinfo.getReturnArrivedCity() };
			citylist = dbDao.query(TCityEntity.class, Cnd.where("id", "in", cityids), null);
			String[] flightnums = { tripinfo.getGoFlightNum(), tripinfo.getReturnFlightNum() };
			List<TFlightEntity> flightlistpre = dbDao.query(TFlightEntity.class,
					Cnd.where("flightnum", "in", flightnums), null);
			for (TFlightEntity tFlightEntity : flightlistpre) {
				if (!flightlist.contains(tFlightEntity)) {
					flightlist.add(tFlightEntity);
				}
			}
		}
		String orderstatus = "";
		for (JPOrderStatusEnum orderenum : JPOrderStatusEnum.values()) {
			if (orderenum.intKey() == orderinfo.getStatus()) {
				orderstatus = orderenum.value();
				break;
			}
		}
		DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		//送签时间
		String sendvisadatestr = "";
		Date sendVisaDate = orderinfo.getSendVisaDate();
		Date sendvisaenddate = orderinfo.getSendvisaenddate();
		if (!Util.isEmpty(sendVisaDate) && !Util.isEmpty(sendvisaenddate)) {
			String sendvisastr = format.format(sendVisaDate);
			String sendvisaendstr = format.format(sendvisaenddate);
			sendvisadatestr = sendvisastr + " - " + sendvisaendstr;
		}
		String outvisadatestr = "";
		Date outVisaDate = orderinfo.getOutVisaDate();
		Date outvisaenddate = orderinfo.getOutvisaenddate();
		if (!Util.isEmpty(outVisaDate) && !Util.isEmpty(outvisaenddate)) {
			String outvisastr = format.format(outVisaDate);
			String outvisaendstr = format.format(outvisaenddate);
			outvisadatestr = outvisastr + " - " + outvisaendstr;
		}
		result.put("sendvisadatestr", sendvisadatestr);
		result.put("outvisadatestr", outvisadatestr);
		result.put("orderstatus", orderstatus);
		result.put("flightlist", flightlist);
		result.put("collarAreaEnum", EnumUtil.enum2(CollarAreaEnum.class));
		result.put("customerTypeEnum", EnumUtil.enum2(CustomerTypeEnum.class));
		result.put("mainSaleUrgentEnum", EnumUtil.enum2(MainSaleUrgentEnum.class));
		result.put("mainSaleUrgentTimeEnum", EnumUtil.enum2(MainSaleUrgentTimeEnum.class));
		result.put("mainSaleTripTypeEnum", EnumUtil.enum2(MainSaleTripTypeEnum.class));
		result.put("mainSalePayTypeEnum", EnumUtil.enum2(MainSalePayTypeEnum.class));
		result.put("mainSaleVisaTypeEnum", EnumUtil.enum2(SimpleVisaTypeEnum.class));
		result.put("citylist", citylist);
		result.put("orderjpinfo", orderjpinfo);
		result.put("orderinfo", orderinfo);
		result.put("tripinfo", tripinfo);
		result.put("customerinfo", customerinfo);

		result.put("returntripAirlineSelect", returntripAirlineSelect);
		result.put("gotripAirlineSelect", gotripAirlineSelect);

		return result;
	}

	/**
	 *获取客户信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param customerid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getCustomerinfoById(Long customerid) {
		TCustomerEntity customerinfo = dbDao.fetch(TCustomerEntity.class, customerid);
		return customerinfo;
	}

	public Object disabled(int orderid) {
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid);
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
		orderEntity.setIsDisabled(IsYesOrNoEnum.YES.intKey());
		orderEntity.setUpdateTime(new Date());
		dbDao.update(orderEntity);
		return null;
	}

	public Object undisabled(int orderid) {
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid);
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
		orderEntity.setIsDisabled(IsYesOrNoEnum.NO.intKey());
		orderEntity.setUpdateTime(new Date());
		dbDao.update(orderEntity);
		return null;
	}

	/**
	 * 获取客户金额
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param customerid
	 * @param visatype
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getCustomerAmount(Integer customerid, Integer visatype) {
		TCustomerVisainfoEntity customerVisa = dbDao.fetch(TCustomerVisainfoEntity.class,
				Cnd.where("customerid", "=", customerid).and("visatype", "=", visatype));
		return customerVisa;
	}

	/**
	 * 保存游客基本信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param request
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveApplicantInfo(HttpServletRequest request, TApplicantForm form) {
		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Map<String, Object> result = Maps.newHashMap();
		Integer orderjpid = form.getOrderid();
		Integer orderid = null;
		TApplicantEntity applicant = new TApplicantEntity();
		TApplicantOrderJpEntity applicantjp = new TApplicantOrderJpEntity();
		applicantjp.setMainRelation(form.getMainRelation());
		//修改
		if (!Util.isEmpty(form.getId())) {
			applicant = dbDao.fetch(TApplicantEntity.class, form.getId().longValue());
			applicantjp = dbDao.fetch(TApplicantOrderJpEntity.class, Cnd.where("applicantId", "=", applicant.getId()));
			result.put("applicantjpid", applicantjp.getId());
			result.put("applicantid", applicant.getId());
			applicantjp.setMainRelation(form.getMainRelation());
			dbDao.update(applicantjp);
		}
		applicant.setOpId(loginUser.getId());
		applicant.setIsSameInfo(IsYesOrNoEnum.YES.intKey());
		applicant.setIsPrompted(IsYesOrNoEnum.NO.intKey());
		applicant.setAddress(form.getAddress());
		applicant.setCardId(form.getCardId());
		applicant.setCity(form.getCity());
		applicant.setDetailedAddress(form.getDetailedAddress());
		applicant.setEmail(form.getEmail());
		if (!Util.isEmpty(form.getOtherLastNameEn())) {
			applicant.setOtherLastNameEn(form.getOtherLastNameEn().substring(1));
		}
		if (!Util.isEmpty(form.getOtherFirstNameEn())) {
			applicant.setOtherFirstNameEn(form.getOtherFirstNameEn().substring(1));
		}
		applicant.setNationality(form.getNationality());
		applicant.setHasOtherName(form.getHasOtherName());
		applicant.setHasOtherNationality(form.getHasOtherNationality());
		applicant.setOtherFirstName(form.getOtherFirstName());
		applicant.setOtherLastName(form.getOtherLastName());
		applicant.setAddressIsSameWithCard(form.getAddressIsSameWithCard());
		applicant.setCardProvince(form.getCardProvince());
		applicant.setCardCity(form.getCardCity());
		applicant.setIssueOrganization(form.getIssueOrganization());
		applicant.setNation(form.getNation());
		applicant.setProvince(form.getProvince());
		//applicant.setSex(form.getSex());
		applicant.setTelephone(form.getTelephone());
		applicant.setValidEndDate(form.getValidEndDate());
		applicant.setValidStartDate(form.getValidStartDate());
		applicant.setCardFront(form.getCardFront());
		applicant.setCardBack(form.getCardBack());
		applicant.setStatus(TrialApplicantStatusEnum.FIRSTTRIAL.intKey());
		applicant.setCreateTime(new Date());
		applicant.setEmergencyLinkman(form.getEmergencyLinkman());
		applicant.setEmergencyTelephone(form.getEmergencyTelephone());
		applicant.setEmergencyaddress(form.getEmergencyaddress());
		//游客登录
		ApplicantUser applicantUser = new ApplicantUser();
		applicantUser.setMobile(applicant.getTelephone());
		applicantUser.setOpid(applicant.getOpId());
		applicantUser.setPassword("000000");
		applicantUser.setUsername(applicant.getFirstName() + applicant.getLastName());
		/*if (!Util.isEmpty(applicant.getTelephone())) {
			TUserEntity userEntity = dbDao.fetch(TUserEntity.class, Cnd.where("simplemobile", "=", applicant.getTelephone())
					.and("userType", "=", UserLoginEnum.TOURIST_IDENTITY.intKey()));
			if (Util.isEmpty(userEntity)) {
				TUserEntity tUserEntity = userViewService.addApplicantUser(applicantUser);
				applicant.setUserId(tUserEntity.getId());
			} else {
				userEntity.setName(applicantUser.getUsername());
				userEntity.setMobile(applicant.getTelephone());
				userEntity.setPassword(applicantUser.getPassword());
				userEntity.setOpId(applicantUser.getOpid());
				userEntity.setUpdateTime(new Date());
				applicant.setUserId(userEntity.getId());
				dbDao.update(userEntity);
			}
		}*/
		Integer applicantid = form.getId();
		if (!Util.isEmpty(form.getId())) {
			dbDao.update(applicant);

		} else {

			TApplicantEntity insertapplicant = dbDao.insert(applicant);
			applicantid = insertapplicant.getId();
			result.put("applicantid", applicantid);
			//如果订单不存在，则先创建订单
			if (Util.isEmpty(form.getOrderid())) {
				Map<String, Integer> generrateorder = generrateorder(loginUser, loginCompany);
				orderid = generrateorder.get("orderid");
				orderjpid = generrateorder.get("orderjpid");
			} else {
				TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
				orderid = orderjp.getOrderId();
				applicantjp.setOrderId(orderid);
			}
			//新增日本订单基本信息
			applicantjp.setMainRelation(form.getMainRelation());
			dbDao.insert(applicantjp);
			applicantjp.setOrderId(orderjpid);
			applicantjp.setApplicantId(applicantid);
			applicantjp.setBaseIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setPassIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setVisaIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setMainRelation(form.getMainRelation());
			TApplicantOrderJpEntity insertappjp = dbDao.insert(applicantjp);
			result.put("applicantjpid", insertappjp.getId());
			//日本工作信息
			TApplicantWorkJpEntity workJp = new TApplicantWorkJpEntity();
			workJp.setApplicantId(insertappjp.getId());
			workJp.setCreateTime(new Date());
			workJp.setOpId(loginUser.getId());
			dbDao.insert(workJp);
			//护照信息
			TApplicantPassportEntity passport = new TApplicantPassportEntity();
			passport.setSex(form.getSex());
			passport.setFirstName(form.getFirstName());
			passport.setLastName(form.getLastName());
			if (!Util.isEmpty(form.getFirstNameEn())) {
				passport.setFirstNameEn(form.getFirstNameEn().substring(1));
			}
			if (!Util.isEmpty(form.getLastNameEn())) {
				passport.setLastNameEn(form.getLastNameEn().substring(1));
			}
			passport.setIssuedOrganization("公安部出入境管理局");
			passport.setIssuedOrganizationEn("MPS Exit&Entry Adiministration");
			passport.setApplicantId(applicantid);
			dbDao.insert(passport);
		}
		result.put("orderjpid", orderjpid);
		//创建日本申请人 信息
		return result;
	}

	/**
	 * 跳转到护照信息页面
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param applicantid
	 * @param orderid
	 * @param request 
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object passportInfo(Integer applicantid, Integer orderid, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String, Object> result = Maps.newHashMap();
		result.put("orderid", orderid);
		result.put("applicantid", applicantid);
		TApplicantPassportEntity passport = dbDao.fetch(TApplicantPassportEntity.class,
				Cnd.where("applicantId", "=", applicantid));
		if (!Util.isEmpty(passport.getIssuedPlaceEn())) {
			if (!passport.getIssuedPlaceEn().startsWith("/")) {
				passport.setIssuedPlaceEn("/" + passport.getIssuedPlaceEn());
			}
		}
		if (!Util.isEmpty(passport.getBirthAddressEn())) {
			if (!passport.getBirthAddressEn().startsWith("/")) {
				passport.setBirthAddressEn("/" + passport.getBirthAddressEn());
			}
		}
		result.put("passport", passport);
		if (!Util.isEmpty(passport.getFirstNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(passport.getFirstNameEn());
			result.put("firstNameEn", sb.toString());
		}
		if (!Util.isEmpty(passport.getLastNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(passport.getLastNameEn());
			result.put("lastNameEn", sb.toString());
		}
		//格式化日期
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		if (!Util.isEmpty(passport.getBirthday())) {
			Date birthday = passport.getBirthday();
			result.put("birthday", sdf.format(birthday));
		}
		if (!Util.isEmpty(passport.getIssuedDate())) {
			Date issuedDate = passport.getIssuedDate();
			result.put("issuedDate", sdf.format(issuedDate));
		}
		if (!Util.isEmpty(passport.getValidEndDate())) {
			Date validEndDate = passport.getValidEndDate();
			result.put("validEndDate", sdf.format(validEndDate));
		}
		result.put("passportType", EnumUtil.enum2(PassportTypeEnum.class));
		//所访问的ip地址
		String localAddr = request.getServerName();
		result.put("localAddr", localAddr);
		//所访问的端口
		int localPort = request.getServerPort();
		result.put("localPort", localPort);
		//websocket地址
		result.put("websocketaddr", SIMPLE_WEBSOCKET_ADDR);
		//生成二维码的URL
		String passporturl = "http://" + localAddr + ":" + localPort + "/simplemobile/passport.html?applicantid="
				+ applicantid + "&orderid=" + orderid;
		//生成二维码
		String qrCode = qrCodeService.encodeQrCode(request, passporturl);
		result.put("qrCode", qrCode);
		return result;
	}

	public Object toNewfilminginfo(int applyid, int orderid, HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("orderid", orderid);
		String applyurl = " ";
		String passurl = " ";
		if (!Util.isEmpty(applyid)) {
			result.put("applyid", applyid);
			TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyid);
			if (!Util.isEmpty(apply.getCardFront())) {
				applyurl = apply.getCardFront();
			}

			TApplicantPassportEntity passport = dbDao.fetch(TApplicantPassportEntity.class,
					Cnd.where("applicantId", "=", applyid));
			if (!Util.isEmpty(passport.getPassportUrl())) {
				passurl = passport.getPassportUrl();
			}

		}
		result.put("applyurl", applyurl);
		result.put("passurl", passurl);
		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();
		result.put("userdi", userid);
		//所访问的ip地址
		String localAddr = request.getServerName();
		result.put("localAddr", localAddr);
		//所访问的端口
		int localPort = request.getServerPort();
		result.put("localPort", localPort);
		//websocket地址
		result.put("websocketaddr", SIMPLE_WEBSOCKET_ADDR);
		//生成二维码
		if (Util.isEmpty(applyid)) {
			applyid = 0;
		}
		if (Util.isEmpty(orderid)) {
			orderid = 0;
		}
		String qrCode = dataUpload(applyid, orderid, request);
		result.put("qrCode", qrCode);
		return result;
	}

	public Object basicInfo(Integer applicantid, Integer orderid, HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("orderid", orderid);
		result.put("applicantid", applicantid);

		//护照信息
		TApplicantPassportEntity passport = dbDao.fetch(TApplicantPassportEntity.class,
				Cnd.where("applicantId", "=", applicantid));
		if (!Util.isEmpty(passport.getIssuedPlaceEn())) {
			if (!passport.getIssuedPlaceEn().startsWith("/")) {
				passport.setIssuedPlaceEn("/" + passport.getIssuedPlaceEn());
			}
		}
		if (!Util.isEmpty(passport.getBirthAddressEn())) {
			if (!passport.getBirthAddressEn().startsWith("/")) {
				passport.setBirthAddressEn("/" + passport.getBirthAddressEn());
			}
		}
		result.put("passport", passport);
		if (!Util.isEmpty(passport.getFirstNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(passport.getFirstNameEn());
			result.put("firstNameEn", sb.toString());
		}
		if (!Util.isEmpty(passport.getLastNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(passport.getLastNameEn());
			result.put("lastNameEn", sb.toString());
		}
		//格式化日期
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		if (!Util.isEmpty(passport.getBirthday())) {
			Date birthday = passport.getBirthday();
			result.put("birthday", sdf.format(birthday));
		}
		if (!Util.isEmpty(passport.getIssuedDate())) {
			Date issuedDate = passport.getIssuedDate();
			result.put("issuedDate", sdf.format(issuedDate));
		}
		if (!Util.isEmpty(passport.getValidEndDate())) {
			Date validEndDate = passport.getValidEndDate();
			result.put("validEndDate", sdf.format(validEndDate));
		}
		result.put("passportType", EnumUtil.enum2(PassportTypeEnum.class));

		//基本信息
		TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, applicantid.longValue());
		result.put("applicant", applicant);
		TApplicantOrderJpEntity orderjp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantid));
		result.put("orderjp", orderjp);
		if (!Util.isEmpty(applicant.getBirthday())) {
			Date birthday = applicant.getBirthday();
			String birthdayStr = sdf.format(birthday);
			result.put("birthday", birthdayStr);
		}
		if (!Util.isEmpty(applicant.getValidStartDate())) {
			Date validStartDate = applicant.getValidStartDate();
			String validStartDateStr = sdf.format(validStartDate);
			result.put("validStartDate", validStartDateStr);
		}
		if (!Util.isEmpty(applicant.getValidEndDate())) {
			Date validEndDate = applicant.getValidEndDate();
			String validEndDateStr = sdf.format(validEndDate);
			result.put("validEndDate", validEndDateStr);
		}

		if (!Util.isEmpty(applicant.getFirstNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicant.getFirstNameEn());
			result.put("firstNameEn", sb.toString());
		}
		if (!Util.isEmpty(applicant.getOtherFirstNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicant.getOtherFirstNameEn());
			result.put("otherFirstNameEn", sb.toString());
		}

		if (!Util.isEmpty(applicant.getLastNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicant.getLastNameEn());
			result.put("lastNameEn", sb.toString());
		}

		if (!Util.isEmpty(applicant.getOtherLastNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicant.getOtherLastNameEn());
			result.put("otherLastNameEn", sb.toString());
		}
		//婚姻状况下拉选项
		result.put("marryStatusEnum", EnumUtil.enum2(MarryStatusEnum.class));

		return result;
	}

	public Object saveBasicinfo(BasicinfoForm form, HttpServletRequest request) {

		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		//基本信息
		TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, form.getApplicantid().longValue());
		//日本申请人信息
		TApplicantOrderJpEntity applicantjp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicant.getId()));
		applicantjp.setMainRelation(form.getMainRelation());
		dbDao.update(applicantjp);

		TApplicantWorkJpEntity workjp = dbDao.fetch(TApplicantWorkJpEntity.class,
				Cnd.where("applicantId", "=", applicantjp.getId()));
		//根据出生日期设置签证信息的工作类型
		if (!Util.isEmpty(form.getBirthday()) && Util.isEmpty(workjp.getCareerStatus())) {
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
			int years = orderJpViewService.accordingbirtday(sdf.format(form.getBirthday()));
			if (years >= 55) {
				workjp.setCareerStatus(2);
			}
			if (years <= 5) {
				workjp.setCareerStatus(5);
			}
			dbDao.update(workjp);
		}

		applicant.setOpId(loginUser.getId());
		applicant.setIsSameInfo(IsYesOrNoEnum.YES.intKey());
		applicant.setIsPrompted(IsYesOrNoEnum.NO.intKey());
		applicant.setMarryurltype(form.getIsnamedisabled());
		applicant.setAddress(form.getAddress());
		applicant.setCardId(form.getCardId());
		if (Util.isEmpty(form.getJuzhudicheckbox())) {
			applicant.setAddressIsSameWithCard(IsYesOrNoEnum.NO.intKey());
		} else {
			applicant.setAddressIsSameWithCard(form.getJuzhudicheckbox());
		}
		applicant.setCity(form.getCity());
		applicant.setDetailedAddress(form.getDetailedAddress());
		applicant.setEmail(form.getEmail());
		if (!Util.isEmpty(form.getOtherLastNameEn())) {
			applicant.setOtherLastNameEn(form.getOtherLastNameEn().substring(1));
		}
		if (!Util.isEmpty(form.getOtherFirstNameEn())) {
			applicant.setOtherFirstNameEn(form.getOtherFirstNameEn().substring(1));
		}
		applicant.setNationality(form.getNationality());
		applicant.setHasOtherName(form.getHasOtherName());
		applicant.setHasOtherNationality(form.getHasOtherNationality());
		applicant.setOtherFirstName(form.getOtherFirstName());
		applicant.setOtherLastName(form.getOtherLastName());
		applicant.setCardProvince(form.getCardProvince());
		applicant.setCardCity(form.getCardCity());
		applicant.setIssueOrganization(form.getIssueOrganization());
		applicant.setNation(form.getNation());
		applicant.setProvince(form.getProvince());
		applicant.setTelephone(form.getTelephone());
		applicant.setValidEndDate(form.getValidEndDate());
		applicant.setValidStartDate(form.getValidStartDate());
		//applicant.setCardFront(form.getCardFront());
		//applicant.setCardBack(form.getCardBack());
		applicant.setStatus(TrialApplicantStatusEnum.FIRSTTRIAL.intKey());
		applicant.setCreateTime(new Date());
		applicant.setEmergencyLinkman(form.getEmergencyLinkman());
		applicant.setEmergencyTelephone(form.getEmergencyTelephone());
		applicant.setEmergencyaddress(form.getEmergencyaddress());
		applicant.setMarryStatus(form.getMarryStatus());
		applicant.setFirstName(form.getFirstName());
		if (!Util.isEmpty(form.getLastName())) {
			applicant.setLastName(form.getLastName());
		} else {
			applicant.setLastName("");
		}
		if (!Util.isEmpty(form.getFirstNameEn())) {
			applicant.setFirstNameEn(form.getFirstNameEn().substring(1));
		} else {
			applicant.setFirstNameEn("");
		}
		if (!Util.isEmpty(form.getLastNameEn())) {
			applicant.setLastNameEn(form.getLastNameEn().substring(1));
		} else {
			applicant.setLastNameEn("");
		}
		applicant.setSex(form.getSex());
		applicant.setBirthday(form.getBirthday());

		dbDao.update(applicant);

		//护照信息
		TApplicantPassportEntity passport = dbDao.fetch(TApplicantPassportEntity.class,
				Cnd.where("applicantId", "=", form.getApplicantid()));
		passport.setOpId(loginUser.getId());

		passport.setFirstName(form.getFirstName());
		if (!Util.isEmpty(form.getFirstNameEn())) {
			passport.setFirstNameEn(form.getFirstNameEn().substring(1));
		}
		passport.setLastName(form.getLastName());
		if (!Util.isEmpty(form.getLastNameEn())) {
			passport.setLastNameEn(form.getLastNameEn().substring(1));
		}
		//passport.setPassportUrl(form.getPassportUrl());
		passport.setOCRline1(form.getOCRline1());
		passport.setOCRline2(form.getOCRline2());
		passport.setBirthAddress(form.getBirthAddress());
		passport.setBirthAddressEn(form.getBirthAddressEn());
		passport.setBirthday(form.getBirthday());
		passport.setFirstName(form.getFirstName());
		passport.setIssuedDate(form.getIssuedDate());
		passport.setIssuedOrganization(form.getIssuedOrganization());
		passport.setIssuedOrganizationEn(form.getIssuedOrganizationEn());
		passport.setIssuedPlace(form.getIssuedPlace());
		passport.setIssuedPlaceEn(form.getIssuedPlaceEn());
		if (!Util.isEmpty(form.getLastName())) {
			passport.setLastName(form.getLastName());
		} else {
			passport.setLastName("");
		}
		passport.setPassport(form.getPassport());
		passport.setSex(form.getSex());
		passport.setSexEn(form.getSexEn());
		passport.setType(form.getType());
		passport.setValidEndDate(form.getValidEndDate());
		passport.setValidStartDate(form.getValidStartDate());
		passport.setValidType(form.getValidType());
		passport.setUpdateTime(new Date());
		if (!Util.isEmpty(form.getFirstNameEn())) {
			passport.setFirstNameEn(form.getFirstNameEn().substring(1));
		} else {
			passport.setFirstNameEn("");
		}
		if (!Util.isEmpty(form.getLastNameEn())) {
			passport.setLastNameEn(form.getLastNameEn().substring(1));
		} else {
			passport.setLastNameEn("");
		}

		dbDao.update(passport);

		return null;
	}

	/**
	 * 保存护照信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveEditPassport(TApplicantPassportForm form, HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TApplicantPassportEntity passport = new TApplicantPassportEntity();
		/*if (isCNChar(form.getFirstNameEn()) || isCNChar(form.getLastNameEn())) {
			//输入非法
			result.put("msg", "姓名拼音不能包含中文");
			return result;

		}*/
		if (!Util.isEmpty(form.getId())) {
			passport = dbDao.fetch(TApplicantPassportEntity.class, form.getId().longValue());
			/*TApplicantOrderJpEntity applicantorder = dbDao.fetch(TApplicantOrderJpEntity.class, passport
					.getApplicantId().longValue());*/
			TApplicantOrderJpEntity applicantorder = dbDao.fetch(TApplicantOrderJpEntity.class,
					Cnd.where("applicantId", "=", passport.getApplicantId().longValue()));
			result.put("applicantjpid", applicantorder.getApplicantId());
			result.put("applicantid", applicantorder.getApplicantId());
			result.put("orderid", applicantorder.getOrderId());

		}
		//TApplicantPassportEntity passport = dbDao.fetch(TApplicantPassportEntity.class, form.getId().longValue());
		passport.setOpId(loginUser.getId());

		passport.setFirstName(form.getFirstName());
		if (!Util.isEmpty(form.getFirstNameEn())) {
			passport.setFirstNameEn(form.getFirstNameEn().substring(1));
		}
		passport.setLastName(form.getLastName());
		if (!Util.isEmpty(form.getLastNameEn())) {
			passport.setLastNameEn(form.getLastNameEn().substring(1));
		}
		passport.setPassportUrl(form.getPassportUrl());
		passport.setOCRline1(form.getOCRline1());
		passport.setOCRline2(form.getOCRline2());
		passport.setBirthAddress(form.getBirthAddress());
		passport.setBirthAddressEn(form.getBirthAddressEn());
		passport.setBirthday(form.getBirthday());
		//passport.setFirstName(form.getFirstName());
		//passport.setFirstNameEn(passportForm.getFirstNameEn().substring(1));
		passport.setIssuedDate(form.getIssuedDate());
		passport.setIssuedOrganization(form.getIssuedOrganization());
		passport.setIssuedOrganizationEn(form.getIssuedOrganizationEn());
		passport.setIssuedPlace(form.getIssuedPlace());
		passport.setIssuedPlaceEn(form.getIssuedPlaceEn());
		//passport.setLastName(form.getLastName());
		//passport.setLastNameEn(passportForm.getLastNameEn().substring(1));
		passport.setPassport(form.getPassport());
		passport.setSex(form.getSex());
		passport.setSexEn(form.getSexEn());
		passport.setType(form.getType());
		passport.setValidEndDate(form.getValidEndDate());
		passport.setValidStartDate(form.getValidStartDate());
		passport.setValidType(form.getValidType());
		passport.setUpdateTime(new Date());
		if (!Util.isEmpty(form.getId())) {
			dbDao.update(passport);
			TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, passport.getApplicantId().longValue());
			applicant.setFirstName(form.getFirstName());
			applicant.setLastName(form.getLastName());
			if (!Util.isEmpty(form.getFirstNameEn())) {
				applicant.setFirstNameEn(form.getFirstNameEn().substring(1));
			}
			if (!Util.isEmpty(form.getLastNameEn())) {
				applicant.setLastNameEn(form.getLastNameEn().substring(1));
			}
			applicant.setSex(form.getSex());
			applicant.setBirthday(form.getBirthday());
			dbDao.update(applicant);
		} else {
			Integer orderjpid = form.getOrderid();
			if (Util.isEmpty(orderjpid)) {
				Map<String, Integer> generrateorder = generrateorder(loginUser, loginCompany);
				orderjpid = generrateorder.get("orderjpid");
			}
			TApplicantEntity applicantEntity = new TApplicantEntity();
			applicantEntity.setFirstName(form.getFirstName());
			//applicantEntity.setFirstNameEn(form.getFirstNameEn());
			applicantEntity.setLastName(form.getLastName());
			if (!Util.isEmpty(form.getFirstNameEn())) {
				applicantEntity.setFirstNameEn(form.getFirstNameEn().substring(1));
			}
			if (!Util.isEmpty(form.getLastNameEn())) {
				applicantEntity.setLastNameEn(form.getLastNameEn().substring(1));
			}
			//applicantEntity.setLastNameEn(form.getLastNameEn());
			applicantEntity.setSex(form.getSex());
			applicantEntity.setBirthday(form.getBirthday());
			TApplicantEntity insertapplicant = dbDao.insert(applicantEntity);
			TApplicantOrderJpEntity applicantjp = new TApplicantOrderJpEntity();
			applicantjp.setApplicantId(insertapplicant.getId());
			applicantjp.setOrderId(orderjpid);
			applicantjp.setBaseIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setPassIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setVisaIsCompleted(IsYesOrNoEnum.NO.intKey());
			//设置主申请人信息
			List<TApplicantOrderJpEntity> orderapplicant = dbDao.query(TApplicantOrderJpEntity.class,
					Cnd.where("orderId", "=", orderjpid), null);
			if (!Util.isEmpty(orderapplicant) && orderapplicant.size() >= 1) {

				applicantjp.setIsMainApplicant(IsYesOrNoEnum.NO.intKey());
			} else {
				//设置为主申请人
				applicantjp.setIsMainApplicant(IsYesOrNoEnum.YES.intKey());
				insertapplicant.setMainId(insertapplicant.getId());
				dbDao.update(insertapplicant);
			}
			TApplicantOrderJpEntity insertappjp = dbDao.insert(applicantjp);
			TApplicantWorkJpEntity workJp = new TApplicantWorkJpEntity();
			workJp.setApplicantId(insertappjp.getId());
			workJp.setCreateTime(new Date());
			workJp.setOpId(loginUser.getId());
			dbDao.insert(workJp);
			passport.setApplicantId(insertapplicant.getId());
			dbDao.insert(passport);
			TApplicantVisaOtherInfoEntity visaother = new TApplicantVisaOtherInfoEntity();
			visaother.setApplicantid(insertappjp.getId());
			visaother.setHotelname("参照'赴日予定表'");
			visaother.setVouchname("参照'身元保证书'");
			visaother.setInvitename("同上");
			visaother.setTraveladvice("推荐");
			dbDao.insert(visaother);
			result.put("applicantjpid", applicantjp.getApplicantId());
			result.put("applicantid", applicantjp.getApplicantId());
			result.put("orderid", applicantjp.getOrderId());

		}
		//保存历史信息
		//		savaOrUpdatePassport(form, request);
		//int update = dbDao.update(passport);
		return result;
	}

	public static boolean isCNChar(String s) {
		boolean booleanValue = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c > 128) {
				booleanValue = true;
				break;
			}
		}
		return booleanValue;
	}

	public boolean isChineseStr(String str) {
		String reg = "[\\u4e00-\\u9fa5]+";
		boolean isChinese = str.matches(reg);
		return isChinese;
	}

	/***
	 * 新增or更新申请人护照信息
	 * 
	 * 
	 * 
	 */
	//	public void savaOrUpdatePassport(TApplicantPassportForm form, HttpServletRequest request) {
	//		HttpSession session = request.getSession();
	//		TUserEntity loginUser = LoginUtil.getLoginUser(session);
	//		//根据日本订单Id查询订单Id
	//		Integer orderid = form.getOrderid();
	//		TOrderJpEntity orderJp = dbDao.fetch(TOrderJpEntity.class, Cnd.where("id", "=", orderid));
	//		//根据订单Id查询申请人护照信息历史信息
	//		TApplicantPassportHisEntity passportHis = dbDao.fetch(TApplicantPassportHisEntity.class,
	//				Cnd.where("orderId", "=", orderJp.getId()));
	//		//判断是否为空
	//		if (Util.isEmpty(passportHis)) {
	//			passportHis = new TApplicantPassportHisEntity();
	//		}
	//		//订单Id
	//		passportHis.setOrderId(orderJp.getId());
	//		//姓
	//		passportHis.setFirstName(form.getFirstName());
	//		//姓(拼音)
	//		if (!Util.isEmpty(form.getFirstNameEn())) {
	//			passportHis.setFirstNameEn(form.getFirstNameEn().substring(1));
	//		}
	//		//名
	//		passportHis.setLastName(form.getLastName());
	//		//名(拼音)
	//		if (!Util.isEmpty(form.getLastNameEn())) {
	//			passportHis.setLastNameEn(form.getLastNameEn().substring(1));
	//		}
	//		//护照类型
	//		passportHis.setType(form.getType());
	//		//护照号
	//		passportHis.setPassport(form.getPassport());
	//		//性别
	//		passportHis.setSex(form.getSex());
	//		//性别(拼音)
	//		passportHis.setSexEn(form.getSexEn());
	//		//出生地点
	//		passportHis.setBirthAddress(form.getBirthAddress());
	//		//出生地点(拼音)
	//		passportHis.setBirthAddressEn(form.getBirthAddressEn());
	//		//出生日期
	//		passportHis.setBirthday(form.getBirthday());
	//		//签发地点
	//		passportHis.setIssuedPlace(form.getIssuedPlace());
	//		//签发地点(拼音)
	//		passportHis.setIssuedPlaceEn(form.getIssuedPlaceEn());
	//		//签发日期
	//		passportHis.setIssuedDate(form.getIssuedDate());
	//		//有效期始 没用
	//		passportHis.setValidStartDate(form.getValidStartDate());
	//		//有效期至
	//		passportHis.setValidEndDate(form.getValidEndDate());
	//		//有效类型
	//		if (!Util.isEmpty(form.getValidType())) {
	//			for (IssueValidityEnum issueValidityEnum : IssueValidityEnum.values()) {
	//				if (!Util.isEmpty(form.getValidType()) && form.getValidType().equals(issueValidityEnum.key())) {
	//					passportHis.setValidType(issueValidityEnum.value());
	//				}
	//			}
	//		}
	//		//签发机关
	//		passportHis.setIssuedOrganization("公安部出入境管理局");
	//		//签发机关(拼音)
	//		passportHis.setIssuedOrganizationEn(form.getIssuedOrganizationEn());
	//		//护照地址
	//		passportHis.setPassportUrl(form.getPassportUrl());
	//		//操作人Id
	//		passportHis.setOpId(loginUser.getId());
	//		//OCR识别码第一行
	//		passportHis.setOCRline1(form.getOCRline1());
	//		//OCR识别码第二行
	//		passportHis.setOCRline2(form.getOCRline2());
	//		//新增or更新
	//		if (!Util.isEmpty(passportHis.getId())) {
	//			passportHis.setUpdateTime(new Date());
	//			dbDao.update(passportHis);
	//		} else {
	//			passportHis.setCreateTime(new Date());
	//			dbDao.insert(passportHis);
	//		}
	//	}

	/**
	 * 跳转到编辑基本信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param applicantid
	 * @param orderid
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateApplicant(Integer applicantid, Integer orderid, HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, applicantid.longValue());
		result.put("applicant", applicant);
		TApplicantOrderJpEntity orderjp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantid));
		result.put("orderjp", orderjp);
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		if (!Util.isEmpty(applicant.getBirthday())) {
			Date birthday = applicant.getBirthday();
			String birthdayStr = sdf.format(birthday);
			result.put("birthday", birthdayStr);
		}
		if (!Util.isEmpty(applicant.getValidStartDate())) {
			Date validStartDate = applicant.getValidStartDate();
			String validStartDateStr = sdf.format(validStartDate);
			result.put("validStartDate", validStartDateStr);
		}
		if (!Util.isEmpty(applicant.getValidEndDate())) {
			Date validEndDate = applicant.getValidEndDate();
			String validEndDateStr = sdf.format(validEndDate);
			result.put("validEndDate", validEndDateStr);
		}

		if (!Util.isEmpty(applicant.getFirstNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicant.getFirstNameEn());
			result.put("firstNameEn", sb.toString());
		}
		if (!Util.isEmpty(applicant.getOtherFirstNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicant.getOtherFirstNameEn());
			result.put("otherFirstNameEn", sb.toString());
		}

		if (!Util.isEmpty(applicant.getLastNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicant.getLastNameEn());
			result.put("lastNameEn", sb.toString());
		}

		if (!Util.isEmpty(applicant.getOtherLastNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicant.getOtherLastNameEn());
			result.put("otherLastNameEn", sb.toString());
		}
		//执行保存到历史表
		//		saveOrUpdateApplicant(applicantid, orderid, request);
		String localAddr = request.getServerName();
		int localPort = request.getServerPort();
		result.put("localAddr", localAddr);
		result.put("localPort", localPort);
		result.put("websocketaddr", SIMPLE_WEBSOCKET_ADDR);
		//生成二维码
		String qrurl = "http://" + request.getServerName() + ":" + localPort + "/simplemobile/info.html?applicantid="
				+ applicantid + "&orderid=" + orderid;
		String qrCode = qrCodeService.encodeQrCode(request, qrurl);
		result.put("qrCode", qrCode);
		result.put("applicantid", applicant.getId());
		result.put("orderid", orderid);
		return result;
	}

	/***
	 * 新增or更新申请人基本信息
	 */
	//	public void saveOrUpdateApplicant(Integer applicantid, Integer orderid, HttpServletRequest request) {
	//		Map<String, Object> result = Maps.newHashMap();
	//		HttpSession session = request.getSession();
	//		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
	//		TUserEntity loginUser = LoginUtil.getLoginUser(session);
	//		//根据申请人ID获取申请人基本信息
	//		TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, applicantid.longValue());
	//		//生成申请人基础信息历史实体
	//		TApplicantHisEntity applicantHis = dbDao.fetch(TApplicantHisEntity.class, orderid.longValue());
	//		if (!Util.isEmpty(applicantHis)) {
	//			applicantHis = new TApplicantHisEntity();
	//		}
	//		//主申请人ID
	//		if (!Util.isEmpty(applicant.getMainId())) {
	//			applicantHis.setMainId(applicant.getMainId());
	//		}
	//		//登陆人ID
	//		applicantHis.setId(loginUser.getId());
	//		//登陆人
	//		applicantHis.setUserName(loginUser.getName());
	//		//申请人状态
	//		Integer status = applicant.getStatus();
	//		if (!Util.isEmpty(applicant.getStatus())) {
	//
	//			for (TrialApplicantStatusEnum stEnum : TrialApplicantStatusEnum.values())
	//				if (!Util.isEmpty(status) && status == stEnum.intKey()) {
	//					applicantHis.setStatus(stEnum.value());
	//					break;
	//				}
	//		}
	//		//姓
	//		if (!Util.isEmpty(applicant.getFirstName())) {
	//			applicantHis.setFirstName(applicant.getFirstName());
	//		}
	//		//性(拼音)
	//		if (!Util.isEmpty(applicant.getFirstNameEn())) {
	//			StringBuffer sb = new StringBuffer();
	//			sb.append("/").append(applicant.getFirstNameEn());
	//			applicantHis.setFirstNameEn(sb.toString());
	//		}
	//		//名
	//		if (!Util.isEmpty(applicant.getLastName())) {
	//			applicantHis.setLastName(applicant.getLastName());
	//		}
	//		//名(拼音)
	//		if (!Util.isEmpty(applicant.getLastNameEn())) {
	//			StringBuffer sb = new StringBuffer();
	//			sb.append("/").append(applicant.getLastNameEn());
	//			applicantHis.setLastNameEn(sb.toString());
	//		}
	//		//email
	//		if (!Util.isEmpty(applicant.getEmail())) {
	//			applicantHis.setEmail(applicant.getEmail());
	//		}
	//		//性别
	//		if (!Util.isEmpty(applicant.getSex())) {
	//			applicantHis.setSex(applicant.getSex());
	//		}
	//		//民族
	//		if (!Util.isEmpty(applicant.getNation())) {
	//			applicantHis.setNation(applicant.getNation());
	//		}
	//		//出生日期
	//		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
	//		if (!Util.isEmpty(applicant.getBirthday())) {
	//			Date birthday = applicant.getBirthday();
	//			String birthdayStr = sdf.format(birthday);
	//			applicantHis.setBirthday(birthday);
	//		}
	//		//手机号
	//		if (!Util.isEmpty(applicant.getTelephone())) {
	//			applicantHis.setTelephone(applicant.getTelephone());
	//		}
	//		//住址
	//		if (!Util.isEmpty(applicant.getAddress())) {
	//			applicantHis.setAddress(applicant.getAddress());
	//		}
	//		//身份证号
	//		if (!Util.isEmpty(applicant.getCardId())) {
	//			applicantHis.setCardId(applicant.getCardId());
	//		}
	//		//身份证正面
	//		if (!Util.isEmpty(applicant.getCardFront())) {
	//			applicantHis.setCardFront(applicant.getCardFront());
	//		}
	//		//身份证反面
	//		if (!Util.isEmpty(applicant.getCardBack())) {
	//			applicantHis.setCardBack(applicant.getCardBack());
	//		}
	//		//签发机关
	//		if (!Util.isEmpty(applicant.getIssueOrganization())) {
	//			applicantHis.setIssueOrganization(applicant.getIssueOrganization());
	//		}
	//		//有效期始
	//		if (!Util.isEmpty(applicant.getValidStartDate())) {
	//			Date validStartDate = applicant.getValidStartDate();
	//			String validStartDateStr = sdf.format(validStartDate);
	//			applicantHis.setValidStartDate(validStartDate);
	//		}
	//		//有效期至
	//		if (!Util.isEmpty(applicant.getValidEndDate())) {
	//			Date validEndDate = applicant.getValidEndDate();
	//			String validEndDateStr = sdf.format(validEndDate);
	//			applicantHis.setValidEndDate(validEndDate);
	//		}
	//		//现居住地址省份
	//		if (!Util.isEmpty(applicant.getProvince())) {
	//			applicantHis.setProvince(applicant.getProvince());
	//		}
	//		//现居住地址城市
	//		if (!Util.isEmpty(applicant.getCity())) {
	//			applicantHis.setCity(applicant.getCity());
	//		}
	//		//详细地址
	//		if (!Util.isEmpty(applicant.getDetailedAddress())) {
	//			applicantHis.setDetailedAddress(applicant.getDetailedAddress());
	//		}
	//		//曾用姓
	//		if (!Util.isEmpty(applicant.getOtherFirstName())) {
	//			applicantHis.setOtherFirstName(applicant.getOtherFirstName());
	//		}
	//		//曾用姓(拼音)
	//		if (!Util.isEmpty(applicant.getOtherFirstNameEn())) {
	//			StringBuffer sb = new StringBuffer();
	//			sb.append("/").append(applicant.getOtherFirstNameEn());
	//			applicantHis.setOtherFirstNameEn(sb.toString());
	//		}
	//		//曾用名
	//		if (!Util.isEmpty(applicant.getOtherLastName())) {
	//			applicantHis.setOtherLastName(applicant.getOtherLastName());
	//		}
	//		//曾用名(拼音)
	//		if (!Util.isEmpty(applicant.getOtherLastNameEn())) {
	//			StringBuffer sb = new StringBuffer();
	//			sb.append("/").append(applicant.getOtherLastNameEn());
	//			applicantHis.setOtherLastNameEn(sb.toString());
	//		}
	//		//紧急联系人姓名
	//		if (!Util.isEmpty(applicant.getEmergencyLinkman())) {
	//			applicantHis.setEmergencyLinkman(applicant.getEmergencyLinkman());
	//		}
	//		//紧急联系人手机
	//		if (!Util.isEmpty(applicant.getEmergencyTelephone())) {
	//			applicantHis.setEmergencyTelephone(applicant.getEmergencyTelephone());
	//		}
	//		//身份证省份
	//		if (!Util.isEmpty(applicant.getCardProvince())) {
	//			applicantHis.setCardProvince(applicant.getCardProvince());
	//		}
	//		//身份证城市
	//		if (!Util.isEmpty(applicant.getCardCity())) {
	//			applicantHis.setCardCity(applicant.getCardCity());
	//		}
	//		//是否另有国籍
	//
	//		Integer hasOtherNationality = applicant.getHasOtherNationality();
	//		if (!Util.isEmpty(hasOtherNationality)) {
	//			for (IsHasOrderOrNotEnum stEnum : IsHasOrderOrNotEnum.values())
	//				if (!Util.isEmpty(hasOtherNationality) && hasOtherNationality == stEnum.intKey()) {
	//					applicantHis.setHasOtherNationality(stEnum.value());
	//					break;
	//				}
	//		}
	//		//是否有曾用名
	//		Integer hasOtherName = applicant.getHasOtherName();
	//		for (IsHasOrderOrNotEnum stEnum : IsHasOrderOrNotEnum.values())
	//			if (!Util.isEmpty(hasOtherName) && hasOtherName == stEnum.intKey()) {
	//				applicantHis.setHasOtherName(stEnum.value());
	//				break;
	//			}
	//		//结婚证/离婚证地址
	//		if (!Util.isEmpty(applicant.getMarryUrl())) {
	//			applicantHis.setMarryUrl(applicant.getMarryUrl());
	//		}
	//		//结婚状况
	//		Integer marryStatus = applicant.getMarryStatus();
	//		for (MarryStatusEnum stEnum : MarryStatusEnum.values())
	//			if (!Util.isEmpty(marryStatus) && marryStatus == stEnum.intKey()) {
	//				applicantHis.setMarryStatus(stEnum.value());
	//				break;
	//			}
	//		//国籍
	//		if (!Util.isEmpty(applicant.getNationality())) {
	//			applicantHis.setNationality(applicant.getNationality());
	//		}
	//		//现居住地址是否与身份证相同
	//		Integer addressIsSameWithCard = applicant.getAddressIsSameWithCard();
	//		for (IsHasOrderOrNotEnum stEnum : IsHasOrderOrNotEnum.values())
	//			if (!Util.isEmpty(addressIsSameWithCard) && addressIsSameWithCard == stEnum.intKey()) {
	//				applicantHis.setAddressIsSameWithCard(stEnum.value());
	//				break;
	//			}
	//
	//		//操作人
	//		if (!Util.isEmpty(applicant.getOpId())) {
	//			applicantHis.setOpId(applicant.getOpId());
	//		}
	//		if (!Util.isEmpty(applicantHis.getId())) {
	//			applicantHis.setUpdateTime(new Date());
	//			dbDao.update(applicantHis);
	//		} else {
	//			applicantHis.setCreateTime(new Date());
	//			dbDao.insert(applicantHis);
	//		}
	//	}

	/**
	 * 跳转到签证信息页面
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param applicantid
	 * @param orderid
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object visaInfo(Integer applicantid, Integer orderid, HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		HttpSession session = request.getSession();

		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userType = loginUser.getUserType();
		result.put("userType", userType);
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applicantid.longValue());
		TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantid));
		TOrderJpEntity jporderinfo = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId().longValue());
		result.put("jporderinfo", jporderinfo);
		result.put("applyorderinfo", applicantOrderJpEntity);
		result.put("marryStatus", apply.getMarryStatus());
		result.put("marryUrl", apply.getMarryUrl());
		result.put("outboundrecord", apply.getOutboundrecord());
		TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId()
				.longValue());
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderJpEntity.getOrderId().longValue());

		if (!Util.isEmpty(orderid)) {
			result.put("orderid", orderid);
		} else {
			result.put("orderid", orderJpEntity.getOrderId());
		}
		result.put("marryStatusEnum", EnumUtil.enum2(MarryStatusEnum.class));
		result.put("mainOrVice", EnumUtil.enum2(MainOrViceEnum.class));
		result.put("isOrNo", EnumUtil.enum2(IsYesOrNoEnum.class));
		result.put("applicantRelation", EnumUtil.enum2(MainApplicantRelationEnum.class));
		result.put("applicantRemark", EnumUtil.enum2(MainApplicantRemarkEnum.class));
		result.put("jobStatusEnum", EnumUtil.enum2(JobStatusEnum.class));
		result.put("mainsalevisatypeenum", EnumUtil.enum2(MainSaleVisaTypeEnum.class));
		result.put("isyesornoenum", EnumUtil.enum2(IsYesOrNoEnum.class));
		String visaInfoSqlstr = sqlManager.get("visaInfo_byApplicantId");
		Sql visaInfoSql = Sqls.create(visaInfoSqlstr);
		visaInfoSql.setParam("id", applicantid);
		Record visaInfo = dbDao.fetch(visaInfoSql);
		//获取申请人
		TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class, new Long(applicantid).intValue());
		result.put("applicant", applicantEntity);
		if (!Util.isEmpty(applicantEntity.getMainId())) {
			TApplicantEntity mainApplicant = dbDao.fetch(TApplicantEntity.class, new Long(applicantEntity.getMainId()));
			if (!Util.isEmpty(mainApplicant)) {
				result.put("mainApplicant", mainApplicant);
			}
		}
		TApplicantUnqualifiedEntity unqualifiedEntity = dbDao.fetch(TApplicantUnqualifiedEntity.class,
				Cnd.where("applicantId", "=", applicantid));
		if (!Util.isEmpty(unqualifiedEntity)) {
			result.put("unqualified", unqualifiedEntity);
		}
		//获取订单主申请人
		String sqlStr = sqlManager.get("mainApplicant_byOrderId");
		Sql applysql = Sqls.create(sqlStr);
		List<Record> records = dbDao.query(
				applysql,
				Cnd.where("oj.orderId", "=", orderJpEntity.getOrderId()).and("aoj.isMainApplicant", "=",
						IsYesOrNoEnum.YES.intKey()), null);
		//获取日本申请人信息
		result.put("orderStatus", orderEntity.getStatus());
		result.put("orderJpId", orderJpEntity.getId());
		result.put("orderJp", applicantOrderJpEntity);
		result.put("infoType", ApplicantInfoTypeEnum.VISA.intKey());
		//获取财产信息
		String wealthsqlStr = sqlManager.get("wealth_byApplicantId");
		Sql wealthsql = Sqls.create(wealthsqlStr);
		wealthsql.setParam("id", applicantOrderJpEntity.getId());
		List<Record> wealthJp = dbDao.query(wealthsql, null, null);

		/*List<TApplicantWealthJpEntity> wealthJp = dbDao.query(TApplicantWealthJpEntity.class,
				Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()), null);*/
		result.put("wealthJp", wealthJp);
		//获取工作信息
		TApplicantWorkJpEntity applicantWorkJpEntity = dbDao.fetch(TApplicantWorkJpEntity.class,
				Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()));
		if (Util.isEmpty(applicantWorkJpEntity.getUnitName())) {
			result.put("unitName", "无");
		} else {
			result.put("unitName", applicantWorkJpEntity.getUnitName());
		}
		result.put("workJp", applicantWorkJpEntity);
		result.put("mainApply", records);
		result.put("visaInfo", visaInfo);
		TApplicantVisaOtherInfoEntity visaother = dbDao.fetch(TApplicantVisaOtherInfoEntity.class,
				Cnd.where("applicantid", "=", applicantOrderJpEntity.getId()));
		if (Util.isEmpty(visaother)) {
			visaother = new TApplicantVisaOtherInfoEntity();
		}
		result.put("visaother", visaother);
		//获取所访问的ip地址
		String localAddr = request.getServerName();
		//所访问的端口
		int localPort = request.getServerPort();
		result.put("localAddr", localAddr);
		result.put("localPort", localPort);
		result.put("websocketaddr", SIMPLE_WEBSOCKET_ADDR);
		return result;
	}

	/**
	 * 保存签证信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveEditVisa(VisaEditDataForm form, HttpServletRequest request) {
		long startTime = System.currentTimeMillis();//获取当前时间
		System.out.println("进入请求了========");
		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		try {
			System.out.println("applicant:" + form.getApplicantId());
			//日本申请人
			if (!Util.isEmpty(form.getApplicantId())) {
				System.out.println("有applicantid==========");
				//日本申请人
				TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
						Cnd.where("applicantId", "=", form.getApplicantId()));
				System.out.println("查询到了日本申请人信息=======");
				TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId()
						.longValue());
				orderjp.setVisaType(form.getVisatype());
				orderjp.setVisaCounty(form.getVisacounty());
				orderjp.setIsVisit(form.getIsVisit());
				orderjp.setThreeCounty(form.getThreecounty());
				//orderjp.setLaststartdate(form.getLaststartdate());
				//orderjp.setLaststayday(form.getLaststayday());
				//orderjp.setLastreturndate(form.getLastreturndate());
				applicantOrderJpEntity.setLaststartdate(form.getLaststartdate());
				applicantOrderJpEntity.setLaststayday(form.getLaststayday());
				applicantOrderJpEntity.setLastreturndate(form.getLastreturndate());
				dbDao.update(orderjp);
				System.out.println("更新日本订单信息完毕========");
				//申请人
				TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class,
						new Long(applicantOrderJpEntity.getApplicantId()).intValue());
				//applicantEntity.setMarryStatus(form.getMarryStatus());
				applicantEntity.setMarryUrl(form.getMarryUrl());
				applicantEntity.setMarryurltype(form.getMarryStatus());
				applicantEntity.setOutboundrecord(form.getOutboundrecord());
				//更新申请人信息
				if (Util.eq(form.getApplicant(), MainOrViceEnum.YES.intKey())) {//是主申请人
					applicantEntity.setMainId(applicantEntity.getId());
					dbDao.update(applicantEntity);
					applicantOrderJpEntity.setIsMainApplicant(MainOrViceEnum.YES.intKey());
				} else {
					applicantEntity.setMainId(null);
					dbDao.update(applicantEntity);
					applicantOrderJpEntity.setIsMainApplicant(MainOrViceEnum.NO.intKey());
				}
				System.out.println("更新申请人信息完毕========");

				//更新日本申请人信息
				if (!Util.isEmpty(form.getSameMainTrip())) {
					applicantOrderJpEntity.setSameMainTrip(form.getSameMainTrip());
				}
				if (!Util.isEmpty(form.getSameMainWealth())) {
					applicantOrderJpEntity.setSameMainWealth(form.getSameMainWealth());
					//如果申请人跟主申请人的财产信息一样，把主申请人的财产信息保存到申请人财产信息中
					if (Util.eq(form.getSameMainWealth(), IsYesOrNoEnum.YES.intKey())) {
						List<TApplicantWealthJpEntity> beforeList = dbDao.query(TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()), null);
						if (beforeList.size() > 0) {
							dbDao.delete(beforeList);
						}
						System.out.println("副申请人财产信息同主申请人========");

					} else {
						//添加财产信息
						long wealthTime = System.currentTimeMillis();
						List<TApplicantWealthJpEntity> beforeList = dbDao.query(TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()), null);
						if (beforeList.size() > 0) {
							dbDao.delete(beforeList);
						}

						List<Map<String, WealthEntity>> wealthInfoList = form.getWealthInfoObject();
						if (wealthInfoList.size() > 0) {
							for (Map<String, WealthEntity> map : wealthInfoList) {
								for (String sequence : map.keySet()) {
									WealthEntity wealthEntity = map.get(sequence);
									String wealthtitle = wealthEntity.getWealthtitle();
									String wealthvalue = wealthEntity.getWealthvalue();
									String wealthtype = wealthEntity.getWealthtype();
									String wealthname = wealthEntity.getWealthname();

									if (!Util.isEmpty(wealthvalue)) {
										TApplicantWealthJpEntity wealthjp = new TApplicantWealthJpEntity();
										wealthjp.setSequence(Integer.valueOf(sequence));
										wealthjp.setBankflowfree(wealthtitle);
										wealthjp.setDetails(wealthvalue);
										wealthjp.setType(wealthtype);
										wealthjp.setApplicantId(applicantOrderJpEntity.getId());
										wealthjp.setCreateTime(new Date());
										dbDao.insert(wealthjp);
									}
								}
							}

						}

						//insertorupdateWealthinfo(form, applicantOrderJpEntity, loginUser);
						long wealthTime2 = System.currentTimeMillis();
						System.out.println("财产信息所用时间：" + (wealthTime2 - wealthTime) + "ms");
					}
				}
				TApplicantVisaOtherInfoEntity otherinfo = dbDao.fetch(TApplicantVisaOtherInfoEntity.class,
						Cnd.where("applicantid", "=", applicantOrderJpEntity.getId()));
				if (Util.isEmpty(otherinfo)) {
					otherinfo = new TApplicantVisaOtherInfoEntity();
				}
				otherinfo.setApplicantid(applicantOrderJpEntity.getId());
				otherinfo.setHotelname(form.getHotelname());
				otherinfo.setHotelphone(form.getHotelphone());
				otherinfo.setHoteladdress(form.getHoteladdress());
				otherinfo.setVouchname(form.getVouchname());
				otherinfo.setIsname(form.getIsname());
				otherinfo.setVouchnameen(form.getVouchnameen());
				otherinfo.setVouchphone(form.getVouchphone());
				otherinfo.setVouchaddress(form.getVouchaddress());
				otherinfo.setVouchbirth(form.getVouchbirth());
				otherinfo.setVouchsex(form.getVouchsex());
				otherinfo.setVouchmainrelation(form.getVouchmainrelation());
				otherinfo.setVouchjob(form.getVouchjob());
				otherinfo.setVouchcountry(form.getVouchcountry());
				otherinfo.setInvitename(form.getInvitename());
				otherinfo.setInvitephone(form.getInvitephone());
				otherinfo.setInviteaddress(form.getInviteaddress());
				otherinfo.setInvitebirth(form.getInvitebirth());
				otherinfo.setInvitesex(form.getInvitesex());
				otherinfo.setInvitemainrelation(form.getInvitemainrelation());
				otherinfo.setInvitejob(form.getInvitejob());
				otherinfo.setInvitecountry(form.getInvitecountry());
				otherinfo.setTraveladvice(form.getTraveladvice());
				otherinfo.setIsyaoqing(form.getIsyaoqing());
				if (!Util.isEmpty(otherinfo.getId())) {
					dbDao.update(otherinfo);
				} else {
					dbDao.insert(otherinfo);
				}
				System.out.println("其他信息完毕========");
				//更新工作信息
				TApplicantWorkJpEntity applicantWorkJpEntity = dbDao.fetch(TApplicantWorkJpEntity.class,
						Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()));
				Integer careerStatus = form.getCareerStatus();
				if (!Util.isEmpty(careerStatus)) {
					long wealthTime = System.currentTimeMillis();
					toUpdateWorkJp(careerStatus, applicantWorkJpEntity, applicantOrderJpEntity, session);
					long wealthTime2 = System.currentTimeMillis();
					System.out.println("资料类型所用时间：" + (wealthTime2 - wealthTime) + "ms");
				} else {
					Integer applicantJpId = applicantOrderJpEntity.getId();
					List<TApplicantFrontPaperworkJpEntity> frontListDB = dbDao.query(
							TApplicantFrontPaperworkJpEntity.class, Cnd.where("applicantId", "=", applicantJpId), null);
					List<TApplicantVisaPaperworkJpEntity> visaListDB = dbDao.query(
							TApplicantVisaPaperworkJpEntity.class, Cnd.where("applicantId", "=", applicantJpId), null);
					if (!Util.isEmpty(frontListDB)) {//如果库中有数据，则删掉
						dbDao.delete(frontListDB);
					}
					if (!Util.isEmpty(visaListDB)) {
						dbDao.delete(visaListDB);
					}
					applicantWorkJpEntity.setPrepareMaterials(null);
				}
				applicantWorkJpEntity.setUnitName(form.getUnitName());
				applicantWorkJpEntity.setPosition(form.getPosition());
				applicantWorkJpEntity.setCareerStatus(form.getCareerStatus());
				applicantWorkJpEntity.setName(form.getName());
				applicantWorkJpEntity.setAddress(form.getAddress());
				applicantWorkJpEntity.setTelephone(form.getTelephone());
				applicantWorkJpEntity.setUpdateTime(new Date());
				dbDao.update(applicantWorkJpEntity);
				System.out.println("工作信息完毕========");
				if (!Util.isEmpty(form.getMainRelation())) {
					applicantOrderJpEntity.setMainRelation(form.getMainRelation());
				} else {
					applicantOrderJpEntity.setMainRelation(null);
				}
				if (!Util.isEmpty(form.getRelationRemark())) {
					applicantOrderJpEntity.setRelationRemark(form.getRelationRemark());
				} else {
					applicantOrderJpEntity.setRelationRemark(null);
				}
				//applicantOrderJpEntity.setMarryStatus(visaForm.getMarryStatus());
				//applicantOrderJpEntity.setMarryUrl(visaForm.getMarryUrl());
				dbDao.update(applicantOrderJpEntity);

			}
			long endTime = System.currentTimeMillis();
			System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
			System.out.println("-------------------------");
		} catch (Exception e) {
			String simplename = e.getClass().getSimpleName();
			if ("ClientAbortException".equals(simplename)) {
				System.out.println("错误出现了！");
			} else {
				e.printStackTrace();
			}

			return null;
		}
		System.out.println("*****************");
		return null;
	}

	public Object insertorupdateWealthinfo(VisaEditDataForm form, TApplicantOrderJpEntity applicantOrderJpEntity,
			TUserEntity loginUser) {
		//银行流水
		if (!Util.isEmpty(form.getBankflow())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "银行流水"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getBankflow());
				applicantWealthJpEntity.setBankflowfree(form.getBankflowfree());
				applicantWealthJpEntity.setSequence(1);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getBankflow());
				wealthJp.setBankflowfree(form.getBankflowfree());
				wealthJp.setType("银行流水");
				wealthJp.setSequence(1);
				wealthJp.setCreateTime(new Date());
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "银行流水"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("银行流水完毕========");
		//车产
		if (!Util.isEmpty(form.getVehicle())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
					TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
							ApplicantJpWealthEnum.CAR.value()));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getVehicle());
				applicantWealthJpEntity.setVehiclefree(form.getVehiclefree());
				applicantWealthJpEntity.setSequence(2);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getVehicle());
				wealthJp.setVehiclefree(form.getVehiclefree());
				wealthJp.setType(ApplicantJpWealthEnum.CAR.value());
				wealthJp.setCreateTime(new Date());
				wealthJp.setSequence(2);
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
					TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
							ApplicantJpWealthEnum.CAR.value()));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("车产完毕========");
		//房产
		if (!Util.isEmpty(form.getHouseProperty())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
					TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
							ApplicantJpWealthEnum.HOME.value()));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getHouseProperty());
				applicantWealthJpEntity.setHousePropertyfree(form.getHousePropertyfree());
				applicantWealthJpEntity.setSequence(3);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getHouseProperty());
				wealthJp.setHousePropertyfree(form.getHousePropertyfree());
				wealthJp.setType(ApplicantJpWealthEnum.HOME.value());
				wealthJp.setCreateTime(new Date());
				wealthJp.setSequence(3);
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
					TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
							ApplicantJpWealthEnum.HOME.value()));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("房产完毕========");
		//理财
		if (!Util.isEmpty(form.getFinancial())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
					TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
							ApplicantJpWealthEnum.LICAI.value()));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getFinancial());
				applicantWealthJpEntity.setFinancialfree(form.getFinancialfree());
				applicantWealthJpEntity.setSequence(4);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getFinancial());
				wealthJp.setFinancialfree(form.getFinancialfree());
				wealthJp.setType(ApplicantJpWealthEnum.LICAI.value());
				wealthJp.setCreateTime(new Date());
				wealthJp.setSequence(4);
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
					TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
							ApplicantJpWealthEnum.LICAI.value()));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("理财完毕========");
		//在职证明
		if (!Util.isEmpty(form.getCertificate())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "在职证明"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getCertificate());
				applicantWealthJpEntity.setCertificatefree(form.getCertificatefree());
				applicantWealthJpEntity.setSequence(5);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getCertificate());
				wealthJp.setCertificatefree(form.getCertificatefree());
				wealthJp.setType("在职证明");
				wealthJp.setCreateTime(new Date());
				wealthJp.setSequence(5);
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "在职证明"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("在职证明完毕========");
		//银行存款
		if (!Util.isEmpty(form.getDeposit())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "银行存款"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getDeposit());
				applicantWealthJpEntity.setDepositfree(form.getDepositfree());
				applicantWealthJpEntity.setSequence(6);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getDeposit());
				wealthJp.setDepositfree(form.getDepositfree());
				wealthJp.setType("银行存款");
				wealthJp.setSequence(6);
				wealthJp.setCreateTime(new Date());
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "银行存款"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("银行存款完毕========");
		//税单
		if (!Util.isEmpty(form.getTaxbill())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "税单"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getTaxbill());
				applicantWealthJpEntity.setTaxbillfree(form.getTaxbillfree());
				applicantWealthJpEntity.setSequence(7);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getTaxbill());
				wealthJp.setTaxbillfree(form.getTaxbillfree());
				wealthJp.setType("税单");
				wealthJp.setSequence(7);
				wealthJp.setCreateTime(new Date());
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "税单"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("税单完毕========");
		//完税证明
		if (!Util.isEmpty(form.getTaxproof())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "完税证明"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getTaxproof());
				applicantWealthJpEntity.setTaxprooffree(form.getTaxprooffree());
				applicantWealthJpEntity.setSequence(8);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getTaxproof());
				wealthJp.setTaxprooffree(form.getTaxprooffree());
				wealthJp.setType("完税证明");
				wealthJp.setSequence(8);
				wealthJp.setCreateTime(new Date());
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "完税证明"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("完税证明完毕========");
		//银行金卡
		if (!Util.isEmpty(form.getGoldcard())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "银行金卡"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getGoldcard());
				applicantWealthJpEntity.setGoldcardfree(form.getGoldcardfree());
				applicantWealthJpEntity.setSequence(9);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getGoldcard());
				wealthJp.setGoldcardfree(form.getGoldcardfree());
				wealthJp.setType("银行金卡");
				wealthJp.setSequence(9);
				wealthJp.setCreateTime(new Date());
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "银行金卡"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("银行金卡完毕========");
		//特定高校在读生
		if (!Util.isEmpty(form.getReadstudent())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "特定高校在读生"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getReadstudent());
				applicantWealthJpEntity.setReadstudentfree(form.getReadstudentfree());
				applicantWealthJpEntity.setSequence(10);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getReadstudent());
				wealthJp.setReadstudentfree(form.getReadstudentfree());
				wealthJp.setType("特定高校在读生");
				wealthJp.setSequence(10);
				wealthJp.setCreateTime(new Date());
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "特定高校在读生"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("在读生完毕========");
		//特定高校毕业生
		if (!Util.isEmpty(form.getGraduate())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "特定高校毕业生"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getGraduate());
				applicantWealthJpEntity.setGraduatefree(form.getGraduatefree());
				applicantWealthJpEntity.setSequence(11);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getGraduate());
				wealthJp.setGraduatefree(form.getGraduatefree());
				wealthJp.setType("特定高校毕业生");
				wealthJp.setSequence(11);
				wealthJp.setCreateTime(new Date());
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "特定高校毕业生"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("财产信息完毕=====");
		return null;
	}

	/***
	 * TODO(签证信息历史信息保存)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param careerStatus
	 * @param applicantWorkJpEntity
	 * @param applicantOrderJpEntity
	 * @param session TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public void saveOrUpdateVisa(VisaEditDataForm form, HttpServletRequest request) {

		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		//是否是主申请人
		//		if (!Util.isEmpty(form.getApplicantId())) {
		//			//根据订单Id获取签证信息历史
		//			TTouristVisaHisEntity tTouristVisaHisEntity = dbDao.fetch(TTouristVisaHisEntity.class,
		//					Cnd.where("orderId", "=", form.getOrderid()));
		//			if (Util.isEmpty(tTouristVisaHisEntity)) {
		//				tTouristVisaHisEntity = new TTouristVisaHisEntity();
		//			}
		//			//订单ID
		//			tTouristVisaHisEntity.setOrderId(form.getOrderid());
		//			//用户名
		//			tTouristVisaHisEntity.setUserName(loginUser.getName());
		//			//用户ID
		//			tTouristVisaHisEntity.setUserId(loginUser.getId());
		//			//婚姻状况
		//			Integer marryStatus = form.getMarryStatus();
		//			if (!Util.isEmpty(marryStatus)) {
		//				for (MarryStatusEnum marryEnum : MarryStatusEnum.values()) {
		//					if (!Util.isEmpty(marryStatus) && marryStatus.equals(marryEnum.key())) {
		//						tTouristVisaHisEntity.setMarryStatus(marryEnum.value());
		//					}
		//				}
		//			}
		//			//日本申请人
		//			TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
		//					Cnd.where("applicantId", "=", form.getApplicantId()));
		//			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId().longValue());
		//			//签证类型
		//			Integer visatype = form.getVisatype();
		//			if (!Util.isEmpty(visatype)) {
		//				for (IssueValidityEnum marryEnum : IssueValidityEnum.values()) {
		//					if (!Util.isEmpty(marryStatus) && marryStatus.equals(marryEnum.key())) {
		//						tTouristVisaHisEntity.setVisaType(marryEnum.value());
		//					}
		//				}
		//			}
		//			//签证县
		//			tTouristVisaHisEntity.setVisaCounty(form.getVisacounty());
		//			//过去三年是否访问
		//			if (!Util.isEmpty(form.getIsVisit())) {
		//				for (IssueValidityEnum marryEnum : IssueValidityEnum.values()) {
		//					if (!Util.isEmpty(form.getIsVisit()) && form.getIsVisit().equals(marryEnum.key())) {
		//						tTouristVisaHisEntity.setIsVisit(marryEnum.value());
		//					}
		//				}
		//			}
		//			//过去三年访问过的县
		//			tTouristVisaHisEntity.setThreeCounty(form.getThreecounty());
		//			//上次赴日时间
		//			tTouristVisaHisEntity.setLaststartdate(form.getLaststartdate());
		//			//上次停留天数
		//			tTouristVisaHisEntity.setLaststayday(form.getLaststayday());
		//			//上次返回时间
		//			tTouristVisaHisEntity.setLastreturndate(form.getLastreturndate());
		//			//结婚证/离婚证地址
		//			tTouristVisaHisEntity.setMarryUrl(form.getMarryUrl());
		//			if (!Util.isEmpty(form.getAddApply())) {
		//				if (Util.eq(form.getAddApply(), 2)) {
		//					tTouristVisaHisEntity.setStatus(TrialApplicantStatusEnum.FillCompleted.intKey());
		//				}
		//			}
		//			//主申请人
		//			if (!Util.isEmpty(tTouristVisaHisEntity.getMainId())) {
		//				TApplicantEntity mainApplicant = dbDao.fetch(TApplicantEntity.class,
		//						new Long(applicantEntity.getMainId()).intValue());
		//			}
		//			//更新申请人信息
		//			if (Util.eq(form.getApplicant(), MainOrViceEnum.YES.intKey())) {//是主申请人
		//				applicantEntity.setMainId(applicantEntity.getId());
		//				dbDao.update(applicantEntity);
		//			} else {
		//				if (!Util.isEmpty(form.getMainApplicant())) {
		//					applicantEntity.setMainId(form.getMainApplicant());
		//				}
		//			}
		//			if (Util.eq(applicantEntity.getId(), applicantEntity.getMainId())) {
		//				applicantOrderJpEntity.setIsMainApplicant(MainOrViceEnum.YES.intKey());
		//			} else {
		//				applicantOrderJpEntity.setIsMainApplicant(MainOrViceEnum.NO.intKey());
		//			}
		//
		//			//更新日本申请人信息
		//			if (!Util.isEmpty(form.getSameMainTrip())) {
		//				applicantOrderJpEntity.setSameMainTrip(form.getSameMainTrip());
		//			}
		//			if (!Util.isEmpty(form.getSameMainWealth())) {
		//				applicantOrderJpEntity.setSameMainWealth(form.getSameMainWealth());
		//				//如果申请人跟主申请人的财产信息一样，把主申请人的财产信息保存到申请人财产信息中
		//				if (Util.eq(form.getSameMainWealth(), IsYesOrNoEnum.YES.intKey())) {
		//					if (!Util.isEmpty(applicantEntity.getMainId())) {
		//						TApplicantEntity mainApplicant = dbDao.fetch(TApplicantEntity.class,
		//								new Long(applicantEntity.getMainId()).intValue());
		//						TApplicantOrderJpEntity mainAppyJp = dbDao.fetch(TApplicantOrderJpEntity.class,
		//								Cnd.where("applicantId", "=", mainApplicant.getId()));
		//						//获取主申请人的财产信息
		//						//银行流水
		//						TApplicantWealthJpEntity mainApplyWealthJp = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", mainAppyJp.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.BANK.value()));
		//						TApplicantWealthJpEntity applyWealthJp = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.BANK.value()));
		//						if (!Util.isEmpty(mainApplyWealthJp)) {
		//							if (!Util.isEmpty(applyWealthJp)) {//如果申请人有银行流水信息，则更新
		//								if (!Util.isEmpty(mainApplyWealthJp.getDetails())) {
		//									applyWealthJp.setDetails(mainApplyWealthJp.getDetails());
		//									applyWealthJp.setApplicantId(applicantOrderJpEntity.getId());
		//									applyWealthJp.setOpId(loginUser.getId());
		//									applyWealthJp.setUpdateTime(new Date());
		//									dbDao.update(applyWealthJp);
		//								}
		//							} else {//没有则添加
		//								TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
		//								applyWealth.setType("银行流水");
		//								applyWealth.setDetails(mainApplyWealthJp.getDetails());
		//								applyWealth.setApplicantId(applicantOrderJpEntity.getId());
		//								applyWealth.setOpId(loginUser.getId());
		//								applyWealth.setCreateTime(new Date());
		//								dbDao.insert(applyWealth);
		//							}
		//						}
		//						//车产
		//						TApplicantWealthJpEntity applicantWealthJpCar = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.CAR.value()));
		//						TApplicantWealthJpEntity mainApplyWealthJpCar = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", mainAppyJp.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.CAR.value()));
		//						if (!Util.isEmpty(mainApplyWealthJpCar)) {
		//							if (!Util.isEmpty(applicantWealthJpCar)) {//如果申请人有银行流水信息，则更新
		//								if (!Util.isEmpty(mainApplyWealthJpCar.getDetails())) {
		//									applicantWealthJpCar.setDetails(mainApplyWealthJpCar.getDetails());
		//									applicantWealthJpCar.setApplicantId(applicantOrderJpEntity.getId());
		//									applicantWealthJpCar.setOpId(loginUser.getId());
		//									applicantWealthJpCar.setUpdateTime(new Date());
		//									dbDao.update(applicantWealthJpCar);
		//								}
		//							} else {
		//								TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
		//								applyWealth.setType("车产");
		//								applyWealth.setDetails(mainApplyWealthJpCar.getDetails());
		//								applyWealth.setApplicantId(applicantOrderJpEntity.getId());
		//								applyWealth.setOpId(loginUser.getId());
		//								applyWealth.setCreateTime(new Date());
		//								dbDao.insert(applyWealth);
		//							}
		//						}
		//
		//						//房产
		//						TApplicantWealthJpEntity applicantWealthJpHome = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.HOME.value()));
		//						TApplicantWealthJpEntity mainApplyWealthJpHome = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", mainAppyJp.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.HOME.value()));
		//						if (!Util.isEmpty(mainApplyWealthJpHome)) {
		//							if (!Util.isEmpty(applicantWealthJpHome)) {//如果申请人有银行流水信息，则更新
		//								if (!Util.isEmpty(mainApplyWealthJpHome.getDetails())) {
		//									applicantWealthJpHome.setDetails(mainApplyWealthJpHome.getDetails());
		//									applicantWealthJpHome.setApplicantId(applicantOrderJpEntity.getId());
		//									applicantWealthJpHome.setOpId(loginUser.getId());
		//									applicantWealthJpHome.setUpdateTime(new Date());
		//									dbDao.update(applicantWealthJpHome);
		//								}
		//							} else {
		//								TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
		//								applyWealth.setType("房产");
		//								applyWealth.setDetails(mainApplyWealthJpHome.getDetails());
		//								applyWealth.setApplicantId(applicantOrderJpEntity.getId());
		//								applyWealth.setOpId(loginUser.getId());
		//								applyWealth.setCreateTime(new Date());
		//								dbDao.insert(applyWealth);
		//							}
		//						}
		//
		//						//理财
		//						TApplicantWealthJpEntity applicantWealthJpLi = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.LICAI.value()));
		//						TApplicantWealthJpEntity mainApplyWealthJpLi = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", mainAppyJp.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.LICAI.value()));
		//						if (!Util.isEmpty(mainApplyWealthJpLi)) {
		//							if (!Util.isEmpty(applicantWealthJpLi)) {//如果申请人有银行流水信息，则更新
		//								if (!Util.isEmpty(mainApplyWealthJpLi.getDetails())) {
		//									applicantWealthJpLi.setDetails(mainApplyWealthJpLi.getDetails());
		//									applicantWealthJpLi.setApplicantId(applicantOrderJpEntity.getId());
		//									applicantWealthJpLi.setOpId(loginUser.getId());
		//									applicantWealthJpLi.setUpdateTime(new Date());
		//									dbDao.update(applicantWealthJpLi);
		//								}
		//							} else {
		//								TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
		//								applyWealth.setType("理财");
		//								applyWealth.setDetails(mainApplyWealthJpLi.getDetails());
		//								applyWealth.setApplicantId(applicantOrderJpEntity.getId());
		//								applyWealth.setOpId(loginUser.getId());
		//								applyWealth.setCreateTime(new Date());
		//								dbDao.insert(applyWealth);
		//							}
		//						}
		//
		//					}
		//				} else {
		//					//添加财产信息
		//					TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
		//					wealthJp.setApplicantId(applicantOrderJpEntity.getId());
		//					//银行流水
		//					if (!Util.isEmpty(form.getDeposit())) {
		//						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.BANK.value()));
		//						if (!Util.isEmpty(applicantWealthJpEntity)) {
		//							applicantWealthJpEntity.setDetails(form.getDeposit());
		//							dbDao.update(applicantWealthJpEntity);
		//						} else {
		//							wealthJp.setDetails(form.getDeposit());
		//							wealthJp.setType(ApplicantJpWealthEnum.BANK.value());
		//							wealthJp.setCreateTime(new Date());
		//							wealthJp.setOpId(loginUser.getId());
		//							dbDao.insert(wealthJp);
		//						}
		//					} else {
		//						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.BANK.value()));
		//						if (!Util.isEmpty(applicantWealthJpEntity)) {
		//							dbDao.delete(applicantWealthJpEntity);
		//						}
		//					}
		//					//车产
		//					if (!Util.isEmpty(form.getVehicle())) {
		//						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.CAR.value()));
		//						if (!Util.isEmpty(applicantWealthJpEntity)) {
		//							applicantWealthJpEntity.setDetails(form.getVehicle());
		//							dbDao.update(applicantWealthJpEntity);
		//						} else {
		//							wealthJp.setDetails(form.getVehicle());
		//							wealthJp.setType(ApplicantJpWealthEnum.CAR.value());
		//							wealthJp.setCreateTime(new Date());
		//							wealthJp.setOpId(loginUser.getId());
		//							dbDao.insert(wealthJp);
		//						}
		//					} else {
		//						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.CAR.value()));
		//						if (!Util.isEmpty(applicantWealthJpEntity)) {
		//							dbDao.delete(applicantWealthJpEntity);
		//						}
		//					}
		//					//房产
		//					if (!Util.isEmpty(form.getHouseProperty())) {
		//						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.HOME.value()));
		//						if (!Util.isEmpty(applicantWealthJpEntity)) {
		//							applicantWealthJpEntity.setDetails(form.getHouseProperty());
		//							dbDao.update(applicantWealthJpEntity);
		//						} else {
		//							wealthJp.setDetails(form.getHouseProperty());
		//							wealthJp.setType(ApplicantJpWealthEnum.HOME.value());
		//							wealthJp.setCreateTime(new Date());
		//							wealthJp.setOpId(loginUser.getId());
		//							dbDao.insert(wealthJp);
		//						}
		//					} else {
		//						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.HOME.value()));
		//						if (!Util.isEmpty(applicantWealthJpEntity)) {
		//							dbDao.delete(applicantWealthJpEntity);
		//						}
		//					}
		//					//理财
		//					if (!Util.isEmpty(form.getFinancial())) {
		//						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.LICAI.value()));
		//						if (!Util.isEmpty(applicantWealthJpEntity)) {
		//							applicantWealthJpEntity.setDetails(form.getFinancial());
		//							dbDao.update(applicantWealthJpEntity);
		//						} else {
		//							wealthJp.setDetails(form.getFinancial());
		//							wealthJp.setType(ApplicantJpWealthEnum.LICAI.value());
		//							wealthJp.setCreateTime(new Date());
		//							wealthJp.setOpId(loginUser.getId());
		//							dbDao.insert(wealthJp);
		//						}
		//					} else {
		//						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.LICAI.value()));
		//						if (!Util.isEmpty(applicantWealthJpEntity)) {
		//							dbDao.delete(applicantWealthJpEntity);
		//						}
		//					}
		//				}
		//			}
		//			TApplicantVisaOtherInfoEntity otherinfo = dbDao.fetch(TApplicantVisaOtherInfoEntity.class,
		//					Cnd.where("applicantid", "=", applicantOrderJpEntity.getId()));
		//			if (Util.isEmpty(otherinfo)) {
		//				otherinfo = new TApplicantVisaOtherInfoEntity();
		//			}
		//			otherinfo.setApplicantid(applicantOrderJpEntity.getId());
		//			otherinfo.setHotelname(form.getHotelname());
		//			otherinfo.setHotelphone(form.getHotelphone());
		//			otherinfo.setHoteladdress(form.getHoteladdress());
		//			otherinfo.setVouchname(form.getVouchname());
		//			otherinfo.setIsname(form.getIsname());
		//			otherinfo.setVouchnameen(form.getVouchnameen());
		//			otherinfo.setVouchphone(form.getVouchphone());
		//			otherinfo.setVouchaddress(form.getVouchaddress());
		//			otherinfo.setVouchbirth(form.getVouchbirth());
		//			otherinfo.setVouchsex(form.getVouchsex());
		//			otherinfo.setVouchmainrelation(form.getVouchmainrelation());
		//			otherinfo.setVouchjob(form.getVouchjob());
		//			otherinfo.setVouchcountry(form.getVouchcountry());
		//			otherinfo.setInvitename(form.getInvitename());
		//			otherinfo.setInvitephone(form.getInvitephone());
		//			otherinfo.setInviteaddress(form.getInviteaddress());
		//			otherinfo.setInvitebirth(form.getInvitebirth());
		//			otherinfo.setInvitesex(form.getInvitesex());
		//			otherinfo.setInvitemainrelation(form.getInvitemainrelation());
		//			otherinfo.setInvitejob(form.getInvitejob());
		//			otherinfo.setInvitecountry(form.getInvitecountry());
		//			otherinfo.setTraveladvice(form.getTraveladvice());
		//			otherinfo.setIsyaoqing(form.getIsyaoqing());
		//			if (!Util.isEmpty(otherinfo.getId())) {
		//				dbDao.update(otherinfo);
		//			} else {
		//				dbDao.insert(otherinfo);
		//			}
		//			//更新工作信息
		//			TApplicantWorkJpEntity applicantWorkJpEntity = dbDao.fetch(TApplicantWorkJpEntity.class,
		//					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()));
		//			Integer careerStatus = form.getCareerStatus();
		//			if (!Util.isEmpty(careerStatus)) {
		//				toUpdateWorkJp(careerStatus, applicantWorkJpEntity, applicantOrderJpEntity, session);
		//			} else {
		//				Integer applicantJpId = applicantOrderJpEntity.getId();
		//				List<TApplicantFrontPaperworkJpEntity> frontListDB = dbDao.query(
		//						TApplicantFrontPaperworkJpEntity.class, Cnd.where("applicantId", "=", applicantJpId), null);
		//				List<TApplicantVisaPaperworkJpEntity> visaListDB = dbDao.query(TApplicantVisaPaperworkJpEntity.class,
		//						Cnd.where("applicantId", "=", applicantJpId), null);
		//				if (!Util.isEmpty(frontListDB)) {//如果库中有数据，则删掉
		//					dbDao.delete(frontListDB);
		//				}
		//				if (!Util.isEmpty(visaListDB)) {
		//					dbDao.delete(visaListDB);
		//				}
		//				applicantWorkJpEntity.setPrepareMaterials(null);
		//			}
		//			applicantWorkJpEntity.setUnitName(form.getUnitName());
		//			applicantWorkJpEntity.setPosition(form.getPosition());
		//			applicantWorkJpEntity.setCareerStatus(form.getCareerStatus());
		//			applicantWorkJpEntity.setName(form.getName());
		//			applicantWorkJpEntity.setAddress(form.getAddress());
		//			applicantWorkJpEntity.setTelephone(form.getTelephone());
		//			applicantWorkJpEntity.setUpdateTime(new Date());
		//			dbDao.update(applicantWorkJpEntity);
		//			if (!Util.isEmpty(form.getMainRelation())) {
		//				applicantOrderJpEntity.setMainRelation(form.getMainRelation());
		//			} else {
		//				applicantOrderJpEntity.setMainRelation(null);
		//			}
		//			if (!Util.isEmpty(form.getRelationRemark())) {
		//				applicantOrderJpEntity.setRelationRemark(form.getRelationRemark());
		//			} else {
		//				applicantOrderJpEntity.setRelationRemark(null);
		//			}
		//			//applicantOrderJpEntity.setMarryStatus(visaForm.getMarryStatus());
		//			//applicantOrderJpEntity.setMarryUrl(visaForm.getMarryUrl());
		//			int update = dbDao.update(applicantOrderJpEntity);
		//
		//		}

	}

	public void toUpdateWorkJp(int careerStatus, TApplicantWorkJpEntity applicantWorkJpEntity,
			TApplicantOrderJpEntity applicantOrderJpEntity, HttpSession session) {

		Integer applicantJpId = applicantOrderJpEntity.getId();
		/*List<TApplicantFrontPaperworkJpEntity> frontListDB = dbDao.query(TApplicantFrontPaperworkJpEntity.class,
					Cnd.where("applicantId", "=", applicantJpId), null);*/
		List<TApplicantVisaPaperworkJpEntity> visaListDB = dbDao.query(TApplicantVisaPaperworkJpEntity.class,
				Cnd.where("applicantId", "=", applicantJpId), null);
		/*if (!Util.isEmpty(frontListDB)) {//如果库中有数据，则删掉
				dbDao.delete(frontListDB);
			}*/
		if (!Util.isEmpty(visaListDB)) {
			dbDao.delete(visaListDB);
		}
		if (Util.eq(careerStatus, JobStatusEnum.WORKING_STATUS.intKey())) {//在职
			//dbDao.insert(toInsertFrontJp(JobStatusEnum.WORKING_STATUS.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.WORKING_STATUS.intKey(), applicantJpId, session));

			StringBuilder sbWork = new StringBuilder();
			for (JobStatusWorkingEnum jobWorking : JobStatusWorkingEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
		if (Util.eq(careerStatus, JobStatusEnum.RETIREMENT_STATUS.intKey())) {//退休
			//dbDao.insert(toInsertFrontJp(JobStatusEnum.RETIREMENT_STATUS.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.RETIREMENT_STATUS.intKey(), applicantJpId, session));
			StringBuilder sbWork = new StringBuilder();
			for (JobStatusRetirementEnum jobWorking : JobStatusRetirementEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
		if (Util.eq(careerStatus, JobStatusEnum.FREELANCE_STATUS.intKey())) {//自由职业
			//dbDao.insert(toInsertFrontJp(JobStatusEnum.FREELANCE_STATUS.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.FREELANCE_STATUS.intKey(), applicantJpId, session));
			StringBuilder sbWork = new StringBuilder();
			for (JobStatusFreeEnum jobWorking : JobStatusFreeEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			int length = workStatus.length();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
		if (Util.eq(careerStatus, JobStatusEnum.student_status.intKey())) {//学生
			//dbDao.insert(toInsertFrontJp(JobStatusEnum.student_status.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.student_status.intKey(), applicantJpId, session));
			StringBuilder sbWork = new StringBuilder();
			for (JobStatusStudentEnum jobWorking : JobStatusStudentEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
		if (Util.eq(careerStatus, JobStatusEnum.Preschoolage_status.intKey())) {//学龄前
			//dbDao.insert(toInsertFrontJp(JobStatusEnum.Preschoolage_status.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.Preschoolage_status.intKey(), applicantJpId, session));
			StringBuilder sbWork = new StringBuilder();
			for (JobStatusPreschoolEnum jobWorking : JobStatusPreschoolEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
		System.out.println("职业相关处理完毕");
	}

	public List<TApplicantFrontPaperworkJpEntity> toInsertFrontJp(Integer workType, Integer applicantId,
			HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		List<TApplicantFrontPaperworkJpEntity> frontList = new ArrayList<>();
		if (Util.eq(workType, JobStatusEnum.WORKING_STATUS.intKey())) {
			for (JobStatusWorkingEnum jobStatusEnum : JobStatusWorkingEnum.values()) {
				TApplicantFrontPaperworkJpEntity frontJp = new TApplicantFrontPaperworkJpEntity();
				frontJp.setApplicantId(applicantId);
				frontJp.setOpId(loginUser.getId());
				frontJp.setRealInfo(jobStatusEnum.value());
				frontJp.setUpdateTime(new Date());
				frontJp.setCreateTime(new Date());
				frontJp.setType(workType);
				frontJp.setStatus(1);
				frontList.add(frontJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.RETIREMENT_STATUS.intKey())) {
			for (JobStatusRetirementEnum jobStatusEnum : JobStatusRetirementEnum.values()) {
				TApplicantFrontPaperworkJpEntity frontJp = new TApplicantFrontPaperworkJpEntity();
				frontJp.setApplicantId(applicantId);
				frontJp.setOpId(loginUser.getId());
				frontJp.setRealInfo(jobStatusEnum.value());
				frontJp.setUpdateTime(new Date());
				frontJp.setCreateTime(new Date());
				frontJp.setType(workType);
				frontJp.setStatus(1);
				frontList.add(frontJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.FREELANCE_STATUS.intKey())) {
			for (JobStatusFreeEnum jobStatusEnum : JobStatusFreeEnum.values()) {
				TApplicantFrontPaperworkJpEntity frontJp = new TApplicantFrontPaperworkJpEntity();
				frontJp.setApplicantId(applicantId);
				frontJp.setOpId(loginUser.getId());
				frontJp.setRealInfo(jobStatusEnum.value());
				frontJp.setUpdateTime(new Date());
				frontJp.setCreateTime(new Date());
				frontJp.setType(workType);
				frontJp.setStatus(1);
				frontList.add(frontJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.student_status.intKey())) {
			for (JobStatusStudentEnum jobStatusEnum : JobStatusStudentEnum.values()) {
				TApplicantFrontPaperworkJpEntity frontJp = new TApplicantFrontPaperworkJpEntity();
				frontJp.setApplicantId(applicantId);
				frontJp.setOpId(loginUser.getId());
				frontJp.setRealInfo(jobStatusEnum.value());
				frontJp.setUpdateTime(new Date());
				frontJp.setCreateTime(new Date());
				frontJp.setType(workType);
				frontJp.setStatus(1);
				frontList.add(frontJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.Preschoolage_status.intKey())) {
			for (JobStatusPreschoolEnum jobStatusEnum : JobStatusPreschoolEnum.values()) {
				TApplicantFrontPaperworkJpEntity frontJp = new TApplicantFrontPaperworkJpEntity();
				frontJp.setApplicantId(applicantId);
				frontJp.setOpId(loginUser.getId());
				frontJp.setRealInfo(jobStatusEnum.value());
				frontJp.setUpdateTime(new Date());
				frontJp.setCreateTime(new Date());
				frontJp.setType(workType);
				frontJp.setStatus(1);
				frontList.add(frontJp);
			}
		}

		return frontList;
	}

	public List<TApplicantVisaPaperworkJpEntity> toInsertVisaJp(Integer workType, Integer applicantId,
			HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		List<TApplicantVisaPaperworkJpEntity> visaList = new ArrayList<>();
		if (Util.eq(workType, JobStatusEnum.WORKING_STATUS.intKey())) {
			for (JobStatusWorkingEnum jobStatusEnum : JobStatusWorkingEnum.values()) {
				TApplicantVisaPaperworkJpEntity visaJp = new TApplicantVisaPaperworkJpEntity();
				visaJp.setApplicantId(applicantId);
				visaJp.setRealInfo(jobStatusEnum.value());
				visaJp.setOpId(loginUser.getId());
				visaJp.setUpdateTime(new Date());
				visaJp.setCreateTime(new Date());
				visaJp.setStatus(1);
				visaJp.setType(workType);
				visaList.add(visaJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.RETIREMENT_STATUS.intKey())) {
			for (JobStatusRetirementEnum jobStatusEnum : JobStatusRetirementEnum.values()) {
				TApplicantVisaPaperworkJpEntity visaJp = new TApplicantVisaPaperworkJpEntity();
				visaJp.setApplicantId(applicantId);
				visaJp.setRealInfo(jobStatusEnum.value());
				visaJp.setOpId(loginUser.getId());
				visaJp.setUpdateTime(new Date());
				visaJp.setCreateTime(new Date());
				visaJp.setStatus(1);
				visaJp.setType(workType);
				visaList.add(visaJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.FREELANCE_STATUS.intKey())) {
			for (JobStatusFreeEnum jobStatusEnum : JobStatusFreeEnum.values()) {
				TApplicantVisaPaperworkJpEntity visaJp = new TApplicantVisaPaperworkJpEntity();
				visaJp.setApplicantId(applicantId);
				visaJp.setRealInfo(jobStatusEnum.value());
				visaJp.setOpId(loginUser.getId());
				visaJp.setUpdateTime(new Date());
				visaJp.setCreateTime(new Date());
				visaJp.setStatus(1);
				visaJp.setType(workType);
				visaList.add(visaJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.student_status.intKey())) {
			for (JobStatusStudentEnum jobStatusEnum : JobStatusStudentEnum.values()) {
				TApplicantVisaPaperworkJpEntity visaJp = new TApplicantVisaPaperworkJpEntity();
				visaJp.setApplicantId(applicantId);
				visaJp.setRealInfo(jobStatusEnum.value());
				visaJp.setOpId(loginUser.getId());
				visaJp.setUpdateTime(new Date());
				visaJp.setCreateTime(new Date());
				visaJp.setStatus(1);
				visaJp.setType(workType);
				visaList.add(visaJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.Preschoolage_status.intKey())) {
			for (JobStatusPreschoolEnum jobStatusEnum : JobStatusPreschoolEnum.values()) {
				TApplicantVisaPaperworkJpEntity visaJp = new TApplicantVisaPaperworkJpEntity();
				visaJp.setApplicantId(applicantId);
				visaJp.setRealInfo(jobStatusEnum.value());
				visaJp.setOpId(loginUser.getId());
				visaJp.setUpdateTime(new Date());
				visaJp.setCreateTime(new Date());
				visaJp.setStatus(1);
				visaJp.setType(workType);
				visaList.add(visaJp);
			}
		}
		return visaList;
	}

	/**
	 * 取消订单
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object cancelOrder(Long orderid) {
		if (!Util.isEmpty(orderid)) {
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid);
			TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
			List<TOrderTravelplanJpEntity> travelplan = dbDao.query(TOrderTravelplanJpEntity.class,
					Cnd.where("orderid", "=", orderid), null);
			if (!Util.isEmpty(travelplan)) {
				dbDao.delete(travelplan);
			}
			List<TApplicantOrderJpEntity> applicantjp = dbDao.query(TApplicantOrderJpEntity.class,
					Cnd.where("orderid", "=", orderid), null);
			if (!Util.isEmpty(applicantjp)) {
				Integer[] applicantids = new Integer[applicantjp.size()];
				Integer[] applicantidjps = new Integer[applicantjp.size()];
				int i = 0;
				for (TApplicantOrderJpEntity tApplicantOrderJpEntity : applicantjp) {
					applicantids[i] = tApplicantOrderJpEntity.getOrderId();
					applicantidjps[i] = tApplicantOrderJpEntity.getId();
					i++;
				}
				//签证录入删除
				List<TApplicantVisaJpEntity> applicantvisajp = dbDao.query(TApplicantVisaJpEntity.class,
						Cnd.where("applicantId", "in", applicantidjps), null);
				if (!Util.isEmpty(applicantvisajp)) {
					dbDao.delete(applicantvisajp);
				}
				//删除前台实收
				List<TApplicantFrontPaperworkJpEntity> frontpaper = dbDao.query(TApplicantFrontPaperworkJpEntity.class,
						Cnd.where("applicantId", "in", applicantidjps), null);
				if (!Util.isEmpty(frontpaper)) {
					dbDao.delete(frontpaper);
				}
				//删除签证实收
				List<TApplicantVisaPaperworkJpEntity> visapaper = dbDao.query(TApplicantVisaPaperworkJpEntity.class,
						Cnd.where("applicantId", "in", applicantidjps), null);
				if (!Util.isEmpty(visapaper)) {
					dbDao.delete(visapaper);
				}
				//删除财产信息
				List<TApplicantWealthJpEntity> wealthjp = dbDao.query(TApplicantWealthJpEntity.class,
						Cnd.where("applicantId", "in", applicantidjps), null);
				if (!Util.isEmpty(wealthjp)) {
					dbDao.delete(wealthjp);
				}
				List<TApplicantWorkJpEntity> workjp = dbDao.query(TApplicantWorkJpEntity.class,
						Cnd.where("applicantId", "in", applicantidjps), null);
				if (!Util.isEmpty(workjp)) {
					dbDao.delete(workjp);
				}
				//删除护照信息
				List<TApplicantPassportEntity> passport = dbDao.query(TApplicantPassportEntity.class,
						Cnd.where("applicantId", "in", applicantids), null);
				if (!Util.isEmpty(passport)) {
					dbDao.delete(passport);
				}
				List<TApplicantEntity> applicant = dbDao.query(TApplicantEntity.class,
						Cnd.where("id", "in", applicantids), null);
				dbDao.delete(applicantjp);
				if (!Util.isEmpty(applicant)) {
					dbDao.delete(applicant);
				}
			}
			//删除日本订单表
			dbDao.delete(orderjp);
			//删除订单表
			dbDao.delete(orderinfo);

		}
		return null;
	}

	public Object addPassport(Integer orderid, HttpServletRequest request) {

		HttpSession session = request.getSession();
		Map<String, Object> result = Maps.newHashMap();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		result.put("userid", loginUser.getId());
		result.put("orderid", orderid);
		String localAddr = request.getServerName();
		request.getServerName();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		int localPort = request.getServerPort();
		result.put("localAddr", localAddr);
		result.put("localPort", localPort);
		result.put("websocketaddr", SIMPLE_WEBSOCKET_ADDR);
		//生成二维码
		if (Util.isEmpty(orderid)) {
			orderid = 0;
		}
		String qrCode = dataUpload(0, orderid, request);
		result.put("qrCode", qrCode);
		result.put("sessionid", session.getId());
		return result;

	}

	public Object changeVisatype(int orderid, int visatype) {
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid);
		orderjp.setVisaType(visatype);
		dbDao.update(orderjp);
		return null;
	}

	public Object getCustomerCitySelect(String cityname, String citytype, String exname, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Integer comid = loginCompany.getId();

		String sqlStr = "";

		if (Util.isEmpty(cityname)) {
			if (Util.eq("goDepartureCity", citytype)) {
				sqlStr = sqlManager.get("cityselectBygodeparturecity");
			}
			if (Util.eq("goArrivedCity", citytype)) {
				sqlStr = sqlManager.get("cityselectBygoarrivedcity");
			}
			if (Util.eq("returnDepartureCity", citytype)) {
				sqlStr = sqlManager.get("cityselectByreturndeparturecity");
			}
			if (Util.eq("returnArrivedCity", citytype)) {
				sqlStr = sqlManager.get("cityselectByreturnarrivedcity");
			}
			Sql applysql = Sqls.create(sqlStr);
			Cnd cnd = Cnd.NEW();
			cnd.and("tr.comId", "=", comid);
			cnd.groupBy("tc.city");
			cnd.orderBy("count", "DESC");
			List<Record> infoList = dbDao.query(applysql, cnd, null);
			if (infoList.size() > 4) {
				infoList = infoList.subList(0, 5);
			}
			return infoList;
		} else {
			List<TCityEntity> citySelect = new ArrayList<TCityEntity>();
			try {
				citySelect = dbDao.query(TCityEntity.class, Cnd.where("city", "like", Strings.trim(cityname) + "%"),
						null);
				//移除的城市
				TCityEntity exinfo = new TCityEntity();
				for (TCityEntity tCityEntity : citySelect) {
					if (!Util.isEmpty(exname) && tCityEntity.getCity().equals(exname)) {
						exinfo = tCityEntity;
					}
				}
				citySelect.remove(exinfo);
				if (citySelect.size() > 5) {
					citySelect = citySelect.subList(0, 5);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return citySelect;

		}

	}

	/**
	 * 记录没有正确翻译的汉字
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param characterStr
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toRecordCharacters(String characterStr) {
		TUncommoncharacterEntity fetch = dbDao.fetch(TUncommoncharacterEntity.class,
				Cnd.where("hanzi", "=", characterStr));
		if (Util.isEmpty(fetch)) {
			TUncommoncharacterEntity entity = new TUncommoncharacterEntity();
			entity.setHanzi(characterStr);
			entity.setCreatetime(new Date());
			dbDao.insert(entity);
		}
		return null;
	}

	public String dataUpload(int applyid, int orderid, HttpServletRequest request) {
		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		String page = "pages/Japan/upload/index/index";
		String scene = "";
		//因为小程序参数最长为32，所以参数尽量简化，o是订单id,u是userid,a是申请人id
		scene = "o=" + orderid + "&u=" + loginUser.getId() + "&a=" + applyid;
		System.out.println("scene:" + scene + "--------------");
		String accessToken = (String) getAccessToken();
		System.out.println("accessToken:" + accessToken + "=================");
		String url = createBCode(accessToken, page, scene);
		System.out.println("url:" + url);
		return url;
	}

	//获取accessToken
	public Object getAccessToken() {
		/*Map<String, Object> kvConfigProperties = SystemProperties.getKvConfigProperties();
		String wxConfigStr = String.valueOf(kvConfigProperties.get("T_APP_STAFF_CONF_WX_ID"));
		Long T_APP_STAFF_CONF_WX_ID = Long.valueOf(wxConfigStr);
		TConfWxEntity wx = dbDao.fetch(TConfWxEntity.class, T_APP_STAFF_CONF_WX_ID);
		String WX_APPID = wx.getAppid();
		String WX_APPSECRET = wx.getAppsecret();
		String WX_TOKENKEY = wx.getAccesstokenkey();*/

		String WX_APPID = "wx17bf0dde91fec324";
		String WX_APPSECRET = "6cc0aa2089c4ba020297fb23af31081a";
		String WX_TOKENKEY = "WX_ACCESS_TOKEN_2018";

		String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
		String requestUrl = accessTokenUrl.replace("APPID", WX_APPID).replace("APPSECRET", WX_APPSECRET);
		JSONObject result = HttpUtil.doGet(requestUrl);
		//redis中设置 access_token
		accessTokenUrl = result.getString("access_token");

		return accessTokenUrl;
	}

	public String createBCode(String accessToken, String page, String scene) {
		String url = WX_B_CODE_URL.replace("ACCESS_TOKEN", accessToken);
		Map<String, Object> param = new HashMap<>();
		param.put("path", page);
		param.put("scene", scene);
		param.put("width", "100");
		param.put("auto_color", false);
		Map<String, Object> line_color = new HashMap<>();
		line_color.put("r", 0);
		line_color.put("g", 0);
		line_color.put("b", 0);
		param.put("line_color", line_color);
		JSONObject json = JSONObject.parseObject(JSON.toJSONString(param));
		System.out.println("json:" + json.toString());
		//JSONObject json = JSONObject.fromObject(param);
		InputStream inputStream = toPostRequest(json.toString(), accessToken);

		String imgurl = CommonConstants.IMAGES_SERVER_ADDR + qiniuUploadService.uploadImage(inputStream, "", "");
		/*try {
			String imageUrl = httpPostWithJSON2(url, json.toString(), "xxx.png");
			return imageUrl;
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return imgurl;
	}

	//返回图片地址
	public String httpPostWithJSON2(String url, String json, String imagePath) throws Exception {
		String result = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");

		StringEntity se = new StringEntity(json);
		se.setContentType("application/json");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "UTF-8"));
		httpPost.setEntity(se);
		HttpResponse response = httpClient.execute(httpPost);
		if (response != null) {
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				InputStream instreams = resEntity.getContent();
				//上传至资源服务器生成url
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				byte[] bs = new byte[1024];//缓冲数组
				int len = -1;
				while ((len = instreams.read(bs)) != -1) {
					byteArrayOutputStream.write(bs, 0, len);
				}
				byte b[] = byteArrayOutputStream.toByteArray();
				byteArrayOutputStream.close();
				instreams.close();
				//将byte字节数组上传至资源服务器返回图片地址
				// ......
			}
		}
		httpPost.abort();
		return result;
	}

	//发送POST请求
	public InputStream toPostRequest(String json, String accessToken) {
		String host = "https://api.weixin.qq.com";
		String path = "/wxa/getwxacodeunlimit?access_token=" + accessToken;
		String method = "POST";
		String entityStr = "";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		HttpResponse response;
		InputStream inputStream = null;
		try {
			response = HttpUtils.doPost(host, path, method, headers, querys, json);
			inputStream = response.getEntity().getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*try {
			response = HttpUtils.doPost(host, path, method, headers, querys, json);
			entityStr = EntityUtils.toString(response.getEntity());
			System.out.println("POST请求返回的数据：" + entityStr);
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}*/
		return inputStream;
	}

	public Object hasApplyInfo(int applyid, int orderid, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Map<String, Object> result = Maps.newHashMap();
		if (Util.isEmpty(applyid) || applyid == 0) {
			//新建申请人表
			TApplicantEntity apply = new TApplicantEntity();
			apply.setOpId(loginUser.getId());
			apply.setIsSameInfo(IsYesOrNoEnum.YES.intKey());
			apply.setIsPrompted(IsYesOrNoEnum.NO.intKey());
			apply.setStatus(TrialApplicantStatusEnum.FIRSTTRIAL.intKey());
			apply.setCreateTime(new Date());
			TApplicantEntity insertApply = dbDao.insert(apply);
			applyid = insertApply.getId();
			//新建日本申请人表
			TApplicantOrderJpEntity applicantjp = new TApplicantOrderJpEntity();
			if (Util.isEmpty(orderid) || orderid == 0) {
				Map<String, Integer> generrateorder = generrateorder(loginUser, loginCompany);
				orderid = generrateorder.get("orderjpid");
			}

			//设置主申请人信息
			List<TApplicantOrderJpEntity> orderapplicant = dbDao.query(TApplicantOrderJpEntity.class,
					Cnd.where("orderId", "=", orderid), null);
			if (!Util.isEmpty(orderapplicant) && orderapplicant.size() >= 1) {

				applicantjp.setIsMainApplicant(IsYesOrNoEnum.NO.intKey());
				TApplicantOrderJpEntity mainApply = dbDao.fetch(TApplicantOrderJpEntity.class,
						Cnd.where("orderId", "=", orderid).and("isMainApplicant", "=", IsYesOrNoEnum.YES.intKey()));
				apply.setMainId(mainApply.getApplicantId());
				dbDao.update(apply);
			} else {
				//设置为主申请人
				applicantjp.setIsMainApplicant(IsYesOrNoEnum.YES.intKey());
				apply.setMainId(applyid);
				dbDao.update(apply);
			}

			applicantjp.setOrderId(orderid);
			applicantjp.setApplicantId(applyid);
			applicantjp.setBaseIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setPassIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setVisaIsCompleted(IsYesOrNoEnum.NO.intKey());
			TApplicantOrderJpEntity insertappjp = dbDao.insert(applicantjp);

			//日本工作信息
			TApplicantWorkJpEntity workJp = new TApplicantWorkJpEntity();
			workJp.setApplicantId(insertappjp.getId());
			workJp.setCreateTime(new Date());
			workJp.setOpId(loginUser.getId());
			dbDao.insert(workJp);
			//护照信息
			TApplicantPassportEntity passport = new TApplicantPassportEntity();
			passport.setIssuedOrganization("公安部出入境管理局");
			passport.setIssuedOrganizationEn("MPS Exit&Entry Adiministration");
			passport.setApplicantId(applyid);
			dbDao.insert(passport);

			TApplicantVisaOtherInfoEntity visaother = new TApplicantVisaOtherInfoEntity();
			visaother.setApplicantid(insertappjp.getId());
			visaother.setHotelname("参照'赴日予定表'");
			visaother.setVouchname("参照'身元保证书'");
			visaother.setInvitename("同上");
			visaother.setTraveladvice("推荐");
			dbDao.insert(visaother);
		}
		result.put("applyid", applyid);
		result.put("orderid", orderid);
		return result;
	}

	public Object isSamewithMainapply(int orderid) {
		Map<String, Object> result = Maps.newHashMap();
		String applicantSqlstr = sqlManager.get("getMainapplyinfo");
		Sql applicantSql = Sqls.create(applicantSqlstr);
		applicantSql.setParam("id", orderid);
		Record mainapplyinfo = dbDao.fetch(applicantSql);
		result.put("maininfo", mainapplyinfo);

		return result;
	}

	public Object autoCalculateStaydays(Date laststartdate, Date lastreturndate) {
		int daysBetween = DateUtil.daysBetween(laststartdate, lastreturndate);
		return daysBetween + 1;
	}

}
