/**
 * AddOrderForm.java
 * com.juyo.visa.admin.simple.form
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.simple.form;

import java.util.Date;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年1月26日 	 
 */
@Data
public class AddOrderForm {

	private Integer orderid;

	private String tripPurpose;

	private Integer triptype;

	private Date goDate;

	private Integer stayday;

	private Date returnDate;

	private Integer goDepartureCity;

	private Integer goArrivedCity;

	private String goFlightNum;

	private Integer returnDepartureCity;

	private Integer returnArrivedCity;

	private String returnFlightNum;

	private Integer customerType;

	private String compName;

	private String comShortName;

	private String compName2;

	private String comShortName2;

	private Integer payType;

	private Integer visatype;

	private String amount;

	private Integer cityid;

	private Integer urgentType;

	private Integer urgentDay;

	private String sendvisadate;

	private String outvisadate;

	private Integer customerid;

	private String sendvisanum;

	private Integer zhikecustomid;
}
