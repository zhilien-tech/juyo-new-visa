/**
 * FirstTrialJpViewService.java
 * com.juyo.visa.admin.firstTrialJp.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.firstTrialJp.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.POST;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.firstTrialJp.from.FirstTrialJpEditDataForm;
import com.juyo.visa.admin.firstTrialJp.from.FirstTrialJpListDataForm;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.enums.CollarAreaEnum;
import com.juyo.visa.common.enums.ExpressTypeEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.MainSalePayTypeEnum;
import com.juyo.visa.common.enums.MainSaleTripTypeEnum;
import com.juyo.visa.common.enums.MainSaleUrgentEnum;
import com.juyo.visa.common.enums.MainSaleUrgentTimeEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.common.enums.TrialApplicantStatusEnum;
import com.juyo.visa.common.enums.UserLoginEnum;
import com.juyo.visa.common.enums.VisaDataTypeEnum;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantUnqualifiedEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderRecipientEntity;
import com.juyo.visa.entities.TReceiveaddressEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TApplicantUnqualifiedForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * 日本订单初审Service
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年11月9日 	 
 */
@IocBean
public class FirstTrialJpViewService extends BaseService<TOrderEntity> {

	/**
	 * 初审列表数据
	 * @param form
	 * @param request
	 * @return
	 */
	public Object trialListData(FirstTrialJpListDataForm form, HttpSession session) {
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
			String sqlStr = sqlManager.get("firstTrialJp_list_data_applicant");
			Sql applysql = Sqls.create(sqlStr);
			List<Record> records = dbDao.query(applysql, Cnd.where("taoj.orderId", "=", orderid), null);
			for (Record applicant : records) {
				Integer status = (Integer) applicant.get("applicantStatus");
				for (TrialApplicantStatusEnum statusEnum : TrialApplicantStatusEnum.values()) {
					if (!Util.isEmpty(status) && status.equals(statusEnum.intKey())) {
						applicant.put("applicantStatus", statusEnum.value());
					}
				}
			}
			record.put("everybodyInfo", records);
		}
		result.put("trialJapanData", list);
		return result;
	}

	/**
	 * 跳转到日本初审详情页面
	 * <p>
	 * 为日本初审详情页面准备数据
	 *
	 * @param orderid
	 * @return 
	 */
	public Object trialDetail(Integer orderid) {
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

		return result;
	}

	/**
	 * 日本初审详情页数据
	 * <p>
	 * @param orderid
	 * @return 
	 */
	public Object getJpTrialDetailData(Integer orderid) {
		Map<String, Object> result = Maps.newHashMap();
		//订单信息
		String sqlstr = sqlManager.get("firstTrialJp_order_info_byid");
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
		//申请人信息
		List<Record> applyinfo = getApplicantByOrderid(orderid);
		result.put("applyinfo", applyinfo);

		return result;
	}

	//判断订单下申请人是否合格
	public Boolean isQualified(Integer orderid) {
		List<Record> applicants = getApplicantByOrderid(orderid);
		boolean isQualified = true;
		if (!Util.isEmpty(applicants)) {
			for (Record record : applicants) {
				String status = (String) record.get("applicantstatus");
				if (Util.eq("不合格", status)) {
					isQualified = false;
				}
			}
		}
		return isQualified;
	}

	//根据订单id 获得申请人
	public List<Record> getApplicantByOrderid(Integer orderid) {
		String applysqlstr = sqlManager.get("firstTrialJp_orderDetail_applicant_by_orderid");
		Sql applysql = Sqls.create(applysqlstr);
		applysql.setParam("orderid", orderid);
		List<Record> applyinfo = dbDao.query(applysql, null, null);
		for (Record record : applyinfo) {
			//资料类型
			Integer type = (Integer) record.get("type");
			for (VisaDataTypeEnum visadatatype : VisaDataTypeEnum.values()) {
				if (!Util.isEmpty(type) && type.equals(visadatatype.intKey())) {
					record.put("type", visadatatype.value());
				}
			}
			String sex = (String) record.get("sex");
			record.set("sex", sex);
			Integer status = (Integer) record.get("applicantstatus");
			for (TrialApplicantStatusEnum statusEnum : TrialApplicantStatusEnum.values()) {
				if (!Util.isEmpty(status) && status.equals(statusEnum.intKey())) {
					record.put("applicantstatus", statusEnum.value());
				}
			}
		}

		return applyinfo;
	}

	//快递 发邮件
	public Object express(int orderid, HttpSession session) {

		Map<String, Object> result = Maps.newHashMap();

		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Integer comId = loginCompany.getId();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		Integer userType = loginUser.getUserType();

		//收件地址
		List<TReceiveaddressEntity> receiveAddresss = new ArrayList<TReceiveaddressEntity>();
		if (userType == UserLoginEnum.PERSONNEL.intKey()) {
			//工作人员
			receiveAddresss = dbDao.query(TReceiveaddressEntity.class, Cnd.where("userId", "=", userId), null);
		} else {
			//其他
			receiveAddresss = dbDao.query(TReceiveaddressEntity.class, Cnd.where("comId", "=", comId), null);
		}
		result.put("receiveAddresss", receiveAddresss);

		//订单收件信息
		String sqlRec = sqlManager.get("firstTrialJp_receive_address_by_orderid");
		Sql sql = Sqls.create(sqlRec);
		sql.setParam("orderid", orderid);
		Record orderReceive = dbDao.fetch(sql);
		result.put("orderReceive", orderReceive);

		//快递方式
		result.put("expressType", EnumUtil.enum2(ExpressTypeEnum.class));

		//订单主申请人
		String sqlStr = sqlManager.get("firstTrialJp_list_data_applicant");
		Sql applysql = Sqls.create(sqlStr);
		List<Record> records = dbDao.query(applysql,
				Cnd.where("taoj.orderId", "=", orderid).and("taoj.isMainApplicant", "=", IsYesOrNoEnum.YES.intKey()),
				null);
		for (Record applicant : records) {
			Integer status = (Integer) applicant.get("applicantStatus");
			for (TrialApplicantStatusEnum statusEnum : TrialApplicantStatusEnum.values()) {
				if (!Util.isEmpty(status) && status.equals(statusEnum.intKey())) {
					applicant.put("applicantStatus", statusEnum.value());
				}
			}
		}
		result.put("applicant", records);
		//订单id
		result.put("orderid", orderid);

		return result;
	}

	//获取订单主申请人
	public Object getmainApplicantByOrderid(int orderid) {
		Map<String, Object> result = Maps.newHashMap();
		String sqlStr = sqlManager.get("firstTrialJp_list_data_applicant");
		Sql applysql = Sqls.create(sqlStr);
		List<Record> records = dbDao.query(applysql,
				Cnd.where("taoj.orderId", "=", orderid).and("taoj.isMainApplicant", "=", IsYesOrNoEnum.YES.intKey()),
				null);
		for (Record applicant : records) {
			Integer status = (Integer) applicant.get("applicantStatus");
			for (TrialApplicantStatusEnum statusEnum : TrialApplicantStatusEnum.values()) {
				if (!Util.isEmpty(status) && status.equals(statusEnum.intKey())) {
					applicant.put("applicantStatus", statusEnum.value());
				}
			}
		}
		result.put("applicant", records);
		return result;
	}

	//获取申请人信息
	public Object basicInfo(int applyid) {
		TApplicantEntity appllicant = dbDao.fetch(TApplicantEntity.class, Cnd.where("id", "=", applyid));
		return appllicant;
	}

	//合格申请人
	public Object qualified(int applyid) {
		int update = dbDao.update(TApplicantEntity.class,
				Chain.make("status", TrialApplicantStatusEnum.qualified.intKey()), Cnd.where("id", "=", applyid));
		if (update > 0) {
			//清空不合格信息
			TApplicantUnqualifiedEntity unqualifiedInfo = dbDao.fetch(TApplicantUnqualifiedEntity.class,
					Cnd.where("applicantId", "=", applyid));
			if (!Util.isEmpty(unqualifiedInfo)) {
				dbDao.delete(unqualifiedInfo);
			}
		}
		return update > 0;
	}

	//获取申请人不合格信息
	public Object unqualified(int applyid) {
		Map<String, Object> result = Maps.newHashMap();
		int count = nutDao.count(TApplicantUnqualifiedEntity.class, Cnd.where("applicantId", "=", applyid));
		TApplicantUnqualifiedEntity unqualifiedInfo = dbDao.fetch(TApplicantUnqualifiedEntity.class,
				Cnd.where("applicantId", "=", applyid));
		result.put("applyid", applyid);
		result.put("hasUnqInfo", count > 0);
		result.put("unqualifiedInfo", unqualifiedInfo);
		return result;
	}

	//保存不合格信息
	public Object saveUnqualified(TApplicantUnqualifiedForm form) {
		int YES = IsYesOrNoEnum.YES.intKey();
		int NO = IsYesOrNoEnum.NO.intKey();
		Integer applicantId = form.getApplicantId();
		String isBase = form.getIsBase();
		int isB = 0;
		if (Util.eq("on", isBase)) {
			isB = YES;
		}
		String baseRemark = form.getBaseRemark();
		String isPassport = form.getIsPassport();
		int isP = 0;
		if (Util.eq("on", isPassport)) {
			isP = YES;
		}
		String passRemark = form.getPassRemark();
		String isVisa = form.getIsVisa();
		int isV = 0;
		if (Util.eq("on", isVisa)) {
			isV = YES;
		}
		String visaRemark = form.getVisaRemark();

		TApplicantUnqualifiedEntity unqualifiedInfo = dbDao.fetch(TApplicantUnqualifiedEntity.class,
				Cnd.where("applicantId", "=", applicantId));
		if (Util.isEmpty(unqualifiedInfo)) {
			//添加
			if (!Util.isEmpty(applicantId)) {
				TApplicantUnqualifiedEntity unq = new TApplicantUnqualifiedEntity();
				unq.setApplicantId(applicantId);
				unq.setIsBase(isB);
				unq.setBaseRemark(baseRemark);
				unq.setIsPassport(isP);
				unq.setPassRemark(passRemark);
				unq.setIsVisa(isV);
				unq.setVisaRemark(visaRemark);
				dbDao.insert(unq);
			}

		} else {
			//更新
			unqualifiedInfo.setApplicantId(applicantId);
			unqualifiedInfo.setIsBase(isB);
			unqualifiedInfo.setBaseRemark(baseRemark);
			unqualifiedInfo.setIsPassport(isP);
			unqualifiedInfo.setPassRemark(passRemark);
			unqualifiedInfo.setIsVisa(isV);
			unqualifiedInfo.setVisaRemark(visaRemark);
			nutDao.update(unqualifiedInfo);
		}

		//只要有一个不合格, 则申请人状态不合格
		if (isB == 1 || isV == 1 || isP == 1) {
			dbDao.update(TApplicantEntity.class, Chain.make("status", TrialApplicantStatusEnum.unqualified.intKey()),
					Cnd.where("id", "=", applicantId));
		}

		return Json.toJson("success");
	}

	//根据电话，获取收件地址信息
	public Object getRAddressSelect(String searchStr, String type, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Integer comId = loginCompany.getId();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		Integer userType = loginUser.getUserType();
		Cnd cnd = Cnd.NEW();
		if (Util.eq("mobileType", type)) {
			cnd.and("mobile", "like", Strings.trim(searchStr) + "%");
		} else if (Util.eq("usernameType", type)) {
			cnd.and("receiver", "like", Strings.trim(searchStr) + "%");
		}
		cnd.limit(0, 5);
		if (userType == UserLoginEnum.PERSONNEL.intKey()) {
			//工作人员
			cnd.and("userId", "=", userId);
		} else {
			//其他
			cnd.and("comId", "=", comId);
		}

		List<TReceiveaddressEntity> query = dbDao.query(TReceiveaddressEntity.class, cnd, null);
		return query;
	}

	//根据id获取收件信息
	public Object getRAddressById(String addressId) {
		return dbDao.fetch(TReceiveaddressEntity.class, Cnd.where("id", "=", addressId));
	}

	/**
	 * 保存快递信息，并发送邮件
	 */
	@At
	@POST
	public Object saveExpressInfo(Integer orderid, Integer expresstype, Integer receiveAddressId, HttpSession session) {
		//获取当前用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		TOrderRecipientEntity orderReceive = dbDao.fetch(TOrderRecipientEntity.class,
				Cnd.where("orderId", "=", orderid));
		if (!Util.isEmpty(orderReceive)) {
			//更新
			orderReceive.setOrderId(orderid);
			orderReceive.setExpressType(expresstype);
			orderReceive.setReceiveAddressId(receiveAddressId);
			orderReceive.setOpId(userId);
			orderReceive.setUpdateTime(DateUtil.nowDate());
			nutDao.update(orderReceive);
		} else {
			//添加
			TOrderRecipientEntity orderReceiveAdd = new TOrderRecipientEntity();
			orderReceiveAdd.setOrderId(orderid);
			orderReceiveAdd.setExpressType(expresstype);
			orderReceiveAdd.setReceiveAddressId(receiveAddressId);
			orderReceiveAdd.setOpId(userId);
			orderReceiveAdd.setUpdateTime(DateUtil.nowDate());
			orderReceiveAdd.setCreateTime(DateUtil.nowDate());
			dbDao.insert(orderReceiveAdd);
		}

		//改变订单状态 由初审到前台、签证 TODO

		//发送短信、邮件 TODO

		return null;
	}

	/**
	 * 保存初审编辑页数据
	 * <p>
	 * @param editDataForm
	 * @param session
	 * @return 
	 */
	public Object saveJpTrialDetailInfo(FirstTrialJpEditDataForm editDataForm, HttpSession session) {
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
		return null;
	}

}
