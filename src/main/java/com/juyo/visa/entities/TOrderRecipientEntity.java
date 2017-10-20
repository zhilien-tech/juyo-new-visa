package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_order_recipient")
public class TOrderRecipientEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("订单id")
	private Integer orderId;
	
	@Column
    @Comment("收件地址id")
	private Integer receiveAddressId;
	
	@Column
    @Comment("快递方式")
	private Integer expressType;
	
	@Column
    @Comment("收件人")
	private String receiver;
	
	@Column
    @Comment("电话")
	private String telephone;
	
	@Column
    @Comment("收件地址")
	private String expressAddress;
	
	@Column
    @Comment("操作人")
	private Integer opId;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	
	@Column
    @Comment("更新时间")
	private Date updateTime;
	

}