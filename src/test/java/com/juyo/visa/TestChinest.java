package com.juyo.visa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.uxuexi.core.common.util.DateUtil;

public class TestChinest {

	public static void main(String[] args) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		Date gotripdate = new Date();
		List<String> holidayDate = new ArrayList<>(getYearDoubleWeekend(2018));
		int stayday = 10;
		int count = 0;
		int totalday = stayday + count;
		for (int i = 0; i < totalday; i++) {
			String dateStr = sdf.format(DateUtil.addDay(gotripdate, i + 1));
			if (holidayDate.contains(dateStr)) {
				count++;
				totalday++;
			}
			System.out.println("i:" + i);
			System.out.println(stayday + count);
			System.out.println(count + "=======");
		}

		/*String newString = "{'200001':{'wealth_title':'银行流水325','wealth_value':'345','wealth_type':'银行流水'}}";
		//org.json.JSONObject jsonObject = new org.json.JSONObject(newString);
		//System.out.println(jsonObject);
		Map maps = JSON.parseObject(newString, Map.class);
		for (Object map : maps.entrySet()) {
			System.out.println(((Map.Entry) map).getKey() + "     " + ((Map.Entry) map).getValue());
		}*/

		/*String scene = "orderid=" + 1 + "&applicantid=" + 2;
		String encode = URL.encode(scene);
		System.out.println(encode);*/

		/*String province = "自治区";
		if (province.length() > 3 && province.endsWith("自治区")) {
			province = province.substring(0, province.length() - 3);
		}
		System.out.println(province);*/
		/*String test1 = "0123456789abcde!@#$%^& 水电费";
		char[] chars_test1 = test1.toCharArray();
		for (int i = 0; i < chars_test1.length; i++) {
			String temp = String.valueOf(chars_test1[i]);
			// 判断是全角字符
			if (temp.matches("[^\\x00-\\xff]")) {
				System.out.println("全角   " + temp);
			}
			// 判断是半角字符
			else {
				System.out.println("半角    " + temp);
			}
		}*/

		/*String a = "asdb";
		StringBuffer sb = new StringBuffer("34243");
		sb.insert(0, a);
		System.out.println(sb);
		*/
		/*
				String someStr = "ABCDEFG";
				String search = "AOJOIHGRJIOTH";
				CharSequence seq = new String(someStr);
				for (int i = 0; i < seq.length(); i++) {
					char charAt = seq.charAt(i);
				}
				char[] charArray = someStr.toCharArray();

				for (int i = 0; i < charArray.length; i++) {
					char c = charArray[i];

					System.out.println(charArray[i]);
				}*/

		/*int max = 8;
		int min = 1;
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;

		System.out.println(s);*/

		/*int[] paramArray = { 1, 2, 3, 4, 5, 6 };

		int count = 4;
		int[] newArray = new int[count];
		Random random = new Random();
		int min = 2;
		count = random.nextInt(count) % (count - min + 1) + min;
		System.out.println("count:" + count);

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
		for (int i = 0; i < newArray.length; i++) {
			System.out.println(newArray[i]);
		}*/

		/*int num = 0;
		ArrayList<Integer> datesList = new ArrayList<>();
		datesList.add(1);
		datesList.add(3);
		datesList.add(6);
		datesList.add(9);
		datesList.add(13);
		for (int i = 0; i < 2; i++) {
			num += datesList.get(i);
		}
		System.out.println(num);*/

		/*Random random = new Random();
		int days = 80;
		int[] paramArray = { 1, 2, 3 };

		List<Integer> numbers = new ArrayList<Integer>();
		int sum = 0;

		while (true) {
			int n = random.nextInt(days - 1) + 2;
			System.out.println(n);
			sum += n;
			numbers.add(n);

			if (numbers.size() > paramArray.length || sum > days) {
				numbers.clear();
				sum = 0;
			}

			if (numbers.size() == paramArray.length && sum == days) {
				break;
				//System.out.println(numbers);
			}
		}
		System.out.println(numbers);*/

		/*String date = "06/09/1985";
		String substring = date.substring(3, 5);
		System.out.println(substring);*/

		/*int[] paramArray = { 7, 9, 13, 14, 15 };
		for (int i = 0; i < paramArray.length; i++) {
			if (paramArray[i] == 13) {
				paramArray = ArrayUtils.remove(paramArray, i);
			}
		}

		for (int i : paramArray) {
			System.out.println(i);
		}
		*/
		/*String encode = URL
				.encode("Ynsxg/DQ8PbcWgUtLwfgIhBXnnJaPeWCE9xBTGBfoG5kbT7kteAQepn2gUXjsPVxrxp+PxlfW/Lba1sr9lT7Rg==");
		System.out.println(encode);*/

		/*String str1 = "哈fafa";  
		String str2 = "afa";
		String reg = "[\\u4e00-\\u9fa5]+";  
		boolean r = str1.matches(reg);
		boolean s = str2.matches(reg);//true  

		System.out.println(!(r&&s));*/

		/*int[] strArray = { 20, 22, 24 };

		int[] randomArray = getRandomArray(strArray, 2);
		for (int i : randomArray) {
			System.out.println(i);
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

}
