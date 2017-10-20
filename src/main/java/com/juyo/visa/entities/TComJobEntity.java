package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_com_job")
public class TComJobEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("职位id")
	private Integer jobId;
	
	@Column
    @Comment("公司id")
	private Integer comId;
	

}