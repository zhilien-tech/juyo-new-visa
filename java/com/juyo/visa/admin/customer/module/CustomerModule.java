package com.juyo.visa.admin.customer.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.customer.service.CustomerViewService;
import com.juyo.visa.forms.TCustomerAddForm;
import com.juyo.visa.forms.TCustomerForm;
import com.juyo.visa.forms.TCustomerUpdateForm;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/customer")
public class CustomerModule {

	private static final Log log = Logs.get();

	@Inject
	private CustomerViewService customerViewService;

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
		return customerViewService.listPage(sqlParamForm,pager);
	}*/
	@At
	public Object listData(@Param("..") final TCustomerForm sqlParamForm, final HttpSession session) {
		return customerViewService.listData(sqlParamForm, session);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add(@Param("isCustomerAdd") int isCustomerAdd) {
		return customerViewService.toAddCustomerPage(isCustomerAdd);
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TCustomerAddForm addForm, final HttpSession session) {
		return customerViewService.addCustomer(addForm, session);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return customerViewService.fetchCustomer(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TCustomerUpdateForm updateForm, HttpSession session) {
		return customerViewService.updateCustomer(updateForm, session);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		customerViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		customerViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

}