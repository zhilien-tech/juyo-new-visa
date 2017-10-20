package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_com_businessscope")
public class TComBusinessscopeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("经营范围")
	private Integer countryId;
	
	@Column
    @Comment("公司id")
	private Integer comId;
	

}