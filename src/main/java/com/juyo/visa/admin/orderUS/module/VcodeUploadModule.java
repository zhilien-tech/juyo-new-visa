/**
 * VcodeUpload.java
 * com.juyo.visa.admin.orderUS.module
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.orderUS.module;

import java.io.File;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.juyo.visa.admin.orderUS.form.VcodeForm;
import com.juyo.visa.admin.orderUS.service.VcodeUploadService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2018年3月15日 	 
 */
@At("admin/orderUS")
@IocBean
@Filters
public class VcodeUploadModule {
	@Inject
	private VcodeUploadService vcodeUploadService;

	/**
	 * 上传验证码图片
	 */
	@At
	@POST
	@AdaptBy(type = UploadAdaptor.class)
	public Object uploadVcode(@Param("file") File file) {
		return vcodeUploadService.vcodeUpload(file);
	}

	@At
	@GET
	@Ok("jsp")
	public Object writeVcode() {
		return vcodeUploadService.writeVcode();
	}

	@At
	@POST
	public Object returnVcode(@Param("..") VcodeForm vcode) {
		return vcodeUploadService.returnVcode(vcode);
	}

	@At
	@POST
	public Object getVcode() {
		return vcodeUploadService.getVcode();
	}
}
