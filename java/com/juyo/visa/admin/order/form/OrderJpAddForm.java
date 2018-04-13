package com.juyo.visa.admin.order.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderJpAddForm extends OrderAddForm {
	private static final long serialVersionUID = 1L;

	/**订单id*/
	private Integer orderId;

	/**客户来源*/
	private Integer source;

	/**签证类型*/
	private Integer visaType;

	/**签证县*/
	private String visaCounty;

	/**过去三年是否访问*/
	private Integer isVisit;

	/**过去三年访问过的县*/
	private String threeCounty;

}