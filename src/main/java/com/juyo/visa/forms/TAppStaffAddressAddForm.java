package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffAddressAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**人员id*/
	private Integer staffId;
		
	/**联系人*/
	private String linkman;
		
	/**手机*/
	private String telephone;
		
	/**邮箱*/
	private String email;
		
	/**省份*/
	private String province;
		
	/**市区*/
	private String city;
		
	/**详细地址*/
	private String detailAddress;
		
	/**创建时间*/
	private Date createtime;
		
	/**更新时间*/
	private Date updatetime;
		
}