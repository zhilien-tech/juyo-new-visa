/**
 * VisaJapanModule.java
 * com.juyo.visa.admin.visajp
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp;

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
 * @Date	 2017年10月30日 	 
 */
@IocBean
@At("/admin/visaJapan")
public class VisaJapanModule {

	@At
	@GET
	@Ok("jsp")
	public Object visaList() {
		return null;
	}
}
