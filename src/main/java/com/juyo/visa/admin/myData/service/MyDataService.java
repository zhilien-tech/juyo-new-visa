package com.juyo.visa.admin.myData.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.order.form.VisaEditDataForm;
import com.juyo.visa.common.base.QrCodeService;
import com.juyo.visa.common.enums.ApplicantInfoTypeEnum;
import com.juyo.visa.common.enums.ApplicantJpWealthEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.JobStatusEnum;
import com.juyo.visa.common.enums.JobStatusFreeEnum;
import com.juyo.visa.common.enums.JobStatusPreschoolEnum;
import com.juyo.visa.common.enums.JobStatusRetirementEnum;
import com.juyo.visa.common.enums.JobStatusStudentEnum;
import com.juyo.visa.common.enums.JobStatusWorkingEnum;
import com.juyo.visa.common.enums.MainApplicantRelationEnum;
import com.juyo.visa.common.enums.MainApplicantRemarkEnum;
import com.juyo.visa.common.enums.MainOrViceEnum;
import com.juyo.visa.common.enums.MarryStatusEnum;
import com.juyo.visa.common.enums.PassportTypeEnum;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantFrontPaperworkJpEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TApplicantUnqualifiedEntity;
import com.juyo.visa.entities.TApplicantVisaPaperworkJpEntity;
import com.juyo.visa.entities.TApplicantWealthJpEntity;
import com.juyo.visa.entities.TApplicantWorkJpEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TTouristBaseinfoEntity;
import com.juyo.visa.entities.TTouristPassportEntity;
import com.juyo.visa.entities.TTouristVisaEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TTouristBaseinfoForm;
import com.juyo.visa.forms.TTouristPassportForm;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * 我的资料Service
 * <p>
 *
 * @author   
 * @Date	 2017年12月08日 	 
 */
@IocBean
public class MyDataService extends BaseService<TOrderJpEntity> {

	@Inject
	private QrCodeService qrCodeService;
	//基本信息连接websocket的地址
	private static final String BASIC_WEBSPCKET_ADDR = "basicinfowebsocket";

	public Object getBasicInfo(HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = MapUtil.map();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TTouristBaseinfoEntity touristBaseinfoEntity = dbDao.fetch(TTouristBaseinfoEntity.class,
				Cnd.where("userId", "=", loginUser.getId()));

		List<TApplicantEntity> applyList = dbDao.query(TApplicantEntity.class,
				Cnd.where("userId", "=", loginUser.getId()), null);
		Collections.reverse(applyList);
		//获取最新订单的申请人为当前申请人
		TApplicantEntity applicantEntity = applyList.get(0);
		/*TApplicantUnqualifiedEntity unqualifiedEntity = dbDao.fetch(TApplicantUnqualifiedEntity.class,
				Cnd.where("applicantId", "=", applicantEntity.getId()));
		if (!Util.isEmpty(unqualifiedEntity)) {
			result.put("unqualified", unqualifiedEntity);
		}*/
		TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantEntity.getId()));
		TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId()
				.longValue());
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderJpEntity.getOrderId().longValue());
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		if (!Util.isEmpty(touristBaseinfoEntity)) {
			if (!Util.isEmpty(touristBaseinfoEntity.getBirthday())) {
				Date birthday = touristBaseinfoEntity.getBirthday();
				String birthdayStr = sdf.format(birthday);
				result.put("birthday", birthdayStr);
			}
			if (!Util.isEmpty(touristBaseinfoEntity.getValidStartDate())) {
				Date validStartDate = touristBaseinfoEntity.getValidStartDate();
				String validStartDateStr = sdf.format(validStartDate);
				result.put("validStartDate", validStartDateStr);
			}
			if (!Util.isEmpty(touristBaseinfoEntity.getValidEndDate())) {
				Date validEndDate = touristBaseinfoEntity.getValidEndDate();
				String validEndDateStr = sdf.format(validEndDate);
				result.put("validEndDate", validEndDateStr);
			}

			if (!Util.isEmpty(touristBaseinfoEntity.getFirstNameEn())) {
				StringBuffer sb = new StringBuffer();
				sb.append("/").append(touristBaseinfoEntity.getFirstNameEn());
				result.put("firstNameEn", sb.toString());
			}

			if (!Util.isEmpty(touristBaseinfoEntity.getOtherFirstNameEn())) {
				StringBuffer sb = new StringBuffer();
				sb.append("/").append(touristBaseinfoEntity.getOtherFirstNameEn());
				result.put("otherFirstNameEn", sb.toString());
			}

			if (!Util.isEmpty(touristBaseinfoEntity.getLastNameEn())) {
				StringBuffer sb = new StringBuffer();
				sb.append("/").append(touristBaseinfoEntity.getLastNameEn());
				result.put("lastNameEn", sb.toString());
			}

			if (!Util.isEmpty(touristBaseinfoEntity.getOtherLastNameEn())) {
				StringBuffer sb = new StringBuffer();
				sb.append("/").append(touristBaseinfoEntity.getOtherLastNameEn());
				result.put("otherLastNameEn", sb.toString());
			}
			result.put("applicant", touristBaseinfoEntity);
		} else {
			result.put("applicant", "");
		}

		//生成二维码
		String qrurl = "http://" + request.getLocalAddr() + ":" + request.getLocalPort()
				+ "/mobile/info.html?applicantid=" + applicantEntity.getId();
		String qrCode = qrCodeService.encodeQrCode(request, qrurl);
		result.put("qrCode", qrCode);
		result.put("applicantId", applicantEntity.getId());
		result.put("orderid", orderEntity.getId());

		return result;
	}

	public Object saveEditApplicant(TTouristBaseinfoForm applicantForm, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		TTouristBaseinfoEntity baseinfoEntity = dbDao.fetch(TTouristBaseinfoEntity.class,
				Cnd.where("userId", "=", userId));
		if (!Util.isEmpty(baseinfoEntity)) {
			baseinfoEntity.setAddress(applicantForm.getAddress());
			baseinfoEntity.setAddressIsSameWithCard(applicantForm.getAddressIsSameWithCard());
			baseinfoEntity.setBirthday(applicantForm.getBirthday());
			baseinfoEntity.setCardBack(applicantForm.getCardBack());
			baseinfoEntity.setCardFront(applicantForm.getCardFront());
			baseinfoEntity.setCardId(applicantForm.getCardId());
			baseinfoEntity.setCity(applicantForm.getCity());
			baseinfoEntity.setUpdateTime(new Date());
			baseinfoEntity.setDetailedAddress(applicantForm.getDetailedAddress());
			baseinfoEntity.setEmail(applicantForm.getEmail());
			baseinfoEntity.setEmergencyLinkman(applicantForm.getEmergencyLinkman());
			baseinfoEntity.setEmergencyTelephone(applicantForm.getEmergencyTelephone());
			baseinfoEntity.setFirstName(applicantForm.getFirstName());
			baseinfoEntity.setFirstNameEn(applicantForm.getFirstNameEn());
			baseinfoEntity.setHasOtherName(applicantForm.getHasOtherName());
			baseinfoEntity.setHasOtherNationality(applicantForm.getHasOtherNationality());
			baseinfoEntity.setIssueOrganization(applicantForm.getIssueOrganization());
			baseinfoEntity.setLastName(applicantForm.getLastName());
			baseinfoEntity.setLastNameEn(applicantForm.getLastNameEn());
			baseinfoEntity.setNation(applicantForm.getNation());
			baseinfoEntity.setNationality(applicantForm.getNationality());
			baseinfoEntity.setOtherFirstName(applicantForm.getOtherFirstName());
			baseinfoEntity.setOtherFirstNameEn(applicantForm.getOtherFirstNameEn());
			baseinfoEntity.setOtherLastName(applicantForm.getOtherLastName());
			baseinfoEntity.setOtherLastNameEn(applicantForm.getOtherLastNameEn());
			baseinfoEntity.setProvince(applicantForm.getProvince());
			baseinfoEntity.setSex(applicantForm.getSex());
			baseinfoEntity.setTelephone(applicantForm.getTelephone());
			baseinfoEntity.setValidEndDate(applicantForm.getValidEndDate());
			baseinfoEntity.setValidStartDate(applicantForm.getValidStartDate());
			int update = dbDao.update(baseinfoEntity);
			if (update > 0) {
				baseinfoEntity.setBaseIsCompleted(IsYesOrNoEnum.YES.intKey());
				dbDao.update(baseinfoEntity);
			}
		} else {
			TTouristBaseinfoEntity baseinfo = new TTouristBaseinfoEntity();
			baseinfo.setUserId(userId);
			baseinfo.setAddress(applicantForm.getAddress());
			baseinfo.setAddressIsSameWithCard(applicantForm.getAddressIsSameWithCard());
			baseinfo.setBirthday(applicantForm.getBirthday());
			baseinfo.setCardBack(applicantForm.getCardBack());
			baseinfo.setCardFront(applicantForm.getCardFront());
			baseinfo.setCardId(applicantForm.getCardId());
			baseinfo.setCity(applicantForm.getCity());
			baseinfo.setUpdateTime(new Date());
			baseinfo.setCreateTime(new Date());
			baseinfo.setDetailedAddress(applicantForm.getDetailedAddress());
			baseinfo.setEmail(applicantForm.getEmail());
			baseinfo.setEmergencyLinkman(applicantForm.getEmergencyLinkman());
			baseinfo.setEmergencyTelephone(applicantForm.getEmergencyTelephone());
			baseinfo.setFirstName(applicantForm.getFirstName());
			baseinfo.setFirstNameEn(applicantForm.getFirstNameEn());
			baseinfo.setHasOtherName(applicantForm.getHasOtherName());
			baseinfo.setHasOtherNationality(applicantForm.getHasOtherNationality());
			baseinfo.setIssueOrganization(applicantForm.getIssueOrganization());
			baseinfo.setLastName(applicantForm.getLastName());
			baseinfo.setLastNameEn(applicantForm.getLastNameEn());
			baseinfo.setNation(applicantForm.getNation());
			baseinfo.setNationality(applicantForm.getNationality());
			baseinfo.setOtherFirstName(applicantForm.getOtherFirstName());
			baseinfo.setOtherFirstNameEn(applicantForm.getOtherFirstNameEn());
			baseinfo.setOtherLastName(applicantForm.getOtherLastName());
			baseinfo.setOtherLastNameEn(applicantForm.getOtherLastNameEn());
			baseinfo.setProvince(applicantForm.getProvince());
			baseinfo.setSex(applicantForm.getSex());
			baseinfo.setTelephone(applicantForm.getTelephone());
			baseinfo.setValidEndDate(applicantForm.getValidEndDate());
			baseinfo.setValidStartDate(applicantForm.getValidStartDate());
			TTouristBaseinfoEntity insert = dbDao.insert(baseinfo);
			if (!Util.isEmpty(insert)) {
				insert.setBaseIsCompleted(IsYesOrNoEnum.YES.intKey());
				dbDao.update(insert);
			}
		}
		return null;
	}

	public Object getPassportInfo(HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = MapUtil.map();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		List<TApplicantEntity> applyList = dbDao.query(TApplicantEntity.class,
				Cnd.where("userId", "=", loginUser.getId()), null);
		Collections.reverse(applyList);
		//最新订单的申请人为当前申请人
		TApplicantEntity applicantEntity = applyList.get(0);
		TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantEntity.getId()));
		TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId()
				.longValue());
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderJpEntity.getOrderId().longValue());
		String passportSqlstr = sqlManager.get("orderJp_list_passportInfo_byApplicantId");
		Sql passportSql = Sqls.create(passportSqlstr);
		passportSql.setParam("id", applicantEntity.getId());
		Record passport = dbDao.fetch(passportSql);
		//格式化日期
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		TTouristPassportEntity touristPassportEntity = dbDao.fetch(TTouristPassportEntity.class,
				Cnd.where("userId", "=", userId));
		if (!Util.isEmpty(touristPassportEntity)) {
			if (!Util.isEmpty(touristPassportEntity.getBirthday())) {
				Date goTripDate = touristPassportEntity.getBirthday();
				result.put("birthday", sdf.format(goTripDate));
			}
			if (!Util.isEmpty(touristPassportEntity.getValidEndDate())) {
				Date goTripDate = touristPassportEntity.getValidEndDate();
				result.put("validEndDate", sdf.format(goTripDate));
			}
			if (!Util.isEmpty(touristPassportEntity.getIssuedDate())) {
				Date goTripDate = touristPassportEntity.getIssuedDate();
				result.put("issuedDate", sdf.format(goTripDate));
			}
			result.put("passport", touristPassportEntity);
		} else {
			result.put("passport", "");
		}

		//生成二维码
		String qrurl = "http://" + request.getLocalAddr() + ":" + request.getLocalPort()
				+ "/mobile/info.html?applicantid=" + applicantEntity.getId();
		String qrCode = qrCodeService.encodeQrCode(request, qrurl);
		result.put("qrCode", qrCode);
		result.put("passportType", EnumUtil.enum2(PassportTypeEnum.class));
		result.put("applicantId", applicantEntity.getId());
		result.put("orderid", orderEntity.getId());
		return result;
	}

	public Object saveEditPassport(TTouristPassportForm passportForm, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		TTouristPassportEntity touristPassportEntity = dbDao.fetch(TTouristPassportEntity.class,
				Cnd.where("userId", "=", userId));
		if (!Util.isEmpty(touristPassportEntity)) {//游客护照信息不为空，则更新
			touristPassportEntity.setBirthAddress(passportForm.getBirthAddress());
			touristPassportEntity.setBirthAddressEn(passportForm.getBirthAddressEn());
			touristPassportEntity.setBirthday(passportForm.getBirthday());
			touristPassportEntity.setFirstName(passportForm.getFirstName());
			touristPassportEntity.setFirstNameEn(passportForm.getFirstNameEn());
			touristPassportEntity.setIssuedDate(passportForm.getIssuedDate());
			touristPassportEntity.setIssuedOrganization(passportForm.getIssuedOrganization());
			touristPassportEntity.setIssuedOrganizationEn(passportForm.getIssuedOrganizationEn());
			touristPassportEntity.setIssuedPlace(passportForm.getIssuedPlace());
			touristPassportEntity.setIssuedPlaceEn(passportForm.getIssuedPlaceEn());
			touristPassportEntity.setLastName(passportForm.getLastName());
			touristPassportEntity.setLastNameEn(passportForm.getLastNameEn());
			touristPassportEntity.setOCRline1(passportForm.getOCRline1());
			touristPassportEntity.setOCRline2(passportForm.getOCRline2());
			touristPassportEntity.setPassport(passportForm.getPassport());
			touristPassportEntity.setPassportUrl(passportForm.getPassportUrl());
			touristPassportEntity.setSex(passportForm.getSex());
			touristPassportEntity.setSexEn(passportForm.getSexEn());
			touristPassportEntity.setType(passportForm.getType());
			touristPassportEntity.setUpdateTime(new Date());
			touristPassportEntity.setValidEndDate(passportForm.getValidEndDate());
			touristPassportEntity.setValidStartDate(passportForm.getValidStartDate());
			touristPassportEntity.setValidType(passportForm.getValidType());
			int update = dbDao.update(touristPassportEntity);
			if (update > 0) {
				touristPassportEntity.setPassIsCompleted(IsYesOrNoEnum.YES.intKey());
				dbDao.update(touristPassportEntity);
			}
		} else {
			TTouristPassportEntity touristPass = new TTouristPassportEntity();
			touristPass.setBirthAddress(passportForm.getBirthAddress());
			touristPass.setBirthAddressEn(passportForm.getBirthAddressEn());
			touristPass.setBirthday(passportForm.getBirthday());
			touristPass.setFirstName(passportForm.getFirstName());
			touristPass.setFirstNameEn(passportForm.getFirstNameEn());
			touristPass.setIssuedDate(passportForm.getIssuedDate());
			touristPass.setIssuedOrganization(passportForm.getIssuedOrganization());
			touristPass.setIssuedOrganizationEn(passportForm.getIssuedOrganizationEn());
			touristPass.setIssuedPlace(passportForm.getIssuedPlace());
			touristPass.setIssuedPlaceEn(passportForm.getIssuedPlaceEn());
			touristPass.setLastName(passportForm.getLastName());
			touristPass.setLastNameEn(passportForm.getLastNameEn());
			touristPass.setOCRline1(passportForm.getOCRline1());
			touristPass.setOCRline2(passportForm.getOCRline2());
			touristPass.setPassport(passportForm.getPassport());
			touristPass.setPassportUrl(passportForm.getPassportUrl());
			touristPass.setSex(passportForm.getSex());
			touristPass.setSexEn(passportForm.getSexEn());
			touristPass.setType(passportForm.getType());
			touristPass.setCreateTime(new Date());
			touristPass.setUpdateTime(new Date());
			touristPass.setValidEndDate(passportForm.getValidEndDate());
			touristPass.setValidStartDate(passportForm.getValidStartDate());
			touristPass.setValidType(passportForm.getValidType());
			touristPass.setUserId(userId);
			TTouristPassportEntity passportEntity = dbDao.insert(touristPass);
			if (!Util.isEmpty(passportEntity)) {
				passportEntity.setPassIsCompleted(IsYesOrNoEnum.YES.intKey());
				dbDao.update(passportEntity);
			}
		}
		return null;
	}

	public Object visaInput(HttpSession session) {
		Map<String, Object> result = MapUtil.map();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		List<TApplicantEntity> applyList = dbDao.query(TApplicantEntity.class,
				Cnd.where("userId", "=", loginUser.getId()), null);
		Collections.reverse(applyList);
		TApplicantEntity applicantEntity = applyList.get(0);
		if (!Util.isEmpty(applicantEntity)) {
			TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
					Cnd.where("applicantId", "=", applicantEntity.getId()));
			result.put("applyid", applicantOrderJpEntity.getId());
		}
		return result;
	}

	public Object getVisaInfo(HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = MapUtil.map();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		List<TApplicantEntity> applyList = dbDao.query(TApplicantEntity.class,
				Cnd.where("userId", "=", loginUser.getId()), null);
		Collections.reverse(applyList);
		TApplicantEntity applicantEntity = applyList.get(0);
		TApplicantUnqualifiedEntity unqualifiedEntity = dbDao.fetch(TApplicantUnqualifiedEntity.class,
				Cnd.where("applicantId", "=", applicantEntity.getId()));
		if (!Util.isEmpty(unqualifiedEntity)) {
			result.put("unqualified", unqualifiedEntity);
		}
		TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantEntity.getId()));
		TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId()
				.longValue());
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderJpEntity.getOrderId().longValue());
		result.put("orderid", orderJpEntity.getOrderId());
		result.put("marryStatus", EnumUtil.enum2(MarryStatusEnum.class));
		result.put("mainOrVice", EnumUtil.enum2(MainOrViceEnum.class));
		result.put("isOrNo", EnumUtil.enum2(IsYesOrNoEnum.class));
		result.put("applicantRelation", EnumUtil.enum2(MainApplicantRelationEnum.class));
		result.put("applicantRemark", EnumUtil.enum2(MainApplicantRemarkEnum.class));
		result.put("jobStatusEnum", EnumUtil.enum2(JobStatusEnum.class));
		String visaInfoSqlstr = sqlManager.get("visaInfo_byApplicantId");
		Sql visaInfoSql = Sqls.create(visaInfoSqlstr);
		visaInfoSql.setParam("id", applicantOrderJpEntity.getApplicantId());
		Record visaInfo = dbDao.fetch(visaInfoSql);
		//获取申请人
		result.put("applicant", applicantEntity);
		if (!Util.isEmpty(applicantEntity.getMainId())) {
			TApplicantEntity mainApplicant = dbDao.fetch(TApplicantEntity.class, new Long(applicantEntity.getMainId()));
			if (!Util.isEmpty(mainApplicant)) {
				result.put("mainApplicant", mainApplicant);
			}
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
		//获取所访问的ip地址
		String localAddr = request.getLocalAddr();
		//所访问的端口
		int localPort = request.getLocalPort();
		result.put("localAddr", localAddr);
		result.put("localPort", localPort);
		result.put("websocketaddr", BASIC_WEBSPCKET_ADDR);
		return result;
	}

	public Object saveEditVisa(VisaEditDataForm visaForm, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		TTouristVisaEntity visaEntity = dbDao.fetch(TTouristVisaEntity.class, Cnd.where("userId", "=", userId));
		if (Util.isEmpty(visaEntity)) {//游客签证信息为空，则添加
			TTouristVisaEntity visa = new TTouristVisaEntity();
			visa.setAddress(visaForm.getAddress());
			visa.setCareerStatus(visaForm.getCareerStatus());
			visa.setCreateTime(new Date());
			visa.setUpdateTime(new Date());
			visa.setDetails(visaForm.getDetails());
			visa.setIsMainApplicant(visaForm.getApplicant());
			visa.setMainId(visaForm.getMainApplicant());
			visa.setMainRelation(visaForm.getMainRelation());
			visa.setMarryStatus(visaForm.getMarryStatus());
			visa.setMarryUrl(visaForm.getMarryUrl());
			visa.setName(visaForm.getName());
			visa.setOccupation(visaForm.getOccupation());
			visa.setRelationRemark(visaForm.getRelationRemark());
			visa.setSameMainWealth(visaForm.getSameMainWealth());
			visa.setSameMainWork(visaForm.getSameMainWork());
			visa.setTelephone(visaForm.getTelephone());
			visa.setType(visaForm.getType());
			visa.setUserId(userId);
			visa.setVisaIsCompleted(IsYesOrNoEnum.YES.intKey());
			dbDao.insert(visa);
		} else {
			visaEntity.setAddress(visaForm.getAddress());
			visaEntity.setCareerStatus(visaForm.getCareerStatus());
			visaEntity.setUpdateTime(new Date());
			visaEntity.setDetails(visaForm.getDetails());
			visaEntity.setIsMainApplicant(visaForm.getApplicant());
			visaEntity.setMainId(visaForm.getMainApplicant());
			visaEntity.setMainRelation(visaForm.getMainRelation());
			visaEntity.setMarryStatus(visaForm.getMarryStatus());
			visaEntity.setMarryUrl(visaForm.getMarryUrl());
			visaEntity.setName(visaForm.getName());
			visaEntity.setOccupation(visaForm.getOccupation());
			visaEntity.setRelationRemark(visaForm.getRelationRemark());
			visaEntity.setSameMainWealth(visaForm.getSameMainWealth());
			visaEntity.setSameMainWork(visaForm.getSameMainWork());
			visaEntity.setTelephone(visaForm.getTelephone());
			visaEntity.setType(visaForm.getType());
			int update = dbDao.update(visaEntity);
			if (update > 0) {
				visaEntity.setVisaIsCompleted(IsYesOrNoEnum.YES.intKey());
				dbDao.update(visaEntity);
			}
		}

		if (!Util.isEmpty(visaForm.getSameMainWealth())) {
			//如果游客跟主申请人的财产信息一样，把主申请人的财产信息保存到游客财产信息中
			if (Util.eq(visaForm.getSameMainWealth(), IsYesOrNoEnum.YES.intKey())) {
				//主申请人
				TApplicantEntity mainApply = dbDao.fetch(TApplicantEntity.class, visaForm.getMainApplicant()
						.longValue());
			}
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
			//主申请人
			if (!Util.isEmpty(applicantEntity.getMainId())) {
				TApplicantEntity mainApplicant = dbDao.fetch(TApplicantEntity.class,
						new Long(applicantEntity.getMainId()).intValue());
			}

			if (!Util.isEmpty(applicantOrderJpEntity.getId())) {
				//更新申请人信息
				if (Util.eq(visaForm.getApplicant(), MainOrViceEnum.YES.intKey())) {//是主申请人
					applicantEntity.setMainId(applicantEntity.getId());
					dbDao.update(applicantEntity);
					applicantOrderJpEntity.setIsMainApplicant(MainOrViceEnum.YES.intKey());
				} else {
					if (!Util.isEmpty(visaForm.getMainApplicant())) {
						applicantEntity.setMainId(visaForm.getMainApplicant());
						dbDao.update(applicantEntity);
						applicantOrderJpEntity.setIsMainApplicant(MainOrViceEnum.NO.intKey());
					}
				}

				if (!Util.isEmpty(visaForm.getSameMainWealth())) {
					applicantOrderJpEntity.setSameMainWealth(visaForm.getSameMainWealth());
					//如果申请人跟主申请人的财产信息一样，把主申请人的财产信息保存到申请人财产信息中
					if (Util.eq(visaForm.getSameMainWealth(), IsYesOrNoEnum.YES.intKey())) {
						if (!Util.isEmpty(applicantEntity.getMainId())) {
							TApplicantEntity mainApplicant = dbDao.fetch(TApplicantEntity.class, new Long(
									applicantEntity.getMainId()).intValue());
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
									TApplicantWealthJpEntity.class, Cnd.where("applicantId", "=", mainAppyJp.getId())
											.and("type", "=", ApplicantJpWealthEnum.HOME.value()));
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
				if (!Util.isEmpty(visaForm.getCareerStatus())) {
					Integer careerStatus = visaForm.getCareerStatus();
					Integer applicantJpId = applicantOrderJpEntity.getId();
					List<TApplicantFrontPaperworkJpEntity> frontListDB = dbDao.query(
							TApplicantFrontPaperworkJpEntity.class, Cnd.where("applicantId", "=", applicantJpId), null);
					List<TApplicantVisaPaperworkJpEntity> visaListDB = dbDao.query(
							TApplicantVisaPaperworkJpEntity.class, Cnd.where("applicantId", "=", applicantJpId), null);
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
				}
				applicantWorkJpEntity.setCareerStatus(visaForm.getCareerStatus());
				applicantWorkJpEntity.setName(visaForm.getName());
				applicantWorkJpEntity.setAddress(visaForm.getAddress());
				applicantWorkJpEntity.setTelephone(visaForm.getTelephone());
				applicantWorkJpEntity.setUpdateTime(new Date());
				dbDao.update(applicantWorkJpEntity);
				if (!Util.isEmpty(visaForm.getMainRelation())) {
					applicantOrderJpEntity.setMainRelation(visaForm.getMainRelation());
				}
				if (!Util.isEmpty(visaForm.getRelationRemark())) {
					applicantOrderJpEntity.setRelationRemark(visaForm.getRelationRemark());
				}
				dbDao.update(applicantOrderJpEntity);
			}
		}
		return null;
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

	public Object changeStatus(int orderid, int applicantid, String completeType, HttpSession session) {
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderid);
		TApplicantOrderJpEntity applyJp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantid));
		if (Util.eq(completeType, "base")) {
			applyJp.setBaseIsCompleted(IsYesOrNoEnum.YES.intKey());
		}
		if (Util.eq(completeType, "pass")) {
			applyJp.setPassIsCompleted(IsYesOrNoEnum.YES.intKey());
		}
		if (Util.eq(completeType, "visa")) {
			applyJp.setVisaIsCompleted(IsYesOrNoEnum.YES.intKey());
		}
		dbDao.update(applyJp);
		int count = 0;
		if (!Util.isEmpty(orderEntity)) {
			TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class,
					Cnd.where("orderId", "=", orderEntity.getId()));
			List<TApplicantOrderJpEntity> applyJpList = dbDao.query(TApplicantOrderJpEntity.class,
					Cnd.where("orderId", "=", orderJpEntity.getId()), null);
			for (TApplicantOrderJpEntity tApplicantOrderJpEntity : applyJpList) {
				if (Util.eq(tApplicantOrderJpEntity.getBaseIsCompleted(), IsYesOrNoEnum.YES.intKey())
						&& Util.eq(tApplicantOrderJpEntity.getPassIsCompleted(), IsYesOrNoEnum.YES.intKey())
						&& Util.eq(tApplicantOrderJpEntity.getVisaIsCompleted(), IsYesOrNoEnum.YES.intKey())) {
					count++;
				}
			}
			if (Util.eq(count, applyJpList.size())) {
				Integer orderStatus = orderEntity.getStatus();
				if (orderStatus < JPOrderStatusEnum.FIRSTTRIAL_ORDER.intKey()) {
					orderEntity.setStatus(JPOrderStatusEnum.FILLED_INFORMATION.intKey());
					dbDao.update(orderEntity);
				}
			}
		}
		return null;
	}
}
