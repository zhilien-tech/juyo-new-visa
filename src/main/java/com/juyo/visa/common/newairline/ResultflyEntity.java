/**
 * ZhuanflyEntity.java
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
public class ResultflyEntity {
	/*航班号*/
	private String flightnum;
	/*转机航班号*/
	private String zhuanflightnum;
	/*起飞航班名字*/
	private String goflightname;
	/*起飞时间*/
	private String takeofftime;
	/*转机抵达时间*/
	private String zhuanlandingofftime;
	/*转机起飞时间*/
	private String zhuantakeofftime;
	/*抵达航班名字*/
	private String arrflightname;
	/*转机航班名字*/
	private String zhuanflightname;
	/*抵达时间*/
	private String landingofftime;
}
