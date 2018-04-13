package com.juyo.visa.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TTouristPassportUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**用户id*/
	private Integer userId;

	/**姓*/
	private String firstName;

	/**姓(拼音)*/
	private String firstNameEn;

	private Integer applicantId;

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

	/**护照备注*/
	private String passRemark;

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

	/**护照信息是否填写完毕*/
	private Integer passIsCompleted;

	/**操作人*/
	private Integer opId;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

	/**护照地址*/
	private String passportUrl;

	/**OCR识别码第一行*/
	private String oCRline1;

	/**OCR识别码第二行*/
	private String oCRline2;

}