package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_applicant")
public class TApplicantEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("主申请人id")
	private Integer mainId;
	
	@Column
    @Comment("申请人状态")
	private Integer status;
	
	@Column
    @Comment("用户id（登录用户id）")
	private Integer userId;
	
	@Column
    @Comment("姓")
	private String firstName;
	
	@Column
    @Comment("姓(拼音)")
	private String firstNameEn;
	
	@Column
    @Comment("名")
	private String lastName;
	
	@Column
    @Comment("名(拼音)")
	private String lastNameEn;
	
	@Column
    @Comment("手机号")
	private String telephone;
	
	@Column
    @Comment("邮箱")
	private String email;
	
	@Column
    @Comment("性别")
	private Integer sex;
	
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
	private String cardId;
	
	@Column
    @Comment("身份证正面")
	private String cardFront;
	
	@Column
    @Comment("身份证反面")
	private String cardBack;
	
	@Column
    @Comment("签发机关")
	private String issueOrganization;
	
	@Column
    @Comment("有效期始")
	private Date validStartDate;
	
	@Column
    @Comment("有效期至")
	private Date validEndDate;
	
	@Column
    @Comment("现居住地址省份")
	private String province;
	
	@Column
    @Comment("现居住地址城市")
	private String city;
	
	@Column
    @Comment("详细地址")
	private String detailedAddress;
	
	@Column
    @Comment("操作人")
	private Integer opId;
	
	@Column
    @Comment("更新时间")
	private Date updateTime;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	

}