package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_app_staff_beforeeducation")
public class TAppStaffBeforeeducationEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffid;
	
	@Column
    @Comment("机构名称")
	private String institution;
	
	@Column
    @Comment("机构地址")
	private String institutionaddress;
	
	@Column
    @Comment("机构地址次选")
	private String secinstitutionaddress;
	
	@Column
    @Comment("机构所在市")
	private String institutioncity;
	
	@Column
    @Comment("机构所在省")
	private String institutionprovince;
	
	@Column
    @Comment("机构所在省是否适用")
	private Integer isinstitutionprovinceapply;
	
	@Column
    @Comment("机构邮政编码")
	private String institutionzipcode;
	
	@Column
    @Comment("机构邮政编码是否适用")
	private Integer isinstitutionzipcodeapply;
	
	@Column
    @Comment("机构所在国家")
	private Integer institutioncountry;
	
	@Column
    @Comment("学科")
	private String course;
	
	@Column
    @Comment("课程开始时间")
	private Date coursestartdate;
	
	@Column
    @Comment("课程结束时间")
	private Date courseenddate;
	
	@Column
    @Comment("机构名称(英文)")
	private String institutionen;
	
	@Column
    @Comment("机构地址(英文)")
	private String institutionaddressen;
	
	@Column
    @Comment("机构地址次选(英文)")
	private String secinstitutionaddressen;
	
	@Column
    @Comment("机构所在市(英文)")
	private String institutioncityen;
	
	@Column
    @Comment("机构所在省(英文)")
	private String institutionprovinceen;
	
	@Column
    @Comment("机构所在省是否适用(英文)")
	private Integer isinstitutionprovinceapplyen;
	
	@Column
    @Comment("机构邮政编码(英文)")
	private String institutionzipcodeen;
	
	@Column
    @Comment("机构邮政编码是否适用(英文)")
	private Integer isinstitutionzipcodeapplyen;
	
	@Column
    @Comment("机构所在国家(英文)")
	private Integer institutioncountryen;
	
	@Column
    @Comment("学科(英文)")
	private String courseen;
	
	@Column
    @Comment("课程开始时间(英文)")
	private Date coursestartdateen;
	
	@Column
    @Comment("课程结束时间(英文)")
	private Date courseenddateen;
	

}