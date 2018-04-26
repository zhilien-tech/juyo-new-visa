package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 直系亲属---与你的关系(英文)
 */
public enum ImmediateFamilyMembersRelationshipEnEnum implements IEnum {
	SPOUSE(1, "SPOUSE"), FIANCE_FIANCEE(2, "FIANCE/FIANCEE"), CHILD(3, "CHILD"), SIBLING(4, "SIBLING");
	private int key;
	private String value;

	private ImmediateFamilyMembersRelationshipEnEnum(final int key, final String value) {
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
