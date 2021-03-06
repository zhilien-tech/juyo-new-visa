package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_hotel")
public class THotelEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

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
	@Comment("所属城市id")
	private Integer cityId;

	@Column
	@Comment("所属城市区域")
	private String region;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("更新时间")
	private Date updateTime;

}