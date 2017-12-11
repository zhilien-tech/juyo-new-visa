package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * <p>
 * 游客快递方式
 * @author   彭辉
 * @Date	 2017年12月10日
 */
public enum YouKeExpressTypeEnum implements IEnum {
	STORE_SEND(1, "门店送"), SELF_SEND(2, "自送");
	private int key;
	private String value;

	private YouKeExpressTypeEnum(final int key, final String value) {
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
