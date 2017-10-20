package com.juyo.visa.admin.customer.service;

import com.uxuexi.core.web.base.service.BaseService;
import com.juyo.visa.entities.TCustomerEntity;
import com.juyo.visa.forms.TCustomerForm;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

@IocBean
public class CustomerViewService extends BaseService<TCustomerEntity> {
	private static final Log log = Logs.get();
	
	public Object listData(TCustomerForm queryForm) {
		return listPage4Datatables(queryForm);
	}
   
}