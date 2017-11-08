/**
 * PassportForm.java
 * com.juyo.visa.admin.visajp.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp.form;

import java.util.Date;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年11月8日 	 
 */
@Data
public class PassportForm {

	private Integer id;

	private String type;

	private String passport;

	private String sexstr;

	private String birthaddressstr;

	private Date birthday;

	private String issuedplacestr;

	private Date issueddate;

	private Integer validtype;

	private Date validenddate;
}
