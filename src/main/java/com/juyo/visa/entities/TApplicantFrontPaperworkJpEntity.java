package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_applicant_front_paperwork_jp")
public class TApplicantFrontPaperworkJpEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("申请人id")
	private Integer applicantId;
	
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
	private Integer opId;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	
	@Column
    @Comment("更新时间")
	private Date updateTime;
	

}