package com.juyo.visa.admin.myData.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
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
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.DateUtil;
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
		TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class,
				Cnd.where("userId", "=", loginUser.getId()));
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

		//生成二维码
		String qrurl = "http://" + request.getLocalAddr() + ":" + request.getLocalPort()
				+ "/mobile/info.html?applicantid=" + applicantEntity.getId();
		String qrCode = qrCodeService.encodeQrCode(request, qrurl);
		result.put("qrCode", qrCode);
		result.put("applicantId", applicantEntity.getId());
		result.put("orderid", orderEntity.getId());
		result.put("applicant", applicantEntity);
		return result;
	}

	public Object getPassportInfo(HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = MapUtil.map();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class,
				Cnd.where("userId", "=", loginUser.getId()));
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
		String passportSqlstr = sqlManager.get("orderJp_list_passportInfo_byApplicantId");
		Sql passportSql = Sqls.create(passportSqlstr);
		passportSql.setParam("id", applicantEntity.getId());
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
		if (!Util.isEmpty(passport.get("firstNameEn"))) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(passport.get("firstNameEn"));
			result.put("firstNameEn", sb.toString());
		}

		if (!Util.isEmpty(passport.get("lastNameEn"))) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(passport.get("lastNameEn"));
			result.put("lastNameEn", sb.toString());
		}

		//生成二维码
		String qrurl = "http://" + request.getLocalAddr() + ":" + request.getLocalPort()
				+ "/mobile/info.html?applicantid=" + applicantEntity.getId();
		String qrCode = qrCodeService.encodeQrCode(request, qrurl);
		result.put("qrCode", qrCode);
		result.put("passport", passport);
		result.put("passportType", EnumUtil.enum2(PassportTypeEnum.class));
		result.put("applicantId", applicantEntity.getId());
		result.put("orderid", orderEntity.getId());
		return result;
	}

	public Object visaInput(HttpSession session) {
		Map<String, Object> result = MapUtil.map();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class,
				Cnd.where("userId", "=", loginUser.getId()));
		TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantEntity.getId()));
		result.put("applyid", applicantOrderJpEntity.getId());
		return result;
	}

	public Object getVisaInfo(HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = MapUtil.map();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class,
				Cnd.where("userId", "=", loginUser.getId()));
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

		Date nowDate = DateUtil.nowDate();
		if (!Util.isEmpty(visaForm.getIsOrderUpTime()) && !Util.isEmpty(visaForm.getOrderid())) {
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

				//更新日本申请人信息
				if (!Util.isEmpty(visaForm.getSameMainTrip())) {
					applicantOrderJpEntity.setSameMainTrip(visaForm.getSameMainTrip());
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

					applicantWorkJpEntity.setCareerStatus(visaForm.getCareerStatus());

				}
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
}
