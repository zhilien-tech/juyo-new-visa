package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TTouristtVisaInputAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**申请人id*/
	private Integer applicantId;
		
	/**用户id*/
	private Integer userId;
		
	/**签发编号*/
	private String visaNum;
		
	/**签发地*/
	private String visaAddress;
		
	/**签证类型*/
	private Integer visaType;
		
	/**年限(年)*/
	private Integer visaYears;
		
	/**真实资料*/
	private String realInfo;
		
	/**签发时间*/
	private Date visaDate;
		
	/**有效期至*/
	private Date validDate;
		
	/**停留时间(天)*/
	private Integer stayDays;
		
	/**操作人*/
	private Integer opId;
		
	/**创建时间*/
	private Date createTime;
		
	/**更新时间*/
	private Date updateTime;
		
	/**签证国家*/
	private String visaCountry;
		
	/**上传签证图片URL*/
	private String picUrl;
		
	/**签证录入时间*/
	private Date visaEntryTime;
		
}