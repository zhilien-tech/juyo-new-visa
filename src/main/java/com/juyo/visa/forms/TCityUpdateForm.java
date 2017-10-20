package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCityUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**国家*/
	private String country;
		
	/**省/州/县*/
	private String province;
		
	/**城市*/
	private String city;
		
	/**创建时间*/
	private Date createTime;
		
	/**更新时间*/
	private Date updateTime;
		
}