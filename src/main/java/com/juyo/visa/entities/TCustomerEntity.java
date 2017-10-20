package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_customer")
public class TCustomerEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("用户id")
	private Integer userId;
	
	@Column
    @Comment("公司id")
	private Integer compId;
	
	@Column
    @Comment("公司名称")
	private String name;
	
	@Column
    @Comment("公司简称")
	private String shortname;
	
	@Column
    @Comment("客户来源")
	private Integer source;
	
	@Column
    @Comment("联系人")
	private String linkman;
	
	@Column
    @Comment("手机")
	private String mobile;
	
	@Column
    @Comment("邮箱")
	private String email;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	
	@Column
    @Comment("更新时间")
	private Date updateTime;
	

}