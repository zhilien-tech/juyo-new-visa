/**
 * MobileModule.java
 * com.juyo.visa.admin.mobile
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.mobile.module;

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

import com.juyo.visa.admin.mobile.form.BasicinfoUSForm;
import com.juyo.visa.admin.mobile.form.MainApplicantForm;
import com.juyo.visa.admin.mobile.form.MarryImageForm;
import com.juyo.visa.admin.mobile.form.MobileApplicantForm;
import com.juyo.visa.admin.mobile.form.WealthInfoForm;
import com.juyo.visa.admin.mobile.form.WorkInfoForm;
import com.juyo.visa.admin.mobile.service.MobileUSService;
import com.juyo.visa.entities.TApplicantLowerEntity;
import com.juyo.visa.entities.TApplicantPassportLowerEntity;
import com.juyo.visa.forms.TApplicantBackmailJpForm;

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
	public Object getOpenidandSessionkey(String code, int staffid) {
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
	public Object updateImage(@Param("encode") String encode, @Param("file") File file, @Param("staffid") int staffid,
			@Param("type") int type, @Param("status") int status) {
		return mobileUSService.updateImage(encode, file, staffid, type, status);
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
	 * 申请人回显数据接口
	 */
	@At
	@POST
	public Object applicatinfo(@Param("..") MobileApplicantForm form) {
		return mobileUSService.applicatinfo(form);
	}

	/**
	 * 保存基本信息
	 */
	@At
	@POST
	public Object saveApplicatinfo(@Param("..") MobileApplicantForm form,
			@Param("..") TApplicantLowerEntity applicantEntity, HttpSession session) {
		return mobileUSService.saveApplicatinfo(form, applicantEntity, session);
	}

	/**
	 * 护照信息回显数据
	 */
	@At
	@POST
	public Object passportinfo(@Param("..") MobileApplicantForm form) {
		return mobileUSService.passportinfo(form);
	}

	/**
	 * 保存护照信息
	 */
	@At
	@POST
	public Object savePassportInfo(@Param("..") TApplicantPassportLowerEntity passportinfo) {
		return mobileUSService.savePassportInfo(passportinfo);
	}

	/**
	 * 签证信息页面数据
	 */
	@At
	@POST
	public Object getVisaInfoData(@Param("..") MobileApplicantForm form) {
		return mobileUSService.getVisaInfoData(form);
	}

	/**
	 * 上传结婚照、离婚照片。。。。
	 */
	@At
	@AdaptBy(type = UploadAdaptor.class)
	public Object uploadPic(@Param("image") File file, HttpServletRequest request, HttpServletResponse response) {
		return mobileUSService.uploadPic(file, request, response);
	}

	/**
	 * 保存上传的照片信息
	 */
	@At
	@POST
	public Object saveCardInfo(@Param("..") MarryImageForm form) {
		return mobileUSService.saveCardInfo(form);
	}

	/**
	 * 获取申请人信息数据
	 */
	@At
	@POST
	public Object getApplicantData(@Param("applicantid") Integer applicantid) {
		return mobileUSService.getApplicantData(applicantid);
	}

	/**
	 * 保存主申请人数据
	 */
	@At
	@POST
	public Object saveMainApplicant(@Param("..") MainApplicantForm form) {
		return mobileUSService.saveMainApplicant(form);
	}

	/**
	 * 获取申请人的职业或教育信息
	 */
	@At
	@POST
	public Object getJobOrEducationData(@Param("applicantid") Long applicantid) {
		return mobileUSService.getJobOrEducationData(applicantid);
	}

	/**
	 * 保存申请人的职业教育信息
	 */
	@At
	@POST
	public Object saveJobOrEducationData(@Param("..") WorkInfoForm form) {
		return mobileUSService.saveJobOrEducationData(form);
	}

	/**
	 * 获取财产信息页面数据
	 */
	@At
	@POST
	public Object getWealthData(@Param("applicantid") Integer applicantid) {
		return mobileUSService.getWealthData(applicantid);
	}

	/**
	 * 保存财产信息
	 */
	@At
	@POST
	public Object saveWealthData(@Param("..") WealthInfoForm form) {
		return mobileUSService.saveWealthData(form);
	}

	/**
	 * 保存签证信息
	 */
	@At
	@POST
	public Object saveVisaInfo(@Param("applicantid") Integer applicantid, @Param("marrystatus") Integer marrystatus) {
		return mobileUSService.saveVisaInfo(applicantid, marrystatus);
	}

	/**
	 * 获取回邮信息
	 */
	@At
	@POST
	public Object getBackMailInfo(@Param("applicantId") Integer applicantId) {
		return mobileUSService.getBackMailInfo(applicantId);
	}

	/**
	 * 保存回邮信息
	 */
	@At
	@POST
	public Object saveBackMailInfo(@Param("..") TApplicantBackmailJpForm form) {
		return mobileUSService.saveBackMailInfo(form);
	}

	/**
	 * 查询照片格式是否提示过
	 */
	@At
	@POST
	public Object ismobileprompted(@Param("applicantid") int applicantid) {
		return mobileUSService.ismobileprompted(applicantid);
	}

	/**
	 * 记录已经提示过
	 */
	@At
	@POST
	public Object mobilehasprompted(@Param("applicantid") int applicantid) {
		return mobileUSService.mobilehasprompted(applicantid);
	}
}
