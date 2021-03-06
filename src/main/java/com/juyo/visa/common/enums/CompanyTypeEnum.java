package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 公司类型
 * <p>
 * 送签社 or 地接社
 * @author   彭辉
 * @Date	 2017年10月20日
 */
public enum CompanyTypeEnum implements IEnum {
	//特殊公司：BIGCUSTOMER_US(5, "美国---誉尚")  BAOYING_US(6,"美国---葆婴");
	SONGQIAN(1, "送签社"), DIJI(2, "地接社"), SONGQIANSIMPLE(3, "送签社精简"), BIGCUSTOMER(4, "大客户"), ORDERSIMPLE(5, "订单精简");
	private int key;
	private String value;

	private CompanyTypeEnum(final int key, final String value) {
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
