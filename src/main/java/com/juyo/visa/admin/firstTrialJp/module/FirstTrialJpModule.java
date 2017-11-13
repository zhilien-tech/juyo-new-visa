/**
 * FirstTrialJpModule.java
 * com.juyo.visa.admin.firstTrialJp.module
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.firstTrialJp.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.firstTrialJp.from.FirstTrialJpListDataForm;
import com.juyo.visa.admin.firstTrialJp.service.FirstTrialJpViewService;

/**
 * 日本订单初审Module
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年11月9日 	 
 */

@IocBean
@At("/admin/firstTrialJp")
public class FirstTrialJpModule {

	@Inject
	private FirstTrialJpViewService firstTrialJpViewService;

	/**
	 * 跳转到签证列表页
	 *
	 * @return null
	 */
	@At
	@GET
	@Ok("jsp")
	public Object list() {
		return null;
	}

	/**
	 *获取初审列表数据
	 * <p>
	 * @param form
	 * @param request
	 * @return 
	 */
	@At
	@POST
	public Object trialListData(@Param("..") FirstTrialJpListDataForm form, HttpSession session) {
		return firstTrialJpViewService.trialListData(form, session);
	}

	/**
	 * 跳转到签证详情页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object trialDetail(@Param("orderid") Integer orderid) {
		return firstTrialJpViewService.trialDetail(orderid);
	}
}
