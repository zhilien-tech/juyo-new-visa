/**
 * TAppStaffCredentialsEnum.java
 * com.juyo.visa.common.enums.visaProcess
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2018年3月28日 	 
 */
public enum TAppStaffCredentialsEnum implements IEnum {
	NEWHUZHAO(1, "新护照"), OLDHUZHAO(2, "旧护照"), IDCARD(3, "身份证"), HUKOUBEN(4, "户口本"), HOME(5, "房产证"), MARRAY(6, "结婚证"), BANK(
			7, "银行流水"), WORKE(8, "在职证明"), YINGYEZHIHZAO(9, "营业执照"), DRIVE(10, "驾驶证"), OLDUS(11, "过期美签"), NEWUS(12,
			"美国出签"), TWOINCHPHOTO(13, "2寸免冠照");
	private int key;
	private String value;

	private TAppStaffCredentialsEnum(final int key, final String value) {
		this.value = value;
		this.key = key;
	}

	@Override
	public String key() {

		// TODO Auto-generated method stub
		return String.valueOf(key);

	}

	@Override
	public String value() {

		// TODO Auto-generated method stub
		return value;

	}

	public int intKey() {
		return key;
	}

}
