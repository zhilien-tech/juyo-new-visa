package com.juyo.visa.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TApplicantWealthJpAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**申请人id*/
	private Integer applicantId;

	/**财产类型*/
	private String type;

	/**财产明细*/
	private String details;

	private Integer sequence;

	/**操作人*/
	private Integer opId;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

}