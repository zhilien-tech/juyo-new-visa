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
public enum SimpleVisaTypeEnum implements IEnum {

	SINGLE(1, "日本单次"), CSSINGLE(2, "冲绳单次"), GCSINGLE(3, "宫城单次"), YSSINGLE(4, "岩手单次"), FDSINGLE(5, "福岛单次"), PTSANNIAN(6,
			"普通三年多次"), CSDUOCI(7, "冲绳三年多次"), GCDUOCI(8, "宫城三年多次"), FDDUOCI(9, "福岛三年多次"), YSDUOCI(10, "岩手三年多次"), QSDUOCI(
			11, "青森三年多次"), QTDUOCI(12, "秋田三年多次"), SXDUOCI(13, "山形三年多次"), WNDUOCI(14, "普通五年多次");

	private int key;
	private String value;

	private SimpleVisaTypeEnum(final int key, final String value) {
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
