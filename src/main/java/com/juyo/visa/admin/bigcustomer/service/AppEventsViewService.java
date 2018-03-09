package com.juyo.visa.admin.bigcustomer.service;

import java.util.ArrayList;
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
import org.nutz.mvc.annotation.Param;

import com.beust.jcommander.internal.Maps;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.enums.visaProcess.VisaCountryEnum;
import com.juyo.visa.common.enums.visaProcess.VisaProcess_US_Enum;
import com.juyo.visa.entities.TAppEventsIntroduceEntity;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TAppStaffEventsEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TAppEventsForm;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.JsonUtil;
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
		Integer comId = loginCompany.getId();

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
			String sqlStr = sqlManager.get("appevents_detail_by_eventId");
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

	/**
	 * 
	 * 报名活动
	 *
	 * @param eventId 活动id
	 * @param session 
	 * @return 
	 */
	public Object signUpEvents(@Param("eventId") Integer eventId, HttpSession session) {
		//当前登录用户Id
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer loginUserId = loginUser.getId();

		if (!Util.isEmpty(eventId)) {
			TAppStaffEventsEntity ase = new TAppStaffEventsEntity();
			ase.setEventsId(eventId);
			ase.setStaffId(loginUserId);
			dbDao.insert(ase);
			return JsonUtil.toJson("报名成功");
		} else {
			return JsonUtil.toJson("报名失败");
		}
	}

	/**
	 * 
	 * 获取已报名活动的人员信息
	 *
	 * @param eventId
	 * @return 
	 */
	public Object getStaffInfoByEventId(@Param("eventId") Integer eventId) {
		Record record = new Record();
		if (!Util.isEmpty(eventId)) {
			//活动详情
			String sqlStr = sqlManager.get("appevents_staffs_infos_by_eventId");
			Sql sql = Sqls.create(sqlStr);
			sql.setParam("eventId", eventId);
			record = dbDao.fetch(sql);
		}

		return record;
	}

	/**
	 * 
	 * 签证办理流程 
	 *
	 * @param visaCountry 签证国
	 * @return 
	 */
	public Object getVisaProcessByCountry(@Param("visaCountry") Integer visaCountry) {

		Map<String, String> map = Maps.newHashMap();
		if (Util.eq(VisaCountryEnum.USA.intKey(), visaCountry)) {
			//美国签证流程
			map = EnumUtil.enum2(VisaProcess_US_Enum.class);
		} else if (Util.eq(VisaCountryEnum.JAPAN.intKey(), visaCountry)) {
			//日本签证流程 TODO

		}
		return map;
	}

	/**
	 * 
	 * 根据人员id，获取相关活动的进度列表
	 * <p>
	 * TODO *************************重要***********************
	 * 		1.进度页列表，签证办理状态
	 * 		2.活动相关报名人员查询
	 * 		3.报名活动 和 订单关联，需要获取订单相关信息（比如 订单号、订单状态、订单申请人等）
	 *
	 * @param staffId 人员Id
	 * @return 
	 */
	public Object getProcessListByStaffId(@Param("staffId") Integer staffId) {
		List<Record> records = new ArrayList<Record>();
		if (!Util.isEmpty(staffId)) {
			String sqlStr = sqlManager.get("appevents_process_list_by_staffId");
			Sql sql = Sqls.create(sqlStr);
			sql.setParam("staffId", staffId);
			records = dbDao.query(sql, null, null);
			for (Record record : records) {
				//签证国家
				int visacountry = record.getInt("visaCountry");
				for (VisaCountryEnum country : VisaCountryEnum.values()) {
					if (visacountry == country.intKey()) {
						record.set("visaCountry", country.value());
						break;
					}
				}
			}

		}
		return records;
	}

	/**
	 * 
	 * 查看报名活动的进度详情
	 * <p>
	 * TODO	************************待完善******************************
	 * 		1.根据订单id，查询当前订单状态----签证进度状态
	 * 		2.查询签证进度流程
	 * 
	 * @param visaCountry 签证国家
	 * @param orderId 订单id
	 * @return 
	 */
	public Object getMyProcessDetails(@Param("visaCountry") Integer visaCountry, @Param("orderId") Integer orderId) {
		Map<String, String> map = Maps.newHashMap();
		if (Util.eq(VisaCountryEnum.USA.intKey(), visaCountry)) {
			//美国签证流程
			map = EnumUtil.enum2(VisaProcess_US_Enum.class);
		} else if (Util.eq(VisaCountryEnum.JAPAN.intKey(), visaCountry)) {
			//日本签证流程 TODO

		}

		//TODO 获取订单状态

		//TODO 进度页渲染操作

		return map;
	}

}