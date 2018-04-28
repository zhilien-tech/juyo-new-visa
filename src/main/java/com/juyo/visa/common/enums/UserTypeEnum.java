package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 用户登录身份 枚举
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   刘旭利
 * @Date	 2018年4月27日
 */
public enum UserTypeEnum implements IEnum {

	PERSONNEL(1, "工作人员"), TOURIST_IDENTITY(2, "游客身份");

	private int key;
	private String value;

	private UserTypeEnum(final int key, final String value) {
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
