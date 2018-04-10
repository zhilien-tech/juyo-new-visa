package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 美国联络点---与你的关系
 */
public enum ContactPointRelationshipStatusEnum implements IEnum {
	RELATIVES(1, "亲属"), SPOUSE(2, "配偶"), FRIEND(3, "朋友"), BUSINESS_PARTNER(4, "商业伙伴"), EMPLOYER(5, "雇主"), SCHOOL_OFFICIAL(
			6, "学校官方"), OTHER(7, "其他");
	private int key;
	private String value;

	private ContactPointRelationshipStatusEnum(final int key, final String value) {
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
