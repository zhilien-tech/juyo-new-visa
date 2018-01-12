package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 日本签证 订单主流程
 * 
 * @author   彭辉
 * @Date	 2018年01月10日
 */
public enum JPOrderProcessTypeEnum implements IEnum {
	SALES_PROCESS(1, "销售"), FIRSTTRIAL_PROCESS(2, "初审"), RECEPTION_PROCESS(3, "前台"), VISA_PROCESS(4, "签证"), AFTERMARKET_PROCESS(
			5, "售后");
	private int key;
	private String value;

	private JPOrderProcessTypeEnum(final int key, final String value) {
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
