package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_flight")
public class TFlightEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("航班号")
	private String flightnum;
	
	@Column
    @Comment("航空公司")
	private String airlinecomp;
	
	@Column
    @Comment("起飞机场")
	private String takeOffName;
	
	@Column
    @Comment("起飞机场三字代码")
	private String takeOffCode;
	
	@Column
    @Comment("降落机场")
	private String landingName;
	
	@Column
    @Comment("降落机场三字代码")
	private String landingCode;
	
	@Column
    @Comment("起飞城市id")
	private Integer takeOffCityId;
	
	@Column
    @Comment("降落城市id")
	private Integer landingCityId;
	
	@Column
    @Comment("起飞时间")
	private String takeOffTime;
	
	@Column
    @Comment("降落时间")
	private String landingTime;
	
	@Column
    @Comment("起飞航站楼")
	private String takeOffTerminal;
	
	@Column
    @Comment("降落航站楼")
	private String landingTerminal;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	
	@Column
    @Comment("更新时间")
	private Date updateTime;
	

}