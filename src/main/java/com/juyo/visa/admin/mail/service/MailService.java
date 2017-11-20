package com.juyo.visa.admin.mail.service;

import org.nutz.ioc.loader.annotation.IocBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.juyo.visa.admin.mail.entities.MailContent;
import com.juyo.visa.admin.mail.entities.MailSender;
import com.juyo.visa.entities.TConfMailEntity;
import com.uxuexi.core.db.dao.IDbDao;

/**
 * 邮箱service
 */
@IocBean
public class MailService {

	@Autowired
	private MailSender simpleMailSender;
	@Autowired
	protected IDbDao dbDao;

	/**
	 * 发送邮件内容
	 *
	 * @param targets :接收者多接收者直接使用逗号分割
	 * @param content :需要发送的内容
	 * @param args    :[0]:subject副标题;[1]:类型Type.TEXT或Type.HTML
	 */
	public String send(String targets, String content, Object... args) {
		String result = "success";
		//Mail mail = mailDao.getAvailableOne();
		TConfMailEntity mail = dbDao.fetch(TConfMailEntity.class, 2);
		if (mail == null) {
			result = "请联系管理员配置发件邮箱服务器!";
		} else {
			// 这个类主要是设置邮件
			MailContent mailInfo = new MailContent();
			mailInfo.setMailServerHost(mail.getHOST());
			mailInfo.setMailServerPort(String.valueOf(mail.getPORT()));
			mailInfo.setValidate(true);
			mailInfo.setUserName(mail.getUSERNAME()); // 实际发送者
			mailInfo.setPassword(mail.getPASSWORD());// 您的邮箱密码
			mailInfo.setFromAddress(mail.getADDRESS()); // 设置发送人邮箱地址
			mailInfo.setToAddress(targets); // 设置接受者邮箱地址
			mailInfo.setSubject((String) args[0]);//副标题
			mailInfo.setContent(content);//邮件内容
			// 这个类主要来发送邮件
			if (Type.TEXT.equals(args[1])) { // 发送文体格式
				if (!simpleMailSender.sendTextMail(mailInfo)) {
					result = "发送失败,如一直存在问题请联系管理员!";
				}
			} else if (Type.HTML.equals(args[1])) {// 发送html格式
				if (!simpleMailSender.sendHtmlMail(mailInfo)) {
					result = "发送失败,如一直存在问题请联系管理员!";
				}
			}
		}
		return result;
	}

	public boolean init() {
		return true;
	}

	public String config() {
		return "";
	}

	public boolean clean() {
		return true;
	}

	public static enum Type {
		TEXT, HTML
	}

}
