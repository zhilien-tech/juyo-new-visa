/**
 * SaleViewService.java
 * com.juyo.visa.admin.sale.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.order.service;

import org.nutz.ioc.loader.annotation.IocBean;

import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.forms.TOrderJpForm;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年10月26日 	 
 */
@IocBean
public class OrderJpViewService extends BaseService<TOrderJpEntity> {

	public Object listDate(TOrderJpForm queryForm) {

		return this.listDate(queryForm);
	}
}
