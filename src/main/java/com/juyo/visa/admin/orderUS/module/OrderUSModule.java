/**
 * OrderUSModule.java
 * com.juyo.visa.admin.orderUS.module
 * Copyright (c) 2018, 北京科技有限公司版权所有.
 */

package com.juyo.visa.admin.orderUS.module;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.orderUS.form.OrderUSListDataForm;
import com.juyo.visa.admin.orderUS.service.OrderUSViewService;
import com.juyo.visa.forms.OrderUpdateForm;

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
	 * 跳转到美国订单list页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object listUS(HttpServletRequest request) {
		return orderUSViewService.toList(request);
	}

	/**
	 * 加载列表页数据
	 */
	@At
	@POST
	public Object listDetailUSData(@Param("..") final OrderUSListDataForm sqlParamForm, HttpSession session) {
		return orderUSViewService.listData(sqlParamForm, session);
	}

	/**
	 * 跳转到美国订单详情页
	 */
	@At
	@GET
	@Ok("jsp")
	public Object orderUSDetail(@Param("orderid") int orderid, HttpServletRequest request) {
		return orderUSViewService.getOrderUSDetail(orderid, request);
	}

	/**
	 * 获取最新订单
	 */
	@At
	@POST
	public Object getOrderRefresh(@Param("orderid") int orderid, HttpServletRequest request) {
		return orderUSViewService.getOrderUSDetail(orderid, request);
	}

	/**
	 * 认领按钮功能
	 */
	@At
	@POST
	public Object toMyself(@Param("orderid") int orderid, HttpSession session) {
		return orderUSViewService.toMyself(orderid, session);
	}

	/**
	 * 跳转到跟进页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object addFollow(@Param("orderid") int orderid) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("orderid", orderid);
		return result;
	}

	/**
	 * 保存跟进内容
	 */
	@At
	@POST
	public Object saveFollow(@Param("orderid") int orderid, @Param("content") String content, HttpSession session) {
		return orderUSViewService.saveFollow(orderid, content, session);
	}

	/**
	 * 跟进解决按钮
	 */
	@At
	@POST
	public Object solveFollow(@Param("id") int id, HttpSession session) {
		return orderUSViewService.solveFollow(id, session);
	}

	/**
	 * 跳转到日志页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object log(@Param("orderid") int orderid, HttpSession session) {
		return orderUSViewService.toLog(orderid, session);
	}

	/**
	 * 获取日志页面数据
	 */
	@At
	@POST
	public Object getLogs(@Param("orderid") int orderid, HttpSession session) {
		return orderUSViewService.getLogs(orderid, session);
	}

	/**
	 * 日志页面点击保存时负责人变更
	 */
	@At
	@POST
	public Object changePrincipal(@Param("orderid") int orderid, @Param("principal") int principal, HttpSession session) {
		return orderUSViewService.changePrincipal(orderid, principal, session);
	}

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
	 * 通过
	 */
	@At
	@POST
	public Object passUS(@Param("orderid") int orderid, HttpSession session) {
		return orderUSViewService.passUS(orderid, session);
	}

	/**
	 * 拒绝
	 */
	@At
	@POST
	public Object refuseUS(@Param("orderid") int orderid, HttpSession session) {
		return orderUSViewService.refuseUS(orderid, session);
	}

	/**
	 * 订单保存
	 */
	@At
	@POST
	public Object orderSave(@Param("..") OrderUpdateForm form, HttpSession session) {
		return orderUSViewService.orderSave(form, session);
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
