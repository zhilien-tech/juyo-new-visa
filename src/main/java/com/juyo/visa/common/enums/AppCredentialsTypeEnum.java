package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * <p>
 * 大客户证件类型
 */
public enum AppCredentialsTypeEnum implements IEnum {
	PHOTO(1, "照片"), PASSPORT(2, "护照首页"), PASSPORT_OLD(3, "旧护照"), ID_CARD(4, "身份证"), ACCOUNT_BOOK(5, "户口本"), marriage(6,
			"婚姻状况"), AUXILIARY_ASSETS(7, "辅助资产"), VISA_RECORDS_BEFORE(8, "以往签证记录");
	private int key;
	private String value;

	private AppCredentialsTypeEnum(final int key, final String value) {
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
