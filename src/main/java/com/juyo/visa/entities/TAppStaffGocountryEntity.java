package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_app_staff_gocountry")
public class TAppStaffGocountryEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffid;
	
	@Column
    @Comment("去旅游的国家")
	private Integer traveledcountry;
	
	@Column
    @Comment("去旅游的国家(英文)")
	private Integer traveledcountryen;
	

}