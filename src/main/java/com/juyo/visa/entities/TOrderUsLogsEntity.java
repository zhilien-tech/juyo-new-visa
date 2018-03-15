package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_order_us_logs")
public class TOrderUsLogsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("订单id")
	private Integer orderid;
	
	@Column
    @Comment("订单状态")
	private Integer orderstatus;
	
	@Column
    @Comment("备注")
	private String remark;
	
	@Column
    @Comment("操作人")
	private Integer opid;
	
	@Column
    @Comment("创建时间")
	private Date createtime;
	
	@Column
    @Comment("更新时间")
	private Date updatetime;
	

}