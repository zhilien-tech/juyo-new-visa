package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_state_us")
public class TStateUsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("名称")
	private String name;
	
	@Column
	@Comment("名称英文")
	private String nameen;
	
	@Column
    @Comment("代码")
	private String code;
	
	@Column
	@Comment("首府")
	private String capital;
	
	@Column
	@Comment("首府英文")
	private String capitalen;
	
	@Column
	@Comment("备注")
	private String remark;
	

}