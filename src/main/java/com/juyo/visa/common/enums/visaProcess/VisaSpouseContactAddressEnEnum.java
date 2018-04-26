package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 配偶联系地址(英文)
 */
public enum VisaSpouseContactAddressEnEnum implements IEnum {
	SAME_RESIDENTIAL_ADDRESS(1, "SAME AS HOME ADDRESS"), SAME_MAILING_ADDRESS(2, "SAME AS MAILING ADDRESS"), SAME_US_CONTACT_ADDRESS(3, "SAME AS U.S. CONTACT ADDRESS"), NOT_KNOW(
			4, "DO NONT KNOW"), ORTHER(5, "OTHER(SPECIFY ADDRESS)");

	private int key;
	private String value;

	private VisaSpouseContactAddressEnEnum(final int key, final String value) {
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
