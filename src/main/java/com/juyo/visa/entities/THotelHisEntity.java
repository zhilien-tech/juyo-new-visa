/**
 * THotelHisEntity.java
 * com.juyo.visa.entities
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;

public class THotelHisEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	@Column
	@Comment("订单Id")
	private String OrderId;

	@Column
	@Comment("酒店名称(中文)")
	private String name;

	@Column
	@Comment("酒店名称(原文)")
	private String namejp;

	@Column
	@Comment("酒店地址(中文)")
	private String address;

	@Column
	@Comment("酒店地址(原文)")
	private String addressjp;

	@Column
	@Comment("电话")
	private String mobile;

	@Column
	@Comment("所属城市")
	private String city;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("更新时间")
	private Date updateTime;

}
