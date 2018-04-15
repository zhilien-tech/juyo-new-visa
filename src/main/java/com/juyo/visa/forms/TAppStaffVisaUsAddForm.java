package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffVisaUsAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**申请人id*/
	private Integer staffid;
		
	/**签发编号*/
	private String visanum;
		
	/**签发地*/
	private String visaaddress;
		
	/**签证类型*/
	private Integer visatype;
		
	/**年限(年)*/
	private Integer visayears;
		
	/**真实资料*/
	private String realinfo;
		
	/**签发时间*/
	private Date visadate;
		
	/**有效期至*/
	private Date validdate;
		
	/**停留时间(天)*/
	private Integer staydays;
		
	/**操作人*/
	private Integer opid;
		
	/**创建时间*/
	private Date createtime;
		
	/**更新时间*/
	private Date updatetime;
		
	/**签证国家*/
	private String visacountry;
		
	/**上传签证图片URL*/
	private String picurl;
		
	/**签证录入时间*/
	private Date visaentrytime;
		
}