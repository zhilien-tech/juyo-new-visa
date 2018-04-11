package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_app_staff_wxinfo")
public class TAppStaffWxinfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffid;
	
	@Column
    @Comment("用户唯一标识")
	private String openid;
	
	@Column
    @Comment("用户昵称")
	private String nickname;
	
	@Column
    @Comment("性别")
	private Integer sex;
	
	@Column
    @Comment("省")
	private String province;
	
	@Column
    @Comment("市")
	private String city;
	
	@Column
    @Comment("国家")
	private String country;
	
	@Column
    @Comment("用户头像")
	private String headimgurl;
	
	@Column
    @Comment("用户特权信息")
	private String privilege;
	
	@Column
    @Comment("个人信息id")
	private String unionid;
	

}