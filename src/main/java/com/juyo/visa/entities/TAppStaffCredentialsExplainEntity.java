package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_app_staff_credentials_explain")
public class TAppStaffCredentialsExplainEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffid;
	
	@Column
    @Comment("证件类型")
	private Integer type;
	
	@Column
    @Comment("产权人")
	private String propertyholder;
	
	@Column
    @Comment("面积")
	private String area;
	
	@Column
    @Comment("地址")
	private String address;
	
	@Column
    @Comment("创建时间")
	private Date createtime;
	
	@Column
    @Comment("更新时间")
	private Date updatetime;
	

}