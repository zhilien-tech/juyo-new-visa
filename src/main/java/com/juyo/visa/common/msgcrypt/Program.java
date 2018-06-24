package com.juyo.visa.common.msgcrypt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.juyo.visa.common.ocr.HttpUtils;
import com.uxuexi.core.common.util.JsonUtil;

public class Program {

	public static void main(String[] args) throws Exception {

		// 需要加密的明文
		String encodingAesKey = "jllZTM3ZWEzZGI1NGQ5NGI3MTc4NDNhNzAzODE5NTYt";
		String token = "ODBiOGIxNDY4NjdlMzc2Yg==";
		String timestamp = getTimeStamp();
		String nonce = getRandomStr();
		String appId = "jhhMThiZjM1ZGQ2Y";
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("passport_num", "E72073527");
		String replyMsg = JsonUtil.toJson(jsonMap);
		System.out.println(replyMsg);
		//String replyMsg = "123456HH哈哈哈";
		System.out.println("timestamp:" + timestamp);
		System.out.println("nonce:" + nonce);
		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
		//String mingwen = pc.encrypt(nonce, replyMsg);
		String mingwen = pc.encryptMsg(replyMsg, timestamp, nonce);
		System.out.println("加密后: " + mingwen);

		/*WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);

		Map<String, String> result = new HashMap<String, String>();
		result.put("msg_signature", "660e9d1dd2eeb15ccce610bad4ad7d285603ea21");
		result.put(
				"encrypt",
				"FK3DqhgVpcNEcSNgjpEQduJfQ9rlnZ54mBQYXWG5H0adI/C2yvGV9zacWxti6t+jDqqfFOZKWIn19KPv8ELtZ3Hl1cC58f45+CAKcDQQitckMsjNTxFsdsOi49rJ5EV8");
		result.put("timeStamp", "0606024301");
		result.put("nonce", "d97d9c63");
		String mingwen = JsonUtil.toJson(result);*/

		String host = "https://open.visae.net";
		String path = "/visae/america/lg/save/data/?token=ODBiOGIxNDY4NjdlMzc2Yg%3d%3d";
		String method = "POST";
		String entityStr = "";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, mingwen);
		entityStr = EntityUtils.toString(response.getEntity());
		System.out.println("=======" + entityStr);
		JSONObject resultObj = new JSONObject(entityStr);
		String replyyyy = (String) resultObj.get("encrypt");
		System.out.println(replyyyy);

		String result2 = pc.decrypt(replyyyy);
		System.out.println("解密后明文: " + result2);

	}

	// 随机生成16位字符串
	static String getRandomStr() {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	//生成时间戳
	static String getTimeStamp() {
		Date date = new Date();
		//样式：yyyy年MM月dd日HH时mm分ss秒SSS毫秒
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmss");
		String timeStampStr = simpleDateFormat.format(date);
		return timeStampStr;
	}
}
