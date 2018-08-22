/**
 * MobileService.java
 * com.juyo.visa.admin.mobile.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.mobile.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.itextpdf.xmp.impl.Base64;
import com.juyo.visa.admin.mobile.form.BasicinfoUSForm;
import com.juyo.visa.admin.mobile.form.FamilyinfoUSForm;
import com.juyo.visa.admin.mobile.form.PassportinfoUSForm;
import com.juyo.visa.admin.mobile.form.TravelinfoUSForm;
import com.juyo.visa.admin.mobile.form.WorkandeducateinfoUSForm;
import com.juyo.visa.admin.order.service.OrderJpViewService;
import com.juyo.visa.admin.user.service.UserViewService;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.MarryStatusEnum;
import com.juyo.visa.common.enums.USMarryStatusEnum;
import com.juyo.visa.common.enums.orderUS.DistrictEnum;
import com.juyo.visa.common.enums.orderUS.USOrderListStatusEnum;
import com.juyo.visa.common.enums.visaProcess.ImmediateFamilyMembersRelationshipEnum;
import com.juyo.visa.common.enums.visaProcess.VisaFamilyInfoEnum;
import com.juyo.visa.common.util.HttpUtil;
import com.juyo.visa.common.util.SpringContextUtil;
import com.juyo.visa.common.util.TranslateUtil;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TAppStaffBeforeeducationEntity;
import com.juyo.visa.entities.TAppStaffBeforeworkEntity;
import com.juyo.visa.entities.TAppStaffCompanioninfoEntity;
import com.juyo.visa.entities.TAppStaffCredentialsEntity;
import com.juyo.visa.entities.TAppStaffFamilyinfoEntity;
import com.juyo.visa.entities.TAppStaffGocountryEntity;
import com.juyo.visa.entities.TAppStaffGousinfoEntity;
import com.juyo.visa.entities.TAppStaffImmediaterelativesEntity;
import com.juyo.visa.entities.TAppStaffPassportEntity;
import com.juyo.visa.entities.TAppStaffPrevioustripinfoEntity;
import com.juyo.visa.entities.TAppStaffTravelcompanionEntity;
import com.juyo.visa.entities.TAppStaffWorkEducationTrainingEntity;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TCountryEntity;
import com.juyo.visa.entities.TUsStateEntity;
import com.juyo.visa.websocket.BasicInfoWSHandler;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.redis.RedisDao;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * 手机端接口service
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2018年8月21日	 
 */
@IocBean
public class MobileUSService extends BaseService<TApplicantEntity> {

	@Inject
	private UserViewService userViewService;
	@Inject
	private UploadService qiniuupService;
	@Inject
	private OrderJpViewService orderJpViewService;

	@Inject
	private UploadService qiniuUploadService;//文件上传

	@Inject
	private RedisDao redisDao;

	private BasicInfoWSHandler basicInfoWSHandler = (BasicInfoWSHandler) SpringContextUtil.getBean(
			"myBasicInfoHandler", BasicInfoWSHandler.class);

	//在职需要的资料
	private static Integer[] WORKINGDATA = { 1, 2, 3, 4, 5, 6, 7, 8 };
	//退休需要的资料
	private static Integer[] RETIREMENTDATA = { 1, 2, 3, 4, 5, 8, 15 };
	//自由职业所需资料
	private static Integer[] FREELANCEDATA = { 1, 2, 3, 4, 5, 8 };
	//学生所需资料
	private static Integer[] STUDENTDATA = { 1, 2, 3, 5, 13, 14, 16, 17 };
	//学龄前所需资料
	private static Integer[] PRESCHOOLAGEDATA = { 1, 2, 5, 12, 16, 17 };

	/**
	 * 小程序获取登录态
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param code
	 * @param staffid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getOpenidandSessionkey(String code, int staffid) {
		Map<String, Object> result1 = Maps.newHashMap();
		JSONObject result = getOpenid(code);
		String openid = result.getString("openid");
		String sessionkey = result.getString("session_key");
		System.out.println("openid:" + openid);
		System.out.println("sessionkey:" + sessionkey);
		String encode = Base64.encode(String.valueOf(staffid));
		System.out.println("encode:" + encode);
		redisDao.set(encode, openid + sessionkey);
		redisDao.expire(encode, 60 * 60 * 24 * 3);
		result1.put("encode", encode);
		TAppStaffBasicinfoEntity basic = dbDao.fetch(TAppStaffBasicinfoEntity.class, staffid);
		result1.put("telephone", basic.getTelephone());
		return result1;
	}

	/**
	 * 调用微信平台获取openid和session_key的接口
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param code 小程序返回的临时登录凭证
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public JSONObject getOpenid(String code) {
		String WX_APPID = "wx17bf0dde91fec324";
		String WX_APPSECRET = "6cc0aa2089c4ba020297fb23af31081a";

		String openidUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
		String requestUrl = openidUrl.replace("APPID", WX_APPID).replace("SECRET", WX_APPSECRET)
				.replace("JSCODE", code);
		JSONObject result = HttpUtil.doGet(requestUrl);
		return result;
	}

	/**
	 * 订单列表页
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param telephone
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object listData(String encode, String telephone) {
		//先验证是否登录过期
		/*String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			//如果为空则过期
			return -1;
		} else {*/
		String sqlStr = sqlManager.get("orderUS_mobile_listdata");
		Sql orderussql = Sqls.create(sqlStr);
		orderussql.setParam("telephone", telephone);
		List<Record> orderusList = dbDao.query(orderussql, null, null);
		for (Record record : orderusList) {
			//领区处理
			if (!Util.isEmpty(record.get("cityid"))) {
				int cityid = (int) record.get("cityid");
				for (DistrictEnum district : DistrictEnum.values()) {
					if (cityid == district.intKey()) {
						record.set("cityid", district.value());
					}
				}
			} else {
				record.set("cityid", "北京");
			}
			//订单状态处理
			if (!Util.isEmpty(record.get("orderstatus"))) {
				int orderStatus = (int) record.get("orderstatus");
				for (USOrderListStatusEnum statusEnum : USOrderListStatusEnum.values()) {
					if (!Util.isEmpty(orderStatus) && orderStatus == statusEnum.intKey()) {
						record.set("orderstatus", statusEnum.value());
					}
				}
			}
		}
		return orderusList;
		//}
	}

	/**
	 * 小程序查询是否已上传图片
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param encode
	 * @param staffid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object ishavePhoto(String encode, int staffid) {
		//先验证是否登录过期
		/*String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			//如果为空则过期
			return -1;
		} else {*/
		Map<String, Object> result = Maps.newHashMap();

		List<TAppStaffCredentialsEntity> list = dbDao.query(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		for (TAppStaffCredentialsEntity tAppStaffCredentialsEntity : list) {
			TAppStaffCredentialsEntity credentials = new TAppStaffCredentialsEntity();
			credentials.setStatus(tAppStaffCredentialsEntity.getStatus());
			credentials.setUrl(tAppStaffCredentialsEntity.getUrl());
			result.put(String.valueOf(tAppStaffCredentialsEntity.getType()), credentials);
		}
		return result;
		//}
	}

	/**
	 * 图片上传
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param encode
	 * @param staffid
	 * @param type
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateImage(String encode, File file, int staffid, int type) {
		/*String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {*/
		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(file);
		String url = CommonConstants.IMAGES_SERVER_ADDR + map.get("data");
		TAppStaffCredentialsEntity credentials = dbDao.fetch(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "=", type));

		//先查询是否有图片，有的话只需更新图片地址，没有的话新建
		if (Util.isEmpty(credentials)) {
			TAppStaffCredentialsEntity credentialsEntity = new TAppStaffCredentialsEntity();
			credentialsEntity.setCreatetime(new Date());
			credentialsEntity.setStaffid(staffid);
			credentialsEntity.setType(type);
			credentialsEntity.setUpdatetime(new Date());
			credentialsEntity.setUrl(url);
			dbDao.insert(credentialsEntity);
		} else {
			credentials.setUpdatetime(new Date());
			credentials.setUrl(url);
			dbDao.update(credentials);
		}
		return null;
		//}
	}

	/**
	 * 申请人基本信息回显
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param encode
	 * @param staffid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object basicinfo(String encode, int staffid) {

		/*String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {*/
		Map<String, Object> result = Maps.newHashMap();
		TAppStaffBasicinfoEntity basic = dbDao.fetch(TAppStaffBasicinfoEntity.class, staffid);
		SimpleDateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		//出生日期渲染
		if (!Util.isEmpty(basic.getBirthday())) {
			String birthday = format.format(basic.getBirthday());
			result.put("birthday", birthday);
		}
		//婚姻状况处理
		if (!Util.isEmpty(basic.getMarrystatus())) {
			Integer marrystatus = basic.getMarrystatus();
			for (MarryStatusEnum marry : MarryStatusEnum.values()) {
				if (marrystatus == marry.intKey()) {
					result.put("marrystatus", marry.value());
				}
			}
		}
		result.put("basic", basic);
		result.put("staffid", staffid);
		result.put("encode", encode);
		result.put("marrystatusenum", EnumUtil.enum2(USMarryStatusEnum.class));
		return result;
		//}

	}

	/**
	 * 基本信息保存
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveBasicinfo(BasicinfoUSForm form) {
		/*String encode = form.getEncode();
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {*/
		TAppStaffBasicinfoEntity basicinfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, form.getStaffid().longValue());
		basicinfo.setDetailedaddress(form.getDetailedaddress());
		basicinfo.setBirthday(form.getBirthday());
		basicinfo.setCardcity(form.getCardcity());
		basicinfo.setCardId(form.getCardId());
		basicinfo.setCardprovince(form.getCardprovince());
		basicinfo.setCity(form.getCity());
		basicinfo.setEmail(form.getEmail());
		basicinfo.setFirstname(form.getFirstname());
		basicinfo.setFirstnameen(form.getFirstnameen());
		basicinfo.setHasothername(form.getHasothername());
		basicinfo.setLastname(form.getLastname());
		basicinfo.setLastnameen(form.getLastnameen());
		basicinfo.setMarrystatus(form.getMarrystatus());
		basicinfo.setNationality(form.getNationality());
		basicinfo.setOtherfirstname(form.getOtherfirstname());
		basicinfo.setOtherfirstnameen(form.getOtherfirstnameen());
		basicinfo.setOtherlastname(form.getOtherlastname());
		basicinfo.setOtherlastnameen(form.getOtherlastnameen());
		basicinfo.setProvince(form.getProvince());
		basicinfo.setSex(form.getSex());
		basicinfo.setUpdatetime(new Date());
		dbDao.update(basicinfo);
		//}
		return null;
	}

	/**
	 * 护照信息回显
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param encode
	 * @param staffid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object passportinfo(String encode, int staffid) {
		/*String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {*/
		Map<String, Object> result = Maps.newHashMap();
		SimpleDateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		TAppStaffPassportEntity passportinfo = dbDao.fetch(TAppStaffPassportEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("passport", passportinfo);
		//日期相关处理
		if (!Util.isEmpty(passportinfo.getIssueddate())) {
			result.put("issueddate", format.format(passportinfo.getIssueddate()));
		}
		if (!Util.isEmpty(passportinfo.getValidenddate())) {
			result.put("validenddate", format.format(passportinfo.getValidenddate()));
		}
		return result;
		//}
	}

	/**
	 * 保存护照信息
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object savePassportInfo(PassportinfoUSForm form) {
		/*String encode = form.getEncode();
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {*/
		Integer staffid = form.getStaffid();
		TAppStaffPassportEntity passport = dbDao.fetch(TAppStaffPassportEntity.class, staffid.longValue());
		passport.setPassport(form.getPassport());
		passport.setIssuedplace(form.getIssuedplace());
		passport.setIssuedplaceen(form.getIssuedplaceen());
		passport.setIssueddate(form.getIssueddate());
		passport.setValidtype(form.getValidtype());
		passport.setValidenddate(form.getValidenddate());
		passport.setIssuedorganization(form.getIssuedorganization());
		passport.setIssuedorganizationen(form.getIssuedorganizationen());
		passport.setIslostpassport(form.getIslostpassport());
		passport.setIsrememberpassportnum(form.getIsrememberpassportnum());
		passport.setLostpassportnum(form.getLostpassportnum());
		dbDao.update(passport);
		//}
		return null;
	}

	/**
	 * 家庭信息回显
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param encode
	 * @param staffid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object familyinfo(String encode, int staffid) {
		/*String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {*/
		Map<String, Object> result = Maps.newHashMap();
		SimpleDateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		TAppStaffFamilyinfoEntity familyinfo = dbDao.fetch(TAppStaffFamilyinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("family", familyinfo);
		//日期格式处理
		if (!Util.isEmpty(familyinfo.getSpousebirthday())) {
			result.put("spousebirthday", format.format(familyinfo.getSpousebirthday()));
		}
		if (!Util.isEmpty(familyinfo.getFatherbirthday())) {
			result.put("fatherbirthday", format.format(familyinfo.getFatherbirthday()));
		}
		if (!Util.isEmpty(familyinfo.getMotherbirthday())) {
			result.put("motherbirthday", format.format(familyinfo.getMotherbirthday()));
		}
		TAppStaffImmediaterelativesEntity immediaterelatives = dbDao.fetch(TAppStaffImmediaterelativesEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("immediaterelatives", immediaterelatives);

		result.put("familyinfoenum", EnumUtil.enum2(VisaFamilyInfoEnum.class));
		result.put("immediaterelationshipenum", EnumUtil.enum2(ImmediateFamilyMembersRelationshipEnum.class));
		return result;
		//}
	}

	/**
	 * 家庭信息保存
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveFamilyinfo(FamilyinfoUSForm form) {
		/*String encode = form.getEncode();
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {*/
		Integer staffid = form.getStaffid();
		TAppStaffFamilyinfoEntity familyinfo = dbDao.fetch(TAppStaffFamilyinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		//配偶信息
		familyinfo.setSpousefirstname(form.getSpousefirstname());
		familyinfo.setSpousefirstnameen(form.getSpousefirstnameen());
		familyinfo.setSpouselastname(form.getSpouselastname());
		familyinfo.setSpouselastnameen(form.getSpouselastnameen());
		familyinfo.setSpousebirthday(form.getSpousebirthday());
		familyinfo.setSpousenationality(form.getSpousenationality());
		familyinfo.setSpousecountry(form.getSpousecountry());
		familyinfo.setSpousecity(form.getSpousecity());
		//父亲信息
		familyinfo.setFatherfirstname(form.getFatherfirstname());
		familyinfo.setFatherfirstnameen(form.getFatherfirstnameen());
		familyinfo.setFatherlastname(form.getFatherlastname());
		familyinfo.setFatherlastnameen(form.getFatherlastnameen());
		familyinfo.setFatherbirthday(form.getFatherbirthday());
		familyinfo.setIsfatherinus(form.getIsfatherinus());
		familyinfo.setFatherstatus(form.getFatherstatus());
		//母亲信息
		familyinfo.setMotherbirthday(form.getMotherbirthday());
		familyinfo.setMotherfirstname(form.getMotherfirstname());
		familyinfo.setMotherfirstnameen(form.getMotherfirstnameen());
		familyinfo.setMotherlastname(form.getMotherlastname());
		familyinfo.setMotherlastnameen(form.getMotherlastnameen());
		familyinfo.setMotherstatus(form.getMotherstatus());
		familyinfo.setIsmotherinus(form.getIsmotherinus());
		//其他亲属
		familyinfo.setHasimmediaterelatives(form.getHasimmediaterelatives());
		dbDao.update(familyinfo);
		TAppStaffImmediaterelativesEntity immediaterelatives = dbDao.fetch(TAppStaffImmediaterelativesEntity.class,
				Cnd.where("staffid", "=", staffid));
		immediaterelatives.setRelativesfirstname(form.getRelativesfirstname());
		immediaterelatives.setRelativesfirstnameen(form.getRelativesfirstnameen());
		immediaterelatives.setRelativeslastname(form.getRelativeslastname());
		immediaterelatives.setRelativeslastnameen(form.getRelativeslastnameen());
		immediaterelatives.setRelationship(form.getRelationship());
		immediaterelatives.setRelativesstatus(form.getRelativesstatus());
		dbDao.update(immediaterelatives);
		return null;
		//}
	}

	/**
	 * 职业与教育信息回显
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param encode
	 * @param staffid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object workandeducation(String encode, int staffid) {
		/*String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {*/
		Map<String, Object> result = Maps.newHashMap();
		SimpleDateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		//职业信息
		TAppStaffWorkEducationTrainingEntity workinfo = dbDao.fetch(TAppStaffWorkEducationTrainingEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("workinfo", workinfo);
		if (!Util.isEmpty(workinfo.getWorkstartdate())) {
			result.put("workstartdate", format.format(workinfo.getWorkstartdate()));
		}
		//上份工作信息
		TAppStaffBeforeworkEntity beforework = dbDao.fetch(TAppStaffBeforeworkEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("beforework", beforework);
		if (!Util.isEmpty(beforework.getEmploystartdate())) {
			result.put("employestartdate", format.format(beforework.getEmploystartdate()));
		}
		if (!Util.isEmpty(beforework.getEmployenddate())) {
			result.put("employenddate", format.format(beforework.getEmployenddate()));
		}
		//教育信息
		TAppStaffBeforeeducationEntity beforeeducate = dbDao.fetch(TAppStaffBeforeeducationEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("beforeeducate", beforeeducate);
		if (!Util.isEmpty(beforeeducate.getCoursestartdate())) {
			result.put("coursestartdate", format.format(beforeeducate.getCoursestartdate()));
		}
		if (!Util.isEmpty(beforeeducate.getCourseenddate())) {
			result.put("courseenddate", format.format(beforeeducate.getCourseenddate()));
		}
		return result;
		//}
	}

	/**
	 * 工作教育信息保存
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveWorkandeducation(WorkandeducateinfoUSForm form) {
		/*String encode = form.getEncode();
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {*/
		Integer staffid = form.getStaffid();
		//工作教育信息
		TAppStaffWorkEducationTrainingEntity workinfo = dbDao.fetch(TAppStaffWorkEducationTrainingEntity.class,
				Cnd.where("staffid", "=", staffid));
		workinfo.setOccupation(form.getOccupation());
		workinfo.setUnitname(form.getUnitname());
		workinfo.setTelephone(form.getTelephone());
		workinfo.setCountry(form.getCountry());
		workinfo.setProvince(form.getProvince());
		workinfo.setCity(form.getCity());
		workinfo.setAddress(form.getAddress());
		workinfo.setWorkstartdate(form.getWorkstartdate());
		workinfo.setPosition(form.getPosition());
		workinfo.setSalary(form.getSalary());
		workinfo.setIsemployed(form.getIsemployed());
		dbDao.update(workinfo);
		//上份工作信息
		TAppStaffBeforeworkEntity beforework = dbDao.fetch(TAppStaffBeforeworkEntity.class,
				Cnd.where("staffid", "=", staffid));
		beforework.setEmployername(form.getEmployername());
		beforework.setEmployernameen(form.getEmployernameen());
		beforework.setEmployertelephone(form.getEmployertelephone());
		beforework.setEmployercountry(form.getEmployercountry());
		beforework.setEmployerprovince(form.getEmployerprovince());
		beforework.setEmployercity(form.getEmployercity());
		beforework.setEmployeraddress(form.getEmployeraddress());
		beforework.setEmployeraddressen(form.getEmployeraddressen());
		beforework.setEmploystartdate(form.getEmploystartdate());
		beforework.setEmployenddate(form.getEmployenddate());
		beforework.setJobtitle(form.getJobtitle());
		dbDao.update(beforework);
		//教育信息
		TAppStaffBeforeeducationEntity beforeeducate = dbDao.fetch(TAppStaffBeforeeducationEntity.class,
				Cnd.where("staffid", "=", staffid));
		beforeeducate.setHighesteducation(form.getHighesteducation());
		beforeeducate.setInstitution(form.getInstitution());
		beforeeducate.setInstitutionen(form.getInstitutionen());
		beforeeducate.setCourse(form.getCourse());
		beforeeducate.setInstitutioncountry(form.getInstitutioncountry());
		beforeeducate.setInstitutionprovince(form.getInstitutionprovince());
		beforeeducate.setInstitutioncity(form.getInstitutioncity());
		beforeeducate.setInstitutionaddress(form.getInstitutionaddress());
		beforeeducate.setInstitutionaddressen(form.getInstitutionaddressen());
		beforeeducate.setCoursestartdate(form.getCoursestartdate());
		beforeeducate.setCourseenddate(form.getCourseenddate());
		dbDao.update(beforeeducate);
		return null;
		//}
	}

	/**
	 * 旅行信息回显
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param encode
	 * @param staffid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object travelinfo(String encode, int staffid) {
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			Map<String, Object> result = Maps.newHashMap();
			SimpleDateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
			//同行人信息
			TAppStaffTravelcompanionEntity travelcompanion = dbDao.fetch(TAppStaffTravelcompanionEntity.class,
					Cnd.where("staffid", "=", staffid));
			result.put("travelcompanion", travelcompanion.getIstravelwithother());
			List<TAppStaffCompanioninfoEntity> companioninfoList = dbDao.query(TAppStaffCompanioninfoEntity.class,
					Cnd.where("staffid", "=", staffid), null);
			result.put("companioninfoList", companioninfoList);
			//以前的美国旅游信息
			TAppStaffPrevioustripinfoEntity previoustripinfo = dbDao.fetch(TAppStaffPrevioustripinfoEntity.class,
					Cnd.where("staffid", "=", staffid));
			result.put("previoustripinfo", previoustripinfo);
			if (!Util.isEmpty(previoustripinfo.getIssueddate())) {
				result.put("issueddate", format.format(previoustripinfo.getIssueddate()));
			}
			//去过美国信息
			TAppStaffGousinfoEntity gousinfo = dbDao.fetch(TAppStaffGousinfoEntity.class,
					Cnd.where("staffid", "=", staffid));
			result.put("staydays", gousinfo.getStaydays());
			if (!Util.isEmpty(gousinfo.getArrivedate())) {
				result.put("arrivedate", format.format(gousinfo.getArrivedate()));
			}

			//是否有出境记录
			TAppStaffWorkEducationTrainingEntity workeducation = dbDao.fetch(
					TAppStaffWorkEducationTrainingEntity.class, Cnd.where("staffid", "=", staffid));
			result.put("isexitrecord", workeducation.getIstraveledanycountry());
			//出境记录
			List<TAppStaffGocountryEntity> gocountry = dbDao.query(TAppStaffGocountryEntity.class,
					Cnd.where("staffid", "=", staffid), null);
			result.put("gocountry", gocountry);
			return result;
		}
	}

	/**
	 * 保存旅行信息
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveTravelinfo(TravelinfoUSForm form) {
		/*String encode = form.getEncode();
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {*/
		Integer staffid = form.getStaffid();
		//同行人信息
		TAppStaffTravelcompanionEntity travelcompanion = dbDao.fetch(TAppStaffTravelcompanionEntity.class,
				Cnd.where("staffid", "=", staffid));
		travelcompanion.setIstravelwithother(form.getIstravelwithother());
		dbDao.update(travelcompanion);
		return null;
		//}
	}

	/**
	 * 国家模糊查询
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param searchstr
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getCountry(String searchstr) {
		List<String> countryList = new ArrayList<>();
		List<TCountryEntity> country = dbDao.query(TCountryEntity.class,
				Cnd.where("chinesename", "like", "%" + Strings.trim(searchstr) + "%"), null);
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

	/**
	 * 模糊查询美国所有州
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param searchstr
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getUSstate(String searchstr) {
		List<String> stateList = new ArrayList<>();
		List<TUsStateEntity> state = dbDao.query(TUsStateEntity.class,
				Cnd.where("statecn", "like", "%" + Strings.trim(searchstr) + "%"), null);
		for (TUsStateEntity tState : state) {
			if (!stateList.contains(tState.getStatecn())) {
				stateList.add(tState.getStatecn());
			}
		}
		List<String> list = new ArrayList<>();
		if (!Util.isEmpty(stateList) && stateList.size() >= 5) {
			for (int i = 0; i < 5; i++) {
				list.add(stateList.get(i));
			}
			return list;
		} else {
			return stateList;
		}
	}

	/**
	 * 中文翻译英文
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param str
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public String translate(String str) {
		String result = null;
		try {
			result = TranslateUtil.translate(str, "en");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

}
