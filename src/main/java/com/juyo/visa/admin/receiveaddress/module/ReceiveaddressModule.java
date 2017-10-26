package com.juyo.visa.admin.receiveaddress.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.receiveaddress.service.ReceiveaddressViewService;
import com.juyo.visa.forms.TReceiveaddressAddForm;
import com.juyo.visa.forms.TReceiveaddressForm;
import com.juyo.visa.forms.TReceiveaddressUpdateForm;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/receiveaddress")
@Filters({//@By(type = AuthFilter.class)
})
public class ReceiveaddressModule {

	private static final Log log = Logs.get();

	@Inject
	private ReceiveaddressViewService receiveaddressViewService;

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
	public Pagination list(@Param("..") final TReceiveaddressForm sqlParamForm,@Param("..") final Pager pager) {
		return receiveaddressViewService.listPage(sqlParamForm,pager);
	}*/
	@At
	public Object listData(@Param("..") final TReceiveaddressForm sqlParamForm) {
		return receiveaddressViewService.listData(sqlParamForm);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add() {
		return null;
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TReceiveaddressAddForm addForm) {
		return receiveaddressViewService.addReceiveaddress(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return receiveaddressViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TReceiveaddressUpdateForm updateForm) {
		return receiveaddressViewService.updateReceiveaddress(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		receiveaddressViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		receiveaddressViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

}