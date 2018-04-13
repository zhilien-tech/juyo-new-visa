/**
 * CollarAreaEnum.java
 * com.juyo.visa.common.enums
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * TODO 领区枚举
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年10月31日 	 
 */
public enum JapanVisaStatusEnum implements IEnum {

	VISA(1, "签证"), ZHAOBAOZHONG(2, "发招宝中"), YIFAZHAOBAO(3, "已发招宝"), ZHAOBAISHIBAI(4, "发招宝失败"), BIANGENGZHONG(5, "招宝变更中"), YIBIANGENG(
			6, "招宝已变更"), BIANGENGSHIBAI(7, "招宝变更失败"), QUXIAOZHONG(8, "招宝取消中"), YIQUXIAO(9, "招宝已取消"), QUXIAOSHIBAI(10,
			"招宝取消失败");

	private int key;
	private String value;

	private JapanVisaStatusEnum(final int key, final String value) {
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
