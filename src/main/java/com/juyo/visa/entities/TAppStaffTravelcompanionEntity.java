package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_app_staff_travelcompanion")
public class TAppStaffTravelcompanionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private String staffid;
	
	@Column
    @Comment("是否与其他人一起旅行")
	private Integer istravelwithother;
	
	@Column
    @Comment("是否与其他人一起旅行(英文)")
	private Integer istravelwithotheren;
	
	@Column
    @Comment("是否作为团队或组织的一部分")
	private Integer ispart;
	
	@Column
    @Comment("是否作为团队或组织的一部分(英文)")
	private Integer isparten;
	
	@Column
    @Comment("团队名称")
	private String groupname;
	
	@Column
    @Comment("团队名称(英文)")
	private String groupnameen;
	
	@Column
    @Comment("同伴的姓")
	private String firstname;
	
	@Column
    @Comment("同伴的姓(英文)")
	private String firstnameen;
	
	@Column
    @Comment("同伴的名")
	private String lastname;
	
	@Column
    @Comment("同伴的名(英文)")
	private String lastnameen;
	
	@Column
    @Comment("与你的关系")
	private Integer relationship;
	
	@Column
    @Comment("与你的关系(英文)")
	private Integer relationshipen;
	

}