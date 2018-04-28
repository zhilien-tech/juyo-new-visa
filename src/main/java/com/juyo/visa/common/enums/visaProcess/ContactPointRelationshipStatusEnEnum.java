package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 美国联络点---与你的关系（英文）
 */
public enum ContactPointRelationshipStatusEnEnum implements IEnum {
	RELATIVES(1, "RELATIVE"), SPOUSE(2, "SPOUSE"), FRIEND(3, "FRIEND"), BUSINESS_PARTNER(4, "BUSINESS ASSOCIATE"), EMPLOYER(5, "EMPLOYER"), SCHOOL_OFFICIAL(
			6, "SCHOOL OFFICIAL"), OTHER(7, "OTHER");
	private int key;
	private String value;

	private ContactPointRelationshipStatusEnEnum(final int key, final String value) {
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
