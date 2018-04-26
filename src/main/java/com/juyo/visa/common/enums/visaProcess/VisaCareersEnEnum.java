package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 工作/教育/培训信息---职业枚举(英文)
 */
public enum VisaCareersEnEnum implements IEnum {
	AGRICULTURE(1, "AGRICULTURE"), ARTIST_PERFORMER(2, "ARTIST/PERFORMER"), BUSINESS(3, "BUSINESS"), COMMUNICATIONS(4, "COMMUNICATIONS"), COMPUTER_SCIENCE(
			5, "COMPUTER SCIENCE"), CULINARY_FOOD_SERVICES(6, "CULINARY/FOOD SERVICES"), EDUCATION(7, "EDUCATION"), ENGINEERING(8, "ENGINEERING"), GOVERNMENT(9,
			"GOVERNMENT"), HOMEMAKER(10, "HOMEMAKER"),

	LEGAL_PROFESSION(11, "LEGAL PROFESSION"), MEDICAL_TREATMENT(12, "MEDICAL/HEALTH"), MILITARY(13, "MILITARY"), NATURAL_SCIENCE(14, "NATURAL SCIENCE"), NOT_EMPLOYED(
			15, "NOT EMPLOYED"), PHYSICAL_SCIENCES(16, "PHYSICAL SCIENCES"), RELIGIOUS_VOCATION(17, "RELIGIOUS VOCATION"), RESEARCH(18, "RESEARCH"), RETIRED(19,
			"RETIRED"), SOCIAL_SCIENCE(20, "SOCIAL SCIENCE"), STUDENT(21, "STUDENT"), ORTHER(22, "ORTHER");

	private int key;
	private String value;

	private VisaCareersEnEnum(final int key, final String value) {
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
