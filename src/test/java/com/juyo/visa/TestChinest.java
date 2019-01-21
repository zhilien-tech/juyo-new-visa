package com.juyo.visa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.google.common.collect.Maps;
import com.juyo.visa.common.ocr.HttpUtils;

public class TestChinest {

	static final int N = 50000;
	private final static String PreUrl = "http://www.baidu.com/s?wd="; //百度搜索URL
	private final static String TransResultStartFlag = "<span class=\"op_dict_text2\">"; //翻译开始标签
	private final static String TransResultEndFlag = "</span>"; //翻译结束标签

	private static final String APP_ID = "20181211000246598";
	private static final String SECURITY_KEY = "8_MjFaIQyqSO5FvZCvm7";

	public static void main(String[] args) throws FailingHttpStatusCodeException, IOException {

		//int[] intlist = { 1, 3, 10 };
		String[] testlist = {};

		//13
		//1 3 10

		/*ArrayList<Integer> scenicarray = getScenicarray(intlist);

		int[] d = new int[scenicarray.size()];
		for (int i = 0; i < scenicarray.size(); i++) {
			d[i] = scenicarray.get(i);
		}*/

		ArrayList<Integer> intlist = new ArrayList();
		intlist.add(1);
		intlist.add(3);
		intlist.add(10);

		long first = System.currentTimeMillis();

		ArrayList<String> getsomeCount = getsomeCount(intlist, 5);
		long last = System.currentTimeMillis();
		System.out.println("所用时间：" + (last - first) + "ms");
		System.out.println(getsomeCount);

		/*//1 3 10
		Random random = new Random();
		int nextInt = random.nextInt(1);

		System.out.println(nextInt);*/
		/*Character str = new Character(' ');
		System.out.println(Pattern.compile("^[ A-Za-z0-9-&'#$*%;!,?.<>^@]+$").matcher(String.valueOf(str)).find());
		System.out.println(Pattern.compile("^[ A-Za-z0-9-&']+$").matcher(String.valueOf(str)).find());

		String match = "    ";
		System.out.println(Pattern.compile("^[ A-Za-z0-9-&'#$*%;!,?.<>^@]+$").matcher(match).find());

		String text = "s+  【】{}[]:'?`~   d*!?o   <f>h.os  i66-&'@#$%^^.";
		boolean find1 = Pattern.compile("^[A-Za-z0-9-&'#$*%;!,?.<>^@]+$").matcher(text).find();
		System.out.println("find1:" + find1);
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < text.length(); i++) {
			char character = text.charAt(i);
			boolean find = Pattern.compile("^[ A-Za-z0-9-&'#$*%;!,?.<>^@]+$").matcher(String.valueOf(character)).find();
			System.out.println("find:" + find + ";character:" + character);
			if (find) {
				result.append(character);
			}
		}
		System.out.println(result.toString() + "++++++++++");

		//text = text.replaceAll("\\s{2,}", " ").trim();
		StringBuffer result = new StringBuffer();
		//text = text.replaceAll("\\s+", " ").trim();
		System.out.println(text);

		for (int i = 0; i < text.length(); i++) {
			char character = text.charAt(i);
			boolean find = Pattern.compile("^[ A-Za-z0-9-&'#$*%;!,?.()<>@^]+$").matcher(String.valueOf(character))
					.find();
			System.out.println("find:" + find + ";character:" + character);
			if (find) {
				result.append(character);
			}
		}
		System.out.println("最终过滤结果：" + result.toString());*/

		/*Matcher m = Pattern.compile("^[A-Za-z0-9-&']+$").matcher(text);
		System.out.println(m.find());*/

		//loginFrance("755706503@qq.com", "xiong321");
		//loginUSA("BEJ", "wt4ae");
		//loginUSATEST("BEJ", "wt4ae");

		/*Map<String, String> resultData = Maps.newHashMap();
		resultData.put("755706503@qq.com", "xiong321");
		resultData.put("52350750@qq.com", "xiong321");
		resultData.put("mrgw0124@163.com", "xiong321");

		for (String email : resultData.keySet()) {
			System.out.println("进入循环了==============");
			loginThread(email, resultData.get(email));
		}*/

		//Map<String, Object> testPHPMap = sendGet();
		//Map<String, List<String>> cookies = (Map<String, List<String>>) testPHPMap.get("cookies");

		/*Map<String, Object> testPHPMap = testPHP();
		Header[] cookies = (Header[]) testPHPMap.get("cookies");

		String testPHP = (String) testPHPMap.get("entityStr");

		Document doc = Jsoup.parse(testPHP);
		Elements select = doc.select("input[name=_sid]");
		String val = select.val();
		System.out.println(val);*/

		/*Map<String, String> resultData = Maps.newHashMap();
		resultData.put("email", "755706503@qq.com");
		resultData.put("pwd", "xiong321");
		resultData.put("process", "login");
		resultData.put("_sid", val);

		String json = JsonUtil.toJson(resultData);*/

		/*String json = "";
		try {
			json = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode("52350750@qq.com", "UTF-8") + "&"
					+ URLEncoder.encode("pwd", "UTF-8") + "=" + URLEncoder.encode("xiong321", "UTF-8") + "&"
					+ URLEncoder.encode("process", "UTF-8") + "=" + URLEncoder.encode("login", "UTF-8") + "&"
					+ URLEncoder.encode("_sid", "UTF-8") + "=" + URLEncoder.encode(val, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		//String json = "process=login&_sid=" + val + "&email=755706503@qq.com&pwd=xiong321";

		//String postRequest = toPostRequest(json, cookies);
		//String postRequest = sendPost(json, cookies);

		//System.out.println(postRequest);

		/*UUID randomUUID = UUID.randomUUID();
		System.out.println(randomUUID.toString().replace("-", ""));

		String str = "1d3a2a75c7169551506b741243fd3ab0";
		System.out.println(str);*/

		/*int arr[] = { 2, 1, 2, 3, 4, 3, 4, 6, 8, 10, 10, 8, 8, 2, 2, 8 };

		Map<Integer, Integer> result = Maps.newHashMap();
		ArrayList<Integer> arrayList = new ArrayList<>();

		for (Integer integer : arr) {
			Integer integer2 = result.get(integer);

			result.put(integer, (integer2 == null) ? 1 : integer2 + 1);

		}

		for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
			System.out.println("重复的数据为:" + entry.getValue() + "个" + entry.getKey());
		}*/

		/*DateFormat format = new SimpleDateFormat("yyyy-M-d");
		String datestr = "2018-12-30";
		try {
			Date parse = format.parse(datestr);
			System.out.println(parse);
			String plusDay = plusDay(1, parse);
			System.out.println(plusDay);
		} catch (ParseException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}*/

		/*TransApi api = new TransApi(APP_ID, SECURITY_KEY);

		String query = "不知道";
		System.out.println(api.getTransResult(query, "auto", "en"));*/

		//String translateResult = getTranslateResult("日本");
		//System.out.println(translateResult + "======");

		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String string = "2018-12-03 17:40:26";
		Date first;
		try {
			first = sdf.parse(string);
			Date last = new Date();
			System.out.println(first);
			System.out.println(last);
			long datePoor = getDatePoor(last, first);
			System.out.println(datePoor);
			if (datePoor > 6) {
				System.out.println("==============");
				System.out.println("超时了，需要发通知了~~~");
			}
		} catch (ParseException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}*/

		/*long first = System.currentTimeMillis();

		int[] paramArray = { 22, 51, 38, 50, 85, 53, 37 };
		int arrcityid = 51;
		int returngocityid = 22;

		for (int i = 0; i < paramArray.length; i++) {
			if (paramArray[i] == arrcityid) {
				paramArray = ArrayUtils.remove(paramArray, i);
			}
		}
		for (int i = 0; i < paramArray.length; i++) {
			if (paramArray[i] == returngocityid) {
				paramArray = ArrayUtils.remove(paramArray, i);
			}
		}

		long last = System.currentTimeMillis();
		System.out.println((last - first) + "ms");

		for (int i = 0; i < paramArray.length; i++) {
			System.out.println(paramArray[i]);
		}*/

		/*Random random = new Random();
		System.out.println(random.nextInt(1));
		for (int i = 0; i < 100; i++) {
			System.out.println(random.nextInt(1));
			int nextInt = random.nextInt(31);
			if (nextInt + 2 > 30) {
				System.out.println(nextInt + 2);
			}
		}*/

		/*int[] arr = { 1, 2, 3, 4, 5, 6 };
		List<Integer> randomDates = getRandomDates(arr, 32);
		for (Integer integer : randomDates) {
			System.out.println(integer);
		}*/
		/*int str = 1200;
		DecimalFormat df = new DecimalFormat("#,###");
		System.out.println(df.format(str));*/

		/*String str = "ggweg146 51-& ";
		System.out.println(isLegalStr(str));*/
		/*if (str.endsWith("自治区")) {
			str = str.substring(0, str.length() - 3);
		}
		if (str.endsWith("区")) {
			str = str.substring(0, str.length() - 1);
		}

		System.out.println(str);*/

		/*try {
			System.out.println(TranslateUtil.translate(str, "en"));
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}*/

		//System.out.println(imgToBse64("http://oyu1xyxxk.bkt.clouddn.com/5dc3274f-204b-4336-bf57-985e09c7ca14..jpeg"));

		/*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");

		Date firstDate = DateUtil.nowDate();
		System.out.println(simpleDateFormat.format(firstDate));
		long count = 0;
		for (long i = 0; i < 10000; i++) {
			for (int j = 0; j < 100000; j++) {
				count += (i + j);
			}
		}
		System.out.println(count);
		Date nowDate = DateUtil.nowDate();
		System.out.println(simpleDateFormat.format(nowDate));

		long twoDatebetweenMillis = DateUtil.twoDatebetweenMillis(firstDate, nowDate);
		System.out.println("twoDatebetweenMillis:" + twoDatebetweenMillis);

		long millisBetween = DateUtil.millisBetween(firstDate, nowDate);
		System.out.println(millisBetween);
		if (millisBetween != 0 && millisBetween % 30000 == 0) {
			System.out.println("进来了~~~~~~~~~");
		}*/

		/*Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-mm-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("YYYY-MM-dd");
		System.out.println(sdf.format(date));
		System.out.println(sdf2.format(date));*/

		/*String code = "QFmcqNtSVfyt5nINFegOXL8D9GOe4CcWCw7H8OAV7YloccdRUTYhFTHkESkk//N13YC6IJ6Hmunkwv6U6nKW/w==";
		try {
			String encode = URLEncoder.encode(code, "utf-8");
			System.out.println(encode);
		} catch (UnsupportedEncodingException e1) {

			// TODO Auto-generated catch block
			e1.printStackTrace();

		}*/
		/*String str = R.sg(5, 18).next();

		System.out.println("str的值:" + str);
		System.out.println("str的length:" + str.length());*/

		/*ArrayList<Object> arrayList = new ArrayList<>();
		System.out.println(arrayList);
		System.out.println(Util.isEmpty(arrayList));

		try {
			String translate = TranslateUtil.translate("不清楚", "en");
			System.out.println(translate);
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		 */
		/*NutMap resultMap = new NutMap();
		TIdcardEntity cardentity = new TIdcardEntity();
		cardentity.setCity("北京");
		cardentity.setProvince("北京");
		cardentity.setCounty("中国");
		resultMap.put("北京", cardentity);

		TIdcardEntity cardentity1 = new TIdcardEntity();
		cardentity1.setCity("天津");
		cardentity1.setProvince("天津");
		cardentity1.setCounty("中国");
		resultMap.put("天津", cardentity1);

		TIdcardEntity cardentity2 = new TIdcardEntity();
		cardentity2.setCity("大阪");
		cardentity2.setProvince("大阪");
		cardentity2.setCounty("日本");
		resultMap.put("大阪", cardentity2);

		resultMap.forEach((key, value) -> {
			System.out.println(key + ":" + value);
		});

		for (String key : resultMap.keySet()) {
			System.out.println(key + ":" + ((TIdcardEntity) resultMap.get(key)).getCounty());
		}*/

		/*//转机
		String goFlightNum = "首都机场-羽田机场-青森机场 CA181//JL141 0800/1300//1400/1600";
		//第一个时间 0800
		String substring = goFlightNum.substring(goFlightNum.lastIndexOf(" ") + 1, goFlightNum.lastIndexOf(" ") + 5);

		//第二个时间 1300
		String substring2 = goFlightNum.substring(goFlightNum.lastIndexOf("//") - 4, goFlightNum.lastIndexOf("//"));

		//第三个时间 1400
		String substring10 = goFlightNum.substring(goFlightNum.lastIndexOf("//") + 2, goFlightNum.lastIndexOf("/"));

		//最后一个时间 1600
		String substring9 = goFlightNum.substring(goFlightNum.lastIndexOf("/") + 1);

		//第一个机场名 首都机场
		String substring5 = goFlightNum.substring(0, goFlightNum.indexOf("-", goFlightNum.indexOf("-")));

		//第二个机场 羽田机场
		String substring7 = goFlightNum.substring(goFlightNum.indexOf("-") + 1, goFlightNum.lastIndexOf("-"));

		//最后一个机场名  青森机场
		String substring3 = goFlightNum.substring(goFlightNum.lastIndexOf("-") + 1, goFlightNum.indexOf(" "));

		//第一个航班 CA181
		String substring6 = goFlightNum.substring(goFlightNum.indexOf(" ") + 1, goFlightNum.indexOf("//"));

		//第二个航班 JL141
		String substring8 = goFlightNum.substring(goFlightNum.indexOf("//") + 2, goFlightNum.lastIndexOf(" "));

		//两个航班号 CA181//JL141
		String substring4 = goFlightNum.substring(goFlightNum.indexOf(" ") + 1, goFlightNum.lastIndexOf(" "));

		String substring12 = goFlightNum.substring(goFlightNum.lastIndexOf(" ") + 1);
		StringBuffer stringBuilder1 = new StringBuffer(substring12);
		stringBuilder1.insert(2, ":");
		stringBuilder1.insert(8, ":");
		stringBuilder1.insert(15, ":");
		stringBuilder1.insert(21, ":");

		System.out.println(stringBuilder1.toString());*/

		/*//直飞
		String goFlightNum = "首都国际机场-关西国际机场 CA161 1625/2030";

		//第一个机场名 首都机场
		String substring9 = goFlightNum.substring(0, goFlightNum.indexOf("-", goFlightNum.indexOf("-")));
		//第二个机场 青森机场
		String substring3 = goFlightNum.substring(goFlightNum.indexOf("-") + 1, goFlightNum.indexOf(" "));
		//起飞时间 0800
		String substring10 = goFlightNum.substring(goFlightNum.lastIndexOf(" ") + 1, goFlightNum.indexOf("/"));
		//降落时间 1300
		String substring = goFlightNum.substring(goFlightNum.indexOf("/") + 1);
		//航班号 CA181
		String substring2 = goFlightNum.substring(goFlightNum.indexOf(" ") + 1, goFlightNum.lastIndexOf(" "));

		String substring4 = goFlightNum.substring(goFlightNum.lastIndexOf(" ") + 1);
		StringBuffer stringBuilder1 = new StringBuffer(substring2);
		//stringBuilder1.insert(8, ":");
		stringBuilder1.append("//" + substring2);

		System.out.println(stringBuilder1.toString());*/

		//System.out.println(s.substring(s.indexOf(".", s.indexOf(".")) + 1, s.indexOf(".", s.indexOf(".") + 1)));
	}

	public static ArrayList<Integer> getScenicarray(int[] intlist) {
		//从所有方位中随机出用几个方位
		Random random2 = new Random();
		int nextInt = random2.nextInt(intlist.length) + 1;
		//随机出具体的方位

		int rans = 0;
		ArrayList<Integer> arrayList2 = new ArrayList();
		for (int i = 0; i < nextInt; i++) {
			Random random3 = new Random();
			int nextInt2 = random3.nextInt(intlist.length);
			if (!arrayList2.contains(nextInt2)) {
				arrayList2.add(nextInt2);
			}
		}

		System.out.println(arrayList2);
		return arrayList2;
	}

	public static ArrayList<String> getsomeCount(ArrayList<Integer> intlist, int size) {

		//从所有方位中随机出用几个方位
		Random random2 = new Random();
		int nextInt = random2.nextInt(intlist.size()) + 1;
		System.out.println("1:" + nextInt);

		//随机出给定数组中的具体的哪几个方位的下标
		ArrayList<Integer> arrayList2 = new ArrayList();
		for (int i = 0; i < nextInt; i++) {
			Random random3 = new Random();
			int nextInt2 = random3.nextInt(intlist.size());
			//不能取相同的方位
			if (!arrayList2.contains(nextInt2)) {
				arrayList2.add(nextInt2);
			}
		}
		//判断下所选取的方位个数和随机出来的是否一致，如果不一致，重新随机
		if (arrayList2.size() != nextInt) {
			return getsomeCount(intlist, size);
		}
		//按从小打到顺序排序
		Collections.sort(arrayList2);
		System.out.println("2:" + arrayList2);

		/*//list转数组
		int[] d = new int[arrayList2.size()];
		for (int i = 0; i < arrayList2.size(); i++) {
			d[i] = arrayList2.get(i);
		}*/

		//把随机出的具体方位查出来
		ArrayList<Integer> arrayList3 = new ArrayList();
		for (int i = 0; i < arrayList2.size(); i++) {
			int j = intlist.get(arrayList2.get(i));
			if (arrayList2.size() < 3 && j == 1) {
				return getsomeCount(intlist, size);
			}
			arrayList3.add(intlist.get(arrayList2.get(i)));

		}
		System.out.println("3:" + arrayList3);

		//从随机的每个方位中，再随机出几个景点
		//count1随机出的总景点个数，暂时每个方位最多5个景点
		int count = 0;
		ArrayList<Integer> arrayList = new ArrayList();
		for (int i = 0; i < arrayList3.size(); i++) {
			Random random = new Random();
			int count1 = random.nextInt(arrayList3.get(i)) + 1;
			if (count1 > 3) {
				return getsomeCount(intlist, size);
			}
			System.out.println("4:" + count1);
			arrayList.add(count1);
			count += count1;

		}
		System.out.println("arrayList:" + arrayList);
		System.out.println("count:" + count);
		if (count != size) {
			return getsomeCount(intlist, size);
		}

		//假数据
		ArrayList<ArrayList<String>> arrayList4 = new ArrayList();
		ArrayList<String> arrayList5 = new ArrayList();
		arrayList5.add("一");
		arrayList4.add(arrayList5);
		ArrayList<String> arrayList6 = new ArrayList();
		arrayList6.add("你");
		arrayList6.add("好");
		arrayList6.add("世界");
		arrayList4.add(arrayList6);
		ArrayList<String> arrayList7 = new ArrayList();
		arrayList7.add("算");
		arrayList7.add("是");
		arrayList7.add("换");
		arrayList7.add("看");
		arrayList7.add("十多个");
		arrayList7.add("口蹄疫");
		arrayList4.add(arrayList7);
		ArrayList<String> arrayList8 = new ArrayList();

		for (int i = 0; i < arrayList2.size(); i++) {
			//随机出来的每个方位的数据
			ArrayList<String> arrayList9 = arrayList4.get(arrayList2.get(i));
			//需要随机的个数
			Integer integer2 = arrayList.get(i);
			for (int j = 0; j < integer2; j++) {
				Random random = new Random();
				int nextInt2 = random.nextInt(arrayList9.size());
				String string = arrayList9.get(nextInt2);
				arrayList8.add(string);
				arrayList9.remove(string);
			}

		}
		System.out.println("5:" + arrayList8);
		System.out.println("last:" + arrayList);
		return arrayList8;
	}

	public static String plusDay(int num, Date newDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		Calendar cl = Calendar.getInstance();
		cl.setTime(newDate);
		// cl.set(Calendar.DATE, day);
		cl.add(Calendar.DATE, num);
		String temp = "";
		temp = sdf.format(cl.getTime());
		System.out.println(temp);

		return temp;
	}

	public static void loginThread(String email, String password) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				loginFrance(email, password);
			}
		});

		thread.start();
	}

	public static void loginFrance(String email, String password) {
		// 第一次请求：跳转到登录页面，获取cookie
		Connection con = Jsoup.connect("https://fr.tlscontact.com/cn/BJS/login.php");// 获取连接
		con.header("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");// 配置模拟浏览器
		try {
			con.timeout(60000);
			Response rs = con.execute();
			Map<String, String> cookies = rs.cookies();
			for (String key : cookies.keySet()) {
				System.out.println(key + "--->" + cookies.get(key));
			}
			Document d1 = Jsoup.parse(rs.body());// 转换为Dom树
			Elements select = d1.select("input[name=_sid]");
			String val = select.val();

			List<Element> et = d1.select("#login_form");// 获取form表单，可以通过查看页面源码代码得知
			// 获取，cooking和表单属性，下面map存放post时的数据
			Map<String, String> datas = new HashMap<>();
			for (Element e : et.get(0).getAllElements()) {
				if (e.attr("name").equals("email")) {
					e.attr("value", email);// 设置用户名
				}
				if (e.attr("name").equals("pwd")) {
					e.attr("value", password); // 设置用户密码
				}
				if (e.attr("name").equals("process")) {
					e.attr("value", "login"); // 设置用户密码
				}
				if (e.attr("name").equals("_sid")) {
					e.attr("value", val); // 设置用户密码
				}
				if (e.attr("name").length() > 0) {// 排除空值表单属性
					datas.put(e.attr("name"), e.attr("value"));
				}
			}
			/**
			 * 第二次请求：post表单数据，以及cookie信息,登录
			 * 
			 * **/
			Connection con2 = Jsoup.connect("https://fr.tlscontact.com/cn/BJS/login.php");
			con2.timeout(60000);
			con2.header("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
			// 设置cookie和post上面的map数据
			Response login = con2.ignoreContentType(true).method(Method.POST).data(datas).cookies(rs.cookies())
					.execute();
			// 登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可
			Map<String, String> map = login.cookies();
			for (String s : map.keySet()) {
				System.out.println(s + "      " + map.get(s));
			}

			// 第三次请求：登录成功后，返回一个网址，继续请求
			Connection con3 = Jsoup.connect("https://fr.tlscontact.com/cn/BJS/myapp.php");// 获取连接
			con3.timeout(60000);
			con3.header("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");// 配置模拟浏览器
			Response rs3 = con3.ignoreContentType(true).method(Method.GET).cookies(rs.cookies()).execute();
			System.out.println("rs3:" + rs3.body());

			String rs3body = rs3.body();
			//获取fg_id
			String substring = rs3body.substring(rs3body.indexOf("?") + 1, rs3body.lastIndexOf("\""));

			// 第四次请求：第三次请求返回fg_id,每个用户的识别id，带上id继续请求，进入填写申请页面
			String urlStr = "https://fr.tlscontact.com/cn/BJS/myapp.php?" + substring;
			Connection con4 = Jsoup.connect(urlStr);// 获取连接
			con4.timeout(60000);
			con4.header("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");// 配置模拟浏览器
			Response rs4 = con4.ignoreContentType(true).method(Method.GET).cookies(rs.cookies()).execute();
			System.out.println("rs4:" + rs4.body());

			/*// 第五次请求：创建一个应用程序，真正需要填写的表格出现
			String substring5 = rs3body.substring(rs3body.lastIndexOf("=") + 1, rs3body.lastIndexOf("\""));
			String urlStr5 = "https://fr.tlscontact.com/cn/BJS/form.php?f_id=NEW&f_xref_fg_id=" + substring5;
			Connection con5 = Jsoup.connect(urlStr5);// 获取连接
			con5.timeout(60000);
			con5.header("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");// 配置模拟浏览器
			Response rs5 = con5.ignoreContentType(true).method(Method.POST).cookies(rs.cookies()).execute();
			System.out.println("rs5:" + rs5.body());*/

		} catch (IOException e1) {

			// TODO Auto-generated catch block
			e1.printStackTrace();

		}// 获取响应
	}

	public static void loginUSATEST(String email, String password) throws FailingHttpStatusCodeException, IOException {

		String rs = "";
		/*HttpGet get = new HttpGet("https://www.gotosearch.info/?gws_rd=cr#safe=strict&q=httpclient4.3.3&btnK=Google+%E6%90%9C%E7%B4%A2") ;
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				//信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
					                 }
				        }).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(sslsf).build() ;
			rs = client.execute( get,  new BasicResponseHandler() ) ;
		} catch ( Exception e) {
			e.printStackTrace();
		}
		System.out.println(rs);*/

		String refer = "http://outofmemory.cn/";
		URL link = new URL("https://ceac.state.gov/GenNIV/Default.aspx");
		WebClient wc = new WebClient();
		WebRequest request = new WebRequest(link);
		//request.setProxyHost("120.120.120.x");  
		//request.setProxyPort(8080);  
		////设置请求报文头里的User-Agent字段  
		request.setAdditionalHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
		//其他报文头字段可以根据需要添加  
		wc.getCookieManager().setCookiesEnabled(true);//开启cookie管理  
		wc.getOptions().setJavaScriptEnabled(true);//开启js解析。对于变态网页，这个是必须的  
		wc.getOptions().setCssEnabled(true);//开启css解析。对于变态网页，这个是必须的。  
		wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
		wc.getOptions().setThrowExceptionOnScriptError(false);
		wc.getOptions().setUseInsecureSSL(true);
		wc.getOptions().setTimeout(10000);
		/*//设置cookie。如果你有cookie，可以在这里设置  
		Set<Cookie> cookies = null;
		Iterator<Cookie> i = cookies.iterator();
		while (i.hasNext()) {
			wc.getCookieManager().addCookie(i.next());
		}*/
		//准备工作已经做好了  
		HtmlPage page = null;
		page = wc.getPage(request);
		String html = page.asText();
		System.out.println(html);
		if (page == null) {
			System.out.println("采集失败!!!");
			return;
		}
		String content = page.asText();//网页内容保存在content里  
		if (content == null) {
			System.out.println("采集 失败!!!");
			return;
		}
		//搞定了  
		CookieManager CM = wc.getCookieManager(); //WC = Your WebClient's name  
		Set<Cookie> cookies_ret = CM.getCookies();
	}

	public static void loginUSA(String email, String password) {

		try {
			WebClient webClient = new WebClient();
			//设置webClient的相关参数
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setCssEnabled(false);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			//webClient.getOptions().setTimeout(50000);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			//模拟浏览器打开一个目标网址
			HtmlPage rootPage = webClient.getPage("https://ceac.state.gov/GenNIV/Default.aspx");
			System.out.println("为了获取js执行的数据 线程开始沉睡等待");
			Thread.sleep(3000);//主要是这个线程的等待 因为js加载也是需要时间的
			System.out.println("线程结束沉睡");
			String html = rootPage.asText();
			System.out.println(html);
		} catch (Exception e) {
		}

		try {
			/*// 第一次请求：跳转到登录页面，获取cookie
			Connection con = Jsoup.connect("https://ceac.state.gov/GenNIV/Default.aspx");// 获取连接
			con.header("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");// 配置模拟浏览器
			con.timeout(60000);
			Response rs = con.ignoreContentType(true).execute();
			System.out.println("rs1:" + rs.body());
			Map<String, String> cookies = rs.cookies();
			System.out.println(cookies.size());
			for (String key : cookies.keySet()) {
				System.out.println(key + "--->" + cookies.get(key));
			}
			Document d1 = Jsoup.parse(rs.body());// 转换为Dom树

			List<Element> et = d1.select("#aspnetForm");// 获取form表单，可以通过查看页面源码代码得知
			// 获取，cooking和表单属性，下面map存放post时的数据
			Map<String, String> datas = new HashMap<>();
			for (Element e : et.get(0).getAllElements()) {
				if (e.attr("name").equals("ctl00$SiteContentPlaceHolder$ucLocation$ddlLocation")) {
					e.attr("value", email);// 设置用户名
				}
				if (e.attr("name").equals("ctl00$SiteContentPlaceHolder$ucLocation$IdentifyCaptcha1$txtCodeTextBox")) {
					e.attr("value", password); // 设置用户密码
				}
				if (e.attr("name").length() > 0) {// 排除空值表单属性
					datas.put(e.attr("name"), e.attr("value"));
				}
			}*/
			/**
			 * 第二次请求：post表单数据，以及cookie信息,登录
			 * 
			 * **/
			/*Connection con2 = Jsoup.connect("https://fr.tlscontact.com/cn/BJS/login.php");
			con2.timeout(60000);
			con2.header("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
			// 设置cookie和post上面的map数据
			Response login = con2.ignoreContentType(true).method(Method.POST).data(datas).cookies(rs.cookies())
					.execute();
			// 登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可
			Map<String, String> map = login.cookies();
			for (String s : map.keySet()) {
				System.out.println(s + "      " + map.get(s));
			}

			// 第三次请求：登录成功后，返回一个网址，继续请求
			Connection con3 = Jsoup.connect("https://fr.tlscontact.com/cn/BJS/myapp.php");// 获取连接
			con3.timeout(60000);
			con3.header("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");// 配置模拟浏览器
			Response rs3 = con3.ignoreContentType(true).method(Method.GET).cookies(rs.cookies()).execute();
			System.out.println("rs3:" + rs3.body());

			String rs3body = rs3.body();
			//获取fg_id
			String substring = rs3body.substring(rs3body.indexOf("?") + 1, rs3body.lastIndexOf("\""));

			// 第四次请求：第三次请求返回fg_id,每个用户的识别id，带上id继续请求，进入填写申请页面
			String urlStr = "https://fr.tlscontact.com/cn/BJS/myapp.php?" + substring;
			Connection con4 = Jsoup.connect(urlStr);// 获取连接
			con4.timeout(60000);
			con4.header("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");// 配置模拟浏览器
			Response rs4 = con4.ignoreContentType(true).method(Method.GET).cookies(rs.cookies()).execute();
			System.out.println("rs4:" + rs4.body());*/

		} catch (Exception e1) {

			// TODO Auto-generated catch block
			e1.printStackTrace();

		}// 获取响应
	}

	public static Map<String, Object> sendGet() {
		Map<String, Object> resultMap = Maps.newHashMap();
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = "https://fr.tlscontact.com/cn/BJS/login.php";
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			//connection.setRequestProperty("accept", "*/*");
			//connection.setRequestProperty("connection", "Keep-Alive");
			//connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			resultMap.put("cookies", map);
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		resultMap.put("entityStr", result);
		return resultMap;
	}

	public static String sendPost(String params, Header[] cookies) {
		//PrintWriter out = null;
		OutputStreamWriter out = null;
		BufferedReader in = null;
		String result = "";
		String headerstr = "";
		try {

			for (int i = 0; i < cookies.length; i++) {
				System.out.println("cookie:" + cookies[i].toString());
				String string = cookies[i].toString().substring(11);
				headerstr += string + ";";
			}
			headerstr += " current_locale=en";
			System.out.println(headerstr);

			URL realUrl = new URL("https://fr.tlscontact.com/cn/BJS/login.php");
			// 打开和URL之间的连接
			HttpsURLConnection conn = (HttpsURLConnection) realUrl.openConnection();
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setInstanceFollowRedirects(false);
			//conn.connect();

			/*for (String key : cookies.keySet()) {
				String resultstr = "";
				for (String str : cookies.get(key)) {
					resultstr += str + ";";
				}
				if (!Util.isEmpty(key) && !Util.eq("Content-Type", key)) {
					if (resultstr.endsWith(";")) {
						conn.setRequestProperty(key, resultstr.substring(0, resultstr.length() - 1));
					} else {
						conn.setRequestProperty(key, resultstr);

					}
				}
			}*/

			// 设置通用的请求属性
			conn.setRequestProperty("accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
			conn.setRequestProperty("Cookie", headerstr);
			Map<String, List<String>> requestProperties = conn.getRequestProperties();
			for (String key : requestProperties.keySet()) {
				System.out.println(key + "--->" + requestProperties.get(key));
			}

			out = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
			// 获取URLConnection对象对应的输出流
			//out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.write(params);
			//out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应

			String cookiesHeader = conn.getHeaderField("Set-Cookie");

			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println("POST请求头:" + key + "--->" + map.get(key));
			}

			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally {
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
		return result;
	}

	public static Map<String, Object> testPHP() {

		Map<String, Object> result = Maps.newHashMap();
		String entityStr = "";
		String host = "https://fr.tlscontact.com";
		String path = "/cn/BJS/login.php";
		String method = "GET";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
		Map<String, String> querys = new HashMap<String, String>();
		HttpResponse response;
		try {
			response = HttpUtils.doGet(host, path, method, headers, querys);
			entityStr = EntityUtils.toString(response.getEntity());

			/*Header[] allHeaders = response.getAllHeaders();
			for (Header header : allHeaders) {
				System.out.println("headers:" + header.toString());
			}*/

			Header[] cookies = response.getHeaders("Set-Cookie");

			result.put("entityStr", entityStr);
			result.put("cookies", cookies);

			//System.out.println("GET请求返回的数据：" + entityStr);
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		//System.out.println(entityStr);
		return result;
	}

	public static String toPostRequest(String json, Header[] cookies) {
		String path = "";
		String host = "https://fr.tlscontact.com";
		path = "/cn/BJS/login.php";
		String method = "POST";
		String entityStr = "";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
		String headerstr = "";

		for (int i = 0; i < cookies.length; i++) {
			String string = cookies[i].toString().substring(11);
			System.out.println("String:" + string);
			headerstr += string + ";";
		}
		String[] split = headerstr.split(";");
		headerstr = split[0] + ";" + split[3];
		//headerstr.replace(" expires=Wed, 25-Dec-19 07:43:41 GMT; domain=tlscontact.com; path=/;", "");
		System.out.println(headerstr);
		headers.put("Cookie", headerstr);

		Map<String, String> querys = new HashMap<String, String>();
		HttpResponse response;
		System.out.println("headers:" + headers);
		System.out.println("httpurl:" + (host + path));
		System.out.println("json:" + json);
		try {
			response = HttpUtils.doPost(host, path, method, headers, querys, json);
			entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
			//System.out.println("POST请求返回的数据：" + entityStr);
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		//System.out.println(entityStr + "!!!!");
		/*JSONObject object = new JSONObject(entityStr);
		System.out.println(object.toString());*/
		return entityStr;
	}

	public static String getTranslateResult(String urlString) { //传入要搜索的单词
		URL url;
		String content = "";
		try {
			url = new URL(PreUrl + urlString);
			// 打开URL
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			// 得到输入流，即获得了网页的内容
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String preLine = "";
			String line;
			int flag = 1;
			// 读取输入流的数据，并显示

			while ((line = reader.readLine()) != null) { //获取翻译结果的算法
				System.out.println(line);

				if (preLine.indexOf(TransResultStartFlag) != -1 && line.indexOf(TransResultEndFlag) == -1) {
					System.out.println("111111111111");
					System.out.println(line);
					content += line.replaceAll("　| ", "") + "\n"; //去电源代码上面的半角以及全角字符
					flag = 0;
				}
				if (line.indexOf(TransResultEndFlag) != -1) {
					flag = 1;
				}
				if (flag == 1) {
					preLine = line;
				}
			}
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} //生成完整的URL
		return content;//返回翻译结果
	}

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

	public static boolean isLegalStr(String str) {
		String reg = "[A-Za-z0-9-& ]+";
		boolean isChinese = str.matches(reg);
		return isChinese;
	}

	public static List<Integer> getRandomDates(int[] paramArray, int days) {
		Random random = new Random();
		List<Integer> numbers = new ArrayList<Integer>();
		int sum = 0;
		while (true) {
			int n = random.nextInt(days - 1) + 2;
			System.out.println("n:" + n);
			sum += n;
			numbers.add(n);

			if (numbers.size() > paramArray.length || sum > days) {
				numbers.clear();
				sum = 0;
			}

			if (numbers.size() == paramArray.length && sum == days) {
				break;
			}
		}
		System.out.println(numbers);

		return numbers;
	}

	public static int[] getRandomArray(int[] paramArray, int count) {
		if (paramArray.length < count) {
			return paramArray;
		}
		int[] newArray = new int[count];
		Random random = new Random();
		int temp = 0;//接收产生的随机数
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= count; i++) {
			temp = random.nextInt(paramArray.length);//将产生的随机数作为被抽数组的索引
			if (!(list.contains(temp))) {
				newArray[i - 1] = paramArray[temp];
				list.add(temp);
			} else {
				i--;
			}
		}
		return newArray;
	}

	public static Set<String> getYearDoubleWeekend(int year) {
		Set<String> listDates = new HashSet<String>();
		Calendar calendar = Calendar.getInstance();//当前日期
		calendar.set(year, 6, 1);
		Calendar nowyear = Calendar.getInstance();
		Calendar nexty = Calendar.getInstance();
		nowyear.set(year, 01, 01);//2010-1-1
		nexty.set(year + 1, 01, 01);//2011-1-1
		calendar.add(Calendar.DAY_OF_MONTH, -calendar.get(Calendar.DAY_OF_WEEK));//周六
		Calendar c = (Calendar) calendar.clone();
		for (; calendar.before(nexty) && calendar.after(nowyear); calendar.add(Calendar.DAY_OF_YEAR, -7)) {
			listDates.add(calendar.get(Calendar.YEAR) + "-" + (1 + calendar.get(Calendar.MONTH)) + "-"
					+ calendar.get(Calendar.DATE));
			listDates.add(calendar.get(Calendar.YEAR) + "-" + (1 + calendar.get(Calendar.MONTH)) + "-"
					+ (1 + calendar.get(Calendar.DATE)));
		}
		for (; c.before(nexty) && c.after(nowyear); c.add(Calendar.DAY_OF_YEAR, 7)) {
			listDates.add(c.get(Calendar.YEAR) + "-" + (1 + c.get(Calendar.MONTH)) + "-" + c.get(Calendar.DATE));
			listDates.add(c.get(Calendar.YEAR) + "-" + (1 + c.get(Calendar.MONTH)) + "-" + (1 + c.get(Calendar.DATE)));
		}
		return listDates;
	}

	private static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
		}
	}

	static long timeList(List list) {
		long start = System.currentTimeMillis();
		Object o = new Object();
		for (int i = 0; i < N; i++) {
			list.add(0, o);
		}
		return System.currentTimeMillis() - start;
	}

	static long readList(List list) {
		long start = System.currentTimeMillis();
		for (int i = 0, j = list.size(); i < j; i++) {
			Object object = list.get(i);
		}
		return System.currentTimeMillis() - start;
	}

	static List addList(List list) {
		long start = System.currentTimeMillis();
		Object o = new Object();
		for (int i = 0; i < N; i++) {
			list.add(0, o);
		}
		System.out.println(list.getClass());
		System.out.println(System.currentTimeMillis() - start);
		System.out.println("===========");
		return list;
	}

	static String imgToBse64(String filePath) {
		InputStream inputStream = null;
		HttpURLConnection httpURLConnection = null;
		byte[] data = null;
		try {
			URL url = new URL(filePath);//创建的URL  
			if (url != null) {
				httpURLConnection = (HttpURLConnection) url.openConnection();//打开链接  
				httpURLConnection.setConnectTimeout(3000);//设置网络链接超时时间，3秒，链接失败后重新链接  
				httpURLConnection.setDoInput(true);//打开输入流  
				httpURLConnection.setRequestMethod("GET");//表示本次Http请求是GET方式  
				int responseCode = httpURLConnection.getResponseCode();//获取返回码  
				if (responseCode == 200) {//成功为200  
					//从服务器获得一个输入流  
					inputStream = httpURLConnection.getInputStream();
					data = new byte[inputStream.available()];
					inputStream.read(data);
					inputStream.close();
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Base64.encodeBase64String(data);
	}

}
