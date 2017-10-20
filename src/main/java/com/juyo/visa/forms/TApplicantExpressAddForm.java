package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TApplicantExpressAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**申请人id*/
	private Integer applicantId;
		
	/**快递号*/
	private String expressNum;
		
	/**快递方式*/
	private Integer expressType;
		
	/**资料来源*/
	private Integer source;
		
	/**快递地址*/
	private String expressAddress;
		
	/**联系人*/
	private String linkman;
		
	/**电话*/
	private String telephone;
		
	/**发票项目内容*/
	private String invoiceContent;
		
	/**发票抬头*/
	private String invoiceHead;
		
	/**税号*/
	private String taxNum;
		
	/**备注*/
	private String remark;
		
	/**操作人*/
	private Integer opId;
		
	/**创建时间*/
	private Date createTime;
		
	/**更新时间*/
	private Date updateTime;
		
}