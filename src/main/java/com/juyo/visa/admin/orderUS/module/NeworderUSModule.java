/**
 * OrderUSModule.java
 * com.juyo.visa.admin.orderUS.module
 * Copyright (c) 2018, 北京科技有限公司版权所有.
 */

package com.juyo.visa.admin.orderUS.module;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.mobile.form.BasicinfoUSForm;
import com.juyo.visa.admin.mobile.form.FamilyinfoUSForm;
import com.juyo.visa.admin.mobile.form.PassportinfoUSForm;
import com.juyo.visa.admin.mobile.form.TravelinfoUSForm;
import com.juyo.visa.admin.mobile.form.WorkandeducateinfoUSForm;
import com.juyo.visa.admin.orderUS.service.AutofillService;
import com.juyo.visa.admin.orderUS.service.NeworderUSViewService;
import com.juyo.visa.admin.orderUS.service.OrderUSViewService;

/**
 * 美国订单US
 *
 * @author   
 * @Date	 2018年3月30日 	 
 */
@IocBean
@At("/admin/neworderUS")
public class NeworderUSModule {

	@Inject
	private OrderUSViewService orderUSViewService;

	@Inject
	private NeworderUSViewService neworderUSViewService;

	@Inject
	private AutofillService autofillService;

	/**
	 * 跳转到拍摄资料页面
	 */
	@At
	@GET
	@Ok("jsp:admin/pcVisa/updatePhoto")
	public Object updatePhoto(@Param("staffid") int staffid, HttpServletRequest request) {
		return neworderUSViewService.tofillimage(staffid, request);
	}

	@At
	@GET
	@Filters
	@Ok("jsp:admin/pcVisa/QRPhoto")
	public Object QRPhoto(@Param("staffid") int staffid, HttpServletRequest request) {
		return neworderUSViewService.tofillimage(staffid, request);
	}

	/**
	 * 跳转到基本信息
	 */
	@At
	@GET
	@Ok("jsp:admin/bigCustomer/updateBaseInfo")
	public Object updateBaseInfo(@Param("staffid") int staffid) {
		return neworderUSViewService.toBasicinfo(staffid);
	}

	/**
	 * 保存基本信息
	 */
	@At
	@POST
	public Object saveBasicinfo(@Param("..") BasicinfoUSForm form) {
		return neworderUSViewService.saveBasicinfo(form);
		//return neworderUSViewService.saveBasicinfoThread(form);
	}

	/**
	 * 跳转到护照信息
	 */
	@At
	@GET
	@Ok("jsp:admin/bigCustomer/updatePassportInfo")
	public Object updatePassportInfo(@Param("staffid") int staffid) {
		return neworderUSViewService.toPassportinfo(staffid);
	}

	/**
	 * 保存护照信息
	 */
	@At
	@POST
	public Object savePassportinfo(@Param("..") PassportinfoUSForm form) {
		return neworderUSViewService.savePassportinfo(form);
		//return neworderUSViewService.savePassportinfoThread(form);
	}

	/**
	 * 跳转到家庭信息
	 */
	@At
	@GET
	@Ok("jsp:admin/bigCustomer/updateFamilyInfo")
	public Object updateFamilyInfo(@Param("staffid") int staffid) {
		return neworderUSViewService.toFamilyinfo(staffid);
	}

	/**
	 * 保存家庭信息
	 */
	@At
	@POST
	public Object saveFamilyinfo(@Param("..") FamilyinfoUSForm form) {
		return neworderUSViewService.saveFamilyinfo(form);
		//return neworderUSViewService.saveFamilyinfoThread(form);
	}

	/**
	 * 跳转到职业与教育信息
	 */
	@At
	@GET
	@Ok("jsp:admin/bigCustomer/updateWorkInfo")
	public Object updateWorkInfo(@Param("staffid") int staffid) {
		return neworderUSViewService.toWorkandeducation(staffid);
	}

	/**
	 * 保存职业与教育信息
	 */
	@At
	@POST
	public Object saveWorkandeducation(@Param("..") WorkandeducateinfoUSForm form) {
		return neworderUSViewService.saveWorkandeducation(form);
		//return neworderUSViewService.saveWorkandeducationThread(form);
	}

	/**
	 * 跳转到旅行信息
	 */
	@At
	@GET
	@Ok("jsp:admin/bigCustomer/updateTravelInfo")
	public Object updateTravelInfo(@Param("staffid") int staffid) {
		return neworderUSViewService.toTravelinfo(staffid);
	}

	/**
	 * 保存旅行信息
	 */
	@At
	@POST
	public Object saveTravelinfo(@Param("..") TravelinfoUSForm form) {
		return neworderUSViewService.saveTravelinfo(form);
		//return neworderUSViewService.saveTravelinfoThread(form);
	}

	/**
	 * 国家模糊查询
	 */
	@At
	@POST
	public Object selectCountry(@Param("searchstr") String searchstr) {
		return neworderUSViewService.selectCountry(searchstr);
	}

	/**
	 * 省份模糊查询
	 */
	@At
	@POST
	public Object selectProvince(@Param("searchstr") String searchstr) {
		return neworderUSViewService.selectProvince(searchstr);
	}

	@At
	@POST
	public Object selectCity(@Param("province") String province, @Param("searchstr") String searchstr) {
		return neworderUSViewService.selectCity(province, searchstr);
	}

	/**
	 * 美国州模糊查询
	 */
	@At
	@POST
	public Object selectUSstate(@Param("searchstr") String searchstr) {
		return neworderUSViewService.selectUSstate(searchstr);
	}

	/**
	 * 美国城市模糊查询
	 */
	@At
	@POST
	public Object selectUScity(@Param("searchstr") String searchstr) {
		return neworderUSViewService.selectUScity(searchstr);
	}

	/**
	 * 美国州城市联动模糊查询
	 */
	@At
	@POST
	public Object selectUSstateandcity(@Param("province") int province, @Param("searchstr") String searchstr) {
		return neworderUSViewService.selectUSstateandcity(province, searchstr);
	}

	/**
	 * 根据国家名称查询id
	 */
	@At
	@POST
	public Object getCountryid(@Param("searchstr") String searchstr) {
		return neworderUSViewService.getCountryid(searchstr);
	}

	/**
	 * 翻译
	 */
	@At
	@POST
	@Filters
	public Object translate(@Param("strType") String type, @Param("q") String q) {
		return neworderUSViewService.translate(type, q);
	}

	/**
	 * 根据城市名称获取酒店信息
	 */
	@At
	@POST
	public Object selectUSHotel(@Param("plancity") String plancity, @Param("searchstr") String searchstr) {
		return neworderUSViewService.selectUSHotel(plancity, searchstr);
	}

	/**
	 * 根据酒店名称查询酒店地址
	 */
	@At
	@POST
	public Object getHoteladdress(@Param("hotelname") String hotelname) {
		return neworderUSViewService.getHoteladdress(hotelname);
	}

	/**
	 * 根据抵达日期和停留天数计算离开日期
	 */
	@At
	@POST
	public Object autoCalculateBackDate(@Param("gotripdate") Date gotripdate, @Param("stayday") Integer stayday) {
		return neworderUSViewService.autoCalculateBackDate(gotripdate, stayday);
	}

	/**
	 * 根据抵达日期和离开日期计算停留天数
	 */
	@At
	@POST
	public Object autoCalCulateStayday(@Param("gotripdate") Date gotripdate, @Param("returnDate") Date returnDate) {
		return neworderUSViewService.autoCalCulateStayday(gotripdate, returnDate);
	}

	@At
	@POST
	public Object autoCalculategoDate(@Param("gotripdate") Date gotripdate, @Param("stayday") Integer stayday) {
		return neworderUSViewService.autoCalculategoDate(gotripdate, stayday);
	}

	@At
	@GET
	public Object baiduTranslate(@Param("q") String q) {
		return neworderUSViewService.translate(q);
	}

}
