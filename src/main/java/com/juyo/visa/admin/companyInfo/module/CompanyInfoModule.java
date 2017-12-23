package com.juyo.visa.admin.companyInfo.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import com.juyo.visa.admin.companyInfo.service.CompanyInfoViewService;

@IocBean
@At("/admin/companyInfo")
public class CompanyInfoModule {

	private static final Log log = Logs.get();

	@Inject
	private CompanyInfoViewService companyInfoViewService;

	/**
	 * 跳转到list页面
	 */
	@At
	@Ok("jsp")
	public Object list(HttpSession session) {
		return companyInfoViewService.getAdminCompany(session);
	}

	/**
	 * 跳转到add页面
	 */
	@At
	@Ok("jsp")
	public Object add(HttpSession session) {
		return null;
	}
}