/**
 * MailContent.java
 * com.juyo.visa.admin.mail.entities
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.mail.entities;

import java.security.Security;
import java.util.Properties;

import lombok.Data;

/**
 * 发送邮件需要使用的基本信息
 *
 * @author   彭辉
 * @Date	 2017年11月20日 	 
 */
@Data
public class MailContent {

	// 发送邮件的服务器的IP和端口
	private String mailServerHost;
	private String mailServerPort = "465";
	// 邮件发送者的地址
	private String fromAddress;
	// 邮件接收者的地址
	private String toAddress;
	// 登陆邮件发送服务器的用户名和密码
	private String userName;
	private String password;
	// 是否需要身份验证
	private boolean validate = true;
	// 邮件主题
	private String subject;
	// 邮件的文本内容
	private String content;
	// 邮件附件的文件名
	private String[] attachFileNames;

	/**
	 * 获得邮件会话属性
	 */
	public Properties getProperties() {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", this.mailServerHost);
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", this.mailServerPort);
		props.setProperty("mail.smtp.socketFactory.port", this.mailServerPort);
		props.put("mail.smtp.auth", String.valueOf(validate));
		return props;
	}

	public boolean isValidate() {
		return validate;
	}
}
