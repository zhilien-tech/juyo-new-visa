package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

public enum UserTypeEnum implements IEnum {

	PERSONNEL(1, "工作人员"), TOURIST_IDENTITY(2, "游客身份");

	private int key;
	private String value;

	private UserTypeEnum(final int key, final String value) {
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
