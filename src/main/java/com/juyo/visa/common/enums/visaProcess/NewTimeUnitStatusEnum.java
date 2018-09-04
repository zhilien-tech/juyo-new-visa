package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 以前的美国旅游信息---时间单位
 */
public enum NewTimeUnitStatusEnum implements IEnum {
	YEAR(1, "日"), MONTH(2, "周"), WEEK(3, "月"), DAY(4, "年");
	private int key;
	private String value;

	private NewTimeUnitStatusEnum(final int key, final String value) {
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
