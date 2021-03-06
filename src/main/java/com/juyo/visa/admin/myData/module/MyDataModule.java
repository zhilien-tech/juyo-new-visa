package com.juyo.visa.admin.myData.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.myData.service.MyDataService;
import com.juyo.visa.admin.order.form.VisaEditDataForm;
import com.juyo.visa.admin.order.service.OrderJpViewService;
import com.juyo.visa.forms.TApplicantForm;
import com.juyo.visa.forms.TApplicantPassportForm;
import com.juyo.visa.forms.TTouristBaseinfoForm;
import com.juyo.visa.forms.TTouristPassportForm;
import com.juyo.visa.forms.TTouristVisaForm;

/**
 * 我的资料Module
 * <p>
 *
 * @author   
 * @Date	 2017年12月08日 	 
 */
@IocBean
@At("/admin/myData")
@Filters
public class MyDataModule {

	@Inject
	private MyDataService myDataService;

	@Inject
	private OrderJpViewService saleViewService;

	/**
	 * 获取申请人基本信息
	 */
	@At
	@Ok("jsp")
	public Object basicInfo(HttpSession session, HttpServletRequest request) {
		return myDataService.getBasicInfo(session, request);
	}

	/**
	 * 获取申请人基本信息（常用联系人）
	 */
	@At
	@Ok("jsp:admin/myData/basicInfo")
	public Object basic(@Param("contact") int contact, @Param("applyId") int applyId, HttpSession session,
			HttpServletRequest request) {
		return myDataService.getBasicInfo(contact, applyId, session, request);
	}

	/**
	 * 基本信息修改后保存
	 */
	@At
	@POST
	public Object saveEditApplicant(@Param("..") TTouristBaseinfoForm applicantForm, HttpSession session) {
		return myDataService.saveEditApplicant(applicantForm, session);
	}

	/**
	 * 获取申请人护照信息
	 */
	@At
	@Ok("jsp")
	public Object passportInfo(HttpSession session, HttpServletRequest request) {
		return myDataService.getPassportInfo(session, request);
	}

	/**
	 * 获取申请人护照信息(常用联系人)
	 */
	@At
	@Ok("jsp:admin/myData/passportInfo")
	public Object passport(@Param("contact") int contact, @Param("applyId") int applyId, HttpSession session,
			HttpServletRequest request) {
		return myDataService.getPassportInfo(contact, applyId, session, request);
	}

	/**
	 * 修改护照信息后保存
	 */
	@At
	@POST
	public Object saveEditPassport(@Param("..") TTouristPassportForm passportForm, HttpSession session) {
		return myDataService.saveEditPassport(passportForm, session);
	}

	/**
	 * 已有签证
	 */
	@At
	@Ok("jsp")
	public Object visaInput(HttpSession session) {
		return myDataService.visaInput(session);
	}

	/**
	 * 签证信息国家国旗页
	 */
	@At
	@Ok("jsp")
	public Object visaCountry() {
		return null;
	}

	/**
	 * 签证信息
	 */
	@At
	@GET
	@Ok("jsp")
	public Object visaInfo(HttpSession session, HttpServletRequest request) {
		return myDataService.getVisaInfo(session, request);
	}

	/**
	 * 签证信息（常用联系人）
	 */
	@At
	@GET
	@Ok("jsp:admin/myData/visaInfo")
	public Object visa(@Param("contact") int contact, @Param("applyId") int applyId, HttpSession session,
			HttpServletRequest request) {
		return myDataService.getVisaInfo(contact, applyId, session, request);
	}

	/**
	 * 签证信息修改保存
	 */
	@At
	@POST
	public Object saveEditVisa(@Param("..") TTouristVisaForm visaForm, HttpSession session) {
		return myDataService.saveEditVisa(visaForm, session);
	}

	/**
	 * 常用联系人
	 */
	@At
	@GET
	@Ok("jsp")
	public Object topContacts(HttpSession session) {
		return null;
	}

	/**
	 * 常用联系人列表
	 */
	@At
	@POST
	public Object topContactsList(HttpSession session) {
		return myDataService.topContactsList(session);
	}

	/**
	 * 账户安全
	 */
	@At
	@GET
	@Ok("jsp")
	public Object safety(HttpSession session, HttpServletRequest request) {
		return null;
	}

	/**
	 * 根据身份证号获取申请人详细信息
	 */
	@At
	@POST
	public Object getAllInfoByCard(@Param("cardId") String cardId) {
		return saleViewService.getAllInfoByCard(cardId);
	}

	/**
	 * 游客资料填写完毕改变订单状态
	 */
	@At
	@POST
	public Object changeStatus(@Param("orderid") int orderid, @Param("applicantid") int applicantid,
			@Param("completeType") String completeType, HttpSession session) {
		return myDataService.changeStatus(orderid, applicantid, completeType, session);
	}

	/**
	 * 根据手机号查询游客表
	 */
	@At
	@POST
	public Object getTouristInfoByTelephone(@Param("telephone") String telephone,
			@Param("applicantId") int applicantId, HttpSession session) {
		return myDataService.getTouristInfoByTelephone(telephone, applicantId, session);
	}

	/**
	 * 根据身份证号查询游客表
	 */
	@At
	@POST
	public Object getTouristInfoByCard(@Param("cardId") String cardId, @Param("applicantId") int applicantId,
			HttpSession session) {
		return myDataService.getTouristInfoByCard(cardId, applicantId, session);
	}

	/**
	 * 根据护照号查询游客表
	 */
	@At
	@POST
	public Object getTouristInfoByPass(@Param("id") int applyId, @Param("passport") String pass, HttpSession session) {
		return myDataService.getTouristInfoByPass(applyId, pass, session);
	}

	/**
	 * 比较基本信息是否改变
	 */
	@At
	@POST
	public Object baseIsChanged(@Param("..") TApplicantForm applicantForm, HttpSession session) {
		return myDataService.baseIsChanged(applicantForm, session);
	}

	/**
	 * 比较护照信息是否改变
	 */
	@At
	@POST
	public Object passIsChanged(@Param("..") TApplicantPassportForm passportForm, HttpSession session) {
		return myDataService.passIsChanged(passportForm, session);
	}

	/**
	 * 比较签证信息是否改变
	 */
	@At
	@POST
	public Object visaIsChanged(@Param("..") VisaEditDataForm visaForm, HttpSession session) {
		return myDataService.visaIsChanged(visaForm, session);
	}

	/**
	 * 查询三个信息是否有改变
	 */
	@At
	@POST
	public Object infoIsChanged(@Param("applyid") int applyid, HttpSession session) {
		return myDataService.infoIsChanged(applyid, session);
	}

	/**
	 * 查询是否提示过
	 */
	@At
	@POST
	public Object isPrompted(@Param("applyId") int applyid, HttpSession session) {
		return myDataService.isPrompted(applyid, session);
	}

	/**
	 * 删除申请人
	 */
	@At
	@POST
	public Object deleteApplicant(@Param("userId") Integer userId) {
		return myDataService.deleteApplicant(userId);
	}
}
