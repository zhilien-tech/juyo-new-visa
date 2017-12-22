/**
 * AftermarketModule.java
 * com.juyo.visa.admin.aftermarket
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.aftermarket.module;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.juyo.visa.admin.aftermarket.form.AftermarketListForm;
import com.juyo.visa.admin.aftermarket.service.AftermarketService;
import com.juyo.visa.common.enums.JPOrderStatusEnum;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年12月4日 	 
 */
@IocBean
@At("admin/aftermarket")
public class AftermarketModule {

	@Inject
	private AftermarketService aftermarketService;

	/**
	 * 跳转到列表页
	 */
	@At
	@Ok("jsp")
	public Object list() {
		Map<String, Object> result = Maps.newHashMap();
		List<Map> orderstatus = Lists.newArrayList();
		for (JPOrderStatusEnum jporderstatus : JPOrderStatusEnum.values()) {
			if (jporderstatus.intKey() >= JPOrderStatusEnum.AFTERMARKET_ORDER.intKey()) {
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
	 * 获取售后列表数据
	 */
	@At
	@POST
	public Object aftermarketListData(@Param("..") AftermarketListForm form, HttpSession session) {
		return aftermarketService.aftermarketListData(form, session);
	}

	/**
	 * 发送邮件和短信
	 */
	@At
	@POST
	public Object sendMailAndMessage(@Param("applicantid") Integer applicantid, HttpServletRequest request) {
		return aftermarketService.sendMailAndMessage(applicantid, request);
	}
}
