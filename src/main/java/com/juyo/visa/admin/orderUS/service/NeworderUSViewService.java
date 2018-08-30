/**
 * SaleViewService.java
 * com.juyo.visa.admin.sale.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.orderUS.service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.http.HttpResponse;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.mail.service.MailService;
import com.juyo.visa.admin.mobile.form.BasicinfoUSForm;
import com.juyo.visa.admin.mobile.form.FamilyinfoUSForm;
import com.juyo.visa.admin.mobile.form.PassportinfoUSForm;
import com.juyo.visa.admin.mobile.form.TravelinfoUSForm;
import com.juyo.visa.admin.mobile.form.WorkandeducateinfoUSForm;
import com.juyo.visa.admin.weixinToken.service.WeXinTokenViewService;
import com.juyo.visa.common.base.JuYouResult;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.MarryStatusEnum;
import com.juyo.visa.common.enums.visaProcess.EmigrationreasonEnum;
import com.juyo.visa.common.enums.visaProcess.ImmediateFamilyMembersRelationshipEnum;
import com.juyo.visa.common.enums.visaProcess.TravelCompanionRelationshipEnum;
import com.juyo.visa.common.enums.visaProcess.VisaCareersEnum;
import com.juyo.visa.common.enums.visaProcess.VisaFamilyInfoEnum;
import com.juyo.visa.common.enums.visaProcess.VisaHighestEducationEnum;
import com.juyo.visa.common.enums.visaProcess.VisaSpouseContactAddressEnum;
import com.juyo.visa.common.enums.visaProcess.YesOrNoEnum;
import com.juyo.visa.common.ocr.HttpUtils;
import com.juyo.visa.common.util.HttpUtil;
import com.juyo.visa.common.util.PinyinTool;
import com.juyo.visa.common.util.PinyinTool.Type;
import com.juyo.visa.common.util.SpringContextUtil;
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
import com.juyo.visa.entities.TCountryRegionEntity;
import com.juyo.visa.entities.TOrderUsEntity;
import com.juyo.visa.entities.TStateUsEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.websocket.SimpleSendInfoWSHandler;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.redis.RedisDao;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 	 
 */
@IocBean
public class NeworderUSViewService extends BaseService<TOrderUsEntity> {

	@Inject
	private RedisDao redisDao;

	@Inject
	private AutofillService autofillService;

	@Inject
	private MailService mailService;

	@Inject
	private WeXinTokenViewService weXinTokenViewService;

	@Inject
	private UploadService qiniuUploadService;//文件上传

	private final static String SMS_SIGNATURE = "【优悦签】";
	private final static Integer US_YUSHANG_COMID = 68;
	//活动id，默认为1
	private final static Integer EVENTID = 1;
	private final static Integer DEFAULT_IS_NO = YesOrNoEnum.NO.intKey();
	private final static Integer DEFAULT_SELECT = IsYesOrNoEnum.NO.intKey();

	//美国接口相关
	private final static String ENCODINGAESKEY = "jllZTM3ZWEzZGI1NGQ5NGI3MTc4NDNhNzAzODE5NTYt";
	private final static String TOKEN = "ODBiOGIxNDY4NjdlMzc2Yg==";
	private final static String APPID = "jhhMThiZjM1ZGQ2Y";

	/*//订单列表页连接websocket的地址
	private static final String USLIST_WEBSPCKET_ADDR = "uslistwebsocket";

	private USListWSHandler uslistwebsocket = (USListWSHandler) SpringContextUtil.getBean("usListHander",
			USListWSHandler.class);*/

	//图片上传后连接websocket的地址
	private static final String SEND_INFO_WEBSPCKET_ADDR = "simplesendinfosocket";
	private SimpleSendInfoWSHandler simplesendinfosocket = (SimpleSendInfoWSHandler) SpringContextUtil.getBean(
			"simplesendinfosocket", SimpleSendInfoWSHandler.class);
	//微信生成小程序码接口
	private static String WX_B_CODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN"; //不限次数 scene长度为32个字符

	/**
	 * 跳转到拍摄资料
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param staffid
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object tofillimage(int staffid, HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		String localAddr = request.getServerName();
		request.getServerName();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		result.put("localAddr", serverName);
		result.put("localPort", serverPort);
		result.put("websocketaddr", SEND_INFO_WEBSPCKET_ADDR);

		//生成二维码
		String qrCode = dataUpload(staffid, request);
		result.put("qrCode", qrCode);

		//图片回显
		List<TAppStaffCredentialsEntity> credentialsList = dbDao.query(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		for (TAppStaffCredentialsEntity tAppStaffCredentialsEntity : credentialsList) {
			TAppStaffCredentialsEntity credentials = new TAppStaffCredentialsEntity();
			credentials.setType(tAppStaffCredentialsEntity.getType());
			credentials.setUrl(tAppStaffCredentialsEntity.getUrl());
			result.put(String.valueOf(tAppStaffCredentialsEntity.getType()), credentials);
		}
		return JuYouResult.ok(result);
	}

	/**
	 * 生成小程序码
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param staffid
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public String dataUpload(int staffid, HttpServletRequest request) {
		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		String page = "pages/Japan/upload/index/index";
		String scene = "";
		//因为小程序参数最长为32，所以参数尽量简化，a是人员id
		scene = "a=" + staffid;
		System.out.println("scene:" + scene + "--------------");
		String accessToken = (String) getAccessToken();
		System.out.println("accessToken:" + accessToken + "=================");
		String url = createBCode(accessToken, page, scene);
		System.out.println("url:" + url);
		return url;
	}

	//获取accessToken
	public Object getAccessToken() {
		String WX_APPID = "wx17bf0dde91fec324";
		String WX_APPSECRET = "6cc0aa2089c4ba020297fb23af31081a";
		String WX_TOKENKEY = "WX_ACCESS_TOKEN_2018";

		String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
		String requestUrl = accessTokenUrl.replace("APPID", WX_APPID).replace("APPSECRET", WX_APPSECRET);
		com.alibaba.fastjson.JSONObject result = HttpUtil.doGet(requestUrl);
		//redis中设置 access_token
		accessTokenUrl = result.getString("access_token");

		return accessTokenUrl;
	}

	public String createBCode(String accessToken, String page, String scene) {
		String url = WX_B_CODE_URL.replace("ACCESS_TOKEN", accessToken);
		Map<String, Object> param = new HashMap<>();
		param.put("path", page);
		param.put("scene", scene);
		param.put("width", "100");
		param.put("auto_color", false);
		Map<String, Object> line_color = new HashMap<>();
		line_color.put("r", 0);
		line_color.put("g", 0);
		line_color.put("b", 0);
		param.put("line_color", line_color);
		com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(JSON.toJSONString(param));
		System.out.println("json:" + json.toString());
		InputStream inputStream = toPostRequest(json.toString(), accessToken);

		String imgurl = CommonConstants.IMAGES_SERVER_ADDR + qiniuUploadService.uploadImage(inputStream, "", "");
		return imgurl;
	}

	//发送POST请求
	public InputStream toPostRequest(String json, String accessToken) {
		String host = "https://api.weixin.qq.com";
		String path = "/wxa/getwxacodeunlimit?access_token=" + accessToken;
		String method = "POST";
		String entityStr = "";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		HttpResponse response;
		InputStream inputStream = null;
		try {
			response = HttpUtils.doPost(host, path, method, headers, querys, json);
			inputStream = response.getEntity().getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	/**
	 * 跳转到基本信息
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param staffid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toBasicinfo(int staffid) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("staffid", staffid);
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		List<TAppStaffCredentialsEntity> photourls = dbDao.query(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "in", "3,13,14"), null);
		result.put("photourls", photourls);
		result.put("marrystatusenum", EnumUtil.enum2(MarryStatusEnum.class));
		TAppStaffBasicinfoEntity basicinfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, staffid);
		result.put("basicinfo", basicinfo);
		//日期处理
		if (!Util.isEmpty(basicinfo.getBirthday())) {
			result.put("birthday", sdf.format(basicinfo.getBirthday()));
		}
		//姓名拼音处理
		if (!Util.isEmpty(basicinfo.getFirstnameen())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(basicinfo.getFirstnameen());
			result.put("firstnameen", sb.toString());
		}
		if (!Util.isEmpty(basicinfo.getLastnameen())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(basicinfo.getLastnameen());
			result.put("lastnameen", sb.toString());
		}
		return JuYouResult.ok(result);
	}

	/**
	 * 保存基本信息
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveBasicinfo(BasicinfoUSForm form) {
		Integer staffid = form.getStaffid();
		//基本信息
		updateBasicinfo(form);

		//护照信息
		TAppStaffPassportEntity passportinfo = dbDao.fetch(TAppStaffPassportEntity.class,
				Cnd.where("staffid", "=", staffid));
		passportinfo.setFirstname(form.getFirstname());
		passportinfo.setLastname(form.getLastname());
		if (!Util.isEmpty(form.getFirstnameen())) {
			passportinfo.setFirstnameen(form.getFirstnameen().substring(1));
		}
		passportinfo.setLastname(form.getLastname());
		if (!Util.isEmpty(form.getLastnameen())) {
			passportinfo.setLastnameen(form.getLastnameen().substring(1));
		}
		passportinfo.setSex(form.getSex());
		if (Util.eq("女", form.getSex())) {
			passportinfo.setSexen("F");
		} else {
			passportinfo.setSexen("M");
		}
		passportinfo.setBirthday(form.getBirthday());
		dbDao.update(passportinfo);
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
		Integer staffid = form.getStaffid();
		TAppStaffBasicinfoEntity basicinfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, staffid.longValue());
		basicinfo.setFirstname(form.getFirstname());
		if (!Util.isEmpty(form.getFirstnameen())) {
			basicinfo.setFirstnameen(form.getFirstnameen().substring(1));
		}
		basicinfo.setLastname(form.getLastname());
		if (!Util.isEmpty(form.getLastnameen())) {
			basicinfo.setLastnameen(form.getLastnameen().substring(1));
		}
		basicinfo.setSex(form.getSex());
		basicinfo.setTelephone(form.getTelephone());
		basicinfo.setEmail(form.getEmail());
		basicinfo.setCardId(form.getCardId());
		basicinfo.setProvince(form.getProvince());
		basicinfo.setCity(form.getCity());
		basicinfo.setDetailedaddress(form.getDetailedaddress());
		basicinfo.setDetailedaddressen(form.getDetailedaddressen());
		basicinfo.setMarrystatus(form.getMarrystatus());
		basicinfo.setMarryexplain(form.getMarryexplain());
		basicinfo.setBirthday(form.getBirthday());
		basicinfo.setNationality(form.getNationality());
		basicinfo.setHasothername(form.getHasothername());
		basicinfo.setOtherfirstname(form.getOtherfirstname());
		basicinfo.setOtherfirstnameen(form.getOtherfirstnameen());
		basicinfo.setOtherlastname(form.getOtherlastname());
		basicinfo.setOtherlastnameen(form.getOtherlastnameen());
		//英文
		basicinfo.setHasothernameen(form.getHasothernameen());
		basicinfo.setTelephoneen(form.getTelephone());
		basicinfo.setEmailen(form.getEmail());
		basicinfo.setCardIden(form.getCardId());
		basicinfo.setProvinceen(translate(form.getProvince()));
		basicinfo.setCityen(translate(form.getCity()));
		basicinfo.setMarrystatusen(form.getMarrystatus());
		basicinfo.setMarryexplainen(translate(form.getMarryexplain()));
		basicinfo.setNationalityen(translate(form.getNationality()));
		dbDao.update(basicinfo);
		return null;
	}

	/**
	 * 跳转到护照信息
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param staffid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toPassportinfo(int staffid) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("staffid", staffid);
		TAppStaffCredentialsEntity passportphoto = dbDao.fetch(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "=", 1));
		result.put("passporturl", passportphoto.getUrl());
		TAppStaffPassportEntity passportinfo = dbDao.fetch(TAppStaffPassportEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("passportinfo", passportinfo);
		//日期处理
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		if (!Util.isEmpty(passportinfo.getIssueddate())) {
			result.put("issueddate", sdf.format(passportinfo.getIssueddate()));
		}
		if (!Util.isEmpty(passportinfo.getValidenddate())) {
			result.put("validenddate", sdf.format(passportinfo.getValidenddate()));
		}

		return JuYouResult.ok(result);
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
	public Object savePassportinfo(PassportinfoUSForm form) {
		Integer staffid = form.getStaffid();
		TAppStaffPassportEntity passportinfo = dbDao.fetch(TAppStaffPassportEntity.class,
				Cnd.where("staffid", "=", staffid));
		passportinfo.setPassport(form.getPassport());
		passportinfo.setIssuedplace(form.getIssuedplace());
		passportinfo.setIssuedplaceen(form.getIssuedplaceen());
		passportinfo.setIssueddate(form.getIssueddate());
		passportinfo.setValidtype(form.getValidtype());
		passportinfo.setValidenddate(form.getValidenddate());
		passportinfo.setIssuedorganization(form.getIssuedorganization());
		passportinfo.setIssuedorganizationen(form.getIssuedorganizationen());
		passportinfo.setIslostpassport(form.getIslostpassport());
		passportinfo.setIsrememberpassportnum(form.getIsrememberpassportnum());
		passportinfo.setLostpassportnum(form.getLostpassportnum());

		//英文
		//中文翻译成拼音并大写工具
		PinyinTool tool = new PinyinTool();
		try {
			passportinfo.setIssuedplaceen("/" + tool.toPinYin(form.getIssuedplace(), "", Type.UPPERCASE));
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		passportinfo.setIslostpassporten(form.getIslostpassport());
		passportinfo.setIsrememberpassportnumen(form.getIsrememberpassportnum());
		passportinfo.setLostpassportnumen(form.getLostpassportnum());
		dbDao.update(passportinfo);
		return JuYouResult.ok();
	}

	/**
	 * 跳转到家庭信息
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param staffid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toFamilyinfo(int staffid) {
		Map<String, Object> result = Maps.newHashMap();
		TAppStaffFamilyinfoEntity familyinfo = dbDao.fetch(TAppStaffFamilyinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("familyinfo", familyinfo);
		//日期处理
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		if (!Util.isEmpty(familyinfo.getSpousebirthday())) {
			result.put("spousebirthday", sdf.format(familyinfo.getSpousebirthday()));
		}
		if (!Util.isEmpty(familyinfo.getFatherbirthday())) {
			result.put("fatherbirthday", sdf.format(familyinfo.getFatherbirthday()));
		}
		if (!Util.isEmpty(familyinfo.getMotherbirthday())) {
			result.put("motherbirthday", sdf.format(familyinfo.getMotherbirthday()));
		}
		//下拉处理
		//身份状态
		result.put("familyinfoenum", EnumUtil.enum2(VisaFamilyInfoEnum.class));
		//配偶联系地址
		result.put("spousecontactaddressenum", EnumUtil.enum2(VisaSpouseContactAddressEnum.class));
		//直系亲属---与你的关系
		result.put("immediaterelationshipenum", EnumUtil.enum2(ImmediateFamilyMembersRelationshipEnum.class));
		return JuYouResult.ok(result);
	}

	/**
	 * 保存家庭信息
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveFamilyinfo(FamilyinfoUSForm form) {
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
		updateOtherinfo(form);

		return JuYouResult.ok();
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
		familyinfo.setSpousecountry(form.getSpousecountry());
		familyinfo.setSpouseaddress(form.getSpouseaddress());
		familyinfo.setSpousecity(form.getSpousecity());
		familyinfo.setSpousenationality(form.getSpousenationality());
		//英文
		familyinfo.setSpousebirthdayen(form.getSpousebirthday());
		familyinfo.setSpouseaddressen(form.getSpouseaddress());
		familyinfo.setSpousecityen(translate(form.getSpousecity()));
		familyinfo.setSpousecountryen(form.getSpousecountry());
		familyinfo.setSpousenationalityen(form.getSpousenationality());
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
		familyinfo.setFatherbirthday(form.getFatherbirthday());
		familyinfo.setFatherfirstname(form.getFatherfirstname());
		familyinfo.setFatherfirstnameen(form.getFatherfirstnameen());
		familyinfo.setFatherlastname(form.getFatherlastname());
		familyinfo.setFatherlastnameen(form.getFatherlastnameen());
		familyinfo.setFatherstatus(form.getFatherstatus());
		familyinfo.setIsfatherinus(form.getIsfatherinus());
		//英文
		familyinfo.setFatherbirthdayen(form.getFatherbirthday());
		familyinfo.setFatherstatusen(form.getFatherstatus());
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
		familyinfo.setIsmotherinus(form.getIsmotherinus());
		//英文
		familyinfo.setMotherbirthdayen(form.getMotherbirthday());
		familyinfo.setMotherstatusen(form.getMotherstatus());
		familyinfo.setIsmotherinusen(form.getIsmotherinus());
		return null;
	}

	/**
	 * 其他亲属信息保存
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
	 * 跳转到职业与教育信息
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param staffid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toWorkandeducation(int staffid) {
		Map<String, Object> result = Maps.newHashMap();
		//职业信息
		TAppStaffWorkEducationTrainingEntity workinfo = dbDao.fetch(TAppStaffWorkEducationTrainingEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("workinfo", workinfo);
		//日期处理
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		if (!Util.isEmpty(workinfo.getWorkstartdate())) {
			result.put("workstartdate", sdf.format(workinfo.getWorkstartdate()));
		}

		//上份工作信息
		TAppStaffBeforeworkEntity beforework = dbDao.fetch(TAppStaffBeforeworkEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("beforework", beforework);
		if (!Util.isEmpty(beforework.getEmploystartdate())) {
			result.put("employestartdate", sdf.format(beforework.getEmploystartdate()));
		}
		if (!Util.isEmpty(beforework.getEmployenddate())) {
			result.put("employenddate", sdf.format(beforework.getEmployenddate()));
		}
		//教育信息
		TAppStaffBeforeeducationEntity beforeeducate = dbDao.fetch(TAppStaffBeforeeducationEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("beforeeducate", beforeeducate);
		if (!Util.isEmpty(beforeeducate.getCoursestartdate())) {
			result.put("coursestartdate", sdf.format(beforeeducate.getCoursestartdate()));
		}
		if (!Util.isEmpty(beforeeducate.getCourseenddate())) {
			result.put("courseenddate", sdf.format(beforeeducate.getCourseenddate()));
		}

		//主要职业
		result.put("careersenum", EnumUtil.enum2(VisaCareersEnum.class));
		//最高学历
		result.put("highesteducationenum", EnumUtil.enum2(VisaHighestEducationEnum.class));
		return JuYouResult.ok(result);
	}

	/**
	 * 保存教育与职业信息
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveWorkandeducation(WorkandeducateinfoUSForm form) {
		Integer staffid = form.getStaffid();
		//职业信息
		updateWorkinfo(form);

		//以前的工作信息
		if (Util.eq(1, form.getIsemployed())) {
			updateBeforework(form);
		}
		//教育信息
		if (Util.eq(1, form.getIssecondarylevel())) {
			updateBeforeeducation(form);
		}

		return JuYouResult.ok();
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
		workinfo.setIssecondarylevel(form.getIssecondarylevel());
		workinfo.setIsemployed(form.getIsemployed());
		//英文
		workinfo.setOccupationen(form.getOccupation());
		workinfo.setTelephoneen(form.getTelephone());
		workinfo.setCountryen(form.getCountry());
		workinfo.setProvinceen(translate(form.getProvince()));
		workinfo.setCityen(translate(form.getCity()));
		workinfo.setWorkstartdateen(form.getWorkstartdateen());
		workinfo.setPositionen(translate(form.getPosition()));
		workinfo.setSalaryen(form.getSalary());
		workinfo.setDutyen(translate(form.getDuty()));
		workinfo.setIssecondarylevelen(form.getIssecondarylevel());
		workinfo.setIsemployeden(form.getIsemployed());

		dbDao.update(workinfo);
		return null;
	}

	/**
	 * 以前的工作信息保存
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
		TAppStaffBeforeeducationEntity beforeeducation = dbDao.fetch(TAppStaffBeforeeducationEntity.class,
				Cnd.where("staffid", "=", staffid));
		beforeeducation.setHighesteducation(form.getHighesteducation());
		beforeeducation.setInstitution(form.getInstitution());
		beforeeducation.setInstitutionen(form.getInstitutionen());
		beforeeducation.setCourse(form.getCourse());
		beforeeducation.setInstitutioncountry(form.getInstitutioncountry());
		beforeeducation.setInstitutionprovince(form.getInstitutionprovince());
		beforeeducation.setInstitutioncity(form.getInstitutioncity());
		beforeeducation.setInstitutionaddress(form.getInstitutionaddress());
		beforeeducation.setInstitutionaddressen(form.getInstitutionaddressen());
		beforeeducation.setCoursestartdate(form.getCoursestartdate());
		beforeeducation.setCourseenddate(form.getCourseenddate());
		//英文
		beforeeducation.setHighesteducationen(form.getHighesteducation());
		beforeeducation.setCourseen(translate(form.getCourse()));
		beforeeducation.setInstitutioncountryen(form.getInstitutioncountry());
		beforeeducation.setInstitutionprovinceen(translate(form.getInstitutionprovince()));
		beforeeducation.setInstitutioncityen(translate(form.getInstitutioncity()));
		beforeeducation.setCoursestartdateen(form.getCoursestartdate());
		beforeeducation.setCourseenddateen(form.getCourseenddate());

		dbDao.update(beforeeducation);
		return null;
	}

	/**
	 * 跳转到旅游信息
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param staffid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toTravelinfo(int staffid) {
		Map<String, Object> result = Maps.newHashMap();
		TAppStaffTravelcompanionEntity travelcompanion = dbDao.fetch(TAppStaffTravelcompanionEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("travelwithother", travelcompanion.getIstravelwithother());
		//同伴信息
		List<TAppStaffCompanioninfoEntity> companioninfo = dbDao.query(TAppStaffCompanioninfoEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		result.put("companioninfo", companioninfo);
		//以前的美国旅游信息
		TAppStaffPrevioustripinfoEntity tripinfo = dbDao.fetch(TAppStaffPrevioustripinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("tripinfo", tripinfo);
		//时间处理
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		if (!Util.isEmpty(tripinfo.getIssueddate())) {
			result.put("issueddate", sdf.format(tripinfo.getIssueddate()));
		}
		//去过美国信息
		List<TAppStaffGousinfoEntity> gousinfo = dbDao.query(TAppStaffGousinfoEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		result.put("gousinfo", gousinfo);
		//美国驾照信息
		TAppStaffDriverinfoEntity driverinfo = dbDao.fetch(TAppStaffDriverinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("driverinfo", driverinfo);
		//是否有出境记录
		TAppStaffWorkEducationTrainingEntity workinfo = dbDao.fetch(TAppStaffWorkEducationTrainingEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("istraveledanycountry", workinfo.getIstraveledanycountry());
		//出境记录
		List<TAppStaffGocountryEntity> gocountry = dbDao.query(TAppStaffGocountryEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		result.put("gocountry", gocountry);

		result.put("emigrationreasonenumenum", EnumUtil.enum2(EmigrationreasonEnum.class));
		result.put("travelcompanionrelationshipenum", EnumUtil.enum2(TravelCompanionRelationshipEnum.class));
		return JuYouResult.ok(result);
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
		Integer staffid = form.getStaffid();
		//是否与其他人同行
		TAppStaffTravelcompanionEntity travelcompanion = dbDao.fetch(TAppStaffTravelcompanionEntity.class,
				Cnd.where("staffid", "=", staffid));
		travelcompanion.setIstravelwithother(form.getIstravelwithother());
		dbDao.update(travelcompanion);

		//同伴信息
		List<TAppStaffCompanioninfoEntity> companioninfo_old = dbDao.query(TAppStaffCompanioninfoEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		List<TAppStaffCompanioninfoEntity> companioninfo_new = form.getCompanioninfoList();
		dbDao.updateRelations(companioninfo_old, companioninfo_new);

		//以前的美国旅游信息
		updatePrevioustripinfo(form);

		//去过美国信息
		List<TAppStaffGousinfoEntity> gousinfo_old = dbDao.query(TAppStaffGousinfoEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		List<TAppStaffGousinfoEntity> gousinfo_new = form.getGousinfoList();
		dbDao.updateRelations(gousinfo_old, gousinfo_new);

		//美国驾照
		TAppStaffDriverinfoEntity driverinfo = dbDao.fetch(TAppStaffDriverinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		driverinfo.setDriverlicensenumber(form.getDriverlicensenumber());
		driverinfo.setWitchstateofdriver(form.getWitchstateofdriver());
		driverinfo.setDriverlicensenumberen(form.getDriverlicensenumber());
		driverinfo.setWitchstateofdriveren(form.getWitchstateofdriver());
		dbDao.update(driverinfo);

		//出境记录
		TAppStaffWorkEducationTrainingEntity workinfo = dbDao.fetch(TAppStaffWorkEducationTrainingEntity.class,
				Cnd.where("staffid", "=", staffid));
		workinfo.setIstraveledanycountry(form.getIstraveledanycountry());
		dbDao.update(workinfo);

		List<TAppStaffGocountryEntity> gocountry_old = dbDao.query(TAppStaffGocountryEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		List<TAppStaffGocountryEntity> gocountry_new = form.getGocountryList();
		dbDao.updateRelations(gocountry_old, gocountry_new);
		return JuYouResult.ok();
	}

	/**
	 * 以前的美国旅游信息保存
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updatePrevioustripinfo(TravelinfoUSForm form) {
		Integer staffid = form.getStaffid();
		TAppStaffPrevioustripinfoEntity tripinfo = dbDao.fetch(TAppStaffPrevioustripinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		tripinfo.setCostpayer(form.getCostpayer());
		tripinfo.setHasbeeninus(form.getHasbeeninus());
		tripinfo.setHasdriverlicense(form.getHasdriverlicense());
		tripinfo.setIsissuedvisa(form.getIsissuedvisa());
		tripinfo.setIssueddate(form.getIssueddate());
		tripinfo.setVisanumber(form.getVisanumber());
		tripinfo.setIsapplyingsametypevisa(form.getIsapplyingsametypevisa());
		tripinfo.setIstenprinted(form.getIstenprinted());
		tripinfo.setIslost(form.getIslost());
		tripinfo.setIscancelled(form.getIscancelled());
		tripinfo.setIsrefused(form.getIsrefused());
		tripinfo.setRefusedexplain(form.getRefusedexplain());
		tripinfo.setIsfiledimmigrantpetition(form.getIsfiledimmigrantpetition());
		tripinfo.setEmigrationreason(form.getEmigrationreason());
		tripinfo.setImmigrantpetitionexplain(form.getImmigrantpetitionexplain());
		//英文
		tripinfo.setCostpayeren(form.getCostpayer());
		tripinfo.setHasbeeninusen(form.getHasbeeninus());
		tripinfo.setHasdriverlicenseen(form.getHasdriverlicense());
		tripinfo.setIsissuedvisaen(form.getIsissuedvisa());
		tripinfo.setVisanumberen(form.getVisanumber());
		tripinfo.setIsapplyingsametypevisaen(form.getIsapplyingsametypevisa());
		tripinfo.setIstenprinteden(form.getIstenprinted());
		tripinfo.setIslosten(form.getIslost());
		tripinfo.setIscancelleden(form.getIscancelled());
		tripinfo.setIsrefuseden(form.getIsrefused());
		tripinfo.setRefusedexplainen(translate(form.getRefusedexplain()));
		tripinfo.setIsfiledimmigrantpetitionen(form.getIsfiledimmigrantpetition());
		tripinfo.setEmigrationreasonen(form.getEmigrationreason());
		tripinfo.setImmigrantpetitionexplainen(translate(form.getImmigrantpetitionexplain()));
		dbDao.update(tripinfo);
		return null;
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

	/**
	 * 国家模糊查询
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param searchstr
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object selectCountry(String searchstr) {
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

	/**
	 * 美国州模糊查询
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param searchstr
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object selectUSstate(String searchstr) {
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
