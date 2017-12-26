package com.juyo.visa.admin.companyInfo.module;

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

import com.juyo.visa.admin.companyInfo.service.CompanyInfoViewService;
import com.juyo.visa.forms.TCompanyOfCustomerForm;

@IocBean
@At("/admin/companyInfo")
public class CompanyInfoModule {

	private static final Log log = Logs.get();

	@Inject
	private CompanyInfoViewService companyInfoViewService;

	/**
	 * 跳转到list页面
	 */
	@At
	@Ok("jsp")
	public Object list(HttpSession session) {
		return companyInfoViewService.getAdminCompany(session);
	}

	/**
	 *获取列表数据
	 * <p>
	 */
	@At
	public Object companyInfoListData(@Param("..") TCompanyOfCustomerForm form, HttpSession session) {
		return companyInfoViewService.getCompanyInfoList(form, session);
	}

	/**
	 * 跳转到add页面
	 */
	@At
	@Ok("jsp")
	public Object add(HttpSession session) {
		return null;
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TCompanyOfCustomerForm addForm, final HttpSession session) {
		return companyInfoViewService.add(addForm, session);
	}

	/**
	 * 跳转到edit页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object edit(@Param("id") Integer comInfoId) {
		return companyInfoViewService.getCompanyInfoById(comInfoId);
	}

	/**
	 * 编辑
	 */
	@At
	@POST
	public Object update(@Param("..") TCompanyOfCustomerForm updateForm, final HttpSession session) {
		return companyInfoViewService.update(updateForm, session);
	}

	/**
	 * 校验公司全称唯一性
	 *
	 * @param companyName
	 * @param adminId
	 * @return 
	 */
	@At
	public Object checkCompanyNameExist(@Param("companyName") final String companyName, @Param("id") final String id) {
		return companyInfoViewService.checkCompanyNameExist(companyName, id);
	}
}