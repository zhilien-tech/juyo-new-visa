package com.juyo.visa.admin.authority.module;

import com.juyo.visa.admin.authority.service.AuthorityViewService;
import com.juyo.visa.forms.TDepartmentUpdateForm;
import com.juyo.visa.forms.TDepartmentAddForm;
import com.juyo.visa.forms.TDepartmentForm;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.dao.pager.Pager;
import org.nutz.mvc.annotation.*;

import com.uxuexi.core.web.base.page.Pagination;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/authority")
@Filters({//@By(type = AuthFilter.class)
	})
public class AuthorityModule {

	private static final Log log = Logs.get();
	
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
	/*@At
	@Ok("jsp")
	public Pagination list(@Param("..") final TDepartmentForm sqlParamForm,@Param("..") final Pager pager) {
    	return authorityViewService.listPage(sqlParamForm,pager);
    }*/
    @At
	public Object listData(@Param("..") final TDepartmentForm sqlParamForm) {
		return authorityViewService.listData(sqlParamForm);
	}
    
    /**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add() {
		return null ;
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..")TDepartmentAddForm addForm) {
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
	public Object update(@Param("..")TDepartmentUpdateForm updateForm) {
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