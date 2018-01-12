/**
 * AirLineParam.java
 * com.juyo.visa.common.haoservice
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.common.haoservice;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年1月11日 	 
 */
@Data
public class AirLineParam {

	//出发城市
	private String dep;

	//抵达城市
	private String arr;

	//出发日期(格式：20180101)
	private String date;
}
