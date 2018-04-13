package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_app_staff_formerspouse")
public class TAppStaffFormerspouseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffid;
	
	@Column
    @Comment("前妻的数量")
	private Integer formerspousecount;
	
	@Column
    @Comment("前妻的姓")
	private String firstname;
	
	@Column
    @Comment("前妻的名")
	private String lastname;
	
	@Column
    @Comment("前妻的生日")
	private Date birthday;
	
	@Column
    @Comment("前妻的国籍")
	private Integer nationality;
	
	@Column
    @Comment("前妻的出生城市")
	private String city;
	
	@Column
    @Comment("是否知道前妻的出生城市")
	private Integer isknowcity;
	
	@Column
    @Comment("前妻的出生国家")
	private Integer country;
	
	@Column
    @Comment("结婚日期")
	private Date marrieddate;
	
	@Column
    @Comment("离婚日期")
	private Date divorcedate;
	
	@Column
    @Comment("离婚原因")
	private String divorceexplain;
	
	@Column
    @Comment("离婚国家")
	private Integer divorce;
	
	@Column
    @Comment("前妻的数量(英文)")
	private Integer formerspousecounten;
	
	@Column
    @Comment("前妻的姓(英文)")
	private String firstnameen;
	
	@Column
    @Comment("前妻的名(英文)")
	private String lastnameen;
	
	@Column
    @Comment("前妻的生日(英文)")
	private Date birthdayen;
	
	@Column
    @Comment("前妻的国籍(英文)")
	private Integer nationalityen;
	
	@Column
    @Comment("前妻的出生城市(英文)")
	private String cityen;
	
	@Column
    @Comment("是否知道前妻的出生城市(英文)")
	private Integer isknowcityen;
	
	@Column
    @Comment("前妻的出生国家(英文)")
	private Integer countryen;
	
	@Column
    @Comment("结婚日期(英文)")
	private Date marrieddateen;
	
	@Column
    @Comment("离婚日期(英文)")
	private Date divorcedateen;
	
	@Column
    @Comment("离婚原因(英文)")
	private String divorceexplainen;
	
	@Column
    @Comment("离婚国家(英文)")
	private Integer divorceen;
	

}