package com.juyo.visa.common.enums.orderUS;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 美国订单列表页状态 枚举
 * @author   闫腾
 * @Date	 2018年4月2日
 * 
 * 
 */
public enum USOrderListStatusEnum implements IEnum {
	PLACE_ORDER(1, "下单"), HEGE(2, "合格"), AUTOFILL(3, "自动填表"), TONGGUO(4, "通过"), JUJUE(5, "拒绝");
	private int key;
	private String value;

	private USOrderListStatusEnum(final int key, final String value) {
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
