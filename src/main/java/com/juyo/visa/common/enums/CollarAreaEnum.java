/**
 * CollarAreaEnum.java
 * com.juyo.visa.common.enums
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * TODO 领区枚举
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年10月31日 	 
 */
public enum CollarAreaEnum implements IEnum {

	BEIJING(1, "北京"), SHANGHAI(2, "上海"), GUANGZHOU(5, "广州"), CHENGDU(4, "成都"), SHENYANG(3, "沈阳"), QINGDAO(6, "青岛"), DALIAN(
			7, "大连");

	private int key;
	private String value;

	private CollarAreaEnum(final int key, final String value) {
		this.value = value;
		this.key = key;
	}

	@Override
	public String key() {
		return String.valueOf(key);
	}

	@Override
	public String value() {
		return value;
	}

	public int intKey() {
		return key;
	}
}
