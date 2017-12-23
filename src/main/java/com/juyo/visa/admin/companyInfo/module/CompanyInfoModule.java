package com.juyo.visa.admin.companyInfo.module;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

@IocBean
@At("/admin/companyInfo")
public class CompanyInfoModule {

	private static final Log log = Logs.get();

	/*@Inject
	private CompanyInfoViewService companyInfoViewService;*/

	/**
	 * 跳转到list页面
	 */
	@At
	@Ok("jsp")
	public Object list() {
		return null;
	}

}