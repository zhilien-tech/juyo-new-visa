package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TReceiveaddressUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**用户id*/
	private Integer userId;
		
	/**公司id*/
	private Integer comId;
		
	/**收件人*/
	private String receiver;
		
	/**电话*/
	private String mobile;
		
	/**收件地址*/
	private String address;
		
	/**操作人id*/
	private Integer opId;
		
	/**创建时间*/
	private Date createTime;
		
	/**更新时间*/
	private Date updateTime;
		
}