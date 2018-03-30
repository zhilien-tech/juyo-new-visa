/**
 * OrderUSModule.java
 * com.juyo.visa.admin.orderUS.module
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.orderUS.module;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

/**
 * 美国订单US
 *
 * @author   
 * @Date	 2018年3月30日 	 
 */
@IocBean
@At("/admin/orderUS")
public class OrderUSModule {

	/**
	 * 
	 * 分享发送消息
	 *
	 * @param staffId 人员id
	 * @param orderid 订单id
	 * @param telephone 手机号
	 * @return 
	 */
	@At
	@POST
	public Object sendShareMsg(@Param("staffId") Integer staffId, @Param("orderid") Integer orderid,
			HttpServletRequest request) {
		return sendShareMsg(staffId, orderid, request);
	}

}
