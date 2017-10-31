/**
 * VisaJapanModule.java
 * com.juyo.visa.admin.visajp
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.visajp.form.VisaListDataForm;
import com.juyo.visa.admin.visajp.service.VisaJapanService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年10月30日 	 
 */
@IocBean
@At("/admin/visaJapan")
public class VisaJapanModule {

	@Inject
	private VisaJapanService visaJapanService;

	/**
	 * TODO 跳转到签证列表页
	 * <p>
	 * TODO 跳转到签证列表页
	 *
	 * @return TODO 跳转到签证列表页
	 */
	@At
	@GET
	@Ok("jsp")
	public Object visaList() {
		return null;
	}

	/**
	 *获取签证列表数据
	 * <p>
	 * TODO 日本签证列表数据
	 *
	 * @param form
	 * @param request
	 * @return TODO 日本签证列表数据
	 */
	@At
	@POST
	public Object visaListData(@Param("..") VisaListDataForm form, HttpSession session) {
		return visaJapanService.visaListData(form, session);
	}
}
