/**
 * OrderEditDataForm.java
 * com.juyo.visa.admin.order.form
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.order.form;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class VisaEditDataForm implements Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */

	private static final long serialVersionUID = 1L;

	//本身申请人ID
	private Integer applicantId;

	private Integer userType;

	private String visaRemark;

	private String unitName;

	private String position;

	private Integer orderid;

	private Integer marryStatus;

	private String marryUrl;

	private Integer isOrderUpTime;

	private Integer addApply;

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

	private String certificate;

	private String bankflow;

	private String taxbill;

	private String taxproof;

	private String readstudent;

	private String graduate;

	private String details;

	private Integer sameMainTrip;

	private Integer sameMainWealth;

	private Integer sameMainWork;

	private Integer isTrailOrder;

	//订单流程枚举值
	private Integer orderProcessType;

	private String visacounty;
	private String visacountys;

	private String threecounty;

	private Integer visatype;

	private Integer isVisit;

	private String hotelname;

	private String hotelphone;

	private String hoteladdress;

	private String vouchname;
	private String vouchnameen;

	private String vouchphone;

	private String vouchaddress;

	private Date vouchbirth;

	private String vouchsex;

	private String vouchmainrelation;

	private String vouchjob;

	private String vouchcountry;

	private String invitename;

	private String invitephone;

	private String inviteaddress;

	private Date invitebirth;

	private String invitesex;

	private String invitemainrelation;

	private String invitejob;

	private String invitecountry;

	private String traveladvice;
	private String isname;
	private String isyaoqing;

	private Date laststartdate;

	private Integer laststayday;

	private Date lastreturndate;
}
