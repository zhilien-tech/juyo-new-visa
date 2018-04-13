package com.juyo.visa;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.ChainBy;
import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Localization;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import com.juyo.visa.common.actionfilter.AuthFilter;
import com.juyo.visa.common.actionfilter.LoginFilter;
import com.uxuexi.core.web.view.WeViewMaker;

@IocBy(type = ComboIocProvider.class, args = { "*org.nutz.ioc.loader.json.JsonLoader", "webconfig/",
		"*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "com.juyo.visa" })
@Encoding(input = "UTF-8", output = "UTF-8")
@Modules(scanPackage = true)
@Localization("msg")
@Ok("json")
@Fail("fail")
@SetupBy(WeSetup.class)
@ChainBy(args = { "${app.root}/WEB-INF/classes/webconfig/chains.js" })
@Views(WeViewMaker.class)
@Filters({ @By(type = LoginFilter.class), @By(type = AuthFilter.class) })
@IocBean
public class MainModule {

	@At("/")
	@Ok("jsp:admin.login")
	public Object main() {
		return null;
	}

	@At("/tlogin")
	@Ok("jsp:admin.tlogin")
	@Filters
	public Object tlogin() {
		return null;
	}

	@At("/public/menu")
	@Ok("jsp:public.menu")
	public Object menu() {
		return null;
	}
	@At("/public/header")
	@Ok("jsp:public.header")
	public Object header() {
		return null;
	}
	@At("/public/aside")
	@Ok("jsp:public.aside")
	public Object aside() {
		return null;
	}

}
