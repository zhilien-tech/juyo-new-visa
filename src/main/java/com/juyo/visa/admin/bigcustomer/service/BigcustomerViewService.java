package com.juyo.visa.admin.bigcustomer.service;

import com.uxuexi.core.web.base.service.BaseService;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.forms.TAppStaffBasicinfoForm;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

@IocBean
public class BigcustomerViewService extends BaseService<TAppStaffBasicinfoEntity> {
	private static final Log log = Logs.get();
	
	public Object listData(TAppStaffBasicinfoForm queryForm) {
		return listPage4Datatables(queryForm);
	}
   
}