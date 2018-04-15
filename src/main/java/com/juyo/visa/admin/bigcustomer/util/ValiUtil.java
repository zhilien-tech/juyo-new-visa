/**
 * ValiUtil.java
 * com.juyo.visa.admin.bigcustomer.util
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.bigcustomer.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2018年4月15日 	 
 */
public class ValiUtil {

	public static boolean isChineseStr(String str) {
		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
		char c[] = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			Matcher matcher = pattern.matcher(String.valueOf(c[i]));
			if (!matcher.matches()) {
				return false;
			}
		}
		return true;
	}

}
