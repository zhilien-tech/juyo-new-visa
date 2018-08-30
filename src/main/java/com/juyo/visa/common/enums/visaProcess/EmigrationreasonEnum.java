package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 旅伴信息---与你的关系
 */
public enum EmigrationreasonEnum implements IEnum {
	PARENTS(1, "家庭移民"), SPOUSE(2, "雇主担保移民"), CHILD(3, "投资移民"), OTHER_RELATIVES(4, "其他");
	private int key;
	private String value;

	private EmigrationreasonEnum(final int key, final String value) {
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
