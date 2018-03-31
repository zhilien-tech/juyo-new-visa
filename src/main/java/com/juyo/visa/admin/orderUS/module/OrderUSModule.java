/**
 * OrderUSModule.java
 * com.juyo.visa.admin.orderUS.module
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.orderUS.module;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.juyo.visa.admin.orderUS.service.OrderUSViewService;

/**
 * 美国订单US
 *
 * @author   
 * @Date	 2018年3月30日 	 
 */
@IocBean
@At("/admin/orderUS")
public class OrderUSModule {

	@Inject
	private OrderUSViewService orderUSViewService;

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
		return orderUSViewService.sendShareMsg(staffId, orderid, request);
	}

	/**
	 * 身份证正面上传、扫描
	 */
	@At
	@Ok("json")
	@Filters
	@AdaptBy(type = UploadAdaptor.class)
	public Object IDCardRecognition(@Param("image") File file, HttpServletRequest request, HttpServletResponse response) {
		return orderUSViewService.IDCardRecognition(file, request, response);
	}

	/**
	 * 身份证背面上传、扫描
	 */
	@At
	@Ok("json")
	@Filters
	@AdaptBy(type = UploadAdaptor.class)
	public Object IDCardRecognitionBack(@Param("image") File file, HttpServletRequest request,
			HttpServletResponse response) {
		return orderUSViewService.IDCardRecognitionBack(file, request, response);
	}

	/**
	 * 护照上传、扫描
	 */
	@At
	@Ok("json")
	@Filters
	@AdaptBy(type = UploadAdaptor.class)
	public Object passportRecognition(@Param("image") File file, HttpServletRequest request,
			HttpServletResponse response) {
		return orderUSViewService.passportRecognitionBack(file, request, response);
	}

}
