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
import java.util.Map.Entry;
import java.util.Set;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Lists;
import com.juyo.visa.admin.visajp.form.FlightSelectParam;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.haoservice.AirLineParam;
import com.juyo.visa.common.haoservice.AirLineResult;
import com.juyo.visa.common.haoservice.AirlineData;
import com.juyo.visa.entities.TAirportInfoEntity;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.TFlightEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.redis.RedisDao;
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

	@Inject
	private RedisDao redisDao;

	public List<TFlightEntity> getTripAirlineSelect(FlightSelectParam param) {
		List<TFlightEntity> result = Lists.newArrayList();
		String dep = "";
		if (!Util.isEmpty(param.getGocity())) {
			TCityEntity gocity = dbDao.fetch(TCityEntity.class, param.getGocity());
			dep = gocity.getCode();
		} else {
			return result;
		}
		String arr = "";
		if (!Util.isEmpty(param.getArrivecity())) {
			TCityEntity arrivecity = dbDao.fetch(TCityEntity.class, param.getArrivecity());
			arr = arrivecity.getCode();
		} else {
			return result;
		}
		String date = "";
		if (!Util.isEmpty(param.getDate())) {
			Date string2Date = DateUtil.string2Date(param.getDate(), DateUtil.FORMAT_YYYY_MM_DD);
			DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYYMMDD);
			date = format.format(string2Date);
		} else {
			return result;
		}
		AirLineResult airLineResult = new AirLineResult();
		//查询缓存
		String flightinfo = redisDao.hget(CommonConstants.AIRLINE_INFO_KEY, dep + arr);
		String searchstr = param.getFlight().trim();
		//缓存中查询航班的数据
		List<TFlightEntity> flightlists = Lists.newArrayList();
		if (Util.isEmpty(flightinfo)) {
			//如果缓存中不存在数据则查询数据库
			flightlists = dbDao
					.query(TFlightEntity.class,
							Cnd.where("takeOffCityId", "=", param.getGocity()).and("landingCityId", "=",
									param.getArrivecity()), null);
		} else {
			//转换为list
			flightlists = JsonUtil.fromJsonAsList(TFlightEntity.class, flightinfo);
		}
		if (!Util.isEmpty(searchstr)) {
			searchstr = searchstr.toUpperCase();
			List<TFlightEntity> resultlist = Lists.newArrayList();
			for (TFlightEntity flight : flightlists) {
				if (flight.getFlightnum().indexOf(searchstr) > 0) {
					resultlist.add(flight);
				}
			}
			airLineResult.setAiliLineInFos(resultlist);
		} else {
			airLineResult.setAiliLineInFos(flightlists);
		}
		//设置参数
		return airLineResult.getAiliLineInFos();
	}

	/**
	 * 获取机场名称
	 */
	private String getAirportName(String aircode) {
		String result = "";
		if (!Util.isEmpty(aircode)) {
			//从缓存中取数据
			String airname = redisDao.hget(CommonConstants.AIRPORT_CODE_NAME, aircode);
			if (Util.isEmpty(airname)) {
				//从数据库查出来放到缓存
				TAirportInfoEntity airportinfo = dbDao.fetch(TAirportInfoEntity.class, Cnd.where("code", "=", aircode));
				if (!Util.isEmpty(airportinfo)) {
					result = airportinfo.getName();
					redisDao.hset(CommonConstants.AIRPORT_CODE_NAME, aircode, airportinfo.getName());
				}
			} else {
				result = airname;
			}
		}
		return result;
	}

	//清除缓存的航班
	public void deleteAielineCache() {
		Map<String, String> hgetAll = redisDao.hgetAll(CommonConstants.AIRLINE_INFO_KEY);
		Set<Entry<String, String>> entrySet = hgetAll.entrySet();
		for (Entry<String, String> entry : entrySet) {
			redisDao.hdel(CommonConstants.AIRLINE_INFO_KEY, entry.getKey());
		}
	}

	/**
	 * 将缓存中的数据写入到数据库
	 */
	public void updateFlightByCache(Integer depid, Integer arrid) {
		String dep = "";
		if (!Util.isEmpty(depid)) {
			TCityEntity depcity = dbDao.fetch(TCityEntity.class, depid.longValue());
			dep = depcity.getCode();
		}
		String arr = "";
		if (!Util.isEmpty(arrid)) {
			TCityEntity arrcity = dbDao.fetch(TCityEntity.class, arrid.longValue());
			arr = arrcity.getCode();
		}
		List<TFlightEntity> before = dbDao.query(TFlightEntity.class,
				Cnd.where("takeOffCityId", "=", depid).and("landingCityId", "=", arrid), null);
		if (!Util.isEmpty(dep) && !Util.isEmpty(arr)) {
			//从缓存中取数据
			String interfaceairinfo = redisDao.hget(CommonConstants.AIRLINE_INFO_KEY, dep + arr);
			//如果缓存中数据存在
			if (!Util.isEmpty(interfaceairinfo)) {
				List<TFlightEntity> flightlist = JsonUtil.fromJsonAsList(TFlightEntity.class, interfaceairinfo);
				if (!Util.isEmpty(flightlist) && flightlist.size() > 0) {
					dbDao.updateRelations(before, flightlist);
				}
			}
		}
	}

	/**
	 * 
	 * 从接口查询数据并放到缓存并同步到数据库
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param param
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getAirLineByInterfate(FlightSelectParam param) {
		List<TFlightEntity> result = Lists.newArrayList();
		String dep = "";
		if (!Util.isEmpty(param.getGocity())) {
			TCityEntity gocity = dbDao.fetch(TCityEntity.class, param.getGocity());
			dep = gocity.getCode();
		} else {
			return result;
		}
		String arr = "";
		if (!Util.isEmpty(param.getArrivecity())) {
			TCityEntity arrivecity = dbDao.fetch(TCityEntity.class, param.getArrivecity());
			arr = arrivecity.getCode();
		} else {
			return result;
		}
		String date = "";
		if (!Util.isEmpty(param.getDate())) {
			Date string2Date = DateUtil.string2Date(param.getDate(), DateUtil.FORMAT_YYYY_MM_DD);
			DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYYMMDD);
			date = format.format(string2Date);
		} else {
			return result;
		}
		AirLineResult airLineResult = new AirLineResult();
		//查询缓存
		String flightinfo = redisDao.hget(CommonConstants.AIRLINE_INFO_KEY, dep + arr);
		if (Util.isEmpty(flightinfo)) {
			//查询接口
			AirLineParam airLineParam = new AirLineParam();
			airLineParam.setDep(dep);
			airLineParam.setArr(arr);
			airLineParam.setDate(date);
			//执行查询
			String airLinejson = AirlineData.getAirLineInfo(airLineParam);
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
				//起飞机场三字代码
				String OrgCity = airmap.get("OrgCity");
				flightEntity.setTakeOffName(getAirportName(OrgCity));
				flightEntity.setTakeOffCode(OrgCity);
				//降落机场三字代码
				String Dstcity = airmap.get("Dstcity");
				flightEntity.setLandingName(getAirportName(Dstcity));
				flightEntity.setLandingCode(Dstcity);
				//起飞城市
				flightEntity.setTakeOffCityId(param.getGocity().intValue());
				//降落城市
				flightEntity.setLandingCityId(param.getArrivecity().intValue());
				flightEntity.setTakeOffTime(airmap.get("DepTime"));
				flightEntity.setLandingTime(airmap.get("ArrTime")
						+ (Util.isEmpty(airmap.get("arrTimeModify")) ? "" : airmap.get("arrTimeModify")));
				airLineResult.getAiliLineInFos().add(flightEntity);
			}
			//放入缓存
			redisDao.hset(CommonConstants.AIRLINE_INFO_KEY, dep + arr,
					JsonUtil.toJson(airLineResult.getAiliLineInFos()));
			//同步到数据库
			updateFlightByCache(param.getGocity().intValue(), param.getArrivecity().intValue());
		}
		//设置参数
		return null;
	}
}
