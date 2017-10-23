package com.juyo.visa.admin.login.module;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;

import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.util.ValidateImageUtil;

@At("/")
@IocBean
public class ValidateImageModule {

	/**
	 * 生成图片验证码
	 * @throws IOException 
	 */
	@At
	@Ok("raw:application/octet-stream")
	@Filters
	public void validateImage(final HttpServletResponse response, final HttpSession session) throws IOException {
		ValidateImageUtil viu = ValidateImageUtil.Instance();
		session.setAttribute(CommonConstants.CONFIRMCODE, viu.getString());

		InputStream ins = viu.getImage();
		byte[] buffer = new byte[ins.available()];
		ins.read(buffer);
		ins.close();

		OutputStream out = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("image/jpeg");
		out.write(buffer);
		out.flush();
		out.close();
	}

}