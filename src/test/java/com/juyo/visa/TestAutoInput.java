/**
 * TestAutoInput.java
 * com.juyo.visa
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年1月9日 	 
 */
public class TestAutoInput {

	@Test
	public void testUpload() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			// 创建Http Post请求
			long startTime = System.currentTimeMillis(); //获取开始时间
			//查询城市
			//			HttpPost httpPost = new HttpPost("http://apis.haoservice.com/plan/queryCity");
			//查询航线 
			HttpPost httpPost = new HttpPost("http://192.168.1.7:8080/admin/simulate/japanErrorHandle.html");

			//查询航班
			//			HttpPost httpPost = new HttpPost("http://apis.haoservice.com/plan/FlightQueryByFlightNoV2");
			List<NameValuePair> paramList = new ArrayList<>();
			MultipartEntity reqentity = new MultipartEntity();
			//抵达城市
			paramList.add(new BasicNameValuePair("cid", "350"));
			paramList.add(new BasicNameValuePair("errorCode", "4"));
			paramList.add(new BasicNameValuePair("errorMsg", "受付番号生成失败"));
			//paramList.add(new BasicNameValuePair("acceptanceNumber", "XIAOHUIHUI"));
			FileBody file = new FileBody(new File("E:\\图片\\TIM图片20171221100911.png"));
			reqentity.addPart("cid", new StringBody("350"));
			reqentity.addPart("acceptanceNumber", new StringBody("XIAOHUIHUI"));
			reqentity.addPart("file", file);
			//航班号
			//			paramList.add(new BasicNameValuePair("flightNo", "CA181"));
			//出发时间
			// 模拟表单
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
			//httpPost.setEntity(reqentity);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			String string = EntityUtils.toString(response.getEntity(), "utf-8");
			//返回结果
			System.out.println("返回结果：" + string);
			long endTime = System.currentTimeMillis(); //获取结束时间
			System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
