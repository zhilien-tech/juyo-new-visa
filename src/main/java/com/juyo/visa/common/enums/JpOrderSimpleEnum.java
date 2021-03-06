/**
 * JpOrderSimpleEnum.java
 * com.juyo.visa.common.enums
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 日本订单（简）订单状态 枚举
 *
 * @author   刘旭利
 * @Date	 2018年1月23日 	 
 */
public enum JpOrderSimpleEnum implements IEnum {
	PLACE_ORDER(1, "下单"), AUTO_FILL_FORM_PREPARE(15, "准备发招宝"), AUTO_FILL_FORM_ING(28, "发招宝中"), READYCOMMING(70,
			"准备提交大使馆"), COMMITING(32, "提交中"), COMMINGFAIL(31, "提交失败"), AUTO_FILL_FORM_ED(17, "招宝成功"), AUTO_FILL_FORM_FAILED(
			18, "发招宝失败"), BIANGENGZHONG(19, "招宝变更中"), YIBIANGENG(20, "招宝已变更"), BIANGENGSHIBAI(21, "招宝变更失败"), QUXIAOZHONG(
			22, "招宝取消中"), YIQUXIAO(23, "招宝已取消"), QUXIAOSHIBAI(24, "招宝取消失败"), REFUSE_SIGN(27, "报告拒签"), JAPANREPORT(29,
			"归国报告"), JAPANREPORTFAIL(30, "归国报告失败"), DISABLED(26, "作废");
	private int key;
	private String value;

	private JpOrderSimpleEnum(final int key, final String value) {
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
