package com.juyo.visa;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 生成短网址并返回
 * @author: Zhusw
     * @date: 2015年10月19日上午9:58:54
 */
public class GenerateShortUrlUtil {
	public static DefaultHttpClient httpclient;
	static {
		httpclient = new DefaultHttpClient();
	}

	/**
	 * 生成短连接信息
	 */
	public static String generateShortUrl(String url) {
		try {
			HttpPost httpost = new HttpPost("http://suo.im/api.php");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("format", "utf-8")); // 编码
			params.add(new BasicNameValuePair("url", url)); // 用户名称
			httpost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse response = httpclient.execute(httpost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
			System.out.println(jsonStr);
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}

	}

	/**
	 * 测试生成短连接
	 */
	public static void main(String[] args) {
		generateShortUrl("http://114.215.195.220:8080/admin/login.html");
	}
}
