/**
 * SimpleVisaModule.java
 * com.juyo.visa.admin.simple.module
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.simple.module;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.order.form.VisaEditDataForm;
import com.juyo.visa.admin.simple.form.AddOrderForm;
import com.juyo.visa.admin.simple.form.AddSimpleHotelForm;
import com.juyo.visa.admin.simple.form.BasicinfoForm;
import com.juyo.visa.admin.simple.form.GenerrateTravelForm;
import com.juyo.visa.admin.simple.form.ListDataForm;
import com.juyo.visa.admin.simple.service.SimpleVisaService;
import com.juyo.visa.forms.TApplicantPassportForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年1月22日 	 
 */
@IocBean
@At("admin/simple")
public class SimpleVisaModule {

	@Inject
	private SimpleVisaService simpleVisaService;

	/**
	 * 跳转到列表
	 */
	@At
	@Ok("jsp")
	public Object list(HttpServletRequest request) {
		return simpleVisaService.toList(request);
	}

	/**
	 *获取列表数据 
	 */
	@At
	@POST
	public Object listData(@Param("..") ListDataForm form, HttpServletRequest request) {
		return simpleVisaService.ListData(form, request);
	}

	/**
	 * 添加订单
	 */
	@At
	@Ok("jsp")
	public Object addOrder(HttpServletRequest request) {
		return simpleVisaService.addOrder(request);
	}

	/**
	 * 生成行程安排
	 */
	@At
	@POST
	public Object generateTravelPlan(HttpServletRequest request, @Param("..") GenerrateTravelForm form) {
		return simpleVisaService.generateTravelPlan(request, form);
	}

	/**
	 * 获取景点
	 */
	@At
	@POST
	public Object getScenicSelect(@Param("scenicname") String scenicname, @Param("cityId") int cityId,
			@Param("planid") int planid, @Param("visatype") int visatype) {
		return simpleVisaService.getScenicSelect(scenicname, cityId, planid, visatype);
	}

	/**
	 * 获取酒店
	 */
	@At
	@POST
	public Object getHotelSelect(@Param("hotelname") String hotelname, @Param("cityId") int cityId) {
		return simpleVisaService.getHotelSelect(hotelname, cityId);
	}

	/**
	 * 添加酒店信息
	 */
	@At
	@Ok("jsp")
	public Object addHotel(@Param("planid") Integer planid, @Param("visatype") int visatype) {
		return simpleVisaService.addHotel(planid, visatype);
	}

	@At
	@POST
	public Object addsimplehotel(@Param("..") AddSimpleHotelForm form) {
		return simpleVisaService.addsimplehotel(form);
	}

	/**
	 * 跳转到添加申请人页面
	 * 
	 */
	@At
	@Ok("jsp")
	public Object addApplicant(@Param("orderid") Integer orderid, HttpServletRequest request) {
		return simpleVisaService.addApplicant(orderid, request);
	}

	/**
	 * 下单保存
	 */
	@At
	@POST
	public Object saveAddOrderinfo(@Param("..") AddOrderForm form, HttpServletRequest request) {
		return simpleVisaService.saveAddOrderinfo(form, request);
	}

	/**
	 * 跳转到编辑订单页面
	 */
	@At
	@Ok("jsp")
	public Object editOrder(HttpServletRequest request, @Param("orderid") Integer orderid) {
		return simpleVisaService.editOrder(request, orderid);
	}

	/**
	 * 获取客户公司信息
	 */
	@At
	@POST
	public Object getCustomerinfoById(@Param("customerid") Long customerid) {
		return simpleVisaService.getCustomerinfoById(customerid);
	}

	/**
	 * 作废按钮
	 */
	@At
	@POST
	public Object disabled(@Param("orderId") int orderid) {
		return simpleVisaService.disabled(orderid);
	}

	/**
	 * 还原按钮
	 */
	@At
	@POST
	public Object undisabled(@Param("orderId") int orderid) {
		return simpleVisaService.undisabled(orderid);
	}

	/**
	 * 获取客户金额
	 */
	@At
	@POST
	public Object getCustomerAmount(@Param("customerid") Integer customerid, @Param("visatype") Integer visatype) {
		return simpleVisaService.getCustomerAmount(customerid, visatype);
	}

	/**
	 * 保存申请人
	 */
	/*@At
	@POST
	public Object saveApplicantInfo(HttpServletRequest request, @Param("..") TApplicantForm form) {
		return simpleVisaService.saveApplicantInfo(request, form);
	}*/
	/**
	 * 保存申请人
	 */
	@At
	@POST
	public Object saveApplicantInfo(@Param("..") BasicinfoForm form, HttpServletRequest request) {
		return simpleVisaService.saveBasicinfo(form, request);
	}

	/**
	 * 跳转到护照信息页面
	 */
	@At
	@Ok("jsp")
	public Object passportInfo(@Param("applicantid") Integer applicantid, @Param("orderid") Integer orderid,
			HttpServletRequest request) {
		//return simpleVisaService.passportInfo(applicantid, orderid, request);
		return simpleVisaService.toNewfilminginfo(applicantid, orderid, request);
	}

	/**
	 * 跳转到新护照信息和基本信息页面页面
	 */
	@At
	@Ok("jsp")
	public Object basicInfo(@Param("applicantid") Integer applicantid, @Param("orderid") Integer orderid,
			HttpServletRequest request) {
		return simpleVisaService.basicInfo(applicantid, orderid, request);
	}

	/**
	 * 保存新护照信息和基本信息
	 */
	@At
	@POST
	public Object saveBasicinfo(@Param("..") BasicinfoForm form, HttpServletRequest request) {
		return simpleVisaService.saveBasicinfo(form, request);
	}

	/**
	 * 保存护照信息
	 */
	@At
	@POST
	public Object saveEditPassport(@Param("..") TApplicantPassportForm form, HttpServletRequest request) {
		return simpleVisaService.saveEditPassport(form, request);
	}

	/**
	 * 跳转到基本信息编辑页面
	 */
	@At
	@Ok("jsp")
	public Object updateApplicant(@Param("applicantid") Integer applicantid, @Param("orderid") Integer orderid,
			HttpServletRequest request) {
		//return simpleVisaService.updateApplicant(applicantid, orderid, request);
		return simpleVisaService.basicInfo(applicantid, orderid, request);
	}

	/**
	 * 跳转到签证信息页面
	 */
	@At
	@Ok("jsp")
	public Object visaInfo(@Param("applicantid") Integer applicantid, @Param("orderid") Integer orderid,
			HttpServletRequest request) {
		return simpleVisaService.visaInfo(applicantid, orderid, request);
	}

	/**
	 * 保存签证信息
	 */
	@At
	@POST
	public Object saveEditVisa(@Param("..") VisaEditDataForm form, HttpServletRequest request) {
		System.out.println("保存签证信息form:" + form);
		return simpleVisaService.saveEditVisa(form, request);
		/*try {
			Thread.sleep(18000);//括号里面的5000代表5000毫秒，也就是5秒，可以该成你需要的时间
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "111";*/
	}

	/**
	 * 取消订单
	 */
	@At
	@POST
	public Object cancelOrder(@Param("orderid") Long orderid) {
		return simpleVisaService.cancelOrder(orderid);
	}

	/**
	 * 添加护照
	 */
	@At
	@Ok("jsp")
	public Object addPassport(@Param("orderid") Integer orderid, HttpServletRequest request) {
		return simpleVisaService.addPassport(orderid, request);
	}

	/**
	 * 改变签证类型
	 */
	@At
	@POST
	public Object changeVisatype(@Param("orderid") int orderid, @Param("visatype") int visatype) {
		return simpleVisaService.changeVisatype(orderid, visatype);
	}

	/**
	 * 获取单位名称
	 */
	@At
	@POST
	public Object getUnitname(@Param("searchStr") String searchStr) {
		return simpleVisaService.getUnitname(searchStr);
	}

	/**
	 * 获取单位电话
	 */
	@At
	@POST
	public Object getUnittelephone(@Param("searchStr") String searchStr) {
		return simpleVisaService.getUnittelephone(searchStr);
	}

	/**
	 * 获取城市下拉列表
	 */
	@At
	@POST
	public Object getCustomerCitySelect(@Param("cityname") String cityname, @Param("citytype") String citytype,
			@Param("exname") String exname, HttpSession session) {
		return simpleVisaService.getCustomerCitySelect(cityname, citytype, exname, session);
	}

	/**
	 * 保存姓名中没有翻译的中文，以便在 汉字转拼音 js中加入对应的中文拼音
	 */
	@At
	@POST
	public Object toRecordCharacters(@Param("characterStr") String characterStr) {
		return simpleVisaService.toRecordCharacters(characterStr);
	}

	/*@At
	@Ok("jsp")
	public Object dataUpload(@Param("orderid") Integer orderid, HttpServletRequest request) {
		return simpleVisaService.dataUpload(orderid, request);
	}*/

	@At
	@POST
	public Object hasApplyInfo(@Param("applyid") int applyid, @Param("orderid") int orderid,
			@Param("token") String token, HttpServletRequest request) {
		return simpleVisaService.hasApplyInfo(applyid, orderid, token, request);
	}

	@At
	@POST
	public Object isSamewithMainapply(@Param("orderid") int orderid) {
		return simpleVisaService.isSamewithMainapply(orderid);
	}

	@At
	@POST
	public Object autoCalculateStaydays(@Param("laststartdate") Date laststartdate,
			@Param("lastreturndate") Date lastreturndate) {
		return simpleVisaService.autoCalculateStaydays(laststartdate, lastreturndate);
	}

	@At
	@POST
	public Object ishaveMainapply(@Param("orderid") int orderid, @Param("applicantid") int applicantid) {
		return simpleVisaService.ishaveMainapply(orderid, applicantid);
	}

	@At
	@POST
	public Object saveSendandGround(@Param("orderid") int orderid, @Param("sendsignid") int sendsignid,
			@Param("groundconnectid") int groundconnectid) {
		return simpleVisaService.saveSendandGround(orderid, sendsignid, groundconnectid);
	}

}
