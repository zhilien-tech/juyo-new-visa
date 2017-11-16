/**
 * FirstTrialJpModule.java
 * com.juyo.visa.admin.firstTrialJp.module
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.firstTrialJp.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.firstTrialJp.from.FirstTrialJpListDataForm;
import com.juyo.visa.admin.firstTrialJp.service.FirstTrialJpViewService;
import com.juyo.visa.forms.TApplicantUnqualifiedForm;

/**
 * 日本订单初审Module
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年11月9日 	 
 */

@IocBean
@At("/admin/firstTrialJp")
public class FirstTrialJpModule {

	@Inject
	private FirstTrialJpViewService firstTrialJpViewService;

	/**
	 * 跳转到签证列表页
	 *
	 * @return null
	 */
	@At
	@GET
	@Ok("jsp")
	public Object list() {
		return null;
	}

	/**
	 *获取初审列表数据
	 * <p>
	 * @param form
	 * @param request
	 * @return 
	 */
	@At
	@POST
	public Object trialListData(@Param("..") FirstTrialJpListDataForm form, HttpSession session) {
		return firstTrialJpViewService.trialListData(form, session);
	}

	/**
	 * 跳转到签证详情页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object trialDetail(@Param("orderid") Integer orderid) {
		return firstTrialJpViewService.trialDetail(orderid);
	}

	/**
	 * 获取签证详情页数据
	 */
	@At
	@POST
	public Object getJpTrialDetailData(@Param("orderid") Integer orderid) {
		return firstTrialJpViewService.getJpTrialDetailData(orderid);
	}

	/**
	 * 快递 发信息
	 */
	@At
	@Ok("jsp")
	public Object express(@Param("id") int orderid, HttpSession session) {
		return firstTrialJpViewService.express(orderid, session);
	}

	/**
	 * 获取订单主申请人
	 */
	@At
	@POST
	public Object getmainApplicantByOrderid(@Param("id") int orderid) {
		return firstTrialJpViewService.getmainApplicantByOrderid(orderid);
	}

	/**
	 * 获取申请人信息
	 */
	@At
	@Ok("jsp")
	public Object basicInfo(@Param("applyid") int applyid) {
		return firstTrialJpViewService.basicInfo(applyid);
	}

	/**
	 * 申请人 合格信息
	 */
	@At
	@Ok("jsp")
	public Object qualified(@Param("applyid") int applyid) {
		return firstTrialJpViewService.qualified(applyid);
	}

	/**
	 * 申请人 不合格信息
	 */
	@At
	@Ok("jsp")
	public Object unqualified(@Param("applyid") int applyid) {
		return firstTrialJpViewService.unqualified(applyid);
	}

	/**
	 * 
	 * 保存不合格信息
	 */
	@At
	@POST
	public Object saveUnqualified(@Param("..") TApplicantUnqualifiedForm form) {
		return firstTrialJpViewService.saveUnqualified(form);
	}

	/**
	 * 根据电话，获取收件地址信息
	 */
	@At
	@POST
	public Object getRAddressSelect(@Param("searchStr") String searchStr, @Param("type") String type,
			HttpSession session) {
		return firstTrialJpViewService.getRAddressSelect(searchStr, type, session);
	}

	/**
	 * 根据id，获取收件地址信息
	 */
	@At
	@POST
	public Object getRAddressById(@Param("addressId") String addressId) {
		return firstTrialJpViewService.getRAddressById(addressId);
	}

	/**
	 * 保存快递信息，并发送邮件
	 */
	@At
	@POST
	public Object saveExpressInfo(@Param("orderid") Integer orderid, @Param("expresstype") Integer expresstype,
			@Param("receiveAddressId") Integer receiveAddressId, HttpSession session) {
		return firstTrialJpViewService.saveExpressInfo(orderid, expresstype, receiveAddressId, session);
	}

}
