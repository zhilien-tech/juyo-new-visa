package com.juyo.visa.common.enums.orderUS;

import com.uxuexi.core.common.enums.IEnum;

public enum PayRelationshipEnum implements IEnum {
	CHILD(1, "子女"), PARENTS(2, "父母"), SPOUSE(3, "配偶"), OTHER_RELATIVES(4, "其他亲属"), FRIEND(5, "朋友"), OTHER(6, "其他");
	private int key;
	private String value;

	private PayRelationshipEnum(final int key, final String value) {
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