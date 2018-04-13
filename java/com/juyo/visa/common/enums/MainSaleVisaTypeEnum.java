package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

public enum MainSaleVisaTypeEnum implements IEnum {

	SINGLE(1, "单次"), SIX(2, "冲绳东北六县三年多次"), PUTONGTHREE(3, "普通三年多次"), PUTONGFIVE(4, "普通五年多次");

	private int key;
	private String value;

	private MainSaleVisaTypeEnum(final int key, final String value) {
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
