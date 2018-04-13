package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 用户状态
 * 0-未激活，1-激活，2-冻结
 *
 */
public enum UserStatusEnum implements IEnum {

	INVALID(0, "未激活"),VALID(1, "激活"),FROZEN(2,"冻结");
	private int key;
	private String value;

	private UserStatusEnum(final int key, final String value) {
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
