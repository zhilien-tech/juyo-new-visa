package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 订单详情里签证类型 枚举
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   刘旭利
 * @Date	 2018年4月27日
 */
public enum MainSaleVisaTypeEnum implements IEnum {

	SINGLE(1, "单次"), LIUXIAN(2, "冲绳东北六县多次"), PTSANNIAN(3, "普通三年多次"), PTWUNIAN(4, "普通五年多次");
	;

	private int key;
	private String value;

	private MainSaleVisaTypeEnum(final int key, final String value) {
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
