package com.juyo.visa.admin.receiveaddress.service;

import com.uxuexi.core.web.base.service.BaseService;
import com.juyo.visa.entities.TReceiveaddressEntity;
import com.juyo.visa.forms.TReceiveaddressForm;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

@IocBean
public class ReceiveaddressViewService extends BaseService<TReceiveaddressEntity> {
	private static final Log log = Logs.get();
	
	public Object listData(TReceiveaddressForm queryForm) {
		return listPage4Datatables(queryForm);
	}
   
}