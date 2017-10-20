package com.juyo.visa.admin.hotel.service;

import java.util.Date;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.THotelEntity;
import com.juyo.visa.forms.THotelAddForm;
import com.juyo.visa.forms.THotelForm;
import com.juyo.visa.forms.THotelUpdateForm;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
public class HotelViewService extends BaseService<THotelEntity> {
	private static final Log log = Logs.get();

	public Object listData(THotelForm queryForm) {
		return listPage4Datatables(queryForm);
	}

	public Object addHotel(THotelAddForm addForm) {
		addForm.setCreateTime(new Date());
		//dbDao.fetch(TCityEntity.class, Cnd.)
		TCityEntity city = dbDao.fetch(TCityEntity.class, Long.valueOf(addForm.getCityId()));
		addForm.setCityId(city.getId());
		THotelEntity hotel = this.add(addForm);
		return JsonResult.success("添加成功");
	}

	public Object updateHotel(THotelUpdateForm updateForm) {
		updateForm.setUpdateTime(new Date());
		THotelEntity hotel = this.fetch(updateForm.getId());
		updateForm.setCreateTime(hotel.getCreateTime());
		this.update(updateForm);
		return JsonResult.success("修改成功");
	}

}