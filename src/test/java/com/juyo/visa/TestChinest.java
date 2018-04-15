package com.juyo.visa;

public class TestChinest {

	public static void main(String[] args) {
		String str1 = "哈fafa";  
		String str2 = "afa";
		String reg = "[\\u4e00-\\u9fa5]+";  
		boolean r = str1.matches(reg);
		boolean s = str2.matches(reg);//true  
		
		System.out.println(!(r&&s));
	}
}
