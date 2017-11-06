/**
 * VisaJapanService.java
 * com.juyo.visa.admin.visajp.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp.service;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.visajp.form.GeneratePlanForm;
import com.juyo.visa.admin.visajp.form.VisaEditDataForm;
import com.juyo.visa.admin.visajp.form.VisaListDataForm;
import com.juyo.visa.common.enums.AlredyVisaTypeEnum;
import com.juyo.visa.common.enums.CollarAreaEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.MainSalePayTypeEnum;
import com.juyo.visa.common.enums.MainSaleTripTypeEnum;
import com.juyo.visa.common.enums.MainSaleUrgentEnum;
import com.juyo.visa.common.enums.MainSaleUrgentTimeEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.common.enums.VisaDataTypeEnum;
import com.juyo.visa.entities.TApplicantVisaJpEntity;
import com.juyo.visa.entities.TApplicantVisaPaperworkJpEntity;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TFlightEntity;
import com.juyo.visa.entities.THotelEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderTravelplanJpEntity;
import com.juyo.visa.entities.TOrderTripJpEntity;
import com.juyo.visa.entities.TScenicEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年10月30日 	 
 */
@IocBean
public class VisaJapanService extends BaseService<TOrderEntity> {

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
				for (VisaDataTypeEnum dataTypeEnum : VisaDataTypeEnum.values()) {
					if (dataType == dataTypeEnum.intKey()) {
						apply.put("dataType", dataTypeEnum.value());
					}
				}
			}
			record.put("everybodyInfo", query);
		}
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
		result.put("orderinfo", orderinfo);
		//出行信息
		TOrderTripJpEntity travelinfo = dbDao.fetch(TOrderTripJpEntity.class, Cnd.where("orderId", "=", orderid));
		if (Util.isEmpty(travelinfo)) {
			travelinfo = new TOrderTripJpEntity();
		}
		Map<String, String> tralinfoMap = obj2Map(travelinfo);
		if (!Util.isEmpty(travelinfo.getGoDate())) {
			tralinfoMap.put("goDate", format.format(travelinfo.getGoDate()));
		}
		if (!Util.isEmpty(travelinfo.getReturnDate())) {
			tralinfoMap.put("returnDate", format.format(travelinfo.getReturnDate()));
		}
		result.put("travelinfo", tralinfoMap);
		//申请人信息
		String applysqlstr = sqlManager.get("get_jporder_detail_applyinfo_byorderid");
		Sql applysql = Sqls.create(applysqlstr);
		applysql.setParam("orderid", orderid);
		List<Record> applyinfo = dbDao.query(applysql, null, null);
		for (Record record : applyinfo) {
			Integer type = (Integer) record.get("type");
			for (VisaDataTypeEnum visadatatype : VisaDataTypeEnum.values()) {
				if (type.equals(visadatatype.intKey())) {
					record.put("type", visadatatype.value());
				}
			}
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
		if (Util.isEmpty(travelinfo)) {
			travelinfo = new TOrderTripJpEntity();
		}
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
			goflightnum = dbDao.fetch(TFlightEntity.class, travelinfo.getGoFlightNum().longValue());
		}
		result.put("goflightnum", goflightnum);
		//回程航班
		TFlightEntity returnflightnum = new TFlightEntity();
		if (!Util.isEmpty(travelinfo.getGoFlightNum())) {
			returnflightnum = dbDao.fetch(TFlightEntity.class, travelinfo.getReturnFlightNum().longValue());
		}
		result.put("returnflightnum", returnflightnum);
		return result;
	}

	/**
	 * 保存签证编辑页数据
	 * <p>
	 * TODO 保存签证编辑页数据
	 *
	 * @param editDataForm
	 * @param travelinfo 
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveJpVisaDetailInfo(VisaEditDataForm editDataForm, String travelinfojson, HttpSession session) {
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
		order.setUpdateTime(new Date());
		dbDao.update(order);
		//日本订单信息
		TOrderJpEntity jporder = dbDao.fetch(TOrderJpEntity.class, editDataForm.getOrderid().longValue());
		jporder.setVisaType(editDataForm.getVisatype());
		jporder.setVisaCounty(editDataForm.getVisacounty());
		jporder.setIsVisit(editDataForm.getIsvisit());
		jporder.setThreeCounty(editDataForm.getThreecounty());
		dbDao.update(jporder);
		//出行信息
		@SuppressWarnings("unchecked")
		Map<String, Object> travelmap = JsonUtil.fromJson(travelinfojson, Map.class);
		Integer id = Integer.valueOf((String) travelmap.get("id"));
		//出行信息是否存在
		TOrderTripJpEntity travel = !Util.isEmpty(travelmap.get("id")) ? dbDao.fetch(TOrderTripJpEntity.class,
				id.longValue()) : new TOrderTripJpEntity();
		//travel = dbDao.fetch(TOrderTripJpEntity.class, (Integer.valueOf(travelmap.get("id"))).longValue());
		travel.setTripPurpose(String.valueOf(travelmap.get("trippurpose")));
		String godatestr = String.valueOf(travelmap.get("goDate"));
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
		if (!Util.isEmpty(travelmap.get("godeparturecity"))) {
			travel.setGoDepartureCity(Integer.valueOf(String.valueOf(travelmap.get("godeparturecity"))));
		} else {
			travel.setGoDepartureCity(null);
		}
		if (!Util.isEmpty(travelmap.get("goarrivedcity"))) {
			travel.setGoArrivedCity(Integer.valueOf(String.valueOf(travelmap.get("goarrivedcity"))));
		} else {
			travel.setGoArrivedCity(null);
		}
		if (!Util.isEmpty(travelmap.get("goflightnum"))) {
			travel.setGoFlightNum(Integer.valueOf(String.valueOf(travelmap.get("goflightnum"))));
		} else {
			travel.setGoFlightNum(null);
		}
		if (!Util.isEmpty(travelmap.get("returndeparturecity"))) {
			travel.setReturnDepartureCity(Integer.valueOf(String.valueOf(travelmap.get("returndeparturecity"))));
		} else {
			travel.setReturnDepartureCity(null);
		}
		if (!Util.isEmpty(travelmap.get("returnarrivedcity"))) {
			travel.setReturnArrivedCity(Integer.valueOf(String.valueOf(travelmap.get("returnarrivedcity"))));
		} else {
			travel.setReturnArrivedCity(null);
		}
		if (!Util.isEmpty(travelmap.get("returnflightnum"))) {
			travel.setReturnFlightNum(Integer.valueOf(String.valueOf(travelmap.get("returnflightnum"))));
		} else {
			travel.setReturnFlightNum(null);
		}

		//保存出行信息
		if (Util.isEmpty(travelmap.get("id"))) {
			travel.setOpId(loginUser.getId());
			travel.setCreateTime(new Date());
			travel.setOrderId(editDataForm.getOrderid());
			dbDao.insert(travel);
		} else {
			travel.setUpdateTime(new Date());
			dbDao.update(travel);
		}
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
	public Object resetPlan(Integer orderid, Integer planid) {
		TOrderTravelplanJpEntity plan = dbDao.fetch(TOrderTravelplanJpEntity.class, planid.longValue());
		//获取城市所有的酒店
		List<THotelEntity> hotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", plan.getCityId()), null);
		//获取城市所有的景区
		String sqlString = sqlManager.get("get_reset_travel_plan_scenic");
		Sql sql = Sqls.create(sqlString);
		sql.setParam("orderid", orderid);
		sql.setParam("cityid", plan.getCityId());
		sql.setParam("scenicname", plan.getScenic());
		List<Record> scenics = dbDao.query(sql, null, null);
		Random random = new Random();
		plan.setScenic(scenics.get(random.nextInt(scenics.size())).getString("name"));
		plan.setHotel(hotels.get(random.nextInt(hotels.size())).getId());
		dbDao.update(plan);
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
		//需要生成的travelplan
		List<TOrderTravelplanJpEntity> travelplans = Lists.newArrayList();
		for (int i = 0; i < daysBetween; i++) {
			TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
			travelplan.setCityId(planform.getGoArrivedCity());
			travelplan.setDay(String.valueOf(i + 1));
			travelplan.setOrderId(planform.getOrderid());
			travelplan.setOutDate(DateUtil.addDay(planform.getGoDate(), i));
			travelplan.setCityName(city.getCity());
			travelplan.setCreateTime(new Date());
			//酒店
			Random random = new Random();
			int hotelindex = random.nextInt(hotels.size());
			THotelEntity hotel = hotels.get(hotelindex);
			travelplan.setHotel(hotel.getId());
			//景区
			int scenicindex = random.nextInt(scenics.size());
			TScenicEntity scenic = scenics.get(scenicindex);
			scenics.remove(scenic);
			travelplan.setScenic(scenic.getName());
			travelplan.setOpId(loginUser.getOpId());
			travelplans.add(travelplan);
		}

		List<TOrderTravelplanJpEntity> before = dbDao.query(TOrderTravelplanJpEntity.class,
				Cnd.where("orderid", "=", planform.getOrderid()), null);
		//更新行程安排
		dbDao.updateRelations(before, travelplans);
		result.put("data", getTravelPlanByOrderId(planform.getOrderid()));
		result.put("status", "success");
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
		for (Record record : travelplans) {
			Date outdate = (Date) record.get("outdate");
			record.put("outdate", format.format(outdate));
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
		THotelEntity hotel = dbDao.fetch(THotelEntity.class, plan.getHotel().longValue());
		result.put("hotel", hotel);
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
		TOrderTravelplanJpEntity travel = dbDao.fetch(TOrderTravelplanJpEntity.class, travelform.getId().longValue());
		//城市信息
		TCityEntity city = dbDao.fetch(TCityEntity.class, travelform.getCityId().longValue());
		travel.setCityId(travelform.getCityId());
		travel.setCityName(city.getCity());
		travel.setDay(travelform.getDay());
		travel.setHotel(travelform.getHotel());
		travel.setOutDate(travelform.getOutDate());
		travel.setScenic(travelform.getScenic());
		travel.setUpdateTime(new Date());
		dbDao.update(travel);
		return null;
	}

	/**
	 * 跳转到实收页面
	 * <p>
	 * TODO 为实收页面准备数据
	 *
	 * @param session
	 * @param orderid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object revenue(HttpSession session, Integer orderid) {
		Map<String, Object> result = Maps.newHashMap();

		//申请人列表
		String sqlStr = sqlManager.get("get_japan_visa_list_data_apply");
		Sql applysql = Sqls.create(sqlStr);
		List<Record> query = dbDao.query(applysql, Cnd.where("taoj.orderId", "=", orderid), null);
		for (Record apply : query) {
			Integer dataType = (Integer) apply.get("dataType");
			for (VisaDataTypeEnum dataTypeEnum : VisaDataTypeEnum.values()) {
				if (dataType == dataTypeEnum.intKey()) {
					apply.put("dataType", dataTypeEnum.value());
				}
			}
			List<TApplicantVisaPaperworkJpEntity> revenue = dbDao.query(TApplicantVisaPaperworkJpEntity.class,
					Cnd.where("applicantId", "=", apply.get("applicatid")), null);
			apply.put("revenue", revenue);
		}
		result.put("applicant", query);
		result.put("orderid", orderid);
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
		return dbDao.insert(visapaperwork);
	}

	/**
	 *保存真是资料数据
	 * <p>
	 * TODO 保存真实资料数据
	 *
	 * @param orderjp
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveRealInfoData(TOrderJpEntity orderjp) {

		return dbDao.update(orderjp);

	}

	/**
	 * 跳转到签证录入列表页面
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param session
	 * @param applyid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object visaInput(HttpSession session, Integer applyid) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("applyid", applyid);
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
}
