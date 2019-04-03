/**
 * ApplicantInfo.java
 * com.juyo.visa.admin.interfaceJapan.entity
 * Copyright (c) 2019, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.interfaceJapan.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2019年4月3日 	 
 */
@Data
public class ApplicantInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/*姓*/
	private String firstname;

	/*姓拼音*/
	private String firstnameEn;

	/*名*/
	private String lastname;

	/*名拼音*/
	private String lastnameEn;

	/*生日*/
	private String birthday;

	/*性别*/
	private String sex;

	/*居住地域*/
	private String province;

	/*护照号*/
	private String passportNo;

	/*是否是主申请人*/
	private Integer isMainApplicant;
}
