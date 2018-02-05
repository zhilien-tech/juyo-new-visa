package com.juyo.visa.admin.bigcustomer.module;

import com.juyo.visa.admin.bigcustomer.service.BigcustomerViewService;
import com.juyo.visa.forms.TAppStaffBasicinfoUpdateForm;
import com.juyo.visa.forms.TAppStaffBasicinfoAddForm;
import com.juyo.visa.forms.TAppStaffBasicinfoForm;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.dao.pager.Pager;
import org.nutz.mvc.annotation.*;

import com.uxuexi.core.web.base.page.Pagination;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/bigcustomer")
@Filters({//@By(type = AuthFilter.class)
	})
public class BigcustomerModule {

	private static final Log log = Logs.get();
	
	@Inject
	private BigcustomerViewService bigcustomerViewService;
	
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
	public Pagination list(@Param("..") final TAppStaffBasicinfoForm sqlParamForm,@Param("..") final Pager pager) {
    	return bigcustomerViewService.listPage(sqlParamForm,pager);
    }*/
    @At
	public Object listData(@Param("..") final TAppStaffBasicinfoForm sqlParamForm) {
		return bigcustomerViewService.listData(sqlParamForm);
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
	public Object add(@Param("..")TAppStaffBasicinfoAddForm addForm) {
		return bigcustomerViewService.add(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return bigcustomerViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..")TAppStaffBasicinfoUpdateForm updateForm) {
		return bigcustomerViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		bigcustomerViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		bigcustomerViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}
	
}