package com.juyo.visa.admin.bigcustomer.module;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	//配置与变量名一致
	@Inject
	private PcVisaViewService pcVisaViewService;

	@At
	@GET
	@Ok("re")
	public Object toReload(HttpSession session) {
		return pcVisaViewService.toReload(session);
	}

	/**
	 * 跳转到美国游客list页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object visaList() {
		return null;
	}

	/**
	 * 跳转到美国订单list页面
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
			@Param("orderid") String orderid) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("ordernum", ordernum);
		result.put("staffid", staffid);
		result.put("orderid", orderid);
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
	public Object visaDetail(@Param("orderid") Integer orderid, @Param("flag") Integer flag, HttpSession session) {
		return pcVisaViewService.visaDetail(orderid, flag, session);
	}

	/**
	 * 获取详情页最新内容
	 */
	@At
	@POST
	public Object getNewDetail(@Param("orderid") Integer orderid, @Param("flag") Integer flag, HttpSession session) {
		return pcVisaViewService.visaDetail(orderid, flag, session);
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
	@Ok("jsp")
	public Object updatePhoto(@Param("staffid") Integer staffid, @Param("flag") int flag,
			@Param("isDisable") Integer isDisable, HttpServletRequest request, HttpSession session) {
		return pcVisaViewService.updatePhoto(staffid, flag, isDisable, request, session);
	}

	/**
	 * 身份证正面上传、扫描
	 */
	@At
	@Ok("json")
	@Filters
	public Object IDCardRecognition(@Param("image") File file, @Param("staffid") Integer staffid,
			@Param("type") Integer type, HttpServletRequest request, HttpServletResponse response) {
		String imageUrl = pcVisaViewService.uploadImage(file, request, response);
		return null;
	}
}
