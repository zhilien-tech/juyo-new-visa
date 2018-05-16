package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_company_of_customer")
public class TCompanyOfCustomerEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("公司id")
	private Integer comid;
	
	@Column
	@Comment("送签社公司id")
	private Integer sendcomid;
	
	@Column
    @Comment("公司名称")
	private String fullname;
	
	@Column
    @Comment("公司简称")
	private String shortname;
	
	@Column
    @Comment("指定番号")
	private String designatedNum;
	
	@Column
    @Comment("联系人")
	private String linkman;
	
	@Column
    @Comment("电话")
	private String mobile;
	
	@Column
    @Comment("邮箱")
	private String email;
	
	@Column
    @Comment("地址")
	private String address;
	
	@Column
    @Comment("操作人")
	private Integer opId;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	
	@Column
    @Comment("修改时间")
	private Date updateTime;
	

}