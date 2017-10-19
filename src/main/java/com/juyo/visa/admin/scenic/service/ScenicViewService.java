package com.juyo.visa.admin.scenic.service;

import com.uxuexi.core.web.base.service.BaseService;
import com.juyo.visa.entities.TScenicEntity;
import com.juyo.visa.forms.TScenicForm;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

@IocBean
public class ScenicViewService extends BaseService<TScenicEntity> {
	private static final Log log = Logs.get();
	
	public Object listData(TScenicForm queryForm) {
		return listPage4Datatables(queryForm);
	}
   
}