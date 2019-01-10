/**
 * MobileService.java
 * com.juyo.visa.admin.mobile.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.mobile.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.json.JSONArray;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.springframework.web.socket.TextMessage;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.itextpdf.xmp.impl.Base64;
import com.juyo.visa.admin.mobile.form.BasicinfoUSDateForm;
import com.juyo.visa.admin.mobile.form.BasicinfoUSForm;
import com.juyo.visa.admin.mobile.form.FamilyinfoUSDateForm;
import com.juyo.visa.admin.mobile.form.FamilyinfoUSForm;
import com.juyo.visa.admin.mobile.form.PassportinfoUSDateForm;
import com.juyo.visa.admin.mobile.form.PassportinfoUSForm;
import com.juyo.visa.admin.mobile.form.TravelinfoUSForm;
import com.juyo.visa.admin.mobile.form.WorkandeducateinfoUSDateForm;
import com.juyo.visa.admin.mobile.form.WorkandeducateinfoUSForm;
import com.juyo.visa.admin.order.form.RecognitionForm;
import com.juyo.visa.common.baidu.BaidutranslateEntity;
import com.juyo.visa.common.baidu.TransApi;
import com.juyo.visa.common.base.JuYouResult;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.MarryStatusEnum;
import com.juyo.visa.common.enums.orderUS.DistrictEnum;
import com.juyo.visa.common.enums.orderUS.USOrderListStatusEnum;
import com.juyo.visa.common.enums.visaProcess.EmigrationreasonEnum;
import com.juyo.visa.common.enums.visaProcess.ImmediateFamilyMembersRelationshipEnum;
import com.juyo.visa.common.enums.visaProcess.NewTimeUnitStatusEnum;
import com.juyo.visa.common.enums.visaProcess.TravelCompanionRelationshipEnum;
import com.juyo.visa.common.enums.visaProcess.VisaCareersEnum;
import com.juyo.visa.common.enums.visaProcess.VisaFamilyInfoEnum;
import com.juyo.visa.common.enums.visaProcess.VisaHighestEducationEnum;
import com.juyo.visa.common.enums.visaProcess.VisaSpouseContactAddressEnum;
import com.juyo.visa.common.enums.visaProcess.VisaUSStatesEnum;
import com.juyo.visa.common.util.HttpUtil;
import com.juyo.visa.common.util.MapUtil;
import com.juyo.visa.common.util.PinyinTool;
import com.juyo.visa.common.util.PinyinTool.Type;
import com.juyo.visa.common.util.SpringContextUtil;
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
import com.juyo.visa.websocket.SimpleSendInfoWSHandler;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.JsonUtil;
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

	private SimpleSendInfoWSHandler simplesendinfosocket = (SimpleSendInfoWSHandler) SpringContextUtil.getBean(
			"mySimpleSendInfoWSHandler", SimpleSendInfoWSHandler.class);

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
		System.out.println("telephone=======:" + telephone);
		//先验证是否登录过期
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			//如果为空则过期
			return -1;
		} else {
			System.out.println("openid:" + openid);
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
		}
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
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			//如果为空则过期
			return -1;
		} else {
			Map<String, Object> result = Maps.newHashMap();

			List<TAppStaffCredentialsEntity> list = dbDao.query(TAppStaffCredentialsEntity.class,
					Cnd.where("staffid", "=", staffid).orderBy("sequence", "ASC"), null);
			result.put("credentials", list);
			return result;
		}
	}

	public Object simpleishavephoto(String encode, int staffid) {
		//先验证是否登录过期
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			//如果为空则过期
			return -1;
		} else {
			Map<String, Object> result = Maps.newHashMap();

			List<TAppStaffCredentialsEntity> list = dbDao.query(TAppStaffCredentialsEntity.class,
					Cnd.where("staffid", "=", staffid).orderBy("sequence", "ASC"), null);
			for (TAppStaffCredentialsEntity tAppStaffCredentialsEntity : list) {
				TAppStaffCredentialsEntity credentials = new TAppStaffCredentialsEntity();
				credentials.setType(tAppStaffCredentialsEntity.getType());
				credentials.setUrl(tAppStaffCredentialsEntity.getUrl());
				result.put(String.valueOf(credentials.getType()), credentials);
			}
			result.put("credentials", list);
			return result;
		}
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
		String openid = redisDao.get(encode);
		System.out.println("上传图片openid:" + openid);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			System.out.println("开始上传图片---");
			Map<String, Object> map = qiniuUploadService.ajaxUploadImage(file);
			String url = CommonConstants.IMAGES_SERVER_ADDR + map.get("data");

			System.out.println("sequence:" + sequence);
			System.out.println("type:" + type);
			System.out.println("staffid:" + staffid);
			//分多张图片和单张图片,sequence=0时为单张图片，分添加和修改。sequence不为0时为多张图片，只有添加，没有修改
			if (Util.isEmpty(sequence) || sequence == 0) {//单张图片,分修改和添加
				TAppStaffCredentialsEntity credentials = dbDao.fetch(TAppStaffCredentialsEntity.class,
						Cnd.where("staffid", "=", staffid).and("type", "=", type));
				//先查询是否有图片，有的话只需更新图片，没有的话添加
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
			} else {//多张图片，只有添加
				TAppStaffCredentialsEntity credentialsEntity = new TAppStaffCredentialsEntity();
				credentialsEntity.setCreatetime(new Date());
				credentialsEntity.setStaffid(staffid);
				credentialsEntity.setType(type);
				credentialsEntity.setUpdatetime(new Date());
				credentialsEntity.setUrl(url);
				credentialsEntity.setSequence(sequence);
				dbDao.insert(credentialsEntity);
			}

			//消息通知
			try {
				RecognitionForm form = new RecognitionForm();
				form.setApplyid(staffid);
				simplesendinfosocket.broadcast(new TextMessage(JsonUtil.toJson(form)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return url;
		}
	}

	/**
	 * 删除图片
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param encode
	 * @param staffid
	 * @param type
	 * @param sequence
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object deleteImage(String encode, int staffid, int type, int sequence) {
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			TAppStaffCredentialsEntity credentials = new TAppStaffCredentialsEntity();
			if (Util.isEmpty(sequence) || sequence == 0) {
				credentials = dbDao.fetch(TAppStaffCredentialsEntity.class,
						Cnd.where("staffid", "=", staffid).and("type", "=", type));
			} else {
				credentials = dbDao.fetch(TAppStaffCredentialsEntity.class,
						Cnd.where("staffid", "=", staffid).and("type", "=", type).and("sequence", "=", sequence));
			}
			if (!Util.isEmpty(credentials)) {
				dbDao.delete(credentials);
			}
			return JuYouResult.ok();
		}
	}

	/**
	 * 省市联动下拉
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param encode
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getProvinceAndCity(String encode) {
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			//NutMap resultMap = new NutMap();
			Map<String, List<Record>> resultMap = Maps.newLinkedHashMap();
			String sqlStr = sqlManager.get("orderUS_mobile_getProvince");
			Sql provincesql = Sqls.create(sqlStr);
			List<Record> provinceList = dbDao.query(provincesql, null, null);
			for (Record provinceString : provinceList) {
				String provinceStr = provinceString.getString("province");
				String sqlStr2 = sqlManager.get("orderUS_mobile_getCity");
				Sql orderussql = Sqls.create(sqlStr2);
				orderussql.setParam("province", provinceStr);
				List<Record> cityList = dbDao.query(orderussql, null, null);
				resultMap.put(provinceStr, cityList);
			}
			return resultMap;
		}
	}

	/**
	 * 根据省份，城市下拉
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param encode
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getCitys(String encode, String province) {
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			String sqlStr = sqlManager.get("orderUS_mobile_getCity");
			Sql orderussql = Sqls.create(sqlStr);
			orderussql.setParam("province", province);
			List<Record> cityList = dbDao.query(orderussql, null, null);
			return cityList;
		}
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

		long start = System.currentTimeMillis();

		System.out.println("基本信息回显的staffid:" + staffid);

		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			Map<String, Object> result = Maps.newHashMap();

			String sqlStr = sqlManager.get("orderusmobile_getbasic");
			Sql basicsql = Sqls.create(sqlStr);
			basicsql.setParam("staffid", staffid);
			Record basic = dbDao.fetch(basicsql);
			System.out.println("basic:" + basic);

			//性别处理
			String sex = basic.getString("sex");
			if (Util.isEmpty(sex)) {
				basic.put("sex", "男");
			}

			if (Util.isEmpty(basic.get("marrystatus"))) {
				basic.put("marrystatus", "");
			}

			if (Util.isEmpty(basic.get("hasothername"))) {
				basic.put("hasothername", 2);
			}

			if (Util.isEmpty(basic.get("address"))) {
				basic.put("address", "");
			}
			if (Util.isEmpty(basic.get("addressen"))) {
				basic.put("addressen", "");
			}
			if (Util.isEmpty(basic.get("cardid"))) {
				basic.put("cardid", "");
			}
			if (Util.isEmpty(basic.get("province"))) {
				basic.put("province", "");
			}
			if (Util.isEmpty(basic.get("city"))) {
				basic.put("city", "");
			}
			if (Util.isEmpty(basic.get("detailedaddress"))) {
				basic.put("detailedaddress", "");
			}
			if (Util.isEmpty(basic.get("detailedaddressen"))) {
				basic.put("detailedaddressen", "");
			}
			if (Util.isEmpty(basic.get("cardprovince"))) {
				basic.put("cardprovince", "");
			}
			if (Util.isEmpty(basic.get("cardcity"))) {
				basic.put("cardcity", "");
			}
			if (Util.isEmpty(basic.get("otherfirstname"))) {
				basic.put("otherfirstname", "");
			}
			if (Util.isEmpty(basic.get("otherfirstnameen"))) {
				basic.put("otherfirstnameen", "");
			}
			if (Util.isEmpty(basic.get("otherlastname"))) {
				basic.put("otherlastname", "");
			}
			if (Util.isEmpty(basic.get("otherlastnameen"))) {
				basic.put("otherlastnameen", "");
			}
			if (Util.isEmpty(basic.get("mailaddress"))) {
				basic.put("mailaddress", "");
			}
			if (Util.isEmpty(basic.get("mailaddressen"))) {
				basic.put("mailaddressen", "");
			}
			if (Util.isEmpty(basic.get("mailcity"))) {
				basic.put("mailcity", "");
			}
			if (Util.isEmpty(basic.get("mailprovince"))) {
				basic.put("mailprovince", "");
			}
			if (Util.isEmpty(basic.get("mailcountry"))) {
				basic.put("mailcountry", "");
			}

			SimpleDateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
			//日期渲染，如果为空，给空字符串
			if (!Util.isEmpty(basic.get("birthday"))) {
				String birthday = format.format(basic.get("birthday"));
				basic.put("birthday", birthday);
			} else {
				basic.put("birthday", "");
			}
			if (!Util.isEmpty(basic.get("marrieddate"))) {
				basic.put("marrieddate", format.format(basic.get("marrieddate")));
			} else {
				basic.put("marrieddate", "");
			}
			if (!Util.isEmpty(basic.get("divorcedate"))) {
				basic.put("divorcedate", format.format(basic.get("divorcedate")));
			} else {
				basic.put("divorcedate", "");
			}

			/*TAppStaffBasicinfoEntity basicinfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, staffid);
			TAppStaffFamilyinfoEntity familyinfo = dbDao.fetch(TAppStaffFamilyinfoEntity.class,
					Cnd.where("staffid", "=", staffid));
			BasicinfoUSDateForm resultbasicinfo = new BasicinfoUSDateForm();

			BasicinfoUSDateForm basic = removeDataToNewEntity(basicinfo, resultbasicinfo, familyinfo);*/

			//婚姻状况处理
			/*if (!Util.isEmpty(basic.getMarrystatus())) {
				Integer marrystatus = basic.getMarrystatus();
				for (MarryStatusEnum marry : MarryStatusEnum.values()) {
					if (marrystatus == marry.intKey()) {
						result.put("marrystatus", marry.value());
					}
				}
			}*/
			//基本信息
			result.put("basic", basic);
			//人员id
			result.put("staffid", staffid);
			//登录标记encode
			result.put("encode", encode);

			//国家下拉
			List<TCountryRegionEntity> gocountryFiveList = dbDao.query(TCountryRegionEntity.class, null, null);
			result.put("gocountryfivelist", gocountryFiveList);

			//婚姻状况枚举
			result.put("marrystatusenum", EnumUtil.enum2(MarryStatusEnum.class));
			System.out.println("基本信息回显result:" + result);
			long last = System.currentTimeMillis();
			System.out.println("基本信息回显所用时间:" + (last - start) + "ms");

			return JuYouResult.ok(result);
		}

	}

	public BasicinfoUSDateForm removeDataToNewEntity(TAppStaffBasicinfoEntity basicinfo,
			BasicinfoUSDateForm resultbasicinfo, TAppStaffFamilyinfoEntity familyinfo) {
		SimpleDateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);

		resultbasicinfo.setAacode(basicinfo.getAacode());
		resultbasicinfo.setAddress(basicinfo.getAddress());
		resultbasicinfo.setAddressen(basicinfo.getAddressen());
		resultbasicinfo.setAddressIssamewithcard(basicinfo.getAddressIssamewithcard());
		resultbasicinfo.setAddressIssamewithcarden(basicinfo.getAddressIssamewithcarden());
		resultbasicinfo.setBirthcountry(basicinfo.getBirthcountry());
		resultbasicinfo.setBirthcountryen(getCountrycode(basicinfo.getBirthcountry()));

		if (!Util.isEmpty(basicinfo.getBirthday())) {
			resultbasicinfo.setBirthday(format.format(basicinfo.getBirthday()));
		} else {
			resultbasicinfo.setBirthday("");
		}

		resultbasicinfo.setCardback(basicinfo.getCardback());
		resultbasicinfo.setCardcity(basicinfo.getCardcity());
		resultbasicinfo.setCardcityen(basicinfo.getCardcityen());
		resultbasicinfo.setCardfront(basicinfo.getCardfront());
		resultbasicinfo.setCardId(basicinfo.getCardId());
		resultbasicinfo.setCardnum(basicinfo.getCardnum());
		resultbasicinfo.setCardprovince(basicinfo.getCardprovince());
		resultbasicinfo.setCity(basicinfo.getCity());
		resultbasicinfo.setDepartment(basicinfo.getDepartment());
		resultbasicinfo.setDetailedaddress(basicinfo.getDetailedaddress());
		resultbasicinfo.setDetailedaddressen(basicinfo.getDetailedaddressen());

		if (!Util.isEmpty(familyinfo.getDivorcedate())) {
			resultbasicinfo.setDivorcedate(format.format(familyinfo.getDivorcedate()));
		} else {
			resultbasicinfo.setDivorcedate("");
		}

		resultbasicinfo.setDivorcecountry(familyinfo.getDivorcecountry());
		resultbasicinfo.setDivorcecountryen(familyinfo.getDivorcecountryen());
		resultbasicinfo.setEmail(basicinfo.getEmail());
		resultbasicinfo.setEmailen(basicinfo.getEmailen());
		resultbasicinfo.setEmergencylinkman(basicinfo.getEmergencylinkman());
		resultbasicinfo.setEmergencytelephone(basicinfo.getEmergencytelephone());
		resultbasicinfo.setFirstname(basicinfo.getFirstname());
		resultbasicinfo.setFirstnameen(basicinfo.getFirstnameen());
		resultbasicinfo.setHasothername(basicinfo.getHasothername());
		resultbasicinfo.setHasothernationality(basicinfo.getHasothernationality());
		resultbasicinfo.setHasotherpassport(basicinfo.getHasotherpassport());
		resultbasicinfo.setJob(basicinfo.getJob());
		resultbasicinfo.setLastname(basicinfo.getLastname());
		resultbasicinfo.setLastnameen(basicinfo.getLastnameen());
		resultbasicinfo.setMailaddress(basicinfo.getMailaddress());
		resultbasicinfo.setMailaddressen(basicinfo.getMailaddressen());
		resultbasicinfo.setMailcity(basicinfo.getMailcity());
		resultbasicinfo.setMailcityen(basicinfo.getMailcityen());
		resultbasicinfo.setMailcountry(basicinfo.getMailcountry());
		resultbasicinfo.setMailcountryen(basicinfo.getMailcountryen());
		resultbasicinfo.setMailprovince(basicinfo.getMailprovince());
		resultbasicinfo.setMailprovinceen(basicinfo.getMailprovinceen());

		if (!Util.isEmpty(familyinfo.getMarrieddate())) {
			resultbasicinfo.setMarrieddate(format.format(familyinfo.getMarrieddate()));
		} else {
			resultbasicinfo.setMarrieddate("");
		}

		resultbasicinfo.setMarryexplain(basicinfo.getMarryexplain());
		resultbasicinfo.setMarryexplainen(basicinfo.getMarryexplainen());
		resultbasicinfo.setMarrystatus(basicinfo.getMarrystatus());
		resultbasicinfo.setNation(basicinfo.getNation());
		resultbasicinfo.setNationality(basicinfo.getNationality());
		resultbasicinfo.setOthercountry(basicinfo.getOthercountry());
		resultbasicinfo.setOthercountryen(basicinfo.getOthercountryen());
		resultbasicinfo.setOtherfirstname(basicinfo.getOtherfirstname());
		resultbasicinfo.setOtherfirstnameen(basicinfo.getOtherfirstnameen());
		resultbasicinfo.setOtherlastname(basicinfo.getOtherlastname());
		resultbasicinfo.setOtherlastnameen(basicinfo.getOtherlastnameen());
		resultbasicinfo.setOtherpassportnumber(basicinfo.getOtherpassportnumber());
		resultbasicinfo.setOtherpassportnumberen(basicinfo.getOtherpassportnumberen());
		resultbasicinfo.setProvince(basicinfo.getProvince());
		resultbasicinfo.setProvinceen(basicinfo.getProvinceen());

		if (Util.isEmpty(basicinfo.getSex())) {
			resultbasicinfo.setSex("男");
		} else {
			resultbasicinfo.setSex(basicinfo.getSex());
		}

		resultbasicinfo.setTelephone(basicinfo.getTelephone());
		resultbasicinfo.setTelephoneen(basicinfo.getTelephoneen());

		return resultbasicinfo;
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
		System.out.println("基本信息保存form:" + form);

		String encode = form.getEncode();
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			//基本信息
			updateBasicinfo(form);

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
		}
		return JuYouResult.ok();
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
	public Object updateBasicinfo(BasicinfoUSForm form) {

		System.out.println("form:" + form);
		TAppStaffFamilyinfoEntity familyinfo = dbDao.fetch(TAppStaffFamilyinfoEntity.class,
				Cnd.where("staffid", "=", form.getStaffid().longValue()));
		System.out.println(form.getMarrystatus());
		if (!Util.isEmpty(form.getMarrystatus()) && form.getMarrystatus() == 2) {//离异时，有结婚时间和离婚时间
			familyinfo.setMarrieddate(form.getMarrieddate());
			familyinfo.setDivorcedate(form.getDivorcedate());
		} else {
			familyinfo.setMarrieddate(null);
			familyinfo.setDivorcedate(null);
		}
		//familyinfo.setDivorcecountry(form.getDivorcecountry());
		//familyinfo.setDivorcecountryen(translate(form.getDivorcecountryen()));
		dbDao.update(familyinfo);

		TAppStaffBasicinfoEntity basicinfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, form.getStaffid().longValue());
		basicinfo.setBirthday(form.getBirthday());
		basicinfo.setCardcity(form.getCardcity());
		basicinfo.setCardId(form.getCardid());
		//basicinfo.setCardId(form.getCardId());
		basicinfo.setCardprovince(form.getCardprovince());
		basicinfo.setCity(form.getCity());
		basicinfo.setEmail(form.getEmail());
		basicinfo.setFirstname(form.getFirstname());
		basicinfo.setFirstnameen(form.getFirstnameen());
		basicinfo.setLastname(form.getLastname());
		basicinfo.setLastnameen(form.getLastnameen());
		basicinfo.setBirthcountry(form.getBirthcountry());
		basicinfo.setBirthcountryen(getCountrycode(form.getBirthcountry()));
		basicinfo.setMarrystatus(form.getMarrystatus());
		//basicinfo.setNationality(form.getNationality());
		basicinfo.setHasothername(form.getHasothername());
		basicinfo.setHasothernameen(form.getHasothername());

		basicinfo.setIsmailsamewithlive(form.getIsmailsamewithlive());
		basicinfo.setIsmailsamewithliveen(form.getIsmailsamewithlive());

		if (form.getIsmailsamewithlive() == 1) {
			basicinfo.setMailaddress("");
			basicinfo.setMailaddressen("");
			basicinfo.setMailcity("");
			basicinfo.setMailcityen("");
			basicinfo.setMailprovince("");
			basicinfo.setMailprovinceen("");
			basicinfo.setMailcountry("中国");
			basicinfo.setMailcountryen("China");
		} else {
			//basicinfo.setMailaddressen(translate(form.getMailaddress()));

			if (Util.isEmpty(form.getMailaddressen())) {
				basicinfo.setMailaddressen(translationHandle(2, translate(form.getMailaddress())));
			} else {
				if (Util.eq(form.getMailaddress(), basicinfo.getMailaddress())) {
					basicinfo.setMailaddressen(translationHandle(2, form.getMailaddressen()));
				} else {
					if (Util.eq(form.getMailaddressen(), basicinfo.getMailaddressen())) {
						basicinfo.setMailaddressen(translationHandle(2, translate(form.getMailaddress())));
					} else {
						basicinfo.setMailaddressen(translationHandle(2, form.getMailaddressen()));
					}
				}
			}

			basicinfo.setMailaddress(form.getMailaddress());

			//中文翻译成拼音并大写工具
			PinyinTool tool = new PinyinTool();
			if (!Util.isEmpty(form.getMailprovince())) {
				String issuedplace = form.getMailprovince();
				if (Util.eq("内蒙古", issuedplace) || Util.eq("内蒙古自治区", issuedplace)) {
					basicinfo.setMailprovinceen("NEI MONGOL");
				} else if (Util.eq("陕西", issuedplace) || Util.eq("陕西省", issuedplace)) {
					basicinfo.setMailprovinceen("SHAANXI");
				} else {
					if (issuedplace.endsWith("省") || issuedplace.endsWith("市")) {
						issuedplace = issuedplace.substring(0, issuedplace.length() - 1);
					}
					if (issuedplace.endsWith("自治区")) {
						issuedplace = issuedplace.substring(0, issuedplace.length() - 3);
					}
					if (issuedplace.endsWith("区")) {
						issuedplace = issuedplace.substring(0, issuedplace.length() - 1);
					}
					try {
						basicinfo.setMailprovinceen(tool.toPinYin(issuedplace, "", Type.UPPERCASE));
					} catch (BadHanyuPinyinOutputFormatCombination e1) {
						e1.printStackTrace();
					}
				}

			}

			if (!Util.isEmpty(form.getMailcity())) {
				String issuedplace = form.getMailcity();
				try {
					basicinfo.setMailcityen(tool.toPinYin(issuedplace, "", Type.UPPERCASE));
				} catch (BadHanyuPinyinOutputFormatCombination e1) {
					e1.printStackTrace();
				}

			}

			if (!Util.isEmpty(form.getMailcountry())) {
				String issuedplace = form.getMailcountry();
				try {
					basicinfo.setMailcountryen(getCountrycode(issuedplace));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

			basicinfo.setMailcity(form.getMailcity());
			//basicinfo.setMailcityen(translate(form.getMailcity()));

			basicinfo.setMailprovince(form.getMailprovince());
			//basicinfo.setMailprovinceen(translate(form.getMailprovince()));
			basicinfo.setMailcountry(form.getMailcountry());
			//basicinfo.setMailcountryen(translate(form.getMailcountry()));
		}

		if (form.getHasothername() == 2) {
			basicinfo.setOtherfirstname(null);
			basicinfo.setOtherfirstnameen(null);
			basicinfo.setOtherlastname(null);
			basicinfo.setOtherlastnameen(null);
		} else {
			basicinfo.setOtherfirstname(form.getOtherfirstname());
			basicinfo.setOtherfirstnameen(form.getOtherfirstnameen());
			basicinfo.setOtherlastname(form.getOtherlastname());
			basicinfo.setOtherlastnameen(form.getOtherlastnameen());
		}
		basicinfo.setProvince(form.getProvince());
		basicinfo.setSex(form.getSex());
		basicinfo.setUpdatetime(new Date());
		//英文翻译保存
		//basicinfo.setDetailedaddressen(translate(form.getDetailedaddress()));

		//如果地址英文为空，直接翻译中文地址
		if (Util.isEmpty(form.getDetailedaddressen())) {
			basicinfo.setDetailedaddressen(translationHandle(2, translate(form.getDetailedaddress())));
		} else {//地址英文不为空时，比较地址中文，如果一样，直接保存地址英文
			if (Util.eq(form.getDetailedaddress(), basicinfo.getDetailedaddress())) {
				basicinfo.setDetailedaddressen(translationHandle(2, form.getDetailedaddressen()));
			} else {//中文地址变了，比较英文地址是否一样,如果英文地址一样，则翻译，否则直接存
				if (Util.eq(form.getDetailedaddressen(), basicinfo.getDetailedaddressen())) {
					basicinfo.setDetailedaddressen(translationHandle(2, translate(form.getDetailedaddress())));
				} else {
					basicinfo.setDetailedaddressen(translationHandle(2, form.getDetailedaddressen()));
				}
			}
		}

		basicinfo.setDetailedaddress(form.getDetailedaddress());
		basicinfo.setCardprovinceen(translate(form.getCardprovince()));
		//basicinfo.setNationalityen(translate(form.getNationality()));

		//中文翻译成拼音并大写工具
		PinyinTool tool = new PinyinTool();
		if (!Util.isEmpty(form.getProvince())) {
			String issuedplace = form.getProvince();
			if (Util.eq("内蒙古", issuedplace) || Util.eq("内蒙古自治区", issuedplace)) {
				basicinfo.setProvinceen("NEI MONGOL");
			} else if (Util.eq("陕西", issuedplace) || Util.eq("陕西省", issuedplace)) {
				basicinfo.setProvinceen("SHAANXI");
			} else {
				if (issuedplace.endsWith("省") || issuedplace.endsWith("市")) {
					issuedplace = issuedplace.substring(0, issuedplace.length() - 1);
				}
				if (issuedplace.endsWith("自治区")) {
					issuedplace = issuedplace.substring(0, issuedplace.length() - 3);
				}
				if (issuedplace.endsWith("区")) {
					issuedplace = issuedplace.substring(0, issuedplace.length() - 1);
				}
				try {
					basicinfo.setProvinceen(tool.toPinYin(issuedplace, "", Type.UPPERCASE));
				} catch (BadHanyuPinyinOutputFormatCombination e1) {
					e1.printStackTrace();
				}
			}

		}
		if (!Util.isEmpty(form.getCity())) {
			String issuedplace = form.getCity();
			/*if (issuedplace.endsWith("省") || issuedplace.endsWith("市")) {
				issuedplace = issuedplace.substring(0, issuedplace.length() - 1);
			}
			if (issuedplace.endsWith("自治区")) {
				issuedplace = issuedplace.substring(0, issuedplace.length() - 3);
			}
			if (issuedplace.endsWith("区")) {
				issuedplace = issuedplace.substring(0, issuedplace.length() - 1);
			}*/
			try {
				basicinfo.setCityen(tool.toPinYin(issuedplace, "", Type.UPPERCASE));
			} catch (BadHanyuPinyinOutputFormatCombination e1) {
				e1.printStackTrace();
			}

		}

		if (!Util.isEmpty(form.getCardcity())) {
			String issuedplace = form.getCardcity();
			try {
				basicinfo.setCardcityen(tool.toPinYin(issuedplace, "", Type.UPPERCASE));
			} catch (BadHanyuPinyinOutputFormatCombination e1) {
				e1.printStackTrace();
			}

		}

		//basicinfo.setProvinceen(translate(form.getProvince()));
		//basicinfo.setCityen(translate(form.getCity()));

		basicinfo.setMarrystatusen(form.getMarrystatus());
		basicinfo.setTelephone(translationHandle(3, form.getTelephone()));
		basicinfo.setTelephoneen(translationHandle(3, form.getTelephone()));
		basicinfo.setEmailen(form.getEmail());
		basicinfo.setCardIden(form.getCardid());
		dbDao.update(basicinfo);
		return null;
	}

	//根据国家名称查询国籍代码
	public String getCountrycode(String countryname) {
		String countrycode = "";
		TCountryRegionEntity fetch = dbDao
				.fetch(TCountryRegionEntity.class, Cnd.where("chinesename", "=", countryname));
		if (!Util.isEmpty(fetch)) {
			countrycode = fetch.getInternationalcode();
		}
		return countrycode;
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

		long first = System.currentTimeMillis();
		System.out.println("护照信息回显的staffid:" + staffid);
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			Map<String, Object> result = Maps.newHashMap();
			SimpleDateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);

			/*String sqlStr = sqlManager.get("orderusmobile_getpassport");
			Sql passportsql = Sqls.create(sqlStr);
			passportsql.setParam("staffid", staffid);
			Record passportinfo = dbDao.fetch(passportsql);

			//性别处理
			String sex = passportinfo.getString("sex");
			if (Util.isEmpty(sex)) {
				passportinfo.put("sex", "男");
			}
			//日期相关处理
			if (!Util.isEmpty(passportinfo.get("issueddate"))) {
				passportinfo.put("issueddate", format.format(passportinfo.get("issueddate")));
			} else {
				passportinfo.put("issueddate", "");
			}
			if (!Util.isEmpty(passportinfo.get("validenddate"))) {
				passportinfo.put("validenddate", format.format(passportinfo.get("validenddate")));
			} else {
				passportinfo.put("validenddate", "");
			}*/

			TAppStaffPassportEntity passport = dbDao.fetch(TAppStaffPassportEntity.class,
					Cnd.where("staffid", "=", staffid));
			Map<String, String> passportinfo = MapUtil.obj2Map(passport);

			//性别处理
			String sex = passport.getSex();
			if (Util.isEmpty(sex)) {
				passportinfo.put("sex", "男");
			}
			//日期相关处理

			if (!Util.isEmpty(passport.getIssueddate())) {
				passportinfo.put("issueddate", format.format(passport.getIssueddate()));
			} else {
				passportinfo.put("issueddate", "");
			}
			if (!Util.isEmpty(passport.getValidenddate())) {
				passportinfo.put("validenddate", format.format(passport.getValidenddate()));
			} else {
				passportinfo.put("validenddate", "");
			}
			if (!Util.isEmpty(passport.getBirthday())) {
				passportinfo.put("birthday", format.format(passport.getBirthday()));
			} else {
				passportinfo.put("birthday", "");
			}
			if (!Util.isEmpty(passport.getCreatetime())) {
				passportinfo.put("createtime", format.format(passport.getCreatetime()));
			} else {
				passportinfo.put("createtime", "");
			}
			if (!Util.isEmpty(passport.getUpdatetime())) {
				passportinfo.put("updatetime", format.format(passport.getUpdatetime()));
			} else {
				passportinfo.put("updatetime", "");
			}

			//PassportinfoUSDateForm passportMap = new PassportinfoUSDateForm();

			//PassportinfoUSDateForm passportinfo = removeDataToNewPassport(passport, passportMap);

			result.put("passport", passportinfo);
			System.out.println("护照信息回显result:" + result);

			long last = System.currentTimeMillis();
			System.out.println("护照信息回显所用时间:" + (last - first) + "ms");
			return JuYouResult.ok(result);
		}
	}

	public PassportinfoUSDateForm removeDataToNewPassport(TAppStaffPassportEntity passport,
			PassportinfoUSDateForm passportinfo) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		passportinfo.setBirthaddress(passport.getBirthaddress());
		passportinfo.setBirthaddressen(passport.getBirthaddressen());

		if (!Util.isEmpty(passport.getBirthday())) {
			passportinfo.setBirthday(sdf.format(passport.getBirthday()));
		} else {
			passportinfo.setBirthday("");
		}

		passportinfo.setFirstname(passport.getFirstname());
		passportinfo.setFirstnameen(passport.getFirstnameen());
		passportinfo.setIslostpassport(passport.getIslostpassport());
		passportinfo.setIslostpassporten(passport.getIslostpassporten());
		passportinfo.setIsrememberpassportnum(passport.getIsrememberpassportnum());
		passportinfo.setIsrememberpassportnumen(passport.getIsrememberpassportnumen());

		if (!Util.isEmpty(passport.getIssueddate())) {
			passportinfo.setIssueddate(sdf.format(passport.getIssueddate()));
		} else {
			passportinfo.setIssueddate("");
		}

		passportinfo.setIssuedorganization(passport.getIssuedorganization());
		passportinfo.setIssuedorganizationen(passport.getIssuedorganizationen());
		passportinfo.setIssuedplace(passport.getIssuedplace());
		passportinfo.setIssuedplaceen(passport.getIssuedplaceen());
		passportinfo.setLastname(passport.getLastname());
		passportinfo.setLastnameen(passport.getLastnameen());
		passportinfo.setLostpassportnum(passport.getLostpassportnum());
		passportinfo.setLostpassportnumen(passport.getLostpassportnumen());
		passportinfo.setPassport(passport.getPassport());

		if (Util.isEmpty(passport.getSex())) {
			passportinfo.setSex("男");
		} else {
			passportinfo.setSex(passport.getSex());
		}

		passportinfo.setSexen(passport.getSexen());
		passportinfo.setType(passport.getType());

		if (!Util.isEmpty(passport.getValidenddate())) {
			passportinfo.setValidenddate(sdf.format(passport.getValidenddate()));
		} else {
			passportinfo.setValidenddate("");
		}

		passportinfo.setValidtype(passport.getValidtype());

		return passportinfo;
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
		System.out.println("护照信息保存form:" + form);
		String encode = form.getEncode();
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			Integer staffid = form.getStaffid();
			TAppStaffPassportEntity passport = dbDao.fetch(TAppStaffPassportEntity.class,
					Cnd.where("staffid", "=", staffid));
			passport.setPassport(form.getPassport());
			passport.setIssuedplace(form.getIssuedplace());
			passport.setIssuedplaceen(form.getIssuedplaceen());
			passport.setIssueddate(form.getIssueddate());
			passport.setValidtype(form.getValidtype());
			passport.setValidenddate(form.getValidenddate());
			passport.setIssuedorganization(form.getIssuedorganization());
			passport.setIssuedorganizationen(form.getIssuedorganizationen());
			passport.setIslostpassport(form.getIslostpassport());
			//passport.setIsrememberpassportnum(form.getIsrememberpassportnum());
			//passport.setLostpassportnum(form.getLostpassportnum());

			//英文
			//中文翻译成拼音并大写工具
			PinyinTool tool = new PinyinTool();
			if (!Util.isEmpty(form.getIssuedplace())) {
				String issuedplace = form.getIssuedplace();
				if (Util.eq("内蒙古", issuedplace)) {
					passport.setIssuedplaceen("NEI MONGOL");
				} else if (Util.eq("陕西", issuedplace)) {
					passport.setIssuedplaceen("SHAANXI");
				} else {
					try {

						passport.setIssuedplaceen(tool.toPinYin(form.getIssuedplace(), "", Type.UPPERCASE));
					} catch (BadHanyuPinyinOutputFormatCombination e1) {
						e1.printStackTrace();
					}
				}

			}

			if (form.getIslostpassport() == 2) {
				passport.setIsrememberpassportnum(2);
				passport.setIsrememberpassportnumen(2);
				passport.setLostpassportnum("不知道");
				passport.setLostpassportnumen("I do not know");
			} else {
				passport.setIsrememberpassportnum(form.getIsrememberpassportnum());
				passport.setIsrememberpassportnumen(form.getIsrememberpassportnum());
				if (form.getIsrememberpassportnum() == 2) {
					passport.setLostpassportnum("不知道");
					passport.setLostpassportnumen("I do not know");
				} else {
					passport.setLostpassportnum(form.getLostpassportnum());
					passport.setLostpassportnumen(form.getLostpassportnum());
				}
			}

			passport.setIslostpassporten(form.getIslostpassport());
			//passport.setIsrememberpassportnumen(form.getIsrememberpassportnum());
			//passport.setLostpassportnumen(form.getLostpassportnum());
			dbDao.update(passport);
		}
		return JuYouResult.ok();
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
		long first = System.currentTimeMillis();
		System.out.println("家庭信息回显的staffid:" + staffid);
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			Map<String, Object> result = Maps.newHashMap();
			SimpleDateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);

			TAppStaffFamilyinfoEntity familyinfo = dbDao.fetch(TAppStaffFamilyinfoEntity.class,
					Cnd.where("staffid", "=", staffid));

			//FamilyinfoUSDateForm family = new FamilyinfoUSDateForm();
			//FamilyinfoUSDateForm familyinfoMap = removeDataToNewFamilyinfo(familyinfo, family);

			Map<String, String> familyinfoMap = MapUtil.obj2Map(familyinfo);

			//日期格式处理
			if (!Util.isEmpty(familyinfo.getSpousebirthday())) {
				familyinfoMap.put("spousebirthday", format.format(familyinfo.getSpousebirthday()));
			} else {
				familyinfoMap.put("spousebirthday", "");
			}
			if (!Util.isEmpty(familyinfo.getMarrieddate())) {
				familyinfoMap.put("marrieddate", format.format(familyinfo.getMarrieddate()));
			} else {
				familyinfoMap.put("marrieddate", "");
			}
			if (!Util.isEmpty(familyinfo.getDivorcedate())) {
				familyinfoMap.put("divorcedate", format.format(familyinfo.getDivorcedate()));
			} else {
				familyinfoMap.put("divorcedate", "");
			}
			if (!Util.isEmpty(familyinfo.getSpousebirthdayen())) {
				familyinfoMap.put("spousebirthdayen", format.format(familyinfo.getSpousebirthdayen()));
			} else {
				familyinfoMap.put("spousebirthdayen", "");
			}
			if (!Util.isEmpty(familyinfo.getFatherbirthday())) {
				familyinfoMap.put("fatherbirthday", format.format(familyinfo.getFatherbirthday()));
			} else {
				familyinfoMap.put("fatherbirthday", "");
			}
			if (!Util.isEmpty(familyinfo.getFatherbirthdayen())) {
				familyinfoMap.put("fatherbirthdayen", format.format(familyinfo.getFatherbirthdayen()));
			} else {
				familyinfoMap.put("fatherbirthdayen", "");
			}

			if (!Util.isEmpty(familyinfo.getMotherbirthday())) {
				familyinfoMap.put("motherbirthday", format.format(familyinfo.getMotherbirthday()));
			} else {
				familyinfoMap.put("motherbirthday", "");
			}
			if (!Util.isEmpty(familyinfo.getMotherbirthdayen())) {
				familyinfoMap.put("motherbirthdayen", format.format(familyinfo.getMotherbirthdayen()));
			} else {
				familyinfoMap.put("motherbirthdayen", "");
			}
			result.put("family", familyinfoMap);

			TAppStaffBasicinfoEntity basicinfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, staffid);
			if (Util.isEmpty(basicinfo.getMarrystatus())) {
				result.put("marrystatus", 4);
			} else {
				result.put("marrystatus", basicinfo.getMarrystatus());
			}

			TAppStaffImmediaterelativesEntity immediaterelatives = dbDao.fetch(TAppStaffImmediaterelativesEntity.class,
					Cnd.where("staffid", "=", staffid));
			if (!Util.isEmpty(immediaterelatives)) {
				result.put("immediaterelatives", immediaterelatives);
			} else {
				TAppStaffImmediaterelativesEntity emptyimmediaterelatives = new TAppStaffImmediaterelativesEntity();
				result.put("immediaterelatives", emptyimmediaterelatives);
			}

			long second = System.currentTimeMillis();
			//System.out.println("只有家庭信息所用时间:" + (second - first) + "ms");

			//国家下拉
			List<TCountryRegionEntity> gocountryFiveList = dbDao.query(TCountryRegionEntity.class, null, null);
			result.put("gocountryfivelist", gocountryFiveList);
			//城市下拉
			String sqlStr = sqlManager.get("orderUS_mobile_getProvince");
			Sql provincesql = Sqls.create(sqlStr);
			List<Record> provinceList = dbDao.query(provincesql, null, null);
			result.put("provincelist", provinceList);

			long third = System.currentTimeMillis();
			System.out.println("下拉所用时间:" + (third - second) + "ms");

			result.put("spousecontactaddressenum", EnumUtil.enum2(VisaSpouseContactAddressEnum.class));
			result.put("familyinfoenum", EnumUtil.enum2(VisaFamilyInfoEnum.class));
			result.put("immediaterelationshipenum", EnumUtil.enum2(ImmediateFamilyMembersRelationshipEnum.class));
			System.out.println("家庭信息回显result:" + result);
			long last = System.currentTimeMillis();
			System.out.println("家庭信息回显所用时间:" + (last - first) + "ms");
			return JuYouResult.ok(result);
		}
	}

	public FamilyinfoUSDateForm removeDataToNewFamilyinfo(TAppStaffFamilyinfoEntity family,
			FamilyinfoUSDateForm familyinfoMap) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		familyinfoMap.setCity(family.getCity());
		familyinfoMap.setCityen(family.getCityen());
		familyinfoMap.setCountry(family.getCountry());
		familyinfoMap.setCountryen(family.getCountryen());

		if (!Util.isEmpty(family.getFatherbirthday())) {
			familyinfoMap.setFatherbirthday(sdf.format(family.getFatherbirthday()));
		} else {
			familyinfoMap.setFatherbirthday("");
		}

		familyinfoMap.setFatherfirstname(family.getFatherfirstname());
		familyinfoMap.setFatherfirstnameen(family.getFatherfirstnameen());
		familyinfoMap.setFatherlastname(family.getFatherlastname());
		familyinfoMap.setFatherlastnameen(family.getFatherlastnameen());
		familyinfoMap.setFatherstatus(family.getFatherstatus());
		familyinfoMap.setFatherstatusen(family.getFatherstatusen());
		familyinfoMap.setFirstaddress(family.getFirstaddress());
		familyinfoMap.setFirstaddressen(family.getFirstaddressen());
		familyinfoMap.setHasimmediaterelatives(family.getHasimmediaterelatives());
		familyinfoMap.setHasimmediaterelativesen(family.getHasimmediaterelativesen());
		familyinfoMap.setHasotherrelatives(family.getHasotherrelatives());
		familyinfoMap.setHasotherrelativesen(family.getHasotherrelativesen());
		familyinfoMap.setIsfatherinus(family.getIsfatherinus());
		familyinfoMap.setIsfatherinusen(family.getIsfatherinusen());
		familyinfoMap.setIsmotherinus(family.getIsmotherinus());
		familyinfoMap.setIsmotherinusen(family.getIsmotherinusen());

		if (!Util.isEmpty(family.getMotherbirthday())) {
			familyinfoMap.setMotherbirthday(sdf.format(family.getMotherbirthday()));
		} else {
			familyinfoMap.setMotherbirthday("");
		}

		familyinfoMap.setMotherbirthdayen(family.getMotherbirthdayen());
		familyinfoMap.setMotherfirstname(family.getMotherfirstname());
		familyinfoMap.setMotherfirstnameen(family.getMotherfirstnameen());
		familyinfoMap.setMotherlastname(family.getMotherlastname());
		familyinfoMap.setMotherlastnameen(family.getMotherlastnameen());
		familyinfoMap.setMotherstatus(family.getMotherstatus());
		familyinfoMap.setMotherstatusen(family.getMotherstatusen());
		familyinfoMap.setProvince(family.getProvince());
		familyinfoMap.setProvinceen(family.getProvinceen());
		familyinfoMap.setSecondaddress(family.getSecondaddress());
		familyinfoMap.setSecondaddressen(family.getSecondaddressen());
		familyinfoMap.setSpouseaddress(family.getSpouseaddress());
		familyinfoMap.setSpouseaddressen(family.getSpouseaddressen());

		if (!Util.isEmpty(family.getSpousebirthday())) {
			familyinfoMap.setSpousebirthday(sdf.format(family.getSpousebirthday()));
		} else {
			familyinfoMap.setSpousebirthday("");
		}

		familyinfoMap.setSpousecity(family.getSpousecity());
		familyinfoMap.setSpousecityen(family.getSpousecityen());
		familyinfoMap.setSpousecountry(family.getSpousecountry());
		familyinfoMap.setSpousecountryen(family.getSpousecountryen());
		familyinfoMap.setSpousefirstname(family.getSpousefirstname());
		familyinfoMap.setSpousefirstnameen(family.getSpousefirstnameen());
		familyinfoMap.setSpouselastname(family.getSpouselastname());
		familyinfoMap.setSpouselastnameen(family.getSpouselastnameen());
		familyinfoMap.setSpousenationality(family.getSpousenationality());
		familyinfoMap.setSpousenationalityen(family.getSpousenationalityen());
		familyinfoMap.setZipcode(family.getZipcode());
		familyinfoMap.setZipcodeen(family.getZipcodeen());

		return familyinfoMap;
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
		System.out.println("家庭信息保存form:" + form);
		String encode = form.getEncode();
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			Integer staffid = form.getStaffid();
			TAppStaffFamilyinfoEntity familyinfo = dbDao.fetch(TAppStaffFamilyinfoEntity.class,
					Cnd.where("staffid", "=", staffid));
			//配偶信息
			updateSpouseinfo(form, familyinfo);

			//父亲信息
			updateFatherinfo(form, familyinfo);

			//母亲信息
			updateMotherinfo(form, familyinfo);

			familyinfo.setHasimmediaterelatives(form.getHasimmediaterelatives());
			familyinfo.setHasimmediaterelativesen(form.getHasimmediaterelatives());
			dbDao.update(familyinfo);

			//其他亲属
			if (form.getHasimmediaterelatives() == 1) {//有其他亲属,则更新或添加
				updateOtherinfo(form);
			} else {
				TAppStaffImmediaterelativesEntity immediaterelatives = dbDao.fetch(
						TAppStaffImmediaterelativesEntity.class, Cnd.where("staffid", "=", staffid));
				if (!Util.isEmpty(immediaterelatives)) {
					dbDao.delete(immediaterelatives);
				}
			}

			return JuYouResult.ok();
		}
	}

	/**
	 * 配偶信息保存
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param familyinfo
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateSpouseinfo(FamilyinfoUSForm form, TAppStaffFamilyinfoEntity familyinfo) {
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
		familyinfo.setSpousecountryen(form.getSpousecountry());
		familyinfo.setSpousenationalityen(form.getSpousenationality());
		//familyinfo.setSpousenationalityen(translate(form.getSpousenationality()));
		//familyinfo.setSpousecountryen(translate(form.getSpousecountry()));
		//familyinfo.setSpousecityen(translate(form.getSpousecity()));

		//中文翻译成拼音并大写工具
		PinyinTool tool = new PinyinTool();
		if (!Util.isEmpty(form.getSpousecity())) {
			String issuedplace = form.getSpousecity();
			if (Util.eq("内蒙古", issuedplace) || Util.eq("内蒙古自治区", issuedplace)) {
				familyinfo.setSpousecityen("NEI MONGOL");
			} else if (Util.eq("陕西", issuedplace) || Util.eq("陕西省", issuedplace)) {
				familyinfo.setSpousecityen("SHAANXI");
			} else {
				if (issuedplace.endsWith("省") || issuedplace.endsWith("市")) {
					issuedplace = issuedplace.substring(0, issuedplace.length() - 1);
				}
				if (issuedplace.endsWith("自治区")) {
					issuedplace = issuedplace.substring(0, issuedplace.length() - 3);
				}
				if (issuedplace.endsWith("区")) {
					issuedplace = issuedplace.substring(0, issuedplace.length() - 1);
				}
				try {
					familyinfo.setSpousecityen(tool.toPinYin(issuedplace, "", Type.UPPERCASE));
				} catch (BadHanyuPinyinOutputFormatCombination e1) {
					e1.printStackTrace();
				}
			}

		}

		familyinfo.setSpouseaddressen(form.getSpouseaddress());
		return null;
	}

	/**
	 * 父亲信息保存
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param familyinfo
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateFatherinfo(FamilyinfoUSForm form, TAppStaffFamilyinfoEntity familyinfo) {
		familyinfo.setFatherfirstname(form.getFatherfirstname());
		familyinfo.setFatherfirstnameen(form.getFatherfirstnameen());
		familyinfo.setFatherlastname(form.getFatherlastname());
		familyinfo.setFatherlastnameen(form.getFatherlastnameen());
		familyinfo.setFatherbirthday(form.getFatherbirthday());
		familyinfo.setIsfatherinus(form.getIsfatherinus());
		familyinfo.setFatherstatus(form.getFatherstatus());
		familyinfo.setFatherstatusen(form.getFatherstatus());
		if (form.getIsfatherinus() == 2) {
			familyinfo.setFatherstatus(null);
			familyinfo.setFatherstatusen(null);
		}

		//英文
		familyinfo.setFatherbirthdayen(form.getFatherbirthday());
		familyinfo.setIsfatherinusen(form.getIsfatherinus());
		return null;
	}

	/**
	 * 母亲信息保存
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param familyinfo
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateMotherinfo(FamilyinfoUSForm form, TAppStaffFamilyinfoEntity familyinfo) {
		familyinfo.setMotherbirthday(form.getMotherbirthday());
		familyinfo.setMotherfirstname(form.getMotherfirstname());
		familyinfo.setMotherfirstnameen(form.getMotherfirstnameen());
		familyinfo.setMotherlastname(form.getMotherlastname());
		familyinfo.setMotherlastnameen(form.getMotherlastnameen());
		familyinfo.setMotherstatus(form.getMotherstatus());
		familyinfo.setMotherstatusen(form.getMotherstatus());
		familyinfo.setIsmotherinus(form.getIsmotherinus());
		if (form.getIsmotherinus() == 2) {
			familyinfo.setMotherstatus(null);
			familyinfo.setMotherstatusen(null);
		}
		//英文
		familyinfo.setMotherbirthdayen(form.getMotherbirthday());
		familyinfo.setIsmotherinusen(form.getIsmotherinus());
		return null;
	}

	/**
	 * 其他亲属保存
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateOtherinfo(FamilyinfoUSForm form) {
		Integer staffid = form.getStaffid();
		TAppStaffImmediaterelativesEntity immediaterelatives = dbDao.fetch(TAppStaffImmediaterelativesEntity.class,
				Cnd.where("staffid", "=", staffid));
		if (Util.isEmpty(immediaterelatives)) {//如果为空，则添加，否则更新
			immediaterelatives = new TAppStaffImmediaterelativesEntity();
			immediaterelatives.setStaffid(staffid);
			immediaterelatives = dbDao.insert(immediaterelatives);
		}
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
		System.out.println("工作教育信息回显的staffid:" + staffid);
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			Map<String, Object> result = Maps.newHashMap();
			SimpleDateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
			//职业信息
			TAppStaffWorkEducationTrainingEntity workinfo = dbDao.fetch(TAppStaffWorkEducationTrainingEntity.class,
					Cnd.where("staffid", "=", staffid));
			Map<String, String> workinfoMap = MapUtil.obj2Map(workinfo);
			if (!Util.isEmpty(workinfo.getWorkstartdate())) {
				workinfoMap.put("workstartdate", format.format(workinfo.getWorkstartdate()));
			} else {
				workinfoMap.put("workstartdate", "");
			}
			if (!Util.isEmpty(workinfo.getWorkstartdateen())) {
				workinfoMap.put("workstartdateen", format.format(workinfo.getWorkstartdateen()));
			} else {
				workinfoMap.put("workstartdateen", "");
			}
			workinfoMap.remove("staffid");

			result.put("workinfo", workinfoMap);

			//上份工作信息
			TAppStaffBeforeworkEntity beforework = dbDao.fetch(TAppStaffBeforeworkEntity.class,
					Cnd.where("staffid", "=", staffid));

			if (Util.isEmpty(beforework)) {
				TAppStaffBeforeworkEntity newbeforework = new TAppStaffBeforeworkEntity();
				result.put("beforework", newbeforework);
			} else {
				Map<String, String> beforeworkMap = MapUtil.obj2Map(beforework);

				if (!Util.isEmpty(beforework.getEmploystartdate())) {
					beforeworkMap.put("employstartdate", format.format(beforework.getEmploystartdate()));
				} else {
					beforeworkMap.put("employstartdate", "");
				}
				if (!Util.isEmpty(beforework.getEmploystartdateen())) {
					beforeworkMap.put("employstartdateen", format.format(beforework.getEmploystartdateen()));
				} else {
					beforeworkMap.put("employstartdateen", "");
				}

				if (!Util.isEmpty(beforework.getEmployenddate())) {
					beforeworkMap.put("employenddate", format.format(beforework.getEmployenddate()));
				} else {
					beforeworkMap.put("employenddate", "");
				}
				if (!Util.isEmpty(beforework.getEmployenddateen())) {
					beforeworkMap.put("employenddateen", format.format(beforework.getEmployenddateen()));
				} else {
					beforeworkMap.put("employenddateen", "");
				}

				beforeworkMap.remove("staffid");
				result.put("beforework", beforeworkMap);
			}

			//教育信息

			TAppStaffBeforeeducationEntity beforeeducate = dbDao.fetch(TAppStaffBeforeeducationEntity.class,
					Cnd.where("staffid", "=", staffid));

			if (Util.isEmpty(beforeeducate)) {
				TAppStaffBeforeeducationEntity newbeforeeducate = new TAppStaffBeforeeducationEntity();
				result.put("beforeeducate", newbeforeeducate);
			} else {
				Map<String, String> beforeeducateMap = MapUtil.obj2Map(beforeeducate);

				if (!Util.isEmpty(beforeeducate.getCoursestartdate())) {
					beforeeducateMap.put("coursestartdate", format.format(beforeeducate.getCoursestartdate()));
				} else {
					beforeeducateMap.put("coursestartdate", "");
				}
				if (!Util.isEmpty(beforeeducate.getCoursestartdateen())) {
					beforeeducateMap.put("coursestartdateen", format.format(beforeeducate.getCoursestartdateen()));
				} else {
					beforeeducateMap.put("coursestartdateen", "");
				}
				if (!Util.isEmpty(beforeeducate.getCourseenddate())) {
					beforeeducateMap.put("courseenddate", format.format(beforeeducate.getCourseenddate()));
				} else {
					beforeeducateMap.put("courseenddate", "");
				}
				if (!Util.isEmpty(beforeeducate.getCourseenddateen())) {
					beforeeducateMap.put("courseenddateen", format.format(beforeeducate.getCourseenddateen()));
				} else {
					beforeeducateMap.put("courseenddateen", "");
				}

				beforeeducateMap.remove("staffid");
				result.put("beforeeducate", beforeeducateMap);
			}

			/*//职业信息
			TAppStaffWorkEducationTrainingEntity workinfo = dbDao.fetch(TAppStaffWorkEducationTrainingEntity.class,
					Cnd.where("staffid", "=", staffid));

			WorkandeducateinfoUSDateForm work = new WorkandeducateinfoUSDateForm();
			WorkandeducateinfoUSDateForm workinfoMap = removeDataToNewWorkandeducate(workinfo, work);
			result.put("workinfo", workinfoMap);*/

			/*
			//职业信息
			TAppStaffWorkEducationTrainingEntity workinfo = dbDao.fetch(TAppStaffWorkEducationTrainingEntity.class,
					Cnd.where("staffid", "=", staffid));
			result.put("workinfo", workinfo);
			if (!Util.isEmpty(workinfo.getWorkstartdate())) {
				result.put("workstartdate", format.format(workinfo.getWorkstartdate()));
			} else {
				result.put("workstartdate", "");
			}
			 
			//上份工作信息
			TAppStaffBeforeworkEntity beforework = dbDao.fetch(TAppStaffBeforeworkEntity.class,
					Cnd.where("staffid", "=", staffid));
			if (!Util.isEmpty(beforework)) {
				result.put("beforework", beforework);
				if (!Util.isEmpty(beforework.getEmploystartdate())) {
					result.put("employstartdate", format.format(beforework.getEmploystartdate()));
				} else {
					result.put("employstartdate", "");
				}
				if (!Util.isEmpty(beforework.getEmployenddate())) {
					result.put("employenddate", format.format(beforework.getEmployenddate()));
				} else {
					result.put("employenddate", "");
				}
			} else {
				TAppStaffBeforeworkEntity newbeforework = new TAppStaffBeforeworkEntity();
				result.put("beforework", newbeforework);
				result.put("employstartdate", "");
				result.put("employenddate", "");
			}
			//教育信息
			TAppStaffBeforeeducationEntity beforeeducate = dbDao.fetch(TAppStaffBeforeeducationEntity.class,
					Cnd.where("staffid", "=", staffid));
			if (!Util.isEmpty(beforeeducate)) {
				result.put("beforeeducate", beforeeducate);
				if (!Util.isEmpty(beforeeducate.getCoursestartdate())) {
					result.put("coursestartdate", format.format(beforeeducate.getCoursestartdate()));
				} else {
					result.put("coursestartdate", "");
				}
				if (!Util.isEmpty(beforeeducate.getCourseenddate())) {
					result.put("courseenddate", format.format(beforeeducate.getCourseenddate()));
				} else {
					result.put("courseenddate", "");
				}
			} else {
				TAppStaffBeforeeducationEntity newbeforeeducate = new TAppStaffBeforeeducationEntity();
				result.put("beforeeducate", newbeforeeducate);
				result.put("coursestartdate", "");
				result.put("courseenddate", "");
			}*/

			//国家下拉
			List<TCountryRegionEntity> gocountryFiveList = dbDao.query(TCountryRegionEntity.class, null, null);
			result.put("gocountryfivelist", gocountryFiveList);

			result.put("careersenum", EnumUtil.enum2(VisaCareersEnum.class));
			result.put("highesteducationenum", EnumUtil.enum2(VisaHighestEducationEnum.class));
			System.out.println("职业与教育信息回显result:" + result);
			return JuYouResult.ok(result);
		}
	}

	public WorkandeducateinfoUSDateForm removeDataToNewWorkandeducate(TAppStaffWorkEducationTrainingEntity workinfo,
			WorkandeducateinfoUSDateForm workinfoMap) {

		//workinfoMap.setAddress(workinfo.getAddress());
		//workinfoMap.setAddressen(workinfo.getAddressen());

		return workinfoMap;
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
		System.out.println("工作教育信息保存form:" + form);
		String encode = form.getEncode();
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			Integer staffid = form.getStaffid();
			//工作教育信息
			updateWorkinfo(form);

			//以前的工作信息
			if (Util.eq(1, form.getIsemployed())) {
				updateBeforework(form);
			} else {
				TAppStaffBeforeworkEntity beforework = dbDao.fetch(TAppStaffBeforeworkEntity.class,
						Cnd.where("staffid", "=", staffid));
				if (!Util.isEmpty(beforework)) {
					dbDao.delete(beforework);
				}
			}
			//教育信息
			//updateBeforeeducation(form);
			if (Util.eq(1, form.getIssecondarylevel())) {
				updateBeforeeducation(form);
			} else {
				TAppStaffBeforeeducationEntity beforeeducation = dbDao.fetch(TAppStaffBeforeeducationEntity.class,
						Cnd.where("staffid", "=", staffid));
				if (!Util.isEmpty(beforeeducation)) {
					dbDao.delete(beforeeducation);
				}
			}

			return JuYouResult.ok();
		}
	}

	/**
	 * 工作信息保存
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateWorkinfo(WorkandeducateinfoUSForm form) {
		Integer staffid = form.getStaffid();
		TAppStaffWorkEducationTrainingEntity workinfo = dbDao.fetch(TAppStaffWorkEducationTrainingEntity.class,
				Cnd.where("staffid", "=", staffid));
		System.out.println(form);
		System.out.println(form.getOccupation());
		workinfo.setOccupation(Integer.valueOf(form.getOccupation()));

		if (Util.isEmpty(form.getUnitnameen())) {
			workinfo.setUnitnameen(translationHandle(1, translate(form.getUnitname())));
		} else {
			if (Util.eq(form.getUnitname(), workinfo.getUnitname())) {
				workinfo.setUnitnameen(translationHandle(1, form.getUnitnameen()));
			} else {
				if (Util.eq(form.getUnitnameen(), workinfo.getUnitnameen())) {
					workinfo.setUnitnameen(translationHandle(1, translate(form.getUnitname())));
				} else {
					workinfo.setUnitnameen(translationHandle(1, form.getUnitnameen()));
				}
			}
		}

		workinfo.setUnitname(form.getUnitname());
		//workinfo.setUnitnameen(form.getUnitnameen());

		workinfo.setTelephone(translationHandle(3, form.getTelephone()));
		workinfo.setCountry(form.getCountry());
		workinfo.setProvince(form.getProvince());
		workinfo.setCity(form.getCity());

		if (Util.isEmpty(form.getAddressen())) {
			workinfo.setAddressen(translationHandle(2, translate(form.getAddress())));
		} else {
			if (Util.eq(form.getAddress(), workinfo.getAddress())) {
				workinfo.setAddressen(translationHandle(2, form.getAddressen()));
			} else {
				if (Util.eq(form.getAddressen(), workinfo.getAddressen())) {
					workinfo.setAddressen(translationHandle(2, translate(form.getAddress())));
				} else {
					workinfo.setAddressen(translationHandle(2, form.getAddressen()));
				}
			}
		}

		workinfo.setAddress(form.getAddress());
		//workinfo.setAddressen(form.getAddressen());

		workinfo.setWorkstartdate(form.getWorkstartdate());

		if (Util.isEmpty(form.getPositionen())) {
			workinfo.setPositionen(translationHandle(1, translate(form.getPosition())));
		} else {
			if (Util.eq(form.getPosition(), workinfo.getPosition())) {
				workinfo.setPositionen(translationHandle(1, form.getPositionen()));
			} else {
				if (Util.eq(form.getPositionen(), workinfo.getPositionen())) {
					workinfo.setPositionen(translationHandle(1, translate(form.getPosition())));
				} else {
					workinfo.setPositionen(translationHandle(1, form.getPositionen()));
				}
			}
		}

		workinfo.setPosition(form.getPosition());
		workinfo.setSalary(form.getSalary());

		if (Util.isEmpty(form.getDutyen())) {
			workinfo.setDutyen(translate(form.getDuty()));
		} else {
			if (Util.eq(form.getDuty(), workinfo.getDuty())) {
				workinfo.setDutyen(form.getDutyen());
			} else {
				if (Util.eq(form.getDutyen(), workinfo.getDutyen())) {
					workinfo.setDutyen(translate(form.getDuty()));
				} else {
					workinfo.setDutyen(form.getDutyen());
				}
			}
		}

		workinfo.setDuty(form.getDuty());
		workinfo.setIsemployed(form.getIsemployed());
		workinfo.setIssecondarylevel(form.getIssecondarylevel());
		//英文翻译保存
		workinfo.setOccupationen(form.getOccupation());
		workinfo.setTelephoneen(translationHandle(3, form.getTelephone()));

		//中文翻译成拼音并大写工具
		PinyinTool tool = new PinyinTool();
		if (!Util.isEmpty(form.getProvince())) {
			String issuedplace = form.getProvince();
			if (Util.eq("内蒙古", issuedplace) || Util.eq("内蒙古自治区", issuedplace)) {
				workinfo.setProvinceen("NEI MONGOL");
			} else if (Util.eq("陕西", issuedplace) || Util.eq("陕西省", issuedplace)) {
				workinfo.setProvinceen("SHAANXI");
			} else {
				if (issuedplace.endsWith("省") || issuedplace.endsWith("市")) {
					issuedplace = issuedplace.substring(0, issuedplace.length() - 1);
				}
				if (issuedplace.endsWith("自治区")) {
					issuedplace = issuedplace.substring(0, issuedplace.length() - 3);
				}
				if (issuedplace.endsWith("区")) {
					issuedplace = issuedplace.substring(0, issuedplace.length() - 1);
				}
				try {
					workinfo.setProvinceen(tool.toPinYin(issuedplace, "", Type.UPPERCASE));
				} catch (BadHanyuPinyinOutputFormatCombination e1) {
					e1.printStackTrace();
				}
			}

		}

		if (!Util.isEmpty(form.getCity())) {
			String issuedplace = form.getCity();
			/*if (issuedplace.endsWith("省") || issuedplace.endsWith("市")) {
						issuedplace = issuedplace.substring(0, issuedplace.length() - 1);
					}
					if (issuedplace.endsWith("自治区")) {
						issuedplace = issuedplace.substring(0, issuedplace.length() - 3);
					}
					if (issuedplace.endsWith("区")) {
						issuedplace = issuedplace.substring(0, issuedplace.length() - 1);
					}*/
			try {
				workinfo.setCityen(tool.toPinYin(issuedplace, "", Type.UPPERCASE));
			} catch (BadHanyuPinyinOutputFormatCombination e1) {
				e1.printStackTrace();
			}

		}

		//workinfo.setProvinceen(translate(form.getProvince()));
		//workinfo.setCityen(translate(form.getCity()));

		workinfo.setCountryen(form.getCountry());
		workinfo.setWorkstartdateen(form.getWorkstartdate());
		//workinfo.setPositionen(translate(form.getPosition()));
		workinfo.setSalaryen(form.getSalary());
		//workinfo.setDutyen(translate(form.getDuty()));
		workinfo.setIsemployeden(form.getIsemployed());
		workinfo.setIssecondarylevelen(form.getIssecondarylevel());
		dbDao.update(workinfo);
		return null;
	}

	/**
	 * 上份工作信息保存
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateBeforework(WorkandeducateinfoUSForm form) {
		Integer staffid = form.getStaffid();
		TAppStaffBeforeworkEntity beforework = dbDao.fetch(TAppStaffBeforeworkEntity.class,
				Cnd.where("staffid", "=", staffid));
		if (Util.isEmpty(beforework)) {
			beforework = new TAppStaffBeforeworkEntity();
			beforework.setStaffid(staffid);
			dbDao.insert(beforework);
		}

		if (Util.isEmpty(form.getEmployernameen())) {
			beforework.setEmployernameen(translationHandle(1, translate(form.getEmployername())));
		} else {
			if (Util.eq(form.getEmployername(), beforework.getEmployername())) {
				beforework.setEmployernameen(translationHandle(1, form.getEmployernameen()));
			} else {
				if (Util.eq(form.getEmployernameen(), beforework.getEmployernameen())) {
					beforework.setEmployernameen(translationHandle(1, translate(form.getEmployername())));
				} else {
					beforework.setEmployernameen(translationHandle(1, form.getEmployernameen()));
				}
			}
		}

		beforework.setEmployername(form.getEmployername());

		beforework.setEmployertelephone(translationHandle(3, form.getEmployertelephone()));
		beforework.setEmployercountry(form.getEmployercountry());
		beforework.setEmployerprovince(form.getEmployerprovince());
		beforework.setEmployercity(form.getEmployercity());

		if (Util.isEmpty(form.getEmployeraddressen())) {
			beforework.setEmployeraddressen(translationHandle(2, translate(form.getEmployeraddress())));
		} else {
			if (Util.eq(form.getEmployeraddress(), beforework.getEmployeraddress())) {
				beforework.setEmployeraddressen(translationHandle(2, form.getEmployeraddressen()));
			} else {
				if (Util.eq(form.getEmployeraddressen(), beforework.getEmployeraddressen())) {
					beforework.setEmployeraddressen(translationHandle(2, translate(form.getEmployeraddress())));
				} else {
					beforework.setEmployeraddressen(translationHandle(2, form.getEmployeraddressen()));
				}
			}
		}

		beforework.setEmployeraddress(form.getEmployeraddress());

		beforework.setEmploystartdate(form.getEmploystartdate());
		beforework.setEmployenddate(form.getEmployenddate());

		if (Util.isEmpty(form.getJobtitleen())) {
			beforework.setJobtitleen(translationHandle(1, translate(form.getJobtitle())));
		} else {
			if (Util.eq(form.getJobtitle(), beforework.getJobtitle())) {
				beforework.setJobtitleen(translationHandle(1, form.getJobtitleen()));
			} else {
				if (Util.eq(form.getJobtitleen(), beforework.getJobtitleen())) {
					beforework.setJobtitleen(translationHandle(1, translate(form.getJobtitle())));
				} else {
					beforework.setJobtitleen(translationHandle(1, form.getJobtitleen()));
				}
			}
		}

		beforework.setJobtitle(form.getJobtitle());

		if (Util.isEmpty(form.getPreviousdutyen())) {
			beforework.setPreviousdutyen(translationHandle(4, translate(form.getPreviousduty())));
		} else {
			if (Util.eq(form.getPreviousduty(), beforework.getPreviousduty())) {
				beforework.setPreviousdutyen(translationHandle(4, form.getPreviousdutyen()));
			} else {
				if (Util.eq(form.getPreviousdutyen(), beforework.getPreviousdutyen())) {
					beforework.setPreviousdutyen(translationHandle(4, translate(form.getPreviousduty())));
				} else {
					beforework.setPreviousdutyen(translationHandle(4, form.getPreviousdutyen()));
				}
			}
		}

		beforework.setPreviousduty(form.getPreviousduty());

		//英文
		beforework.setEmployertelephoneen(translationHandle(3, form.getEmployertelephone()));
		beforework.setEmployercountryen(form.getEmployercountry());

		//中文翻译成拼音并大写工具
		PinyinTool tool = new PinyinTool();
		if (!Util.isEmpty(form.getEmployerprovince())) {
			String issuedplace = form.getEmployerprovince();
			if (Util.eq("内蒙古", issuedplace) || Util.eq("内蒙古自治区", issuedplace)) {
				beforework.setEmployerprovinceen("NEI MONGOL");
			} else if (Util.eq("陕西", issuedplace) || Util.eq("陕西省", issuedplace)) {
				beforework.setEmployerprovinceen("SHAANXI");
			} else {
				if (issuedplace.endsWith("省") || issuedplace.endsWith("市")) {
					issuedplace = issuedplace.substring(0, issuedplace.length() - 1);
				}
				if (issuedplace.endsWith("自治区")) {
					issuedplace = issuedplace.substring(0, issuedplace.length() - 3);
				}
				if (issuedplace.endsWith("区")) {
					issuedplace = issuedplace.substring(0, issuedplace.length() - 1);
				}
				try {
					beforework.setEmployerprovinceen(tool.toPinYin(issuedplace, "", Type.UPPERCASE));
				} catch (BadHanyuPinyinOutputFormatCombination e1) {
					e1.printStackTrace();
				}
			}

		}

		if (!Util.isEmpty(form.getEmployercity())) {
			String issuedplace = form.getEmployercity();
			try {
				beforework.setEmployercityen(tool.toPinYin(issuedplace, "", Type.UPPERCASE));
			} catch (BadHanyuPinyinOutputFormatCombination e1) {
				e1.printStackTrace();
			}

		}

		//beforework.setEmployerprovinceen(translate(form.getEmployerprovince()));
		//beforework.setEmployercityen(translate(form.getEmployercity()));

		beforework.setEmploystartdateen(form.getEmploystartdate());
		beforework.setEmployenddateen(form.getEmployenddate());
		dbDao.update(beforework);
		return null;
	}

	/**
	 * 以前的教育信息保存
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateBeforeeducation(WorkandeducateinfoUSForm form) {
		Integer staffid = form.getStaffid();
		TAppStaffBeforeeducationEntity beforeeducate = dbDao.fetch(TAppStaffBeforeeducationEntity.class,
				Cnd.where("staffid", "=", staffid));
		if (Util.isEmpty(beforeeducate)) {
			beforeeducate = new TAppStaffBeforeeducationEntity();
			beforeeducate.setStaffid(staffid);
			dbDao.insert(beforeeducate);
		}
		beforeeducate.setHighesteducation(form.getHighesteducation());

		if (Util.isEmpty(form.getInstitutionen())) {
			beforeeducate.setInstitutionen(translationHandle(1, translate(form.getInstitution())));
		} else {
			if (Util.eq(form.getInstitution(), beforeeducate.getInstitution())) {
				beforeeducate.setInstitutionen(translationHandle(1, form.getInstitutionen()));
			} else {
				if (Util.eq(form.getInstitutionen(), beforeeducate.getInstitutionen())) {
					beforeeducate.setInstitutionen(translationHandle(1, translate(form.getInstitution())));
				} else {
					beforeeducate.setInstitutionen(translationHandle(1, form.getInstitutionen()));
				}
			}
		}

		beforeeducate.setInstitution(form.getInstitution());

		if (Util.isEmpty(form.getCourseen())) {//如果英文为空，直接翻译
			beforeeducate.setCourseen(translate(form.getCourse()));
		} else {
			if (Util.eq(form.getCourse(), beforeeducate.getCourse())) {//英文不为空，中文相同说明没变，直接存英文
				beforeeducate.setCourseen(form.getCourseen());
			} else {
				if (Util.eq(form.getCourseen(), beforeeducate.getCourseen())) {//中文不同，英文相同，说明没有翻译过来，需要翻译
					beforeeducate.setCourseen(translate(form.getCourse()));
				} else {
					beforeeducate.setCourseen(form.getCourseen());//中文、英文都不一样，直接存英文
				}
			}
		}

		beforeeducate.setCourse(form.getCourse());

		beforeeducate.setInstitutioncountry(form.getInstitutioncountry());
		beforeeducate.setInstitutionprovince(form.getInstitutionprovince());
		beforeeducate.setInstitutioncity(form.getInstitutioncity());

		if (Util.isEmpty(form.getInstitutionaddressen())) {
			beforeeducate.setInstitutionaddressen(translationHandle(2, translate(form.getInstitutionaddress())));
		} else {
			if (Util.eq(form.getInstitutionaddress(), beforeeducate.getInstitutionaddress())) {
				beforeeducate.setInstitutionaddressen(translationHandle(2, form.getInstitutionaddressen()));
			} else {
				if (Util.eq(form.getInstitutionaddressen(), beforeeducate.getInstitutionaddressen())) {
					beforeeducate
							.setInstitutionaddressen(translationHandle(2, translate(form.getInstitutionaddress())));
				} else {
					beforeeducate.setInstitutionaddressen(translationHandle(2, form.getInstitutionaddressen()));
				}
			}
		}

		beforeeducate.setInstitutionaddress(form.getInstitutionaddress());

		beforeeducate.setCoursestartdate(form.getCoursestartdate());
		beforeeducate.setCourseenddate(form.getCourseenddate());
		//英文
		beforeeducate.setHighesteducationen(form.getHighesteducation());
		beforeeducate.setInstitutioncountryen(form.getInstitutioncountry());

		//中文翻译成拼音并大写工具
		PinyinTool tool = new PinyinTool();
		if (!Util.isEmpty(form.getInstitutionprovince())) {
			String issuedplace = form.getInstitutionprovince();
			if (Util.eq("内蒙古", issuedplace) || Util.eq("内蒙古自治区", issuedplace)) {
				beforeeducate.setInstitutionprovinceen("NEI MONGOL");
			} else if (Util.eq("陕西", issuedplace) || Util.eq("陕西省", issuedplace)) {
				beforeeducate.setInstitutionprovinceen("SHAANXI");
			} else {
				if (issuedplace.endsWith("省") || issuedplace.endsWith("市")) {
					issuedplace = issuedplace.substring(0, issuedplace.length() - 1);
				}
				if (issuedplace.endsWith("自治区")) {
					issuedplace = issuedplace.substring(0, issuedplace.length() - 3);
				}
				if (issuedplace.endsWith("区")) {
					issuedplace = issuedplace.substring(0, issuedplace.length() - 1);
				}
				try {
					beforeeducate.setInstitutionprovinceen(tool.toPinYin(issuedplace, "", Type.UPPERCASE));
				} catch (BadHanyuPinyinOutputFormatCombination e1) {
					e1.printStackTrace();
				}
			}

		}

		if (!Util.isEmpty(form.getInstitutioncity())) {
			String issuedplace = form.getInstitutioncity();
			try {
				beforeeducate.setInstitutioncityen(tool.toPinYin(issuedplace, "", Type.UPPERCASE));
			} catch (BadHanyuPinyinOutputFormatCombination e1) {
				e1.printStackTrace();
			}

		}

		//beforeeducate.setInstitutionprovinceen(translate(form.getInstitutionprovince()));
		//beforeeducate.setInstitutioncityen(translate(form.getInstitutioncity()));

		beforeeducate.setCoursestartdateen(form.getCoursestartdate());
		beforeeducate.setCourseenddateen(form.getCourseenddate());
		dbDao.update(beforeeducate);
		return null;
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
		System.out.println("旅行信息回显的staffid:" + staffid);
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			Map<String, Object> result = Maps.newHashMap();
			SimpleDateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
			//同行人信息
			TAppStaffTravelcompanionEntity travelcompanion = dbDao.fetch(TAppStaffTravelcompanionEntity.class,
					Cnd.where("staffid", "=", staffid));
			result.put("istravelwithother", travelcompanion.getIstravelwithother());
			List<TAppStaffCompanioninfoEntity> companioninfoList = dbDao.query(TAppStaffCompanioninfoEntity.class,
					Cnd.where("staffid", "=", staffid), null);
			result.put("companioninfoList", companioninfoList);
			//以前的美国旅游信息

			TAppStaffPrevioustripinfoEntity previoustripinfo = dbDao.fetch(TAppStaffPrevioustripinfoEntity.class,
					Cnd.where("staffid", "=", staffid));
			Map<String, String> previoustripinfoMap = MapUtil.obj2Map(previoustripinfo);

			if (Util.isEmpty(previoustripinfo.getCostpayer())) {
				previoustripinfoMap.put("costpayer", "1");
			}
			if (!Util.isEmpty(previoustripinfo.getIssueddate())) {
				previoustripinfoMap.put("issueddate", format.format(previoustripinfo.getIssueddate()));
			} else {
				previoustripinfoMap.put("issueddate", "");
			}
			if (!Util.isEmpty(previoustripinfo.getIssueddateen())) {
				previoustripinfoMap.put("issueddateen", format.format(previoustripinfo.getIssueddateen()));
			} else {
				previoustripinfoMap.put("issueddateen", "");
			}
			result.put("previoustripinfo", previoustripinfoMap);

			/*TAppStaffPrevioustripinfoEntity previoustripinfo = dbDao.fetch(TAppStaffPrevioustripinfoEntity.class,
					Cnd.where("staffid", "=", staffid));
			if (Util.isEmpty(previoustripinfo.getCostpayer())) {
				previoustripinfo.setCostpayer(1);
			}

			result.put("previoustripinfo", previoustripinfo);
			if (!Util.isEmpty(previoustripinfo.getIssueddate())) {
				result.put("issueddate", format.format(previoustripinfo.getIssueddate()));
			} else {
				result.put("issueddate", "");
			}*/
			//去过美国信息
			ArrayList<Map<String, String>> resultList = new ArrayList<>();
			List<TAppStaffGousinfoEntity> gousinfoList = dbDao.query(TAppStaffGousinfoEntity.class,
					Cnd.where("staffid", "=", staffid), null);
			for (TAppStaffGousinfoEntity gousinfo : gousinfoList) {
				Map<String, String> gousinfoMap = MapUtil.obj2Map(gousinfo);
				if (!Util.isEmpty(gousinfo.getArrivedate())) {
					gousinfoMap.put("arrivedate", format.format(gousinfo.getArrivedate()));
				} else {
					gousinfoMap.put("arrivedate", "");
				}
				if (!Util.isEmpty(gousinfo.getArrivedateen())) {
					gousinfoMap.put("arrivedateen", format.format(gousinfo.getArrivedateen()));
				} else {
					gousinfoMap.put("arrivedateen", "");
				}
				resultList.add(gousinfoMap);

			}
			result.put("gousinfo", resultList);

			/*TAppStaffGousinfoEntity gousinfo = dbDao.fetch(TAppStaffGousinfoEntity.class,
					Cnd.where("staffid", "=", staffid));
			if (!Util.isEmpty(gousinfo)) {
				result.put("gousinfo", gousinfo);
			} else {
				TAppStaffGousinfoEntity newgousinfo = new TAppStaffGousinfoEntity();
				result.put("gousinfo", newgousinfo);

			}*/

			//美国的驾照信息
			TAppStaffDriverinfoEntity driverinfo = dbDao.fetch(TAppStaffDriverinfoEntity.class,
					Cnd.where("staffid", "=", staffid));

			if (!Util.isEmpty(driverinfo)) {
				result.put("driverinfo", driverinfo);
			} else {
				TAppStaffDriverinfoEntity newdriverinfo = new TAppStaffDriverinfoEntity();
				result.put("driverinfo", newdriverinfo);
			}

			//是否有出境记录
			TAppStaffWorkEducationTrainingEntity workeducation = dbDao.fetch(
					TAppStaffWorkEducationTrainingEntity.class, Cnd.where("staffid", "=", staffid));
			if (Util.isEmpty(workeducation.getIstraveledanycountry())) {
				result.put("istraveledanycountry", 2);
			} else {
				result.put("istraveledanycountry", workeducation.getIstraveledanycountry());
			}
			//出境记录
			List<TAppStaffGocountryEntity> gocountry = dbDao.query(TAppStaffGocountryEntity.class,
					Cnd.where("staffid", "=", staffid), null);
			result.put("gocountry", gocountry);

			//国家下拉
			List<TCountryRegionEntity> gocountryFiveList = dbDao.query(TCountryRegionEntity.class, null, null);
			result.put("gocountryfivelist", gocountryFiveList);
			result.put("timeunitstatusenum", EnumUtil.enum2(NewTimeUnitStatusEnum.class));
			result.put("usstatesenum", EnumUtil.enum2(VisaUSStatesEnum.class));
			result.put("emigrationreasonenumenum", EnumUtil.enum2(EmigrationreasonEnum.class));
			result.put("travelcompanionrelationshipenum", EnumUtil.enum2(TravelCompanionRelationshipEnum.class));
			System.out.println("旅行信息回显result");
			return JuYouResult.ok(result);
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
		System.out.println("旅行信息保存form:" + form);
		String encode = form.getEncode();
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			System.out.println("cookie正常，正式保存开始");
			Integer staffid = form.getStaffid();
			//是否有同行人
			System.out.println("开始保存同行人信息");
			TAppStaffTravelcompanionEntity travelcompanion = dbDao.fetch(TAppStaffTravelcompanionEntity.class,
					Cnd.where("staffid", "=", staffid));
			travelcompanion.setIstravelwithother(form.getIstravelwithother());
			travelcompanion.setIstravelwithotheren(form.getIstravelwithother());
			dbDao.update(travelcompanion);

			//同行人信息
			List<TAppStaffCompanioninfoEntity> companionList_old = dbDao.query(TAppStaffCompanioninfoEntity.class,
					Cnd.where("staffid", "=", staffid), null);
			if (form.getIstravelwithother() == 2) {//没有同行人时，需要清掉同行人信息
				if (!Util.isEmpty(companionList_old)) {
					dbDao.delete(companionList_old);
				}
			} else {
				String companioninfoList = form.getCompanioninfoList();
				List<TAppStaffCompanioninfoEntity> companionList_New = JsonUtil.fromJsonAsList(
						TAppStaffCompanioninfoEntity.class, companioninfoList);
				dbDao.updateRelations(companionList_old, companionList_New);
			}

			System.out.println("同行人信息保存完毕");

			//以前的美国旅游信息
			updatePrevioustripinfo(form);
			System.out.println("以前的美国旅游信息保存完毕");

			//去过美国信息
			TAppStaffGousinfoEntity gousinfo = dbDao.fetch(TAppStaffGousinfoEntity.class,
					Cnd.where("staffid", "=", staffid));
			//美国驾照信息
			TAppStaffDriverinfoEntity driverinfo = dbDao.fetch(TAppStaffDriverinfoEntity.class,
					Cnd.where("staffid", "=", staffid));

			if (form.getHasbeeninus() == 1) {//去过美国，则添加或者修改
				if (Util.isEmpty(gousinfo)) {
					gousinfo = new TAppStaffGousinfoEntity();
					gousinfo.setStaffid(staffid);
					dbDao.insert(gousinfo);
				}
				if (!Util.isEmpty(form.getArrivedate())) {
					gousinfo.setArrivedate(form.getArrivedate());
					gousinfo.setArrivedateen(form.getArrivedate());
				} else {
					gousinfo.setArrivedate(null);
					gousinfo.setArrivedateen(null);
				}
				gousinfo.setDateunit(form.getDateunit());
				gousinfo.setDateuniten(form.getDateunit());
				gousinfo.setStaydays(form.getStaydays());
				gousinfo.setStaydaysen(form.getStaydays());
				dbDao.update(gousinfo);

				if (form.getHasdriverlicense() == 1) {//有美国驾照，添加或者修改
					if (Util.isEmpty(driverinfo)) {
						driverinfo = new TAppStaffDriverinfoEntity();
						driverinfo.setStaffid(staffid);
						dbDao.insert(driverinfo);
					}
					driverinfo.setDriverlicensenumber(form.getDriverlicensenumber());
					driverinfo.setWitchstateofdriver(form.getWitchstateofdriver());
					driverinfo.setDriverlicensenumberen(form.getDriverlicensenumber());
					driverinfo.setWitchstateofdriveren(form.getWitchstateofdriver());
					dbDao.update(driverinfo);
				} else {
					if (!Util.isEmpty(driverinfo)) {
						dbDao.delete(driverinfo);
					}
				}

			} else {
				if (!Util.isEmpty(gousinfo)) {
					dbDao.delete(gousinfo);
				}
				if (!Util.isEmpty(driverinfo)) {
					dbDao.delete(driverinfo);
				}
			}
			System.out.println("去过美国信息保存完毕");

			/*//美国驾照信息
			TAppStaffDriverinfoEntity driverinfo = dbDao.fetch(TAppStaffDriverinfoEntity.class,
					Cnd.where("staffid", "=", staffid));
			if (form.getHasdriverlicense() == 1) {//有美国驾照，添加或者修改
				if (Util.isEmpty(driverinfo)) {
					driverinfo = new TAppStaffDriverinfoEntity();
					driverinfo.setStaffid(staffid);
					dbDao.insert(driverinfo);
				}
				driverinfo.setDriverlicensenumber(form.getDriverlicensenumber());
				driverinfo.setWitchstateofdriver(form.getWitchstateofdriver());
				driverinfo.setDriverlicensenumberen(form.getDriverlicensenumber());
				driverinfo.setWitchstateofdriveren(form.getWitchstateofdriver());
				dbDao.update(driverinfo);
			} else {
				if (!Util.isEmpty(driverinfo)) {
					dbDao.delete(driverinfo);
				}
			}
			System.out.println("美国驾照信息保存完毕");*/

			//是否有出境记录
			TAppStaffWorkEducationTrainingEntity workeducation = dbDao.fetch(
					TAppStaffWorkEducationTrainingEntity.class, Cnd.where("staffid", "=", staffid));
			workeducation.setIstraveledanycountry(form.getIstraveledanycountry());
			workeducation.setIstraveledanycountryen(form.getIstraveledanycountry());
			System.out.println(workeducation);
			dbDao.update(workeducation);

			//出境记录
			List<TAppStaffGocountryEntity> countryList_old = dbDao.query(TAppStaffGocountryEntity.class,
					Cnd.where("staffid", "=", staffid), null);
			if (form.getIstraveledanycountry() == 2) {
				if (!Util.isEmpty(countryList_old)) {
					dbDao.delete(countryList_old);
				}
			} else {
				String gocountry = form.getGocountry();
				List<TAppStaffGocountryEntity> countryList_New = JsonUtil.fromJsonAsList(
						TAppStaffGocountryEntity.class, gocountry);
				dbDao.updateRelations(countryList_old, countryList_New);
			}

			System.out.println("出境记录信息保存完毕");
			return JuYouResult.ok();
		}
	}

	/**
	 * 以前的美国旅游信息保存
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updatePrevioustripinfo(TravelinfoUSForm form) {
		Integer staffid = form.getStaffid();
		TAppStaffPrevioustripinfoEntity previoustripinfo = dbDao.fetch(TAppStaffPrevioustripinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		previoustripinfo.setCostpayer(form.getCostpayer());
		previoustripinfo.setHasbeeninus(form.getHasbeeninus());

		if (form.getHasbeeninus() == 2) {
			previoustripinfo.setHasdriverlicense(2);
			previoustripinfo.setHasbeeninusen(2);
		} else {
			previoustripinfo.setHasdriverlicense(form.getHasdriverlicense());
			previoustripinfo.setHasdriverlicenseen(form.getHasdriverlicense());
		}

		previoustripinfo.setIsissuedvisa(form.getIsissuedvisa());
		if (form.getIsissuedvisa() == 1) {
			previoustripinfo.setIssueddate(form.getIssueddate());
			previoustripinfo.setIssueddateen(form.getIssueddate());
			previoustripinfo.setVisanumber(form.getVisanumber());
			previoustripinfo.setVisanumberen(form.getVisanumber());
		} else {
			previoustripinfo.setIssueddate(null);
			previoustripinfo.setIssueddateen(null);
			previoustripinfo.setVisanumber("");
			previoustripinfo.setVisanumberen("");
		}

		if (form.getIsrefused() == 1) {
			if (Util.isEmpty(form.getRefusedexplainen())) {//如果没有英文，则直接翻译
				previoustripinfo.setRefusedexplainen(translate(form.getRefusedexplain()));
			} else {
				if (Util.eq(form.getRefusedexplain(), previoustripinfo.getRefusedexplain())) {//英文不为空，中文不变，直接保存英文
					previoustripinfo.setRefusedexplainen(form.getRefusedexplainen());
				} else {//英文不为空，中文改变，分两种情况：一种英文不变，说明没翻译，需要翻译，另一种英文改变，直接保存英文
					if (Util.eq(form.getRefusedexplainen(), previoustripinfo.getRefusedexplainen())) {
						previoustripinfo.setRefusedexplainen(translate(form.getRefusedexplain()));
					} else {
						previoustripinfo.setRefusedexplainen(form.getRefusedexplainen());
					}
				}
			}

			previoustripinfo.setRefusedexplain(form.getRefusedexplain());
		} else {
			previoustripinfo.setRefusedexplain("");
			previoustripinfo.setRefusedexplainen("");
		}

		if (form.getIsfiledimmigrantpetition() == 1) {
			previoustripinfo.setEmigrationreason(form.getEmigrationreason());
			previoustripinfo.setEmigrationreasonen(form.getEmigrationreason());

			if (Util.isEmpty(form.getImmigrantpetitionexplainen())) {
				previoustripinfo.setImmigrantpetitionexplainen(translate(form.getImmigrantpetitionexplain()));
			} else {
				if (Util.eq(form.getImmigrantpetitionexplain(), previoustripinfo.getImmigrantpetitionexplain())) {
					previoustripinfo.setImmigrantpetitionexplainen(form.getImmigrantpetitionexplainen());
				} else {
					if (Util.eq(form.getImmigrantpetitionexplainen(), previoustripinfo.getImmigrantpetitionexplainen())) {
						previoustripinfo.setImmigrantpetitionexplainen(translate(form.getImmigrantpetitionexplain()));
					} else {
						previoustripinfo.setImmigrantpetitionexplainen(form.getImmigrantpetitionexplainen());
					}
				}
			}

			previoustripinfo.setImmigrantpetitionexplain(form.getImmigrantpetitionexplain());
		} else {
			previoustripinfo.setEmigrationreason(0);
			previoustripinfo.setEmigrationreasonen(0);
			previoustripinfo.setImmigrantpetitionexplain("");
			previoustripinfo.setImmigrantpetitionexplainen("");
		}

		previoustripinfo.setIsapplyingsametypevisa(form.getIsapplyingsametypevisa());
		previoustripinfo.setIstenprinted(form.getIstenprinted());
		previoustripinfo.setIslost(form.getIslost());
		previoustripinfo.setIscancelled(form.getIscancelled());
		previoustripinfo.setIsrefused(form.getIsrefused());
		//previoustripinfo.setRefusedexplain(form.getRefusedexplain());
		previoustripinfo.setIsfiledimmigrantpetition(form.getIsfiledimmigrantpetition());
		//previoustripinfo.setEmigrationreason(form.getEmigrationreason());
		//previoustripinfo.setImmigrantpetitionexplain(form.getImmigrantpetitionexplain());
		//英文
		previoustripinfo.setCostpayeren(form.getCostpayer());
		previoustripinfo.setHasbeeninusen(form.getHasbeeninus());
		previoustripinfo.setIsissuedvisaen(form.getIsissuedvisa());
		previoustripinfo.setIsapplyingsametypevisaen(form.getIsapplyingsametypevisa());
		previoustripinfo.setIstenprinteden(form.getIstenprinted());
		previoustripinfo.setIslosten(form.getIslost());
		previoustripinfo.setIscancelleden(form.getIscancelled());
		previoustripinfo.setIsrefuseden(form.getIsrefused());
		previoustripinfo.setIsfiledimmigrantpetitionen(form.getIsfiledimmigrantpetition());
		dbDao.update(previoustripinfo);
		return null;
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
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			TAppStaffBasicinfoEntity basicinfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, staffid);
			basicinfo.setMarrystatus(marrystatus);
			basicinfo.setMarrystatusen(marrystatus);
			dbDao.update(basicinfo);
			return null;
		}
	}

	/**
	 * 婚姻状况回显
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param encode
	 * @param staffid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toMarrystatus(String encode, int staffid) {
		String openid = redisDao.get(encode);
		if (Util.isEmpty(openid)) {
			return -1;
		} else {
			Map<String, Object> result = Maps.newHashMap();
			TAppStaffBasicinfoEntity basicinfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, staffid);
			result.put("marrystatus", basicinfo.getMarrystatus());
			return result;
		}
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
	 * 特殊字符处理
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param translation
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public String translationHandle(int type, String translation) {
		StringBuffer result = new StringBuffer();
		if (!Util.isEmpty(translation)) {
			//translation = translate(translation);
			if (type == 1) {//工作教育信息单位、学校名称特殊字符处理
				//先把连续空格转成单空格
				translation = translation.replaceAll("\\s+", " ").trim();
				//去掉不合格的字符
			} else if (type == 2) {//地址

			}
			for (int i = 0; i < translation.length(); i++) {
				char character = translation.charAt(i);
				boolean isLegal = isLegal(type, character);
				if (isLegal) {
					result.append(character);
				}
			}
		}

		return result.toString();
	}

	/**
	 * 正则表达式匹配特殊字符
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param character
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public boolean isLegal(int type, char character) {
		boolean isLegal = false;

		if (type == 1) {//工作教育信息单位、学校名称,只允许数字，字母，-,',&和单空格
			isLegal = Pattern.compile("^[ A-Za-z0-9-&']+$").matcher(String.valueOf(character)).find();
		} else if (type == 2) {//地址特殊字符处理
			isLegal = Pattern.compile("^[ A-Za-z0-9-&'#$*%;!,?.()<>@^]+$").matcher(String.valueOf(character)).find();
		} else if (type == 3) {//电话号码特殊字符处理，只允许数字和+
			isLegal = Pattern.compile("^[0-9+]+$").matcher(String.valueOf(character)).find();
		}
		return isLegal;
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
		/*String result = "";
		if (!Util.isEmpty(str)) {
			try {
				result = TranslateUtil.translate(str, "en");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("没有内容你让我翻译什么啊，神经病啊☺");
		}
		return result;*/

		String result = "";
		if (!Util.isEmpty(str)) {
			try {
				TransApi api = new TransApi();
				result = api.getTransResult(str, "auto", "en");
				System.out.println(result);
				org.json.JSONObject resultStr = new org.json.JSONObject(result);
				try {
					JSONArray resultArray = (JSONArray) resultStr.get("trans_result");
					List<BaidutranslateEntity> resultList = com.alibaba.fastjson.JSONObject.parseArray(
							resultArray.toString(), BaidutranslateEntity.class);

					result = resultList.get(0).getDst();
					System.out.println("翻译内容为：" + str + ",翻译结果为result：" + result);
				} catch (Exception e) {
					result = "";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("没有内容你让我翻译什么啊，神经病啊o(╥﹏╥)o");
		}
		return result;
	}
}
