/**
 * TestMail.java
 * com.juyo.visa
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.juyo.visa.common.ocr.HttpUtils;

/**
 * 测试人脸识别
 */
public class TestFaceall {

	public static void main(String args[]) throws Exception {
		String host = "http://faceall.market.alicloudapi.com";
		String path = "/v2/detection/detect";
		String method = "POST";
		String appcode = "19598dc0fd65499b93a9dec6c43489b7";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		//根据API的要求，定义相对应的Content-Type
		headers.put("Content-Type", "application/json; charset=UTF-8");
		headers.put("Content-Type", "application/json");
		Map<String, String> querys = new HashMap<String, String>();
		String url = "http://oyu1xyxxk.bkt.clouddn.com/94636e82-7185-4600-8863-55d6f6baf0da.";
		String bodys = "{\"img_url\":\"" + url + "\",\"attributes\":\"true\"}";

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
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
			//System.out.println("====" + EntityUtils.toString(response.getEntity()));
			String result = EntityUtils.toString(response.getEntity());
			System.out.println("====" + result);
			JSONObject resultObj = new JSONObject(result);
			JSONArray outputArray = resultObj.getJSONArray("faces");
			JSONObject out = outputArray.getJSONObject(0).getJSONObject("attributes").getJSONObject("pose");
			System.out.println(out);
			Double roll = (Double) out.get("roll");
			if (roll >= 15 || roll <= -15) {
				System.err.println("请正面面对");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
