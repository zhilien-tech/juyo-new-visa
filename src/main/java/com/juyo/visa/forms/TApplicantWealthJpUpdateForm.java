package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TApplicantWealthJpUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**申请人id*/
	private Integer applicantId;
		
	/**财产类型*/
	private Integer type;
		
	/**财产明细*/
	private String details;
		
	/**操作人*/
	private Integer opId;
		
	/**创建时间*/
	private Date createTime;
		
	/**更新时间*/
	private Date updateTime;
		
}