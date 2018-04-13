package com.juyo.visa.admin.myVisa.module;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.myVisa.form.MyVisaListDataForm;
import com.juyo.visa.admin.myVisa.service.MyVisaService;
import com.juyo.visa.admin.visajp.form.VisaListDataForm;

/**
 * 我的签证Module
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年11月23日 	 
 */
@IocBean
@At("/admin/myVisa")
@Filters
public class MyVisaModule {

	@Inject
	private MyVisaService myVisaService;

	/**
	 * 申请人 办理中的签证
	 *
	 * @param session
	 * @return 
	 */
	@At
	@GET
	@Ok("jsp")
	public Object inProcessVisa(@Param("orderJpId") String orderJpId) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("orderJpId", orderJpId);
		return result;
	}

	/**
	 * 申请人 订单列表
	 */
	@At
	@GET
	@Ok("jsp")
	public Object visaList() {
		return null;
	}

	/**
	 * 申请人 订单列表数据
	 */
	@At
	@POST
	public Object visaListData(@Param("..") VisaListDataForm form, HttpSession session) {
		return myVisaService.visaListData(form, session);
	}

	/**
	 *获取我的签证列表数据
	 * <p>
	 * @param form
	 * @param request
	 * @return 
	 */
	@At
	@POST
	public Object myVisaListData(@Param("orderJpId") int orderJpId, @Param("..") MyVisaListDataForm form,
			HttpSession session) {
		return myVisaService.myVisaListData(orderJpId, form, session);
	}

	/**
	 * 
	 * 进度页
	 *
	 * @param orderid
	 * @param applicantid
	 * @return 
	 */
	@At
	@Ok("jsp")
	public Object flowChart(@Param("orderid") Integer orderid, @Param("applicantid") Integer applicantid) {
		return myVisaService.flowChart(orderid, applicantid);
	}

	/**
	 * 填写快递单号页
	 * 
	 * @param applicantid
	 * @return 
	 */
	@At
	@GET
	@Ok("jsp")
	public Object youkeExpressInfo(@Param("applicantId") Integer applicantid, @Param("orderId") Integer orderId) {
		return myVisaService.youkeExpressInfo(applicantid, orderId);
	}

	/**
	 *保存快递单号
	 */
	@At
	@POST
	public Object saveExpressInfo(@Param("expressType") int expressType, @Param("expressNum") String expressNum,
			@Param("applicantId") Integer applicantId, @Param("orderId") Integer orderId, HttpSession session) {
		return myVisaService.saveExpressInfo(expressType, expressNum, applicantId, orderId, session);
	}

	/**
	 * 将基本信息赋值给员工
	 */
	@At
	@POST
	public Object copyBaseToPersonnel(@Param("applyid") int applyid, HttpSession session) {
		return myVisaService.copyBaseToPersonnel(applyid, session);
	}

	/**
	 * 将基本信息赋值给游客
	 */
	@At
	@POST
	public Object copyBaseToTourist(@Param("applyid") int applyid, HttpSession session) {
		return myVisaService.copyBaseToTourist(applyid, session);
	}

	/**
	 * 将护照信息赋值给游客
	 */
	@At
	@POST
	public Object copyPassToTourist(@Param("applyid") int applyid, HttpSession session) {
		return myVisaService.copyPassToTourist(applyid, session);
	}

	/**
	 * 将签证信息赋值给游客
	 */
	@At
	@POST
	public Object copyVisaToTourist(@Param("applyid") int applyid, HttpSession session) {
		return myVisaService.copyVisaToTourist(applyid, session);
	}

	/**
	 * 设置为不再提示，即将申请人表的isSameInfo字段设置为0
	 */
	@At
	@POST
	public Object toSetUnsame(@Param("applyid") int applyid, HttpSession session) {
		return myVisaService.toSetUnsame(applyid, session);
	}

	/**
	 * 记录通过身份证、手机号、护照号更新信息时选择了是还是否
	 */
	@At
	@POST
	public Object updateOrNot(@Param("applyid") int applyid, @Param("updateOrNot") String updateOrNot,
			HttpSession session) {
		return myVisaService.updateOrNot(applyid, updateOrNot, session);
	}

	/**
	 * 记录保存时信息改变选择了是还是否
	 */
	@At
	@POST
	public Object saveIsOrNot(@Param("applyid") int applyid, @Param("updateOrNot") String updateOrNot,
			HttpSession session) {
		return myVisaService.saveIsOrNot(applyid, updateOrNot, session);
	}

	/**
	 * 查询是否改变
	 */
	@At
	@POST
	public Object isUpdate(@Param("applyid") int applyid) {
		return myVisaService.isUpdate(applyid);
	}

	/**
	 * 把游客所有信息赋值给员工
	 */
	@At
	@POST
	public Object copyAllInfoToPersonnel(@Param("applyid") int applyid, HttpSession session) {
		return myVisaService.copyAllInfoToPersonnel(applyid, session);
	}

	/**
	 * 把所有信息赋值给游客
	 */
	@At
	@POST
	public Object copyAllInfoToTourist(@Param("applyid") int applyid, @Param("emptyInfo") String emptyInfo,
			HttpSession session) {
		return myVisaService.copyAllInfoToTuorist(applyid, emptyInfo, session);
	}
}
