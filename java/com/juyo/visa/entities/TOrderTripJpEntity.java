package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_order_trip_jp")
public class TOrderTripJpEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("订单id")
	private Integer orderId;

	@Column
	@Comment("行程类型")
	private Integer tripType;

	@Column
	@Comment("出行目的")
	private String tripPurpose;

	@Column
	@Comment("出发日期(去程)")
	private Date goDate;

	@Column
	@Comment("出发城市(去程)")
	private Integer goDepartureCity;

	@Column
	@Comment("抵达城市(去程)")
	private Integer goArrivedCity;

	@Column
	@Comment("航班号(去程)")
	private String goFlightNum;

	@Column
	@Comment("出发日期(返程)")
	private Date returnDate;

	@Column
	@Comment("出发城市(返程)")
	private Integer returnDepartureCity;

	@Column
	@Comment("返回城市(返程)")
	private Integer returnArrivedCity;

	@Column
	@Comment("航班号(返程)")
	private String returnFlightNum;

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