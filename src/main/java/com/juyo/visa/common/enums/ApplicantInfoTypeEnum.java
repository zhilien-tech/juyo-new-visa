package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * <p>
 * 申请人 基本信息、护照信息、签证信息
 * @author   彭辉
 * @Date	 2017年10月20日
 */
public enum ApplicantInfoTypeEnum implements IEnum {
	BASE(1, "基本信息"), PASSPORT(2, "护照信息"), VISA(3, "签证信息");
	private int key;
	private String value;

	private ApplicantInfoTypeEnum(final int key, final String value) {
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
