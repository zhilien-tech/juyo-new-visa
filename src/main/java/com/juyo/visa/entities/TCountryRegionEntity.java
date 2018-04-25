package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_country_region")
public class TCountryRegionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("三字代码")
	private String code;
	
	@Column
    @Comment("名称")
	private String name;
	
	@Column
    @Comment("中文名")
	private String chinesename;
	
	@Column
    @Comment("所属洲")
	private String continent;
	
	@Column
    @Comment("所属区域")
	private String region;
	
	@Column
    @Comment("国旗url")
	private String flagurl;
	

}