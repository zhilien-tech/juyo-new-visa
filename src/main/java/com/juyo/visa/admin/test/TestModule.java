/**
 * TestModule.java
 * com.juyo.visa.admin.test
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
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

	@At("admin/orderJp/addApplicant")
	@Ok("jsp")
	public Object addApplicant() {
		return null;
	}

	@At("admin/firstTrialJp/list")
	@Ok("jsp")
	public Object list() {
		return null;
	}

	@At("admin/firstTrialJp/edit")
	@Ok("jsp")
	public Object edit() {
		return null;
	}

	/*@At("admin/firstTrialJp/basicInfo")
	@Ok("jsp")
	public Object basicInfo() {
		return null;
	}*/

	@At("admin/firstTrialJp/express")
	@Ok("jsp")
	public Object express() {
		return null;
	}

	@At("admin/firstTrialJp/passport")
	@Ok("jsp")
	public Object passport() {
		return null;
	}

	@At("admin/firstTrialJp/visaInfo")
	@Ok("jsp")
	public Object visaInfo() {
		return null;
	}

	@At("admin/firstTrialJp/unqualified")
	@Ok("jsp")
	public Object unqualified() {
		return null;
	}

	@At("admin/orderJp/share")
	@Ok("jsp")
	public Object share() {
		return null;
	}

	@At("admin/orderJp/log")
	@Ok("jsp")
	public Object log() {
		return null;
	}

	/*@At("admin/myData/basicInfo")
	@Ok("jsp")
	public Object basicInfo() {
		return null;
	}

	@At("admin/myData/passportInfo")
	@Ok("jsp")
	public Object passportInfo() {
		return null;
	}*/

	/*@At("admin/myVisa/inProcessVisa")
	@Ok("jsp")
	public Object inProcessVisa() {
		return null;
	}*/

	/*@At("admin/JapanDijie/japanList")
	@Ok("jsp")
	public Object japanList() {
		return null;
	}*/

	/*@At("admin/receptionJP/listJapan")
	@Ok("jsp")
	public Object listJapan() {
		return null;
	}*/
}
