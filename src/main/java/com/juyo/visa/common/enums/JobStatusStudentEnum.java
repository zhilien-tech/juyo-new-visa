package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 退休所需资料
 * @author   
 * @Date	 
 */
public enum JobStatusStudentEnum implements IEnum {
	PASSPORT(PrepareMaterialsEnum_JP.PASSPORT.intKey(), "护照原件"), PHOTO(PrepareMaterialsEnum_JP.PHOTO.intKey(), "2寸白底照片"), IDCARD_COPIES(
			PrepareMaterialsEnum_JP.IDCARD_COPIES.intKey(), "身份证正反面复印件"), SCHOOL_CERTIFICATE(
			PrepareMaterialsEnum_JP.SCHOOL_CERTIFICATE.intKey(), "在校证明"), ACCOUNT_BOOK(
			PrepareMaterialsEnum_JP.ACCOUNT_BOOK.intKey(), "户口本"), STUDENT_IDCARD(
			PrepareMaterialsEnum_JP.STUDENT_IDCARD.intKey(), "学生证"), RELATIONSHIP_CERTIFICATE(
			PrepareMaterialsEnum_JP.RELATIONSHIP_CERTIFICATE.intKey(), "亲属公证"), ENTRUST_CERTIFICATE(
			PrepareMaterialsEnum_JP.ENTRUST_CERTIFICATE.intKey(), "委托公证");
	private int key;
	private String value;

	private JobStatusStudentEnum(final int key, final String value) {
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
