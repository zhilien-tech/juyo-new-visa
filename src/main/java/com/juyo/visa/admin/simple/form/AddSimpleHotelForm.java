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
public class AddSimpleHotelForm {

	/**酒店名称(中文)*/
	private String name;

	/**酒店名称(原文)*/
	private String namejp;

	/**酒店地址(中文)*/
	private String address;

	/**酒店地址(原文)*/
	private String addressjp;

	/**电话*/
	private String mobile;

	/**所属城市id*/
	private Integer cityId;

	/**所属城市*/
	private String cityName;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

	private Integer travelplanid;
}
