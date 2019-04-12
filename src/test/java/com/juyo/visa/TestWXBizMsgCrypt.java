/**
 * TestAnsj.java
 * com.juyo.visa
 * Copyright (c) 2019, 北京科技有限公司版权所有.
*/

package com.juyo.visa;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;
import com.juyo.visa.common.msgcrypt.AesException;
import com.juyo.visa.common.msgcrypt.WXBizMsgCrypt;
import com.uxuexi.core.common.util.JsonUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2019年3月7日 	 
 */
public class TestWXBizMsgCrypt {

	private final static String ENCODINGAESKEY = "jllZTM3ZWEzZGI1NGQ5NGI3MTc4NDNhNzAzODE5NTYt";
	private final static String TOKEN = "ODBiOGIxNDY4NjdlMzc2Yg==";
	private final static String APPID = "jhhMThiZjM1ZGQ2Y";

	public static Object testEncrypt() {
		Map<String, Object> result = Maps.newHashMap();
		result.put("userName", "zhiliren");
		result.put("orderVoucher", "aM96NQ45");

		WXBizMsgCrypt pc = null;
		String resultStr = "";
		String json = "";
		try {
			pc = new WXBizMsgCrypt(TOKEN, ENCODINGAESKEY, APPID);
			//对数据进行加密
			json = pc.encryptMsg(JsonUtil.toJson(result), getTimeStamp(), getRandomString());
		} catch (AesException e2) {

			// TODO Auto-generated catch block
			e2.printStackTrace();

		}

		//将json字符串转成json对象
		org.json.JSONObject encryptObj = new org.json.JSONObject(json);
		String timestamp = (String) encryptObj.get("timeStamp");
		System.out.println("timestamp:" + timestamp);
		String signature = (String) encryptObj.get("msg_signature");
		System.out.println("signature:" + signature);
		String nonce = (String) encryptObj.get("nonce");
		System.out.println("nonce:" + nonce);
		json = (String) encryptObj.get("encrypt");
		System.out.println("json:" + json);

		try {
			String encode = URLEncoder.encode(json, "utf-8");
			System.out.println("encode:" + encode);

		} catch (UnsupportedEncodingException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	public static String getTimeStamp() {
		Date date = new Date();
		//样式：yyyy年MM月dd日HH时mm分ss秒SSS毫秒
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmss");
		String timeStampStr = simpleDateFormat.format(date);
		return timeStampStr;
	}

	public static String getRandomString() {
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

	public static void main(String[] args) {
		testEncrypt();
	}
}
