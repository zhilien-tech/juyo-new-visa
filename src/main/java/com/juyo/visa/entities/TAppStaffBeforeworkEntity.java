package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_app_staff_beforework")
public class TAppStaffBeforeworkEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffid;
	
	@Column
    @Comment("雇主姓名")
	private String employername;
	
	@Column
    @Comment("雇主街道地址")
	private String employeraddress;
	
	@Column
    @Comment("雇主街道地址(次选)")
	private String employeraddressSec;
	
	@Column
    @Comment("雇主所在市")
	private String employercity;
	
	@Column
    @Comment("雇主所在省")
	private String employerprovince;
	
	@Column
    @Comment("雇主所在省是否适用")
	private Integer isemployerprovinceapply;
	
	@Column
    @Comment("雇主邮政编码")
	private String employerzipcode;
	
	@Column
    @Comment("雇主邮政编码是否适用")
	private Integer isemployerzipcodeapply;
	
	@Column
    @Comment("雇主所在国家")
	private Integer employercountry;
	
	@Column
    @Comment("雇主电话")
	private String employertelephone;
	
	@Column
    @Comment("职称")
	private String jobtitle;
	
	@Column
    @Comment("主管的姓")
	private String supervisorfirstname;
	
	@Column
    @Comment("是否知道主管的姓")
	private Integer isknowsupervisorfirstname;
	
	@Column
    @Comment("主管的名")
	private String supervisorlastname;
	
	@Column
    @Comment("是否知道主管的名")
	private Integer isknowsupervisorlastname;
	
	@Column
    @Comment("入职时间")
	private Date employstartdate;
	
	@Column
    @Comment("离职时间")
	private Date employenddate;
	
	@Column
    @Comment("以前的工作职责")
	private String previousduty;
	
	@Column
    @Comment("雇主姓名(英文)")
	private String employernameen;
	
	@Column
    @Comment("雇主街道地址(英文)")
	private String employeraddressen;
	
	@Column
    @Comment("雇主街道地址(次选)(英文)")
	private String employeraddressSecen;
	
	@Column
    @Comment("雇主所在市(英文)")
	private String employercityen;
	
	@Column
    @Comment("雇主所在省(英文)")
	private String employerprovinceen;
	
	@Column
    @Comment("雇主所在省是否适用(英文)")
	private Integer isemployerprovinceapplyen;
	
	@Column
    @Comment("雇主邮政编码(英文)")
	private String employerzipcodeen;
	
	@Column
    @Comment("雇主邮政编码是否适用(英文)")
	private Integer isemployerzipcodeapplyen;
	
	@Column
    @Comment("雇主所在国家(英文)")
	private Integer employercountryen;
	
	@Column
    @Comment("雇主电话(英文)")
	private String employertelephoneen;
	
	@Column
    @Comment("职称(英文)")
	private String jobtitleen;
	
	@Column
    @Comment("主管的姓(英文)")
	private String supervisorfirstnameen;
	
	@Column
    @Comment("是否知道主管的姓(英文)")
	private Integer isknowsupervisorfirstnameen;
	
	@Column
    @Comment("主管的名(英文)")
	private String supervisorlastnameen;
	
	@Column
    @Comment("是否知道主管的名(英文)")
	private Integer isknowsupervisorlastnameen;
	
	@Column
    @Comment("入职时间(英文)")
	private Date employstartdateen;
	
	@Column
    @Comment("离职时间(英文)")
	private Date employenddateen;
	
	@Column
    @Comment("以前的工作职责(英文)")
	private String previousdutyen;
	

}