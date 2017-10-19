package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_applicant_visa_jp")
public class TApplicantVisaJpEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("申请人id")
	private Integer applicantId;
	
	@Column
    @Comment("签发编号")
	private String visaNum;
	
	@Column
    @Comment("签发地")
	private String visaAddress;
	
	@Column
    @Comment("签证类型")
	private Integer visaType;
	
	@Column
    @Comment("年限(年)")
	private Integer visaYears;
	
	@Column
    @Comment("签发时间")
	private Date visaDate;
	
	@Column
    @Comment("有效期至")
	private Date validDate;
	
	@Column
    @Comment("停留时间(天)")
	private Integer stayDays;
	
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