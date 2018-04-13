package com.juyo.visa.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TTouristVisaAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**用户id*/
	private Integer userId;

	/**我的职业*/
	private String occupation;

	private Integer applicantId;

	private String unitName;

	/**单位名称*/
	private String name;

	/**单位电话*/
	private String telephone;

	/**预准备资料*/
	private String prepareMaterials;

	/**单位地址*/
	private String address;

	/**财产明细*/
	private String details;

	/**财富信息是否同主*/
	private Integer sameMainWealth;

	/**工作信息是否同主*/
	private Integer sameMainWork;

	/**财产类型*/
	private String type;

	/**职业状况*/
	private Integer careerStatus;

	/**婚姻状况*/
	private Integer marryStatus;

	/**结婚证/离婚证地址*/
	private String marryUrl;

	/**主申请人id*/
	private Integer mainId;

	/**是否是主申请人*/
	private Integer isMainApplicant;

	/**与主申请人的关系*/
	private String mainRelation;

	/**与主申请人关系备注*/
	private String relationRemark;

	/**签证信息是否填写完毕*/
	private Integer visaIsCompleted;

	/**操作人*/
	private Integer opId;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

	private String financial;

	private String houseProperty;

	private String deposit;

	private String vehicle;
}