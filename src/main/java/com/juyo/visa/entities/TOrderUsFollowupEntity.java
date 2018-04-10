package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_order_us_followup")
public class TOrderUsFollowupEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("订单id")
	private Integer orderid;
	
	@Column
    @Comment("用户id")
	private Integer userid;
	
	@Column
    @Comment("内容")
	private String content;
	
	@Column
    @Comment("解决人")
	private Integer solveid;
	
	@Column
    @Comment("解决时间")
	private Date solvetime;
	
	@Column
    @Comment("状态")
	private Integer status;
	
	@Column
    @Comment("创建时间")
	private Date createtime;
	
	@Column
    @Comment("更新时间")
	private Date updatetime;
	

}