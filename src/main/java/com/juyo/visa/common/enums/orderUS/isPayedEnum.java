package com.juyo.visa.common.enums.orderUS;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 美国订单列表页是否付款 枚举
 * @author   闫腾
 * @Date	 2018年4月2日
 * 
 * 
 */
public enum isPayedEnum implements IEnum {
	NOTPAY(1, "未付款"), PAYED(2, "已付款"), TUIKUAN(3, "退款");
	private int key;
	private String value;

	private isPayedEnum(final int key, final String value) {
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
