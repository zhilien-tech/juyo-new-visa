package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_app_staff_gousinfo")
public class TAppStaffGousinfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffid;
	
	@Column
    @Comment("抵达日期")
	private Date arrivedate;
	
	@Column
    @Comment("停留时间")
	private Integer staydays;
	
	@Column
    @Comment("日期单位")
	private Integer dateunit;
	
	@Column
    @Comment("抵达日期(英文)")
	private Date arrivedateen;
	
	@Column
    @Comment("停留时间(英文)")
	private Integer staydaysen;
	
	@Column
    @Comment("日期单位(英文)")
	private Integer dateuniten;
	

}