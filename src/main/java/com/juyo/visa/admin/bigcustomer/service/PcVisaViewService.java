package com.juyo.visa.admin.bigcustomer.service;

import java.util.ArrayList;
import java.util.Collections;
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
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.bigcustomer.form.VisaListDataForm;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.enums.TravelpurposeEnum;
import com.juyo.visa.common.enums.visaProcess.VisaStatusEnum;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TAppStaffContactpointEntity;
import com.juyo.visa.entities.TAppStaffFamilyinfoEntity;
import com.juyo.visa.entities.TAppStaffPassportEntity;
import com.juyo.visa.entities.TAppStaffPrevioustripinfoEntity;
import com.juyo.visa.entities.TAppStaffTravelcompanionEntity;
import com.juyo.visa.entities.TAppStaffWorkEducationTrainingEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TOrderUsEntity;
import com.juyo.visa.entities.TOrderUsInfoEntitiy;
import com.juyo.visa.entities.TOrderUsTravelinfoEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.OrderUpdateForm;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class PcVisaViewService extends BaseService<TOrderUsEntity> {
	private static final Log log = Logs.get();

	/**
	 * 
	 * 办理证签证列表页
	 *
	 * @param form
	 * @param session
	 * @return
	 */
	public Object visaListData(VisaListDataForm form, HttpSession session) {
		//获取当前公司
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//获取当前用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		form.setUserid(loginUser.getId());
		form.setAdminId(loginCompany.getAdminId());

		Map<String, Object> result = Maps.newHashMap();
		List<Record> list = new ArrayList<>();

		//分页
		Sql sql = form.sql(sqlManager);
		Integer pageNumber = form.getPageNumber();
		Integer pageSize = form.getPageSize();
		Pager pager = new OffsetPager((pageNumber - 1) * pageSize, pageSize);
		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));
		sql.setPager(pager);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		//主sql数据 
		List<Record> orderList = (List<Record>) sql.getResult();
		for (Record order : orderList) {
			String orderid = order.getString("orderid");
			//获取该订单下的申请人
			String sqlStr = sqlManager.get("bigCustomer_order_applicant_list");
			Sql applysql = Sqls.create(sqlStr);
			Cnd cnd = Cnd.NEW();
			cnd.and("tasou.orderid", "=", orderid);
			List<Record> applicantList = dbDao.query(applysql, cnd, null);
			for (Record app : applicantList) {
				int status = app.getInt("visastatus");
				for (VisaStatusEnum statusEnum : VisaStatusEnum.values()) {
					if (!Util.isEmpty(status) && status == statusEnum.intKey()) {
						app.set("visastatus", statusEnum.value());
						break;
					}
				}
			}
			order.put("everybodyInfo", applicantList);
			if (applicantList.size() > 0) {
				order.put("firstbodyInfo", applicantList.get(0));
			} else {
				Record record = new Record();
				record.set("staffid", "");
				record.set("staffname", "");
				record.set("telephone", "");
				record.set("cardnum", "");
				record.set("passport", "");
				record.set("ordernumber", "");
				record.set("status", "");
				record.set("aacode", "");
				record.set("orderid", "");

				order.put("firstbodyInfo", record);
			}
			list.add(order);
		}

		//list前后倒置，这样第一个对象为最新的订单
		Collections.reverse(list);
		result.put("visaListData", list);

		return result;
	}

	/**
	 * 获取签证 旅伴信息
	 */
	public Object getStaffTravelCompanion(Integer staffid) {
		TAppStaffTravelcompanionEntity entity = dbDao.fetch(TAppStaffTravelcompanionEntity.class,
				Cnd.where("staffid", "=", staffid));
		return entity;
	}

	/**
	 * 获取签证 以前的美国旅游信息
	 */
	public Object getStaffpreviousTripInfo(Integer staffid) {
		TAppStaffPrevioustripinfoEntity entity = dbDao.fetch(TAppStaffPrevioustripinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		return entity;
	}

	/**
	 * 获取签证 美国联络点
	 */
	public Object getStaffContactPoint(Integer staffid) {
		TAppStaffContactpointEntity entity = dbDao.fetch(TAppStaffContactpointEntity.class,
				Cnd.where("staffid", "=", staffid));
		return entity;
	}

	/**
	 * 获取签证 家庭信息
	 */
	public Object getStaffFamilyInfo(Integer staffid) {
		TAppStaffFamilyinfoEntity entity = dbDao.fetch(TAppStaffFamilyinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		return entity;

	}

	/**
	 * 获取签证 工作/教育/培训信息
	 */
	public Object getStaffWorkEducationTraining(Integer staffid) {
		TAppStaffWorkEducationTrainingEntity entity = dbDao.fetch(TAppStaffWorkEducationTrainingEntity.class,
				Cnd.where("staffid", "=", staffid));
		return entity;
	}

	/**
	 * 
	 * 获取订单出行信息
	 *
	 * @param orderid 订单id
	 * @return 订单出行信息
	 */
	public Object getOrderTravelInfo(Integer orderid) {
		TOrderUsTravelinfoEntity entity = dbDao.fetch(TOrderUsTravelinfoEntity.class,
				Cnd.where("orderid", "=", orderid));
		return entity;
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

	/*
	 * 
	 * 
	 */
	public Object getOrderInfo(Integer orderid) {
		TOrderUsInfoEntitiy entitiy = dbDao.fetch(TOrderUsInfoEntitiy.class, Cnd.where("id", "=", orderid));
		return entitiy;
	}

	/**
	 * 跳转到签证详情页
	 */
	public Object visaDetail(Integer orderid) {
		Map<String, Object> result = Maps.newHashMap();

		TOrderUsTravelinfoEntity orderTravelInfo = (TOrderUsTravelinfoEntity) getOrderTravelInfo(orderid);
		String travelpurpose = orderTravelInfo.getTravelpurpose();
		if (!Util.isEmpty(travelpurpose)) {
			String travelpurposeString = TravelpurposeEnum.getValue(travelpurpose).getValue();
			//获取出行目的
			orderTravelInfo.setTravelpurpose(travelpurposeString);
		}
		List<Record> staffSummaryInfoList = (List<Record>) getStaffSummaryInfo(orderid);
		TOrderUsInfoEntitiy orderInfoEntity = (TOrderUsInfoEntitiy) getOrderInfo(orderid);
		result.put("orderInfo", orderInfoEntity);
		result.put("travelInfo", orderTravelInfo);
		if (!Util.isEmpty(staffSummaryInfoList)) {
			result.put("summaryInfo", staffSummaryInfoList.get(0));
		} else {
			result.put("summaryInfo", null);
		}
		return result;
	}

	/*
	 * 基本信息保存
	 */
	public Object visaSave(OrderUpdateForm form, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();
		//订单id
		Integer orderid = form.getOrderid();
		//获取出行信息表
		TOrderUsTravelinfoEntity orderTravelInfo = dbDao.fetch(TOrderUsTravelinfoEntity.class,
				Cnd.where("orderId", "=", orderid));

		if (!Util.isEmpty(form.getGodate())) {
			orderTravelInfo.setGodate(form.getGodate());
		}
		if (!Util.isEmpty(form.getLeavedate())) {
			orderTravelInfo.setLeavedate(form.getLeavedate());
		}
		if (!Util.isEmpty(form.getArrivedate())) {
			orderTravelInfo.setArrivedate(form.getArrivedate());
		}
		if (!Util.isEmpty(form.getStaydays())) {
			orderTravelInfo.setStaydays(form.getStaydays());
		}
		if (!Util.isEmpty(form.getPlanaddress())) {
			orderTravelInfo.setPlanaddress(form.getPlanaddress());
		}
		if (!Util.isEmpty(form.getPlancity())) {
			orderTravelInfo.setPlancity(form.getPlancity());
		}
		if (!Util.isEmpty(form.getPlanstate())) {
			orderTravelInfo.setPlanstate(form.getPlanstate());
		}
		if (!Util.isEmpty(form.getTravelpurpose())) {
			String travelpurpose = form.getTravelpurpose();
			String key = TravelpurposeEnum.getEnum(travelpurpose).getKey();
			orderTravelInfo.setTravelpurpose(key);
		}
		orderTravelInfo.setGodeparturecity(form.getGodeparturecity());
		orderTravelInfo.setGoArrivedCity(form.getGoArrivedCity());
		orderTravelInfo.setGoFlightNum(form.getGoFlightNum());
		orderTravelInfo.setReturnDepartureCity(form.getReturnDepartureCity());
		orderTravelInfo.setReturnArrivedCity(form.getReturnArrivedCity());
		orderTravelInfo.setReturnFlightNum(form.getReturnFlightNum());
		//修改出行信息
		int orderUpdateNum = dbDao.update(orderTravelInfo);

		//获取人员基本信息
		TAppStaffBasicinfoEntity basicinfo = dbDao.fetch(TAppStaffBasicinfoEntity.class,
				Cnd.where("id", "=", form.getStaffid()));
		basicinfo.setAacode(form.getAacode());
		basicinfo.setInterviewdate(form.getInterviewdate());
		basicinfo.setBirthday(form.getBirthday());
		int basicinfoUpdate = dbDao.update(basicinfo);

		//获取护照信息
		TAppStaffPassportEntity passPortInfo = dbDao.fetch(TAppStaffPassportEntity.class,
				Cnd.where("staffid", "=", form.getStaffid()));
		passPortInfo.setFirstname(basicinfo.getFirstname());
		passPortInfo.setFirstnameen(basicinfo.getFirstnameen());
		passPortInfo.setLastname(basicinfo.getLastname());
		passPortInfo.setLastnameen(basicinfo.getLastnameen());
		passPortInfo.setSex(form.getSex());
		passPortInfo.setPassport(form.getPassport());
		int passPortInfoUpdateNum = dbDao.update(passPortInfo);
		if (orderUpdateNum == 1 && passPortInfoUpdateNum == 1 && basicinfoUpdate == 1) {
			return "1";
		} else {
			return "0";
		}
	}
}
