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

	public Object addCity(TCityAddForm addForm) {
		addForm.setCreateTime(new Date());
		this.add(addForm);
		return JsonResult.success("添加成功");
	}

	public Object updateCity(TCityUpdateForm updateForm) {
		TCityEntity city = this.fetch(updateForm.getId());
		updateForm.setCreateTime(city.getCreateTime());
		updateForm.setUpdateTime(new Date());
		this.update(updateForm);
		return JsonResult.success("修改成功");
	}

	public Object searchByCityId(TCityForm sqlParamForm) {
		sqlParamForm.getCity();
		return sqlParamForm.getCity();
	}

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