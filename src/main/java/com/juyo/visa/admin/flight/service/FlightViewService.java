package com.juyo.visa.admin.flight.service;

import com.uxuexi.core.web.base.service.BaseService;
import com.juyo.visa.entities.TFlightEntity;
import com.juyo.visa.forms.TFlightForm;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

@IocBean
public class FlightViewService extends BaseService<TFlightEntity> {
	private static final Log log = Logs.get();
	
	public Object listData(TFlightForm queryForm) {
		return listPage4Datatables(queryForm);
	}
   
}