package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_order_travelplan_jp")
public class TOrderTravelplanJpEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("订单id")
	private Integer orderId;
	
	@Column
    @Comment("天数")
	private String day;
	
	@Column
    @Comment("日期")
	private Date outDate;
	
	@Column
    @Comment("景区")
	private String scenic;
	
	@Column
    @Comment("酒店")
	private Integer hotel;
	
	@Column
    @Comment("操作人id")
	private Integer opId;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	
	@Column
    @Comment("更新时间")
	private Date updateTime;
	

}