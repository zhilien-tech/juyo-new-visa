/**
 * ReceptionJpViewService.java
 * com.juyo.visa.admin.receptionJp.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.receptionJp.service;

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
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.receptionJp.form.ReceptionJpForm;
import com.juyo.visa.common.enums.ExpressTypeEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.VisaDataTypeEnum;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TOrderRecipientEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2017年11月24日 	 
 */
@IocBean
public class ReceptionJpViewService extends BaseService<TOrderRecipientEntity> {
	public Object listData(ReceptionJpForm form, HttpSession session) {
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
			int orderstatus = (int) record.get("orderstatus");
			for (JPOrderStatusEnum orderStatus : JPOrderStatusEnum.values()) {
				if (orderstatus == orderStatus.intKey()) {
					record.put("orderstatus", orderStatus.value());
				}
			}
			Integer orderid = (Integer) record.get("id");
			String sqlStr = sqlManager.get("reception_list_data");
			Sql applysql = Sqls.create(sqlStr);
			List<Record> records = dbDao.query(applysql, Cnd.where("taoj.orderId", "=", orderid), null);
			for (Record applicant : records) {
				Integer dataType = (Integer) applicant.get("dataType");
				for (VisaDataTypeEnum dataTypeEnum : VisaDataTypeEnum.values()) {
					if (!Util.isEmpty(dataType) && dataType.equals(dataTypeEnum.intKey())) {
						applicant.put("dataType", dataTypeEnum.value());
					}
				}

				Integer expressType = (Integer) applicant.get("expressType");
				for (ExpressTypeEnum expressTypeEnum : ExpressTypeEnum.values()) {
					if (!Util.isEmpty(expressType) && expressType.equals(expressTypeEnum.intKey())) {
						applicant.put("expressType", expressTypeEnum.value());
					}
				}
			}
			record.put("everybodyInfo", records);
		}
		result.put("receptionJpData", list);
		return result;
	}
}
