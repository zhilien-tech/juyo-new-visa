package com.juyo.visa.admin.myVisa.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import org.nutz.mvc.annotation.Param;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.myVisa.form.MyVisaListDataForm;
import com.juyo.visa.admin.visajp.form.VisaListDataForm;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.JobStatusEnum;
import com.juyo.visa.common.enums.TrialApplicantStatusEnum;
import com.juyo.visa.common.enums.YouKeExpressTypeEnum;
import com.juyo.visa.entities.TApplicantBackmailJpEntity;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantExpressEntity;
import com.juyo.visa.entities.TApplicantFrontPaperworkJpEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TApplicantUnqualifiedEntity;
import com.juyo.visa.entities.TApplicantVisaJpEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * 我的签证 service
 */
@IocBean
public class MyVisaService extends BaseService<TOrderJpEntity> {

	//申请人办理中的签证
	@SuppressWarnings("null")
	public Object myVisaListData(int orderJpId, MyVisaListDataForm form, HttpSession session) {

		Map<String, Object> result = Maps.newHashMap();

		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		//判断当前登录用户,如果isSameLinker为1则为统一联系人，查询订单下所有申请人，为0时只查自己
		TApplicantEntity apply = null;
		List<TApplicantEntity> applyList = dbDao.query(TApplicantEntity.class, Cnd.where("userId", "=", userId), null);
		for (TApplicantEntity tApplicantEntity : applyList) {
			TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
					Cnd.where("applicantId", "=", tApplicantEntity.getId()));
			Integer orderId = applicantOrderJpEntity.getOrderId();
			if (Util.eq(orderId, orderJpId)) {
				apply = tApplicantEntity;
			}
		}
		TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", apply.getId()));
		Integer isSameLinker = applicantOrderJpEntity.getIsSameLinker();
		form.setOrderjpid(orderJpId);
		form.setIsMainLink(isSameLinker);
		form.setApplicatid(apply.getId());
		Sql sql = form.sql(sqlManager);

		Integer pageNumber = form.getPageNumber();
		Integer pageSize = form.getPageSize();
		Pager pager = new OffsetPager((pageNumber - 1) * pageSize, pageSize);

		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));
		sql.setPager(pager);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		List<Record> records = (List<Record>) sql.getResult();
		//格式化日期
		DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		for (Record record : records) {
			//判断是否有统一联系人
			Integer sameLinker = (Integer) record.get("isSameLinker");
			//资料类型
			Integer ostatus = (Integer) record.get("orderstatus");
			for (JPOrderStatusEnum jpos : JPOrderStatusEnum.values()) {
				if (!Util.isEmpty(ostatus) && ostatus.equals(jpos.intKey())) {
					record.put("orderstatus", jpos.value());
				}
			}
			if (!Util.isEmpty(record.get("sendvisadate"))) {
				Date sendVisaDate = (Date) record.get("sendvisadate");
				record.put("sendvisadate", format.format(sendVisaDate));
			}
			if (!Util.isEmpty(record.get("outvisadate"))) {
				Date outVisaDate = (Date) record.get("outvisadate");
				record.put("outvisadate", format.format(outVisaDate));
			}
			if (Util.eq(sameLinker, IsYesOrNoEnum.YES.intKey())) {
				result.put("myVisaData", record);
			}
		}
		result.put("myVisaData", records);

		return result;
	}

	public Object visaListData(VisaListDataForm form, HttpSession session) {
		//获取当前公司
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//获取当前用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Map<String, Object> result = Maps.newHashMap();
		List<TApplicantEntity> applyList = dbDao.query(TApplicantEntity.class,
				Cnd.where("userId", "=", loginUser.getId()), null);
		List<TOrderJpEntity> orderJpList = new ArrayList<TOrderJpEntity>();
		List<Record> list = new ArrayList<>();
		for (TApplicantEntity tApplicantEntity : applyList) {
			TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
					Cnd.where("applicantId", "=", tApplicantEntity.getId()));
			if (!Util.isEmpty(applicantOrderJpEntity)) {
				Integer orderId = applicantOrderJpEntity.getOrderId();
				if (!Util.isEmpty(orderId)) {
					TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, orderId.longValue());
					orderJpList.add(orderJpEntity);
				}
			}
		}
		for (TOrderJpEntity tOrderJpEntity : orderJpList) {
			String passportSqlstr = sqlManager.get("myvisa_japan_visa_list_data");
			Sql passportSql = Sqls.create(passportSqlstr);
			Cnd orderJpCnd = Cnd.NEW();
			orderJpCnd.and("toj.id", "=", tOrderJpEntity.getId());
			passportSql.setCondition(orderJpCnd);
			Record passport = dbDao.fetch(passportSql);
			list.add(passport);
		}
		Collections.reverse(list);
		for (Record record : list) {
			Integer orderid = (Integer) record.get("id");
			String sqlStr = sqlManager.get("myvisa_japan_visa_list_data_apply");
			Sql applysql = Sqls.create(sqlStr);
			List<Record> query = dbDao.query(applysql, Cnd.where("taoj.orderId", "=", orderid), null);
			for (Record apply : query) {
				Integer dataType = (Integer) apply.get("dataType");
				for (JobStatusEnum dataTypeEnum : JobStatusEnum.values()) {
					if (!Util.isEmpty(dataType) && dataType.equals(dataTypeEnum.intKey())) {
						apply.put("dataType", dataTypeEnum.value());
					}
				}
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
		result.put("visaJapanData", list);
		return result;

	}

	//签证进度页
	public Object flowChart(@Param("orderid") Integer orderid, @Param("applicantid") Integer applicantid) {
		Map<String, Object> result = Maps.newHashMap();

		//订单信息
		String orderstatus = "下单";
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, Cnd.where("id", "=", orderid));
		Integer ostatus = order.getStatus();
		for (JPOrderStatusEnum jpos : JPOrderStatusEnum.values()) {
			if (!Util.isEmpty(ostatus) && ostatus.equals(jpos.intKey())) {
				orderstatus = jpos.value();
			}
		}
		TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", order.getId()));
		result.put("orderJpId", orderJpEntity.getId());
		result.put("order", order);
		result.put("orderstatus", orderstatus);

		//申请人信息
		String sqlString = sqlManager.get("myvisa_applicant_by_id");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("ta.id", "=", applicantid);
		sql.setCondition(cnd);
		Record applicantInfo = dbDao.fetch(sql);
		int appllicantStatus = applicantInfo.getInt("applicantstatus");
		for (TrialApplicantStatusEnum statusEnum : TrialApplicantStatusEnum.values()) {
			if (!Util.isEmpty(appllicantStatus) && Util.eq(appllicantStatus, statusEnum.intKey())) {
				applicantInfo.set("applicantstatus", statusEnum.value());
			}
		}
		result.put("applicant", applicantInfo);

		//合格不合格信息
		TApplicantUnqualifiedEntity isQulifiedApplicant = dbDao.fetch(TApplicantUnqualifiedEntity.class,
				Cnd.where("applicantId", "=", applicantid));
		result.put("isQulifiedApplicant", isQulifiedApplicant);
		Integer indexOfPage = 1;
		String unqualifiedInfo = "(";
		if (!Util.isEmpty(isQulifiedApplicant)) {
			Integer isBase = isQulifiedApplicant.getIsBase();
			Integer isPassport = isQulifiedApplicant.getIsPassport();
			Integer isVisa = isQulifiedApplicant.getIsVisa();
			//打开页面的顺序
			if (Util.eq(1, isVisa)) {
				indexOfPage = 3;
			}
			if (Util.eq(1, isPassport)) {
				indexOfPage = 2;
			}
			if (Util.eq(1, isBase)) {
				indexOfPage = 1;
			}
			//不合格信息展示
			if (Util.eq(1, isBase)) {
				unqualifiedInfo += "基本信息、";
			}
			if (Util.eq(1, isPassport)) {
				unqualifiedInfo += "护照信息、";
			}
			if (Util.eq(1, isVisa)) {
				unqualifiedInfo += "签证信息、";
			}
		}
		result.put("indexOfPage", indexOfPage);
		if (unqualifiedInfo.length() > 1) {
			unqualifiedInfo = (unqualifiedInfo.subSequence(0, unqualifiedInfo.length() - 1)) + ")";
		} else {
			unqualifiedInfo = "";
		}
		result.put("unqualifiedInfo", unqualifiedInfo);

		//快递单号
		TApplicantExpressEntity expressEntity = dbDao.fetch(TApplicantExpressEntity.class,
				Cnd.where("applicantId", "=", applicantid));
		String expressNum = "";
		if (!Util.isEmpty(expressEntity)) {
			expressNum = expressEntity.getExpressNum();
		}
		result.put("expressNum", expressNum);
		result.put("expressEntity", expressEntity);

		//预计发招宝时间 
		String prepareDay = "";
		DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		TApplicantFrontPaperworkJpEntity frontPaperworkJpEntity = dbDao.fetch(TApplicantFrontPaperworkJpEntity.class,
				Cnd.where("applicantId", "=", applicantid));
		if (!Util.isEmpty(frontPaperworkJpEntity)) {
			Date receiveDate = frontPaperworkJpEntity.getReceiveDate();
			if (!Util.isEmpty(receiveDate)) {
				//收件时间+3天=预计发招宝时间
				Date prepareDate = DateUtil.addDay(receiveDate, 3);
				prepareDay = format.format(prepareDate);
			}
		}
		result.put("prepareDate", prepareDay);

		//预计送签时间
		Date sendVisaDate = order.getSendVisaDate();
		String svDate = "";

		if (!Util.isEmpty(sendVisaDate)) {
			svDate = format.format(sendVisaDate);
		}
		result.put("sendVisaDate", svDate);

		//进度隐藏页
		int indexOfBlue = getIndexOfBlue(order, applicantid);
		result.put("indexOfBlue", indexOfBlue);

		return result;
	}

	//填写快递单号页
	public Object youkeExpressInfo(Integer applicantId) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("applicantId", applicantId);
		result.put("expressInfo",
				dbDao.fetch(TApplicantExpressEntity.class, Cnd.where("applicantId", "=", applicantId)));
		result.put("expressType", EnumUtil.enum2(YouKeExpressTypeEnum.class));
		return result;
	}

	//保存快递单号
	public Object saveExpressInfo(int expressType, String expressNum, Integer applicantId, HttpSession session) {

		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();

		Date nowDate = DateUtil.nowDate();

		TApplicantExpressEntity expressEntity = dbDao.fetch(TApplicantExpressEntity.class,
				Cnd.where("applicantId", "=", applicantId));
		if (Util.isEmpty(expressEntity)) {
			//添加
			TApplicantExpressEntity express = new TApplicantExpressEntity();
			express.setApplicantId(applicantId);
			express.setExpressNum(expressNum);
			express.setExpressType(expressType);
			express.setOpId(userId);
			express.setCreateTime(nowDate);
			express.setUpdateTime(nowDate);
			dbDao.insert(express);
		} else {
			//编辑
			expressEntity.setExpressNum(expressNum);
			expressEntity.setExpressType(expressType);
			expressEntity.setOpId(userId);
			expressEntity.setUpdateTime(nowDate);
			dbDao.update(expressEntity);
		}
		return "ExpressNum Success";
	}

	//获得进度状态
	public int getIndexOfBlue(TOrderEntity order, Integer applicantid) {
		int indexOfBlue = 0;
		int status = order.getStatus();
		Integer orderid = order.getId();
		Date sendVisaDate = order.getSendVisaDate();
		if (status <= JPOrderStatusEnum.FILLED_INFORMATION.intKey()) {
			//填写完成前
			indexOfBlue = 1;
		} else if (status >= JPOrderStatusEnum.FIRSTTRIAL_ORDER.intKey()
				&& status <= JPOrderStatusEnum.QUALIFIED_ORDER.intKey()) {
			//合格不合格
			indexOfBlue = 2;
		} else if (status == JPOrderStatusEnum.SEND_ADDRESS.intKey()) {
			//已发地址，游客填快递单号
			indexOfBlue = 3;
			//快递是否已寄出
			int count = nutDao.count(TApplicantExpressEntity.class, Cnd.where("applicantId", "=", applicantid));
			if (count > 0) {
				//已寄出
				indexOfBlue = 4;
			}
		} else if (status == JPOrderStatusEnum.RECEPTION_RECEIVED.intKey()) {
			//前台已收件
			indexOfBlue = 5;
			//预计发招宝时间
			indexOfBlue = 6;
		} else if (status == JPOrderStatusEnum.AUTO_FILL_FORM_ED.intKey()) {
			//已发招宝
			indexOfBlue = 7;
			//预计送签时间
			if (!Util.isEmpty(sendVisaDate)) {
				indexOfBlue = 8;
			}
			//资料已进入使馆
			TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", orderid));
			Date deliveryDate = orderJpEntity.getDeliveryDataTime();
			if (!Util.isEmpty(deliveryDate)) {
				indexOfBlue = 9;
			}
		} else if (status >= JPOrderStatusEnum.VISA_ORDER.intKey()
				&& status < JPOrderStatusEnum.AFTERMARKET_ORDER.intKey()) {
			//签证已返回
			TApplicantVisaJpEntity applicantVisaJpEntity = dbDao.fetch(TApplicantVisaJpEntity.class,
					Cnd.where("applicantId", "=", applicantid));
			Date visaEntryTime = applicantVisaJpEntity.getVisaEntryTime();
			if (!Util.isEmpty(visaEntryTime)) {
				indexOfBlue = 10;
			}
		} else if (status >= JPOrderStatusEnum.AFTERMARKET_ORDER.intKey()) {
			//售后，回邮资料
			TApplicantBackmailJpEntity backmailJpEntity = dbDao.fetch(TApplicantBackmailJpEntity.class,
					Cnd.where("applicantId", "=", applicantid));
			if (!Util.isEmpty(backmailJpEntity)) {
				Date backSourceTime = backmailJpEntity.getBackSourceTime();
				if (!Util.isEmpty(backSourceTime)) {
					indexOfBlue = 11;
				}
			}
		}
		return indexOfBlue;
	}
}
