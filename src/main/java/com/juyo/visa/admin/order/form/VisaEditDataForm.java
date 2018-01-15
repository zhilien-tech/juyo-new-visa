/**
 * OrderEditDataForm.java
 * com.juyo.visa.admin.order.form
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.order.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class VisaEditDataForm implements Serializable {

	//本身申请人ID
	private Integer applicantId;

	private Integer userType;

	private String visaRemark;

	private Integer orderid;

	private Integer marryStatus;

	private String marryUrl;

	private Integer isOrderUpTime;

	//是主申请人还是副申请人
	private Integer applicant;

	private String mainRelation;

	private String relationRemark;

	//主申请人ID
	private Integer mainApplicant;

	private Integer tourist;

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

	private Integer isTrailOrder;
}
