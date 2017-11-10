/**
 * PersonalInfoModule.java
 * com.juyo.visa.admin.personalInfo.module
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.personalInfo.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.personalInfo.form.PersonalInfoUpdateForm;
import com.juyo.visa.admin.personalInfo.service.PersonalInfoService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2017年11月10日 	 
 */
@IocBean
@At("/admin/personalInfo")
public class PersonalInfoModule {

	@Inject
	private PersonalInfoService personalInfoService;

	/**
	 * 个人信息列表页展示
	 * @param filter
	 */
	@At
	@Ok("jsp")
	private Object personalInfoList(final HttpSession session) {
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
	@Ok("jsp")
	public Object updatePersonal(@Param("..") final PersonalInfoUpdateForm updateForm) {
		return personalInfoService.updatePersonal(updateForm);
	}
}
