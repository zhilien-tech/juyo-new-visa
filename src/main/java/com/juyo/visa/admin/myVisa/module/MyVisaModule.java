package com.juyo.visa.admin.myVisa.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.myVisa.form.MyVisaListDataForm;
import com.juyo.visa.admin.myVisa.service.MyVisaService;

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
	@Ok("jsp")
	public Object inProcessVisa() {
		return null;
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
	public Object myVisaListData(@Param("..") MyVisaListDataForm form, HttpSession session) {
		return myVisaService.myVisaListData(form, session);
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
	public Object youkeExpressInfo(@Param("applicantId") Integer applicantid) {
		return myVisaService.youkeExpressInfo(applicantid);
	}

	/**
	 *保存快递单号
	 */
	@At
	@POST
	public Object saveExpressInfo(@Param("expressType") int expressType, @Param("expressNum") String expressNum,
			@Param("applicantId") Integer applicantId, HttpSession session) {
		return myVisaService.saveExpressInfo(expressType, expressNum, applicantId, session);
	}
}
