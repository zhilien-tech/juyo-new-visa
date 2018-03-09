package com.juyo.visa.admin.bigcustomer.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.bigcustomer.service.AppEventsViewService;
import com.juyo.visa.forms.TAppEventsForm;

@IocBean
@At("/admin/appEvents")
public class AppEventsModule {

	private static final Log log = Logs.get();

	@Inject
	private AppEventsViewService appEventsViewService;

	/**
	 * 跳转到list页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object list() {
		return null;
	}

	/**
	 * 分页查询
	 */
	@At
	public Object listData(@Param("..") final TAppEventsForm sqlParamForm, HttpSession session) {
		return appEventsViewService.listData(sqlParamForm, session);
	}

	/**
	 *跳转到活动详情页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object appEventDetails(@Param("eventId") Integer eventId, HttpSession session) {
		return appEventsViewService.appEventDetails(eventId, session);
	}

	/**
	 * 报名活动
	 */
	@At
	@POST
	public Object signUpEvents(@Param("eventId") Integer eventId, HttpSession session) {
		return appEventsViewService.signUpEvents(eventId, session);
	}

	/**
	 * 查询已报名活动的人员信息
	 */
	@At
	@POST
	public Object getStaffInfoByEventId(@Param("eventId") Integer eventId) {
		return appEventsViewService.getStaffInfoByEventId(eventId);
	}

	/**
	 * 获取签证流程
	 */
	@At
	@POST
	public Object getVisaProcessByCountry(@Param("visaCountry") Integer visaCountry) {
		return appEventsViewService.getVisaProcessByCountry(visaCountry);
	}

	/**
	 * 进度列表页
	 */
	@At
	@POST
	public Object getProcessListByStaffId(@Param("staffId") Integer staffId) {
		return appEventsViewService.getProcessListByStaffId(staffId);
	}

	/**
	 * 查看活动进度页
	 */
	@At
	@POST
	public Object getMyProcessDetails(@Param("visaCountry") Integer visaCountry, @Param("orderId") Integer orderId) {
		return appEventsViewService.getMyProcessDetails(visaCountry, orderId);
	}
}