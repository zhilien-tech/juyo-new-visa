package com.juyo.visa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;

import com.juyo.visa.common.baidu.TransApi;

public class TestChinest {

	static final int N = 50000;
	private final static String PreUrl = "http://www.baidu.com/s?wd="; //百度搜索URL
	private final static String TransResultStartFlag = "<span class=\"op_dict_text2\">"; //翻译开始标签
	private final static String TransResultEndFlag = "</span>"; //翻译结束标签

	private static final String APP_ID = "20181211000246598";
	private static final String SECURITY_KEY = "8_MjFaIQyqSO5FvZCvm7";

	public static void main(String[] args) {

		TransApi api = new TransApi(APP_ID, SECURITY_KEY);

		String query = "不知道";
		System.out.println(api.getTransResult(query, "auto", "en"));

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
