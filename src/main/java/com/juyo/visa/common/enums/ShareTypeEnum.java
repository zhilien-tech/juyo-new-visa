package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 
 * <p>
 * 分享类型 枚举
 * @author   
 * @Date	 
 */
public enum ShareTypeEnum implements IEnum {
	UNIFIED(1, "统一联系人"), SINGLE(2, "单独分享");
	private int key;
	private String value;

	private ShareTypeEnum(final int key, final String value) {
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
