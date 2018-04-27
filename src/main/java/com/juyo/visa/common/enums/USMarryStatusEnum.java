package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * <p>
 * 美国婚姻状况中文 枚举
 * @author   闫腾
 * @Date	 2018年3月27日
 */
public enum USMarryStatusEnum implements IEnum {
	YIHUN(1, "已婚"), PT(2, "普通法婚姻"), COMPANION(3, "民间团体/国内合作伙伴"), DANSHEN(4, "单身"), SANGOU(5, "丧偶"), LIHUN(6, "离婚"), FENJU(
			7, "合法分居"), QITA(8, "其他");
	private int key;
	private String value;

	private USMarryStatusEnum(final int key, final String value) {
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
