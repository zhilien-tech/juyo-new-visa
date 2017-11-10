/**
 * SaleViewService.java
 * com.juyo.visa.admin.sale.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.juyo.visa.admin.order.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import org.nutz.lang.Strings;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.order.form.OrderEditDataForm;
import com.juyo.visa.admin.order.form.OrderJpForm;
import com.juyo.visa.common.enums.CollarAreaEnum;
import com.juyo.visa.common.enums.CustomerTypeEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.MainBackMailSourceTypeEnum;
import com.juyo.visa.common.enums.MainBackMailTypeEnum;
import com.juyo.visa.common.enums.MainSalePayTypeEnum;
import com.juyo.visa.common.enums.MainSaleTripTypeEnum;
import com.juyo.visa.common.enums.MainSaleUrgentEnum;
import com.juyo.visa.common.enums.MainSaleUrgentTimeEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.common.enums.NoZHIKECustomerTypeEnum;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TApplicantPassportEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TCustomerEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TApplicantForm;
import com.juyo.visa.forms.TApplicantPassportForm;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年10月26日 	 
 */
@IocBean
public class OrderJpViewService extends BaseService<TOrderJpEntity> {
	public static List<TApplicantEntity> applicantList = new ArrayList<>();

	public Object listData(OrderJpForm queryForm, HttpSession session) {
		Map<String, Object> result = MapUtil.map();
		Sql sql = queryForm.sql(sqlManager);

		Integer pageNumber = queryForm.getPageNumber();
		Integer pageSize = queryForm.getPageSize();
		Pager pager = new OffsetPager((pageNumber - 1) * pageSize, pageSize);
		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));
		sql.setPager(pager);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);
		//XIANSHANG(1, "线上"), OTS(2, "OTS"), ZHIKE(3, "直客"), XIANXIA(4, "线下");
		List<Record> orderJp = (List<Record>) sql.getResult();
		for (Record record : orderJp) {
			if (!Util.isEmpty(record.get("source"))) {
				int sourceInt = (int) record.get("source");
				for (CustomerTypeEnum customerTypeEnum : CustomerTypeEnum.values()) {
					if (sourceInt == customerTypeEnum.intKey()) {
						record.put("source", customerTypeEnum.value());
					}
				}
			}
		}
		result.put("orderJp", orderJp);
		return result;

	}

	public Object addOrder(HttpSession session) {
		Map<String, Object> result = MapUtil.map();
		result.put("collarAreaEnum", EnumUtil.enum2(CollarAreaEnum.class));
		result.put("customerTypeEnum", EnumUtil.enum2(CustomerTypeEnum.class));
		result.put("mainSaleUrgentEnum", EnumUtil.enum2(MainSaleUrgentEnum.class));
		result.put("mainSaleUrgentTimeEnum", EnumUtil.enum2(MainSaleUrgentTimeEnum.class));
		result.put("mainSaleTripTypeEnum", EnumUtil.enum2(MainSaleTripTypeEnum.class));
		result.put("mainSalePayTypeEnum", EnumUtil.enum2(MainSalePayTypeEnum.class));
		result.put("mainSaleVisaTypeEnum", EnumUtil.enum2(MainSaleVisaTypeEnum.class));
		result.put("threeYearsIsVisitedEnum", EnumUtil.enum2(IsYesOrNoEnum.class));
		result.put("mainBackMailSourceTypeEnum", EnumUtil.enum2(MainBackMailSourceTypeEnum.class));
		result.put("mainBackMailTypeEnum", EnumUtil.enum2(MainBackMailTypeEnum.class));
		return result;
	}

	public Object addOrder(Integer id, HttpSession session) {
		Map<String, Object> result = MapUtil.map();
		result.put("orderId", id);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		List<TCustomerEntity> customers = dbDao.query(TCustomerEntity.class,
				Cnd.where("userId", "=", loginUser.getId()), null);
		TOrderJpEntity orderJpinfo = dbDao.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", id.longValue()));
		TOrderEntity orderInfo = dbDao.fetch(TOrderEntity.class, id.longValue());
		/*List<TApplicantOrderJpEntity> applicants = dbDao.query(TApplicantOrderJpEntity.class,
				Cnd.where("orderId", "=", orderJpinfo.getId()), null);
		if (!Util.isEmpty(applicants)) {
			for (TApplicantOrderJpEntity tApplicantOrderJpEntity : applicants) {
				if (!Util.isEmpty(tApplicantOrderJpEntity)) {
					Integer applicantId = tApplicantOrderJpEntity.getApplicantId();
					TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, applicantId.longValue());
					result.put("applicant", applicant);
				}
			}
		}*/

		result.put("orderInfo", orderInfo);
		result.put("orderJpinfo", orderJpinfo);
		result.put("customer", customers);
		result.put("customer", customers);
		result.put("collarAreaEnum", EnumUtil.enum2(CollarAreaEnum.class));
		result.put("customerTypeEnum", EnumUtil.enum2(NoZHIKECustomerTypeEnum.class));
		result.put("mainSaleUrgentEnum", EnumUtil.enum2(MainSaleUrgentEnum.class));
		result.put("mainSaleUrgentTimeEnum", EnumUtil.enum2(MainSaleUrgentTimeEnum.class));
		result.put("mainSaleTripTypeEnum", EnumUtil.enum2(MainSaleTripTypeEnum.class));
		result.put("mainSalePayTypeEnum", EnumUtil.enum2(MainSalePayTypeEnum.class));
		result.put("mainSaleVisaTypeEnum", EnumUtil.enum2(MainSaleVisaTypeEnum.class));
		result.put("threeYearsIsVisitedEnum", EnumUtil.enum2(IsYesOrNoEnum.class));
		result.put("mainBackMailSourceTypeEnum", EnumUtil.enum2(MainBackMailSourceTypeEnum.class));
		result.put("mainBackMailTypeEnum", EnumUtil.enum2(MainBackMailTypeEnum.class));
		return result;
	}

	public Object addApplicant(TApplicantForm applicantForm, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TApplicantEntity applicant = new TApplicantEntity();
		applicant.setUserId(loginUser.getId());
		applicant.setOpId(loginUser.getId());
		if (!Util.isEmpty(applicantForm.getAddress())) {
			applicant.setAddress(applicantForm.getAddress());
		}
		if (!Util.isEmpty(applicantForm.getBirthday())) {
			applicant.setBirthday(applicantForm.getBirthday());
		}
		if (!Util.isEmpty(applicantForm.getCardId())) {
			applicant.setCardId(applicantForm.getCardId());
		}
		if (!Util.isEmpty(applicantForm.getCity())) {
			applicant.setCity(applicantForm.getCity());
		}
		if (!Util.isEmpty(applicantForm.getDetailedAddress())) {
			applicant.setDetailedAddress(applicantForm.getDetailedAddress());
		}
		if (!Util.isEmpty(applicantForm.getEmail())) {
			applicant.setEmail(applicantForm.getEmail());
		}
		if (!Util.isEmpty(applicantForm.getFirstName())) {
			applicant.setFirstName(applicantForm.getFirstName());
		}
		if (!Util.isEmpty(applicantForm.getIssueOrganization())) {
			applicant.setIssueOrganization(applicantForm.getIssueOrganization());
		}
		if (!Util.isEmpty(applicantForm.getLastName())) {
			applicant.setLastName(applicantForm.getLastName());
		}
		if (!Util.isEmpty(applicantForm.getNation())) {
			applicant.setNation(applicantForm.getNation());
		}
		if (!Util.isEmpty(applicantForm.getProvince())) {
			applicant.setProvince(applicantForm.getProvince());
		}
		if (!Util.isEmpty(applicantForm.getSex())) {
			applicant.setSex(applicantForm.getSex());
		}
		if (!Util.isEmpty(applicantForm.getTelephone())) {
			applicant.setTelephone(applicantForm.getTelephone());
		}
		if (!Util.isEmpty(applicantForm.getValidEndDate())) {
			applicant.setValidEndDate(applicantForm.getValidEndDate());
		}
		if (!Util.isEmpty(applicantForm.getValidStartDate())) {
			applicant.setValidStartDate(applicantForm.getValidStartDate());
		}
		Map<String, Object> result = MapUtil.map();
		if (!Util.isEmpty(applicantForm.getOrderid())) {
			dbDao.insert(applicant);
			Integer applicantId = applicant.getId();
			TApplicantOrderJpEntity applicantOrderJp = new TApplicantOrderJpEntity();
			TOrderJpEntity orderJp = dbDao.fetch(TOrderJpEntity.class,
					Cnd.where("orderId", "=", applicantForm.getOrderid()));
			Integer orderJpId = orderJp.getId();
			applicantOrderJp.setOrderId(orderJpId);
			applicantOrderJp.setApplicantId(applicantId);
			dbDao.insert(applicantOrderJp);
			TApplicantPassportEntity passport = new TApplicantPassportEntity();
			if (!Util.isEmpty(applicantForm.getSex())) {
				if (applicantForm.getSex() == 1) {
					passport.setSex("男");
				} else {
					passport.setSex("女");
				}
			}
			if (!Util.isEmpty(applicantForm.getFirstName())) {
				passport.setFirstName(applicantForm.getFirstName());
			}
			if (!Util.isEmpty(applicantForm.getLastName())) {
				passport.setLastName(applicantForm.getLastName());
			}
			passport.setApplicantId(applicantId);
			dbDao.insert(passport);
			return applicant;
		} else {
			TApplicantEntity applicantDB = dbDao.insert(applicant);
			Integer applicantId = applicantDB.getId();
			TApplicantPassportEntity passport = new TApplicantPassportEntity();
			if (!Util.isEmpty(applicantForm.getSex())) {
				if (applicantForm.getSex() == 1) {
					passport.setSex("男");
				} else {
					passport.setSex("女");
				}
			}
			if (!Util.isEmpty(applicantForm.getFirstName())) {
				passport.setFirstName(applicantForm.getFirstName());
			}
			if (!Util.isEmpty(applicantForm.getLastName())) {
				passport.setLastName(applicantForm.getLastName());
			}
			passport.setApplicantId(applicantId);
			dbDao.insert(passport);
			//List<TApplicantEntity> applicantList = new ArrayList<>();
			//applicantList.add(applicant);
			return applicantDB;
		}
	}

	public Object saveOrder(OrderEditDataForm orderInfo, String customerInfo, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		//订单信息
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderInfo.getId().longValue());
		if (!Util.isEmpty(orderInfo.getNumber())) {
			order.setNumber(orderInfo.getNumber());
		}
		if (!Util.isEmpty(orderInfo.getCityid())) {
			order.setCityId(orderInfo.getCityid());
		}
		if (!Util.isEmpty(orderInfo.getUrgenttype())) {
			order.setUrgentType(orderInfo.getUrgenttype());
		}
		if (!Util.isEmpty(orderInfo.getUrgentday())) {
			order.setUrgentDay(orderInfo.getUrgentday());
		}
		if (!Util.isEmpty(orderInfo.getTravel())) {
			order.setTravel(orderInfo.getTravel());
		}
		if (!Util.isEmpty(orderInfo.getPaytype())) {
			order.setPayType(orderInfo.getPaytype());
		}
		if (!Util.isEmpty(orderInfo.getMoney())) {
			order.setMoney(orderInfo.getMoney());
		}
		if (!Util.isEmpty(orderInfo.getGotripdate())) {
			order.setGoTripDate(orderInfo.getGotripdate());
		}
		if (!Util.isEmpty(orderInfo.getStayday())) {
			order.setStayDay(orderInfo.getStayday());
		}
		if (!Util.isEmpty(orderInfo.getBacktripdate())) {
			order.setBackTripDate(orderInfo.getBacktripdate());
		}
		if (!Util.isEmpty(orderInfo.getSendvisadate())) {
			order.setSendVisaDate(orderInfo.getSendvisadate());
		}
		if (!Util.isEmpty(orderInfo.getOutvisadate())) {
			order.setOutVisaDate(orderInfo.getOutvisadate());
		}
		order.setUpdateTime(new Date());
		dbDao.update(order);
		//日本订单信息
		TOrderJpEntity jporder = dbDao.fetch(TOrderJpEntity.class, orderInfo.getId().longValue());
		if (!Util.isEmpty(orderInfo.getVisatype())) {
			jporder.setVisaType(orderInfo.getVisatype());
		}
		if (!Util.isEmpty(orderInfo.getVisacounty())) {
			jporder.setVisaCounty(orderInfo.getVisacounty());
		}
		if (!Util.isEmpty(orderInfo.getIsvisit())) {
			jporder.setIsVisit(orderInfo.getIsvisit());
		}
		if (!Util.isEmpty(orderInfo.getThreecounty())) {
			jporder.setThreeCounty(orderInfo.getThreecounty());
		}
		dbDao.update(jporder);
		//客户信息
		TCustomerEntity customer = dbDao.fetch(TCustomerEntity.class, new Long(order.getCustomerId()).intValue());
		Map<String, Object> customermap = JsonUtil.fromJson(customerInfo, Map.class);
		customer.setId(order.getCustomerId());
		customer.setCompId(loginCompany.getId());
		if (!Util.isEmpty(customermap.get("email"))) {
			customer.setEmail(String.valueOf(customermap.get("email")));
		}
		if (!Util.isEmpty(customermap.get("linkman"))) {
			customer.setLinkman(String.valueOf(customermap.get("linkman")));
		}
		if (!Util.isEmpty(customermap.get("mobile"))) {
			customer.setMobile(String.valueOf(customermap.get("mobile")));
		}
		if (!Util.isEmpty(customermap.get("name"))) {
			customer.setName(String.valueOf(customermap.get("name")));
		}
		if (!Util.isEmpty(customermap.get("shortname"))) {
			customer.setShortname(String.valueOf(customermap.get("shortname")));
		}
		if (!Util.isEmpty(customermap.get("source"))) {
			customer.setSource(Integer.valueOf(String.valueOf(customermap.get("source"))));
		}
		customer.setUserId(loginUser.getId());
		customer.setUpdateTime(new Date());
		dbDao.update(customer);
		//申请人信息

		return null;
	}

	public Object saveAddOrderinfo(OrderEditDataForm orderInfo, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TOrderEntity orderEntity = new TOrderEntity();
		//判断是否为直客
		if (!Util.isEmpty(orderInfo.getSource())) {
			if (orderInfo.getSource() == 4) {
				orderEntity.setIsDirectCus(IsYesOrNoEnum.YES.intKey()); //1是直客
			}
		}

		//客户信息
		TCustomerEntity customer = new TCustomerEntity();
		customer.setCompId(loginCompany.getId());
		customer.setUserId(loginUser.getId());
		//customer.setCreateTime(new Date());
		if (!Util.isEmpty(orderInfo.getEmail())) {
			orderEntity.setEmail(orderInfo.getEmail());
		}
		if (!Util.isEmpty(orderInfo.getLinkman())) {
			orderEntity.setLinkman(orderInfo.getLinkman());
		}
		if (!Util.isEmpty(orderInfo.getMobile())) {
			orderEntity.setTelephone(orderInfo.getMobile());
		}
		//dbDao.insert(customer);
		//Integer customerId = customer.getId();

		//订单信息

		orderEntity.setComId(loginCompany.getId());
		orderEntity.setUserId(loginUser.getId());
		orderEntity.setCreateTime(new Date());
		//orderEntity.setCustomerId(customerId);
		if (!Util.isEmpty(orderInfo.getCityid())) {
			orderEntity.setCityId(orderInfo.getCityid());
		}
		if (!Util.isEmpty(orderInfo.getBacktripdate())) {
			orderEntity.setBackTripDate(orderInfo.getBacktripdate());
		}
		if (!Util.isEmpty(orderInfo.getName())) {
			orderEntity.setComName(orderInfo.getName());
		}
		if (!Util.isEmpty(orderInfo.getShortname())) {
			orderEntity.setComShortName(orderInfo.getShortname());
		}
		if (!Util.isEmpty(orderInfo.getEmail())) {
			orderEntity.setEmail(orderInfo.getEmail());
		}
		if (!Util.isEmpty(orderInfo.getGotripdate())) {
			orderEntity.setGoTripDate(orderInfo.getGotripdate());
		}
		if (!Util.isEmpty(orderInfo.getLinkman())) {
			orderEntity.setLinkman(orderInfo.getLinkman());
		}
		if (!Util.isEmpty(orderInfo.getMoney())) {
			orderEntity.setMoney(orderInfo.getMoney());
		}
		if (!Util.isEmpty(orderInfo.getUrgenttype())) {
			orderEntity.setUrgentType(orderInfo.getUrgenttype());
		}
		if (!Util.isEmpty(orderInfo.getUrgentday())) {
			orderEntity.setUrgentDay(orderInfo.getUrgentday());
		}
		if (!Util.isEmpty(orderInfo.getTravel())) {
			orderEntity.setTravel(orderInfo.getTravel());
		}
		if (!Util.isEmpty(orderInfo.getMobile())) {
			orderEntity.setTelephone(orderInfo.getMobile());
		}
		if (!Util.isEmpty(orderInfo.getStayday())) {
			orderEntity.setStayDay(orderInfo.getStayday());
		}
		if (!Util.isEmpty(orderInfo.getSendvisadate())) {
			orderEntity.setSendVisaDate(orderInfo.getSendvisadate());
		}
		if (!Util.isEmpty(orderInfo.getPaytype())) {
			orderEntity.setPayType(orderInfo.getPaytype());
		}
		if (!Util.isEmpty(orderInfo.getNumber())) {
			orderEntity.setNumber(orderInfo.getNumber());
		}
		if (!Util.isEmpty(orderInfo.getOutvisadate())) {
			orderEntity.setOutVisaDate(orderInfo.getOutvisadate());
		}

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
		String ordernum = format + "-JP" + sum1;

		orderEntity.setOrderNum(ordernum);
		//orderEntity.setOrderNum("2");
		dbDao.insert(orderEntity);
		Integer orderId = orderEntity.getId();

		//日本订单信息
		TOrderJpEntity orderJpEntity = new TOrderJpEntity();
		orderJpEntity.setOrderId(orderId);
		if (!Util.isEmpty(orderInfo.getIsvisit())) {
			orderJpEntity.setIsVisit(orderInfo.getIsvisit());
		}
		if (!Util.isEmpty(orderInfo.getThreecounty())) {
			orderJpEntity.setThreeCounty(orderInfo.getThreecounty());
		}
		if (!Util.isEmpty(orderInfo.getVisacounty())) {
			orderJpEntity.setVisaCounty(orderInfo.getVisacounty());
		}
		if (!Util.isEmpty(orderInfo.getVisatype())) {
			orderJpEntity.setVisaType(orderInfo.getVisatype());
		}
		dbDao.insert(orderJpEntity);
		//申请人信息
		Integer orderJpId = orderJpEntity.getId();

		Iterator<TApplicantEntity> it = applicantList.iterator();
		while (it.hasNext()) {
			TApplicantEntity applicantEntity = it.next();
			TApplicantOrderJpEntity applicantOrderJp = new TApplicantOrderJpEntity();
			applicantOrderJp.setOrderId(orderJpId);
			applicantOrderJp.setApplicantId(applicantEntity.getId());
			dbDao.insert(applicantOrderJp);
			if (applicantOrderJp.getApplicantId() == applicantEntity.getId()) {
				it.remove();
			}
		}
		/*for (TApplicantEntity applicantEntity : applicantList) {
			TApplicantOrderJpEntity applicantOrderJp = new TApplicantOrderJpEntity();
			applicantOrderJp.setOrderId(orderJpId);
			applicantOrderJp.setApplicantId(applicantEntity.getId());
			dbDao.insert(applicantOrderJp);
			if (applicantOrderJp.getApplicantId() == applicantEntity.getId()) {
				applicantList.remove(applicantEntity);
			}
		}*/
		return null;
	}

	public Object fetchOrder(Integer id) {
		Map<String, Object> result = Maps.newHashMap();
		//客户信息
		String customerSqlstr = sqlManager.get("orderJp_list_customerInfo_byOrderId");
		Sql customerSql = Sqls.create(customerSqlstr);
		customerSql.setParam("id", id);
		Record customerInfo = dbDao.fetch(customerSql);
		result.put("customerInfo", customerInfo);
		//订单信息
		String orderSqlstr = sqlManager.get("orderJp_list_orderInfo_byOrderId");
		Sql orderSql = Sqls.create(orderSqlstr);
		orderSql.setParam("id", id);
		Record orderInfo = dbDao.fetch(orderSql);
		result.put("orderInfo", orderInfo);
		//申请人信息
		String applicantSqlstr = sqlManager.get("orderJp_list_applicantInfo_byOrderId");
		Sql applicantSql = Sqls.create(applicantSqlstr);
		applicantSql.setParam("id", id);
		List<Record> applicantInfo = dbDao.query(applicantSql, null, null);
		result.put("applicantInfo", applicantInfo);
		//回邮信息
		result.put("backmailInfo", null);
		return result;
	}

	public Object updateApplicant(Integer id) {
		Map<String, Object> result = Maps.newHashMap();
		TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class, new Long(id).intValue());
		result.put("applicant", applicantEntity);
		return result;
	}

	public Object saveEditApplicant(TApplicantForm applicantForm, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TApplicantEntity applicant = new TApplicantEntity();
		applicant.setOpId(loginUser.getId());
		if (!Util.isEmpty(applicantForm.getId())) {
			applicant.setId(applicantForm.getId());
		}
		if (!Util.isEmpty(applicantForm.getAddress())) {
			applicant.setAddress(applicantForm.getAddress());
		}
		if (!Util.isEmpty(applicantForm.getBirthday())) {
			applicant.setBirthday(applicantForm.getBirthday());
		}
		if (!Util.isEmpty(applicantForm.getCardId())) {
			applicant.setCardId(applicantForm.getCardId());
		}
		if (!Util.isEmpty(applicantForm.getCity())) {
			applicant.setCity(applicantForm.getCity());
		}
		if (!Util.isEmpty(applicantForm.getDetailedAddress())) {
			applicant.setDetailedAddress(applicantForm.getDetailedAddress());
		}
		if (!Util.isEmpty(applicantForm.getEmail())) {
			applicant.setEmail(applicantForm.getEmail());
		}
		if (!Util.isEmpty(applicantForm.getFirstName())) {
			applicant.setFirstName(applicantForm.getFirstName());
		}
		if (!Util.isEmpty(applicantForm.getIssueOrganization())) {
			applicant.setIssueOrganization(applicantForm.getIssueOrganization());
		}
		if (!Util.isEmpty(applicantForm.getLastName())) {
			applicant.setLastName(applicantForm.getLastName());
		}
		if (!Util.isEmpty(applicantForm.getNation())) {
			applicant.setNation(applicantForm.getNation());
		}
		if (!Util.isEmpty(applicantForm.getProvince())) {
			applicant.setProvince(applicantForm.getProvince());
		}
		if (!Util.isEmpty(applicantForm.getSex())) {
			applicant.setSex(applicantForm.getSex());
		}
		if (!Util.isEmpty(applicantForm.getTelephone())) {
			applicant.setTelephone(applicantForm.getTelephone());
		}
		if (!Util.isEmpty(applicantForm.getValidEndDate())) {
			applicant.setValidEndDate(applicantForm.getValidEndDate());
		}
		if (!Util.isEmpty(applicantForm.getValidStartDate())) {
			applicant.setValidStartDate(applicantForm.getValidStartDate());
		}
		applicant.setUpdateTime(new Date());
		dbDao.update(applicant);
		return null;
	}

	public Object getEditApplicant(Integer orderid) {
		String applicantSqlstr = sqlManager.get("orderJp_list_applicantInfo_byOrderId");
		Sql applicantSql = Sqls.create(applicantSqlstr);
		applicantSql.setParam("id", orderid);
		List<Record> applicantInfo = dbDao.query(applicantSql, null, null);
		return applicantInfo;
	}

	public Object getEditPassport(Integer id) {
		Map<String, Object> result = MapUtil.map();
		String passportSqlstr = sqlManager.get("orderJp_list_passportInfo_byApplicantId");
		Sql passportSql = Sqls.create(passportSqlstr);
		passportSql.setParam("id", id);
		Record passport = dbDao.fetch(passportSql);
		result.put("passport", passport);
		result.put("applicantId", id);
		return result;
	}

	public Object saveEditPassport(TApplicantPassportForm passportForm, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TApplicantPassportEntity passport = new TApplicantPassportEntity();
		passport.setOpId(loginUser.getId());

		if (!Util.isEmpty(passportForm.getApplicantId())) {
			passport.setApplicantId(passportForm.getApplicantId());
		}
		if (!Util.isEmpty(passportForm.getId())) {
			passport.setId(passportForm.getId());
		}
		if (!Util.isEmpty(passportForm.getBirthAddress())) {
			passport.setBirthAddress(passportForm.getBirthAddress());
		}
		if (!Util.isEmpty(passportForm.getBirthAddressEn())) {
			passport.setBirthAddressEn(passportForm.getBirthAddressEn());
		}
		if (!Util.isEmpty(passportForm.getBirthday())) {
			passport.setBirthday(passportForm.getBirthday());
		}
		if (!Util.isEmpty(passportForm.getFirstName())) {
			passport.setFirstName(passportForm.getFirstName());
		}
		if (!Util.isEmpty(passportForm.getFirstNameEn())) {
			passport.setFirstNameEn(passportForm.getFirstNameEn());
		}
		if (!Util.isEmpty(passportForm.getIssuedDate())) {
			passport.setIssuedDate(passportForm.getIssuedDate());
		}
		if (!Util.isEmpty(passportForm.getIssuedOrganization())) {
			passport.setIssuedOrganization(passportForm.getIssuedOrganization());
		}
		if (!Util.isEmpty(passportForm.getIssuedOrganizationEn())) {
			passport.setIssuedOrganizationEn(passportForm.getIssuedOrganizationEn());
		}
		if (!Util.isEmpty(passportForm.getIssuedPlace())) {
			passport.setIssuedPlace(passportForm.getIssuedPlace());
		}
		if (!Util.isEmpty(passportForm.getIssuedPlaceEn())) {
			passport.setIssuedPlaceEn(passportForm.getIssuedPlaceEn());
		}
		if (!Util.isEmpty(passportForm.getLastName())) {
			passport.setLastName(passportForm.getLastName());
		}
		if (!Util.isEmpty(passportForm.getLastNameEn())) {
			passport.setLastNameEn(passportForm.getLastNameEn());
		}
		if (!Util.isEmpty(passportForm.getPassport())) {
			passport.setPassport(passportForm.getPassport());
		}
		if (!Util.isEmpty(passportForm.getSex())) {
			passport.setSex(passportForm.getSex());
		}
		if (!Util.isEmpty(passportForm.getSexEn())) {
			passport.setSexEn(passportForm.getSexEn());
		}
		if (!Util.isEmpty(passportForm.getType())) {
			passport.setType(passportForm.getType());
		}
		if (!Util.isEmpty(passportForm.getValidEndDate())) {
			passport.setValidEndDate(passportForm.getValidEndDate());
		}
		if (!Util.isEmpty(passportForm.getValidStartDate())) {
			passport.setValidStartDate(passportForm.getValidStartDate());
		}
		if (!Util.isEmpty(passportForm.getValidType())) {
			passport.setValidType(passportForm.getValidType());
		}
		passport.setUpdateTime(new Date());
		dbDao.update(passport);
		return null;
	}

	public Object deleteApplicant(Integer id) {
		List<TApplicantPassportEntity> passports = dbDao.query(TApplicantPassportEntity.class,
				Cnd.where("applicantId", "=", id), null);
		if (!Util.isEmpty(passports)) {
			for (TApplicantPassportEntity tApplicantPassportEntity : passports) {
				dbDao.delete(TApplicantPassportEntity.class, tApplicantPassportEntity.getId());
			}
		}
		List<TApplicantOrderJpEntity> applicantOrderJp = dbDao.query(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", id), null);
		if (!Util.isEmpty(applicantOrderJp)) {
			for (TApplicantOrderJpEntity tApplicantOrderJpEntity : applicantOrderJp) {
				dbDao.delete(TApplicantOrderJpEntity.class, tApplicantOrderJpEntity.getId());
			}
		}

		//dbDao.delete(TApplicantPassportEntity.class, id);
		dbDao.delete(TApplicantEntity.class, id);
		for (TApplicantEntity DelApplicant : applicantList) {
			if (DelApplicant.getId() == id) {
				applicantList.remove(DelApplicant);
			}
		}
		return null;
	}

	public Object getLinkman(String linkman, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userType = loginUser.getUserType();
		List<TCustomerEntity> customerList = new ArrayList<>();
		//用户为公司管理员则显示该公司下所有客户
		if (userType == 5) {
			customerList = dbDao.query(TCustomerEntity.class,
					Cnd.where("compId", "=", loginCompany.getId()).and("linkman", "like", Strings.trim(linkman) + "%"),
					null);
		} else if (userType == 1) {
			customerList = dbDao.query(
					TCustomerEntity.class,
					Cnd.where("compId", "=", loginCompany.getId()).and("userId", "=", loginUser.getId())
							.and("linkman", "like", Strings.trim(linkman) + "%"), null);
		}
		return customerList;
	}

	public Object getPhoneNumSelect(String mobile, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userType = loginUser.getUserType();
		List<TCustomerEntity> customerList = new ArrayList<>();
		//用户为公司管理员则显示该公司下所有客户
		if (userType == 5) {
			customerList = dbDao.query(TCustomerEntity.class,
					Cnd.where("compId", "=", loginCompany.getId()).and("mobile", "like", Strings.trim(mobile) + "%"),
					null);
		} else if (userType == 1) {
			customerList = dbDao.query(
					TCustomerEntity.class,
					Cnd.where("compId", "=", loginCompany.getId()).and("userId", "=", loginUser.getId())
							.and("mobile", "like", Strings.trim(mobile) + "%"), null);
		}
		return customerList;
	}

	public Object getcompNameSelect(String compName, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userType = loginUser.getUserType();
		List<TCustomerEntity> customerList = new ArrayList<>();
		//用户为公司管理员则显示该公司下所有客户
		if (userType == 5) {
			customerList = dbDao.query(TCustomerEntity.class,
					Cnd.where("compId", "=", loginCompany.getId()).and("name", "like", Strings.trim(compName) + "%"),
					null);
		} else if (userType == 1) {
			customerList = dbDao.query(
					TCustomerEntity.class,
					Cnd.where("compId", "=", loginCompany.getId()).and("userId", "=", loginUser.getId())
							.and("name", "like", Strings.trim(compName) + "%"), null);
		}
		return customerList;
	}

	public Object getComShortNameSelect(String comShortName, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userType = loginUser.getUserType();
		List<TCustomerEntity> customerList = new ArrayList<>();
		//用户为公司管理员则显示该公司下所有客户
		if (userType == 5) {
			customerList = dbDao.query(
					TCustomerEntity.class,
					Cnd.where("compId", "=", loginCompany.getId()).and("shortname", "like",
							Strings.trim(comShortName) + "%"), null);
		} else if (userType == 1) {
			customerList = dbDao.query(
					TCustomerEntity.class,
					Cnd.where("compId", "=", loginCompany.getId()).and("userId", "=", loginUser.getId())
							.and("shortname", "like", Strings.trim(comShortName) + "%"), null);
		}
		return customerList;
	}

	public Object getEmailSelect(String email, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userType = loginUser.getUserType();
		List<TCustomerEntity> customerList = new ArrayList<>();
		//用户为公司管理员则显示该公司下所有客户
		if (userType == 5) {
			customerList = dbDao.query(TCustomerEntity.class,
					Cnd.where("compId", "=", loginCompany.getId()).and("email", "like", Strings.trim(email) + "%"),
					null);
		} else if (userType == 1) {
			customerList = dbDao.query(
					TCustomerEntity.class,
					Cnd.where("compId", "=", loginCompany.getId()).and("userId", "=", loginUser.getId())
							.and("email", "like", Strings.trim(email) + "%"), null);
		}
		return customerList;
	}

	public Object getCustomerById(Integer id, HttpSession session) {
		TCustomerEntity customerEntity = dbDao.fetch(TCustomerEntity.class, id.longValue());
		return customerEntity;
	}
}
