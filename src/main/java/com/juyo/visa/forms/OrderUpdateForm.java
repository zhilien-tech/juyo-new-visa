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
	 * 人员id
	 */
	private Integer staffid;
	/*
	 * 性别
	 */
	private String sex;
	/*
	 * 出生日期
	 */
	private Date birthday;
	/*
	 * AA码
	 */
	private String aacode;

	/*
	 * 护照号
	 */
	private String passport;

	/*
	 * 面试时间
	 */
	private Date Interviewdate;

}
