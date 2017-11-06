/**
 * SaleViewService.java
 * com.juyo.visa.admin.sale.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.juyo.visa.admin.order.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;

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
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TCustomerEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TApplicantForm;
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

	public Object addOrder() {
		Map<String, Object> result = MapUtil.map();
		//result.put("orderId", id);
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

	public Object addOrder(Integer id) {
		Map<String, Object> result = MapUtil.map();
		result.put("orderId", id);
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

	public Object addApplicant(TApplicantForm applicantForm, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TApplicantEntity applicant = new TApplicantEntity();
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
		//applicant.setCreateTime(new Date());
		//applicant.setUserId(loginUser.getId());
		//dbDao.insert(applicant);
		return applicant;
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
			customer.setEmail(String.valueOf(customermap.get("linkman")));
		}
		if (!Util.isEmpty(customermap.get("mobile"))) {
			customer.setEmail(String.valueOf(customermap.get("mobile")));
		}
		if (!Util.isEmpty(customermap.get("name"))) {
			customer.setEmail(String.valueOf(customermap.get("name")));
		}
		if (!Util.isEmpty(customermap.get("shortname"))) {
			customer.setEmail(String.valueOf(customermap.get("shortname")));
		}
		if (!Util.isEmpty(customermap.get("source"))) {
			customer.setSource(Integer.valueOf(String.valueOf(customermap.get("source"))));
		}
		customer.setUserId(loginUser.getId());
		customer.setUpdateTime(new Date());
		dbDao.update(customer);
		return null;
	}

	public Object saveAddOrderinfo(OrderEditDataForm orderInfo, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);

		//客户信息
		TCustomerEntity customer = new TCustomerEntity();
		customer.setCompId(loginCompany.getId());
		customer.setUserId(loginUser.getId());
		customer.setCreateTime(new Date());
		if (!Util.isEmpty(orderInfo.getEmail())) {
			customer.setEmail(orderInfo.getEmail());
		}
		if (!Util.isEmpty(orderInfo.getLinkman())) {
			customer.setLinkman(orderInfo.getLinkman());
		}
		if (!Util.isEmpty(orderInfo.getMobile())) {
			customer.setMobile(orderInfo.getMobile());
		}
		if (!Util.isEmpty(orderInfo.getName())) {
			customer.setName(orderInfo.getName());
		}
		if (!Util.isEmpty(orderInfo.getShortname())) {
			customer.setShortname(orderInfo.getShortname());
		}
		if (!Util.isEmpty(orderInfo.getSource())) {
			customer.setSource(orderInfo.getSource());
		}
		dbDao.insert(customer);
		Integer customerId = customer.getId();

		//订单信息
		TOrderEntity orderEntity = new TOrderEntity();
		orderEntity.setComId(loginCompany.getId());
		orderEntity.setUserId(loginUser.getId());
		orderEntity.setCreateTime(new Date());
		orderEntity.setCustomerId(customerId);
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
		orderEntity.setOrderNum("2");
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
}
