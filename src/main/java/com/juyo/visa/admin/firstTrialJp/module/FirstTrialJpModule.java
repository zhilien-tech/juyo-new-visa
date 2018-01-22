/**
 * FirstTrialJpModule.java
 * com.juyo.visa.admin.firstTrialJp.module
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.firstTrialJp.module;

import javax.servlet.http.HttpServletRequest;
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
import com.juyo.visa.forms.TApplicantBackmailJpForm;
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
	 * 获取分享消息的申请人
	 */
	@At
	@POST
	public Object getShareApplicantByOrderid(@Param("orderjpid") Integer orderjpid) {
		return firstTrialJpViewService.getShareApplicantByOrderid(orderjpid);
	}

	/**
	 * 获取订单下所有申请人
	 */
	@At
	@POST
	public Object getAllApplicantByOrderid(@Param("orderjpid") Integer orderjpid) {
		return firstTrialJpViewService.getAllApplicantByOrderid(orderjpid);
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
	public Object saveUnqualified(@Param("..") TApplicantUnqualifiedForm form, HttpSession session,
			HttpServletRequest request) {
		return firstTrialJpViewService.saveUnqualified(form, session, request);
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
			@Param("expresstype") Integer expresstype, @Param("sharetype") Integer sharetype,
			@Param("receiver") String receiver, @Param("mobile") String mobile,
			@Param("expressaddress") String expressaddress, @Param("shareManIds") String shareManIds,
			@Param("opType") Integer opType, HttpSession session) {
		return firstTrialJpViewService.saveExpressInfo(orderid, orderjpid, expresstype, sharetype, receiver, mobile,
				expressaddress, shareManIds, opType, session);
	}

	/**
	 * 初审 快递发送短信邮件
	 */
	@At
	@POST
	public Object sendExpressMsg(@Param("orderid") Integer orderid, @Param("orderjpid") Integer orderjpid,
			@Param("sharetype") Integer sharetype, @Param("shareManIds") String shareManIds) {
		return firstTrialJpViewService.sendExpressMsg(orderid, orderjpid, sharetype, shareManIds);
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
	public Object backMailInfo(@Param("applicantId") Integer applicantId) {
		return firstTrialJpViewService.backMailInfo(applicantId);
	}

	/**
	 * 获取回邮信息
	 */
	@At
	@POST
	public Object getBackMailInfo(@Param("applicantId") Integer applicantId, HttpSession session) {
		return firstTrialJpViewService.getBackMailInfo(applicantId, session);
	}

	/**
	 * 保存回邮信息
	 */
	@At
	@POST
	public Object saveBackMailInfo(@Param("..") TApplicantBackmailJpForm form, HttpSession session) {
		return firstTrialJpViewService.saveBackMailInfo(form, session);
	}

	/**
	 * 获取申请人职业
	 */
	@At
	@POST
	public Object getCareerStatus(@Param("orderjpid") Integer orderjpid) {
		return firstTrialJpViewService.getCareerStatus(orderjpid);
	}

	/**
	 * 获取快递申请人 电话、邮箱是否有空
	 */
	@At
	@POST
	public Object checkExpressManInfo(@Param("shareManIds") String shareManIds) {
		return firstTrialJpViewService.checkExpressManInfo(shareManIds);
	}

	/**
	 * 判断申请人是否合格
	 */
	@At
	@POST
	public Object isQualifiedByApplicantId(@Param("applicantId") Integer applicantId) {
		return firstTrialJpViewService.isQualifiedByApplicantId(applicantId);
	}

	/**
	 * 不合格发送短信邮件
	 */
	@At
	@POST
	public String sendUnqualifiedMsg(@Param("applicantId") Integer applicantId, @Param("orderId") Integer orderId,
			HttpServletRequest request) {
		return firstTrialJpViewService.sendUnqualifiedMsg(applicantId, orderId, request);
	}

	/**
	 * 补充必填项---我的职业
	 */
	@At
	@GET
	@Ok("jsp")
	public Object validApplicantInfo(@Param("applicantId") Integer applyid, @Param("orderId") Integer orderid) {
		return firstTrialJpViewService.isValidInfo(applyid, orderid);
	}

	/**
	 * 补充必填项---我的电话或邮箱
	 */
	@At
	@GET
	@Ok("jsp")
	public Object validExpressManInfo(@Param("applicantId") Integer applyid, @Param("orderId") Integer orderid) {
		return firstTrialJpViewService.isValidInfo(applyid, orderid);
	}

	/**
	 * 判断订单收件信息是否填写
	 */
	@At
	@POST
	public Object getOrderRecipient(@Param("orderid") Integer orderid) {
		return firstTrialJpViewService.getOrderRecipient(orderid);
	}

	/**
	 * 地址通知，发送消息
	 */
	@At
	@POST
	public Object sendAddressMsg(@Param("orderid") Integer orderid, @Param("orderjpid") Integer orderjpid,
			@Param("shareType") Integer shareType, @Param("shareManIds") String shareManIds, HttpSession session) {
		return firstTrialJpViewService.sendAddressMsg(orderid, orderjpid, shareType, shareManIds, session);
	}

}
