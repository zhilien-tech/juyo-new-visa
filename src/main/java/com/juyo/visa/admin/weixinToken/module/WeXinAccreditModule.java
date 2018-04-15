/**
 * WeXinAccreditModule.java
 * com.juyo.visa.admin.weixinToken.module
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.weixinToken.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.weixinToken.service.WeXinAccreditService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2018年4月10日 	 
 */
@IocBean
@Filters
@At("admin/weixinOpenId")
public class WeXinAccreditModule {
	@Inject
	private WeXinAccreditService weXinAccreditService;

	//获取授权用户信息
	@At
	@POST
	@Filters
	public Object saveWxinfo(@Param("code") String code) {
		System.out.println(code);
		return weXinAccreditService.SaveUser(code);
	}

	//查询进度
	@At
	@POST
	@Filters
	public Object checkProgress(@Param("code") String code) {
		System.out.println(code);
		return weXinAccreditService.SaveUser(code);
	}

	//	//校验用户是否授权过
	//	public Object verifyUser(@Param("code") String code) {
	//
	//		return weXinAccreditService.VerifyUser();
	//	}

}
