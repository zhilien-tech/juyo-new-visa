package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 回邮方式
 *
 */
public enum MainBackMailTypeEnum implements IEnum {

	KUAIDI(1, "快递"), ZIQU(2, "自取"), SHIGUAN(3, "使馆邮寄"), NEIBU(4, "内部交接");

	private int key;
	private String value;

	private MainBackMailTypeEnum(final int key, final String value) {
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
