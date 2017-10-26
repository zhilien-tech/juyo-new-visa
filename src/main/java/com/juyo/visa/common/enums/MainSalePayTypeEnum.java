package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

public enum MainSalePayTypeEnum implements IEnum {

	XIANJIN(1, "现金"), CHUQIAN(2, "出签付"), WX(3, "微信"), ZFB(4, "支付宝"), YUEFU(4, "月付");

	private int key;
	private String value;

	private MainSalePayTypeEnum(final int key, final String value) {
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
