package com.juyo.visa.admin.city.service;

import com.uxuexi.core.web.base.service.BaseService;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.forms.TCityForm;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

@IocBean
public class CityViewService extends BaseService<TCityEntity> {
	private static final Log log = Logs.get();
	
	public Object listData(TCityForm queryForm) {
		return listPage4Datatables(queryForm);
	}
   
}