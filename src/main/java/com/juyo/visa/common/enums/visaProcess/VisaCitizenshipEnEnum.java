package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 签证信息---国籍（英文）
 */
public enum VisaCitizenshipEnEnum implements IEnum {
	CHINA(1, "CHINA"), US(2, "UNITED STATES OF AMERICA");

	private int key;
	private String value;

	private VisaCitizenshipEnEnum(final int key, final String value) {
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
