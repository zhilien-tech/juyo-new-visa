package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_company")
public class TCompanyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("公司名称")
	private String name;

	@Column
	@Comment("公司简称")
	private String shortName;

	@Column
	@Comment("管理员账号id")
	private Integer adminId;

	@Column
	@Comment("联系人")
	private String linkman;

	@Column
	@Comment("联系人手机号")
	private String mobile;

	@Column
	@Comment("邮箱")
	private String email;

	@Column
	@Comment("地址")
	private String address;

	@Column
	@Comment("公司类型")
	private Integer comType;

	@Column
	@Comment("营业执照")
	private String license;

	@Column
	@Comment("公章")
	private String seal;

	@Column
	@Comment("是否是客户")
	private Integer isCustomer;

	@Column
	@Comment("客户指定番号")
	private String cdesignNum;

	@Column
	@Comment("账户余额")
	private Double accountBalance;

	@Column
	@Comment("奖励金额")
	private Double awardMoney;
	@Column
	@Comment("广告url")
	private String adUrl;

	@Column
	@Comment("操作人")
	private Integer opId;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("修改时间")
	private Date updateTime;

	@Column
	@Comment("删除标识")
	private Integer deletestatus;

}