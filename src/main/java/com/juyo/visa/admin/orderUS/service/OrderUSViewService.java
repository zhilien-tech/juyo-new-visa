/**
 * SaleViewService.java
 * com.juyo.visa.admin.sale.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.orderUS.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.socket.TextMessage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.mail.service.MailService;
import com.juyo.visa.admin.order.entity.TIdcardEntity;
import com.juyo.visa.admin.orderUS.entity.USPassportJsonEntity;
import com.juyo.visa.admin.orderUS.entity.USStaffJsonEntity;
import com.juyo.visa.admin.orderUS.form.OrderUSListDataForm;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.AlredyVisaTypeEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JapanPrincipalChangeEnum;
import com.juyo.visa.common.enums.PrepareMaterialsEnum_JP;
import com.juyo.visa.common.enums.TravelpurposeEnum;
import com.juyo.visa.common.enums.USOrderStatusEnum;
import com.juyo.visa.common.enums.UserLoginEnum;
import com.juyo.visa.common.enums.orderUS.DistrictEnum;
import com.juyo.visa.common.enums.orderUS.IsPayedEnum;
import com.juyo.visa.common.enums.orderUS.USOrderListStatusEnum;
import com.juyo.visa.common.enums.visaProcess.VisaUSStatesEnum;
import com.juyo.visa.common.ocr.HttpUtils;
import com.juyo.visa.common.ocr.Input;
import com.juyo.visa.common.ocr.RecognizeData;
import com.juyo.visa.common.util.ImageDeal;
import com.juyo.visa.common.util.PinyinTool;
import com.juyo.visa.common.util.PinyinTool.Type;
import com.juyo.visa.common.util.SpringContextUtil;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TAppStaffOrderUsEntity;
import com.juyo.visa.entities.TAppStaffPassportEntity;
import com.juyo.visa.entities.TAppStaffVisaUsEntity;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TFlightEntity;
import com.juyo.visa.entities.TOrderUsEntity;
import com.juyo.visa.entities.TOrderUsFollowupEntity;
import com.juyo.visa.entities.TOrderUsInfoEntitiy;
import com.juyo.visa.entities.TOrderUsLogsEntity;
import com.juyo.visa.entities.TOrderUsTravelinfoEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.OrderUpdateForm;
import com.juyo.visa.forms.TAppStaffVisaUsAddForm;
import com.juyo.visa.forms.TAppStaffVisaUsUpdateForm;
import com.juyo.visa.websocket.USListWSHandler;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
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
	private MailService mailService;

	@Inject
	private UploadService qiniuUploadService;//文件上传

	private final static String SMS_SIGNATURE = "【优悦签】";
	private final static Integer US_YUSHANG_COMID = 65;
	//订单列表页连接websocket的地址
	private static final String USLIST_WEBSPCKET_ADDR = "uslistwebsocket";

	private USListWSHandler uslistwebsocket = (USListWSHandler) SpringContextUtil.getBean("usListHander",
			USListWSHandler.class);

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
		String localAddr = request.getLocalAddr();
		int localPort = request.getLocalPort();
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
		form.setUserid(loginUser.getId());
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
		for (Record record : list) {
			Integer orderid = (Integer) record.get("orderid");
			Object payObj = record.get("ispayed");
			Object cityObj = record.get("cityid");
			if (!Util.isEmpty(cityObj)) {
				int cityid = (int) cityObj;
				for (DistrictEnum district : DistrictEnum.values()) {
					if (cityid == district.intKey()) {
						record.set("cityid", district.value());
					}
				}
			}
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

			int orderStatus = (int) record.get("orderstatus");
			for (USOrderListStatusEnum statusEnum : USOrderListStatusEnum.values()) {
				if (!Util.isEmpty(orderStatus) && orderStatus == statusEnum.intKey()) {
					record.set("orderstatus", statusEnum.value());
				} else if (orderStatus == JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.intKey()) {
					record.set("orderstatus", JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.value());
				}
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
	public Object getOrderUSDetail(int orderid, HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		if (orderid == 0) {
			result.put("orderid", orderid);
			return result;
		}
		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();
		//格式化日期
		DateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		//订单信息
		TOrderUsEntity orderus = dbDao.fetch(TOrderUsEntity.class, orderid);
		result.put("orderinfo", orderus);
		TAppStaffOrderUsEntity orderUsEntity = dbDao.fetch(TAppStaffOrderUsEntity.class,
				Cnd.where("orderid", "=", orderid));
		//基本信息
		TAppStaffOrderUsEntity staffOrder = dbDao.fetch(TAppStaffOrderUsEntity.class,
				Cnd.where("orderid", "=", orderid));
		TAppStaffBasicinfoEntity basicinfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, staffOrder.getStaffid()
				.longValue());
		result.put("basicinfo", basicinfo);
		Integer staffid = basicinfo.getId();
		Integer status = orderus.getStatus();
		//订单状态
		for (USOrderListStatusEnum statusenum : USOrderListStatusEnum.values()) {
			if (status == statusenum.intKey()) {
				result.put("orderstatus", statusenum.value());
			} else if (status == JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.intKey()) {
				result.put("orderstatus", JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.value());
			}
		}
		//护照信息
		TAppStaffPassportEntity passport = dbDao.fetch(TAppStaffPassportEntity.class,
				Cnd.where("staffid", "=", staffid));
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
		if (!Util.isEmpty(orderTravelInfo)) {

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
			String travelpurpose = "";
			if (!Util.isEmpty(orderTravelInfo)) {
				travelpurpose = orderTravelInfo.getTravelpurpose();

			}
			if (!Util.isEmpty(travelpurpose)) {
				String travelpurposeString = TravelpurposeEnum.getValue(travelpurpose).getValue();
				//获取出行目的
				orderTravelInfo.setTravelpurpose(travelpurposeString);
			}
			List<Record> staffSummaryInfoList = (List<Record>) getStaffSummaryInfo(orderid);
			TOrderUsInfoEntitiy orderInfoEntity = (TOrderUsInfoEntitiy) getOrderInfo(orderid);
			if (!Util.isEmpty(orderTravelInfo.getGodeparturecity())) {
				TCityEntity gocity = dbDao.fetch(TCityEntity.class,
						Cnd.where("id", "=", orderTravelInfo.getGodeparturecity()));
				orderInfoEntity.setGoDepartureCity(gocity.getCity());
			}
			if (!Util.isEmpty(orderTravelInfo.getGoArrivedCity())) {
				TCityEntity gocity = dbDao.fetch(TCityEntity.class,
						Cnd.where("id", "=", orderTravelInfo.getGoArrivedCity()));
				orderInfoEntity.setGoArrivedCity((gocity.getCity()));
			}
			if (!Util.isEmpty(orderTravelInfo.getReturnDepartureCity())) {
				TCityEntity gocity = dbDao.fetch(TCityEntity.class,
						Cnd.where("id", "=", orderTravelInfo.getReturnDepartureCity()));
				orderInfoEntity.setReturnDepartureCity(gocity.getCity());
			}
			if (!Util.isEmpty(orderTravelInfo.getReturnArrivedCity())) {
				TCityEntity gocity = dbDao.fetch(TCityEntity.class,
						Cnd.where("id", "=", orderTravelInfo.getReturnArrivedCity()));
				orderInfoEntity.setReturnArrivedCity(gocity.getCity());
			}
			result.put("orderInfo", orderInfoEntity);
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

			if (!Util.isEmpty(staffSummaryInfoList)) {
				result.put("summaryInfo", staffSummaryInfoList.get(0));
			} else {
				result.put("summaryInfo", null);
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
		result.put("followinfo", followList);
		//领区下拉
		result.put("cityidenum", EnumUtil.enum2(DistrictEnum.class));
		//是否付款下拉
		result.put("ispayedenum", EnumUtil.enum2(IsPayedEnum.class));
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
			} else if (status == JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.intKey()) {
				result.put("orderstatus", JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.value());
			}
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
		if (Util.eq(userType, UserLoginEnum.BIG_COMPANY_ADMIN.intKey())) {
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
		orderus.setStatus(JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.intKey());
		dbDao.update(orderus);
		//插入日志
		insertLogs(orderid, orderus.getStatus(), loginUser.getId());
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
		Integer orderid = form.getOrderid();
		//获取出行信息表
		TOrderUsTravelinfoEntity orderTravelInfo = dbDao.fetch(TOrderUsTravelinfoEntity.class,
				Cnd.where("orderid", "=", orderid));
		if (Util.isEmpty(orderTravelInfo)) {
			orderTravelInfo = new TOrderUsTravelinfoEntity();
			orderTravelInfo.setOrderid(orderid);
			orderTravelInfo = dbDao.insert(orderTravelInfo);
		}
		if (!Util.isEmpty(form.getGodate()))
			orderTravelInfo.setGodate(form.getGodate());
		if (!Util.isEmpty(form.getLeavedate()))
			orderTravelInfo.setLeavedate(form.getLeavedate());
		if (!Util.isEmpty(form.getArrivedate()))
			orderTravelInfo.setArrivedate(form.getArrivedate());
		if (!Util.isEmpty(form.getStaydays()))
			orderTravelInfo.setStaydays(form.getStaydays());
		if (!Util.isEmpty(form.getPlanaddress()))
			orderTravelInfo.setAddress(form.getPlanaddress());
		if (!Util.isEmpty(form.getPlancity()))
			orderTravelInfo.setCity(form.getPlancity());
		if (!Util.isEmpty(form.getPlanstate()))
			orderTravelInfo.setState(form.getPlanstate());
		if (!Util.isEmpty(form.getTravelpurpose())) {
			String travelpurpose = form.getTravelpurpose();
			String key = TravelpurposeEnum.getEnum(travelpurpose).getKey();
			orderTravelInfo.setTravelpurpose(key);
		}
		orderTravelInfo.setHastripplan(form.getHastripplan());
		orderTravelInfo.setGodeparturecity(form.getGodeparturecity());
		orderTravelInfo.setGoArrivedCity(form.getGoArrivedCity());
		orderTravelInfo.setGoFlightNum(form.getGoFlightNum());
		orderTravelInfo.setReturnDepartureCity(form.getReturnDepartureCity());
		orderTravelInfo.setReturnArrivedCity(form.getReturnArrivedCity());
		orderTravelInfo.setReturnFlightNum(form.getReturnFlightNum());
		//修改出行信息
		int orderUpdateNum = dbDao.update(orderTravelInfo);
		TOrderUsEntity orderus = dbDao.fetch(TOrderUsEntity.class, orderid.longValue());
		orderus.setCityid(form.getCityid());
		orderus.setIspayed(form.getIspayed());
		orderus.setGroupname(form.getGroupname());
		orderus.setUpdatetime(new Date());
		dbDao.update(orderus);
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
	public Object addOrderByStuffId(Integer staffId) {

		TOrderUsEntity orderUs = new TOrderUsEntity();
		String orderNum = generateOrderNumByDate();
		Date nowDate = DateUtil.nowDate();
		orderUs.setOrdernumber(orderNum);
		orderUs.setComid(US_YUSHANG_COMID);
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

		String pcUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/tlogin";

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
				emailText = emailText.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum);
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
						.replace("${ordernum}", orderNum).replace("${mobileUrl}", telephone).replace("${email}", email);
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
						.replace("${ordernum}", orderNum);
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

	public Object IDCardRecognition(File file, HttpServletRequest request, HttpServletResponse response) {
		//将图片进行旋转处理
		ImageDeal imageDeal = new ImageDeal(file.getPath(), request.getContextPath(), UUID.randomUUID().toString(),
				"jpeg");
		File spin = null;
		try {
			spin = imageDeal.spin(-90);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//上传
		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(spin);
		file.delete();
		if (!Util.isEmpty(spin)) {
			spin.delete();
		}
		String url = CommonConstants.IMAGES_SERVER_ADDR + map.get("data");
		//从服务器上获取图片的流，读取扫描
		byte[] bytes = saveImageToDisk(url);
		String imageDataValue = Base64.encodeBase64String(bytes);
		Input input = new Input(imageDataValue, "face");
		RecognizeData rd = new RecognizeData();
		rd.getInputs().add(input);
		String content = Json.toJson(rd);
		String info = (String) appCodeCall(content);//扫描完毕
		System.out.println("info:" + info);
		//解析扫描的结果，结构化成标准json格式
		USStaffJsonEntity jsonEntity = new USStaffJsonEntity();
		JSONObject resultObj = new JSONObject(info);
		JSONArray outputArray = resultObj.getJSONArray("outputs");
		String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
		JSONObject out = new JSONObject(output);
		if (out.getBoolean("success")) {
			String addr = out.getString("address"); // 获取地址
			String name = out.getString("name"); // 获取名字
			String num = out.getString("num"); // 获取身份证号
			jsonEntity.setUrl(url);
			jsonEntity.setAddress(addr);
			Date date;
			try {
				date = new SimpleDateFormat("yyyyMMdd").parse(out.getString("birth"));
				String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				jsonEntity.setBirth(sdf.format(sdf.parse(dateStr)));
			} catch (JSONException | ParseException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			jsonEntity.setName(name);
			jsonEntity.setNationality(out.getString("nationality"));
			jsonEntity.setNum(num);
			jsonEntity.setRequest_id(out.getString("request_id"));
			jsonEntity.setSex(out.getString("sex"));
			jsonEntity.setSuccess(out.getBoolean("success"));
			String cardId = jsonEntity.getNum().substring(0, 6);
			TIdcardEntity IDcardEntity = dbDao.fetch(TIdcardEntity.class, Cnd.where("code", "=", cardId));
			if (!Util.isEmpty(IDcardEntity)) {
				jsonEntity.setProvince(IDcardEntity.getProvince());
				jsonEntity.setCity(IDcardEntity.getCity());
			}
		}
		return jsonEntity;
	}

	public Object IDCardRecognitionBack(File file, HttpServletRequest request, HttpServletResponse response) {
		//将图片进行旋转处理
		ImageDeal imageDeal = new ImageDeal(file.getPath(), request.getContextPath(), UUID.randomUUID().toString(),
				"jpeg");
		File spin = null;
		try {
			spin = imageDeal.spin(-90);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//上传
		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(spin);
		file.delete();
		if (!Util.isEmpty(spin)) {
			spin.delete();
		}
		String url = CommonConstants.IMAGES_SERVER_ADDR + map.get("data");
		//从服务器上获取图片的流，读取扫描
		byte[] bytes = saveImageToDisk(url);
		String imageDataValue = Base64.encodeBase64String(bytes);
		Input input = new Input(imageDataValue, "back");
		RecognizeData rd = new RecognizeData();
		rd.getInputs().add(input);
		String content = Json.toJson(rd);
		String info = (String) appCodeCall(content);//扫描完毕
		System.out.println("info:" + info);
		//解析扫描的结果，结构化成标准json格式
		USStaffJsonEntity jsonEntity = new USStaffJsonEntity();
		JSONObject resultObj = new JSONObject(info);
		JSONArray outputArray = resultObj.getJSONArray("outputs");
		String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
		JSONObject out = new JSONObject(output);
		if (out.getBoolean("success")) {
			String issue = out.getString("issue");
			jsonEntity.setIssue(issue);
			jsonEntity.setUrl(url);
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

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			jsonEntity.setSuccess(out.getBoolean("success"));
		}
		return jsonEntity;
	}

	public Object passportRecognitionBack(File file, HttpServletRequest request, HttpServletResponse response) {
		//将图片进行旋转处理
		ImageDeal imageDeal = new ImageDeal(file.getPath(), request.getContextPath(), UUID.randomUUID().toString(),
				"jpeg");
		File spin = null;
		try {
			spin = imageDeal.spin(-90);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//上传
		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(spin);
		file.delete();
		if (!Util.isEmpty(spin)) {
			spin.delete();
		}
		String url = CommonConstants.IMAGES_SERVER_ADDR + map.get("data");
		//从服务器上获取图片的流，读取扫描
		byte[] bytes = saveImageToDisk(url);

		String imageDataB64 = Base64.encodeBase64String(bytes);
		Input input = new Input(imageDataB64);

		RecognizeData rd = new RecognizeData();
		rd.getInputs().add(input);

		String content = Json.toJson(rd);
		String info = (String) aliPassportOcrAppCodeCall(content);
		System.out.println("info:" + info);

		//解析扫描的结果，结构化成标准json格式
		USPassportJsonEntity jsonEntity = new USPassportJsonEntity();
		JSONObject resultObj = new JSONObject(info);
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
			jsonEntity.setUrl(url);
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

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			jsonEntity.setSuccess(out.getBoolean("success"));
		}
		return jsonEntity;
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

}
