package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_applicant")
public class TApplicantLowerEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("主申请人id")
	private Integer mainid;

	@Column
	@Comment("申请人状态")
	private Integer status;

	@Column
	@Comment("用户id（登录用户id）")
	private Integer userid;

	@Column
	@Comment("姓")
	private String firstname;

	@Column
	@Comment("姓(拼音)")
	private String firstnameen;

	@Column
	@Comment("名")
	private String lastname;

	@Column
	@Comment("名(拼音)")
	private String lastnameen;

	@Column
	@Comment("曾用姓")
	private String otherfirstname;

	@Column
	@Comment("曾用名")
	private String otherlastname;

	@Column
	@Comment("曾用姓(拼音)")
	private String otherfirstnameen;

	@Column
	@Comment("曾用名(拼音)")
	private String otherlastnameen;

	@Column
	@Comment("紧急联系人姓名")
	private String emergencylinkman;

	@Column
	@Comment("紧急联系人手机")
	private String emergencytelephone;

	@Column
	@Comment("是否另有国籍")
	private Integer hasothernationality;

	@Column
	@Comment("国籍")
	private String nationality;

	@Column
	@Comment("是否有曾用名")
	private Integer hasothername;

	@Column
	@Comment("现居住地是否与身份证相同")
	private Integer addressissamewithcard;

	@Column
	@Comment("结婚证/离婚证地址")
	private String marryurl;

	@Column
	@Comment("结婚状况")
	private Integer marrystatus;

	@Column
	@Comment("身份证省份")
	private String cardprovince;

	@Column
	@Comment("身份证城市")
	private String cardcity;

	@Column
	@Comment("手机号")
	private String telephone;

	@Column
	@Comment("邮箱")
	private String email;

	@Column
	@Comment("性别")
	private String sex;

	@Column
	@Comment("民族")
	private String nation;

	@Column
	@Comment("出生日期")
	private Date birthday;

	@Column
	@Comment("住址")
	private String address;

	@Column
	@Comment("身份证号")
	private String cardid;

	@Column
	@Comment("身份证正面")
	private String cardfront;

	@Column
	@Comment("身份证反面")
	private String cardback;

	@Column
	@Comment("签发机关")
	private String issueorganization;

	@Column
	@Comment("有效期始")
	private Date validstartdate;

	@Column
	@Comment("有效期至")
	private Date validenddate;

	@Column
	@Comment("现居住地址省份")
	private String province;

	@Column
	@Comment("现居住地址城市")
	private String city;

	@Column
	@Comment("详细地址")
	private String detailedaddress;

	@Column
	@Comment("操作人")
	private Integer opid;

	@Column
	@Comment("更新时间")
	private Date updatetime;

	@Column
	@Comment("创建时间")
	private Date createtime;

	@Column
	@Comment("婚姻状况证件类型")
	private Integer marryurltype;

}