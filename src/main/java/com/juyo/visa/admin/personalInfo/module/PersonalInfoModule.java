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
	private Object list(final HttpSession session) {
		return personalInfoService.toUpdatePersonal(session);
	}

	/**
	 * @param userId
	 * @param session
	 * 编辑个人信息页面回显数据
	 */
	@At
	@GET
	@Ok("jsp")
	public Object toUpdatePersonal(final HttpSession session) {
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
}
