/**
 * VisaEditDataForm.java
 * com.juyo.visa.admin.visajp.form
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp.form;

import java.util.Date;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2017年11月1日 	 
 */
@Data
public class OrdertravelplanForm {

	private Integer id;

	private Integer orderId;

	private String day;

	private Date outDate;

	private String scenic;

	private Integer hotel;

	private Integer isupdatecity;

	private Integer cityId;

	private String cityName;

	private Integer opId;

	private Date createTime;

	private Date updateTime;

	private Integer roomcount;
}
