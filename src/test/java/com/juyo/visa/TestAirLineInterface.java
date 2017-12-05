/**
 * TestAirLineInterface.java
 * com.juyo.visa
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa;

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
import org.junit.Test;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年12月4日 	 
 */
public class TestAirLineInterface {

	@Test
	public void testAirLine() {
		/*try {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url("http://apis.haoservice.com/plan/queryCity?&key=fc96373fef3e40ffac0821a2ebb25a4d").get()
					.build();
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				System.out.println(response.body().toString());
			}
		} catch (IOException e) {
			e.printStackTrace();

		}*/
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			// 创建Http Post请求
			long startTime = System.currentTimeMillis(); //获取开始时间
			//查询城市
			//			HttpPost httpPost = new HttpPost("http://apis.haoservice.com/plan/queryCity");
			//查询航线 
			HttpPost httpPost = new HttpPost("http://apis.haoservice.com/plan/InternationalFlightQueryByCity");
			//查询航班
			//			HttpPost httpPost = new HttpPost("http://apis.haoservice.com/plan/FlightQueryByFlightNoV2");
			List<NameValuePair> paramList = new ArrayList<>();
			paramList.add(new BasicNameValuePair("key", "fc96373fef3e40ffac0821a2ebb25a4d"));
			//出发城市
			paramList.add(new BasicNameValuePair("dep", "XMN"));
			//抵达城市
			paramList.add(new BasicNameValuePair("arr", "TYO"));
			//航班号
			//			paramList.add(new BasicNameValuePair("flightNo", "CA181"));
			//出发时间
			paramList.add(new BasicNameValuePair("date", "20171206"));
			// 模拟表单
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
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
