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

	//出行目的 
	private String tripPurpose;
	//行程类型
	private Integer triptype;
	//出发日期
	private Date goDate;
	//停留天数
	private Integer stayday;
	//
	private Date returnDate;
	//出发城市（出程）
	private Integer goDepartureCity;
	//抵达城市(去程)
	private Integer goArrivedCity;
	//航班号(去程)
	private String goFlightNum;
	//出发城市(返程)
	private Integer returnDepartureCity;
	//返回城市(返程)===
	private Integer returnArrivedCity;
	//航班号(返程)
	private String returnFlightNum;
	//客户管理类型 1, "线上" 2, "OTS" 3, "线下", 4, "直客"
	private Integer customerType;
	//公司名称
	private String compName;
	//公司简称
	private String comShortName;
	//公司别名
	private String compName2;

	private String comShortName2;
	//支付方式
	private Integer payType;
	//签证类型
	private Integer visatype;
	//金额
	private String amount;
	//城市ID
	private Integer cityid;
	//加急类型
	private Integer urgentType;
	//加急天数
	private Integer urgentDay;
	//送签时间
	private Date sendvisadate;
	//出签时间
	private Date outvisadate;
	//客户信息Id
	private Integer customerid;
	//送签编号
	private String sendvisanum;
	//直客用户Id
	private Integer zhikecustomid;
}
