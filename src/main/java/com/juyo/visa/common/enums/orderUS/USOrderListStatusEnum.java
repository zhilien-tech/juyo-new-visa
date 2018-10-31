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
	PLACE_ORDER(1, "下单"), FILLING(2, "填写资料"), FILlED(3, "填写完成"), HEGE(4, "合格"), AUTOFILLING(5, "自动填表中"), AUTOFILLED(6,
			"自动填表成功"), AUTOFILLFAILED(7, "自动填表失败"), TONGGUO(8, "通过"), JUJUE(9, "拒签"), DISABLED(10, "作废");
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
