package com.juyo.visa.admin.joyu.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import com.juyo.visa.common.util.PublicIpUtil;
import com.juyo.visa.entities.TEncryptlinkinfoEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * 短地址跳转
 *
 * @author   彭辉
 * @Date	 2017年12月18日 	 
 */
@IocBean
public class JoyuViewService extends BaseService<TEncryptlinkinfoEntity> {

	public Object joyu(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String originallink = "http://" + request.getServerName() + ":" + request.getServerPort() + "/tlogin";

		//获取请求路径参数部分
		String encryptUrl = request.getQueryString();
		if (!Util.isEmpty(encryptUrl) && encryptUrl.length() >= 6) {
			encryptUrl = encryptUrl.substring(0, 6);
		}

		TEncryptlinkinfoEntity entity = dbDao.fetch(TEncryptlinkinfoEntity.class,
				Cnd.where("encryptlink", "=", encryptUrl));
		if (entity != null) {
			originallink = entity.getOriginallink();
		}

		response.sendRedirect(originallink);
		return "Redirect Success";
	}
}
