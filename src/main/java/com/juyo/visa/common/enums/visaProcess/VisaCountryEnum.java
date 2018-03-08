package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 签证国 枚举
 */
public enum VisaCountryEnum implements IEnum {
	USA(1, "美国"), England(2, "英国");
	private int key;
	private String value;

	private VisaCountryEnum(final int key, final String value) {
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
