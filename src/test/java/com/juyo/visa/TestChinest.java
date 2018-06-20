package com.juyo.visa;

public class TestChinest {

	public static void main(String[] args) {
		/*String str1 = "哈fafa";  
		String str2 = "afa";
		String reg = "[\\u4e00-\\u9fa5]+";  
		boolean r = str1.matches(reg);
		boolean s = str2.matches(reg);//true  
		
		System.out.println(!(r&&s));*/

		String goFlightNum = "首都机场-羽田机场-青森机场  CA181//JL141  0800/1300//1400/1600";
		/*String substring = goFlightNum.substring(goFlightNum.lastIndexOf(" "),
				goFlightNum.indexOf("/", goFlightNum.indexOf("/") + 1));*/
		String substring = goFlightNum.substring(goFlightNum.lastIndexOf(" ") + 1);
		String substring2 = substring.substring(substring.lastIndexOf("/") + 1);

		String substring3 = goFlightNum.substring(goFlightNum.lastIndexOf("-") + 1, goFlightNum.indexOf(" "));
		String substring4 = goFlightNum.substring(goFlightNum.indexOf("-", goFlightNum.lastIndexOf("-")) + 1,
				goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")));

		System.out.println(substring4);
		//System.out.println(s.substring(s.indexOf(".", s.indexOf(".")) + 1, s.indexOf(".", s.indexOf(".") + 1)));
	}
}
