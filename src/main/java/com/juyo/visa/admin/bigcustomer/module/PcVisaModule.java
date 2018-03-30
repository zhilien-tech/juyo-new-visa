package com.juyo.visa.admin.bigcustomer.module;

import java.util.Map;

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

import com.google.common.collect.Maps;
import com.juyo.visa.admin.bigcustomer.form.ListDetailUSDataForm;
import com.juyo.visa.admin.bigcustomer.form.VisaListDataForm;
import com.juyo.visa.admin.bigcustomer.service.PcVisaViewService;
import com.juyo.visa.forms.OrderUpdateForm;

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

	/**
	 * 跳转到list页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object listUS() {
		return null;
	}

	@At
	@GET
	@Ok("jsp")
	public Object listDetailUS(@Param("ordernum") String ordernum, @Param("staffid") int staffid,
			@Param("telephone") String telephone, @Param("email") String email) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("ordernum", ordernum);
		result.put("staffid", staffid);
		result.put("telephone", telephone);
		result.put("email", email);
		return result;
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
	 * 申请人 订单列表数据1
	 */
	@At
	@POST
	public Object listDetailUSData(@Param("..") ListDetailUSDataForm form, HttpSession session) {
		return pcVisaViewService.listDetailUSData(form, session);
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

	/**
	 * 修改基本信息
	 */
	@At
	@POST
	public Object visaSave(@Param("..") OrderUpdateForm form, final HttpSession session) {
		return pcVisaViewService.visaSave(form, session);

	}

	/*
	 * 跳转拍照资料
	 */

	@At
	@GET
	public Object updatePhoto(@Param("staffid") Integer staffid) {
		return pcVisaViewService.updatePhoto(staffid);
	}

}
