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
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.Param;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.myVisa.form.MyVisaListDataForm;
import com.juyo.visa.admin.order.service.OrderJpViewService;
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
import com.juyo.visa.entities.TApplicantPassportEntity;
import com.juyo.visa.entities.TApplicantUnqualifiedEntity;
import com.juyo.visa.entities.TApplicantVisaJpEntity;
import com.juyo.visa.entities.TApplicantVisaPaperworkJpEntity;
import com.juyo.visa.entities.TApplicantWealthJpEntity;
import com.juyo.visa.entities.TApplicantWorkJpEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TTouristBaseinfoEntity;
import com.juyo.visa.entities.TTouristPassportEntity;
import com.juyo.visa.entities.TTouristVisaEntity;
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
	@Inject
	private OrderJpViewService orderJpViewService;

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
			Integer ostatus = (Integer) record.get("applystatus");
			for (TrialApplicantStatusEnum jpos : TrialApplicantStatusEnum.values()) {
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
		long startTime = System.currentTimeMillis();//获取当前时间

		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		List<Record> list = new ArrayList<>();
		Map<String, Object> result = Maps.newHashMap();
		String orderSqlstr = sqlManager.get("myvisa_visaList_data");
		Sql orderSql = Sqls.create(orderSqlstr);
		Cnd orderJpCnd = Cnd.NEW();
		orderJpCnd.and("ta.userId", "=", loginUser.getId());
		orderSql.setCondition(orderJpCnd);
		List<Record> orderRecord = dbDao.query(orderSql, orderJpCnd, null);

		for (Record record : orderRecord) {
			Integer isSameLinker = (Integer) record.get("isSameLinker");
			Integer orderJpId = (Integer) record.get("id");
			String sqlStr = sqlManager.get("myvisa_japan_visa_list_data_apply");
			Sql applysql = Sqls.create(sqlStr);
			Cnd recordCnd = Cnd.NEW();
			if (Util.eq(isSameLinker, IsYesOrNoEnum.YES.intKey())) {
				recordCnd.and("taoj.orderId", "=", orderJpId);
			} else {
				recordCnd.and("taoj.orderId", "=", orderJpId);
				recordCnd.and("ta.userId", "=", loginUser.getId());
			}
			applysql.setCondition(recordCnd);
			List<Record> query = dbDao.query(applysql, recordCnd, null);
			for (Record apply : query) {
				//查询资料类型（同职业）
				Integer dataType = (Integer) apply.get("dataType");
				for (JobStatusEnum dataTypeEnum : JobStatusEnum.values()) {
					if (!Util.isEmpty(dataType) && dataType.equals(dataTypeEnum.intKey())) {
						apply.put("dataType", dataTypeEnum.value());
					}
				}
			}
			record.put("everybodyInfo", query);
			//订单状态
			Integer visastatus = record.getInt("japanState");
			for (JPOrderStatusEnum visaenum : JPOrderStatusEnum.values()) {
				if (visaenum.intKey() == visastatus) {
					record.put("visastatus", visaenum.value());
				}
			}
			list.add(record);
		}
		//list前后倒置，这样第一个对象为最新的订单
		Collections.reverse(list);
		result.put("visaJapanData", list);
		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
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
		String expressType = "";
		if (!Util.isEmpty(expressEntity)) {
			expressNum = expressEntity.getExpressNum();
			Integer type = expressEntity.getExpressType();
			for (YouKeExpressTypeEnum statusEnum : YouKeExpressTypeEnum.values()) {
				if (!Util.isEmpty(expressNum) && type == statusEnum.intKey()) {
					expressType = statusEnum.value();
				}
			}
		}
		result.put("expressType", expressType);
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

		//回邮信息
		TApplicantOrderJpEntity applyJp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantid));
		TApplicantBackmailJpEntity backmailJpEntity = dbDao.fetch(TApplicantBackmailJpEntity.class,
				Cnd.where("applicantId", "=", applyJp.getId()));
		result.put("backmail", backmailJpEntity);

		//进度隐藏页
		int indexOfBlue = getIndexOfBlue(order, applicantid);
		result.put("indexOfBlue", indexOfBlue);

		result.put("orderid", orderid);
		return result;
	}

	//填写快递单号页
	public Object youkeExpressInfo(Integer applicantId, Integer orderId) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("applicantId", applicantId);
		result.put("orderId", orderId);
		result.put("expressInfo",
				dbDao.fetch(TApplicantExpressEntity.class, Cnd.where("applicantId", "=", applicantId)));
		result.put("expressType", EnumUtil.enum2(YouKeExpressTypeEnum.class));
		return result;
	}

	//保存快递单号
	public Object saveExpressInfo(int expressType, String expressNum, Integer applicantId, Integer orderId,
			HttpSession session) {

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

		if (!Util.isEmpty(orderId)) {
			TOrderEntity order = dbDao.fetch(TOrderEntity.class, Cnd.where("id", "=", orderId));
			if (!Util.isEmpty(order)) {
				Integer orderStatus = order.getStatus();
				int send_address = JPOrderStatusEnum.SEND_ADDRESS.intKey();
				int send_data = JPOrderStatusEnum.SEND_DATA.intKey();
				if (!Util.isEmpty(orderStatus) && orderStatus == send_address) {
					order.setStatus(send_data);
					order.setUpdateTime(nowDate);
					dbDao.update(order);
				}
			}
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
			indexOfBlue = 0;
		} else if (status >= JPOrderStatusEnum.FIRSTTRIAL_ORDER.intKey()
				&& status <= JPOrderStatusEnum.QUALIFIED_ORDER.intKey()) {
			//合格不合格
			indexOfBlue = 1;
		} else if (status == JPOrderStatusEnum.SEND_ADDRESS.intKey()) {
			//已发地址，游客填快递单号
			indexOfBlue = 2;
			//快递是否已寄出
			int count = nutDao.count(TApplicantExpressEntity.class, Cnd.where("applicantId", "=", applicantid));
			if (count > 0) {
				//已寄出
				indexOfBlue = 3;
			}
		} else if (status == JPOrderStatusEnum.SEND_DATA.intKey()) {
			//已发地址，游客填快递单号
			indexOfBlue = 3;
		} else if (status == JPOrderStatusEnum.RECEPTION_RECEIVED.intKey()) {
			//前台已收件
			indexOfBlue = 4;
		} else if (status >= JPOrderStatusEnum.VISA_ORDER.intKey()
				&& status < JPOrderStatusEnum.AFTERMARKET_ORDER.intKey()) {
			indexOfBlue = 4;
			if (status == JPOrderStatusEnum.AUTO_FILL_FORM_ED.intKey()) {
				//预计送签时间
				if (!Util.isEmpty(sendVisaDate)) {
					indexOfBlue = 5;
				}
				//资料已进入使馆
				TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", orderid));
				Date deliveryDate = orderJpEntity.getDeliveryDataTime();
				if (!Util.isEmpty(deliveryDate)) {
					indexOfBlue = 6;
				}
			}
			//签证已返回
			TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
					Cnd.where("applicantId", "=", applicantid));
			TApplicantVisaJpEntity applicantVisaJpEntity = dbDao.fetch(TApplicantVisaJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()));
			if (!Util.isEmpty(applicantVisaJpEntity)) {
				Date visaEntryTime = applicantVisaJpEntity.getVisaEntryTime();
				if (!Util.isEmpty(visaEntryTime)) {
					indexOfBlue = 7;
				}
			}
		} else if (status >= JPOrderStatusEnum.AFTERMARKET_ORDER.intKey()) {
			//到售后就有回邮信息
			indexOfBlue = 8;
			/*//售后，回邮资料
			TApplicantBackmailJpEntity backmailJpEntity = dbDao.fetch(TApplicantBackmailJpEntity.class,
					Cnd.where("applicantId", "=", applicantid));
			if (!Util.isEmpty(backmailJpEntity)) {
				Date backSourceTime = backmailJpEntity.getBackSourceTime();
				if (!Util.isEmpty(backSourceTime)) {
					indexOfBlue = 9;
				}
			}*/
			//如果工作人员点了回邮发送，则进入资料已寄出状态
			TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
					Cnd.where("applicantId", "=", applicantid));
			TApplicantBackmailJpEntity backmailJpEntity = dbDao.fetch(TApplicantBackmailJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()));
			if (!Util.isEmpty(backmailJpEntity)) {
				Date backSourceTime = backmailJpEntity.getBackSourceTime();
				if (!Util.isEmpty(backSourceTime)) {
					indexOfBlue = 9;
				}
			}
		}
		return indexOfBlue;
	}

	public Object copyAllInfoToTuorist(int applyid, String emptyInfo, HttpSession session) {
		copyBaseToTourist(applyid, session);
		copyPassToTourist(applyid, session);
		copyVisaToTourist(applyid, session);
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyid);
		if (!Util.eq(emptyInfo, "YES")) {
			if (!Util.isEmpty(apply.getUserId())) {
				TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
						Cnd.where("userId", "=", apply.getUserId()));
				base.setIsSameInfo(IsYesOrNoEnum.YES.intKey());
				base.setSaveIsPrompted(IsYesOrNoEnum.YES.intKey());
				dbDao.update(base);
			} else {
				TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
						Cnd.where("applicantId", "=", applyid));
				base.setIsSameInfo(IsYesOrNoEnum.YES.intKey());
				base.setSaveIsPrompted(IsYesOrNoEnum.YES.intKey());
				dbDao.update(base);
			}
		}
		return null;
	}

	public Object copyAllInfoToPersonnel(int applyid, HttpSession session) {
		copyBaseToPersonnel(applyid, session);
		copyPassToPersonnel(applyid, session);
		copyVisaToPersonnel(applyid, session);

		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyid);
		if (!Util.isEmpty(apply.getUserId())) {
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("userId", "=", apply.getUserId()));
			base.setIsSameInfo(IsYesOrNoEnum.YES.intKey());
			base.setUpdateIsPrompted(IsYesOrNoEnum.YES.intKey());
			dbDao.update(base);
		} else {
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("applicantId", "=", applyid));
			base.setIsSameInfo(IsYesOrNoEnum.YES.intKey());
			base.setUpdateIsPrompted(IsYesOrNoEnum.YES.intKey());
			dbDao.update(base);
		}
		return null;
	}

	public Object copyBaseToPersonnel(int applyid, HttpSession session) {
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyid);
		if (!Util.isEmpty(apply.getUserId())) {
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("userId", "=", apply.getUserId()));
			if (!Util.isEmpty(base)) {
				String applicantSqlstr = sqlManager.get("copyBaseToPersonnel");
				Sql applicantSql = Sqls.create(applicantSqlstr);
				Cnd applyCnd = Cnd.NEW();
				applyCnd.and("tb.userId", "=", apply.getUserId());
				applyCnd.and("ta.id", "=", applyid);
				applicantSql.setCondition(applyCnd);
				nutDao.execute(applicantSql);

				changeOrderstatus(applyid, "base");
			}
		} else {
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("applicantId", "=", apply.getId()));
			if (!Util.isEmpty(base)) {
				String applicantSqlstr = sqlManager.get("copyBaseToPersonnel");
				Sql applicantSql = Sqls.create(applicantSqlstr);
				Cnd applyCnd = Cnd.NEW();
				applyCnd.and("tb.applicantId", "=", applyid);
				applyCnd.and("ta.id", "=", applyid);
				applicantSql.setCondition(applyCnd);
				nutDao.execute(applicantSql);

				changeOrderstatus(applyid, "base");
			}
		}
		return null;
	}

	public Object copyPassToPersonnel(int applyid, HttpSession session) {
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyid);
		if (!Util.isEmpty(apply.getUserId())) {
			TTouristPassportEntity pass = dbDao.fetch(TTouristPassportEntity.class,
					Cnd.where("userId", "=", apply.getUserId()));
			if (!Util.isEmpty(pass)) {
				String applicantSqlstr = sqlManager.get("copyPassToPersonnel");
				Sql applicantSql = Sqls.create(applicantSqlstr);
				Cnd applyCnd = Cnd.NEW();
				applyCnd.and("tp.userId", "=", apply.getUserId());
				applyCnd.and("ta.applicantId", "=", applyid);
				applicantSql.setCondition(applyCnd);
				nutDao.execute(applicantSql);

				changeOrderstatus(applyid, "pass");
			}
		} else {
			TTouristPassportEntity pass = dbDao.fetch(TTouristPassportEntity.class,
					Cnd.where("applicantId", "=", apply.getId()));
			if (!Util.isEmpty(pass)) {
				String applicantSqlstr = sqlManager.get("copyPassToPersonnel");
				Sql applicantSql = Sqls.create(applicantSqlstr);
				Cnd applyCnd = Cnd.NEW();
				applyCnd.and("tp.applicantId", "=", applyid);
				applyCnd.and("ta.applicantId", "=", applyid);
				applicantSql.setCondition(applyCnd);
				nutDao.execute(applicantSql);

				changeOrderstatus(applyid, "pass");
			}
		}
		return null;
	}

	public Object copyVisaToPersonnel(int applyid, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyid);
		TApplicantOrderJpEntity applyJp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applyid));
		TApplicantWorkJpEntity workJp = dbDao.fetch(TApplicantWorkJpEntity.class,
				Cnd.where("applicantId", "=", applyJp.getId()));
		List<TApplicantWealthJpEntity> wealthList = dbDao.query(TApplicantWealthJpEntity.class,
				Cnd.where("applicantId", "=", applyJp.getId()), null);

		if (!Util.isEmpty(apply.getUserId())) {//有userId时根据userId查询
			TTouristVisaEntity visa = dbDao
					.fetch(TTouristVisaEntity.class, Cnd.where("userId", "=", apply.getUserId()));
			//更新
			UpdateVisaToPersonnel(applyJp, workJp, wealthList, visa, apply, session);

			changeOrderstatus(applyid, "visa");

		} else {//根据申请人id查询
			TTouristVisaEntity visa = dbDao.fetch(TTouristVisaEntity.class,
					Cnd.where("applicantId", "=", apply.getId()));
			//更新
			UpdateVisaToPersonnel(applyJp, workJp, wealthList, visa, apply, session);

			changeOrderstatus(applyid, "visa");
		}
		return null;
	}

	public Object copyBaseToTourist(int applyid, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyid);
		if (!Util.isEmpty(apply.getUserId())) {//有userId时根据userId查询
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("userId", "=", apply.getUserId()));
			if (!Util.isEmpty(base)) {//不为空时更新
				String applicantSqlstr = sqlManager.get("copyBaseToTourist");
				Sql applicantSql = Sqls.create(applicantSqlstr);
				Cnd applyCnd = Cnd.NEW();
				applyCnd.and("ta.id", "=", apply.getId());
				applyCnd.and("tb.userId", "=", apply.getUserId());
				applicantSql.setCondition(applyCnd);
				nutDao.execute(applicantSql);
			} else {//添加
				String applicantSqlstr = sqlManager.get("insertBaseToTourist");
				Sql applicantSql = Sqls.create(applicantSqlstr);
				Cnd applyCnd = Cnd.NEW();
				applyCnd.and("ta.id", "=", apply.getId());
				applicantSql.setCondition(applyCnd);
				nutDao.execute(applicantSql);
			}
		} else {//根据申请人id查询
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("applicantId", "=", apply.getId()));
			if (!Util.isEmpty(base)) {//更新
				String applicantSqlstr = sqlManager.get("copyBaseToTourist");
				Sql applicantSql = Sqls.create(applicantSqlstr);
				Cnd applyCnd = Cnd.NEW();
				applyCnd.and("ta.id", "=", apply.getId());
				applyCnd.and("tb.applicantId", "=", apply.getId());
				applicantSql.setCondition(applyCnd);
				nutDao.execute(applicantSql);
			} else {//添加
				String applicantSqlstr = sqlManager.get("insertBaseToTouristNoUserId");
				Sql applicantSql = Sqls.create(applicantSqlstr);
				Cnd applyCnd = Cnd.NEW();
				applyCnd.and("ta.id", "=", apply.getId());
				applicantSql.setCondition(applyCnd);
				nutDao.execute(applicantSql);
			}
		}
		TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
				Cnd.where("applicantId", "=", apply.getId()));
		if (!Util.isEmpty(base)) {//不为空，说明有游客信息
			if (!Util.isEmpty(base.getUserId())) {//如果userId为空，把申请人的userId给游客,同时更新游客申请人ID，对应为最新的申请人

			} else {//如果为空，需要判断userId有没有被占用
				TTouristBaseinfoEntity uidBase = dbDao.fetch(TTouristBaseinfoEntity.class,
						Cnd.where("userId", "=", apply.getUserId()));
				if (Util.isEmpty(uidBase)) {
					base.setUserId(apply.getUserId());
					dbDao.update(base);
				}
			}
		}

		return null;
	}

	public Object copyPassToTourist(int applyid, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyid);
		TApplicantPassportEntity passport = dbDao.fetch(TApplicantPassportEntity.class,
				Cnd.where("applicantId", "=", apply.getId()));
		if (!Util.isEmpty(apply.getUserId())) {//有userId时根据userId查询
			TTouristPassportEntity base = dbDao.fetch(TTouristPassportEntity.class,
					Cnd.where("userId", "=", apply.getUserId()));
			if (!Util.isEmpty(base)) {//不为空时更新
				String applicantSqlstr = sqlManager.get("copyPassToTourist");
				Sql applicantSql = Sqls.create(applicantSqlstr);
				Cnd applyCnd = Cnd.NEW();
				applyCnd.and("ta.applicantId", "=", apply.getId());
				applyCnd.and("tp.userId", "=", apply.getUserId());
				applicantSql.setCondition(applyCnd);
				nutDao.execute(applicantSql);
			} else {//添加
				String applicantSqlstr = sqlManager.get("insertPassToTourist");
				Sql applicantSql = Sqls.create(applicantSqlstr);
				Cnd applyCnd = Cnd.NEW();
				applyCnd.and("ta.id", "=", apply.getId());
				applicantSql.setCondition(applyCnd);
				nutDao.execute(applicantSql);
			}
		} else {//根据申请人id查询
			TTouristPassportEntity base = dbDao.fetch(TTouristPassportEntity.class,
					Cnd.where("applicantId", "=", apply.getId()));
			if (!Util.isEmpty(base)) {//更新
				String applicantSqlstr = sqlManager.get("copyPassToTourist");
				Sql applicantSql = Sqls.create(applicantSqlstr);
				Cnd applyCnd = Cnd.NEW();
				applyCnd.and("ta.applicantId", "=", apply.getId());
				applyCnd.and("tp.applicantId", "=", apply.getId());
				applicantSql.setCondition(applyCnd);
				nutDao.execute(applicantSql);
			} else {//添加
				String applicantSqlstr = sqlManager.get("insertPassToTouristNoUserId");
				Sql applicantSql = Sqls.create(applicantSqlstr);
				Cnd applyCnd = Cnd.NEW();
				applyCnd.and("ta.id", "=", apply.getId());
				applicantSql.setCondition(applyCnd);
				nutDao.execute(applicantSql);
			}
		}
		TTouristPassportEntity pass = dbDao.fetch(TTouristPassportEntity.class,
				Cnd.where("applicantId", "=", apply.getId()));
		if (!Util.isEmpty(pass)) {//不为空，说明有游客信息
			if (!Util.isEmpty(pass.getUserId())) {//如果userId为空，把申请人的userId给游客,同时更新游客申请人ID，对应为最新的申请人

			} else {//如果为空，需要判断userId有没有被占用
				TTouristPassportEntity uidPass = dbDao.fetch(TTouristPassportEntity.class,
						Cnd.where("userId", "=", apply.getUserId()));
				if (Util.isEmpty(uidPass)) {
					pass.setUserId(apply.getUserId());
					dbDao.update(pass);
				}
			}
		}
		return null;
	}

	public Object copyVisaToTourist(int applyid, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyid);
		TApplicantOrderJpEntity applyJp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applyid));
		TApplicantWorkJpEntity workJp = dbDao.fetch(TApplicantWorkJpEntity.class,
				Cnd.where("applicantId", "=", applyJp.getId()));
		List<TApplicantWealthJpEntity> wealthList = dbDao.query(TApplicantWealthJpEntity.class,
				Cnd.where("applicantId", "=", applyJp.getId()), null);

		if (!Util.isEmpty(apply.getUserId())) {//有userId时根据userId查询
			TTouristVisaEntity visa = dbDao
					.fetch(TTouristVisaEntity.class, Cnd.where("userId", "=", apply.getUserId()));
			//更新或添加
			toUpdateVisa(applyJp, workJp, wealthList, visa, apply);
		} else {//根据申请人id查询
			TTouristVisaEntity visa = dbDao.fetch(TTouristVisaEntity.class,
					Cnd.where("applicantId", "=", apply.getId()));
			//更新或添加
			toUpdateVisa(applyJp, workJp, wealthList, visa, apply);

		}
		TTouristVisaEntity visa = dbDao.fetch(TTouristVisaEntity.class, Cnd.where("applicantId", "=", apply.getId()));
		if (!Util.isEmpty(visa)) {//不为空，说明有游客信息
			if (!Util.isEmpty(visa.getUserId())) {//如果userId为空，把申请人的userId给游客,同时更新游客申请人ID，对应为最新的申请人

			} else {//如果为空，需要判断userId有没有被占用
				TTouristVisaEntity uidVisa = dbDao.fetch(TTouristVisaEntity.class,
						Cnd.where("userId", "=", apply.getUserId()));
				if (Util.isEmpty(uidVisa)) {
					visa.setUserId(apply.getUserId());
					dbDao.update(visa);
				}
			}
		}
		return null;
	}

	public Object toSetUnsame(int applyid, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class, applyid);
		applicantEntity.setIsSameInfo(IsYesOrNoEnum.NO.intKey());
		applicantEntity.setIsPrompted(IsYesOrNoEnum.YES.intKey());
		dbDao.update(applicantEntity);
		TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class, Cnd.where("userId", "=", userId));
		base.setBaseIsCompleted(IsYesOrNoEnum.NO.intKey());
		dbDao.update(base);
		return null;
	}

	public Object updateOrNot(int applyid, String updateOrNot, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
				Cnd.where("userId", "=", loginUser.getId()));
		if (Util.eq(updateOrNot, "YES")) {
			base.setUpdateIsOrNot(IsYesOrNoEnum.YES.intKey());
		} else {
			base.setUpdateIsOrNot(IsYesOrNoEnum.NO.intKey());
		}
		base.setUpdateIsPrompted(IsYesOrNoEnum.YES.intKey());
		dbDao.update(base);
		return null;
	}

	public Object saveIsOrNot(int applyid, String updateOrNot, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
				Cnd.where("userId", "=", loginUser.getId()));
		if (Util.eq(updateOrNot, "YES")) {
			base.setSaveIsOrNot(IsYesOrNoEnum.YES.intKey());
		} else {
			base.setSaveIsOrNot(IsYesOrNoEnum.NO.intKey());
		}
		base.setSaveIsPrompted(IsYesOrNoEnum.YES.intKey());
		dbDao.update(base);
		return null;
	}

	public Object isUpdate(int applyid) {
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyid);
		if (Util.eq(apply.getIsSameInfo(), IsYesOrNoEnum.YES.intKey())) {
			return 1;
		} else {
			return 0;
		}
	}

	public List<TApplicantEntity> getAllApply(HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		List<TApplicantEntity> applyList = dbDao.query(TApplicantEntity.class,
				Cnd.where("userId", "=", loginUser.getId()), null);
		List<TApplicantEntity> lastApplyList = new ArrayList<>();
		List<TOrderEntity> orderList = new ArrayList<>();
		for (TApplicantEntity tApplicantEntity : applyList) {
			TApplicantOrderJpEntity applyJp = dbDao.fetch(TApplicantOrderJpEntity.class,
					Cnd.where("applicantId", "=", tApplicantEntity.getId()));
			TOrderJpEntity orderJp = dbDao.fetch(TOrderJpEntity.class, applyJp.getOrderId().longValue());
			TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderJp.getOrderId().longValue());
			orderList.add(order);
		}
		for (TOrderEntity tOrderEntity : orderList) {
			TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class,
					Cnd.where("orderId", "=", tOrderEntity.getId()));
			List<TApplicantOrderJpEntity> applyJpList = dbDao.query(TApplicantOrderJpEntity.class,
					Cnd.where("orderId", "=", orderJpEntity.getId()), null);
			for (TApplicantOrderJpEntity tApplicantOrderJpEntity : applyJpList) {
				TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, tApplicantOrderJpEntity.getApplicantId()
						.longValue());
				lastApplyList.add(apply);
			}
		}
		return lastApplyList;
	}

	public Object UpdateVisaToPersonnel(TApplicantOrderJpEntity applyJp, TApplicantWorkJpEntity workJp,
			List<TApplicantWealthJpEntity> wealthList, TTouristVisaEntity visa, TApplicantEntity apply,
			HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		//更新工作信息
		if (!Util.isEmpty(visa.getCareerStatus())) {
			orderJpViewService.toUpdateWorkJp(visa.getCareerStatus(), workJp, applyJp, session);
		} else {
			Integer applicantJpId = applyJp.getId();
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
			workJp.setPrepareMaterials(null);
		}
		workJp.setAddress(visa.getAddress());
		workJp.setCareerStatus(visa.getCareerStatus());
		workJp.setName(visa.getName());
		workJp.setTelephone(visa.getTelephone());
		workJp.setUpdateTime(new Date());
		dbDao.update(workJp);
		//更新申请人信息
		apply.setMainId(visa.getMainId());
		apply.setMarryStatus(visa.getMarryStatus());
		apply.setMarryUrl(visa.getMarryUrl());
		dbDao.update(apply);
		//更新日本申请人信息
		applyJp.setIsMainApplicant(visa.getIsMainApplicant());
		applyJp.setMainRelation(visa.getMainRelation());
		applyJp.setRelationRemark(visa.getRelationRemark());
		applyJp.setSameMainWealth(visa.getSameMainWealth());
		dbDao.update(applyJp);
		//更新财产信息
		if (!Util.isEmpty(wealthList)) {
			dbDao.delete(wealthList);
		}
		if (!Util.isEmpty(visa.getDeposit())) {
			TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
			applyWealth.setType("银行存款");
			applyWealth.setDetails(visa.getDeposit());
			applyWealth.setApplicantId(applyJp.getId());
			applyWealth.setOpId(loginUser.getId());
			applyWealth.setCreateTime(new Date());
			applyWealth.setUpdateTime(new Date());
			dbDao.insert(applyWealth);
		}
		if (!Util.isEmpty(visa.getVehicle())) {
			TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
			applyWealth.setType("车产");
			applyWealth.setDetails(visa.getVehicle());
			applyWealth.setApplicantId(applyJp.getId());
			applyWealth.setOpId(loginUser.getId());
			applyWealth.setCreateTime(new Date());
			applyWealth.setUpdateTime(new Date());
			dbDao.insert(applyWealth);
		}
		if (!Util.isEmpty(visa.getHouseProperty())) {
			TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
			applyWealth.setType("房产");
			applyWealth.setDetails(visa.getHouseProperty());
			applyWealth.setApplicantId(applyJp.getId());
			applyWealth.setOpId(loginUser.getId());
			applyWealth.setCreateTime(new Date());
			applyWealth.setUpdateTime(new Date());
			dbDao.insert(applyWealth);
		}
		if (!Util.isEmpty(visa.getFinancial())) {
			TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
			applyWealth.setType("理财");
			applyWealth.setDetails(visa.getFinancial());
			applyWealth.setApplicantId(applyJp.getId());
			applyWealth.setOpId(loginUser.getId());
			applyWealth.setCreateTime(new Date());
			applyWealth.setUpdateTime(new Date());
			dbDao.insert(applyWealth);
		}
		return null;
	}

	public Object toUpdateVisa(TApplicantOrderJpEntity applyJp, TApplicantWorkJpEntity workJp,
			List<TApplicantWealthJpEntity> wealthList, TTouristVisaEntity visa, TApplicantEntity apply) {
		if (Util.isEmpty(visa)) {
			visa = new TTouristVisaEntity();
			visa.setApplicantId(apply.getId());
			if (!Util.isEmpty(apply.getUserId())) {
				visa.setUserId(apply.getUserId());
			}
		}
		visa.setAddress(workJp.getAddress());
		visa.setCareerStatus(workJp.getCareerStatus());
		visa.setIsMainApplicant(applyJp.getIsMainApplicant());
		visa.setMainId(apply.getMainId());
		visa.setMainRelation(applyJp.getMainRelation());
		visa.setMarryStatus(apply.getMarryStatus());
		visa.setMarryUrl(apply.getMarryUrl());
		visa.setApplicantId(apply.getId());
		visa.setName(workJp.getName());
		visa.setRelationRemark(applyJp.getRelationRemark());
		visa.setSameMainWealth(applyJp.getSameMainWealth());
		visa.setTelephone(workJp.getTelephone());
		int depositCount = 0;
		int vehicleCount = 0;
		int houseCount = 0;
		int financialCount = 0;
		for (TApplicantWealthJpEntity wealth : wealthList) {
			if (Util.eq(wealth.getType(), "银行存款")) {
				visa.setDeposit(wealth.getDetails());
				depositCount = 1;
			}
			if (Util.eq(wealth.getType(), "车产")) {
				visa.setVehicle(wealth.getDetails());
				vehicleCount = 1;
			}
			if (Util.eq(wealth.getType(), "房产")) {
				visa.setHouseProperty(wealth.getDetails());
				houseCount = 1;
			}
			if (Util.eq(wealth.getType(), "理财")) {
				visa.setFinancial(wealth.getDetails());
				financialCount = 1;
			}
		}
		if (Util.eq(depositCount, 0)) {
			visa.setDeposit(null);
		}
		if (Util.eq(vehicleCount, 0)) {
			visa.setVehicle(null);
		}
		if (Util.eq(houseCount, 0)) {
			visa.setHouseProperty(null);
		}
		if (Util.eq(financialCount, 0)) {
			visa.setFinancial(null);
		}
		if (Util.isEmpty(visa.getId())) {
			dbDao.insert(visa);
		} else {
			dbDao.update(visa);
		}
		return null;
	}

	public Object changeOrderstatus(int applyid, String infoType) {
		TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applyid));
		TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId()
				.longValue());
		List<TApplicantOrderJpEntity> applyJpList = dbDao.query(TApplicantOrderJpEntity.class,
				Cnd.where("orderId", "=", orderJpEntity.getId()), null);
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderJpEntity.getOrderId().longValue());
		orderJpViewService.changeOrderStatusToFiled(applyJpList, infoType, applicantOrderJpEntity, orderEntity);
		return null;
	}

}
