package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_applicant_express")
public class TApplicantExpressEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("申请人id")
	private Integer applicantId;
	
	@Column
    @Comment("快递号")
	private String expressNum;
	
	@Column
    @Comment("快递方式")
	private Integer expressType;
	
	@Column
    @Comment("资料来源")
	private Integer source;
	
	@Column
    @Comment("快递地址")
	private String expressAddress;
	
	@Column
    @Comment("联系人")
	private String linkman;
	
	@Column
    @Comment("电话")
	private String telephone;
	
	@Column
    @Comment("发票项目内容")
	private String invoiceContent;
	
	@Column
    @Comment("发票抬头")
	private String invoiceHead;
	
	@Column
    @Comment("税号")
	private String taxNum;
	
	@Column
    @Comment("备注")
	private String remark;
	
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