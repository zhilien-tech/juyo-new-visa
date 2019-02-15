package com.juyo.visa.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_hotel_us")
public class THotelUsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("酒店名称")
	private String name;

	@Column
	@Comment("酒店名称(英文)")
	private String nameen;

	@Column
	@Comment("酒店地址")
	private String address;

	@Column
	@Comment("酒店地址(原英文)")
	private String addressen;

	@Column
	@Comment("电话")
	private String telephone;

	@Column
	@Comment("邮政编码")
	private String zipcode;

	@Column
	@Comment("星级")
	private String star;

	@Column
	@Comment("所属城市id")
	private Integer cityId;

}