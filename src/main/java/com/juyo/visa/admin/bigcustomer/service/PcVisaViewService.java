package com.juyo.visa.admin.bigcustomer.service;

import java.util.ArrayList;
import java.util.Collections;
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
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.bigcustomer.form.VisaListDataForm;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.entities.TAppStaffContactpointEntity;
import com.juyo.visa.entities.TAppStaffFamilyinfoEntity;
import com.juyo.visa.entities.TAppStaffPrevioustripinfoEntity;
import com.juyo.visa.entities.TAppStaffTravelcompanionEntity;
import com.juyo.visa.entities.TAppStaffWorkEducationTrainingEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TOrderUsEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class PcVisaViewService extends BaseService<TOrderUsEntity> {
	private static final Log log = Logs.get();

	/**
	 * 
	 * 办理证签证列表页
	 *
	 * @param form
	 * @param session
	 * @return
	 */
	public Object visaListData(VisaListDataForm form, HttpSession session) {
		//获取当前公司
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//获取当前用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		form.setUserid(loginUser.getId());
		form.setCompanyid(loginCompany.getId());
		form.setAdminId(loginCompany.getAdminId());

		Map<String, Object> result = Maps.newHashMap();
		List<Record> list = new ArrayList<>();

		//分页
		Sql sql = form.sql(sqlManager);
		Integer pageNumber = form.getPageNumber();
		Integer pageSize = form.getPageSize();
		Pager pager = new OffsetPager((pageNumber - 1) * pageSize, pageSize);
		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));
		sql.setPager(pager);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		//主sql数据 
		List<Record> orderList = (List<Record>) sql.getResult();
		for (Record order : orderList) {
			String orderid = order.getString("orderid");
			//获取该订单下的申请人
			String sqlStr = sqlManager.get(" bigCustomer_order_applicant_list");
			Sql applysql = Sqls.create(sqlStr);
			Cnd cnd = Cnd.NEW();
			cnd.and("tasou.orderid", "=", orderid);
			List<Record> applicantList = dbDao.query(applysql, cnd, null);
			order.put("everybodyInfo", applicantList);
			list.add(order);
		}

		//list前后倒置，这样第一个对象为最新的订单
		Collections.reverse(list);
		result.put("visaListData", list);

		return result;
	}

	/**
	 * 获取签证 旅伴信息
	 */
	public Object getStaffTravelCompanion(Integer staffid) {
		TAppStaffTravelcompanionEntity entity = dbDao.fetch(TAppStaffTravelcompanionEntity.class,
				Cnd.where("staffid", "=", staffid));
		return entity;
	}

	/**
	 * 获取签证 以前的美国旅游信息
	 */
	public Object getStaffpreviousTripInfo(Integer staffid) {
		TAppStaffPrevioustripinfoEntity entity = dbDao.fetch(TAppStaffPrevioustripinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		return entity;
	}

	/**
	 * 获取签证 美国联络点
	 */
	public Object getStaffContactPoint(Integer staffid) {
		TAppStaffContactpointEntity entity = dbDao.fetch(TAppStaffContactpointEntity.class,
				Cnd.where("staffid", "=", staffid));
		return entity;
	}

	/**
	 * 获取签证 家庭信息
	 */
	public Object getStaffFamilyInfo(Integer staffid) {
		TAppStaffFamilyinfoEntity entity = dbDao.fetch(TAppStaffFamilyinfoEntity.class,
				Cnd.where("staffid", "=", staffid));
		return entity;
	}

	/**
	 * 获取签证 工作/教育/培训信息
	 */
	public Object getStaffWorkEducationTraining(Integer staffid) {
		TAppStaffWorkEducationTrainingEntity entity = dbDao.fetch(TAppStaffWorkEducationTrainingEntity.class,
				Cnd.where("staffid", "=", staffid));
		return entity;
	}

}
