package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 申请人状态
 * <p>
 * @author   彭辉
 * @Date	 2017年11月13日 	 
 */
public enum TrialApplicantStatusEnum implements IEnum {
	FIRSTTRIAL(1, "初审"), QUALIFIED(2, "合格"), UNQUALIFIED(3, "不合格"), FillCompleted(4, "填写完成");
	private int key;
	private String value;

	private TrialApplicantStatusEnum(final int key, final String value) {
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
