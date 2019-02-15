package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 美国各个州 枚举
 */
public enum VisaHighestEducationEnum implements IEnum {
	DAZHUAN(3, "大专"), BENKE(4, "本科"), BOSHI(5, "硕士"), SHUOSHI(6, "博士");

	private int key;
	private String value;

	private VisaHighestEducationEnum(final int key, final String value) {
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
