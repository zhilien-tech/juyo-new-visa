/**
 * OrderUpdateForm.java
 * com.juyo.visa.forms
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.forms;

import java.util.Date;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   马云鹏 
 */
@Data
public class OrderUpdateForm {
	/*
	 * 预计出发日期
	 */
	private Date godate;

	/*
	 * 抵达美国日期
	 */
	private Date arrivedate;

	/*
	 * 停留天数
	 */
	private Integer staydays;
	/*
	 * 离开美国日期
	 */
	private Date leavedate;

	/*
	 * 订单id
	 */
	private Integer orderid;
	/*
	 * 美国地址
	 */
	private String planaddress;

	/*
	 * 市
	 */
	private String plancity;

	/*
	 * 州
	 */
	private Integer planstate;
	/*
	 * 出行目的
	 */
	private String travelpurpose;
	/*
	 * 出发城市（去程）
	 */
	private Integer godeparturecity;
	/*
	 * 抵达城市（去程）
	 */
	private Integer goArrivedCity;
	/*
	 * 航班号（去程）
	 */
	private String goFlightNum;
	/*
	 * 出发城市（返程）
	 */
	private Integer returnDepartureCity;
	/*
	 * 抵达城市（返程）
	 */
	private Integer returnArrivedCity;
	/*
	 * 航班号（返程）
	 */
	private String returnFlightNum;

	//团名
	private String groupname;
	//领区
	private Integer cityid;
	//是否付款
	private Integer ispayed;

}
