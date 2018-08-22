/**
 * MobileModule.java
 * com.juyo.visa.admin.mobile
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.mobile.module;

import java.io.File;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.juyo.visa.admin.mobile.form.BasicinfoUSForm;
import com.juyo.visa.admin.mobile.form.FamilyinfoUSForm;
import com.juyo.visa.admin.mobile.form.PassportinfoUSForm;
import com.juyo.visa.admin.mobile.form.TravelinfoUSForm;
import com.juyo.visa.admin.mobile.form.WorkandeducateinfoUSForm;
import com.juyo.visa.admin.mobile.service.MobileUSService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2018年8月21日 	 
 */
@IocBean
@Filters
@At("/admin/mobileus")
public class MobileUSModule {

	@Inject
	private MobileUSService mobileUSService;

	/**
	 * 小程序获取登录态
	 */
	@At
	@POST
	public Object getOpenidandSessionkey(@Param("code") String code, @Param("staffid") int staffid) {
		return mobileUSService.getOpenidandSessionkey(code, staffid);
	}

	/**
	 * 小程序订单列表数据
	 */
	@At
	@POST
	public Object listData(@Param("encode") String encode, @Param("telephone") String telephone) {
		return mobileUSService.listData(encode, telephone);
	}

	/**
	 * 查询是否已上传图片
	 */
	@At
	@POST
	public Object ishavePhoto(@Param("encode") String encode, @Param("staffid") int staffid) {
		return mobileUSService.ishavePhoto(encode, staffid);
	}

	/**
	 * 图片上传
	 */
	@At
	@AdaptBy(type = UploadAdaptor.class)
	public Object updateImage(@Param("encode") String encode, @Param("image") File file, @Param("staffid") int staffid,
			@Param("type") int type) {
		return mobileUSService.updateImage(encode, file, staffid, type);
	}

	/**
	 * 基本信息回显数据接口
	 */
	@At
	@POST
	public Object basicinfo(@Param("encode") String encode, @Param("staffid") int staffid) {
		return mobileUSService.basicinfo(encode, staffid);
	}

	/**
	 * 基本信息保存
	 */
	@At
	@POST
	public Object saveBasicinfo(@Param("..") BasicinfoUSForm form) {
		return mobileUSService.saveBasicinfo(form);
	}

	/**
	 * 护照信息回显
	 */
	@At
	@POST
	public Object passportinfo(@Param("encode") String encode, @Param("staffid") int staffid) {
		return mobileUSService.passportinfo(encode, staffid);
	}

	/**
	 * 保存护照信息
	 */
	@At
	@POST
	public Object savePassportinfo(@Param("..") PassportinfoUSForm form) {
		return mobileUSService.savePassportInfo(form);
	}

	/**
	 * 家庭信息回显
	 */
	@At
	@POST
	public Object familyinfo(@Param("encode") String encode, @Param("staffid") int staffid) {
		return mobileUSService.familyinfo(encode, staffid);
	}

	/**
	 * 保存家庭信息
	 */
	@At
	@POST
	public Object saveFamilyinfo(@Param("..") FamilyinfoUSForm form) {
		return mobileUSService.saveFamilyinfo(form);
	}

	/**
	 * 职业与教育信息回显
	 */
	@At
	@POST
	public Object workandeducation(@Param("encode") String encode, @Param("staffid") int staffid) {
		return mobileUSService.workandeducation(encode, staffid);
	}

	/**
	 * 保存职业与教育信息
	 */
	@At
	@POST
	public Object saveWorkandeducation(@Param("..") WorkandeducateinfoUSForm form) {
		return mobileUSService.saveWorkandeducation(form);
	}

	/**
	 * 旅行信息回显
	 */
	@At
	@POST
	public Object travelinfo(@Param("encode") String encode, @Param("staffid") int staffid) {
		return mobileUSService.travelinfo(encode, staffid);
	}

	/**
	 * 保存旅行信息
	 */
	@At
	@POST
	public Object saveTravelinfo(@Param("..") TravelinfoUSForm form) {
		return mobileUSService.saveTravelinfo(form);
	}

}
