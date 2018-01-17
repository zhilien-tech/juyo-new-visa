/**
 * AirlineData.java
 * com.juyo.visa.common.haoservice
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.common.haoservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年1月11日 	 
 */
public class AirlineData {

	private static String AIRLINEKEY = "99915bf5907d45a38ee2cfc6f19031a4";
	private static String AIRLINEURL = "http://apis.haoservice.com/efficient/flight/sk";

	//	private static String AIRLINEURL = "http://apis.haoservice.com/plan/InternationalFlightQueryByCity";

	public static String getAirLineInfo(AirLineParam airLineParam) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = "";
		try {
			// 创建Http Post请求
			long startTime = System.currentTimeMillis(); //获取开始时间
			//查询城市
			//			HttpPost httpPost = new HttpPost("http://apis.haoservice.com/plan/queryCity");
			//查询航线 
			HttpPost httpPost = new HttpPost(AIRLINEURL);
			//查询航班
			//			HttpPost httpPost = new HttpPost("http://apis.haoservice.com/plan/FlightQueryByFlightNoV2");
			List<NameValuePair> paramList = new ArrayList<>();
			paramList.add(new BasicNameValuePair("key", AIRLINEKEY));
			//出发城市
			paramList.add(new BasicNameValuePair("orgCity", airLineParam.getDep()));
			//抵达城市
			paramList.add(new BasicNameValuePair("dstCity", airLineParam.getArr()));
			//航班号
			//			paramList.add(new BasicNameValuePair("flightNo", "CA181"));
			//出发时间
			paramList.add(new BasicNameValuePair("flightDate", airLineParam.getDate()));
			// 模拟表单
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
			//返回结果
			System.out.println("返回结果：" + result);
			long endTime = System.currentTimeMillis(); //获取结束时间
			System.out.println("查询航班运行时间： " + (endTime - startTime) + "ms");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = "";
		try {
			// 创建Http Post请求
			long startTime = System.currentTimeMillis(); //获取开始时间
			//查询城市
			//			HttpPost httpPost = new HttpPost("http://apis.haoservice.com/plan/queryCity");
			//查询航线 
			HttpPost httpPost = new HttpPost(AIRLINEURL);
			//查询航班
			//			HttpPost httpPost = new HttpPost("http://apis.haoservice.com/plan/FlightQueryByFlightNoV2");
			List<NameValuePair> paramList = new ArrayList<>();
			paramList.add(new BasicNameValuePair("key", AIRLINEKEY));
			//出发城市
			paramList.add(new BasicNameValuePair("orgCity", "SHA"));
			//抵达城市
			paramList.add(new BasicNameValuePair("dstCity", "TYO"));
			//航班号
			//			paramList.add(new BasicNameValuePair("flightNo", "CA181"));
			//出发时间
			paramList.add(new BasicNameValuePair("flightDate", "20180121"));
			// 模拟表单
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
			//返回结果
			System.out.println("返回结果：" + result);
			long endTime = System.currentTimeMillis(); //获取结束时间
			System.out.println("查询航班运行时间： " + (endTime - startTime) + "ms");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
