package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 用户登录枚举
 * @author   彭辉
 * @Date	 2017年10月20日 	 
 */
public enum MonthsEnum implements IEnum {
	JAN(1, "JAN"), FEB(2, "FEB"), MAR(3, "MAR"), APR(4, "APR"), MAY(5, "MAY"), JUN(6, "JUN"), JUL(7, "JUL"), AUG(8,
			"AUG"), SEP(9, "SEP"), OCT(10, "OCT"), NOV(11, "NOV"), DEC(12, "DEC");

	private int key;
	private String value;

	private MonthsEnum(final int key, final String value) {
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
