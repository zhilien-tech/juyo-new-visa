package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


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
    @Comment("创建时间")
	private Date createTime;
	
	@Column
    @Comment("更新时间")
	private Date updateTime;
	

}