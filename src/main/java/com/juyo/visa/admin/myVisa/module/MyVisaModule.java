package com.juyo.visa.admin.myVisa.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;

import com.juyo.visa.admin.myVisa.service.MyVisaService;

/**
 * 我的签证Module
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年11月23日 	 
 */

@At("/admin/myvisa")
@Filters
public class MyVisaModule {

	@Inject
	private MyVisaService myVisaService;

	@At
	@GET
	@Ok("jsp")
	public Object flowChart() {
		return null;
	}

}
