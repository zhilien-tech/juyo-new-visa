/**
 * AirLineResult.java
 * com.juyo.visa.common.haoservice
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.common.haoservice;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.juyo.visa.entities.TFlightEntity;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年1月11日 	 
 */
@Data
public class AirLineResult {

	//错误代码
	private String error_code;

	//错误内容
	private String reason;

	//返回结果
	private List<TFlightEntity> ailiLineInFos = new ArrayList<TFlightEntity>();
}
