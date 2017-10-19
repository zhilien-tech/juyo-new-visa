package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_applicant_unqualified")
public class TApplicantUnqualifiedEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("申请人id")
	private Integer applicantId;
	
	@Column
    @Comment("基本信息是否合格")
	private Integer isBase;
	
	@Column
    @Comment("基本备注")
	private String baseRemark;
	
	@Column
    @Comment("护照信息是否合格")
	private Integer isPassport;
	
	@Column
    @Comment("护照备注")
	private String passRemark;
	
	@Column
    @Comment("签证信息是否合格")
	private Integer isVisa;
	
	@Column
    @Comment("签证备注")
	private String visaRemark;
	
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