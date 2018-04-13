package com.juyo.visa.admin.changePrincipal.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;

import com.juyo.visa.admin.changePrincipal.service.ChangePrincipalViewService;

/**
 * 变更订单负责人
 * <p>
 *
 * @author   彭辉
 * @Date	 2018年01月11日 	 
 */

@IocBean
@At("/admin/changePrincipal")
public class ChangePrincipalModule {

	@Inject
	private ChangePrincipalViewService changePrincipalViewService;

	/**
	 * 
	 * 变更订单负责人
	 * <p>
	 *
	 * @param orderId 订单id
	 * @param orderProcessType 订单主流程枚举
	 * @param userId 用户id
	 * @return 
	 */
	public Object ChangePrincipal(Integer orderId, Integer orderProcessType, Integer userId) {
		//TODO 根据传参，变更订单负责人
		return changePrincipalViewService.ChangePrincipal(orderId, orderProcessType, userId);
	}

}
