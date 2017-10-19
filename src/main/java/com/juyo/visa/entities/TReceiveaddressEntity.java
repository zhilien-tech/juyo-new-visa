package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_receiveaddress")
public class TReceiveaddressEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("用户id")
	private Integer userId;
	
	@Column
    @Comment("公司id")
	private Integer comId;
	
	@Column
    @Comment("收件人")
	private String receiver;
	
	@Column
    @Comment("电话")
	private String mobile;
	
	@Column
    @Comment("收件地址")
	private String address;
	
	@Column
    @Comment("操作人id")
	private Integer opId;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	
	@Column
    @Comment("更新时间")
	private Date updateTime;
	

}