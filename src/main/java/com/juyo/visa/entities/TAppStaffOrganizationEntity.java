package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_app_staff_organization")
public class TAppStaffOrganizationEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffid;
	
	@Column
    @Comment("组织名称")
	private String organizationname;
	
	@Column
    @Comment("组织名称(英文)")
	private String organizationnameen;
	

}