package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 美国订单状态 枚举
 * @author   彭辉
 * @Date	 2017年10月20日
 * 
 * CHANGE_PRINCIPAL_OF_ORDER(88, "负责人变更")
 */
public enum USOrderStatusEnum implements IEnum {
	PLACE_ORDER(1, "下单");
	private int key;
	private String value;

	private USOrderStatusEnum(final int key, final String value) {
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
