package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_applicant_order_jp")
public class TApplicantOrderJpEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("日本订单id")
	private Integer orderId;
	
	@Column
    @Comment("申请人id")
	private Integer applicantId;
	
	@Column
    @Comment("是否为统一联系人")
	private Integer isSameLinker;
	
	@Column
    @Comment("是否是主申请人")
	private Integer isMainApplicant;
	
	@Column
    @Comment("与主申请人的关系")
	private Integer mainRelation;
	
	@Column
    @Comment("与主申请人关系备注")
	private String relationRemark;
	
	@Column
    @Comment("出行信息是否同主")
	private Integer sameMainTrip;
	
	@Column
    @Comment("财富信息是否同主")
	private Integer sameMainWealth;
	
	@Column
    @Comment("工作信息是否同主")
	private Integer sameMainWork;
	
	@Column
    @Comment("视频地址")
	private String videoUrl;
	

}