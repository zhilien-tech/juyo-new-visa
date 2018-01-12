/**
 * AirLineList.java
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
public class AirLineInFo {

	//航班号
	private String name;
	//日期
	private String date;
	//航空公司	
	private String airmode;
	//机型
	private String dep;
	//起飞机场
	private String arr;
	//降落机场
	private String company;
	//航班状态
	private String status;
	//计划起飞时间
	private String dep_time;
	//计划到达时间
	private String arr_time;
	//预计/实际起飞时间
	private String fly_time;
	//预计/实际到达时间
	private String distance;
	//准点率
	private String punctuality;
	//飞行时间
	private String dep_temperature;
	//飞行距离
	private String arr_temperature;
	//起飞机场天气
	private String etd;
	//到达机场天气
	private String eta;

}
