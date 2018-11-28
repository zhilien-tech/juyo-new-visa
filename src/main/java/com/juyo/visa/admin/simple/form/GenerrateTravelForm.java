/**
 * GenerrateTravelForm.java
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
public class GenerrateTravelForm {

	private Integer orderid;

	private Integer triptype;

	private Date goDate;

	private Date returnDate;

	private Integer goDepartureCity;

	private Integer goArrivedCity;

	private String goFlightNum;

	private Integer returnDepartureCity;

	private Integer returnArrivedCity;

	private String returnFlightNum;

	private Integer visatype;

	private Integer cityid;

	private Integer gotransferarrivedcity;

	private Integer gotransferdeparturecity;

	private Integer returntransferarrivedcity;

	private Integer returntransferdeparturecity;

	private String gotransferflightnum;

	private String returntransferflightnum;

	private Integer newgodeparturecity;

	private Integer newgoarrivedcity;

	private String newgoflightnum;

	private Integer newreturndeparturecity;

	private Integer newreturnarrivedcity;

	private String newreturnflightnum;
}
