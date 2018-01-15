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
			/*AirLineInFo airLineInFo = new AirLineInFo();
			airLineInFo.setName(airmap.get("name"));
			airLineInFo.setDate(airmap.get("date"));
			airLineInFo.setAirmode(airmap.get("airmode"));
			airLineInFo.setDep(airmap.get("dep"));
			airLineInFo.setArr(airmap.get("arr"));
			airLineInFo.setCompany(airmap.get("company"));
			airLineInFo.setStatus(airmap.get("status"));
			//出发时间
			Date deptimedate = DateUtil.string2Date(airmap.get("dep_time"), DateUtil.FORMAT_FULL_PATTERN);
			airLineInFo.setDep_time(dateFormat.format(deptimedate));
			//抵达时间
			Date arrtimedate = DateUtil.string2Date(airmap.get("arr_time"), DateUtil.FORMAT_FULL_PATTERN);
			airLineInFo.setArr_time(dateFormat.format(arrtimedate));
			airLineInFo.setFly_time(airmap.get("fly_time"));
			airLineInFo.setDistance(airmap.get("distance"));
			airLineInFo.setPunctuality(airmap.get("punctuality"));
			airLineInFo.setDep_temperature(airmap.get("dep_temperature"));
			airLineInFo.setArr_temperature(airmap.get("arr_temperature"));
			airLineInFo.setEtd(airmap.get("etd"));
			airLineInFo.setEta(airmap.get("eta"));*/
			TFlightEntity flightEntity = new TFlightEntity();
			flightEntity.setAirlinecomp(airmap.get("airmode"));
			flightEntity.setFlightnum(airmap.get("name"));
			//机场
			String depair = airmap.get("dep");
			//起飞机场
			flightEntity.setTakeOffName(depair.substring(0, depair.indexOf("机场") + 2));
			flightEntity.setTakeOffCode(airLineParam.getDep());
			flightEntity.setLandingName(airmap.get("arr"));
			flightEntity.setLandingCode(airLineParam.getArr());
			flightEntity.setTakeOffCityId(param.getGocity().intValue());
			flightEntity.setLandingCityId(param.getArrivecity().intValue());
			Date deptimedate = DateUtil.string2Date(airmap.get("dep_time"), DateUtil.FORMAT_FULL_PATTERN);
			flightEntity.setTakeOffTime(dateFormat.format(deptimedate));
			Date arrtimedate = DateUtil.string2Date(airmap.get("arr_time"), DateUtil.FORMAT_FULL_PATTERN);
			flightEntity.setLandingTime(dateFormat.format(arrtimedate));
			flightEntity.setTakeOffTerminal(depair.substring(depair.indexOf("机场") + 2, depair.length()));
			//airLineResult.getAiliLineInFos().add(airLineInFo);
			airLineResult.getAiliLineInFos().add(flightEntity);
		}
		return airLineResult.getAiliLineInFos();
	}

	public static void main(String[] args) {
		String jichang = "上海浦东机场T1";
		String substring = jichang.substring(0, jichang.indexOf("机场") + 2);
		String subSequence = jichang.substring(jichang.indexOf("机场") + 2, jichang.length());
		System.out.println(substring);
		System.out.println(subSequence);
	}
}
