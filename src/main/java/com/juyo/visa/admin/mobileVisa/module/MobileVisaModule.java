/**
 * MobileVisaModule.java
 * com.juyo.visa.admin.mobileVisa.module
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.mobileVisa.module;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
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
	 * 获取图片信息是否存在
	 */
	@At
	@GET
	public Object getImageInfoBytypeAndStaffid(@Param("staffid") Integer staffid, @Param("type") Integer type) {
		return mobileVisaService.getImageInfoBytypeAndStaffid(staffid, type);
	}

	/*
	 * 添加修改单张照片
	 */
	@At
	@AdaptBy(type = UploadAdaptor.class)
	public Object uploadImage(HttpSession session, @Param("image") File file, @Param("staffid") Integer staffid,
			@Param("type") Integer type) {
		String uploadImage = mobileVisaService.uploadImage(file);
		return mobileVisaService.updateImage(uploadImage, staffid, type);
	}

}
