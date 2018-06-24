/**
 * ZhiflyEntity.java
 * com.juyo.visa.common.newairline
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.common.newairline;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年6月19日 	 
 */
@Data
public class ZhiflyEntity {

	/*航班号*/
	private String flightnum;
	/*起飞航班名字*/
	private String goflightname;
	/*起飞时间*/
	private String takeofftime;
	/*抵达航班名字*/
	private String arrflightname;
	/*抵达时间*/
	private String landingofftime;
}
