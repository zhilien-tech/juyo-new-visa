package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 以前的美国旅游信息---时间单位
 */
public enum TimeUnitStatusEnum implements IEnum {
	YEAR(1, "年"), MONTH(2, "月"), WEEK(3, "周"), DAY(4, "日"), LESS_24HOURS(5, "少于24小时");
	private int key;
	private String value;

	private TimeUnitStatusEnum(final int key, final String value) {
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
