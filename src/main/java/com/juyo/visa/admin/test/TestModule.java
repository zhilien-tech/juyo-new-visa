/**
 * TestModule.java
 * com.juyo.visa.admin.test
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.test;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   fanqiqi
 * @Date	 2017年10月31日 	 
 */
@IocBean
public class TestModule {

	@At("admin/visaJapan/visaInput")
	@Ok("jsp")
	public Object visaInput() {
		return null;
	}

	@At("admin/visaJapan/schedulingEdit")
	@Ok("jsp")
	public Object schedulingEdit() {
		return null;
	}

	@At("admin/orderJp/addApplicant")
	@Ok("jsp")
	public Object addApplicant() {
		return null;
	}

}
