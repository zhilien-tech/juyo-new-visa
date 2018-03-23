package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 家庭信息---父母亲身体状态
 */
public enum VisaFamilyInfoEnum implements IEnum {

	US_CITIZENS(1, "美国公民"), PERMANENT_OCCUPANTS(2, "美国合法永久居住者"), NONIMMIGRANT(3, "非移民"), ORTHER(4, "其他/不知道");

	private int key;
	private String value;

	private VisaFamilyInfoEnum(final int key, final String value) {
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
