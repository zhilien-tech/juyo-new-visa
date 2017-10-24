package com.juyo.visa.admin.authority.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.authority.form.TAuthoritySqlForm;
import com.juyo.visa.admin.authority.service.AuthorityViewService;
import com.juyo.visa.forms.TDepartmentAddForm;
import com.juyo.visa.forms.TDepartmentUpdateForm;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/authority")
@Filters({//@By(type = AuthFilter.class)
})
public class AuthorityModule {

	/**无效数据id*/
	public static final int INVALID_DATA_ID = -1;

	@Inject
	private AuthorityViewService authorityViewService;

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
	@At
	public Object listData(@Param("..") final TAuthoritySqlForm sqlParamForm) {
		return authorityViewService.listData(sqlParamForm);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add(final HttpSession session) throws CloneNotSupportedException {
		return authorityViewService.findCompanyFunctions(INVALID_DATA_ID, session);
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TDepartmentAddForm addForm) {
		return authorityViewService.add(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return authorityViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TDepartmentUpdateForm updateForm) {
		return authorityViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		authorityViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		authorityViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

}