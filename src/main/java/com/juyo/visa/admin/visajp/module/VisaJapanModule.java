/**
 * VisaJapanModule.java
 * com.juyo.visa.admin.visajp
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.visajp.form.GeneratePlanForm;
import com.juyo.visa.admin.visajp.form.VisaEditDataForm;
import com.juyo.visa.admin.visajp.form.VisaListDataForm;
import com.juyo.visa.admin.visajp.service.VisaJapanService;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderTravelplanJpEntity;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年10月30日 	 
 */
@IocBean
@At("/admin/visaJapan")
public class VisaJapanModule {

	@Inject
	private VisaJapanService visaJapanService;

	/**
	 * TODO 跳转到签证列表页
	 * <p>
	 * TODO 跳转到签证列表页
	 *
	 * @return TODO 跳转到签证列表页
	 */
	@At
	@GET
	@Ok("jsp")
	public Object visaList() {
		return null;
	}

	/**
	 *获取签证列表数据
	 * <p>
	 * TODO 日本签证列表数据
	 *
	 * @param form
	 * @param request
	 * @return TODO 日本签证列表数据
	 */
	@At
	@POST
	public Object visaListData(@Param("..") VisaListDataForm form, HttpSession session) {
		return visaJapanService.visaListData(form, session);
	}

	/**
	 * 跳转到签证详情页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object visaDetail(@Param("orderid") Integer orderid) {
		return visaJapanService.visaDetail(orderid);
	}

	/**
	 * 获取签证详情页数据
	 */
	@At
	@POST
	public Object getJpVisaDetailData(@Param("orderid") Integer orderid) {
		return visaJapanService.getJpVisaDetailData(orderid);
	}

	/**
	 * 保存签证详情数据
	 */
	@At
	@POST
	public Object saveJpVisaDetailInfo(@Param("..") VisaEditDataForm editDataForm,
			@Param("travelinfo") String travelinfo, HttpSession session) {
		return visaJapanService.saveJpVisaDetailInfo(editDataForm, travelinfo, session);
	}

	/**
	 * 重置计划
	 */
	@At
	@POST
	public Object resetPlan(@Param("orderid") Integer orderid, @Param("planid") Integer planid) {
		return visaJapanService.resetPlan(orderid, planid);
	}

	/**
	 * 生成行程安排
	 */
	@At
	@POST
	public Object generatePlan(@Param("..") GeneratePlanForm planform, HttpSession session) {
		return visaJapanService.generatePlan(planform, session);
	}

	/**
	 * 跳转到修改计划页面
	 */
	@At
	@Ok("jsp")
	public Object schedulingEdit(@Param("planid") Integer planid) {
		return visaJapanService.schedulingEdit(planid);
	}

	/**
	 * 保存修改数据
	 */
	@At
	@POST
	public Object saveEditPlanData(@Param("..") TOrderTravelplanJpEntity travel, HttpSession session) {
		return visaJapanService.saveEditPlanData(travel, session);
	}

	/**
	 * 获取行程安排数据
	 */
	@At
	@POST
	public Object getTrvalPlanData(@Param("orderid") Integer orderid) {
		return visaJapanService.getTravelPlanByOrderId(orderid);
	}

	/**
	 * 跳转到实收页面
	 */
	@At
	@Ok("jsp")
	public Object revenue(HttpSession session, @Param("orderid") Integer orderid) {
		return visaJapanService.revenue(session, orderid);
	}

	/**
	 * 加载实收页面数据
	 */
	@At
	@POST
	public Object visaRevenue(HttpSession session, @Param("orderid") Integer orderid) {
		return visaJapanService.visaRevenue(session, orderid);
	}

	/**
	 * 保存申请人真实资料数据
	 */
	@At
	@POST
	public Object saveApplicatRevenue(@Param("applicatid") Integer applicatid, @Param("realInfo") String realInfo,
			HttpSession session) {
		return visaJapanService.saveApplicatRevenue(applicatid, realInfo, session);
	}

	/**
	 * 保存真实资料备注
	 */
	@At
	@POST
	public Object saveRealInfoData(@Param("..") TOrderJpEntity orderjp) {
		return visaJapanService.saveRealInfoData(orderjp);
	}

	/**
	 * 跳转到签证录入页面
	 */
	@At
	@Ok("jsp")
	public Object visaInput(HttpSession session, @Param("applyid") Integer applyid) {
		return visaJapanService.visaInput(session, applyid);
	}

	/**
	 * 获取签证录入列表数据
	 */
	@At
	@POST
	public Object getJpVisaInputListData(@Param("applyid") Integer applyid) {
		return visaJapanService.getJpVisaInputListData(applyid);
	}
}
