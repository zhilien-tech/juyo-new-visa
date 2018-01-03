/**
 * ReceptionJpViewService.java
 * com.juyo.visa.admin.receptionJp.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.receptionJp.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
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
import com.juyo.visa.admin.firstTrialJp.service.FirstTrialJpViewService;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.order.service.OrderJpViewService;
import com.juyo.visa.admin.receptionJp.form.ReceptionJpForm;
import com.juyo.visa.admin.visajp.form.PassportForm;
import com.juyo.visa.admin.visajp.form.VisaEditDataForm;
import com.juyo.visa.common.enums.CollarAreaEnum;
import com.juyo.visa.common.enums.ExpressTypeEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.JobStatusEnum;
import com.juyo.visa.common.enums.MainSalePayTypeEnum;
import com.juyo.visa.common.enums.MainSaleTripTypeEnum;
import com.juyo.visa.common.enums.MainSaleUrgentEnum;
import com.juyo.visa.common.enums.MainSaleUrgentTimeEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.common.enums.ReceptionSearchStatusEnum_JP;
import com.juyo.visa.common.enums.VisaDataTypeEnum;
import com.juyo.visa.common.util.MapUtil;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantFrontPaperworkJpEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TApplicantPassportEntity;
import com.juyo.visa.entities.TApplicantVisaPaperworkJpEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderRecipientEntity;
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
 * @author   
 * @Date	 2017年11月24日 	 
 */
@IocBean
public class ReceptionJpViewService extends BaseService<TOrderRecipientEntity> {

	@Inject
	private OrderJpViewService orderJpViewService;
	@Inject
	private FirstTrialJpViewService firstTrialJpViewService;

	public Object listData(ReceptionJpForm form, HttpSession session) {
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
			int orderstatus = (int) record.get("orderstatus");
			for (JPOrderStatusEnum orderStatus : JPOrderStatusEnum.values()) {
				/*if (orderstatus == orderStatus.SEND_ADDRESS.intKey()) {
					record.put("orderstatus", "已寄出");
				} else {
				}*/
				if (orderstatus == orderStatus.intKey()) {
					record.put("orderstatus", orderStatus.value());
				}
			}
			Integer orderid = (Integer) record.get("orderJpId");
			String sqlStr = sqlManager.get("reception_list_data");
			Sql applysql = Sqls.create(sqlStr);
			List<Record> records = dbDao.query(applysql, Cnd.where("taoj.orderId", "=", orderid), null);
			for (Record applicant : records) {
				Integer dataType = (Integer) applicant.get("dataType");
				for (JobStatusEnum dataTypeEnum : JobStatusEnum.values()) {
					if (!Util.isEmpty(dataType) && dataType.equals(dataTypeEnum.intKey())) {
						applicant.put("dataType", dataTypeEnum.value());
					}
				}

				Integer expressType = (Integer) applicant.get("expressType");
				for (ExpressTypeEnum expressTypeEnum : ExpressTypeEnum.values()) {
					if (!Util.isEmpty(expressType) && expressType.equals(expressTypeEnum.intKey())) {
						applicant.put("expressType", expressTypeEnum.value());
					}
				}
			}
			record.put("everybodyInfo", records);
		}
		result.put("pageTotal", pager.getPageCount());
		result.put("pageListCount", list.size());
		result.put("receptionJpData", list);
		return result;
	}

	public Object toList() {
		Map<String, Object> result = Maps.newHashMap();
		result.put("receptionSearchStatus", EnumUtil.enum2(ReceptionSearchStatusEnum_JP.class));
		return result;
	}

	public Object receptionDetail(Integer orderid) {
		Map<String, Object> result = Maps.newHashMap();
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderid.longValue());
		//日本订单数据
		TOrderJpEntity jporderinfo = dbDao.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", orderEntity.getId()));
		for (JPOrderStatusEnum orderStatus : JPOrderStatusEnum.values()) {
			if (orderEntity.getStatus() == orderStatus.intKey()) {
				result.put("orStatus", orderStatus.value());
			}
		}
		result.put("orderinfo", orderEntity);
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

	public Object getJpVisaDetailData(Integer orderid) {
		Map<String, Object> result = Maps.newHashMap();
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderid.longValue());
		TOrderJpEntity jpEntity = dbDao.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", orderEntity.getId()));
		//订单信息
		String sqlstr = sqlManager.get("get_jp_receptionOrderInfo_byid");
		Sql sql = Sqls.create(sqlstr);
		sql.setParam("orderid", jpEntity.getId());
		Record orderinfo = dbDao.fetch(sql);
		int status = (int) orderinfo.get("status");
		for (JPOrderStatusEnum orderStatus : JPOrderStatusEnum.values()) {
			if (status == orderStatus.intKey()) {
				orderinfo.put("status", orderStatus.value());
			}
		}
		if (!Util.isEmpty(orderinfo.get("money"))) {
			DecimalFormat df = new DecimalFormat("#.00");
			orderinfo.put("money", df.format(orderinfo.get("money")));
			//orderinfo.put("money", Double.valueOf(df.format(orderinfo.get("money"))).doubleValue());
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
		String applysqlstr = sqlManager.get("get_applyInfo_byorderid");
		Sql applysql = Sqls.create(applysqlstr);
		applysql.setParam("orderid", jpEntity.getId());
		List<Record> applyinfo = dbDao.query(applysql, null, null);
		for (Record record : applyinfo) {
			Integer type = (Integer) record.get("type");
			for (VisaDataTypeEnum visadatatype : VisaDataTypeEnum.values()) {
				if (!Util.isEmpty(type) && type.equals(visadatatype.intKey())) {
					record.put("type", visadatatype.value());
				}
			}
		}
		result.put("applyinfo", applyinfo);
		return result;

	}

	public Object saveRealInfoData(TOrderJpEntity orderjp, String applicatinfo, HttpSession session) {
		//更新备注信息
		dbDao.update(orderjp);
		List<Map> applicatlist = JsonUtil.fromJson(applicatinfo, List.class);
		//需要更新的真实资料
		List<TApplicantFrontPaperworkJpEntity> paperworks = Lists.newArrayList();
		//所有的人
		for (Map map : applicatlist) {
			String applicatid = map.get("applicatid").toString();
			String revenuejson = String.valueOf(map.get("revenue"));
			List<TApplicantFrontPaperworkJpEntity> paperworkjps = JsonUtil.fromJsonAsList(
					TApplicantFrontPaperworkJpEntity.class, revenuejson);
			for (TApplicantFrontPaperworkJpEntity paperworkjp : paperworkjps) {
				if (!Util.isEmpty(paperworkjp.getId())) {
					TApplicantFrontPaperworkJpEntity fetch = dbDao.fetch(TApplicantFrontPaperworkJpEntity.class,
							paperworkjp.getId().longValue());
					if (!Util.isEmpty(paperworkjp.getRealInfo())) {
						fetch.setRealInfo(paperworkjp.getRealInfo());
						fetch.setStatus(paperworkjp.getStatus());
						//级联更新签证资料表的数据
						if (!Util.isEmpty(fetch.getVisapaperworkid())) {
							TApplicantVisaPaperworkJpEntity visapaperwork = dbDao.fetch(
									TApplicantVisaPaperworkJpEntity.class, fetch.getVisapaperworkid().longValue());
							if (!Util.isEmpty(visapaperwork)) {
								visapaperwork.setRealInfo(paperworkjp.getRealInfo());
								dbDao.update(visapaperwork);
							}
						}
						paperworks.add(fetch);
					} else {
						Integer visapaperworkid = fetch.getVisapaperworkid();
						if (!Util.isEmpty(visapaperworkid)) {
							TApplicantVisaPaperworkJpEntity visapaperworkinfo = dbDao.fetch(
									TApplicantVisaPaperworkJpEntity.class, visapaperworkid.longValue());
							dbDao.delete(visapaperworkinfo);
						}
						dbDao.delete(fetch);
					}
				} else {
					paperworkjp.setApplicantId(Integer.valueOf(applicatid));
					//同步到签证实收
					TApplicantVisaPaperworkJpEntity visapaperwork = new TApplicantVisaPaperworkJpEntity();
					visapaperwork.setApplicantId(Integer.valueOf(applicatid));
					visapaperwork.setCreateTime(new Date());
					visapaperwork.setRealInfo(paperworkjp.getRealInfo());
					Integer type = null;
					List<TApplicantVisaPaperworkJpEntity> query = dbDao.query(TApplicantVisaPaperworkJpEntity.class,
							Cnd.where("applicantid", "=", applicatid), null);
					for (TApplicantVisaPaperworkJpEntity tApplicantVisaPaperwork : query) {
						if (!Util.isEmpty(tApplicantVisaPaperwork.getType())) {
							type = tApplicantVisaPaperwork.getType();
							break;
						}
					}
					visapaperwork.setType(type);
					TApplicantVisaPaperworkJpEntity visapaperworkinfo = dbDao.insert(visapaperwork);
					//保存签证实收信息
					paperworkjp.setVisapaperworkid(visapaperworkinfo.getId());
					dbDao.insert(paperworkjp);
				}
			}
		}
		//删掉灰色的
		if (!Util.isEmpty(paperworks)) {
			dbDao.update(paperworks);
		}
		//点击实收之后添加日志
		orderJpViewService.insertLogs(orderjp.getOrderId(), JPOrderStatusEnum.RECEPTION_RECEIVED.intKey(), session);

		/*for (Map map : applicatlist) {//给订单下所有联系人发短信
			int applyid = Integer.valueOf((String) map.get("applicatid"));
			TApplicantOrderJpEntity applyJp = dbDao.fetch(TApplicantOrderJpEntity.class, applyid);
			TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyJp.getApplicantId().longValue());
			try {
				sendMessage(orderEntity.getId(), apply.getId());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/

		return null;

	}

	public Object toSend(int orderid, HttpSession session) {
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", orderid));
		//发送短信(根据分享是统一联系人还是单独分享来发送短信)
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
		//根据日本订单表查出对应的日本申请人
		List<TApplicantOrderJpEntity> applyJpList = dbDao.query(TApplicantOrderJpEntity.class,
				Cnd.where("orderId", "=", orderjp.getId()), null);
		//查询是否有统一联系人
		TApplicantOrderJpEntity sameApply = dbDao.fetch(
				TApplicantOrderJpEntity.class,
				Cnd.where("orderId", "=", orderjp.getId()).and("isSameLinker", "=", IsYesOrNoEnum.YES.intKey())
						.and("isShareSms", "=", IsYesOrNoEnum.YES.intKey()));
		if (!Util.isEmpty(sameApply)) {//如果有统一联系人，给统一联系人发短信
			TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class, sameApply.getApplicantId()
					.longValue());
			try {
				sendMessage(orderEntity.getId(), applicantEntity.getId());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {//没有统一联系人时，单独发送
			for (TApplicantOrderJpEntity applyJp : applyJpList) {
				TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyJp.getApplicantId().longValue());
				if (Util.eq(applyJp.getIsShareSms(), IsYesOrNoEnum.YES.intKey())) {//单独发短信
					try {
						sendMessage(orderEntity.getId(), apply.getId());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	//发送手机信息
	public Object sendMessage(int orderid, int applicantid) throws IOException {
		List<String> readLines = IOUtils
				.readLines(getClass().getClassLoader().getResourceAsStream("reception_sms.txt"));
		StringBuilder tmp = new StringBuilder();
		for (String line : readLines) {
			tmp.append(line);
		}

		//查询订单号
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderid);
		String orderNum = order.getOrderNum();

		//申请人信息
		TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class, applicantid);
		StringBuffer sb = new StringBuffer();
		sb.append(applicantEntity.getFirstName()).append(applicantEntity.getLastName());
		String name = sb.toString();
		String sex = applicantEntity.getSex();
		String telephone = applicantEntity.getTelephone();
		if (Util.isEmpty(sex)) {
			sex = "先生/女士";
		} else if (Util.eq(sex, "男")) {
			sex = "先生";
		} else if (Util.eq(sex, "女")) {
			sex = "女士";
		}
		String result = "";

		String smsContent = tmp.toString();
		smsContent = smsContent.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum);
		result = firstTrialJpViewService.sendSMS(telephone, smsContent);
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
		jporder.setRemark(editDataForm.getRemark());
		dbDao.update(jporder);

		return null;
	}

	public Object validateIsoriginal(Integer paperid) {
		boolean result = false;
		TApplicantFrontPaperworkJpEntity fetch = dbDao.fetch(TApplicantFrontPaperworkJpEntity.class,
				paperid.longValue());
		if (!Util.isEmpty(fetch) && !Util.isEmpty(fetch.getType())) {
			result = true;
		}
		return result;

	}

	public Object revenue(HttpSession session, Integer orderid) {
		Map<String, Object> result = Maps.newHashMap();
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, new Long(orderid).intValue());
		TOrderJpEntity orderJpEntity = dbDao
				.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", orderEntity.getId()));

		//申请人列表
		String sqlStr = sqlManager.get("reception_list_data");
		Sql applysql = Sqls.create(sqlStr);
		List<Record> query = dbDao.query(applysql, Cnd.where("taoj.orderId", "=", orderJpEntity.getId()), null);
		for (Record apply : query) {
			Integer dataType = (Integer) apply.get("dataType");
			for (JobStatusEnum dataTypeEnum : JobStatusEnum.values()) {
				if (!Util.isEmpty(dataType) && dataType.equals(dataTypeEnum.intKey())) {
					apply.put("dataType", dataTypeEnum.value());
				}
			}
			List<TApplicantFrontPaperworkJpEntity> revenue = dbDao.query(TApplicantFrontPaperworkJpEntity.class,
					Cnd.where("applicantId", "=", apply.get("applicatid")), null);
			apply.put("revenue", revenue);
		}
		result.put("applicant", query);
		result.put("orderJpinfo", orderJpEntity);
		result.put("orderid", orderid);
		return result;
	}

	public Object receptionRevenue(HttpSession session, Integer orderid) {
		Map<String, Object> result = Maps.newHashMap();
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderid.longValue());
		TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", orderinfo.getId()));
		result.put("orderinfo", orderJpEntity);
		return result;
	}

	public Object saveApplicatRevenue(Integer applicatid, String realInfo, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		/*TApplicantFrontPaperworkJpEntity frontPaperworkJpEntity = dbDao.fetch(TApplicantFrontPaperworkJpEntity.class,
				Cnd.where("applicantId", "=", applicatid));
		if (!Util.isEmpty(frontPaperworkJpEntity)) {
			frontPaperworkJpEntity.setUpdateTime(new Date());
			frontPaperworkJpEntity.setRealInfo(realInfo);
			frontPaperworkJpEntity.setOpId(loginUser.getId());
			return dbDao.update(frontPaperworkJpEntity);
		} else {*/
		TApplicantFrontPaperworkJpEntity frontpaperwork = new TApplicantFrontPaperworkJpEntity();
		frontpaperwork.setApplicantId(applicatid);
		frontpaperwork.setCreateTime(new Date());
		frontpaperwork.setRealInfo(realInfo);
		frontpaperwork.setOpId(loginUser.getId());
		//同步到签证
		TApplicantVisaPaperworkJpEntity visapaperwork = new TApplicantVisaPaperworkJpEntity();
		visapaperwork.setApplicantId(applicatid);
		visapaperwork.setCreateTime(new Date());
		visapaperwork.setRealInfo(realInfo);
		visapaperwork.setOpId(loginUser.getId());
		Integer type = null;
		List<TApplicantVisaPaperworkJpEntity> query = dbDao.query(TApplicantVisaPaperworkJpEntity.class,
				Cnd.where("applicantid", "=", applicatid), null);
		for (TApplicantVisaPaperworkJpEntity tApplicantVisaPaperwork : query) {
			if (!Util.isEmpty(tApplicantVisaPaperwork.getType())) {
				type = tApplicantVisaPaperwork.getType();
				break;
			}
		}
		visapaperwork.setType(type);
		TApplicantVisaPaperworkJpEntity visapaperworkinfo = dbDao.insert(visapaperwork);
		frontpaperwork.setVisapaperworkid(visapaperworkinfo.getId());
		return dbDao.insert(frontpaperwork);
		//}
	}

	public Object passportInfo(Integer applyId) {
		Map<String, Object> result = Maps.newHashMap();
		TApplicantOrderJpEntity applyjp = dbDao.fetch(TApplicantOrderJpEntity.class, applyId.longValue());
		TApplicantPassportEntity passport = dbDao.fetch(TApplicantPassportEntity.class,
				Cnd.where("applicantId", "=", applyjp.getApplicantId()));
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

	public Object editPassportCount(Integer applicatid, String inputVal) {

		TApplicantFrontPaperworkJpEntity paperwork = dbDao.fetch(TApplicantFrontPaperworkJpEntity.class,
				Cnd.where("applicantId", "=", applicatid).and("realInfo", "like", "%护照%"));
		paperwork.setRealInfo(inputVal);
		dbDao.update(paperwork);
		return null;
	}

	public Object visaTransfer(HttpSession session, Integer orderid) {
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, new Long(orderid).intValue());
		orderEntity.setStatus(JPOrderStatusEnum.VISA_ORDER.intKey());
		dbDao.update(orderEntity);
		return null;
	}
}
