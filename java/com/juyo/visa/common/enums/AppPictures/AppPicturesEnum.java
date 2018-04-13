package com.juyo.visa.common.enums.AppPictures;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * App拍摄资料 枚举
 * <p>
 * 
 */
public enum AppPicturesEnum implements IEnum {
	PHOTO(1, "照片"), NO(0, "否");
	private int key;
	private String value;

	private AppPicturesEnum(final int key, final String value) {
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
