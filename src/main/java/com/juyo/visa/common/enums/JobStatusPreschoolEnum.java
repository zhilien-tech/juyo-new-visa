package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 学前所需资料
 * @author   
 * @Date	 
 */
public enum JobStatusPreschoolEnum implements IEnum {
	PASSPORT(PrepareMaterialsEnum_JP.PASSPORT.intKey(), "护照原件"), PHOTO(PrepareMaterialsEnum_JP.PHOTO.intKey(), "2寸白底照片"), BIRTH_CERTIFICATE(
			PrepareMaterialsEnum_JP.BIRTH_CERTIFICATE.intKey(), "出生证明"), RELATIONSHIP_CERTIFICATE(
			PrepareMaterialsEnum_JP.RELATIONSHIP_CERTIFICATE.intKey(), "亲属公证"), ACCOUNT_BOOK(
			PrepareMaterialsEnum_JP.ACCOUNT_BOOK.intKey(), "户口本"), ENTRUST_CERTIFICATE(
			PrepareMaterialsEnum_JP.ENTRUST_CERTIFICATE.intKey(), "委托公证");
	private int key;
	private String value;

	private JobStatusPreschoolEnum(final int key, final String value) {
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
