package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 没有直客情况下的客户来源 枚举
 * <p>
 * 
 * @author   
 * @Date	 
 */
public enum NoZHIKECustomerTypeEnum implements IEnum {
	XIANSHANG(1, "线上"), OTS(2, "OTS"), XIANXIA(3, "线下");
	private int key;
	private String value;

	private NoZHIKECustomerTypeEnum(final int key, final String value) {
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
