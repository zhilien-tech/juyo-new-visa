package com.juyo.visa.admin.order.entity;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_idcard")
public class TIdcardEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("代码")
	private String code;
	
	@Column
    @Comment("地区")
	private String area;
	
	@Column
    @Comment("省份代码")
	private String provincecode;
	
	@Column
    @Comment("省份")
	private String province;
	
	@Column
    @Comment("城市代码")
	private String citycode;
	
	@Column
    @Comment("城市")
	private String city;
	
	@Column
    @Comment("县代码")
	private String countycode;
	
	@Column
    @Comment("县区")
	private String county;
	

}