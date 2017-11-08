package com.juyo.visa.admin.scenic.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.scenic.service.ScenicViewService;
import com.juyo.visa.forms.TScenicAddForm;
import com.juyo.visa.forms.TScenicForm;
import com.juyo.visa.forms.TScenicUpdateForm;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/scenic")
public class ScenicModule {

	private static final Log log = Logs.get();

	@Inject
	private ScenicViewService scenicViewService;

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
	public Pagination list(@Param("..") final TScenicForm sqlParamForm,@Param("..") final Pager pager) {
		return scenicViewService.listPage(sqlParamForm,pager);
	}*/
	@At
	public Object listData(@Param("..") final TScenicForm sqlParamForm) {
		return scenicViewService.listData(sqlParamForm);
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
	public Object add(@Param("..") TScenicAddForm addForm) {
		return scenicViewService.addScenic(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return scenicViewService.fetchScenic(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TScenicUpdateForm updateForm) {
		return scenicViewService.updateScenic(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		scenicViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		scenicViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

	/**
	 * 景区下拉框
	 */
	@At
	@POST
	public Object getScenicSelect(@Param("scenicname") String scenicname, @Param("cityId") Integer cityId) {
		return scenicViewService.getScenicSelect(scenicname, cityId);
	}
}