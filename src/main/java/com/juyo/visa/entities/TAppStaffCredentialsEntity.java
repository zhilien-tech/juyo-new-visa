package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_app_staff_credentials")
public class TAppStaffCredentialsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffId;
	
	@Column
    @Comment("主证件id")
	private Integer mainId;
	
	@Column
    @Comment("证件url")
	private String url;
	
	@Column
    @Comment("证件类型")
	private Integer type;
	
	@Column
    @Comment("证件状态")
	private Integer status;
	
	@Column
    @Comment("证件序号")
	private Integer sequence;
	
	@Column
    @Comment("页面元素id")
	private String pageElementId;
	
	@Column
    @Comment("创建时间")
	private Date createtime;
	
	@Column
    @Comment("更新时间")
	private Date updatetime;
	

}