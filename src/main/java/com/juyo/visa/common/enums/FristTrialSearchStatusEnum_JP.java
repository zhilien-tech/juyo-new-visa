package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * <p>
 * 日本初审检索状态
 * @author   彭辉
 * @Date	 2017年10月20日
 */
public enum FristTrialSearchStatusEnum_JP implements IEnum {

	FIRSTTRIAL(JPOrderStatusEnum.FIRSTTRIAL_ORDER.intKey(), "初审"), UNQUALIFIED(TrialApplicantStatusEnum.unqualified
			.intKey(), "不合格"), QUALIFIED(JPOrderStatusEnum.QUALIFIED_ORDER.intKey(), "合格"), SEND_ADDRESS(
			JPOrderStatusEnum.SEND_ADDRESS.intKey(), "发地址");
	private int key;
	private String value;

	private FristTrialSearchStatusEnum_JP(final int key, final String value) {
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
