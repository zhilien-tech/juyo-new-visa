package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_function")
public class TFunctionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("上级功能id")
	private Integer parentId;
	
	@Column
    @Comment("功能名称")
	private String funName;
	
	@Column
    @Comment("访问地址")
	private String url;
	
	@Column
    @Comment("功能等级，是指在功能树中所处的层级")
	private Integer level;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	
	@Column
    @Comment("更新时间")
	private Date updateTime;
	
	@Column
    @Comment("备注")
	private String remark;
	
	@Column
    @Comment("序号")
	private Integer sort;
	
	@Column
    @Comment("菜单栏图标")
	private String portrait;
	

}