package com.juyo.visa.admin.company.module;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.juyo.visa.admin.company.service.CompanyViewService;
import com.juyo.visa.forms.TCompanyAddForm;
import com.juyo.visa.forms.TCompanyForm;
import com.juyo.visa.forms.TCompanyUpdateForm;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/company")
public class CompanyModule {

	private static final Log log = Logs.get();

	/**
	 * 注入容器中的dbDao对象，用于数据库查询、持久操作
	 */
	@Inject
	private IDbDao dbDao;

	@Inject
	private CompanyViewService companyViewService;

	/**
	 * 跳转到list页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object list() {
		return companyViewService.toListCompanyPage();
	}

	/**
	 * 分页查询
	 */
	/*@At
	@Ok("jsp")
	public Pagination list(@Param("..") final TCompanyForm sqlParamForm,@Param("..") final Pager pager) {
		return companyViewService.listPage(sqlParamForm,pager);
	}*/
	@At
	public Object listData(@Param("..") final TCompanyForm sqlParamForm) {
		return companyViewService.listData(sqlParamForm);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add() {
		return companyViewService.toAddCompanyPage();
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TCompanyAddForm addForm, final HttpSession session) {
		try {
			companyViewService.addCompany(addForm, session);
			return JsonResult.success("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error("添加失败");
		}
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return companyViewService.getCompanyPageInfo(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TCompanyUpdateForm updateForm, final HttpSession session) {
		try {
			companyViewService.updateCompany(updateForm, session);
			return JsonResult.success("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error("修改失败");
		}
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		companyViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		companyViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

	/**
	 * 上传文件
	 */
	@At
	@Ok("json")
	@AdaptBy(type = UploadAdaptor.class)
	public Object uploadFile(@Param("image") File file, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return companyViewService.uploadFile(file, request, response);
	}

	/**
	 * 校验用户名唯一性
	 *
	 * @param loginName
	 * @param adminId
	 * @return 
	 */
	@At
	public Object checkLoginNameExist(@Param("loginName") final String loginName, @Param("adminId") final String adminId) {
		return companyViewService.checkLoginNameExist(loginName, adminId);
	}

	/**
	 * 校验公司全称唯一性
	 *
	 * @param companyName
	 * @param adminId
	 * @return 
	 */
	@At
	public Object checkCompanyNameExist(@Param("companyName") final String companyName,
			@Param("adminId") final String adminId) {
		return companyViewService.checkCompanyNameExist(companyName, adminId);
	}

}