package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 美国签证 是否
 * <p>
 * 是 or 否
 * @author 
 * @Date	 
 */
public enum YesOrNoEnum implements IEnum {
	YES(1, "是"), NO(2, "否");
	private int key;
	private String value;

	private YesOrNoEnum(final int key, final String value) {
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
