package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 付款方式
 *
 */
public enum MainSalePayTypeEnum implements IEnum {

	YUFUKUAN(1, "预付款"), CHUQIAN(2, "出签付"), YUEFU(3, "月付"), XIANJIN(4, "现金"), WX(5, "微信"), ZFB(6, "支付宝");

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
