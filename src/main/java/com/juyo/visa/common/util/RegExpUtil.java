/**
 * RegExpUtil.java
 * com.juyo.visa.common.util
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年12月25日 	 
 */
public class RegExpUtil {

	public static boolean isMobileLegal(String str) {
		String regExp = "^(1[3,4,5,7,8])\\d{9}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}
}
