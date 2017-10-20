package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_company")
public class TCompanyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("公司名称")
	private String name;
	
	@Column
    @Comment("公司简称")
	private String shortName;
	
	@Column
    @Comment("管理员账号id")
	private Integer adminId;
	
	@Column
    @Comment("联系人")
	private String linkman;
	
	@Column
    @Comment("联系人手机号")
	private String mobile;
	
	@Column
    @Comment("邮箱")
	private String email;
	
	@Column
    @Comment("地址")
	private String address;
	
	@Column
    @Comment("公司类型")
	private Integer comType;
	
	@Column
    @Comment("营业执照")
	private String license;
	
	@Column
    @Comment("操作人")
	private Integer opId;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	
	@Column
    @Comment("修改时间")
	private Date updateTime;
	
	@Column
    @Comment("删除标识")
	private Integer deletestatus;
	

}