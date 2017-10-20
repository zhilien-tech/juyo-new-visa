package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_businessscope_function")
public class TBusinessscopeFunctionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("")
	private Integer countryId;
	
	@Column
    @Comment("")
	private Integer compType;
	
	@Column
    @Comment("")
	private Integer funId;
	

}