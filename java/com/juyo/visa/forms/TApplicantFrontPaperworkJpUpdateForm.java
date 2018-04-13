package com.juyo.visa.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TApplicantFrontPaperworkJpUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**申请人id*/
	private Integer applicantId;

	/**资料类型(同职业状况)*/
	private Integer type;

	/**真实资料(证件)*/
	private Integer content;

	/**资料本数*/
	private Integer count;

	/**操作人*/
	private Integer opId;

	private Date receiveDate;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

}