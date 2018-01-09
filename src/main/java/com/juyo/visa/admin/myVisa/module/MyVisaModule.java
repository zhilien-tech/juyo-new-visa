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
	 * 判断游客信息是否改变
	 */
	@At
	@POST
	public Object isChanged(@Param("applyid") int applyid, HttpSession session) {
		return myVisaService.isChanged(applyid, session);
	}

	/**
	 * 将游客信息赋值给员工
	 */
	@At
	@POST
	public Object copyInfoToPersonnel(@Param("applyid") int applyid, HttpSession session) {
		return myVisaService.copyInfoToPersonnel(applyid, session);
	}

	/**
	 * 将游客基本信息赋值给员工基本信息
	 */
	@At
	@POST
	public Object copyBaseToPersonnel(@Param("applyid") int applyid, HttpSession session) {
		return myVisaService.copyBaseToPersonnel(applyid, session);
	}

	/**
	 * 将游客护照信息赋值给员工基本信息
	 */
	@At
	@POST
	public Object copyPassToPersonnel(@Param("applyid") int applyid, HttpSession session) {
		return myVisaService.copyPassToPersonnel(applyid, session);
	}

	/**
	 * 设置为不再提示，即将申请人表的isSameInfo字段设置为0
	 */
	@At
	@POST
	public Object toSetUnsame(@Param("applyid") int applyid, HttpSession session) {
		return myVisaService.toSetUnsame(applyid, session);
	}
}
