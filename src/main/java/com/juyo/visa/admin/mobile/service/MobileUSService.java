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

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

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
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.MarryStatusEnum;
import com.juyo.visa.common.enums.orderUS.DistrictEnum;
import com.juyo.visa.common.enums.orderUS.USOrderListStatusEnum;
import com.juyo.visa.common.enums.visaProcess.ImmediateFamilyMembersRelationshipEnum;
import com.juyo.visa.common.enums.visaProcess.TravelCompanionRelationshipEnum;
import com.juyo.visa.common.enums.visaProcess.VisaCareersEnum;
import com.juyo.visa.common.enums.visaProcess.VisaFamilyInfoEnum;
import com.juyo.visa.common.enums.visaProcess.VisaHighestEducationEnum;
import com.juyo.visa.common.enums.visaProcess.VisaSpouseContactAddressEnum;
import com.juyo.visa.common.util.HttpUtil;
import com.juyo.visa.common.util.PinyinTool;
import com.juyo.visa.common.util.PinyinTool.Type;
import com.juyo.visa.common.util.TranslateUtil;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TAppStaffBeforeeducationEntity;
import com.juyo.visa.entities.TAppStaffBeforeworkEntity;
import com.juyo.visa.entities.TAppStaffCompanioninfoEntity;
import com.juyo.visa.entities.TAppStaffCredentialsEntity;
import com.juyo.visa.entities.TAppStaffDriverinfoEntity;
import com.juyo.visa.entities.TAppStaffFamilyinfoEntity;
import com.juyo.visa.entities.TAppStaffGocountryEntity;
import com.juyo.visa.entities.TAppStaffGousinfoEntity;
import com.juyo.visa.entities.TAppStaffImmediaterelativesEntity;
import com.juyo.visa.entities.TAppStaffPassportEntity;
import com.juyo.visa.entities.TAppStaffPrevioustripinfoEntity;
import com.juyo.visa.entities.TAppStaffTravelcompanionEntity;
import com.juyo.visa.entities.TAppStaffWorkEducationTrainingEntity;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TCountryRegionEntity;
import com.juyo.visa.entities.TStateUsEntity;
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
	private UploadService qiniuUploadService;//文件上传

	@Inject
	private RedisDao redisDao;

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
		result.put("credentials", list);
		return result;
		//}
	}

	public Object simpleishavephoto(String encode, int staffid) {
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
			credentials.setType(tAppStaffCredentialsEntity.getType());
			credentials.setUrl(tAppStaffCredentialsEntity.getUrl());
			result.put(String.valueOf(credentials.getType()), credentials);
		}
		result.put("credentials", list);
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
	public Object updateImage(String encode, File file, int staffid, int type, int sequence) {
		/*String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {*/
		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(file);
		String url = CommonConstants.IMAGES_SERVER_ADDR + map.get("data");

		TAppStaffCredentialsEntity credentials = new TAppStaffCredentialsEntity();
		if (Util.isEmpty(sequence) || sequence == 0) {
			credentials = dbDao.fetch(TAppStaffCredentialsEntity.class,
					Cnd.where("staffid", "=", staffid).and("type", "=", type));
		} else {
			credentials = dbDao.fetch(TAppStaffCredentialsEntity.class,
					Cnd.where("staffid", "=", staffid).and("type", "=", type).and("sequence", "=", sequence));
		}

		//先查询是否有图片，有的话只需更新图片地址，没有的话新建
		if (Util.isEmpty(credentials)) {
			TAppStaffCredentialsEntity credentialsEntity = new TAppStaffCredentialsEntity();
			credentialsEntity.setCreatetime(new Date());
			credentialsEntity.setStaffid(staffid);
			credentialsEntity.setType(type);
			credentialsEntity.setSequence(sequence);
			credentialsEntity.setUpdatetime(new Date());
			credentialsEntity.setUrl(url);
			dbDao.insert(credentialsEntity);
		} else {
			credentials.setUpdatetime(new Date());
			credentials.setUrl(url);
			dbDao.update(credentials);
		}
		return url;
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
		result.put("marrystatusenum", EnumUtil.enum2(MarryStatusEnum.class));
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
		//基本信息
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
		//英文翻译保存
		basicinfo.setDetailedaddressen(translate(form.getDetailedaddress()));
		basicinfo.setCardprovinceen(translate(form.getCardprovince()));
		basicinfo.setCityen(translate(form.getCity()));
		basicinfo.setNationalityen(translate(form.getNationality()));
		basicinfo.setProvinceen(translate(form.getProvince()));
		basicinfo.setMarrystatusen(form.getMarrystatus());
		basicinfo.setTelephoneen(form.getTelephone());
		basicinfo.setEmailen(form.getEmail());
		basicinfo.setCardIden(form.getCardId());
		dbDao.update(basicinfo);

		//护照信息
		TAppStaffPassportEntity passportinfo = dbDao.fetch(TAppStaffPassportEntity.class,
				Cnd.where("staffid", "=", form.getStaffid().longValue()));
		passportinfo.setFirstname(form.getFirstname());
		passportinfo.setFirstnameen(form.getFirstnameen());
		passportinfo.setLastname(form.getLastname());
		passportinfo.setLastnameen(form.getLastnameen());
		passportinfo.setSex(form.getSex());
		if (Util.eq("女", form.getSex())) {
			passportinfo.setSexen("F");
		} else {
			passportinfo.setSexen("M");
		}
		passportinfo.setBirthday(form.getBirthday());
		dbDao.update(passportinfo);
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

		//英文
		//中文翻译成拼音并大写工具
		PinyinTool tool = new PinyinTool();
		try {
			passport.setIssuedplaceen("/" + tool.toPinYin(form.getIssuedplace(), "", Type.UPPERCASE));
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		passport.setIslostpassporten(form.getIslostpassport());
		passport.setIsrememberpassportnumen(form.getIsrememberpassportnum());
		passport.setLostpassportnumen(form.getLostpassportnum());
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

		result.put("spousecontactaddressenum", EnumUtil.enum2(VisaSpouseContactAddressEnum.class));
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
		//英文
		familyinfo.setSpousebirthdayen(form.getSpousebirthday());
		//配偶国籍和出生国家是select还是input???
		//familyinfo.setSpousenationalityen(translate(form.getSpousenationality()));
		//familyinfo.setSpousecountryen(translate(form.getSpousecountry()));
		familyinfo.setSpousecityen(translate(form.getSpousecity()));
		familyinfo.setSpouseaddressen(form.getSpouseaddress());

		//父亲信息
		familyinfo.setFatherfirstname(form.getFatherfirstname());
		familyinfo.setFatherfirstnameen(form.getFatherfirstnameen());
		familyinfo.setFatherlastname(form.getFatherlastname());
		familyinfo.setFatherlastnameen(form.getFatherlastnameen());
		familyinfo.setFatherbirthday(form.getFatherbirthday());
		familyinfo.setIsfatherinus(form.getIsfatherinus());
		familyinfo.setFatherstatus(form.getFatherstatus());
		//英文
		familyinfo.setFatherbirthdayen(form.getFatherbirthday());
		familyinfo.setFatherstatusen(form.getFatherstatus());
		familyinfo.setIsfatherinusen(form.getIsfatherinus());

		//母亲信息
		familyinfo.setMotherbirthday(form.getMotherbirthday());
		familyinfo.setMotherfirstname(form.getMotherfirstname());
		familyinfo.setMotherfirstnameen(form.getMotherfirstnameen());
		familyinfo.setMotherlastname(form.getMotherlastname());
		familyinfo.setMotherlastnameen(form.getMotherlastnameen());
		familyinfo.setMotherstatus(form.getMotherstatus());
		familyinfo.setIsmotherinus(form.getIsmotherinus());
		//英文
		familyinfo.setMotherbirthdayen(form.getMotherbirthday());
		familyinfo.setMotherstatusen(form.getMotherstatus());
		familyinfo.setIsmotherinusen(form.getIsmotherinus());

		//其他亲属
		familyinfo.setHasimmediaterelatives(form.getHasimmediaterelatives());
		familyinfo.setHasimmediaterelativesen(form.getHasimmediaterelatives());
		dbDao.update(familyinfo);
		TAppStaffImmediaterelativesEntity immediaterelatives = dbDao.fetch(TAppStaffImmediaterelativesEntity.class,
				Cnd.where("staffid", "=", staffid));
		immediaterelatives.setRelativesfirstname(form.getRelativesfirstname());
		immediaterelatives.setRelativesfirstnameen(form.getRelativesfirstnameen());
		immediaterelatives.setRelativeslastname(form.getRelativeslastname());
		immediaterelatives.setRelativeslastnameen(form.getRelativeslastnameen());
		immediaterelatives.setRelationship(form.getRelationship());
		immediaterelatives.setRelativesstatus(form.getRelativesstatus());
		//英文
		immediaterelatives.setRelationshipen(form.getRelationship());
		immediaterelatives.setRelativesstatusen(form.getRelativesstatus());
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

		result.put("careersenum", EnumUtil.enum2(VisaCareersEnum.class));
		result.put("highesteducationenum", EnumUtil.enum2(VisaHighestEducationEnum.class));
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
		workinfo.setUnitnameen(form.getUnitnameen());
		workinfo.setTelephone(form.getTelephone());
		workinfo.setCountry(form.getCountry());
		workinfo.setProvince(form.getProvince());
		workinfo.setCity(form.getCity());
		workinfo.setAddress(form.getAddress());
		workinfo.setAddressen(form.getAddressen());
		workinfo.setWorkstartdate(form.getWorkstartdate());
		workinfo.setPosition(form.getPosition());
		workinfo.setSalary(form.getSalary());
		workinfo.setDuty(form.getDuty());
		workinfo.setIsemployed(form.getIsemployed());
		workinfo.setIssecondarylevel(form.getIssecondarylevel());
		//英文翻译保存
		workinfo.setOccupationen(form.getOccupation());
		workinfo.setTelephoneen(form.getTelephone());
		workinfo.setProvinceen(translate(form.getProvince()));
		workinfo.setCityen(translate(form.getCity()));
		workinfo.setWorkstartdateen(form.getWorkstartdate());
		workinfo.setPositionen(translate(form.getPosition()));
		workinfo.setSalaryen(form.getSalary());
		workinfo.setDutyen(translate(form.getDuty()));
		workinfo.setIsemployeden(form.getIsemployed());
		workinfo.setIssecondarylevelen(form.getIssecondarylevel());
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
		beforework.setPreviousduty(form.getPreviousduty());
		//英文
		beforework.setEmployertelephoneen(form.getEmployertelephone());
		beforework.setEmployercountryen(form.getEmployercountry());
		beforework.setEmployerprovinceen(translate(form.getEmployerprovince()));
		beforework.setEmployercityen(translate(form.getEmployercity()));
		beforework.setEmploystartdateen(form.getEmploystartdate());
		beforework.setEmployenddateen(form.getEmployenddate());
		beforework.setJobtitleen(translate(form.getJobtitle()));
		beforework.setPreviousdutyen(translate(form.getPreviousduty()));
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
		//英文
		beforeeducate.setHighesteducationen(form.getHighesteducation());
		beforeeducate.setCourseen(translate(form.getCourse()));
		beforeeducate.setInstitutioncountryen(form.getInstitutioncountry());
		beforeeducate.setInstitutionprovinceen(translate(form.getInstitutionprovince()));
		beforeeducate.setInstitutioncityen(translate(form.getInstitutioncity()));
		beforeeducate.setCoursestartdateen(form.getCoursestartdate());
		beforeeducate.setCourseenddateen(form.getCourseenddate());
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
		/*String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {*/
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

		//美国的驾照信息
		TAppStaffDriverinfoEntity driverinfo = dbDao.fetch(TAppStaffDriverinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("driverinfo", driverinfo);

		//是否有出境记录
		TAppStaffWorkEducationTrainingEntity workeducation = dbDao.fetch(TAppStaffWorkEducationTrainingEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("istraveledanycountry", workeducation.getIstraveledanycountry());
		//出境记录
		List<TAppStaffGocountryEntity> gocountry = dbDao.query(TAppStaffGocountryEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		result.put("gocountry", gocountry);

		result.put("travelcompanionrelationshipenum", EnumUtil.enum2(TravelCompanionRelationshipEnum.class));
		return result;
		//}
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
		//是否有同行人
		TAppStaffTravelcompanionEntity travelcompanion = dbDao.fetch(TAppStaffTravelcompanionEntity.class,
				Cnd.where("staffid", "=", staffid));
		travelcompanion.setIstravelwithother(form.getIstravelwithother());
		travelcompanion.setIstravelwithotheren(form.getIstravelwithother());
		dbDao.update(travelcompanion);

		//同行人信息
		List<TAppStaffCompanioninfoEntity> companioninfo_old = dbDao.query(TAppStaffCompanioninfoEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		List<TAppStaffCompanioninfoEntity> companioninfo_new = form.getCompanioninfoList();
		dbDao.updateRelations(companioninfo_old, companioninfo_new);

		//以前的美国旅游信息
		TAppStaffPrevioustripinfoEntity previoustripinfo = dbDao.fetch(TAppStaffPrevioustripinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		previoustripinfo.setCostpayer(form.getCostpayer());
		previoustripinfo.setHasbeeninus(form.getHasbeeninus());
		previoustripinfo.setHasdriverlicense(form.getHasdriverlicense());
		previoustripinfo.setIsissuedvisa(form.getIsissuedvisa());
		previoustripinfo.setIssueddate(form.getIssueddate());
		previoustripinfo.setVisanumber(form.getVisanumber());
		previoustripinfo.setIsapplyingsametypevisa(form.getIsapplyingsametypevisa());
		previoustripinfo.setIstenprinted(form.getIstenprinted());
		previoustripinfo.setIslost(form.getIslost());
		previoustripinfo.setIscancelled(form.getIscancelled());
		previoustripinfo.setIsrefused(form.getIsrefused());
		previoustripinfo.setRefusedexplain(form.getRefusedexplain());
		previoustripinfo.setIsfiledimmigrantpetition(form.getIsfiledimmigrantpetition());
		previoustripinfo.setEmigrationreason(form.getEmigrationreason());
		previoustripinfo.setImmigrantpetitionexplain(form.getImmigrantpetitionexplain());
		//英文
		previoustripinfo.setCostpayeren(form.getCostpayer());
		previoustripinfo.setHasbeeninusen(form.getHasbeeninus());
		previoustripinfo.setHasdriverlicenseen(form.getHasdriverlicense());
		previoustripinfo.setIsissuedvisaen(form.getIsissuedvisa());
		previoustripinfo.setIssueddateen(form.getIssueddate());
		previoustripinfo.setVisanumberen(translate(form.getVisanumber()));
		previoustripinfo.setIsapplyingsametypevisaen(form.getIsapplyingsametypevisa());
		previoustripinfo.setIstenprinteden(form.getIstenprinted());
		previoustripinfo.setIslosten(form.getIslost());
		previoustripinfo.setIscancelleden(form.getIscancelled());
		previoustripinfo.setIsrefuseden(form.getIsrefused());
		previoustripinfo.setRefusedexplainen(translate(form.getRefusedexplain()));
		previoustripinfo.setIsfiledimmigrantpetitionen(form.getIsfiledimmigrantpetition());
		previoustripinfo.setEmigrationreasonen(form.getEmigrationreason());
		previoustripinfo.setImmigrantpetitionexplainen(translate(form.getImmigrantpetitionexplain()));
		dbDao.update(previoustripinfo);

		//去过美国信息
		List<TAppStaffGousinfoEntity> gousinfo_old = dbDao.query(TAppStaffGousinfoEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		List<TAppStaffGousinfoEntity> gousinfo_new = form.getGousinfoList();
		dbDao.updateRelations(gousinfo_old, gousinfo_new);

		/*TAppStaffGousinfoEntity gousinfo = dbDao.fetch(TAppStaffGousinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		gousinfo.setStaydays(form.getStaydays());
		gousinfo.setArrivedate(form.getArrivedate());
		dbDao.update(gousinfo);*/

		//美国驾照信息
		TAppStaffDriverinfoEntity driverinfo = dbDao.fetch(TAppStaffDriverinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		driverinfo.setDriverlicensenumber(form.getDriverlicensenumber());
		driverinfo.setWitchstateofdriver(form.getWitchstateofdriver());
		driverinfo.setDriverlicensenumberen(form.getDriverlicensenumber());
		driverinfo.setWitchstateofdriveren(form.getWitchstateofdriver());
		dbDao.update(driverinfo);

		//是否有出境记录
		TAppStaffWorkEducationTrainingEntity workeducation = dbDao.fetch(TAppStaffWorkEducationTrainingEntity.class,
				Cnd.where("staffid", "=", staffid));
		workeducation.setIstraveledanycountry(form.getIstraveledanycountry());

		//出境记录
		List<TAppStaffGocountryEntity> gocountry_old = dbDao.query(TAppStaffGocountryEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		List<TAppStaffGocountryEntity> gocountry_new = form.getGocountryList();
		dbDao.updateRelations(gocountry_old, gocountry_new);
		return null;
		//}
	}

	/**
	 * 婚姻状况修改
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param encode
	 * @param staffid
	 * @param marrystatus
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object changeMarrystatus(String encode, int staffid, int marrystatus) {
		/*String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {*/
		TAppStaffBasicinfoEntity basicinfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, staffid);
		basicinfo.setMarrystatus(marrystatus);
		basicinfo.setMarrystatusen(marrystatus);
		dbDao.update(basicinfo);
		return null;
		//}
	}

	public Object toMarrystatus(String encode, int staffid) {
		/*String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {*/
		Map<String, Object> result = Maps.newHashMap();
		TAppStaffBasicinfoEntity basicinfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, staffid);
		result.put("marrystatus", basicinfo.getMarrystatus());
		return result;
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
	public Object getCountry(String encode, String searchstr) {
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			List<String> countryList = new ArrayList<>();
			List<TCountryRegionEntity> country = dbDao.query(TCountryRegionEntity.class,
					Cnd.where("chinesename", "like", "%" + Strings.trim(searchstr) + "%"), null);
			for (TCountryRegionEntity tCountry : country) {
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
	public Object getUSstate(String encode, String searchstr) {
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			List<String> stateList = new ArrayList<>();
			List<TStateUsEntity> state = dbDao.query(TStateUsEntity.class,
					Cnd.where("name", "like", "%" + Strings.trim(searchstr) + "%"), null);
			for (TStateUsEntity tState : state) {
				if (!stateList.contains(tState.getName())) {
					stateList.add(tState.getName());
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
