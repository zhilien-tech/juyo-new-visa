package com.juyo.visa.admin.joyu.module;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;

import com.juyo.visa.admin.joyu.service.JoyuViewService;

@IocBean
@At("/")
@Filters
public class JoyuModule {

	@Inject
	private JoyuViewService joyuViewService;

	@At
	public Object joyu(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return joyuViewService.joyu(request, response);
	}

}
