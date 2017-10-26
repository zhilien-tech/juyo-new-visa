package com.juyo.visa.admin.hotel.service;

import java.util.Date;
import java.util.Map;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Maps;
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

	/**
	 * 
	 * TODO 加载更新页面时回显
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param id
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object fetchHotel(final long id) {
		Map<String, Object> result = Maps.newHashMap();
		THotelEntity hotel = this.fetch(id);
		TCityEntity city = dbDao.fetch(TCityEntity.class, new Long(hotel.getCityId()).intValue());
		result.put("hotel", hotel);
		result.put("city", city);
		return result;
	}

	/**
	 * 
	 * TODO 添加
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param addForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addHotel(THotelAddForm addForm) {
		addForm.setCreateTime(new Date());
		//dbDao.fetch(TCityEntity.class, Cnd.)
		TCityEntity city = dbDao.fetch(TCityEntity.class, Long.valueOf(addForm.getCityId()));
		addForm.setCityId(city.getId());
		THotelEntity hotel = this.add(addForm);
		return JsonResult.success("添加成功");
	}

	/**
	 * 
	 * TODO 更新
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param updateForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateHotel(THotelUpdateForm updateForm) {
		updateForm.setUpdateTime(new Date());
		THotelEntity hotel = this.fetch(updateForm.getId());
		updateForm.setCreateTime(hotel.getCreateTime());
		this.update(updateForm);
		return JsonResult.success("修改成功");
	}

}