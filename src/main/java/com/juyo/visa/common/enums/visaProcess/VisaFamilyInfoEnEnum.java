package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 家庭信息---父母亲身体状态(英文)
 */
public enum VisaFamilyInfoEnEnum implements IEnum {

	US_CITIZENS(1, "U.S. Citizen"), PERMANENT_OCCUPANTS(2, "U.S. Legal Permanent Resident (LPR) "), NONIMMIGRANT(3, "NonImmigrant"), ORTHER(4, "Other/I Don't Know");

	private int key;
	private String value;

	private VisaFamilyInfoEnEnum(final int key, final String value) {
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
