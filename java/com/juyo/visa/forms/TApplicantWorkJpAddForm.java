package com.juyo.visa.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TApplicantWorkJpAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**申请人id*/
	private Integer applicantId;

	/**我的职业*/
	private String occupation;

	private String unitName;

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