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
	PLACE_ORDER(1, "下单"), FILLING(2, "填写资料"), FILlED(3, "填写完成"), HEGE(4, "合格"), PREAUTOFILLING(5, "预检查中"), PREAUTOFILLED(
			6, "预检查成功"), PREAUTOFILLFAILED(7, "预检查失败"), AUTOFILLING(8, "正式填表中"), AUTOFILLED(9, "正式填表成功"), AUTOFILLFAILED(
			10, "正式填表失败"), TONGGUO(11, "通过"), JUJUE(12, "拒签"), DISABLED(13, "作废");
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
