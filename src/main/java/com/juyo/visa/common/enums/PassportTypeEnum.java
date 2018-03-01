package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 签证年限
 * <p>
 * 
 * @author   彭辉
 * @Date	 2017年10月20日
 */
public enum PassportTypeEnum implements IEnum {
	FIVE(1, "10年"), TEN(2, "5年");
	private int key;
	private String value;

	private PassportTypeEnum(final int key, final String value) {
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
