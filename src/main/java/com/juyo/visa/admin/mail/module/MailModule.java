package com.juyo.visa.admin.mail.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.mail.service.MailService;

/**
 * 日本发送邮件 Module
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年11月21日 	 
 */

@IocBean
@At("/admin/mail")
@Filters
public class MailModule {

	@Inject
	private MailService mailService;

	/**
	 * 文件下载
	 */
	@At
	public Object download(@Param("url") final String url, @Param("fileName") final String fileName,
			HttpServletRequest request, HttpServletResponse response) {
		return mailService.download(url, fileName, request, response);
	}
}
