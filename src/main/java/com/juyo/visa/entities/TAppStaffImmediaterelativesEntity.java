package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_app_staff_immediaterelatives")
public class TAppStaffImmediaterelativesEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffid;
	
	@Column
    @Comment("亲属的姓")
	private String relativesfirstname;
	
	@Column
    @Comment("亲属的名")
	private String relativeslastname;
	
	@Column
    @Comment("与你的关系")
	private Integer relationship;
	
	@Column
    @Comment("亲属的身份")
	private Integer relativesstatus;
	
	@Column
    @Comment("亲属的姓(英文)")
	private String relativesfirstnameen;
	
	@Column
    @Comment("亲属的名(英文)")
	private String relativeslastnameen;
	
	@Column
    @Comment("与你的关系(英文)")
	private Integer relationshipen;
	
	@Column
    @Comment("亲属的身份(英文)")
	private Integer relativesstatusen;
	

}