package com.juyo.visa.admin.myData.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;

import com.juyo.visa.admin.myData.service.MyDataService;

/**
 * 我的资料Module
 * <p>
 *
 * @author   
 * @Date	 2017年12月08日 	 
 */
@IocBean
@At("/admin/myData")
@Filters
public class MyDataModule {

	@Inject
	private MyDataService myDataService;

	/**
	 * 获取申请人基本信息
	 */
	@At
	@Ok("jsp")
	public Object basicInfo(HttpSession session) {
		return myDataService.getBasicInfo(session);
	}

	/**
	 * 获取申请人护照信息
	 */
	@At
	@Ok("jsp")
	public Object passportInfo(HttpSession session) {
		return myDataService.getPassportInfo(session);
	}

	@At
	@Ok("jsp")
	public Object visaInput(HttpSession session) {
		return myDataService.visaInput(session);
	}

}
