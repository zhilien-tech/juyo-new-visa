/**
 * ReceptionJpModule.java
 * com.juyo.visa.admin.receptionJp.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.receptionJp.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.receptionJp.form.ReceptionJpForm;
import com.juyo.visa.admin.receptionJp.service.ReceptionJpViewService;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TUserEntity;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2017年11月24日 	 
 */
@IocBean
@At("/admin/ReceptionJp")
public class ReceptionJpModule {
	@Inject
	private ReceptionJpViewService receptionJpViewService;

	/**
	 * 跳转到list页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object listJapan() {
		return null;
	}

	/**
	 * 加载list页面
	 */
	@At
	@POST
	public Object listData(@Param("..") final ReceptionJpForm sqlParamForm, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		return receptionJpViewService.listData(sqlParamForm, session);
	}
}
