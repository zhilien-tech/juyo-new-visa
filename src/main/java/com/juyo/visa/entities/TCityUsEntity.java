package com.juyo.visa.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_city_us")
public class TCityUsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("城市名称")
	private String cityname;

	@Column
	@Comment("城市名称(英文)")
	private String citynameen;

	@Column
	@Comment("所属州id")
	private Integer stateid;

}