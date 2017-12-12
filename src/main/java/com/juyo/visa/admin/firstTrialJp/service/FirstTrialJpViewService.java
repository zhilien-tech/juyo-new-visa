/**
 * FirstTrialJpViewService.java
 * com.juyo.visa.admin.firstTrialJp.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.firstTrialJp.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.nutz.dao.Chain;
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
import org.nutz.mvc.annotation.Param;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.firstTrialJp.entity.BackMailInfoEntity;
import com.juyo.visa.admin.firstTrialJp.from.FirstTrialJpEditDataForm;
import com.juyo.visa.admin.firstTrialJp.from.FirstTrialJpListDataForm;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.mail.service.MailService;
import com.juyo.visa.admin.order.service.OrderJpViewService;
import com.juyo.visa.common.enums.CollarAreaEnum;
import com.juyo.visa.common.enums.ExpressTypeEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.JobStatusEnum;
import com.juyo.visa.common.enums.MainBackMailSourceTypeEnum;
import com.juyo.visa.common.enums.MainBackMailTypeEnum;
import com.juyo.visa.common.enums.MainSalePayTypeEnum;
import com.juyo.visa.common.enums.MainSaleTripTypeEnum;
import com.juyo.visa.common.enums.MainSaleUrgentEnum;
import com.juyo.visa.common.enums.MainSaleUrgentTimeEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.common.enums.PrepareMaterialsEnum_JP;
import com.juyo.visa.common.enums.TrialApplicantStatusEnum;
import com.juyo.visa.common.enums.UserLoginEnum;
import com.juyo.visa.common.enums.VisaDataTypeEnum;
import com.juyo.visa.entities.TApplicantBackmailJpEntity;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TApplicantUnqualifiedEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TOrderBackmailEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderLogsEntity;
import com.juyo.visa.entities.TOrderRecipientEntity;
import com.juyo.visa.entities.TReceiveaddressEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TApplicantBackmailJpForm;
import com.juyo.visa.forms.TApplicantUnqualifiedForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.redis.RedisDao;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;
import com.we.business.sms.SMSService;
import com.we.business.sms.impl.HuaxinSMSServiceImpl;

/**
 * 日本订单初审Service
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年11月9日 	 
 */
@IocBean
public class FirstTrialJpViewService extends BaseService<TOrderEntity> {

	@Inject
	private MailService mailService;

	@Inject
	private OrderJpViewService orderJpViewService;

	@Inject
	private RedisDao redisDao;

	private final static String MUBAN_DOCX_URL = "http://oyu1xyxxk.bkt.clouddn.com/a40f95f1-c87f-401a-be75-25f0d42f9f72.docx";
	private final static String FILE_NAME = "初审资料填写模板.docx";
	private final static String SMS_SIGNATURE = "【优悦签】";
	private final static String VISA_COUNTRY = "日本签证";

	/**
	 * 打开初审列表页
	 */
	public Object toList() {

		//检索下拉
		int TRIALORDER = JPOrderStatusEnum.FIRSTTRIAL_ORDER.intKey();

		Map<String, Object> result = Maps.newHashMap();
		//Map<String, String> searchStatus = EnumUtil.enum2(FristTrialSearchStatusEnum_JP.class);
		Map<String, String> orderStatus = EnumUtil.enum2(JPOrderStatusEnum.class);

		Iterator<Map.Entry<String, String>> status = orderStatus.entrySet().iterator();
		while (status.hasNext()) {
			Map.Entry<String, String> entry = status.next();
			String keyStr = entry.getKey();
			int key = Integer.valueOf(keyStr);
			if (key < TRIALORDER)
				status.remove();
		}

		result.put("searchStatus", orderStatus);
		return result;
	}

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
			Integer orderjpId = (Integer) record.get("orderjpId");
			String sqlStr = sqlManager.get("firstTrialJp_list_data_applicant");
			Sql applysql = Sqls.create(sqlStr);
			List<Record> records = dbDao.query(applysql, Cnd.where("taoj.orderId", "=", orderjpId), null);
			records = editApplicantsInfo(records);
			record.put("everybodyInfo", records);

			String orderStatus = record.getString("orderstatus");
			for (JPOrderStatusEnum statusEnum : JPOrderStatusEnum.values()) {
				if (!Util.isEmpty(orderStatus) && orderStatus.equals(String.valueOf(statusEnum.intKey()))) {
					record.set("orderstatus", statusEnum.value());
				}
			}

		}
		result.put("trialJapanData", list);
		result.put("pageTotal", pager.getPageCount());
		result.put("pageListCount", list.size());

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
	public Object trialDetail(Integer orderid, Integer orderjpid) {
		Map<String, Object> result = Maps.newHashMap();
		//日本订单数据
		TOrderJpEntity jporderinfo = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
		result.put("jporderinfo", jporderinfo);
		//订单id
		result.put("orderid", orderid);
		//t_order_jp id
		result.put("orderjpid", orderjpid);
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
		//资料类型
		result.put("mainBackMailSourceTypeEnum", EnumUtil.enum2(MainBackMailSourceTypeEnum.class));
		//回邮方式
		result.put("mainBackMailTypeEnum", EnumUtil.enum2(MainBackMailTypeEnum.class));
		//回邮信息
		List<TOrderBackmailEntity> backinfo = dbDao.query(TOrderBackmailEntity.class,
				Cnd.where("orderId", "=", orderid), null);
		result.put("backinfo", backinfo);

		return result;
	}

	/**
	 * 日本初审详情页数据
	 * <p>
	 * @param orderid
	 * @return 
	 */
	public Object getJpTrialDetailData(Integer orderid, Integer orderjpid) {
		Map<String, Object> result = Maps.newHashMap();
		//订单信息
		String sqlstr = sqlManager.get("firstTrialJp_order_info_byid");
		Sql sql = Sqls.create(sqlstr);
		sql.setParam("orderjpid", orderjpid);
		Record orderinfo = dbDao.fetch(sql);
		String orderStatus = orderinfo.getString("status");
		for (JPOrderStatusEnum statusEnum : JPOrderStatusEnum.values()) {
			if (!Util.isEmpty(orderStatus) && orderStatus.equals(String.valueOf(statusEnum.intKey()))) {
				orderinfo.set("status", statusEnum.value());
			}
		}
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
		List<Record> applyinfo = getApplicantByOrderid(orderjpid);
		result.put("applyinfo", applyinfo);
		//回邮信息
		List<TOrderBackmailEntity> backinfo = dbDao.query(TOrderBackmailEntity.class, Cnd
				.where("orderId", "=", orderid).orderBy("createTime", "DESC"), null);
		result.put("backinfo", backinfo);

		return result;
	}

	//判断订单下申请人是否合格
	public Boolean isQualified(Integer orderjpid) {
		List<Record> applicants = getApplicantByOrderid(orderjpid);
		boolean isQualified = true;
		if (!Util.isEmpty(applicants)) {
			for (Record record : applicants) {
				String status = (String) record.get("applicantstatus");
				if (!Util.eq("合格", status)) {
					isQualified = false;
				}
			}
		}
		return isQualified;
	}

	//根据订单id 获得申请人
	public List<Record> getApplicantByOrderid(Integer orderjpid) {
		String applysqlstr = sqlManager.get("firstTrialJp_orderDetail_applicant_by_orderid");
		Sql applysql = Sqls.create(applysqlstr);
		applysql.setParam("orderjpid", orderjpid);
		List<Record> applyinfo = dbDao.query(applysql, null, null);
		applyinfo = editApplicantsInfo(applyinfo);
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
		}

		return applyinfo;
	}

	//快递 发邮件
	public Object express(Integer orderid, Integer orderjpid, HttpSession session) {

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

		/*//订单主申请人
		String sqlStr = sqlManager.get("firstTrialJp_list_data_applicant");
		Sql applysql = Sqls.create(sqlStr);
		List<Record> records = dbDao.query(applysql,
				Cnd.where("taoj.orderId", "=", orderjpid).and("taoj.isMainApplicant", "=", IsYesOrNoEnum.YES.intKey()),
				null);*/

		//业务需求，更改为 销售分享的申请人 如果为统一联系人只展示一个， 否则展示单独分享的
		String sqlStr = sqlManager.get("firstTrialJp_share_sms_applicant");
		int yes = IsYesOrNoEnum.YES.intKey();
		Sql applysql = Sqls.create(sqlStr);
		List<Record> records = dbDao.query(applysql,
				Cnd.where("taoj.orderId", "=", orderjpid).and("taoj.isShareSms", "=", yes), null);

		List<Record> sameRecord = new ArrayList<Record>();
		for (Record record : records) {
			String isSameMan = record.getString("isSameLinker");
			if (Util.eq(isSameMan, yes)) {
				//统一联系人
				sameRecord.add(record);
				records = sameRecord;
			}
		}

		records = editApplicantsInfo(records);

		result.put("applicant", records);
		//订单id
		result.put("orderid", orderid);
		//t_order_jp id
		result.put("orderjpid", orderjpid);

		return result;
	}

	//获取订单主申请人
	public Map<String, Object> getmainApplicantByOrderid(int orderjpid) {
		Map<String, Object> result = Maps.newHashMap();
		String sqlStr = sqlManager.get("firstTrialJp_list_data_applicant");
		Sql applysql = Sqls.create(sqlStr);
		List<Record> records = dbDao.query(applysql,
				Cnd.where("taoj.orderId", "=", orderjpid).and("taoj.isMainApplicant", "=", IsYesOrNoEnum.YES.intKey()),
				null);
		records = editApplicantsInfo(records);
		result.put("applicant", records);
		return result;
	}

	//获得订单分享消息人列表
	public Map<String, Object> getShareApplicantByOrderid(Integer orderjpid) {
		Map<String, Object> result = Maps.newHashMap();
		String sqlStr = sqlManager.get("firstTrialJp_share_sms_applicant");
		int yes = IsYesOrNoEnum.YES.intKey();
		Sql applysql = Sqls.create(sqlStr);
		List<Record> records = dbDao.query(applysql,
				Cnd.where("taoj.orderId", "=", orderjpid).and("taoj.isShareSms", "=", yes), null);

		List<Record> sameRecord = new ArrayList<Record>();
		for (Record record : records) {
			String isSameMan = record.getString("isSameLinker");
			if (Util.eq(isSameMan, yes)) {
				//统一联系人
				sameRecord.add(record);
				records = sameRecord;
			}
		}
		records = editApplicantsInfo(records);
		result.put("applicant", records);
		return result;
	}

	//获得订单所有的申请人
	public Map<String, Object> getAllApplicantByOrderid(int orderjpid) {
		Map<String, Object> result = Maps.newHashMap();
		String sqlStr = sqlManager.get("firstTrialJp_share_sms_applicant");
		Sql applysql = Sqls.create(sqlStr);
		List<Record> records = dbDao.query(applysql, Cnd.where("taoj.orderId", "=", orderjpid), null);
		records = editApplicantsInfo(records);
		result.put("applicant", records);
		return result;
	}

	//获取分享消息的统一联系人
	public Map<String, Object> getSameApplicantByOrderid(int orderjpid) {
		Map<String, Object> result = Maps.newHashMap();
		String sqlStr = sqlManager.get("firstTrialJp_share_sms_applicant");
		int yes = IsYesOrNoEnum.YES.intKey();
		Sql applysql = Sqls.create(sqlStr);
		List<Record> records = dbDao.query(
				applysql,
				Cnd.where("taoj.orderId", "=", orderjpid).and("taoj.isShareSms", "=", yes)
						.and("taoj.isSameLinker", "=", yes), null);
		records = editApplicantsInfo(records);
		result.put("applicant", records);
		return result;
	}

	//获取单独分享的申请人
	public Map<String, Object> getAloneApplicantByOrderid(int orderjpid) {
		Map<String, Object> result = Maps.newHashMap();
		String sqlStr = sqlManager.get("firstTrialJp_share_sms_applicant");
		int yes = IsYesOrNoEnum.YES.intKey();
		Sql applysql = Sqls.create(sqlStr);
		List<Record> records = dbDao.query(applysql,
				Cnd.where("taoj.orderId", "=", orderjpid).and("taoj.isShareSms", "=", yes), null);
		records = editApplicantsInfo(records);
		result.put("applicant", records);
		return result;
	}

	//获取申请人信息
	public Object basicInfo(int applyid) {
		TApplicantEntity appllicant = dbDao.fetch(TApplicantEntity.class, Cnd.where("id", "=", applyid));
		return appllicant;
	}

	//合格申请人
	public Object qualified(Integer applyid, Integer orderid, Integer orderjpid, HttpSession session) {
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
		Boolean qualified = isQualified(orderjpid);
		Date nowDate = DateUtil.nowDate();
		if (!qualified) {
			//只要一个不合格，订单状态为初审
			int firsttrialstatus = JPOrderStatusEnum.FIRSTTRIAL_ORDER.intKey();
			dbDao.update(TOrderEntity.class, Chain.make("status", firsttrialstatus), Cnd.where("id", "=", orderid));
			dbDao.update(TOrderEntity.class, Chain.make("updateTime", nowDate), Cnd.where("id", "=", orderid));
		} else {
			//全合格，订单状态为合格
			int qualifiedstatus = JPOrderStatusEnum.QUALIFIED_ORDER.intKey();
			orderJpViewService.insertLogs(orderid, qualifiedstatus, session);
			dbDao.update(TOrderEntity.class, Chain.make("status", qualifiedstatus), Cnd.where("id", "=", orderid));
			dbDao.update(TOrderEntity.class, Chain.make("updateTime", nowDate), Cnd.where("id", "=", orderid));
		}

		try {
			//发送合格消息
			sendApplicantVerifySMS(applyid, orderid, "applicant_qualified_sms.txt");
			//发送合格邮件
			sendApplicantVerifyEmail(applyid, orderid, "applicant_qualified_mail.html");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return update > 0;
	}

	//获取申请人不合格信息
	public Object unqualified(Integer applyid, Integer orderid) {
		Map<String, Object> result = Maps.newHashMap();
		int count = nutDao.count(TApplicantUnqualifiedEntity.class, Cnd.where("applicantId", "=", applyid));
		TApplicantUnqualifiedEntity unqualifiedInfo = dbDao.fetch(TApplicantUnqualifiedEntity.class,
				Cnd.where("applicantId", "=", applyid));
		result.put("applyid", applyid);
		result.put("orderid", orderid);
		result.put("hasUnqInfo", count > 0);
		result.put("unqualifiedInfo", unqualifiedInfo);
		return result;
	}

	//保存不合格信息
	public Object saveUnqualified(TApplicantUnqualifiedForm form, HttpSession session) {
		int YES = IsYesOrNoEnum.YES.intKey();
		int NO = IsYesOrNoEnum.NO.intKey();
		Integer applicantId = form.getApplicantId();
		Integer orderid = form.getOrderid();
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

		//只要有一个不合格
		if (isB == 1 || isV == 1 || isP == 1) {
			//更改申请人状态为不合格
			dbDao.update(TApplicantEntity.class, Chain.make("status", TrialApplicantStatusEnum.unqualified.intKey()),
					Cnd.where("id", "=", applicantId));
			//更改订单状态为初审
			int firsttrialstatus = JPOrderStatusEnum.FIRSTTRIAL_ORDER.intKey();
			Date nowDate = DateUtil.nowDate();
			dbDao.update(TOrderEntity.class, Chain.make("status", firsttrialstatus), Cnd.where("id", "=", orderid));
			dbDao.update(TOrderEntity.class, Chain.make("updateTime", nowDate), Cnd.where("id", "=", orderid));
			//记录日志
			orderJpViewService.insertLogs(orderid, firsttrialstatus, session);
		}

		try {
			//发送不合格消息
			sendApplicantVerifySMS(applicantId, orderid, "applicant_unqualified_sms.txt");
			//发送不合格邮件
			sendApplicantVerifyEmail(applicantId, orderid, "applicant_unqualified_mail.html");
		} catch (IOException e) {
			e.printStackTrace();
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

		if (userType == UserLoginEnum.PERSONNEL.intKey()) {
			//工作人员
			cnd.and("userId", "=", userId);
		} else {
			//其他
			cnd.and("comId", "=", comId);
		}
		cnd.limit(0, 5);

		List<TReceiveaddressEntity> query = dbDao.query(TReceiveaddressEntity.class, cnd, null);
		List<TReceiveaddressEntity> newList = new ArrayList<TReceiveaddressEntity>();
		if (query.size() > 5) {
			for (int i = 0; i < query.size(); i++) {
				if (i < 5) {
					newList.add(query.get(i));
				}
			}
			return newList;
		} else {
			return query;
		}

	}

	//根据id获取收件信息
	public Object getRAddressById(String addressId) {
		return dbDao.fetch(TReceiveaddressEntity.class, Cnd.where("id", "=", addressId));
	}

	/**
	 * 保存快递信息，并发送邮件
	 */
	public Object saveExpressInfo(Integer orderid, Integer orderjpid, Integer expresstype, String receiver,
			String mobile, String expressAddress, HttpSession session) {
		//获取当前用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		TOrderRecipientEntity orderReceive = dbDao.fetch(TOrderRecipientEntity.class,
				Cnd.where("orderId", "=", orderid));
		if (!Util.isEmpty(orderReceive)) {
			//更新
			orderReceive.setOrderId(orderid);
			orderReceive.setExpressType(expresstype);
			orderReceive.setReceiver(receiver);
			orderReceive.setTelephone(mobile);
			orderReceive.setExpressAddress(expressAddress);
			orderReceive.setOpId(userId);
			orderReceive.setUpdateTime(DateUtil.nowDate());
			nutDao.update(orderReceive);
		} else {
			//添加
			TOrderRecipientEntity orderReceiveAdd = new TOrderRecipientEntity();
			orderReceiveAdd.setOrderId(orderid);
			orderReceiveAdd.setExpressType(expresstype);
			orderReceiveAdd.setReceiver(receiver);
			orderReceiveAdd.setTelephone(mobile);
			orderReceiveAdd.setExpressAddress(expressAddress);
			//orderReceiveAdd.setReceiveAddressId(receiveAddressId);
			orderReceiveAdd.setOpId(userId);
			orderReceiveAdd.setUpdateTime(DateUtil.nowDate());
			orderReceiveAdd.setCreateTime(DateUtil.nowDate());
			dbDao.insert(orderReceiveAdd);
		}

		//改变订单状态 由初审到发地址
		Date nowDate = DateUtil.nowDate();
		int receptionStatus = JPOrderStatusEnum.SEND_ADDRESS.intKey();
		dbDao.update(TOrderEntity.class, Chain.make("status", receptionStatus), Cnd.where("id", "=", orderid));
		dbDao.update(TOrderEntity.class, Chain.make("updateTime", nowDate), Cnd.where("id", "=", orderid));

		//发送短信、邮件
		try {
			sendMail(orderid, orderjpid);
			sendMessage(orderid, orderjpid);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//添加日志记录  订单已发地址
		int send_address = JPOrderStatusEnum.SEND_ADDRESS.intKey();
		orderJpViewService.insertLogs(orderid, send_address, session);

		return "SUCCESS";
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
		Integer orderjpid = editDataForm.getOrderid();
		//订单信息
		TOrderJpEntity oj = dbDao.fetch(TOrderJpEntity.class, Long.valueOf(orderjpid));
		Integer orderId = oj.getOrderId();
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, Long.valueOf(orderId));
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
		TOrderJpEntity jporder = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
		jporder.setVisaType(editDataForm.getVisatype());
		jporder.setVisaCounty(editDataForm.getVisacounty());
		jporder.setIsVisit(editDataForm.getIsvisit());
		jporder.setThreeCounty(editDataForm.getThreecounty());
		dbDao.update(jporder);

		//回邮信息
		List<TOrderBackmailEntity> backMailInfos = editDataForm.getBackMailInfos();
		String editBackMailInfos = editBackMailInfos(backMailInfos, orderId);

		return editBackMailInfos;
	}

	//订单收件人信息
	public Object getReceiverByOrderid(int orderid) {
		String sqlRec = sqlManager.get("firstTrialJp_receive_address_by_orderid");
		Sql sql = Sqls.create(sqlRec);
		sql.setParam("orderid", orderid);
		Record orderReceive = dbDao.fetch(sql);
		return orderReceive;
	}

	//添加回邮信息
	public String editBackMailInfos(List<TOrderBackmailEntity> backMailInfos, Integer orderid) {

		List<TOrderBackmailEntity> beforeBackMails = dbDao.query(TOrderBackmailEntity.class,
				Cnd.where("orderId", "=", orderid), null);

		List<TOrderBackmailEntity> updateBackMails = new ArrayList<TOrderBackmailEntity>();

		for (TOrderBackmailEntity backMailInfo : backMailInfos) {
			Date nowDate = DateUtil.nowDate();
			Integer obmId = backMailInfo.getId();
			if (Util.isEmpty(obmId)) {
				backMailInfo.setCreateTime(nowDate);
			}
			backMailInfo.setOrderId(orderid);
			backMailInfo.setUpdateTime(nowDate);
			updateBackMails.add(backMailInfo);
		}
		dbDao.updateRelations(beforeBackMails, updateBackMails);

		return "更新回邮信息";
	}

	//发送邮件信息
	public Object sendMail(int orderid, int orderjpid) throws IOException {
		List<String> readLines = IOUtils
				.readLines(getClass().getClassLoader().getResourceAsStream("express_mail.html"));
		StringBuilder tmp = new StringBuilder();
		for (String line : readLines) {
			tmp.append(line);
		}

		//查询订单号
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderid);
		String orderNum = order.getOrderNum();

		//订单收件人信息
		Record orderReceive = (Record) getReceiverByOrderid(orderid);
		String expressType = orderReceive.getString("expressType");
		String receiver = orderReceive.getString("receiver");
		String mobile = orderReceive.getString("telephone");
		String address = orderReceive.getString("expressAddress");

		//邮件分享
		String result = "";
		//统一联系人
		Map<String, Object> map = getSameApplicantByOrderid(orderjpid);
		List<Record> sameMans = (List<Record>) map.get("applicant");
		if (sameMans.size() > 0) {
			//有统一联系人,统一分享
			String toEmail = "";
			String emailText = tmp.toString();
			for (Record man : sameMans) {
				String name = man.getString("applicantname");
				String sex = man.getString("sex");
				if (Util.eq("男", sex)) {
					sex = "先生";
				} else {
					sex = "女士";
				}
				String data = man.getString("data");
				toEmail = man.getString("email");

				emailText = emailText.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum)
						.replace("${receiver}", receiver).replace("${mobile}", mobile).replace("${address}", address)
						.replace("${URL}", MUBAN_DOCX_URL).replace("${fileName}", FILE_NAME);
			}
			//获取订单下所有的申请人
			String applicanthtml = "";
			Map<String, Object> allManMap = getAllApplicantByOrderid(orderjpid);
			List<Record> allMan = (List<Record>) allManMap.get("applicant");
			for (Record man : allMan) {
				String name = man.getString("applicantname");
				String nameHtml = "<div style=''>&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;	<b>" + name
						+ "</b>：</div>";
				String data = man.getString("data");
				String[] split = data.split("、");
				if (split.length > 1) {
					for (int i = 0; i < split.length; i++) {
						nameHtml += "<div>&nbsp;&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp;	&nbsp;&nbsp; "
								+ (i + 1) + "." + split[i] + "</div>";
					}
				}
				applicanthtml += nameHtml;
			}
			emailText = emailText.replace("${applicantInfos}", applicanthtml);
			result = mailService.send(toEmail, emailText, "邮寄初审资料", MailService.Type.HTML);
		} else {
			//单独分享
			Map<String, Object> aloneMans = getAloneApplicantByOrderid(orderjpid);
			List<Record> applicants = (List<Record>) aloneMans.get("applicant");
			for (Record record : applicants) {
				String name = record.getString("applicantname");
				String sex = record.getString("sex");
				if (Util.eq("男", sex)) {
					sex = "先生";
				} else {
					sex = "女士";
				}
				String data = record.getString("data");
				String toEmail = record.getString("email");

				String emailText = tmp.toString();
				emailText = emailText.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum)
						.replace("${receiver}", receiver).replace("${mobile}", mobile).replace("${address}", address)
						.replace("${URL}", MUBAN_DOCX_URL).replace("${fileName}", FILE_NAME);

				String dataHtml = "<div style=''>&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;	<b>" + name
						+ "</b>：</div>";
				String[] split = data.split("、");
				if (split.length > 1) {
					for (int i = 0; i < split.length; i++) {
						dataHtml += "<div>&nbsp;&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp;	&nbsp;&nbsp; "
								+ (i + 1) + "." + split[i] + "</div>";
					}
				}

				emailText = emailText.replace("${applicantInfos}", dataHtml);

				result = mailService.send(toEmail, emailText, "邮寄初审资料", MailService.Type.HTML);
			}
		}

		/*Map<String, Object> map = getmainApplicantByOrderid(orderjpid);
		List<Record> applicants = (List<Record>) map.get("applicant");
		String result = "";
		for (Record record : applicants) {
			String name = record.getString("applicantname");
			String sex = record.getString("sex");
			String data = record.getString("data");
			String toEmail = record.getString("email");

			String emailText = tmp.toString();
			emailText = emailText.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum)
					.replace("${data}", data).replace("${receiver}", receiver).replace("${mobile}", mobile)
					.replace("${address}", address).replace("${URL}", MUBAN_DOCX_URL).replace("${fileName}", FILE_NAME);

			result = mailService.send(toEmail, emailText, "邮寄初审资料", MailService.Type.HTML);
		}*/

		return result;
	}

	//发送手机信息
	public Object sendMessage(int orderid, int orderjpid) throws IOException {
		List<String> readLines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream("express_sms.txt"));
		StringBuilder tmp = new StringBuilder();
		for (String line : readLines) {
			tmp.append(line);
		}

		//查询订单号
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderid);
		String orderNum = order.getOrderNum();

		//订单收件人信息
		Record orderReceive = (Record) getReceiverByOrderid(orderid);
		String expressType = orderReceive.getString("expressType");
		String receiver = orderReceive.getString("receiver");
		String mobile = orderReceive.getString("telephone");
		String address = orderReceive.getString("expressAddress");

		//之前业务，只给主申请人发
		/*Map<String, Object> map = getmainApplicantByOrderid(orderjpid);
		List<Record> applicants = (List<Record>) map.get("applicant");
		String result = "";
		for (Record record : applicants) {
			String name = record.getString("applicantname");
			String sex = record.getString("sex");
			String data = record.getString("data");
			String telephone = record.getString("telephone");

			String smsContent = tmp.toString();
			smsContent = smsContent.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum)
					.replace("${data}", data).replace("${receiver}", receiver).replace("${mobile}", mobile)
					.replace("${address}", address);




			result = sendSMS(telephone, smsContent);

		}*/

		//统一联系人只发自己， 单独分享发全部
		String result = "";
		//统一联系人
		Map<String, Object> map = getSameApplicantByOrderid(orderjpid);
		List<Record> sameMans = (List<Record>) map.get("applicant");
		String smsContent = tmp.toString();
		String telephone = "";
		if (sameMans.size() > 0) {
			//统一联系人
			for (Record man : sameMans) {
				String name = man.getString("applicantname");
				String sex = man.getString("sex");
				if (Util.eq("男", sex)) {
					sex = "先生";
				} else {
					sex = "女士";
				}
				telephone = man.getString("telephone");
				smsContent = smsContent.replace("${name}", name).replace("${sex}", sex)
						.replace("${ordernum}", orderNum).replace("${visaCountry}", VISA_COUNTRY)
						.replace("${receiver}", receiver).replace("${mobile}", mobile).replace("${address}", address);

			}
			result = sendSMS(telephone, smsContent);

		} else {
			//单独分享的
			Map<String, Object> aloneMap = getAloneApplicantByOrderid(orderjpid);
			List<Record> aloneApplicants = (List<Record>) aloneMap.get("applicant");
			for (Record record : aloneApplicants) {
				String name = record.getString("applicantname");
				String sex = record.getString("sex");
				if (Util.eq("男", sex)) {
					sex = "先生";
				} else {
					sex = "女士";
				}
				String singleTelephone = record.getString("telephone");

				String content = tmp.toString();
				content = content.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum)
						.replace("${visaCountry}", VISA_COUNTRY).replace("${receiver}", receiver)
						.replace("${mobile}", mobile).replace("${address}", address);

				result = sendSMS(singleTelephone, content);
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

	//添加日志
	public String addLogs(Integer orderId, Integer opId, Integer orderStatus) {
		TOrderLogsEntity orderLogs = new TOrderLogsEntity();
		Date nowDate = DateUtil.nowDate();
		orderLogs.setOpId(opId);
		orderLogs.setOrderId(orderId);
		orderLogs.setOrderStatus(orderStatus);
		orderLogs.setCreateTime(nowDate);
		orderLogs.setUpdateTime(nowDate);
		TOrderLogsEntity log = dbDao.insert(orderLogs);
		if (!Util.isEmpty(log)) {
			return "LOGS IS SUCCESS";
		} else {
			return "LOGS IS FAILED";
		}

	}

	//修改申请人信息
	public List<Record> editApplicantsInfo(List<Record> records) {
		for (Record applicant : records) {
			Integer status = (Integer) applicant.get("applicantStatus");
			for (TrialApplicantStatusEnum statusEnum : TrialApplicantStatusEnum.values()) {
				if (!Util.isEmpty(status) && status.equals(statusEnum.intKey())) {
					applicant.put("applicantStatus", statusEnum.value());
				}
			}
			Integer jobType = (Integer) applicant.get("dataType");
			for (JobStatusEnum statusEnum : JobStatusEnum.values()) {
				if (!Util.isEmpty(jobType) && jobType.equals(statusEnum.intKey())) {
					applicant.put("dataType", statusEnum.value());
				}
			}
			String dataMaterial = "";
			String prepareMaterials = (String) applicant.get("DATA");
			if (!Util.isEmpty(prepareMaterials)) {
				String[] split = prepareMaterials.split(",");
				for (String s : split) {
					Integer material = Integer.valueOf(s);
					for (PrepareMaterialsEnum_JP statusEnum : PrepareMaterialsEnum_JP.values()) {
						if (!Util.isEmpty(s) && material.equals(statusEnum.intKey())) {
							dataMaterial += statusEnum.value() + "、";
						}
					}
				}
			}
			if (!Util.isEmpty(dataMaterial)) {
				dataMaterial = dataMaterial.substring(0, dataMaterial.length() - 1);
			}
			applicant.put("DATA", dataMaterial);
		}
		return records;
	}

	//回邮信息
	public Object backMailInfo(@Param("applicantId") Integer applicantId) {
		Map<String, Object> result = Maps.newHashMap();
		//资料类型
		result.put("mainSourceTypeEnum", EnumUtil.enum2(MainBackMailSourceTypeEnum.class));
		//回邮方式
		result.put("mainBackMailTypeEnum", EnumUtil.enum2(MainBackMailTypeEnum.class));
		//申请人id
		result.put("applicantId", applicantId);
		return result;
	}

	//获取回邮信息
	public Object getBackMailInfo(Integer applicantId, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();
		TApplicantOrderJpEntity taoj = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantId));

		Map<String, Object> result = Maps.newHashMap();
		String sqlStr = sqlManager.get("backmail_info_by_applicantId");
		Sql sql = Sqls.create(sqlStr);
		sql.setParam("applicantId", applicantId);
		Record backmailinfo = dbDao.fetch(sql);
		if (!Util.isEmpty(backmailinfo)) {
			result.put("backmailinfo", backmailinfo);
		} else {
			BackMailInfoEntity backmail = new BackMailInfoEntity();
			Date nowDate = DateUtil.nowDate();
			backmail.setSource(MainBackMailSourceTypeEnum.KUAIDI.intKey());
			backmail.setExpresstype(MainBackMailTypeEnum.KUAIDI.intKey());
			backmail.setCreatetime(nowDate);
			backmail.setUpdatetime(nowDate);
			backmail.setOpid(userid);
			backmail.setApplicantid(applicantId);
			backmail.setApplicantjpid(taoj.getId());
			result.put("backmailinfo", backmail);
		}

		return result;
	}

	//保存回邮信息
	public Object saveBackMailInfo(TApplicantBackmailJpForm form, HttpSession session) {
		Integer backmailId = form.getId();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();

		List<TApplicantBackmailJpEntity> before = dbDao.query(TApplicantBackmailJpEntity.class,
				Cnd.where("id", "=", backmailId), null);
		List<TApplicantBackmailJpEntity> after = new ArrayList<TApplicantBackmailJpEntity>();

		TApplicantBackmailJpEntity backmail = new TApplicantBackmailJpEntity();
		backmail.setId(form.getId());
		backmail.setExpressType(form.getExpressType());
		backmail.setExpressNum(form.getExpressNum());
		backmail.setTeamName(form.getTeamName());
		backmail.setSource(form.getSource());
		backmail.setLinkman(form.getLinkman());
		backmail.setTelephone(form.getTelephone());
		backmail.setExpressAddress(form.getExpressAddress());
		backmail.setInvoiceContent(form.getInvoiceContent());
		backmail.setApplicantId(form.getApplicantJPId());
		backmail.setInvoiceHead(form.getInvoiceHead());
		backmail.setTaxNum(form.getTaxNum());
		backmail.setRemark(form.getRemark());
		backmail.setUpdateTime(DateUtil.nowDate());
		backmail.setOpId(userid);
		after.add(backmail);

		dbDao.updateRelations(before, after);

		return "BackMail Success";
	}

	//发送合格短信
	public Object sendApplicantVerifySMS(Integer applyid, Integer orderid, String smsTemplate) throws IOException {
		List<String> readLines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream(smsTemplate));
		StringBuilder tmp = new StringBuilder();
		for (String line : readLines) {
			tmp.append(line);
		}
		TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, applyid.longValue());
		String name = applicant.getFirstName() + applicant.getLastName();
		String sex = applicant.getSex();
		String telephone = applicant.getTelephone();
		if (Util.eq("男", sex)) {
			sex = "先生";
		} else {
			sex = "女士";
		}
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderid.longValue());
		String orderNum = order.getOrderNum();
		String smsContent = tmp.toString();
		smsContent = smsContent.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum);
		String result = sendSMS(telephone, smsContent);
		return result;

	}

	//合格/不合格 发送审核结果邮件
	public Object sendApplicantVerifyEmail(Integer applyid, Integer orderid, String mailTemplate) throws IOException {
		List<String> readLines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream(mailTemplate));
		StringBuilder tmp = new StringBuilder();
		for (String line : readLines) {
			tmp.append(line);
		}
		String emailText = tmp.toString();
		String result = "";
		//查询订单号
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderid.longValue());
		String orderNum = order.getOrderNum();
		//申请人
		TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, applyid.longValue());
		String name = applicant.getFirstName() + applicant.getLastName();
		String sex = applicant.getSex();
		String toEmail = applicant.getEmail();
		if (Util.eq("男", sex)) {
			sex = "先生";
		} else {
			sex = "女士";
		}

		emailText = emailText.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum);
		result = mailService.send(toEmail, emailText, "邮寄初审资料", MailService.Type.HTML);

		return result;
	}

}
