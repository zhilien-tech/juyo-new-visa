/**
 * IssueValidityEnum.java
 * com.juyo.visa.common.enums
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * TODO 签发有效期
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年11月8日 	 
 */
public enum IssueValidityEnum implements IEnum {

	FIVEYEAR(5, "5年"), TENYEAR(10, "10年");
	private int key;
	private String value;

	private IssueValidityEnum(final int key, final String value) {
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
