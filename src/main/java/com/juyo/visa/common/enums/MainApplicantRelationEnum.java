package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 与主申请人关系
 * 
 */
public enum MainApplicantRelationEnum implements IEnum {
	WIFE(1, "之妻"), HUSBAND(2, "之夫"), SON(3, "之子"), DAUGHTER(4, "之女"), FATHOR(5, "之父"), MOTHER(6, "之母"), FRIEND(7, "朋友"), TONGSHI(
			8, "同事"), TONGXUE(9, "同学");
	private int key;
	private String value;

	private MainApplicantRelationEnum(final int key, final String value) {
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
