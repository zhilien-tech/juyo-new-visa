package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * <p>
 * 美国婚姻状况英文 枚举
 * @author   闫腾
 * @Date	 2018年3月27日
 */
public enum USMarryStatusEnEnum implements IEnum {
	YIHUN(1, "MARRIED"), PT(2, "COMMON LAW MARRIAGE"), COMPANION(3, "CIVIL UNION/DOMESTIC PARTNERSHIP"), DANSHEN(4,
			"SINGLE"), SANGOU(5, "WIDOWED"), LIHUN(6, "DIVORCED"), FENJU(7, "LEGALLY SEPARATED"), QITA(8, "OTHER");
	private int key;
	private String value;

	private USMarryStatusEnEnum(final int key, final String value) {
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
