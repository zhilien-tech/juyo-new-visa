/**
 * MobileVisaModule.java
 * com.juyo.visa.admin.mobileVisa.module
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.mobileVisa.module;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	 * 获取身份证照片
	 */
	@At
	@POST
	public Object getIDcardphoto(@Param("staffid") Integer staffid, @Param("type") Integer type,
			@Param("status") Integer status) {
		return mobileVisaService.getIDcardphoto(staffid, type, status);
	}

	/*
	 * 获取单张照片信息
	 */
	@At
	@POST
	public Object getInfoByType(@Param("..") MobileVisaBasicInfoForm mobileVisaBasicInfoForm) {
		return mobileVisaService.getInfoByType(mobileVisaBasicInfoForm);
	}

	/*
	 * 获取用户基本信息
	 */
	@At
	@POST
	public Object getBasicInfoByStaffid(@Param("staffid") Integer staffid) {
		return mobileVisaService.getBasicInfoByStaffid(staffid);
	}

	/*
	 * 获取图片信息是否存在
	 */
	@At
	@POST
	public Object getImageInfoBytypeAndStaffid(@Param("staffid") Integer staffid, @Param("type") Integer type) {
		return mobileVisaService.getImageInfoBytypeAndStaffid(staffid, type);
	}

	/*
	 * 添加修改单张照片
	 */
	@At
	@AdaptBy(type = UploadAdaptor.class)
	public Object uploadImage(HttpSession session, @Param("image") File file, @Param("staffid") Integer staffid,
			@Param("type") Integer type, @Param("flag") int flag, @Param("status") Integer status,
			@Param("sequence") Integer sequence, HttpServletRequest request, HttpServletResponse response,
			@Param("sessionid") String sessionid) {
		return mobileVisaService.updateImage(file, staffid, type, flag, status, sequence, request, response, sessionid);
	}

	/*
	 * 添加修改一套多张照片
	 */
	@At
	@AdaptBy(type = UploadAdaptor.class)
	public Object uploadMuchImage(HttpSession session, @Param("image") File file, @Param("staffid") Integer staffid,
			@Param("type") Integer type, @Param("mainid") Integer mainid, @Param("sequence") Integer sequence,
			@Param("sessionid") String sessionid) {

		return mobileVisaService.updateMuchImage(file, staffid, mainid, type, sequence, sessionid);
	}

	/*
	 * 获取一个类型多张图片
	 */
	@At
	@POST
	public Object getMuchPhotoByStaffid(@Param("staffid") Integer staffid, @Param("type") Integer type) {
		return mobileVisaService.getMuchPhotoByStaffid(staffid, type);
	}

	/*
	 * 保存第二套房产说明信息
	 */
	@At
	@POST
	public Object saveSecondHousecard(@Param("type") int type, @Param("staffid") int staffid,
			@Param("propertyholder") String propertyholder, @Param("area") String area, @Param("address") String address) {
		return mobileVisaService.saveSecondHousecard(type, staffid, propertyholder, area, address);
	}

	/*
	 * 获取第二套房产说明
	 */
	@At
	@POST
	public Object getSecondHousecard(@Param("type") int type, @Param("staffid") int staffid) {
		return mobileVisaService.getSecondHousecard(type, staffid);
	}
	
	
	/*
	 * 获取微信多图上传 图片集合
	 */
	@At
	@POST
	public Object getWxMorePhotos(@Param("type") int type, @Param("staffid") int staffid) {
		return mobileVisaService.getWxMorePhotos(type, staffid);
	}

}
