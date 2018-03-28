package com.juyo.visa.admin.bigcustomer.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.bigcustomer.form.VisaListDataForm;
import com.juyo.visa.admin.bigcustomer.service.PcVisaViewService;

@IocBean
@Filters
@At("/admin/pcVisa")
public class PcVisaModule {

	private static final Log log = Logs.get();

	@Inject
	private PcVisaViewService pcVisaViewService;

	/**
	 * 跳转到list页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object visaList() {
		return null;
	}
	
	@At
	@GET
	@Ok("jsp")
	public Object visaDetailUS() {
		return null;
	}

	/**
	 * 申请人 订单列表数据
	 */
	@At
	@POST
	public Object visaListData(@Param("..") VisaListDataForm form, HttpSession session) {
		return pcVisaViewService.visaListData(form, session);
	}

	/**
	 * 跳转到签证详情页
	 */
	@At
	@GET
	@Ok("jsp")
	public Object visaDetail(@Param("orderid") Integer orderid) {
		return pcVisaViewService.visaDetail(orderid);
	}
}
