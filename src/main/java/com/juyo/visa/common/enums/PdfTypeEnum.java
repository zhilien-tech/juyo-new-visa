package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 日本发招宝下载 pdf类型
 * 
 */
public enum PdfTypeEnum implements IEnum {
	UNIVERSAL_TYPE(1, "通用类型"), LIAONINGWANDA_TYPE(2, "辽宁万达"), HUANYU_TYPE(3, "寰宇"), JINQIAO_TYPE(4, "金桥"), SHENZHOU_TYPE(
			5, "神州"), FENGSHANG_TYPE(6, "风尚"), BAICHENG_TYPE(7, "上海百城"), HEPING_TYPE(8, "和平"), BAOSHI_TYPE(9, "宝狮"), ANJIE_TYPE(
			10, "安捷"), JIAOTONG_TYPE(11, "交通公社"), YIQIYOU_TYPE(12, "一起游"), ZHONGBAO_TYPE(13, "上海中宝");
	private int key;
	private String value;

	private PdfTypeEnum(final int key, final String value) {
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
