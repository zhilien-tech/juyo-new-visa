package com.juyo.visa.admin.city.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

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