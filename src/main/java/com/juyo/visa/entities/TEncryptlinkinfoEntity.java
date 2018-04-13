package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_encryptlinkinfo")
public class TEncryptlinkinfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("原链接")
	private String originallink;
	
	@Column
    @Comment("加密链接")
	private String encryptlink;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	
	@Column
    @Comment("操作人")
	private Integer opId;
	

}