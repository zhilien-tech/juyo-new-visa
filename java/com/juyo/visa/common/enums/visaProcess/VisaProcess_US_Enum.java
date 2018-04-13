package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 美国签证流程 枚举
 */
public enum VisaProcess_US_Enum implements IEnum {
	FILLIN_FORMATION(1, "填写资料"), REVIEW_INFORMATION(2, "资料审核");
	private int key;
	private String value;

	private VisaProcess_US_Enum(final int key, final String value) {
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
