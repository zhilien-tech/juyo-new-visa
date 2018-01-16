/**
 * BackMailModule.java
 * com.juyo.visa.admin.firstTrialJp.module
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.backMail.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.backMail.service.BackMailViewService;
import com.juyo.visa.forms.TApplicantBackmailJpForm;

/**
 * 日本订单 回邮信息
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年12月4日 	 
 */

@IocBean
@At("/admin/backMailJp")
public class BackMailJpModule {

	@Inject
	private BackMailViewService backMailViewService;

	/**
	 * 跳转到回邮信息页
	 *
	 * @return 
	 */
	@At
	@GET
	@Ok("jsp")
	public Object backMailInfo(@Param("applicantId") Integer applicantId, @Param("orderId") Integer orderId,
			@Param("isAfterMarket") Integer isAfterMarket, @Param("orderProcessType") Integer orderProcessType) {
		return backMailViewService.backMailInfo(applicantId, orderId, isAfterMarket, orderProcessType);
	}

	/**
	 * 获取回邮信息
	 */
	@At
	@POST
	public Object getBackMailInfo(@Param("applicantId") Integer applicantId, HttpSession session) {
		return backMailViewService.getBackMailInfo(applicantId, session);
	}

	/**
	 * 保存回邮信息
	 */
	@At
	@POST
	public Object saveBackMailInfo(@Param("..") TApplicantBackmailJpForm form, HttpSession session) {
		return backMailViewService.saveBackMailInfo(form, session);
	}
}
