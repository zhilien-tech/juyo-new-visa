package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_app_staff_paperwork_us")
public class TAppStaffPaperworkUsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffid;
	
	@Column
    @Comment("资料类型(同职业状况)")
	private Integer type;
	
	@Column
    @Comment("真实资料(证件)")
	private Integer content;
	
	@Column
    @Comment("资料本数")
	private Integer count;
	
	@Column
    @Comment("操作人")
	private Integer opid;
	
	@Column
    @Comment("创建时间")
	private Date createtime;
	
	@Column
    @Comment("更新时间")
	private Date updatetime;
	
	@Column
    @Comment("真实资料")
	private String realinfo;
	
	@Column
    @Comment("状态 1：已收 0：未收")
	private Integer status;
	

}