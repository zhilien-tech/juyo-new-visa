/**
 * JapanDijieService.java
 * com.juyo.visa.admin.dijie.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.dijie.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.IocBean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.juyo.visa.admin.dijie.form.DijieOrderListForm;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.simple.entity.StatisticsEntity;
import com.juyo.visa.common.enums.CompanyTypeEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.JpOrderSimpleEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.common.enums.SimpleVisaTypeEnum;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TCompanyOfCustomerEntity;
import com.juyo.visa.entities.TFlightEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderTripJpEntity;
import com.juyo.visa.entities.TOrderTripMultiJpEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年12月23日 	 
 */
@IocBean
public class JapanDijieService extends BaseService<TOrderEntity> {

	public Object toList(HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		JSONArray ja = new JSONArray();

		//送签社下拉
		if (loginCompany.getComType().equals(CompanyTypeEnum.SONGQIAN.intKey())
				|| loginCompany.getComType().equals(CompanyTypeEnum.SONGQIANSIMPLE.intKey())
				|| loginCompany.getComType().equals(CompanyTypeEnum.ORDERSIMPLE.intKey())) {
			//如果公司自己有指定番号，说明有送签资质，也需要出现在下拉中
			if (!Util.isEmpty(loginCompany.getCdesignNum())) {
				ja.add(loginCompany);
			}
			List<TCompanyOfCustomerEntity> list = dbDao.query(TCompanyOfCustomerEntity.class,
					Cnd.where("comid", "=", loginCompany.getId()), null);
			for (TCompanyOfCustomerEntity tCompanyOfCustomerEntity : list) {
				JSONObject jo = new JSONObject();
				Integer sendcomid = tCompanyOfCustomerEntity.getSendcomid();

				TCompanyEntity sendCompany = dbDao.fetch(TCompanyEntity.class,
						Cnd.where("id", "=", sendcomid).and("cdesignNum", "!=", ""));

				ja.add(sendCompany);
			}
		}
		List<TCompanyEntity> query = dbDao.query(
				TCompanyEntity.class,
				Cnd.where("comType", "=", CompanyTypeEnum.SONGQIANSIMPLE.intKey()).or("comType", "=",
						CompanyTypeEnum.ORDERSIMPLE.intKey()), null);
		result.put("songqianlist", query);

		//员工下拉
		/*Integer comid = loginCompany.getId();
		Integer adminId = loginCompany.getAdminId();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userType = loginUser.getUserType();//当前登录用户类型

		//查询拥有某个权限模块的工作人员
		String sqlstr = sqlManager.get("logs_user_select_list");
		Sql sql = Sqls.create(sqlstr);
		Cnd cnd = Cnd.NEW();
		cnd.and("tcf.comid", "=", comid);
		cnd.and("tu.id", "!=", adminId);
		for (JPOrderProcessTypeEnum typeEnum : JPOrderProcessTypeEnum.values()) {
			if (1 == typeEnum.intKey()) {
				cnd.and(" tf.funName", "=", typeEnum.value());
			}
		}
		List<Record> employees = dbDao.query(sql, cnd, null);
		Cnd usercnd = Cnd.NEW();
		usercnd.and("comId", "=", loginCompany.getId());
		if (!loginCompany.getAdminId().equals(loginUser.getId())) {
			usercnd.and("id", "!=", loginCompany.getAdminId());
		}
		if (loginCompany.getComType().equals(CompanyTypeEnum.SONGQIANSIMPLE.intKey())) {

			List<TUserEntity> companyuser = dbDao.query(TUserEntity.class, usercnd, null);
			List<Record> companyusers = Lists.newArrayList();
			for (TUserEntity tUserEntity : companyuser) {
				Record record = new Record();
				record.put("userid", tUserEntity.getId());
				record.put("username", tUserEntity.getName());
				companyusers.add(record);
			}
			employees = companyusers;
		}

		result.put("employees", employees);*/
		result.put("mainsalevisatypeenum", EnumUtil.enum2(SimpleVisaTypeEnum.class));
		result.put("orderstatus", EnumUtil.enum2(JpOrderSimpleEnum.class));
		return result;
	}

	/**
	 * 获取地接社列表数据
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param request
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object listData(HttpServletRequest request, DijieOrderListForm form) {
		long startTime = System.currentTimeMillis();
		Map<String, Object> result = Maps.newHashMap();
		HttpSession session = request.getSession();
		//获取当前公司
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//获取当前用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		form.setUserid(loginUser.getId());
		form.setCompanyid(loginCompany.getId());
		form.setAdminId(loginCompany.getAdminId());
		Sql sql = form.sql(sqlManager);

		Integer pageNumber = form.getPageNumber();
		Integer pageSize = form.getPageSize();

		Pager pager = new OffsetPager((pageNumber - 1) * pageSize, pageSize);
		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));

		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		//主sql数据
		List<Record> totallist = (List<Record>) sql.getResult();
		int orderscount = totallist.size();
		int peopletotal = 0;
		/*int disableorder = 0;
		int disablepeople = 0;*/
		int zhaobaoorder = 0;
		int zhaobaopeople = 0;
		for (Record record : totallist) {
			//作废单子、人数
			/*if (Util.eq(1, record.get("isdisabled"))) {
				disableorder++;
				if (!Util.eq(0, record.get("peoplenumber"))) {
					disablepeople += record.getInt("peoplenumber");
				}
			}*/

			//收费单子，人数
			if (Util.eq(1, record.get("zhaobaoupdate")) && Util.eq(0, record.get("isDisabled"))) {
				zhaobaoorder++;
				if (!Util.eq(0, record.get("peoplenumber"))) {
					zhaobaopeople += record.getInt("peoplenumber");
				}
			}

			//单子人数
			if (!Util.eq(0, record.get("peoplenumber"))) {
				peopletotal += record.getInt("peoplenumber");
			}
		}

		sql.setPager(pager);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);
		@SuppressWarnings("unchecked")
		//主sql数据
		List<Record> list = (List<Record>) sql.getResult();
		//添加申请人信息
		for (Record record : list) {
			//查询申请人信息
			Integer orderid = (Integer) record.get("id");
			String sqlStr = sqlManager.get("get_japan_dijie_list_apply_data");
			Sql applysql = Sqls.create(sqlStr);
			List<Record> query = dbDao.query(applysql, Cnd.where("taoj.orderId", "=", orderid), null);
			record.put("applyinfo", query);
			//订单状态
			Integer visastatus = record.getInt("japanState");
			for (JPOrderStatusEnum visaenum : JPOrderStatusEnum.values()) {
				if (visaenum.intKey() == visastatus) {
					record.put("visastatus", visaenum.value());
				}
			}
		}

		//查询单组单人
		List<Record> singleperson = getSingleperson(form);

		StatisticsEntity entity = new StatisticsEntity();
		/*entity.setDisableorder(disableorder);
				entity.setDisablepeople(disablepeople);*/
		entity.setOrderscount(orderscount);
		entity.setPeopletotal(peopletotal);
		entity.setZhaobaoorder(zhaobaoorder);
		entity.setZhaobaopeople(zhaobaopeople);
		entity.setSingleperson(singleperson.size());
		entity.setMultiplayer(zhaobaoorder - singleperson.size());
		result.put("entity", entity);

		result.put("pagetotal", pager.getPageCount());
		result.put("visaJapanData", list);
		long endTime = System.currentTimeMillis();
		System.out.println("方法所用时间为：" + (endTime - startTime) + "ms");
		return result;
	}

	/**
	 * 查询单人单组数据
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public List<Record> getSingleperson(DijieOrderListForm form) {
		long startTime = System.currentTimeMillis();
		String singlesqlStr = sqlManager.get("getSingleperson");
		Sql singlesql = Sqls.create(singlesqlStr);

		Cnd singlecnd = Cnd.NEW();
		if (!Util.isEmpty(form.getSongqianshe())) {
			singlecnd.and("tr.comId", "=", form.getSongqianshe());
		} /*else {
			singlecnd.and("toj.groundconnectid", "=", form.getCompanyid());
			}*/
		singlecnd.and("toj.groundconnectid", "=", form.getCompanyid());
		if (!Util.isEmpty(form.getSearchStr())) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tr.orderNum", "like", "%" + form.getSearchStr() + "%")
					.or("tc.linkman", "like", "%" + form.getSearchStr() + "%")
					.or("tc.mobile", "like", "%" + form.getSearchStr() + "%")
					.or("tc.email", "like", "%" + form.getSearchStr() + "%")
					//.or("taj.applyname", "like", "%" + form.getSearchStr() + "%")
					.or("toj.acceptDesign", "like", "%" + form.getSearchStr() + "%")
					//.or("taj.passport", "like", "%" + form.getSearchStr() + "%")
					.or("(SELECT GROUP_CONCAT(CONCAT(ta.firstName,ta.lastName) SEPARATOR 'төл') applyname FROM t_applicant ta INNER JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id LEFT JOIN t_order_jp toj ON taoj.orderId = toj.id LEFT JOIN t_order tor ON toj.orderId = tor.id WHERE tor.id = tr.id GROUP BY toj.orderId)",
							"like", "%" + form.getSearchStr() + "%")
					.or("(SELECT tap.passport FROM t_applicant ta INNER JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id LEFT JOIN t_order_jp toj ON taoj.orderId = toj.id LEFT JOIN t_order tor ON toj.orderId = tor.id WHERE tor.id = tr.id GROUP BY toj.orderId)",
							"like", "%" + form.getSearchStr() + "%");
			singlecnd.and(exp);
		}

		if (!Util.isEmpty(form.getSendstartdate()) && !Util.isEmpty(form.getSendenddate())) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tr.sendVisaDate", "between", new Object[] { form.getSendstartdate(), form.getSendenddate() });
			singlecnd.and(exp);
		}

		if (!Util.isEmpty(form.getOrderstartdate()) && !Util.isEmpty(form.getOrderenddate())) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tr.createTime", "between", new Object[] { form.getOrderstartdate(), form.getOrderenddate() });
			singlecnd.and(exp);
		}
		if (!Util.isEmpty(form.getStatus())) {
			if (Util.eq(form.getStatus(), JPOrderStatusEnum.DISABLED.intKey())) {
				singlecnd.and("tr.isDisabled", "=", IsYesOrNoEnum.YES.intKey());
			} else if (Util.eq(form.getStatus(), 22)) {
				SqlExpressionGroup e1 = Cnd.exps("tr.status", "=", form.getStatus()).or("tr.status", "=", 35)
						.and("tr.isDisabled", "=", IsYesOrNoEnum.NO.intKey());
				singlecnd.and(e1);
			} else if (Util.eq(19, form.getStatus())) {
				SqlExpressionGroup e1 = Cnd.exps("tr.status", "=", form.getStatus()).or("tr.status", "=", 34)
						.and("tr.isDisabled", "=", IsYesOrNoEnum.NO.intKey());
				singlecnd.and(e1);
			} else {
				SqlExpressionGroup e1 = Cnd.exps("tr.status", "=", form.getStatus()).and("tr.isDisabled", "=",
						IsYesOrNoEnum.NO.intKey());
				singlecnd.and(e1);
			}
		}

		/*if (!Util.isEmpty(form.getSongqianshe())) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("toj.sendsignid", "=", form.getSongqianshe());
			singlecnd.and(exp);
		}*/

		if (!Util.isEmpty(form.getVisatype())) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("toj.visatype", "=", form.getVisatype());
			singlecnd.and(exp);
		}
		singlecnd.and("tr.zhaobaoupdate", "=", 1);
		singlecnd.groupBy("tr.orderNum").having(Cnd.wrap("ct = 1"));
		//singlecnd.orderBy("tr.isDisabled", "ASC");
		//singlecnd.orderBy("tr.updatetime", "desc");

		singlesql.setCondition(singlecnd);
		List<Record> singleperson = dbDao.query(singlesql, singlecnd, null);
		long endTime = System.currentTimeMillis();
		System.out.println("查询单组单人所用时间为：" + (endTime - startTime) + "ms");
		return singleperson;
	}

	public Object orderdetail(Integer orderid, HttpServletRequest request) {

		Map<String, Object> result = Maps.newHashMap();
		//日本订单数据
		TOrderJpEntity jporderinfo = dbDao.fetch(TOrderJpEntity.class, orderid.longValue());
		result.put("jporderinfo", jporderinfo);
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, jporderinfo.getOrderId().longValue());
		result.put("orderinfo", orderinfo);
		//订单id
		result.put("orderid", orderid);
		//签证类型
		result.put("mainsalevisatypeenum", EnumUtil.enum2(MainSaleVisaTypeEnum.class));
		//是否
		result.put("isyesornoenum", EnumUtil.enum2(IsYesOrNoEnum.class));
		//出行信息
		TOrderTripJpEntity travelinfo = dbDao.fetch(TOrderTripJpEntity.class, Cnd.where("orderId", "=", orderid));
		List<TOrderTripMultiJpEntity> multitrip = new ArrayList<TOrderTripMultiJpEntity>();
		if (Util.isEmpty(travelinfo)) {
			travelinfo = new TOrderTripJpEntity();
			travelinfo.setGoDate(orderinfo.getGoTripDate());
			travelinfo.setReturnDate(orderinfo.getBackTripDate());
			travelinfo.setTripType(1);
		} else {
			if (travelinfo.getTripType().equals(2)) {
				multitrip = dbDao.query(TOrderTripMultiJpEntity.class, Cnd.where("tripid", "=", travelinfo.getId()),
						null);
			} else if (travelinfo.getTripType().equals(1)) {
				//去程出发城市
				TCityEntity goleavecity = new TCityEntity();
				if (!Util.isEmpty(travelinfo.getGoDepartureCity())) {
					goleavecity = dbDao.fetch(TCityEntity.class, travelinfo.getGoDepartureCity().longValue());
				}
				result.put("goleavecity", goleavecity);
				//去程抵达城市
				TCityEntity goarrivecity = new TCityEntity();
				if (!Util.isEmpty(travelinfo.getGoArrivedCity())) {
					goarrivecity = dbDao.fetch(TCityEntity.class, travelinfo.getGoArrivedCity().longValue());
				}
				result.put("goarrivecity", goarrivecity);
				//回程出发城市
				TCityEntity backleavecity = new TCityEntity();
				if (!Util.isEmpty(travelinfo.getReturnDepartureCity())) {
					backleavecity = dbDao.fetch(TCityEntity.class, travelinfo.getReturnDepartureCity().longValue());
				}
				result.put("backleavecity", backleavecity);
				//回程返回城市
				TCityEntity backarrivecity = new TCityEntity();
				if (!Util.isEmpty(travelinfo.getReturnArrivedCity())) {
					backarrivecity = dbDao.fetch(TCityEntity.class, travelinfo.getReturnArrivedCity().longValue());
				}
				result.put("backarrivecity", backarrivecity);
				//去程航班
				TFlightEntity goflightnum = new TFlightEntity();
				if (!Util.isEmpty(travelinfo.getGoFlightNum())) {
					goflightnum = dbDao.fetch(TFlightEntity.class,
							Cnd.where("flightnum", "=", travelinfo.getGoFlightNum()));
				}
				result.put("goflightnum", goflightnum);
				//回程航班
				TFlightEntity returnflightnum = new TFlightEntity();
				if (!Util.isEmpty(travelinfo.getGoFlightNum())) {
					returnflightnum = dbDao.fetch(TFlightEntity.class,
							Cnd.where("flightnum", "=", travelinfo.getReturnFlightNum()));
				}
				result.put("returnflightnum", returnflightnum);
			}
		}
		result.put("travelinfo", travelinfo);
		//多程城市
		Integer[] mulcityids = new Integer[multitrip.size() * 2];
		int citycount = 0;
		for (TOrderTripMultiJpEntity tripMultiJp : multitrip) {
			mulcityids[citycount] = !Util.isEmpty(tripMultiJp.getDepartureCity()) ? tripMultiJp.getDepartureCity()
					: null;
			mulcityids[citycount + 1] = !Util.isEmpty(tripMultiJp.getArrivedCity()) ? tripMultiJp.getArrivedCity()
					: null;
			citycount += 2;
		}
		List<TCityEntity> citys = dbDao.query(TCityEntity.class, Cnd.where("id", "in", mulcityids), null);
		result.put("citys", citys);
		//多程航班号
		String[] mulflightids = new String[multitrip.size()];
		int flightcount = 0;
		for (TOrderTripMultiJpEntity tripMultiJp : multitrip) {
			mulflightids[flightcount] = !Util.isEmpty(tripMultiJp.getFlightNum()) ? tripMultiJp.getFlightNum() : "";
			flightcount++;
		}
		List<TFlightEntity> flights = dbDao
				.query(TFlightEntity.class, Cnd.where("flightnum", "in", mulflightids), null);
		//补全三个航程
		if (multitrip.size() < 3) {
			int multitripsize = multitrip.size();
			for (int i = 0; i < 3 - multitripsize; i++) {
				multitrip.add(new TOrderTripMultiJpEntity());
			}
		}
		result.put("flights", flights);
		result.put("multitrip", multitrip);
		result.put("multitripjson", JsonUtil.toJson(multitrip));
		return result;

	}

	public Object sendZhaoBao(HttpServletRequest request, Long orderid) {
		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Integer adminId = loginCompany.getAdminId();
		JSONArray ja = new JSONArray();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("orderid", orderid);
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid);
		TCompanyEntity fetch = dbDao.fetch(TCompanyEntity.class, orderjp.getSendsignid().longValue());
		ja.add(fetch);
		result.put("orderjpinfo", orderjp);
		result.put("songqianlist", ja);
		//地接社下拉
		/*List<TCompanyEntity> dijielist = dbDao.query(TCompanyEntity.class,
				Cnd.where("comType", "=", CompanyTypeEnum.DIJI.intKey()).and("name", "like", "株式会社金通商社"), null);*/
		List<TCompanyEntity> dijielist = dbDao.query(TCompanyEntity.class,
				Cnd.where("comType", "=", CompanyTypeEnum.DIJI.intKey()), null);
		result.put("dijielist", dijielist);
		return result;
	}

}
