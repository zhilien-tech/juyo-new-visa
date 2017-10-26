package com.juyo.visa.admin.flight.service;

import java.util.Date;
import java.util.Map;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Maps;
import com.juyo.visa.entities.TCityEntity;
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

	/**
	 * 
	 * TODO 加载更新页面时回显
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param id
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object fetchFlight(final long id) {
		Map<String, Object> result = Maps.newHashMap();
		TFlightEntity flight = this.fetch(id);
		TCityEntity takeOffCity = dbDao.fetch(TCityEntity.class, new Long(flight.getTakeOffCityId()).intValue());
		TCityEntity landingCity = dbDao.fetch(TCityEntity.class, new Long(flight.getLandingCityId()).intValue());
		result.put("flight", flight);
		result.put("takeOffCity", takeOffCity);
		result.put("landingCity", landingCity);
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
	public Object addFlight(TFlightAddForm addForm) {
		addForm.setCreateTime(new Date());
		this.add(addForm);
		return JsonResult.success("添加成功");
	}

	/**
	 * 
	 * TODO 修改
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param updateForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateFlight(TFlightUpdateForm updateForm) {
		updateForm.setUpdateTime(new Date());
		TFlightEntity flight = this.fetch(updateForm.getId());
		updateForm.setCreateTime(flight.getCreateTime());
		this.update(updateForm);
		return JsonResult.success("修改成功");
	}

}