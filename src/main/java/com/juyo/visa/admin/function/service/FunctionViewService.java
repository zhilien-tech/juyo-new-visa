package com.juyo.visa.admin.function.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
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

	//跳转到编辑页面
	public Object getFunctionById(long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		//上级功能选择的时候要排除自己
		obj.put("funList", dbDao.query(TFunctionEntity.class, Cnd.where("id", "!=", id), null));
		obj.put("function", dbDao.fetch(TFunctionEntity.class, id));
		return obj;
	}

	//统计功能
	public Object getFunctions() {
		Map<String, Object> obj = MapUtil.map();
		List<TFunctionEntity> functionList = dbDao.query(TFunctionEntity.class, null, null);
		obj.put("functions", functionList);
		return obj;
	}
}