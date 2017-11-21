/**
 * TestMail.java
 * com.juyo.visa
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa;

import com.juyo.visa.admin.mail.entities.MailContent;
import com.juyo.visa.admin.mail.entities.MailSender;

/**
 * 测试发邮件
 */
public class TestMail {

	public static void main(String args[]) {
		String result = "failure";
		MailSender simpleMailSender = new MailSender();

		// 这个类主要是设置邮件
		MailContent mailInfo = new MailContent();
		mailInfo.setMailServerHost("smtp.exmail.qq.com");
		mailInfo.setMailServerPort("465");
		mailInfo.setValidate(true);
		mailInfo.setUserName("booking@fastour.com"); // 实际发送者
		mailInfo.setPassword("Fastour2017@");// 您的邮箱密码
		mailInfo.setFromAddress("booking@fastour.com"); // 设置发送人邮箱地址
		mailInfo.setToAddress("897670855@qq.com"); // 设置接受者邮箱地址
		mailInfo.setSubject("测试发邮件");//副标题

		mailInfo.setContent("小灰灰。。。");//邮件内容
		// 这个类主要来发送邮件
		boolean boo = simpleMailSender.sendTextMail(mailInfo);
		if (boo) {
			result = "success";
		}
		System.out.println(result);
	}
}
