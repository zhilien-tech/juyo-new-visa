package com.juyo.visa.admin.authority.service;

import com.uxuexi.core.web.base.service.BaseService;
import com.juyo.visa.entities.TDepartmentEntity;
import com.juyo.visa.forms.TDepartmentForm;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

@IocBean
public class AuthorityViewService extends BaseService<TDepartmentEntity> {
	private static final Log log = Logs.get();
	
	public Object listData(TDepartmentForm queryForm) {
		return listPage4Datatables(queryForm);
	}
   
}