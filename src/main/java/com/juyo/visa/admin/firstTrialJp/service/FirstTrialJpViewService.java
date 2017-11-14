/**
 * FirstTrialJpViewService.java
 * com.juyo.visa.admin.firstTrialJp.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.firstTrialJp.service;

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
import com.juyo.visa.admin.firstTrialJp.from.FirstTrialJpListDataForm;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.enums.TrialApplicantStatusEnum;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * 日本订单初审Service
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年11月9日 	 
 */
@IocBean
public class FirstTrialJpViewService extends BaseService<TOrderEntity> {

	/**
	 * 初审列表数据
	 * @param form
	 * @param request
	 * @return
	 */
	public Object trialListData(FirstTrialJpListDataForm form, HttpSession session) {
		//获取当前公司
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//获取当前用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		form.setUserid(loginUser.getId());
		form.setCompanyid(loginCompany.getId());
		form.setAdminId(loginCompany.getAdminId());
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
			Integer orderid = (Integer) record.get("id");
			String sqlStr = sqlManager.get("firstTrialJp_list_data_applicant");
			Sql applysql = Sqls.create(sqlStr);
			List<Record> records = dbDao.query(applysql, Cnd.where("taoj.orderId", "=", orderid), null);
			for (Record applicant : records) {
				Integer status = (Integer) applicant.get("applicantStatus");
				for (TrialApplicantStatusEnum statusEnum : TrialApplicantStatusEnum.values()) {
					if (!Util.isEmpty(status) && status.equals(statusEnum.intKey())) {
						applicant.put("applicantStatus", statusEnum.value());
					}
				}
			}
			record.put("everybodyInfo", records);
		}
		result.put("trialJapanData", list);
		return result;

	}
}
