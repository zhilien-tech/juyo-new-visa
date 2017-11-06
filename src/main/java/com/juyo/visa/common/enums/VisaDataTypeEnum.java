/**
 * VisaDataTypeEnum.java
 * com.juyo.visa.common.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * TODO 已有签证类型
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年10月31日 	 
 */
public enum VisaDataTypeEnum implements IEnum {
	EMPLOYED(1, "在职"), UNEMPLOYED(2, "无业"), CHILD(3, "儿童"), STUDENT(4, "学生"), RETIREMENT(5, "退休");
	private int key;
	private String value;

	private VisaDataTypeEnum(final int key, final String value) {
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
