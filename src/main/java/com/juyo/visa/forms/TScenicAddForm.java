package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TScenicAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**景点(中文)*/
	private String name;
		
	/**景点(原文)*/
	private String namejp;
		
	/**所属城市id*/
	private Integer cityId;
		
	/**创建时间*/
	private Date createTime;
		
	/**更新时间*/
	private Date updateTime;
		
}