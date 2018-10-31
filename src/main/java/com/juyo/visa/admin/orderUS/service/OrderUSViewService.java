/**
 * SaleViewService.java
 * com.juyo.visa.admin.sale.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.orderUS.service;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.springframework.web.socket.TextMessage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.mail.service.MailService;
import com.juyo.visa.admin.order.entity.PassportJsonEntity;
import com.juyo.visa.admin.order.entity.TIdcardEntity;
import com.juyo.visa.admin.order.form.RecognitionForm;
import com.juyo.visa.admin.orderUS.entity.AutofillSearchJsonEntity;
import com.juyo.visa.admin.orderUS.entity.USStaffJsonEntity;
import com.juyo.visa.admin.orderUS.form.OrderUSListDataForm;
import com.juyo.visa.admin.simulate.form.JapanSimulatorForm;
import com.juyo.visa.admin.visajp.util.TemplateUtil;
import com.juyo.visa.admin.weixinToken.service.WeXinTokenViewService;
import com.juyo.visa.common.base.SystemProperties;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.AlredyVisaTypeEnum;
import com.juyo.visa.common.enums.CompanyTypeEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.JapanPrincipalChangeEnum;
import com.juyo.visa.common.enums.PrepareMaterialsEnum_JP;
import com.juyo.visa.common.enums.TravelpurposeEnum;
import com.juyo.visa.common.enums.USOrderStatusEnum;
import com.juyo.visa.common.enums.UserLoginEnum;
import com.juyo.visa.common.enums.orderUS.DistrictEnum;
import com.juyo.visa.common.enums.orderUS.IsPayedEnum;
import com.juyo.visa.common.enums.orderUS.USOrderListStatusEnum;
import com.juyo.visa.common.enums.visaProcess.TAppStaffCredentialsEnum;
import com.juyo.visa.common.enums.visaProcess.VisaUSStatesEnum;
import com.juyo.visa.common.enums.visaProcess.YesOrNoEnum;
import com.juyo.visa.common.msgcrypt.AesException;
import com.juyo.visa.common.msgcrypt.WXBizMsgCrypt;
import com.juyo.visa.common.ocr.HttpUtils;
import com.juyo.visa.common.ocr.Input;
import com.juyo.visa.common.ocr.RecognizeData;
import com.juyo.visa.common.util.PinyinTool;
import com.juyo.visa.common.util.PinyinTool.Type;
import com.juyo.visa.common.util.SpringContextUtil;
import com.juyo.visa.common.util.TranslateUtil;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TAppStaffContactpointEntity;
import com.juyo.visa.entities.TAppStaffCredentialsEntity;
import com.juyo.visa.entities.TAppStaffEventsEntity;
import com.juyo.visa.entities.TAppStaffFamilyinfoEntity;
import com.juyo.visa.entities.TAppStaffOrderUsEntity;
import com.juyo.visa.entities.TAppStaffPassportEntity;
import com.juyo.visa.entities.TAppStaffPrevioustripinfoEntity;
import com.juyo.visa.entities.TAppStaffTravelcompanionEntity;
import com.juyo.visa.entities.TAppStaffVcodeEntity;
import com.juyo.visa.entities.TAppStaffVisaUsEntity;
import com.juyo.visa.entities.TAppStaffWorkEducationTrainingEntity;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.TCompanyCustomerMapEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TFlightEntity;
import com.juyo.visa.entities.TOrderUsEntity;
import com.juyo.visa.entities.TOrderUsFollowupEntity;
import com.juyo.visa.entities.TOrderUsInfoEntitiy;
import com.juyo.visa.entities.TOrderUsLogsEntity;
import com.juyo.visa.entities.TOrderUsTravelinfoEntity;
import com.juyo.visa.entities.TUsStateEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.OrderUpdateForm;
import com.juyo.visa.forms.TAppStaffVisaUsAddForm;
import com.juyo.visa.forms.TAppStaffVisaUsUpdateForm;
import com.juyo.visa.websocket.SimpleSendInfoWSHandler;
import com.juyo.visa.websocket.USListWSHandler;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.redis.RedisDao;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.we.business.sms.SMSService;
import com.we.business.sms.impl.HuaxinSMSServiceImpl;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 	 
 */
@IocBean
public class OrderUSViewService extends BaseService<TOrderUsEntity> {

	@Inject
	private RedisDao redisDao;

	@Inject
	private AutofillService autofillService;

	@Inject
	private MailService mailService;

	@Inject
	private WeXinTokenViewService weXinTokenViewService;

	@Inject
	private UploadService qiniuUploadService;//文件上传

	private final static String SMS_SIGNATURE = "【优悦签】";
	private final static Integer US_YUSHANG_COMID = 68;
	//活动id，默认为1
	private final static Integer EVENTID = 1;
	private final static Integer DEFAULT_IS_NO = YesOrNoEnum.NO.intKey();
	private final static Integer DEFAULT_SELECT = IsYesOrNoEnum.NO.intKey();

	//美国接口相关
	private final static String ENCODINGAESKEY = "jllZTM3ZWEzZGI1NGQ5NGI3MTc4NDNhNzAzODE5NTYt";
	private final static String TOKEN = "ODBiOGIxNDY4NjdlMzc2Yg==";
	private final static String APPID = "jhhMThiZjM1ZGQ2Y";

	//订单列表页连接websocket的地址
	private static final String USLIST_WEBSPCKET_ADDR = "uslistwebsocket";

	private USListWSHandler uslistwebsocket = (USListWSHandler) SpringContextUtil.getBean("usListHander",
			USListWSHandler.class);

	private SimpleSendInfoWSHandler simplesendinfosocket = (SimpleSendInfoWSHandler) SpringContextUtil.getBean(
			"mySimpleSendInfoWSHandler", SimpleSendInfoWSHandler.class);

	/**
	 * 列表页下拉框内容获取
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toList(HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("searchStatus", EnumUtil.enum2(USOrderListStatusEnum.class));
		result.put("cityid", EnumUtil.enum2(DistrictEnum.class));
		result.put("ispayed", EnumUtil.enum2(IsPayedEnum.class));
		//websocket
		String localAddr = request.getServerName();
		int localPort = request.getServerPort();
		result.put("localAddr", localAddr);
		result.put("localPort", localPort);
		result.put("websocketaddr", USLIST_WEBSPCKET_ADDR);
		return result;
	}

	/**
	 * 获取美国订单列表页数据
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object listData(OrderUSListDataForm form, HttpSession session) {
		Map<String, Object> result = Maps.newHashMap();
		//获取当前公司
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//获取当前用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		String comidsString = "";
		List<TCompanyCustomerMapEntity> coms = dbDao.query(TCompanyCustomerMapEntity.class,
				Cnd.where("belongComId", "=", loginCompany.getId()), null);
		for (TCompanyCustomerMapEntity tCompanyCustomerMapEntity : coms) {
			comidsString += tCompanyCustomerMapEntity.getBigCustomerId() + ",";
		}
		String comids = comidsString + "" + loginCompany.getId();
		form.setUserid(loginUser.getId());
		form.setComids(comids);
		form.setCompanyid(loginCompany.getId());
		form.setAdminId(loginCompany.getAdminId());
		Sql sql = form.sql(sqlManager);

		Integer pageNumber = form.getPageNumber();
		Integer pageSize = form.getPageSize();

		Pager pager = new OffsetPager((pageNumber - 1) * pageSize, pageSize);
		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));
		sql.setPager(pager);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		//主sql数据
		List<Record> list = (List<Record>) sql.getResult();
		//格式化面试时间
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (Record record : list) {
			Integer orderid = (Integer) record.get("orderid");
			Object payObj = record.get("ispayed");
			Object cityObj = record.get("cityid");

			//面签时间格式化
			if (!Util.isEmpty(record.get("interviewdate"))) {
				Date interviewdate = (Date) record.get("interviewdate");
				record.put("interviewdate", sdf.format(interviewdate));
			}
			//领区
			if (!Util.isEmpty(cityObj)) {
				int cityid = (int) cityObj;
				for (DistrictEnum district : DistrictEnum.values()) {
					if (cityid == district.intKey()) {
						record.set("cityid", district.value());
					}
				}
			}
			//是否付款
			if (!Util.isEmpty(payObj)) {
				int ispayed = (int) payObj;
				for (IsPayedEnum pay : IsPayedEnum.values()) {
					if (ispayed == pay.intKey()) {
						record.set("ispayed", pay.value());
					}
				}
			}
			String sqlStr = sqlManager.get("orderUS_listData_staff");
			Sql applysql = Sqls.create(sqlStr);
			List<Record> records = dbDao.query(applysql, Cnd.where("tasou.orderid", "=", orderid), null);
			record.put("everybodyInfo", records);

			//订单状态
			int orderStatus = (int) record.get("orderstatus");
			for (USOrderListStatusEnum statusEnum : USOrderListStatusEnum.values()) {
				if (!Util.isEmpty(orderStatus) && orderStatus == statusEnum.intKey()) {
					record.set("orderstatus", statusEnum.value());
				}
				/*else if (orderStatus == JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.intKey()) {
					record.set("orderstatus", JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.value());
				}*/
			}

		}
		result.put("orderUSListData", list);
		result.put("pageTotal", pager.getPageCount());
		result.put("pageListCount", list.size());
		return result;
	}

	public Object newlistData(OrderUSListDataForm form, HttpSession session) {
		Map<String, Object> result = Maps.newHashMap();
		//获取当前公司
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//获取当前用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		String comidsString = "";
		List<TCompanyCustomerMapEntity> coms = dbDao.query(TCompanyCustomerMapEntity.class,
				Cnd.where("belongComId", "=", loginCompany.getId()), null);
		for (TCompanyCustomerMapEntity tCompanyCustomerMapEntity : coms) {
			comidsString += tCompanyCustomerMapEntity.getBigCustomerId() + ",";
		}
		String comids = comidsString + "" + loginCompany.getId();
		form.setUserid(loginUser.getId());
		form.setComids(comids);
		form.setCompanyid(loginCompany.getId());
		form.setAdminId(loginCompany.getAdminId());
		Sql sql = form.sql(sqlManager);

		Integer pageNumber = form.getPageNumber();
		Integer pageSize = form.getPageSize();

		Pager pager = new OffsetPager(0, pageNumber * 10);
		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));
		sql.setPager(pager);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		//主sql数据
		List<Record> list = (List<Record>) sql.getResult();
		//格式化面试时间
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (Record record : list) {
			Integer orderid = (Integer) record.get("orderid");
			Object payObj = record.get("ispayed");
			Object cityObj = record.get("cityid");

			//面签时间格式化
			if (!Util.isEmpty(record.get("interviewdate"))) {
				Date interviewdate = (Date) record.get("interviewdate");
				record.put("interviewdate", sdf.format(interviewdate));
			}
			//领区
			if (!Util.isEmpty(cityObj)) {
				int cityid = (int) cityObj;
				for (DistrictEnum district : DistrictEnum.values()) {
					if (cityid == district.intKey()) {
						record.set("cityid", district.value());
					}
				}
			}
			//是否付款
			if (!Util.isEmpty(payObj)) {
				int ispayed = (int) payObj;
				for (IsPayedEnum pay : IsPayedEnum.values()) {
					if (ispayed == pay.intKey()) {
						record.set("ispayed", pay.value());
					}
				}
			}
			String sqlStr = sqlManager.get("orderUS_listData_staff");
			Sql applysql = Sqls.create(sqlStr);
			List<Record> records = dbDao.query(applysql, Cnd.where("tasou.orderid", "=", orderid), null);
			record.put("everybodyInfo", records);

			//订单状态
			int orderStatus = (int) record.get("orderstatus");
			for (USOrderListStatusEnum statusEnum : USOrderListStatusEnum.values()) {
				if (!Util.isEmpty(orderStatus) && orderStatus == statusEnum.intKey()) {
					record.set("orderstatus", statusEnum.value());
				}
				/*else if (orderStatus == JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.intKey()) {
					record.set("orderstatus", JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.value());
				}*/
			}

		}
		result.put("orderUSListData", list);
		result.put("pageTotal", pager.getPageCount());
		result.put("pageListCount", list.size());
		return result;
	}

	/**
	 * 获取美国订单详情页信息
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid 订单id
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getOrderUSDetail(int orderid, int addOrder, HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		//addOrder=1时是从下单跳转
		result.put("isaddorder", addOrder);
		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Integer comid = loginCompany.getId();
		result.put("company", loginCompany);

		//领区下拉
		result.put("cityidenum", EnumUtil.enum2(DistrictEnum.class));

		//是否付款下拉
		result.put("ispayedenum", EnumUtil.enum2(IsPayedEnum.class));

		//orderid=0时为下单,下单创建人员表以及相关表
		if (addOrder == 1) {
			//创建相关表
			Map<String, Object> map = (Map<String, Object>) insertSomeInfo(result, comid);

			return map;
		}
		result.put("orderid", orderid);

		//格式化日期
		DateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");

		//订单信息
		TOrderUsEntity orderus = dbDao.fetch(TOrderUsEntity.class, orderid);
		result.put("orderinfo", orderus);
		TAppStaffOrderUsEntity orderUsEntity = dbDao.fetch(TAppStaffOrderUsEntity.class,
				Cnd.where("orderid", "=", orderid));

		//大客户公司名称
		if (!Util.isEmpty(orderus.getBigcustomername())) {
			Long bigcustomername = Long.valueOf(orderus.getBigcustomername());
			TCompanyEntity bigcom = dbDao.fetch(TCompanyEntity.class, bigcustomername);
			result.put("bigcom", bigcom);
		}

		//基本信息
		TAppStaffOrderUsEntity staffOrder = dbDao.fetch(TAppStaffOrderUsEntity.class,
				Cnd.where("orderid", "=", orderid));
		TAppStaffBasicinfoEntity basicinfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, staffOrder.getStaffid()
				.longValue());
		result.put("basicinfo", basicinfo);
		if (!Util.isEmpty(basicinfo.getInterviewdate())) {
			result.put("Interviewdate", sdf2.format(basicinfo.getInterviewdate()));
		}
		Integer staffid = basicinfo.getId();
		Integer status = orderus.getStatus();

		//二寸照片从人员证件信息表中取
		TAppStaffCredentialsEntity twoinchphoto = dbDao.fetch(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "=", TAppStaffCredentialsEnum.TWOINCHPHOTO.intKey()));
		if (!Util.isEmpty(twoinchphoto)) {
			result.put("twoinchphoto", twoinchphoto);
		}

		//订单状态
		for (USOrderListStatusEnum statusenum : USOrderListStatusEnum.values()) {
			if (status == statusenum.intKey()) {
				result.put("orderstatus", statusenum.value());
			}
			/*else if (status == JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.intKey()) {
				result.put("orderstatus", JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.value());
			}*/
		}

		//护照信息
		TAppStaffPassportEntity passport = dbDao.fetch(TAppStaffPassportEntity.class,
				Cnd.where("staffid", "=", staffid));
		//姓名拼音处理
		/*if (!Util.isEmpty(passport.getFirstnameen())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(passport.getFirstnameen());
			result.put("firstnameen", sb.toString());
		}
		if (!Util.isEmpty(passport.getLastnameen())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(passport.getLastnameen());
			result.put("lastnameen", sb.toString());
		}*/
		/*if (!Util.isEmpty(passport.getLastnameen())) {
			result.put("lastnameen", passport.getLastnameen().substring(1));
		}*/
		if (!Util.isEmpty(passport.getBirthday())) {
			result.put("birthday", sdf.format(passport.getBirthday()));
		}
		result.put("passport", passport);

		//出行信息
		TOrderUsTravelinfoEntity orderTravelInfo = dbDao.fetch(TOrderUsTravelinfoEntity.class,
				Cnd.where("orderid", "=", orderid));

		//获取用户资料信息
		TAppStaffOrderUsEntity stafforderUsEntity = dbDao.fetch(TAppStaffOrderUsEntity.class,
				Cnd.where("orderid", "=", orderid));
		if (!Util.isEmpty(orderUsEntity)) {

			//获取用户基本信息
			TAppStaffBasicinfoEntity basicinfoEntity = dbDao.fetch(TAppStaffBasicinfoEntity.class,
					Cnd.where("id", "=", orderUsEntity.getStaffid()));
			result.put("basicinfo", basicinfoEntity);
			//获取该用户的资料类型
			String sqlStr = sqlManager.get("t_app_paperwork_US_info");
			Sql applysql = Sqls.create(sqlStr);
			Cnd cnd = Cnd.NEW();
			cnd.and("staffid", "=", orderUsEntity.getStaffid());
			List<Record> infoList = dbDao.query(applysql, cnd, null);
			for (Record appRecord : infoList) {
				int type = appRecord.getInt("type");
				for (PrepareMaterialsEnum_JP pmEnum : PrepareMaterialsEnum_JP.values())
					if (!Util.isEmpty(type) && type == pmEnum.intKey()) {
						appRecord.set("type", pmEnum.value());
						break;
					}
			}
			StringBuffer str = new StringBuffer();
			for (Record record : infoList) {
				if (record.getString("type") != null) {
					str.append(record.getString("type"));
					str.append("、");
				}
			}
			result.put("realinfo", str);
		} else
			result.put("realinfo", null);
		List<Record> staffSummaryInfoList = (List<Record>) getStaffSummaryInfo(orderid);
		if (!Util.isEmpty(staffSummaryInfoList)) {
			result.put("summaryInfo", staffSummaryInfoList.get(0));
		} else {
			result.put("summaryInfo", null);
		}
		TOrderUsInfoEntitiy orderInfoEntity = (TOrderUsInfoEntitiy) getOrderInfo(orderid);
		result.put("orderInfo", orderInfoEntity);
		if (!Util.isEmpty(orderTravelInfo)) {

			String travelpurpose = "";
			if (!Util.isEmpty(orderTravelInfo)) {
				travelpurpose = orderTravelInfo.getTravelpurpose();

			}
			if (!Util.isEmpty(travelpurpose)) {
				String travelpurposeString = TravelpurposeEnum.getValue(travelpurpose).getValue();
				//获取出行目的
				orderTravelInfo.setTravelpurpose(travelpurposeString);
			}
			if (!Util.isEmpty(orderTravelInfo.getGodeparturecity())) {
				TCityEntity gocity = dbDao.fetch(TCityEntity.class,
						Cnd.where("id", "=", orderTravelInfo.getGodeparturecity()));
				orderInfoEntity.setGoDepartureCity(gocity.getCity());
			}
			if (!Util.isEmpty(orderTravelInfo.getGoArrivedCity())) {
				/*TCityEntity gocity = dbDao.fetch(TCityEntity.class,
						Cnd.where("id", "=", orderTravelInfo.getGoArrivedCity()));
				orderInfoEntity.setGoArrivedCity((gocity.getCity()));*/
				TUsStateEntity gocity = dbDao.fetch(TUsStateEntity.class, orderTravelInfo.getGoArrivedCity()
						.longValue());
				orderInfoEntity.setGoArrivedCity((gocity.getStatecn()));
			}
			if (!Util.isEmpty(orderTravelInfo.getReturnDepartureCity())) {
				/*TCityEntity gocity = dbDao.fetch(TCityEntity.class,
						Cnd.where("id", "=", orderTravelInfo.getReturnDepartureCity()));
				orderInfoEntity.setReturnDepartureCity(gocity.getCity());*/
				TUsStateEntity gocity = dbDao.fetch(TUsStateEntity.class, orderTravelInfo.getGoArrivedCity()
						.longValue());
				orderInfoEntity.setReturnDepartureCity((gocity.getStatecn()));
			}
			if (!Util.isEmpty(orderTravelInfo.getReturnArrivedCity())) {
				TCityEntity gocity = dbDao.fetch(TCityEntity.class,
						Cnd.where("id", "=", orderTravelInfo.getReturnArrivedCity()));
				orderInfoEntity.setReturnArrivedCity(gocity.getCity());
			}
			result.put("travelInfo", orderTravelInfo);

			//获取航班信息
			TFlightEntity goFlightEntity = dbDao.fetch(TFlightEntity.class,
					Cnd.where("flightnum", "=", orderTravelInfo.getGoFlightNum()));
			TFlightEntity returnFlightEntity = dbDao.fetch(TFlightEntity.class,
					Cnd.where("flightnum", "=", orderTravelInfo.getReturnFlightNum()));
			if (!Util.isEmpty(goFlightEntity)) {
				result.put("goFlightInfo", goFlightEntity);
			} else {
				result.put("goFlightInfo", null);
			}

			if (!Util.isEmpty(returnFlightEntity)) {
				result.put("returnFlightInfo", returnFlightEntity);
			} else {
				result.put("returnFlightInfo", null);
			}
		}

		//送签美国州
		Map<Integer, String> stateMap = new HashMap<Integer, String>();
		for (VisaUSStatesEnum e : VisaUSStatesEnum.values()) {
			stateMap.put(e.intKey(), e.value());
		}
		result.put("state", stateMap);

		//跟进信息
		String followSqlstr = sqlManager.get("orderUS_getFollows");
		Sql followSql = Sqls.create(followSqlstr);
		followSql.setParam("id", orderid);
		List<Record> followList = dbDao.query(followSql, null, null);
		if (!Util.isEmpty(followList)) {
			for (Record record : followList) {
				if (!Util.isEmpty(record.get("solveid"))) {
					int solveid = (int) record.get("solveid");
					TUserEntity solveUser = dbDao.fetch(TUserEntity.class, solveid);
					record.set("solveid", solveUser.getName());
				}
				if (!Util.isEmpty(record.get("solvetime"))) {
					Date solvetime = (Date) record.get("solvetime");
					record.set("solvetime", format.format(solvetime));
				}
				if (!Util.isEmpty(record.get("createtime"))) {
					Date solvetime = (Date) record.get("createtime");
					record.set("createtime", format.format(solvetime));
				}
				String str = (String) record.get("content");
				record.set("content", str.replace("\n", "<br/>"));
			}
		}
		result.put("followinfo", followList);

		return result;
	}

	public Object getBigcustomerSelect(String bigcustomer) {
		List<TCompanyEntity> query = dbDao.query(
				TCompanyEntity.class,
				Cnd.where("name", "like", "%" + Strings.trim(bigcustomer) + "%").and("comType", "=",
						CompanyTypeEnum.BIGCUSTOMER.intKey()), null);
		return query;
	}

	public Object insertSomeInfo(Map<String, Object> result, Integer comid) {
		//创建人员信息表
		TAppStaffBasicinfoEntity basic = new TAppStaffBasicinfoEntity();
		basic.setIsidentificationnumberapply(IsYesOrNoEnum.YES.intKey());
		basic.setIsidentificationnumberapplyen(IsYesOrNoEnum.YES.intKey());
		basic.setIssecuritynumberapply(IsYesOrNoEnum.YES.intKey());
		basic.setIssecuritynumberapplyen(IsYesOrNoEnum.YES.intKey());
		basic.setIstaxpayernumberapply(IsYesOrNoEnum.YES.intKey());
		basic.setIstaxpayernumberapplyen(IsYesOrNoEnum.YES.intKey());
		basic.setComid(comid);
		basic.setCreatetime(new Date());
		basic.setUpdatetime(new Date());
		TAppStaffBasicinfoEntity basicDB = dbDao.insert(basic);
		result.put("basicinfo", basicDB);
		Integer staffId = basicDB.getId();

		//人员报名活动
		TAppStaffEventsEntity staffEventEntity = new TAppStaffEventsEntity();
		staffEventEntity.setEventsId(EVENTID);
		staffEventEntity.setStaffId(staffId);
		TAppStaffEventsEntity insertEntity = dbDao.insert(staffEventEntity);

		//创建护照信息表
		TAppStaffPassportEntity passport = new TAppStaffPassportEntity();
		passport.setStaffid(staffId);
		passport.setCreatetime(new Date());
		passport.setUpdatetime(new Date());
		TAppStaffPassportEntity passportDB = dbDao.insert(passport);
		result.put("passport", passportDB);

		//签证信息的添加
		//旅伴信息
		TAppStaffTravelcompanionEntity travelCompanionInfo = new TAppStaffTravelcompanionEntity();
		travelCompanionInfo.setStaffid(staffId);
		travelCompanionInfo.setIspart(DEFAULT_IS_NO);
		travelCompanionInfo.setIstravelwithother(DEFAULT_IS_NO);
		travelCompanionInfo.setIsparten(DEFAULT_IS_NO);
		travelCompanionInfo.setIstravelwithotheren(DEFAULT_IS_NO);
		dbDao.insert(travelCompanionInfo);
		//以前的美国旅游信息
		TAppStaffPrevioustripinfoEntity previUSTripInfo = new TAppStaffPrevioustripinfoEntity();
		previUSTripInfo.setStaffid(staffId);
		previUSTripInfo.setHasbeeninus(DEFAULT_IS_NO); //是否去过美国
		previUSTripInfo.setHasdriverlicense(DEFAULT_IS_NO);//是否有美国驾照
		previUSTripInfo.setIsissuedvisa(DEFAULT_IS_NO);
		previUSTripInfo.setIsapplyingsametypevisa(DEFAULT_IS_NO);
		previUSTripInfo.setIssamecountry(DEFAULT_IS_NO);
		previUSTripInfo.setIslost(DEFAULT_IS_NO);
		previUSTripInfo.setIstenprinted(DEFAULT_IS_NO);
		previUSTripInfo.setIscancelled(DEFAULT_IS_NO);
		previUSTripInfo.setIsrefused(DEFAULT_IS_NO);
		previUSTripInfo.setIslegalpermanentresident(DEFAULT_IS_NO);
		previUSTripInfo.setIsfiledimmigrantpetition(DEFAULT_IS_NO);

		previUSTripInfo.setHasbeeninusen(DEFAULT_IS_NO);
		previUSTripInfo.setHasdriverlicenseen(DEFAULT_IS_NO);
		previUSTripInfo.setIsissuedvisaen(DEFAULT_IS_NO);
		previUSTripInfo.setIsapplyingsametypevisaen(DEFAULT_IS_NO);
		previUSTripInfo.setIssamecountryen(DEFAULT_IS_NO);
		previUSTripInfo.setIslosten(DEFAULT_IS_NO);
		previUSTripInfo.setIstenprinteden(DEFAULT_IS_NO);
		previUSTripInfo.setIscancelleden(DEFAULT_IS_NO);
		previUSTripInfo.setIsrefuseden(DEFAULT_IS_NO);
		previUSTripInfo.setIslegalpermanentresidenten(DEFAULT_IS_NO);
		previUSTripInfo.setIsfiledimmigrantpetitionen(DEFAULT_IS_NO);
		dbDao.insert(previUSTripInfo);

		//美国联络点
		TAppStaffContactpointEntity contactPointInfo = new TAppStaffContactpointEntity();
		contactPointInfo.setStaffid(staffId);
		contactPointInfo.setRalationship(DEFAULT_SELECT);
		contactPointInfo.setState(DEFAULT_SELECT);
		contactPointInfo.setRalationshipen(DEFAULT_SELECT);
		contactPointInfo.setStateen(DEFAULT_SELECT);
		dbDao.insert(contactPointInfo);

		//家庭信息
		TAppStaffFamilyinfoEntity familyInfo = new TAppStaffFamilyinfoEntity();
		familyInfo.setStaffid(staffId);
		familyInfo.setIsfatherinus(DEFAULT_IS_NO);
		familyInfo.setIsmotherinus(DEFAULT_IS_NO);
		familyInfo.setHasimmediaterelatives(DEFAULT_IS_NO);
		familyInfo.setHasotherrelatives(DEFAULT_IS_NO);
		familyInfo.setIsknowspousecity(DEFAULT_IS_NO);

		familyInfo.setFatherstatus(DEFAULT_SELECT);
		familyInfo.setMotherstatus(DEFAULT_SELECT);
		familyInfo.setFatherstatusen(DEFAULT_SELECT);
		familyInfo.setMotherstatusen(DEFAULT_SELECT);

		familyInfo.setIsfatherinusen(DEFAULT_IS_NO);
		familyInfo.setIsmotherinusen(DEFAULT_IS_NO);
		familyInfo.setHasimmediaterelativesen(DEFAULT_IS_NO);
		familyInfo.setHasotherrelativesen(DEFAULT_IS_NO);
		familyInfo.setIsknowspousecityen(DEFAULT_IS_NO);

		familyInfo.setSpousenationality(DEFAULT_SELECT);
		familyInfo.setSpousenationalityen(DEFAULT_SELECT);
		familyInfo.setSpousecountry(DEFAULT_SELECT);
		familyInfo.setSpousecountryen(DEFAULT_SELECT);
		familyInfo.setSpouseaddress(DEFAULT_SELECT);
		familyInfo.setSpouseaddressen(DEFAULT_SELECT);

		dbDao.insert(familyInfo);

		//工作/教育/培训信息 
		TAppStaffWorkEducationTrainingEntity workEducationInfo = new TAppStaffWorkEducationTrainingEntity();
		workEducationInfo.setStaffid(staffId);

		workEducationInfo.setOccupation(DEFAULT_SELECT);
		workEducationInfo.setOccupationen(DEFAULT_SELECT);
		workEducationInfo.setCountry(DEFAULT_SELECT);
		workEducationInfo.setCountryen(DEFAULT_SELECT);

		workEducationInfo.setIsemployed(DEFAULT_IS_NO);
		workEducationInfo.setIssecondarylevel(DEFAULT_IS_NO);
		workEducationInfo.setIsclan(DEFAULT_IS_NO);
		workEducationInfo.setIstraveledanycountry(DEFAULT_IS_NO);
		workEducationInfo.setIsworkedcharitableorganization(DEFAULT_IS_NO);
		workEducationInfo.setHasspecializedskill(DEFAULT_IS_NO);
		workEducationInfo.setHasservedinmilitary(DEFAULT_IS_NO);
		workEducationInfo.setIsservedinrebelgroup(DEFAULT_IS_NO);

		workEducationInfo.setIsemployeden(DEFAULT_IS_NO);
		workEducationInfo.setIssecondarylevelen(DEFAULT_IS_NO);
		workEducationInfo.setIsclanen(DEFAULT_IS_NO);
		workEducationInfo.setIstraveledanycountryen(DEFAULT_IS_NO);
		workEducationInfo.setIsworkedcharitableorganizationen(DEFAULT_IS_NO);
		workEducationInfo.setHasspecializedskillen(DEFAULT_IS_NO);
		workEducationInfo.setHasservedinmilitaryen(DEFAULT_IS_NO);
		workEducationInfo.setIsservedinrebelgroupen(DEFAULT_IS_NO);
		dbDao.insert(workEducationInfo);

		TOrderUsEntity orderUs = new TOrderUsEntity();
		String orderNum = generateOrderNumByDate();
		Date nowDate = DateUtil.nowDate();
		orderUs.setOrdernumber(orderNum);
		orderUs.setComid(comid);
		orderUs.setStatus(USOrderStatusEnum.PLACE_ORDER.intKey());//下单
		orderUs.setCreatetime(nowDate);
		orderUs.setUpdatetime(nowDate);
		TOrderUsEntity order = dbDao.insert(orderUs);

		//更新人员-订单关系表
		if (!Util.isEmpty(order)) {
			Integer orderId = order.getId();
			TAppStaffOrderUsEntity staffOrder = new TAppStaffOrderUsEntity();
			staffOrder.setOrderid(orderId);
			staffOrder.setStaffid(staffId);
			dbDao.insert(staffOrder);
			result.put("orderid", orderId);

			//跟进信息
			//格式化日期
			DateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
			String followSqlstr = sqlManager.get("orderUS_getFollows");
			Sql followSql = Sqls.create(followSqlstr);
			followSql.setParam("id", orderId);
			List<Record> followList = dbDao.query(followSql, null, null);
			if (Util.isEmpty(followList)) {
				for (Record record : followList) {
					if (!Util.isEmpty(record.get("solveid"))) {
						int solveid = (int) record.get("solveid");
						TUserEntity solveUser = dbDao.fetch(TUserEntity.class, solveid);
						record.set("solveid", solveUser.getName());
					}
					if (!Util.isEmpty(record.get("solvetime"))) {
						Date solvetime = (Date) record.get("solvetime");
						record.set("solvetime", format.format(solvetime));
					}
					if (!Util.isEmpty(record.get("createtime"))) {
						Date solvetime = (Date) record.get("createtime");
						record.set("createtime", format.format(solvetime));
					}

				}
			}
			result.put("followinfo", followList);
		}

		return result;
	}

	/*
	 * 获取订单详情
	 * 
	 */
	public Object getOrderInfo(Integer orderid) {
		TOrderUsInfoEntitiy entitiy = dbDao.fetch(TOrderUsInfoEntitiy.class, Cnd.where("id", "=", orderid));
		return entitiy;
	}

	/**
	 * 
	 * 获取申请人概要信息
	 *
	 * @param orderid 订单id
	 * @return 人员概要信息集合
	 */
	public Object getStaffSummaryInfo(Integer orderid) {
		String sqlStr = sqlManager.get("appevents_staff_Summary_info");
		Sql applysql = Sqls.create(sqlStr);
		Cnd cnd = Cnd.NEW();
		cnd.and("tasou.orderid", "=", orderid);
		List<Record> summaryInfos = dbDao.query(applysql, cnd, null);
		return summaryInfos;
	}

	public Object getOrderStatus(int orderid, HttpSession session) {
		Map<String, Object> result = Maps.newHashMap();
		//订单信息
		TOrderUsEntity orderus = dbDao.fetch(TOrderUsEntity.class, orderid);
		Integer status = orderus.getStatus();
		for (USOrderListStatusEnum statusenum : USOrderListStatusEnum.values()) {
			if (status == statusenum.intKey()) {
				result.put("orderstatus", statusenum.value());
			}
			/*else if (status == JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.intKey()) {
				result.put("orderstatus", JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.value());
			}*/
		}
		return result;
	}

	/**
	 * 认领功能，调用之后订单进入“我的”里面
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toMyself(int orderid, HttpSession session) {
		TOrderUsEntity orderus = dbDao.fetch(TOrderUsEntity.class, orderid);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		orderus.setOpid(loginUser.getId());
		orderus.setUpdatetime(new Date());
		dbDao.update(orderus);
		return null;
	}

	public Object visaInfo(int staffid, int orderid, HttpSession session) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("staffid", staffid);
		result.put("orderid", orderid);
		return result;
	}

	public Object getVisaInfoData(int staffid) {
		Map<String, Object> result = Maps.newHashMap();
		List<TAppStaffVisaUsEntity> visainfoList = dbDao.query(TAppStaffVisaUsEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		List<Map<String, Object>> visainputmaps = Lists.newArrayList();
		for (TAppStaffVisaUsEntity VisaJpEntity : visainfoList) {
			DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
			Map<String, Object> visainputmap = Maps.newHashMap();
			if (!Util.isEmpty(VisaJpEntity.getVisadate())) {
				visainputmap.put("visadatestr", format.format(VisaJpEntity.getVisadate()));
			}
			if (!Util.isEmpty(VisaJpEntity.getValiddate())) {
				visainputmap.put("validdatestr", format.format(VisaJpEntity.getValiddate()));
			}
			String visatypestr = "";
			if (!Util.isEmpty(VisaJpEntity.getVisatype())) {
				for (AlredyVisaTypeEnum typeEnum : AlredyVisaTypeEnum.values()) {
					if (VisaJpEntity.getVisatype().equals(typeEnum.intKey())) {
						visatypestr = typeEnum.value();
					}
				}
			}
			visainputmap.put("visatypestr", visatypestr);
			visainputmap.putAll(obj2Map(VisaJpEntity));
			visainputmaps.add(visainputmap);
		}
		result.put("visaInputData", visainputmaps);
		return result;
	}

	private static Map<String, String> obj2Map(Object obj) {

		Map<String, String> map = new HashMap<String, String>();
		// System.out.println(obj.getClass());  
		// 获取f对象对应类中的所有属性域  
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
			varName = varName.toLowerCase();//将key置为小写，默认为对象的属性  
			try {
				// 获取原来的访问控制权限  
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限  
				fields[i].setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量  
				Object o = fields[i].get(obj);
				if (o != null)
					map.put(varName, o.toString());
				// System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);  
				// 恢复访问控制权限  
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return map;
	}

	public Object visaInputUpdate(int id, int orderid) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("visadatatypeenum", EnumUtil.enum2(AlredyVisaTypeEnum.class));
		TAppStaffVisaUsEntity staffvisaus = dbDao.fetch(TAppStaffVisaUsEntity.class, id);
		result.put("staffvisa", staffvisaus);
		result.put("staffid", staffvisaus.getStaffid());
		result.put("orderid", orderid);
		return result;
	}

	public Object updatedata(TAppStaffVisaUsUpdateForm updateForm, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		TAppStaffVisaUsEntity fetch = dbDao.fetch(TAppStaffVisaUsEntity.class, updateForm.getId());
		fetch.setStaydays(updateForm.getStaydays());
		fetch.setUpdatetime(new Date());
		fetch.setValiddate(updateForm.getValiddate());
		fetch.setVisaaddress(updateForm.getVisaaddress());
		fetch.setVisacountry(updateForm.getVisacountry());
		fetch.setVisadate(updateForm.getVisadate());
		fetch.setVisanum(updateForm.getVisanum());
		fetch.setVisatype(updateForm.getVisatype());
		fetch.setVisayears(updateForm.getVisayears());
		fetch.setPicurl(updateForm.getPicurl());
		//fetch.setVisaEntrytime(new Date());
		return dbDao.update(fetch);
	}

	public Object deleteVisainput(int id) {
		TAppStaffVisaUsEntity fetch = dbDao.fetch(TAppStaffVisaUsEntity.class, id);
		dbDao.delete(fetch);
		return JsonResult.success("删除成功");
	}

	public Object visaInputAdd(int staffid, int orderid) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("visadatatypeenum", EnumUtil.enum2(AlredyVisaTypeEnum.class));
		result.put("orderid", orderid);
		result.put("staffid", staffid);
		return result;
	}

	public Object addVisainput(TAppStaffVisaUsAddForm addForm, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();
		TAppStaffVisaUsEntity visaus = new TAppStaffVisaUsEntity();
		visaus.setCreatetime(new Date());
		visaus.setOpid(userid);
		visaus.setPicurl(addForm.getPicurl());
		visaus.setRealinfo(addForm.getRealinfo());
		visaus.setStaffid(addForm.getStaffid());
		visaus.setStaydays(addForm.getStaydays());
		visaus.setUpdatetime(new Date());
		visaus.setValiddate(addForm.getValiddate());
		visaus.setVisaaddress(addForm.getVisaaddress());
		visaus.setVisacountry(addForm.getVisacountry());
		visaus.setVisadate(addForm.getVisadate());
		visaus.setVisaentrytime(addForm.getVisaentrytime());
		visaus.setVisanum(addForm.getVisanum());
		visaus.setVisatype(addForm.getVisatype());
		visaus.setVisayears(addForm.getVisayears());
		dbDao.insert(visaus);
		return null;
	}

	/**
	 * 跟进内容保存
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid 订单id
	 * @param content 跟进内容
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveFollow(int orderid, String content, HttpSession session) {
		TOrderUsFollowupEntity follow = new TOrderUsFollowupEntity();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();
		follow.setContent(content);
		follow.setCreatetime(new Date());
		follow.setOrderid(orderid);
		follow.setUpdatetime(new Date());
		follow.setStatus(IsYesOrNoEnum.NO.intKey());
		follow.setUserid(userid);
		dbDao.insert(follow);
		return null;
	}

	/**
	 * 点击跟进中解决按钮
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param id
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object solveFollow(int id, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();
		TOrderUsFollowupEntity follow = dbDao.fetch(TOrderUsFollowupEntity.class, id);
		follow.setSolveid(userid);
		follow.setSolvetime(new Date());
		follow.setStatus(IsYesOrNoEnum.YES.intKey());
		follow.setUpdatetime(new Date());
		dbDao.update(follow);
		return null;
	}

	/**
	 * 跳转到日志页面，获取日志页面下拉框数据
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid 订单id
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toLog(int orderid, HttpSession session) {
		Map<String, Object> result = Maps.newHashMap();
		TCompanyEntity company = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userType = loginUser.getUserType();
		Integer userid = loginUser.getId();
		//如果是管理员，则需要查询公司下所有的员工来给下拉框赋值
		if (Util.eq(userType, UserLoginEnum.JJ_COMPANY_ADMIN.intKey())) {
			//获取公司下的所有工作人员,除去管理员
			List<TUserEntity> userList = dbDao.query(TUserEntity.class,
					Cnd.where("comId", "=", company.getId()).and("userType", "=", UserLoginEnum.PERSONNEL.intKey()),
					null);
			//获取管理员信息
			TUserEntity adminUser = dbDao.fetch(TUserEntity.class, userid.longValue());
			userList.add(adminUser);
			result.put("users", userList);
		}
		//获取订单操作人
		TOrderUsEntity orderus = dbDao.fetch(TOrderUsEntity.class, orderid);
		if (!Util.isEmpty(orderus.getOpid())) {
			Integer opid = orderus.getOpid();
			result.put("opid", opid);
		}
		result.put("userid", userid);
		result.put("userType", userType);
		result.put("orderid", orderid);
		return result;
	}

	/**
	 * 获取日志页面vue数据
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid 订单id
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getLogs(int orderid, HttpSession session) {
		Map<String, Object> result = Maps.newHashMap();
		String logSqlstr = sqlManager.get("orderUS_getLogs");
		Sql logSql = Sqls.create(logSqlstr);
		logSql.setParam("id", orderid);
		List<Record> logs = dbDao.query(logSql, null, null); //日志列表
		for (Record record : logs) {
			if (!Util.isEmpty(record.get("orderstatus"))) {
				Integer status = (Integer) record.get("orderstatus");
				for (USOrderListStatusEnum statusEnum : USOrderListStatusEnum.values()) {
					if (status == statusEnum.intKey()) {
						record.put("orderstatus", statusEnum.value());
					} else if (status == JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.intKey()) {
						record.put("orderstatus", JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.value());
					} else if (status == JPOrderStatusEnum.DISABLED.intKey()) {
						record.put("orderstatus", JPOrderStatusEnum.DISABLED.value());
					}
				}
			}
		}
		result.put("logs", logs);
		return result;
	}

	/**
	 * 日志页面负责人变更
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid 订单id
	 * @param principal 指定的负责人id
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object changePrincipal(int orderid, int principal, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		//把订单的操作人id改为管理员指定的id
		TOrderUsEntity orderus = dbDao.fetch(TOrderUsEntity.class, orderid);
		orderus.setOpid(principal);
		orderus.setUpdatetime(new Date());
		//orderus.setStatus(JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.intKey());
		dbDao.update(orderus);
		//插入日志
		insertLogs(orderid, JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.intKey(), loginUser.getId());
		return null;
	}

	/**
	 * 插入日志
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid 订单id
	 * @param orderstatus 订单状态
	 * @param userid 操作人id
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object insertLogs(int orderid, int orderstatus, int userid) {
		TOrderUsLogsEntity log = new TOrderUsLogsEntity();
		log.setOrderid(orderid);
		log.setOrderstatus(orderstatus);
		log.setOpid(userid);
		log.setCreatetime(new Date());
		log.setUpdatetime(new Date());
		dbDao.insert(log);
		return null;
	}

	public Object getAutofillOrder(int orderid, HttpSession session) {

		/*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
		RateLimiter rateLimiter = RateLimiter.create(1);
		List<TOrderUsEntity> query = dbDao.query(TOrderUsEntity.class, Cnd.where("status", "=", 5), null);
		Integer orderusid = query.get(0).getId();
		int count = 0;
		while (true) {
			count++;
			rateLimiter.acquire();
			ThreadDemo1 aDemo = new ThreadDemo1();
			new Thread(aDemo).start();

			System.out.println(simpleDateFormat.format(new Date()));
			if (count == 10) {
				break;
			}
		}
		System.out.println("出循环了~~~~~~~~~~~");*/
		Object applyinfo = getApplyinfo("3ea2a02d");
		selectApplyinfo("E49878974");
		return null;
	}

	class ThreadDemo1 implements Runnable {
		private HttpSession session;

		public void setSession(HttpSession session) {
			this.session = session;
		}

		@SuppressWarnings("synthetic-access")
		@Override
		public void run() {
			try {
				List<TOrderUsEntity> query = dbDao.query(TOrderUsEntity.class, Cnd.where("status", "=", 5), null);
				RateLimiter rateLimiter = RateLimiter.create(1);
				for (int i = 0; i < query.size(); i++) {
					rateLimiter.acquire();
					autofill(query.get(i).getId(), this.session);
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("thread_01");
		}
	}

	public Object autofill10(int orderid, HttpSession session) {
		String orderidstr = "orderid";
		redisDao.lpush(orderidstr, String.valueOf(orderid));
		//System.out.println(redisDao.rpop(orderidstr));
		return null;
	}

	public String autofill(int orderid, HttpSession session) {

		Thread t = new Thread(new Runnable() {
			public void run() {
				// run方法调用自动填表
				autofillMethod(orderid, session);
			}
		});
		t.start();
		/*ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(5));
		executor.execute(t);
		System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" + executor.getQueue().size()
				+ "，已执行玩别的任务数目：" + executor.getCompletedTaskCount());
		executor.shutdown();*/

		return "ok";
	}

	//自动填表
	public Object autofillMethod(int orderid, HttpSession session) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
		System.out.println(simpleDateFormat.format(new Date()) + "============");
		System.out.println("=====orderid:" + orderid);
		Map<String, Object> result = Maps.newHashMap();
		//TUserEntity loginUser = LoginUtil.getLoginUser(session);
		//改变订单状态
		TOrderUsEntity orderus = dbDao.fetch(TOrderUsEntity.class, orderid);
		orderus.setIsautofilling(1);
		orderus.setStatus(USOrderListStatusEnum.AUTOFILLING.intKey());
		dbDao.update(orderus);
		//根据订单id查询对应申请人，根据申请人查询二寸照片
		TAppStaffOrderUsEntity staffOrderUS = dbDao.fetch(TAppStaffOrderUsEntity.class,
				Cnd.where("orderid", "=", orderid));
		TAppStaffBasicinfoEntity basicinfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, staffOrderUS.getStaffid()
				.longValue());
		Integer staffid = basicinfo.getId();
		TAppStaffCredentialsEntity twoinchphoto = dbDao.fetch(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "=", 13));
		String imgurl = twoinchphoto.getUrl();
		System.out.println("imgurl:" + imgurl);
		TAppStaffPassportEntity passportinfo = dbDao.fetch(TAppStaffPassportEntity.class,
				Cnd.where("staffid", "=", staffid));
		String passportnum = passportinfo.getPassport();

		/*int height = 300;
		int width = 300;
		Icon icon = null;
		try {
			icon = getFixedIcon(imgurl, width, height);
		} catch (Exception e) {
			System.out.println("exception : " + e);
		}*/

		//调用第一个接口
		//selectApplyinfo();
		//调用第二个，第三个接口,获取申请人识别码
		//String applyidcode = (String) insertandupdateApplyinfo(orderid);
		//调用第四个接口
		//Object applyinfo = getApplyinfo(applyidcode);
		//调用第五个接口,上传图片
		//int successStatus = (int) uploadImgtoUS(imgurl, applyidcode);
		//System.out.println("imgsuccessStatus:" + successStatus);
		//调用第六个接口，向DS160官网提交申请
		//successStatus = (int) submittoDS160(applyidcode, type);
		//System.out.println("ds160successStatus:" + successStatus);
		//调用第四个接口,查询
		//be3d1654,77d68168,baf0e46c,8046d51a,de3dbd8a,48ca6a6f,1f1bdb37,bb9c3f38,15231000,73dacefd

		//调整顺序
		String applyidcode = "";
		int successStatus = 0;
		Map<String, Object> AAcodeinfo = Maps.newHashMap();
		Map<String, Object> repeatResult = Maps.newHashMap();
		String statusname = "";
		String AAcode = "";
		String errorMsg = "";
		String reviewurl = "";
		String pdfurl = "";
		String avatorurl = "";
		String daturl = "";
		String errorurl = "";
		List<AutofillSearchJsonEntity> applyinfoList = new ArrayList<>();
		AutofillSearchJsonEntity applyResult = new AutofillSearchJsonEntity();

		//创建申请人，上传头像，向官网申请
		applyResult = (AutofillSearchJsonEntity) applyToDS160(passportnum, imgurl, orderid, staffid, orderus);
		reviewurl = applyResult.getReview_url();
		if (Util.isEmpty(applyResult.getReview_url())) {
			return applyResult;
		}

		applyidcode = applyResult.getCode();
		//向DS160官网递交，参数1代表申请，参数2代表递交
		successStatus = (int) applyorsubmit(applyidcode, 2);
		int count = 0;
		if (successStatus == 1) {
			//根据护照号查询,参数1代表申请，参数2代表递交
			applyResult = (AutofillSearchJsonEntity) infinitQuery(applyidcode, passportnum, 2);
			statusname = applyResult.getStatus();
			errorMsg = applyResult.getErrorMsg();
			while (Util.eq("提交失败", statusname)) {
				System.out.println("errorMsg------:" + errorMsg);
				count++;
				System.out.println("count===========:" + count);
				if (count == 4) {
					System.out.println("提交还是失败了o(╥﹏╥)o");
					orderus.setIsautofilling(0);
					orderus.setErrormsg(applyResult.getErrorMsg());
					orderus.setStatus(USOrderListStatusEnum.AUTOFILLFAILED.intKey());
					dbDao.update(orderus);
					System.out.println("提交失败4次之后app_id:" + applyResult.getApp_id());
					basicinfo.setAacode(applyResult.getApp_id());
					dbDao.update(basicinfo);
					return applyResult;
				}
				applyResult = (AutofillSearchJsonEntity) applyToDS160(passportnum, imgurl, orderid, staffid, orderus);
				reviewurl = applyResult.getReview_url();
				if (Util.isEmpty(applyResult.getReview_url())) {
					return applyResult;
				}
				applyidcode = applyResult.getCode();
				successStatus = (int) applyorsubmit(applyidcode, 2);
				if (successStatus == 1) {
					applyResult = (AutofillSearchJsonEntity) infinitQuery(applyidcode, passportnum, 2);
					statusname = applyResult.getStatus();
				}
			}

			System.out.println("reviewurl:" + reviewurl);
			pdfurl = applyResult.getPdf_url();
			System.out.println("pdfurl:" + pdfurl);
			daturl = applyResult.getDat_url();
			System.out.println("daturl:" + daturl);
			AAcode = applyResult.getApp_id();
			System.out.println("AAcode:" + AAcode);
			errorurl = applyResult.getError_url();
			System.out.println("errorurl:" + errorurl);
			errorMsg = applyResult.getErrorMsg();
			System.out.println("errorMsg:" + errorMsg);
			orderus.setReviewurl(reviewurl);
			orderus.setPdfurl(pdfurl);
			orderus.setDaturl(daturl);
			orderus.setApplyidcode(applyidcode);
			//orderus.setErrorurl(errorurl);
			//成功时不更新错误信息，这样可以看到之前的错误信息
			//orderus.setErrormsg(errorMsg);
			orderus.setErrormsg("");
			orderus.setErrorurl("");
			orderus.setIsautofilling(0);
			orderus.setStatus(USOrderListStatusEnum.AUTOFILLED.intKey());
			dbDao.update(orderus);
			if (!Util.isEmpty(applyResult.getApp_id())) {
				basicinfo.setAacode(applyResult.getApp_id());
				dbDao.update(basicinfo);
			}
		}

		result.put("applyidcode", applyidcode);
		result.put("AAcode", AAcode);
		result.put("errorMsg", errorMsg);
		result.put("daturl", daturl);
		result.put("reviewurl", reviewurl);
		result.put("pdfurl", pdfurl);
		result.put("avatorurl", avatorurl);

		//uploadImgtoUS(imgurl, "9667808b");
		//submittoDS160("9667808b", "cover");
		//记录日志
		//insertLogs(orderid, USOrderListStatusEnum.AUTOFILL.intKey(), loginUser.getId());
		return result;
	}

	public Object applyToDS160(String passportnum, String imgurl, int orderid, int staffid, TOrderUsEntity orderus) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
		Map<String, Object> repeatResult = Maps.newHashMap();
		AutofillSearchJsonEntity applyResult = new AutofillSearchJsonEntity();
		int successStatus = 0;
		String statusname = "";
		String errorMsg = "";
		String reviewurl = "";
		String errorurl = "";

		//创建申请人信息，并上传头像
		repeatResult = repeatInsertandupdate(imgurl, orderid, staffid);
		successStatus = (int) repeatResult.get("successStatus");
		String applyidcode = (String) repeatResult.get("applyidcode");
		String errorType = (String) repeatResult.get("errorType");

		if (successStatus == 1) {
			//向DS160官网申请，参数1代表申请
			successStatus = (int) applyorsubmit(applyidcode, 1);
			if (successStatus == 1) {
				//根据护照号查询,参数1代表申请，参数2代表递交
				applyResult = (AutofillSearchJsonEntity) infinitQuery(applyidcode, passportnum, 1);
				statusname = applyResult.getStatus();
				while (Util.eq("申请失败", statusname)) {
					errorMsg = applyResult.getErrorMsg();
					if (errorMsg.contains("Connection") || errorMsg.contains("打码工超时未打码")
							|| errorMsg.contains("图片服务器连接错误") || errorMsg.contains("Cannot allocate memory")) {
						repeatResult = repeatInsertandupdate(imgurl, orderid, staffid);
						successStatus = (int) repeatResult.get("successStatus");
						applyidcode = (String) repeatResult.get("applyidcode");
						if (successStatus == 1) {
							successStatus = (int) applyorsubmit(applyidcode, 1);
							if (successStatus == 1) {
								applyResult = (AutofillSearchJsonEntity) infinitQuery(applyidcode, passportnum, 1);
								statusname = applyResult.getStatus();
								System.out.println("申请失败的while循环里:" + statusname);
							}
						}
					} else {
						System.out.println("申请失败了~~~~~~~~~~~~~~~~~~~~");
						errorurl = applyResult.getError_url();
						if (!Util.isEmpty(errorurl)) {
							try {
								// 创建URL
								URL url = new URL(errorurl);
								// 创建链接
								HttpURLConnection conn = (HttpURLConnection) url.openConnection();
								conn.setRequestMethod("GET");
								conn.setConnectTimeout(5000);
								InputStream is = conn.getInputStream();
								errorurl = CommonConstants.IMAGES_SERVER_ADDR
										+ qiniuUploadService.uploadImage(is, "", "");
								is.close();

							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						applyResult.setError_url(errorurl);
						orderus.setErrorurl(errorurl);
						orderus.setApplyidcode(applyidcode);
						orderus.setErrormsg(applyResult.getErrorMsg());
						orderus.setReviewurl("");
						orderus.setPdfurl("");
						orderus.setDaturl("");
						orderus.setIsautofilling(0);
						orderus.setStatus(USOrderListStatusEnum.AUTOFILLFAILED.intKey());
						dbDao.update(orderus);
						System.out.println("申请失败:" + simpleDateFormat.format(new Date()));
						return applyResult;
					}

				}

			}

		} else {
			applyResult.setReview_url("");
			orderus.setErrormsg(errorType);
			orderus.setReviewurl("");
			orderus.setPdfurl("");
			orderus.setDaturl("");
			orderus.setIsautofilling(0);
			orderus.setStatus(USOrderListStatusEnum.AUTOFILLFAILED.intKey());
			dbDao.update(orderus);
		}
		applyResult.setCode(applyidcode);
		return applyResult;
	}

	public static Icon getFixedIcon(String filePath, int width, int height) throws Exception {
		File f = new File(filePath);
		BufferedImage bi = ImageIO.read(f);
		double wRatio = (new Integer(width)).doubleValue() / bi.getWidth(); //宽度的比例		
		double hRatio = (new Integer(height)).doubleValue() / bi.getHeight(); //高度的比例		
		Image image = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH); //设置图像的缩放大小		
		AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(wRatio, hRatio), null); //设置图像的缩放比例		
		image = op.filter(bi, null);
		int lastLength = filePath.lastIndexOf(".");
		String subFilePath = filePath.substring(0, lastLength); //得到图片输出路径    
		String fileType = filePath.substring(lastLength); //图片类型   
		File zoomFile = new File(subFilePath + "_" + width + "_" + height + fileType);
		Icon ret = null;
		try {
			ImageIO.write((BufferedImage) image, "jpg", zoomFile);
			ret = new ImageIcon(zoomFile.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 上传申请人数据并上传头像
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param imgurl
	 * @param orderid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Map<String, Object> repeatInsertandupdate(String imgurl, int orderid, int staffid) {
		Map<String, Object> result = Maps.newHashMap();
		int successStatus = 0;
		//创建申请人数据
		String applyidcode = (String) insertandupdateApplyinfo(orderid, staffid);
		int count = 1;
		while (Util.isEmpty(applyidcode)) {
			count++;
			if (count == 4) {
				result.put("errorType", "创建申请人");
				result.put("applyidcode", applyidcode);
				result.put("successStatus", successStatus);
				return result;
			}
			applyidcode = (String) insertandupdateApplyinfo(orderid, staffid);
		}
		if (!Util.isEmpty(applyidcode)) {
			//try {
			successStatus = (int) uploadImgtoUS(imgurl, applyidcode);
			System.out.println("上传头像imgsuccessStatus:" + successStatus);
			int successCount = 1;
			while (successStatus != 1) {
				successCount++;
				if (successCount == 4) {
					result.put("errorType", "上传头像");
					result.put("applyidcode", applyidcode);
					result.put("successStatus", successStatus);
					return result;
				}
				successStatus = (int) uploadImgtoUS(imgurl, applyidcode);
				System.out.println("上传头像imgsuccessStatus在while循环里:" + successStatus);
			}
		}
		result.put("errorType", "");
		result.put("applyidcode", applyidcode);
		result.put("successStatus", successStatus);
		return result;
	}

	/**
	 * 向DS160官网申请或递交
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param applyidcode
	 * @param type 1代表申请，2代表递交
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object applyorsubmit(String applyidcode, int type) {
		int successStatus = 0;
		try {
			if (type == 1) {
				successStatus = (int) submittoDS160(applyidcode, "applying");
				System.out.println("申请DS160官网ds160successStatus:" + successStatus);
				while (successStatus != 1) {
					successStatus = (int) submittoDS160(applyidcode, "applying");
					System.out.println("申请DS160官网ds160successStatus在while循环里:" + successStatus);
				}
			} else {
				successStatus = (int) submittoDS160(applyidcode, "submitting");
				System.out.println("递交DS160官网ds160successStatus:" + successStatus);
				while (successStatus != 1) {
					successStatus = (int) submittoDS160(applyidcode, "submitting");
					System.out.println("递交DS160官网ds160successStatus在while循环里:" + successStatus);
				}
			}
		} catch (Exception e) {
			System.out.println("里边失败了~~");
		}
		return successStatus;
	}

	/**
	 * 查询申请人数据
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param applyidcode
	 * @param passportnum
	 * @param type
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object infinitQuery(String applyidcode, String passportnum, int type) {
		Map<String, Object> result = Maps.newHashMap();
		List<AutofillSearchJsonEntity> applyinfoList = new ArrayList<>();
		AutofillSearchJsonEntity applyResult = new AutofillSearchJsonEntity();
		Map<String, Object> AAcodeinfo = Maps.newHashMap();
		String statusname = "";
		String AAcode = "";
		String errorMsg = "";
		String errorurl = "";
		applyinfoList = selectApplyinfo(passportnum);
		applyResult = applyinfoList.get(0);
		statusname = applyResult.getStatus();
		AAcode = applyResult.getApp_id();

		System.out.println("statusname:" + statusname);
		System.out.println("AAcode:" + AAcode);
		if (type == 1) {
			Date firstDate = DateUtil.nowDate();
			Date nowDate = null;
			while (Util.eq("正在申请", statusname) || Util.eq("已保存", statusname)) {

				try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					System.out.println("申请等待30秒出错~~~~~~");
					// TODO Auto-generated catch block
					e.printStackTrace();

				}

				nowDate = DateUtil.nowDate();
				long millisBetween = twoDatebetweenMillis(firstDate, nowDate);
				System.out.println("millisBetween:*********" + millisBetween);
				System.out.println("进入循环了~~~~~~~~~~~");
				applyinfoList = selectApplyinfo(passportnum);
				applyResult = applyinfoList.get(0);
				statusname = applyResult.getStatus();
				AAcode = applyResult.getApp_id();
				System.out.println("while循环里申请statusname:" + statusname);
				System.out.println("while循环里申请AAcode:" + AAcode);
			}
			if (Util.eq("申请失败", statusname)) {
				errorurl = applyResult.getError_url();
				System.out.println("申请失败原因网址:" + errorurl);
				AAcodeinfo = getApplyinfo(applyidcode);
				errorMsg = (String) AAcodeinfo.get("errorMsg");
				System.out.println("申请失败原因：" + errorMsg);
			}
		} else {
			while (Util.eq("正在提交", statusname)) {
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					System.out.println("提交等待30秒出错~~~~~~");
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
				applyinfoList = selectApplyinfo(passportnum);
				applyResult = applyinfoList.get(0);
				statusname = applyResult.getStatus();
				AAcode = applyResult.getApp_id();
				System.out.println("while循环里提交statusname:" + statusname);
				System.out.println("while循环里提交AAcode:" + AAcode);
			}
			if (Util.eq("提交失败", statusname)) {
				errorurl = applyResult.getError_url();
				System.out.println("提交失败图片网址:" + errorurl);
				AAcodeinfo = getApplyinfo(applyidcode);
				errorMsg = (String) AAcodeinfo.get("errorMsg");
				System.out.println("提交失败原因：" + errorMsg);
			}
		}
		applyResult.setErrorMsg(errorMsg);
		return applyResult;
	}

	//第一个接口，查询
	public List<AutofillSearchJsonEntity> selectApplyinfo(String passportnum) {
		//JSONObject jsonObject = new JSONObject();
		long startTime = System.currentTimeMillis();
		Map<String, Object> resultData = Maps.newHashMap();
		resultData.put("search", passportnum);

		System.out.println("passportJSON:" + resultData);
		//List<AutofillSearchJsonEntity> searchInterface = searchInterface(resultData);
		JSONArray array = (JSONArray) searchInterface(resultData, "");
		List<AutofillSearchJsonEntity> searchList = com.alibaba.fastjson.JSONObject.parseArray(array.toString(),
				AutofillSearchJsonEntity.class);
		long endTime = System.currentTimeMillis();
		System.out.println("第一个接口所用时间为：" + (endTime - startTime) + "ms");
		return searchList;
	}

	//第二个，第三个接口，创建和更改申请人数据，只是请求方式不同，一个为POST,一个为PATCH
	public Object insertandupdateApplyinfo(int orderid, int staffid) {
		String applyidcode = "";
		Map<String, Object> result = autofillService.getData(orderid, staffid);
		if (!Util.isEmpty(result)) {
			//第二个，第三个接口，创建和更改申请人数据，只是请求方式不同，一个为POST,一个为PATCH
			//applyidcode = toGetApplyidcode(result.get("resultData"));
			String resultStr = (String) toGetEncrypt(result.get("resultData"), applyidcode, "POST");
			//String resultStr = (String) toGetEncrypt(result.get("resultData"), applyidcode, "PATCH");
			//从解密之后的字符串获取applyidcode
			JSONObject aacodeObj = new JSONObject(resultStr);
			try {
				if (aacodeObj.getInt("success") == 1) {
					applyidcode = (String) aacodeObj.get("code");
					System.out.println("创建申请人数据成功，applyidcode: " + applyidcode);
				} else {
					System.out.println("糟糕，创建申请人数据获取识别码失败了o(╥﹏╥)o");
				}
			} catch (Exception e) {
				//applyidcode = aacodeObj.getString("detail");
				System.out.println("创建申请人数据出现错误，错误信息:" + aacodeObj.getString("detail"));
			}
			/*if (!Util.isEmpty(aacodeObj.getInt("success"))) {
					if (aacodeObj.getInt("success") == 1) {
						applyidcode = (String) aacodeObj.get("code");
						System.out.println("applyidcode: " + applyidcode);
					} else {
						System.out.println("糟糕，获取识别码失败了o(╥﹏╥)o");
					}
				} else {
					System.out.println(aacodeObj.getString("detail"));
				}*/
		}
		System.out.println("申请人识别码为：" + applyidcode);
		return applyidcode;
	}

	//第四个接口，申请人数据详情
	public Map<String, Object> getApplyinfo(String applyidcode) {
		Map<String, Object> result = Maps.newHashMap();
		Map<String, Object> applycode = Maps.newHashMap();
		String appid = "";
		String errorMsg = "";
		String reviewurl = "";
		String pdfurl = "";
		String avatorurl = "";
		String daturl = "";
		//applycode.put("code", "d19eb111");//code为第二个接口返回
		JSONObject applyinfo = (JSONObject) searchInterface(applycode, applyidcode);
		JSONObject applystatus = (JSONObject) applyinfo.get("status");
		String statusname = (String) applystatus.get("name");
		result.put("statusname", statusname);
		try {
			errorMsg = (String) applyinfo.get("error_msg");
		} catch (Exception e) {
			errorMsg = "";
		}
		result.put("errorMsg", errorMsg);
		try {
			appid = (String) applyinfo.get("app_id");
		} catch (Exception e) {
			appid = "";
		}
		try {
			reviewurl = (String) applyinfo.get("review_url");
		} catch (Exception e) {
			reviewurl = "";
		}
		try {
			pdfurl = (String) applyinfo.get("pdf_url");
		} catch (Exception e) {
			pdfurl = "";
		}
		try {
			avatorurl = (String) applyinfo.get("avator_url");
		} catch (Exception e) {
			avatorurl = "";
		}
		try {
			daturl = (String) applyinfo.get("dat_url");
		} catch (Exception e) {
			daturl = "";
		}

		result.put("reviewurl", reviewurl);
		result.put("pdfurl", pdfurl);
		result.put("avatorurl", avatorurl);
		result.put("daturl", daturl);
		result.put("AAcode", appid);
		System.out.println("AA码为：" + appid);
		return result;
	}

	//第五个接口，上传美签申请人头像
	public Object uploadImgtoUS(String imgurl, String applyidcode) {
		//String imgbase64 = imgToBse64(imgurl);
		String imgbase64 = ImageToBase64ByOnline(imgurl);
		Map<String, Object> resultData = Maps.newHashMap();
		resultData.put("file", imgbase64);
		//resultData.put("file", imgurl);
		System.out.println("base64imgurl:" + resultData);

		String resultStr = (String) toGetEncrypt(resultData, applyidcode, "image");
		//从解密之后的字符串获取applyidcode
		JSONObject aacodeObj = new JSONObject(resultStr);
		int successStatus = (int) aacodeObj.get("success");
		System.out.println("applyidcode: " + applyidcode);
		return successStatus;
	}

	//第六个接口,递交DS160官网数据
	public Object submittoDS160(String applyidcode, String type) {
		Map<String, Object> resultData = Maps.newHashMap();
		resultData.put("action", type);
		String resultStr = (String) toGetEncrypt(resultData, applyidcode, type);
		JSONObject aacodeObj = new JSONObject(resultStr);
		int successStatus = (int) aacodeObj.get("success");
		System.out.println("applyidcode: " + applyidcode);
		return successStatus;
	}

	//将enctrypt加密，发送请求，然后解密拿到解密之后的enctrypt
	public Object toGetEncrypt(Object result, String applyidcode, String type) {
		WXBizMsgCrypt pc;
		String resultStr = "";
		String json = encrypt(result);
		//System.out.println("json:" + json);
		try {
			//发送POST请求
			String returnResult = toPostRequest(json, applyidcode, type);
			//String returnResult = toPatchRequest(json);
			JSONObject resultObj = new JSONObject(returnResult);
			String encrypt = (String) resultObj.get("encrypt");
			//对请求返回来的encrypt解密
			pc = new WXBizMsgCrypt(TOKEN, ENCODINGAESKEY, APPID);
			resultStr = pc.decrypt(encrypt);
			//System.out.println("toGetEncrypt解密后明文: " + resultStr);
		} catch (AesException e) {
			e.printStackTrace();
		}

		return resultStr;
	}

	//发送POST请求
	public String toPostRequest(String json, String applyidcode, String type) {
		String path = "";
		String host = "https://open.visae.net";
		if (!Util.isEmpty(applyidcode)) {
			if (Util.eq("image", type)) {
				path = "/visae/america/lg/save/data/" + applyidcode
						+ "/upload_photo/?token=ODBiOGIxNDY4NjdlMzc2Yg%3d%3d";
			} else {
				path = "/visae/america/lg/save/data/" + applyidcode
						+ "/submit_ds160/?token=ODBiOGIxNDY4NjdlMzc2Yg%3d%3d";
			}
		} else {
			path = "/visae/america/lg/save/data/?token=ODBiOGIxNDY4NjdlMzc2Yg%3d%3d";
		}
		String method = "POST";
		String entityStr = "";
		Map<String, String> headers = new HashMap<String, String>();
		if (Util.eq("PATCH", type)) {
			headers.put(" X-HTTP-Method-Override", "PATCH");
		}
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		HttpResponse response;
		System.out.println("httpurl:" + (host + path));
		System.out.println("json:" + json);
		try {
			response = HttpUtils.doPost(host, path, method, headers, querys, json);
			entityStr = EntityUtils.toString(response.getEntity());
			//System.out.println("POST请求返回的数据：" + entityStr);
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return entityStr;
	}

	/*//发送PATCH请求
		public String toPatchRequest(String json) {
			String host = "https://open.visae.net";
			String path = "/visae/america/lg/save/data/?token=ODBiOGIxNDY4NjdlMzc2Yg%3d%3d";
			String method = "POST";
			String entityStr = "";
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json; charset=UTF-8");
			headers.put(" x-http-method-override", "PATCH");
			Map<String, String> querys = new HashMap<String, String>();
			HttpResponse response;
			try {
				response = HttpUtils.doPost(host, path, method, headers, querys, json);
				entityStr = EntityUtils.toString(response.getEntity());
				System.out.println("PATCH请求返回的数据：" + entityStr);
			} catch (Exception e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			return entityStr;
		}*/

	//查询接口
	public Object searchInterface(Object result, String applyidcode) {
		List<AutofillSearchJsonEntity> searchList = new ArrayList<AutofillSearchJsonEntity>();
		String url = "";
		//先加密，获取加密之后的数据
		String encrypt = encrypt(result);
		JSONObject encryptObj = new JSONObject(encrypt);
		String timestamp = (String) encryptObj.get("timeStamp");
		String signature = (String) encryptObj.get("msg_signature");
		String nonce = (String) encryptObj.get("nonce");
		encrypt = (String) encryptObj.get("encrypt");
		System.out.println("encode之前的encrypt:" + encrypt);
		try {
			encrypt = URLEncoder.encode(encrypt, "utf-8");
			System.out.println("encode之后的encrypt:" + encrypt);
		} catch (UnsupportedEncodingException e1) {

			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		//encrypt = com.alibaba.dubbo.common.URL.encode(encrypt);
		//GET请求拼凑URL
		if (!Util.isEmpty(applyidcode)) {

			url = "/visae/america/lg/save/data/" + applyidcode + "/?token=ODBiOGIxNDY4NjdlMzc2Yg%3d%3d&timeStamp="
					+ timestamp + "&msg_signature=" + signature + "&nonce=" + nonce + "&encrypt=" + encrypt;
		} else {
			url = "/visae/america/lg/save/data/?token=ODBiOGIxNDY4NjdlMzc2Yg%3d%3d&timeStamp=" + timestamp
					+ "&msg_signature=" + signature + "&nonce=" + nonce + "&encrypt=" + encrypt;
		}
		System.out.println("encode之后的url:" + url);
		//发送请求，并对返回的结果解密
		String getRequest = toGetRequest(url);
		JSONObject jsonObject = new JSONObject(getRequest);
		encrypt = (String) jsonObject.get("encrypt");
		//System.out.println("encrypt:" + encrypt);
		//解密
		WXBizMsgCrypt pc;
		JSONArray array = null;
		JSONObject resultdata = null;
		try {
			pc = new WXBizMsgCrypt(TOKEN, ENCODINGAESKEY, APPID);
			String resultStr = pc.decrypt(encrypt);
			System.out.println("searchInterface解密后明文: " + resultStr);
			JSONObject searchObj = new JSONObject(resultStr);
			if (!Util.isEmpty(applyidcode)) {
				//{"msg": null, "data": {"id": 3402, "status": {"number": 1, "name": "已保存"}, "create_by": {"id": 36849, "name": "ysgj"}, "submit_time": null, "get_time": null, "create_date": "2018-09-19 17:23", "update_date": "2018-09-19 17:23", "date_of_birth": "2018-09-03", "apply_info": {"id": 2576640, "code": "baa996b5", "base_info": {"InforMation": {"WorkEducation": {"Education": [{"unit_name_cn": "北电", "AdderssInfo": {"province": "北京市", "country": "ANGL", "street": "大学", "city": "北京市", "zip_code": "110200"}, "major": "发顺丰色鬼", "end_date": "2018-09-10", "unit_name_en": "Nortel", "start_date": "2018-09-06"}], "Works": [{"unit_name_cn": "水电费第三方", "AdderssInfo": {"province": "北京市", "country": "ASMO", "street": "舒服舒服的", "city": "北京市", "zip_code": "110200"}, "DirectorsName": {"given_names_cn": "", "surnames_cn": "", "given_names_en": "", "surnames_en": ""}, "end_date": "2018-09-13", "monthly_income": "", "phone": "265646", "unit_name_en": "Water and electricity fee third party", "job_description": "水电费", "start_date": "2018-09-14", "job_title": "水电费"}], "describe": "", "current_status": "CM"}, "ExitInfo": {"OldPassport": [], "Language": []}, "TravelInfo": {"arrival_date": "2018-10-12", "go_country": "USA", "first_country": "", "Passport": {"passport_no": "E72073528", "issuance_date": "2016-05-31", "passport_type": "R", "PassportIssuance": {"province": "XINJIANG", "country": "CHIN", "city": ""}, "expiration_date": "2021-06-05", "country": "CHIN"}, "airways": "首都国际机场-羽田国际机场 NH964 0825/1250", "leave_airways": "成田国际机场-首都国际机场 CA6652 1820/2120", "travel_agency": "", "purpose_explanation": "", "purpose": "", "leave_date": "2018-10-18", "Peer": [], "leave_street": "密歇根州", "in_street": "密歇根州"}, "FamilyInfo": {"FatherInfo": {"date_of_birth": "2018-09-04", "NameInfo": {"given_names_cn": "水电", "surnames_cn": "水电费", "given_names_en": "SHUIDIAN", "surnames_en": "SHUIDIANFEI"}}, "AdderssInfo": {"province": "河北", "country": "CHIN", "street": "山西省大同市", "city": "张家口", "zip_code": ""}, "MotherInfo": {"date_of_birth": "2018-09-06", "NameInfo": {"given_names_cn": "水", "surnames_cn": "阿斯蒂芬", "given_names_en": "SHUI", "surnames_en": "ASIDIFEN"}}, "SpouseInfo": {"AdderssInfo": {"province": "", "country": "", "street": "", "city": "", "zip_code": ""}, "NameInfo": {"given_names_cn": "水电费", "surnames_cn": "水电水电都是", "given_names_en": "SHUIDIANFEI", "surnames_en": "SHUIDIANSHUIDIANDOUSHI"}, "end_date": "", "BirthplaceInfo": {"province": "", "country": "ASMO", "city": "内蒙古自治区"}, "date_of_birth": "2018-09-13", "divorced_reason": "", "nationality": "ALGR", "address_type": 5, "start_date": "", "divorced_country": ""}, "family_phone": ""}, "BaseInfo": {"NameInfo": {"old_given_names_en": "SIASADE", "old_surnames_en": "LISHUIDIANFEISHIDEFEN", "surnames_en": "LIU", "old_given_names_cn": "四阿萨德", "given_names_code_cn": "", "given_names_en": "YUFEI1", "given_names_cn": "宇飞1", "surnames_cn": "刘", "surnames_code_cn": "", "old_surnames_cn": "里水电费实得分"}, "photo": "https://upload.visae.net/visae/us/ds160/2018/09/19/photo/e7616438-d142-496b-8950-465373fcbe08.jpeg", "sex": 1, "phone": "18612131435", "BirthplaceInfo": {"province": "山西", "country": "CHIN", "city": "临汾"}, "date_of_birth": "2018-09-03", "Marriage": "W", "PHOTO_MD5_URL": "https://upload.visae.net/https://upload.visae.net/visae/us/ds160/2018/09/19/photo/e7616438-d142-496b-8950-465373fcbe08.jpeg?qhash/md5&e=1537422458&token=Kcgo66ixdBsRf20QIHsITR-5xOdxwpk8793v9-Ym:Iff3zB88RrcyuAKCfkWr-R5P5ck=", "nationality": "CHIN", "ic": "1104964189498", "email": "110@qq.com", "photo_url": "https://upload.visae.net/https://upload.visae.net/visae/us/ds160/2018/09/19/photo/e7616438-d142-496b-8950-465373fcbe08.jpeg?e=1537422458&token=Kcgo66ixdBsRf20QIHsITR-5xOdxwpk8793v9-Ym:7MfWtWK3Gq9prNU3pV9aO7LQK6Y="}}}, "visa_info": {"AmericaInfo": {"us_social_security_number": "", "united_states_citizen": "", "PayParty": {"payer": "S"}, "Contacts": {"AdderssInfo": {"province": "AK", "country": "USA", "street": "国为不产有产人在在地一有在要工", "zip_code": "", "city": "4233432423"}, "NameInfo": {"given_names_cn": "先哲", "surnames_cn": "孙", "given_names_en": "XIANZHE", "surnames_en": "SUN"}, "relationship": "C", "phone": "132432432423", "organization": "4324243242", "email": "18612131435@163.com"}, "MailingAddress": {"province": "CHN", "country": "CHN", "street": "CHN", "zip_code": "", "city": "CHN"}, "RelativesUS": {"Father": {"status": "C", "in_the_us": 1}, "other_in_us": false, "Immediate": [{"NameInfo": {"given_names_cn": "的", "surnames_cn": "的", "given_names_en": "的", "surnames_en": "DEDSDS"}}], "Mother": {"status": "P", "in_the_us": 1}}, "apply_for_emigrant": "水个个人然后", "school_in_america": "", "ResidenceTime": {"date_type": "", "number": ""}, "esta": "", "sevis_id": "", "OtherNationality": [{"country": "", "passport": ""}], "StayCity": [], "GreenCard": [{"country": ""}], "work_phone_number": "", "principal_applicant_sevis_id": "", "EverGoToAmerica": {"USDriverLicense": [{"state": "AR", "number": "345345"}], "LastUSVisa": {"LostOrStolen": {"explain": "", "year": 0}, "revoked": "32432432434232", "finger_fingerprint": 1, "number": "165156", "date": "2018-09-19", "same_place": 1, "same_visa": 1}, "InformationUSVisit": [{"date": "2018-09-20", "ResidenceTime": {"date_type": "D", "number": 2}}]}, "Security": {"q20": "", "q21": "", "q22": "", "q23": "", "q24": "", "q25": "", "q26": "", "q27": "", "q28": "", "q29": "", "q1": "", "q3": "", "q2": "", "q5": "", "q4": "", "q7": "", "q6": "", "q9": "", "q8": "", "q15": "", "q14": "", "q17": "", "q16": "", "q11": "", "q10": "", "q13": "", "q12": "", "q19": "", "q18": ""}, "refuse_entry": "第三方围观围观双方各是", "program_number": "", "ResidentialInfo": {"province": "", "country": "", "street": "", "zip_code": "", "city": ""}, "national_identification_number": "", "Supplement": {"paramilitary": "", "religion": "", "special_skills": "", "Charitable": [], "MilitaryService": {"armed_services": "", "end_date": "", "level": "", "country": "", "specialty": "", "start_date": ""}, "VisitedCountry": [{"country": "日本"}, {"country": "中国"}]}, "application_site": "BEJ", "us_taxpayerid_number": ""}}, "status": 1, "visa_type": 175, "create_by": 36849}, "app_id": null, "error_msg": null, "name": "刘宇飞1", "name_en": "LIU YUFEI1", "passport_num": "E72073528", "review_url": null, "pdf_url": null, "avator_url": null, "error_url": null, "dat_url": null}, "success": 1}
				resultdata = (JSONObject) searchObj.get("data");
			} else {
				//从解密之后的数据中获取所查询的数据，并最终转化成list
				array = (JSONArray) searchObj.get("data");
			}
			//将JSONArray转成list集合
			//searchList = com.alibaba.fastjson.JSONObject.parseArray(array.toString(), AutofillSearchJsonEntity.class);
			//List<?> list2 = JSONArray.toList(array, new AutofillSearchJsonEntity(), new JsonConfig());
		} catch (AesException e) {
			e.printStackTrace();
		}
		if (!Util.isEmpty(applyidcode)) {
			return resultdata;
		} else {
			return array;
		}
	}

	//将图片转成base64
	public String imgToBse64(String filePath) {
		InputStream inputStream = null;
		HttpURLConnection httpURLConnection = null;
		byte[] data = null;
		try {
			URL url = new URL(filePath);//创建的URL  
			if (url != null) {
				httpURLConnection = (HttpURLConnection) url.openConnection();//打开链接  
				httpURLConnection.setConnectTimeout(3000);//设置网络链接超时时间，3秒，链接失败后重新链接  
				httpURLConnection.setDoInput(true);//打开输入流  
				httpURLConnection.setRequestMethod("GET");//表示本次Http请求是GET方式  
				int responseCode = httpURLConnection.getResponseCode();//获取返回码  
				if (responseCode == 200) {//成功为200  
					//从服务器获得一个输入流  
					inputStream = httpURLConnection.getInputStream();
					data = new byte[inputStream.available()];
					inputStream.read(data);
					inputStream.close();
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Base64.encodeBase64String(data);
	}

	public static String ImageToBase64ByOnline(String imgURL) {
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		try {
			// 创建URL
			URL url = new URL(imgURL);
			byte[] by = new byte[1024];
			// 创建链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			InputStream is = conn.getInputStream();
			// 将内容读取内存中
			int len = -1;
			while ((len = is.read(by)) != -1) {
				data.write(by, 0, len);
			}
			// 关闭流
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		return Base64.encodeBase64String(data.toByteArray());
	}

	//将数据加密
	public String encrypt(Object result) {
		String encodingAesKey = ENCODINGAESKEY;
		String token = TOKEN;
		String timestamp = getTimeStamp();
		String nonce = getRandomString();
		String appId = APPID;

		String jsonResult = JsonUtil.toJson(result);
		System.out.println("jsonResult:" + jsonResult);

		//加密
		WXBizMsgCrypt pc;
		String json = "";
		try {
			pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
			json = pc.encryptMsg(jsonResult, timestamp, nonce);
			System.out.println("body:" + json);

		} catch (AesException e) {
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * 获取时间戳方法
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public String getTimeStamp() {
		Date date = new Date();
		//样式：yyyy年MM月dd日HH时mm分ss秒SSS毫秒
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmss");
		String timeStampStr = simpleDateFormat.format(date);
		return timeStampStr;
	}

	/**
	 * 生成随机字符串
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public String getRandomString() {
		String str = "zxcvbnmlkjhgfdsaqwertyuiop1234567890";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		//长度为几就循环几次
		for (int i = 0; i < 8; ++i) {
			//产生0-61的数字
			int number = random.nextInt(str.length());
			//将产生的数字通过length次承载到sb中
			sb.append(str.charAt(number));
		}
		//将承载的字符转换成字符串
		return sb.toString();
	}

	//发送GET请求
	public String toGetRequest(String path) {
		String entityStr = "";
		String host = "https://open.visae.net";
		String method = "GET";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		HttpResponse response;
		try {
			response = HttpUtils.doGet(host, path, method, headers, querys);
			entityStr = EntityUtils.toString(response.getEntity());
			//System.out.println("GET请求返回的数据：" + entityStr);
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return entityStr;
	}

	/**
	 * 点击订单详情中的通过按钮，改变订单状态为通过
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid 订单id
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object passUS(int orderid, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();
		TOrderUsEntity orderus = dbDao.fetch(TOrderUsEntity.class, orderid);
		orderus.setStatus(USOrderListStatusEnum.TONGGUO.intKey());
		orderus.setUpdatetime(new Date());
		dbDao.update(orderus);
		insertLogs(orderid, USOrderListStatusEnum.TONGGUO.intKey(), userid);
		return null;
	}

	public Object returnVcode(String vcode, HttpSession session) {
		List<TAppStaffVcodeEntity> query = dbDao.query(TAppStaffVcodeEntity.class, null, null);
		TAppStaffVcodeEntity tAppStaffVcodeEntity = query.get(0);
		tAppStaffVcodeEntity.setVcode(vcode);
		dbDao.update(tAppStaffVcodeEntity);
		return null;
	}

	public Object toRenturnVcode(JapanSimulatorForm form) {
		List<TAppStaffVcodeEntity> query = dbDao.query(TAppStaffVcodeEntity.class, null, null);
		TAppStaffVcodeEntity tAppStaffVcodeEntity = query.get(0);
		String vcode = tAppStaffVcodeEntity.getVcode();
		return vcode;
	}

	/**
	 * 点击订单详情中的拒签按钮，改变订单状态为拒绝
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid 订单id
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object refuseUS(int orderid, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();
		TOrderUsEntity orderus = dbDao.fetch(TOrderUsEntity.class, orderid);
		orderus.setStatus(USOrderListStatusEnum.JUJUE.intKey());
		orderus.setUpdatetime(new Date());
		dbDao.update(orderus);
		insertLogs(orderid, USOrderListStatusEnum.JUJUE.intKey(), userid);
		return null;
	}

	/**
	 * 作废功能
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid 订单id
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object disabled(int orderid, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TOrderUsEntity orderus = dbDao.fetch(TOrderUsEntity.class, orderid);
		orderus.setIsdisable(IsYesOrNoEnum.YES.intKey());
		orderus.setUpdatetime(new Date());
		dbDao.update(orderus);
		//插入日志
		insertLogs(orderus.getId(), JPOrderStatusEnum.DISABLED.intKey(), loginUser.getId());
		return null;
	}

	/**
	 * 还原功能
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid 订单id
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object undisabled(int orderid) {
		TOrderUsEntity orderus = dbDao.fetch(TOrderUsEntity.class, orderid);
		orderus.setIsdisable(IsYesOrNoEnum.NO.intKey());
		orderus.setUpdatetime(new Date());
		dbDao.update(orderus);
		return null;
	}

	/**
	 * 保存订单并返回
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object orderSave(OrderUpdateForm form, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer orderid = form.getOrderid();
		Integer staffid = form.getStaffid();
		TOrderUsEntity orderus = dbDao.fetch(TOrderUsEntity.class, orderid.longValue());
		/*TOrderUsEntity orderus = new TOrderUsEntity();
			//如果订单Id为0说明是下单，需要创建订单表，并处理下单时创建的人员表等的关系
			if(orderid == 0){
				orderus.setCreatetime(new Date());
				orderus.setUpdatetime(new Date());
				orderus = dbDao.insert(orderus);
				orderid = orderus.getId();
			}else{
				orderus = dbDao.fetch(TOrderUsEntity.class, orderid.longValue());
			}*/
		//获取出行信息表
		TOrderUsTravelinfoEntity orderTravelInfo = dbDao.fetch(TOrderUsTravelinfoEntity.class,
				Cnd.where("orderid", "=", orderid));
		if (Util.isEmpty(orderTravelInfo)) {
			orderTravelInfo = new TOrderUsTravelinfoEntity();
			orderTravelInfo.setOrderid(orderid);
			orderTravelInfo.setTravelpurpose("TEMP. BUSINESS PLEASURE VISITOR(B)");
			orderTravelInfo.setSpecify(1);
			orderTravelInfo = dbDao.insert(orderTravelInfo);
		}
		orderTravelInfo.setGodate(form.getGodate());
		orderTravelInfo.setLeavedate(form.getLeavedate());
		orderTravelInfo.setArrivedate(form.getArrivedate());
		orderTravelInfo.setStaydays(form.getStaydays());
		orderTravelInfo.setAddress(form.getPlanaddress());
		orderTravelInfo.setAddressen(form.getPlanaddressen());
		orderTravelInfo.setCity(form.getPlancity());
		orderTravelInfo.setCityen(form.getPlancityen());
		orderTravelInfo.setState(form.getPlanstate());
		if (!Util.isEmpty(form.getTravelpurpose())) {
			String travelpurpose = form.getTravelpurpose();
			String key = TravelpurposeEnum.getEnum(travelpurpose).getKey();
			orderTravelInfo.setTravelpurpose(key);
		} else {
			orderTravelInfo.setTravelpurpose("TEMP. BUSINESS PLEASURE VISITOR(B)");
		}
		orderTravelInfo.setHastripplan(form.getHastripplan());
		orderTravelInfo.setGodeparturecity(form.getGodeparturecity());
		orderTravelInfo.setGoArrivedCity(form.getGoArrivedCity());
		if (form.getHastripplan() == 1) {
			orderTravelInfo.setGoFlightNum(form.getGoFlightNum());
			orderTravelInfo.setReturnFlightNum(form.getReturnFlightNum());
		} else {
			orderTravelInfo.setGoFlightNum("");
			orderTravelInfo.setReturnFlightNum("");
		}
		orderTravelInfo.setReturnDepartureCity(form.getReturnDepartureCity());
		orderTravelInfo.setReturnArrivedCity(form.getReturnArrivedCity());
		//修改订单信息
		int orderUpdateNum = dbDao.update(orderTravelInfo);

		orderus.setCityid(form.getCityid());
		orderus.setIspayed(form.getIspayed());
		orderus.setGroupname(form.getGroupname());
		orderus.setBigcustomername(form.getBigcustomername());
		orderus.setUpdatetime(new Date());

		//如果有面签时间，则改变订单状态为面签
		/*if (!Util.isEmpty(form.getInterviewdate())) {
			if (orderus.getStatus() < USOrderListStatusEnum.MIANQIAN.intKey()) {
				orderus.setStatus(USOrderListStatusEnum.MIANQIAN.intKey());
			}
			insertLogs(orderus.getId(), USOrderListStatusEnum.MIANQIAN.intKey(), loginUser.getId());
		}*/
		dbDao.update(orderus);
		//把面签时间添加到人员信息中
		TAppStaffOrderUsEntity fetch = dbDao.fetch(TAppStaffOrderUsEntity.class, Cnd.where("orderid", "=", orderid));
		TAppStaffBasicinfoEntity basic = dbDao.fetch(TAppStaffBasicinfoEntity.class, fetch.getStaffid().longValue());
		basic.setInterviewdate(form.getInterviewdate());
		dbDao.update(basic);
		//消息通知
		try {
			uslistwebsocket.broadcast(new TextMessage(""));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 跳转到通知页面
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid 订单id
	 * @param staffid 人员id
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toNotice(int orderid, int staffid, HttpSession session) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("orderid", orderid);
		result.put("staffid", staffid);
		return result;
	}

	//根据人员id添加订单
	public Object addOrderByStuffId(Integer staffId, int userid) {
		Map<String, Object> kvConfigProperties = SystemProperties.getKvConfigProperties();
		String BaoYingComIdStr = String.valueOf(kvConfigProperties.get("T_APP_STAFF_BAOYING_COMPANY_ID"));
		Integer US_BaoYing_COM_ID = Integer.valueOf(BaoYingComIdStr);

		TOrderUsEntity orderUs = new TOrderUsEntity();
		String orderNum = generateOrderNumByDate();
		Date nowDate = DateUtil.nowDate();
		orderUs.setOrdernumber(orderNum);
		orderUs.setOpid(userid);
		orderUs.setComid(US_BaoYing_COM_ID);
		//大客户名称默认为： 葆婴
		orderUs.setBigcustomername(BaoYingComIdStr);
		orderUs.setStatus(USOrderStatusEnum.PLACE_ORDER.intKey());//下单
		orderUs.setIspayed(IsPayedEnum.NOTPAY.intKey());
		orderUs.setCreatetime(nowDate);
		orderUs.setIsdisable(IsYesOrNoEnum.NO.intKey());
		orderUs.setUpdatetime(nowDate);
		TOrderUsEntity order = dbDao.insert(orderUs);
		insertLogs(order.getId(), USOrderListStatusEnum.PLACE_ORDER.intKey(), userid);

		//更新人员-订单关系表
		if (!Util.isEmpty(order)) {
			Integer orderId = order.getId();
			TAppStaffOrderUsEntity staffOrder = new TAppStaffOrderUsEntity();
			staffOrder.setOrderid(orderId);
			staffOrder.setStaffid(staffId);
			dbDao.insert(staffOrder);
		}

		return JsonResult.success("添加成功");

	}

	//工作人员  根据人员id添加订单
	public Object addWorkerOrderByStuffId(Integer staffId, int loginUserid, int loginComId) {
		TOrderUsEntity orderUs = new TOrderUsEntity();
		String orderNum = generateOrderNumByDate();
		Date nowDate = DateUtil.nowDate();
		orderUs.setOrdernumber(orderNum);
		orderUs.setOpid(loginUserid);
		orderUs.setComid(loginComId);
		orderUs.setStatus(USOrderStatusEnum.PLACE_ORDER.intKey());//下单
		orderUs.setIspayed(IsPayedEnum.NOTPAY.intKey());
		orderUs.setCreatetime(nowDate);
		orderUs.setIsdisable(IsYesOrNoEnum.NO.intKey());
		orderUs.setUpdatetime(nowDate);
		TOrderUsEntity order = dbDao.insert(orderUs);
		insertLogs(order.getId(), USOrderListStatusEnum.PLACE_ORDER.intKey(), loginUserid);

		//更新人员-订单关系表
		if (!Util.isEmpty(order)) {
			Integer orderId = order.getId();
			TAppStaffOrderUsEntity staffOrder = new TAppStaffOrderUsEntity();
			staffOrder.setOrderid(orderId);
			staffOrder.setStaffid(staffId);
			dbDao.insert(staffOrder);
		}

		return JsonResult.success("添加成功");

	}

	//生成订单号
	public String generateOrderNumByDate() {
		SimpleDateFormat smf = new SimpleDateFormat("yyMMdd");
		String format = smf.format(new Date());
		String sqlString = sqlManager.get("orderUS_orderNum_nowDate");
		Sql sql = Sqls.create(sqlString);
		List<Record> query = dbDao.query(sql, null, null);
		int sum = 1;
		if (!Util.isEmpty(query) && query.size() > 0) {
			String string = query.get(0).getString("ordernumber");
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
		String orderNum = format + "-US" + sum1;

		return orderNum;
	}

	/**
	 * 分享发送消息
	 *
	 * @param staffId 人员id
	 * @param orderid 订单id
	 * @param mobileUrl 手机号
	 * @return 
	 */
	public Object sendShareMsg(Integer staffId, Integer orderid, String sendType, HttpServletRequest request) {

		String pcUrl = "https://" + request.getServerName() + ":" + request.getServerPort() + "/tlogin";
		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TOrderUsEntity orderus = dbDao.fetch(TOrderUsEntity.class, orderid.longValue());
		if (!Util.isEmpty(staffId)) {
			try {
				//发送短信
				//分享
				if (Util.eq(sendType, "share")) {
					sendSMSUS(staffId, orderid, sendType, "orderustemp/order_us_share_sms.txt");
				}
				//合格
				if (Util.eq(sendType, "qualified")) {
					sendSMSUS(staffId, orderid, sendType, "orderustemp/order_us_qualified_sms.txt");
				}
				//面试
				if (Util.eq(sendType, "interview")) {
					sendSMSUS(staffId, orderid, sendType, "orderustemp/order_us_interview_sms.txt");
				}
				//发送邮件
				//分享
				if (Util.eq(sendType, "share")) {
					sendEmailUS(staffId, orderid, pcUrl, sendType, "orderustemp/order_us_share_mail.html");
				}
				//合格
				if (Util.eq(sendType, "qualified")) {
					sendEmailUS(staffId, orderid, pcUrl, sendType, "orderustemp/order_us_qualified_mail.html");
				}
				//面试
				if (Util.eq(sendType, "interview")) {
					sendEmailUS(staffId, orderid, pcUrl, sendType, "orderustemp/order_us_interview_mail.html");
				}
				//改变订单状态
				//分享
				if (Util.eq(sendType, "share")) {
					if (orderus.getStatus() < USOrderListStatusEnum.FILLING.intKey()) {
						orderus.setStatus(USOrderListStatusEnum.FILLING.intKey());
						dbDao.update(orderus);
					}
					insertLogs(orderus.getId(), USOrderListStatusEnum.FILLING.intKey(), loginUser.getId());
				}
				//合格
				if (Util.eq(sendType, "qualified")) {
					if (orderus.getStatus() < USOrderListStatusEnum.HEGE.intKey()) {
						orderus.setStatus(USOrderListStatusEnum.HEGE.intKey());
						dbDao.update(orderus);
					}
					insertLogs(orderus.getId(), USOrderListStatusEnum.HEGE.intKey(), loginUser.getId());
				}
				//面试
				if (Util.eq(sendType, "interview")) {

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return Json.toJson("发送成功");
		} else {
			return Json.toJson("电话号不能为空");
		}
	}

	//发送邮件
	public Object sendEmailUS(Integer staffId, Integer orderid, String pcUrl, String sendType, String mailTemplate)
			throws IOException {
		List<String> readLines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream(mailTemplate));
		StringBuilder tmp = new StringBuilder();
		for (String line : readLines) {
			tmp.append(line);
		}
		String emailText = tmp.toString();
		String result = "";
		//查询订单号
		TOrderUsEntity order = dbDao.fetch(TOrderUsEntity.class, orderid.longValue());
		String orderNum = order.getOrdernumber();

		//申请人
		TAppStaffBasicinfoEntity staffBaseInfo = dbDao.fetch(TAppStaffBasicinfoEntity.class,
				Cnd.where("id", "=", staffId));
		String name = staffBaseInfo.getFirstname() + staffBaseInfo.getLastname();
		String telephone = staffBaseInfo.getTelephone();
		String toEmail = staffBaseInfo.getEmail();
		String sex = staffBaseInfo.getSex();
		String interviewdateStr = "";
		Date interviewdate = staffBaseInfo.getInterviewdate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (!Util.isEmpty(interviewdate)) {
			interviewdateStr = sdf.format(interviewdate);
		}

		if (!Util.isEmpty(toEmail)) {
			/*if (Util.eq("男", sex)) {
					sex = "先生";
				} else {
					sex = "女士";
				}*/
			sex = "先生/女士";
			//分享
			if (Util.eq(sendType, "share")) {
				emailText = emailText.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum)
						.replace("${telephone}", telephone).replace("${pcUrl}", pcUrl);
				result = mailService.send(toEmail, emailText, "美国订单分享", MailService.Type.HTML);
			}
			//合格
			if (Util.eq(sendType, "qualified")) {
				emailText = emailText.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum);
				result = mailService.send(toEmail, emailText, "美国订单合格", MailService.Type.HTML);
			}
			//面试
			if (Util.eq(sendType, "interview")) {
				emailText = emailText.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum)
						.replace("${interviewdateStr}", interviewdateStr);
				result = mailService.send(toEmail, emailText, "美国面试通知", MailService.Type.HTML);
			}
		}

		return result;
	}

	//发送短信
	public Object sendSMSUS(Integer staffId, Integer orderid, String sendType, String smsTemplate) throws IOException {
		List<String> readLines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream(smsTemplate));
		StringBuilder tmp = new StringBuilder();
		for (String line : readLines) {
			tmp.append(line);
		}
		TAppStaffBasicinfoEntity staffBaseInfo = dbDao.fetch(TAppStaffBasicinfoEntity.class,
				Cnd.where("id", "=", staffId));
		String name = staffBaseInfo.getFirstname() + staffBaseInfo.getLastname();
		String telephone = staffBaseInfo.getTelephone();
		String email = staffBaseInfo.getEmail();
		String sex = staffBaseInfo.getSex();
		String url = "";
		String interviewdateStr = "";
		Date interviewdate = staffBaseInfo.getInterviewdate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (!Util.isEmpty(interviewdate)) {
			interviewdateStr = sdf.format(interviewdate);
		}
		String result = "";
		if (!Util.isEmpty(telephone)) {
			/*if (Util.eq("男", sex)) {
					sex = "先生";
				} else {
					sex = "女士";
				}*/
			sex = "先生/女士";
			TOrderUsEntity order = dbDao.fetch(TOrderUsEntity.class, orderid.longValue());
			String orderNum = order.getOrdernumber();
			String smsContent = tmp.toString();

			//分享
			if (Util.eq(sendType, "share")) {
				smsContent = smsContent.replace("${name}", name).replace("${sex}", sex)
						.replace("${ordernum}", orderNum).replace("${mobileUrl}", telephone).replace("${email}", url);
				result = sendSMS(telephone, smsContent);
			}
			//合格
			if (Util.eq(sendType, "qualified")) {
				smsContent = smsContent.replace("${name}", name).replace("${sex}", sex)
						.replace("${ordernum}", orderNum);
				result = sendSMS(telephone, smsContent);
			}
			//面试
			if (Util.eq(sendType, "interview")) {
				smsContent = smsContent.replace("${name}", name).replace("${sex}", sex)
						.replace("${ordernum}", orderNum).replace("${interviewdateStr}", interviewdateStr);
				result = sendSMS(telephone, smsContent);
			}
		}

		return result;

	}

	/**
	 * 发送手机短信
	 * <p>
	 * @param mobilenum  手机号
	 * @param content  短信内容
	 */
	public String sendSMS(String mobilenum, String content) {
		String result = "发送失败";
		try {
			SMSService smsService = new HuaxinSMSServiceImpl(redisDao);
			smsService.send(mobilenum, SMS_SIGNATURE + content);
			result = "发送成功";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Object IDCardRecognition(String encode, File file, int staffid) {
		/*String openid = redisDao.get(encode);
			if (Util.isEmpty(openid)) {
				return -1;
			} else {*/
		System.out.println("encode:" + encode + " file:" + file + " staffid:" + staffid);
		String imageDataValue = saveDiskImageToDisk(file);
		Input input = new Input(imageDataValue, "face");
		RecognizeData rd = new RecognizeData();
		rd.getInputs().add(input);
		String content = Json.toJson(rd);
		String info = (String) appCodeCall(content);//扫描完毕
		long endTime = System.currentTimeMillis();
		System.out.println("info:" + info);

		USStaffJsonEntity jsonEntity = new USStaffJsonEntity();

		JSONObject resultObj = null;
		try {
			resultObj = new JSONObject(info);
		} catch (JSONException e) {
			jsonEntity.setSuccess(false);
			return jsonEntity;
		}

		JSONArray outputArray = resultObj.getJSONArray("outputs");
		String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
		JSONObject out = new JSONObject(output);
		if (out.getBoolean("success")) {
			String addr = out.getString("address"); // 获取地址
			String name = out.getString("name"); // 获取名字
			String num = out.getString("num"); // 获取身份证号
			jsonEntity.setAddress(addr);
			Date date;
			try {
				date = new SimpleDateFormat("yyyyMMdd").parse(out.getString("birth"));
				String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				jsonEntity.setBirth(sdf.format(sdf.parse(dateStr)));
			} catch (JSONException | ParseException e) {
				e.printStackTrace();
				jsonEntity.setSuccess(false);
				return jsonEntity;
			}
			jsonEntity.setName(name);
			jsonEntity.setNationality(out.getString("nationality"));
			jsonEntity.setNum(num);
			jsonEntity.setRequest_id(out.getString("request_id"));
			jsonEntity.setSex(out.getString("sex"));
			jsonEntity.setSuccess(out.getBoolean("success"));

			//上传
			Map<String, Object> map = qiniuUploadService.ajaxUploadImage(file);
			String url = CommonConstants.IMAGES_SERVER_ADDR + map.get("data");
			System.out.println("url:" + url);
			jsonEntity.setUrl(url);

			String cardId = jsonEntity.getNum().substring(0, 6);
			TIdcardEntity IDcardEntity = dbDao.fetch(TIdcardEntity.class, Cnd.where("code", "=", cardId));
			if (!Util.isEmpty(IDcardEntity)) {
				jsonEntity.setProvince(IDcardEntity.getProvince());
				jsonEntity.setCity(IDcardEntity.getCity());
			}
		}

		if (!Util.isEmpty(jsonEntity)) {
			if (jsonEntity.isSuccess()) {
				//获取用户的基本信息
				TAppStaffBasicinfoEntity staffInfo = dbDao.fetch(TAppStaffBasicinfoEntity.class,
						Cnd.where("id", "=", staffid));
				//保存身份证正面信息
				saveBasicinfoFront(jsonEntity, staffInfo);
				//保存证件照片
				TAppStaffCredentialsEntity fetch = dbDao.fetch(TAppStaffCredentialsEntity.class,
						Cnd.where("staffid", "=", staffid).and("type", "=", TAppStaffCredentialsEnum.IDCARD.intKey()));
				if (Util.isEmpty(fetch)) {
					TAppStaffCredentialsEntity credentials = new TAppStaffCredentialsEntity();
					credentials.setStaffid(staffid);
					credentials.setCreatetime(new Date());
					credentials.setUpdatetime(new Date());
					credentials.setUrl(jsonEntity.getUrl());
					credentials.setType(TAppStaffCredentialsEnum.IDCARD.intKey());
					dbDao.insert(credentials);
				} else {
					fetch.setUrl(jsonEntity.getUrl());
					fetch.setUpdatetime(new Date());
					dbDao.update(fetch);
				}
			}
		}

		//消息通知
		try {
			RecognitionForm form = new RecognitionForm();
			form.setApplyid(staffid);
			simplesendinfosocket.broadcast(new TextMessage(JsonUtil.toJson(form)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonEntity;
		//}
	}

	public Object IDCardRecognitionBack(String encode, File file, int staffid) {
		/*String openid = redisDao.get(encode);
			if (Util.isEmpty(openid)) {
				return -1;
			} else {*/
		System.out.println("encode:" + encode + " file:" + file + " staffid:" + staffid);

		USStaffJsonEntity jsonEntity = new USStaffJsonEntity();
		String imageDataValue = saveDiskImageToDisk(file);
		Input input = new Input(imageDataValue, "back");
		RecognizeData rd = new RecognizeData();
		rd.getInputs().add(input);
		String content = Json.toJson(rd);
		String info = (String) appCodeCall(content);//扫描完毕
		System.out.println("info:" + info);
		//解析扫描的结果，结构化成标准json格式
		JSONObject resultObj = null;
		try {
			resultObj = new JSONObject(info);
		} catch (JSONException e) {
			jsonEntity.setSuccess(false);
			return jsonEntity;
		}
		JSONArray outputArray = resultObj.getJSONArray("outputs");
		String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
		JSONObject out = new JSONObject(output);
		if (out.getBoolean("success")) {
			String issue = out.getString("issue");
			jsonEntity.setIssue(issue);
			Date startDate;
			Date endDate;
			try {
				startDate = new SimpleDateFormat("yyyyMMdd").parse(out.getString("start_date"));
				endDate = new SimpleDateFormat("yyyyMMdd").parse(out.getString("end_date"));
				String startDateStr = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
				String endDateStr = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				jsonEntity.setStarttime(sdf.format(sdf.parse(startDateStr)));
				jsonEntity.setEndtime(sdf.format(sdf.parse(endDateStr)));
			} catch (JSONException | ParseException e) {
				e.printStackTrace();
				jsonEntity.setSuccess(false);
				return jsonEntity;

			}
			jsonEntity.setSuccess(out.getBoolean("success"));
			//上传
			Map<String, Object> map = qiniuUploadService.ajaxUploadImage(file);
			String url = CommonConstants.IMAGES_SERVER_ADDR + map.get("data");
			System.out.println("url:" + url);
			jsonEntity.setUrl(url);
		}

		if (!Util.isEmpty(jsonEntity)) {
			if (jsonEntity.isSuccess()) {
				//获取用户的基本信息
				TAppStaffBasicinfoEntity staffInfo = dbDao.fetch(TAppStaffBasicinfoEntity.class,
						Cnd.where("id", "=", staffid));
				//保存身份证背面信息
				saveBasicinfoBack(jsonEntity, staffInfo);

			}

			//保存证件照片
			TAppStaffCredentialsEntity fetch = dbDao.fetch(TAppStaffCredentialsEntity.class,
					Cnd.where("staffid", "=", staffid).and("type", "=", TAppStaffCredentialsEnum.IDCARDBACK.intKey()));
			if (Util.isEmpty(fetch)) {
				TAppStaffCredentialsEntity credentials = new TAppStaffCredentialsEntity();
				credentials.setStaffid(staffid);
				credentials.setCreatetime(new Date());
				credentials.setUpdatetime(new Date());
				credentials.setUrl(jsonEntity.getUrl());
				credentials.setType(TAppStaffCredentialsEnum.IDCARDBACK.intKey());
				dbDao.insert(credentials);
			} else {
				fetch.setUrl(jsonEntity.getUrl());
				fetch.setUpdatetime(new Date());
				dbDao.update(fetch);
			}

		}

		//消息通知
		try {
			RecognitionForm form = new RecognitionForm();
			form.setApplyid(staffid);
			simplesendinfosocket.broadcast(new TextMessage(JsonUtil.toJson(form)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonEntity;
		//}
	}

	public Object passportRecognitionBack(String encode, File file, int staffid) {

		/*String openid = redisDao.get(encode);
			if (Util.isEmpty(openid)) {
				return -1;
			} else {*/

		System.out.println("encode:" + encode + " file:" + file + " staffid:" + staffid);

		PassportJsonEntity jsonEntity = new PassportJsonEntity();
		String imageDataB64 = saveDiskImageToDisk(file);
		Input input = new Input(imageDataB64);
		RecognizeData rd = new RecognizeData();
		rd.getInputs().add(input);
		String content = Json.toJson(rd);
		String info = (String) aliPassportOcrAppCodeCall(content);
		System.out.println("info:" + info);

		//解析扫描的结果，结构化成标准json格式
		JSONObject resultObj = null;
		try {
			resultObj = new JSONObject(info);
		} catch (JSONException e) {
			jsonEntity.setSuccess(false);
			return jsonEntity;
		}
		JSONArray outputArray = resultObj.getJSONArray("outputs");
		String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
		JSONObject out = new JSONObject(output);
		String substring = "";
		if (out.getBoolean("success")) {
			String type = out.getString("type");
			if (!Util.isEmpty(type)) {
				substring = type.substring(0, 1);
			}
			jsonEntity.setType(substring);
			jsonEntity.setNum(out.getString("passport_no"));
			if (Util.isEmpty(jsonEntity.getNum()) || jsonEntity.getNum().length() != 9) {
				jsonEntity.setSuccess(false);
				return jsonEntity;
			}
			if (out.getString("sex").equals("F")) {
				jsonEntity.setSex("女");
				jsonEntity.setSexEn("F");
			} else {
				jsonEntity.setSex("男");
				jsonEntity.setSexEn("M");
			}
			//姓和名分开
			String nameEn = out.getString("name");//姓名拼音
			String nameAll = out.getString("name_cn");//姓名汉字
			char[] nameCnCharArray = nameAll.toCharArray();
			if (nameEn.contains(".")) {
				String[] nameEnSplit = nameEn.split("\\.");
				int lengthEn = nameEnSplit[0].length();
				int count = 0;
				int xingLength = 0;
				PinyinTool tool = new PinyinTool();
				try {
					for (int i = 0; i < nameCnCharArray.length; i++) {
						int length = tool.toPinYin(String.valueOf(nameCnCharArray[i]), "", Type.UPPERCASE).length();
						count += length;
						if (Util.eq(count, lengthEn)) {
							xingLength = i + 1;
						}
					}
					jsonEntity.setXingCn(nameAll.substring(0, xingLength));
					jsonEntity.setMingCn(nameAll.substring(xingLength));
				} catch (BadHanyuPinyinOutputFormatCombination e1) {
					e1.printStackTrace();
				}
			}
			if (Util.isEmpty(jsonEntity.getXingCn()) || Util.isEmpty(jsonEntity.getMingCn())) {
				jsonEntity.setSuccess(false);
				return jsonEntity;
			}

			jsonEntity.setOCRline1(out.getString("line0"));
			jsonEntity.setOCRline2(out.getString("line1"));
			jsonEntity.setBirthCountry(out.getString("birth_place"));
			jsonEntity.setVisaCountry(out.getString("issue_place"));
			Date birthDay;
			Date expiryDate;
			Date issueDate;
			try {
				birthDay = new SimpleDateFormat("yyyyMMdd").parse(out.getString("birth_date"));
				expiryDate = new SimpleDateFormat("yyyyMMdd").parse(out.getString("expiry_date"));
				issueDate = new SimpleDateFormat("yyyyMMdd").parse(out.getString("issue_date"));
				String startDateStr = new SimpleDateFormat("yyyy-MM-dd").format(birthDay);
				String endDateStr = new SimpleDateFormat("yyyy-MM-dd").format(expiryDate);
				String issueDateStr = new SimpleDateFormat("yyyy-MM-dd").format(issueDate);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				jsonEntity.setBirth(sdf.format(sdf.parse(startDateStr)));
				jsonEntity.setExpiryDay(sdf.format(sdf.parse(endDateStr)));
				jsonEntity.setIssueDate(sdf.format(sdf.parse(issueDateStr)));
			} catch (JSONException | ParseException e) {
				e.printStackTrace();
				jsonEntity.setSuccess(false);
				return jsonEntity;

			}
			jsonEntity.setSuccess(out.getBoolean("success"));
			//将图片上传到七牛云
			Map<String, Object> map = qiniuUploadService.ajaxUploadImage(file);
			String url = CommonConstants.IMAGES_SERVER_ADDR + map.get("data");
			System.out.println("url:" + url);
			jsonEntity.setUrl(url);
		}
		if (!Util.isEmpty(jsonEntity)) {
			if (jsonEntity.isSuccess()) {
				//获取用户的基本信息
				TAppStaffBasicinfoEntity staffInfo = dbDao.fetch(TAppStaffBasicinfoEntity.class,
						Cnd.where("id", "=", staffid));
				//获取用户的护照信息
				TAppStaffPassportEntity passportEntity = dbDao.fetch(TAppStaffPassportEntity.class,
						Cnd.where("staffid", "=", staffid));
				savePassport(jsonEntity, passportEntity, staffInfo);

				//保存证件照片
				TAppStaffCredentialsEntity fetch = dbDao.fetch(TAppStaffCredentialsEntity.class,
						Cnd.where("staffid", "=", staffid)
								.and("type", "=", TAppStaffCredentialsEnum.NEWHUZHAO.intKey()));
				if (Util.isEmpty(fetch)) {
					TAppStaffCredentialsEntity credentials = new TAppStaffCredentialsEntity();
					credentials.setStaffid(staffid);
					credentials.setCreatetime(new Date());
					credentials.setUpdatetime(new Date());
					credentials.setUrl(jsonEntity.getUrl());
					credentials.setType(TAppStaffCredentialsEnum.NEWHUZHAO.intKey());
					dbDao.insert(credentials);
				} else {
					fetch.setUrl(jsonEntity.getUrl());
					fetch.setUpdatetime(new Date());
					dbDao.update(fetch);
				}
			}
		}

		//消息通知
		try {
			RecognitionForm form = new RecognitionForm();
			form.setApplyid(staffid);
			simplesendinfosocket.broadcast(new TextMessage(JsonUtil.toJson(form)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonEntity;
		//}

	}

	/*
	 * 身份证正面资料保存
	 */
	public void saveBasicinfoFront(USStaffJsonEntity jsonEntity, TAppStaffBasicinfoEntity staffInfo) {
		staffInfo.setAddress(jsonEntity.getAddress());
		staffInfo.setAddressen(translate(jsonEntity.getAddress()));
		staffInfo.setCardfront(jsonEntity.getUrl());
		staffInfo.setCardId(jsonEntity.getNum());
		staffInfo.setCardIden(jsonEntity.getNum());
		staffInfo.setCardcity(jsonEntity.getCity());
		staffInfo.setCardcityen(translate(jsonEntity.getCity()));
		staffInfo.setCardprovince(jsonEntity.getProvince());
		staffInfo.setCardprovinceen(translate(jsonEntity.getProvince()));
		staffInfo.setSex(jsonEntity.getSex());
		staffInfo.setNation(jsonEntity.getNationality());
		staffInfo.setNationen(translate(jsonEntity.getNationality()));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			String d = jsonEntity.getBirth() + " 00:00:00";
			date = formatter.parse(d);
		} catch (ParseException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		staffInfo.setBirthday(date);
		dbDao.update(staffInfo);
	}

	/*
	 * 身份证背面信息保存
	 */
	public void saveBasicinfoBack(USStaffJsonEntity jsonEntity, TAppStaffBasicinfoEntity staffInfo) {
		staffInfo.setIssueorganization(jsonEntity.getIssue());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date datestar = new Date();
		Date dateend = new Date();
		try {
			String dstar = jsonEntity.getStarttime() + " 00:00:00";
			datestar = formatter.parse(dstar);
			String dend = jsonEntity.getEndtime() + " 00:00:00";
			dateend = formatter.parse(dend);
		} catch (ParseException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		staffInfo.setValidstartdate(datestar);
		staffInfo.setValidenddate(dateend);
		staffInfo.setCardback(jsonEntity.getUrl());
		dbDao.update(staffInfo, null);
	}

	/*
	 * 护照信息保存
	 */
	public void savePassport(PassportJsonEntity passportJsonEntity, TAppStaffPassportEntity passportEntity,
			TAppStaffBasicinfoEntity staffInfo) {
		//中文翻译成拼音并大写工具
		PinyinTool tool = new PinyinTool();
		//用户基本信息修改
		staffInfo.setFirstname(passportJsonEntity.getXingCn());//姓
		staffInfo.setFirstnameen(translatename(passportJsonEntity.getXingCn()));//姓英
		staffInfo.setLastname(passportJsonEntity.getMingCn());//名
		staffInfo.setLastnameen(translatename(passportJsonEntity.getMingCn()));//名英
		dbDao.update(staffInfo);
		//护照信息修改
		passportEntity.setFirstname(passportJsonEntity.getXingCn());//姓
		passportEntity.setFirstnameen(translatename(passportJsonEntity.getXingCn()));//姓英
		passportEntity.setLastname(passportJsonEntity.getMingCn());//名
		passportEntity.setLastnameen(translatename(passportJsonEntity.getMingCn()));//名英	
		passportEntity.setPassporturl(passportJsonEntity.getUrl());//护照照片地址
		passportEntity.setOCRline1(passportJsonEntity.getOCRline1());
		passportEntity.setOCRline2(passportJsonEntity.getOCRline2());
		passportEntity.setSex(passportJsonEntity.getSex());
		passportEntity.setSexen(passportJsonEntity.getSexEn());
		passportEntity.setBirthaddress(passportJsonEntity.getBirthCountry());//出生地

		if (Util.eq("陕西", passportJsonEntity.getBirthCountry())) {
			passportEntity.setBirthaddressen("SHAANXI");
		} else if (Util.eq("内蒙古", passportJsonEntity.getBirthCountry())) {
			passportEntity.setBirthaddressen("NEI MONGOL");
		} else {
			passportEntity.setBirthaddressen(translatename(passportJsonEntity.getBirthCountry()));
		}

		if (Util.eq("陕西", passportJsonEntity.getVisaCountry())) {
			passportEntity.setIssuedplaceen("SHAANXI");
		} else if (Util.eq("内蒙古", passportJsonEntity.getVisaCountry())) {
			passportEntity.setIssuedplaceen("NEI MONGOL");
		} else {
			passportEntity.setIssuedplaceen(translatename(passportJsonEntity.getVisaCountry()));
		}

		//passportEntity.setBirthaddressen(translate(passportJsonEntity.getBirthCountry()));
		//passportEntity.setIssuedplaceen(translate(passportJsonEntity.getVisaCountry()));
		passportEntity.setType(passportJsonEntity.getType());
		//出生日期
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date birthday = new Date();
		Date issueDate = new Date();//签发日期
		Date expiryDay = new Date();//有效期
		try {
			String d1 = passportJsonEntity.getBirth() + " 00:00:00";
			String d2 = passportJsonEntity.getIssueDate() + " 00:00:00";
			String d3 = passportJsonEntity.getExpiryDay() + " 00:00:00";
			birthday = formatter.parse(d1);
			issueDate = formatter.parse(d2);
			expiryDay = formatter.parse(d3);
		} catch (ParseException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		passportEntity.setBirthday(birthday);//设置出生日期
		passportEntity.setIssueddate(issueDate);//设置签发日期
		passportEntity.setValidenddate(expiryDay);//设置有效期至
		passportEntity.setIssuedplace(passportJsonEntity.getVisaCountry());//设置签发地点

		passportEntity.setPassport(passportJsonEntity.getNum());
		dbDao.update(passportEntity, null);

	}

	//翻译姓名
	public String translatename(String str) {
		PinyinTool tool = new PinyinTool();
		String result = null;
		try {
			result = tool.toPinYin(str, "", Type.UPPERCASE);
		} catch (BadHanyuPinyinOutputFormatCombination e1) {

			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		return result;

	}

	//翻译英文
	public String translate(String str) {
		String result = null;
		try {
			result = TranslateUtil.translate(str, "en");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	private static Object appCodeCall(String content) {
		String host = "https://dm-51.data.aliyun.com";
		String path = "/rest/160601/ocr/ocr_idcard.json";
		String method = "POST";
		String entityStr = "";
		String appcode = "19598dc0fd65499b93a9dec6c43489b7";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		//根据API的要求，定义相对应的Content-Type
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		try {
			/**
			 * 重要提示如下:
			 * HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 */
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, content);
			//获取response的body
			//System.out.println(EntityUtils.toString(response.getEntity()));
			entityStr = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entityStr;
	}

	private static Object aliPassportOcrAppCodeCall(String content) {
		String host = "http://ocrhz.market.alicloudapi.com";
		String path = "/rest/160601/ocr/ocr_passport.json";
		String method = "POST";
		/*String appcode = "db7570313ab4478793f42ad8cd48723b";*/
		String appcode = "19598dc0fd65499b93a9dec6c43489b7";
		String entityStr = "";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		//根据API的要求，定义相对应的Content-Type
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();

		try {
			/**
			 * 重要提示如下:
			 * HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 */
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, content);
			//System.out.println(response.toString());
			//获取response的body
			//System.out.println(EntityUtils.toString(response.getEntity()));
			entityStr = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entityStr;
	}

	//这个函数负责把获取到的InputStream流保存到本地。  
	public static byte[] saveImageToDisk(String filePath) {
		InputStream inputStream = null;
		inputStream = getInputStream(filePath);//调用getInputStream()函数。  
		byte[] data = new byte[1024];
		byte[] result = new byte[1024];
		int len = -1;
		ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
		try {
			while ((len = inputStream.read(data)) != -1) {//循环读取inputStream流中的数据，存入文件流fileOutputStream  
				fileOutputStream.write(data, 0, len);
			}
			result = fileOutputStream.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {//finally函数，不管有没有异常发生，都要调用这个函数下的代码  
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();//记得及时关闭文件流  
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();//关闭输入流  
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	//这个函数负责把获取到的InputStream流保存到本地。  
	public static ByteArrayOutputStream saveImageToOutputStream(String filePath) {
		InputStream inputStream = null;
		inputStream = getInputStream(filePath);//调用getInputStream()函数。  
		byte[] data = new byte[1024];
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		int len = -1;
		ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
		try {
			while ((len = inputStream.read(data)) != -1) {//循环读取inputStream流中的数据，存入文件流fileOutputStream  
				fileOutputStream.write(data, 0, len);
			}
			result = fileOutputStream;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {//finally函数，不管有没有异常发生，都要调用这个函数下的代码  
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();//记得及时关闭文件流  
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();//关闭输入流  
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static InputStream getInputStream(String filePath) {
		InputStream inputStream = null;
		HttpURLConnection httpURLConnection = null;
		try {
			URL url = new URL(filePath);//创建的URL  
			if (url != null) {
				httpURLConnection = (HttpURLConnection) url.openConnection();//打开链接  
				httpURLConnection.setConnectTimeout(3000);//设置网络链接超时时间，3秒，链接失败后重新链接  
				httpURLConnection.setDoInput(true);//打开输入流  
				httpURLConnection.setRequestMethod("GET");//表示本次Http请求是GET方式  
				int responseCode = httpURLConnection.getResponseCode();//获取返回码  
				if (responseCode == 200) {//成功为200  
					//从服务器获得一个输入流  
					inputStream = httpURLConnection.getInputStream();
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	//这个函数负责读取本地图片并转化成流。  
	public static String saveDiskImageToDisk(File filePath) {

		InputStream inputStream = null;
		byte[] data = new byte[1024];
		byte[] result = new byte[1024];
		int len = -1;
		ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
		try {
			inputStream = new FileInputStream(filePath);
			while ((len = inputStream.read(data)) != -1) {//循环读取inputStream流中的数据，存入文件流fileOutputStream  
				fileOutputStream.write(data, 0, len);
			}
			result = fileOutputStream.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {//finally函数，不管有没有异常发生，都要调用这个函数下的代码  
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();//记得及时关闭文件流  
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();//关闭输入流  
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return Base64.encodeBase64String(result);
	}

	//微信JSSDK上传的文件需要重新下载后上传到七牛云
	/*public Object wechatJsSDKUploadToQiniu(Integer staffId, String mediaIds, String sessionid, Integer type) {
			System.out.println("staffid=" + staffId + "  mediaIds=" + mediaIds + "  sessionid=" + sessionid + "  type="
					+ type);
			weXinTokenViewService.wechatJsSDKUploadToQiniu(staffId, mediaIds, sessionid, type);
			TAppStaffCredentialsEntity passport = dbDao.fetch(TAppStaffCredentialsEntity.class,
					Cnd.where("staffid", "=", staffId).and("type", "=", type));
			System.out.println("staffid=" + staffId + "  mediaIds=" + mediaIds + "  sessionid=" + sessionid + "  type="
					+ type);
			String url = passport.getUrl();
			passportRecognitionBack(url, staffId);
			System.out.println("url=" + url);
			System.out.println("staffid=" + staffId + "  mediaIds=" + mediaIds + "  sessionid=" + sessionid + "  type="
					+ type);
			return null;
		}*/

	/**
	 * 文件下载
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid
	 * @param response
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object downloadFile(int orderid, HttpServletResponse response) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Map<String, File> fileMap = new HashMap<String, File>();
		TemplateUtil templateUtil = new TemplateUtil();
		byte[] byteArray = null;

		TOrderUsEntity orderus = dbDao.fetch(TOrderUsEntity.class, orderid);
		String reviewurl = orderus.getReviewurl();
		String pdfurl = orderus.getPdfurl();
		String daturl = orderus.getDaturl();

		ByteArrayOutputStream reviewurlbyteArray = saveImageToOutputStream(reviewurl);
		ByteArrayOutputStream pdfurlbyteArray = saveImageToOutputStream(pdfurl);
		ByteArrayOutputStream daturlbyteArray = saveImageToOutputStream(daturl);

		fileMap.put("预览.PNG", templateUtil.createTempFile(reviewurlbyteArray));
		fileMap.put("确认.PDF", templateUtil.createTempFile(pdfurlbyteArray));
		fileMap.put("DAT文件.TXT", templateUtil.createTempFile(daturlbyteArray));
		stream = templateUtil.mergeToZip(fileMap);

		byteArray = stream.toByteArray();
		String filename = orderus.getOrdernumber() + ".zip";
		downloadType(filename, byteArray, response);
		return null;
	}

	public Object downloadType(String filename, byte[] type, HttpServletResponse response) {
		try {
			// 将文件进行编码
			String fileName = URLEncoder.encode(filename, "UTF-8");
			// 设置下载的响应头
			response.setContentType("application/zip");
			//通过response.reset()刷新可能存在一些未关闭的getWriter(),避免可能出现未关闭的getWriter()
			//response.setContentType("application/octet-stream");
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);// 设置文件名
			// 将字节流相应到浏览器（下载）
			IOUtils.write(type, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object toErrorphoto(String errorurl) {
		String imgurl = "";
		try {
			// 创建URL
			URL url = new URL(errorurl);
			// 创建链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			InputStream is = conn.getInputStream();
			imgurl = CommonConstants.IMAGES_SERVER_ADDR + qiniuUploadService.uploadImage(is, "", "");
			is.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return imgurl;
	}

	/**
	 * 计算两个日期的相差毫秒数
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param datePre
	 * @param dateLatter
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public long twoDatebetweenMillis(Date datePre, Date dateLatter) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
		long between = 0;
		try {
			Date begin = simpleDateFormat.parse(simpleDateFormat.format(datePre));
			Date end = simpleDateFormat.parse(simpleDateFormat.format(dateLatter));
			between = (end.getTime() - begin.getTime());
		} catch (ParseException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return between;
	}

}
