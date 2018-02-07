/**
 * SimpleVisaService.java
 * com.juyo.visa.admin.simple.service
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.simple.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.order.form.VisaEditDataForm;
import com.juyo.visa.admin.simple.form.AddOrderForm;
import com.juyo.visa.admin.simple.form.GenerrateTravelForm;
import com.juyo.visa.admin.simple.form.ListDataForm;
import com.juyo.visa.admin.user.form.ApplicantUser;
import com.juyo.visa.admin.user.service.UserViewService;
import com.juyo.visa.common.base.QrCodeService;
import com.juyo.visa.common.enums.ApplicantInfoTypeEnum;
import com.juyo.visa.common.enums.ApplicantJpWealthEnum;
import com.juyo.visa.common.enums.BoyOrGirlEnum;
import com.juyo.visa.common.enums.CollarAreaEnum;
import com.juyo.visa.common.enums.CustomerTypeEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.JobStatusEnum;
import com.juyo.visa.common.enums.JobStatusFreeEnum;
import com.juyo.visa.common.enums.JobStatusPreschoolEnum;
import com.juyo.visa.common.enums.JobStatusRetirementEnum;
import com.juyo.visa.common.enums.JobStatusStudentEnum;
import com.juyo.visa.common.enums.JobStatusWorkingEnum;
import com.juyo.visa.common.enums.MainApplicantRelationEnum;
import com.juyo.visa.common.enums.MainApplicantRemarkEnum;
import com.juyo.visa.common.enums.MainOrViceEnum;
import com.juyo.visa.common.enums.MainSalePayTypeEnum;
import com.juyo.visa.common.enums.MainSaleTripTypeEnum;
import com.juyo.visa.common.enums.MainSaleUrgentEnum;
import com.juyo.visa.common.enums.MainSaleUrgentTimeEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.common.enums.MarryStatusEnum;
import com.juyo.visa.common.enums.TrialApplicantStatusEnum;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantFrontPaperworkJpEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TApplicantPassportEntity;
import com.juyo.visa.entities.TApplicantUnqualifiedEntity;
import com.juyo.visa.entities.TApplicantVisaPaperworkJpEntity;
import com.juyo.visa.entities.TApplicantWealthJpEntity;
import com.juyo.visa.entities.TApplicantWorkJpEntity;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TCustomerEntity;
import com.juyo.visa.entities.TCustomerVisainfoEntity;
import com.juyo.visa.entities.TFlightEntity;
import com.juyo.visa.entities.THotelEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderTravelplanJpEntity;
import com.juyo.visa.entities.TOrderTripJpEntity;
import com.juyo.visa.entities.TScenicEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TApplicantForm;
import com.juyo.visa.forms.TApplicantPassportForm;
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
	private UserViewService userViewService;

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
		result.put("mainSaleVisaTypeEnum", EnumUtil.enum2(MainSaleVisaTypeEnum.class));
		return result;
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
	public Object generateTravelPlan(HttpServletRequest request, GenerrateTravelForm form) {
		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Map<String, Object> result = Maps.newHashMap();
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
		int daysBetween = DateUtil.daysBetween(form.getGoDate(), form.getReturnDate());
		//获取城市
		TCityEntity city = dbDao.fetch(TCityEntity.class, form.getGoArrivedCity().longValue());
		//获取城市所有的酒店
		List<THotelEntity> hotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", form.getGoArrivedCity()),
				null);
		//获取城市所有的景区
		List<TScenicEntity> scenics = dbDao.query(TScenicEntity.class,
				Cnd.where("cityId", "=", form.getGoArrivedCity()), null);
		if (scenics.size() < daysBetween) {
			result.put("message", "没有更多的景区");
			return result;
		}
		Integer orderjpid = form.getOrderid();
		Integer orderid = null;
		if (Util.isEmpty(orderjpid)) {
			Map<String, Integer> generrateorder = generrateorder(loginUser, loginCompany);
			orderid = generrateorder.get("orderid");
			orderjpid = generrateorder.get("orderjpid");
		} else {
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
			orderid = orderjp.getOrderId();
		}
		//需要生成的travelplan
		List<TOrderTravelplanJpEntity> travelplans = Lists.newArrayList();
		Random random = new Random();
		//在一个城市只住一家酒店
		int hotelindex = random.nextInt(hotels.size());
		//为什么要<=，因为最后一天也要玩
		for (int i = 0; i <= daysBetween; i++) {
			TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
			travelplan.setCityId(form.getGoArrivedCity());
			travelplan.setDay(String.valueOf(i + 1));
			travelplan.setOrderId(orderjpid);
			travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
			travelplan.setCityName(city.getCity());
			travelplan.setCreateTime(new Date());
			//酒店
			if (i != daysBetween) {
				THotelEntity hotel = hotels.get(hotelindex);
				travelplan.setHotel(hotel.getId());
			}
			if (i > 0) {
				//景区
				int scenicindex = random.nextInt(scenics.size());
				TScenicEntity scenic = scenics.get(scenicindex);
				scenics.remove(scenic);
				travelplan.setScenic(scenic.getName());
			}
			travelplans.add(travelplan);
		}

		List<TOrderTravelplanJpEntity> before = dbDao.query(TOrderTravelplanJpEntity.class,
				Cnd.where("orderid", "=", orderjpid), null);
		//更新行程安排
		dbDao.updateRelations(before, travelplans);
		result.put("status", "success");
		result.put("orderid", orderjpid);
		result.put("data", getTravelPlanByOrderId(orderjpid));
		result.put("orderjpid", orderjpid);
		return result;
	}

	private Map<String, Integer> generrateorder(TUserEntity user, TCompanyEntity company) {
		Map<String, Integer> result = Maps.newHashMap();
		//如果订单不存在，则先创建订单
		TOrderEntity orderinfo = new TOrderEntity();
		orderinfo.setComId(company.getId());
		orderinfo.setUserId(user.getId());
		orderinfo.setOrderNum(generrateOrdernum());
		orderinfo.setStatus(JPOrderStatusEnum.PLACE_ORDER.intKey());
		orderinfo.setCreateTime(new Date());
		orderinfo.setUpdateTime(new Date());
		TOrderEntity orderinsert = dbDao.insert(orderinfo);
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
			if (count > 1) {
				if (!Util.isEmpty(record.get("hotelname"))) {
					String hotelname = (String) record.get("hotelname");
					if (hotelname.equals(prehotelname)) {
						record.put("hotelname", "同上");
					}
				}
			}
			count++;
			if (!Util.isEmpty(record.get("hotelname"))) {
				prehotelname = (String) record.get("hotelname");
			}

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
		String localAddr = request.getLocalAddr();
		int localPort = request.getLocalPort();
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
		} else {
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
			orderid = orderjp.getOrderId();
		}
		TOrderJpEntity orderjpinfo = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderid.longValue());
		//保存客户信息
		if (!Util.isEmpty(form.getCustomerType())) {
			if (form.getCustomerType().equals(CustomerTypeEnum.ZHIKE.intKey())) {
				TCustomerEntity customer = new TCustomerEntity();
				customer.setName(form.getCompName2());
				customer.setShortname(form.getComShortName2());
				customer.setPayType(form.getPayType());
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
				dbDao.query(TCustomerVisainfoEntity.class, Cnd.where("customerid", "=", insertcustom), null);
				orderinfo.setCustomerId(insertcustom.getId());
			} else {
				orderinfo.setCustomerId(form.getCustomerid());
			}
		}
		orderinfo.setCityId(form.getCityid());
		orderinfo.setUrgentType(form.getUrgentType());
		orderinfo.setUrgentDay(form.getUrgentDay());
		orderinfo.setSendVisaDate(form.getSendvisadate());
		orderinfo.setOutVisaDate(form.getOutvisadate());
		orderinfo.setGoTripDate(form.getGoDate());
		orderinfo.setStayDay(form.getStayday());
		orderinfo.setBackTripDate(form.getReturnDate());
		dbDao.update(orderinfo);
		//更新日本订单表
		orderjpinfo.setVisastatus(form.getVisatype());
		orderjpinfo.setAmount(form.getAmount());
		dbDao.update(orderjpinfo);
		//出行信息
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
		if (!Util.isEmpty(tripinfo)) {
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
		result.put("flightlist", flightlist);
		result.put("collarAreaEnum", EnumUtil.enum2(CollarAreaEnum.class));
		result.put("customerTypeEnum", EnumUtil.enum2(CustomerTypeEnum.class));
		result.put("mainSaleUrgentEnum", EnumUtil.enum2(MainSaleUrgentEnum.class));
		result.put("mainSaleUrgentTimeEnum", EnumUtil.enum2(MainSaleUrgentTimeEnum.class));
		result.put("mainSaleTripTypeEnum", EnumUtil.enum2(MainSaleTripTypeEnum.class));
		result.put("mainSalePayTypeEnum", EnumUtil.enum2(MainSalePayTypeEnum.class));
		result.put("mainSaleVisaTypeEnum", EnumUtil.enum2(MainSaleVisaTypeEnum.class));
		result.put("citylist", citylist);
		result.put("orderjpinfo", orderjpinfo);
		result.put("orderinfo", orderinfo);
		result.put("tripinfo", tripinfo);
		result.put("customerinfo", customerinfo);
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
		//修改
		if (!Util.isEmpty(form.getId())) {
			applicant = dbDao.fetch(TApplicantEntity.class, form.getId().longValue());
			applicantjp = dbDao.fetch(TApplicantOrderJpEntity.class, Cnd.where("applicantId", "=", applicant.getId()));
			result.put("applicantjpid", applicantjp.getId());
			result.put("applicantid", applicant.getId());
		}
		applicant.setOpId(loginUser.getId());
		applicant.setIsSameInfo(IsYesOrNoEnum.YES.intKey());
		applicant.setIsPrompted(IsYesOrNoEnum.NO.intKey());
		applicant.setAddress(form.getAddress());
		applicant.setBirthday(form.getBirthday());
		applicant.setCardId(form.getCardId());
		applicant.setCity(form.getCity());
		applicant.setDetailedAddress(form.getDetailedAddress());
		applicant.setEmail(form.getEmail());
		if (!Util.isEmpty(form.getFirstNameEn())) {
			applicant.setFirstNameEn(form.getFirstNameEn().substring(1));
		}
		if (!Util.isEmpty(form.getOtherLastNameEn())) {
			applicant.setOtherLastNameEn(form.getOtherLastNameEn().substring(1));
		}
		if (!Util.isEmpty(form.getOtherFirstNameEn())) {
			applicant.setOtherFirstNameEn(form.getOtherFirstNameEn().substring(1));
		}
		applicant.setNationality(form.getNationality());
		applicant.setHasOtherName(form.getHasOtherName());
		applicant.setHasOtherNationality(form.getHasOtherNationality());
		applicant.setFirstName(form.getFirstName());
		applicant.setLastName(form.getLastName());
		applicant.setOtherFirstName(form.getOtherFirstName());
		applicant.setOtherLastName(form.getOtherLastName());
		applicant.setAddressIsSameWithCard(form.getAddressIsSameWithCard());
		applicant.setCardProvince(form.getCardProvince());
		applicant.setCardCity(form.getCardCity());
		applicant.setIssueOrganization(form.getIssueOrganization());
		if (!Util.isEmpty(form.getLastNameEn())) {
			applicant.setLastNameEn(form.getLastNameEn().substring(1));
		}
		applicant.setNation(form.getNation());
		applicant.setProvince(form.getProvince());
		applicant.setSex(form.getSex());
		applicant.setTelephone(form.getTelephone());
		applicant.setValidEndDate(form.getValidEndDate());
		applicant.setValidStartDate(form.getValidStartDate());
		applicant.setCardFront(form.getCardFront());
		applicant.setCardBack(form.getCardBack());
		applicant.setStatus(TrialApplicantStatusEnum.FIRSTTRIAL.intKey());
		applicant.setCreateTime(new Date());
		applicant.setEmergencyLinkman(form.getEmergencyLinkman());
		applicant.setEmergencyTelephone(form.getEmergencyTelephone());
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
			}
			applicantjp.setOrderId(orderjpid);
			applicantjp.setApplicantId(applicantid);
			applicantjp.setBaseIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setPassIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setVisaIsCompleted(IsYesOrNoEnum.NO.intKey());
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
		result.put("passport", passport);
		//所访问的ip地址
		String localAddr = request.getLocalAddr();
		result.put("localAddr", localAddr);
		//所访问的端口
		int localPort = request.getLocalPort();
		result.put("localPort", localPort);
		//websocket地址
		result.put("websocketaddr", SIMPLE_WEBSOCKET_ADDR);
		//生成二维码的URL
		String passporturl = "http://" + localAddr + ":" + localPort + "/simplemobile/passport.html?applicantid="
				+ applicantid;
		//生成二维码
		String qrCode = qrCodeService.encodeQrCode(request, passporturl);
		result.put("qrCode", qrCode);
		return result;
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
		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TApplicantPassportEntity passport = dbDao.fetch(TApplicantPassportEntity.class, form.getId().longValue());
		passport.setOpId(loginUser.getId());

		passport.setPassportUrl(form.getPassportUrl());
		passport.setOCRline1(form.getOCRline1());
		passport.setOCRline2(form.getOCRline2());
		passport.setBirthAddress(form.getBirthAddress());
		passport.setBirthAddressEn(form.getBirthAddressEn());
		passport.setBirthday(form.getBirthday());
		passport.setFirstName(form.getFirstName());
		//passport.setFirstNameEn(passportForm.getFirstNameEn().substring(1));
		passport.setIssuedDate(form.getIssuedDate());
		passport.setIssuedOrganization(form.getIssuedOrganization());
		passport.setIssuedOrganizationEn(form.getIssuedOrganizationEn());
		passport.setIssuedPlace(form.getIssuedPlace());
		passport.setIssuedPlaceEn(form.getIssuedPlaceEn());
		passport.setLastName(form.getLastName());
		//passport.setLastNameEn(passportForm.getLastNameEn().substring(1));
		passport.setPassport(form.getPassport());
		passport.setSex(form.getSex());
		passport.setSexEn(form.getSexEn());
		passport.setType(form.getType());
		passport.setValidEndDate(form.getValidEndDate());
		passport.setValidStartDate(form.getValidStartDate());
		passport.setValidType(form.getValidType());
		passport.setUpdateTime(new Date());
		int update = dbDao.update(passport);
		return null;
	}

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
		String localAddr = request.getLocalAddr();
		int localPort = request.getLocalPort();
		result.put("localAddr", localAddr);
		result.put("localPort", localPort);
		result.put("websocketaddr", SIMPLE_WEBSOCKET_ADDR);
		//生成二维码
		String qrurl = "http://" + request.getLocalAddr() + ":" + request.getLocalPort()
				+ "/simplemobile/info.html?applicantid=" + applicantid;
		String qrCode = qrCodeService.encodeQrCode(request, qrurl);
		result.put("qrCode", qrCode);
		result.put("applicantid", applicant.getId());
		result.put("orderid", orderid);
		return result;
	}

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
		result.put("marryStatus", apply.getMarryStatus());
		result.put("marryUrl", apply.getMarryUrl());
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
		result.put("workJp", applicantWorkJpEntity);
		result.put("mainApply", records);
		result.put("visaInfo", visaInfo);
		//获取所访问的ip地址
		String localAddr = request.getLocalAddr();
		//所访问的端口
		int localPort = request.getLocalPort();
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
		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		//日本申请人
		if (!Util.isEmpty(form.getApplicantId())) {
			//日本申请人
			TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
					Cnd.where("applicantId", "=", form.getApplicantId()));
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId().longValue());
			orderjp.setVisaType(form.getVisatype());
			orderjp.setVisaCounty(form.getVisacounty());
			orderjp.setIsVisit(form.getIsVisit());
			orderjp.setThreeCounty(form.getThreecounty());
			dbDao.update(orderjp);
			//申请人
			TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class,
					new Long(applicantOrderJpEntity.getApplicantId()).intValue());
			applicantEntity.setMarryStatus(form.getMarryStatus());
			applicantEntity.setMarryUrl(form.getMarryUrl());
			applicantEntity.setMarryurltype(form.getMarryStatus());
			if (!Util.isEmpty(form.getAddApply())) {
				if (Util.eq(form.getAddApply(), 2)) {
					applicantEntity.setStatus(TrialApplicantStatusEnum.FillCompleted.intKey());
				}
			}
			//主申请人
			if (!Util.isEmpty(applicantEntity.getMainId())) {
				TApplicantEntity mainApplicant = dbDao.fetch(TApplicantEntity.class,
						new Long(applicantEntity.getMainId()).intValue());
			}
			//更新申请人信息
			if (Util.eq(form.getApplicant(), MainOrViceEnum.YES.intKey())) {//是主申请人
				applicantEntity.setMainId(applicantEntity.getId());
				dbDao.update(applicantEntity);
			} else {
				if (!Util.isEmpty(form.getMainApplicant())) {
					applicantEntity.setMainId(form.getMainApplicant());
					dbDao.update(applicantEntity);
				}
			}
			if (Util.eq(applicantEntity.getId(), applicantEntity.getMainId())) {
				applicantOrderJpEntity.setIsMainApplicant(MainOrViceEnum.YES.intKey());
			} else {
				applicantOrderJpEntity.setIsMainApplicant(MainOrViceEnum.NO.intKey());
			}

			//更新日本申请人信息
			if (!Util.isEmpty(form.getSameMainTrip())) {
				applicantOrderJpEntity.setSameMainTrip(form.getSameMainTrip());
			}
			if (!Util.isEmpty(form.getSameMainWealth())) {
				applicantOrderJpEntity.setSameMainWealth(form.getSameMainWealth());
				//如果申请人跟主申请人的财产信息一样，把主申请人的财产信息保存到申请人财产信息中
				if (Util.eq(form.getSameMainWealth(), IsYesOrNoEnum.YES.intKey())) {
					if (!Util.isEmpty(applicantEntity.getMainId())) {
						TApplicantEntity mainApplicant = dbDao.fetch(TApplicantEntity.class,
								new Long(applicantEntity.getMainId()).intValue());
						TApplicantOrderJpEntity mainAppyJp = dbDao.fetch(TApplicantOrderJpEntity.class,
								Cnd.where("applicantId", "=", mainApplicant.getId()));
						//获取主申请人的财产信息
						//银行存款
						TApplicantWealthJpEntity mainApplyWealthJp = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", mainAppyJp.getId()).and("type", "=",
										ApplicantJpWealthEnum.BANK.value()));
						TApplicantWealthJpEntity applyWealthJp = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.BANK.value()));
						if (!Util.isEmpty(mainApplyWealthJp)) {
							if (!Util.isEmpty(applyWealthJp)) {//如果申请人有银行存款信息，则更新
								if (!Util.isEmpty(mainApplyWealthJp.getDetails())) {
									applyWealthJp.setDetails(mainApplyWealthJp.getDetails());
									applyWealthJp.setApplicantId(applicantOrderJpEntity.getId());
									applyWealthJp.setOpId(loginUser.getId());
									applyWealthJp.setUpdateTime(new Date());
									dbDao.update(applyWealthJp);
								}
							} else {//没有则添加
								TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
								applyWealth.setType("银行存款");
								applyWealth.setDetails(mainApplyWealthJp.getDetails());
								applyWealth.setApplicantId(applicantOrderJpEntity.getId());
								applyWealth.setOpId(loginUser.getId());
								applyWealth.setCreateTime(new Date());
								dbDao.insert(applyWealth);
							}
						}
						//车产
						TApplicantWealthJpEntity applicantWealthJpCar = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.CAR.value()));
						TApplicantWealthJpEntity mainApplyWealthJpCar = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", mainAppyJp.getId()).and("type", "=",
										ApplicantJpWealthEnum.CAR.value()));
						if (!Util.isEmpty(mainApplyWealthJpCar)) {
							if (!Util.isEmpty(applicantWealthJpCar)) {//如果申请人有银行存款信息，则更新
								if (!Util.isEmpty(mainApplyWealthJpCar.getDetails())) {
									applicantWealthJpCar.setDetails(mainApplyWealthJpCar.getDetails());
									applicantWealthJpCar.setApplicantId(applicantOrderJpEntity.getId());
									applicantWealthJpCar.setOpId(loginUser.getId());
									applicantWealthJpCar.setUpdateTime(new Date());
									dbDao.update(applicantWealthJpCar);
								}
							} else {
								TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
								applyWealth.setType("车产");
								applyWealth.setDetails(mainApplyWealthJpCar.getDetails());
								applyWealth.setApplicantId(applicantOrderJpEntity.getId());
								applyWealth.setOpId(loginUser.getId());
								applyWealth.setCreateTime(new Date());
								dbDao.insert(applyWealth);
							}
						}

						//房产
						TApplicantWealthJpEntity applicantWealthJpHome = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.HOME.value()));
						TApplicantWealthJpEntity mainApplyWealthJpHome = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", mainAppyJp.getId()).and("type", "=",
										ApplicantJpWealthEnum.HOME.value()));
						if (!Util.isEmpty(mainApplyWealthJpHome)) {
							if (!Util.isEmpty(applicantWealthJpHome)) {//如果申请人有银行存款信息，则更新
								if (!Util.isEmpty(mainApplyWealthJpHome.getDetails())) {
									applicantWealthJpHome.setDetails(mainApplyWealthJpHome.getDetails());
									applicantWealthJpHome.setApplicantId(applicantOrderJpEntity.getId());
									applicantWealthJpHome.setOpId(loginUser.getId());
									applicantWealthJpHome.setUpdateTime(new Date());
									dbDao.update(applicantWealthJpHome);
								}
							} else {
								TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
								applyWealth.setType("房产");
								applyWealth.setDetails(mainApplyWealthJpHome.getDetails());
								applyWealth.setApplicantId(applicantOrderJpEntity.getId());
								applyWealth.setOpId(loginUser.getId());
								applyWealth.setCreateTime(new Date());
								dbDao.insert(applyWealth);
							}
						}

						//理财
						TApplicantWealthJpEntity applicantWealthJpLi = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.LICAI.value()));
						TApplicantWealthJpEntity mainApplyWealthJpLi = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", mainAppyJp.getId()).and("type", "=",
										ApplicantJpWealthEnum.LICAI.value()));
						if (!Util.isEmpty(mainApplyWealthJpLi)) {
							if (!Util.isEmpty(applicantWealthJpLi)) {//如果申请人有银行存款信息，则更新
								if (!Util.isEmpty(mainApplyWealthJpLi.getDetails())) {
									applicantWealthJpLi.setDetails(mainApplyWealthJpLi.getDetails());
									applicantWealthJpLi.setApplicantId(applicantOrderJpEntity.getId());
									applicantWealthJpLi.setOpId(loginUser.getId());
									applicantWealthJpLi.setUpdateTime(new Date());
									dbDao.update(applicantWealthJpLi);
								}
							} else {
								TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
								applyWealth.setType("理财");
								applyWealth.setDetails(mainApplyWealthJpLi.getDetails());
								applyWealth.setApplicantId(applicantOrderJpEntity.getId());
								applyWealth.setOpId(loginUser.getId());
								applyWealth.setCreateTime(new Date());
								dbDao.insert(applyWealth);
							}
						}

					}
				} else {
					//添加财产信息
					TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
					wealthJp.setApplicantId(applicantOrderJpEntity.getId());
					//银行存款
					if (!Util.isEmpty(form.getDeposit())) {
						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.BANK.value()));
						if (!Util.isEmpty(applicantWealthJpEntity)) {
							applicantWealthJpEntity.setDetails(form.getDeposit());
							dbDao.update(applicantWealthJpEntity);
						} else {
							wealthJp.setDetails(form.getDeposit());
							wealthJp.setType(ApplicantJpWealthEnum.BANK.value());
							wealthJp.setCreateTime(new Date());
							wealthJp.setOpId(loginUser.getId());
							dbDao.insert(wealthJp);
						}
					} else {
						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.BANK.value()));
						if (!Util.isEmpty(applicantWealthJpEntity)) {
							dbDao.delete(applicantWealthJpEntity);
						}
					}
					//车产
					if (!Util.isEmpty(form.getVehicle())) {
						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.CAR.value()));
						if (!Util.isEmpty(applicantWealthJpEntity)) {
							applicantWealthJpEntity.setDetails(form.getVehicle());
							dbDao.update(applicantWealthJpEntity);
						} else {
							wealthJp.setDetails(form.getVehicle());
							wealthJp.setType(ApplicantJpWealthEnum.CAR.value());
							wealthJp.setCreateTime(new Date());
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
					//房产
					if (!Util.isEmpty(form.getHouseProperty())) {
						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.HOME.value()));
						if (!Util.isEmpty(applicantWealthJpEntity)) {
							applicantWealthJpEntity.setDetails(form.getHouseProperty());
							dbDao.update(applicantWealthJpEntity);
						} else {
							wealthJp.setDetails(form.getHouseProperty());
							wealthJp.setType(ApplicantJpWealthEnum.HOME.value());
							wealthJp.setCreateTime(new Date());
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
					//理财
					if (!Util.isEmpty(form.getFinancial())) {
						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.LICAI.value()));
						if (!Util.isEmpty(applicantWealthJpEntity)) {
							applicantWealthJpEntity.setDetails(form.getFinancial());
							dbDao.update(applicantWealthJpEntity);
						} else {
							wealthJp.setDetails(form.getFinancial());
							wealthJp.setType(ApplicantJpWealthEnum.LICAI.value());
							wealthJp.setCreateTime(new Date());
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
				}
			}
			//更新工作信息
			TApplicantWorkJpEntity applicantWorkJpEntity = dbDao.fetch(TApplicantWorkJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()));
			Integer careerStatus = form.getCareerStatus();
			if (!Util.isEmpty(careerStatus)) {
				toUpdateWorkJp(careerStatus, applicantWorkJpEntity, applicantOrderJpEntity, session);
			} else {
				Integer applicantJpId = applicantOrderJpEntity.getId();
				List<TApplicantFrontPaperworkJpEntity> frontListDB = dbDao.query(
						TApplicantFrontPaperworkJpEntity.class, Cnd.where("applicantId", "=", applicantJpId), null);
				List<TApplicantVisaPaperworkJpEntity> visaListDB = dbDao.query(TApplicantVisaPaperworkJpEntity.class,
						Cnd.where("applicantId", "=", applicantJpId), null);
				if (!Util.isEmpty(frontListDB)) {//如果库中有数据，则删掉
					dbDao.delete(frontListDB);
				}
				if (!Util.isEmpty(visaListDB)) {
					dbDao.delete(visaListDB);
				}
				applicantWorkJpEntity.setPrepareMaterials(null);
			}
			applicantWorkJpEntity.setCareerStatus(form.getCareerStatus());
			applicantWorkJpEntity.setName(form.getName());
			applicantWorkJpEntity.setAddress(form.getAddress());
			applicantWorkJpEntity.setTelephone(form.getTelephone());
			applicantWorkJpEntity.setUpdateTime(new Date());
			dbDao.update(applicantWorkJpEntity);
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
			int update = dbDao.update(applicantOrderJpEntity);

		}
		return null;
	}

	public void toUpdateWorkJp(int careerStatus, TApplicantWorkJpEntity applicantWorkJpEntity,
			TApplicantOrderJpEntity applicantOrderJpEntity, HttpSession session) {

		Integer applicantJpId = applicantOrderJpEntity.getId();
		List<TApplicantFrontPaperworkJpEntity> frontListDB = dbDao.query(TApplicantFrontPaperworkJpEntity.class,
				Cnd.where("applicantId", "=", applicantJpId), null);
		List<TApplicantVisaPaperworkJpEntity> visaListDB = dbDao.query(TApplicantVisaPaperworkJpEntity.class,
				Cnd.where("applicantId", "=", applicantJpId), null);
		if (!Util.isEmpty(frontListDB)) {//如果库中有数据，则删掉
			dbDao.delete(frontListDB);
		}
		if (!Util.isEmpty(visaListDB)) {
			dbDao.delete(visaListDB);
		}
		if (Util.eq(careerStatus, JobStatusEnum.WORKING_STATUS.intKey())) {//在职
			dbDao.insert(toInsertFrontJp(JobStatusEnum.WORKING_STATUS.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.WORKING_STATUS.intKey(), applicantJpId, session));

			StringBuilder sbWork = new StringBuilder();
			for (JobStatusWorkingEnum jobWorking : JobStatusWorkingEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
		if (Util.eq(careerStatus, JobStatusEnum.RETIREMENT_STATUS.intKey())) {//退休
			dbDao.insert(toInsertFrontJp(JobStatusEnum.RETIREMENT_STATUS.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.RETIREMENT_STATUS.intKey(), applicantJpId, session));
			StringBuilder sbWork = new StringBuilder();
			for (JobStatusRetirementEnum jobWorking : JobStatusRetirementEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
		if (Util.eq(careerStatus, JobStatusEnum.FREELANCE_STATUS.intKey())) {//自由职业
			dbDao.insert(toInsertFrontJp(JobStatusEnum.FREELANCE_STATUS.intKey(), applicantJpId, session));
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
			dbDao.insert(toInsertFrontJp(JobStatusEnum.student_status.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.student_status.intKey(), applicantJpId, session));
			StringBuilder sbWork = new StringBuilder();
			for (JobStatusStudentEnum jobWorking : JobStatusStudentEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
		if (Util.eq(careerStatus, JobStatusEnum.Preschoolage_status.intKey())) {//学龄前
			dbDao.insert(toInsertFrontJp(JobStatusEnum.Preschoolage_status.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.Preschoolage_status.intKey(), applicantJpId, session));
			StringBuilder sbWork = new StringBuilder();
			for (JobStatusPreschoolEnum jobWorking : JobStatusPreschoolEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
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

	public Object cancelOrder(Long orderid) {
		if (!Util.isEmpty(orderid)) {
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid);

		}
		return null;
	}
}
