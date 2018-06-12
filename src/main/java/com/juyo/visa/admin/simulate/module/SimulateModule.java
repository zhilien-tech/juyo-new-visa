/**
 * SimulateModule.java
 * com.juyo.visa.admin.simulate
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.simulate.module;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.juyo.visa.admin.simulate.form.JapanSimulateErrorForm;
import com.juyo.visa.admin.simulate.form.JapanSimulatorForm;
import com.juyo.visa.admin.simulate.service.SimulateJapanService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年11月9日 	 
 */
@At("admin/simulate")
@IocBean
@Filters
public class SimulateModule {

	@Inject
	private SimulateJapanService simulateJapanService;

	/**
	 * 查看是否有可执行的订单
	 */
	@At
	public Object fetchJapanOrder() {
		return simulateJapanService.fetchJapanOrder();
	}

	/**
	 * 更新发招宝状态为提交中
	 */
	@At
	public Object ds160Japan(@Param("oid") Integer cid) {
		return simulateJapanService.ds160Japan(cid);
	}

	/**
	 * 上传归国报告
	 */
	@At
	@POST
	@AdaptBy(type = UploadAdaptor.class)
	public Object UploadJapan(@Param("oid") Integer cid, @Param("..") JapanSimulatorForm form, @Param("file") File file) {
		return simulateJapanService.uploadJapan(cid, form, file);
	}

	/**
	 * 错误处理
	 */
	@At
	@POST
	public Object japanErrorHandle(@Param("..") JapanSimulateErrorForm form, @Param("cid") Long cid) {
		return simulateJapanService.japanErrorHandle(form, cid);
	}

	/**
	 * 招宝变更失败状态获取
	 */
	@At
	@POST
	public Object updateZhaobao(@Param("..") JapanSimulatorForm form) {
		return simulateJapanService.updateZhaobao(form);
	}

	/**
	 * 招宝取消失败状态获取
	 */
	@At
	@POST
	public Object cancelZhaobao(@Param("..") JapanSimulatorForm form) {
		return simulateJapanService.cancelZhaobao(form);
	}

	/**
	 * 代理下载excel
	 */
	@At
	public Object agentDownload(@Param("excelurl") String excelurl, HttpServletResponse response) {
		return simulateJapanService.agentDownload(excelurl, response);
	}

	/**
	 * 更新受付番号
	 */
	@At
	public Object updateAcceptanceNumber(@Param("..") JapanSimulatorForm form) {
		return simulateJapanService.updateAcceptanceNumber(form);
	}

	/**
	 * 添加填表日志
	 */
	@At
	public Object insertLog(@Param("..") JapanSimulatorForm form) {
		return simulateJapanService.insertLog(form);
	}

	/**
	 * 更新为已发招宝
	 */
	@At
	public Object updateYifa(@Param("..") JapanSimulatorForm form) {
		return simulateJapanService.updateYifa(form);
	}
}
