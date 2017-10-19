package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_applicant_passport")
public class TApplicantPassportEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("申请人id")
	private Integer applicantId;
	
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
    @Comment("类型")
	private String type;
	
	@Column
    @Comment("护照号")
	private String passport;
	
	@Column
    @Comment("性别")
	private String sex;
	
	@Column
    @Comment("性别(拼音)")
	private String sexEn;
	
	@Column
    @Comment("出生地点")
	private String birthAddress;
	
	@Column
    @Comment("出生地点(拼音)")
	private String birthAddressEn;
	
	@Column
    @Comment("出生日期")
	private Date birthday;
	
	@Column
    @Comment("签发地点")
	private String issuedPlace;
	
	@Column
    @Comment("签发地点(拼音)")
	private String issuedPlaceEn;
	
	@Column
    @Comment("签发日期")
	private Date issuedDate;
	
	@Column
    @Comment("有效期始")
	private Date validStartDate;
	
	@Column
    @Comment("有效类型")
	private Integer validType;
	
	@Column
    @Comment("有效期至")
	private Date validEndDate;
	
	@Column
    @Comment("签发机关")
	private String issuedOrganization;
	
	@Column
    @Comment("签发机关(拼音)")
	private String issuedOrganizationEn;
	
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