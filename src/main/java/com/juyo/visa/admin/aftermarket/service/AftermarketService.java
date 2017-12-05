/**
 * AftermarketService.java
 * com.juyo.visa.admin.aftermarket.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.aftermarket.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.aftermarket.form.AftermarketListForm;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年12月4日 	 
 */
@IocBean
public class AftermarketService extends BaseService<TOrderEntity> {

	/**
	 * 获取售后列表数据
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object aftermarketListData(AftermarketListForm form, HttpSession session) {

		//获取当前公司
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//获取当前用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		form.setCompanyid(loginCompany.getId());
		Map<String, Object> result = Maps.newHashMap();
		Sql sql = form.sql(sqlManager);

		Integer pageNumber = form.getPageNumber();
		Integer pageSize = form.getPageSize();

		Pager pager = new OffsetPager((pageNumber - 1) * pageSize, pageSize);
		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));
		sql.setPager(pager);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		//主sql数据
		List<Record> list = (List<Record>) sql.getResult();
		for (Record record : list) {
			String sqlstring = sqlManager.get("get_aftermarket_list_applicat_data");
			Sql applicatsql = Sqls.create(sqlstring);
			Cnd applicatcnd = Cnd.NEW();
			applicatcnd.and("taoj.orderid", "=", record.get("orderjpid"));
			List<Record> applicats = dbDao.query(applicatsql, applicatcnd, null);
			record.put("applicats", applicats);
		}
		result.put("pagetotal", pager.getPageCount());
		result.put("aftermarketData", list);
		return result;

	}

}
