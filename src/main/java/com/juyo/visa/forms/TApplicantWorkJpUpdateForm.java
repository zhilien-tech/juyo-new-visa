package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TApplicantWorkJpUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**申请人id*/
	private Integer applicantId;
		
	/**我的职业*/
	private String occupation;
		
	/**单位名称*/
	private String name;
		
	/**单位电话*/
	private String telephone;
		
	/**单位地址*/
	private String address;
		
	/**职业状况*/
	private Integer careerStatus;
		
	/**操作人*/
	private Integer opId;
		
	/**创建时间*/
	private Date createTime;
		
	/**更新时间*/
	private Date updateTime;
		
}