package com.juyo.visa.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TApplicantVisaJpAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**申请人id*/
	private Integer applicantId;

	/**签发编号*/
	private String visaNum;

	/**签发地*/
	private String visaAddress;

	/**签证类型*/
	private Integer visaType;

	/**年限(年)*/
	private Integer visaYears;

	/**签发时间*/
	private Date visaDate;

	/**有效期至*/
	private Date validDate;

	/**停留时间(天)*/
	private Integer stayDays;

	/**操作人*/
	private Integer opId;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

	/**签证国家*/
	private String visaCountry;

	/**上传签证图片URL*/
	private String picUrl;

	/**签证录入时间（签证）*/
	private Date visaEntryTime;

	/**订单id	 */
	private Integer orderid;

}