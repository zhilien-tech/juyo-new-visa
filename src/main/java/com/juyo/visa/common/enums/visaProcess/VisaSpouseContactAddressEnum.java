package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 签证信息---国籍
 */
public enum VisaSpouseContactAddressEnum implements IEnum {
	CHINA(1, "中国"), US(2, "美国");
	private int key;
	private String value;

	private VisaSpouseContactAddressEnum(final int key, final String value) {
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
