package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_order")
public class TOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("订单号")
	private String orderNum;
	
	@Column
    @Comment("用户id")
	private Integer userId;
	
	@Column
    @Comment("公司id")
	private Integer comId;
	
	@Column
    @Comment("订单状态")
	private Integer status;
	
	@Column
    @Comment("人数")
	private Integer number;
	
	@Column
    @Comment("领区")
	private Integer cityId;
	
	@Column
    @Comment("加急类型")
	private Integer urgentType;
	
	@Column
    @Comment("加急天数")
	private Integer urgentDay;
	
	@Column
    @Comment("行程")
	private Integer travel;
	
	@Column
    @Comment("付款方式")
	private Integer payType;
	
	@Column
    @Comment("金额")
	private Double money;
	
	@Column
    @Comment("出行时间")
	private Date goTripDate;
	
	@Column
    @Comment("停留天数")
	private Integer stayDay;
	
	@Column
    @Comment("返回时间")
	private Date backTripDate;
	
	@Column
    @Comment("送签时间")
	private Date sendVisaDate;
	
	@Column
    @Comment("出签时间")
	private Date outVisaDate;
	
	@Column
    @Comment("实收备注")
	private String realReceiveRemark;
	
	@Column
    @Comment("客户管理id")
	private Integer customerId;
	
	@Column
    @Comment("客户来源是否为直客")
	private Integer isDirectCus;
	
	@Column
    @Comment("公司全称")
	private String comName;
	
	@Column
    @Comment("公司简称")
	private String comShortName;
	
	@Column
    @Comment("联系人")
	private String linkman;
	
	@Column
    @Comment("手机")
	private String telephone;
	
	@Column
    @Comment("邮箱")
	private String email;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	
	@Column
    @Comment("更新时间")
	private Date updateTime;
	

}