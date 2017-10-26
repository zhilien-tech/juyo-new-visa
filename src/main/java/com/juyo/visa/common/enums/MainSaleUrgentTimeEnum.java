package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

public enum MainSaleUrgentTimeEnum implements IEnum {

	THREE(1, "3个工作日"), FIVE(2, "5个工作日"), SEVEN(3, "7个工作日"), ;

	private int key;
	private String value;

	private MainSaleUrgentTimeEnum(final int key, final String value) {
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
