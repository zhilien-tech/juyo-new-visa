package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_user")
public class TUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("公司id")
	private Integer comId;

	@Column
	@Comment("用户姓名")
	private String name;

	@Column
	@Comment("用户名/手机号码")
	private String mobile;

	@Column
	@Comment("联系QQ")
	private String qq;

	@Column
	@Comment("电子邮箱")
	private String email;

	@Column
	@Comment("所属部门id")
	private Integer departmentId;

	@Column
	@Comment("用户职位id")
	private Integer jobId;

	@Column
	@Comment("用户是否禁用")
	private Integer isDisable;

	@Column
	@Comment("密码")
	private String password;

	@Column
	@Comment("用户类型")
	private Integer userType;

	@Column
	@Comment("上次登陆时间")
	private Date lastLoginTime;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("修改时间")
	private Date updateTime;

	@Column
	@Comment("操作人id")
	private Integer opId;

	@Column
	@Comment("详情页类型")
	private Integer ordertype;

}