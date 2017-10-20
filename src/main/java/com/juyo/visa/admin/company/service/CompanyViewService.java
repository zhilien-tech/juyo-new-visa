package com.juyo.visa.admin.company.service;

import com.uxuexi.core.web.base.service.BaseService;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.forms.TCompanyForm;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

@IocBean
public class CompanyViewService extends BaseService<TCompanyEntity> {
	private static final Log log = Logs.get();
	
	public Object listData(TCompanyForm queryForm) {
		return listPage4Datatables(queryForm);
	}
   
}