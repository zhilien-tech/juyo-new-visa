package com.juyo.visa.admin.bigcustomer.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.entities.TAppEventsIntroduceEntity;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.forms.TAppEventsForm;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class AppEventsViewService extends BaseService<TAppStaffBasicinfoEntity> {
	private static final Log log = Logs.get();

	/**
	 * 
	 * 大客户 首页 列表页
	 *
	 * @param queryForm
	 * @param session
	 * @return
	 */
	public Object listData(TAppEventsForm queryForm, HttpSession session) {

		//当前登录公司Id
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Integer comId = loginCompany.getId();//当前登录公司id

		Map<String, Object> map = listPage4Datatables(queryForm);
		return map;
	}

	/**
	 * 
	 * App 首页活动 详情页
	 * 
	 * @param eventId
	 * @param session
	 * @return 
	 */
	public Object appEventDetails(Integer eventId, HttpSession session) {
		Record record = new Record();
		if (!Util.isEmpty(eventId)) {
			//活动详情
			String sqlStr = sqlManager.get("appevents_detail_byId");
			Sql sql = Sqls.create(sqlStr);
			sql.setParam("eventId", eventId);
			record = dbDao.fetch(sql);

			//活动介绍
			List<TAppEventsIntroduceEntity> eventIntroduceList = dbDao.query(TAppEventsIntroduceEntity.class,
					Cnd.where("eventsId", "=", eventId), null);
			record.put("eventIntroduceList", eventIntroduceList);
		}

		return record;
	}
}