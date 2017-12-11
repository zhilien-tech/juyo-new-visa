package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * <p>
 * 男 or 女
 * @author   彭辉
 * @Date	 2017年10月20日
 */
public enum MarryStatusEnum implements IEnum {
	YIHUN(1, "已婚"), LIYI(2, "离异"), SANGOU(3, "丧偶"), DANSHEN(4, "单身"), QITA(5, "其它");
	private int key;
	private String value;

	private MarryStatusEnum(final int key, final String value) {
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
