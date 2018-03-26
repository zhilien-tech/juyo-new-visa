package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_app_staff_conscientious")
public class TAppStaffConscientiousEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffid;
	
	@Column
    @Comment("服兵役国家")
	private Integer militarycountry;
	
	@Column
    @Comment("服务分支")
	private String servicebranch;
	
	@Column
    @Comment("排名")
	private String rank;
	
	@Column
    @Comment("军事专业")
	private String militaryspecialty;
	
	@Column
    @Comment("服兵役开始时间")
	private Date servicestartdate;
	
	@Column
    @Comment("服兵役结束时间")
	private Date serviceenddate;
	
	@Column
    @Comment("服兵役国家(英文)")
	private Integer militarycountryen;
	
	@Column
    @Comment("服务分支(英文)")
	private String servicebranchen;
	
	@Column
    @Comment("排名(英文)")
	private String ranken;
	
	@Column
    @Comment("军事专业(英文)")
	private String militaryspecialtyen;
	
	@Column
    @Comment("服兵役开始时间(英文)")
	private Date servicestartdateen;
	
	@Column
    @Comment("服兵役结束时间(英文)")
	private Date serviceenddateen;
	

}