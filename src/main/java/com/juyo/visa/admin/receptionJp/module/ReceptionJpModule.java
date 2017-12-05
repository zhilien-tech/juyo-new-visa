/**
 * ReceptionJpModule.java
 * com.juyo.visa.admin.receptionJp.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.receptionJp.module;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.receptionJp.form.ReceptionJpForm;
import com.juyo.visa.admin.receptionJp.service.ReceptionJpViewService;
import com.juyo.visa.admin.visajp.form.PassportForm;
import com.juyo.visa.admin.visajp.form.VisaEditDataForm;
import com.juyo.visa.common.enums.IssueValidityEnum;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.EnumUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2017年11月24日 	 
 */
@IocBean
@At("/admin/receptionJP")
public class ReceptionJpModule {
	@Inject
	private ReceptionJpViewService receptionJpViewService;

	/**
	 * 跳转到list页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object listJapan() {
		return receptionJpViewService.toList();
	}

	/**
	 * 加载list页面
	 */
	@At
	@POST
	public Object listData(@Param("..") final ReceptionJpForm sqlParamForm, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		return receptionJpViewService.listData(sqlParamForm, session);
	}

	/**
	 * 跳转到详情页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object receptionDetail(@Param("orderid") Integer orderid) {
		return receptionJpViewService.receptionDetail(orderid);
	}

	/**
	 * 获取详情页数据
	 */
	@At
	@POST
	public Object getJpVisaDetailData(@Param("orderid") Integer orderid) {
		return receptionJpViewService.getJpVisaDetailData(orderid);
	}

	/**
	 * 保存详情数据
	 */
	@At
	@POST
	public Object saveJpVisaDetailInfo(@Param("..") VisaEditDataForm editDataForm,
			@Param("travelinfo") String travelinfo, HttpSession session) {
		return receptionJpViewService.saveJpVisaDetailInfo(editDataForm, travelinfo, session);
	}

	/**
	 * 跳转到实收页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object revenue(HttpSession session, @Param("orderid") Integer orderid) {
		return receptionJpViewService.revenue(session, orderid);
	}

	/**
	 * 加载实收页面数据
	 */
	@At
	@POST
	public Object receptionRevenue(HttpSession session, @Param("orderid") Integer orderid) {
		return receptionJpViewService.receptionRevenue(session, orderid);
	}

	/**
	 * 保存申请人真实资料数据
	 */
	@At
	@POST
	public Object saveApplicatRevenue(@Param("applicatid") Integer applicatid, @Param("realInfo") String realInfo,
			HttpSession session) {
		return receptionJpViewService.saveApplicatRevenue(applicatid, realInfo, session);
	}

	/**
	 * 保存真实资料备注
	 */
	@At
	@POST
	public Object saveRealInfoData(@Param("..") TOrderJpEntity orderjp, @Param("applicatinfo") String applicatinfo) {
		return receptionJpViewService.saveRealInfoData(orderjp, applicatinfo);
	}

	/**
	 * 跳转到护照信息页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object passportInfo(@Param("applyId") Integer applyId) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("applyId", applyId);
		result.put("issuevalidityenum", EnumUtil.enum2(IssueValidityEnum.class));
		return result;
	}

	/**
	 * 获取护照页面信息
	 */
	@At
	@POST
	public Object getPassportData(@Param("applyId") Integer applyId) {
		return receptionJpViewService.passportInfo(applyId);
	}

	/**
	 * 保存护照信息页面
	 */
	@At
	@POST
	public Object savePassportInfo(@Param("..") PassportForm form, HttpSession session) {
		return receptionJpViewService.savePassportInfo(form, session);
	}

	/**
	 * 保存护照本数信息
	 */
	@At
	@POST
	public Object editPassportCount(@Param("applicatid") Integer applicatid, @Param("inputVal") String inputVal) {
		return receptionJpViewService.editPassportCount(applicatid, inputVal);
	}

	/**
	 * 移交签证
	 */
	@At
	@POST
	public Object visaTransfer(HttpSession session, @Param("orderid") Integer orderid) {
		return receptionJpViewService.visaTransfer(session, orderid);
	}

}
