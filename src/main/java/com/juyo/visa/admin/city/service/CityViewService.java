package com.juyo.visa.admin.city.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Maps;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.forms.TCityAddForm;
import com.juyo.visa.forms.TCityForm;
import com.juyo.visa.forms.TCityUpdateForm;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
public class CityViewService extends BaseService<TCityEntity> {
	private static final Log log = Logs.get();

	public Object listData(TCityForm queryForm) {
		return listPage4Datatables(queryForm);
	}

	/**
	 * 
	 * TODO 根据所选国家获取所有省/州/县
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param country
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public List<String> queryProvince(String country) {
		List<String> provinceList = new ArrayList<>();
		List<TCityEntity> city = dbDao.query(TCityEntity.class, Cnd.where("country", "=", country), null);
		for (TCityEntity tCityEntity : city) {
			if (!provinceList.contains(tCityEntity.getProvince())) {
				provinceList.add(tCityEntity.getProvince());
			}
		}
		return provinceList;
	}

	/**
	 * 
	 * TODO 根据所选省/州/县 获取所有城市
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param province
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public List<String> queryCity(String province) {
		List<String> cityList = new ArrayList<>();
		List<TCityEntity> city = dbDao.query(TCityEntity.class, Cnd.where("province", "=", province), null);
		for (TCityEntity tCityEntity : city) {
			if (!cityList.contains(tCityEntity.getCity())) {
				cityList.add(tCityEntity.getCity());
			}
		}
		return cityList;
	}

	/**
	 * 
	 * TODO 页面加载时获取 国家 省/州/县 城市 所有的下拉选项
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object listCountrySearch() {
		Map<String, Object> result = Maps.newHashMap();
		Map<Integer, String> cityMapByCountry = Maps.newHashMap();
		Map<Integer, String> cityMapByProvince = Maps.newHashMap();
		Map<Integer, String> cityMapByCity = Maps.newHashMap();
		List<TCityEntity> tCityList = dbDao.query(TCityEntity.class, null, null);
		for (TCityEntity tCityEntity : tCityList) {
			//TCityEntity cityByCountry = dbDao.fetch(TCityEntity.class, Cnd.where("country", "=", tCityEntity.getCountry()));
			if (!cityMapByCountry.containsValue(tCityEntity.getCountry())) {
				cityMapByCountry.put(tCityEntity.getId(), tCityEntity.getCountry());
			}

			if (!cityMapByProvince.containsValue(tCityEntity.getProvince())) {
				cityMapByProvince.put(tCityEntity.getId(), tCityEntity.getProvince());
			}
			if (!cityMapByCity.containsValue(tCityEntity.getCity())) {
				cityMapByCity.put(tCityEntity.getId(), tCityEntity.getCity());
			}
		}
		result.put("country", cityMapByCountry);
		result.put("province", cityMapByProvince);
		result.put("city", cityMapByCity);
		return result;
	}

	/**
	 * 
	 * TODO 添加城市信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param addForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addCity(TCityAddForm addForm) {
		addForm.setCreateTime(new Date());
		this.add(addForm);
		return JsonResult.success("添加成功");
	}

	/**
	 * 
	 * TODO 修改城市信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param updateForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateCity(TCityUpdateForm updateForm) {
		TCityEntity city = this.fetch(updateForm.getId());
		updateForm.setCreateTime(city.getCreateTime());
		updateForm.setUpdateTime(new Date());
		this.update(updateForm);
		return JsonResult.success("修改成功");
	}

	/**
	 * 
	 * TODO 根据城市ID获取城市名称
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param sqlParamForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object searchByCityId(TCityForm sqlParamForm) {
		sqlParamForm.getCity();
		return sqlParamForm.getCity();
	}

	/**
	 * 
	 * TODO 获取城市所有下拉选项
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param cityname
	 * @param exname
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getCustomerCitySelect(String cityname, String exname) {
		List<TCityEntity> citySelect = new ArrayList<TCityEntity>();
		try {
			citySelect = dbDao.query(TCityEntity.class, Cnd.where("city", "like", Strings.trim(cityname) + "%"), null);
			//移除的城市
			TCityEntity exinfo = new TCityEntity();
			for (TCityEntity tCityEntity : citySelect) {
				if (!Util.isEmpty(exname) && tCityEntity.getCity().equals(exname)) {
					exinfo = tCityEntity;
				}
			}
			citySelect.remove(exinfo);
			if (citySelect.size() > 5) {
				citySelect = citySelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return citySelect;
	}

}