//package com.juyo.visa.admin.progress.module;
//
//import javax.servlet.http.HttpSession;
//
//import org.nutz.ioc.loader.annotation.Inject;
//import org.nutz.ioc.loader.annotation.IocBean;
//import org.nutz.mvc.annotation.At;
//import org.nutz.mvc.annotation.GET;
//import org.nutz.mvc.annotation.Ok;
//import org.nutz.mvc.annotation.POST;
//import org.nutz.mvc.annotation.Param;
//
//import com.juyo.visa.admin.personalInfo.form.PasswordForm;
//import com.juyo.visa.admin.personalInfo.form.PersonalInfoUpdateForm;
//import com.juyo.visa.admin.progress.service.ProgressInfoService;
//import com.uxuexi.core.web.chain.support.JsonResult;
//
//@IocBean
//@At("/admin/progress")
//public class ProgressInfoModule {
//
//	@Inject
//	private ProgressInfoService progressInfoService;
//
//	/**
//	 * 跳转到list页面
//	 */
//	@At
//	@GET
//	@Ok("jsp")
//	//	public Object StatusInfo(final HttpSession session) {
//	//		return progressInfoService.toUpdatePersonal(session);
//	//	}
//	/**
//	 * @param session
//	 * 编辑个人信息页面回显数据
//	 */
//
//}
