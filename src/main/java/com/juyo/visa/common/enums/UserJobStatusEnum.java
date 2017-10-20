package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 用户就职状态
 * 1-在职，2-离职
 *
 */
public enum UserJobStatusEnum implements IEnum {

	ON(1, "在职"), OFF(2, "离职");
	private int key;
	private String value;

	private UserJobStatusEnum(final int key, final String value) {
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
