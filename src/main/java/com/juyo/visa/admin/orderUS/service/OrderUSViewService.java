/**
 * SaleViewService.java
 * com.juyo.visa.admin.sale.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.orderUS.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;

import com.juyo.visa.admin.mail.service.MailService;
import com.juyo.visa.admin.order.entity.TIdcardEntity;
import com.juyo.visa.admin.orderUS.entity.USPassportJsonEntity;
import com.juyo.visa.admin.orderUS.entity.USStaffJsonEntity;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.USOrderStatusEnum;
import com.juyo.visa.common.ocr.HttpUtils;
import com.juyo.visa.common.ocr.Input;
import com.juyo.visa.common.ocr.RecognizeData;
import com.juyo.visa.common.util.ImageDeal;
import com.juyo.visa.common.util.PinyinTool;
import com.juyo.visa.common.util.PinyinTool.Type;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TAppStaffOrderUsEntity;
import com.juyo.visa.entities.TOrderUsEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.redis.RedisDao;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.we.business.sms.SMSService;
import com.we.business.sms.impl.HuaxinSMSServiceImpl;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 	 
 */
@IocBean
public class OrderUSViewService extends BaseService<TOrderUsEntity> {

	@Inject
	private RedisDao redisDao;

	@Inject
	private MailService mailService;

	@Inject
	private UploadService qiniuUploadService;//文件上传

	private final static String SMS_SIGNATURE = "【优悦签】";
	private final static Integer US_YUSHANG_COMID = 65;

	//根据人员id添加订单
	public Object addOrderByStuffId(Integer staffId) {

		TOrderUsEntity orderUs = new TOrderUsEntity();
		String orderNum = generateOrderNumByDate();
		Date nowDate = DateUtil.nowDate();
		orderUs.setOrdernumber(orderNum);
		orderUs.setComid(US_YUSHANG_COMID);
		orderUs.setStatus(USOrderStatusEnum.PLACE_ORDER.intKey());//下单
		orderUs.setCreatetime(nowDate);
		orderUs.setUpdatetime(nowDate);
		TOrderUsEntity order = dbDao.insert(orderUs);

		//更新人员-订单关系表
		if (!Util.isEmpty(order)) {
			Integer orderId = order.getId();
			TAppStaffOrderUsEntity staffOrder = new TAppStaffOrderUsEntity();
			staffOrder.setOrderid(orderId);
			staffOrder.setStaffid(staffId);
			dbDao.insert(staffOrder);
		}

		return JsonResult.success("添加成功");

	}

	//生成订单号
	public String generateOrderNumByDate() {
		SimpleDateFormat smf = new SimpleDateFormat("yyMMdd");
		String format = smf.format(new Date());
		String sqlString = sqlManager.get("orderUS_orderNum_nowDate");
		Sql sql = Sqls.create(sqlString);
		List<Record> query = dbDao.query(sql, null, null);
		int sum = 1;
		if (!Util.isEmpty(query) && query.size() > 0) {
			String string = query.get(0).getString("ordernumber");
			int a = Integer.valueOf(string.substring(9, string.length()));
			sum += a;
		}
		String sum1 = "";
		if (sum / 10 == 0) {
			sum1 = "000" + sum;
		} else if (sum / 100 == 0) {
			sum1 = "00" + sum;

		} else if (sum / 1000 == 0) {
			sum1 = "0" + sum;
		} else {
			sum1 = "" + sum;

		}
		String orderNum = format + "-US" + sum1;

		return orderNum;
	}

	/**
	 * 分享发送消息
	 *
	 * @param staffId 人员id
	 * @param orderid 订单id
	 * @param mobileUrl 手机号
	 * @return 
	 */
	public Object sendShareMsg(Integer staffId, Integer orderid, HttpServletRequest request) {

		String pcUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/tlogin";

		if (!Util.isEmpty(staffId)) {
			try {
				//发送短信
				sendSMSUS(staffId, orderid, "orderustemp/order_us_share_sms.txt");
				//发送邮件
				sendEmailUS(staffId, orderid, pcUrl, "orderustemp/order_us_share_mail.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return Json.toJson("发送成功");
		} else {
			return Json.toJson("电话号不能为空");
		}
	}

	//发送邮件
	public Object sendEmailUS(Integer staffId, Integer orderid, String pcUrl, String mailTemplate) throws IOException {
		List<String> readLines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream(mailTemplate));
		StringBuilder tmp = new StringBuilder();
		for (String line : readLines) {
			tmp.append(line);
		}
		String emailText = tmp.toString();
		String result = "";
		//查询订单号
		TOrderUsEntity order = dbDao.fetch(TOrderUsEntity.class, orderid.longValue());
		String orderNum = order.getOrdernumber();

		//申请人
		TAppStaffBasicinfoEntity staffBaseInfo = dbDao.fetch(TAppStaffBasicinfoEntity.class,
				Cnd.where("id", "=", staffId));
		String name = staffBaseInfo.getFirstname() + staffBaseInfo.getLastname();
		String telephone = staffBaseInfo.getTelephone();
		String toEmail = staffBaseInfo.getEmail();
		String sex = staffBaseInfo.getSex();

		if (!Util.isEmpty(toEmail)) {
			/*if (Util.eq("男", sex)) {
				sex = "先生";
			} else {
				sex = "女士";
			}*/
			sex = "先生/女士";

			emailText = emailText.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum)
					.replace("${telephone}", telephone).replace("${pcUrl}", pcUrl);
			result = mailService.send(toEmail, emailText, "美国订单分享", MailService.Type.HTML);
		}

		return result;
	}

	//发送短信
	public Object sendSMSUS(Integer staffId, Integer orderid, String smsTemplate) throws IOException {
		List<String> readLines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream(smsTemplate));
		StringBuilder tmp = new StringBuilder();
		for (String line : readLines) {
			tmp.append(line);
		}
		TAppStaffBasicinfoEntity staffBaseInfo = dbDao.fetch(TAppStaffBasicinfoEntity.class,
				Cnd.where("id", "=", staffId));
		String name = staffBaseInfo.getFirstname() + staffBaseInfo.getLastname();
		String telephone = staffBaseInfo.getTelephone();
		String email = staffBaseInfo.getEmail();
		String sex = staffBaseInfo.getSex();
		String result = "";
		if (!Util.isEmpty(telephone)) {
			/*if (Util.eq("男", sex)) {
				sex = "先生";
			} else {
				sex = "女士";
			}*/
			sex = "先生/女士";
			TOrderUsEntity order = dbDao.fetch(TOrderUsEntity.class, orderid.longValue());
			String orderNum = order.getOrdernumber();
			String smsContent = tmp.toString();
			smsContent = smsContent.replace("${name}", name).replace("${sex}", sex).replace("${ordernum}", orderNum)
					.replace("${mobileUrl}", telephone).replace("${email}", email);
			result = sendSMS(telephone, smsContent);
		}

		return result;

	}

	/**
	 * 发送手机短信
	 * <p>
	 * @param mobilenum  手机号
	 * @param content  短信内容
	 */
	public String sendSMS(String mobilenum, String content) {
		String result = "发送失败";
		try {
			SMSService smsService = new HuaxinSMSServiceImpl(redisDao);
			smsService.send(mobilenum, SMS_SIGNATURE + content);
			result = "发送成功";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Object IDCardRecognition(File file, HttpServletRequest request, HttpServletResponse response) {
		//将图片进行旋转处理
		ImageDeal imageDeal = new ImageDeal(file.getPath(), request.getContextPath(), UUID.randomUUID().toString(),
				"jpeg");
		File spin = null;
		try {
			spin = imageDeal.spin(-90);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//上传
		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(spin);
		file.delete();
		if (!Util.isEmpty(spin)) {
			spin.delete();
		}
		String url = CommonConstants.IMAGES_SERVER_ADDR + map.get("data");
		//从服务器上获取图片的流，读取扫描
		byte[] bytes = saveImageToDisk(url);
		String imageDataValue = Base64.encodeBase64String(bytes);
		Input input = new Input(imageDataValue, "face");
		RecognizeData rd = new RecognizeData();
		rd.getInputs().add(input);
		String content = Json.toJson(rd);
		String info = (String) appCodeCall(content);//扫描完毕
		System.out.println("info:" + info);
		//解析扫描的结果，结构化成标准json格式
		USStaffJsonEntity jsonEntity = new USStaffJsonEntity();
		JSONObject resultObj = new JSONObject(info);
		JSONArray outputArray = resultObj.getJSONArray("outputs");
		String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
		JSONObject out = new JSONObject(output);
		if (out.getBoolean("success")) {
			String addr = out.getString("address"); // 获取地址
			String name = out.getString("name"); // 获取名字
			String num = out.getString("num"); // 获取身份证号
			jsonEntity.setUrl(url);
			jsonEntity.setAddress(addr);
			Date date;
			try {
				date = new SimpleDateFormat("yyyyMMdd").parse(out.getString("birth"));
				String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				jsonEntity.setBirth(sdf.format(sdf.parse(dateStr)));
			} catch (JSONException | ParseException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			jsonEntity.setName(name);
			jsonEntity.setNationality(out.getString("nationality"));
			jsonEntity.setNum(num);
			jsonEntity.setRequest_id(out.getString("request_id"));
			jsonEntity.setSex(out.getString("sex"));
			jsonEntity.setSuccess(out.getBoolean("success"));
			String cardId = jsonEntity.getNum().substring(0, 6);
			TIdcardEntity IDcardEntity = dbDao.fetch(TIdcardEntity.class, Cnd.where("code", "=", cardId));
			if (!Util.isEmpty(IDcardEntity)) {
				jsonEntity.setProvince(IDcardEntity.getProvince());
				jsonEntity.setCity(IDcardEntity.getCity());
			}
		}
		return jsonEntity;
	}

	public Object IDCardRecognitionBack(File file, HttpServletRequest request, HttpServletResponse response) {
		//将图片进行旋转处理
		ImageDeal imageDeal = new ImageDeal(file.getPath(), request.getContextPath(), UUID.randomUUID().toString(),
				"jpeg");
		File spin = null;
		try {
			spin = imageDeal.spin(-90);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//上传
		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(spin);
		file.delete();
		if (!Util.isEmpty(spin)) {
			spin.delete();
		}
		String url = CommonConstants.IMAGES_SERVER_ADDR + map.get("data");
		//从服务器上获取图片的流，读取扫描
		byte[] bytes = saveImageToDisk(url);
		String imageDataValue = Base64.encodeBase64String(bytes);
		Input input = new Input(imageDataValue, "back");
		RecognizeData rd = new RecognizeData();
		rd.getInputs().add(input);
		String content = Json.toJson(rd);
		String info = (String) appCodeCall(content);//扫描完毕
		//解析扫描的结果，结构化成标准json格式
		USStaffJsonEntity jsonEntity = new USStaffJsonEntity();
		JSONObject resultObj = new JSONObject(info);
		JSONArray outputArray = resultObj.getJSONArray("outputs");
		String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
		JSONObject out = new JSONObject(output);
		if (out.getBoolean("success")) {
			String issue = out.getString("issue");
			jsonEntity.setIssue(issue);
			jsonEntity.setUrl(url);
			Date startDate;
			Date endDate;
			try {
				startDate = new SimpleDateFormat("yyyyMMdd").parse(out.getString("start_date"));
				endDate = new SimpleDateFormat("yyyyMMdd").parse(out.getString("end_date"));
				String startDateStr = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
				String endDateStr = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				jsonEntity.setStarttime(sdf.format(sdf.parse(startDateStr)));
				jsonEntity.setEndtime(sdf.format(sdf.parse(endDateStr)));
			} catch (JSONException | ParseException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			jsonEntity.setSuccess(out.getBoolean("success"));
		}
		return jsonEntity;
	}

	public Object passportRecognitionBack(File file, HttpServletRequest request, HttpServletResponse response) {
		//将图片进行旋转处理
		ImageDeal imageDeal = new ImageDeal(file.getPath(), request.getContextPath(), UUID.randomUUID().toString(),
				"jpeg");
		File spin = null;
		try {
			spin = imageDeal.spin(-90);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//上传
		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(spin);
		file.delete();
		if (!Util.isEmpty(spin)) {
			spin.delete();
		}
		String url = CommonConstants.IMAGES_SERVER_ADDR + map.get("data");
		//从服务器上获取图片的流，读取扫描
		byte[] bytes = saveImageToDisk(url);

		String imageDataB64 = Base64.encodeBase64String(bytes);
		Input input = new Input(imageDataB64);

		RecognizeData rd = new RecognizeData();
		rd.getInputs().add(input);

		String content = Json.toJson(rd);
		String info = (String) aliPassportOcrAppCodeCall(content);

		//解析扫描的结果，结构化成标准json格式
		USPassportJsonEntity jsonEntity = new USPassportJsonEntity();
		JSONObject resultObj = new JSONObject(info);
		JSONArray outputArray = resultObj.getJSONArray("outputs");
		String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
		JSONObject out = new JSONObject(output);
		String substring = "";
		if (out.getBoolean("success")) {
			String type = out.getString("type");
			if (!Util.isEmpty(type)) {
				substring = type.substring(0, 1);
			}
			jsonEntity.setType(substring);
			jsonEntity.setNum(out.getString("passport_no"));
			if (out.getString("sex").equals("F")) {
				jsonEntity.setSex("女");
				jsonEntity.setSexEn("F");
			} else {
				jsonEntity.setSex("男");
				jsonEntity.setSexEn("M");
			}
			//姓和名分开
			String nameEn = out.getString("name");//姓名拼音
			String nameAll = out.getString("name_cn");//姓名汉字
			char[] nameCnCharArray = nameAll.toCharArray();
			if (nameEn.contains(".")) {
				String[] nameEnSplit = nameEn.split("\\.");
				int lengthEn = nameEnSplit[0].length();
				int count = 0;
				int xingLength = 0;
				PinyinTool tool = new PinyinTool();
				try {
					for (int i = 0; i < nameCnCharArray.length; i++) {
						int length = tool.toPinYin(String.valueOf(nameCnCharArray[i]), "", Type.UPPERCASE).length();
						count += length;
						if (Util.eq(count, lengthEn)) {
							xingLength = i + 1;
						}
					}
					jsonEntity.setXingCn(nameAll.substring(0, xingLength));
					jsonEntity.setMingCn(nameAll.substring(xingLength));
				} catch (BadHanyuPinyinOutputFormatCombination e1) {
					e1.printStackTrace();
				}
			}
			jsonEntity.setUrl(url);
			jsonEntity.setOCRline1(out.getString("line0"));
			jsonEntity.setOCRline2(out.getString("line1"));
			jsonEntity.setBirthCountry(out.getString("birth_place"));
			jsonEntity.setVisaCountry(out.getString("issue_place"));
			Date birthDay;
			Date expiryDate;
			Date issueDate;
			try {
				birthDay = new SimpleDateFormat("yyyyMMdd").parse(out.getString("birth_date"));
				expiryDate = new SimpleDateFormat("yyyyMMdd").parse(out.getString("expiry_date"));
				issueDate = new SimpleDateFormat("yyyyMMdd").parse(out.getString("issue_date"));
				String startDateStr = new SimpleDateFormat("yyyy-MM-dd").format(birthDay);
				String endDateStr = new SimpleDateFormat("yyyy-MM-dd").format(expiryDate);
				String issueDateStr = new SimpleDateFormat("yyyy-MM-dd").format(issueDate);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				jsonEntity.setBirth(sdf.format(sdf.parse(startDateStr)));
				jsonEntity.setExpiryDay(sdf.format(sdf.parse(endDateStr)));
				jsonEntity.setIssueDate(sdf.format(sdf.parse(issueDateStr)));
			} catch (JSONException | ParseException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			jsonEntity.setSuccess(out.getBoolean("success"));
		}
		return jsonEntity;
	}

	private static Object appCodeCall(String content) {
		String host = "https://dm-51.data.aliyun.com";
		String path = "/rest/160601/ocr/ocr_idcard.json";
		String method = "POST";
		String entityStr = "";
		String appcode = "19598dc0fd65499b93a9dec6c43489b7";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		//根据API的要求，定义相对应的Content-Type
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		try {
			/**
			 * 重要提示如下:
			 * HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 */
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, content);
			//获取response的body
			//System.out.println(EntityUtils.toString(response.getEntity()));
			entityStr = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entityStr;
	}

	private static Object aliPassportOcrAppCodeCall(String content) {
		String host = "http://ocrhz.market.alicloudapi.com";
		String path = "/rest/160601/ocr/ocr_passport.json";
		String method = "POST";
		/*String appcode = "db7570313ab4478793f42ad8cd48723b";*/
		String appcode = "19598dc0fd65499b93a9dec6c43489b7";
		String entityStr = "";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		//根据API的要求，定义相对应的Content-Type
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();

		try {
			/**
			 * 重要提示如下:
			 * HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 */
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, content);
			//System.out.println(response.toString());
			//获取response的body
			//System.out.println(EntityUtils.toString(response.getEntity()));
			entityStr = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entityStr;
	}

	//这个函数负责把获取到的InputStream流保存到本地。  
	public static byte[] saveImageToDisk(String filePath) {
		InputStream inputStream = null;
		inputStream = getInputStream(filePath);//调用getInputStream()函数。  
		byte[] data = new byte[1024];
		byte[] result = new byte[1024];
		int len = -1;
		ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
		try {
			while ((len = inputStream.read(data)) != -1) {//循环读取inputStream流中的数据，存入文件流fileOutputStream  
				fileOutputStream.write(data, 0, len);
			}
			result = fileOutputStream.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {//finally函数，不管有没有异常发生，都要调用这个函数下的代码  
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();//记得及时关闭文件流  
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();//关闭输入流  
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static InputStream getInputStream(String filePath) {
		InputStream inputStream = null;
		HttpURLConnection httpURLConnection = null;
		try {
			URL url = new URL(filePath);//创建的URL  
			if (url != null) {
				httpURLConnection = (HttpURLConnection) url.openConnection();//打开链接  
				httpURLConnection.setConnectTimeout(3000);//设置网络链接超时时间，3秒，链接失败后重新链接  
				httpURLConnection.setDoInput(true);//打开输入流  
				httpURLConnection.setRequestMethod("GET");//表示本次Http请求是GET方式  
				int responseCode = httpURLConnection.getResponseCode();//获取返回码  
				if (responseCode == 200) {//成功为200  
					//从服务器获得一个输入流  
					inputStream = httpURLConnection.getInputStream();
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}

}
