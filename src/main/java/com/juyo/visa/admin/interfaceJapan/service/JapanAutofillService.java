/**
 * SendZhaobaoService.java
 * com.juyo.visa.admin.interfaceJapan.service
 * Copyright (c) 2019, 北京科技有限公司版权所有.
 */

package com.juyo.visa.admin.interfaceJapan.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.interfaceJapan.entity.ApplicantInfo;
import com.juyo.visa.admin.interfaceJapan.form.AutofillDataForm;
import com.juyo.visa.admin.interfaceJapan.form.ParamDataForm;
import com.juyo.visa.admin.order.service.OrderJpViewService;
import com.juyo.visa.admin.simple.service.SimpleVisaService;
import com.juyo.visa.admin.visajp.service.DownLoadVisaFileService;
import com.juyo.visa.admin.visajp.util.TemplateUtil;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.JpOrderSimpleEnum;
import com.juyo.visa.common.enums.TrialApplicantStatusEnum;
import com.juyo.visa.common.msgcrypt.AesException;
import com.juyo.visa.common.msgcrypt.WXBizMsgCrypt;
import com.juyo.visa.common.util.InterfaceResultObject;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantFrontPaperworkJpEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TApplicantPassportEntity;
import com.juyo.visa.entities.TApplicantVisaOtherInfoEntity;
import com.juyo.visa.entities.TApplicantVisaPaperworkJpEntity;
import com.juyo.visa.entities.TApplicantWealthJpEntity;
import com.juyo.visa.entities.TApplicantWorkJpEntity;
import com.juyo.visa.entities.TApplicanttTripJpEntity;
import com.juyo.visa.entities.TAutofillComOrderEntity;
import com.juyo.visa.entities.TAutofillCompanyEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderLogsEntity;
import com.juyo.visa.entities.TOrderTripJpEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.redis.RedisDao;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2019年4月2日 	 
 */
@IocBean
public class JapanAutofillService extends BaseService<TOrderEntity> {

	@Inject
	private RedisDao redisDao;

	@Inject
	private SimpleVisaService simpleVisaService;

	@Inject
	private OrderJpViewService orderJpViewService;

	@Inject
	private DownLoadVisaFileService downLoadVisaFileService;

	@Inject
	private UploadService qiniuUpService;

	//加密解密
	private final static String ENCODINGAESKEY = "jllZTM3ZWEzZGI1NGQ5NGI3MTc4NDNhNzAzODE5NTYt";
	private final static String TOKEN = "ODBiOGIxNDY4NjdlMzc2Yg==";
	private final static String APPID = "jhhMThiZjM1ZGQ2Y";

	public Object autofill(String token, ParamDataForm form, HttpServletRequest request) {
		System.out.println("访问到了~~~~~");
		System.out.println("传过来的数据为:" + form);

		String ip = getIP(request);
		System.out.println("autofillIP:" + ip);

		boolean flag = limitIPaccess(ip);
		if (!flag) {
			return encrypt(InterfaceResultObject.fail("提交过于频繁，请稍后再试！"));
		}

		System.out.println("token:" + token);
		if (!Util.eq("ODBiOGIxNDY4NjdlMzc2Yg==", token)) {
			return encrypt(InterfaceResultObject.fail("身份验证失败"));
		}

		//密文，需要解密
		String encrypt = form.getEncrypt();

		WXBizMsgCrypt pc;
		String resultStr = "";
		try {
			pc = new WXBizMsgCrypt(TOKEN, ENCODINGAESKEY, APPID);
			resultStr = pc.decrypt(encrypt);
			System.out.println("toGetEncrypt解密后明文: " + resultStr);
		} catch (AesException e) {
			e.printStackTrace();
			return encrypt(InterfaceResultObject.fail("请按要求传输数据"));
		}

		JSONObject paramData = new JSONObject(resultStr);

		AutofillDataForm autofillform = setDataToAutofillform(paramData);

		//身份验证
		TUserEntity loginUser = dbDao.fetch(TUserEntity.class, Cnd.where("mobile", "=", autofillform.getUserName()));
		if (Util.isEmpty(loginUser)) {
			return encrypt(InterfaceResultObject.fail("查无此人"));
		}

		TCompanyEntity loginCompany = dbDao.fetch(TCompanyEntity.class, Cnd.where("adminId", "=", loginUser.getId()));
		if (Util.isEmpty(loginCompany)) {
			return encrypt(InterfaceResultObject.fail("没有这个公司"));
		}

		TAutofillCompanyEntity autofillCom = dbDao.fetch(TAutofillCompanyEntity.class,
				Cnd.where("comid", "=", loginCompany.getId()));
		if (Util.isEmpty(autofillCom)) {
			return encrypt(InterfaceResultObject.fail("此公司没有权限"));
		} else {
			if (Util.eq(autofillCom.getIsdisabled(), 1)) {
				return encrypt(InterfaceResultObject.fail("此公司没有权限"));
			}
		}

		TCompanyEntity sendvisCom = dbDao.fetch(TCompanyEntity.class,
				Cnd.where("cdesignNum", "=", autofillform.getDesignatedNum()));
		if (Util.isEmpty(sendvisCom)) {
			return encrypt(InterfaceResultObject.fail("此送签社没有送签资格"));
		}

		//信息验证(是否满足发招宝所需数据)
		String resultstr = infoValidate(autofillform);

		if (!Util.isEmpty(resultstr)) {
			resultstr = resultstr.substring(0, resultstr.length() - 1);
			if (!resultstr.endsWith("正确") && !resultstr.endsWith("今天")) {
				resultstr += "不能为空";
			}
			return encrypt(InterfaceResultObject.fail(resultstr));
		}

		//orderVoucher订单凭证，如果为空，入库发招宝，如果不为空招宝变更
		JSONObject result = encrypt(InterfaceResultObject.fail("程序出错，请重新尝试"));
		TOrderEntity orderinfo = null;
		TOrderJpEntity orderjpinfo = null;
		TAutofillComOrderEntity autofillcomorder = null;

		//发招宝
		if (Util.eq("send", autofillform.getAction())) {

			if (Util.eq(14, autofillform.getVisaType())) {
				return encrypt(InterfaceResultObject.fail("签证类型为普通五年多次时不能进行此操作"));
			}

			if (!Util.isEmpty(autofillform.getOrderVoucher())) {
				return encrypt(InterfaceResultObject.fail("发招宝时别填写订单识别码！"));
			}
			//数据库入库并把订单状态改为发招宝中
			Map<String, Object> insertInfo = insertInfo(autofillform, loginUser, loginCompany);
			orderinfo = (TOrderEntity) insertInfo.get("orderinfo");
			orderjpinfo = (TOrderJpEntity) insertInfo.get("orderjpinfo");
			autofillcomorder = (TAutofillComOrderEntity) insertInfo.get("autofillcomorder");
			result = encrypt(InterfaceResultObject.success(autofillcomorder.getOrdervoucher()));

		} else if (Util.eq("update", autofillform.getAction())) {//招宝变更

			if (Util.eq(14, autofillform.getVisaType())) {
				return encrypt(InterfaceResultObject.fail("签证类型为普通五年多次时不能进行此操作"));
			}

			if (Util.isEmpty(autofillform.getOrderVoucher())) {
				return encrypt(InterfaceResultObject.fail("招宝变更时请填写订单识别码！"));
			} else {
				//修改订单信息，并把订单状态改为招宝变更
				String orderVoucher = autofillform.getOrderVoucher();
				TAutofillComOrderEntity autofillComOrder = dbDao.fetch(TAutofillComOrderEntity.class,
						Cnd.where("ordervoucher", "=", orderVoucher));
				if (Util.isEmpty(autofillComOrder)) {
					return encrypt(InterfaceResultObject.fail("并无此订单，请确认订单识别码是否正确"));
				} else {
					int orderid = autofillComOrder.getOrderid();
					Map<String, Object> updateAndChangezhaobao = updateAndChangezhaobao(autofillform, orderid,
							loginUser);
					orderinfo = (TOrderEntity) updateAndChangezhaobao.get("orderinfo");
					orderjpinfo = (TOrderJpEntity) updateAndChangezhaobao.get("orderjpinfo");
					result = encrypt(InterfaceResultObject.success());
				}
			}

		} else if (Util.eq("cancel", autofillform.getAction())) {//招宝取消

			if (Util.eq(14, autofillform.getVisaType())) {
				return encrypt(InterfaceResultObject.fail("签证类型为普通五年多次时不能进行此操作"));
			}

			if (Util.isEmpty(autofillform.getOrderVoucher())) {
				return encrypt(InterfaceResultObject.fail("招宝取消时请填写订单识别码！"));
			} else {
				//修改订单信息，并把订单状态改为招宝取消
				String orderVoucher = autofillform.getOrderVoucher();
				TAutofillComOrderEntity autofillComOrder = dbDao.fetch(TAutofillComOrderEntity.class,
						Cnd.where("ordervoucher", "=", orderVoucher));
				if (Util.isEmpty(autofillComOrder)) {
					return encrypt(InterfaceResultObject.fail("并无此订单，请确认订单识别码是否正确"));
				} else {
					int orderid = autofillComOrder.getOrderid();
					orderinfo = dbDao.fetch(TOrderEntity.class, orderid);
					orderinfo.setStatus(JPOrderStatusEnum.QUXIAOZHONG.intKey());
					orderjpinfo = dbDao.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", orderid));

					result = encrypt(InterfaceResultObject.success());
				}
			}
		} else {
			return encrypt(InterfaceResultObject.fail("没有" + autofillform.getAction() + "行为"));
		}

		/*if (Util.isEmpty(autofillform.getOrderVoucher())) {
			//数据库入库并把订单状态改为发招宝中
			Map<String, Object> insertInfo = insertInfo(autofillform, loginUser, loginCompany);
			orderinfo = (TOrderEntity) insertInfo.get("orderinfo");
			orderjpinfo = (TOrderJpEntity) insertInfo.get("orderjpinfo");
			autofillcomorder = (TAutofillComOrderEntity) insertInfo.get("autofillcomorder");
			result = orderUSViewService.encrypt(InterfaceResultObject.success(autofillcomorder.getOrdervoucher()));
		} else {
			//修改订单信息，并把订单状态改为招宝变更
			String orderVoucher = autofillform.getOrderVoucher();
			TAutofillComOrderEntity autofillComOrder = dbDao.fetch(TAutofillComOrderEntity.class,
					Cnd.where("ordervoucher", "=", orderVoucher));
			if (Util.isEmpty(autofillComOrder)) {
				return orderUSViewService.encrypt(InterfaceResultObject.fail("并无此订单，请确认订单识别码是否正确"));
			} else {
				int orderid = autofillComOrder.getOrderid();
				Map<String, Object> updateAndChangezhaobao = updateAndChangezhaobao(autofillform, orderid, loginUser);
				orderinfo = (TOrderEntity) updateAndChangezhaobao.get("orderinfo");
				orderjpinfo = (TOrderJpEntity) updateAndChangezhaobao.get("orderjpinfo");
				result = orderUSViewService.encrypt(InterfaceResultObject.success(""));
			}
		}*/

		//领区(待补充)

		//生成excel
		//申请人信息

		if (Util.eq("send", autofillform.getAction()) || Util.eq("update", autofillform.getAction())) {

			Map<String, Object> tempdata = new HashMap<String, Object>();
			String applysqlstr = sqlManager.get("get_applyinfo_from_filedown_by_orderid_jp");
			Sql applysql = Sqls.create(applysqlstr);
			Cnd cnd = Cnd.NEW();
			cnd.and("taoj.orderId", "=", orderjpinfo.getId());
			cnd.orderBy("taoj.isMainApplicant", "DESC").orderBy("taoj.id", "ASC");
			List<Record> applyinfo = dbDao.query(applysql, cnd, null);
			tempdata.put("applyinfo", applyinfo);
			//excel导出
			ByteArrayOutputStream excelExport = downLoadVisaFileService.excelExport(tempdata);
			//生成excel临时文件
			TemplateUtil templateUtil = new TemplateUtil();
			File excelfile = templateUtil.createTempFile(excelExport);
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(excelfile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			String qiniuurl = qiniuUpService.uploadImage(fileInputStream, "xlsx", null);
			//返回上传后七牛云的路径
			String fileqiniupath = CommonConstants.IMAGES_SERVER_ADDR + qiniuurl;
			//保存生成的七牛excel路径
			orderjpinfo.setExcelurl(fileqiniupath);

			//送签社
			orderjpinfo.setSendsignid(sendvisCom.getId());
			//地接社
			orderjpinfo.setGroundconnectid(autofillCom.getGroundconnectid());
		}

		orderjpinfo.setZhaobaotime(new Date());
		dbDao.update(orderjpinfo);
		//更新订单状态为发招保中或准备提交大使馆，此时发招宝就会开始，所以必须在准备工作之后，即orderjp相关的操作和excel完成之后

		Integer userId = loginUser.getId();
		orderinfo.setVisaOpid(userId);

		//改变订单状态为发招宝
		//orderinfo.setStatus(JPOrderStatusEnum.READYCOMMING.intKey());

		dbDao.update(orderinfo);

		//result = orderUSViewService.encrypt(ResultObject.success(autofillcomorder.getOrdervoucher()));
		//result = orderUSViewService.encrypt(ResultObject.success(orderinfo.getId()));
		System.out.println("返回的数据：" + result);
		return result;
	}

	//将数据加密
	public JSONObject encrypt(Object result) {
		String encodingAesKey = ENCODINGAESKEY;
		String token = TOKEN;
		String timestamp = getTimeStamp();
		String nonce = getRandomString();
		String appId = APPID;

		String jsonResult = JsonUtil.toJson(result);
		System.out.println("jsonResult:" + jsonResult);

		//加密
		WXBizMsgCrypt pc;
		String json = "";
		JSONObject jsonObject = null;
		try {
			pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
			jsonObject = pc.encryptMsg1(jsonResult, timestamp, nonce);
			System.out.println("body:" + json);

		} catch (AesException e) {
			e.printStackTrace();
		}

		//JSONObject jsonObject = new JSONObject(json);
		System.out.println("jsonObject:" + jsonObject);
		return jsonObject;
	}

	/**
	 * 获取时间戳方法
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public String getTimeStamp() {
		Date date = new Date();
		//样式：yyyy年MM月dd日HH时mm分ss秒SSS毫秒
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmss");
		String timeStampStr = simpleDateFormat.format(date);
		return timeStampStr;
	}

	/**
	 * 生成随机字符串
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public String getRandomString() {
		String str = "zxcvbnmlkjhgfdsaqwertyuiop1234567890";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		//长度为几就循环几次
		for (int i = 0; i < 8; ++i) {
			//产生0-61的数字
			int number = random.nextInt(str.length());
			//将产生的数字通过length次承载到sb中
			sb.append(str.charAt(number));
		}
		//将承载的字符转换成字符串
		return sb.toString();
	}

	/**
	 * 限制同一IP在30秒内最多只能访问10次
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param ip
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public boolean limitIPaccess(String ip) {
		boolean flag = true;

		String ipVal = redisDao.get(ip);

		String countVal = redisDao.get(ip + "count");

		if (Util.isEmpty(ipVal)) {
			ipVal = String.valueOf(System.currentTimeMillis());
			System.out.println("记录第一次IP的时间:" + ipVal);
			redisDao.set(ip, ipVal);
		}

		if (Util.isEmpty(countVal)) {
			countVal = "1";
			redisDao.set(ip + "count", "1");
		} else {
			countVal = String.valueOf(Integer.valueOf(countVal) + 1);
			redisDao.set(ip + "count", countVal);
		}

		if (!Util.isEmpty(ipVal) && (System.currentTimeMillis() - Long.valueOf(ipVal) < 30000)
				&& (Integer.valueOf(countVal) > 10)) {
			System.out.println("进false了");
			flag = false;
		}

		if (System.currentTimeMillis() - Long.valueOf(ipVal) >= 30000) {
			System.out.println("进delete了");
			System.out.println("记录第二次IP的时间:" + System.currentTimeMillis());
			redisDao.set(ip + "count", "1");
			redisDao.set(ip, String.valueOf(System.currentTimeMillis()));
			//redisDao.del(ip + "count");
		}

		/*CopyOnWriteArrayList<ConcurrentHashMap<String, Long>> ipList = new CopyOnWriteArrayList<>();
		ConcurrentHashMap<String, Long> ipMap = new ConcurrentHashMap<>();
		if (ipList != null && !ipList.isEmpty()) {
			for (ConcurrentHashMap<String, Long> myMap : ipList) {
				if (myMap.get(ip) != null) {
					//同一IP 1秒内只能提交一次
					if (System.currentTimeMillis() - myMap.get(ip) > 1000) {
						myMap.put(ip, System.currentTimeMillis());
						flag = true;
					}
				}
			}
			if (ipList.size() == 10) {
				//放满10次请求 清空一次
				ipList.clear();
			}
		}
		ipMap.put(ip, System.currentTimeMillis());
		ipList.add(ipMap);*/
		return flag;
	}

	/**
	 * 把传过来的数据解密后放入到对应的实体中
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param paramData
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public AutofillDataForm setDataToAutofillform(JSONObject paramData) {
		AutofillDataForm autofillform = new AutofillDataForm();
		String userName = "";
		String goDate = "";
		String returnDate = "";
		String designatedNum = "";
		//String acceptDesign = "";
		String orderVoucher = "";
		String action = "";
		Integer visaType = null;
		try {
			userName = paramData.getString("userName");
		} catch (Exception e) {

		}
		try {
			goDate = paramData.getString("goDate");
		} catch (Exception e) {

		}
		try {
			returnDate = paramData.getString("returnDate");
		} catch (Exception e) {

		}
		try {
			designatedNum = paramData.getString("designatedNum");
		} catch (Exception e) {

		}
		/*try {
			acceptDesign = paramData.getString("acceptDesign");
		} catch (Exception e) {

		}*/
		try {
			orderVoucher = paramData.getString("orderVoucher");
		} catch (Exception e) {

		}
		try {
			action = paramData.getString("action");
		} catch (Exception e) {

		}
		try {
			visaType = paramData.getInt("visaType");
		} catch (Exception e) {

		}

		JSONArray jsonArray = paramData.getJSONArray("applicantsList");

		List<ApplicantInfo> applicantsList = com.alibaba.fastjson.JSONObject.parseArray(jsonArray.toString(),
				ApplicantInfo.class);

		autofillform.setApplicantsList(applicantsList);
		autofillform.setGoDate(goDate);
		autofillform.setReturnDate(returnDate);
		autofillform.setDesignatedNum(designatedNum);
		autofillform.setUserName(userName);
		//autofillform.setAcceptDesign(acceptDesign);
		autofillform.setOrderVoucher(orderVoucher);
		autofillform.setAction(action);
		autofillform.setVisaType(visaType);
		return autofillform;
	}

	/**
	 * 验证传过来的信息是否完整，日期格式是否正确
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public String infoValidate(AutofillDataForm form) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer resultstrbuf = new StringBuffer("");
		StringBuffer datebuf = new StringBuffer("");
		StringBuffer formatbuf = new StringBuffer("");
		int count = 1;

		int daysBetween = DateUtil.daysBetween(sdf.format(new Date()), form.getGoDate());
		if (daysBetween < 0) {
			datebuf.append("出发日期不能早于今天，");
		}
		System.out.println(daysBetween + "============");
		if (Util.isEmpty(form.getGoDate())) {
			resultstrbuf.append("出发日期、");
		} else {
			try {
				sdf.parse(form.getGoDate());
			} catch (ParseException e) {
				formatbuf.append("出发日期、");
				e.printStackTrace();

			}
		}
		if (Util.isEmpty(form.getReturnDate())) {
			resultstrbuf.append("返回日期、");
		} else {
			try {
				sdf.parse(form.getReturnDate());
			} catch (ParseException e) {
				formatbuf.append("返回日期、");
				e.printStackTrace();

			}
		}
		if (Util.isEmpty(form.getUserName())) {
			resultstrbuf.append("用户名、");
		}
		if (Util.isEmpty(form.getDesignatedNum())) {
			resultstrbuf.append("指定番号、");
		}
		/*if (Util.isEmpty(form.getAcceptDesign())) {
			resultstrbuf.append("收付番号、");
		}*/
		if (Util.isEmpty(form.getVisaType())) {
			resultstrbuf.append("签证类型、");
		}
		if (Util.isEmpty(form.getApplicantsList())) {
			resultstrbuf.append("申请人、");
		} else {
			for (ApplicantInfo applicant : form.getApplicantsList()) {
				if (Util.isEmpty(applicant.getFirstname())) {
					resultstrbuf.append("申请人" + count + "的姓、");
				}
				if (Util.isEmpty(applicant.getFirstnameEn())) {
					resultstrbuf.append("申请人" + count + "的姓拼音、");
				}
				if (Util.isEmpty(applicant.getLastname())) {
					resultstrbuf.append("申请人" + count + "的名、");
				}
				if (Util.isEmpty(applicant.getLastnameEn())) {
					resultstrbuf.append("申请人" + count + "的名拼音、");
				}
				if (Util.isEmpty(applicant.getBirthday())) {
					resultstrbuf.append("申请人" + count + "的出生日期、");
				} else {
					try {
						sdf.parse(applicant.getBirthday());
					} catch (ParseException e) {
						formatbuf.append("申请人" + count + "的出生日期、");
						e.printStackTrace();

					}
				}
				if (Util.isEmpty(applicant.getIsMainApplicant())) {
					resultstrbuf.append("申请人" + count + "是否是主申请人、");
				}
				if (Util.isEmpty(applicant.getPassportNo())) {
					resultstrbuf.append("申请人" + count + "的护照号、");
				}
				if (Util.isEmpty(applicant.getProvince())) {
					resultstrbuf.append("申请人" + count + "的现居住地、");
				}
				if (Util.isEmpty(applicant.getSex())) {
					resultstrbuf.append("申请人" + count + "的性别、");
				}
				count++;
			}
		}

		/*String formatstr = formatbuf.toString();
		if (!Util.isEmpty(formatstr)) {
			formatbuf.deleteCharAt(formatbuf.length() - 1).append("格式不正确，").append(resultstrbuf);
		} else {
			formatbuf.append(resultstrbuf);
		}*/

		String formatstr = formatbuf.toString();
		if (!Util.isEmpty(formatstr)) {
			formatbuf.deleteCharAt(formatbuf.length() - 1).append("格式不正确，").append(resultstrbuf);
		} else {
			formatbuf.append(resultstrbuf);
		}
		datebuf.append(formatbuf);
		return datebuf.toString();
	}

	/**
	 * 把传过来的数据创建订单入库，并把订单状态改为发招宝
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param loginUser
	 * @param loginCompany
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Map<String, Object> insertInfo(AutofillDataForm form, TUserEntity loginUser, TCompanyEntity loginCompany) {
		Map<String, Object> result = Maps.newTreeMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//根据身份信息先创建订单
		Map<String, Integer> generrateorder = simpleVisaService.generrateorder(loginUser, loginCompany);
		Integer orderid = generrateorder.get("orderid");
		Integer orderjpid = generrateorder.get("orderjpid");

		//调用自动填表接口的公司和订单关系表
		String stringRandom = getStringRandom(8);
		TAutofillComOrderEntity autofillComOrder = new TAutofillComOrderEntity();
		autofillComOrder.setComid(loginCompany.getId());
		autofillComOrder.setUserid(loginUser.getId());
		autofillComOrder.setOrderid(orderid);
		autofillComOrder.setOrdervoucher(stringRandom);
		autofillComOrder.setCreateTime(new Date());
		autofillComOrder.setUpdateTime(new Date());
		dbDao.insert(autofillComOrder);

		//添加下单日志
		insertLogs(orderid, JpOrderSimpleEnum.PLACE_ORDER.intKey(), loginUser);

		TOrderJpEntity orderjpinfo = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderid.longValue());

		if (!Util.isEmpty(form.getGoDate())) {
			try {
				orderinfo.setGoTripDate(sdf.parse(form.getGoDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (!Util.isEmpty(form.getReturnDate())) {
			try {
				orderinfo.setBackTripDate(sdf.parse(form.getReturnDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		orderinfo.setStatus(JPOrderStatusEnum.READYCOMMING.intKey());

		Integer visatype = form.getVisaType();
		if (visatype == 7) {
			orderjpinfo.setVisaCounty("冲绳县");
		} else if (visatype == 8) {
			orderjpinfo.setVisaCounty("宫城县");
		} else if (visatype == 9) {
			orderjpinfo.setVisaCounty("福岛县");
		} else if (visatype == 10) {
			orderjpinfo.setVisaCounty("岩手县");
		} else if (visatype == 11) {
			orderjpinfo.setVisaCounty("青森县");
		} else if (visatype == 12) {
			orderjpinfo.setVisaCounty("秋田县");
		} else if (visatype == 13) {
			orderjpinfo.setVisaCounty("山形县");
		} else {
			orderjpinfo.setVisaCounty("");
		}
		orderjpinfo.setVisaType(visatype);

		//出行信息
		TOrderTripJpEntity orderjptrip = new TOrderTripJpEntity();

		orderjptrip.setTripPurpose("旅游");
		orderjptrip.setTripType(1);
		if (!Util.isEmpty(form.getGoDate())) {
			try {
				orderjptrip.setGoDate(sdf.parse(form.getGoDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (!Util.isEmpty(form.getReturnDate())) {
			try {
				orderjptrip.setReturnDate(sdf.parse(form.getReturnDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		orderjptrip.setOrderId(orderjpid);

		dbDao.insert(orderjptrip);

		//创建申请人
		List<ApplicantInfo> applicantsList = form.getApplicantsList();
		for (ApplicantInfo applicantinfo : applicantsList) {
			//新建申请人表
			TApplicantEntity apply = new TApplicantEntity();
			apply.setOpId(loginUser.getId());
			apply.setIsSameInfo(IsYesOrNoEnum.YES.intKey());
			apply.setIsPrompted(IsYesOrNoEnum.NO.intKey());
			apply.setStatus(TrialApplicantStatusEnum.FIRSTTRIAL.intKey());
			apply.setFirstName(applicantinfo.getFirstname());
			apply.setFirstNameEn(applicantinfo.getFirstnameEn());
			apply.setLastName(applicantinfo.getLastname());
			apply.setLastNameEn(applicantinfo.getLastnameEn());
			apply.setSex(applicantinfo.getSex());
			if (!Util.isEmpty(applicantinfo.getBirthday())) {
				try {
					apply.setBirthday(sdf.parse(applicantinfo.getBirthday()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			apply.setProvince(applicantinfo.getProvince());

			apply.setCreateTime(new Date());
			TApplicantEntity insertApply = dbDao.insert(apply);
			int applyid = insertApply.getId();

			if (Util.eq(1, applicantinfo.getIsMainApplicant())) {
				apply.setMainId(applyid);
				dbDao.update(apply);
			}

			//新建护照信息表
			TApplicantPassportEntity passport = new TApplicantPassportEntity();
			passport.setApplicantId(applyid);
			passport.setFirstName(applicantinfo.getFirstname());
			passport.setFirstNameEn(applicantinfo.getFirstnameEn());
			passport.setLastName(applicantinfo.getLastname());
			passport.setLastNameEn(applicantinfo.getLastnameEn());
			passport.setSex(applicantinfo.getSex());
			if (!Util.isEmpty(applicantinfo.getBirthday())) {
				try {
					passport.setBirthday(sdf.parse(applicantinfo.getBirthday()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			passport.setPassport(applicantinfo.getPassportNo());
			passport.setIssuedOrganization("公安部出入境管理局");
			passport.setIssuedOrganizationEn("MPS Exit&Entry Adiministration");
			dbDao.insert(passport);

			//新建日本申请人表
			TApplicantOrderJpEntity applicantjp = new TApplicantOrderJpEntity();

			applicantjp.setOrderId(orderjpid);
			applicantjp.setApplicantId(applyid);
			applicantjp.setBaseIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setPassIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setVisaIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setIsMainApplicant(applicantinfo.getIsMainApplicant());
			TApplicantOrderJpEntity insertappjp = dbDao.insert(applicantjp);

			//日本工作信息
			TApplicantWorkJpEntity workJp = new TApplicantWorkJpEntity();
			workJp.setApplicantId(insertappjp.getId());
			workJp.setCreateTime(new Date());
			workJp.setOpId(loginUser.getId());
			dbDao.insert(workJp);

			TApplicantVisaOtherInfoEntity visaother = new TApplicantVisaOtherInfoEntity();
			visaother.setApplicantid(insertappjp.getId());
			visaother.setHotelname("参照'赴日予定表'");
			visaother.setVouchname("参照'身元保证书'");
			visaother.setInvitename("同上");
			visaother.setTraveladvice("推荐");
			dbDao.insert(visaother);

		}
		result.put("orderinfo", orderinfo);
		result.put("orderjpinfo", orderjpinfo);
		result.put("autofillcomorder", autofillComOrder);
		return result;
	}

	//生成订单凭证随机码，由随机数字和字母组成,  
	public String getStringRandom(int length) {

		String val = "";
		Random random = new Random();

		//参数length，表示生成几位随机数  
		for (int i = 0; i < length; i++) {

			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			//输出字母还是数字  
			if ("char".equalsIgnoreCase(charOrNum)) {
				//输出是大写字母还是小写字母  
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	//添加日志
	public void insertLogs(Integer orderid, Integer status, TUserEntity loginUser) {
		TOrderLogsEntity logs = new TOrderLogsEntity();
		logs.setCreateTime(new Date());
		logs.setOpId(loginUser.getId());
		logs.setOrderId(orderid);
		logs.setOrderStatus(status);
		logs.setUpdateTime(new Date());
		dbDao.insert(logs);
	}

	/**
	 * 根据订单Id修改相关数据，并招宝变更
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param orderid
	 * @param loginUser
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Map<String, Object> updateAndChangezhaobao(AutofillDataForm form, int orderid, TUserEntity loginUser) {
		Map<String, Object> result = Maps.newTreeMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderid);
		TOrderJpEntity orderjpinfo = dbDao.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", orderid));

		if (!Util.isEmpty(form.getGoDate())) {
			try {
				orderinfo.setGoTripDate(sdf.parse(form.getGoDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (!Util.isEmpty(form.getReturnDate())) {
			try {
				orderinfo.setBackTripDate(sdf.parse(form.getReturnDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		orderinfo.setStatus(JPOrderStatusEnum.BIANGENGZHONG.intKey());

		Integer visatype = form.getVisaType();
		if (visatype == 7) {
			orderjpinfo.setVisaCounty("冲绳县");
		} else if (visatype == 8) {
			orderjpinfo.setVisaCounty("宫城县");
		} else if (visatype == 9) {
			orderjpinfo.setVisaCounty("福岛县");
		} else if (visatype == 10) {
			orderjpinfo.setVisaCounty("岩手县");
		} else if (visatype == 11) {
			orderjpinfo.setVisaCounty("青森县");
		} else if (visatype == 12) {
			orderjpinfo.setVisaCounty("秋田县");
		} else if (visatype == 13) {
			orderjpinfo.setVisaCounty("山形县");
		} else {
			orderjpinfo.setVisaCounty("");
		}
		orderjpinfo.setVisaType(visatype);

		//出行信息

		TOrderTripJpEntity orderjptrip = dbDao.fetch(TOrderTripJpEntity.class,
				Cnd.where("orderId", "=", orderjpinfo.getId()));

		if (!Util.isEmpty(form.getGoDate())) {
			try {
				orderjptrip.setGoDate(sdf.parse(form.getGoDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (!Util.isEmpty(form.getReturnDate())) {
			try {
				orderjptrip.setReturnDate(sdf.parse(form.getReturnDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		dbDao.update(orderjptrip);

		//申请人信息
		//先删除之前的申请人信息

		String applysqlStr = sqlManager.get("autofillInterface_getApplicants");
		Sql applysql = Sqls.create(applysqlStr);
		applysql.setParam("orderjpid", orderjpinfo.getId());
		List<Record> applyidList = dbDao.query(applysql, null, null);

		for (Record record : applyidList) {
			int id = record.getInt("id");

			//删除护照信息
			TApplicantPassportEntity passport = dbDao.fetch(TApplicantPassportEntity.class,
					Cnd.where("applicantId", "=", id));
			if (!Util.isEmpty(passport)) {
				dbDao.delete(passport);
			}

			TApplicantOrderJpEntity applicantOrderJp = dbDao.fetch(TApplicantOrderJpEntity.class,
					Cnd.where("applicantId", "=", id));
			if (!Util.isEmpty(applicantOrderJp)) {
				//删除工作信息
				TApplicantWorkJpEntity applicantWorkJpEntity = dbDao.fetch(TApplicantWorkJpEntity.class,
						Cnd.where("applicantId", "=", applicantOrderJp.getId()));
				if (!Util.isEmpty(applicantWorkJpEntity)) {
					dbDao.delete(applicantWorkJpEntity);
				}
				//删除财产信息
				List<TApplicantWealthJpEntity> wealths = dbDao.query(TApplicantWealthJpEntity.class,
						Cnd.where("applicantId", "=", applicantOrderJp.getId()), null);
				if (!Util.isEmpty(wealths)) {
					for (TApplicantWealthJpEntity tApplicantWealthJpEntity : wealths) {
						dbDao.delete(tApplicantWealthJpEntity);
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
				//删除日本申请人信息
				dbDao.delete(applicantOrderJp);
			}
			//删除申请人
			dbDao.delete(TApplicantEntity.class, id);
		}

		List<ApplicantInfo> applicantsList = form.getApplicantsList();

		for (ApplicantInfo applicantinfo : applicantsList) {
			//新建申请人表
			TApplicantEntity apply = new TApplicantEntity();
			apply.setOpId(loginUser.getId());
			apply.setIsSameInfo(IsYesOrNoEnum.YES.intKey());
			apply.setIsPrompted(IsYesOrNoEnum.NO.intKey());
			apply.setStatus(TrialApplicantStatusEnum.FIRSTTRIAL.intKey());
			apply.setFirstName(applicantinfo.getFirstname());
			apply.setFirstNameEn(applicantinfo.getFirstnameEn());
			apply.setLastName(applicantinfo.getLastname());
			apply.setLastNameEn(applicantinfo.getLastnameEn());
			apply.setSex(applicantinfo.getSex());
			if (!Util.isEmpty(applicantinfo.getBirthday())) {
				try {
					apply.setBirthday(sdf.parse(applicantinfo.getBirthday()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			apply.setProvince(applicantinfo.getProvince());

			apply.setCreateTime(new Date());
			TApplicantEntity insertApply = dbDao.insert(apply);
			int applyid = insertApply.getId();

			if (Util.eq(1, applicantinfo.getIsMainApplicant())) {
				apply.setMainId(applyid);
				dbDao.update(apply);
			}

			//新建护照信息表
			TApplicantPassportEntity passport = new TApplicantPassportEntity();
			passport.setApplicantId(applyid);
			passport.setFirstName(applicantinfo.getFirstname());
			passport.setFirstNameEn(applicantinfo.getFirstnameEn());
			passport.setLastName(applicantinfo.getLastname());
			passport.setLastNameEn(applicantinfo.getLastnameEn());
			passport.setSex(applicantinfo.getSex());
			if (!Util.isEmpty(applicantinfo.getBirthday())) {
				try {
					passport.setBirthday(sdf.parse(applicantinfo.getBirthday()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			passport.setPassport(applicantinfo.getPassportNo());
			passport.setIssuedOrganization("公安部出入境管理局");
			passport.setIssuedOrganizationEn("MPS Exit&Entry Adiministration");
			dbDao.insert(passport);

			//新建日本申请人表
			TApplicantOrderJpEntity applicantjp = new TApplicantOrderJpEntity();

			applicantjp.setOrderId(orderjpinfo.getId());
			applicantjp.setApplicantId(applyid);
			applicantjp.setBaseIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setPassIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setVisaIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setIsMainApplicant(applicantinfo.getIsMainApplicant());
			TApplicantOrderJpEntity insertappjp = dbDao.insert(applicantjp);

			//日本工作信息
			TApplicantWorkJpEntity workJp = new TApplicantWorkJpEntity();
			workJp.setApplicantId(insertappjp.getId());
			workJp.setCreateTime(new Date());
			workJp.setOpId(loginUser.getId());
			dbDao.insert(workJp);

			TApplicantVisaOtherInfoEntity visaother = new TApplicantVisaOtherInfoEntity();
			visaother.setApplicantid(insertappjp.getId());
			visaother.setHotelname("参照'赴日予定表'");
			visaother.setVouchname("参照'身元保证书'");
			visaother.setInvitename("同上");
			visaother.setTraveladvice("推荐");
			dbDao.insert(visaother);

		}
		result.put("orderinfo", orderinfo);
		result.put("orderjpinfo", orderjpinfo);
		return result;

	}

	/**
	 * 根据订单识别码查询订单状态
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param token
	 * @param timeStamp
	 * @param nonce
	 * @param msg_signature
	 * @param encrypt
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object search(String token, String encrypt, HttpServletRequest request) {

		if (!Util.eq("ODBiOGIxNDY4NjdlMzc2Yg==", token)) {
			return encrypt(InterfaceResultObject.fail("身份不正确"));
		}

		//获取ip地址
		String ip = getIP(request);
		System.out.println("searchIP:" + ip);

		//IP访问限制
		boolean flag = limitIPaccess(ip);
		if (!flag) {
			return encrypt(InterfaceResultObject.fail("提交过于频繁，请稍后再试！"));
		}

		//密文，需要解密
		WXBizMsgCrypt pc;
		String resultStr = "";
		try {
			pc = new WXBizMsgCrypt(TOKEN, ENCODINGAESKEY, APPID);
			resultStr = pc.decrypt(encrypt);
			System.out.println("toGetEncrypt解密后明文: " + resultStr);
		} catch (AesException e) {
			e.printStackTrace();
			return encrypt(InterfaceResultObject.fail("请按要求传输数据"));
		}

		JSONObject paramData = new JSONObject(resultStr);

		String userName = paramData.getString("userName");
		if (Util.isEmpty(userName)) {
			return encrypt(InterfaceResultObject.fail("用户名不能为空"));
		}

		String orderVoucher = paramData.getString("orderVoucher");
		if (Util.isEmpty(orderVoucher)) {
			return encrypt(InterfaceResultObject.fail("订单识别码不能为空"));
		}

		//身份验证
		TUserEntity loginUser = dbDao.fetch(TUserEntity.class, Cnd.where("mobile", "=", userName));
		if (Util.isEmpty(loginUser)) {
			return encrypt(InterfaceResultObject.fail("查无此人"));
		}

		TCompanyEntity loginCompany = dbDao.fetch(TCompanyEntity.class, Cnd.where("adminId", "=", loginUser.getId()));
		if (Util.isEmpty(loginCompany)) {
			return encrypt(InterfaceResultObject.fail("没有这个公司"));
		}

		TAutofillCompanyEntity autofillCom = dbDao.fetch(TAutofillCompanyEntity.class,
				Cnd.where("comid", "=", loginCompany.getId()));
		if (Util.isEmpty(autofillCom)) {
			return encrypt(InterfaceResultObject.fail("此公司没有权限"));
		} else {
			if (Util.eq(autofillCom.getIsdisabled(), 1)) {
				return encrypt(InterfaceResultObject.fail("此公司没有权限"));
			}
		}

		TAutofillComOrderEntity autofillComOrder = dbDao.fetch(TAutofillComOrderEntity.class,
				Cnd.where("orderVoucher", "=", orderVoucher));
		if (Util.isEmpty(autofillComOrder)) {
			return encrypt(InterfaceResultObject.fail("并无此订单，请确认订单识别码是否正确"));
		}
		//查询订单状态
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, autofillComOrder.getOrderid().longValue());
		Integer status = orderinfo.getStatus();
		String orderstatus = "";

		for (JPOrderStatusEnum visaenum : JPOrderStatusEnum.values()) {
			if (visaenum.intKey() == status) {
				orderstatus = visaenum.value();
			}
		}

		return encrypt(InterfaceResultObject.success(orderstatus));
	}

	public String getIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		//ip = ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;

		// 对于通过多个代理的情况，第一个 IP 为客户端真实 IP，多个 IP 按照','分割
		// "***.***.***.***".length() = 15
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}

		return ip;
	}
}
