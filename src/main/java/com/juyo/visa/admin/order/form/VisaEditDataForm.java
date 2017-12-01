/**
 * OrderEditDataForm.java
 * com.juyo.visa.admin.order.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.order.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class VisaEditDataForm implements Serializable {

	private Integer applicantId;

	private Integer orderid;

	private Integer isOrderUpTime;

	private Integer applicant;

	private String mainRelation;

	private String relationRemark;

	private Integer mainApplicant;

	private String wealthType;

	private String occupation;

	private String name;

	private String telephone;

	private String address;

	private Integer careerStatus;

	private String type;

	private String financial;

	private String houseProperty;

	private String vehicle;

	private String deposit;

	private String details;

	private Integer sameMainTrip;

	private Integer sameMainWealth;

	private Integer sameMainWork;
}
