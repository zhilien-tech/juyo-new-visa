/**
 * TOrderTripHisJpEntity.java
 * com.juyo.visa.entities
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2018年4月3日 	 
 */
@Data
@Table("t_order_trip_his_jp")
public class TOrderTripHisJpEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("订单id")
	private Integer orderId;

	@Column
	@Comment("行程类型")
	private String tripType;

	@Column
	@Comment("出行目的")
	private String tripPurpose;

	@Column
	@Comment("出发日期(去程)")
	private Date goDate;

	@Column
	@Comment("出发城市(去程)")
	private String goDepartureCity;

	@Column
	@Comment("抵达城市(去程)")
	private String goArrivedCity;

	@Column
	@Comment("航班号(去程)")
	private String goFlightNum;

	@Column
	@Comment("出发日期(返程)")
	private Date returnDate;

	@Column
	@Comment("出发城市(返程)")
	private String returnDepartureCity;

	@Column
	@Comment("返回城市(返程)")
	private String returnArrivedCity;

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
