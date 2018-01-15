/**
 * TestMD5.java
 * com.juyo.visa.common.comstants
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.common.comstants;

import com.juyo.visa.common.access.AccessConfig;
import com.juyo.visa.common.access.sign.MD5;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年10月23日 	 
 */
public class TestMD5 {

	public static void main(String[] args) {
		String sign = MD5.sign("000000", AccessConfig.password_secret, AccessConfig.INPUT_CHARSET);
		System.out.println(sign);
	}
}
