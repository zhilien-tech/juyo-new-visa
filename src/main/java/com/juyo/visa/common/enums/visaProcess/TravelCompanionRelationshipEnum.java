package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 旅伴信息---与你的关系
 */
public enum TravelCompanionRelationshipEnum implements IEnum {
	PARENTS(1, "父母"), SPOUSE(2, "配偶"), CHILD(3, "子女"), OTHER_RELATIVES(4, "其他亲属"), FRIEND(5, "朋友"), BUSINESS_ASSOCIATE(
			6, "商业伙伴"),OTHER(7,"其他");
	private int key;
	private String value;

	private TravelCompanionRelationshipEnum(final int key, final String value) {
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
