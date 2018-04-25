package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;
/**
 * 
 * 回邮类型 枚举
 *
 */
public enum MainBackMailSourceTypeEnum implements IEnum {

	KUAIDI(1, "快递"), QIANTAI(2, "前台"), QITA(3, "其它");

	private int key;
	private String value;

	private MainBackMailSourceTypeEnum(final int key, final String value) {
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
