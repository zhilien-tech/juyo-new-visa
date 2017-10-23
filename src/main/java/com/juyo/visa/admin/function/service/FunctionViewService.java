package com.juyo.visa.admin.function.service;

import com.uxuexi.core.web.base.service.BaseService;
import com.juyo.visa.entities.TFunctionEntity;
import com.juyo.visa.forms.TFunctionForm;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

@IocBean
public class FunctionViewService extends BaseService<TFunctionEntity> {
	private static final Log log = Logs.get();
	
	public Object listData(TFunctionForm queryForm) {
		return listPage4Datatables(queryForm);
	}
   
}