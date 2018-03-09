/**
 * SimpleMobileService.java
 * com.juyo.visa.admin.simple.service
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.simple.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.springframework.web.socket.TextMessage;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.mobile.form.MobileApplicantForm;
import com.juyo.visa.admin.mobile.service.MobileService;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.TrialApplicantStatusEnum;
import com.juyo.visa.common.util.MapUtil;
import com.juyo.visa.common.util.SpringContextUtil;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantLowerEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TApplicantPassportEntity;
import com.juyo.visa.entities.TApplicantPassportLowerEntity;
import com.juyo.visa.entities.TApplicantVisaOtherInfoEntity;
import com.juyo.visa.entities.TApplicantWorkJpEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.websocket.SimpleInfoWSHandler;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年2月1日 	 
 */
@IocBean
public class SimpleMobileService extends BaseService<TOrderEntity> {

	@Inject
	private MobileService mobileService;
	@Inject
	private SimpleVisaService simpleVisaService;

	SimpleInfoWSHandler simpleInfoWSHandler = (SimpleInfoWSHandler) SpringContextUtil.getBean("mySimpleInfoHander",
			SimpleInfoWSHandler.class);

	/**
	 * 保存基本信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param applicantEntity
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveApplicatinfo(MobileApplicantForm form, TApplicantLowerEntity applicant) {
		TUserEntity mobileuser = null;
		if (!Util.isEmpty(applicant.getTelephone())) {
			mobileuser = dbDao.fetch(TUserEntity.class, Cnd.where("mobile", "=", applicant.getTelephone()));
		}
		//编辑申请人信息
		if (!Util.isEmpty(applicant.getId())) {
			/*if (!Util.isEmpty(mobileuser)) {
				applicant.setUserid(mobileuser.getId());
			} else {
				ApplicantUser applicantUser = new ApplicantUser();
				applicantUser.setMobile(applicant.getTelephone());
				applicantUser.setOpid(form.getUserid());
				applicantUser.setPassword("000000");
				applicantUser.setUsername(applicant.getFirstname() + applicant.getLastname());
				TUserEntity tUserEntity = userViewService.addApplicantUser(applicantUser);
				applicant.setUserid(tUserEntity.getId());
			}*/
			dbDao.update(applicant);
			form.setMessagetype(1);
		} else {
			//在用户表添加信息
			/*if (!Util.isEmpty(mobileuser)) {
				applicant.setUserid(mobileuser.getId());
			} else {
				ApplicantUser applicantUser = new ApplicantUser();
				applicantUser.setMobile(applicant.getTelephone());
				applicantUser.setOpid(form.getUserid());
				applicantUser.setPassword("000000");
				applicantUser.setUsername(applicant.getFirstname() + applicant.getLastname());
				TUserEntity tUserEntity = userViewService.addApplicantUser(applicantUser);
				applicant.setUserid(tUserEntity.getId());
			}*/

			applicant.setStatus(TrialApplicantStatusEnum.FIRSTTRIAL.intKey());
			applicant.setCreatetime(new Date());
			TApplicantLowerEntity applicatinsert = dbDao.insert(applicant);
			//设置申请人id，在编辑护照信息时候使用
			form.setApplicantid(applicatinsert.getId());
			form.setMessagetype(4);
			Integer orderjpid = null;
			//如果订单存在则保存订单
			if (!Util.isEmpty(form.getOrderid())) {
				//查询日本订单表信息
				TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, form.getOrderid().longValue());
				orderjpid = orderjp.getId();

			} else {
				//如果订单不存在，则先创建订单
				TOrderEntity orderinfo = new TOrderEntity();
				orderinfo.setComId(form.getComid());
				orderinfo.setUserId(form.getUserid());
				orderinfo.setOrderNum(mobileService.generrateOrdernum());
				orderinfo.setStatus(JPOrderStatusEnum.PLACE_ORDER.intKey());
				orderinfo.setCreateTime(new Date());
				orderinfo.setUpdateTime(new Date());
				TOrderEntity orderinsert = dbDao.insert(orderinfo);
				TOrderJpEntity orderjp = new TOrderJpEntity();
				orderjp.setOrderId(orderinsert.getId());
				TOrderJpEntity orderjpinsert = dbDao.insert(orderjp);
				orderjpid = orderjpinsert.getId();
				form.setOrderid(orderjpid);
			}
			//保存日本申请人信息
			TApplicantOrderJpEntity applicantjp = new TApplicantOrderJpEntity();
			//设置主申请人信息
			List<TApplicantOrderJpEntity> orderapplicant = dbDao.query(TApplicantOrderJpEntity.class,
					Cnd.where("orderId", "=", orderjpid), null);
			if (!Util.isEmpty(orderapplicant) && orderapplicant.size() >= 1) {

			} else {
				//设置为主申请人
				applicantjp.setIsMainApplicant(IsYesOrNoEnum.YES.intKey());
				applicatinsert.setMainid(applicatinsert.getId());
				dbDao.update(applicatinsert);
			}
			applicantjp.setOrderId(orderjpid);
			applicantjp.setApplicantId(applicatinsert.getId());
			TApplicantOrderJpEntity applicantjpinsert = dbDao.insert(applicantjp);
			Integer applicantJpId = applicantjpinsert.getId();

			//日本工作信息
			TApplicantWorkJpEntity workJp = new TApplicantWorkJpEntity();
			workJp.setApplicantId(applicantJpId);
			workJp.setCreateTime(new Date());
			workJp.setOpId(form.getUserid());
			dbDao.insert(workJp);
			//护照信息
			TApplicantPassportEntity passport = new TApplicantPassportEntity();
			passport.setSex(applicant.getSex());
			passport.setFirstName(applicant.getFirstname());
			passport.setLastName(applicant.getLastname());
			if (!Util.isEmpty(applicant.getFirstnameen())) {
				passport.setFirstNameEn(applicant.getFirstnameen());
			}
			if (!Util.isEmpty(applicant.getLastnameen())) {
				passport.setLastNameEn(applicant.getLastnameen());
			}
			passport.setIssuedOrganization("公安部出入境管理局");
			passport.setIssuedOrganizationEn("MPS Exit&Entry Adiministration");
			passport.setApplicantId(applicatinsert.getId());
			dbDao.insert(passport);
		}
		try {
			//刷新电脑端页面
			simpleInfoWSHandler.broadcast(new TextMessage(JsonUtil.toJson(form)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return form;

	}

	/***
	 * 保存护照信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param passportinfo
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object savePassportInfo(TApplicantPassportLowerEntity passportinfo, MobileApplicantForm form) {
		if (!Util.isEmpty(passportinfo.getId())) {
			dbDao.update(passportinfo);
		} else {
			Integer orderjpid = form.getOrderid();
			if (Util.isEmpty(orderjpid)) {
				TUserEntity loginUser = new TUserEntity();
				if (!Util.isEmpty(form.getUserid())) {
					loginUser = dbDao.fetch(TUserEntity.class, form.getUserid().longValue());
				}
				TCompanyEntity loginCompany = new TCompanyEntity();
				if (!Util.isEmpty(form.getComid())) {
					loginCompany = dbDao.fetch(TCompanyEntity.class, form.getComid().longValue());
				}
				Map<String, Integer> generrateorder = simpleVisaService.generrateorder(loginUser, loginCompany);
				orderjpid = generrateorder.get("orderjpid");
				form.setOrderid(orderjpid);
			}
			TApplicantEntity applicantEntity = new TApplicantEntity();
			applicantEntity.setFirstName(passportinfo.getFirstname());
			applicantEntity.setFirstNameEn(passportinfo.getFirstnameen());
			applicantEntity.setLastName(passportinfo.getLastname());
			applicantEntity.setLastNameEn(passportinfo.getLastnameen());
			TApplicantEntity insertapplicant = dbDao.insert(applicantEntity);
			TApplicantOrderJpEntity applicantjp = new TApplicantOrderJpEntity();
			applicantjp.setApplicantId(insertapplicant.getId());
			applicantjp.setOrderId(orderjpid);
			applicantjp.setBaseIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setPassIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setVisaIsCompleted(IsYesOrNoEnum.NO.intKey());
			//设置主申请人信息
			List<TApplicantOrderJpEntity> orderapplicant = dbDao.query(TApplicantOrderJpEntity.class,
					Cnd.where("orderId", "=", orderjpid), null);
			if (!Util.isEmpty(orderapplicant) && orderapplicant.size() >= 1) {

				applicantjp.setIsMainApplicant(IsYesOrNoEnum.NO.intKey());
			} else {
				//设置为主申请人
				applicantjp.setIsMainApplicant(IsYesOrNoEnum.YES.intKey());
				insertapplicant.setMainId(insertapplicant.getId());
				dbDao.update(insertapplicant);
			}
			TApplicantOrderJpEntity insertappjp = dbDao.insert(applicantjp);
			TApplicantWorkJpEntity workJp = new TApplicantWorkJpEntity();
			workJp.setApplicantId(insertappjp.getId());
			workJp.setCreateTime(new Date());
			dbDao.insert(workJp);
			passportinfo.setApplicantid(insertapplicant.getId());
			dbDao.insert(passportinfo);
		}
		form.setApplicantid(passportinfo.getApplicantid());
		form.setMessagetype(2);
		try {
			simpleInfoWSHandler.broadcast(new TextMessage(JsonUtil.toJson(form)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return form;
	}

	/**
	 * 保存签证信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param applicantid
	 * @param marrystatus
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveVisaInfo(Integer applicantid, Integer marrystatus) {
		TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, applicantid.longValue());
		applicant.setMarryStatus(marrystatus);
		dbDao.update(applicant);

		try {
			//刷新电脑端页面
			MobileApplicantForm form = new MobileApplicantForm();
			form.setApplicantid(applicantid);
			form.setMessagetype(3);
			simpleInfoWSHandler.broadcast(new TextMessage(JsonUtil.toJson(form)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取签证其他信息数据
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param applicantid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getVisaOtherInfoData(Integer applicantid) {
		Map<String, Object> result = Maps.newHashMap();
		TApplicantOrderJpEntity applicantjp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantid));
		TApplicantVisaOtherInfoEntity otherinfo = dbDao.fetch(TApplicantVisaOtherInfoEntity.class,
				Cnd.where("applicantid", "=", applicantjp.getId()));
		if (Util.isEmpty(otherinfo)) {
			otherinfo = new TApplicantVisaOtherInfoEntity();
			otherinfo.setApplicantid(applicantjp.getId());
			dbDao.insert(otherinfo);
		}
		Map<String, String> obj2Map = MapUtil.obj2Map(otherinfo);
		DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		if (!Util.isEmpty(otherinfo.getVouchbirth())) {
			String vouchbirth = format.format(otherinfo.getVouchbirth());
			obj2Map.put("vouchbirth", vouchbirth);
		}
		if (!Util.isEmpty(otherinfo.getInvitebirth())) {
			String invitebirth = format.format(otherinfo.getInvitebirth());
			obj2Map.put("invitebirth", invitebirth);
		}
		result.put("otherinfo", obj2Map);
		return result;
	}

	/**
	 * 保存酒店信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveHotelInfo(TApplicantVisaOtherInfoEntity form) {
		TApplicantOrderJpEntity applicantjp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", form.getApplicantid()));
		TApplicantVisaOtherInfoEntity otherinfo = dbDao.fetch(TApplicantVisaOtherInfoEntity.class,
				Cnd.where("applicantid", "=", applicantjp.getId()));
		otherinfo.setHotelname(form.getHotelname());
		otherinfo.setHotelphone(form.getHotelphone());
		otherinfo.setHoteladdress(form.getHoteladdress());
		return dbDao.update(otherinfo);
	}

	/**
	 * 保存在日担保人信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveVouchInfo(TApplicantVisaOtherInfoEntity form) {
		TApplicantOrderJpEntity applicantjp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", form.getApplicantid()));
		TApplicantVisaOtherInfoEntity otherinfo = dbDao.fetch(TApplicantVisaOtherInfoEntity.class,
				Cnd.where("applicantid", "=", applicantjp.getId()));
		otherinfo.setVouchname(form.getVouchname());
		otherinfo.setVouchphone(form.getVouchphone());
		otherinfo.setVouchaddress(form.getVouchaddress());
		otherinfo.setVouchbirth(form.getVouchbirth());
		otherinfo.setVouchsex(form.getVouchsex());
		otherinfo.setVouchmainrelation(form.getVouchmainrelation());
		otherinfo.setVouchjob(form.getVouchjob());
		otherinfo.setVouchcountry(form.getVouchcountry());
		return dbDao.update(otherinfo);
	}

	/**
	 * 保存邀请人信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveInviteInfo(TApplicantVisaOtherInfoEntity form) {
		TApplicantOrderJpEntity applicantjp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", form.getApplicantid()));
		TApplicantVisaOtherInfoEntity otherinfo = dbDao.fetch(TApplicantVisaOtherInfoEntity.class,
				Cnd.where("applicantid", "=", applicantjp.getId()));
		otherinfo.setInvitename(form.getInvitename());
		otherinfo.setInvitephone(form.getInvitephone());
		otherinfo.setInviteaddress(form.getInviteaddress());
		otherinfo.setInvitebirth(form.getInvitebirth());
		otherinfo.setInvitesex(form.getInvitesex());
		otherinfo.setInvitemainrelation(form.getInvitemainrelation());
		otherinfo.setInvitejob(form.getInvitejob());
		otherinfo.setInvitecountry(form.getInvitecountry());
		return dbDao.update(otherinfo);
	}

	/**
	 * 保存其他信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveOtherInfo(TApplicantVisaOtherInfoEntity form) {
		TApplicantOrderJpEntity applicantjp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", form.getApplicantid()));
		TApplicantVisaOtherInfoEntity otherinfo = dbDao.fetch(TApplicantVisaOtherInfoEntity.class,
				Cnd.where("applicantid", "=", applicantjp.getId()));
		otherinfo.setTraveladvice(form.getTraveladvice());
		return dbDao.update(otherinfo);
	}
}
