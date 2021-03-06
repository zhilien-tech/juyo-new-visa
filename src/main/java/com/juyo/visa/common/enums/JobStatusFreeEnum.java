package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 自由职业所需资料
 * @author   
 * @Date	 
 */
public enum JobStatusFreeEnum implements IEnum {
	PASSPORT(PrepareMaterialsEnum_JP.PASSPORT.intKey(), "护照原件"), PHOTO(PrepareMaterialsEnum_JP.PHOTO.intKey(), "2寸白底照片"), IDCARD_COPIES(
			PrepareMaterialsEnum_JP.IDCARD_COPIES.intKey(), "身份证正反面复印件"), MARRIAGE_CERTIFICATE(
			PrepareMaterialsEnum_JP.MARRIAGE_CERTIFICATE.intKey(), "结婚证"), ACCOUNT_BOOK(
			PrepareMaterialsEnum_JP.ACCOUNT_BOOK.intKey(), "户口本"), BANK_FLOW(
			PrepareMaterialsEnum_JP.BANK_FLOW.intKey(), "银行流水");
	private int key;
	private String value;

	private JobStatusFreeEnum(final int key, final String value) {
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
