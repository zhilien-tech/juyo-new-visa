package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 订单详情里加急天数 枚举
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   刘旭利
 * @Date	 2018年4月27日
 */
public enum MainSaleUrgentTimeEnum implements IEnum {

	THREE(1, "3个工作日"), FIVE(2, "5个工作日"), SEVEN(3, "7个工作日"), ;

	private int key;
	private String value;

	private MainSaleUrgentTimeEnum(final int key, final String value) {
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
