package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * <p>
 * 财产信息
 * @author   彭辉
 * @Date	 2017年10月20日
 */
public enum ApplicantJpWealthEnum implements IEnum {
	BANK(1, "银行流水"), CAR(2, "车产"), HOME(3, "房产"), LICAI(4, "理财");
	private int key;
	private String value;

	private ApplicantJpWealthEnum(final int key, final String value) {
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
