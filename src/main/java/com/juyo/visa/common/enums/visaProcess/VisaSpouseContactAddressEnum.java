package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 配偶联系地址
 */
public enum VisaSpouseContactAddressEnum implements IEnum {
	SAME_RESIDENTIAL_ADDRESS(1, "与居住地址一样"), SAME_MAILING_ADDRESS(2, "与邮寄地址一样"), SAME_US_CONTACT_ADDRESS(3, "与美国联系地址一样"), NOT_KNOW(
			4, "不知道"), ORTHER(5, "其他(指定地址)");

	private int key;
	private String value;

	private VisaSpouseContactAddressEnum(final int key, final String value) {
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
