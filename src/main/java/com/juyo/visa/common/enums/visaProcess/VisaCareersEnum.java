package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 工作/教育/培训信息---职业枚举
 */
public enum VisaCareersEnum implements IEnum {
	AGRICULTURE(1, "农业"), ARTIST_PERFORMER(2, "艺术家/表演家"), BUSINESS(3, "商业"), COMMUNICATIONS(4, "通信"), COMPUTER_SCIENCE(
			5, "计算机科学"), CULINARY_FOOD_SERVICES(6, "烹饪/食品服务"), EDUCATION(7, "教育"), ENGINEERING(8, "工程"), GOVERNMENT(9,
			"政府"), HOMEMAKER(10, "家庭主妇"),

	LEGAL_PROFESSION(11, "法律界人士"), MEDICAL_TREATMENT(12, "医疗/保健"), MILITARY(13, "军事"), NATURAL_SCIENCE(14, "自然科学"), NOT_EMPLOYED(
			15, "不受雇用"), PHYSICAL_SCIENCES(16, "物理科学"), RELIGIOUS_VOCATION(17, "宗教职业"), RESEARCH(18, "研究"), RETIRED(19,
			"退休"), SOCIAL_SCIENCE(20, "社会科学"), STUDENT(21, "学生"), ORTHER(22, "其他");

	private int key;
	private String value;

	private VisaCareersEnum(final int key, final String value) {
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
