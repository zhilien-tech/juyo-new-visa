/**
 * TestMail.java
 * com.juyo.visa
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import com.juyo.visa.common.util.PinyinTool;
import com.juyo.visa.common.util.PinyinTool.Type;

/**
 * 测试发邮件
 */
public class TestPinyin {

	public static void main(String args[]) {
		PinyinTool tool = new PinyinTool();
		try {
			String name = "LIUYA";
			System.out.println("刘亚壮的运行测试结果为====" + tool.toPinYin("刘亚壮", "", Type.UPPERCASE));
			System.out.println(name.length());
		} catch (BadHanyuPinyinOutputFormatCombination e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
}
