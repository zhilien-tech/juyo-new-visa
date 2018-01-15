/**
 * JapanDijieService.java
 * com.juyo.visa.admin.dijie.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.dijie.service;

import java.util.ArrayList;
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
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.dijie.form.DijieOrderListForm;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.TCompanyEntity;
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
		result.put("pagetotal", pager.getPageCount());
		result.put("visaJapanData", list);
		return result;
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
					goflightnum = dbDao.fetch(TFlightEntity.class, travelinfo.getGoFlightNum().longValue());
				}
				result.put("goflightnum", goflightnum);
				//回程航班
				TFlightEntity returnflightnum = new TFlightEntity();
				if (!Util.isEmpty(travelinfo.getGoFlightNum())) {
					returnflightnum = dbDao.fetch(TFlightEntity.class, travelinfo.getReturnFlightNum().longValue());
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
		Integer[] mulflightids = new Integer[multitrip.size()];
		int flightcount = 0;
		for (TOrderTripMultiJpEntity tripMultiJp : multitrip) {
			mulflightids[flightcount] = !Util.isEmpty(tripMultiJp.getFlightNum()) ? tripMultiJp.getFlightNum() : null;
			flightcount++;
		}
		List<TFlightEntity> flights = dbDao.query(TFlightEntity.class, Cnd.where("id", "in", mulflightids), null);
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

}
