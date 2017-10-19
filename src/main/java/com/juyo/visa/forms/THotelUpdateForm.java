package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class THotelUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**酒店名称(中文)*/
	private String name;
		
	/**酒店名称(原文)*/
	private String namejp;
		
	/**酒店地址(中文)*/
	private String address;
		
	/**酒店地址(原文)*/
	private String addressjp;
		
	/**电话*/
	private String mobile;
		
	/**所属城市id*/
	private Integer cityId;
		
	/**创建时间*/
	private Date createTime;
		
	/**更新时间*/
	private Date updateTime;
		
}