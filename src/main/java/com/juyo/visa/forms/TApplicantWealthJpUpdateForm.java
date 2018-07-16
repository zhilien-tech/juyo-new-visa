package com.juyo.visa.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TApplicantWealthJpUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**申请人id*/
	private Integer applicantId;

	/**财产类型*/
	private String type;

	private String bankflowfree;

	private String vehiclefree;

	private String housePropertyfree;

	private String financialfree;

	private String certificatefree;

	private String depositfree;

	private String taxbillfree;

	private String taxprooffree;

	private String readstudentfree;

	private String graduatefree;

	private Integer sequence;

	/**财产明细*/
	private String details;

	/**操作人*/
	private Integer opId;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

}