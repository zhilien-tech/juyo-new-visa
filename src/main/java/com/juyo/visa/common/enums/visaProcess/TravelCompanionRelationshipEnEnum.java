package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 旅伴信息---与你的关系（英文）
 */
public enum TravelCompanionRelationshipEnEnum implements IEnum {
	PARENTS(1, "PARENTS"), SPOUSE(2, "SPOUSE"), CHILD(3, "CHILD"), OTHER_RELATIVES(4, "OTHER RELATIVES"), FRIEND(5, "FRIEND"), BUSINESS_ASSOCIATE(
			6, "BUSINESS ASSOCIATE"),OTHER(7,"OTHER");
	private int key;
	private String value;

	private TravelCompanionRelationshipEnEnum(final int key, final String value) {
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
