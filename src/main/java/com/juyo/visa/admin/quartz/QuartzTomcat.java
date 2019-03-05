/**
 * package-info.java
 * com.juyo.visa.admin.quartz
 * Copyright (c) 2018, 北京科技有限公司版权所有.
 */

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   刘旭利
 * @Date	 2018年12月4日
 */

package com.juyo.visa.admin.quartz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.io.IOUtils;
import org.nutz.integration.quartz.annotation.Scheduled;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.juyo.visa.admin.orderUS.service.OrderUSViewService;
import com.juyo.visa.entities.TOrderJpEntity;
import com.uxuexi.core.redis.RedisDao;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
@Scheduled(cron = "0 /1 0-23 * * ? *")
//每天的早上6点到晚上12点，每隔一分钟执行一次， 秒 分 时 天 月 每周 年(可选)
//直接使用注解来声明cron
public class QuartzTomcat extends BaseService<TOrderJpEntity> implements Job {

	@Inject
	private OrderUSViewService orderUSViewService;
	@Inject
	private RedisDao redisDao;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		System.out.println("Tomcat监控");
		System.out.println(sdf.format(new Date()));

		//主
		String path = "https://test.f-visa.com/admin/mobileVisa/getBasicInfoByStaffid";
		//从
		//String path = "https://www.f-visa.com/admin/mobileVisa/getBasicInfoByStaffid";
		String param = "staffid=4686";

		//HttpURLConnection httpURLConnection = null;
		HttpsURLConnection httpURLConnection = null;
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {

			//创建SSLContext
			SSLContext sslContext = SSLContext.getInstance("SSL");
			TrustManager[] tm = { new MyX509TrustManager() };
			//初始化
			sslContext.init(null, tm, new java.security.SecureRandom());
			;
			//获取SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(path);//创建的URL  
			httpURLConnection = (HttpsURLConnection) url.openConnection();

			//httpURLConnection = (HttpsURLConnection) url.openConnection();//打开链接  
			httpURLConnection.setConnectTimeout(3000);//设置网络链接超时时间，3秒，链接失败后重新链接  
			httpURLConnection.setReadTimeout(3000);//只有加上这个参数才能被catch到连接不上，否则catch不到错误
			httpURLConnection.setDoInput(true);//打开输入流  
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("POST");//表示本次Http请求方式
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//表头，不能改变

			//设置当前实例使用的SSLSoctetFactory
			httpURLConnection.setSSLSocketFactory(ssf);
			//httpURLConnection.connect();

			out = new PrintWriter(httpURLConnection.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();

			int responseCode = httpURLConnection.getResponseCode();//获取返回码  
			if (responseCode == 200) {
				in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
				System.out.println(result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				sendSMS("主服务器", "发生错误");
			} catch (Exception e1) {

				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	//发送短信
	public Object sendSMS(String ordernum, String orderstatus) throws IOException {
		List<String> readLines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream(
				"messagetmp/autofilljp_sms.txt"));
		StringBuilder tmp = new StringBuilder();
		for (String line : readLines) {
			tmp.append(line);
		}
		String telephone = "15600027715";
		String telephone2 = "18612131435";
		String result = "";
		String smsContent = tmp.toString();
		smsContent = smsContent.replace("${ordernum}", ordernum).replace("${orderstatus}", orderstatus);
		System.out.println("短信分享内容：" + smsContent);
		result = orderUSViewService.sendSMS(telephone, smsContent);
		//result = orderUSViewService.sendSMS(telephone2, smsContent);

		return result;

	}

	/**
	 * 计算两个时间相差多少分钟
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param endDate
	 * @param nowDate
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public static long getDatePoor(Date endDate, Date nowDate) {

		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		//return day + "天" + hour + "小时" + min + "分钟";

		// 计算差多少秒//输出结果
		long sec = diff % nd % nh % nm / ns;
		long result = (day * 24 * 60 * 60 + hour * 60 * 60 + min * 60 + sec) / 60;
		return result;
	}

}