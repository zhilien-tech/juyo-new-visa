package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * <p>
 * 婚姻状况  枚举
 * @author   
 * @Date	 
 */
public enum MarryStatusEnum implements IEnum {
	DANSHEN(4, "单身"), YIHUN(1, "已婚"), LIYI(2, "离异"), SANGOU(3, "丧偶");
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
