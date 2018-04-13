/**
 * ErrorCodeEnum.java
 * com.juyo.visa.admin.simulate.enums
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.simulate.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年1月8日 	 
 */
public enum ErrorCodeEnum implements IEnum {
	other(0, "其它错误信息"), completedNumberFail(1, "受付番号生成失败"), persionNameList(2, "个人名簿生成失败"), comeReport(3, "归国报告上传失败");
	private int key;
	private String value;

	private ErrorCodeEnum(final int key, final String value) {
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
