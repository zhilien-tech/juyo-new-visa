package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 客户管理类型
 * <p>
 * @author   彭辉
 * @Date	 2017年10月20日
 */
public enum CustomerTypeEnum implements IEnum {
	XIANSHANG(1, "线上"), OTS(2, "OTS"), XIANXIA(3, "线下"), ZHIKE(4, "直客");
	private int key;
	private String value;

	private CustomerTypeEnum(final int key, final String value) {
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
