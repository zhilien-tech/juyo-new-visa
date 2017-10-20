package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_job")
public class TJobEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("部门id")
	private Integer deptId;
	
	@Column
    @Comment("职位名称")
	private String jobName;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	
	@Column
    @Comment("修改时间")
	private Date updateTime;
	
	@Column
    @Comment("备注")
	private String remark;
	
	@Column
    @Comment("操作人id")
	private Integer opId;
	

}