package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

public enum MainSaleTripTypeEnum implements IEnum {

	DAI(1, "代"), ZHEN(2, "真");

	private int key;
	private String value;

	private MainSaleTripTypeEnum(final int key, final String value) {
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
