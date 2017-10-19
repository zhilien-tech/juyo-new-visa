package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TApplicantAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**主申请人id*/
	private Integer mainId;
		
	/**申请人状态*/
	private Integer status;
		
	/**用户id（登录用户id）*/
	private Integer userId;
		
	/**姓*/
	private String firstName;
		
	/**姓(拼音)*/
	private String firstNameEn;
		
	/**名*/
	private String lastName;
		
	/**名(拼音)*/
	private String lastNameEn;
		
	/**手机号*/
	private String telephone;
		
	/**邮箱*/
	private String email;
		
	/**性别*/
	private Integer sex;
		
	/**民族*/
	private String nation;
		
	/**出生日期*/
	private Date birthday;
		
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
		
	/**有效期始*/
	private Date validStartDate;
		
	/**有效期至*/
	private Date validEndDate;
		
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
		
}