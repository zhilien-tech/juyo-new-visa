package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 主申请人 备注
 */
public enum MainApplicantRemarkEnum implements IEnum {
	ZHUKA(1, "主卡"), FRIEND(2, "朋友"), TONGSHI(3, "同事"), TONGXUE(4, "同学");
	private int key;
	private String value;

	private MainApplicantRemarkEnum(final int key, final String value) {
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
