package com.juyo.visa.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TApplicantOrderJpAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**日本订单id*/
	private Integer orderId;

	/**申请人id*/
	private Integer applicantId;

	/**是否为统一联系人*/
	private Integer isSameLinker;

	private Integer marryStatus;

	private String marryUrl;

	/**是否分享消息*/
	private Integer isShareSms;

	/**是否是主申请人*/
	private Integer isMainApplicant;

	/**与主申请人的关系*/
	private String mainRelation;

	/**与主申请人关系备注*/
	private String relationRemark;

	/**出行信息是否同主*/
	private Integer sameMainTrip;

	/**财富信息是否同主*/
	private Integer sameMainWealth;

	/**工作信息是否同主*/
	private Integer sameMainWork;

	/**视频地址*/
	private String videoUrl;

	private Integer baseIsCompleted;

	private Integer passIsCompleted;

	private Integer visaIsCompleted;

}