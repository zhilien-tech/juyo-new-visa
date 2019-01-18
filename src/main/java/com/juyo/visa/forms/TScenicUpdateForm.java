package com.juyo.visa.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TScenicUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**景点(中文)*/
	private String name;

	/**景点(原文)*/
	private String namejp;

	/**所属城市id*/
	private Integer cityId;

	/**所属城市区域*/
	private String region;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

}