package com.juyo.visa.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffPassportUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**人员id*/
	private Integer staffid;

	/**姓*/
	private String firstname;

	/**姓(拼音)*/
	private String firstnameen;

	/**名*/
	private String lastname;

	/**名(拼音)*/
	private String lastnameen;

	/**类型*/
	private String type;

	/**护照号*/
	private String passport;

	/**性别*/
	private String sex;

	/**性别(拼音)*/
	private String sexen;

	/**出生地点*/
	private String birthaddress;

	/**出生地点(拼音)*/
	private String birthaddressen;

	/**出生日期*/
	private Date birthday;

	/**签发地点*/
	private String issuedplace;

	/**签发地点(拼音)*/
	private String issuedplaceen;

	/**签发日期*/
	private Date issueddate;

	/**有效期始*/
	private Date validstartdate;

	/**有效类型*/
	private Integer validtype;

	/**有效期至*/
	private Date validenddate;

	/**签发机关*/
	private String issuedorganization;

	/**签发机关(拼音)*/
	private String issuedorganizationen;

	/**操作人*/
	private Integer opid;

	/**创建时间*/
	private Date createtime;

	/**更新时间*/
	private Date updatetime;

	/**护照地址*/
	private String passporturl;

	/**OCR识别码第一行*/
	private String oCRline1;

	/**OCR识别码第二行*/
	private String oCRline2;

	private Integer islostpassport;

	private Integer islostpassporten;

	private Integer isrememberpassportnum;

	private Integer isrememberpassportnumen;

	private String lostpassportnum;

	private String lostpassportnumen;

}