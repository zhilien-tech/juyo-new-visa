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

import com.google.common.collect.Maps;
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
import com.juyo.visa.forms.TApplicantForm;
import com.juyo.visa.forms.TApplicantPassportForm;
import com.juyo.visa.forms.TTouristBaseinfoForm;
import com.juyo.visa.forms.TTouristPassportForm;
import com.juyo.visa.forms.TTouristVisaForm;
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

	public Object getBasicInfo(int contact, int applyId, HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = MapUtil.map();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TTouristBaseinfoEntity baseinfo = new TTouristBaseinfoEntity();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyId);
		TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", apply.getId()));
		TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId()
				.longValue());
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderJpEntity.getOrderId().longValue());
		if (!Util.isEmpty(apply.getUserId())) {
			TTouristBaseinfoEntity touristBaseinfoEntity = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("userId", "=", apply.getUserId()));
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
				TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
						Cnd.where("applicantId", "=", applyId));
				if (!Util.isEmpty(base)) {
					if (!Util.isEmpty(base.getBirthday())) {
						Date birthday = base.getBirthday();
						String birthdayStr = sdf.format(birthday);
						result.put("birthday", birthdayStr);
					}
					if (!Util.isEmpty(base.getValidStartDate())) {
						Date validStartDate = base.getValidStartDate();
						String validStartDateStr = sdf.format(validStartDate);
						result.put("validStartDate", validStartDateStr);
					}
					if (!Util.isEmpty(base.getValidEndDate())) {
						Date validEndDate = base.getValidEndDate();
						String validEndDateStr = sdf.format(validEndDate);
						result.put("validEndDate", validEndDateStr);
					}

					if (!Util.isEmpty(base.getFirstNameEn())) {
						StringBuffer sb = new StringBuffer();
						sb.append("/").append(base.getFirstNameEn());
						result.put("firstNameEn", sb.toString());
					}

					if (!Util.isEmpty(base.getOtherFirstNameEn())) {
						StringBuffer sb = new StringBuffer();
						sb.append("/").append(base.getOtherFirstNameEn());
						result.put("otherFirstNameEn", sb.toString());
					}

					if (!Util.isEmpty(base.getLastNameEn())) {
						StringBuffer sb = new StringBuffer();
						sb.append("/").append(base.getLastNameEn());
						result.put("lastNameEn", sb.toString());
					}

					if (!Util.isEmpty(base.getOtherLastNameEn())) {
						StringBuffer sb = new StringBuffer();
						sb.append("/").append(base.getOtherLastNameEn());
						result.put("otherLastNameEn", sb.toString());
					}
					result.put("applicant", base);
				} else {
					result.put("applicant", baseinfo);
				}
			}
		} else {
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("applicantId", "=", applyId));
			if (!Util.isEmpty(base)) {
				if (!Util.isEmpty(base.getBirthday())) {
					Date birthday = base.getBirthday();
					String birthdayStr = sdf.format(birthday);
					result.put("birthday", birthdayStr);
				}
				if (!Util.isEmpty(base.getValidStartDate())) {
					Date validStartDate = base.getValidStartDate();
					String validStartDateStr = sdf.format(validStartDate);
					result.put("validStartDate", validStartDateStr);
				}
				if (!Util.isEmpty(base.getValidEndDate())) {
					Date validEndDate = base.getValidEndDate();
					String validEndDateStr = sdf.format(validEndDate);
					result.put("validEndDate", validEndDateStr);
				}

				if (!Util.isEmpty(base.getFirstNameEn())) {
					StringBuffer sb = new StringBuffer();
					sb.append("/").append(base.getFirstNameEn());
					result.put("firstNameEn", sb.toString());
				}

				if (!Util.isEmpty(base.getOtherFirstNameEn())) {
					StringBuffer sb = new StringBuffer();
					sb.append("/").append(base.getOtherFirstNameEn());
					result.put("otherFirstNameEn", sb.toString());
				}

				if (!Util.isEmpty(base.getLastNameEn())) {
					StringBuffer sb = new StringBuffer();
					sb.append("/").append(base.getLastNameEn());
					result.put("lastNameEn", sb.toString());
				}

				if (!Util.isEmpty(base.getOtherLastNameEn())) {
					StringBuffer sb = new StringBuffer();
					sb.append("/").append(base.getOtherLastNameEn());
					result.put("otherLastNameEn", sb.toString());
				}
				result.put("applicant", base);
			} else {
				result.put("applicant", baseinfo);
			}
		}
		result.put("contact", contact);
		result.put("applyId", applyId);
		//生成二维码
		String qrurl = "http://" + request.getLocalAddr() + ":" + request.getLocalPort()
				+ "/mobile/info.html?applicantid=" + apply.getId();
		String qrCode = qrCodeService.encodeQrCode(request, qrurl);
		result.put("qrCode", qrCode);
		result.put("applicantId", apply.getId());
		result.put("orderid", orderEntity.getId());
		return result;
	}

	public Object getBasicInfo(HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = MapUtil.map();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		getBase(loginUser.getId(), request, result);
		return result;
	}

	public Object saveEditApplicant(TTouristBaseinfoForm applicantForm, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		if (!Util.isEmpty(applicantForm.getContact())) {//不为空说明是常用联系人过来的
			TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applicantForm.getApplyId().longValue());
			if (Util.isEmpty(apply.getUserId())) {//没有userId说明申请人没有写手机号
				TTouristBaseinfoEntity applyBase = dbDao.fetch(TTouristBaseinfoEntity.class,
						Cnd.where("applicantId", "=", apply.getId()));
				insertOrUpdateBase(applyBase, applicantForm, apply.getUserId());
			} else {//只要申请人有userId,那么对应的游客肯定有userID
				TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
						Cnd.where("userId", "=", apply.getUserId()));
				insertOrUpdateBase(base, applicantForm, apply.getUserId());
			}
		} else {//登录人
			TTouristBaseinfoEntity baseinfoEntity = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("userId", "=", userId));
			if (Util.isEmpty(baseinfoEntity)) {
				TTouristBaseinfoEntity tTouristBaseinfoEntity = new TTouristBaseinfoEntity();
				insertToBase(tTouristBaseinfoEntity, applicantForm, userId);
			} else {
				insertOrUpdateBase(baseinfoEntity, applicantForm, userId);
			}
		}
		return null;
	}

	public Object getPassportInfo(HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = MapUtil.map();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		getPass(loginUser.getId(), request, result);
		return result;
	}

	public Object getPassportInfo(int contact, int applyId, HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = MapUtil.map();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TTouristPassportEntity passinfo = new TTouristPassportEntity();
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyId);
		TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", apply.getId()));
		TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId()
				.longValue());
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderJpEntity.getOrderId().longValue());
		if (!Util.isEmpty(apply.getUserId())) {
			TTouristPassportEntity touristPassportEntity = dbDao.fetch(TTouristPassportEntity.class,
					Cnd.where("userId", "=", apply.getUserId()));
			if (!Util.isEmpty(touristPassportEntity)) {
				//格式化日期
				SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
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
				TTouristPassportEntity pass = dbDao.fetch(TTouristPassportEntity.class,
						Cnd.where("applicantId", "=", applyId));
				if (!Util.isEmpty(pass)) {
					//格式化日期
					SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
					if (!Util.isEmpty(pass.getBirthday())) {
						Date goTripDate = pass.getBirthday();
						result.put("birthday", sdf.format(goTripDate));
					}
					if (!Util.isEmpty(pass.getValidEndDate())) {
						Date goTripDate = pass.getValidEndDate();
						result.put("validEndDate", sdf.format(goTripDate));
					}
					if (!Util.isEmpty(pass.getIssuedDate())) {
						Date goTripDate = pass.getIssuedDate();
						result.put("issuedDate", sdf.format(goTripDate));
					}
					result.put("passport", pass);
				} else {
					result.put("passport", passinfo);
				}
			}
		} else {
			TTouristPassportEntity pass = dbDao.fetch(TTouristPassportEntity.class,
					Cnd.where("applicantId", "=", applyId));
			if (!Util.isEmpty(pass)) {
				//格式化日期
				SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
				if (!Util.isEmpty(pass.getBirthday())) {
					Date goTripDate = pass.getBirthday();
					result.put("birthday", sdf.format(goTripDate));
				}
				if (!Util.isEmpty(pass.getValidEndDate())) {
					Date goTripDate = pass.getValidEndDate();
					result.put("validEndDate", sdf.format(goTripDate));
				}
				if (!Util.isEmpty(pass.getIssuedDate())) {
					Date goTripDate = pass.getIssuedDate();
					result.put("issuedDate", sdf.format(goTripDate));
				}
				result.put("passport", pass);
			} else {
				result.put("passport", passinfo);
			}
		}
		result.put("passportType", EnumUtil.enum2(PassportTypeEnum.class));
		result.put("contact", contact);
		result.put("applyId", applyId);
		//生成二维码
		String qrurl = "http://" + request.getLocalAddr() + ":" + request.getLocalPort()
				+ "/mobile/info.html?applicantid=" + apply.getId();
		String qrCode = qrCodeService.encodeQrCode(request, qrurl);
		result.put("qrCode", qrCode);
		result.put("applicantId", apply.getId());
		result.put("orderid", orderEntity.getId());
		return result;
	}

	public Object saveEditPassport(TTouristPassportForm passportForm, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		if (!Util.isEmpty(passportForm.getContact())) {//从常用联系人跳转过来
			TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, passportForm.getApplyId().longValue());
			if (Util.isEmpty(apply.getUserId())) {
				TTouristPassportEntity applyPass = dbDao.fetch(TTouristPassportEntity.class,
						Cnd.where("applicantId", "=", apply.getId()));
				insertOrUpdatePass(applyPass, passportForm, apply.getUserId());
			} else {
				TTouristPassportEntity pass = dbDao.fetch(TTouristPassportEntity.class,
						Cnd.where("userId", "=", apply.getUserId()));
				insertOrUpdatePass(pass, passportForm, apply.getUserId());
			}
		} else {
			TTouristPassportEntity touristPassportEntity = dbDao.fetch(TTouristPassportEntity.class,
					Cnd.where("userId", "=", userId));
			if (Util.isEmpty(touristPassportEntity)) {
				TTouristPassportEntity tTouristPassportEntity = new TTouristPassportEntity();
				insertToPass(tTouristPassportEntity, passportForm, userId);
			} else {
				insertOrUpdatePass(touristPassportEntity, passportForm, userId);
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
			result.put("orderJpId", applicantOrderJpEntity.getOrderId());
		}
		return result;
	}

	public Object getVisaInfo(HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = MapUtil.map();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		getVisa(loginUser.getId(), request, result);
		result.put("marryStatus", EnumUtil.enum2(MarryStatusEnum.class));
		result.put("mainOrVice", EnumUtil.enum2(MainOrViceEnum.class));
		result.put("isOrNo", EnumUtil.enum2(IsYesOrNoEnum.class));
		result.put("applicantRelation", EnumUtil.enum2(MainApplicantRelationEnum.class));
		result.put("applicantRemark", EnumUtil.enum2(MainApplicantRemarkEnum.class));
		result.put("jobStatusEnum", EnumUtil.enum2(JobStatusEnum.class));
		return result;
	}

	public Object getVisaInfo(int contact, int applyId, HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = MapUtil.map();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TTouristVisaEntity visainfo = new TTouristVisaEntity();
		Integer userId = loginUser.getId();
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyId);
		TApplicantOrderJpEntity applyJp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", apply.getId()));
		TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, applyJp.getOrderId().longValue());
		if (!Util.isEmpty(apply.getUserId())) {
			TTouristVisaEntity visa = dbDao
					.fetch(TTouristVisaEntity.class, Cnd.where("userId", "=", apply.getUserId()));
			if (!Util.isEmpty(visa)) {
				result.put("visaInfo", visa);
			} else {
				TTouristVisaEntity visaEntity = dbDao.fetch(TTouristVisaEntity.class,
						Cnd.where("applicantId", "=", applyId));
				if (!Util.isEmpty(visaEntity)) {
					result.put("visaInfo", visaEntity);
				} else {
					result.put("visaInfo", visainfo);
				}
			}
		} else {
			TTouristVisaEntity visaEntity = dbDao.fetch(TTouristVisaEntity.class,
					Cnd.where("applicantId", "=", applyId));
			if (!Util.isEmpty(visaEntity)) {
				result.put("visaInfo", visaEntity);
			} else {
				result.put("visaInfo", visainfo);
			}
		}
		//获取订单主申请人
		String sqlStr = sqlManager.get("mainApplicant_byOrderId");
		Sql applysql = Sqls.create(sqlStr);
		List<Record> records = dbDao.query(
				applysql,
				Cnd.where("oj.orderId", "=", orderJpEntity.getOrderId()).and("aoj.isMainApplicant", "=",
						IsYesOrNoEnum.YES.intKey()), null);
		result.put("mainApply", records);
		result.put("contact", contact);
		result.put("applyId", applyId);
		result.put("marryStatus", EnumUtil.enum2(MarryStatusEnum.class));
		result.put("mainOrVice", EnumUtil.enum2(MainOrViceEnum.class));
		result.put("isOrNo", EnumUtil.enum2(IsYesOrNoEnum.class));
		result.put("applicantRelation", EnumUtil.enum2(MainApplicantRelationEnum.class));
		result.put("applicantRemark", EnumUtil.enum2(MainApplicantRemarkEnum.class));
		result.put("jobStatusEnum", EnumUtil.enum2(JobStatusEnum.class));

		return result;
	}

	public Object saveEditVisa(TTouristVisaForm visaForm, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		if (!Util.isEmpty(visaForm.getContact())) {
			TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, visaForm.getApplyId().longValue());
			if (!Util.isEmpty(apply.getUserId())) {
				TTouristVisaEntity applyVisa = dbDao.fetch(TTouristVisaEntity.class,
						Cnd.where("userId", "=", apply.getUserId()));
				insertOrUpdateVisa(applyVisa, visaForm, apply.getUserId());
			} else {
				TTouristVisaEntity visa = dbDao.fetch(TTouristVisaEntity.class,
						Cnd.where("applicantId", "=", apply.getId()));
				insertOrUpdateVisa(visa, visaForm, apply.getUserId());
			}
		} else {
			TTouristVisaEntity visaEntity = dbDao.fetch(TTouristVisaEntity.class, Cnd.where("userId", "=", userId));
			if (Util.isEmpty(visaEntity)) {
				TTouristVisaEntity tTouristVisaEntity = new TTouristVisaEntity();
				insertToVisa(tTouristVisaEntity, visaForm, userId);
			} else {
				insertOrUpdateVisa(visaEntity, visaForm, userId);
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

	public Object isEq(Object first, Object last, TTouristBaseinfoEntity base) {
		if (!Util.eq(first, last)) {
			if (Util.eq(base.getBaseIsCompleted(), IsYesOrNoEnum.NO.intKey())) {
				base.setBaseIsCompleted(IsYesOrNoEnum.YES.intKey());
				dbDao.update(base);
			}
		}
		return null;
	}

	public Object isPassEq(Object first, Object last, TTouristPassportEntity pass) {
		if (!Util.eq(first, last)) {
			if (Util.eq(pass.getPassIsCompleted(), IsYesOrNoEnum.NO.intKey())) {
				pass.setPassIsCompleted(IsYesOrNoEnum.YES.intKey());
				dbDao.update(pass);
			}
		}
		return null;
	}

	public Object isVisaEq(Object first, Object last, TTouristVisaEntity visa) {
		if (!Util.eq(first, last)) {
			if (Util.eq(visa.getVisaIsCompleted(), IsYesOrNoEnum.NO.intKey())) {
				visa.setVisaIsCompleted(IsYesOrNoEnum.YES.intKey());
				dbDao.update(visa);
			}
		}
		return null;
	}

	public Object getTouristInfoByTelephone(String telephone, int applicantId, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Map<String, Object> result = MapUtil.map();
		Integer userId = loginUser.getId();
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applicantId);
		if (!Util.isEmpty(apply.getUserId())) {
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("telephone", "=", telephone).and("userId", "=", apply.getUserId()));
			SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			if (!Util.isEmpty(base)) {
				if (!Util.isEmpty(base.getBirthday())) {
					Date birthday = base.getBirthday();
					String birthdayStr = sdf.format(birthday);
					result.put("birthday", birthdayStr);
				}
				if (!Util.isEmpty(base.getValidStartDate())) {
					Date validStartDate = base.getValidStartDate();
					String validStartDateStr = sdf.format(validStartDate);
					result.put("validStartDate", validStartDateStr);
				}
				if (!Util.isEmpty(base.getValidEndDate())) {
					Date validEndDate = base.getValidEndDate();
					String validEndDateStr = sdf.format(validEndDate);
					result.put("validEndDate", validEndDateStr);
				}
			}
			result.put("base", base);
		} else {
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("applicantId", "=", applicantId).and("telephone", "=", telephone));
			SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			if (!Util.isEmpty(base)) {
				if (!Util.isEmpty(base.getBirthday())) {
					Date birthday = base.getBirthday();
					String birthdayStr = sdf.format(birthday);
					result.put("birthday", birthdayStr);
				}
				if (!Util.isEmpty(base.getValidStartDate())) {
					Date validStartDate = base.getValidStartDate();
					String validStartDateStr = sdf.format(validStartDate);
					result.put("validStartDate", validStartDateStr);
				}
				if (!Util.isEmpty(base.getValidEndDate())) {
					Date validEndDate = base.getValidEndDate();
					String validEndDateStr = sdf.format(validEndDate);
					result.put("validEndDate", validEndDateStr);
				}
			}
			result.put("base", base);
		}

		/*if (Util.eq(apply.getUserId(), userId)) {//说明为当前登录用户
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("telephone", "=", telephone).and("userId", "=", userId));
			SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			if (!Util.isEmpty(base)) {
				if (!Util.isEmpty(base.getBirthday())) {
					Date birthday = base.getBirthday();
					String birthdayStr = sdf.format(birthday);
					result.put("birthday", birthdayStr);
				}
				if (!Util.isEmpty(base.getValidStartDate())) {
					Date validStartDate = base.getValidStartDate();
					String validStartDateStr = sdf.format(validStartDate);
					result.put("validStartDate", validStartDateStr);
				}
				if (!Util.isEmpty(base.getValidEndDate())) {
					Date validEndDate = base.getValidEndDate();
					String validEndDateStr = sdf.format(validEndDate);
					result.put("validEndDate", validEndDateStr);
				}
			}
			result.put("base", base);
		} else {
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("applicantId", "=", applicantId).and("telephone", "=", telephone));
			SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			if (!Util.isEmpty(base)) {
				if (!Util.isEmpty(base.getBirthday())) {
					Date birthday = base.getBirthday();
					String birthdayStr = sdf.format(birthday);
					result.put("birthday", birthdayStr);
				}
				if (!Util.isEmpty(base.getValidStartDate())) {
					Date validStartDate = base.getValidStartDate();
					String validStartDateStr = sdf.format(validStartDate);
					result.put("validStartDate", validStartDateStr);
				}
				if (!Util.isEmpty(base.getValidEndDate())) {
					Date validEndDate = base.getValidEndDate();
					String validEndDateStr = sdf.format(validEndDate);
					result.put("validEndDate", validEndDateStr);
				}
			}
			result.put("base", base);
		}*/
		return result;
	}

	public Object getTouristInfoByCard(String cardId, int applicantId, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Map<String, Object> result = MapUtil.map();
		Integer userId = loginUser.getId();
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applicantId);
		if (!Util.isEmpty(apply.getUserId())) {
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class, Cnd.where("cardId", "=", cardId)
					.and("userId", "=", apply.getUserId()));
			SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			if (!Util.isEmpty(base)) {
				if (!Util.isEmpty(base.getBirthday())) {
					Date birthday = base.getBirthday();
					String birthdayStr = sdf.format(birthday);
					result.put("birthday", birthdayStr);
				}
				if (!Util.isEmpty(base.getValidStartDate())) {
					Date validStartDate = base.getValidStartDate();
					String validStartDateStr = sdf.format(validStartDate);
					result.put("validStartDate", validStartDateStr);
				}
				if (!Util.isEmpty(base.getValidEndDate())) {
					Date validEndDate = base.getValidEndDate();
					String validEndDateStr = sdf.format(validEndDate);
					result.put("validEndDate", validEndDateStr);
				}
			}
			result.put("base", base);
		} else {
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("applicantId", "=", applicantId).and("cardId", "=", cardId));
			SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			if (!Util.isEmpty(base)) {
				if (!Util.isEmpty(base.getBirthday())) {
					Date birthday = base.getBirthday();
					String birthdayStr = sdf.format(birthday);
					result.put("birthday", birthdayStr);
				}
				if (!Util.isEmpty(base.getValidStartDate())) {
					Date validStartDate = base.getValidStartDate();
					String validStartDateStr = sdf.format(validStartDate);
					result.put("validStartDate", validStartDateStr);
				}
				if (!Util.isEmpty(base.getValidEndDate())) {
					Date validEndDate = base.getValidEndDate();
					String validEndDateStr = sdf.format(validEndDate);
					result.put("validEndDate", validEndDateStr);
				}
			}
			result.put("base", base);
		}

		/*if (Util.eq(apply.getUserId(), userId)) {
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class, Cnd.where("cardId", "=", cardId)
					.and("userId", "=", userId));
			SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			if (!Util.isEmpty(base)) {
				if (!Util.isEmpty(base.getBirthday())) {
					Date birthday = base.getBirthday();
					String birthdayStr = sdf.format(birthday);
					result.put("birthday", birthdayStr);
				}
				if (!Util.isEmpty(base.getValidStartDate())) {
					Date validStartDate = base.getValidStartDate();
					String validStartDateStr = sdf.format(validStartDate);
					result.put("validStartDate", validStartDateStr);
				}
				if (!Util.isEmpty(base.getValidEndDate())) {
					Date validEndDate = base.getValidEndDate();
					String validEndDateStr = sdf.format(validEndDate);
					result.put("validEndDate", validEndDateStr);
				}
			}
			result.put("base", base);
		} else {
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("applicantId", "=", applicantId).and("cardId", "=", cardId));
			SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			if (!Util.isEmpty(base)) {
				if (!Util.isEmpty(base.getBirthday())) {
					Date birthday = base.getBirthday();
					String birthdayStr = sdf.format(birthday);
					result.put("birthday", birthdayStr);
				}
				if (!Util.isEmpty(base.getValidStartDate())) {
					Date validStartDate = base.getValidStartDate();
					String validStartDateStr = sdf.format(validStartDate);
					result.put("validStartDate", validStartDateStr);
				}
				if (!Util.isEmpty(base.getValidEndDate())) {
					Date validEndDate = base.getValidEndDate();
					String validEndDateStr = sdf.format(validEndDate);
					result.put("validEndDate", validEndDateStr);
				}
			}
			result.put("base", base);
		}*/
		return result;
	}

	public Object getTouristInfoByPass(int applyId, String pass, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Map<String, Object> result = MapUtil.map();
		Integer userId = loginUser.getId();
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyId);
		if (Util.eq(apply.getUserId(), userId)) {
			TTouristPassportEntity passport = dbDao.fetch(TTouristPassportEntity.class, Cnd
					.where("userId", "=", userId).and("passport", "=", pass));
			SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			if (!Util.isEmpty(passport)) {
				if (!Util.isEmpty(passport.getBirthday())) {
					Date birthday = passport.getBirthday();
					String birthdayStr = sdf.format(birthday);
					result.put("birthday", birthdayStr);
				}
				if (!Util.isEmpty(passport.getIssuedDate())) {
					Date validStartDate = passport.getIssuedDate();
					String validStartDateStr = sdf.format(validStartDate);
					result.put("issuedDate", validStartDateStr);
				}
				if (!Util.isEmpty(passport.getValidEndDate())) {
					Date validEndDate = passport.getValidEndDate();
					String validEndDateStr = sdf.format(validEndDate);
					result.put("validEndDate", validEndDateStr);
				}
			}
			result.put("pass", passport);
		} else {
			TTouristPassportEntity passport = dbDao.fetch(TTouristPassportEntity.class,
					Cnd.where("applicantId", "=", applyId).and("passport", "=", pass));
			SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			if (!Util.isEmpty(passport)) {
				if (!Util.isEmpty(passport.getBirthday())) {
					Date birthday = passport.getBirthday();
					String birthdayStr = sdf.format(birthday);
					result.put("birthday", birthdayStr);
				}
				if (!Util.isEmpty(passport.getIssuedDate())) {
					Date validStartDate = passport.getIssuedDate();
					String validStartDateStr = sdf.format(validStartDate);
					result.put("issuedDate", validStartDateStr);
				}
				if (!Util.isEmpty(passport.getValidEndDate())) {
					Date validEndDate = passport.getValidEndDate();
					String validEndDateStr = sdf.format(validEndDate);
					result.put("validEndDate", validEndDateStr);
				}
			}
			result.put("pass", passport);
		}
		return result;
	}

	public Object topContactsList(HttpSession session) {
		long startTime = System.currentTimeMillis();//获取当前时间
		//获取当前用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Map<String, Object> result = Maps.newHashMap();

		List<Record> query = new ArrayList<>();
		List<Record> lastRecords = new ArrayList<>();
		String str = "";
		List<TApplicantEntity> applyList = dbDao.query(TApplicantEntity.class,
				Cnd.where("userId", "=", loginUser.getId()), null);
		String orderSqlstr = sqlManager.get("mydata_orderJpIds");
		Sql orderSql = Sqls.create(orderSqlstr);
		Cnd orderJpCnd = Cnd.NEW();
		orderJpCnd.and("ta.userId", "=", loginUser.getId());
		orderSql.setCondition(orderJpCnd);
		List<Record> orderRecord = dbDao.query(orderSql, orderJpCnd, null);
		for (Record record : orderRecord) {
			Object orderJpStr = record.get("id");
			if (!Util.isEmpty(orderJpStr)) {
				str += (Integer) orderJpStr;
			}
		}

		/*if (!Util.isEmpty(applyList)) {
			for (TApplicantEntity tApplicantEntity : applyList) {
				TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
						Cnd.where("applicantId", "=", tApplicantEntity.getId()));
				if (!Util.isEmpty(applicantOrderJpEntity)) {
					Integer orderId = applicantOrderJpEntity.getOrderId();
					if (!Util.isEmpty(orderId)) {
						TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, orderId.longValue());
						List<TApplicantOrderJpEntity> applyJps = dbDao.query(TApplicantOrderJpEntity.class,
								Cnd.where("orderId", "=", orderJpEntity.getId()), null);
						for (TApplicantOrderJpEntity tApplicantOrderJpEntity : applyJps) {
							TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, tApplicantOrderJpEntity
									.getApplicantId().longValue());
							if (!applyList.contains(apply)) {
								str += apply.getId() + ",";
							}
						}
					}
				}
			}*/

		if (!Util.isEmpty(str)) {
			String applicants = str.substring(0, str.length() - 1);
			String applicantSqlstr = sqlManager.get("orderJp_applicantTable");
			Sql applicantSql = Sqls.create(applicantSqlstr);
			Cnd appcnd = Cnd.NEW();
			appcnd.and("a.id", "in", applicants);
			applicantSql.setCondition(appcnd);
			List<Record> applicantInfo = dbDao.query(applicantSql, appcnd, null);
			for (Record record : applicantInfo) {
				Integer applyId = (Integer) record.get("id");
				TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyId.longValue());
				String applicantSqlStr = sqlManager.get("mydata_inProcessVisa_list");
				Sql applicantsql = Sqls.create(applicantSqlStr);
				Cnd appCnd = Cnd.NEW();
				//appcnd.and("ta.id", "=", applyId);
				if (!Util.isEmpty(apply.getUserId())) {
					appCnd.and("ta.userId", "=", apply.getUserId());
				} else {
					appCnd.and("ta.id", "=", applyId);
				}
				applicantsql.setCondition(appCnd);
				List<Record> query2 = dbDao.query(applicantsql, appCnd, null);
				for (Record record2 : query2) {
					lastRecords.add(record2);
				}
			}
			result.put("visaJapanData", lastRecords);
		}

		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
		return result;
	}

	public Object getBase(int userId, HttpServletRequest request, Map<String, Object> result) {
		TTouristBaseinfoEntity baseinfo = new TTouristBaseinfoEntity();
		TTouristBaseinfoEntity touristBaseinfoEntity = dbDao.fetch(TTouristBaseinfoEntity.class,
				Cnd.where("userId", "=", userId));
		List<TApplicantEntity> applyList = dbDao.query(TApplicantEntity.class, Cnd.where("userId", "=", userId), null);
		Collections.reverse(applyList);
		//获取最新订单的申请人为当前申请人
		TApplicantEntity applicantEntity = applyList.get(0);
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
			result.put("applicant", baseinfo);
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

	public Object getPass(int userId, HttpServletRequest request, Map<String, Object> result) {
		List<TApplicantEntity> applyList = dbDao.query(TApplicantEntity.class, Cnd.where("userId", "=", userId), null);
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
		TTouristPassportEntity pass = new TTouristPassportEntity();
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
			result.put("passport", pass);
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

	public Object getVisa(int userId, HttpServletRequest request, Map<String, Object> result) {
		TTouristVisaEntity touristVisaEntity = dbDao.fetch(TTouristVisaEntity.class, Cnd.where("userId", "=", userId));
		TTouristVisaEntity touristVisa = new TTouristVisaEntity();
		if (!Util.isEmpty(touristVisaEntity)) {
			result.put("visaInfo", touristVisaEntity);
		} else {
			result.put("visaInfo", touristVisa);
		}
		List<TApplicantEntity> applyList = dbDao.query(TApplicantEntity.class, Cnd.where("userId", "=", userId), null);
		Collections.reverse(applyList);
		//当前申请人
		TApplicantEntity applicantEntity = applyList.get(0);
		TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantEntity.getId()));
		TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId()
				.longValue());
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderJpEntity.getOrderId().longValue());
		result.put("orderid", orderJpEntity.getOrderId());
		result.put("applicantid", applicantEntity.getId());

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
		//result.put("visaInfo", visaInfo);
		//获取所访问的ip地址
		String localAddr = request.getLocalAddr();
		//所访问的端口
		int localPort = request.getLocalPort();
		result.put("localAddr", localAddr);
		result.put("localPort", localPort);
		result.put("websocketaddr", BASIC_WEBSPCKET_ADDR);
		return result;
	}

	public Object insertToBase(TTouristBaseinfoEntity baseinfoEntity, TTouristBaseinfoForm applicantForm, int userId) {
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
		if (!Util.isEmpty(applicantForm.getFirstNameEn())) {
			baseinfoEntity.setFirstNameEn(applicantForm.getFirstNameEn().substring(1));
		}
		baseinfoEntity.setHasOtherName(applicantForm.getHasOtherName());
		baseinfoEntity.setHasOtherNationality(applicantForm.getHasOtherNationality());
		baseinfoEntity.setIssueOrganization(applicantForm.getIssueOrganization());
		baseinfoEntity.setLastName(applicantForm.getLastName());
		if (!Util.isEmpty(applicantForm.getLastNameEn())) {
			baseinfoEntity.setLastNameEn(applicantForm.getLastNameEn().substring(1));
		}
		baseinfoEntity.setNation(applicantForm.getNation());
		baseinfoEntity.setNationality(applicantForm.getNationality());
		baseinfoEntity.setOtherFirstName(applicantForm.getOtherFirstName());
		if (!Util.isEmpty(applicantForm.getOtherFirstNameEn())) {
			baseinfoEntity.setOtherFirstNameEn(applicantForm.getOtherFirstNameEn().substring(1));
		}
		baseinfoEntity.setOtherLastName(applicantForm.getOtherLastName());
		if (!Util.isEmpty(applicantForm.getOtherLastNameEn())) {
			baseinfoEntity.setOtherLastNameEn(applicantForm.getOtherLastNameEn().substring(1));
		}
		baseinfoEntity.setProvince(applicantForm.getProvince());
		baseinfoEntity.setSex(applicantForm.getSex());
		baseinfoEntity.setTelephone(applicantForm.getTelephone());
		baseinfoEntity.setValidEndDate(applicantForm.getValidEndDate());
		baseinfoEntity.setValidStartDate(applicantForm.getValidStartDate());
		baseinfoEntity.setUserId(userId);
		baseinfoEntity.setSaveIsPrompted(IsYesOrNoEnum.NO.intKey());
		baseinfoEntity.setUpdateIsPrompted(IsYesOrNoEnum.NO.intKey());
		dbDao.insert(baseinfoEntity);
		return null;
	}

	public Object insertToPass(TTouristPassportEntity touristPassportEntity, TTouristPassportForm passportForm,
			int userId) {
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
		touristPassportEntity.setUserId(userId);
		dbDao.insert(touristPassportEntity);
		return null;
	}

	public Object insertToVisa(TTouristVisaEntity visaEntity, TTouristVisaForm visaForm, int userId) {
		visaEntity.setAddress(visaForm.getAddress());
		visaEntity.setCareerStatus(visaForm.getCareerStatus());
		visaEntity.setUpdateTime(new Date());
		if (!Util.isEmpty(visaForm.getSameMainWealth())) {
			//如果游客跟主申请人的财产信息一样，把主申请人的财产信息保存到游客财产信息中
			if (Util.eq(visaForm.getSameMainWealth(), IsYesOrNoEnum.YES.intKey())) {
				//主申请人
				TApplicantEntity mainApply = dbDao.fetch(TApplicantEntity.class, visaForm.getMainApplicant()
						.longValue());
				TApplicantOrderJpEntity mainApplyJp = dbDao.fetch(TApplicantOrderJpEntity.class,
						Cnd.where("applicantId", "=", mainApply.getId()));
				//获取主申请人的财产信息
				//银行存款
				TApplicantWealthJpEntity mainApplyWealthJp = dbDao.fetch(
						TApplicantWealthJpEntity.class,
						Cnd.where("applicantId", "=", mainApplyJp.getId()).and("type", "=",
								ApplicantJpWealthEnum.BANK.value()));
				if (!Util.isEmpty(mainApplyWealthJp)) {
					visaEntity.setDeposit(mainApplyWealthJp.getDetails());
				} else {
					visaEntity.setDeposit(null);
				}
				//车产
				TApplicantWealthJpEntity mainApplyWealthJpCar = dbDao.fetch(
						TApplicantWealthJpEntity.class,
						Cnd.where("applicantId", "=", mainApplyJp.getId()).and("type", "=",
								ApplicantJpWealthEnum.CAR.value()));
				if (!Util.isEmpty(mainApplyWealthJpCar)) {
					visaEntity.setVehicle(mainApplyWealthJpCar.getDetails());
				} else {
					visaEntity.setVehicle(null);
				}

				//房产
				TApplicantWealthJpEntity mainApplyWealthJpHome = dbDao.fetch(
						TApplicantWealthJpEntity.class,
						Cnd.where("applicantId", "=", mainApplyJp.getId()).and("type", "=",
								ApplicantJpWealthEnum.HOME.value()));
				if (!Util.isEmpty(mainApplyWealthJpHome)) {
					visaEntity.setHouseProperty(mainApplyWealthJpHome.getDetails());
				} else {
					visaEntity.setHouseProperty(null);
				}

				//理财
				TApplicantWealthJpEntity mainApplyWealthJpLi = dbDao.fetch(
						TApplicantWealthJpEntity.class,
						Cnd.where("applicantId", "=", mainApplyJp.getId()).and("type", "=",
								ApplicantJpWealthEnum.LICAI.value()));
				if (!Util.isEmpty(mainApplyWealthJpLi)) {
					visaEntity.setFinancial(mainApplyWealthJpLi.getDetails());
				} else {
					visaEntity.setFinancial(null);
				}

			} else {
				visaEntity.setDeposit(visaForm.getDeposit());
				visaEntity.setHouseProperty(visaForm.getHouseProperty());
				visaEntity.setFinancial(visaForm.getFinancial());
				visaEntity.setVehicle(visaForm.getVehicle());
			}
		}
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
		visaEntity.setUserId(userId);
		dbDao.insert(visaEntity);
		return null;
	}

	public Object insertOrUpdateBase(TTouristBaseinfoEntity baseinfoEntity, TTouristBaseinfoForm applicantForm,
			int userId) {
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
		if (!Util.isEmpty(applicantForm.getFirstNameEn())) {
			baseinfoEntity.setFirstNameEn(applicantForm.getFirstNameEn().substring(1));
		}
		baseinfoEntity.setHasOtherName(applicantForm.getHasOtherName());
		baseinfoEntity.setHasOtherNationality(applicantForm.getHasOtherNationality());
		baseinfoEntity.setIssueOrganization(applicantForm.getIssueOrganization());
		baseinfoEntity.setLastName(applicantForm.getLastName());
		if (!Util.isEmpty(applicantForm.getLastNameEn())) {
			baseinfoEntity.setLastNameEn(applicantForm.getLastNameEn().substring(1));
		}
		baseinfoEntity.setNation(applicantForm.getNation());
		baseinfoEntity.setNationality(applicantForm.getNationality());
		baseinfoEntity.setOtherFirstName(applicantForm.getOtherFirstName());
		if (!Util.isEmpty(applicantForm.getOtherFirstNameEn())) {
			baseinfoEntity.setOtherFirstNameEn(applicantForm.getOtherFirstNameEn().substring(1));
		}
		baseinfoEntity.setOtherLastName(applicantForm.getOtherLastName());
		if (!Util.isEmpty(applicantForm.getOtherLastNameEn())) {
			baseinfoEntity.setOtherLastNameEn(applicantForm.getOtherLastNameEn().substring(1));
		}
		baseinfoEntity.setProvince(applicantForm.getProvince());
		baseinfoEntity.setSex(applicantForm.getSex());
		baseinfoEntity.setTelephone(applicantForm.getTelephone());
		baseinfoEntity.setValidEndDate(applicantForm.getValidEndDate());
		baseinfoEntity.setValidStartDate(applicantForm.getValidStartDate());
		dbDao.update(baseinfoEntity);
		return null;
	}

	public Object insertOrUpdatePass(TTouristPassportEntity touristPassportEntity, TTouristPassportForm passportForm,
			int userId) {
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
		dbDao.update(touristPassportEntity);
		return null;
	}

	public Object insertOrUpdateVisa(TTouristVisaEntity visaEntity, TTouristVisaForm visaForm, int userId) {
		visaEntity.setAddress(visaForm.getAddress());
		visaEntity.setCareerStatus(visaForm.getCareerStatus());
		visaEntity.setUpdateTime(new Date());
		if (!Util.isEmpty(visaForm.getSameMainWealth())) {
			//如果游客跟主申请人的财产信息一样，把主申请人的财产信息保存到游客财产信息中
			if (Util.eq(visaForm.getSameMainWealth(), IsYesOrNoEnum.YES.intKey())) {
				//主申请人
				TApplicantEntity mainApply = dbDao.fetch(TApplicantEntity.class, visaForm.getMainApplicant()
						.longValue());
				TApplicantOrderJpEntity mainApplyJp = dbDao.fetch(TApplicantOrderJpEntity.class,
						Cnd.where("applicantId", "=", mainApply.getId()));
				//获取主申请人的财产信息
				//银行存款
				TApplicantWealthJpEntity mainApplyWealthJp = dbDao.fetch(
						TApplicantWealthJpEntity.class,
						Cnd.where("applicantId", "=", mainApplyJp.getId()).and("type", "=",
								ApplicantJpWealthEnum.BANK.value()));
				if (!Util.isEmpty(mainApplyWealthJp)) {
					visaEntity.setDeposit(mainApplyWealthJp.getDetails());
				} else {
					visaEntity.setDeposit(null);
				}
				//车产
				TApplicantWealthJpEntity mainApplyWealthJpCar = dbDao.fetch(
						TApplicantWealthJpEntity.class,
						Cnd.where("applicantId", "=", mainApplyJp.getId()).and("type", "=",
								ApplicantJpWealthEnum.CAR.value()));
				if (!Util.isEmpty(mainApplyWealthJpCar)) {
					visaEntity.setVehicle(mainApplyWealthJpCar.getDetails());
				} else {
					visaEntity.setVehicle(null);
				}

				//房产
				TApplicantWealthJpEntity mainApplyWealthJpHome = dbDao.fetch(
						TApplicantWealthJpEntity.class,
						Cnd.where("applicantId", "=", mainApplyJp.getId()).and("type", "=",
								ApplicantJpWealthEnum.HOME.value()));
				if (!Util.isEmpty(mainApplyWealthJpHome)) {
					visaEntity.setHouseProperty(mainApplyWealthJpHome.getDetails());
				} else {
					visaEntity.setHouseProperty(null);
				}

				//理财
				TApplicantWealthJpEntity mainApplyWealthJpLi = dbDao.fetch(
						TApplicantWealthJpEntity.class,
						Cnd.where("applicantId", "=", mainApplyJp.getId()).and("type", "=",
								ApplicantJpWealthEnum.LICAI.value()));
				if (!Util.isEmpty(mainApplyWealthJpLi)) {
					visaEntity.setFinancial(mainApplyWealthJpLi.getDetails());
				} else {
					visaEntity.setFinancial(null);
				}

			} else {
				visaEntity.setDeposit(visaForm.getDeposit());
				visaEntity.setHouseProperty(visaForm.getHouseProperty());
				visaEntity.setFinancial(visaForm.getFinancial());
				visaEntity.setVehicle(visaForm.getVehicle());
			}
		}
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
		dbDao.update(visaEntity);
		return null;
	}

	public Object baseIsChanged(TApplicantForm applicantForm, HttpSession session) {
		//通过申请人ID获取当前申请人信息
		Integer applyId = applicantForm.getId();
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyId.longValue());
		if (Util.isEmpty(apply.getUserId())) {//userId为空说明没有填写手机号,需要根据申请人ID来查询游客信息
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("applicantId", "=", applyId));
			if (Util.isEmpty(base)) {
				return 2;
			} else {
				int eqBase = isEqBase(applicantForm, base);
				if (Util.eq(eqBase, 1)) {
					base.setIsSameInfo(IsYesOrNoEnum.NO.intKey());
					dbDao.update(base);
					return 1;
				} else {
					return 0;
				}
			}
		} else {//userId不为空时，根据userId来查询
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("userId", "=", apply.getUserId()));
			if (Util.isEmpty(base)) {
				return 2;
			} else {
				int eqBase = isEqBase(applicantForm, base);
				if (Util.eq(eqBase, 1)) {
					base.setIsSameInfo(IsYesOrNoEnum.NO.intKey());
					dbDao.update(base);
					return 1;
				} else {
					return 0;
				}
			}
		}
	}

	public Object passIsChanged(TApplicantPassportForm passportForm, HttpSession session) {
		//通过申请人ID获取当前申请人信息
		Integer applyId = passportForm.getApplicantId();
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyId.longValue());
		if (Util.isEmpty(apply.getUserId())) {//userId为空说明没有填写手机号,需要根据申请人ID来查询游客信息
			TTouristPassportEntity pass = dbDao.fetch(TTouristPassportEntity.class,
					Cnd.where("applicantId", "=", applyId));
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("applicantId", "=", applyId));
			if (Util.isEmpty(pass)) {
				return 2;
			} else {
				int eqPass = isEqPass(passportForm, pass);
				if (Util.eq(eqPass, 1)) {
					base.setIsSameInfo(IsYesOrNoEnum.NO.intKey());
					dbDao.update(base);
					return 1;
				} else {
					return 0;
				}
			}
		} else {//userId不为空时，根据userId来查询
			TTouristPassportEntity pass = dbDao.fetch(TTouristPassportEntity.class,
					Cnd.where("userId", "=", apply.getUserId()));
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("userId", "=", apply.getUserId()));
			if (Util.isEmpty(pass)) {
				return 2;
			} else {
				int eqPass = isEqPass(passportForm, pass);
				if (Util.eq(eqPass, 1)) {
					base.setIsSameInfo(IsYesOrNoEnum.NO.intKey());
					dbDao.update(base);
					return 1;
				} else {
					return 0;
				}
			}
		}
	}

	public Object visaIsChanged(VisaEditDataForm visaForm, HttpSession session) {
		//通过申请人ID获取当前申请人信息
		Integer applyId = visaForm.getApplicantId();
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyId.longValue());
		if (Util.isEmpty(apply.getUserId())) {//userId为空说明没有填写手机号,需要根据申请人ID来查询游客信息
			TTouristVisaEntity visa = dbDao.fetch(TTouristVisaEntity.class, Cnd.where("applicantId", "=", applyId));
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("applicantId", "=", applyId));
			if (Util.isEmpty(visa)) {
				return 2;
			} else {
				int eqVisa = isEqVisa(visaForm, visa);
				if (Util.eq(eqVisa, 1)) {
					base.setIsSameInfo(IsYesOrNoEnum.NO.intKey());
					dbDao.update(base);
					return 1;
				} else {
					return 0;
				}
			}
		} else {//userId不为空时，根据userId来查询
			TTouristVisaEntity visa = dbDao
					.fetch(TTouristVisaEntity.class, Cnd.where("userId", "=", apply.getUserId()));
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("userId", "=", apply.getUserId()));
			if (Util.isEmpty(visa)) {
				return 2;
			} else {
				int eqVisa = isEqVisa(visaForm, visa);
				if (Util.eq(eqVisa, 1)) {
					base.setIsSameInfo(IsYesOrNoEnum.NO.intKey());
					dbDao.update(base);
					return 1;
				} else {
					return 0;
				}
			}
		}
	}

	public Object infoIsChanged(int applyid, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TTouristBaseinfoEntity loginBase = dbDao.fetch(TTouristBaseinfoEntity.class,
				Cnd.where("userId", "=", loginUser.getId()));
		Map<String, Object> result = MapUtil.map();
		Integer userId = loginUser.getId();
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyid);
		if (!Util.isEmpty(apply.getUserId())) {
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("userId", "=", apply.getUserId()));
			if (Util.isEmpty(base)) {
				result.put("base", 2);
			} else {
				result.put("base", base.getIsSameInfo());
			}
		} else {
			TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
					Cnd.where("applicantId", "=", applyid));
			if (Util.isEmpty(base)) {
				result.put("base", 2);
			} else {
				result.put("base", base.getIsSameInfo());
			}
		}
		if (Util.isEmpty(loginBase)) {
			result.put("isPrompted", 2);
			result.put("isUpdated", 2);
		} else {
			//查询是否提示过
			result.put("isPrompted", loginBase.getSaveIsPrompted());
			//查询是否更新
			result.put("isUpdated", loginBase.getSaveIsOrNot());
		}
		return result;
	}

	public int isEqBase(TApplicantForm applicantForm, TTouristBaseinfoEntity base) {
		if (!Util.eq(applicantForm.getAddress(), base.getAddress())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getAddressIsSameWithCard(), base.getAddressIsSameWithCard())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getBirthday(), base.getBirthday())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getCardBack(), base.getCardBack())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getCardFront(), base.getCardFront())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getCardId(), base.getCardId())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getCity(), base.getCity())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getDetailedAddress(), base.getDetailedAddress())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getEmail(), base.getEmail())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getEmergencyLinkman(), base.getEmergencyLinkman())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getEmergencyTelephone(), base.getEmergencyTelephone())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getFirstName(), base.getFirstName())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getHasOtherName(), base.getHasOtherName())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getHasOtherNationality(), base.getHasOtherNationality())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getIssueOrganization(), base.getIssueOrganization())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getLastName(), base.getLastName())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getNation(), base.getNation())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getNationality(), base.getNationality())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getOtherFirstName(), base.getOtherFirstName())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getOtherLastName(), base.getOtherLastName())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getProvince(), base.getProvince())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getSex(), base.getSex())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getTelephone(), base.getTelephone())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getValidEndDate(), base.getValidEndDate())) {
			return 1;
		}
		if (!Util.eq(applicantForm.getValidStartDate(), base.getValidStartDate())) {
			return 1;
		}
		return 0;
	}

	public int isEqPass(TApplicantPassportForm passportForm, TTouristPassportEntity pass) {
		if (!Util.eq(passportForm.getBirthAddress(), pass.getBirthAddress())) {
			return 1;
		}
		if (!Util.eq(passportForm.getBirthday(), pass.getBirthday())) {
			return 1;
		}
		if (!Util.eq(passportForm.getIssuedDate(), pass.getIssuedDate())) {
			return 1;
		}
		if (!Util.eq(passportForm.getIssuedPlace(), pass.getIssuedPlace())) {
			return 1;
		}
		if (!Util.eq(passportForm.getPassport(), pass.getPassport())) {
			return 1;
		}
		if (!Util.eq(passportForm.getPassportUrl(), pass.getPassportUrl())) {
			return 1;
		}
		if (!Util.eq(passportForm.getSex(), pass.getSex())) {
			return 1;
		}
		if (!Util.eq(passportForm.getSexEn(), pass.getSexEn())) {
			return 1;
		}
		if (!Util.eq(passportForm.getType(), pass.getType())) {
			return 1;
		}
		if (!Util.eq(passportForm.getValidEndDate(), pass.getValidEndDate())) {
			return 1;
		}
		if (!Util.eq(passportForm.getValidStartDate(), pass.getValidStartDate())) {
			return 1;
		}
		if (!Util.eq(passportForm.getValidType(), pass.getValidType())) {
			return 1;
		}
		return 0;
	}

	public int isEqVisa(VisaEditDataForm visaForm, TTouristVisaEntity visa) {
		if (!Util.eq(visaForm.getAddress(), visa.getAddress())) {
			return 1;
		}
		if (!Util.eq(visaForm.getCareerStatus(), visa.getCareerStatus())) {
			return 1;
		}
		if (!Util.eq(visaForm.getApplicant(), visa.getIsMainApplicant())) {
			return 1;
		}
		/*if (!Util.eq(visaForm.getMainApplicant(), visa.getMainId())) {
			return 1;
		}*/
		if (!Util.isEmpty(visaForm.getMainRelation())) {
			if (!Util.eq(visaForm.getMainRelation(), visa.getMainRelation())) {
				return 1;
			}
		}
		if (!Util.eq(visaForm.getMarryStatus(), visa.getMarryStatus())) {
			return 1;
		}
		if (!Util.eq(visaForm.getMarryUrl(), visa.getMarryUrl())) {
			return 1;
		}
		if (!Util.eq(visaForm.getName(), visa.getName())) {
			return 1;
		}
		if (!Util.isEmpty(visaForm.getRelationRemark())) {
			if (!Util.eq(visaForm.getRelationRemark(), visa.getRelationRemark())) {
				return 1;
			}
		}
		if (!Util.eq(visaForm.getSameMainWealth(), visa.getSameMainWealth())) {
			return 1;
		}
		if (!Util.eq(visaForm.getTelephone(), visa.getTelephone())) {
			return 1;
		}
		if (Util.isEmpty(visaForm.getDeposit())) {
			visaForm.setDeposit(null);
			if (!Util.eq(visaForm.getDeposit(), visa.getDeposit())) {
				return 1;
			}
		} else {
			if (!Util.eq(visaForm.getDeposit(), visa.getDeposit())) {
				return 1;
			}
		}
		if (Util.isEmpty(visaForm.getFinancial())) {
			visaForm.setFinancial(null);
			if (!Util.eq(visaForm.getFinancial(), visa.getFinancial())) {
				return 1;
			}
		} else {
			if (!Util.eq(visaForm.getFinancial(), visa.getFinancial())) {
				return 1;
			}
		}
		if (Util.isEmpty(visaForm.getHouseProperty())) {
			visaForm.setHouseProperty(null);
			if (!Util.eq(visaForm.getHouseProperty(), visa.getHouseProperty())) {
				return 1;
			}
		} else {
			if (!Util.eq(visaForm.getHouseProperty(), visa.getHouseProperty())) {
				return 1;
			}
		}
		if (Util.isEmpty(visaForm.getVehicle())) {
			visaForm.setVehicle(null);
			if (!Util.eq(visaForm.getVehicle(), visa.getVehicle())) {
				return 1;
			}
		} else {
			if (!Util.eq(visaForm.getVehicle(), visa.getVehicle())) {
				return 1;
			}
		}
		return 0;
	}

	public Object isPrompted(int applyid, HttpSession session) {
		Map<String, Object> result = MapUtil.map();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TTouristBaseinfoEntity base = dbDao.fetch(TTouristBaseinfoEntity.class,
				Cnd.where("userId", "=", loginUser.getId()));
		result.put("base", base.getUpdateIsPrompted());
		result.put("updateIsOrNot", base.getUpdateIsOrNot());
		return result;
	}

}
