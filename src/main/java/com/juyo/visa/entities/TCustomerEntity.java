package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_customer")
public class TCustomerEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("用户id")
	private Integer userId;

	@Column
	@Comment("公司id")
	private Integer compId;

	@Column
	@Comment("公司名称")
	private String name;

	@Column
	@Comment("支付方式")
	private Integer payType;

	@Column
	@Comment("是否来自客户信息")
	private Integer isCustomerAdd;

	@Column
	@Comment("公司简称")
	private String shortname;

	@Column
	@Comment("客户来源")
	private Integer source;

	@Column
	@Comment("联系人")
	private String linkman;

	@Column
	@Comment("手机")
	private String mobile;

	@Column
	@Comment("邮箱")
	private String email;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("更新时间")
	private Date updateTime;

}