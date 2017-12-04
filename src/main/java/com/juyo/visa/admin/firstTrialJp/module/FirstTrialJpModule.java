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

import com.juyo.visa.admin.firstTrialJp.from.FirstTrialJpEditDataForm;
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
		return firstTrialJpViewService.toList();
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
	public Object trialDetail(@Param("orderid") Integer orderid, @Param("orderjpid") Integer orderjpid) {
		return firstTrialJpViewService.trialDetail(orderid, orderjpid);
	}

	/**
	 * 获取签证详情页数据
	 */
	@At
	@POST
	public Object getJpTrialDetailData(@Param("orderid") Integer orderid, @Param("orderjpid") Integer orderjpid) {
		return firstTrialJpViewService.getJpTrialDetailData(orderid, orderjpid);
	}

	/**
	 * 快递 发信息
	 */
	@At
	@GET
	@Ok("jsp")
	public Object express(@Param("orderid") Integer orderid, @Param("orderjpid") Integer orderjpid, HttpSession session) {
		return firstTrialJpViewService.express(orderid, orderjpid, session);
	}

	/**
	 * 获取订单主申请人
	 */
	@At
	@POST
	public Object getmainApplicantByOrderid(@Param("orderjpid") int orderjpid) {
		return firstTrialJpViewService.getmainApplicantByOrderid(orderjpid);
	}

	/**
	 * 获取申请人信息
	 */
	@At
	@GET
	@Ok("jsp")
	public Object basicInfo(@Param("applyid") int applyid) {
		return firstTrialJpViewService.basicInfo(applyid);
	}

	/**
	 * 申请人 合格信息
	 */
	@At
	@POST
	public Object qualified(@Param("applyid") Integer applyid, @Param("orderid") Integer orderid,
			@Param("orderjpid") Integer orderjpid, HttpSession session) {
		return firstTrialJpViewService.qualified(applyid, orderid, orderjpid, session);
	}

	/**
	 * 申请人 不合格信息
	 */
	@At
	@GET
	@Ok("jsp")
	public Object unqualified(@Param("applyid") Integer applyid, @Param("orderid") Integer orderid) {
		return firstTrialJpViewService.unqualified(applyid, orderid);
	}

	/**
	 * 
	 * 保存不合格信息
	 */
	@At
	@POST
	public Object saveUnqualified(@Param("..") TApplicantUnqualifiedForm form, HttpSession session) {
		return firstTrialJpViewService.saveUnqualified(form, session);
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
	public Object saveExpressInfo(@Param("orderid") Integer orderid, @Param("orderjpid") Integer orderjpid,
			@Param("expresstype") Integer expresstype, @Param("expressaddress") String expressaddress,
			@Param("receiveAddressId") Integer receiveAddressId, HttpSession session) {
		return firstTrialJpViewService.saveExpressInfo(orderid, orderjpid, expresstype, expressaddress,
				receiveAddressId, session);
	}

	/**
	 * 保存初审详情数据
	 */
	@At
	@POST
	public Object saveJpTrialDetailInfo(@Param("..") FirstTrialJpEditDataForm editDataForm, HttpSession session) {
		return firstTrialJpViewService.saveJpTrialDetailInfo(editDataForm, session);
	}

	/**
	 * 订单申请人是否合格
	 */
	@At
	@POST
	public Boolean isQualified(@Param("orderjpid") Integer orderjpid) {
		return firstTrialJpViewService.isQualified(orderjpid);
	}

	/**
	 * 跳转到回邮信息页
	 *
	 * @return null
	 */
	@At
	@GET
	@Ok("jsp")
	public Object backMailInfo() {
		return null;
	}
}
