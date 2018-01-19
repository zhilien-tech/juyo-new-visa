package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * <p>
 * 保存或更新呢
 * @author   彭辉
 * @Date	 2018年01月19日
 */
public enum SaveOrUpdateEnum implements IEnum {
	SAVE(1, "保存"), UPDATE(2, "更新");
	private int key;
	private String value;

	private SaveOrUpdateEnum(final int key, final String value) {
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
