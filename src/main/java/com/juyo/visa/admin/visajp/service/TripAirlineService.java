/**
 * TripAirlineService.java
 * com.juyo.visa.admin.visajp.service
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.IocBean;

import com.juyo.visa.admin.visajp.form.FlightSelectParam;
import com.juyo.visa.common.haoservice.AirLineParam;
import com.juyo.visa.common.haoservice.AirLineResult;
import com.juyo.visa.common.haoservice.AirlineData;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.TFlightEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年1月11日 	 
 */
@IocBean
public class TripAirlineService extends BaseService<TFlightEntity> {

	public List<TFlightEntity> getTripAirlineSelect(FlightSelectParam param) {

		String dep = "";
		if (!Util.isEmpty(param.getGocity())) {
			TCityEntity gocity = dbDao.fetch(TCityEntity.class, param.getGocity());
			dep = gocity.getCode();
		}
		String arr = "";
		if (!Util.isEmpty(param.getArrivecity())) {
			TCityEntity arrivecity = dbDao.fetch(TCityEntity.class, param.getArrivecity());
			arr = arrivecity.getCode();
		}
		String date = "";
		if (!Util.isEmpty(param.getDate())) {
			Date string2Date = DateUtil.string2Date(param.getDate(), DateUtil.FORMAT_YYYY_MM_DD);
			DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYYMMDD);
			date = format.format(string2Date);
		}
		//设置参数
		AirLineParam airLineParam = new AirLineParam();
		airLineParam.setDep(dep);
		airLineParam.setArr(arr);
		airLineParam.setDate(date);
		//执行查询
		String airLinejson = AirlineData.getAirLineInfo(airLineParam);
		AirLineResult airLineResult = new AirLineResult();
		Map<String, Object> map = JsonUtil.fromJson(airLinejson, Map.class);
		//返回代码
		airLineResult.setError_code(map.get("error_code").toString());
		//返回错误信息
		airLineResult.setReason((String) map.get("reason"));
		List<LinkedHashMap<String, String>> fromJsonAsList = (List) map.get("result");
		DateFormat dateFormat = new SimpleDateFormat("HHmm");
		for (LinkedHashMap<String, String> airmap : fromJsonAsList) {
			TFlightEntity flightEntity = new TFlightEntity();
			//flightEntity.setAirlinecomp(airmap.get("airmode"));
			flightEntity.setFlightnum(airmap.get("flightNo"));
			//机场
			//String depair = airmap.get("dep");
			//起飞机场
			//			flightEntity.setTakeOffName(depair.substring(0, depair.indexOf("机场") + 2));
			flightEntity.setTakeOffName(airmap.get("OrgCity"));
			flightEntity.setTakeOffCode(airmap.get("OrgCity"));
			flightEntity.setLandingName(airmap.get("Dstcity"));
			flightEntity.setLandingCode(airmap.get("Dstcity"));
			flightEntity.setTakeOffCityId(param.getGocity().intValue());
			flightEntity.setLandingCityId(param.getArrivecity().intValue());
			//			Date deptimedate = DateUtil.string2Date(airmap.get("dep_time"), DateUtil.FORMAT_FULL_PATTERN);
			flightEntity.setTakeOffTime(airmap.get("DepTime"));
			//			Date arrtimedate = DateUtil.string2Date(airmap.get("arr_time"), DateUtil.FORMAT_FULL_PATTERN);
			flightEntity.setLandingTime(airmap.get("ArrTime")
					+ (Util.isEmpty(airmap.get("arrTimeModify")) ? "" : airmap.get("arrTimeModify")));
			//			flightEntity.setTakeOffTerminal(depair.substring(depair.indexOf("机场") + 2, depair.length()));
			//airLineResult.getAiliLineInFos().add(airLineInFo);
			airLineResult.getAiliLineInFos().add(flightEntity);
		}
		return airLineResult.getAiliLineInFos();
	}

	public static void main(String[] args) {
	}
}
