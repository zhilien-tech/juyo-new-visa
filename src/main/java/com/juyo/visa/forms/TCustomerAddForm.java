package com.juyo.visa.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCustomerAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**用户id*/
	private Integer userId;

	/**公司id*/
	private Integer compId;

	/**公司名称*/
	private String name;

	private Integer payType;

	private Integer isCustomerAdd;

	/**公司简称*/
	private String shortname;

	/**客户来源*/
	private Integer source;

	/**联系人*/
	private String linkman;

	/**手机*/
	private String mobile;

	/**邮箱*/
	private String email;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

	private String visatype;
}