package com.juyo.visa.common.msgcrypt;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.google.common.collect.Maps;
import com.juyo.visa.common.ocr.HttpUtils;
import com.uxuexi.core.common.util.JsonUtil;

public class Program {

	public static void main(String[] args) throws Exception {

		// 需要加密的明文
		String encodingAesKey = "jllZTM3ZWEzZGI1NGQ5NGI3MTc4NDNhNzAzODE5NTYt";
		String token = "ODBiOGIxNDY4NjdlMzc2Yg==";
		String timestamp = "0522060378";
		String nonce = "378224ea";
		String appId = "jhhMThiZjM1ZGQ2Y";
		String replyMsg = "123456HH哈哈哈";
		Map<String, Object> testresult = Maps.newHashMap();
		testresult.put("major", "Joker");
		testresult.put("major2", "Joker2");
		String json2 = JsonUtil.toJson(testresult);
		System.out.println("json2:" + json2);
		replyMsg = json2;

		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
		String mingwen = pc.encryptMsg(replyMsg, timestamp, nonce);
		System.out.println("加密后: " + mingwen);

		String signature = MsgCryptUtil.getSignature(token, timestamp, nonce, replyMsg);
		System.out.println("签名：" + signature);

		Map<String, String> result = new HashMap<String, String>();

		result.put("nonce", "ea11c1f0");
		result.put("timeStamp", "0601115368");
		result.put("msg_signature", signature);
		result.put("encrypt", mingwen);
		String json = JsonUtil.toJson(result);

		String host = "https://open.visae.net";
		String path = "/visae/america/lg/save/data/?token=ODBiOGIxNDY4NjdlMzc2Yg%3d%3d";
		String method = "POST";
		String entityStr = "";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, json);
		entityStr = EntityUtils.toString(response.getEntity());
		System.out.println("=======" + entityStr);
		JSONObject resultObj = new JSONObject(entityStr);
		String replyyyy = (String) resultObj.get("encrypt");
		System.out.println(replyyyy);

		String result2 = pc.decrypt(replyyyy);
		System.out.println("解密后明文: " + result2);

	}
}
