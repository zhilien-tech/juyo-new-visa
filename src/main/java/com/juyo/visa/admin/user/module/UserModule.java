package com.juyo.visa.admin.user.module;

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

import com.juyo.visa.admin.user.service.UserViewService;
import com.juyo.visa.forms.TUserAddForm;
import com.juyo.visa.forms.TUserForm;
import com.juyo.visa.forms.TUserUpdateForm;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/user")
public class UserModule {

	private static final Log log = Logs.get();

	@Inject
	private UserViewService userViewService;

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
	public Pagination list(@Param("..") final TUserForm sqlParamForm,@Param("..") final Pager pager) {
		return userViewService.listPage(sqlParamForm,pager);
	}*/
	@At
	public Object listData(@Param("..") final TUserForm sqlParamForm) {
		return userViewService.listData(sqlParamForm);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add(final HttpSession session) {
		return userViewService.toAddUserPage(session);
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TUserAddForm addForm) {
		return userViewService.addUser(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id, HttpSession session) {
		return userViewService.fetchUser(id, session);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TUserUpdateForm updateForm) {
		return userViewService.updateUser(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		userViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		userViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

	/**
	 * 加载职位
	 */
	@At
	public Object getJob(@Param("departmentId") Integer departmentId) {
		return userViewService.queryJobs(departmentId);
	}

}