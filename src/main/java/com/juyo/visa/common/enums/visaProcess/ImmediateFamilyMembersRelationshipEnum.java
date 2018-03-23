package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 直系亲属---与你的关系
 */
public enum ImmediateFamilyMembersRelationshipEnum implements IEnum {
	SPOUSE(1, "配偶"), FIANCE_FIANCEE(2, "未婚夫/妻子"), CHILD(3, "子女"), SIBLING(4, "兄弟姐妹");
	private int key;
	private String value;

	private ImmediateFamilyMembersRelationshipEnum(final int key, final String value) {
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
