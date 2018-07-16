/**
 * TripAirlineService.java
 * com.juyo.visa.admin.visajp.service
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.visajp.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Lists;
import com.juyo.visa.admin.visajp.form.FlightSelectParam;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.haoservice.AirLineParam;
import com.juyo.visa.common.haoservice.AirLineResult;
import com.juyo.visa.common.haoservice.AirlineData;
import com.juyo.visa.common.newairline.NewAirLineResult;
import com.juyo.visa.common.newairline.ResultflyEntity;
import com.juyo.visa.entities.TAirportInfoEntity;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.TFlightEntity;
import com.juyo.visa.entities.TFlightHisEntity;
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

	public List<ResultflyEntity> getTripAirlineSelect(FlightSelectParam param) {
		long startTime = System.currentTimeMillis();//获取当前时间
		List<ResultflyEntity> result = Lists.newArrayList();
		NewAirLineResult newairLineResult = new NewAirLineResult();

		String dep = "";
		if (!Util.isEmpty(param.getGocity())) {
			TCityEntity gocity = dbDao.fetch(TCityEntity.class, param.getGocity());
			//如果有关联城市，则需要按照顺序把所有关联城市的航班都查出来
			if (!Util.isEmpty(gocity.getRelationcity())) {
				dep = gocity.getRelationcity();
			} else {
				dep = gocity.getCode();
			}
		} else {
			return result;
		}
		String arr = "";
		if (!Util.isEmpty(param.getArrivecity())) {
			TCityEntity arrivecity = dbDao.fetch(TCityEntity.class, param.getArrivecity());
			if (!Util.isEmpty(arrivecity.getRelationcity())) {
				arr = arrivecity.getRelationcity();
			} else {
				arr = arrivecity.getCode();
			}
		} else {
			return result;
		}
		String date = "";
		if (!Util.isEmpty(param.getDate())) {
			Date string2Date = DateUtil.string2Date(param.getDate(), DateUtil.FORMAT_YYYY_MM_DD);
			DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYYMMDD);
			date = format.format(string2Date);
		}
		/*else {
			return result;
		}*/
		String contains = "ABCDEFGHIGKLMNOPQRSTUVWXYZ";

		//查询缓存
		String flightinfo = redisDao.hget(CommonConstants.AIRLINE_INFO_KEY,
				String.valueOf(param.getGocity()) + String.valueOf(param.getArrivecity()));
		//查询相关航班
		if (!Util.isEmpty(arr) && !arr.contains(",") && !Util.isEmpty(dep) && !dep.contains(",")) {
			String searchstr = param.getFlight().trim();
			List<ResultflyEntity> arrayList = Lists.newArrayList();
			if (Util.isEmpty(flightinfo)) {

				//直飞航班
				String sqlString = sqlManager.get("tripairline_airlines");
				Sql sql = Sqls.create(sqlString);
				Cnd appcnd = Cnd.NEW();
				appcnd.and("tf.takeOffCode", "=", dep);
				appcnd.and("tf.landingCode", "=", arr);
				sql.setCondition(appcnd);
				List<Record> depcitys = dbDao.query(sql, null, null);
				for (Record record : depcitys) {
					ResultflyEntity resultflyEntity = new ResultflyEntity();
					resultflyEntity.setFlightnum(((String) record.get("flightnum")).replace(" ", "").replace("*", ""));
					resultflyEntity.setArrflightname(((String) record.get("landingname")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setGoflightname(((String) record.get("takeoffname")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setTakeofftime(((String) record.get("takeofftime")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setLandingofftime(((String) record.get("landingtime")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setZhuanflightname("a");
					if (!arrayList.contains(resultflyEntity)) {
						arrayList.add(resultflyEntity);
					}
				}

				//转飞航班
				String sqlString2 = sqlManager.get("tripairline_airlines_zhuan");
				Sql sql2 = Sqls.create(sqlString2);
				Cnd appcnd2 = Cnd.NEW();
				appcnd2.and("tf.takeOffCode", "=", dep);
				appcnd2.and("tf2.landingCode", "=", arr);
				sql2.setCondition(appcnd2);
				List<Record> arrcitys = dbDao.query(sql2, null, null);

				for (Record record : arrcitys) {
					ResultflyEntity resultflyEntity = new ResultflyEntity();
					resultflyEntity.setArrflightname(((String) record.get("tflandingname")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity
							.setFlightnum(((String) record.get("tfflightnum")).replace(" ", "").replace("*", ""));
					resultflyEntity.setGoflightname(((String) record.get("tftakeoffname")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setLandingofftime(((String) record.get("tflandingtime")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setTakeofftime(((String) record.get("tftakeofftime")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setZhuanflightname(((String) record.get("tflandingname2")).replace(" ", "")
							.replace("*", ""));
					resultflyEntity.setZhuanflightnum(((String) record.get("tfflightnum2")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setZhuanlandingofftime(((String) record.get("tflandingtime2")).replace(" ", "")
							.replace("*", ""));
					resultflyEntity.setZhuantakeofftime(((String) record.get("tftakeofftime2")).replace(" ", "")
							.replace("*", ""));
					if (!arrayList.contains(resultflyEntity)) {
						arrayList.add(resultflyEntity);
					}
				}
				newairLineResult.setResultflyEntity(arrayList);
			} else {
				//转换为list
				arrayList = JsonUtil.fromJsonAsList(ResultflyEntity.class, flightinfo);
			}
			if (!Util.isEmpty(searchstr)) {
				searchstr = searchstr.toUpperCase();
				List<ResultflyEntity> resultlist = Lists.newArrayList();
				for (ResultflyEntity flight : arrayList) {
					if (flight.getFlightnum().indexOf(searchstr) != -1) {
						if (!resultlist.contains(flight)) {
							resultlist.add(flight);
						}
					}
					if (!Util.isEmpty(flight.getZhuanflightnum())
							&& flight.getZhuanflightnum().indexOf(searchstr) != -1) {
						if (!resultlist.contains(flight)) {
							resultlist.add(flight);
						}
					}
				}
				newairLineResult.setResultflyEntity(resultlist);
			} else {
				newairLineResult.setResultflyEntity(arrayList);
			}
		}
		if (!Util.isEmpty(arr) && arr.contains(",") && !Util.isEmpty(dep) && !dep.contains(",")) {
			//抵达有关联航班,这时arr是由多个id组成的字符串
			List<ResultflyEntity> arrayList = Lists.newArrayList();
			String searchstr = param.getFlight().trim();
			if (Util.isEmpty(flightinfo)) {

				//直飞航班
				String sqlString = sqlManager.get("tripairline_airlines");
				Sql sql = Sqls.create(sqlString);
				Cnd appcnd = Cnd.NEW();
				appcnd.and("tf.takeOffCode", "=", dep);
				appcnd.and("tf.landingCityId", "in", arr);
				sql.setCondition(appcnd);
				List<Record> depcitys = dbDao.query(sql, null, null);
				for (Record record : depcitys) {
					ResultflyEntity resultflyEntity = new ResultflyEntity();
					resultflyEntity.setFlightnum(((String) record.get("flightnum")).replace(" ", "").replace("*", ""));
					resultflyEntity.setArrflightname(((String) record.get("landingname")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setGoflightname(((String) record.get("takeoffname")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setTakeofftime(((String) record.get("takeofftime")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setLandingofftime(((String) record.get("landingtime")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setZhuanflightname("a");
					if (!arrayList.contains(resultflyEntity)) {
						arrayList.add(resultflyEntity);
					}
				}

				//转飞航班
				String sqlString2 = sqlManager.get("tripairline_airlines_zhuan");
				Sql sql2 = Sqls.create(sqlString2);
				Cnd appcnd2 = Cnd.NEW();
				appcnd2.and("tf.takeOffCode", "=", dep);
				appcnd2.and("tf2.landingCityId", "in", arr);
				sql2.setCondition(appcnd2);
				List<Record> arrcitys = dbDao.query(sql2, null, null);

				for (Record record : arrcitys) {
					ResultflyEntity resultflyEntity = new ResultflyEntity();
					resultflyEntity.setArrflightname(((String) record.get("tflandingname")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity
							.setFlightnum(((String) record.get("tfflightnum")).replace(" ", "").replace("*", ""));
					resultflyEntity.setGoflightname(((String) record.get("tftakeoffname")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setLandingofftime(((String) record.get("tflandingtime")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setTakeofftime(((String) record.get("tftakeofftime")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setZhuanflightname(((String) record.get("tflandingname2")).replace(" ", "")
							.replace("*", ""));
					resultflyEntity.setZhuanflightnum(((String) record.get("tfflightnum2")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setZhuanlandingofftime(((String) record.get("tflandingtime2")).replace(" ", "")
							.replace("*", ""));
					resultflyEntity.setZhuantakeofftime(((String) record.get("tftakeofftime2")).replace(" ", "")
							.replace("*", ""));
					if (!arrayList.contains(resultflyEntity)) {
						arrayList.add(resultflyEntity);
					}
				}
				newairLineResult.setResultflyEntity(arrayList);
			} else {
				//转换为list
				arrayList = JsonUtil.fromJsonAsList(ResultflyEntity.class, flightinfo);
			}
			if (!Util.isEmpty(searchstr)) {
				searchstr = searchstr.toUpperCase();
				List<ResultflyEntity> resultlist = Lists.newArrayList();
				for (ResultflyEntity flight : arrayList) {
					if (flight.getFlightnum().indexOf(searchstr) != -1) {
						if (!resultlist.contains(flight)) {
							resultlist.add(flight);
						}
					}
					if (!Util.isEmpty(flight.getZhuanflightnum())
							&& flight.getZhuanflightnum().indexOf(searchstr) != -1) {
						if (!resultlist.contains(flight)) {
							resultlist.add(flight);
						}
					}
				}
				newairLineResult.setResultflyEntity(resultlist);
			} else {
				newairLineResult.setResultflyEntity(arrayList);
			}
		}
		if (!Util.isEmpty(arr) && !arr.contains(",") && !Util.isEmpty(dep) && dep.contains(",")) {
			List<ResultflyEntity> arrayList = Lists.newArrayList();
			String searchstr = param.getFlight().trim();
			if (Util.isEmpty(flightinfo)) {

				//直飞航班
				String sqlString = sqlManager.get("tripairline_airlines");
				Sql sql = Sqls.create(sqlString);
				Cnd appcnd = Cnd.NEW();
				appcnd.and("tf.takeOffCityId", "in", dep);
				appcnd.and("tf.landingCode", "=", arr);
				sql.setCondition(appcnd);
				List<Record> depcitys = dbDao.query(sql, null, null);
				for (Record record : depcitys) {
					ResultflyEntity resultflyEntity = new ResultflyEntity();
					resultflyEntity.setFlightnum(((String) record.get("flightnum")).replace(" ", "").replace("*", ""));
					resultflyEntity.setArrflightname(((String) record.get("landingname")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setGoflightname(((String) record.get("takeoffname")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setTakeofftime(((String) record.get("takeofftime")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setLandingofftime(((String) record.get("landingtime")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setZhuanflightname("a");
					if (!arrayList.contains(resultflyEntity)) {
						arrayList.add(resultflyEntity);
					}
				}

				//转飞航班
				String sqlString2 = sqlManager.get("tripairline_airlines_zhuan");
				Sql sql2 = Sqls.create(sqlString2);
				Cnd appcnd2 = Cnd.NEW();
				appcnd2.and("tf.takeOffCityId", "in", dep);
				appcnd2.and("tf2.landingCode", "=", arr);
				sql2.setCondition(appcnd2);
				List<Record> arrcitys = dbDao.query(sql2, null, null);

				for (Record record : arrcitys) {
					ResultflyEntity resultflyEntity = new ResultflyEntity();
					resultflyEntity.setArrflightname(((String) record.get("tflandingname")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity
							.setFlightnum(((String) record.get("tfflightnum")).replace(" ", "").replace("*", ""));
					resultflyEntity.setGoflightname(((String) record.get("tftakeoffname")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setLandingofftime(((String) record.get("tflandingtime")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setTakeofftime(((String) record.get("tftakeofftime")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setZhuanflightname(((String) record.get("tflandingname2")).replace(" ", "")
							.replace("*", ""));
					resultflyEntity.setZhuanflightnum(((String) record.get("tfflightnum2")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setZhuanlandingofftime(((String) record.get("tflandingtime2")).replace(" ", "")
							.replace("*", ""));
					resultflyEntity.setZhuantakeofftime(((String) record.get("tftakeofftime2")).replace(" ", "")
							.replace("*", ""));
					if (!arrayList.contains(resultflyEntity)) {
						arrayList.add(resultflyEntity);
					}
				}
				newairLineResult.setResultflyEntity(arrayList);
			} else {
				//转换为list
				arrayList = JsonUtil.fromJsonAsList(ResultflyEntity.class, flightinfo);
			}
			if (!Util.isEmpty(searchstr)) {
				searchstr = searchstr.toUpperCase();
				List<ResultflyEntity> resultlist = Lists.newArrayList();
				for (ResultflyEntity flight : arrayList) {
					if (flight.getFlightnum().indexOf(searchstr) != -1) {
						if (!resultlist.contains(flight)) {
							resultlist.add(flight);
						}
					}
					if (!Util.isEmpty(flight.getZhuanflightnum())
							&& flight.getZhuanflightnum().indexOf(searchstr) != -1) {
						if (!resultlist.contains(flight)) {
							resultlist.add(flight);
						}
					}
				}
				newairLineResult.setResultflyEntity(resultlist);
			} else {
				newairLineResult.setResultflyEntity(arrayList);
			}
		}

		if (!Util.isEmpty(arr) && arr.contains(",") && !Util.isEmpty(dep) && dep.contains(",")) {
			List<ResultflyEntity> arrayList = Lists.newArrayList();
			String searchstr = param.getFlight().trim();
			//缓存中没有，查库
			if (Util.isEmpty(flightinfo)) {
				//直飞航班
				String sqlString = sqlManager.get("tripairline_airlines");
				Sql sql = Sqls.create(sqlString);
				Cnd appcnd = Cnd.NEW();
				appcnd.and("tf.takeOffCityId", "in", dep);
				appcnd.and("tf.landingCityId", "in", arr);
				sql.setCondition(appcnd);
				List<Record> depcitys = dbDao.query(sql, null, null);
				for (Record record : depcitys) {
					ResultflyEntity resultflyEntity = new ResultflyEntity();
					resultflyEntity.setFlightnum(((String) record.get("flightnum")).replace(" ", "").replace("*", ""));
					resultflyEntity.setArrflightname(((String) record.get("landingname")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setGoflightname(((String) record.get("takeoffname")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setTakeofftime(((String) record.get("takeofftime")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setLandingofftime(((String) record.get("landingtime")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setZhuanflightname("a");
					if (!arrayList.contains(resultflyEntity)) {
						arrayList.add(resultflyEntity);
					}
				}

				//转飞航班
				String sqlString2 = sqlManager.get("tripairline_airlines_zhuan");
				Sql sql2 = Sqls.create(sqlString2);
				Cnd appcnd2 = Cnd.NEW();
				appcnd2.and("tf.takeOffCityId", "in", dep);
				appcnd2.and("tf2.landingCityId", "in", arr);
				sql2.setCondition(appcnd2);
				List<Record> arrcitys = dbDao.query(sql2, null, null);

				for (Record record : arrcitys) {
					ResultflyEntity resultflyEntity = new ResultflyEntity();
					resultflyEntity.setArrflightname(((String) record.get("tflandingname")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity
							.setFlightnum(((String) record.get("tfflightnum")).replace(" ", "").replace("*", ""));
					resultflyEntity.setGoflightname(((String) record.get("tftakeoffname")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setLandingofftime(((String) record.get("tflandingtime")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setTakeofftime(((String) record.get("tftakeofftime")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setZhuanflightname(((String) record.get("tflandingname2")).replace(" ", "")
							.replace("*", ""));
					resultflyEntity.setZhuanflightnum(((String) record.get("tfflightnum2")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setZhuanlandingofftime(((String) record.get("tflandingtime2")).replace(" ", "")
							.replace("*", ""));
					resultflyEntity.setZhuantakeofftime(((String) record.get("tftakeofftime2")).replace(" ", "")
							.replace("*", ""));
					if (!arrayList.contains(resultflyEntity)) {
						arrayList.add(resultflyEntity);
					}
				}
				newairLineResult.setResultflyEntity(arrayList);

			} else {
				//转换为list
				arrayList = JsonUtil.fromJsonAsList(ResultflyEntity.class, flightinfo);
			}

			if (!Util.isEmpty(searchstr)) {
				searchstr = searchstr.toUpperCase();
				List<ResultflyEntity> resultlist = Lists.newArrayList();
				for (ResultflyEntity flight : arrayList) {
					if (flight.getFlightnum().indexOf(searchstr) != -1) {
						if (!resultlist.contains(flight)) {
							resultlist.add(flight);
						}
					}
					if (!Util.isEmpty(flight.getZhuanflightnum())
							&& flight.getZhuanflightnum().indexOf(searchstr) != -1) {
						if (!resultlist.contains(flight)) {
							resultlist.add(flight);
						}
					}
				}
				newairLineResult.setResultflyEntity(resultlist);
			} else {
				newairLineResult.setResultflyEntity(arrayList);
			}

		}

		//放入缓存
		if (!Util.isEmpty(newairLineResult.getResultflyEntity())) {
			Collections.sort(newairLineResult.getResultflyEntity(), new Comparator<ResultflyEntity>() {
				public int compare(ResultflyEntity o1, ResultflyEntity o2) {
					if (Util.eq("a", o1.getZhuanflightname())) {
						return -1;
					}
					return 0;
				}
			});

			/*redisDao.hset(CommonConstants.AIRLINE_INFO_KEY,
					String.valueOf(param.getGocity()) + String.valueOf(param.getArrivecity()),
					JsonUtil.toJson(newairLineResult.getResultflyEntity()));*/
		}

		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");

		//设置参数
		return newairLineResult.getResultflyEntity();
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

	private int getCityId(String aircode) {
		TCityEntity airportinfo = dbDao.fetch(TCityEntity.class, Cnd.where("code", "=", aircode));
		return airportinfo.getId();
	}

	//清除缓存的航班
	public void deleteAielineCache() {
		Map<String, String> hgetAll = redisDao.hgetAll(CommonConstants.AIRLINE_INFO_KEY);
		if (!Util.isEmpty(hgetAll)) {
			Set<Entry<String, String>> entrySet = hgetAll.entrySet();
			for (Entry<String, String> entry : entrySet) {
				redisDao.hdel(CommonConstants.AIRLINE_INFO_KEY, entry.getKey());
			}
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
		NewAirLineResult newairLineResult = new NewAirLineResult();
		List<TFlightEntity> result = Lists.newArrayList();
		String dep = "";
		if (!Util.isEmpty(param.getGocity())) {
			TCityEntity gocity = dbDao.fetch(TCityEntity.class, param.getGocity());
			if (!Util.isEmpty(gocity.getRelationcity())) {
				dep = gocity.getRelationcity();
			} else {
				dep = gocity.getCode();
			}
		} else {
			return result;
		}
		String arr = "";
		if (!Util.isEmpty(param.getArrivecity())) {
			TCityEntity arrivecity = dbDao.fetch(TCityEntity.class, param.getArrivecity());
			if (!Util.isEmpty(arrivecity.getRelationcity())) {
				arr = arrivecity.getRelationcity();
			} else {
				arr = arrivecity.getCode();
			}
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
		String flightinfo = redisDao.hget(CommonConstants.AIRLINE_INFO_KEY,
				String.valueOf(param.getGocity()) + String.valueOf(param.getArrivecity()));
		if (Util.isEmpty(flightinfo)) {
			//查询相关航班
			if (!Util.isEmpty(arr) && !arr.contains(",") && !Util.isEmpty(dep) && !dep.contains(",")) {
				List<ResultflyEntity> arrayList = Lists.newArrayList();

				//直飞航班
				String sqlString = sqlManager.get("tripairline_airlines");
				Sql sql = Sqls.create(sqlString);
				Cnd appcnd = Cnd.NEW();
				appcnd.and("tf.takeOffCode", "=", dep);
				appcnd.and("tf.landingCode", "=", arr);
				sql.setCondition(appcnd);
				List<Record> depcitys = dbDao.query(sql, null, null);
				for (Record record : depcitys) {
					ResultflyEntity resultflyEntity = new ResultflyEntity();
					resultflyEntity.setFlightnum(((String) record.get("flightnum")).replace(" ", "").replace("*", ""));
					resultflyEntity.setArrflightname(((String) record.get("landingname")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setGoflightname(((String) record.get("takeoffname")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setTakeofftime(((String) record.get("takeofftime")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setLandingofftime(((String) record.get("landingtime")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setZhuanflightname("a");
					if (!arrayList.contains(resultflyEntity)) {
						arrayList.add(resultflyEntity);
					}
				}

				//转飞航班
				String sqlString2 = sqlManager.get("tripairline_airlines_zhuan");
				Sql sql2 = Sqls.create(sqlString2);
				Cnd appcnd2 = Cnd.NEW();
				appcnd2.and("tf.takeOffCode", "=", dep);
				appcnd2.and("tf2.landingCode", "=", arr);
				sql2.setCondition(appcnd2);
				List<Record> arrcitys = dbDao.query(sql2, null, null);

				for (Record record : arrcitys) {
					ResultflyEntity resultflyEntity = new ResultflyEntity();
					resultflyEntity.setArrflightname(((String) record.get("tflandingname")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity
							.setFlightnum(((String) record.get("tfflightnum")).replace(" ", "").replace("*", ""));
					resultflyEntity.setGoflightname(((String) record.get("tftakeoffname")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setLandingofftime(((String) record.get("tflandingtime")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setTakeofftime(((String) record.get("tftakeofftime")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setZhuanflightname(((String) record.get("tflandingname2")).replace(" ", "")
							.replace("*", ""));
					resultflyEntity.setZhuanflightnum(((String) record.get("tfflightnum2")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setZhuanlandingofftime(((String) record.get("tflandingtime2")).replace(" ", "")
							.replace("*", ""));
					resultflyEntity.setZhuantakeofftime(((String) record.get("tftakeofftime2")).replace(" ", "")
							.replace("*", ""));
					if (!arrayList.contains(resultflyEntity)) {
						arrayList.add(resultflyEntity);
					}
				}
				newairLineResult.setResultflyEntity(arrayList);

				/*//航班分直飞和转机
				List<TFlightEntity> zhiflyList = dbDao.query(TFlightEntity.class, Cnd.where("takeOffCode", "=", dep)
						.and("landingCode", "=", arr), null);
				//直飞航班
				if (!Util.isEmpty(zhiflyList)) {
					for (TFlightEntity tFlightEntity : zhiflyList) {
						ResultflyEntity resultflyEntity = new ResultflyEntity();
						resultflyEntity.setFlightnum(tFlightEntity.getFlightnum());
						resultflyEntity.setArrflightname(tFlightEntity.getLandingName());
						resultflyEntity.setGoflightname(tFlightEntity.getTakeOffName());
						resultflyEntity.setTakeofftime(tFlightEntity.getTakeOffTime());
						resultflyEntity.setLandingofftime(tFlightEntity.getLandingTime());
						if (!arrayList.contains(resultflyEntity)) {
							arrayList.add(resultflyEntity);
						}
					}
					//newairLineResult.setResultflyEntity(arrayList);
				}

				//转机航班
				List<TFlightEntity> flights = dbDao
						.query(TFlightEntity.class, Cnd.where("takeOffCode", "=", dep), null);
				if (!Util.isEmpty(flights)) {
					for (TFlightEntity tFlightEntity : flights) {
						if (!Util.isEmpty(tFlightEntity.getRelationflight())) {
							TFlightEntity fetch = dbDao.fetch(TFlightEntity.class, tFlightEntity.getRelationflight()
									.longValue());
							if (Util.eq(fetch.getLandingCode(), arr)) {
								ResultflyEntity resultflyEntity = new ResultflyEntity();
								resultflyEntity.setArrflightname(fetch.getLandingName());
								resultflyEntity.setFlightnum(tFlightEntity.getFlightnum());
								resultflyEntity.setGoflightname(tFlightEntity.getTakeOffName());
								resultflyEntity.setLandingofftime(tFlightEntity.getLandingTime());
								resultflyEntity.setTakeofftime(tFlightEntity.getTakeOffTime());
								resultflyEntity.setZhuanflightname(fetch.getTakeOffName());
								resultflyEntity.setZhuanflightnum(fetch.getFlightnum());
								resultflyEntity.setZhuanlandingofftime(fetch.getLandingTime());
								resultflyEntity.setZhuantakeofftime(fetch.getTakeOffTime());
								if (!arrayList.contains(resultflyEntity)) {
									arrayList.add(resultflyEntity);
								}
							}
						}
					}
				}*/
				/*Collections.sort(arrayList, new Comparator<ResultflyEntity>() {
					public int compare(ResultflyEntity o1, ResultflyEntity o2) {
						if (Util.isEmpty(o1.getZhuanflightname())) {
							return -1;
						}
						return 0;
					}
				});*/
				//newairLineResult.setResultflyEntity(arrayList);
			}

			if (!Util.isEmpty(arr) && arr.contains(",") && !Util.isEmpty(dep) && !dep.contains(",")) {
				//抵达有关联航班,这时arr是由多个id组成的字符串
				List<ResultflyEntity> arrayList = Lists.newArrayList();
				//直飞航班
				String sqlString = sqlManager.get("tripairline_airlines");
				Sql sql = Sqls.create(sqlString);
				Cnd appcnd = Cnd.NEW();
				appcnd.and("tf.takeOffCode", "=", dep);
				appcnd.and("tf.landingCityId", "in", arr);
				sql.setCondition(appcnd);
				List<Record> depcitys = dbDao.query(sql, null, null);
				for (Record record : depcitys) {
					ResultflyEntity resultflyEntity = new ResultflyEntity();
					resultflyEntity.setFlightnum(((String) record.get("flightnum")).replace(" ", "").replace("*", ""));
					resultflyEntity.setArrflightname(((String) record.get("landingname")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setGoflightname(((String) record.get("takeoffname")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setTakeofftime(((String) record.get("takeofftime")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setLandingofftime(((String) record.get("landingtime")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setZhuanflightname("a");
					if (!arrayList.contains(resultflyEntity)) {
						arrayList.add(resultflyEntity);
					}
				}

				//转飞航班
				String sqlString2 = sqlManager.get("tripairline_airlines_zhuan");
				Sql sql2 = Sqls.create(sqlString2);
				Cnd appcnd2 = Cnd.NEW();
				appcnd2.and("tf.takeOffCode", "=", dep);
				appcnd2.and("tf2.landingCityId", "in", arr);
				sql2.setCondition(appcnd2);
				List<Record> arrcitys = dbDao.query(sql2, null, null);

				for (Record record : arrcitys) {
					ResultflyEntity resultflyEntity = new ResultflyEntity();
					resultflyEntity.setArrflightname(((String) record.get("tflandingname")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity
							.setFlightnum(((String) record.get("tfflightnum")).replace(" ", "").replace("*", ""));
					resultflyEntity.setGoflightname(((String) record.get("tftakeoffname")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setLandingofftime(((String) record.get("tflandingtime")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setTakeofftime(((String) record.get("tftakeofftime")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setZhuanflightname(((String) record.get("tflandingname2")).replace(" ", "")
							.replace("*", ""));
					resultflyEntity.setZhuanflightnum(((String) record.get("tfflightnum2")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setZhuanlandingofftime(((String) record.get("tflandingtime2")).replace(" ", "")
							.replace("*", ""));
					resultflyEntity.setZhuantakeofftime(((String) record.get("tftakeofftime2")).replace(" ", "")
							.replace("*", ""));
					if (!arrayList.contains(resultflyEntity)) {
						arrayList.add(resultflyEntity);
					}
				}
				newairLineResult.setResultflyEntity(arrayList);

				/*String[] strings = arr.split(",");
				for (String string : strings) {
					TCityEntity city = dbDao.fetch(TCityEntity.class, Long.valueOf(string));

					//如果缓存中不存在数据则查询数据库
					//航班分直飞和转机
					List<TFlightEntity> zhiflyList = dbDao.query(TFlightEntity.class, Cnd
							.where("takeOffCode", "=", dep).and("landingCode", "=", city.getCode()), null);
					//直飞航班
					if (!Util.isEmpty(zhiflyList)) {
						for (TFlightEntity tFlightEntity : zhiflyList) {
							ResultflyEntity resultflyEntity = new ResultflyEntity();
							resultflyEntity.setFlightnum(tFlightEntity.getFlightnum());
							resultflyEntity.setArrflightname(tFlightEntity.getLandingName());
							resultflyEntity.setGoflightname(tFlightEntity.getTakeOffName());
							resultflyEntity.setTakeofftime(tFlightEntity.getTakeOffTime());
							resultflyEntity.setLandingofftime(tFlightEntity.getLandingTime());
							if (!arrayList.contains(resultflyEntity)) {
								arrayList.add(resultflyEntity);
							}
						}
					}
					//转机航班
					List<TFlightEntity> flights = dbDao.query(TFlightEntity.class, Cnd.where("takeOffCode", "=", dep),
							null);
					if (!Util.isEmpty(flights)) {
						for (TFlightEntity tFlightEntity : flights) {
							if (!Util.isEmpty(tFlightEntity.getRelationflight())) {
								TFlightEntity fetch = dbDao.fetch(TFlightEntity.class, tFlightEntity
										.getRelationflight().longValue());
								if (Util.eq(fetch.getLandingCode(), city.getCode())) {
									ResultflyEntity resultflyEntity = new ResultflyEntity();
									resultflyEntity.setArrflightname(fetch.getLandingName());
									resultflyEntity.setFlightnum(tFlightEntity.getFlightnum());
									resultflyEntity.setGoflightname(tFlightEntity.getTakeOffName());
									resultflyEntity.setLandingofftime(tFlightEntity.getLandingTime());
									resultflyEntity.setTakeofftime(tFlightEntity.getTakeOffTime());
									resultflyEntity.setZhuanflightname(fetch.getTakeOffName());
									resultflyEntity.setZhuanflightnum(fetch.getFlightnum());
									resultflyEntity.setZhuanlandingofftime(fetch.getLandingTime());
									resultflyEntity.setZhuantakeofftime(fetch.getTakeOffTime());
									if (!arrayList.contains(resultflyEntity)) {
										arrayList.add(resultflyEntity);
									}
								}
							}
						}
					}

				}*/
				/*Collections.sort(arrayList, new Comparator<ResultflyEntity>() {
					public int compare(ResultflyEntity o1, ResultflyEntity o2) {
						if (Util.isEmpty(o1.getZhuanflightname())) {
							return -1;
						}
						return 0;
					}
				});*/
				//newairLineResult.setResultflyEntity(arrayList);
			}

			if (!Util.isEmpty(arr) && !arr.contains(",") && !Util.isEmpty(dep) && dep.contains(",")) {
				List<ResultflyEntity> arrayList = Lists.newArrayList();
				//直飞航班
				String sqlString = sqlManager.get("tripairline_airlines");
				Sql sql = Sqls.create(sqlString);
				Cnd appcnd = Cnd.NEW();
				appcnd.and("tf.takeOffCityId", "in", dep);
				appcnd.and("tf.landingCode", "=", arr);
				sql.setCondition(appcnd);
				List<Record> depcitys = dbDao.query(sql, null, null);
				for (Record record : depcitys) {
					ResultflyEntity resultflyEntity = new ResultflyEntity();
					resultflyEntity.setFlightnum(((String) record.get("flightnum")).replace(" ", "").replace("*", ""));
					resultflyEntity.setArrflightname(((String) record.get("landingname")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setGoflightname(((String) record.get("takeoffname")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setTakeofftime(((String) record.get("takeofftime")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setLandingofftime(((String) record.get("landingtime")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setZhuanflightname("a");
					if (!arrayList.contains(resultflyEntity)) {
						arrayList.add(resultflyEntity);
					}
				}

				//转飞航班
				String sqlString2 = sqlManager.get("tripairline_airlines_zhuan");
				Sql sql2 = Sqls.create(sqlString2);
				Cnd appcnd2 = Cnd.NEW();
				appcnd2.and("tf.takeOffCityId", "in", dep);
				appcnd2.and("tf2.landingCode", "=", arr);
				sql2.setCondition(appcnd2);
				List<Record> arrcitys = dbDao.query(sql2, null, null);

				for (Record record : arrcitys) {
					ResultflyEntity resultflyEntity = new ResultflyEntity();
					resultflyEntity.setArrflightname(((String) record.get("tflandingname")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity
							.setFlightnum(((String) record.get("tfflightnum")).replace(" ", "").replace("*", ""));
					resultflyEntity.setGoflightname(((String) record.get("tftakeoffname")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setLandingofftime(((String) record.get("tflandingtime")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setTakeofftime(((String) record.get("tftakeofftime")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setZhuanflightname(((String) record.get("tflandingname2")).replace(" ", "")
							.replace("*", ""));
					resultflyEntity.setZhuanflightnum(((String) record.get("tfflightnum2")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setZhuanlandingofftime(((String) record.get("tflandingtime2")).replace(" ", "")
							.replace("*", ""));
					resultflyEntity.setZhuantakeofftime(((String) record.get("tftakeofftime2")).replace(" ", "")
							.replace("*", ""));
					if (!arrayList.contains(resultflyEntity)) {
						arrayList.add(resultflyEntity);
					}
				}
				newairLineResult.setResultflyEntity(arrayList);

				/*String[] strings = dep.split(",");
				for (String string : strings) {
					TCityEntity city = dbDao.fetch(TCityEntity.class, Long.valueOf(string));

					//如果缓存中不存在数据则查询数据库
					//航班分直飞和转机
					List<TFlightEntity> zhiflyList = dbDao.query(TFlightEntity.class,
							Cnd.where("takeOffCode", "=", city.getCode()).and("landingCode", "=", arr), null);
					//直飞航班
					if (!Util.isEmpty(zhiflyList)) {
						for (TFlightEntity tFlightEntity : zhiflyList) {
							ResultflyEntity resultflyEntity = new ResultflyEntity();
							resultflyEntity.setFlightnum(tFlightEntity.getFlightnum());
							resultflyEntity.setArrflightname(tFlightEntity.getLandingName());
							resultflyEntity.setGoflightname(tFlightEntity.getTakeOffName());
							resultflyEntity.setTakeofftime(tFlightEntity.getTakeOffTime());
							resultflyEntity.setLandingofftime(tFlightEntity.getLandingTime());
							if (!arrayList.contains(resultflyEntity)) {
								arrayList.add(resultflyEntity);
							}
						}
						//newairLineResult.setResultflyEntity(arrayList);
					}
					//转机航班
					List<TFlightEntity> flights = dbDao.query(TFlightEntity.class,
							Cnd.where("takeOffCode", "=", city.getCode()), null);
					if (!Util.isEmpty(flights)) {
						for (TFlightEntity tFlightEntity : flights) {
							if (!Util.isEmpty(tFlightEntity.getRelationflight())) {
								TFlightEntity fetch = dbDao.fetch(TFlightEntity.class, tFlightEntity
										.getRelationflight().longValue());
								if (Util.eq(fetch.getLandingCode(), arr)) {
									ResultflyEntity resultflyEntity = new ResultflyEntity();
									resultflyEntity.setArrflightname(fetch.getLandingName());
									resultflyEntity.setFlightnum(tFlightEntity.getFlightnum());
									resultflyEntity.setGoflightname(tFlightEntity.getTakeOffName());
									resultflyEntity.setLandingofftime(tFlightEntity.getLandingTime());
									resultflyEntity.setTakeofftime(tFlightEntity.getTakeOffTime());
									resultflyEntity.setZhuanflightname(fetch.getTakeOffName());
									resultflyEntity.setZhuanflightnum(fetch.getFlightnum());
									resultflyEntity.setZhuanlandingofftime(fetch.getLandingTime());
									resultflyEntity.setZhuantakeofftime(fetch.getTakeOffTime());
									if (!arrayList.contains(resultflyEntity)) {
										arrayList.add(resultflyEntity);
									}
								}
							}
						}
					}

				}
				newairLineResult.setResultflyEntity(arrayList);*/
			}

			if (!Util.isEmpty(arr) && arr.contains(",") && !Util.isEmpty(dep) && dep.contains(",")) {
				List<ResultflyEntity> arrayList = Lists.newArrayList();

				//直飞航班
				String sqlString = sqlManager.get("tripairline_airlines");
				Sql sql = Sqls.create(sqlString);
				Cnd appcnd = Cnd.NEW();
				appcnd.and("tf.takeOffCityId", "in", dep);
				appcnd.and("tf.landingCityId", "in", arr);
				sql.setCondition(appcnd);
				List<Record> depcitys = dbDao.query(sql, null, null);
				for (Record record : depcitys) {
					ResultflyEntity resultflyEntity = new ResultflyEntity();
					resultflyEntity.setFlightnum(((String) record.get("flightnum")).replace(" ", "").replace("*", ""));
					resultflyEntity.setArrflightname(((String) record.get("landingname")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setGoflightname(((String) record.get("takeoffname")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setTakeofftime(((String) record.get("takeofftime")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setLandingofftime(((String) record.get("landingtime")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setZhuanflightname("a");
					if (!arrayList.contains(resultflyEntity)) {
						arrayList.add(resultflyEntity);
					}
				}

				//转飞航班
				String sqlString2 = sqlManager.get("tripairline_airlines_zhuan");
				Sql sql2 = Sqls.create(sqlString2);
				Cnd appcnd2 = Cnd.NEW();
				appcnd2.and("tf.takeOffCityId", "in", dep);
				appcnd2.and("tf2.landingCityId", "in", arr);
				sql2.setCondition(appcnd2);
				List<Record> arrcitys = dbDao.query(sql2, null, null);

				for (Record record : arrcitys) {
					ResultflyEntity resultflyEntity = new ResultflyEntity();
					resultflyEntity.setArrflightname(((String) record.get("tflandingname")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity
							.setFlightnum(((String) record.get("tfflightnum")).replace(" ", "").replace("*", ""));
					resultflyEntity.setGoflightname(((String) record.get("tftakeoffname")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setLandingofftime(((String) record.get("tflandingtime")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setTakeofftime(((String) record.get("tftakeofftime")).replace(" ", "").replace("*",
							""));
					resultflyEntity.setZhuanflightname(((String) record.get("tflandingname2")).replace(" ", "")
							.replace("*", ""));
					resultflyEntity.setZhuanflightnum(((String) record.get("tfflightnum2")).replace(" ", "").replace(
							"*", ""));
					resultflyEntity.setZhuanlandingofftime(((String) record.get("tflandingtime2")).replace(" ", "")
							.replace("*", ""));
					resultflyEntity.setZhuantakeofftime(((String) record.get("tftakeofftime2")).replace(" ", "")
							.replace("*", ""));
					if (!arrayList.contains(resultflyEntity)) {
						arrayList.add(resultflyEntity);
					}
				}
				newairLineResult.setResultflyEntity(arrayList);

				/*String[] deps = dep.split(",");
				String[] arrs = arr.split(",");
				for (int i = 0; i < deps.length; i++) {
					for (int j = 0; j < arrs.length; j++) {
						TCityEntity depcity = dbDao.fetch(TCityEntity.class, Long.valueOf(deps[i]));
						TCityEntity arrcity = dbDao.fetch(TCityEntity.class, Long.valueOf(arrs[j]));

						//航班分直飞和转机
						List<TFlightEntity> zhiflyList = dbDao.query(
								TFlightEntity.class,
								Cnd.where("takeOffCode", "=", depcity.getCode()).and("landingCode", "=",
										arrcity.getCode()), null);
						//直飞航班
						if (!Util.isEmpty(zhiflyList)) {
							for (TFlightEntity tFlightEntity : zhiflyList) {
								ResultflyEntity resultflyEntity2 = new ResultflyEntity();
								resultflyEntity2.setFlightnum(tFlightEntity.getFlightnum());
								resultflyEntity2.setArrflightname(tFlightEntity.getLandingName());
								resultflyEntity2.setGoflightname(tFlightEntity.getTakeOffName());
								resultflyEntity2.setTakeofftime(tFlightEntity.getTakeOffTime());
								resultflyEntity2.setLandingofftime(tFlightEntity.getLandingTime());
								if (!arrayList.contains(resultflyEntity2)) {
									arrayList.add(resultflyEntity2);
								}
							}
							//newairLineResult.setResultflyEntity(arrayList);
						}
						//转机航班
						List<TFlightEntity> flights = dbDao.query(TFlightEntity.class,
								Cnd.where("takeOffCode", "=", depcity.getCode()), null);
						if (!Util.isEmpty(flights)) {
							for (TFlightEntity tFlightEntity : flights) {
								if (!Util.isEmpty(tFlightEntity.getRelationflight())) {
									TFlightEntity fetch = dbDao.fetch(TFlightEntity.class, tFlightEntity
											.getRelationflight().longValue());
									if (Util.eq(fetch.getLandingCode(), arrcity.getCode())) {
										ResultflyEntity resultflyEntity = new ResultflyEntity();
										resultflyEntity.setArrflightname(fetch.getLandingName());
										resultflyEntity.setFlightnum(tFlightEntity.getFlightnum());
										resultflyEntity.setGoflightname(tFlightEntity.getTakeOffName());
										resultflyEntity.setLandingofftime(tFlightEntity.getLandingTime());
										resultflyEntity.setTakeofftime(tFlightEntity.getTakeOffTime());
										resultflyEntity.setZhuanflightname(fetch.getTakeOffName());
										resultflyEntity.setZhuanflightnum(fetch.getFlightnum());
										resultflyEntity.setZhuanlandingofftime(fetch.getLandingTime());
										resultflyEntity.setZhuantakeofftime(fetch.getTakeOffTime());
										if (!arrayList.contains(resultflyEntity)) {
											arrayList.add(resultflyEntity);
										}
									}
								}
							}
						}

					}

					newairLineResult.setResultflyEntity(arrayList);
				}*/
			}

			//放入缓存
			if (!Util.isEmpty(newairLineResult.getResultflyEntity())) {
				/*Collections.sort(newairLineResult.getResultflyEntity(), new Comparator<ResultflyEntity>() {
					public int compare(ResultflyEntity o1, ResultflyEntity o2) {
						return o1.getZhuanflightname().compareTo(o2.getZhuanflightname());
					}
				});*/
				Collections.sort(newairLineResult.getResultflyEntity(), new Comparator<ResultflyEntity>() {
					public int compare(ResultflyEntity o1, ResultflyEntity o2) {
						if (Util.eq("a", o1.getZhuanflightname())) {
							return -1;
						}
						return 0;
					}
				});

				redisDao.hset(CommonConstants.AIRLINE_INFO_KEY,
						String.valueOf(param.getGocity()) + String.valueOf(param.getArrivecity()),
						JsonUtil.toJson(newairLineResult.getResultflyEntity()));
			}

			//查询接口
			/*AirLineParam airLineParam = new AirLineParam();
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
					JsonUtil.toJson(airLineResult.getAiliLineInFos()));*/
			//同步到数据库
			//updateFlightByCache(param.getGocity().intValue(), param.getArrivecity().intValue());
		}
		//设置参数
		return null;
	}

	public Object getAirlines(FlightSelectParam param) {
		AirLineResult airLineResult = new AirLineResult();
		//查询接口
		AirLineParam airLineParam = new AirLineParam();
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

		ArrayList<String> depList = new ArrayList<>();
		depList.add("TSN");
		depList.add("XIY");
		depList.add("TYN");
		depList.add("LHW");
		depList.add("CGO");
		depList.add("SJW");
		depList.add("HET");
		depList.add("LXA");
		depList.add("INC");
		depList.add("URC");
		depList.add("XNN");
		depList.add("CSX");
		depList.add("WUH");
		/*depList.add("OKA");
		depList.add("ITM");
		depList.add("KIX");
		depList.add("UKB");
		depList.add("HND");
		depList.add("NRT");
		depList.add("NGO");
		depList.add("SPK");
		depList.add("HIJ");
		depList.add("NGS");
		depList.add("KOJ");
		depList.add("AOJ");
		depList.add("HNA");
		depList.add("SDJ");
		depList.add("FKS");
		depList.add("GAJ");
		depList.add("AXT");*/
		ArrayList<String> depList2 = new ArrayList<>();
		/*depList2.add("TSN");
		depList2.add("SIA");
		depList2.add("TYN");
		depList2.add("LHW");
		depList2.add("CGO");
		depList2.add("SJW");
		depList2.add("HET");
		depList2.add("LXA");
		depList2.add("INC");
		depList2.add("URC");
		depList2.add("XNN");
		depList2.add("CSX");
		depList2.add("WUH");*/
		depList2.add("OKA");
		depList2.add("ITM");
		depList2.add("KIX");
		depList2.add("UKB");
		depList2.add("HND");
		depList2.add("NRT");
		depList2.add("NGO");
		depList2.add("SPK");
		depList2.add("HIJ");
		depList2.add("NGS");
		depList2.add("KOJ");
		depList2.add("AOJ");
		depList2.add("HNA");
		depList2.add("SDJ");
		depList2.add("FKS");
		depList2.add("GAJ");
		depList2.add("AXT");

		airLineParam.setDate("20180728");

		String airLinejson = "";
		for (int i = 0; i < depList2.size(); i++) {
			for (int j = 0; j < depList.size(); j++) {
				if (!Util.eq(depList.get(j), depList2.get(i))) {
					airLineResult.getAiliLineInFos().clear();
					airLineParam.setDep(depList2.get(i));
					airLineParam.setArr(depList.get(j));
					airLinejson = AirlineData.getAirLineInfo(airLineParam);
					Map<String, Object> map = JsonUtil.fromJson(airLinejson, Map.class);
					//返回代码
					airLineResult.setError_code(map.get("error_code").toString());
					//返回错误信息
					airLineResult.setReason((String) map.get("reason"));
					List<LinkedHashMap<String, String>> fromJsonAsList = (List) map.get("result");
					DateFormat dateFormat = new SimpleDateFormat("HHmm");
					if (!Util.isEmpty(fromJsonAsList) && fromJsonAsList.size() > 0) {
						for (LinkedHashMap<String, String> airmap : fromJsonAsList) {
							TFlightEntity flightEntity = new TFlightEntity();
							//flightEntity.setAirlinecomp(airmap.get("airmode"));
							flightEntity.setFlightnum(airmap.get("flightNo"));
							//起飞机场三字代码
							String OrgCity = airmap.get("OrgCity");
							flightEntity.setTakeOffName(getAirportName(OrgCity));
							flightEntity.setTakeOffCityId(getCityId(OrgCity));
							flightEntity.setTakeOffCode(OrgCity);
							//降落机场三字代码
							String Dstcity = airmap.get("Dstcity");
							flightEntity.setLandingName(getAirportName(Dstcity));
							flightEntity.setLandingCityId(getCityId(Dstcity));
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
					}
					List<TFlightHisEntity> before = dbDao.query(TFlightHisEntity.class,
							Cnd.where("takeOffCode", "=", depList2.get(i)).and("landingCode", "=", depList.get(j)),
							null);
					List<TFlightHisEntity> flightlist = JsonUtil.fromJsonAsList(TFlightHisEntity.class,
							JsonUtil.toJson(airLineResult.getAiliLineInFos()));
					if (!Util.isEmpty(flightlist) && flightlist.size() > 0) {
						dbDao.updateRelations(before, flightlist);
					}
				}
			}
		}

		return airLineResult.getAiliLineInFos();
	}

}
