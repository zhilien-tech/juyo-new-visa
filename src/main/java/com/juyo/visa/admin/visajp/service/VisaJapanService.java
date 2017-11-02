/**
 * VisaJapanService.java
 * com.juyo.visa.admin.visajp.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.visajp.form.VisaEditDataForm;
import com.juyo.visa.admin.visajp.form.VisaListDataForm;
import com.juyo.visa.common.enums.CollarAreaEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.MainSalePayTypeEnum;
import com.juyo.visa.common.enums.MainSaleTripTypeEnum;
import com.juyo.visa.common.enums.MainSaleUrgentEnum;
import com.juyo.visa.common.enums.MainSaleUrgentTimeEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.common.enums.VisaDataTypeEnum;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TFlightEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderTravelplanJpEntity;
import com.juyo.visa.entities.TOrderTripJpEntity;
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
		result.put("travelinfo", travelinfo);
		//申请人信息
		String applysqlstr = sqlManager.get("get_jporder_detail_applyinfo_byorderid");
		Sql applysql = Sqls.create(applysqlstr);
		applysql.setParam("orderid", orderid);
		List<Record> applyinfo = dbDao.query(applysql, null, null);
		result.put("applyinfo", applyinfo);
		//行程安排
		List<TOrderTravelplanJpEntity> travelplan = dbDao.query(TOrderTravelplanJpEntity.class,
				Cnd.where("orderId", "=", orderid).orderBy("outDate", "asc"), null);
		result.put("travelplan", travelplan);
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
		Integer id = (Integer) travelmap.get("id");
		//出行信息是否存在
		TOrderTripJpEntity travel = !Util.isEmpty(travelmap.get("id")) ? dbDao.fetch(TOrderTripJpEntity.class,
				id.longValue()) : new TOrderTripJpEntity();
		//travel = dbDao.fetch(TOrderTripJpEntity.class, (Integer.valueOf(travelmap.get("id"))).longValue());
		travel.setTripPurpose(String.valueOf(travelmap.get("tripPurpose")));
		String godatestr = String.valueOf(travelmap.get("goDate"));
		if (!Util.isEmpty(travelmap.get("goDate"))) {
			Date godate = DateUtil.string2Date(godatestr, DateUtil.FORMAT_YYYY_MM_DD);
			travel.setGoDate(godate);
		}
		String returndatestr = String.valueOf(travelmap.get("returnDate"));
		if (!Util.isEmpty(travelmap.get("returnDate"))) {
			Date returndate = DateUtil.string2Date(returndatestr, DateUtil.FORMAT_YYYY_MM_DD);
			travel.setReturnDate(returndate);
		}
		if (!Util.isEmpty(travelmap.get("goDepartureCity"))) {
			travel.setGoDepartureCity(Integer.valueOf(String.valueOf(travelmap.get("goDepartureCity"))));
		}
		if (!Util.isEmpty(travelmap.get("goArrivedCity"))) {
			travel.setGoArrivedCity(Integer.valueOf(String.valueOf(travelmap.get("goArrivedCity"))));
		}
		if (!Util.isEmpty(travelmap.get("goFlightNum"))) {
			travel.setGoFlightNum(Integer.valueOf(String.valueOf(travelmap.get("goFlightNum"))));
		}
		if (!Util.isEmpty(travelmap.get("returnDepartureCity"))) {
			travel.setReturnDepartureCity(Integer.valueOf(String.valueOf(travelmap.get("returnDepartureCity"))));
		}
		if (!Util.isEmpty(travelmap.get("returnArrivedCity"))) {
			travel.setReturnArrivedCity(Integer.valueOf(String.valueOf(travelmap.get("returnArrivedCity"))));
		}
		if (!Util.isEmpty(travelmap.get("returnFlightNum"))) {
			travel.setReturnFlightNum(Integer.valueOf(String.valueOf(travelmap.get("returnFlightNum"))));
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
		plan.setScenic(null);
		plan.setHotel(null);
		dbDao.update(plan);
		//行程安排
		List<TOrderTravelplanJpEntity> travelplan = dbDao.query(TOrderTravelplanJpEntity.class,
				Cnd.where("orderId", "=", orderid).orderBy("outDate", "asc"), null);
		return travelplan;

	}

}
