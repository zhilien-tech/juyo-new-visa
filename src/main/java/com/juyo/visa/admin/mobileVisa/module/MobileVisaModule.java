/**
 * MobileVisaModule.java
 * com.juyo.visa.admin.mobileVisa.module
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.mobileVisa.module;

import java.io.File;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.juyo.visa.admin.mobileVisa.form.MobileVisaBasicInfoForm;
import com.juyo.visa.admin.mobileVisa.service.MobileVisaService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   马云鹏
 * @Date	 2018年3月29日 	 
 */
@IocBean
@Filters
@At("/admin/mobileVisa")
public class MobileVisaModule {

	@Inject
	private MobileVisaService mobileVisaService;

	/*
	 * 获取单张照片信息
	 */
	@At
	@POST
	public Object getInfoByType(@Param("..") MobileVisaBasicInfoForm mobileVisaBasicInfoForm) {
		return mobileVisaService.getInfoByType(mobileVisaBasicInfoForm);
	}

	/*
	 * 添加修改单张照片
	 */
	@At
	@AdaptBy(type = UploadAdaptor.class)
	public Object uploadImage(@Param("image") File file, @Param("type") Integer type, @Param("staffid") Integer staffid) {

		String uploadImage = mobileVisaService.uploadImage(file);
		return mobileVisaService.updateImage(uploadImage, type, staffid);

	}

}
