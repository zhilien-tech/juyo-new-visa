package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_conf_wx")
public class TConfWxEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("微信公众号appid")
	private String appid;
	
	@Column
    @Comment("微信公众号appsecret")
	private String appsecret;
	
	@Column
    @Comment("微信公众号accesstoken")
	private String accesstokenkey;
	

}