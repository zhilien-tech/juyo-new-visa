/**
 * SimpleCustomerModule.java
 * com.juyo.visa.admin.simple.module
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.simple.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.simple.service.SimpleCustomerService;
import com.juyo.visa.forms.TCustomerAddForm;
import com.juyo.visa.forms.TCustomerForm;
import com.juyo.visa.forms.TCustomerUpdateForm;
import com.uxuexi.core.web.chain.support.JsonResult;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年1月24日 	 
 */
@IocBean
@At("/admin/simple/customer")
public class SimpleCustomerModule {

	@Inject
	private SimpleCustomerService simpleCustomerService;

	/**
	 * 跳转到list页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object list() {
		return null;
	}

	/**
	 * 分页查询
	 */
	/*@At
	@Ok("jsp")
	public Pagination list(@Param("..") final TCustomerForm sqlParamForm,@Param("..") final Pager pager) {
		return simpleCustomerService.listPage(sqlParamForm,pager);
	}*/
	@At
	public Object listData(@Param("..") final TCustomerForm sqlParamForm, final HttpSession session) {
		return simpleCustomerService.listData(sqlParamForm, session);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add(@Param("isCustomerAdd") int isCustomerAdd) {
		return simpleCustomerService.toAddCustomerPage(isCustomerAdd);
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TCustomerAddForm addForm, final HttpSession session) {
		return simpleCustomerService.addCustomer(addForm, session);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return simpleCustomerService.fetchCustomer(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TCustomerUpdateForm updateForm, HttpSession session) {
		return simpleCustomerService.updateCustomer(updateForm, session);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		simpleCustomerService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		simpleCustomerService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

}
