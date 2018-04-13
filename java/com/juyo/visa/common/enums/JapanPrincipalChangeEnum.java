package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * <p>
 * 负责人变更
 * @author   彭辉
 * @Date	 2018年01月18日
 */
public enum JapanPrincipalChangeEnum implements IEnum {
	CHANGE_PRINCIPAL_OF_ORDER(88, "负责人变更");

	private int key;
	private String value;

	private JapanPrincipalChangeEnum(final int key, final String value) {
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
