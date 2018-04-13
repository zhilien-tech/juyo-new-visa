package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 职位状况
 * @author   彭辉
 * @Date	 2017年10月23日
 */
public enum JobStatusEnum implements IEnum {
	WORKING_STATUS(1, "在职"), RETIREMENT_STATUS(2, "退休"), FREELANCE_STATUS(3, "自由职业"), student_status(4, "学生"), Preschoolage_status(
			5, "学龄前");
	private int key;
	private String value;

	private JobStatusEnum(final int key, final String value) {
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
