package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 准备资料枚举
 * 
 * @author   彭辉
 * @Date	 2017年11月22日
 */
public enum PrepareMaterialsEnum implements IEnum {
	PASSPORT(1, "护照原件"), PHOTO(2, "2寸白底照片"), IDCARD_COPIES(3, "身份证正反面复印件"), MARRIAGE_CERTIFICATE(4, "结婚证"), ACCOUNT_BOOK(
			5, "户口本"), EMPLOYMENT_PROOF(6, "在职证明"), BUSINESS_LICENSE(7, "营业执照"), BANK_FLOW(8, "银行流水"), DEPOSIT_CERTIFICATE(
			9, "存款证明"), HOUSE_PROPERTY(10, "房产"), CAR_PROPERTY(11, "车产"), BIRTH_CERTIFICATE(12, "出生证明"), SCHOOL_CERTIFICATE(
			13, "在校证明"), STUDENT_IDCARD(14, "学生证"), RETIREMENT_CERTIFICATE(15, "退休证"), RELATIONSHIP_CERTIFICATE(16,
			"亲属公证"), ENTRUST_CERTIFICATE(17, "委托公证");

	private int key;
	private String value;

	private PrepareMaterialsEnum(final int key, final String value) {
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
