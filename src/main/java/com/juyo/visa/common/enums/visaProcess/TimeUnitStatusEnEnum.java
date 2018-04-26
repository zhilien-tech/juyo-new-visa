package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 以前的美国旅游信息---时间单位(英文)
 */
public enum TimeUnitStatusEnEnum implements IEnum {
	YEARS(1, "YEAR"), MONTHS(2, "MONTH"), WEEKS(3, "WEEK"), DAYS(4, "DAY"), LESS_24HOURS(5, "LESS THAN 24 HOURS");
	private int key;
	private String value;

	private TimeUnitStatusEnEnum(final int key, final String value) {
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
