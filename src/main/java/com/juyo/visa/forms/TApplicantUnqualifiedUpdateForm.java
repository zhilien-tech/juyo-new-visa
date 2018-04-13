package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TApplicantUnqualifiedUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**申请人id*/
	private Integer applicantId;
		
	/**基本信息是否合格*/
	private Integer isBase;
		
	/**基本备注*/
	private String baseRemark;
		
	/**护照信息是否合格*/
	private Integer isPassport;
		
	/**护照备注*/
	private String passRemark;
		
	/**签证信息是否合格*/
	private Integer isVisa;
		
	/**签证备注*/
	private String visaRemark;
		
	/**操作人*/
	private Integer opId;
		
	/**创建时间*/
	private Date createTime;
		
	/**更新时间*/
	private Date updateTime;
		
}