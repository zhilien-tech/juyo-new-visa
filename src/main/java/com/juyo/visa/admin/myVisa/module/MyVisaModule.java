package com.juyo.visa.admin.myVisa.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.myVisa.form.MyVisaListDataForm;
import com.juyo.visa.admin.myVisa.service.MyVisaService;

/**
 * 我的签证Module
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年11月23日 	 
 */
@IocBean
@At("/admin/myVisa")
@Filters
public class MyVisaModule {

	@Inject
	private MyVisaService myVisaService;

	/**
	 * 申请人 办理中的签证
	 *
	 * @param session
	 * @return 
	 */
	@At
	@Ok("jsp")
	public Object inProcessVisa() {
		return null;
	}

	/**
	 *获取我的签证列表数据
	 * <p>
	 * @param form
	 * @param request
	 * @return 
	 */
	@At
	@POST
	public Object myVisaListData(@Param("..") MyVisaListDataForm form, HttpSession session) {
		return myVisaService.myVisaListData(form, session);
	}

	@At
	@Ok("jsp")
	public Object flowChart() {
		return null;
	}

}
