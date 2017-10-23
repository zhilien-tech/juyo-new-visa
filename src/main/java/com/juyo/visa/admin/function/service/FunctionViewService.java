package com.juyo.visa.admin.function.service;

import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.juyo.visa.entities.TFunctionEntity;
import com.juyo.visa.forms.TFunctionForm;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class FunctionViewService extends BaseService<TFunctionEntity> {
	private static final Log log = Logs.get();

	//分页数据
	public Object listData(TFunctionForm queryForm) {
		return listPage4Datatables(queryForm);
	}

	//统计功能
	public Object getFunctions() {
		Map<String, Object> obj = MapUtil.map();
		List<TFunctionEntity> functionList = dbDao.query(TFunctionEntity.class, null, null);
		obj.put("functions", functionList);
		return obj;
	}
}