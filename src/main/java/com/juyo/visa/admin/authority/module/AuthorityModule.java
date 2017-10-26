package com.juyo.visa.admin.authority.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.authority.form.DeptJobForm;
import com.juyo.visa.admin.authority.form.TAuthoritySqlForm;
import com.juyo.visa.admin.authority.service.AuthorityViewService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/authority")
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
	public Object listData(@Param("..") final TAuthoritySqlForm sqlParamForm, final HttpSession session) {
		return authorityViewService.listData(sqlParamForm, session);
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
	public Object add(@Param("..") DeptJobForm addForm, final HttpSession session) {
		return authorityViewService.saveDeptJobData(addForm, session);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long deptId, final HttpSession session) {
		return authorityViewService.loadJobJosn(deptId, session);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") DeptJobForm updateForm, @Param("deptId") final Long deptId,
			final HttpSession session) {
		try {
			return authorityViewService.updateJobFunctions(updateForm, deptId, session);
		} catch (Exception e) {
			return JsonResult.error(e.getMessage());
		}
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("jobId") final long jobId, final HttpSession session) {
		try {

			return authorityViewService.deleteJob(jobId, session);
		} catch (Exception e) {
			return JsonResult.success("删除失败!");
		}

	}

	/**
	 * 校验部门名称唯一性
	 *
	 * @param deptName
	 * @param deptId
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@At
	public Object checkDeptNameExist(@Param("deptName") final String deptName, @Param("deptId") final Long deptId,
			final HttpSession session) {
		try {
			return authorityViewService.checkDeptNameExist(deptName, deptId, session);
		} catch (Exception e) {
			// TODO: handle exception
			return JsonResult.success("校验成功");
		}
	}

	/**
	 * 校验职位名称唯一性
	 *
	 * @param jobName
	 * @param jobId
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@At
	public Object checkJobNameExist(@Param("jobName") final String jobName, @Param("jobId") final Long jobId) {
		try {
			return authorityViewService.checkJobNameExist(jobName, jobId);
		} catch (Exception e) {
			// TODO: handle exception
			return JsonResult.success("校验成功");
		}

	}

}