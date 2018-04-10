package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_app_staff_vcode")
public class TAppStaffVcodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffid;
	
	@Column
    @Comment("验证码图片")
	private String vcodeurl;
	
	@Column
    @Comment("验证码")
	private String vcode;
	

}