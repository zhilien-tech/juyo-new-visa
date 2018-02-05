package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_app_staff_address")
public class TAppStaffAddressEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffId;
	
	@Column
    @Comment("联系人")
	private String linkman;
	
	@Column
    @Comment("手机")
	private String telephone;
	
	@Column
    @Comment("邮箱")
	private String email;
	
	@Column
    @Comment("省份")
	private String province;
	
	@Column
    @Comment("市区")
	private String city;
	
	@Column
    @Comment("详细地址")
	private String detailAddress;
	
	@Column
    @Comment("创建时间")
	private Date createtime;
	
	@Column
    @Comment("更新时间")
	private Date updatetime;
	

}