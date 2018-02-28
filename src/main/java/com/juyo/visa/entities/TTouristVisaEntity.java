package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_tourist_visa")
public class TTouristVisaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("用户id")
	private Integer userId;

	@Column
	@Comment("我的职业")
	private String occupation;

	@Column
	@Comment("申请人ID")
	private Integer applicantId;

	@Column
	@Comment("父母/配偶单位名称")
	private String unitName;

	@Column
	@Comment("单位名称")
	private String name;

	@Column
	@Comment("单位电话")
	private String telephone;

	@Column
	@Comment("预准备资料")
	private String prepareMaterials;

	@Column
	@Comment("单位地址")
	private String address;

	@Column
	@Comment("车产")
	private String vehicle;

	@Column
	@Comment("财富信息是否同主")
	private Integer sameMainWealth;

	@Column
	@Comment("工作信息是否同主")
	private Integer sameMainWork;

	@Column
	@Comment("银行存款")
	private String deposit;

	@Column
	@Comment("房产")
	private String houseProperty;

	@Column
	@Comment("理财")
	private String financial;

	@Column
	@Comment("职业状况")
	private Integer careerStatus;

	@Column
	@Comment("婚姻状况")
	private Integer marryStatus;

	@Column
	@Comment("结婚证/离婚证地址")
	private String marryUrl;

	@Column
	@Comment("主申请人id")
	private Integer mainId;

	@Column
	@Comment("是否是主申请人")
	private Integer isMainApplicant;

	@Column
	@Comment("与主申请人的关系")
	private String mainRelation;

	@Column
	@Comment("与主申请人关系备注")
	private String relationRemark;

	@Column
	@Comment("签证信息是否填写完毕")
	private Integer visaIsCompleted;

	@Column
	@Comment("操作人")
	private Integer opId;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("更新时间")
	private Date updateTime;

}