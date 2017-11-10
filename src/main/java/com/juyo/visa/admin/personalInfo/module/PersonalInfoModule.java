package com.juyo.visa.admin.personalInfo.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.personalInfo.form.PersonalInfoUpdateForm;
import com.juyo.visa.admin.personalInfo.service.PersonalInfoService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/personalInfo")
public class PersonalInfoModule {

	@Inject
	private PersonalInfoService personalInfoService;

	/**
	 * 跳转到list页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object listInfo(final HttpSession session) {
		return personalInfoService.toUpdatePersonal(session);
	}

	/**
	 * @param session
	 * 编辑个人信息页面回显数据
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(final HttpSession session) {
		return personalInfoService.toUpdatePersonal(session);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object updatePersonal(@Param("..") final PersonalInfoUpdateForm updateForm) {
		return personalInfoService.updatePersonal(updateForm);
	}

	/**
	 * 修改密码页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object updatePassword() {
		return null;
	}

	/**
	 * @param PassForm
	 * @param session
	 * 执行密码修改操作
	 */
	@At
	@POST
	public Object updatePassword(@Param("..") final PersonalInfoUpdateForm PassForm, @Param("id") final Long userId) {
		try {
			//personalInfoService.updatePassData(PassForm, userId);
		} catch (Exception e) {
			return JsonResult.error(e.getMessage());
		}
		return JsonResult.success("密码修改成功!");
	}
}
