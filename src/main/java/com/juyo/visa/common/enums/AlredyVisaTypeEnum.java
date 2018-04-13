/**
 * VisaDataTypeEnum.java
 * com.juyo.visa.common.enums
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * TODO 资料类型
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年10月31日 	 
 */
public enum AlredyVisaTypeEnum implements IEnum {
	TOURIST(1, "旅游"), BUSINESS(2, "商务");
	private int key;
	private String value;

	private AlredyVisaTypeEnum(final int key, final String value) {
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
