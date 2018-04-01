package com.juyo.visa.common.enums.AppPictures;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * App拍摄资料 枚举
 * <p>
 * 
 */
public enum AppPicturesTypeEnum implements IEnum {
	FRONT(1, "身份证正面"), BACK(2, "身份证反面"), ZHU(3, "户口本主页"), FU(4, "户口本副业");
	private int key;
	private String value;

	private AppPicturesTypeEnum(final int key, final String value) {
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
