package com.juyo.visa.admin.baoying.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.baoying.form.TAppStaffMixInfoForm;
import com.juyo.visa.admin.baoying.service.BaoYingViewService;

@IocBean
@At("/admin/baoying")
public class BaoYingModule {

	private static final Log log = Logs.get();

	@Inject
	private BaoYingViewService baoYingViewService;

	/**
	 * 跳转到list页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object listUS(HttpServletRequest request) {
		return baoYingViewService.toList(request);
	}

	/**
	 * 分页查询
	 */
	@At
	public Object listData(@Param("..") final TAppStaffMixInfoForm sqlParamForm, HttpSession session) {
		return baoYingViewService.listData(sqlParamForm, session);
	}

}