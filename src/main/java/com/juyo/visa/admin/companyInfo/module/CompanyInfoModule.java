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

import com.juyo.visa.admin.companyInfo.form.TCompanyCustomerForm;
import com.juyo.visa.admin.companyInfo.service.CompanyInfoViewService;
import com.juyo.visa.forms.TCompanyAddForm;
import com.juyo.visa.forms.TCompanyForm;
import com.juyo.visa.forms.TCompanyOfCustomerForm;
import com.juyo.visa.forms.TCompanyUpdateForm;

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
	 * 
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
	public Object add(@Param("..") TCompanyAddForm addForm, final HttpSession session) {
		return companyInfoViewService.addCompany(addForm, session);
	}
	/**
	 * 添加送签社
	 */
	@At
	@POST
	public Object addSongQian(@Param("..") TCompanyForm addForm, final HttpSession session) {
		return companyInfoViewService.addSongQian(addForm, session);
	}
	/**
	 * 查询公司列表
	 */
	@At
	@POST
	public Object companyList(@Param("name") String name, final HttpSession session) {
		return companyInfoViewService.getCompanyList(name,session);
	}
	/***
	 * 根据ID查询公司
	 */
	@At
	@POST
	public Object getCompanyById(@Param("id") Integer id) {
		return companyInfoViewService.getCompanyById(id);
	}
	/**
	 * 跳转到edit页面
	 */
//	@At
//	@GET
//	@Ok("jsp")
//	public Object edit(@Param("id") Integer comId) {
//		return companyInfoViewService.getCompanyById(comId);
//	}
	@At
	@GET
	@Ok("jsp")
	public Object edit(@Param("id") Integer cocid) {
		return companyInfoViewService.getCompanyById(cocid);
	}
	/**
	 * 查询选中的公司信息
	 * @param id
	 * @return
	 */
	@At
	@POST
	public Object getSelectdCompany(@Param("id") Integer id) {
		return companyInfoViewService.getSelectdCompany(id);
	}
	/**删除查询选中的公司信息
	 * @param id
	 * @return
	 */
	@At
	@POST
	public Object deleteSelectedCompany(@Param("id")  Integer cocid) {
		return companyInfoViewService.deleteSelectedCompany(cocid);
	}
	
	/**
	 * 编辑
	 */
	@At
	@POST
	public Object update(@Param("sendid") Integer sendid,@Param("cocid")  Integer cocid) {
		return companyInfoViewService.updateCompany(sendid, cocid);
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