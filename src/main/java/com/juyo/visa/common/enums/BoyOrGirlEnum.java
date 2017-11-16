package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * <p>
 * 男 or 女
 * @author   彭辉
 * @Date	 2017年10月20日
 */
public enum BoyOrGirlEnum implements IEnum {
	MAN(1, "男"), WOMAN(2, "女");
	private int key;
	private String value;

	private BoyOrGirlEnum(final int key, final String value) {
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
