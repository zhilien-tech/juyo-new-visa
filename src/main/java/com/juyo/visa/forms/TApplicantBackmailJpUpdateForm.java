package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TApplicantBackmailJpUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**申请人id*/
	private Integer applicantId;
		
	/**资料来源*/
	private Integer source;
		
	/**回邮方式*/
	private Integer expressType;
		
	/**团队名称*/
	private String teamName;
		
	/**快递号*/
	private String expressNum;
		
	/**回邮地址*/
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
		
	/**回邮资料时间*/
	private Date backSourceTime;
		
	/**操作人*/
	private Integer opId;
		
	/**创建时间*/
	private Date createTime;
		
	/**更新时间*/
	private Date updateTime;
		
}