/**
 * SendZhaobao.java
 * com.juyo.visa.admin.interfaceJapan.module
 * Copyright (c) 2019, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.interfaceJapan.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;

import com.juyo.visa.admin.interfaceJapan.service.SendZhaobaoService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2019年4月2日 	 
 */
@IocBean
@At("/autofill/japan")
public class SendZhaobaoModule {

	@Inject
	private SendZhaobaoService sendZhaobaoService;

	@At
	public Object sendZhaobao() {
		return sendZhaobaoService.sendZhaobao();
	}
}
