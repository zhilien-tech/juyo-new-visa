package com.juyo.visa.common.enums.orderUS;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 美国订单列表页领区 枚举
 * @author   闫腾
 * @Date	 2018年4月2日
 * 
 * 
 */
public enum DistrictEnum implements IEnum {
	BEIJING(1, "北京"), SHANGHAI(2, "上海"), GUANGZHOU(5, "广州"), CHENGDU(4, "成都"), SHENYANG(3, "沈阳");
	private int key;
	private String value;

	private DistrictEnum(final int key, final String value) {
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
