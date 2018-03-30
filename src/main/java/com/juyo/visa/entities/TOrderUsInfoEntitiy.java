/**
 * TOrderInfoEntitiy.java
 * com.juyo.visa.entities
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   马云鹏
 * @Date	 2018年3月24日 	 
 */
@Data
@Table("t_order_us")
public class TOrderUsInfoEntitiy implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	@Comment("订单号")
	private String ordernumber;

	@Column
	@Comment("订单状态")
	private Integer status;

	@Column
	@Comment("AA码")
	private Integer aacode;

	@Column
	@Comment("出发城市(去程)")
	private String goDepartureCity;

	@Column
	@Comment("抵达城市(去程)")
	private String goArrivedCity;

	@Column
	@Comment("出发城市(返程)")
	private String returnDepartureCity;

	@Column
	@Comment("返回城市(返程)")
	private String returnArrivedCity;

}
