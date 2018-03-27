/**
 * PcVisaModule.java
 * com.juyo.visa.admin.pcVisa.module
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.pcVisa.module;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2018年3月27日 	 
 */
@At("admin/pcvisa")
@IocBean
@Filters
public class PcVisaModule {

	@At
	@GET
	@Ok("jsp")
	public Object visaDetailUS() {
		return null;
	}
}
