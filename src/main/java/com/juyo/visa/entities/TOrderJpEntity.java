package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_order_jp")
public class TOrderJpEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("订单id")
	private Integer orderId;
	
	@Column
    @Comment("签证类型")
	private Integer visaType;
	
	@Column
    @Comment("签证县")
	private String visaCounty;
	
	@Column
    @Comment("过去三年是否访问")
	private Integer isVisit;
	
	@Column
    @Comment("过去三年访问过的县")
	private String threeCounty;
	

}