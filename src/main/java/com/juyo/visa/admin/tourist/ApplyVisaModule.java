/**
 * ApplyVisaModule.java
 * com.juyo.visa.admin.tourist
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.tourist;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年10月25日 	 
 */
@IocBean
@At("/admin/applyvisa")
public class ApplyVisaModule {

	@At
	@GET
	@Ok("jsp")
	public Object list() {
		return null;
	}
}
