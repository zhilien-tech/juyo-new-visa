package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 经营范围枚举
 * <p>
 * @author   彭辉
 * @Date	 2017年10月20日
 */
public enum BusinessScopesEnum implements IEnum {
	JAPAN(1, "日本");
	private int key;
	private String value;

	private BusinessScopesEnum(final int key, final String value) {
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
