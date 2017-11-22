package com.juyo.visa.admin.mail.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.juyo.visa.admin.mail.entities.MailContent;
import com.juyo.visa.admin.mail.entities.MailSender;
import com.juyo.visa.entities.TConfMailEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * 邮箱service
 */
@IocBean
public class MailService extends BaseService<TConfMailEntity> {

	@Inject
	private MailSender simpleMailSender;

	/**
	 * 发送邮件内容
	 *
	 * @param targets :接收者多接收者直接使用逗号分割
	 * @param content :需要发送的内容
	 * @param args    :[0]:subject副标题;[1]:类型Type.TEXT或Type.HTML
	 */
	public String send(String targets, String content, Object... args) {
		String result = "success";
		//Mail mail = mailDao.getAvailableOne();.where("
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

	//文件下载
	public Object download(String fileUrl, String fileName, HttpServletRequest request, HttpServletResponse response) {
		/*String userAgent = request.getHeader("User-Agent");
		//针对IE或者以IE为内核的浏览器：
		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
			try {
				fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			//非IE浏览器的处理：
			try {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}*/

		//将文件进行编码
		try {
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		//下载文件
		InputStream is = null;
		OutputStream out = null;
		if (!Util.isEmpty(fileUrl)) {
			try {
				URL url = new URL(fileUrl);
				URLConnection connection = url.openConnection();
				is = connection.getInputStream();
				out = response.getOutputStream();
				response.reset();
				response.setContentType("application/octet-stream");
				response.setCharacterEncoding("utf-8");
				response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", fileName));
				byte[] buffer = new byte[4096];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					out.write(buffer, 0, count);
				}
				out.flush();
				response.flushBuffer();
				out.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (!Util.isEmpty(is)) {
						is.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					if (!Util.isEmpty(out)) {
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();

				}
			}
		}
		return "DOWNLOAD SUCCESS";
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
