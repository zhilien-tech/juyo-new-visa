/**
 * VisaJapanService.java
 * com.juyo.visa.admin.visajp.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.juyo.visa.admin.changePrincipal.service.ChangePrincipalViewService;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.mail.service.MailService;
import com.juyo.visa.admin.order.service.OrderJpViewService;
import com.juyo.visa.admin.visajp.form.GeneratePlanForm;
import com.juyo.visa.admin.visajp.form.PassportForm;
import com.juyo.visa.admin.visajp.form.SaveTravelForm;
import com.juyo.visa.admin.visajp.form.VisaEditDataForm;
import com.juyo.visa.admin.visajp.form.VisaListDataForm;
import com.juyo.visa.admin.visajp.util.TemplateUtil;
import com.juyo.visa.common.base.JuYouResult;
import com.juyo.visa.common.base.QrCodeService;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.AlredyVisaTypeEnum;
import com.juyo.visa.common.enums.BusinessScopesEnum;
import com.juyo.visa.common.enums.CollarAreaEnum;
import com.juyo.visa.common.enums.CompanyTypeEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderProcessTypeEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.JobStatusEnum;
import com.juyo.visa.common.enums.MainSalePayTypeEnum;
import com.juyo.visa.common.enums.MainSaleTripTypeEnum;
import com.juyo.visa.common.enums.MainSaleUrgentEnum;
import com.juyo.visa.common.enums.MainSaleUrgentTimeEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.common.util.MapUtil;
import com.juyo.visa.common.util.RegExpUtil;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantFrontPaperworkJpEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TApplicantPassportEntity;
import com.juyo.visa.entities.TApplicantVisaJpEntity;
import com.juyo.visa.entities.TApplicantVisaPaperworkJpEntity;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.TComBusinessscopeEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TFlightEntity;
import com.juyo.visa.entities.THotelEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderTravelplanJpEntity;
import com.juyo.visa.entities.TOrderTripJpEntity;
import com.juyo.visa.entities.TOrderTripMultiJpEntity;
import com.juyo.visa.entities.TScenicEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class VisaJapanService extends BaseService<TOrderEntity> {

	@Inject
	private QrCodeService qrCodeService;
	@Inject
	private OrderJpViewService orderJpViewService;
	@Inject
	private MailService mailService;
	@Inject
	private DownLoadVisaFileService downLoadVisaFileService;
	@Inject
	private UploadService qiniuUpService;
	@Inject
	private TripAirlineService tripAirlineService;

	@Inject
	private ChangePrincipalViewService changePrincipalViewService;

	//签证实收通知销售手机短信模板
	private static final String VISA_NOTICE_SALE_MESSAGE_TMP = "messagetmp/visa_notice_sale_message_tmp.txt";

	private static final Integer VISA_PROCESS = JPOrderProcessTypeEnum.VISA_PROCESS.intKey();

	/**
	 * 签证列表数据
	 * <p>
	 * TODO 日本签证列表数据
	 *
	 * @param form
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object visaListData(VisaListDataForm form, HttpSession session) {
		//获取当前公司
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//获取当前用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		form.setUserid(loginUser.getId());
		form.setCompanyid(loginCompany.getId());
		form.setAdminId(loginCompany.getAdminId());
		Map<String, Object> result = Maps.newHashMap();
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
			Integer orderid = (Integer) record.get("id");
			String sqlStr = sqlManager.get("get_japan_visa_list_data_apply");
			Sql applysql = Sqls.create(sqlStr);
			List<Record> query = dbDao.query(applysql, Cnd.where("taoj.orderId", "=", orderid), null);
			for (Record apply : query) {
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
		result.put("pagetotal", pager.getPageCount());
		result.put("visaJapanData", list);
		return result;

	}

	/**
	 * TODO 获取日本签证详情页数据
	 * <p>
	 * TODO 获取订单详情页数据
	 *
	 * @param orderid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getJpVisaDetailData(Integer orderid) {
		Map<String, Object> result = Maps.newHashMap();
		//订单信息
		String sqlstr = sqlManager.get("get_jp_visa_order_info_byid");
		Sql sql = Sqls.create(sqlstr);
		sql.setParam("orderid", orderid);
		Record orderinfo = dbDao.fetch(sql);
		//格式化日期
		DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		if (!Util.isEmpty(orderinfo.get("gotripdate"))) {
			Date goTripDate = (Date) orderinfo.get("gotripdate");
			orderinfo.put("gotripdate", format.format(goTripDate));
		}
		if (!Util.isEmpty(orderinfo.get("backtripdate"))) {
			Date backTripDate = (Date) orderinfo.get("backtripdate");
			orderinfo.put("backtripdate", format.format(backTripDate));
		}
		if (!Util.isEmpty(orderinfo.get("sendvisadate"))) {
			Date sendVisaDate = (Date) orderinfo.get("sendvisadate");
			orderinfo.put("sendvisadate", format.format(sendVisaDate));
		}
		if (!Util.isEmpty(orderinfo.get("outvisadate"))) {
			Date outVisaDate = (Date) orderinfo.get("outvisadate");
			orderinfo.put("outvisadate", format.format(outVisaDate));
		}
		if (!Util.isEmpty(orderinfo.get("status"))) {
			for (JPOrderStatusEnum orderStatusEnum : JPOrderStatusEnum.values()) {
				Integer visastatus = orderinfo.getInt("status");
				if (visastatus.equals(orderStatusEnum.intKey())) {
					orderinfo.put("visastatus", orderStatusEnum.value());
				}
			}
		}
		result.put("orderinfo", orderinfo);
		//出行信息
		TOrderTripJpEntity travelinfo = dbDao.fetch(TOrderTripJpEntity.class, Cnd.where("orderId", "=", orderid));
		if (Util.isEmpty(travelinfo)) {
			travelinfo = new TOrderTripJpEntity();
			travelinfo.setTripType(1);
		}
		Map<String, String> tralinfoMap = obj2Map(travelinfo);
		if (!Util.isEmpty(travelinfo.getGoDate())) {
			tralinfoMap.put("goDate", format.format(travelinfo.getGoDate()));
		}
		if (!Util.isEmpty(travelinfo.getReturnDate())) {
			tralinfoMap.put("returnDate", format.format(travelinfo.getReturnDate()));
		}
		if (Util.isEmpty(travelinfo.getTripPurpose())) {
			tralinfoMap.put("trippurpose", "旅游");
		}
		result.put("travelinfo", tralinfoMap);
		//申请人信息
		String applysqlstr = sqlManager.get("get_jporder_detail_applyinfo_byorderid");
		Sql applysql = Sqls.create(applysqlstr);
		applysql.setParam("orderid", orderid);
		List<Record> applyinfo = dbDao.query(applysql, null, null);
		for (Record record : applyinfo) {
			Integer type = (Integer) record.get("type");
			for (JobStatusEnum visadatatype : JobStatusEnum.values()) {
				if (!Util.isEmpty(type) && type.equals(visadatatype.intKey())) {
					record.put("type", visadatatype.value());
				}
			}
			String data = "";
			String blue = "";
			if (!Util.isEmpty(record.get("blue"))) {
				blue = (String) record.get("blue");
			}
			String black = "";
			if (!Util.isEmpty(record.get("black"))) {
				black = (String) record.get("black");
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
			record.put("realinfo", data);
		}
		result.put("applyinfo", applyinfo);
		//行程安排
		result.put("travelplan", getTravelPlanByOrderId(orderid));
		return result;

	}

	/**
	 * 跳转到日本签证详情页面
	 * <p>
	 * TODO 为日本签证详情页面准备数据
	 *
	 * @param orderid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object visaDetail(Integer orderid) {
		Map<String, Object> result = Maps.newHashMap();
		//日本订单数据
		TOrderJpEntity jporderinfo = dbDao.fetch(TOrderJpEntity.class, orderid.longValue());
		result.put("jporderinfo", jporderinfo);
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, jporderinfo.getOrderId().longValue());
		result.put("orderinfo", orderinfo);
		//订单id
		result.put("orderid", orderid);
		//领区
		result.put("collarareaenum", EnumUtil.enum2(CollarAreaEnum.class));
		//加急
		result.put("mainsaleurgentenum", EnumUtil.enum2(MainSaleUrgentEnum.class));
		//工作日
		result.put("mainsaleurgenttimeenum", EnumUtil.enum2(MainSaleUrgentTimeEnum.class));
		//行程
		result.put("mainsaletriptypeenum", EnumUtil.enum2(MainSaleTripTypeEnum.class));
		//付款方式
		result.put("mainsalepaytypeenum", EnumUtil.enum2(MainSalePayTypeEnum.class));
		//签证类型
		result.put("mainsalevisatypeenum", EnumUtil.enum2(MainSaleVisaTypeEnum.class));
		//是否
		result.put("isyesornoenum", EnumUtil.enum2(IsYesOrNoEnum.class));
		//出行信息
		TOrderTripJpEntity travelinfo = dbDao.fetch(TOrderTripJpEntity.class, Cnd.where("orderId", "=", orderid));
		List<TOrderTripMultiJpEntity> multitrip = new ArrayList<TOrderTripMultiJpEntity>();
		if (Util.isEmpty(travelinfo)) {
			travelinfo = new TOrderTripJpEntity();
			travelinfo.setGoDate(orderinfo.getGoTripDate());
			travelinfo.setReturnDate(orderinfo.getBackTripDate());
			travelinfo.setTripType(1);
		} else {
			if (travelinfo.getTripType().equals(2)) {
				multitrip = dbDao.query(TOrderTripMultiJpEntity.class, Cnd.where("tripid", "=", travelinfo.getId()),
						null);
			} else if (travelinfo.getTripType().equals(1)) {
				//去程出发城市
				TCityEntity goleavecity = new TCityEntity();
				if (!Util.isEmpty(travelinfo.getGoDepartureCity())) {
					goleavecity = dbDao.fetch(TCityEntity.class, travelinfo.getGoDepartureCity().longValue());
				}
				result.put("goleavecity", goleavecity);
				//去程抵达城市
				TCityEntity goarrivecity = new TCityEntity();
				if (!Util.isEmpty(travelinfo.getGoArrivedCity())) {
					goarrivecity = dbDao.fetch(TCityEntity.class, travelinfo.getGoArrivedCity().longValue());
				}
				result.put("goarrivecity", goarrivecity);
				//回程出发城市
				TCityEntity backleavecity = new TCityEntity();
				if (!Util.isEmpty(travelinfo.getReturnDepartureCity())) {
					backleavecity = dbDao.fetch(TCityEntity.class, travelinfo.getReturnDepartureCity().longValue());
				}
				result.put("backleavecity", backleavecity);
				//回程返回城市
				TCityEntity backarrivecity = new TCityEntity();
				if (!Util.isEmpty(travelinfo.getReturnArrivedCity())) {
					backarrivecity = dbDao.fetch(TCityEntity.class, travelinfo.getReturnArrivedCity().longValue());
				}
				result.put("backarrivecity", backarrivecity);
				//去程航班
				TFlightEntity goflightnum = new TFlightEntity();
				if (!Util.isEmpty(travelinfo.getGoFlightNum())) {
					goflightnum = dbDao.fetch(TFlightEntity.class,
							Cnd.where("flightnum", "=", travelinfo.getGoFlightNum()));
				}
				result.put("goflightnum", goflightnum);
				//回程航班
				TFlightEntity returnflightnum = new TFlightEntity();
				if (!Util.isEmpty(travelinfo.getGoFlightNum())) {
					returnflightnum = dbDao.fetch(TFlightEntity.class,
							Cnd.where("flightnum", "=", travelinfo.getReturnFlightNum()));
				}
				result.put("returnflightnum", returnflightnum);
			}
		}
		result.put("travelinfo", travelinfo);
		//多程城市
		Integer[] mulcityids = new Integer[multitrip.size() * 2];
		int citycount = 0;
		for (TOrderTripMultiJpEntity tripMultiJp : multitrip) {
			mulcityids[citycount] = !Util.isEmpty(tripMultiJp.getDepartureCity()) ? tripMultiJp.getDepartureCity()
					: null;
			mulcityids[citycount + 1] = !Util.isEmpty(tripMultiJp.getArrivedCity()) ? tripMultiJp.getArrivedCity()
					: null;
			citycount += 2;
		}
		List<TCityEntity> citys = dbDao.query(TCityEntity.class, Cnd.where("id", "in", mulcityids), null);
		result.put("citys", citys);
		//多程航班号
		String[] mulflightids = new String[multitrip.size()];
		int flightcount = 0;
		for (TOrderTripMultiJpEntity tripMultiJp : multitrip) {
			mulflightids[flightcount] = !Util.isEmpty(tripMultiJp.getFlightNum()) ? tripMultiJp.getFlightNum() : "";
			flightcount++;
		}
		List<TFlightEntity> flights = dbDao
				.query(TFlightEntity.class, Cnd.where("flightnum", "in", mulflightids), null);
		//补全三个航程
		if (multitrip.size() < 3) {
			int multitripsize = multitrip.size();
			for (int i = 0; i < 3 - multitripsize; i++) {
				multitrip.add(new TOrderTripMultiJpEntity());
			}
		}
		result.put("flights", flights);
		result.put("multitrip", multitrip);
		result.put("multitripjson", JsonUtil.toJson(multitrip));

		return result;
	}

	/**
	 * 保存签证编辑页数据
	 * <p>
	 * TODO 保存签证编辑页数据
	 *
	 * @param editDataForm
	 * @param multiways 
	 * @param travelinfo 
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveJpVisaDetailInfo(VisaEditDataForm editDataForm, String travelinfojson, String multiways,
			HttpSession session) {
		//获取登录用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		//订单信息
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, editDataForm.getId().longValue());
		order.setNumber(editDataForm.getNumber());
		order.setCityId(editDataForm.getCityid());
		order.setUrgentType(editDataForm.getUrgenttype());
		order.setUrgentDay(editDataForm.getUrgentday());
		order.setTravel(editDataForm.getTravel());
		order.setPayType(editDataForm.getPaytype());
		order.setMoney(editDataForm.getMoney());
		order.setGoTripDate(editDataForm.getGotripdate());
		order.setStayDay(editDataForm.getStayday());
		order.setBackTripDate(editDataForm.getBacktripdate());
		order.setSendVisaDate(editDataForm.getSendvisadate());
		order.setOutVisaDate(editDataForm.getOutvisadate());
		order.setSendVisaNum(editDataForm.getSendvisanum());
		order.setUpdateTime(new Date());
		dbDao.update(order);
		//日本订单信息
		TOrderJpEntity jporder = dbDao.fetch(TOrderJpEntity.class, editDataForm.getOrderid().longValue());
		jporder.setVisaType(editDataForm.getVisatype());
		jporder.setVisaCounty(editDataForm.getVisacounty());
		jporder.setIsVisit(editDataForm.getIsvisit());
		jporder.setThreeCounty(editDataForm.getThreecounty());
		jporder.setRemark(editDataForm.getRemark());
		dbDao.update(jporder);
		//出行信息
		@SuppressWarnings("unchecked")
		Map<String, Object> travelmap = JsonUtil.fromJson(travelinfojson, Map.class);
		//Integer id = Integer.valueOf((String) travelmap.get("id"));
		//出行信息是否存在
		TOrderTripJpEntity travel = !Util.isEmpty(travelmap.get("id")) ? dbDao.fetch(TOrderTripJpEntity.class,
				Long.valueOf((String) travelmap.get("id"))) : new TOrderTripJpEntity();
		//travel = dbDao.fetch(TOrderTripJpEntity.class, (Integer.valueOf(travelmap.get("id"))).longValue());
		travel.setTripPurpose(String.valueOf(travelmap.get("trippurpose")));
		String godatestr = String.valueOf(travelmap.get("goDate"));
		List<TOrderTripMultiJpEntity> ordertriplist = new ArrayList<TOrderTripMultiJpEntity>();
		Integer triptype = null;
		if (!Util.isEmpty(travelmap.get("triptype"))) {
			triptype = Integer.valueOf(String.valueOf(travelmap.get("triptype")));
			travel.setTripType(triptype);
			if (triptype.equals(1)) {
				if (!Util.isEmpty(travelmap.get("goDate"))) {
					Date godate = DateUtil.string2Date(godatestr, DateUtil.FORMAT_YYYY_MM_DD);
					travel.setGoDate(godate);
				} else {
					travel.setGoDate(null);
				}
				String returndatestr = String.valueOf(travelmap.get("returnDate"));
				if (!Util.isEmpty(travelmap.get("returnDate"))) {
					Date returndate = DateUtil.string2Date(returndatestr, DateUtil.FORMAT_YYYY_MM_DD);
					travel.setReturnDate(returndate);
				} else {
					travel.setReturnDate(null);
				}
				//去程出发城市
				Integer godeparturecity = null;
				if (!Util.isEmpty(travelmap.get("godeparturecity"))) {
					godeparturecity = Integer.valueOf(String.valueOf(travelmap.get("godeparturecity")));
					travel.setGoDepartureCity(godeparturecity);
				} else {
					travel.setGoDepartureCity(null);
				}
				//去程抵达城市
				Integer goarrivedcity = null;
				if (!Util.isEmpty(travelmap.get("goarrivedcity"))) {
					goarrivedcity = Integer.valueOf(String.valueOf(travelmap.get("goarrivedcity")));
					travel.setGoArrivedCity(goarrivedcity);
				} else {
					travel.setGoArrivedCity(null);
				}
				if (!Util.isEmpty(travelmap.get("goflightnum"))) {
					travel.setGoFlightNum(String.valueOf(travelmap.get("goflightnum")));
				} else {
					travel.setGoFlightNum(null);
				}
				//回程出发城市
				Integer returndeparturecity = null;
				if (!Util.isEmpty(travelmap.get("returndeparturecity"))) {
					returndeparturecity = Integer.valueOf(String.valueOf(travelmap.get("returndeparturecity")));
					travel.setReturnDepartureCity(returndeparturecity);
				} else {
					travel.setReturnDepartureCity(null);
				}
				Integer returnarrivedcity = null;
				if (!Util.isEmpty(travelmap.get("returnarrivedcity"))) {
					returnarrivedcity = Integer.valueOf(String.valueOf(travelmap.get("returnarrivedcity")));
					travel.setReturnArrivedCity(returnarrivedcity);
				} else {
					travel.setReturnArrivedCity(null);
				}
				if (!Util.isEmpty(travelmap.get("returnflightnum"))) {
					travel.setReturnFlightNum(String.valueOf(travelmap.get("returnflightnum")));
				} else {
					travel.setReturnFlightNum(null);
				}
				//更新缓存数据
			} else if (triptype.equals(2) && !Util.isEmpty(multiways)) {
				//多程信息
				ordertriplist = JsonUtil.fromJsonAsList(TOrderTripMultiJpEntity.class, multiways);
			}
		} else {
			travel.setTripType(null);
		}
		Integer tripid = null;
		//保存出行信息
		if (Util.isEmpty(travelmap.get("id"))) {
			travel.setOpId(loginUser.getId());
			travel.setCreateTime(new Date());
			travel.setOrderId(editDataForm.getOrderid());
			TOrderTripJpEntity insert = dbDao.insert(travel);
			tripid = insert.getId();
		} else {
			travel.setUpdateTime(new Date());
			dbDao.update(travel);
			tripid = Integer.valueOf((String) travelmap.get("id"));
		}
		for (TOrderTripMultiJpEntity tripMultiJp : ordertriplist) {
			tripMultiJp.setTripid(tripid);
		}
		//保存多程信息
		if (!Util.isEmpty(triptype) && triptype.equals(2)) {
			List<TOrderTripMultiJpEntity> before = dbDao.query(TOrderTripMultiJpEntity.class,
					Cnd.where("tripid", "=", tripid), null);
			dbDao.updateRelations(before, ordertriplist);
		}

		//变更订单负责人
		Integer userId = loginUser.getId();
		Integer orderid = order.getId();
		changePrincipalViewService.ChangePrincipal(orderid, VISA_PROCESS, userId);

		return null;
	}

	/**
	 * 重置计划
	 * <p>
	 * TODO 重置计划
	 *
	 * @param orderid
	 * @param planid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object resetPlan(Integer orderid, Integer planid, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		TOrderTravelplanJpEntity plan = dbDao.fetch(TOrderTravelplanJpEntity.class, planid.longValue());
		if (!Util.eq("1", plan.getDay())) {
			//获取城市所有的酒店
			//List<THotelEntity> hotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", plan.getCityId()), null);
			//获取城市所有的景区
			String sqlString = sqlManager.get("get_reset_travel_plan_scenic");
			Sql sql = Sqls.create(sqlString);
			sql.setParam("orderid", orderid);
			sql.setParam("cityid", plan.getCityId());
			sql.setParam("scenicname", plan.getScenic());
			List<Record> scenics = dbDao.query(sql, null, null);
			Random random = new Random();
			plan.setScenic(scenics.get(random.nextInt(scenics.size())).getString("name"));
			//plan.setHotel(hotels.get(random.nextInt(hotels.size())).getId());
			dbDao.update(plan);
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid.longValue());
			//订单负责人变更
			changePrincipalViewService.ChangePrincipal(orderjp.getOrderId(), VISA_PROCESS, userId);

		}
		//行程安排
		return getTravelPlanByOrderId(orderid);

	}

	/**
	 * 生成行程安排
	 * <p>
	 * TODO 生成行程安排
	 *
	 * @param planform
	 * @return TODO 通过抵达城市生成行程安排
	 */
	public Object generatePlan(GeneratePlanForm planform, HttpSession session) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("status", "error");
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		//需要生成的travelplan
		List<TOrderTravelplanJpEntity> travelplans = Lists.newArrayList();
		//往返
		if (planform.getTriptype().equals(1)) {
			if (Util.isEmpty(planform.getGoArrivedCity())) {
				result.put("message", "请选择抵达城市");
				return result;
			}
			if (Util.isEmpty(planform.getGoDate())) {
				result.put("message", "请选择出发日期");
				return result;
			}
			if (Util.isEmpty(planform.getReturnDate())) {
				result.put("message", "请选择返回日期");
				return result;
			}
			if (Util.isEmpty(planform.getGoFlightNum())) {
				result.put("message", "请选择出发航班号");
				return result;
			}
			if (Util.isEmpty(planform.getReturnFlightNum())) {
				result.put("message", "请选择返回航班号");
				return result;
			}
			int daysBetween = DateUtil.daysBetween(planform.getGoDate(), planform.getReturnDate());
			//获取城市
			TCityEntity city = dbDao.fetch(TCityEntity.class, planform.getGoArrivedCity().longValue());
			//获取城市所有的酒店
			List<THotelEntity> hotels = dbDao.query(THotelEntity.class,
					Cnd.where("cityId", "=", planform.getGoArrivedCity()), null);
			//获取城市所有的景区
			List<TScenicEntity> scenics = dbDao.query(TScenicEntity.class,
					Cnd.where("cityId", "=", planform.getGoArrivedCity()), null);
			if (scenics.size() < daysBetween) {
				result.put("message", "没有更多的景区");
				return result;
			}

			Random random = new Random();
			//在一个城市只住一家酒店
			int hotelindex = random.nextInt(hotels.size());
			//为什么要<=，因为最后一天也要玩
			for (int i = 0; i <= daysBetween; i++) {
				TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
				travelplan.setCityId(planform.getGoArrivedCity());
				travelplan.setDay(String.valueOf(i + 1));
				travelplan.setOrderId(planform.getOrderid());
				travelplan.setOutDate(DateUtil.addDay(planform.getGoDate(), i));
				travelplan.setCityName(city.getCity());
				travelplan.setCreateTime(new Date());
				//酒店
				if (i != daysBetween) {
					THotelEntity hotel = hotels.get(hotelindex);
					travelplan.setHotel(hotel.getId());
				}
				if (i > 0 && i != daysBetween) {
					//景区
					int scenicindex = random.nextInt(scenics.size());
					TScenicEntity scenic = scenics.get(scenicindex);
					scenics.remove(scenic);
					travelplan.setScenic(scenic.getName());
				}
				travelplan.setOpId(loginUser.getOpId());
				travelplans.add(travelplan);
			}
		} else if (planform.getTriptype().equals(2)) {
			//多程
			List<TOrderTripMultiJpEntity> tripMultis = JsonUtil.fromJsonAsList(TOrderTripMultiJpEntity.class,
					planform.getMultiways());
			if (!Util.isEmpty(tripMultis)) {
				for (TOrderTripMultiJpEntity tripMultiJp : tripMultis) {
					if (Util.isEmpty(tripMultiJp.getDepartureDate())) {
						result.put("message", "请填写出发日期");
						return result;
					}
					if (Util.isEmpty(tripMultiJp.getDepartureCity())) {
						result.put("message", "请填写出发城市");
						return result;
					}
					if (Util.isEmpty(tripMultiJp.getArrivedCity())) {
						result.put("message", "请填写抵达城市");
						return result;
					}
				}
				if (Util.isEmpty(tripMultis.get(0).getFlightNum())) {
					result.put("message", "请选择去程航班");
					return result;
				}
				if (Util.isEmpty(tripMultis.get(tripMultis.size() - 1).getFlightNum())) {
					result.put("message", "请选择回程航班");
					return result;
				}
				TOrderTripMultiJpEntity pre = tripMultis.get(0);
				Date departureDate = pre.getDepartureDate();
				int count = 0;
				int day = 1;
				for (TOrderTripMultiJpEntity tripMultiJp : tripMultis) {
					if (count > 0) {
						int betweenday = DateUtil.daysBetween(pre.getDepartureDate(), tripMultiJp.getDepartureDate());
						TCityEntity city = dbDao.fetch(TCityEntity.class, pre.getArrivedCity().longValue());
						//获取城市所有的酒店
						List<THotelEntity> hotels = dbDao.query(THotelEntity.class,
								Cnd.where("cityId", "=", pre.getArrivedCity()), null);
						//获取城市所有的景区
						List<TScenicEntity> scenics = dbDao.query(TScenicEntity.class,
								Cnd.where("cityId", "=", pre.getArrivedCity()), null);
						Random random = new Random();
						//在一个城市只住一家酒店
						int hotelindex = random.nextInt(hotels.size());
						//最后一天也要玩
						if (count == (tripMultis.size() - 1)) {
							betweenday++;
						}
						for (int i = 0; i < betweenday; i++) {
							TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
							travelplan.setCityId(pre.getArrivedCity());
							travelplan.setCityName(city.getCity());
							travelplan.setDay(String.valueOf(day));
							if (i < betweenday - 1 || count < tripMultis.size() - 1) {
								travelplan.setHotel(hotels.get(hotelindex).getId());
							}
							travelplan.setOrderId(planform.getOrderid());
							travelplan.setOutDate(DateUtil.addDay(departureDate, day - 1));

							//景区
							//第一天、最后一天没有景区
							if (day > 1 && (i < betweenday - 1 || count < tripMultis.size() - 1)) {
								int scenicindex = random.nextInt(scenics.size());
								TScenicEntity scenic = scenics.get(scenicindex);
								scenics.remove(scenic);
								travelplan.setScenic(scenic.getName());
							}
							travelplan.setOpId(loginUser.getOpId());
							travelplan.setCreateTime(new Date());
							travelplans.add(travelplan);
							//自动显示第几天
							day++;
						}
					}
					pre = tripMultiJp;
					count++;
				}
			}
		}

		List<TOrderTravelplanJpEntity> before = dbDao.query(TOrderTravelplanJpEntity.class,
				Cnd.where("orderid", "=", planform.getOrderid()), null);
		//更新行程安排
		dbDao.updateRelations(before, travelplans);
		result.put("data", getTravelPlanByOrderId(planform.getOrderid()));
		result.put("status", "success");

		//订单负责人变更
		Integer orderid = planform.getOrderid();
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid.longValue());
		changePrincipalViewService.ChangePrincipal(orderjp.getOrderId(), VISA_PROCESS, userId);

		return result;
	}

	/**
	 * 获取行程安排数据
	 * <p>
	 * TODO 通过订单id获取行程安排数据
	 *
	 * @param orderid
	 * @return TODO 获取行程安排数据
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
			if (!Util.isEmpty(record.get("hotelname"))) {
				String hotelname = (String) record.get("hotelname");
				if (count > 1) {
					if (hotelname.equals(prehotelname)) {
						record.put("hotelname", "同上");
					}
				}
				prehotelname = hotelname;
			}
			count++;
		}
		return travelplans;
	}

	/**
	 * 打开行程安排编辑页面
	 * <p>
	 * TODO 为行程安排编辑页准备数据
	 *
	 * @param planid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object schedulingEdit(Integer planid) {
		Map<String, Object> result = Maps.newHashMap();
		TOrderTravelplanJpEntity plan = dbDao.fetch(TOrderTravelplanJpEntity.class, planid.longValue());
		result.put("travelplan", plan);
		THotelEntity hotel = new THotelEntity();
		if (!Util.isEmpty(plan.getHotel())) {
			hotel = dbDao.fetch(THotelEntity.class, plan.getHotel().longValue());
		}
		result.put("hotel", hotel);
		List<TScenicEntity> scenics = Lists.newArrayList();
		if (!Util.isEmpty(plan.getScenic())) {
			String[] Scenicnames = plan.getScenic().split(",");
			scenics = dbDao.query(TScenicEntity.class, Cnd.where("name", "in", Scenicnames), null);
		}
		result.put("scenics", scenics);
		return result;

	}

	/**
	 *  保存行程安排数据
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param travel
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveEditPlanData(TOrderTravelplanJpEntity travelform, HttpSession session) {

		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();

		TOrderTravelplanJpEntity travel = dbDao.fetch(TOrderTravelplanJpEntity.class, travelform.getId().longValue());
		//城市信息
		TCityEntity city = dbDao.fetch(TCityEntity.class, travelform.getCityId().longValue());
		//自动更改行程的天数
		if (!Util.isEmpty(travelform.getDay()) && !Util.isEmpty(travel.getDay())) {
			//数据库中的数据
			Integer preday = Integer.valueOf(travel.getDay());
			//页面传过来的数据
			Integer afterday = Integer.valueOf(travelform.getDay());
			travel.setOutDate(DateUtil.addDay(travel.getOutDate(), afterday - preday));
			if (afterday > preday) {
				//如果第二天改为第四天，则原来的第三天变为第二天，第四天变为第三天
				List<TOrderTravelplanJpEntity> bigtravel = dbDao.query(TOrderTravelplanJpEntity.class,
						Cnd.where("day", ">", preday).and("day", "<=", afterday), null);
				for (TOrderTravelplanJpEntity tOrderTravelplanJpEntity : bigtravel) {
					String day = tOrderTravelplanJpEntity.getDay();
					if (!Util.isEmpty(day)) {
						tOrderTravelplanJpEntity.setDay(String.valueOf(Integer.valueOf(day) - 1));
						tOrderTravelplanJpEntity.setOutDate(DateUtil.addDay(tOrderTravelplanJpEntity.getOutDate(), -1));
					}
				}
				dbDao.update(bigtravel);
			} else if (afterday < preday) {
				//如果第四天改为第二天，则原来的第二天变为第三天，第三天变为第四三天
				List<TOrderTravelplanJpEntity> bigtravel = dbDao.query(TOrderTravelplanJpEntity.class,
						Cnd.where("day", "<", preday).and("day", ">=", afterday), null);
				for (TOrderTravelplanJpEntity tOrderTravelplanJpEntity : bigtravel) {
					String day = tOrderTravelplanJpEntity.getDay();
					if (!Util.isEmpty(day)) {
						tOrderTravelplanJpEntity.setDay(String.valueOf(Integer.valueOf(day) + 1));
						tOrderTravelplanJpEntity.setOutDate(DateUtil.addDay(tOrderTravelplanJpEntity.getOutDate(), 1));
					}
				}
				dbDao.update(bigtravel);
			}
		}
		//更新城市的酒店和景点
		List<TOrderTravelplanJpEntity> cityplan = dbDao.query(TOrderTravelplanJpEntity.class,
				Cnd.where("cityid", "=", travelform.getCityId()), null);
		//如果城市未修改，则景区修改为页面上的
		if (!Util.isEmpty(cityplan)) {
			travel.setHotel(travelform.getHotel());
			travel.setScenic(travelform.getScenic());
		} else {
			//如果城市已经修改了，景区和酒店联动
			Random random = new Random();
			//获取城市所有的酒店
			List<THotelEntity> hotels = dbDao.query(THotelEntity.class,
					Cnd.where("cityId", "=", travelform.getCityId()), null);
			//获取城市所有的景区
			List<TScenicEntity> scenics = dbDao.query(TScenicEntity.class,
					Cnd.where("cityId", "=", travelform.getCityId()), null);
			int hotelindex = random.nextInt(hotels.size());
			int scenicindex = random.nextInt(scenics.size());
			travel.setHotel(hotels.get(hotelindex).getId());
			travel.setScenic(scenics.get(scenicindex).getName());
		}
		//更新页面信息
		travel.setCityId(travelform.getCityId());
		travel.setCityName(city.getCity());
		travel.setDay(travelform.getDay());
		travel.setUpdateTime(new Date());
		dbDao.update(travel);

		//订单负责人变更
		Integer orderid = travelform.getOrderId();
		if (!Util.isEmpty(orderid)) {
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid.longValue());
			changePrincipalViewService.ChangePrincipal(orderjp.getOrderId(), VISA_PROCESS, userId);
		}

		return null;
	}

	/**
	 * 跳转到实收页面
	 * <p>
	 * TODO 为实收页面准备数据
	 *
	 * @param session
	 * @param orderid
	 * @param type 
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object revenue(HttpSession session, Integer orderid, Integer type) {
		Map<String, Object> result = Maps.newHashMap();

		//申请人列表
		String sqlStr = sqlManager.get("get_japan_visa_list_data_apply");
		Sql applysql = Sqls.create(sqlStr);
		List<Record> query = dbDao.query(applysql, Cnd.where("taoj.orderId", "=", orderid), null);
		for (Record apply : query) {
			Integer dataType = (Integer) apply.get("dataType");
			for (JobStatusEnum dataTypeEnum : JobStatusEnum.values()) {
				if (!Util.isEmpty(dataType) && dataType.equals(dataTypeEnum.intKey())) {
					apply.put("dataType", dataTypeEnum.value());
				}
			}
			List<TApplicantVisaPaperworkJpEntity> revenue = dbDao.query(TApplicantVisaPaperworkJpEntity.class,
					Cnd.where("applicantId", "=", apply.get("applicatid")), null);
			List<TApplicantFrontPaperworkJpEntity> frontrevenue = dbDao.query(TApplicantFrontPaperworkJpEntity.class,
					Cnd.where("applicantId", "=", apply.get("applicatid")), null);
			apply.put("revenue", revenue);
			apply.put("frontrevenue", frontrevenue);
		}
		result.put("applicant", query);
		result.put("orderid", orderid);
		result.put("type", type);
		return result;
	}

	/**
	 * 加载实收页面数据
	 * <p>
	 * TODO 加载实收页面数据
	 *
	 * @param session
	 * @param orderid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object visaRevenue(HttpSession session, Integer orderid) {
		Map<String, Object> result = Maps.newHashMap();
		TOrderJpEntity orderinfo = dbDao.fetch(TOrderJpEntity.class, orderid.longValue());
		result.put("orderinfo", orderinfo);
		return result;
	}

	/**
	 * 保存实收信息
	 * <p>
	 * TODO 保存实收信息
	 *
	 * @param applicatid
	 * @param realInfo
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveApplicatRevenue(Integer applicatid, String realInfo, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TApplicantVisaPaperworkJpEntity visapaperwork = new TApplicantVisaPaperworkJpEntity();
		visapaperwork.setApplicantId(applicatid);
		visapaperwork.setCreateTime(new Date());
		visapaperwork.setRealInfo(realInfo);
		visapaperwork.setOpId(loginUser.getId());
		visapaperwork.setStatus(0);
		TApplicantOrderJpEntity applicantorder = dbDao.fetch(TApplicantOrderJpEntity.class, applicatid.longValue());
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, applicantorder.getOrderId().longValue());
		changePrincipalViewService.ChangePrincipal(orderjp.getOrderId(), VISA_PROCESS, loginUser.getId());
		return dbDao.insert(visapaperwork);
	}

	/**
	 *保存真是资料数据
	 * <p>
	 * TODO 保存真实资料数据
	 *
	 * @param orderjp
	 * @param applicatinfo 
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveRealInfoData(TOrderJpEntity orderjp, String applicatinfo, HttpSession session) {

		TUserEntity loginuser = LoginUtil.getLoginUser(session);
		Integer userId = loginuser.getId();

		//更新备注信息
		dbDao.update(orderjp);
		List<Map> applicatlist = JsonUtil.fromJson(applicatinfo, List.class);
		//需要更新的真实资料
		List<TApplicantVisaPaperworkJpEntity> paperworks = Lists.newArrayList();
		//所有的人
		for (Map map : applicatlist) {
			String applicatid = map.get("applicatid").toString();
			//String datatext = String.valueOf(map.get("datatext"));
			String revenuejson = String.valueOf(map.get("revenue"));
			List<TApplicantVisaPaperworkJpEntity> paperworkjps = JsonUtil.fromJsonAsList(
					TApplicantVisaPaperworkJpEntity.class, revenuejson);
			for (TApplicantVisaPaperworkJpEntity paperworkjp : paperworkjps) {
				//判断id是否存在
				if (!Util.isEmpty(paperworkjp.getId())) {
					TApplicantVisaPaperworkJpEntity fetch = dbDao.fetch(TApplicantVisaPaperworkJpEntity.class,
							paperworkjp.getId().longValue());
					if (!Util.isEmpty(paperworkjp.getRealInfo())) {
						fetch.setRealInfo(paperworkjp.getRealInfo());
						fetch.setStatus(paperworkjp.getStatus());
						paperworks.add(fetch);
					} else {
						dbDao.delete(fetch);
					}
				} else {
					paperworkjp.setApplicantId(Integer.valueOf(applicatid));
					dbDao.insert(paperworkjp);
				}
			}

		}
		//删掉灰色的
		if (!Util.isEmpty(paperworks)) {
			dbDao.update(paperworks);
		}
		//添加日志
		orderJpViewService.insertLogs(orderjp.getOrderId(), JPOrderStatusEnum.VISA_RECEIVED.intKey(), session);

		//订单负责人变更
		Integer orderId = orderjp.getOrderId();
		changePrincipalViewService.ChangePrincipal(orderId, VISA_PROCESS, userId);

		return null;

	}

	/**
	 * 跳转到签证录入列表页面
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param session
	 * @param applyid
	 * @param isvisa 
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object visaInput(HttpSession session, Integer applyid, Integer orderid, Integer isvisa) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("applyid", applyid);
		result.put("isvisa", isvisa);
		result.put("orderid", orderid);
		return result;
	}

	/**
	 * 获取签证录入列表数据
	 * <p>
	 * TODO 获取签证录入列表数据
	 *
	 * @param applyid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getJpVisaInputListData(Integer applyid) {
		Map<String, Object> result = Maps.newHashMap();
		List<TApplicantVisaJpEntity> visainputs = dbDao.query(TApplicantVisaJpEntity.class,
				Cnd.where("applicantId", "=", applyid), null);
		List<Map<String, Object>> visainputmaps = Lists.newArrayList();
		for (TApplicantVisaJpEntity VisaJpEntity : visainputs) {
			DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
			Map<String, Object> visainputmap = Maps.newHashMap();
			if (!Util.isEmpty(VisaJpEntity.getVisaDate())) {
				visainputmap.put("visadatestr", format.format(VisaJpEntity.getVisaDate()));
			}
			if (!Util.isEmpty(VisaJpEntity.getValidDate())) {
				visainputmap.put("validdatestr", format.format(VisaJpEntity.getValidDate()));
			}
			String visatypestr = "";
			if (!Util.isEmpty(VisaJpEntity.getVisaType())) {
				for (AlredyVisaTypeEnum typeEnum : AlredyVisaTypeEnum.values()) {
					if (VisaJpEntity.getVisaType().equals(typeEnum.intKey())) {
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

	/**
	 * 护照详情
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param applyId
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object passportInfo(Integer applyId) {
		Map<String, Object> result = Maps.newHashMap();
		TApplicantOrderJpEntity applyjp = dbDao.fetch(TApplicantOrderJpEntity.class, applyId.longValue());
		TApplicantPassportEntity passport = dbDao.fetch(TApplicantPassportEntity.class,
				Cnd.where("applicantId", "=", applyjp.getApplicantId()));
		if (Util.isEmpty(passport)) {
			passport = new TApplicantPassportEntity();
		}
		Map<String, String> passportMap = MapUtil.obj2Map(passport);
		DateFormat dateformat = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		//性别
		String sexstr = "";
		if (!Util.isEmpty(passport.getSex())) {
			sexstr += passport.getSex();
		}
		sexstr += "/";
		if (!Util.isEmpty(passport.getSexEn())) {
			sexstr += passport.getSexEn();
		}
		passportMap.put("sexstr", sexstr);
		//出生地点
		String birthaddressstr = "";
		if (!Util.isEmpty(passport.getBirthAddress())) {
			birthaddressstr += passport.getBirthAddress();
		}
		birthaddressstr += "/";
		if (!Util.isEmpty(passport.getBirthAddressEn())) {
			birthaddressstr += passport.getBirthAddressEn();
		}
		passportMap.put("birthaddressstr", birthaddressstr);
		//签发地点
		String issuedplacestr = "";
		if (!Util.isEmpty(passport.getIssuedPlace())) {
			issuedplacestr += passport.getIssuedPlace();
		}
		issuedplacestr += "/";
		if (!Util.isEmpty(passport.getIssuedPlaceEn())) {
			issuedplacestr += passport.getIssuedPlaceEn();
		}
		passportMap.put("issuedplacestr", issuedplacestr);
		if (!Util.isEmpty(passport.getBirthday())) {
			passportMap.put("birthday", dateformat.format(passport.getBirthday()));
		}
		if (!Util.isEmpty(passport.getIssuedDate())) {
			passportMap.put("issueddate", dateformat.format(passport.getIssuedDate()));
		}
		if (!Util.isEmpty(passport.getValidEndDate())) {
			passportMap.put("validenddate", dateformat.format(passport.getValidEndDate()));
		}
		result.put("passport", passportMap);
		return result;

	}

	/**
	 * 保存护照信息
	 * <p>
	 * TODO 保存护照信息
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object savePassportInfo(PassportForm form, HttpSession session) {

		TApplicantPassportEntity passport = dbDao.fetch(TApplicantPassportEntity.class, form.getId().longValue());
		passport.setType(form.getType());
		passport.setPassport(form.getPassport());
		//性别
		String[] sexsplit = form.getSexstr().split("/");
		if (sexsplit.length == 2) {
			passport.setSex(sexsplit[0]);
			passport.setSexEn(sexsplit[1]);
		} else if (sexsplit.length == 1) {
			passport.setSex(sexsplit[0]);
		}
		//出生地点
		String[] birthaddrsplit = form.getBirthaddressstr().split("/");
		if (birthaddrsplit.length == 2) {
			passport.setBirthAddress(birthaddrsplit[0]);
			passport.setBirthAddressEn(birthaddrsplit[1]);
		} else if (birthaddrsplit.length == 1) {
			passport.setBirthAddress(birthaddrsplit[0]);
		}
		passport.setBirthday(form.getBirthday());
		//签发地点
		String[] issuedsplit = form.getIssuedplacestr().split("/");
		if (issuedsplit.length == 2) {
			passport.setIssuedPlace(issuedsplit[0]);
			passport.setIssuedPlaceEn(issuedsplit[1]);
		} else if (issuedsplit.length == 1) {
			passport.setIssuedPlace(issuedsplit[0]);
		}
		passport.setIssuedDate(form.getIssueddate());
		passport.setValidType(form.getValidtype());
		passport.setValidEndDate(form.getValidenddate());
		passport.setUpdateTime(new Date());
		return dbDao.update(passport);
	}

	/**
	 * 保存护照信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param applicatid
	 * @param inputVal
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object editPassportCount(Integer applicatid, String inputVal) {

		TApplicantVisaPaperworkJpEntity paperwork = dbDao.fetch(TApplicantVisaPaperworkJpEntity.class,
				Cnd.where("applicantId", "=", applicatid).and("realInfo", "like", "%护照%"));
		paperwork.setRealInfo(inputVal);
		dbDao.update(paperwork);
		return null;
	}

	/**
	 * 自动计算返回日期
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param gotripdate
	 * @param stayday
	 */
	public Object autoCalculateBackDate(Date gotripdate, Integer stayday) {
		Date backtripdate = DateUtil.addDay(gotripdate, stayday - 1);
		DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		return format.format(backtripdate);
	}

	/**
	 * 验证是否为原始的实收材料
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param paperid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object validateIsoriginal(Integer paperid) {
		boolean result = false;
		TApplicantVisaPaperworkJpEntity fetch = dbDao.fetch(TApplicantVisaPaperworkJpEntity.class, paperid.longValue());
		if (!Util.isEmpty(fetch) && !Util.isEmpty(fetch.getType())) {
			result = true;
		}
		return result;

	}

	/**
	 * 移交售后
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object afterMarket(Long orderid, HttpServletRequest request) {
		HttpSession session = request.getSession();
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid);
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
		orderinfo.setStatus(JPOrderStatusEnum.AFTERMARKET_ORDER.intKey());
		dbDao.update(orderinfo);
		//添加日志
		orderJpViewService.insertLogs(orderinfo.getId(), JPOrderStatusEnum.AFTERMARKET_ORDER.intKey(), session);

		//变更订单负责人
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		changePrincipalViewService.ChangePrincipal(orderjp.getOrderId(), VISA_PROCESS, userId);
		return "success";
	}

	/**
	 * 通知销售
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param applyid
	 * @return TODO 申请人id
	 */
	public Object noticeSale(Integer applyid, Integer orderid, HttpSession session) {
		//申请人信息
		TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, applyid.longValue());
		//日本申请人信息
		TApplicantOrderJpEntity applicantjp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applyid));
		//日本订单 信息
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, applicantjp.getOrderId().longValue());
		//订单信息
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
		//资料信息
		List<TApplicantVisaPaperworkJpEntity> materials = dbDao.query(TApplicantVisaPaperworkJpEntity.class,
				Cnd.where("applicantId", "=", applicantjp.getId()), null);
		//销售人员信息
		TUserEntity saleinfo = dbDao.fetch(TUserEntity.class, order.getUserId().longValue());
		//销售人员手机号
		String mobile = saleinfo.getMobile();
		boolean mobileLegal = RegExpUtil.isMobileLegal(mobile);
		if (!mobileLegal) {
			return JuYouResult.build(500, "该订单为公司管理员创建的订单，无法发送短信");
		}
		//订单号
		String ordernum = order.getOrderNum();
		//姓名
		String name = applicant.getFirstName() + applicant.getLastName();
		//手机号
		String telephone = applicant.getTelephone();
		//缺少的资料
		StringBuffer data = new StringBuffer("");
		for (TApplicantVisaPaperworkJpEntity tApplicantVisaPaperworkJpEntity : materials) {
			if (tApplicantVisaPaperworkJpEntity.getStatus().equals(1)) {
				data.append(tApplicantVisaPaperworkJpEntity.getRealInfo());
				data.append("、");
			}
		}
		String datastr = data.toString();
		datastr = datastr.substring(0, datastr.length() - 1);
		Map<String, String> param = Maps.newHashMap();
		param.put("${name}", name);
		param.put("${ordernum}", ordernum);
		param.put("${telephone}", telephone);
		param.put("${data}", datastr);
		mailService.sendMessageByMap(mobile, param, VISA_NOTICE_SALE_MESSAGE_TMP);

		//订单负责人变更
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		changePrincipalViewService.ChangePrincipal(order.getId(), VISA_PROCESS, userId);

		return JuYouResult.ok();
	}

	/**
	 * 发招保弹框
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param request
	 * @param orderid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object sendZhaoBao(HttpServletRequest request, Long orderid) {
		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("orderid", orderid);
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid);
		result.put("orderjpinfo", orderjp);
		List<TCompanyEntity> songqianlist = Lists.newArrayList();
		//送签社下拉
		if (loginCompany.getComType().equals(CompanyTypeEnum.SONGQIAN.intKey())
				|| loginCompany.getComType().equals(CompanyTypeEnum.SONGQIANSIMPLE.intKey())) {
			songqianlist = dbDao
					.query(TCompanyEntity.class, Cnd.where("adminId", "=", loginCompany.getAdminId()), null);
		} else {
			TCompanyEntity songqian = dbDao.fetch(TCompanyEntity.class, orderjp.getSendsignid().longValue());
			songqianlist.add(songqian);
		}
		result.put("songqianlist", songqianlist);
		//地接社下拉
		List<TCompanyEntity> dijielist = dbDao.query(TCompanyEntity.class,
				Cnd.where("comType", "=", CompanyTypeEnum.DIJI.intKey()), null);
		result.put("dijielist", dijielist);
		return result;
	}

	/**
	 * 
	 * 发招宝失败
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param data
	 * @param orderid
	 * @return
	 */
	public Object sendZhaoBaoError(String data, Integer orderid, HttpSession session, Integer type) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		Map<String, Object> result = Maps.newHashMap();
		result.put("orderid", orderid);
		result.put("data", data);
		result.put("type", type);
		//变更订单负责人
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid.longValue());
		changePrincipalViewService.ChangePrincipal(orderjp.getOrderId(), VISA_PROCESS, userId);
		return result;
	}

	/**
	 * 保存招宝信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderJpEntity
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveZhaoBao(TOrderJpEntity orderJpEntity, HttpServletRequest request) {
		HttpSession session = request.getSession();
		TUserEntity loginuser = LoginUtil.getLoginUser(session);
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//查询日本订单表信息
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderJpEntity.getOrderId().longValue());
		//查询订单表信息
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
		//送签社
		if (loginCompany.getComType().equals(CompanyTypeEnum.SONGQIAN.intKey())) {
			orderinfo.setStatus(JPOrderStatusEnum.READYCOMMING.intKey());
			//订单负责人变更
			Integer userId = loginuser.getId();
			changePrincipalViewService.ChangePrincipal(orderjp.getOrderId(), VISA_PROCESS, userId);
		} else if (loginCompany.getComType().equals(CompanyTypeEnum.SONGQIANSIMPLE.intKey())) {
			orderinfo.setStatus(JPOrderStatusEnum.READYCOMMING.intKey());
		} else {
			//地接社为准备提交大使馆
			orderinfo.setStatus(JPOrderStatusEnum.READYCOMMING.intKey());
		}
		//更新订单状态为发招保中或准备提交大使馆
		dbDao.update(orderinfo);
		//生成excel
		//申请人信息
		Map<String, Object> tempdata = new HashMap<String, Object>();
		String applysqlstr = sqlManager.get("get_applyinfo_from_filedown_by_orderid_jp");
		Sql applysql = Sqls.create(applysqlstr);
		Cnd cnd = Cnd.NEW();
		cnd.and("taoj.orderId", "=", orderjp.getId());
		List<Record> applyinfo = dbDao.query(applysql, cnd, null);
		tempdata.put("applyinfo", applyinfo);
		//excel导出
		ByteArrayOutputStream excelExport = downLoadVisaFileService.excelExport(tempdata);
		//生成excel临时文件
		TemplateUtil templateUtil = new TemplateUtil();
		File excelfile = templateUtil.createTempFile(excelExport);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(excelfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String qiniuurl = qiniuUpService.uploadImage(fileInputStream, "xlsx", null);
		//返回上传后七牛云的路径
		String fileqiniupath = CommonConstants.IMAGES_SERVER_ADDR + qiniuurl;
		orderjp.setSendsignid(orderJpEntity.getSendsignid());
		orderjp.setGroundconnectid(orderJpEntity.getGroundconnectid());
		orderjp.setZhaobaotime(new Date());
		//保存生成的七牛excel路径
		orderjp.setExcelurl(fileqiniupath);
		dbDao.update(orderjp);
		orderJpViewService.insertLogs(orderinfo.getId(), JPOrderStatusEnum.AUTO_FILL_FORM_ING.intKey(), session);

		return null;
	}

	/**
	 * 获取申请人列表信息
	 */
	public Object getVisaDetailApply(Integer orderid) {
		Map<String, Object> result = Maps.newHashMap();
		//申请人信息
		String applysqlstr = sqlManager.get("get_jporder_detail_applyinfo_byorderid");
		Sql applysql = Sqls.create(applysqlstr);
		applysql.setParam("orderid", orderid);
		List<Record> applyinfo = dbDao.query(applysql, null, null);
		for (Record record : applyinfo) {
			Integer type = (Integer) record.get("type");
			for (JobStatusEnum visadatatype : JobStatusEnum.values()) {
				if (!Util.isEmpty(type) && type.equals(visadatatype.intKey())) {
					record.put("type", visadatatype.value());
				}
			}
			String data = "";
			String blue = "";
			if (!Util.isEmpty(record.get("blue"))) {
				blue = (String) record.get("blue");
			}
			String black = "";
			if (!Util.isEmpty(record.get("black"))) {
				black = (String) record.get("black");
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
			record.put("realinfo", data);
		}
		result.put("applyinfo", applyinfo);
		return result;
	}

	/**
	 * 验证发招保信息是否完整
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object validateInfoIsFull(Integer orderjpid) {
		StringBuffer resultstrbuf = new StringBuffer("");
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
		//订单信息
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
		//出行信息
		TOrderTripJpEntity ordertripjp = dbDao.fetch(TOrderTripJpEntity.class,
				Cnd.where("orderId", "=", orderjp.getId()));
		List<TOrderTripMultiJpEntity> mutiltrip = new ArrayList<TOrderTripMultiJpEntity>();
		if (!Util.isEmpty(ordertripjp)) {
			mutiltrip = dbDao.query(TOrderTripMultiJpEntity.class, Cnd.where("tripid", "=", ordertripjp.getId()), null);
		}
		//申请人信息
		String applysqlstr = sqlManager.get("get_applyinfo_from_filedown_by_orderid_jp");
		Sql applysql = Sqls.create(applysqlstr);
		Cnd cnd = Cnd.NEW();
		cnd.and("taoj.orderId", "=", orderjp.getId());
		List<Record> applyinfo = dbDao.query(applysql, cnd, null);
		//行程安排
		List<TOrderTravelplanJpEntity> ordertravelplan = dbDao.query(TOrderTravelplanJpEntity.class,
				Cnd.where("orderId", "=", orderjp.getId()), null);
		//公司信息
		TCompanyEntity company = new TCompanyEntity();
		company = dbDao.fetch(TCompanyEntity.class, orderinfo.getComId().longValue());
		//判断签证类型
		if (Util.isEmpty(orderjp.getVisaType())) {
			resultstrbuf.append("签证类型、");
		} else {
			if (!orderjp.getVisaType().equals(MainSaleVisaTypeEnum.SINGLE.intKey())) {
				if (Util.isEmpty(orderjp.getVisaCounty())) {
					resultstrbuf.append("签证县、");
				}
			}
		}
		if (Util.isEmpty(orderinfo.getGoTripDate())) {
			resultstrbuf.append("出发日期、");
		}
		if (Util.isEmpty(orderinfo.getBackTripDate())) {
			resultstrbuf.append("返回日期、");
		}
		int count = 1;
		int passportflag = 0;
		for (Record record : applyinfo) {
			if (Util.isEmpty(record.get("firstname")) && Util.isEmpty(record.get("lastname"))) {
				resultstrbuf.append("申请人" + count + "的姓名、");
			}
			if (Util.isEmpty(record.get("firstnameen")) && Util.isEmpty(record.get("lastnameen"))) {
				resultstrbuf.append("申请人" + count + "的姓名英文、");
			}
			if (Util.isEmpty(record.get("sex"))) {
				resultstrbuf.append("申请人" + count + "的性别、");
			}
			if (Util.isEmpty(record.get("passportno"))) {
				resultstrbuf.append("申请人" + count + "的护照号、");
			}
			count++;
		}
		String resultstr = resultstrbuf.toString();
		//姓名长度限制
		String namelength = "";
		for (Record record : applyinfo) {
			if (Util.isEmpty(record.get("firstname")) && Util.isEmpty(record.get("lastname"))) {
				String name = record.getString("firstname") + record.getString("lastname");
				if (name.length() > 8) {
					namelength = "申请人姓名不能超过八位！";
				}
			}
		}
		if (!Util.isEmpty(resultstr)) {
			resultstr = resultstr.substring(0, resultstr.length() - 1);
			resultstr += "不能为空";
			if (!Util.isEmpty(namelength)) {
				resultstr += namelength;
			}
			return JuYouResult.ok(resultstr);
		} else if (!Util.isEmpty(namelength)) {
			return JuYouResult.ok(namelength);
		} else {
			return JuYouResult.ok();
		}
	}

	/**
	 * 验证送签社的指定番号是否存在
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param sendsignid
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object validateDesignNum(Integer sendsignid, HttpSession session) {
		TCompanyEntity company = dbDao.fetch(TCompanyEntity.class, sendsignid.longValue());
		String designNum = "";
		if (company.getIsCustomer().equals(IsYesOrNoEnum.YES.intKey())) {
			designNum = company.getCdesignNum();
		} else {
			TComBusinessscopeEntity comBusiness = dbDao.fetch(TComBusinessscopeEntity.class,
					Cnd.where("comId", "=", company.getId()).and("countryId", "=", BusinessScopesEnum.JAPAN.intKey()));
			if (!Util.isEmpty(comBusiness)) {
				designNum = comBusiness.getDesignatedNum();
			}
		}
		if (!Util.isEmpty(designNum)) {
			return JuYouResult.ok();
		} else {
			return new JuYouResult(500, company.getName() + "的指定番号不能为空", null);
		}
	}

	/**
	 * 保存出行信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param travelinfo
	 * @param multiways
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveJpVisaTravelInfo(SaveTravelForm form, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		//出行信息
		@SuppressWarnings("unchecked")
		Map<String, Object> travelmap = JsonUtil.fromJson(form.getTravelinfo(), Map.class);
		//Integer id = Integer.valueOf((String) travelmap.get("id"));
		//出行信息是否存在
		TOrderTripJpEntity travel = !Util.isEmpty(travelmap.get("id")) ? dbDao.fetch(TOrderTripJpEntity.class,
				Long.valueOf((String) travelmap.get("id"))) : new TOrderTripJpEntity();
		//travel = dbDao.fetch(TOrderTripJpEntity.class, (Integer.valueOf(travelmap.get("id"))).longValue());
		travel.setTripPurpose(String.valueOf(travelmap.get("trippurpose")));
		String godatestr = String.valueOf(travelmap.get("goDate"));
		List<TOrderTripMultiJpEntity> ordertriplist = new ArrayList<TOrderTripMultiJpEntity>();
		Integer triptype = null;
		if (!Util.isEmpty(travelmap.get("triptype"))) {
			triptype = Integer.valueOf(String.valueOf(travelmap.get("triptype")));
			travel.setTripType(triptype);
			if (triptype.equals(1)) {
				if (!Util.isEmpty(travelmap.get("goDate"))) {
					Date godate = DateUtil.string2Date(godatestr, DateUtil.FORMAT_YYYY_MM_DD);
					travel.setGoDate(godate);
				} else {
					travel.setGoDate(null);
				}
				String returndatestr = String.valueOf(travelmap.get("returnDate"));
				if (!Util.isEmpty(travelmap.get("returnDate"))) {
					Date returndate = DateUtil.string2Date(returndatestr, DateUtil.FORMAT_YYYY_MM_DD);
					travel.setReturnDate(returndate);
				} else {
					travel.setReturnDate(null);
				}
				//去程出发城市
				Integer godeparturecity = null;
				if (!Util.isEmpty(travelmap.get("godeparturecity"))) {
					godeparturecity = Integer.valueOf(String.valueOf(travelmap.get("godeparturecity")));
					travel.setGoDepartureCity(godeparturecity);
				} else {
					travel.setGoDepartureCity(null);
				}
				//去程抵达城市
				Integer goarrivedcity = null;
				if (!Util.isEmpty(travelmap.get("goarrivedcity"))) {
					goarrivedcity = Integer.valueOf(String.valueOf(travelmap.get("goarrivedcity")));
					travel.setGoArrivedCity(goarrivedcity);
				} else {
					travel.setGoArrivedCity(null);
				}
				if (!Util.isEmpty(travelmap.get("goflightnum"))) {
					travel.setGoFlightNum(String.valueOf(travelmap.get("goflightnum")));
				} else {
					travel.setGoFlightNum(null);
				}
				//回程出发城市
				Integer returndeparturecity = null;
				if (!Util.isEmpty(travelmap.get("returndeparturecity"))) {
					returndeparturecity = Integer.valueOf(String.valueOf(travelmap.get("returndeparturecity")));
					travel.setReturnDepartureCity(returndeparturecity);
				} else {
					travel.setReturnDepartureCity(null);
				}
				Integer returnarrivedcity = null;
				if (!Util.isEmpty(travelmap.get("returnarrivedcity"))) {
					returnarrivedcity = Integer.valueOf(String.valueOf(travelmap.get("returnarrivedcity")));
					travel.setReturnArrivedCity(returnarrivedcity);
				} else {
					travel.setReturnArrivedCity(null);
				}
				if (!Util.isEmpty(travelmap.get("returnflightnum"))) {
					travel.setReturnFlightNum(String.valueOf(travelmap.get("returnflightnum")));
				} else {
					travel.setReturnFlightNum(null);
				}
				//更新缓存数据
			} else if (triptype.equals(2) && !Util.isEmpty(form.getMultiways())) {
				//多程信息
				ordertriplist = JsonUtil.fromJsonAsList(TOrderTripMultiJpEntity.class, form.getMultiways());
			}
		} else {
			travel.setTripType(null);
		}
		Integer tripid = null;
		//保存出行信息
		if (Util.isEmpty(travelmap.get("id"))) {
			travel.setOpId(loginUser.getId());
			travel.setCreateTime(new Date());
			travel.setOrderId(form.getOrderid());
			TOrderTripJpEntity insert = dbDao.insert(travel);
			tripid = insert.getId();
		} else {
			travel.setUpdateTime(new Date());
			dbDao.update(travel);
			tripid = Integer.valueOf((String) travelmap.get("id"));
		}
		for (TOrderTripMultiJpEntity tripMultiJp : ordertriplist) {
			tripMultiJp.setTripid(tripid);
		}
		//保存多程信息
		if (!Util.isEmpty(triptype) && triptype.equals(2)) {
			List<TOrderTripMultiJpEntity> before = dbDao.query(TOrderTripMultiJpEntity.class,
					Cnd.where("tripid", "=", tripid), null);
			dbDao.updateRelations(before, ordertriplist);
		}
		return null;
	}

	public Object validateDownLoadInfoIsFull(Integer orderjpid) {

		StringBuffer resultstrbuf = new StringBuffer("");
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
		//订单信息
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
		//出行信息
		TOrderTripJpEntity ordertripjp = dbDao.fetch(TOrderTripJpEntity.class,
				Cnd.where("orderId", "=", orderjp.getId()));
		List<TOrderTripMultiJpEntity> mutiltrip = new ArrayList<TOrderTripMultiJpEntity>();
		if (!Util.isEmpty(ordertripjp)) {
			mutiltrip = dbDao.query(TOrderTripMultiJpEntity.class, Cnd.where("tripid", "=", ordertripjp.getId()), null);
		}
		//申请人信息
		String applysqlstr = sqlManager.get("get_applyinfo_from_filedown_by_orderid_jp");
		Sql applysql = Sqls.create(applysqlstr);
		Cnd cnd = Cnd.NEW();
		cnd.and("taoj.orderId", "=", orderjp.getId());
		List<Record> applyinfo = dbDao.query(applysql, cnd, null);
		//行程安排
		List<TOrderTravelplanJpEntity> ordertravelplan = dbDao.query(TOrderTravelplanJpEntity.class,
				Cnd.where("orderId", "=", orderjp.getId()), null);
		//公司信息
		TCompanyEntity company = new TCompanyEntity();
		company = dbDao.fetch(TCompanyEntity.class, orderinfo.getComId().longValue());
		//判断签证类型
		if (Util.isEmpty(orderjp.getVisaType())) {
			resultstrbuf.append("签证类型、");
		}
		if (Util.isEmpty(orderinfo.getGoTripDate())) {
			resultstrbuf.append("出发日期、");
		}
		if (Util.isEmpty(orderinfo.getBackTripDate())) {
			resultstrbuf.append("返回日期、");
		}
		int count = 1;
		int passportflag = 0;
		for (Record record : applyinfo) {
			if (Util.isEmpty(record.get("firstname")) && Util.isEmpty(record.get("lastname"))) {
				resultstrbuf.append("申请人" + count + "的姓名、");
			}
			if (Util.isEmpty(record.get("firstnameen")) && Util.isEmpty(record.get("lastnameen"))) {
				resultstrbuf.append("申请人" + count + "的姓名英文、");
			}
			if (Util.isEmpty(record.get("sex"))) {
				resultstrbuf.append("申请人" + count + "的性别、");
			}
			if (Util.isEmpty(record.get("passportno"))) {
				resultstrbuf.append("申请人" + count + "的护照号、");
			}
			if (Util.isEmpty(record.get("hotelname"))) {
				resultstrbuf.append("申请人" + count + "的酒店名称、");
			}
			if (Util.isEmpty(record.get("vouchname"))) {
				resultstrbuf.append("申请人" + count + "的在日担保人姓名、");
			}
			if (Util.isEmpty(record.get("invitename"))) {
				resultstrbuf.append("申请人" + count + "的在日邀请人姓名、");
			}
			if (Util.isEmpty(record.get("traveladvice"))) {
				resultstrbuf.append("申请人" + count + "的旅行社意见、");
			}
			count++;
		}
		String resultstr = resultstrbuf.toString();
		if (!Util.isEmpty(resultstr)) {
			resultstr = resultstr.substring(0, resultstr.length() - 1);
			resultstr += "不能为空";
			return JuYouResult.ok(resultstr);
		} else {
			return JuYouResult.ok();
		}

	}

	public Object initOrderstatus(Long orderid) {
		Map<String, Object> result = Maps.newHashMap();
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid);
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
		String orderstatus = "";
		for (JPOrderStatusEnum jpordersimpleenum : JPOrderStatusEnum.values()) {
			if (jpordersimpleenum.intKey() == order.getStatus()) {
				orderstatus = jpordersimpleenum.value();
				break;
			}

		}
		result.put("orderstatus", orderstatus);
		result.put("orderjp", orderjp);
		return result;
	}
}
