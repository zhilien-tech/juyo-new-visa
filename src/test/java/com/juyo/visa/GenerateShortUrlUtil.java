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
	 * 生成端连接信息
	 * 
	 * @author: Zhusw
	 * @date: 2015年10月19日上午10:01:10
	 */
	public static String generateShortUrl(String url) {
		try {
			HttpPost httpost = new HttpPost("http://dwz.cn/create.php");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("url", url)); // 用户名称
			httpost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse response = httpclient.execute(httpost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
			String tinyurl = jsonStr.replace("{\"tinyurl\":\"", "");
			int index = tinyurl.indexOf("\",\"status");
			tinyurl = tinyurl.substring(0, index).replace("\\/", "/");
			System.out.println(tinyurl);
			return tinyurl;
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}

	}

	/**
	 * 测试生成端连接
	 * @param args
	 * @author: Zhusw
	 * @date:2015年10月19日上午10:02:23
	 */
	public static void main(String[] args) {
		generateShortUrl("http://192.168.1.8:8080/public/menu.html");
	}
}
