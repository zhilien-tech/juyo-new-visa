package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 快递方式
 * @author   彭辉
 * @Date	 2017年11月16日
 */
public enum ExpressTypeEnum implements IEnum {
	EXPRESS(1, "快递"), STORESEND(2, "门店送"), SELFSEND(3, "自送");
	private int key;
	private String value;

	private ExpressTypeEnum(final int key, final String value) {
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
