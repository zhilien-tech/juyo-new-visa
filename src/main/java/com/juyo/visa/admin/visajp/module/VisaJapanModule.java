/**
 * VisaJapanModule.java
 * com.juyo.visa.admin.visajp
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp.module;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.juyo.visa.admin.visajp.form.GeneratePlanForm;
import com.juyo.visa.admin.visajp.form.PassportForm;
import com.juyo.visa.admin.visajp.form.SaveTravelForm;
import com.juyo.visa.admin.visajp.form.VisaEditDataForm;
import com.juyo.visa.admin.visajp.form.VisaListDataForm;
import com.juyo.visa.admin.visajp.service.VisaJapanService;
import com.juyo.visa.admin.visajp.service.VisaJapanSimulateService;
import com.juyo.visa.common.enums.IssueValidityEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderTravelplanJpEntity;
import com.uxuexi.core.common.util.EnumUtil;

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

	@Inject
	private VisaJapanSimulateService visaJapanSimulateService;

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
		Map<String, Object> result = Maps.newHashMap();
		List<Map> orderstatus = Lists.newArrayList();
		for (JPOrderStatusEnum jporderstatus : JPOrderStatusEnum.values()) {
			if (jporderstatus.intKey() >= JPOrderStatusEnum.VISA_ORDER.intKey()) {
				Map<String, Object> orderstatu = Maps.newHashMap();
				orderstatu.put("key", jporderstatus.intKey());
				orderstatu.put("value", jporderstatus.value());
				orderstatus.add(orderstatu);
			}
		}
		result.put("orderstatus", orderstatus);
		return result;
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
			@Param("travelinfo") String travelinfo, @Param("multiways") String multiways, HttpSession session) {
		return visaJapanService.saveJpVisaDetailInfo(editDataForm, travelinfo, multiways, session);
	}

	/**
	 * 重置计划
	 */
	@At
	@POST
	public Object resetPlan(@Param("orderid") Integer orderid, @Param("planid") Integer planid,
			@Param("visatype") int visatype, HttpSession session) {
		return visaJapanService.resetPlan(orderid, planid, visatype, session);
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
	public Object schedulingEdit(@Param("planid") Integer planid, @Param("visatype") int visatype) {
		return visaJapanService.schedulingEdit(planid, visatype);
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
	public Object revenue(HttpSession session, @Param("orderid") Integer orderid, @Param("type") Integer type) {
		return visaJapanService.revenue(session, orderid, type);
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
	public Object saveRealInfoData(@Param("..") TOrderJpEntity orderjp, @Param("applicatinfo") String applicatinfo,
			HttpSession session) {
		return visaJapanService.saveRealInfoData(orderjp, applicatinfo, session);
	}

	/**
	 * 跳转到签证录入页面
	 */
	@At
	@Ok("jsp")
	public Object visaInput(HttpSession session, @Param("applyid") Integer applyid, @Param("orderid") Integer orderid,
			@Param("isvisa") Integer isvisa) {
		return visaJapanService.visaInput(session, applyid, orderid, isvisa);
	}

	/**
	 * 获取签证录入列表数据
	 */
	@At
	@POST
	public Object getJpVisaInputListData(@Param("applyid") Integer applyid) {
		return visaJapanService.getJpVisaInputListData(applyid);
	}

	/**
	 * 跳转到护照信息页面
	 */
	@At
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
		return visaJapanService.passportInfo(applyId);
	}

	/**
	 * 保存护照信息页面
	 */
	@At
	@POST
	public Object savePassportInfo(@Param("..") PassportForm form, HttpSession session) {
		return visaJapanService.savePassportInfo(form, session);
	}

	/**
	 * 保存护照本数信息
	 */
	@At
	@POST
	public Object editPassportCount(@Param("applicatid") Integer applicatid, @Param("inputVal") String inputVal) {
		return visaJapanService.editPassportCount(applicatid, inputVal);
	}

	/**
	 * 发招宝、招宝变更、招宝取消
	 */
	@At
	@POST
	public Object sendInsurance(@Param("orderid") Integer orderid, @Param("visastatus") Integer visastatus,
			HttpSession session) {
		return visaJapanSimulateService.sendInsurance(orderid, visastatus, session);
	}

	/**
	 * 下载文件
	 */
	@At
	@GET
	public Object downloadFile(@Param("orderid") Long orderid, HttpServletResponse response, HttpSession session,
			HttpServletRequest request) {
		return visaJapanSimulateService.downloadFile(orderid, response, session, request);
	}

	//	@At
	//	@GET
	//	@Ok("jsp")
	//	public Object applyInfo(@Param("id") Integer id, @Param("orderid") Integer orderid,
	//			@Param("isOrderUpTime") Integer isOrderUpTime, @Param("isTrial") Integer isTrial,
	//			@Param("orderProcessType") Integer orderProcessType, @Param("addApply") int addApply,
	//			@Param("tourist") int tourist, HttpServletRequest request) {
	//		return visaJapanSimulateService.getUserInfo(id, orderid, isOrderUpTime, isTrial, orderProcessType, addApply, tourist,
	//				request);
	//	}
	/**
	 * 上传签证文件
	 */
	@At
	@POST
	@AdaptBy(type = UploadAdaptor.class)
	public Object uploadVisaPic(@Param("uploadfile") File file, HttpServletRequest request) {
		return visaJapanSimulateService.uploadVisaPic(file, request);
	}

	/**
	 * 自动计算返回日期(预计送签、出签专用)
	 */
	@At
	@POST
	public Object autoCalculateBackDateSpecial(@Param("gotripdate") Date gotripdate, @Param("stayday") Integer stayday) {
		return visaJapanService.autoCalculateBackDateSpecial(gotripdate, stayday);
	}

	/**
	 * 自动计算返回日期
	 */
	@At
	@POST
	public Object autoCalculateBackDate(@Param("gotripdate") Date gotripdate, @Param("stayday") Integer stayday) {
		return visaJapanService.autoCalculateBackDate(gotripdate, stayday);
	}

	@At
	@POST
	public Object validateIsoriginal(@Param("paperid") Integer paperid) {
		return visaJapanService.validateIsoriginal(paperid);
	}

	/**
	 * 移交售后
	 */
	@At
	@POST
	public Object afterMarket(@Param("orderid") Long orderid, HttpServletRequest request) {
		return visaJapanService.afterMarket(orderid, request);
	}

	/**
	 * 实收信息通知销售
	 */
	@At
	@POST
	public Object noticeSale(@Param("applyid") Integer applyid, @Param("orderid") Integer orderid, HttpSession session) {
		return visaJapanService.noticeSale(applyid, orderid, session);
	}

	/**
	 * 发招保页面
	 */
	@At
	@Ok("jsp")
	public Object sendZhaoBao(HttpServletRequest request, @Param("orderid") Long orderid) {
		return visaJapanService.sendZhaoBao(request, orderid);
	}

	/**
	 * 保存招宝信息，并发招宝
	 */
	@At
	@POST
	public Object saveZhaoBao(@Param("..") TOrderJpEntity orderJpEntity, HttpServletRequest request) {
		return visaJapanService.saveZhaoBao(orderJpEntity, request);
	}

	/**
	 * 获取签证列表申请人信息
	 */
	@At
	@POST
	public Object getVisaDetailApply(@Param("orderid") Integer orderid) {
		return visaJapanService.getVisaDetailApply(orderid);
	}

	/**
	 * 验证信息是否完整
	 */
	@At
	@POST
	public Object validateInfoIsFull(@Param("orderjpid") Integer orderjpid) {
		return visaJapanService.validateInfoIsFull(orderjpid);
	}

	/**
	 * 验证下载信息是否完整
	 */
	@At
	@POST
	public Object validateDownLoadInfoIsFull(@Param("orderjpid") Integer orderjpid) {
		return visaJapanService.validateDownLoadInfoIsFull(orderjpid);
	}

	/**
	 * 跳转到发招宝信息页面
	 */
	@At
	@Ok("jsp")
	public Object sendZhaoBaoError(HttpServletRequest request, HttpSession session, @Param("type") Integer type) {
		return visaJapanService.sendZhaoBaoError(request, session, type);
	}

	/**
	 * 验证发招宝指定番号是否填写
	 */
	@At
	@POST
	public Object validateDesignNum(@Param("sendsignid") Integer sendsignid, HttpSession session) {
		return visaJapanService.validateDesignNum(sendsignid, session);
	}

	/**
	 * 保存出行信息
	 */
	@At
	@POST
	public Object saveJpVisaTravelInfo(@Param("..") SaveTravelForm form, HttpSession session) {
		return visaJapanService.saveJpVisaTravelInfo(form, session);
	}

	/**
	 * 加载订单状态
	 */
	@At
	@POST
	public Object initOrderstatus(@Param("orderid") Long orderid) {
		return visaJapanService.initOrderstatus(orderid);
	}
}
