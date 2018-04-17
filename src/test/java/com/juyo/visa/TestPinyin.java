/**
 * TestMail.java
 * com.juyo.visa
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang3.ArrayUtils;

import com.juyo.visa.common.util.PinyinTool;
import com.juyo.visa.common.util.PinyinTool.Type;
import com.juyo.visa.common.util.TranslateUtil;

/**
 * 测试发邮件
 */
public class TestPinyin {
	static final String ENGLISH = "en"; // 英

	public static void main(String args[]) throws Exception {
		String translate = TranslateUtil.translate("河北省/保定市/清苑区/徐家湾乡/徐庄村后村组/奉贤街/21号楼/202", ENGLISH);
		String translate1 = TranslateUtil.translate("徐家湾乡徐庄村后村组/清苑区/保定市/河北省", ENGLISH);
		String[] strings = translate.split("/");
		ArrayUtils.reverse(strings);
		String result = "";
		for (int i = 0; i < strings.length; i++) {
			result += strings[i] + ' ';
		}
		System.out.println("中文翻译成英语：" + result);
		//System.out.println("中文翻译成英语：" + translate1);
		//System.out.println("中文翻译成英语：" + new StringBuilder(translate).reverse());
		PinyinTool tool = new PinyinTool();
		try {
			String name = "LIUYA";
			System.out.println("刘亚壮的运行测试结果为====" + tool.toPinYin("/中国", "", Type.UPPERCASE));
			System.out.println(name.length());
		} catch (BadHanyuPinyinOutputFormatCombination e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
}
