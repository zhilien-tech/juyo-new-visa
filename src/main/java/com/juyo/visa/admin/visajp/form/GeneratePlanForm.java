/**
 * generatePlanForm.java
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
 * @Date	 2017年11月3日 	 
 */
@Data
public class GeneratePlanForm {

	private Integer goArrivedCity;

	private Date goDate;

	private Date returnDate;

	private Integer orderid;
}
