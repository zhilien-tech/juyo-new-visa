package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 是否
 * <p>
 * 是 or 否
 * @author   彭辉
 * @Date	 2017年10月20日
 */
public enum MainOrViceEnum implements IEnum {
	YES(1, "主申请人"), NO(0, "副申请人");
	private int key;
	private String value;

	private MainOrViceEnum(final int key, final String value) {
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
