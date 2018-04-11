/**
 * TestMail.java
 * com.juyo.visa
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa;

import com.juyo.visa.common.util.TranslateUtil;

/**
 * 测试发邮件
 */
public class TestPinyin {
	static final String ENGLISH = "en"; // 英

	public static void main(String args[]) throws Exception {
		String translate = TranslateUtil.translate("河北省/保定市/清苑区/徐家湾乡徐庄村后村组", ENGLISH);
		System.out.println("中文翻译成英语：" + translate);
		System.out.println("中文翻译成英语：" + new StringBuilder(translate).reverse());
		/*PinyinTool tool = new PinyinTool();
		try {
			String name = "LIUYA";
			System.out.println("刘亚壮的运行测试结果为====" + tool.toPinYin("刘亚壮", "", Type.UPPERCASE));
			System.out.println(name.length());
		} catch (BadHanyuPinyinOutputFormatCombination e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}*/
	}
}
