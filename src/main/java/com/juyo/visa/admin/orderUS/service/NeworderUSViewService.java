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
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
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
import com.juyo.visa.admin.order.entity.TIdcardEntity;
import com.juyo.visa.admin.weixinToken.service.WeXinTokenViewService;
import com.juyo.visa.common.base.JuYouResult;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.MarryStatusEnum;
import com.juyo.visa.common.enums.PassportTypeEnum;
import com.juyo.visa.common.enums.visaProcess.EmigrationreasonEnum;
import com.juyo.visa.common.enums.visaProcess.ImmediateFamilyMembersRelationshipEnum;
import com.juyo.visa.common.enums.visaProcess.NewTimeUnitStatusEnum;
import com.juyo.visa.common.enums.visaProcess.TravelCompanionRelationshipEnum;
import com.juyo.visa.common.enums.visaProcess.VisaCareersEnum;
import com.juyo.visa.common.enums.visaProcess.VisaFamilyInfoEnum;
import com.juyo.visa.common.enums.visaProcess.VisaHighestEducationEnum;
import com.juyo.visa.common.enums.visaProcess.VisaSpouseContactAddressEnum;
import com.juyo.visa.common.enums.visaProcess.VisaUSStatesEnum;
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
import com.juyo.visa.entities.TCityUsEntity;
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
			"mySimpleSendInfoWSHandler", SimpleSendInfoWSHandler.class);
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
		result.put("staffid", staffid);
		String localAddr = request.getServerName();
		request.getServerName();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		result.put("localAddr", serverName);
		result.put("localPort", serverPort);
		result.put("websocketaddr", SEND_INFO_WEBSPCKET_ADDR);

		//生成带参数小程序码，将小程序码图片上传到七牛云并返回图片地址url
		String qrCode = dataUpload(staffid, request);
		result.put("qrCode", qrCode);

		//图片回显
		String credentsqlStr = sqlManager.get("orderUS_PC_getCredentials");
		Sql credentsql = Sqls.create(credentsqlStr);
		credentsql.setParam("staffid", staffid);
		List<Record> credentialsList = dbDao.query(credentsql, null, null);
		result.put("credentials", credentialsList);

		return result;
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
		String page = "pages/USA/upload/index/index";
		String scene = "";
		//因为小程序参数最长为32，所以参数尽量简化，a是人员id
		scene = "a=" + staffid;
		System.out.println("scene:" + scene + "--------------");
		//获取token
		String accessToken = (String) getAccessToken();
		System.out.println("accessToken:" + accessToken);
		System.out.println("page:" + page);
		//生成小程序码，并上传到七牛云，获得图片路径url
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
		accessTokenUrl = result.getString("access_token");
		return accessTokenUrl;
	}

	public String createBCode(String accessToken, String page, String scene) {
		String url = WX_B_CODE_URL.replace("ACCESS_TOKEN", accessToken);
		Map<String, Object> param = new HashMap<>();
		param.put("page", page);
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
		//调用微信生成小程序码接口
		InputStream inputStream = toPostRequest(json.toString(), accessToken);

		//将小程序码图片上传到七牛云
		String imgurl = CommonConstants.IMAGES_SERVER_ADDR + qiniuUploadService.uploadImage(inputStream, "", "");
		return imgurl;
	}

	//生成微信小程序码
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
		TAppStaffCredentialsEntity front = dbDao.fetch(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "=", 3));
		TAppStaffCredentialsEntity back = dbDao.fetch(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "=", 14));
		TAppStaffCredentialsEntity twoinch = dbDao.fetch(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "=", 13));
		result.put("front", front);
		result.put("back", back);
		result.put("twoinch", twoinch);
		result.put("marrystatusenum", EnumUtil.enum2(MarryStatusEnum.class));
		TAppStaffBasicinfoEntity basicinfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, staffid);
		result.put("basicinfo", basicinfo);

		TAppStaffFamilyinfoEntity familyinfo = dbDao.fetch(TAppStaffFamilyinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("familyinfo", familyinfo);
		//日期处理
		if (!Util.isEmpty(familyinfo.getMarrieddate())) {
			result.put("marrieddate", sdf.format(familyinfo.getMarrieddate()));
		}
		if (!Util.isEmpty(familyinfo.getDivorcedate())) {
			result.put("divorcedate", sdf.format(familyinfo.getDivorcedate()));
		}

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
		return result;
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
		long startTime = System.currentTimeMillis();
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
		} else {
			passportinfo.setFirstnameen("");
		}
		passportinfo.setLastname(form.getLastname());
		if (!Util.isEmpty(form.getLastnameen())) {
			passportinfo.setLastnameen(form.getLastnameen().substring(1));
		} else {
			passportinfo.setLastnameen("");
		}
		passportinfo.setSex(form.getSex());
		if (Util.eq("女", form.getSex())) {
			passportinfo.setSexen("F");
		} else {
			passportinfo.setSexen("M");
		}
		passportinfo.setBirthday(form.getBirthday());
		dbDao.update(passportinfo);
		long endTime = System.currentTimeMillis();
		System.out.println("保存用了" + (endTime - startTime) + "ms");
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

		TAppStaffFamilyinfoEntity familyinfo = dbDao.fetch(TAppStaffFamilyinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		if (form.getMarrystatus() == 2) {//离异时，有结婚日期和离婚日期
			familyinfo.setMarrieddate(form.getMarrieddate());
			familyinfo.setDivorcedate(form.getDivorcedate());
		} else {
			familyinfo.setMarrieddate(null);
			familyinfo.setDivorcedate(null);
		}
		//familyinfo.setDivorcecountry(form.getDivorcecountry());
		//familyinfo.setDivorcecountryen(form.getDivorcecountryen());
		dbDao.update(familyinfo);

		TAppStaffBasicinfoEntity basicinfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, staffid.longValue());
		basicinfo.setFirstname(form.getFirstname());
		if (!Util.isEmpty(form.getFirstnameen())) {
			basicinfo.setFirstnameen(form.getFirstnameen().substring(1));
		} else {
			basicinfo.setFirstnameen("");
		}
		basicinfo.setLastname(form.getLastname());
		if (!Util.isEmpty(form.getLastnameen())) {
			basicinfo.setLastnameen(form.getLastnameen().substring(1));
		} else {
			basicinfo.setLastnameen("");
		}

		basicinfo.setIsmailsamewithlive(form.getIsmailsamewithlive());
		if (form.getIsmailsamewithlive() == 1) {

			basicinfo.setMailcountry("");
			basicinfo.setMailprovince("");
			basicinfo.setMailcity("");
			basicinfo.setMailaddress("");

			basicinfo.setMailcountryen("");
			basicinfo.setMailprovinceen("");
			basicinfo.setMailcityen("");
			basicinfo.setMailaddressen("");
		} else {

			basicinfo.setMailcountry(form.getMailcountry());
			basicinfo.setMailprovince(form.getMailprovince());
			basicinfo.setMailcity(form.getMailcity());
			basicinfo.setMailaddress(form.getMailaddress());

			basicinfo.setMailcountryen(form.getMailcountryen());
			basicinfo.setMailprovinceen(form.getMailprovinceen());
			basicinfo.setMailcityen(form.getMailcityen());
			basicinfo.setMailaddressen(form.getMailaddressen());
		}

		basicinfo.setSex(form.getSex());
		basicinfo.setTelephone(form.getTelephone());
		basicinfo.setEmail(form.getEmail());
		basicinfo.setCardId(form.getCardId());
		basicinfo.setProvince(form.getProvince());
		basicinfo.setCity(form.getCity());
		basicinfo.setCardprovince(form.getCardprovince());
		basicinfo.setCardcity(form.getCardcity());
		basicinfo.setDetailedaddress(form.getDetailedaddress());
		basicinfo.setDetailedaddressen(form.getDetailedaddressen());
		basicinfo.setMarrystatus(form.getMarrystatus());
		basicinfo.setBirthday(form.getBirthday());
		basicinfo.setBirthcountry(form.getBirthcountry());
		//basicinfo.setNationality(form.getNationality());
		basicinfo.setHasothername(form.getHasothername());
		basicinfo.setHasothernameen(form.getHasothername());
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
		//英文
		long startTime = System.currentTimeMillis();
		System.out.println("开始保存英文====");
		basicinfo.setIsmailsamewithliveen(form.getIsmailsamewithliveen());

		basicinfo.setTelephoneen(form.getTelephone());
		basicinfo.setEmailen(form.getEmail());
		basicinfo.setCardIden(form.getCardId());
		basicinfo.setProvinceen(form.getProvinceen());
		basicinfo.setCityen(form.getCityen());
		basicinfo.setCardprovinceen(form.getCardprovinceen());
		basicinfo.setCardcityen(form.getCardcityen());
		basicinfo.setBirthcountryen(form.getBirthcountryen());
		//basicinfo.setNationalityen(form.getNationalityen());
		/*		basicinfo.setProvinceen(translate(form.getProvince()));
				basicinfo.setCityen(translate(form.getCity()));
				basicinfo.setCardprovinceen(translate(form.getCardprovince()));
				basicinfo.setCardcityen(translate(form.getCardcity()));
				basicinfo.setNationalityen(translate(form.getNationality()));
		*/basicinfo.setMarrystatusen(form.getMarrystatus());
		long endTime = System.currentTimeMillis();
		System.out.println("保存英文用了" + (endTime - startTime) + "ms=====");
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
		if (!Util.isEmpty(passportphoto)) {
			result.put("passporturl", passportphoto.getUrl());
		}
		TAppStaffPassportEntity passportinfo = dbDao.fetch(TAppStaffPassportEntity.class,
				Cnd.where("staffid", "=", staffid));
		if (!Util.isEmpty(passportinfo.getIssuedplaceen())) {
			if (!passportinfo.getIssuedplaceen().startsWith("/")) {
				passportinfo.setIssuedplaceen("/" + passportinfo.getIssuedplaceen());
			}
		}
		result.put("passportinfo", passportinfo);

		//日期处理
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		if (!Util.isEmpty(passportinfo.getIssueddate())) {
			result.put("issueddate", sdf.format(passportinfo.getIssueddate()));
		}
		if (!Util.isEmpty(passportinfo.getValidenddate())) {
			result.put("validenddate", sdf.format(passportinfo.getValidenddate()));
		}

		result.put("passporttype", EnumUtil.enum2(PassportTypeEnum.class));
		return result;
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
		//passportinfo.setIssuedplaceen(form.getIssuedplaceen());

		//中文翻译成拼音并大写工具
		PinyinTool tool = new PinyinTool();
		if (!Util.isEmpty(form.getIssuedplace())) {
			String issuedplace = form.getIssuedplace();
			if (Util.eq("内蒙古", issuedplace)) {
				passportinfo.setIssuedplaceen("NEI MONGOL");
			} else if (Util.eq("陕西", issuedplace)) {
				passportinfo.setIssuedplaceen("SHAANXI");
			} else {
				try {

					passportinfo.setIssuedplaceen(tool.toPinYin(form.getIssuedplace(), "", Type.UPPERCASE));
				} catch (BadHanyuPinyinOutputFormatCombination e1) {
					e1.printStackTrace();
				}
			}

		}

		passportinfo.setIssueddate(form.getIssueddate());
		passportinfo.setValidtype(form.getValidtype());
		passportinfo.setValidenddate(form.getValidenddate());
		passportinfo.setIssuedorganization(form.getIssuedorganization());
		passportinfo.setIssuedorganizationen(form.getIssuedorganizationen());
		passportinfo.setIslostpassport(form.getIslostpassport());
		if (form.getIslostpassport() == 2) {
			passportinfo.setIsrememberpassportnum(2);
			passportinfo.setIsrememberpassportnumen(2);
			passportinfo.setLostpassportnum("不知道");
			passportinfo.setLostpassportnumen("I do not know");
		} else {
			passportinfo.setIsrememberpassportnum(form.getIsrememberpassportnum());
			passportinfo.setIsrememberpassportnumen(form.getIsrememberpassportnum());
			if (form.getIsrememberpassportnum() == 2) {
				passportinfo.setLostpassportnum("不知道");
				passportinfo.setLostpassportnumen("I do not know");
			} else {
				passportinfo.setLostpassportnum(form.getLostpassportnum());
				passportinfo.setLostpassportnumen(form.getLostpassportnum());
			}
		}

		//英文
		passportinfo.setIslostpassporten(form.getIslostpassport());
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
		result.put("staffid", staffid);
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

		//直系亲属
		TAppStaffImmediaterelativesEntity immediaterelatives = dbDao.fetch(TAppStaffImmediaterelativesEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("immediaterelatives", immediaterelatives);
		//下拉处理
		//国家下拉
		List<TCountryRegionEntity> gocountryFiveList = dbDao.query(TCountryRegionEntity.class, null, null);
		result.put("gocountryfivelist", gocountryFiveList);
		//身份状态
		result.put("familyinfoenum", EnumUtil.enum2(VisaFamilyInfoEnum.class));
		//配偶联系地址
		result.put("spousecontactaddressenum", EnumUtil.enum2(VisaSpouseContactAddressEnum.class));
		//直系亲属---与你的关系
		result.put("immediaterelationshipenum", EnumUtil.enum2(ImmediateFamilyMembersRelationshipEnum.class));
		return result;
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
		if (form.getHasimmediaterelatives() == 1) {//有其他亲属,则更新或添加
			updateOtherinfo(form);
		} else {
			TAppStaffImmediaterelativesEntity immediaterelatives = dbDao.fetch(TAppStaffImmediaterelativesEntity.class,
					Cnd.where("staffid", "=", staffid));
			if (!Util.isEmpty(immediaterelatives)) {
				dbDao.delete(immediaterelatives);
			}
		}

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
		familyinfo.setFatherstatusen(form.getFatherstatus());
		familyinfo.setIsfatherinus(form.getIsfatherinus());
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
		result.put("staffId", staffid);
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
		if (!Util.isEmpty(beforework)) {
			if (!Util.isEmpty(beforework.getEmploystartdate())) {
				result.put("employestartdate", sdf.format(beforework.getEmploystartdate()));
			}
			if (!Util.isEmpty(beforework.getEmployenddate())) {
				result.put("employenddate", sdf.format(beforework.getEmployenddate()));
			}
		}
		//教育信息
		TAppStaffBeforeeducationEntity beforeeducate = dbDao.fetch(TAppStaffBeforeeducationEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("beforeeducate", beforeeducate);
		if (!Util.isEmpty(beforeeducate)) {
			if (!Util.isEmpty(beforeeducate.getCoursestartdate())) {
				result.put("coursestartdate", sdf.format(beforeeducate.getCoursestartdate()));
			}
			if (!Util.isEmpty(beforeeducate.getCourseenddate())) {
				result.put("courseenddate", sdf.format(beforeeducate.getCourseenddate()));
			}
		}

		//国家下拉
		List<TCountryRegionEntity> gocountryFiveList = dbDao.query(TCountryRegionEntity.class, null, null);
		result.put("gocountryfivelist", gocountryFiveList);
		//主要职业
		result.put("careersenum", EnumUtil.enum2(VisaCareersEnum.class));
		//最高学历
		result.put("highesteducationenum", EnumUtil.enum2(VisaHighestEducationEnum.class));
		return result;
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
		long startTime = System.currentTimeMillis();
		Integer staffid = form.getStaffid();
		long translateTime = 0;
		//职业信息
		translateTime += (long) updateWorkinfo(form);

		//以前的工作信息
		if (Util.eq(1, form.getIsemployed())) {
			translateTime += (long) updateBeforework(form);
		} else {
			TAppStaffBeforeworkEntity beforework = dbDao.fetch(TAppStaffBeforeworkEntity.class,
					Cnd.where("staffid", "=", staffid));
			if (!Util.isEmpty(beforework)) {
				dbDao.delete(beforework);
			}
		}
		//教育信息
		translateTime += (long) updateBeforeeducation(form);
		/*if (Util.eq(1, form.getIssecondarylevel())) {
			translateTime += (long) updateBeforeeducation(form);
		} else {
			TAppStaffBeforeeducationEntity beforeeducation = dbDao.fetch(TAppStaffBeforeeducationEntity.class,
					Cnd.where("staffid", "=", staffid));
			if (!Util.isEmpty(beforeeducation)) {
				dbDao.delete(beforeeducation);
			}
		}*/
		long endTime = System.currentTimeMillis();
		System.out.println("保存用了" + (endTime - startTime) + "ms");
		System.out.println("translateTime:" + translateTime);
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
		workinfo.setZipcode(getZipcode(form.getCity()));
		workinfo.setZipcodeen(workinfo.getZipcode());
		//英文
		long startTime = System.currentTimeMillis();
		workinfo.setOccupationen(form.getOccupation());
		workinfo.setTelephoneen(form.getTelephone());
		workinfo.setCountryen(form.getCountry());
		workinfo.setProvinceen(form.getProvinceen());
		workinfo.setCityen(form.getCityen());
		workinfo.setPositionen(form.getPositionen());
		workinfo.setDutyen(form.getDutyen());

		//workinfo.setProvinceen(translate(form.getProvince()));
		//workinfo.setCityen(translate(form.getCity()));
		//workinfo.setPositionen(translate(form.getPosition()));
		//workinfo.setDutyen(translate(form.getDuty()));
		workinfo.setWorkstartdateen(form.getWorkstartdateen());
		workinfo.setSalaryen(form.getSalary());
		workinfo.setIssecondarylevelen(form.getIssecondarylevel());
		workinfo.setIsemployeden(form.getIsemployed());
		long endTime = System.currentTimeMillis();

		dbDao.update(workinfo);
		return (endTime - startTime);
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
		if (Util.isEmpty(beforework)) {
			beforework = new TAppStaffBeforeworkEntity();
			beforework.setStaffid(staffid);
			dbDao.insert(beforework);
		}
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
		beforework.setEmployerzipcode(getZipcode(form.getEmployercity()));
		beforework.setEmployerzipcodeen(beforework.getEmployerzipcode());
		//英文
		long startTime = System.currentTimeMillis();
		beforework.setEmployertelephoneen(form.getEmployertelephone());
		beforework.setEmployercountryen(form.getEmployercountry());
		beforework.setEmploystartdateen(form.getEmploystartdate());
		beforework.setEmployenddateen(form.getEmployenddate());

		/*		beforework.setEmployerprovinceen(translate(form.getEmployerprovince()));
				beforework.setEmployercityen(translate(form.getEmployercity()));
				beforework.setJobtitleen(translate(form.getJobtitle()));
				beforework.setPreviousdutyen(translate(form.getPreviousduty()));
		*/beforework.setEmployerprovinceen(form.getEmployerprovinceen());
		beforework.setEmployercityen(form.getEmployercityen());
		beforework.setJobtitleen(form.getJobtitleen());
		beforework.setPreviousdutyen(form.getPreviousdutyen());
		long endTime = System.currentTimeMillis();

		dbDao.update(beforework);
		return (endTime - startTime);
	}

	/**
	 * 根据城市查询邮政编码
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param city
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public String getZipcode(String city) {
		TIdcardEntity zipcodecity = dbDao.fetch(TIdcardEntity.class, Cnd.where("city", "=", city));
		if (!Util.isEmpty(zipcodecity)) {
			return zipcodecity.getCode();
		} else {
			return "";
		}
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
		if (Util.isEmpty(beforeeducation)) {
			beforeeducation = new TAppStaffBeforeeducationEntity();
			beforeeducation.setStaffid(staffid);
			dbDao.insert(beforeeducation);
		}
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
		beforeeducation.setInstitutionzipcode(getZipcode(form.getInstitutioncity()));
		beforeeducation.setInstitutionzipcodeen(beforeeducation.getInstitutionzipcode());
		//英文
		long startTime = System.currentTimeMillis();
		beforeeducation.setHighesteducationen(form.getHighesteducation());
		beforeeducation.setInstitutioncountryen(form.getInstitutioncountry());
		beforeeducation.setCoursestartdateen(form.getCoursestartdate());
		beforeeducation.setCourseenddateen(form.getCourseenddate());
		/*		beforeeducation.setCourseen(translate(form.getCourse()));
				beforeeducation.setInstitutionprovinceen(translate(form.getInstitutionprovince()));
				beforeeducation.setInstitutioncityen(translate(form.getInstitutioncity()));
		*/beforeeducation.setCourseen(form.getCourseen());
		beforeeducation.setInstitutionprovinceen(form.getInstitutionprovinceen());
		beforeeducation.setInstitutioncityen(form.getInstitutioncityen());
		long endTime = System.currentTimeMillis();

		dbDao.update(beforeeducation);
		return (endTime - startTime);
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
		result.put("staffId", staffid);
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
		TAppStaffGousinfoEntity gousinfo = dbDao.fetch(TAppStaffGousinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		result.put("gousinfo", gousinfo);
		if (!Util.isEmpty(gousinfo)) {
			if (!Util.isEmpty(gousinfo.getArrivedate())) {
				result.put("arrivedate", sdf.format(gousinfo.getArrivedate()));
			}
		}
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

		/*String gocountrysqlStr = sqlManager.get("orderUS_PC_getGocountrys");
		Sql gocountrysql = Sqls.create(gocountrysqlStr);
		gocountrysql.setParam("staffid", staffid);
		List<Record> gocountry = dbDao.query(gocountrysql, null, null);
		for (Record record : gocountry) {
			int traveledcountry = record.getInt("traveledcountry");
			TCountryRegionEntity fetch = dbDao.fetch(TCountryRegionEntity.class, traveledcountry);
			record.set("traveledcountry", fetch.getChinesename());
		}
		result.put("gocountry", gocountry);*/

		//国家下拉
		List<TCountryRegionEntity> gocountryFiveList = dbDao.query(TCountryRegionEntity.class, null, null);
		result.put("gocountryfivelist", gocountryFiveList);
		result.put("timeunitstatusenum", EnumUtil.enum2(NewTimeUnitStatusEnum.class));
		result.put("usstatesenum", EnumUtil.enum2(VisaUSStatesEnum.class));
		result.put("emigrationreasonenumenum", EnumUtil.enum2(EmigrationreasonEnum.class));
		result.put("travelcompanionrelationshipenum", EnumUtil.enum2(TravelCompanionRelationshipEnum.class));
		return result;
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
		travelcompanion.setIstravelwithotheren(form.getIstravelwithother());
		dbDao.update(travelcompanion);

		//同伴信息
		updateCompanioninfo(form);

		//以前的美国旅游信息
		updatePrevioustripinfo(form);

		//去过美国信息
		updateGousinfo(form);

		//美国驾照
		updateDriverinfo(form);

		//是否有出境记录
		TAppStaffWorkEducationTrainingEntity workinfo = dbDao.fetch(TAppStaffWorkEducationTrainingEntity.class,
				Cnd.where("staffid", "=", staffid));
		workinfo.setIstraveledanycountry(form.getIstraveledanycountry());
		workinfo.setIstraveledanycountryen(form.getIstraveledanycountry());
		dbDao.update(workinfo);

		//出境记录
		updateGocountryinfo(form);

		return JuYouResult.ok();
	}

	/**
	 * 同伴信息处理
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateCompanioninfo(TravelinfoUSForm form) {
		Integer staffid = form.getStaffid();
		List<TAppStaffCompanioninfoEntity> companionList_old = dbDao.query(TAppStaffCompanioninfoEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		if (form.getIstravelwithother() == 2) {//没有同行人时，需要清掉同行人信息
			if (!Util.isEmpty(companionList_old)) {
				dbDao.delete(companionList_old);
			}
		} else {
			String companioninfoList = form.getCompanioninfoList();
			List<TAppStaffCompanioninfoEntity> companionList = form.getCompanionList();
			System.out.println("companioninfoList:" + companioninfoList);
			dbDao.updateRelations(companionList_old, companionList);
		}
		return null;
	}

	/**
	 * 去过美国信息处理
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateGousinfo(TravelinfoUSForm form) {
		Integer staffid = form.getStaffid();
		TAppStaffGousinfoEntity gousinfo = dbDao.fetch(TAppStaffGousinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		if (form.getHasbeeninus() == 1) {//去过美国，则添加或者修改
			if (Util.isEmpty(gousinfo)) {
				gousinfo = new TAppStaffGousinfoEntity();
				gousinfo.setStaffid(staffid);
				dbDao.insert(gousinfo);
			}
			if (!Util.isEmpty(form.getArrivedate())) {
				gousinfo.setArrivedate(form.getArrivedate());
				gousinfo.setArrivedateen(form.getArrivedateen());
			}
			gousinfo.setDateunit(form.getDateunit());
			gousinfo.setDateuniten(form.getDateuniten());
			gousinfo.setStaydays(form.getStaydays());
			gousinfo.setStaydaysen(form.getStaydaysen());
			dbDao.update(gousinfo);
		} else {
			if (!Util.isEmpty(gousinfo)) {
				dbDao.delete(gousinfo);
			}
		}
		return null;
	}

	/**
	 * 美国驾照信息处理
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateDriverinfo(TravelinfoUSForm form) {
		Integer staffid = form.getStaffid();
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
		return null;
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
		if (form.getIsissuedvisa() == 1) {
			tripinfo.setIssueddate(form.getIssueddate());
			tripinfo.setVisanumber(form.getVisanumber());
		} else {
			tripinfo.setIssueddate(null);
			tripinfo.setVisanumber("");
		}
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
		tripinfo.setIsapplyingsametypevisaen(form.getIsapplyingsametypevisa());
		tripinfo.setIstenprinteden(form.getIstenprinted());
		tripinfo.setIslosten(form.getIslost());
		tripinfo.setIscancelleden(form.getIscancelled());
		tripinfo.setIsrefuseden(form.getIsrefused());
		tripinfo.setIsfiledimmigrantpetitionen(form.getIsfiledimmigrantpetition());
		tripinfo.setEmigrationreasonen(form.getEmigrationreason());
		tripinfo.setRefusedexplainen(form.getRefusedexplainen());
		tripinfo.setImmigrantpetitionexplainen(form.getImmigrantpetitionexplainen());
		dbDao.update(tripinfo);
		return null;
	}

	/**
	 * 出境记录处理
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateGocountryinfo(TravelinfoUSForm form) {
		Integer staffid = form.getStaffid();

		List<TAppStaffGocountryEntity> countryList_old = dbDao.query(TAppStaffGocountryEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		if (!Util.isEmpty(countryList_old)) {
			dbDao.delete(countryList_old);
		}
		if (form.getIstraveledanycountry() == 1) {
			String traveledcountry = form.getTraveledcountry();
			String[] split = traveledcountry.split(",");
			for (String string : split) {
				TAppStaffGocountryEntity tAppStaffGocountryEntity = new TAppStaffGocountryEntity();
				tAppStaffGocountryEntity.setStaffid(staffid);
				tAppStaffGocountryEntity.setTraveledcountry(string);
				dbDao.insert(tAppStaffGocountryEntity);
			}
		}
		/*if (form.getIstraveledanycountry() == 2) {
			if (!Util.isEmpty(countryList_old)) {
				dbDao.delete(countryList_old);
			}
		} else {
			String gocountry = form.getGocountry();
			List<TAppStaffGocountryEntity> countryList_New = JsonUtil.fromJsonAsList(TAppStaffGocountryEntity.class,
					gocountry);
			dbDao.updateRelations(countryList_old, countryList_New);
		}*/
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
		String result = "";
		if (!Util.isEmpty(str)) {
			try {
				result = TranslateUtil.translate(str, "en");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("没有内容你让我翻译什么啊，神经病啊o(╥﹏╥)o");
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
		List<TCountryRegionEntity> countryList = new ArrayList<>();
		List<TCountryRegionEntity> country = dbDao.query(TCountryRegionEntity.class,
				Cnd.where("chinesename", "like", "%" + Strings.trim(searchstr) + "%"), null);
		for (TCountryRegionEntity tCountry : country) {
			if (!countryList.contains(tCountry)) {
				countryList.add(tCountry);
			}
		}
		List<TCountryRegionEntity> list = new ArrayList<>();
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
	 * 省份模糊查询
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param searchstr
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object selectProvince(String searchstr) {
		List<TIdcardEntity> countryList = new ArrayList<>();
		List<TIdcardEntity> country = dbDao.query(TIdcardEntity.class,
				Cnd.where("province", "like", "%" + Strings.trim(searchstr) + "%").groupBy("province"), null);
		for (TIdcardEntity tCountry : country) {
			if (!countryList.contains(tCountry)) {
				countryList.add(tCountry);
			}
		}
		List<TIdcardEntity> list = new ArrayList<>();
		if (!Util.isEmpty(countryList) && countryList.size() >= 5) {
			for (int i = 0; i < 5; i++) {
				list.add(countryList.get(i));
			}
			return list;
		} else {
			return countryList;
		}
	}

	public Object selectCity(String province, String searchstr) {
		List<TIdcardEntity> cityList = dbDao.query(
				TIdcardEntity.class,
				Cnd.where("province", "=", province).and("city", "!=", "")
						.and("city", "like", "%" + Strings.trim(searchstr) + "%").groupBy("city"), null);

		List<TIdcardEntity> list = new ArrayList<>();
		if (!Util.isEmpty(cityList) && cityList.size() >= 5) {
			for (int i = 0; i < 5; i++) {
				list.add(cityList.get(i));
			}
			return list;
		} else {
			return cityList;
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
		List<TStateUsEntity> stateList = new ArrayList<>();
		List<TStateUsEntity> state = dbDao.query(TStateUsEntity.class,
				Cnd.where("name", "like", "%" + Strings.trim(searchstr) + "%"), null);
		for (TStateUsEntity tState : state) {
			if (!stateList.contains(tState)) {
				stateList.add(tState);
			}
		}
		List<TStateUsEntity> list = new ArrayList<>();
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
	 * 美国城市模糊查询
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param searchstr
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object selectUScity(String searchstr) {
		List<TCityUsEntity> stateList = new ArrayList<>();
		List<TCityUsEntity> state = dbDao.query(TCityUsEntity.class,
				Cnd.where("cityname", "like", "%" + Strings.trim(searchstr) + "%"), null);
		for (TCityUsEntity tState : state) {
			if (!stateList.contains(tState)) {
				stateList.add(tState);
			}
		}
		List<TCityUsEntity> list = new ArrayList<>();
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
	 * 美国州城市联动模糊查询
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param searchstr
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object selectUSstateandcity(int stateid, String searchstr) {

		String sqlStr = sqlManager.get("orderUS_getSomeCity");
		Sql statesql = Sqls.create(sqlStr);
		statesql.setParam("stateid", stateid);
		List<Record> state = dbDao.query(statesql, null, null);

		List<Record> stateList = new ArrayList<>();

		for (Record record : state) {
			if (!stateList.contains(record)) {
				stateList.add(record);
			}
		}

		List<Record> list = new ArrayList<>();
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
	 * 根据国家名称查询对应的国家ID
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param searchstr
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getCountryid(String searchstr) {
		TCountryRegionEntity country = dbDao
				.fetch(TCountryRegionEntity.class, Cnd.where("chinesename", "=", searchstr));
		if (!Util.isEmpty(country)) {
			return country.getId();
		} else {
			return 0;
		}
	}
}
