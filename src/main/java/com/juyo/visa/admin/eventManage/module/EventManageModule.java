package com.juyo.visa.admin.eventManage.module;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;

@IocBean
@At("/admin/eventManage")
public class EventManageModule {

	private static final Log log = Logs.get();

	/**
	 * 跳转到event页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object event() {
		return null;
	}

	

}