package com.juyo.visa.admin.flight.service;

import java.util.Date;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.juyo.visa.entities.TFlightEntity;
import com.juyo.visa.forms.TFlightAddForm;
import com.juyo.visa.forms.TFlightForm;
import com.juyo.visa.forms.TFlightUpdateForm;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
public class FlightViewService extends BaseService<TFlightEntity> {
	private static final Log log = Logs.get();

	public Object listData(TFlightForm queryForm) {
		return listPage4Datatables(queryForm);
	}

	public Object addFlight(TFlightAddForm addForm) {
		addForm.setCreateTime(new Date());
		this.add(addForm);
		return JsonResult.success("添加成功");
	}

	public Object updateFlight(TFlightUpdateForm updateForm) {
		updateForm.setUpdateTime(new Date());
		TFlightEntity flight = this.fetch(updateForm.getId());
		updateForm.setCreateTime(flight.getCreateTime());
		this.update(updateForm);
		return JsonResult.success("修改成功");
	}

}