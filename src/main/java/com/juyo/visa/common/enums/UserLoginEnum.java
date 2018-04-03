package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 用户登录枚举
 * @author   彭辉
 * @Date	 2017年10月20日 	 
 */
public enum UserLoginEnum implements IEnum {
	PERSONNEL(1, "工作人员"), TOURIST_IDENTITY(2, "游客身份"), SUPERMAN(3, "超级管理员"), ADMIN(4, "平台管理员"), SQ_COMPANY_ADMIN(5,
			"送签社公司管理员"), DJ_COMPANY_ADMIN(6, "地接社公司管理员"), JJ_COMPANY_ADMIN(7, "精简版送签社公司管理员"), BIG_COMPANY_ADMIN(8,
			"美国大客户公司管理员"), BIG_TOURIST_IDENTITY(9, "美国大客户游客身份");

	private int key;
	private String value;

	private UserLoginEnum(final int key, final String value) {
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
