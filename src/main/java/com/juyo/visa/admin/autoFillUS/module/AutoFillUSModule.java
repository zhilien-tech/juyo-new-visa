/**
 * AutoFillUSModule.java
 * com.juyo.visa.admin.autoFillUS.module
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.autoFillUS.module;

import java.io.File;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.juyo.visa.admin.autoFillUS.service.AutoFillUSViewService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   闫腾
 * @Date	 2018年3月26日 	 
 */
@At("admin/autoFillUS")
@IocBean
@Filters
public class AutoFillUSModule {
	@Inject
	private AutoFillUSViewService autoFillUSViewService;

	/**
	 * 查看是否有可执行的订单
	 */
	@At
	public Object fetchUSOrder() {
		return autoFillUSViewService.fetchUSOrder();
	}

	/**
	 * 上传验证码图片
	 */
	@At
	@POST
	@AdaptBy(type = UploadAdaptor.class)
	public Object uploadVcode(@Param("file") File file) {
		return autoFillUSViewService.vcodeUpload(file);
	}
}
