package com.juyo.visa;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.juyo.visa.common.util.TranslateUtil;
import com.uxuexi.core.common.util.Util;

public class TestChinest {

	static final int N = 50000;

	public static void main(String[] args) {
		/*String str = R.sg(5, 18).next();

		System.out.println("str的值:" + str);
		System.out.println("str的length:" + str.length());*/

		ArrayList<Object> arrayList = new ArrayList<>();
		System.out.println(arrayList);
		System.out.println(Util.isEmpty(arrayList));

		try {
			String translate = TranslateUtil.translate("不清楚", "en");
			System.out.println(translate);
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}

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

		System.out.println(substring4);*/

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

		System.out.println(substring2);
		 */
		//System.out.println(s.substring(s.indexOf(".", s.indexOf(".")) + 1, s.indexOf(".", s.indexOf(".") + 1)));
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

}
