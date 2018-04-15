package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 签证状态 枚举
 */
public enum VisaStatusEnum implements IEnum {
	HANDLING_VISA(1, "办理中"), PASSING_VISA(2, "通过");
	private int key;
	private String value;

	private VisaStatusEnum(final int key, final String value) {
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
