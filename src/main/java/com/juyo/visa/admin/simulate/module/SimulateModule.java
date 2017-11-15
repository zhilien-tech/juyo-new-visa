/**
 * SimulateModule.java
 * com.juyo.visa.admin.simulate
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.simulate.module;

import java.io.File;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

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
	@GET
	public Object fetchJapanOrder() {
		return simulateJapanService.fetchJapanOrder();
	}

	/**
	 * 更新发招宝状态为已发招宝
	 */
	@At
	@GET
	public Object ds160Japan(@Param("cid") Integer cid) {
		return simulateJapanService.ds160Japan(cid);
	}

	/**
	 * 上传归国报告
	 */
	@At
	@POST
	@AdaptBy(type = UploadAdaptor.class)
	public Object UploadJapan(@Param("cid") Integer cid, @Param("..") JapanSimulatorForm form, @Param("file") File file) {
		return simulateJapanService.uploadJapan(cid, form, file);
	}

}
