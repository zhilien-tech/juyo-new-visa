package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_app_staff_language")
public class TAppStaffLanguageEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffid;
	
	@Column
    @Comment("使用的语言名称")
	private String languagename;
	
	@Column
    @Comment("使用的语言名称(英文)")
	private String languagenameen;
	

}