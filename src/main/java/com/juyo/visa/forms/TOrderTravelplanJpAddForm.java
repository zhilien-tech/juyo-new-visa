package com.juyo.visa.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TOrderTravelplanJpAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**订单id*/
	private Integer orderId;

	/**天数*/
	private String day;

	/**日期*/
	private Date outDate;

	/**景区*/
	private String scenic;

	/**酒店*/
	private Integer hotel;

	private Integer isupdatecity;

	/**操作人id*/
	private Integer opId;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

}