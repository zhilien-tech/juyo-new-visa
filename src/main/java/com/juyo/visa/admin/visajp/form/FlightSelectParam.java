/**
 * AirLineSelectParam.java
 * com.juyo.visa.admin.visajp.form
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp.form;

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
public class FlightSelectParam {

	private String flight;

	private Long gocity;

	private Long arrivecity;

	private String date;

	private Integer flag;
}
