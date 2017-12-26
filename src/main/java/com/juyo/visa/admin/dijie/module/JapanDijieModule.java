/**
 * JapanDijieModule.java
 * com.juyo.visa.admin.dijie.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.dijie.module;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.dijie.form.DijieOrderListForm;
import com.juyo.visa.admin.dijie.service.JapanDijieService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年12月23日 	 
 */
@At("/admin/JapanDijie")
@IocBean
public class JapanDijieModule {

	@Inject
	private JapanDijieService japanDijieService;

	/**
	 * 跳转到地接社订单列表页面
	 */
	@At
	@Ok("jsp")
	public Object japanList() {
		return null;
	}

	/**
	 * 获取地接社订单列表的数据
	 */
	@At
	@POST
	public Object listData(HttpServletRequest request, @Param("..") DijieOrderListForm form) {
		return japanDijieService.listData(request, form);
	}

	/**
	 * 跳转到地接社详情也
	 */
	@At
	@Ok("jsp")
	public Object orderdetail(@Param("orderid") Integer orderid, HttpServletRequest request) {
		return japanDijieService.orderdetail(orderid, request);
	}
}
