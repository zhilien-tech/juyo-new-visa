package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_applicant_work_jp")
public class TApplicantWorkJpEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("申请人id")
	private Integer applicantId;
	
	@Column
    @Comment("我的职业")
	private String occupation;
	
	@Column
    @Comment("单位名称")
	private String name;
	
	@Column
    @Comment("单位电话")
	private String telephone;
	
	@Column
    @Comment("单位地址")
	private String address;
	
	@Column
    @Comment("职业状况")
	private Integer careerStatus;
	
	@Column
    @Comment("操作人")
	private Integer opId;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	
	@Column
    @Comment("更新时间")
	private Date updateTime;
	

}