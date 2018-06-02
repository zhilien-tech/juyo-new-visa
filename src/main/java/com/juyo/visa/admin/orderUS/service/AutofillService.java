/**
 * AutofillService.java
 * com.juyo.visa.admin.orderUS.service
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.orderUS.service;

import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.juyo.visa.entities.TOrderUsEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class AutofillService extends BaseService<TOrderUsEntity> {

	public Object getData(int orderid) {
		Map<String, Object> result = Maps.newHashMap();
		//查询所需数据（除了一对多）
		String sqlStr = sqlManager.get("getAutofilldata");
		Sql infosql = Sqls.create(sqlStr);
		Cnd cnd = Cnd.NEW();
		cnd.and("tou.id", "=", orderid);
		infosql.setCondition(cnd);
		Record info = dbDao.fetch(infosql);
		//根据API封装数据（从内向外）
		//nameinfo
		Map<String, Object> nameinfo = Maps.newHashMap();
		if (!Util.isEmpty(info.get("telecodefirstname"))) {
			nameinfo.put("surnames_code_cn", info.get("telecodefirstname"));
		} else {
			nameinfo.put("surnames_code_cn", "");
		}
		if (!Util.isEmpty(info.get("telecodelastname"))) {
			nameinfo.put("given_names_code_cn", info.get("telecodelastname"));
		} else {
			nameinfo.put("given_names_code_cn", "");
		}
		return result;
	}
}
