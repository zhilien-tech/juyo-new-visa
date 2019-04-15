/**
 * SendZhaobao.java
 * com.juyo.visa.admin.interfaceJapan.module
 * Copyright (c) 2019, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.interfaceJapan.module;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.interfaceJapan.form.ParamDataForm;
import com.juyo.visa.admin.interfaceJapan.service.JapanAutofillService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2019年4月2日 	 
 */
@IocBean
@Filters
@At("/visa/data/japan")
public class JapanAutofillModule implements AutofillInterface {

	@Inject
	private JapanAutofillService japanAutofillService;

	/**
	 * 发招宝接口，把传递过来的数据入库，之后发招宝
	 */
	@Override
	@At
	@Ok("raw:json")
	@POST
	@AdaptBy(type = JsonAdaptor.class)
	public Object toAutofill(@Param("token") String token, @Param("..") ParamDataForm form, HttpServletRequest request) {
		return japanAutofillService.autofill(token, form, request);
	}

	/**
	 * 查询
	 */
	@Override
	@At
	@GET
	@Ok("raw:json")
	public Object search(@Param("token") String token, @Param("timeStamp") String timeStamp,
			@Param("msg_signature") String msg_signature, @Param("nonce") String nonce,
			@Param("encrypt") String encrypt, HttpServletRequest request) {
		return japanAutofillService.search(token, timeStamp, msg_signature, nonce, encrypt, request);
	}
}
