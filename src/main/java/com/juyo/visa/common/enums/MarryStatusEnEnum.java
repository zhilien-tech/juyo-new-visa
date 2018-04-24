package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * <p>
 * 男 or 女
 * @author   彭辉
 * @Date	 2017年10月20日
 */
public enum MarryStatusEnEnum implements IEnum {
	DANSHEN(4, "SINGLE"), YIHUN(1, "MARRIED"), LIYI(2, "DIVORCED"), SANGOU(3, "WIDOWED");
	private int key;
	private String value;

	private MarryStatusEnEnum(final int key, final String value) {
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
