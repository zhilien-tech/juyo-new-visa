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
		String translate = TranslateUtil.translate("内蒙古//赤峰市//松山区//林河路/临河小区46号楼/1单元/202室", ENGLISH);
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

		String address = "内蒙古/赤峰市/松山区/林河路/临河小区46号楼/1单元/202";
		StringBuffer sb = new StringBuffer(address);
		System.out.println(sb.toString());
		//内蒙古，新疆，广西，宁夏，西藏，屯，州，盟，旗，苏木
		//sb = (StringBuffer) addLine(address, "内蒙古", sb);
		if (address.contains("内蒙古")) {
			sb.insert(sb.toString().indexOf("内蒙古") + 3, "/");
		}
		if (address.contains("新疆")) {
			sb.insert(sb.toString().indexOf("新疆") + 3, "/");
		}
		if (address.contains("广西")) {
			sb.insert(sb.toString().indexOf("广西") + 3, "/");
		}
		if (address.contains("宁夏")) {
			sb.insert(sb.toString().indexOf("宁夏") + 3, "/");
		}
		if (address.contains("西藏")) {
			sb.insert(sb.toString().indexOf("西藏") + 3, "/");
		}
		sb = (StringBuffer) addLine(address, "省", sb);
		sb = (StringBuffer) addLine(address, "屯", sb);
		sb = (StringBuffer) addLine(address, "州", sb);
		sb = (StringBuffer) addLine(address, "盟", sb);
		sb = (StringBuffer) addLine(address, "旗", sb);
		sb = (StringBuffer) addLine(address, "市", sb);
		sb = (StringBuffer) addLine(address, "区", sb);
		sb = (StringBuffer) addLine(address, "县", sb);
		sb = (StringBuffer) addLine(address, "乡", sb);
		sb = (StringBuffer) addLine(address, "村", sb);
		sb = (StringBuffer) addLine(address, "路", sb);
		sb = (StringBuffer) addLine(address, "楼", sb);
		if (address.contains("单元")) {
			sb.insert(sb.toString().indexOf("单元") + 2, "/");
		}
		if (address.contains("苏木")) {
			sb.insert(sb.toString().indexOf("苏木") + 2, "/");
		}
		String aa = "";
		String translateStr = sb.toString();
		if (translateStr.contains("/")) {
			String[] strings2 = translateStr.split("/");
			ArrayUtils.reverse(strings2);
			for (int i = 0; i < strings2.length; i++) {
				aa += strings2[i] + ' ';
			}
		}
		System.out.println("最终翻译" + TranslateUtil.translate(aa, ENGLISH));
		/*if (address.contains("新疆")) {
			sb.insert(address.indexOf("新疆") + 1, "/");
		}
		if (address.contains("省")) {
			sb.insert(address.indexOf("省") + 1, "/");
		}
		if (address.contains("市")) {
			sb.insert(address.indexOf("市") + 1, "/");
		}
		if (address.contains("区")) {
			sb.insert(address.indexOf("区") + 1, "/");
		}
		if (address.contains("县")) {
			sb.insert(address.indexOf("县") + 1, "/");
		}
		if (address.contains("乡")) {
			sb.insert(address.indexOf("乡") + 1, "/");
		}*/
		System.out.println(sb.toString());

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

	public static Object addLine(String address, String type, StringBuffer sb) {
		if (address.contains(type)) {
			sb.insert(sb.toString().indexOf(type) + 1, "/");
		}
		return sb;
	}
}
