package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_app_staff_driverinfo")
public class TAppStaffDriverinfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffid;
	
	@Column
    @Comment("驾照号")
	private String driverlicensenumber;
	
	@Column
    @Comment("是否知道驾照号")
	private Integer isknowdrivernumber;
	
	@Column
    @Comment("哪个州的驾照")
	private Integer witchstateofdriver;
	
	@Column
    @Comment("驾照号(英文)")
	private String driverlicensenumberen;
	
	@Column
    @Comment("是否知道驾照号(英文)")
	private Integer isknowdrivernumberen;
	
	@Column
    @Comment("哪个州的驾照(英文)")
	private Integer witchstateofdriveren;
	

}