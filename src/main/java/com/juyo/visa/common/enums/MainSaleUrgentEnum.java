package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 加急类型 枚举
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   刘旭利
 * @Date	 2018年4月27日
 */
public enum MainSaleUrgentEnum implements IEnum {

	NO(1, "否"), YUEQIAN(2, "约签"), CHUQIAN(3, "出签"), MIANPAI(4, "免排队");

	private int key;
	private String value;

	private MainSaleUrgentEnum(final int key, final String value) {
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
