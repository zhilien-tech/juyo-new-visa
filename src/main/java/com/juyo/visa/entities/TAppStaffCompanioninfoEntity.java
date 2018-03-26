package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_app_staff_companioninfo")
public class TAppStaffCompanioninfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffid;
	
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