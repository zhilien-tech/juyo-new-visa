package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_app_staff_passport")
public class TAppStaffPassportEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffid;
	
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
	private String sexen;
	
	@Column
    @Comment("出生地点")
	private String birthaddress;
	
	@Column
    @Comment("出生地点(拼音)")
	private String birthaddressen;
	
	@Column
    @Comment("出生日期")
	private Date birthday;
	
	@Column
    @Comment("签发地点")
	private String issuedplace;
	
	@Column
    @Comment("签发地点(拼音)")
	private String issuedplaceen;
	
	@Column
    @Comment("签发日期")
	private Date issueddate;
	
	@Column
    @Comment("有效期始")
	private Date validstartdate;
	
	@Column
    @Comment("有效类型")
	private Integer validtype;
	
	@Column
    @Comment("有效期至")
	private Date validenddate;
	
	@Column
    @Comment("签发机关")
	private String issuedorganization;
	
	@Column
    @Comment("签发机关(拼音)")
	private String issuedorganizationen;
	
	@Column
    @Comment("操作人")
	private Integer opid;
	
	@Column
    @Comment("创建时间")
	private Date createtime;
	
	@Column
    @Comment("更新时间")
	private Date updatetime;
	
	@Column
    @Comment("护照地址")
	private String passporturl;
	
	@Column
    @Comment("OCR识别码第一行")
	private String oCRline1;
	
	@Column
    @Comment("OCR识别码第二行")
	private String oCRline2;
	

}