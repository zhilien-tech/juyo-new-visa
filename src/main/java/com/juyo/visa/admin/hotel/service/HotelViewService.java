package com.juyo.visa.admin.hotel.service;

import com.uxuexi.core.web.base.service.BaseService;
import com.juyo.visa.entities.THotelEntity;
import com.juyo.visa.forms.THotelForm;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

@IocBean
public class HotelViewService extends BaseService<THotelEntity> {
	private static final Log log = Logs.get();
	
	public Object listData(THotelForm queryForm) {
		return listPage4Datatables(queryForm);
	}
   
}