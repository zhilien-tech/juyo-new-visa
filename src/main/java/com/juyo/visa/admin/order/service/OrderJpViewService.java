/**
 * SaleViewService.java
 * com.juyo.visa.admin.sale.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.juyo.visa.admin.order.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

import com.google.common.collect.Maps;
import com.juyo.visa.admin.firstTrialJp.service.FirstTrialJpViewService;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.mail.service.MailService;
import com.juyo.visa.admin.order.entity.ApplicantJsonEntity;
import com.juyo.visa.admin.order.entity.PassportJsonEntity;
import com.juyo.visa.admin.order.entity.TIdcardEntity;
import com.juyo.visa.admin.order.form.OrderEditDataForm;
import com.juyo.visa.admin.order.form.OrderJpForm;
import com.juyo.visa.admin.order.form.VisaEditDataForm;
import com.juyo.visa.admin.user.form.ApplicantUser;
import com.juyo.visa.admin.user.service.UserViewService;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.BoyOrGirlEnum;
import com.juyo.visa.common.enums.CollarAreaEnum;
import com.juyo.visa.common.enums.CustomerTypeEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.JobStatusEnum;
import com.juyo.visa.common.enums.MainApplicantRelationEnum;
import com.juyo.visa.common.enums.MainApplicantRemarkEnum;
import com.juyo.visa.common.enums.MainBackMailSourceTypeEnum;
import com.juyo.visa.common.enums.MainBackMailTypeEnum;
import com.juyo.visa.common.enums.MainOrViceEnum;
import com.juyo.visa.common.enums.MainSalePayTypeEnum;
import com.juyo.visa.common.enums.MainSaleTripTypeEnum;
import com.juyo.visa.common.enums.MainSaleUrgentEnum;
import com.juyo.visa.common.enums.MainSaleUrgentTimeEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.common.enums.PassportTypeEnum;
import com.juyo.visa.common.enums.TrialApplicantStatusEnum;
import com.juyo.visa.common.ocr.HttpUtils;
import com.juyo.visa.common.ocr.Input;
import com.juyo.visa.common.ocr.RecognizeData;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TApplicantPassportEntity;
import com.juyo.visa.entities.TApplicantWealthJpEntity;
import com.juyo.visa.entities.TApplicantWorkJpEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TCustomerEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderLogsEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TApplicantForm;
import com.juyo.visa.forms.TApplicantPassportForm;
import com.uxuexi.core.common.util.DateUtil;
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

	@Inject
	private UploadService qiniuUploadService;//文件上传
	@Inject
	private UserViewService userViewService;
	@Inject
	private FirstTrialJpViewService firstTrialJpViewService;
	@Inject
	private MailService mailService;

	private static final String app_key = "24624389";
	private static final String app_secret = "3a28e8c97af2d2eadcf2720b279bdc9d";
	private static final String AppCode = "36c5ae22ed87410290bd90cb198e47a7";

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
			if (!Util.isEmpty(record.get("isDirectCus"))) {
				if (Util.eq(record.get("isDirectCus"), IsYesOrNoEnum.YES.intKey())) {//是直客，客户信息直接从订单中拿
					record.put("source", "直客");
				} else {//不是直客，客户信息从客户信息表中拿
					Integer customerId = (Integer) record.get("customerId");
					TCustomerEntity customerEntity = dbDao
							.fetch(TCustomerEntity.class, new Long(customerId).intValue());
					record.set("comName", customerEntity.getName());
					record.set("comShortName", customerEntity.getShortname());
					record.set("linkman", customerEntity.getLinkman());
					record.set("telephone", customerEntity.getMobile());
					int sourceInt = (int) record.get("source");
					for (CustomerTypeEnum customerTypeEnum : CustomerTypeEnum.values()) {
						if (sourceInt == customerTypeEnum.intKey()) {
							record.put("source", customerTypeEnum.value());
						}
					}
				}

			}
			int status = (int) record.get("status");
			for (JPOrderStatusEnum orderStatus : JPOrderStatusEnum.values()) {
				if (status == orderStatus.intKey()) {
					record.put("status", orderStatus.value());
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
		TCustomerEntity customer = new TCustomerEntity();
		;
		result.put("orderId", id);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TOrderJpEntity orderJpinfo = dbDao.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", id.longValue()));
		TOrderEntity orderInfo = dbDao.fetch(TOrderEntity.class, id.longValue());
		if (!Util.isEmpty(orderInfo.getCustomerId())) {
			customer = dbDao.fetch(TCustomerEntity.class, orderInfo.getCustomerId().longValue());
		}
		result.put("orderInfo", orderInfo);
		result.put("orderJpinfo", orderJpinfo);
		result.put("customer", customer);
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
		//applicant.setUserId(loginUser.getId());
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
		if (!Util.isEmpty(applicantForm.getCardFront())) {
			applicant.setCardFront(applicantForm.getCardFront());
		}
		if (!Util.isEmpty(applicantForm.getCardBack())) {
			applicant.setCardBack(applicantForm.getCardBack());
		}
		Map<String, Object> result = MapUtil.map();
		applicant.setStatus(TrialApplicantStatusEnum.FIRSTTRIAL.intKey());
		applicant.setCreateTime(new Date());

		ApplicantUser applicantUser = new ApplicantUser();
		applicantUser.setMobile(applicant.getTelephone());
		applicantUser.setOpid(applicant.getOpId());
		applicantUser.setPassword("000000");
		applicantUser.setUsername(applicant.getFirstName() + applicant.getLastName());
		TUserEntity tUserEntity = userViewService.addApplicantUser(applicantUser);
		applicant.setUserId(tUserEntity.getId());
		if (!Util.isEmpty(applicantForm.getOrderid())) {
			dbDao.insert(applicant);
			Integer applicantId = applicant.getId();
			//日本申请人信息
			TApplicantOrderJpEntity applicantOrderJp = new TApplicantOrderJpEntity();
			TOrderJpEntity orderJp = dbDao.fetch(TOrderJpEntity.class,
					Cnd.where("orderId", "=", applicantForm.getOrderid()));
			Integer orderJpId = orderJp.getId();
			applicantOrderJp.setOrderId(orderJpId);
			applicantOrderJp.setApplicantId(applicantId);
			dbDao.insert(applicantOrderJp);
			Integer applicantJpId = applicantOrderJp.getId();
			//日本工作信息
			TApplicantWorkJpEntity workJp = new TApplicantWorkJpEntity();
			workJp.setApplicantId(applicantJpId);
			workJp.setCreateTime(new Date());
			dbDao.insert(workJp);
			//日本财产信息
			TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
			wealthJp.setCreateTime(new Date());
			wealthJp.setApplicantId(applicantJpId);
			dbDao.insert(wealthJp);
			//护照信息
			TApplicantPassportEntity passport = new TApplicantPassportEntity();
			if (!Util.isEmpty(applicantForm.getSex())) {
				passport.setSex(applicantForm.getSex());
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
			//日本申请人信息
			TApplicantOrderJpEntity applicantOrderJp = new TApplicantOrderJpEntity();
			applicantOrderJp.setApplicantId(applicantId);
			dbDao.insert(applicantOrderJp);
			Integer applicantJpId = applicantOrderJp.getId();
			//日本工作信息
			TApplicantWorkJpEntity workJp = new TApplicantWorkJpEntity();
			workJp.setApplicantId(applicantJpId);
			workJp.setCreateTime(new Date());
			dbDao.insert(workJp);
			//日本财产信息
			TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
			wealthJp.setCreateTime(new Date());
			wealthJp.setApplicantId(applicantJpId);
			dbDao.insert(wealthJp);
			//护照信息
			TApplicantPassportEntity passport = new TApplicantPassportEntity();
			/*if (!Util.isEmpty(applicantForm.getSex())) {
				passport.setSex(applicantForm.getSex());
			}*/
			if (!Util.isEmpty(applicantForm.getFirstName())) {
				passport.setFirstName(applicantForm.getFirstName());
			}
			if (!Util.isEmpty(applicantForm.getLastName())) {
				passport.setLastName(applicantForm.getLastName());
			}
			passport.setApplicantId(applicantId);
			dbDao.insert(passport);
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
			DecimalFormat df = new DecimalFormat("#.00");
			order.setMoney(Double.valueOf(df.format(orderInfo.getMoney())).doubleValue());
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

		//日本订单信息
		TOrderJpEntity jporder = dbDao.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", orderInfo.getId()));
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

		Map<String, Object> customermap = JsonUtil.fromJson(customerInfo, Map.class);
		if (!Util.isEmpty(customermap.get("source"))) {
			//如果source=4是直客，保存到订单信息中
			if (Util.eq(customermap.get("source"), CustomerTypeEnum.ZHIKE.intKey())) {
				if (!Util.isEmpty(customermap.get("email"))) {
					order.setEmail(String.valueOf(customermap.get("email")));
				} else {
					order.setEmail(null);
				}
				if (!Util.isEmpty(customermap.get("linkman"))) {
					order.setLinkman(String.valueOf(customermap.get("linkman")));
				} else {
					order.setLinkman(null);
				}
				if (!Util.isEmpty(customermap.get("mobile"))) {
					order.setTelephone(String.valueOf(customermap.get("mobile")));
				} else {
					order.setTelephone(null);
				}
				if (!Util.isEmpty(customermap.get("name"))) {
					order.setComName(String.valueOf(customermap.get("name")));
				} else {
					order.setComName(null);
				}
				if (!Util.isEmpty(customermap.get("shortname"))) {
					order.setComShortName(String.valueOf(customermap.get("shortname")));
				} else {
					order.setComShortName(null);
				}
				order.setIsDirectCus(IsYesOrNoEnum.YES.intKey()); //1是直客
			} else {
				TCustomerEntity customer = dbDao.fetch(TCustomerEntity.class,
						new Long(order.getCustomerId()).intValue());
				order.setCustomerId(customer.getId());
				order.setIsDirectCus(IsYesOrNoEnum.NO.intKey()); //0不是直客
			}
		}
		dbDao.update(order);
		return null;
	}

	public Object saveAddOrderinfo(OrderEditDataForm orderInfo, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TOrderEntity orderEntity = new TOrderEntity();

		//判断是否为直客,直客时客户信息保存在订单中
		if (!Util.isEmpty(orderInfo.getSource())) {
			if (Util.eq(orderInfo.getSource(), CustomerTypeEnum.ZHIKE.intKey())) {
				orderEntity.setIsDirectCus(IsYesOrNoEnum.YES.intKey()); //1是直客
				if (!Util.isEmpty(orderInfo.getEmail())) {
					orderEntity.setEmail(orderInfo.getEmail());
				}
				if (!Util.isEmpty(orderInfo.getLinkman())) {
					orderEntity.setLinkman(orderInfo.getLinkman());
				}
				if (!Util.isEmpty(orderInfo.getMobile())) {
					orderEntity.setTelephone(orderInfo.getMobile());
				}
				if (!Util.isEmpty(orderInfo.getName())) {
					orderEntity.setComName(orderInfo.getName());
				}
				if (!Util.isEmpty(orderInfo.getShortname())) {
					orderEntity.setComShortName(orderInfo.getShortname());
				}
			} else {
				//不是直客时
				TCustomerEntity customer = dbDao.fetch(TCustomerEntity.class,
						Long.parseLong(orderInfo.getName().substring(0, (orderInfo.getName().length() - 1))));
				//订单信息
				orderEntity.setCustomerId(customer.getId());
				orderEntity.setIsDirectCus(IsYesOrNoEnum.NO.intKey());
			}
		}

		orderEntity.setComId(loginCompany.getId());
		orderEntity.setUserId(loginUser.getId());
		orderEntity.setCreateTime(new Date());
		//orderEntity.setCustomerId(customerId);
		if (!Util.isEmpty(orderInfo.getCityid())) {
			orderEntity.setCityId(orderInfo.getCityid());
		}
		if (!Util.isEmpty(orderInfo.getBacktripdate())) {
			/*Date godate = DateUtil.string2Date(DateUtil.Date2String(orderInfo.getBacktripdate()),
					DateUtil.FORMAT_YYYY_MM_DD);*/
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
			DecimalFormat df = new DecimalFormat("#.00");
			orderEntity.setMoney(Double.valueOf(df.format(orderInfo.getMoney())).doubleValue());
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
		orderEntity.setCreateTime(new Date());
		orderEntity.setStatus(JPOrderStatusEnum.PLACE_ORDER.intKey());
		dbDao.insert(orderEntity);
		Integer orderId = orderEntity.getId();
		TOrderLogsEntity orderLog = new TOrderLogsEntity();
		orderLog.setOrderId(orderId);
		orderLog.setCreateTime(new Date());
		orderLog.setUpdateTime(new Date());
		orderLog.setOpId(loginUser.getId());
		orderLog.setOrderStatus(orderEntity.getStatus());
		dbDao.insert(orderLog);

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
		if (!Util.isEmpty(orderInfo.getAppId())) {
			String appId = orderInfo.getAppId();
			String applicants = appId.substring(0, appId.length() - 1);
			Cnd appcnd = Cnd.NEW();
			appcnd.and("id", "in", applicants);
			List<TApplicantEntity> applicantInfo = dbDao.query(TApplicantEntity.class, appcnd, null);
			for (TApplicantEntity tApplicantEntity : applicantInfo) {
				TApplicantOrderJpEntity applicantOrderJp = dbDao.fetch(TApplicantOrderJpEntity.class,
						Cnd.where("applicantId", "=", tApplicantEntity.getId()));
				applicantOrderJp.setOrderId(orderJpId);
				dbDao.update(applicantOrderJp);
			}
		}
		return null;
	}

	public Object fetchOrder(Integer id) {
		Map<String, Object> result = Maps.newHashMap();
		//订单信息
		String orderSqlstr = sqlManager.get("orderJp_list_orderInfo_byOrderId");
		Sql orderSql = Sqls.create(orderSqlstr);
		orderSql.setParam("id", id);
		Record orderInfo = dbDao.fetch(orderSql);
		//格式化金额
		DecimalFormat df = new DecimalFormat("#.00");
		if (!Util.isEmpty(orderInfo.get("money"))) {
			orderInfo.put("money", df.format(orderInfo.get("money")));
		}
		//格式化日期
		DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		if (!Util.isEmpty(orderInfo.get("gotripdate"))) {
			Date goTripDate = (Date) orderInfo.get("gotripdate");
			orderInfo.put("gotripdate", format.format(goTripDate));
		}
		if (!Util.isEmpty(orderInfo.get("backtripdate"))) {
			Date backTripDate = (Date) orderInfo.get("backtripdate");
			orderInfo.put("backtripdate", format.format(backTripDate));
		}
		if (!Util.isEmpty(orderInfo.get("sendvisadate"))) {
			Date sendVisaDate = (Date) orderInfo.get("sendvisadate");
			orderInfo.put("sendvisadate", format.format(sendVisaDate));
		}
		if (!Util.isEmpty(orderInfo.get("outvisadate"))) {
			Date outVisaDate = (Date) orderInfo.get("outvisadate");
			orderInfo.put("outvisadate", format.format(outVisaDate));
		}

		result.put("orderInfo", orderInfo);
		//客户信息
		TCustomerEntity customerInfo = new TCustomerEntity();
		if (!Util.isEmpty(orderInfo.get("isDirectCus"))) {
			if (Util.eq(orderInfo.get("isDirectCus"), IsYesOrNoEnum.YES.intKey())) {//直客时，客户信息从订单中取
				customerInfo.setSource(CustomerTypeEnum.ZHIKE.intKey());
				customerInfo.setLinkman((String) orderInfo.get("linkman"));
				customerInfo.setMobile((String) orderInfo.get("telephone"));
				customerInfo.setName((String) orderInfo.get("comName"));
				customerInfo.setShortname((String) orderInfo.get("comShortName"));
				customerInfo.setEmail((String) orderInfo.get("email"));
			} else {//不是直客时，客户信息从客户信息表中取
				if (!Util.isEmpty(orderInfo.get("customerId"))) {
					Integer customerId = (Integer) orderInfo.get("customerId");
					TCustomerEntity customerEntity = dbDao
							.fetch(TCustomerEntity.class, new Long(customerId).intValue());
					customerInfo.setEmail(customerEntity.getEmail());
					customerInfo.setLinkman(customerEntity.getLinkman());
					customerInfo.setMobile(customerEntity.getMobile());
					customerInfo.setName(customerEntity.getName());
					customerInfo.setShortname(customerEntity.getShortname());
					for (CustomerTypeEnum customerTypeEnum : CustomerTypeEnum.values()) {
						if (customerEntity.getSource() == customerTypeEnum.intKey()) {
							customerInfo.setSource(customerTypeEnum.intKey());
						}
					}
				}
			}
		}
		result.put("customerInfo", customerInfo);
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
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		if (!Util.isEmpty(applicantEntity.getBirthday())) {
			Date birthday = applicantEntity.getBirthday();
			String birthdayStr = sdf.format(birthday);
			result.put("birthday", birthdayStr);
		}
		if (!Util.isEmpty(applicantEntity.getValidStartDate())) {
			Date validStartDate = applicantEntity.getValidStartDate();
			String validStartDateStr = sdf.format(validStartDate);
			result.put("validStartDate", validStartDateStr);
		}
		if (!Util.isEmpty(applicantEntity.getValidEndDate())) {
			Date validEndDate = applicantEntity.getValidEndDate();
			String validEndDateStr = sdf.format(validEndDate);
			result.put("validEndDate", validEndDateStr);
		}
		result.put("boyOrGirlEnum", EnumUtil.enum2(BoyOrGirlEnum.class));
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
		if (!Util.isEmpty(applicantForm.getCardFront())) {
			applicant.setCardFront(applicantForm.getCardFront());
		}
		if (!Util.isEmpty(applicantForm.getCardBack())) {
			applicant.setCardBack(applicantForm.getCardBack());
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
		return applicant;
	}

	public Object getEditApplicant(Integer orderid) {
		String applicantSqlstr = sqlManager.get("orderJp_list_applicantInfo_byOrderId");
		Sql applicantSql = Sqls.create(applicantSqlstr);
		applicantSql.setParam("id", orderid);
		List<Record> applicantInfo = dbDao.query(applicantSql, null, null);
		Integer mainId = (Integer) applicantInfo.get(0).get("id");
		TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, new Long(mainId).intValue());
		if (applicant.getMainId() == null) {
			applicant.setMainId(mainId);
			dbDao.update(applicant);
		}
		for (int i = 1; i < applicantInfo.size(); i++) {
			TApplicantEntity fetch = dbDao.fetch(TApplicantEntity.class,
					new Long((Integer) applicantInfo.get(i).get("id")).intValue());
			if (fetch.getMainId() == null) {
				fetch.setMainId(mainId);
				dbDao.update(fetch);
			}
		}
		List<Record> applicantInfoMainId = dbDao.query(applicantSql, null, null);
		for (int i = 0; i < applicantInfoMainId.size(); i++) {
			TApplicantOrderJpEntity applicantJp = dbDao.fetch(TApplicantOrderJpEntity.class,
					Cnd.where("applicantId", "=", new Long((Integer) applicantInfoMainId.get(i).get("id")).intValue()));
			if (Util.eq(applicantInfoMainId.get(i).get("id"), applicantInfoMainId.get(i).get("mainId"))) {
				applicantJp.setIsMainApplicant(IsYesOrNoEnum.YES.intKey());
			} else {
				applicantJp.setIsMainApplicant(IsYesOrNoEnum.NO.intKey());
			}
			dbDao.update(applicantJp);
		}
		return applicantInfoMainId;
	}

	public Object getVisaInfo(Integer id, Integer orderid) {
		Map<String, Object> result = MapUtil.map();
		result.put("mainOrVice", EnumUtil.enum2(MainOrViceEnum.class));
		result.put("isOrNo", EnumUtil.enum2(IsYesOrNoEnum.class));
		result.put("applicantRelation", EnumUtil.enum2(MainApplicantRelationEnum.class));
		result.put("applicantRemark", EnumUtil.enum2(MainApplicantRemarkEnum.class));
		result.put("jobStatusEnum", EnumUtil.enum2(JobStatusEnum.class));
		String visaInfoSqlstr = sqlManager.get("visaInfo_byApplicantId");
		Sql visaInfoSql = Sqls.create(visaInfoSqlstr);
		visaInfoSql.setParam("id", id);
		Record visaInfo = dbDao.fetch(visaInfoSql);
		//获取申请人
		TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class, new Long(id).intValue());
		TApplicantEntity mainApplicant = dbDao.fetch(TApplicantEntity.class, new Long(applicantEntity.getMainId()));
		//获取订单主申请人
		String sqlStr = sqlManager.get("mainApplicant_byOrderId");
		Sql applysql = Sqls.create(sqlStr);
		List<Record> records = dbDao
				.query(applysql,
						Cnd.where("oj.orderId", "=", orderid).and("aoj.isMainApplicant", "=",
								IsYesOrNoEnum.YES.intKey()), null);
		result.put("mainApplicant", mainApplicant);
		result.put("mainApply", records);
		result.put("visaInfo", visaInfo);
		return result;
	}

	public Object getEditPassport(Integer id) {
		Map<String, Object> result = MapUtil.map();
		String passportSqlstr = sqlManager.get("orderJp_list_passportInfo_byApplicantId");
		Sql passportSql = Sqls.create(passportSqlstr);
		passportSql.setParam("id", id);
		Record passport = dbDao.fetch(passportSql);
		//格式化日期
		DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		if (!Util.isEmpty(passport.get("birthday"))) {
			Date goTripDate = (Date) passport.get("birthday");
			passport.put("birthday", format.format(goTripDate));
		}
		if (!Util.isEmpty(passport.get("validEndDate"))) {
			Date goTripDate = (Date) passport.get("validEndDate");
			passport.put("validEndDate", format.format(goTripDate));
		}
		if (!Util.isEmpty(passport.get("issuedDate"))) {
			Date goTripDate = (Date) passport.get("issuedDate");
			passport.put("issuedDate", format.format(goTripDate));
		}
		result.put("passport", passport);
		result.put("passportType", EnumUtil.enum2(PassportTypeEnum.class));
		result.put("applicantId", id);
		return result;
	}

	public Object saveEditVisa(VisaEditDataForm visaForm) {

		return null;
	}

	public Object getShare(Integer id) {
		String applicantSqlstr = sqlManager.get("orderJp_list_applicantInfo_byOrderId");
		Sql applicantSql = Sqls.create(applicantSqlstr);
		applicantSql.setParam("id", id);
		List<Record> applicantInfo = dbDao.query(applicantSql, null, null);
		return applicantInfo;
	}

	public Object applicantComplete(int orderid) {
		String complete = "yes";
		String applicantSqlstr = sqlManager.get("orderJp_list_applicantInfo_byOrderId");
		Sql applicantSql = Sqls.create(applicantSqlstr);
		applicantSql.setParam("id", orderid);
		List<Record> applicantInfo = dbDao.query(applicantSql, null, null);
		for (Record record : applicantInfo) {
			String telephone = record.getString("telephone");
			String email = record.getString("email");
			if (Util.isEmpty(telephone) || Util.isEmpty(email)) {
				complete = "no";
			}
		}
		return complete;
	}

	public Object sendEmail(int orderid, int applicantid) {
		//发送短信、邮件
		try {
			sendMail(orderid, applicantid);
			sendMessage(orderid, applicantid);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object sendEmailUnified(int orderid, int applicantid) {
		try {
			sendMailUnified(orderid, applicantid);
			sendMessageUnified(orderid, applicantid);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	//发送邮件信息
	public Object sendMail(int orderid, int applicantid) throws IOException {
		List<String> readLines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream("share_mail.html"));
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
		String email = applicantEntity.getEmail();
		if (sex == null) {
			sex = "男/女";
		}
		String result = "";

		String emailText = tmp.toString();
		emailText = emailText.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum)
				.replace("${telephone}", telephone);
		result = mailService.send(email, emailText, "分享", MailService.Type.HTML);
		return result;
	}

	//发送手机信息
	public Object sendMessage(int orderid, int applicantid) throws IOException {
		List<String> readLines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream("share_sms.txt"));
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
		String email = applicantEntity.getEmail();
		if (sex == null) {
			sex = "男/女";
		}
		String result = "";

		String smsContent = tmp.toString();
		smsContent = smsContent.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum)
				.replace("${telephone}", telephone);
		result = firstTrialJpViewService.sendSMS(telephone, smsContent);
		return result;
	}

	//发送邮件信息
	public Object sendMailUnified(int orderid, int applicantid) throws IOException {
		List<String> readLines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream("share_mail.html"));
		StringBuilder tmp = new StringBuilder();
		for (String line : readLines) {
			tmp.append(line);
		}

		//查询订单号
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderid);
		String orderNum = order.getOrderNum();

		//申请人信息
		//接收邮件的申请人
		TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class, applicantid);
		String email = applicantEntity.getEmail();

		String applicantSqlstr = sqlManager.get("orderJp_list_applicantInfo_byOrderId");
		Sql applicantSql = Sqls.create(applicantSqlstr);
		applicantSql.setParam("id", orderid);
		List<Record> applicantInfo = dbDao.query(applicantSql, null, null);
		String result = "";

		for (Record record : applicantInfo) {
			String name = record.getString("applyname");
			String sex = record.getString("sex");
			if (sex == null) {
				sex = "男/女";
			}
			String telephone = record.getString("telephone");

			String emailText = tmp.toString();
			emailText = emailText.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum)
					.replace("${telephone}", telephone);
			result = mailService.send(email, emailText, "分享", MailService.Type.HTML);
		}
		return result;
	}

	//发送手机信息
	public Object sendMessageUnified(int orderid, int applicantid) throws IOException {
		List<String> readLines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream("share_sms.txt"));
		StringBuilder tmp = new StringBuilder();
		for (String line : readLines) {
			tmp.append(line);
		}

		//查询订单号
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderid);
		String orderNum = order.getOrderNum();

		//申请人信息
		//接收邮件的申请人
		TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class, applicantid);
		String telephone = applicantEntity.getTelephone();

		String applicantSqlstr = sqlManager.get("orderJp_list_applicantInfo_byOrderId");
		Sql applicantSql = Sqls.create(applicantSqlstr);
		applicantSql.setParam("id", orderid);
		List<Record> applicantInfo = dbDao.query(applicantSql, null, null);
		String result = "";

		for (Record record : applicantInfo) {
			String name = record.getString("applyname");
			String sex = record.getString("sex");
			if (sex == null) {
				sex = "男/女";
			}
			String telephoneLoad = record.getString("telephone");

			String smsContent = tmp.toString();
			smsContent = smsContent.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum)
					.replace("${telephone}", telephoneLoad);
			result = mailService.send(telephone, smsContent);
		}
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
		if (!Util.isEmpty(passportForm.getPassportUrl())) {
			passport.setPassportUrl(passportForm.getPassportUrl());
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
		if (!Util.isEmpty(passportForm.getId())) {
			//如果护照ID不为空，则说明为修改
			passport.setUpdateTime(new Date());
			dbDao.update(passport);
		} else {
			passport.setCreateTime(new Date());
			dbDao.insert(passport);
		}
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

		TApplicantOrderJpEntity applicantOrderJp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", id));
		if (!Util.isEmpty(applicantOrderJp)) {
			TApplicantWorkJpEntity applicantWorkJpEntity = dbDao.fetch(TApplicantWorkJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJp.getId()));
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJp.getId()));
			dbDao.delete(TApplicantWorkJpEntity.class, applicantWorkJpEntity.getId());
			dbDao.delete(TApplicantWealthJpEntity.class, applicantWealthJpEntity.getId());
			dbDao.delete(TApplicantOrderJpEntity.class, applicantOrderJp.getId());
		}
		dbDao.delete(TApplicantEntity.class, id);
		return null;
	}

	public Object getLogs(Integer orderid) {
		Map<String, Object> result = MapUtil.map();
		String name = "";
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, new Long(orderid).intValue());
		TOrderLogsEntity orderLogsEntity = dbDao.fetch(TOrderLogsEntity.class, Cnd.where("orderId", "=", orderid));
		TUserEntity userEntity = dbDao.fetch(TUserEntity.class, new Long(orderEntity.getUserId()).intValue());
		if (!Util.isEmpty(userEntity)) {
			name = userEntity.getName();
		}
		result.put("order", orderEntity);
		result.put("userName", name);
		return result;
	}

	public Object firtTrialJp(Integer id) {
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, new Long(id).intValue());
		orderEntity.setStatus(JPOrderStatusEnum.FIRSTTRIAL_ORDER.intKey());
		dbDao.update(orderEntity);
		return null;
	}

	public Object getApplicants(String applicantIds, HttpSession session) {
		String applicants = applicantIds.substring(0, applicantIds.length() - 1);
		String applicantSqlstr = sqlManager.get("orderJp_applicantTable");
		Sql applicantSql = Sqls.create(applicantSqlstr);
		Cnd appcnd = Cnd.NEW();
		appcnd.and("a.id", "in", applicants);
		applicantSql.setCondition(appcnd);
		List<Record> applicantInfo = dbDao.query(applicantSql, appcnd, null);
		Integer mainId = (Integer) applicantInfo.get(0).get("id");
		TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, new Long(mainId).intValue());
		if (applicant.getMainId() == null) {
			applicant.setMainId(mainId);
			dbDao.update(applicant);
		}
		for (int i = 1; i < applicantInfo.size(); i++) {
			TApplicantEntity fetch = dbDao.fetch(TApplicantEntity.class,
					new Long((Integer) applicantInfo.get(i).get("id")).intValue());
			if (fetch.getMainId() == null) {
				fetch.setMainId(mainId);
				dbDao.update(fetch);
			}
		}
		List<Record> applicantInfoMainId = dbDao.query(applicantSql, appcnd, null);
		for (int i = 0; i < applicantInfoMainId.size(); i++) {
			TApplicantOrderJpEntity applicantJp = dbDao.fetch(TApplicantOrderJpEntity.class,
					Cnd.where("applicantId", "=", new Long((Integer) applicantInfoMainId.get(i).get("id")).intValue()));
			if (Util.eq(applicantInfoMainId.get(i).get("id"), applicantInfoMainId.get(i).get("mainId"))) {
				applicantJp.setIsMainApplicant(IsYesOrNoEnum.YES.intKey());
			} else {
				applicantJp.setIsMainApplicant(IsYesOrNoEnum.NO.intKey());
			}
			dbDao.update(applicantJp);
		}
		return applicantInfoMainId;
	}

	public Object getLinkman(String linkman, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userType = loginUser.getUserType();
		List<TCustomerEntity> customerList = new ArrayList<>();
		//用户为公司管理员则显示该公司下所有客户
		if (userType == 5) {
			customerList = dbDao.query(
					TCustomerEntity.class,
					Cnd.where("compId", "=", loginCompany.getId()).and("linkman", "like",
							"%" + Strings.trim(linkman) + "%"), null);
		} else if (userType == 1) {
			customerList = dbDao.query(
					TCustomerEntity.class,
					Cnd.where("compId", "=", loginCompany.getId()).and("userId", "=", loginUser.getId())
							.and("linkman", "like", "%" + Strings.trim(linkman) + "%"), null);
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
			customerList = dbDao.query(
					TCustomerEntity.class,
					Cnd.where("compId", "=", loginCompany.getId()).and("mobile", "like",
							"%" + Strings.trim(mobile) + "%"), null);
		} else if (userType == 1) {
			customerList = dbDao.query(
					TCustomerEntity.class,
					Cnd.where("compId", "=", loginCompany.getId()).and("userId", "=", loginUser.getId())
							.and("mobile", "like", "%" + Strings.trim(mobile) + "%"), null);
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
			customerList = dbDao.query(
					TCustomerEntity.class,
					Cnd.where("compId", "=", loginCompany.getId()).and("name", "like",
							"%" + Strings.trim(compName) + "%"), null);
		} else if (userType == 1) {
			customerList = dbDao.query(
					TCustomerEntity.class,
					Cnd.where("compId", "=", loginCompany.getId()).and("userId", "=", loginUser.getId())
							.and("name", "like", "%" + Strings.trim(compName) + "%"), null);
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
							"%" + Strings.trim(comShortName) + "%"), null);
		} else if (userType == 1) {
			customerList = dbDao.query(
					TCustomerEntity.class,
					Cnd.where("compId", "=", loginCompany.getId()).and("userId", "=", loginUser.getId())
							.and("shortname", "like", "%" + Strings.trim(comShortName) + "%"), null);
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
					Cnd.where("compId", "=", loginCompany.getId())
							.and("email", "like", "%" + Strings.trim(email) + "%"), null);
		} else if (userType == 1) {
			customerList = dbDao.query(
					TCustomerEntity.class,
					Cnd.where("compId", "=", loginCompany.getId()).and("userId", "=", loginUser.getId())
							.and("email", "like", "%" + Strings.trim(email) + "%"), null);
		}
		return customerList;
	}

	public Object getCustomerById(Integer id, HttpSession session) {
		TCustomerEntity customerEntity = dbDao.fetch(TCustomerEntity.class, id.longValue());
		return customerEntity;
	}

	public Object IDCardRecognition(File file, HttpServletRequest request, HttpServletResponse response) {
		//上传
		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(file);
		file.delete();
		String url = CommonConstants.IMAGES_SERVER_ADDR + map.get("data");
		//从服务器上获取图片的流，读取扫描
		byte[] bytes = saveImageToDisk(url);
		String imageDataValue = Base64.encodeBase64String(bytes);
		Input input = new Input(imageDataValue, "face");
		RecognizeData rd = new RecognizeData();
		rd.getInputs().add(input);
		String content = Json.toJson(rd);
		String info = (String) appCodeCall(content);//扫描完毕
		//解析扫描的结果，结构化成标准json格式
		ApplicantJsonEntity jsonEntity = new ApplicantJsonEntity();
		JSONObject resultObj = new JSONObject(info);
		JSONArray outputArray = resultObj.getJSONArray("outputs");
		String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
		JSONObject out = new JSONObject(output);
		if (out.getBoolean("success")) {
			String addr = out.getString("address"); // 获取地址
			String name = out.getString("name"); // 获取名字
			String num = out.getString("num"); // 获取身份证号
			jsonEntity.setUrl(url);
			jsonEntity.setAddress(addr);
			Date date;
			try {
				date = new SimpleDateFormat("yyyyMMdd").parse(out.getString("birth"));
				String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				jsonEntity.setBirth(sdf.format(sdf.parse(dateStr)));
			} catch (JSONException | ParseException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			jsonEntity.setName(name);
			jsonEntity.setNationality(out.getString("nationality"));
			jsonEntity.setNum(num);
			jsonEntity.setRequest_id(out.getString("request_id"));
			jsonEntity.setSex(out.getString("sex"));
			jsonEntity.setSuccess(out.getBoolean("success"));
			String cardId = jsonEntity.getNum().substring(0, 6);
			TIdcardEntity IDcardEntity = dbDao.fetch(TIdcardEntity.class, Cnd.where("code", "=", cardId));
			if (!Util.isEmpty(IDcardEntity)) {
				jsonEntity.setProvince(IDcardEntity.getProvince());
				jsonEntity.setCity(IDcardEntity.getCity());
			}
		}
		return jsonEntity;
	}

	public Object IDCardRecognitionBack(File file, HttpServletRequest request, HttpServletResponse response) {
		//上传
		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(file);
		file.delete();
		String url = CommonConstants.IMAGES_SERVER_ADDR + map.get("data");
		//从服务器上获取图片的流，读取扫描
		byte[] bytes = saveImageToDisk(url);
		String imageDataValue = Base64.encodeBase64String(bytes);
		Input input = new Input(imageDataValue, "back");
		RecognizeData rd = new RecognizeData();
		rd.getInputs().add(input);
		String content = Json.toJson(rd);
		String info = (String) appCodeCall(content);//扫描完毕
		//解析扫描的结果，结构化成标准json格式
		ApplicantJsonEntity jsonEntity = new ApplicantJsonEntity();
		JSONObject resultObj = new JSONObject(info);
		JSONArray outputArray = resultObj.getJSONArray("outputs");
		String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
		JSONObject out = new JSONObject(output);
		if (out.getBoolean("success")) {
			String issue = out.getString("issue");
			jsonEntity.setIssue(issue);
			jsonEntity.setUrl(url);
			Date startDate;
			Date endDate;
			try {
				startDate = new SimpleDateFormat("yyyyMMdd").parse(out.getString("start_date"));
				endDate = new SimpleDateFormat("yyyyMMdd").parse(out.getString("end_date"));
				String startDateStr = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
				String endDateStr = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				jsonEntity.setStarttime(sdf.format(sdf.parse(startDateStr)));
				jsonEntity.setEndtime(sdf.format(sdf.parse(endDateStr)));
			} catch (JSONException | ParseException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			jsonEntity.setSuccess(out.getBoolean("success"));
		}
		return jsonEntity;
	}

	public Object passportRecognitionBack(File file, HttpServletRequest request, HttpServletResponse response) {
		//上传
		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(file);
		file.delete();
		String url = CommonConstants.IMAGES_SERVER_ADDR + map.get("data");
		//从服务器上获取图片的流，读取扫描
		byte[] bytes = saveImageToDisk(url);

		String imageDataB64 = Base64.encodeBase64String(bytes);
		Input input = new Input(imageDataB64);

		RecognizeData rd = new RecognizeData();
		rd.getInputs().add(input);

		String content = Json.toJson(rd);
		String info = (String) aliPassportOcrAppCodeCall(content);

		//解析扫描的结果，结构化成标准json格式
		PassportJsonEntity jsonEntity = new PassportJsonEntity();
		JSONObject resultObj = new JSONObject(info);
		JSONArray outputArray = resultObj.getJSONArray("outputs");
		String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
		JSONObject out = new JSONObject(output);
		if (out.getBoolean("success")) {
			String type = out.getString("type");
			jsonEntity.setType(type);
			jsonEntity.setNum(out.getString("passport_no"));
			if (out.getString("sex").equals("F")) {
				jsonEntity.setSex("女");
				jsonEntity.setSexEn("F");
			} else {
				jsonEntity.setSex("男");
				jsonEntity.setSexEn("M");
			}
			jsonEntity.setUrl(url);
			jsonEntity.setBirthCountry(out.getString("birth_place"));
			jsonEntity.setVisaCountry(out.getString("issue_place"));
			Date birthDay;
			Date expiryDate;
			Date issueDate;
			try {
				birthDay = new SimpleDateFormat("yyMMdd").parse(out.getString("birth_day"));
				expiryDate = new SimpleDateFormat("yyMMdd").parse(out.getString("expiry_day"));
				issueDate = new SimpleDateFormat("yyyyMMdd").parse(out.getString("issue_date"));
				String startDateStr = new SimpleDateFormat("yyyy-MM-dd").format(birthDay);
				String endDateStr = new SimpleDateFormat("yyyy-MM-dd").format(expiryDate);
				String issueDateStr = new SimpleDateFormat("yyyy-MM-dd").format(issueDate);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				jsonEntity.setBirth(sdf.format(sdf.parse(startDateStr)));
				jsonEntity.setExpiryDay(sdf.format(sdf.parse(endDateStr)));
				jsonEntity.setIssueDate(sdf.format(sdf.parse(issueDateStr)));
			} catch (JSONException | ParseException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			jsonEntity.setSuccess(out.getBoolean("success"));
		}
		return jsonEntity;
	}

	private static Object appCodeCall(String content) {
		String host = "https://dm-51.data.aliyun.com";
		String path = "/rest/160601/ocr/ocr_idcard.json";
		String method = "POST";
		String entityStr = "";
		String appcode = "36c5ae22ed87410290bd90cb198e47a7";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		//根据API的要求，定义相对应的Content-Type
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		try {
			/**
			 * 重要提示如下:
			 * HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 */
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, content);
			//获取response的body
			//System.out.println(EntityUtils.toString(response.getEntity()));
			entityStr = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entityStr;
	}

	private static Object aliPassportOcrAppCodeCall(String content) {
		String host = "http://ocrhz.market.alicloudapi.com";
		String path = "/rest/160601/ocr/ocr_passport.json";
		String method = "POST";
		String appcode = "db7570313ab4478793f42ad8cd48723b";
		String entityStr = "";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		//根据API的要求，定义相对应的Content-Type
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();

		try {
			/**
			 * 重要提示如下:
			 * HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 */
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, content);
			//System.out.println(response.toString());
			//获取response的body
			//System.out.println(EntityUtils.toString(response.getEntity()));
			entityStr = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entityStr;
	}

	//这个函数负责把获取到的InputStream流保存到本地。  
	public static byte[] saveImageToDisk(String filePath) {
		InputStream inputStream = null;
		inputStream = getInputStream(filePath);//调用getInputStream()函数。  
		byte[] data = new byte[1024];
		byte[] result = new byte[1024];
		int len = -1;
		ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
		try {
			while ((len = inputStream.read(data)) != -1) {//循环读取inputStream流中的数据，存入文件流fileOutputStream  
				fileOutputStream.write(data, 0, len);
			}
			result = fileOutputStream.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {//finally函数，不管有没有异常发生，都要调用这个函数下的代码  
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();//记得及时关闭文件流  
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();//关闭输入流  
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static InputStream getInputStream(String filePath) {
		InputStream inputStream = null;
		HttpURLConnection httpURLConnection = null;
		try {
			URL url = new URL(filePath);//创建的URL  
			if (url != null) {
				httpURLConnection = (HttpURLConnection) url.openConnection();//打开链接  
				httpURLConnection.setConnectTimeout(3000);//设置网络链接超时时间，3秒，链接失败后重新链接  
				httpURLConnection.setDoInput(true);//打开输入流  
				httpURLConnection.setRequestMethod("GET");//表示本次Http请求是GET方式  
				int responseCode = httpURLConnection.getResponseCode();//获取返回码  
				if (responseCode == 200) {//成功为200  
					//从服务器获得一个输入流  
					inputStream = httpURLConnection.getInputStream();
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}
}
