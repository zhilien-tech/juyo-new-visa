/**
 * GenerrateTravelForm.java
 * com.juyo.visa.admin.simple.form
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.simple.form;

import java.util.Date;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 
 */
@Data
public class BasicinfoForm {

	private Integer orderid;

	private Integer applicantid;

	/**主申请人id*/
	private Integer mainId;

	/**申请人状态*/
	private Integer status;

	private String outboundrecord;

	/**用户id（登录用户id）*/
	private Integer userId;

	/**姓*/
	private String firstName;

	/**与主申请人的关系*/
	private String mainRelation;

	/**姓(拼音)*/
	private String firstNameEn;

	/**名*/
	private String lastName;

	/**名(拼音)*/
	private String lastNameEn;

	private String baseRemark;

	private Integer tourist;

	private String otherFirstName;

	private String otherFirstNameEn;

	private String otherLastName;

	private Integer isPrompted;

	private Integer ismobileprompted;

	private Integer addApply;

	private String otherLastNameEn;

	private String emergencyLinkman;

	private String emergencyTelephone;

	private String emergencyaddress;

	private Integer hasOtherNationality;

	private Integer hasOtherName;

	private Integer addressIsSameWithCard;

	private String cardProvince;

	private String cardCity;

	private String marryUrl;

	private Integer marryStatus;

	private String nationality;

	private Integer isSameInfo;

	/**手机号*/
	private String telephone;

	/**邮箱*/
	private String email;

	/**民族*/
	private String nation;

	/**住址*/
	private String address;

	/**身份证号*/
	private String cardId;

	/**身份证正面*/
	private String cardFront;

	/**身份证反面*/
	private String cardBack;

	/**签发机关*/
	private String issueOrganization;

	/**现居住地址省份*/
	private String province;

	/**现居住地址城市*/
	private String city;

	/**详细地址*/
	private String detailedAddress;

	/**操作人*/
	private Integer opId;

	/**更新时间*/
	private Date updateTime;

	/**创建时间*/
	private Date createTime;

	private Integer isTrailOrder;

	private Integer userType;

	/**订单操作枚举*/
	private Integer orderProcessType;
	/**申请人id*/
	private Integer applicantId;

	/**类型*/
	private String type;

	/**护照号*/
	private String passport;

	/**性别*/
	private String sex;

	/**性别(拼音)*/
	private String sexEn;

	/**出生地点*/
	private String birthAddress;

	/**出生地点(拼音)*/
	private String birthAddressEn;

	/**出生日期*/
	private Date birthday;

	/**签发地点*/
	private String issuedPlace;

	/**签发地点(拼音)*/
	private String issuedPlaceEn;

	/**签发日期*/
	private Date issuedDate;

	/**有效期始*/
	private Date validStartDate;

	/**有效类型*/
	private Integer validType;

	/**有效期至*/
	private Date validEndDate;

	/**签发机关*/
	private String issuedOrganization;

	/**签发机关(拼音)*/
	private String issuedOrganizationEn;

	/**护照图片地址*/
	private String passportUrl;

	private String passRemark;

	private String OCRline1;

	private String OCRline2;

}
