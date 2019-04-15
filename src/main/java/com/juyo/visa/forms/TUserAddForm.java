package com.juyo.visa.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TUserAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**公司id*/
	private Integer comId;

	/**用户姓名*/
	private String name;

	/**用户名/手机号码*/
	private String mobile;

	/**联系QQ*/
	private String qq;

	/**电子邮箱*/
	private String email;

	/**所属部门id*/
	private Integer departmentId;

	/**用户职位id*/
	private Integer jobId;

	/**用户是否禁用*/
	private Integer isDisable;

	/**密码*/
	private String password;

	/**用户类型*/
	private Integer userType;

	/**上次登陆时间*/
	private Date lastLoginTime;

	/**创建时间*/
	private Date createTime;

	/**修改时间*/
	private Date updateTime;

	/**操作人id*/
	private Integer opId;

	private Integer ordertype;

}