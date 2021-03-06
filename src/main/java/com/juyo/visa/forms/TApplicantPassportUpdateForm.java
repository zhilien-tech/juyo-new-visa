package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TApplicantPassportUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**申请人id*/
	private Integer applicantId;
		
	/**姓*/
	private String firstName;
		
	/**姓(拼音)*/
	private String firstNameEn;
		
	/**名*/
	private String lastName;
		
	/**名(拼音)*/
	private String lastNameEn;
		
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
		
	/**操作人*/
	private Integer opId;
		
	/**创建时间*/
	private Date createTime;
		
	/**更新时间*/
	private Date updateTime;
		
}