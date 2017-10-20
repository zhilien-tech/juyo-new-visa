package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCompanyAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**公司名称*/
	private String name;
		
	/**公司简称*/
	private String shortName;
		
	/**管理员账号id*/
	private Integer adminId;
		
	/**联系人*/
	private String linkman;
		
	/**联系人手机号*/
	private String mobile;
		
	/**邮箱*/
	private String email;
		
	/**地址*/
	private String address;
		
	/**公司类型*/
	private Integer comType;
		
	/**营业执照*/
	private String license;
		
	/**操作人*/
	private Integer opId;
		
	/**创建时间*/
	private Date createTime;
		
	/**修改时间*/
	private Date updateTime;
		
	/**删除标识*/
	private Integer deletestatus;
		
}