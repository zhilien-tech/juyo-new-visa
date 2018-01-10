package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

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
	@Comment("送签编号")
	private String sendVisaNum;

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
	@Comment("是否作废")
	private Integer isDisabled;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("更新时间")
	private Date updateTime;

	@Column
	@Comment("销售操作人id")
	private Integer salesOpid;

	@Column
	@Comment("初审操作人id")
	private Integer trialOpid;

	@Column
	@Comment("前台操作人id")
	private Integer receptionOpid;

	@Column
	@Comment("签证操作人id")
	private Integer visaOpid;

	@Column
	@Comment("售后操作人id")
	private Integer aftermarketOpid;

}