package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 人员 基本信息
 * <p>
 * 是否有曾用名 或 是否另有国籍
 * @author   彭辉
 * @Date	 2018年02月09日
 */
public enum IsHasOrderOrNotEnum implements IEnum {
	HAS(1, "是"), NO(2, "否");
	private int key;
	private String value;

	private IsHasOrderOrNotEnum(final int key, final String value) {
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
