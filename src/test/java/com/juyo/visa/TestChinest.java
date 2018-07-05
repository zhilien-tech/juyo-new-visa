package com.juyo.visa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestChinest {

	public static void main(String[] args) {

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
		}

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

}
