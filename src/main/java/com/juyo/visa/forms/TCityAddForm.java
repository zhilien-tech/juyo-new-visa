package com.juyo.visa.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCityAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**国家*/
	private String country;

	/**省/州/县*/
	private String province;

	private String relationcity;

	/**城市*/
	private String city;

	private String code;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

}