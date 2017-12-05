/**
 * AftermarketModule.java
 * com.juyo.visa.admin.aftermarket
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.aftermarket.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.aftermarket.form.AftermarketListForm;
import com.juyo.visa.admin.aftermarket.service.AftermarketService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年12月4日 	 
 */
@IocBean
@At("admin/aftermarket")
public class AftermarketModule {

	@Inject
	private AftermarketService aftermarketService;

	/**
	 * 跳转到列表页
	 */
	@At
	@Ok("jsp")
	public Object list() {
		return null;
	}

	/**
	 * 获取售后列表数据
	 */
	@At
	@POST
	public Object aftermarketListData(@Param("..") AftermarketListForm form, HttpSession session) {
		return aftermarketService.aftermarketListData(form, session);
	}
}
