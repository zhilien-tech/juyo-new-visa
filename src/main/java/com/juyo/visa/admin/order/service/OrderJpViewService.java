/**
 * SaleViewService.java
 * com.juyo.visa.admin.sale.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
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
import java.util.UUID;

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

import com.google.common.collect.Maps;
import com.juyo.visa.admin.changePrincipal.service.ChangePrincipalViewService;
import com.juyo.visa.admin.firstTrialJp.service.FirstTrialJpViewService;
import com.juyo.visa.admin.firstTrialJp.service.QualifiedApplicantViewService;
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
import com.juyo.visa.common.base.QrCodeService;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.ApplicantInfoTypeEnum;
import com.juyo.visa.common.enums.ApplicantJpWealthEnum;
import com.juyo.visa.common.enums.BoyOrGirlEnum;
import com.juyo.visa.common.enums.CollarAreaEnum;
import com.juyo.visa.common.enums.CustomerTypeEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderProcessTypeEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.JapanPrincipalChangeEnum;
import com.juyo.visa.common.enums.JobStatusEnum;
import com.juyo.visa.common.enums.JobStatusFreeEnum;
import com.juyo.visa.common.enums.JobStatusPreschoolEnum;
import com.juyo.visa.common.enums.JobStatusRetirementEnum;
import com.juyo.visa.common.enums.JobStatusStudentEnum;
import com.juyo.visa.common.enums.JobStatusWorkingEnum;
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
import com.juyo.visa.common.enums.MarryStatusEnum;
import com.juyo.visa.common.enums.PassportTypeEnum;
import com.juyo.visa.common.enums.TrialApplicantStatusEnum;
import com.juyo.visa.common.enums.UserLoginEnum;
import com.juyo.visa.common.ocr.HttpUtils;
import com.juyo.visa.common.ocr.Input;
import com.juyo.visa.common.ocr.RecognizeData;
import com.juyo.visa.common.util.ImageDeal;
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
import com.juyo.visa.entities.TApplicanttTripJpEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TCountryEntity;
import com.juyo.visa.entities.TCustomerEntity;
import com.juyo.visa.entities.TOrderBackmailEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderLogsEntity;
import com.juyo.visa.entities.TTouristBaseinfoEntity;
import com.juyo.visa.entities.TTouristPassportEntity;
import com.juyo.visa.entities.TTouristVisaEntity;
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
	@Inject
	private QrCodeService qrCodeService;
	@Inject
	private QualifiedApplicantViewService qualifiedApplicantViewService;

	@Inject
	private ChangePrincipalViewService changePrincipalViewService;

	//基本信息连接websocket的地址
	private static final String BASIC_WEBSPCKET_ADDR = "basicinfowebsocket";

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
					TCustomerEntity customerEntity = new TCustomerEntity();
					Integer customerId = (Integer) record.get("customerId");
					if (!Util.isEmpty(customerId)) {
						customerEntity = dbDao.fetch(TCustomerEntity.class, new Long(customerId).intValue());
					}
					record.set("comName", customerEntity.getName());
					record.set("comShortName", customerEntity.getShortname());
					record.set("linkman", customerEntity.getLinkman());
					record.set("telephone", customerEntity.getMobile());
					if (!Util.isEmpty(record.get("source"))) {
						int sourceInt = (int) record.get("source");
						for (CustomerTypeEnum customerTypeEnum : CustomerTypeEnum.values()) {
							if (sourceInt == customerTypeEnum.intKey()) {
								record.put("source", customerTypeEnum.value());
							}
						}
					} else {
						record.put("source", "");
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
		result.put("pageTotal", pager.getPageCount());
		result.put("pageListCount", orderJp.size());
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
		result.put("orderId", id);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TOrderJpEntity orderJpinfo = dbDao.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", id.longValue()));
		result.put("orderJpId", orderJpinfo.getId());
		TOrderEntity orderInfo = dbDao.fetch(TOrderEntity.class, id.longValue());
		if (!Util.isEmpty(orderInfo.getCustomerId())) {
			customer = dbDao.fetch(TCustomerEntity.class, orderInfo.getCustomerId().longValue());
		} else {
			customer.setEmail(orderInfo.getEmail());
			customer.setLinkman(orderInfo.getLinkman());
			if (!Util.isEmpty(orderInfo.getIsDirectCus()) && Util.eq(orderInfo.getIsDirectCus(), 1)) {
				customer.setSource(CustomerTypeEnum.ZHIKE.intKey());
			} else {
				customer.setSource(null);
			}
			customer.setMobile(orderInfo.getTelephone());
			customer.setName(orderInfo.getComName());
			customer.setShortname(orderInfo.getComShortName());
		}
		int status = (int) orderInfo.getStatus();
		//int status = (int) record.get("status");
		for (JPOrderStatusEnum orderStatus : JPOrderStatusEnum.values()) {
			if (status == orderStatus.intKey()) {
				result.put("orderstatus", orderStatus.value());
			}
		}
		//回邮信息
		List<TOrderBackmailEntity> backinfo = dbDao.query(TOrderBackmailEntity.class, Cnd.where("orderId", "=", id)
				.orderBy("createTime", "DESC"), null);

		result.put("backinfo", backinfo);
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
		applicant.setIsSameInfo(IsYesOrNoEnum.YES.intKey());
		applicant.setIsPrompted(IsYesOrNoEnum.NO.intKey());
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
		if (!Util.isEmpty(applicantForm.getFirstNameEn())) {
			applicant.setFirstNameEn(applicantForm.getFirstNameEn().substring(1));
		}
		if (!Util.isEmpty(applicantForm.getOtherLastNameEn())) {
			applicant.setOtherLastNameEn(applicantForm.getOtherLastNameEn().substring(1));
		}
		if (!Util.isEmpty(applicantForm.getOtherFirstNameEn())) {
			applicant.setOtherFirstNameEn(applicantForm.getOtherFirstNameEn().substring(1));
		}
		applicant.setNationality(applicantForm.getNationality());
		applicant.setHasOtherName(applicantForm.getHasOtherName());
		applicant.setHasOtherNationality(applicantForm.getHasOtherNationality());
		applicant.setFirstName(applicantForm.getFirstName());
		applicant.setLastName(applicantForm.getLastName());
		applicant.setOtherFirstName(applicantForm.getOtherFirstName());
		applicant.setOtherLastName(applicantForm.getOtherLastName());
		applicant.setAddressIsSameWithCard(applicantForm.getAddressIsSameWithCard());
		if (!Util.isEmpty(applicantForm.getCardProvince())) {
			applicant.setCardProvince(applicantForm.getCardProvince());
		}
		if (!Util.isEmpty(applicantForm.getCardCity())) {
			applicant.setCardCity(applicantForm.getCardCity());
		}
		if (!Util.isEmpty(applicantForm.getIssueOrganization())) {
			applicant.setIssueOrganization(applicantForm.getIssueOrganization());
		}
		if (!Util.isEmpty(applicantForm.getLastNameEn())) {
			applicant.setLastNameEn(applicantForm.getLastNameEn().substring(1));
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
		//游客登录
		ApplicantUser applicantUser = new ApplicantUser();
		applicantUser.setMobile(applicant.getTelephone());
		applicantUser.setOpid(applicant.getOpId());
		applicantUser.setPassword("000000");
		applicantUser.setUsername(applicant.getFirstName() + applicant.getLastName());
		if (!Util.isEmpty(applicant.getTelephone())) {
			TUserEntity userEntity = dbDao.fetch(TUserEntity.class, Cnd.where("mobile", "=", applicant.getTelephone())
					.and("userType", "=", UserLoginEnum.TOURIST_IDENTITY.intKey()));
			if (Util.isEmpty(userEntity)) {
				TUserEntity tUserEntity = userViewService.addApplicantUser(applicantUser);
				applicant.setUserId(tUserEntity.getId());
			} else {
				userEntity.setName(applicantUser.getUsername());
				userEntity.setMobile(applicant.getTelephone());
				userEntity.setPassword(applicantUser.getPassword());
				userEntity.setOpId(applicantUser.getOpid());
				userEntity.setUpdateTime(new Date());
				applicant.setUserId(userEntity.getId());
				dbDao.update(userEntity);
			}
		}

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
			applicantOrderJp.setBaseIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantOrderJp.setPassIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantOrderJp.setVisaIsCompleted(IsYesOrNoEnum.NO.intKey());
			dbDao.insert(applicantOrderJp);
			Integer applicantJpId = applicantOrderJp.getId();
			//日本工作信息
			TApplicantWorkJpEntity workJp = new TApplicantWorkJpEntity();
			workJp.setApplicantId(applicantJpId);
			workJp.setCreateTime(new Date());
			workJp.setOpId(loginUser.getId());
			dbDao.insert(workJp);
			/*//日本财产信息
			TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
			wealthJp.setCreateTime(new Date());
			wealthJp.setApplicantId(applicantJpId);
			dbDao.insert(wealthJp);*/
			//护照信息
			TApplicantPassportEntity passport = new TApplicantPassportEntity();
			passport.setSex(applicantForm.getSex());
			passport.setFirstName(applicantForm.getFirstName());
			passport.setLastName(applicantForm.getLastName());
			if (!Util.isEmpty(applicantForm.getFirstNameEn())) {
				passport.setFirstNameEn(applicantForm.getFirstNameEn().substring(1));
			}
			if (!Util.isEmpty(applicantForm.getLastNameEn())) {
				passport.setLastNameEn(applicantForm.getLastNameEn().substring(1));
			}
			passport.setIssuedOrganization("公安部出入境管理局");
			passport.setIssuedOrganizationEn("MPS Exit&Entry Adiministration");
			passport.setApplicantId(applicantId);
			dbDao.insert(passport);
			return applicant;
		} else {
			TApplicantEntity applicantDB = dbDao.insert(applicant);
			Integer applicantId = applicantDB.getId();
			//日本申请人信息
			TApplicantOrderJpEntity applicantOrderJp = new TApplicantOrderJpEntity();
			applicantOrderJp.setApplicantId(applicantId);
			applicantOrderJp.setBaseIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantOrderJp.setPassIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantOrderJp.setVisaIsCompleted(IsYesOrNoEnum.NO.intKey());
			dbDao.insert(applicantOrderJp);
			Integer applicantJpId = applicantOrderJp.getId();
			//日本工作信息
			TApplicantWorkJpEntity workJp = new TApplicantWorkJpEntity();
			workJp.setApplicantId(applicantJpId);
			workJp.setCreateTime(new Date());
			workJp.setOpId(loginUser.getId());
			dbDao.insert(workJp);
			//日本财产信息
			/*TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
			wealthJp.setCreateTime(new Date());
			wealthJp.setApplicantId(applicantJpId);
			dbDao.insert(wealthJp);*/
			//护照信息
			TApplicantPassportEntity passport = new TApplicantPassportEntity();
			/*if (!Util.isEmpty(applicantForm.getSex())) {
				passport.setSex(applicantForm.getSex());
			}*/
			passport.setFirstName(applicantForm.getFirstName());
			passport.setLastName(applicantForm.getLastName());
			if (!Util.isEmpty(applicantForm.getFirstNameEn())) {
				passport.setFirstNameEn(applicantForm.getFirstNameEn().substring(1));
			}
			if (!Util.isEmpty(applicantForm.getLastNameEn())) {
				passport.setLastNameEn(applicantForm.getLastNameEn().substring(1));
			}
			passport.setIssuedOrganization("公安部出入境管理局");
			passport.setIssuedOrganizationEn("MPS Exit&Entry Adiministration");
			passport.setApplicantId(applicantId);
			dbDao.insert(passport);
			if (!Util.isEmpty(applicantForm.getOrderid())) {
				changePrincipalViewService.ChangePrincipal(applicantForm.getOrderid(),
						JPOrderProcessTypeEnum.SALES_PROCESS.intKey(), loginUser.getId());
			}
			return applicantDB;
		}
	}

	public Object saveOrder(OrderEditDataForm orderInfo, String customerInfo, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		//订单信息
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderInfo.getId().longValue());
		order.setNumber(orderInfo.getNumber());
		order.setCityId(orderInfo.getCityid());
		order.setUrgentType(orderInfo.getUrgenttype());
		order.setUrgentDay(orderInfo.getUrgentday());
		order.setTravel(orderInfo.getTravel());
		order.setPayType(orderInfo.getPaytype());
		if (!Util.isEmpty(orderInfo.getMoney())) {
			DecimalFormat df = new DecimalFormat("#.00");
			order.setMoney(Double.valueOf(df.format(orderInfo.getMoney())).doubleValue());
		}
		order.setGoTripDate(orderInfo.getGotripdate());
		order.setStayDay(orderInfo.getStayday());
		order.setBackTripDate(orderInfo.getBacktripdate());
		order.setSendVisaDate(orderInfo.getSendvisadate());
		order.setOutVisaDate(orderInfo.getOutvisadate());
		order.setUpdateTime(new Date());

		//日本订单信息
		TOrderJpEntity jporder = dbDao.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", orderInfo.getId()));
		jporder.setVisaType(orderInfo.getVisatype());
		jporder.setVisaCounty(orderInfo.getVisacounty());
		jporder.setIsVisit(orderInfo.getIsvisit());
		jporder.setThreeCounty(orderInfo.getThreecounty());
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
				if (!Util.isEmpty(customermap.get("id"))) {
					int cusId = (int) customermap.get("id");
					order.setCustomerId(cusId);
					order.setIsDirectCus(IsYesOrNoEnum.NO.intKey()); //0不是直客
				}
			}
		}
		dbDao.update(order);
		//回邮信息
		//List<TOrderBackmailEntity> backMailInfos = orderInfo.getBackMailInfos();
		//String editBackMailInfos = editBackMailInfos(backMailInfos, orderId);
		return null;
	}

	public Object saveAddOrderinfo(OrderEditDataForm orderInfo, HttpSession session) {
		TOrderEntity orderEntity = null;
		if (Util.isEmpty(orderInfo.getId())) {
			orderEntity = new TOrderEntity();
		} else {
			orderEntity = dbDao.fetch(TOrderEntity.class, orderInfo.getId().longValue());
		}
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);

		//判断是否为直客,直客时客户信息保存在订单中
		if (!Util.isEmpty(orderInfo.getCuSource())) {
			if (Util.eq(orderInfo.getCuSource(), CustomerTypeEnum.ZHIKE.intKey())) {
				orderEntity.setIsDirectCus(IsYesOrNoEnum.YES.intKey()); //1是直客
				if (!Util.isEmpty(orderInfo.getCusEmail())) {
					orderEntity.setEmail(orderInfo.getCusEmail());
				}
				if (!Util.isEmpty(orderInfo.getCusLinkman())) {
					orderEntity.setLinkman(orderInfo.getCusLinkman());
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
		if (!Util.isEmpty(orderInfo.getCusEmail())) {
			orderEntity.setEmail(orderInfo.getCusEmail());
		}
		if (!Util.isEmpty(orderInfo.getGotripdate())) {
			orderEntity.setGoTripDate(orderInfo.getGotripdate());
		}
		if (!Util.isEmpty(orderInfo.getCusLinkman())) {
			orderEntity.setLinkman(orderInfo.getCusLinkman());
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
		orderEntity.setUpdateTime(new Date());
		orderEntity.setIsDisabled(IsYesOrNoEnum.NO.intKey());
		if (Util.isEmpty(orderInfo.getId())) {
			dbDao.insert(orderEntity);
			Integer orderId = orderEntity.getId();
			//下单日志保存
			insertLogs(orderId, JPOrderStatusEnum.PLACE_ORDER.intKey(), session);
		} else {
			dbDao.update(orderEntity);
		}

		//日本订单信息
		TOrderJpEntity orderJpEntity = null;
		Integer orderId = orderEntity.getId();
		if (Util.isEmpty(orderInfo.getId())) {
			orderJpEntity = new TOrderJpEntity();
		} else {
			orderJpEntity = dbDao.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", orderEntity.getId()));
		}
		orderJpEntity.setOrderId(orderEntity.getId());
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
		if (Util.isEmpty(orderInfo.getId())) {
			dbDao.insert(orderJpEntity);
		} else {
			dbDao.update(orderJpEntity);
		}
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

		changePrincipalViewService.ChangePrincipal(orderEntity.getId(), JPOrderProcessTypeEnum.SALES_PROCESS.intKey(),
				loginUser.getId());
		//回邮信息
		List<TOrderBackmailEntity> backMailInfos = orderInfo.getBackMailInfos();
		String editBackMailInfos = editBackMailInfos(backMailInfos, orderId);
		return orderEntity;
	}

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
				customerInfo.setId(null);
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
					customerInfo.setId(customerEntity.getId());
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
		List<TOrderBackmailEntity> backinfo = dbDao.query(TOrderBackmailEntity.class, Cnd.where("orderId", "=", id)
				.orderBy("createTime", "DESC"), null);

		result.put("backinfo", backinfo);
		return result;
	}

	public Object updateApplicant(Integer id, Integer orderid, Integer isTrial, Integer orderProcessType, int addApply,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Map<String, Object> result = Maps.newHashMap();

		Integer userType = loginUser.getUserType();
		result.put("userType", userType);
		result.put("addApply", addApply);
		TApplicantOrderJpEntity applyJp = dbDao.fetch(TApplicantOrderJpEntity.class, Cnd.where("applicantId", "=", id));
		TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, applyJp.getOrderId().longValue());
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderJpEntity.getOrderId().longValue());

		if (isTrial == 0) {
			result.put("isTrailOrder", IsYesOrNoEnum.NO.intKey());
		} else {
			//初审环节操作
			result.put("isTrailOrder", IsYesOrNoEnum.YES.intKey());
		}
		//订单操作流程枚举
		result.put("orderProcessType", orderProcessType);

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

		if (!Util.isEmpty(applicantEntity.getFirstNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicantEntity.getFirstNameEn());
			result.put("firstNameEn", sb.toString());
		}
		if (!Util.isEmpty(applicantEntity.getOtherFirstNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicantEntity.getOtherFirstNameEn());
			result.put("otherFirstNameEn", sb.toString());
		}

		if (!Util.isEmpty(applicantEntity.getLastNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicantEntity.getLastNameEn());
			result.put("lastNameEn", sb.toString());
		}

		if (!Util.isEmpty(applicantEntity.getOtherLastNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicantEntity.getOtherLastNameEn());
			result.put("otherLastNameEn", sb.toString());
		}
		TApplicantUnqualifiedEntity unqualifiedEntity = dbDao.fetch(TApplicantUnqualifiedEntity.class,
				Cnd.where("applicantId", "=", id));
		if (!Util.isEmpty(unqualifiedEntity)) {
			result.put("unqualified", unqualifiedEntity);
		}
		String localAddr = request.getLocalAddr();
		int localPort = request.getLocalPort();
		result.put("localAddr", localAddr);
		result.put("localPort", localPort);
		result.put("websocketaddr", BASIC_WEBSPCKET_ADDR);
		//生成二维码
		String qrurl = "http://" + request.getLocalAddr() + ":" + request.getLocalPort()
				+ "/mobile/info.html?applicantid=" + id;
		String qrCode = qrCodeService.encodeQrCode(request, qrurl);
		result.put("qrCode", qrCode);

		result.put("orderStatus", orderEntity.getStatus());
		result.put("boyOrGirlEnum", EnumUtil.enum2(BoyOrGirlEnum.class));
		result.put("applicant", applicantEntity);
		result.put("orderJpId", orderJpEntity.getId());
		result.put("orderid", orderEntity.getId());
		result.put("infoType", ApplicantInfoTypeEnum.BASE.intKey());
		result.put("applicantId", id);
		return result;
	}

	//判断订单是否是进入初审环节
	public boolean isTrailOrder(Integer orderid) {
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderid.longValue());
		Integer status = order.getStatus();
		boolean istrial = false;
		if (status >= JPOrderStatusEnum.FIRSTTRIAL_ORDER.intKey() && status <= JPOrderStatusEnum.SEND_ADDRESS.intKey()) {
			istrial = true;
		}
		return istrial;
	}

	public Object saveEditApplicant(TApplicantForm applicantForm, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();

		Date nowDate = DateUtil.nowDate();
		if (!Util.isEmpty(applicantForm.getOrderid())
				&& Util.eq(applicantForm.getIsTrailOrder(), IsYesOrNoEnum.YES.intKey())) {
			dbDao.update(TOrderEntity.class, Chain.make("updateTime", nowDate),
					Cnd.where("id", "=", applicantForm.getOrderid()));
		}

		if (!Util.isEmpty(applicantForm.getId())) {
			TApplicantEntity applicant = dbDao
					.fetch(TApplicantEntity.class, new Long(applicantForm.getId()).intValue());

			TApplicantUnqualifiedEntity unqualifiedEntity = dbDao.fetch(TApplicantUnqualifiedEntity.class,
					Cnd.where("applicantId", "=", applicantForm.getId()));
			applicant.setOpId(loginUser.getId());
			applicant.setId(applicantForm.getId());
			applicant.setCardFront(applicantForm.getCardFront());
			applicant.setCardBack(applicantForm.getCardBack());
			applicant.setAddress(applicantForm.getAddress());
			applicant.setBirthday(applicantForm.getBirthday());
			if (!Util.isEmpty(applicantForm.getCardProvince())) {
				applicant.setCardProvince(applicantForm.getCardProvince());
			}
			if (!Util.isEmpty(applicantForm.getCardCity())) {
				applicant.setCardCity(applicantForm.getCardCity());
			}
			applicant.setCardId(applicantForm.getCardId());
			applicant.setCity(applicantForm.getCity());
			applicant.setDetailedAddress(applicantForm.getDetailedAddress());
			applicant.setEmail(applicantForm.getEmail());
			applicant.setFirstName(applicantForm.getFirstName());
			applicant.setFirstNameEn(applicantForm.getFirstNameEn().substring(1));
			applicant.setIssueOrganization(applicantForm.getIssueOrganization());
			applicant.setLastName(applicantForm.getLastName());
			applicant.setLastNameEn(applicantForm.getLastNameEn().substring(1));
			applicant.setOtherFirstName(applicantForm.getOtherFirstName());
			if (!Util.isEmpty(applicantForm.getOtherFirstNameEn())) {
				applicant.setOtherFirstNameEn(applicantForm.getOtherFirstNameEn().substring(1));
			}
			applicant.setOtherLastName(applicantForm.getOtherLastName());
			if (!Util.isEmpty(applicantForm.getOtherLastNameEn())) {
				applicant.setOtherLastNameEn(applicantForm.getOtherLastNameEn().substring(1));
			}
			applicant.setNation(applicantForm.getNation());
			applicant.setNationality(applicantForm.getNationality());
			applicant.setProvince(applicantForm.getProvince());
			applicant.setSex(applicantForm.getSex());
			applicant.setHasOtherName(applicantForm.getHasOtherName());
			applicant.setHasOtherNationality(applicantForm.getHasOtherNationality());

			applicant.setTelephone(applicantForm.getTelephone());
			if (!Util.isEmpty(applicantForm.getAddressIsSameWithCard())) {
				applicant.setAddressIsSameWithCard(applicantForm.getAddressIsSameWithCard());
			}
			applicant.setEmergencyLinkman(applicantForm.getEmergencyLinkman());
			applicant.setEmergencyTelephone(applicantForm.getEmergencyTelephone());
			applicant.setValidEndDate(applicantForm.getValidEndDate());
			applicant.setValidStartDate(applicantForm.getValidStartDate());
			applicant.setUpdateTime(new Date());
			//游客登录
			ApplicantUser applicantUser = new ApplicantUser();
			applicantUser.setMobile(applicant.getTelephone());
			applicantUser.setOpid(applicant.getOpId());
			applicantUser.setPassword("000000");
			applicantUser.setUsername(applicant.getFirstName() + applicant.getLastName());
			if (!Util.isEmpty(applicant.getTelephone())) {
				TUserEntity userEntity = dbDao.fetch(
						TUserEntity.class,
						Cnd.where("mobile", "=", applicant.getTelephone()).and("userType", "=",
								UserLoginEnum.TOURIST_IDENTITY.intKey()));
				if (Util.isEmpty(userEntity)) {
					TUserEntity tUserEntity = userViewService.addApplicantUser(applicantUser);
					applicant.setUserId(tUserEntity.getId());
				} else {
					userEntity.setName(applicantUser.getUsername());
					userEntity.setMobile(applicant.getTelephone());
					userEntity.setPassword(applicantUser.getPassword());
					userEntity.setOpId(applicantUser.getOpid());
					userEntity.setUpdateTime(new Date());
					applicant.setUserId(userEntity.getId());
					dbDao.update(userEntity);
				}
			}

			int update = dbDao.update(applicant);
			if (Util.eq(applicantForm.getUserType(), 2)) {//为游客时
				if (update > 0) {//说明保存成功，这时候必有userId
					TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
							Cnd.where("applicantId", "=", applicant.getId()));
					if (!Util.isEmpty(base)) {//不为空，说明有游客信息
						if (!Util.isEmpty(base.getUserId())) {//如果userId为空，把申请人的userId给游客,同时更新游客申请人ID，对应为最新的申请人

						} else {//如果为空，需要判断userId有没有被占用
							TTouristBaseinfoEntity uidBase = dbDao.fetch(TTouristBaseinfoEntity.class,
									Cnd.where("userId", "=", applicant.getUserId()));
							if (Util.isEmpty(uidBase)) {
								base.setUserId(applicant.getUserId());
								dbDao.update(base);
							}
						}
					}
				}
			}

			TApplicantOrderJpEntity applyJp = dbDao.fetch(TApplicantOrderJpEntity.class,
					Cnd.where("applicantId", "=", applicant.getId()));
			TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, applyJp.getOrderId().longValue());
			String baseRemark = applicantForm.getBaseRemark();
			if (!Util.isEmpty(baseRemark)) {
				qualifiedApplicantViewService.unQualified(applicant.getId(), orderJpEntity.getOrderId(), baseRemark,
						ApplicantInfoTypeEnum.BASE.intKey(), session);
			}
		}

		Integer orderProcessType = applicantForm.getOrderProcessType();
		Integer orderid = applicantForm.getOrderid();
		if (!Util.isEmpty(orderid)) {
			//订单操作人变更
			changePrincipalViewService.ChangePrincipal(orderid, orderProcessType, userId);
		}
		return null;
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

	public Object getVisaInfo(Integer id, Integer orderid, Integer isOrderUpTime, Integer isTrial,
			Integer orderProcessType, int addApply, HttpServletRequest request) {
		Map<String, Object> result = MapUtil.map();
		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);

		Integer userType = loginUser.getUserType();
		result.put("userType", userType);
		result.put("addApply", addApply);
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, id.longValue());
		TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", id));
		result.put("marryStatus", apply.getMarryStatus());
		result.put("marryUrl", apply.getMarryUrl());
		TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId()
				.longValue());
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderJpEntity.getOrderId().longValue());
		if (isTrial == 0) {
			result.put("isTrailOrder", IsYesOrNoEnum.NO.intKey());
		} else {
			//初审环节操作
			result.put("isTrailOrder", IsYesOrNoEnum.YES.intKey());
		}

		//订单流程枚举值
		result.put("orderProcessType", orderProcessType);

		if (!Util.isEmpty(orderid)) {
			result.put("orderid", orderid);
		} else {
			result.put("orderid", orderJpEntity.getOrderId());
		}
		result.put("marryStatusEnum", EnumUtil.enum2(MarryStatusEnum.class));
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
		result.put("applicant", applicantEntity);
		if (!Util.isEmpty(applicantEntity.getMainId())) {
			TApplicantEntity mainApplicant = dbDao.fetch(TApplicantEntity.class, new Long(applicantEntity.getMainId()));
			if (!Util.isEmpty(mainApplicant)) {
				result.put("mainApplicant", mainApplicant);
			}
		}
		TApplicantUnqualifiedEntity unqualifiedEntity = dbDao.fetch(TApplicantUnqualifiedEntity.class,
				Cnd.where("applicantId", "=", id));
		if (!Util.isEmpty(unqualifiedEntity)) {
			result.put("unqualified", unqualifiedEntity);
		}
		//获取订单主申请人
		String sqlStr = sqlManager.get("mainApplicant_byOrderId");
		Sql applysql = Sqls.create(sqlStr);
		List<Record> records = dbDao.query(
				applysql,
				Cnd.where("oj.orderId", "=", orderJpEntity.getOrderId()).and("aoj.isMainApplicant", "=",
						IsYesOrNoEnum.YES.intKey()), null);
		//获取日本申请人信息
		result.put("orderStatus", orderEntity.getStatus());
		result.put("orderJpId", orderJpEntity.getId());
		result.put("orderJp", applicantOrderJpEntity);
		result.put("infoType", ApplicantInfoTypeEnum.VISA.intKey());
		//获取财产信息
		String wealthsqlStr = sqlManager.get("wealth_byApplicantId");
		Sql wealthsql = Sqls.create(wealthsqlStr);
		wealthsql.setParam("id", applicantOrderJpEntity.getId());
		List<Record> wealthJp = dbDao.query(wealthsql, null, null);

		/*List<TApplicantWealthJpEntity> wealthJp = dbDao.query(TApplicantWealthJpEntity.class,
				Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()), null);*/
		result.put("wealthJp", wealthJp);
		//获取工作信息
		TApplicantWorkJpEntity applicantWorkJpEntity = dbDao.fetch(TApplicantWorkJpEntity.class,
				Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()));
		result.put("workJp", applicantWorkJpEntity);
		result.put("mainApply", records);
		result.put("visaInfo", visaInfo);
		result.put("isOrderUpTime", isOrderUpTime);
		//获取所访问的ip地址
		String localAddr = request.getLocalAddr();
		//所访问的端口
		int localPort = request.getLocalPort();
		result.put("localAddr", localAddr);
		result.put("localPort", localPort);
		result.put("websocketaddr", BASIC_WEBSPCKET_ADDR);
		return result;
	}

	public Object getEditPassport(Integer applicantId, Integer orderid, Integer isTrial, Integer orderProcessType,
			int addApply, HttpServletRequest request) {
		Map<String, Object> result = MapUtil.map();
		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);

		Integer userType = loginUser.getUserType();
		result.put("userType", userType);
		result.put("addApply", addApply);
		TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantId));
		TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId()
				.longValue());
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderJpEntity.getOrderId().longValue());
		if (isTrial == 0) {
			result.put("isTrailOrder", IsYesOrNoEnum.NO.intKey());
		} else {
			//初审环节操作
			result.put("isTrailOrder", IsYesOrNoEnum.YES.intKey());
		}

		//订单流程枚举
		result.put("orderProcessType", orderProcessType);

		String passportSqlstr = sqlManager.get("orderJp_list_passportInfo_byApplicantId");
		Sql passportSql = Sqls.create(passportSqlstr);
		passportSql.setParam("id", applicantId);
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
		TApplicantUnqualifiedEntity unqualifiedEntity = dbDao.fetch(TApplicantUnqualifiedEntity.class,
				Cnd.where("applicantId", "=", applicantId));
		if (!Util.isEmpty(unqualifiedEntity)) {
			result.put("unqualified", unqualifiedEntity);
		}
		result.put("passport", passport);
		result.put("passportType", EnumUtil.enum2(PassportTypeEnum.class));
		result.put("applicantId", applicantId);

		result.put("orderStatus", orderEntity.getStatus());
		result.put("orderJpId", orderJpEntity.getId());
		result.put("infoType", ApplicantInfoTypeEnum.PASSPORT.intKey());
		if (!Util.isEmpty(orderid)) {
			result.put("orderid", orderid);
		} else {
			result.put("orderid", orderJpEntity.getOrderId());
		}
		//所访问的ip地址
		String localAddr = request.getLocalAddr();
		result.put("localAddr", localAddr);
		//所访问的端口
		int localPort = request.getLocalPort();
		result.put("localPort", localPort);
		//websocket地址
		result.put("websocketaddr", BASIC_WEBSPCKET_ADDR);
		//生成二维码的URL
		String passporturl = "http://" + localAddr + ":" + localPort + "/mobile/passport.html?applicantid="
				+ applicantId;
		//生成二维码
		String qrCode = qrCodeService.encodeQrCode(request, passporturl);
		result.put("qrCode", qrCode);
		return result;
	}

	public Object saveEditVisa(VisaEditDataForm visaForm, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		Date nowDate = DateUtil.nowDate();
		if (!Util.isEmpty(visaForm.getIsOrderUpTime()) && !Util.isEmpty(visaForm.getOrderid())
				&& Util.eq(visaForm.getIsTrailOrder(), IsYesOrNoEnum.YES.intKey())) {
			dbDao.update(TOrderEntity.class, Chain.make("updateTime", nowDate),
					Cnd.where("id", "=", visaForm.getOrderid()));
		}

		//日本申请人
		if (!Util.isEmpty(visaForm.getApplicantId())) {
			//日本申请人
			TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
					Cnd.where("applicantId", "=", visaForm.getApplicantId()));
			//申请人
			TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class,
					new Long(applicantOrderJpEntity.getApplicantId()).intValue());
			applicantEntity.setMarryStatus(visaForm.getMarryStatus());
			applicantEntity.setMarryUrl(visaForm.getMarryUrl());
			applicantEntity.setMarryurltype(visaForm.getMarryStatus());
			//主申请人
			if (!Util.isEmpty(applicantEntity.getMainId())) {
				TApplicantEntity mainApplicant = dbDao.fetch(TApplicantEntity.class,
						new Long(applicantEntity.getMainId()).intValue());
			}
			//更新申请人信息
			if (Util.eq(visaForm.getApplicant(), MainOrViceEnum.YES.intKey())) {//是主申请人
				applicantEntity.setMainId(applicantEntity.getId());
				dbDao.update(applicantEntity);
			} else {
				if (!Util.isEmpty(visaForm.getMainApplicant())) {
					applicantEntity.setMainId(visaForm.getMainApplicant());
					dbDao.update(applicantEntity);
				}
			}
			if (Util.eq(applicantEntity.getId(), applicantEntity.getMainId())) {
				applicantOrderJpEntity.setIsMainApplicant(MainOrViceEnum.YES.intKey());
			} else {
				applicantOrderJpEntity.setIsMainApplicant(MainOrViceEnum.NO.intKey());
			}

			//更新日本申请人信息
			if (!Util.isEmpty(visaForm.getSameMainTrip())) {
				applicantOrderJpEntity.setSameMainTrip(visaForm.getSameMainTrip());
			}
			if (!Util.isEmpty(visaForm.getSameMainWealth())) {
				applicantOrderJpEntity.setSameMainWealth(visaForm.getSameMainWealth());
				//如果申请人跟主申请人的财产信息一样，把主申请人的财产信息保存到申请人财产信息中
				if (Util.eq(visaForm.getSameMainWealth(), IsYesOrNoEnum.YES.intKey())) {
					if (!Util.isEmpty(applicantEntity.getMainId())) {
						TApplicantEntity mainApplicant = dbDao.fetch(TApplicantEntity.class,
								new Long(applicantEntity.getMainId()).intValue());
						TApplicantOrderJpEntity mainAppyJp = dbDao.fetch(TApplicantOrderJpEntity.class,
								Cnd.where("applicantId", "=", mainApplicant.getId()));
						//获取主申请人的财产信息
						//银行存款
						TApplicantWealthJpEntity mainApplyWealthJp = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", mainAppyJp.getId()).and("type", "=",
										ApplicantJpWealthEnum.BANK.value()));
						TApplicantWealthJpEntity applyWealthJp = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.BANK.value()));
						if (!Util.isEmpty(mainApplyWealthJp)) {
							if (!Util.isEmpty(applyWealthJp)) {//如果申请人有银行存款信息，则更新
								if (!Util.isEmpty(mainApplyWealthJp.getDetails())) {
									applyWealthJp.setDetails(mainApplyWealthJp.getDetails());
									applyWealthJp.setApplicantId(applicantOrderJpEntity.getId());
									applyWealthJp.setOpId(loginUser.getId());
									applyWealthJp.setUpdateTime(new Date());
									dbDao.update(applyWealthJp);
								}
							} else {//没有则添加
								TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
								applyWealth.setType("银行存款");
								applyWealth.setDetails(mainApplyWealthJp.getDetails());
								applyWealth.setApplicantId(applicantOrderJpEntity.getId());
								applyWealth.setOpId(loginUser.getId());
								applyWealth.setCreateTime(new Date());
								dbDao.insert(applyWealth);
							}
						}
						//车产
						TApplicantWealthJpEntity applicantWealthJpCar = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.CAR.value()));
						TApplicantWealthJpEntity mainApplyWealthJpCar = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", mainAppyJp.getId()).and("type", "=",
										ApplicantJpWealthEnum.CAR.value()));
						if (!Util.isEmpty(mainApplyWealthJpCar)) {
							if (!Util.isEmpty(applicantWealthJpCar)) {//如果申请人有银行存款信息，则更新
								if (!Util.isEmpty(mainApplyWealthJpCar.getDetails())) {
									applicantWealthJpCar.setDetails(mainApplyWealthJpCar.getDetails());
									applicantWealthJpCar.setApplicantId(applicantOrderJpEntity.getId());
									applicantWealthJpCar.setOpId(loginUser.getId());
									applicantWealthJpCar.setUpdateTime(new Date());
									dbDao.update(applicantWealthJpCar);
								}
							} else {
								TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
								applyWealth.setType("车产");
								applyWealth.setDetails(mainApplyWealthJpCar.getDetails());
								applyWealth.setApplicantId(applicantOrderJpEntity.getId());
								applyWealth.setOpId(loginUser.getId());
								applyWealth.setCreateTime(new Date());
								dbDao.insert(applyWealth);
							}
						}

						//房产
						TApplicantWealthJpEntity applicantWealthJpHome = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.HOME.value()));
						TApplicantWealthJpEntity mainApplyWealthJpHome = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", mainAppyJp.getId()).and("type", "=",
										ApplicantJpWealthEnum.HOME.value()));
						if (!Util.isEmpty(mainApplyWealthJpHome)) {
							if (!Util.isEmpty(applicantWealthJpHome)) {//如果申请人有银行存款信息，则更新
								if (!Util.isEmpty(mainApplyWealthJpHome.getDetails())) {
									applicantWealthJpHome.setDetails(mainApplyWealthJpHome.getDetails());
									applicantWealthJpHome.setApplicantId(applicantOrderJpEntity.getId());
									applicantWealthJpHome.setOpId(loginUser.getId());
									applicantWealthJpHome.setUpdateTime(new Date());
									dbDao.update(applicantWealthJpHome);
								}
							} else {
								TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
								applyWealth.setType("房产");
								applyWealth.setDetails(mainApplyWealthJpHome.getDetails());
								applyWealth.setApplicantId(applicantOrderJpEntity.getId());
								applyWealth.setOpId(loginUser.getId());
								applyWealth.setCreateTime(new Date());
								dbDao.insert(applyWealth);
							}
						}

						//理财
						TApplicantWealthJpEntity applicantWealthJpLi = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.LICAI.value()));
						TApplicantWealthJpEntity mainApplyWealthJpLi = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", mainAppyJp.getId()).and("type", "=",
										ApplicantJpWealthEnum.LICAI.value()));
						if (!Util.isEmpty(mainApplyWealthJpLi)) {
							if (!Util.isEmpty(applicantWealthJpLi)) {//如果申请人有银行存款信息，则更新
								if (!Util.isEmpty(mainApplyWealthJpLi.getDetails())) {
									applicantWealthJpLi.setDetails(mainApplyWealthJpLi.getDetails());
									applicantWealthJpLi.setApplicantId(applicantOrderJpEntity.getId());
									applicantWealthJpLi.setOpId(loginUser.getId());
									applicantWealthJpLi.setUpdateTime(new Date());
									dbDao.update(applicantWealthJpLi);
								}
							} else {
								TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
								applyWealth.setType("理财");
								applyWealth.setDetails(mainApplyWealthJpLi.getDetails());
								applyWealth.setApplicantId(applicantOrderJpEntity.getId());
								applyWealth.setOpId(loginUser.getId());
								applyWealth.setCreateTime(new Date());
								dbDao.insert(applyWealth);
							}
						}

					}
				} else {
					//添加财产信息
					TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
					wealthJp.setApplicantId(applicantOrderJpEntity.getId());
					//银行存款
					if (!Util.isEmpty(visaForm.getDeposit())) {
						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.BANK.value()));
						if (!Util.isEmpty(applicantWealthJpEntity)) {
							applicantWealthJpEntity.setDetails(visaForm.getDeposit());
							dbDao.update(applicantWealthJpEntity);
						} else {
							wealthJp.setDetails(visaForm.getDeposit());
							wealthJp.setType(ApplicantJpWealthEnum.BANK.value());
							wealthJp.setCreateTime(new Date());
							wealthJp.setOpId(loginUser.getId());
							dbDao.insert(wealthJp);
						}
					} else {
						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.BANK.value()));
						if (!Util.isEmpty(applicantWealthJpEntity)) {
							dbDao.delete(applicantWealthJpEntity);
						}
					}
					//车产
					if (!Util.isEmpty(visaForm.getVehicle())) {
						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.CAR.value()));
						if (!Util.isEmpty(applicantWealthJpEntity)) {
							applicantWealthJpEntity.setDetails(visaForm.getVehicle());
							dbDao.update(applicantWealthJpEntity);
						} else {
							wealthJp.setDetails(visaForm.getVehicle());
							wealthJp.setType(ApplicantJpWealthEnum.CAR.value());
							wealthJp.setCreateTime(new Date());
							wealthJp.setOpId(loginUser.getId());
							dbDao.insert(wealthJp);
						}
					} else {
						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.CAR.value()));
						if (!Util.isEmpty(applicantWealthJpEntity)) {
							dbDao.delete(applicantWealthJpEntity);
						}
					}
					//房产
					if (!Util.isEmpty(visaForm.getHouseProperty())) {
						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.HOME.value()));
						if (!Util.isEmpty(applicantWealthJpEntity)) {
							applicantWealthJpEntity.setDetails(visaForm.getHouseProperty());
							dbDao.update(applicantWealthJpEntity);
						} else {
							wealthJp.setDetails(visaForm.getHouseProperty());
							wealthJp.setType(ApplicantJpWealthEnum.HOME.value());
							wealthJp.setCreateTime(new Date());
							wealthJp.setOpId(loginUser.getId());
							dbDao.insert(wealthJp);
						}
					} else {
						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.HOME.value()));
						if (!Util.isEmpty(applicantWealthJpEntity)) {
							dbDao.delete(applicantWealthJpEntity);
						}
					}
					//理财
					if (!Util.isEmpty(visaForm.getFinancial())) {
						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.LICAI.value()));
						if (!Util.isEmpty(applicantWealthJpEntity)) {
							applicantWealthJpEntity.setDetails(visaForm.getFinancial());
							dbDao.update(applicantWealthJpEntity);
						} else {
							wealthJp.setDetails(visaForm.getFinancial());
							wealthJp.setType(ApplicantJpWealthEnum.LICAI.value());
							wealthJp.setCreateTime(new Date());
							wealthJp.setOpId(loginUser.getId());
							dbDao.insert(wealthJp);
						}
					} else {
						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
								TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
										ApplicantJpWealthEnum.LICAI.value()));
						if (!Util.isEmpty(applicantWealthJpEntity)) {
							dbDao.delete(applicantWealthJpEntity);
						}
					}
				}
			}
			//更新工作信息
			TApplicantWorkJpEntity applicantWorkJpEntity = dbDao.fetch(TApplicantWorkJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()));
			Integer careerStatus = visaForm.getCareerStatus();
			if (!Util.isEmpty(careerStatus)) {
				toUpdateWorkJp(careerStatus, applicantWorkJpEntity, applicantOrderJpEntity, session);
			} else {
				Integer applicantJpId = applicantOrderJpEntity.getId();
				List<TApplicantFrontPaperworkJpEntity> frontListDB = dbDao.query(
						TApplicantFrontPaperworkJpEntity.class, Cnd.where("applicantId", "=", applicantJpId), null);
				List<TApplicantVisaPaperworkJpEntity> visaListDB = dbDao.query(TApplicantVisaPaperworkJpEntity.class,
						Cnd.where("applicantId", "=", applicantJpId), null);
				if (!Util.isEmpty(frontListDB)) {//如果库中有数据，则删掉
					dbDao.delete(frontListDB);
				}
				if (!Util.isEmpty(visaListDB)) {
					dbDao.delete(visaListDB);
				}
				applicantWorkJpEntity.setPrepareMaterials(null);
			}
			applicantWorkJpEntity.setCareerStatus(visaForm.getCareerStatus());
			applicantWorkJpEntity.setName(visaForm.getName());
			applicantWorkJpEntity.setAddress(visaForm.getAddress());
			applicantWorkJpEntity.setTelephone(visaForm.getTelephone());
			applicantWorkJpEntity.setUpdateTime(new Date());
			dbDao.update(applicantWorkJpEntity);
			if (!Util.isEmpty(visaForm.getMainRelation())) {
				applicantOrderJpEntity.setMainRelation(visaForm.getMainRelation());
			} else {
				applicantOrderJpEntity.setMainRelation(null);
			}
			if (!Util.isEmpty(visaForm.getRelationRemark())) {
				applicantOrderJpEntity.setRelationRemark(visaForm.getRelationRemark());
			} else {
				applicantOrderJpEntity.setRelationRemark(null);
			}
			//applicantOrderJpEntity.setMarryStatus(visaForm.getMarryStatus());
			//applicantOrderJpEntity.setMarryUrl(visaForm.getMarryUrl());
			int update = dbDao.update(applicantOrderJpEntity);
			if (Util.eq(visaForm.getUserType(), 2)) {
				if (update > 0) {//说明保存成功，这时候必有userId
					TTouristVisaEntity visa = dbDao.fetch(TTouristVisaEntity.class,
							Cnd.where("applicantId", "=", applicantEntity.getId()));
					if (!Util.isEmpty(visa)) {//不为空，说明有游客信息
						if (!Util.isEmpty(visa.getUserId())) {//如果userId为空，把申请人的userId给游客,同时更新游客申请人ID，对应为最新的申请人

						} else {//如果为空，需要判断userId有没有被占用
							TTouristVisaEntity uidVisa = dbDao.fetch(TTouristVisaEntity.class,
									Cnd.where("userId", "=", applicantEntity.getUserId()));
							if (Util.isEmpty(uidVisa)) {
								visa.setUserId(applicantEntity.getUserId());
								dbDao.update(visa);
							}
						}
					}
				}
			}

		}
		TApplicantOrderJpEntity applyJp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", visaForm.getApplicantId()));
		TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, applyJp.getOrderId().longValue());
		String visaRemark = visaForm.getVisaRemark();
		if (!Util.isEmpty(visaRemark)) {
			qualifiedApplicantViewService.unQualified(visaForm.getApplicantId(), orderJpEntity.getOrderId(),
					visaRemark, ApplicantInfoTypeEnum.VISA.intKey(), session);
		}

		//订单负责人变更
		Integer orderProcessType = visaForm.getOrderProcessType();
		Integer orderId = visaForm.getOrderid();
		changePrincipalViewService.ChangePrincipal(orderId, orderProcessType, userId);
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

	public Object getInfoByCard(String cardId) {
		if (!Util.isEmpty(cardId) && cardId.length() >= 6) {
			String substring = cardId.substring(0, 6);
			return dbDao.fetch(TIdcardEntity.class, Cnd.where("code", "=", substring));
		} else {
			return null;
		}
	}

	public Object getAllInfoByCard(String cardId) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("province", dbDao.fetch(TIdcardEntity.class, Cnd.where("code", "=", cardId.substring(0, 6))));
		Date birthday;
		try {
			birthday = new SimpleDateFormat("yyyyMMdd").parse(cardId.substring(6, 14));
			String birthdayDateStr = new SimpleDateFormat("yyyy-MM-dd").format(birthday);
			result.put("birthday", birthdayDateStr);
		} catch (ParseException e) {
			e.printStackTrace();

		}
		String sexStr = cardId.substring(16, 17);
		int sexInt = Integer.parseInt(sexStr);
		if (sexInt % 2 == 0) {//偶数是女性
			result.put("sex", "女");
		} else {
			result.put("sex", "男");
		}
		return result;
	}

	public Object getNationality(String searchStr) {
		List<String> countryList = new ArrayList<>();
		List<TCountryEntity> country = dbDao.query(TCountryEntity.class,
				Cnd.where("chinesename", "like", "%" + Strings.trim(searchStr) + "%"), null);
		for (TCountryEntity tCountry : country) {
			if (!countryList.contains(tCountry.getChinesename())) {
				countryList.add(tCountry.getChinesename());
			}
		}
		List<String> list = new ArrayList<>();
		if (!Util.isEmpty(countryList) && countryList.size() >= 5) {
			for (int i = 0; i < 5; i++) {
				list.add(countryList.get(i));
			}
			return list;
		} else {
			return countryList;
		}
	}

	public Object getProvince(String searchStr) {
		List<String> provinceList = new ArrayList<>();
		List<TIdcardEntity> province = dbDao.query(TIdcardEntity.class,
				Cnd.where("province", "like", "%" + Strings.trim(searchStr) + "%"), null);
		for (TIdcardEntity tIdcardEntity : province) {
			if (!provinceList.contains(tIdcardEntity.getProvince())) {
				provinceList.add(tIdcardEntity.getProvince());
			}
		}
		List<String> list = new ArrayList<>();
		if (!Util.isEmpty(provinceList) && provinceList.size() >= 5) {
			for (int i = 0; i < 5; i++) {
				list.add(provinceList.get(i));
			}
			return list;
		} else {
			return provinceList;
		}
	}

	public Object getCity(String province, String searchStr) {
		List<String> cityList = new ArrayList<>();
		List<TIdcardEntity> city = dbDao.query(TIdcardEntity.class,
				Cnd.where("province", "=", province).and("city", "like", "%" + Strings.trim(searchStr) + "%"), null);
		for (TIdcardEntity tIdcardEntity : city) {
			if (!cityList.contains(tIdcardEntity.getCity())) {
				cityList.add(tIdcardEntity.getCity());
			}
		}
		List<String> list = new ArrayList<>();
		if (!Util.isEmpty(cityList) && cityList.size() >= 5) {
			for (int i = 0; i < 5; i++) {
				list.add(cityList.get(i));
			}
			return list;
		} else {
			return cityList;
		}
	}

	public Object sendEmail(int orderid, String applicantid, HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = MapUtil.map();
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderid);
		TOrderJpEntity orderJpEntity = dbDao
				.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", orderEntity.getId()));
		List<TApplicantOrderJpEntity> applyListDB = dbDao.query(TApplicantOrderJpEntity.class,
				Cnd.where("orderId", "=", orderJpEntity.getId()), null);
		for (TApplicantOrderJpEntity tApplicantOrderJpEntity : applyListDB) {
			tApplicantOrderJpEntity.setIsShareSms(0);
			tApplicantOrderJpEntity.setIsSameLinker(0);
			dbDao.update(tApplicantOrderJpEntity);
		}
		//发送短信、邮件
		int sendCount = 0;
		String applicants = applicantid.substring(0, applicantid.length() - 1);
		String applicantSqlstr = sqlManager.get("orderJp_applicantTable");
		Sql applicantSql = Sqls.create(applicantSqlstr);
		Cnd appcnd = Cnd.NEW();
		appcnd.and("a.id", "in", applicants);
		applicantSql.setCondition(appcnd);
		List<Record> applicantInfo = dbDao.query(applicantSql, appcnd, null);
		for (Record record : applicantInfo) {
			if (!Util.isEmpty(record.get("id"))) {
				int applicantId = (int) record.get("id");
				try {
					String sendMail = (String) sendMail(orderid, applicantId, request);
					String sendMessage = (String) sendMessage(orderid, applicantId, request);
					if (Util.eq(sendMail, "success") && Util.eq(sendMessage, "发送成功")) {
						sendCount++;
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (Util.eq(sendCount, applicantInfo.size())) {//分享完毕
			List<TApplicantOrderJpEntity> listDB = dbDao.query(TApplicantOrderJpEntity.class,
					Cnd.where("applicantId", "in", applicants), null);
			for (TApplicantOrderJpEntity tApplicantOrderJpEntity : listDB) {
				tApplicantOrderJpEntity.setIsShareSms(1);
				dbDao.update(tApplicantOrderJpEntity);
			}
			insertLogs(orderid, JPOrderStatusEnum.SHARE.intKey(), session);
			if (orderEntity.getStatus() <= JPOrderStatusEnum.SHARE.intKey()) {
				orderEntity.setStatus(JPOrderStatusEnum.SHARE.intKey());
				orderEntity.setUpdateTime(new Date());
				dbDao.update(orderEntity);
			}
			result.put("sendResult", "success");

			/*//创建游客基本信息、护照信息、签证信息
			for (TApplicantOrderJpEntity tApplicantOrderJpEntity : applyListDB) {
				TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class, tApplicantOrderJpEntity
						.getApplicantId().longValue());
				TTouristBaseinfoEntity touristBaseDB = dbDao.fetch(TTouristBaseinfoEntity.class,
						Cnd.where("userId", "=", applicantEntity.getUserId()));
				TTouristPassportEntity touristPassDB = dbDao.fetch(TTouristPassportEntity.class,
						Cnd.where("userId", "=", applicantEntity.getUserId()));
				TTouristVisaEntity touristVisaDB = dbDao.fetch(TTouristVisaEntity.class,
						Cnd.where("userId", "=", applicantEntity.getUserId()));
				//如果游客基本信息为空，则创建
				if (Util.isEmpty(touristBaseDB)) {
					TTouristBaseinfoEntity base = new TTouristBaseinfoEntity();
					base.setUserId(applicantEntity.getUserId());
					base.setApplicantId(applicantEntity.getId());
					dbDao.insert(base);
				}
				if (Util.isEmpty(touristPassDB)) {
					TTouristPassportEntity pass = new TTouristPassportEntity();
					pass.setUserId(applicantEntity.getUserId());
					pass.setApplicantId(applicantEntity.getId());
					dbDao.insert(pass);
				}
				if (Util.isEmpty(touristVisaDB)) {
					TTouristVisaEntity visa = new TTouristVisaEntity();
					visa.setUserId(applicantEntity.getUserId());
					visa.setApplicantId(applicantEntity.getId());
					dbDao.insert(visa);
				}
			}*/
		}
		return result;
	}

	public Object shareComplete(Integer orderid, HttpSession session) {
		insertLogs(orderid, JPOrderStatusEnum.SHARE.intKey(), session);
		return null;
	}

	public Object sendEmailUnified(int orderid, int applicantid, HttpSession session, HttpServletRequest request) {
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderid);
		TOrderJpEntity orderJpEntity = dbDao
				.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", orderEntity.getId()));
		List<TApplicantOrderJpEntity> applyListDB = dbDao.query(TApplicantOrderJpEntity.class,
				Cnd.where("orderId", "=", orderJpEntity.getId()), null);
		//发送邮箱、短信之前设置申请人发送状态为未发送0，不是同一联系人0
		for (TApplicantOrderJpEntity tApplicantOrderJpEntity : applyListDB) {
			tApplicantOrderJpEntity.setIsShareSms(0);
			tApplicantOrderJpEntity.setIsSameLinker(0);
			dbDao.update(tApplicantOrderJpEntity);
		}
		try {
			String sendMailUnified = (String) sendMailUnified(orderid, applicantid, request);
			String sendMessageUnified = (String) sendMessageUnified(orderid, applicantid, request);
			if (Util.eq(sendMailUnified, "success") && Util.eq(sendMessageUnified, "发送成功")) {
				List<TApplicantOrderJpEntity> listDB = dbDao.query(TApplicantOrderJpEntity.class,
						Cnd.where("orderId", "=", orderJpEntity.getId()).and("applicantId", "!=", applicantid), null);
				for (TApplicantOrderJpEntity tApplicantOrderJpEntity : listDB) {
					//发送成功后将除发送者以外的申请人的状态更新为已发送状态1
					tApplicantOrderJpEntity.setIsShareSms(1);
					dbDao.update(tApplicantOrderJpEntity);
				}
				//将统一发送者的状态更新为统一联系人1，已发送状态1
				TApplicantOrderJpEntity mainApply = dbDao.fetch(TApplicantOrderJpEntity.class,
						Cnd.where("applicantId", "=", applicantid));
				mainApply.setIsSameLinker(1);
				mainApply.setIsShareSms(1);
				dbDao.update(mainApply);
				//添加分享日志
				insertLogs(orderid, JPOrderStatusEnum.SHARE.intKey(), session);
				//改变订单状态
				if (orderEntity.getStatus() <= JPOrderStatusEnum.SHARE.intKey()) {
					orderEntity.setStatus(JPOrderStatusEnum.SHARE.intKey());
					orderEntity.setUpdateTime(new Date());
					dbDao.update(orderEntity);
				}
				/*//给游客基本信息和护照信息赋值
				for (TApplicantOrderJpEntity tApplicantOrderJpEntity : applyListDB) {
					TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class, tApplicantOrderJpEntity
							.getApplicantId().longValue());
					if (Util.isEmpty(applicantEntity.getUserId())) {
						TTouristBaseinfoEntity baseDB = dbDao.fetch(TTouristBaseinfoEntity.class,
								Cnd.where("applicantId", "=", applicantEntity.getId()));
						TTouristPassportEntity passDB = dbDao.fetch(TTouristPassportEntity.class,
								Cnd.where("applicantId", "=", applicantEntity.getId()));
						TTouristVisaEntity visaDB = dbDao.fetch(TTouristVisaEntity.class,
								Cnd.where("applicantId", "=", applicantEntity.getId()));
						if (Util.isEmpty(baseDB)) {
							TTouristBaseinfoEntity base = new TTouristBaseinfoEntity();
							base.setApplicantId(applicantEntity.getId());
							dbDao.insert(base);
						}
						if (Util.isEmpty(passDB)) {
							TTouristPassportEntity pass = new TTouristPassportEntity();
							pass.setApplicantId(applicantEntity.getId());
							dbDao.insert(pass);
						}
						if (Util.isEmpty(visaDB)) {
							TTouristVisaEntity visa = new TTouristVisaEntity();
							visa.setApplicantId(applicantEntity.getId());
							dbDao.insert(visa);
						}
					} else {
						TTouristBaseinfoEntity touristBaseDB = dbDao.fetch(TTouristBaseinfoEntity.class,
								Cnd.where("userId", "=", applicantEntity.getUserId()));
						TTouristPassportEntity touristPassDB = dbDao.fetch(TTouristPassportEntity.class,
								Cnd.where("userId", "=", applicantEntity.getUserId()));
						TTouristVisaEntity touristVisaDB = dbDao.fetch(TTouristVisaEntity.class,
								Cnd.where("userId", "=", applicantEntity.getUserId()));
						//如果游客基本信息为空，则把申请人基本信息赋值给游客
						if (Util.isEmpty(touristBaseDB)) {
							TTouristBaseinfoEntity base = new TTouristBaseinfoEntity();
							base.setUserId(applicantEntity.getUserId());
							base.setApplicantId(applicantEntity.getId());
							dbDao.insert(base);
						}
						if (Util.isEmpty(touristPassDB)) {
							TTouristPassportEntity pass = new TTouristPassportEntity();
							pass.setUserId(applicantEntity.getUserId());
							pass.setApplicantId(applicantEntity.getId());
							dbDao.insert(pass);
						}
						if (Util.isEmpty(touristVisaDB)) {
							TTouristVisaEntity visa = new TTouristVisaEntity();
							visa.setUserId(applicantEntity.getUserId());
							visa.setApplicantId(applicantEntity.getId());
							dbDao.insert(visa);
						}
					}
				}*/
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	//发送邮件信息
	public Object sendMail(int orderid, int applicantid, HttpServletRequest request) throws IOException {
		List<String> readLines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream("share_mail.html"));
		StringBuilder tmp = new StringBuilder();
		for (String line : readLines) {
			tmp.append(line);
		}

		String pcUrl = "http://" + request.getLocalAddr() + ":" + request.getLocalPort() + "/tlogin";

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
		if (Util.isEmpty(sex)) {
			sex = "先生/女士";
		} else if (Util.eq(sex, "男")) {
			sex = "先生";
		} else if (Util.eq(sex, "女")) {
			sex = "女士";
		}
		String result = "";

		String emailText = tmp.toString();
		emailText = emailText.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum)
				.replace("${telephone}", telephone).replace("${pcUrl}", pcUrl);
		;
		result = mailService.send(email, emailText, "分享", MailService.Type.HTML);
		return result;
	}

	//发送手机信息
	public Object sendMessage(int orderid, int applicantid, HttpServletRequest request) throws IOException {
		List<String> readLines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream("share_sms.txt"));
		StringBuilder tmp = new StringBuilder();
		for (String line : readLines) {
			tmp.append(line);
		}

		String mobileUrl = "http://" + request.getLocalAddr() + ":" + request.getLocalPort()
				+ "/mobile/info.html?applicantid=" + applicantid;
		//转换长连接为短地址
		mobileUrl = firstTrialJpViewService.getEncryptlink(mobileUrl, request);
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
		if (Util.isEmpty(sex)) {
			sex = "先生/女士";
		} else if (Util.eq(sex, "男")) {
			sex = "先生";
		} else if (Util.eq(sex, "女")) {
			sex = "女士";
		}
		String result = "";

		String smsContent = tmp.toString();
		smsContent = smsContent.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum)
				.replace("${telephone}", telephone).replace("${mobileUrl}", mobileUrl);
		result = firstTrialJpViewService.sendSMS(telephone, smsContent);
		return result;
	}

	//发送邮件信息
	public Object sendMailUnified(int orderid, int applicantid, HttpServletRequest request) throws IOException {
		List<String> readLines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream(
				"shareUnified_mail.html"));
		StringBuilder tmp = new StringBuilder();
		for (String line : readLines) {
			tmp.append(line);
		}
		String pcUrl = "http://" + request.getLocalAddr() + ":" + request.getLocalPort() + "/tlogin";

		//查询订单号
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderid);
		String orderNum = order.getOrderNum();

		//申请人信息
		//接收邮件的申请人
		TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class, applicantid);
		String email = applicantEntity.getEmail();
		StringBuffer buffer = new StringBuffer();
		String unifiedName = buffer.append(applicantEntity.getFirstName()).append(applicantEntity.getLastName())
				.toString();
		String unifiedSex = applicantEntity.getSex();
		if (Util.isEmpty(unifiedSex)) {
			unifiedSex = "男/女";
		} else if (Util.eq(unifiedSex, "男")) {
			unifiedSex = "先生";
		} else if (Util.eq(unifiedSex, "女")) {
			unifiedSex = "女士";
		}
		String applicantSqlstr = sqlManager.get("orderJp_list_applicantInfo_byOrderId");
		Sql applicantSql = Sqls.create(applicantSqlstr);
		applicantSql.setParam("id", orderid);
		List<Record> applicantInfo = dbDao.query(applicantSql, null, null);
		String result = "";
		String applicantInfoStr = "";
		String emailText = tmp.toString();
		emailText = emailText.replace("${unifiedName}", unifiedName).replace("${sex}", unifiedSex)
				.replace("${ordernum}", orderNum).replace("${pcUrl}", pcUrl);
		;

		for (Record record : applicantInfo) {
			String name = record.getString("applyname");
			String telephone = record.getString("telephone");
			if (Util.isEmpty(telephone)) {
				telephone = "";
			}
			/*applicantInfoStr += "<div style=''><span style='font-family: Menlo;'>&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 姓名："
					+ name
					+ "&nbsp; &nbsp; &nbsp;用户名:<span t='7' data='17600206506' isout='1' style='border-bottom: 1px dashed rgb(204, 204, 204); z-index: 1; position: static;'><span t='7' data='17600206506' style='border-bottom: 1px dashed rgb(204, 204, 204); z-index: 1;'><span style='border-bottom: 1px dashed #ccc; z-index: 1' t='7' onclick='return false;' data='17600206506'>"
					+ telephone + "</span></span></span> </span> </div>";*/
			applicantInfoStr += "<div style=''><span style='font-family: Menlo;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户名:<span t='7' data='17600206506' isout='1' style='border-bottom: 1px dashed rgb(204, 204, 204); z-index: 1; position: static;'><span t='7' data='17600206506' style='border-bottom: 1px dashed rgb(204, 204, 204); z-index: 1;'><span style='border-bottom: 1px dashed #ccc; z-index: 1' t='7' onclick='return false;' data='17600206506'>"
					+ telephone + "</span></span></span> </span> </div>";
		}
		emailText = emailText.replace("${applicantInfoS}", applicantInfoStr);
		result = mailService.send(email, emailText, "分享", MailService.Type.HTML);
		return result;
	}

	//发送手机信息
	public Object sendMessageUnified(int orderid, int applicantid, HttpServletRequest request) throws IOException {
		List<String> readLines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream(
				"shareUnified_sms.txt"));
		StringBuilder tmp = new StringBuilder();
		for (String line : readLines) {
			tmp.append(line);
		}
		String mobileUrl = "http://" + request.getLocalAddr() + ":" + request.getLocalPort()
				+ "/mobile/info.html?applicantid=" + applicantid;
		//转换长连接为短地址
		mobileUrl = firstTrialJpViewService.getEncryptlink(mobileUrl, request);
		//查询订单号
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderid);
		String orderNum = order.getOrderNum();

		//申请人信息
		//接收邮件的申请人
		TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class, applicantid);
		String telephone = applicantEntity.getTelephone();
		StringBuffer buffer = new StringBuffer();
		String unifiedName = buffer.append(applicantEntity.getFirstName()).append(applicantEntity.getLastName())
				.toString();
		String unifiedSex = applicantEntity.getSex();
		if (Util.isEmpty(unifiedSex)) {
			unifiedSex = "先生/女士";
		} else if (Util.eq(unifiedSex, "男")) {
			unifiedSex = "先生";
		} else if (Util.eq(unifiedSex, "女")) {
			unifiedSex = "女士";
		}

		String applicantSqlstr = sqlManager.get("orderJp_list_applicantInfo_byOrderId");
		Sql applicantSql = Sqls.create(applicantSqlstr);
		applicantSql.setParam("id", orderid);
		List<Record> applicantInfo = dbDao.query(applicantSql, null, null);
		String result = "";
		String applicantInfoStr = "";
		String smsContent = tmp.toString();
		smsContent = smsContent.replace("${unifiedName}", unifiedName).replace("${sex}", unifiedSex)
				.replace("${ordernum}", orderNum).replace("${mobileUrl}", mobileUrl);

		for (Record record : applicantInfo) {
			String name = record.getString("applyname");
			String telephoneLoad = record.getString("telephone");
			if (Util.isEmpty(telephoneLoad)) {
				telephoneLoad = "";
			}
			//applicantInfoStr += ("  姓名:" + name + "  用户名:" + telephoneLoad);
			applicantInfoStr += "  用户名:" + telephoneLoad;
		}
		smsContent = smsContent.replace("${applicantInfoS}", applicantInfoStr);
		result = firstTrialJpViewService.sendSMS(telephone, smsContent);
		return result;
	}

	public Object checkPassport(String passport, String adminId, int orderid) {
		Map<String, Object> result = new HashMap<String, Object>();
		String passportSqlstr = sqlManager.get("passportInfo_byOrderId");
		Sql passportSql = Sqls.create(passportSqlstr);
		Cnd cnd = Cnd.NEW();
		cnd.and("toj.orderId", "=", orderid);
		cnd.and("ap.passport", "=", passport);
		if (!Util.isEmpty(adminId)) {
			cnd.and("ap.id", "!=", adminId);
		}
		List<Record> passportInfo = dbDao.query(passportSql, cnd, null);
		result.put("valid", passportInfo.size() <= 0);
		return result;
	}

	public Object checkMobile(String mobile, String adminId) {
		Map<String, Object> result = new HashMap<String, Object>();
		int count = 0;
		if (Util.isEmpty(adminId)) {
			count = nutDao.count(TApplicantEntity.class, Cnd.where("telephone", "=", mobile));
		} else {
			count = nutDao.count(TApplicantEntity.class, Cnd.where("telephone", "=", mobile).and("id", "!=", adminId));
		}
		result.put("valid", count <= 0);
		return result;
	}

	public Object saveEditPassport(TApplicantPassportForm passportForm, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		Date nowDate = DateUtil.nowDate();
		if (!Util.isEmpty(passportForm.getOrderid())
				&& Util.eq(passportForm.getIsTrailOrder(), IsYesOrNoEnum.YES.intKey())) {
			dbDao.update(TOrderEntity.class, Chain.make("updateTime", nowDate),
					Cnd.where("id", "=", passportForm.getOrderid()));
		}
		if (!Util.isEmpty(passportForm.getApplicantId())) {
			TApplicantUnqualifiedEntity unqualifiedEntity = dbDao.fetch(TApplicantUnqualifiedEntity.class,
					Cnd.where("applicantId", "=", passportForm.getApplicantId().longValue()));
			TApplicantPassportEntity passport = dbDao.fetch(TApplicantPassportEntity.class,
					Cnd.where("applicantId", "=", passportForm.getApplicantId()));
			passport.setOpId(loginUser.getId());

			passport.setPassportUrl(passportForm.getPassportUrl());
			passport.setOCRline1(passportForm.getOCRline1());
			passport.setOCRline2(passportForm.getOCRline2());
			passport.setBirthAddress(passportForm.getBirthAddress());
			passport.setBirthAddressEn(passportForm.getBirthAddressEn());
			passport.setBirthday(passportForm.getBirthday());
			passport.setFirstName(passportForm.getFirstName());
			//passport.setFirstNameEn(passportForm.getFirstNameEn().substring(1));
			passport.setIssuedDate(passportForm.getIssuedDate());
			passport.setIssuedOrganization(passportForm.getIssuedOrganization());
			passport.setIssuedOrganizationEn(passportForm.getIssuedOrganizationEn());
			passport.setIssuedPlace(passportForm.getIssuedPlace());
			passport.setIssuedPlaceEn(passportForm.getIssuedPlaceEn());
			passport.setLastName(passportForm.getLastName());
			//passport.setLastNameEn(passportForm.getLastNameEn().substring(1));
			passport.setPassport(passportForm.getPassport());
			passport.setSex(passportForm.getSex());
			passport.setSexEn(passportForm.getSexEn());
			passport.setType(passportForm.getType());
			passport.setValidEndDate(passportForm.getValidEndDate());
			passport.setValidStartDate(passportForm.getValidStartDate());
			passport.setValidType(passportForm.getValidType());
			passport.setUpdateTime(new Date());
			int update = dbDao.update(passport);
			TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
					Cnd.where("applicantId", "=", passport.getApplicantId()));

			TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, passportForm.getApplicantId().longValue());
			if (Util.eq(passportForm.getUserType(), 2)) {
				if (update > 0) {//基本信息保存成功，这时候必有userId
					TTouristPassportEntity pass = dbDao.fetch(TTouristPassportEntity.class,
							Cnd.where("applicantId", "=", apply.getId()));
					if (!Util.isEmpty(pass)) {
						if (!Util.isEmpty(pass.getUserId())) {

						} else {
							TTouristPassportEntity uidPass = dbDao.fetch(TTouristPassportEntity.class,
									Cnd.where("userId", "=", apply.getUserId()));
							if (Util.isEmpty(uidPass)) {
								pass.setUserId(apply.getUserId());
								dbDao.update(pass);
							}
						}
					}
				}
			}
			TApplicantOrderJpEntity applyJp = dbDao.fetch(TApplicantOrderJpEntity.class,
					Cnd.where("applicantId", "=", passportForm.getApplicantId()));
			TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, applyJp.getOrderId().longValue());
			String passRemark = passportForm.getPassRemark();
			if (!Util.isEmpty(passRemark)) {
				qualifiedApplicantViewService.unQualified(passportForm.getApplicantId(), orderJpEntity.getOrderId(),
						passRemark, ApplicantInfoTypeEnum.PASSPORT.intKey(), session);
			}
		}

		//变更订单负责人
		Integer orderProcessType = passportForm.getOrderProcessType();
		Integer orderid = passportForm.getOrderid();
		if (!Util.isEmpty(orderid)) {
			changePrincipalViewService.ChangePrincipal(orderid, orderProcessType, userId);
		}
		return null;
	}

	public Object deleteApplicant(Integer id) {
		//删除护照信息
		List<TApplicantPassportEntity> passports = dbDao.query(TApplicantPassportEntity.class,
				Cnd.where("applicantId", "=", id), null);
		if (!Util.isEmpty(passports)) {
			for (TApplicantPassportEntity tApplicantPassportEntity : passports) {
				dbDao.delete(TApplicantPassportEntity.class, tApplicantPassportEntity.getId());
			}
		}

		//删除快递信息
		List<TApplicantExpressEntity> express = dbDao.query(TApplicantExpressEntity.class,
				Cnd.where("applicantId", "=", id), null);
		if (!Util.isEmpty(express)) {
			for (TApplicantExpressEntity tApplicantExpressEntity : express) {
				dbDao.delete(tApplicantExpressEntity);
			}
		}
		//删除不合格信息
		List<TApplicantUnqualifiedEntity> unqualified = dbDao.query(TApplicantUnqualifiedEntity.class,
				Cnd.where("applicantId", "=", id), null);
		if (!Util.isEmpty(unqualified)) {
			for (TApplicantUnqualifiedEntity tApplicantUnqualifiedEntity : unqualified) {
				dbDao.delete(tApplicantUnqualifiedEntity);
			}
		}

		TApplicantOrderJpEntity applicantOrderJp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", id));
		if (!Util.isEmpty(applicantOrderJp)) {
			//删除工作信息
			TApplicantWorkJpEntity applicantWorkJpEntity = dbDao.fetch(TApplicantWorkJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJp.getId()));
			if (!Util.isEmpty(applicantWorkJpEntity)) {
				dbDao.delete(TApplicantWorkJpEntity.class, applicantWorkJpEntity.getId());
			}
			//删除财产信息
			List<TApplicantWealthJpEntity> wealths = dbDao.query(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJp.getId()), null);
			if (!Util.isEmpty(wealths)) {
				for (TApplicantWealthJpEntity tApplicantWealthJpEntity : wealths) {
					dbDao.delete(TApplicantWealthJpEntity.class, tApplicantWealthJpEntity.getId());
				}
			}
			//删除前台真实资料
			List<TApplicantFrontPaperworkJpEntity> frontList = dbDao.query(TApplicantFrontPaperworkJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJp.getId()), null);
			if (!Util.isEmpty(frontList)) {
				for (TApplicantFrontPaperworkJpEntity tApplicantFrontPaperworkJpEntity : frontList) {
					dbDao.delete(tApplicantFrontPaperworkJpEntity);
				}
			}
			//删除签证真实资料
			List<TApplicantVisaPaperworkJpEntity> visaList = dbDao.query(TApplicantVisaPaperworkJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJp.getId()), null);
			if (!Util.isEmpty(visaList)) {
				for (TApplicantVisaPaperworkJpEntity tApplicantVisaPaperworkJpEntity : visaList) {
					dbDao.delete(tApplicantVisaPaperworkJpEntity);
				}
			}
			//删除出行信息
			List<TApplicanttTripJpEntity> trip = dbDao.query(TApplicanttTripJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJp.getId()), null);
			if (!Util.isEmpty(trip)) {
				for (TApplicanttTripJpEntity tApplicanttTripJpEntity : trip) {
					dbDao.delete(tApplicanttTripJpEntity);
				}
			}
			//删除签证信息
			List<TApplicantVisaJpEntity> visaJp = dbDao.query(TApplicantVisaJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJp.getId()), null);
			if (!Util.isEmpty(visaJp)) {
				for (TApplicantVisaJpEntity tApplicantVisaJpEntity : visaJp) {
					dbDao.delete(tApplicantVisaJpEntity);
				}
			}
			//删除回邮信息
			List<TApplicantBackmailJpEntity> backMail = dbDao.query(TApplicantBackmailJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJp.getId()), null);
			if (!Util.isEmpty(backMail)) {
				for (TApplicantBackmailJpEntity tApplicantBackmailJpEntity : backMail) {
					dbDao.delete(tApplicantBackmailJpEntity);
				}
			}
			//删除日本申请人信息
			dbDao.delete(TApplicantOrderJpEntity.class, applicantOrderJp.getId());
		}
		//删除申请人
		dbDao.delete(TApplicantEntity.class, id);
		return null;
	}

	//跳转到日志页
	public Object toLogPage(Integer orderid, Integer orderProcessType, HttpSession session) {
		Map<String, Object> result = MapUtil.map();

		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Integer comid = loginCompany.getId();
		Integer adminId = loginCompany.getAdminId();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userType = loginUser.getUserType();//当前登录用户类型

		//查询拥有某个权限模块的工作人员
		String sqlstr = sqlManager.get("logs_user_select_list");
		Sql sql = Sqls.create(sqlstr);
		Cnd cnd = Cnd.NEW();
		cnd.and("tcf.comid", "=", comid);
		cnd.and("tu.id", "!=", adminId);
		for (JPOrderProcessTypeEnum typeEnum : JPOrderProcessTypeEnum.values()) {
			if (!Util.isEmpty(orderProcessType) && orderProcessType == typeEnum.intKey()) {
				cnd.and(" tf.funName", "=", typeEnum.value());
			}
		}
		List<Record> employees = dbDao.query(sql, cnd, null);

		//获取对应的负责人字段 logs_order_info
		String principalField = changePrincipalViewService.getprincipalField(orderProcessType);
		String sqlStr = sqlManager.get("logs_order_info");
		Sql sqlOrder = Sqls.create(sqlStr);
		sqlOrder.setParam("orderid", orderid);
		Record order = dbDao.fetch(sqlOrder);
		int princiapalId = order.getInt(principalField);

		result.put("orderid", orderid);
		result.put("userType", userType);
		result.put("employees", employees);
		result.put("orderProcessType", orderProcessType);
		result.put("princiapalId", princiapalId);
		return result;
	}

	//加载日志列表
	public Object getLogs(Integer orderid, Integer orderProcessType) {
		Map<String, Object> result = MapUtil.map();
		String logSqlstr = sqlManager.get("username_logs");
		Sql logSql = Sqls.create(logSqlstr);
		logSql.setParam("id", orderid);
		List<Record> logs = dbDao.query(logSql, null, null); //日志列表
		for (Record record : logs) {
			if (!Util.isEmpty(record.get("orderStatus"))) {
				Integer status = (Integer) record.get("orderStatus");
				for (JPOrderStatusEnum statusEnum : JPOrderStatusEnum.values()) {
					if (status == statusEnum.intKey()) {
						record.put("orderStatus", statusEnum.value());
					} else if (status == JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.intKey()) {
						record.put("orderStatus", JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.value());
					}
				}
			}
		}

		result.put("logs", logs);
		return result;
	}

	/**
	 * 
	 * 变更订单负责人
	 * 更新订单状态为 “负责人变更”
	 *
	 * @param orderid 订单id
	 * @param principalId 负责人id
	 * @param orderProcessType 订单流程之一
	 * @return 
	 */
	public Object changePrincipal(Integer orderid, Integer principalId, Integer orderProcessType, HttpSession session) {
		int updatenum = 0;
		Date nowDate = DateUtil.nowDate();
		//Order 要检索的字段
		String fieldStr = changePrincipalViewService.getprincipalField(orderProcessType);
		TOrderEntity order = dbDao.fetch(TOrderEntity.class,
				Cnd.where("id", "=", orderid).and(fieldStr, "=", principalId));
		if (Util.isEmpty(order)) {
			TOrderEntity updateOrder = dbDao.fetch(TOrderEntity.class, Cnd.where("id", "=", orderid));
			if (!Util.isEmpty(updateOrder)) {
				//更新订单对应 负责人字段
				updatenum = dbDao.update(TOrderEntity.class, Chain.make(fieldStr, principalId),
						Cnd.where("id", "=", orderid));
				if (updatenum > 0) {
					// 日志记录 “负责人变更”行为
					int PRINCIPAL_ORDER = JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.intKey();
					/*dbDao.update(TOrderEntity.class, Chain.make("status", PRINCIPAL_ORDER).add("updateTime", nowDate),
							Cnd.where("id", "=", orderid));*/
					//日志记录
					insertLogs(orderid, PRINCIPAL_ORDER, session);
				}
			}
		}
		return updatenum;
	}

	public Object firtTrialJp(Integer id, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, new Long(id).intValue());
		orderEntity.setStatus(JPOrderStatusEnum.FIRSTTRIAL_ORDER.intKey());
		orderEntity.setUpdateTime(DateUtil.nowDate());
		dbDao.update(orderEntity);
		TOrderLogsEntity logsEntity = dbDao.fetch(TOrderLogsEntity.class, Cnd.where("orderId", "=", id));
		logsEntity.setOrderId(id);
		logsEntity.setOrderStatus(JPOrderStatusEnum.FIRSTTRIAL_ORDER.intKey());
		logsEntity.setOpId(loginUser.getId());
		logsEntity.setCreateTime(new Date());
		dbDao.insert(logsEntity);
		return null;
	}

	public Object disabled(int orderid) {
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderid);
		orderEntity.setIsDisabled(IsYesOrNoEnum.YES.intKey());
		orderEntity.setUpdateTime(new Date());
		dbDao.update(orderEntity);
		return null;
	}

	public Object undisabled(int orderid) {
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderid);
		orderEntity.setIsDisabled(IsYesOrNoEnum.NO.intKey());
		orderEntity.setUpdateTime(new Date());
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
		//将图片进行旋转处理
		ImageDeal imageDeal = new ImageDeal(file.getPath(), request.getContextPath(), UUID.randomUUID().toString(),
				"jpeg");
		File spin = null;
		try {
			spin = imageDeal.spin(-90);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//上传
		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(spin);
		file.delete();
		if (!Util.isEmpty(spin)) {
			spin.delete();
		}
		String url = CommonConstants.IMAGES_SERVER_ADDR + map.get("data");
		//从服务器上获取图片的流，读取扫描
		byte[] bytes = saveImageToDisk(url);
		String imageDataValue = Base64.encodeBase64String(bytes);
		Input input = new Input(imageDataValue, "face");
		RecognizeData rd = new RecognizeData();
		rd.getInputs().add(input);
		String content = Json.toJson(rd);
		String info = (String) appCodeCall(content);//扫描完毕
		System.out.println("info:" + info);
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
		//将图片进行旋转处理
		ImageDeal imageDeal = new ImageDeal(file.getPath(), request.getContextPath(), UUID.randomUUID().toString(),
				"jpeg");
		File spin = null;
		try {
			spin = imageDeal.spin(-90);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//上传
		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(spin);
		file.delete();
		if (!Util.isEmpty(spin)) {
			spin.delete();
		}
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

	public Object marryUpload(File file, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(file);
		file.delete();
		map.put("data", CommonConstants.IMAGES_SERVER_ADDR + map.get("data"));
		return map;
	}

	public Object passportRecognitionBack(File file, HttpServletRequest request, HttpServletResponse response) {
		//将图片进行旋转处理
		ImageDeal imageDeal = new ImageDeal(file.getPath(), request.getContextPath(), UUID.randomUUID().toString(),
				"jpeg");
		File spin = null;
		try {
			spin = imageDeal.spin(-90);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//上传
		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(spin);
		file.delete();
		if (!Util.isEmpty(spin)) {
			spin.delete();
		}
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
		String substring = "";
		if (out.getBoolean("success")) {
			String type = out.getString("type");
			if (!Util.isEmpty(type)) {
				substring = type.substring(0, 1);
			}
			jsonEntity.setType(substring);
			jsonEntity.setNum(out.getString("passport_no"));
			if (out.getString("sex").equals("F")) {
				jsonEntity.setSex("女");
				jsonEntity.setSexEn("F");
			} else {
				jsonEntity.setSex("男");
				jsonEntity.setSexEn("M");
			}
			jsonEntity.setUrl(url);
			jsonEntity.setOCRline1(out.getString("line0"));
			jsonEntity.setOCRline2(out.getString("line1"));
			jsonEntity.setBirthCountry(out.getString("birth_place"));
			jsonEntity.setVisaCountry(out.getString("issue_place"));
			Date birthDay;
			Date expiryDate;
			Date issueDate;
			try {
				birthDay = new SimpleDateFormat("yyyyMMdd").parse(out.getString("birth_date"));
				expiryDate = new SimpleDateFormat("yyyyMMdd").parse(out.getString("expiry_date"));
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
		String appcode = "19598dc0fd65499b93a9dec6c43489b7";
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
		/*String appcode = "db7570313ab4478793f42ad8cd48723b";*/
		String appcode = "19598dc0fd65499b93a9dec6c43489b7";
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

	public void insertLogs(Integer orderid, Integer status, HttpSession session) {
		TOrderLogsEntity logs = new TOrderLogsEntity();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		logs.setCreateTime(new Date());
		logs.setOpId(loginUser.getId());
		logs.setOrderId(orderid);
		logs.setOrderStatus(status);
		logs.setUpdateTime(new Date());
		dbDao.insert(logs);
	}

	public List<TApplicantFrontPaperworkJpEntity> toInsertFrontJp(Integer workType, Integer applicantId,
			HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		List<TApplicantFrontPaperworkJpEntity> frontList = new ArrayList<>();
		if (Util.eq(workType, JobStatusEnum.WORKING_STATUS.intKey())) {
			for (JobStatusWorkingEnum jobStatusEnum : JobStatusWorkingEnum.values()) {
				TApplicantFrontPaperworkJpEntity frontJp = new TApplicantFrontPaperworkJpEntity();
				frontJp.setApplicantId(applicantId);
				frontJp.setOpId(loginUser.getId());
				frontJp.setRealInfo(jobStatusEnum.value());
				frontJp.setUpdateTime(new Date());
				frontJp.setCreateTime(new Date());
				frontJp.setType(workType);
				frontJp.setStatus(1);
				frontList.add(frontJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.RETIREMENT_STATUS.intKey())) {
			for (JobStatusRetirementEnum jobStatusEnum : JobStatusRetirementEnum.values()) {
				TApplicantFrontPaperworkJpEntity frontJp = new TApplicantFrontPaperworkJpEntity();
				frontJp.setApplicantId(applicantId);
				frontJp.setOpId(loginUser.getId());
				frontJp.setRealInfo(jobStatusEnum.value());
				frontJp.setUpdateTime(new Date());
				frontJp.setCreateTime(new Date());
				frontJp.setType(workType);
				frontJp.setStatus(1);
				frontList.add(frontJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.FREELANCE_STATUS.intKey())) {
			for (JobStatusFreeEnum jobStatusEnum : JobStatusFreeEnum.values()) {
				TApplicantFrontPaperworkJpEntity frontJp = new TApplicantFrontPaperworkJpEntity();
				frontJp.setApplicantId(applicantId);
				frontJp.setOpId(loginUser.getId());
				frontJp.setRealInfo(jobStatusEnum.value());
				frontJp.setUpdateTime(new Date());
				frontJp.setCreateTime(new Date());
				frontJp.setType(workType);
				frontJp.setStatus(1);
				frontList.add(frontJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.student_status.intKey())) {
			for (JobStatusStudentEnum jobStatusEnum : JobStatusStudentEnum.values()) {
				TApplicantFrontPaperworkJpEntity frontJp = new TApplicantFrontPaperworkJpEntity();
				frontJp.setApplicantId(applicantId);
				frontJp.setOpId(loginUser.getId());
				frontJp.setRealInfo(jobStatusEnum.value());
				frontJp.setUpdateTime(new Date());
				frontJp.setCreateTime(new Date());
				frontJp.setType(workType);
				frontJp.setStatus(1);
				frontList.add(frontJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.Preschoolage_status.intKey())) {
			for (JobStatusPreschoolEnum jobStatusEnum : JobStatusPreschoolEnum.values()) {
				TApplicantFrontPaperworkJpEntity frontJp = new TApplicantFrontPaperworkJpEntity();
				frontJp.setApplicantId(applicantId);
				frontJp.setOpId(loginUser.getId());
				frontJp.setRealInfo(jobStatusEnum.value());
				frontJp.setUpdateTime(new Date());
				frontJp.setCreateTime(new Date());
				frontJp.setType(workType);
				frontJp.setStatus(1);
				frontList.add(frontJp);
			}
		}

		return frontList;
	}

	public List<TApplicantVisaPaperworkJpEntity> toInsertVisaJp(Integer workType, Integer applicantId,
			HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		List<TApplicantVisaPaperworkJpEntity> visaList = new ArrayList<>();
		if (Util.eq(workType, JobStatusEnum.WORKING_STATUS.intKey())) {
			for (JobStatusWorkingEnum jobStatusEnum : JobStatusWorkingEnum.values()) {
				TApplicantVisaPaperworkJpEntity visaJp = new TApplicantVisaPaperworkJpEntity();
				visaJp.setApplicantId(applicantId);
				visaJp.setRealInfo(jobStatusEnum.value());
				visaJp.setOpId(loginUser.getId());
				visaJp.setUpdateTime(new Date());
				visaJp.setCreateTime(new Date());
				visaJp.setStatus(1);
				visaJp.setType(workType);
				visaList.add(visaJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.RETIREMENT_STATUS.intKey())) {
			for (JobStatusRetirementEnum jobStatusEnum : JobStatusRetirementEnum.values()) {
				TApplicantVisaPaperworkJpEntity visaJp = new TApplicantVisaPaperworkJpEntity();
				visaJp.setApplicantId(applicantId);
				visaJp.setRealInfo(jobStatusEnum.value());
				visaJp.setOpId(loginUser.getId());
				visaJp.setUpdateTime(new Date());
				visaJp.setCreateTime(new Date());
				visaJp.setStatus(1);
				visaJp.setType(workType);
				visaList.add(visaJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.FREELANCE_STATUS.intKey())) {
			for (JobStatusFreeEnum jobStatusEnum : JobStatusFreeEnum.values()) {
				TApplicantVisaPaperworkJpEntity visaJp = new TApplicantVisaPaperworkJpEntity();
				visaJp.setApplicantId(applicantId);
				visaJp.setRealInfo(jobStatusEnum.value());
				visaJp.setOpId(loginUser.getId());
				visaJp.setUpdateTime(new Date());
				visaJp.setCreateTime(new Date());
				visaJp.setStatus(1);
				visaJp.setType(workType);
				visaList.add(visaJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.student_status.intKey())) {
			for (JobStatusStudentEnum jobStatusEnum : JobStatusStudentEnum.values()) {
				TApplicantVisaPaperworkJpEntity visaJp = new TApplicantVisaPaperworkJpEntity();
				visaJp.setApplicantId(applicantId);
				visaJp.setRealInfo(jobStatusEnum.value());
				visaJp.setOpId(loginUser.getId());
				visaJp.setUpdateTime(new Date());
				visaJp.setCreateTime(new Date());
				visaJp.setStatus(1);
				visaJp.setType(workType);
				visaList.add(visaJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.Preschoolage_status.intKey())) {
			for (JobStatusPreschoolEnum jobStatusEnum : JobStatusPreschoolEnum.values()) {
				TApplicantVisaPaperworkJpEntity visaJp = new TApplicantVisaPaperworkJpEntity();
				visaJp.setApplicantId(applicantId);
				visaJp.setRealInfo(jobStatusEnum.value());
				visaJp.setOpId(loginUser.getId());
				visaJp.setUpdateTime(new Date());
				visaJp.setCreateTime(new Date());
				visaJp.setStatus(1);
				visaJp.setType(workType);
				visaList.add(visaJp);
			}
		}
		return visaList;
	}

	public Object getOrderStatus(int orderid) {
		Map<String, Object> result = MapUtil.map();
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderid);
		for (JPOrderStatusEnum orderStatus : JPOrderStatusEnum.values()) {
			if (order.getStatus() == orderStatus.intKey()) {
				result.put("status", orderStatus.value());
			}
		}
		return result;
	}

	public Object toUpdateWorkJp(int careerStatus, TApplicantWorkJpEntity applicantWorkJpEntity,
			TApplicantOrderJpEntity applicantOrderJpEntity, HttpSession session) {

		Integer applicantJpId = applicantOrderJpEntity.getId();
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
		if (Util.eq(careerStatus, JobStatusEnum.WORKING_STATUS.intKey())) {//在职
			dbDao.insert(toInsertFrontJp(JobStatusEnum.WORKING_STATUS.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.WORKING_STATUS.intKey(), applicantJpId, session));

			StringBuilder sbWork = new StringBuilder();
			for (JobStatusWorkingEnum jobWorking : JobStatusWorkingEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
		if (Util.eq(careerStatus, JobStatusEnum.RETIREMENT_STATUS.intKey())) {//退休
			dbDao.insert(toInsertFrontJp(JobStatusEnum.RETIREMENT_STATUS.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.RETIREMENT_STATUS.intKey(), applicantJpId, session));
			StringBuilder sbWork = new StringBuilder();
			for (JobStatusRetirementEnum jobWorking : JobStatusRetirementEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
		if (Util.eq(careerStatus, JobStatusEnum.FREELANCE_STATUS.intKey())) {//自由职业
			dbDao.insert(toInsertFrontJp(JobStatusEnum.FREELANCE_STATUS.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.FREELANCE_STATUS.intKey(), applicantJpId, session));
			StringBuilder sbWork = new StringBuilder();
			for (JobStatusFreeEnum jobWorking : JobStatusFreeEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			int length = workStatus.length();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
		if (Util.eq(careerStatus, JobStatusEnum.student_status.intKey())) {//学生
			dbDao.insert(toInsertFrontJp(JobStatusEnum.student_status.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.student_status.intKey(), applicantJpId, session));
			StringBuilder sbWork = new StringBuilder();
			for (JobStatusStudentEnum jobWorking : JobStatusStudentEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
		if (Util.eq(careerStatus, JobStatusEnum.Preschoolage_status.intKey())) {//学龄前
			dbDao.insert(toInsertFrontJp(JobStatusEnum.Preschoolage_status.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.Preschoolage_status.intKey(), applicantJpId, session));
			StringBuilder sbWork = new StringBuilder();
			for (JobStatusPreschoolEnum jobWorking : JobStatusPreschoolEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
		return null;
	}
}
