/**
 * LiaoNingWanDaService.java
 * com.juyo.visa.admin.visajp.service
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.visajp.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.juyo.visa.admin.city.service.CityViewService;
import com.juyo.visa.admin.flight.service.FlightViewService;
import com.juyo.visa.admin.hotel.service.HotelViewService;
import com.juyo.visa.admin.scenic.service.ScenicViewService;
import com.juyo.visa.admin.visajp.util.TemplateUtil;
import com.juyo.visa.admin.visajp.util.TtfClassLoader;
import com.juyo.visa.common.enums.JobStatusEnum;
import com.juyo.visa.common.enums.MarryStatusEnum;
import com.juyo.visa.common.enums.SimpleVisaTypeEnum;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TApplicantWealthJpEntity;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TFlightEntity;
import com.juyo.visa.entities.THotelEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderTravelplanJpEntity;
import com.juyo.visa.entities.TOrderTripJpEntity;
import com.juyo.visa.entities.TOrderTripMultiJpEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年3月14日 	 
 */
@IocBean
public class ShanghaiBaichengService extends BaseService<TOrderJpEntity> {

	@Inject
	private FlightViewService flightViewService;
	@Inject
	private HotelViewService hotelViewService;
	@Inject
	private ScenicViewService scenicViewService;
	@Inject
	private CityViewService cityViewService;

	private static DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");

	/**
	 * 准备下载文件并打包为ZIP文件字节流
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderjp
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public ByteArrayOutputStream generateFile(TOrderJpEntity orderjp, HttpServletRequest request) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Map<String, File> fileMap = new HashMap<String, File>();
		TemplateUtil templateUtil = new TemplateUtil();
		//订单信息
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
		//出行信息
		TOrderTripJpEntity ordertripjp = dbDao.fetch(TOrderTripJpEntity.class,
				Cnd.where("orderId", "=", orderjp.getId()));
		List<TOrderTripMultiJpEntity> mutiltrip = new ArrayList<TOrderTripMultiJpEntity>();
		if (!Util.isEmpty(ordertripjp)) {
			mutiltrip = dbDao.query(TOrderTripMultiJpEntity.class, Cnd.where("tripid", "=", ordertripjp.getId()), null);
		}
		//公司信息
		TCompanyEntity company = new TCompanyEntity();
		if (!Util.isEmpty(orderjp.getSendsignid())) {
			company = dbDao.fetch(TCompanyEntity.class, orderjp.getSendsignid().longValue());
		}
		//申请人信息
		String applysqlstr = sqlManager.get("get_applyinfo_from_filedown_by_orderid_jp");
		Sql applysql = Sqls.create(applysqlstr);
		Cnd cnd = Cnd.NEW();
		cnd.and("taoj.orderId", "=", orderjp.getId());
		//cnd.orderBy("ta.id", "ASC");
		cnd.orderBy("taoj.isMainApplicant", "DESC");
		List<Record> applyinfo = dbDao.query(applysql, cnd, null);
		for (Record record : applyinfo) {
			List<TApplicantWealthJpEntity> query = dbDao.query(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", record.get("applicatid")), null);
			record.put("wealthjpinfo", query);

			//日本申请人信息
			TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
					(int) record.get("applicatid"));
			record.put("applyorderjp", applicantOrderJpEntity);
		}
		Map<String, Object> tempdata = new HashMap<String, Object>();
		//行程安排
		List<TOrderTravelplanJpEntity> ordertravelplan = dbDao.query(TOrderTravelplanJpEntity.class,
				Cnd.where("orderId", "=", orderjp.getId()).orderBy("outDate", "ASC"), null);
		tempdata.put("orderinfo", orderinfo);
		tempdata.put("orderjp", orderjp);
		tempdata.put("company", company);
		tempdata.put("ordertripjp", ordertripjp);
		tempdata.put("applyinfo", applyinfo);
		tempdata.put("ordertravelplan", ordertravelplan);
		tempdata.put("mutiltrip", mutiltrip);
		//准备合并的PDF文件
		List<ByteArrayOutputStream> pdffiles = Lists.newArrayList();
		//准备封皮信息
		ByteArrayOutputStream note = note(tempdata);
		pdffiles.add(note);
		//査 証 申 請 人 名 簿
		ByteArrayOutputStream book = book(tempdata);
		pdffiles.add(book);
		//滞在予定表
		ByteArrayOutputStream tripInfo = tripInfo(tempdata);
		pdffiles.add(tripInfo);
		//申请人名单
		ByteArrayOutputStream applyList = applyList(tempdata);
		pdffiles.add(applyList);
		for (Record record : applyinfo) {
			//申请人信息
			ByteArrayOutputStream apply = applyinfo(record, tempdata);
			pdffiles.add(apply);
			//签证申请信息表
			ByteArrayOutputStream visaapplication = visaapplicationinfo(record, tempdata);
			pdffiles.add(visaapplication);
		}
		//电子航票
		ByteArrayOutputStream airticket = airticket(tempdata);
		pdffiles.add(airticket);
		//酒店信息
		ByteArrayOutputStream hotelInfo = hotelInfo(tempdata);
		pdffiles.add(hotelInfo);

		//		ByteArrayOutputStream returnhome = returnhome(tempdata);
		//		pdffiles.add(returnhome);
		ByteArrayOutputStream mergePdf = templateUtil.mergePdf(pdffiles);
		//申请人信息
		//return stream;
		//新需求修改为只下载pdf文件
		return mergePdf;
	}

	/**
	 * 封皮信息
	 */
	private ByteArrayOutputStream note(Map<String, Object> tempdata) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		//公司信息
		TCompanyEntity company = (TCompanyEntity) tempdata.get("company");
		//出行信息
		TOrderTripJpEntity ordertripjp = (TOrderTripJpEntity) tempdata.get("ordertripjp");
		//申请人信息
		List<Record> applyinfo = (List<Record>) tempdata.get("applyinfo");
		//日本订单信息
		TOrderJpEntity orderjp = (TOrderJpEntity) tempdata.get("orderjp");
		//订单信息
		TOrderEntity orderinfo = (TOrderEntity) tempdata.get("orderinfo");
		//多程信息
		List<TOrderTripMultiJpEntity> mutiltrip = (List<TOrderTripMultiJpEntity>) tempdata.get("mutiltrip");

		String visatypestr = "";
		Integer visaType = orderjp.getVisaType();
		if (!Util.isEmpty(visaType)) {
			for (SimpleVisaTypeEnum visatypeEnum : SimpleVisaTypeEnum.values()) {
				if (visatypeEnum.intKey() == visaType) {
					visatypestr = visatypeEnum.value();
				}
			}
		}
		//准备PDF模板数据 
		Map<String, String> map = new HashMap<String, String>();
		StringBuffer content = new StringBuffer();
		//地接社未做
		String dijie = "";
		String dijiecdesignnum = "";
		if (!Util.isEmpty(orderjp.getGroundconnectid())) {
			TCompanyEntity dijiecompany = dbDao.fetch(TCompanyEntity.class, orderjp.getGroundconnectid().longValue());
			dijie = dijiecompany.getName();
			if (!Util.isEmpty(dijiecompany.getCdesignNum())) {
				dijiecdesignnum = dijiecompany.getCdesignNum();
			}
		}
		String companyname = "";
		if (!Util.isEmpty(company.getName())) {
			companyname = company.getName();
		}
		String cdesignnum = "";
		if (!Util.isEmpty(company.getCdesignNum())) {
			cdesignnum = company.getCdesignNum();
		}
		content.append("　　" + companyname).append("(").append(cdesignnum).append(")根据与").append(dijie).append("(指定番号:")
				.append(dijiecdesignnum).append(")的合同约定,组织").append(applyinfo.size()).append("人访日旅游团，请协助办理赴日签证。");
		map.put("Text1", content.toString());
		map.put("Text8", company.getName());
		if (!Util.isEmpty(ordertripjp)) {
			if (ordertripjp.getTripType().equals(1)) {
				if (!Util.isEmpty(ordertripjp.getGoDate())) {
					map.put("Text2", dateFormat1.format(ordertripjp.getGoDate()));
				}
				//入境航班
				if (!Util.isEmpty(ordertripjp.getGoFlightNum())) {
					String goFlightNum = ordertripjp.getGoFlightNum();
					TCityEntity goCity = dbDao.fetch(TCityEntity.class, ordertripjp.getGoArrivedCity().longValue());
					String cityName = goCity.getCity();
					if (cityName.endsWith("市") || cityName.endsWith("县") || cityName.endsWith("府")) {
						cityName = cityName.substring(0, cityName.length() - 1);
					}
					//入境机场名
					String airportName = goFlightNum.substring(
							goFlightNum.indexOf("-", goFlightNum.lastIndexOf("-")) + 1,
							goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")));
					TFlightEntity airCity = dbDao
							.fetch(TFlightEntity.class, Cnd.where("takeOffName", "=", airportName));
					String aircode = "";
					if (!Util.isEmpty(airCity)) {
						aircode = airCity.getTakeOffCode();
					}
					map.put("Text3",
							cityName
									+ " "
									+ airportName
									+ " "
									+ aircode
									+ ": "
									+ goFlightNum.substring(goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")) + 1,
											goFlightNum.indexOf(" ", goFlightNum.indexOf(" ") + 1)));
				}
				if (!Util.isEmpty(ordertripjp.getReturnDate())) {
					map.put("Text4", dateFormat1.format(ordertripjp.getReturnDate()));
				}
				//天数
				/*if (!Util.isEmpty(ordertripjp.getGoDate()) && !Util.isEmpty(ordertripjp.getReturnDate())) {

					map.put("stay",
							String.valueOf(DateUtil.daysBetween(ordertripjp.getGoDate(), ordertripjp.getReturnDate()) + 1)
									+ "天");
				}*/
				if (!Util.isEmpty(ordertripjp.getReturnFlightNum())) {
					//出境航班
					String goFlightNum = ordertripjp.getReturnFlightNum();
					TCityEntity leaveCity = dbDao.fetch(TCityEntity.class, ordertripjp.getReturnDepartureCity()
							.longValue());
					String cityName = leaveCity.getCity();
					if (cityName.endsWith("市") || cityName.endsWith("县") || cityName.endsWith("府")) {
						cityName = cityName.substring(0, cityName.length() - 1);
					}
					//出境机场名
					String airportName = goFlightNum.substring(0, goFlightNum.indexOf("-", goFlightNum.indexOf("-")));
					TFlightEntity airCity = dbDao
							.fetch(TFlightEntity.class, Cnd.where("takeOffName", "=", airportName));
					String aircode = "";
					if (!Util.isEmpty(airCity)) {
						aircode = airCity.getTakeOffCode();
					}
					map.put("Text5",
							cityName
									+ " "
									+ airportName
									+ " "
									+ aircode
									+ ": "
									+ goFlightNum.substring(goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")) + 1,
											goFlightNum.indexOf(" ", goFlightNum.indexOf(" ") + 1)));
				}
				//map.put("Text14", ordertripjp.getReturnFlightNum().replace("*", ""));
			} else if (ordertripjp.getTripType().equals(2)) {
				//多程处理
				if (!Util.isEmpty(mutiltrip)) {
					//多程第一程为出发日期
					TOrderTripMultiJpEntity entrytrip = mutiltrip.get(0);
					if (!Util.isEmpty(entrytrip.getDepartureDate())) {
						map.put("Text4", dateFormat.format(entrytrip.getDepartureDate()));
					}
					//入境航班
					if (!Util.isEmpty(entrytrip.getFlightNum())) {
						//						TFlightEntity goflight = flightViewService.fetch(entrytrip.getFlightNum().longValue());
						TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,
								Cnd.where("flightnum", "=", entrytrip.getFlightNum()));
						map.put("Text5", goflight.getLandingName() + "、 " + entrytrip.getFlightNum().replace("*", ""));
					}
					//map.put("Text13", entrytrip.getFlightNum());
					//最后一程作为返回日期
					TOrderTripMultiJpEntity returntrip = mutiltrip.get(mutiltrip.size() - 1);
					if (!Util.isEmpty(returntrip.getDepartureDate())) {
						map.put("Text7", dateFormat.format(returntrip.getDepartureDate()));
					}
					if (!Util.isEmpty(returntrip.getFlightNum())) {
						//出境航班
						//						TFlightEntity returnflight = flightViewService.fetch(returntrip.getFlightNum().longValue());
						TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,
								Cnd.where("flightnum", "=", returntrip.getFlightNum()));
						map.put("Text8", goflight.getTakeOffName() + "、 " + returntrip.getFlightNum().replace("*", ""));
					}
					//map.put("Text14", returntrip.getFlightNum().replace("*", ""));
					//停留天数
					if (!Util.isEmpty(entrytrip.getDepartureDate()) && !Util.isEmpty(returntrip.getDepartureDate())) {
						map.put("Text6",
								String.valueOf(DateUtil.daysBetween(entrytrip.getDepartureDate(),
										returntrip.getDepartureDate()) + 1)
										+ "天");
					}
				}
			}
		}
		map.put("Text6", company.getLinkman());
		map.put("Text7", company.getMobile());
		Date sendVisaDate = orderinfo.getSendVisaDate();
		if (!Util.isEmpty(sendVisaDate)) {
			map.put("Text9", dateFormat.format(sendVisaDate));
		} else {
			map.put("Text9", "");
		}
		//获取模板文件
		URL resource = getClass().getClassLoader().getResource("japanfile/shanghaibaicheng/note.pdf");
		TemplateUtil templateUtil = new TemplateUtil();
		stream = templateUtil.pdfTemplateStream(resource, map);
		return stream;
	}

	private ByteArrayOutputStream visaapplicationinfo(Record record, Map<String, Object> tempdata) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
		TOrderTripJpEntity ordertripjp = (TOrderTripJpEntity) tempdata.get("ordertripjp");
		//多程信息
		List<TOrderTripMultiJpEntity> mutiltrip = (List<TOrderTripMultiJpEntity>) tempdata.get("mutiltrip");
		List<TOrderTravelplanJpEntity> ordertravelplan = (List<TOrderTravelplanJpEntity>) tempdata
				.get("ordertravelplan");
		TOrderJpEntity orderjp = (TOrderJpEntity) tempdata.get("orderjp");

		//日本申请人
		TApplicantOrderJpEntity applyorderjp = (TApplicantOrderJpEntity) record.get("applyorderjp");
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("Text1", record.getString("emergencylinkman"));
			map.put("Text2", applyorderjp.getMainRelation());
			map.put("Text3", record.getString("emergencyaddress"));
			map.put("Text4", record.getString("emergencytelephone"));

			//获取模板文件
			URL resource = getClass().getClassLoader().getResource("japanfile/shanghaibaicheng/visaapplication.pdf");
			TemplateUtil templateUtil = new TemplateUtil();
			stream = templateUtil.pdfTemplateStream(resource, map);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return stream;
	}

	/**
	 * 申请人信息
	 */
	private ByteArrayOutputStream applyinfo(Record record, Map<String, Object> tempdata) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
		TOrderTripJpEntity ordertripjp = (TOrderTripJpEntity) tempdata.get("ordertripjp");
		//多程信息
		List<TOrderTripMultiJpEntity> mutiltrip = (List<TOrderTripMultiJpEntity>) tempdata.get("mutiltrip");
		List<TOrderTravelplanJpEntity> ordertravelplan = (List<TOrderTravelplanJpEntity>) tempdata
				.get("ordertravelplan");
		TOrderJpEntity orderjp = (TOrderJpEntity) tempdata.get("orderjp");

		//日本申请人
		TApplicantOrderJpEntity applyorderjp = (TApplicantOrderJpEntity) record.get("applyorderjp");

		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("firstName", record.getString("firstname"));
			map.put("firstNameEn", record.getString("firstnameen"));
			map.put("lastName", record.getString("lastname"));
			map.put("lastNameEn", record.getString("lastnameen"));
			//曾用名
			String otherfirstnameen = !Util.isEmpty(record.get("otherfirstnameen")) ? record
					.getString("otherfirstnameen") : "";
			String otherlastnameen = !Util.isEmpty(record.get("otherlastnameen")) ? record.getString("otherlastnameen")
					: "";
			String otherfirstname = !Util.isEmpty(record.get("otherfirstname")) ? record.getString("otherfirstname")
					: "";
			String otherlastname = !Util.isEmpty(record.get("otherlastname")) ? record.getString("otherlastname") : "";
			String othername = otherfirstname + otherlastname;
			if (!Util.isEmpty(othername)) {
				map.put("othername", otherfirstname + otherlastname);
			} else {
				map.put("othername", "无");
			}
			String othernameen = otherfirstnameen + otherlastnameen;
			if (!Util.isEmpty(othernameen)) {
				map.put("othernameen", otherfirstnameen + otherlastnameen);
			} else {
				map.put("othernameen", "无");
			}
			//性别
			if ("男".equals(record.getString("sex"))) {
				map.put("boy", "0");
			} else if ("女".equals(record.getString("sex"))) {
				map.put("gril", "0");
			}
			//出生日期
			if (!Util.isEmpty(record.get("birthday"))) {
				map.put("birthday", dateformat.format((Date) record.get("birthday")));
			}
			//出生地
			String address = (String) record.get("birthaddress");
			map.put("address", address);
			//婚姻状况
			if (!Util.isEmpty(record.get("marrystatus"))) {
				Integer marrystatus = record.getInt("marrystatus");
				if (marrystatus.equals(MarryStatusEnum.YIHUN.intKey())) {
					map.put("married", "0");
				} else if (marrystatus.equals(MarryStatusEnum.DANSHEN.intKey())) {
					map.put("single", "0");
				} else if (marrystatus.equals(MarryStatusEnum.LIYI.intKey())) {
					map.put("divorce", "0");
				} else if (marrystatus.equals(MarryStatusEnum.SANGOU.intKey())) {
					map.put("widowed", "0");
				}
			}
			//国籍
			map.put("country", "CHINA");
			//曾有的或另有的国际
			if (Util.isEmpty(record.getString("nationality"))) {
				map.put("othernationality", "无");
			} else {
				map.put("othernationality", record.getString("nationality"));
			}
			//身份证号
			map.put("cardId", record.getString("cardid"));
			//护照类别:普通
			map.put("common", "0");
			//护照号
			map.put("passport", record.getString("passportno"));
			//签发地点
			map.put("issuedPlace", record.getString("issuedplace"));
			//签发日期
			if (!Util.isEmpty(record.get("issuedDate"))) {
				map.put("issuedDate", dateformat.format((Date) record.get("issuedDate")));
			}
			//签发机关
			map.put("issuedOrganization", record.getString("issuedOrganization"));
			//有效期至
			if (!Util.isEmpty(record.get("passportenddate"))) {
				map.put("validEndDate", dateformat.format((Date) record.get("passportenddate")));
			}
			if (!Util.isEmpty(ordertripjp)) {
				//赴日目的
				map.put("tripPurpose", ordertripjp.getTripPurpose());
				if (ordertripjp.getTripType().equals(1)) {
					//出行时间
					if (!Util.isEmpty(ordertripjp.getGoDate())) {
						map.put("goDate", dateFormat1.format(ordertripjp.getGoDate()));
					}
					//返回时间
					if (!Util.isEmpty(ordertripjp.getReturnDate())) {
						map.put("returnDate", dateFormat1.format(ordertripjp.getReturnDate()));
					}
					//逗留时间
					if (!Util.isEmpty(ordertripjp.getGoDate()) && !Util.isEmpty(ordertripjp.getReturnDate())) {
						int stayday = DateUtil.daysBetween(ordertripjp.getGoDate(), ordertripjp.getReturnDate());
						map.put("stayday", String.valueOf(stayday + 1) + "天");
					}
					if (!Util.isEmpty(ordertripjp.getGoFlightNum())) {
						String goFlightNum = ordertripjp.getGoFlightNum();

						TCityEntity goCity = dbDao.fetch(TCityEntity.class, ordertripjp.getGoArrivedCity().longValue());
						String cityName = goCity.getCity();
						if (cityName.endsWith("市") || cityName.endsWith("县") || cityName.endsWith("府")) {
							cityName = cityName.substring(0, cityName.length() - 1);
						}
						//入境机场名
						String airportName = goFlightNum.substring(
								goFlightNum.indexOf("-", goFlightNum.lastIndexOf("-")) + 1,
								goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")));
						TFlightEntity airCity = dbDao.fetch(TFlightEntity.class,
								Cnd.where("takeOffName", "=", airportName));
						String aircode = "";
						if (!Util.isEmpty(airCity)) {
							aircode = airCity.getTakeOffCode();
						}

						if (goFlightNum.contains("//")) {//转机
							//入境口岸
							map.put("goArrivedCity", cityName + " " + airportName + " " + aircode);
							//航空公司
							map.put("goFlightNum",
									goFlightNum.substring(goFlightNum.indexOf(" ") + 1, goFlightNum.indexOf("//")));
						} else {//直飞
							//入境口岸
							map.put("goArrivedCity", cityName + " " + airportName + " " + aircode);
							//航空公司
							map.put("goFlightNum",
									goFlightNum.substring(goFlightNum.indexOf(" ") + 1, goFlightNum.lastIndexOf(" ")));
						}
					}
				} else if (ordertripjp.getTripType().equals(2)) {
					//多程处理
					if (!Util.isEmpty(mutiltrip)) {
						//多程第一程为出发日期
						TOrderTripMultiJpEntity entrytrip = mutiltrip.get(0);
						if (!Util.isEmpty(entrytrip.getDepartureDate())) {
							map.put("goDate", dateFormat1.format(entrytrip.getDepartureDate()));
						}
						//入境口岸
						TCityEntity arrivecity = dbDao.fetch(TCityEntity.class, entrytrip.getArrivedCity().longValue());
						if (!Util.isEmpty(arrivecity)) {
							map.put("goArrivedCity", arrivecity.getCity());
						}
						//航空公司
						if (!Util.isEmpty(entrytrip.getFlightNum())) {
							TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,
									Cnd.where("flightnum", "=", entrytrip.getFlightNum()));
							map.put("goFlightNum", entrytrip.getFlightNum());
						}
						//最后一程作为返回日期
						TOrderTripMultiJpEntity returntrip = mutiltrip.get(mutiltrip.size() - 1);
						if (!Util.isEmpty(returntrip.getDepartureDate())) {
							map.put("returnDate", dateFormat1.format(returntrip.getDepartureDate()));
						}
						//停留天数
						if (!Util.isEmpty(entrytrip.getDepartureDate()) && !Util.isEmpty(returntrip.getDepartureDate())) {
							int stayday = DateUtil.daysBetween(entrytrip.getDepartureDate(),
									returntrip.getDepartureDate());
							map.put("stayday", String.valueOf(stayday) + "天");
						}
					}
				}
			}
			//酒店信息

			if (!Util.isEmpty(record.getString("hotelname"))) {
				map.put("hotelname", record.getString("hotelname"));
			} else {
				map.put("hotelname", "参照'赴日予定表'");
			}
			map.put("hotelphone", record.getString("hotelphone"));
			map.put("hoteladdress", record.getString("hoteladdress"));
			//上次赴日日期及停留时间
			String lastinfo = "";
			if (!Util.isEmpty(applyorderjp.getLaststartdate()) && !Util.isEmpty(applyorderjp.getLastreturndate())) {
				lastinfo += dateFormat1.format(applyorderjp.getLaststartdate());
				lastinfo += "~";
				lastinfo += dateFormat1.format(applyorderjp.getLastreturndate());
				lastinfo += "      "
						+ (Util.isEmpty(applyorderjp.getLaststayday()) ? "" : applyorderjp.getLaststayday());
			}
			if (Util.isEmpty(lastinfo)) {
				map.put("fill_26", "无");
			} else {
				map.put("fill_26", lastinfo + "天");
			}
			//在日担保人信息
			if (!Util.isEmpty(record.getString("vouchname"))) {
				map.put("danbaoname", record.getString("vouchname"));
			} else {
				map.put("danbaoname", "参照'身元保证书'");
			}
			int lastIndexOf = record.getString("vouchname").lastIndexOf("-");
			//因为没有处理，暂时先空着
			map.put("danbaonameen", "");
			map.put("danbaotelephone", record.getString("vouchphone"));
			map.put("vouchaddress", record.getString("vouchaddress"));
			if ("男".equals(record.getString("vouchsex"))) {
				map.put("vouchnan", "0");
			} else if ("女".equals(record.getString("vouchsex"))) {
				map.put("vouchnv", "0");
			}
			if (!Util.isEmpty(record.get("vouchbirth"))) {
				map.put("danbaobirthday", dateformat.format((Date) record.get("vouchbirth")));
			}
			map.put("vouchmainrelation", record.getString("vouchmainrelation"));
			map.put("vouchjob", record.getString("vouchjob"));
			map.put("vouchcountry", record.getString("vouchcountry"));
			//在日邀请人
			map.put("invitename", record.getString("invitename"));
			map.put("invitephone", record.getString("invitephone"));
			map.put("inviteaddress", record.getString("inviteaddress"));
			if ("男".equals(record.getString("invitesex"))) {
				map.put("invitenan", "0");
			} else if ("女".equals(record.getString("invitesex"))) {
				map.put("invitenv", "0");
			}
			if (!Util.isEmpty(record.get("invitebirth"))) {
				map.put("invitebirth", dateformat.format((Date) record.get("invitebirth")));
			}
			map.put("invitejob", record.getString("invitejob"));
			map.put("invitemainrelation", record.getString("invitemainrelation"));
			map.put("invitecountry", record.getString("invitecountry"));
			//家庭住址
			map.put("homeaddress", (String) record.get("detailedaddress"));
			//家庭电话
			map.put("homephone", "无");
			//手机
			if (Util.isEmpty(record.getString("telephone"))) {
				map.put("homemobile", "无");
			} else {
				map.put("homemobile", record.getString("telephone"));
			}
			//电子邮箱
			if (Util.isEmpty(record.getString("email"))) {
				map.put("homeEmail", "无");
			} else {
				map.put("homeEmail", record.getString("email"));
			}
			//工作单位
			if (Util.isEmpty(record.getString("workname"))) {
				map.put("workname", "无");
			} else {
				map.put("workname", record.getString("workname"));
			}
			//工作电话
			if (Util.isEmpty(record.getString("workphone"))) {
				map.put("workphone", "无");
			} else {
				map.put("workphone", record.getString("workphone"));
			}
			//工作地址
			if (Util.isEmpty(record.getString("workaddress"))) {
				map.put("workaddress", "无");
			} else {
				map.put("workaddress", record.getString("workaddress"));
			}
			//目前的职位
			map.put("occupation", record.getString("position"));
			//配偶所从事的职业
			map.put("otheroccupation", record.getString("unitname"));
			//map.put("danbaoname", "参照身元保证书");
			//出生日期
			map.put("text2", "0");
			map.put("text3", "0");
			map.put("text4", "0");
			map.put("text5", "0");
			map.put("text6", "0");
			map.put("text7", "0");
			//申请日期
			map.put("applydate", dateformat.format(new Date()));
			map.put("applyname", record.getString("firstname") + record.getString("lastname"));
			//获取模板文件
			URL resource = getClass().getClassLoader().getResource("japanfile/shanghaibaicheng/apply.pdf");
			TemplateUtil templateUtil = new TemplateUtil();
			stream = templateUtil.pdfTemplateStream(resource, map);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return stream;
	}

	/**
	 * 酒店信息
	 */
	public ByteArrayOutputStream hotelInfo(Map<String, Object> tempdata) {
		DateFormat hoteldateformat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			//公司信息
			TCompanyEntity company = (TCompanyEntity) tempdata.get("company");
			//出行信息
			TOrderTripJpEntity ordertripjp = (TOrderTripJpEntity) tempdata.get("ordertripjp");
			//多程信息
			List<TOrderTripMultiJpEntity> mutiltrip = (List<TOrderTripMultiJpEntity>) tempdata.get("mutiltrip");
			//申请人信息
			List<Record> applyinfo = (List<Record>) tempdata.get("applyinfo");
			//日本订单信息
			TOrderJpEntity orderjp = (TOrderJpEntity) tempdata.get("orderjp");
			//行程安排
			List<TOrderTravelplanJpEntity> ordertravelplan = (List<TOrderTravelplanJpEntity>) tempdata
					.get("ordertravelplan");

			//护照号
			String passportNo = "";
			//主申请人名
			StringBuffer strb = new StringBuffer("");
			Collections.reverse(applyinfo);

			for (Record record : applyinfo) {
				//判断为主申请人
				if (Util.eq(1, record.get("ismainapplicant"))) {
					if (!Util.isEmpty(record.get("firstname"))) {
						strb.append(record.getString("firstname"));
					}
					if (!Util.isEmpty(record.get("lastname"))) {
						strb.append(record.getString("lastname"));
					}
					if (!Util.isEmpty(record.get("passportno"))) {
						passportNo = (String) record.get("passportno");
					}

				}
			}
			String mainApplyname = strb.toString();

			//PDF操作开始
			//五个表格组成
			Document document = new Document(PageSize.A4, 0, 0, 36, 36);
			PdfWriter.getInstance(document, stream);
			document.open();
			TtfClassLoader ttf = new TtfClassLoader();
			Font font = ttf.getFont();
			font.setSize(12f);
			font.setFamily("宋体");
			String dijie = "";
			if (!Util.isEmpty(orderjp.getGroundconnectid())) {
				TCompanyEntity dijiecompany = dbDao.fetch(TCompanyEntity.class, orderjp.getGroundconnectid()
						.longValue());
				dijie = dijiecompany.getName();
			}

			{
				//第一个，空行
				Paragraph p = new Paragraph("", font);
				p.setSpacingBefore(5);
				p.setSpacingAfter(5);
				p.setAlignment(Paragraph.ALIGN_CENTER);
				document.add(p);
				//第二个，地接社公司名
				Paragraph np = new Paragraph(dijie, font);
				np.setSpacingBefore(5);
				np.setAlignment(Paragraph.ALIGN_CENTER);
				document.add(np);
				//第三个，ホテル予約確認書
				Paragraph np2 = new Paragraph("ホテル予約確認書", font);
				np2.setSpacingAfter(5);
				np2.setAlignment(Paragraph.ALIGN_CENTER);
				document.add(np2);
				//第四个
				font.setSize(7.96f);
				Paragraph np3 = new Paragraph("                   以下为预定内容：", font);
				np3.setSpacingBefore(5);
				np3.setSpacingAfter(10);
				np3.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(np3);
			}

			//表格，表头

			float[] tcolumns = { 2, 2, 4, 2 };
			PdfPTable titable = new PdfPTable(tcolumns);
			titable.setWidthPercentage(75);
			titable.setTotalWidth(PageSize.A4.getWidth());

			//设置表头
			String titles[] = { "时间", "ホテル", "住所", "电话", };
			font.setSize(7.96f);
			for (String title : titles) {
				PdfPCell cell = new PdfPCell(new Paragraph(title, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(20);
				titable.addCell(cell);
			}
			document.add(titable);

			//主要数据表格
			float[] maincolumns = { 2, 2, 2, 1, 1, 2 };
			PdfPTable maintable = new PdfPTable(maincolumns);
			maintable.setWidthPercentage(75);
			maintable.setTotalWidth(PageSize.A4.getWidth());

			//数据处理
			//分三种情况：1、中间随机城市，去程抵达城市和返程出发城市是同一个城市。2、中间随机城市，去程抵达城市和返程出发城市不是同一个城市。3、不随机城市
			if (!Util.isEmpty(ordertripjp)) {
				List<TOrderTravelplanJpEntity> ordertravelplanList = dbDao.query(TOrderTravelplanJpEntity.class, Cnd
						.where("orderId", "=", orderjp.getId()).orderBy("outDate", "ASC"), null);
				Integer visatype = orderjp.getVisaType();
				if (!Util.isEmpty(ordertravelplanList)) {
					if (ordertravelplanList.get(1).getCityId() != ordertravelplanList.get(2).getCityId()) {//第二个和第三个城市不同，中间会随机别的城市
						ArrayList<Integer> cityidList = new ArrayList<Integer>();

						for (TOrderTravelplanJpEntity tOrderTravelplanJpEntity : ordertravelplanList) {
							if (!cityidList.contains(tOrderTravelplanJpEntity.getCityId())) {
								cityidList.add(tOrderTravelplanJpEntity.getCityId());
							}
						}

						if (ordertravelplanList.get(0).getCityId() != ordertravelplanList.get(
								ordertravelplanList.size() - 1).getCityId()) {
							for (int i = 0; i < cityidList.size(); i++) {
								List<TOrderTravelplanJpEntity> query = dbDao.query(TOrderTravelplanJpEntity.class, Cnd
										.where("orderId", "=", orderjp.getId()).and("cityId", "=", cityidList.get(i)),
										null);
								TOrderTravelplanJpEntity fetch = dbDao.fetch(TOrderTravelplanJpEntity.class,
										Cnd.where("orderId", "=", orderjp.getId())
												.and("cityId", "=", cityidList.get(i)));
								PdfPCell cell;

								//第一行
								//第一列，时间
								Date outDate = fetch.getOutDate();
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");
								Date addDay = DateUtil.addDay(outDate, query.size() - 1);
								String timeStampStr = simpleDateFormat.format(outDate);
								String addtimeStampStr = simpleDateFormat.format(addDay);
								String timeStr = timeStampStr + "-" + addtimeStampStr;
								cell = new PdfPCell(new Paragraph(timeStr, font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								maintable.addCell(cell);

								//第二列，酒店名称
								THotelEntity hotelinfo = hotelViewService.fetch(fetch.getHotel());
								String hotelname = "";
								hotelname = hotelinfo.getNamejp();
								cell = new PdfPCell(new Paragraph(hotelname, font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								maintable.addCell(cell);

								//第三列，酒店地址
								String hoteladdress = "";
								hoteladdress = hotelinfo.getAddressjp();
								cell = new PdfPCell(new Paragraph(hoteladdress, font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setColspan(3);
								cell.setFixedHeight(30);
								maintable.addCell(cell);

								//第四列，酒店电话
								String hotelmobile = "";
								hotelmobile = hotelinfo.getMobile();
								cell = new PdfPCell(new Paragraph(hotelmobile, font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								maintable.addCell(cell);

								//第二行
								//第一列
								cell = new PdfPCell(new Paragraph("", font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(20);
								maintable.addCell(cell);

								//第二列
								cell = new PdfPCell(new Paragraph("予約番号", font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(20);
								maintable.addCell(cell);

								//第三列
								cell = new PdfPCell(new Paragraph("チェックイン", font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(20);
								maintable.addCell(cell);

								//第四列
								cell = new PdfPCell(new Paragraph("泊数", font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(20);
								maintable.addCell(cell);

								//第五列
								cell = new PdfPCell(new Paragraph("部屋", font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(20);
								maintable.addCell(cell);

								//第六列
								cell = new PdfPCell(new Paragraph("食室", font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(20);
								maintable.addCell(cell);

								//第三行
								//第一列
								cell = new PdfPCell(new Paragraph("", font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								maintable.addCell(cell);

								//第二列
								String mainapplyinfo = mainApplyname + "-" + "\n" + passportNo;
								cell = new PdfPCell(new Paragraph(mainapplyinfo, font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								maintable.addCell(cell);

								//第三列
								cell = new PdfPCell(new Paragraph(timeStr, font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								maintable.addCell(cell);

								//第四列
								if (i == cityidList.size() - 1) {
									cell = new PdfPCell(new Paragraph(String.valueOf((query.size() - 1)), font));

								} else {
									cell = new PdfPCell(new Paragraph(String.valueOf(query.size()), font));

								}
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								maintable.addCell(cell);

								//第五列
								String room = "";
								if (applyinfo.size() > 0) {
									if (applyinfo.size() % 2 == 1) {
										room = String.valueOf(applyinfo.size() / 2 + 1);
									} else {
										room = String.valueOf(applyinfo.size() / 2);
									}
								}
								room = room + "SDF";
								cell = new PdfPCell(new Paragraph(room, font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								maintable.addCell(cell);

								//第六列
								cell = new PdfPCell(new Paragraph("早", font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								maintable.addCell(cell);

							}

						} else {
							PdfPCell cell;
							//第一行
							//第一列，时间
							Date outDate = ordertravelplanList.get(0).getOutDate();
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");
							Date addDay = DateUtil.addDay(outDate, 1);
							String timeStampStr = simpleDateFormat.format(outDate);
							String addtimeStampStr = simpleDateFormat.format(addDay);
							String timeStr = timeStampStr + "-" + addtimeStampStr;
							cell = new PdfPCell(new Paragraph(timeStr, font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(30);
							maintable.addCell(cell);

							//第二列，酒店名称
							THotelEntity hotelinfo = hotelViewService.fetch(ordertravelplanList.get(0).getHotel());
							String hotelname = "";
							hotelname = hotelinfo.getNamejp();
							cell = new PdfPCell(new Paragraph(hotelname, font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(30);
							maintable.addCell(cell);

							//第三列，酒店地址
							String hoteladdress = "";
							hoteladdress = hotelinfo.getAddressjp();
							cell = new PdfPCell(new Paragraph(hoteladdress, font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(30);
							cell.setColspan(3);
							maintable.addCell(cell);

							//第四列，酒店电话
							String hotelmobile = "";
							hotelmobile = hotelinfo.getMobile();
							cell = new PdfPCell(new Paragraph(hotelmobile, font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(30);
							maintable.addCell(cell);

							//第二行
							//第一列
							cell = new PdfPCell(new Paragraph("", font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(20);
							maintable.addCell(cell);

							//第二列
							cell = new PdfPCell(new Paragraph("予約番号", font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(20);
							maintable.addCell(cell);

							//第三列
							cell = new PdfPCell(new Paragraph("チェックイン", font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(20);
							maintable.addCell(cell);

							//第四列
							cell = new PdfPCell(new Paragraph("泊数", font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(20);
							maintable.addCell(cell);

							//第五列
							cell = new PdfPCell(new Paragraph("部屋", font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(20);
							maintable.addCell(cell);

							//第六列
							cell = new PdfPCell(new Paragraph("食室", font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(20);
							maintable.addCell(cell);

							//第三行
							//第一列
							cell = new PdfPCell(new Paragraph("", font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(30);
							maintable.addCell(cell);

							//第二列
							String mainapplyinfo = mainApplyname + "-" + "\n" + passportNo;
							cell = new PdfPCell(new Paragraph(mainapplyinfo, font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(30);
							maintable.addCell(cell);

							//第三列
							cell = new PdfPCell(new Paragraph(timeStr, font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(30);
							maintable.addCell(cell);

							//第四列
							cell = new PdfPCell(new Paragraph("2", font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(30);
							maintable.addCell(cell);

							//第五列
							String room = "";
							if (applyinfo.size() > 0) {
								if (applyinfo.size() % 2 == 1) {
									room = String.valueOf(applyinfo.size() / 2 + 1);
								} else {
									room = String.valueOf(applyinfo.size() / 2);
								}
							}
							room = room + "SDF";
							cell = new PdfPCell(new Paragraph(room, font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(30);
							maintable.addCell(cell);

							//第六列
							cell = new PdfPCell(new Paragraph("早", font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(30);
							maintable.addCell(cell);

							//剩下的行数
							Integer firstcityid = cityidList.get(0);
							cityidList.remove(0);
							cityidList.add(firstcityid);
							for (int i = 0; i < cityidList.size(); i++) {
								List<TOrderTravelplanJpEntity> query = dbDao.query(TOrderTravelplanJpEntity.class, Cnd
										.where("orderId", "=", orderjp.getId()).and("cityId", "=", cityidList.get(i)),
										null);
								TOrderTravelplanJpEntity fetch = null;
								if (i == cityidList.size() - 1) {//最后一个
									fetch = query.get(2);
								} else {
									fetch = dbDao.fetch(
											TOrderTravelplanJpEntity.class,
											Cnd.where("orderId", "=", orderjp.getId()).and("cityId", "=",
													cityidList.get(i)));
								}
								//第一行
								//第一列，时间
								Date outDate1 = fetch.getOutDate();
								String addtimeStampStr1 = "";
								if (i == cityidList.size() - 1) {
									Date addDay1 = DateUtil.addDay(outDate1, query.size() - 3);
									addtimeStampStr1 = simpleDateFormat.format(addDay1);
								} else {
									Date addDay1 = DateUtil.addDay(outDate1, query.size() - 1);
									addtimeStampStr1 = simpleDateFormat.format(addDay1);
								}
								String timeStampStr1 = simpleDateFormat.format(outDate1);
								String timeStr1 = timeStampStr1 + "-" + addtimeStampStr1;
								cell = new PdfPCell(new Paragraph(timeStr1, font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								maintable.addCell(cell);

								//第二列，酒店名称
								THotelEntity hotelinfo1 = hotelViewService.fetch(fetch.getHotel());
								String hotelname1 = "";
								hotelname1 = hotelinfo1.getNamejp();
								cell = new PdfPCell(new Paragraph(hotelname1, font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								maintable.addCell(cell);

								//第三列，酒店地址
								String hoteladdress1 = "";
								hoteladdress1 = hotelinfo1.getAddressjp();
								cell = new PdfPCell(new Paragraph(hoteladdress1, font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								cell.setColspan(3);
								maintable.addCell(cell);

								//第四列，酒店电话
								String hotelmobile1 = "";
								hotelmobile1 = hotelinfo1.getMobile();
								cell = new PdfPCell(new Paragraph(hotelmobile1, font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								maintable.addCell(cell);

								//第二行
								//第一列
								cell = new PdfPCell(new Paragraph("", font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(20);
								maintable.addCell(cell);

								//第二列
								cell = new PdfPCell(new Paragraph("予約番号", font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(20);
								maintable.addCell(cell);

								//第三列
								cell = new PdfPCell(new Paragraph("チェックイン", font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(20);
								maintable.addCell(cell);

								//第四列
								cell = new PdfPCell(new Paragraph("泊数", font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(20);
								maintable.addCell(cell);

								//第五列
								cell = new PdfPCell(new Paragraph("部屋", font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(20);
								maintable.addCell(cell);

								//第六列
								cell = new PdfPCell(new Paragraph("食室", font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(20);
								maintable.addCell(cell);

								//第三行
								//第一列
								cell = new PdfPCell(new Paragraph("", font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								maintable.addCell(cell);

								//第二列
								String mainapplyinfo1 = mainApplyname + "-" + "\n" + passportNo;
								cell = new PdfPCell(new Paragraph(mainapplyinfo1, font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								maintable.addCell(cell);

								//第三列
								cell = new PdfPCell(new Paragraph(timeStr, font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								maintable.addCell(cell);

								//第四列
								if (i == cityidList.size() - 1) {
									cell = new PdfPCell(new Paragraph(String.valueOf((query.size() - 3)), font));

								} else {
									cell = new PdfPCell(new Paragraph(String.valueOf(query.size()), font));

								}
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								maintable.addCell(cell);

								//第五列
								String room1 = "";
								if (applyinfo.size() > 0) {
									if (applyinfo.size() % 2 == 1) {
										room1 = String.valueOf(applyinfo.size() / 2 + 1);
									} else {
										room1 = String.valueOf(applyinfo.size() / 2);
									}
								}
								room1 = room1 + "SDF";
								cell = new PdfPCell(new Paragraph(room1, font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								maintable.addCell(cell);

								//第六列
								cell = new PdfPCell(new Paragraph("早", font));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setFixedHeight(30);
								maintable.addCell(cell);
							}

						}

					} else {//中间不随机城市
						PdfPCell cell;

						//第一行
						//第一列，时间
						Date outDate = ordertripjp.getGoDate();
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");
						Date addDay = DateUtil.addDay(outDate, ordertravelplanList.size() - 1);
						String timeStampStr = simpleDateFormat.format(outDate);
						String addtimeStampStr = simpleDateFormat.format(addDay);
						String timeStr = timeStampStr + "-" + addtimeStampStr;
						cell = new PdfPCell(new Paragraph(timeStr, font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(30);
						maintable.addCell(cell);

						//第二列，酒店名称
						TOrderTravelplanJpEntity travelplanEntity = ordertravelplanList.get(0);
						THotelEntity hotelinfo = hotelViewService.fetch(travelplanEntity.getHotel());
						String hotelname = "";
						hotelname = hotelinfo.getNamejp();
						cell = new PdfPCell(new Paragraph(hotelname, font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(30);
						maintable.addCell(cell);

						//第三列，酒店地址
						String hoteladdress = "";
						hoteladdress = hotelinfo.getAddressjp();
						cell = new PdfPCell(new Paragraph(hoteladdress, font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(30);
						cell.setColspan(3);
						maintable.addCell(cell);

						//第四列，酒店电话
						String hotelmobile = "";
						hotelmobile = hotelinfo.getMobile();
						cell = new PdfPCell(new Paragraph(hotelmobile, font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(30);
						maintable.addCell(cell);

						//第二行
						//第一列
						cell = new PdfPCell(new Paragraph("", font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(20);
						maintable.addCell(cell);

						//第二列
						cell = new PdfPCell(new Paragraph("予約番号", font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(20);
						maintable.addCell(cell);

						//第三列
						cell = new PdfPCell(new Paragraph("チェックイン", font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(20);
						maintable.addCell(cell);

						//第四列
						cell = new PdfPCell(new Paragraph("泊数", font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(20);
						maintable.addCell(cell);

						//第五列
						cell = new PdfPCell(new Paragraph("部屋", font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(20);
						maintable.addCell(cell);

						//第六列
						cell = new PdfPCell(new Paragraph("食室", font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(20);
						maintable.addCell(cell);

						//第三行
						//第一列
						cell = new PdfPCell(new Paragraph("", font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(30);
						maintable.addCell(cell);

						//第二列
						String mainapplyinfo = mainApplyname + "-" + "\n" + passportNo;
						cell = new PdfPCell(new Paragraph(mainapplyinfo, font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(30);
						maintable.addCell(cell);

						//第三列
						cell = new PdfPCell(new Paragraph(timeStr, font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(30);
						maintable.addCell(cell);

						//第四列
						int stayday = DateUtil.daysBetween(ordertripjp.getGoDate(), ordertripjp.getReturnDate());
						cell = new PdfPCell(new Paragraph(String.valueOf(stayday), font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(30);
						maintable.addCell(cell);

						//第五列
						String room = "";
						if (applyinfo.size() > 0) {
							if (applyinfo.size() % 2 == 1) {
								room = String.valueOf(applyinfo.size() / 2 + 1);
							} else {
								room = String.valueOf(applyinfo.size() / 2);
							}
						}
						room = room + "SDF";
						cell = new PdfPCell(new Paragraph(room, font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(30);
						maintable.addCell(cell);

						//第六列
						cell = new PdfPCell(new Paragraph("早", font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(30);
						maintable.addCell(cell);

					}
				}
			}

			document.add(maintable);

			{
				//第一行
				Paragraph p = new Paragraph(
						"                   注意事项：                     1.分房单：        必须在客人入住6天前确认传给我公司，并付清全款", font);
				p.setSpacingBefore(5);
				p.setSpacingAfter(5);
				p.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(p);
				//第二行
				Paragraph np = new Paragraph(
						"                                                  2.取消金：        入住日期的6天前取消订房不需要支付取消金", font);
				np.setSpacingAfter(5);
				np.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(np);
				//第三行
				Paragraph np2 = new Paragraph(
						"                                                                    若距离入住日期的5天内取消订房，则需支付取消金",
						font);
				np2.setSpacingAfter(5);
				np2.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(np2);
			}

			float[] explaincolumns = { 2.3f, 1.2f, 2 };
			PdfPTable explaintable = new PdfPTable(explaincolumns);
			explaintable.setWidthPercentage(51);
			explaintable.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			//explaintable.setTotalWidth(PageSize.A4.getWidth());

			PdfPCell cell;

			cell = new PdfPCell(new Paragraph("  ", font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(20);
			cell.setBorder(0);
			explaintable.addCell(cell);

			cell = new PdfPCell(new Paragraph("  5天", font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(20);
			explaintable.addCell(cell);
			cell = new PdfPCell(new Paragraph("  房费的10%", font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(20);
			explaintable.addCell(cell);

			cell = new PdfPCell(new Paragraph("  ", font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(20);
			cell.setBorder(0);
			explaintable.addCell(cell);
			cell = new PdfPCell(new Paragraph("  4天", font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(20);
			explaintable.addCell(cell);
			cell = new PdfPCell(new Paragraph("  房费的20%", font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(20);
			explaintable.addCell(cell);

			cell = new PdfPCell(new Paragraph("  ", font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(20);
			cell.setBorder(0);
			explaintable.addCell(cell);
			cell = new PdfPCell(new Paragraph("  3天", font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(20);
			explaintable.addCell(cell);
			cell = new PdfPCell(new Paragraph("  房费的30%", font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(20);
			explaintable.addCell(cell);

			cell = new PdfPCell(new Paragraph("  ", font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(20);
			cell.setBorder(0);
			explaintable.addCell(cell);
			cell = new PdfPCell(new Paragraph("  2天", font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(20);
			explaintable.addCell(cell);
			cell = new PdfPCell(new Paragraph("  房费的50%", font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(20);
			explaintable.addCell(cell);

			cell = new PdfPCell(new Paragraph("  ", font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(20);
			cell.setBorder(0);
			explaintable.addCell(cell);
			cell = new PdfPCell(new Paragraph("  1天", font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(20);
			explaintable.addCell(cell);
			cell = new PdfPCell(new Paragraph("  房费的80%", font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(20);
			explaintable.addCell(cell);

			cell = new PdfPCell(new Paragraph("  ", font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(20);
			cell.setBorder(0);
			explaintable.addCell(cell);
			cell = new PdfPCell(new Paragraph("  当天", font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(20);
			explaintable.addCell(cell);
			cell = new PdfPCell(new Paragraph("  房费的100%", font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(20);
			explaintable.addCell(cell);

			document.add(explaintable);

			{
				//第四行
				Paragraph p = new Paragraph(
						"                                                  3.以上规定适用于一般情况，若有特殊情况请以我方确认单上注册信息为准。", font);
				p.setSpacingBefore(5);
				p.setSpacingAfter(5);
				p.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(p);
				//第五行
				Paragraph np = new Paragraph(
						"                                                  确认上述内容和注意事项后请签字或加盖公司印章，并立即传回我公司。", font);
				np.setSpacingAfter(5);
				np.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(np);
				//第六行
				Paragraph np2 = new Paragraph("                   确认签字和盖章", font);
				np2.setSpacingBefore(15);
				np2.setSpacingAfter(5);
				np2.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(np2);
			}

			document.close();
			IOUtils.closeQuietly(stream);
			return stream;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stream;
	}

	/**
	 * 申请人名单
	 */
	public ByteArrayOutputStream applyList(Map<String, Object> tempdata) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DateFormat applydateformat = new SimpleDateFormat("yyyy-MM-dd");
		//公司信息
		TCompanyEntity company = (TCompanyEntity) tempdata.get("company");
		//订单信息
		TOrderEntity orderinfo = (TOrderEntity) tempdata.get("orderinfo");
		try {
			Document document = new Document(PageSize.A4.rotate(), 0, 0, 36, 36);
			TtfClassLoader ttf = new TtfClassLoader();
			PdfWriter.getInstance(document, stream);
			document.open();
			Font font = ttf.getFont();
			font.setFamily("宋体");
			Font font1 = ttf.getFont();
			font1.setFamily("宋体");
			font1.setSize(17);

			{
				Paragraph p = new Paragraph("签证申请人名单", font1);
				p.setSpacingBefore(5);
				p.setSpacingAfter(15);
				p.setIndentationLeft(20);
				p.setAlignment(Paragraph.ALIGN_CENTER);
				document.add(p);
			}

			float[] columns = { 2, 3, 4, 2, 3, 3, 3, 3, 3, 2, 3, 4, 3, 2, 4, };
			PdfPTable table = new PdfPTable(columns);
			table.setWidthPercentage(95);
			table.setTotalWidth(PageSize.A4.rotate().getWidth());

			//设置表头
			String titles[] = { "编号", "姓名(中文)", "姓名(英文)", "性别", "护照发行地", "居住地点", "出生年月日", "职业", "出境记录", "婚姻", "身份确认",
					"经济能力确认", "金额", "备注", "旅行社意见", };
			for (String title : titles) {
				PdfPCell cell = new PdfPCell(new Paragraph(title, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}
			List<Record> applyinfo = (List<Record>) tempdata.get("applyinfo");
			int count = 0;
			for (Record record : applyinfo) {
				count++;
				//出生日期
				String birthdaystr = "";
				if (!Util.isEmpty(record.get("birthday"))) {
					birthdaystr = applydateformat.format((Date) record.get("birthday"));
				}
				//职业
				String position = "";
				if (!Util.isEmpty(record.get("position"))) {
					position = (String) record.get("position");
				}
				String marryStatus = "";
				if (!Util.isEmpty(record.get("marrystatus"))) {
					for (MarryStatusEnum marrystatusenum : MarryStatusEnum.values()) {
						if (marrystatusenum.intKey() == record.getInt("marrystatus")) {
							marryStatus = marrystatusenum.value();
						}
					}
				}
				List<TApplicantWealthJpEntity> wealthjpinfo = (List<TApplicantWealthJpEntity>) record
						.get("wealthjpinfo");
				boolean flag = false;
				if (wealthjpinfo.size() > 0) {
					flag = true;
				}
				//
				PdfPCell cell;
				//序号
				cell = new PdfPCell(new Paragraph(String.valueOf(count), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				//中文姓名
				cell = new PdfPCell(new Paragraph(
						(!Util.isEmpty(record.get("firstname")) ? record.getString("firstname") : "")
								+ (!Util.isEmpty(record.get("lastname")) ? record.getString("lastname") : ""), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				//英文姓名
				cell = new PdfPCell(
						new Paragraph(
								(!Util.isEmpty(record.get("firstnameen")) ? record.getString("firstnameen") : "")
										+ " "
										+ (!Util.isEmpty(record.get("lastnameen")) ? record.getString("lastnameen")
												: ""), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				//性别
				cell = new PdfPCell(new Paragraph((!Util.isEmpty(record.get("sex")) ? record.getString("sex") : ""),
						font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				//护照签发地
				cell = new PdfPCell(new Paragraph(
						(!Util.isEmpty(record.get("issuedplace")) ? record.getString("issuedplace") : ""), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				//居住地
				String province = "";
				if (!Util.isEmpty(record.get("province"))) {
					province = (String) record.get("province");
					if (province.endsWith("省") || province.endsWith("市")) {
						province = province.substring(0, province.length() - 1);
					}
					if (province.length() > 3 && province.endsWith("自治区")) {
						province = province.substring(0, province.length() - 3);
					}
				}
				cell = new PdfPCell(new Paragraph(province, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				//出生日期
				cell = new PdfPCell(new Paragraph(birthdaystr, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				//职业
				cell = new PdfPCell(new Paragraph(position, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				//出境记录
				String outboundrecord = "良好";
				if (!Util.isEmpty(record.get("outboundrecord"))) {
					outboundrecord = (String) record.get("outboundrecord");
				}
				cell = new PdfPCell(new Paragraph(outboundrecord, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				//婚姻
				cell = new PdfPCell(new Paragraph(marryStatus, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				//身份确认
				String careerstatus = "";
				int career = 0;
				if (!Util.isEmpty(record.get("careerstatus"))) {
					career = (int) record.get("careerstatus");
				}

				if (career == 1) {
					careerstatus = "身份证\n户口本";
				}
				if (career == 2) {
					careerstatus = "身份证\n户口本\n退休证";
				}
				if (career == 3) {
					careerstatus = "身份证\n户口本";
				}
				if (career == 4) {
					careerstatus = "身份证\n户口本\n学生卡";
				}
				if (career == 5) {
					careerstatus = "户口本\n出生证明";
				}

				cell = new PdfPCell(new Paragraph(careerstatus, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				//如果财产信息不为空
				String wealthType = "";
				String detail = "";
				//其他财产
				if (wealthjpinfo.size() > 0) {
					for (int i = 0; i < wealthjpinfo.size(); i++) {
						TApplicantWealthJpEntity tApplicantWealthJpEntity = wealthjpinfo.get(i);
						//wealthType += tApplicantWealthJpEntity.getType() + "\n";

						Integer sequence = tApplicantWealthJpEntity.getSequence();
						detail += tApplicantWealthJpEntity.getDetails();
						if (sequence < 20) {
							if ("银行流水".equals(tApplicantWealthJpEntity.getType())) {
								if (!Util.isEmpty(tApplicantWealthJpEntity.getBankflowfree())) {
									wealthType += tApplicantWealthJpEntity.getBankflowfree() + "\n";
								} else {
									wealthType += tApplicantWealthJpEntity.getType() + "\n";
								}
								detail += "万\n";
							} else if ("理财".equals(tApplicantWealthJpEntity.getType())) {
								if (!Util.isEmpty(tApplicantWealthJpEntity.getFinancialfree())) {
									wealthType += tApplicantWealthJpEntity.getFinancialfree() + "\n";
								} else {
									wealthType += tApplicantWealthJpEntity.getType() + "\n";
								}
								detail += "万\n";
							} else if ("房产".equals(tApplicantWealthJpEntity.getType())) {
								if (!Util.isEmpty(tApplicantWealthJpEntity.getHousePropertyfree())) {
									wealthType += tApplicantWealthJpEntity.getHousePropertyfree() + "\n";
								} else {
									wealthType += tApplicantWealthJpEntity.getType() + "\n";
								}
								detail += "平米\n";
							} else if ("车产".equals(tApplicantWealthJpEntity.getType())) {
								if (!Util.isEmpty(tApplicantWealthJpEntity.getVehiclefree())) {
									wealthType += tApplicantWealthJpEntity.getVehiclefree() + "\n";
								} else {
									wealthType += tApplicantWealthJpEntity.getType() + "\n";
								}
								detail += "\n";
							} else if ("在职证明".equals(tApplicantWealthJpEntity.getType())) {
								if (!Util.isEmpty(tApplicantWealthJpEntity.getCertificatefree())) {
									wealthType += tApplicantWealthJpEntity.getCertificatefree() + "\n";
								} else {
									wealthType += tApplicantWealthJpEntity.getType() + "\n";
								}
								detail += "万\n";
							} else if ("银行存款".equals(tApplicantWealthJpEntity.getType())) {
								if (!Util.isEmpty(tApplicantWealthJpEntity.getDepositfree())) {
									wealthType += tApplicantWealthJpEntity.getDepositfree() + "\n";
								} else {
									wealthType += tApplicantWealthJpEntity.getType() + "\n";
								}
								detail += "万\n";
							} else if ("税单".equals(tApplicantWealthJpEntity.getType())) {
								if (!Util.isEmpty(tApplicantWealthJpEntity.getTaxbillfree())) {
									wealthType += tApplicantWealthJpEntity.getTaxbillfree() + "\n";
								} else {
									wealthType += tApplicantWealthJpEntity.getType() + "\n";
								}
								detail += "万\n";
							} else if ("完税证明".equals(tApplicantWealthJpEntity.getType())) {
								if (!Util.isEmpty(tApplicantWealthJpEntity.getTaxprooffree())) {
									wealthType += tApplicantWealthJpEntity.getTaxprooffree() + "\n";
								} else {
									wealthType += tApplicantWealthJpEntity.getType() + "\n";
								}
								detail += "元\n";
							} else if ("银行金卡".equals(tApplicantWealthJpEntity.getType())) {
								if (!Util.isEmpty(tApplicantWealthJpEntity.getGoldcardfree())) {
									wealthType += tApplicantWealthJpEntity.getGoldcardfree() + "\n";
								} else {
									wealthType += tApplicantWealthJpEntity.getType() + "\n";
								}
								detail += "\n";
							} else if ("特定高校在读生".equals(tApplicantWealthJpEntity.getType())) {
								if (!Util.isEmpty(tApplicantWealthJpEntity.getReadstudentfree())) {
									wealthType += tApplicantWealthJpEntity.getReadstudentfree() + "\n";
								} else {
									wealthType += tApplicantWealthJpEntity.getType() + "\n";
								}
								detail += "\n";
							} else if ("特定高校毕业生".equals(tApplicantWealthJpEntity.getType())) {
								if (!Util.isEmpty(tApplicantWealthJpEntity.getGraduatefree())) {
									wealthType += tApplicantWealthJpEntity.getGraduatefree() + "\n";
								} else {
									wealthType += tApplicantWealthJpEntity.getType() + "\n";
								}
								detail += "\n";
							}

						} else {
							if ("银行流水".equals(tApplicantWealthJpEntity.getType())
									|| "理财".equals(tApplicantWealthJpEntity.getType())
									|| "在职证明".equals(tApplicantWealthJpEntity.getType())
									|| "银行存款".equals(tApplicantWealthJpEntity.getType())
									|| "税单".equals(tApplicantWealthJpEntity.getType())) {
								if (!Util.isEmpty(tApplicantWealthJpEntity.getBankflowfree())) {
									wealthType += tApplicantWealthJpEntity.getBankflowfree() + "\n";
								}
								detail += "万\n";
							} else if ("房产".equals(tApplicantWealthJpEntity.getType())) {
								if (!Util.isEmpty(tApplicantWealthJpEntity.getBankflowfree())) {
									wealthType += tApplicantWealthJpEntity.getBankflowfree() + "\n";
								}
								detail += "平米\n";
							} else if ("完税证明".equals(tApplicantWealthJpEntity.getType())) {
								if (!Util.isEmpty(tApplicantWealthJpEntity.getBankflowfree())) {
									wealthType += tApplicantWealthJpEntity.getBankflowfree() + "\n";
								}
								detail += "元\n";
							} else {
								if (!Util.isEmpty(tApplicantWealthJpEntity.getBankflowfree())) {
									wealthType += tApplicantWealthJpEntity.getBankflowfree() + "\n";
								}
								detail += "\n";
							}
						}

						/*if (!record.get("isMainApplicant").equals(1)) {//副申请人
							if (wealthType.indexOf("银行流水") == -1) {
								wealthType = "银行流水\n" + wealthType;
								detail = "\n" + detail;
							}
						}*/

					}
					cell = new PdfPCell(new Paragraph(wealthType, font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Paragraph(detail, font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(new Paragraph("银行流水", font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph("", font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
				}
				//备注
				if (record.get("isMainApplicant").equals(1)) {
					cell = new PdfPCell(new Paragraph(
							(!Util.isEmpty(record.get("relationremark")) ? record.getString("relationremark") : ""),
							font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					/*if (flag) {
						cell.setRowspan(wealthjpinfo.size());
					}*/
					table.addCell(cell);
				} else {
					//主申请人姓名
					//					副申请人ID
					TApplicantEntity applicant = new TApplicantEntity();
					if (!Util.isEmpty(record.get("MainId"))) {
						applicant = dbDao.fetch(TApplicantEntity.class, Cnd.where("id", "=", record.get("MainId")));
					}
					cell = new PdfPCell(new Paragraph(
							(!Util.isEmpty(record.get("mainRelation")) ? record.getString("mainRelation") : ""), font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					/*if (flag) {
						cell.setRowspan(wealthjpinfo.size());
					}*/
					table.addCell(cell);

				}

				//旅行社意见
				cell = new PdfPCell(new Paragraph(
						(!Util.isEmpty(record.get("traveladvice")) ? record.getString("traveladvice") : ""), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}
			document.add(table);

			//表格下方数据
			//公司名称
			PdfPTable table2 = new PdfPTable(1); //表格一列
			table2.setWidthPercentage(95);
			table2.setHorizontalAlignment(Element.ALIGN_CENTER); //垂直居中
			table2.setTotalWidth(PageSize.A4.rotate().getWidth());
			//table2.setWidths(wid1);
			//table1.getDefaultCell().setBorderWidth(0); //不显示边框
			String comName = "旅行社名：" + company.getName();
			PdfPCell cell2 = new PdfPCell();
			Paragraph paragraph2 = new Paragraph(comName, font);
			paragraph2.setAlignment(Element.ALIGN_RIGHT);
			cell2.addElement(paragraph2);
			cell2.setBorder(0);
			//			paragraph2.setSpacingAfter(200);
			table2.addCell(cell2);
			//日期
			PdfPCell cell3 = new PdfPCell();
			Paragraph paragraph3 = null;
			//日期格式化
			DateFormat exceldateformat = new SimpleDateFormat("yyyy年MM月dd日");
			Date sendVisaDate = orderinfo.getSendVisaDate();
			if (!Util.isEmpty(sendVisaDate)) {
				paragraph3 = new Paragraph(exceldateformat.format(sendVisaDate), font);
			} else {
				paragraph3 = new Paragraph("", font);
			}
			paragraph3.setAlignment(Element.ALIGN_RIGHT);
			cell3.addElement(paragraph3);
			cell3.setBorder(0);
			table2.addCell(cell3);
			table2.getDefaultCell().setBorderWidth(0);
			document.add(table2);

			document.close();
			IOUtils.closeQuietly(stream);
			return stream;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stream;
	}

	/**
	 * 航班信息
	 */
	public ByteArrayOutputStream flightinfo(Map<String, Object> tempdata) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			//公司信息
			TCompanyEntity company = (TCompanyEntity) tempdata.get("company");
			//出行信息
			TOrderTripJpEntity ordertripjp = (TOrderTripJpEntity) tempdata.get("ordertripjp");
			//申请人信息
			List<Record> applyinfo = (List<Record>) tempdata.get("applyinfo");
			//日本订单信息
			TOrderJpEntity orderjp = (TOrderJpEntity) tempdata.get("orderjp");
			Map<String, String> map = new HashMap<String, String>();
			StringBuffer strb = new StringBuffer("");
			for (Record record : applyinfo) {
				if (!Util.isEmpty(record.get("firstnameen"))) {
					strb.append(record.getString("firstnameen"));
				}
				if (!Util.isEmpty(record.get("lastnameen"))) {
					strb.append(record.getString("lastnameen"));
				}
				strb.append("\n");
			}
			map.put("guest", strb.toString().toUpperCase());
			//获取模板文件
			URL resource = getClass().getClassLoader().getResource("japanfile/flight.pdf");
			TemplateUtil templateUtil = new TemplateUtil();
			stream = templateUtil.pdfTemplateStream(resource, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stream;
	}

	/**
	 * 滞在予定表
	 */
	public ByteArrayOutputStream tripInfo(Map<String, Object> tempdata) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			Map<String, String> map = new HashMap<String, String>();
			//公司信息
			TCompanyEntity company = (TCompanyEntity) tempdata.get("company");
			//出行信息
			TOrderTripJpEntity ordertripjp = (TOrderTripJpEntity) tempdata.get("ordertripjp");
			//多程信息
			List<TOrderTripMultiJpEntity> mutiltrip = (List<TOrderTripMultiJpEntity>) tempdata.get("mutiltrip");
			//申请人信息
			List<Record> applyinfo = (List<Record>) tempdata.get("applyinfo");
			//日本订单信息
			TOrderJpEntity orderjp = (TOrderJpEntity) tempdata.get("orderjp");
			//行程安排
			List<TOrderTravelplanJpEntity> ordertravelplans = (List<TOrderTravelplanJpEntity>) tempdata
					.get("ordertravelplan");
			//PDF操作开始
			Document document = new Document(PageSize.A4, 0, 0, 36, 36);
			PdfWriter.getInstance(document, stream);
			document.open();
			TtfClassLoader ttf = new TtfClassLoader();
			Font font = ttf.getFont();
			font.setSize(22);
			font.setFamily("宋体");
			{
				Paragraph p = new Paragraph("滞在予定表", font);
				p.setSpacingBefore(5);
				p.setSpacingAfter(5);
				p.setAlignment(Paragraph.ALIGN_CENTER);
				document.add(p);
			}
			font.setSize(10);
			//日期格式化
			//String pattern = "yy年MM月dd日";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat pattern = new SimpleDateFormat("yy年MM月dd日");
			String pingcheng = "yy年MM月dd日";
			String applyname = "";
			int dengsize = 0;
			int boysize = 0;
			int grilsize = 0;
			if (!Util.isEmpty(applyinfo)) {
				Record record = applyinfo.get(0);
				applyname += record.getString("firstname");
				applyname += record.getString("lastname");
				for (Record records : applyinfo) {
					if (!Util.isEmpty(records.get("sex"))) {
						if ("男".equals(records.getString("sex"))) {
							boysize++;
						} else {
							grilsize++;
						}
					}
				}
				dengsize = applyinfo.size() - 1;
			}
			//副标题1
			{
				String text = applyname + " ， 他 " + dengsize + " 名(男" + boysize + " / 女" + grilsize + ")";
				Paragraph p = new Paragraph(text, font);
				//				p.setSpacingAfter(5);
				p.setIndentationRight(13);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			//副标题2
			String godatestr = "";
			String returndatestr = "";
			if (!Util.isEmpty(ordertripjp)) {
				if (ordertripjp.getTripType().equals(1)) {
					if (!Util.isEmpty(ordertripjp.getGoDate())) {
						//godatestr = pattern.format(ordertripjp.getGoDate());
						godatestr = format(ordertripjp.getGoDate(), pingcheng);
					}
					if (!Util.isEmpty(ordertripjp.getReturnDate())) {
						//returndatestr = pattern.format(ordertripjp.getReturnDate());
						returndatestr = format(ordertripjp.getReturnDate(), pingcheng);
					}
				} else if (ordertripjp.getTripType().equals(2)) {
					if (!Util.isEmpty(mutiltrip)) {
						//多程第一程为出发日期
						TOrderTripMultiJpEntity entrytrip = mutiltrip.get(0);
						if (!Util.isEmpty(entrytrip.getDepartureDate())) {
							//godatestr = pattern.format(entrytrip.getDepartureDate());
							godatestr = format(entrytrip.getDepartureDate(), pingcheng);
						}
						//最后一程作为返回日期
						TOrderTripMultiJpEntity returntrip = mutiltrip.get(mutiltrip.size() - 1);
						if (!Util.isEmpty(returntrip.getDepartureDate())) {
							//截取月 日
							//returndatestr = pattern.format(returntrip.getDepartureDate());
							returndatestr = format(returntrip.getDepartureDate(), pingcheng);
						}
					}
				}
			}

			{
				//				String subtitle = "（平成" + godatestr + "から平成" + returndatestr + "）";
				String subtitle = "(平成" + godatestr + " - 平成" + returndatestr + ")";
				Paragraph p = new Paragraph(subtitle, font);
				//				p.setSpacingBefore(5);
				p.setIndentationRight(13);
				p.setSpacingAfter(5);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				//添加副标题1
				document.add(p);
			}

			//表格
			float[] columns = { 3, 5, 5 };
			PdfPTable table = new PdfPTable(columns);
			table.setWidthPercentage(95);
			table.setTotalWidth(PageSize.A4.getWidth());

			//设置表头
			String titles[] = { "年月日", "行動予定", "宿泊先", };
			font.setSize(7.98f);
			for (String title : titles) {
				PdfPCell cell = new PdfPCell(new Paragraph(title, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}
			//格式化为日本的日期
			String pointpattren = "yyyy-MM-dd";
			int count = 0;
			Integer lasthotel = null;
			for (TOrderTravelplanJpEntity ordertravelplan : ordertravelplans) {
				count++;
				//行程安排
				String scenic = "";
				//出发城市
				Integer goDepartureCity = ordertripjp.getGoDepartureCity();
				TCityEntity goCity = dbDao.fetch(TCityEntity.class, goDepartureCity.longValue());
				String province = goCity.getProvince();
				if (province.endsWith("省") || province.endsWith("市")) {
					province = province.substring(0, province.length() - 1);
				}
				if (province.length() > 3 && province.endsWith("自治区")) {
					province = province.substring(0, province.length() - 3);
				}
				if (count == 1) {
					if (ordertripjp.getTripType().equals(1)) {
						TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,
								Cnd.where("flightnum", "=", ordertripjp.getGoFlightNum()));
						String goFlightNum = ordertripjp.getGoFlightNum();
						/*scenic = " " + province + "から" + goflight.getFlightnum().replace("*", "") + "便にて"
								+ goflight.getLandingName() + "へ" + "\n 到着後、ホテルへ";*/
						scenic = province
								+ "から"
								+ goFlightNum.substring(goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")) + 1,
										goFlightNum.indexOf(" ", goFlightNum.indexOf(" ") + 1))
								+ "便にて"
								+ goFlightNum.substring(goFlightNum.indexOf("-", goFlightNum.lastIndexOf("-")) + 1,
										goFlightNum.indexOf(" ", goFlightNum.indexOf(" "))) + "へ" + "\n到着後、ホテルへ";
					} else if (ordertripjp.getTripType().equals(2)) {
						//多程出发航班
						if (!Util.isEmpty(mutiltrip)) {
							//多程第一程为出发日期
							TOrderTripMultiJpEntity entrytrip = mutiltrip.get(0);
							TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,
									Cnd.where("flightnum", "=", entrytrip.getFlightNum()));
							String goFlightNum = entrytrip.getFlightNum();
							/*scenic = " " + province + goflight.getFlightnum().replace("*", "") + "便にて"
									+ goflight.getLandingName() + "へ" + "\n 到着後、ホテルへ";*/
							scenic = province
									+ goFlightNum.substring(goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")) + 1,
											goFlightNum.indexOf(" ", goFlightNum.indexOf(" ") + 1))
									+ "便にて"
									+ goFlightNum.substring(goFlightNum.indexOf("-", goFlightNum.lastIndexOf("-")) + 1,
											goFlightNum.indexOf(" ", goFlightNum.indexOf(" "))) + "へ" + "\n到着後、ホテルへ";
						}
					}
				} else if (count == ordertravelplans.size()) {
					if (ordertripjp.getTripType().equals(1)) {
						TFlightEntity returnflight = dbDao.fetch(TFlightEntity.class,
								Cnd.where("flightnum", "=", ordertripjp.getReturnFlightNum()));
						String goFlightNum = ordertripjp.getReturnFlightNum();
						/*scenic = " " + returnflight.getTakeOffName() + "から"
								+ returnflight.getFlightnum().replace("*", "") + "便にて帰国";*/
						scenic = goFlightNum.substring(0, goFlightNum.indexOf("-", goFlightNum.indexOf("-")))
								+ "から"
								+ goFlightNum.substring(goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")) + 1,
										goFlightNum.indexOf(" ", goFlightNum.indexOf(" ") + 1)) + "便にて帰国";
					} else if (ordertripjp.getTripType().equals(2)) {
						//多程出发航班
						if (!Util.isEmpty(mutiltrip)) {
							//最后一程作为返回日期
							TOrderTripMultiJpEntity returntrip = mutiltrip.get(mutiltrip.size() - 1);
							TFlightEntity returnflight = dbDao.fetch(TFlightEntity.class,
									Cnd.where("flightnum", "=", returntrip.getFlightNum()));
							String goFlightNum = returntrip.getFlightNum();
							/*scenic = " " + returnflight.getTakeOffName() + "から"
									+ returnflight.getFlightnum().replace("*", "") + "便にて帰国";*/
							scenic = goFlightNum.substring(0, goFlightNum.indexOf("-", goFlightNum.indexOf("-")))
									+ "から"
									+ goFlightNum.substring(goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")) + 1,
											goFlightNum.indexOf(" ", goFlightNum.indexOf(" ") + 1)) + "便にて帰国";
						}
					}
				} else {
					scenic = ordertravelplan.getScenic();
				}
				//酒店信息
				String hotel = "";
				if (!Util.isEmpty(ordertravelplan.getHotel())) {
					String day = ordertravelplan.getDay();
					Integer intDay = Integer.valueOf(day);
					if (intDay > 1) {
						if (intDay != ordertravelplans.size()) {//不是最后一天
							TOrderTravelplanJpEntity beforeplan = dbDao.fetch(TOrderTravelplanJpEntity.class, Cnd
									.where("orderId", "=", ordertravelplan.getOrderId()).and("day", "=", intDay - 1));
							if (!Util.eq(ordertravelplan.getHotel(), beforeplan.getHotel())) {
								THotelEntity hotelentity = hotelViewService.fetch(ordertravelplan.getHotel());
								hotel = hotelentity.getNamejp() + "\n" + hotelentity.getAddressjp() + "\n"
										+ hotelentity.getMobile();
							} else {
								hotel = "同上";
							}
						} else {
							hotel = "";
						}
					} else {
						THotelEntity hotelentity = hotelViewService.fetch(ordertravelplan.getHotel());
						hotel = hotelentity.getNamejp() + "\n" + hotelentity.getAddressjp() + "\n"
								+ hotelentity.getMobile();
					}

				}
				String[] datas = { sdf.format(ordertravelplan.getOutDate()), scenic, hotel };
				for (String title : datas) {
					PdfPCell cell = new PdfPCell(new Paragraph(title, font));
					if (Util.eq(datas[1], title)) {
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPaddingLeft(4);
					} else {
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					}
					table.addCell(cell);
				}
				lasthotel = ordertravelplan.getHotel();
			}
			document.add(table);
			//表格的高度
			float totalHeight = table.getTotalHeight();
			//PDF的高度
			float height = document.getPageSize().getHeight();
			//底部
			//公章地址
			String sealUrl = "";
			if (!Util.isEmpty(orderjp.getGroundconnectid())) {
				TCompanyEntity dijiecompany = dbDao.fetch(TCompanyEntity.class, orderjp.getGroundconnectid()
						.longValue());
				sealUrl = dijiecompany.getSeal();
			}
			if (!Util.isEmpty(sealUrl)) {

				URL url = new URL(sealUrl);
				//添加盖章
				//Image img = Image.getInstance(getClass().getClassLoader().getResource(getPrefix() + "sealnew.jpg"));
				Image img = Image.getInstance(url);
				img.setAlignment(Image.RIGHT);
				//		img.scaleToFit(400, 200);//大小
				img.scaleToFit(280, 180);//大小
				//img.setIndentationRight(200);
				img.setRotation(800);

				img.setAbsolutePosition(350, height - totalHeight - 300);
				img.setAlignment(Paragraph.ALIGN_RIGHT);

				document.add(img);
				//document.add(getSeal1(sealUrl, 4));
				//				document.add(getSeal1(sealUrl, ordertravelplans.size()));
			}
			document.close();
			IOUtils.closeQuietly(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stream;
	}

	/**
	 *格式化年为小日本的日期
	 */
	public String format(Date date, String pattern) {
		DateTime dt = new DateTime(date);
		return new SimpleDateFormat(pattern).format(dt.plusYears(12).toDate());
	}

	/**
	 * 身元
	 */
	public ByteArrayOutputStream bodyElement(Map<String, Object> tempdata) {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("japanfile/word.docx");
		Map<String, Object> map = new HashMap<String, Object>();
		DateFormat bodydateformat = new SimpleDateFormat("yy");
		//出行信息
		TOrderTripJpEntity ordertripjp = (TOrderTripJpEntity) tempdata.get("ordertripjp");
		//多程信息
		List<TOrderTripMultiJpEntity> mutiltrip = (List<TOrderTripMultiJpEntity>) tempdata.get("mutiltrip");
		//申请人信息
		List<Record> applyinfo = (List<Record>) tempdata.get("applyinfo");
		//日期
		String year = bodydateformat.format(new DateTime().plusYears(12).toDate());
		int month = DateUtil.getMonth(new Date());
		int day = DateUtil.getDay(new Date());

		//申请人
		String applyname = "";
		int dengsize = 0;
		if (!Util.isEmpty(applyinfo)) {
			Record record = applyinfo.get(0);
			applyname += record.getString("firstname");
			applyname += record.getString("lastname");
			dengsize = applyinfo.size() - 1;
		}
		String sYear = "";
		Integer sMonth = null;
		Integer sDay = null;
		String eYear = "";
		Integer eMonth = null;
		Integer eDay = null;
		if (!Util.isEmpty(ordertripjp)) {
			if (ordertripjp.getTripType().equals(1)) {
				//往返的时候选择出发返回日期
				if (!Util.isEmpty(ordertripjp.getGoDate())) {
					sYear = bodydateformat.format(new DateTime(ordertripjp.getGoDate()).plusYears(12).toDate());
					sMonth = DateUtil.getMonth(ordertripjp.getGoDate());
					sDay = DateUtil.getDay(ordertripjp.getGoDate());
				}
				if (!Util.isEmpty(ordertripjp.getReturnDate())) {
					eYear = bodydateformat.format(new DateTime(ordertripjp.getReturnDate()).plusYears(12).toDate());
					eMonth = DateUtil.getMonth(ordertripjp.getReturnDate());
					eDay = DateUtil.getDay(ordertripjp.getReturnDate());
				}
			} else if (ordertripjp.getTripType().equals(2)) {
				if (!Util.isEmpty(mutiltrip)) {
					//多程第一程为出发日期
					TOrderTripMultiJpEntity entrytrip = mutiltrip.get(0);
					if (!Util.isEmpty(entrytrip.getDepartureDate())) {
						sYear = bodydateformat
								.format(new DateTime(entrytrip.getDepartureDate()).plusYears(12).toDate());
						sMonth = DateUtil.getMonth(entrytrip.getDepartureDate());
						sDay = DateUtil.getDay(entrytrip.getDepartureDate());
					}
					//最后一程作为返回日期
					TOrderTripMultiJpEntity returntrip = mutiltrip.get(mutiltrip.size() - 1);
					if (!Util.isEmpty(returntrip.getDepartureDate())) {
						eYear = bodydateformat.format(new DateTime(returntrip.getDepartureDate()).plusYears(12)
								.toDate());
						eMonth = DateUtil.getMonth(returntrip.getDepartureDate());
						eDay = DateUtil.getDay(returntrip.getDepartureDate());
					}
				}
			}
		}
		map.put("year", year);
		map.put("mouth", month);
		map.put("day", day);
		map.put("name", applyname);
		map.put("count", dengsize);
		map.put("sYear", sYear);
		map.put("sMouth", sMonth);
		map.put("sDay", sDay);
		map.put("eYear", eYear);
		map.put("eMouth", eMonth);
		map.put("eDay", eDay);
		map.put("reason", " ");
		map.put("type", " ");
		TemplateUtil templateUtil = new TemplateUtil();
		ByteArrayOutputStream wordStream = templateUtil.toWordStream(inputStream, map);

		return wordStream;
	}

	/**
	 * 航班
	 */
	public ByteArrayOutputStream airticket(Map<String, Object> tempdata) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			//公司信息
			TCompanyEntity company = (TCompanyEntity) tempdata.get("company");
			//出行信息
			TOrderTripJpEntity ordertripjp = (TOrderTripJpEntity) tempdata.get("ordertripjp");
			//申请人信息
			List<Record> applyinfo = (List<Record>) tempdata.get("applyinfo");
			//日本订单信息
			TOrderJpEntity orderjp = (TOrderJpEntity) tempdata.get("orderjp");
			//多程信息
			List<TOrderTripMultiJpEntity> mutiltrip = (List<TOrderTripMultiJpEntity>) tempdata.get("mutiltrip");
			DateFormat df = new SimpleDateFormat("ddMMM", Locale.ENGLISH);
			Map<String, String> map = new HashMap<String, String>();
			StringBuffer strb = new StringBuffer("");

			//PDF操作开始
			Document document = new Document(PageSize.A4, 0, 0, 36, 36);
			PdfWriter.getInstance(document, stream);
			document.open();
			TtfClassLoader ttf = new TtfClassLoader();
			Font font = ttf.getFont();
			font.setSize(15);
			//第一行
			String firstStr = "";
			String genderstr = " ";
			for (Record record : applyinfo) {

				{
					if (!Util.isEmpty(record.get("sex")) && "男".equals(record.getString("sex"))) {
						genderstr = " MR";
					} else {
						genderstr = " MS";
					}
					firstStr += record.getString("firstnameen") + " " + record.getString("lastnameen") + genderstr
							+ ";";
				}

			}
			Paragraph p = new Paragraph("1." + firstStr, font);
			p.setSpacingBefore(5);
			p.setIndentationLeft(20);
			p.setAlignment(Paragraph.ALIGN_LEFT);
			document.add(p);

			//第二行
			//applyinfo.size()
			String secondStr = "";
			String thirdStr = "";
			String fourthStr = "";
			String fifthStr = "";

			if (!Util.isEmpty(ordertripjp)) {
				if (ordertripjp.getTripType().equals(1)) {
					//入境航班
					if (!Util.isEmpty(ordertripjp.getGoFlightNum())) {
						String goFlightNum = ordertripjp.getGoFlightNum();
						//分直飞和转机
						if (goFlightNum.contains("//")) {//转机
							//第一行，共2行
							//机场
							//第一个机场名 
							String firstflight = goFlightNum.substring(0,
									goFlightNum.indexOf("-", goFlightNum.indexOf("-")));

							//第二个机场 
							String secondflight = goFlightNum.substring(goFlightNum.indexOf("-") + 1,
									goFlightNum.lastIndexOf("-"));

							//最后一个机场名 
							String lastflight = goFlightNum.substring(goFlightNum.lastIndexOf("-") + 1,
									goFlightNum.indexOf(" "));

							TFlightEntity firstname = dbDao.fetch(TFlightEntity.class,
									Cnd.where("takeOffName", "=", firstflight));
							TFlightEntity secondname = dbDao.fetch(TFlightEntity.class,
									Cnd.where("landingName", "=", secondflight));
							TFlightEntity lastname = dbDao.fetch(TFlightEntity.class,
									Cnd.where("landingName", "=", lastflight));

							//始发地/目的地
							map.put("Text2", firstname.getTakeOffCode() + "/" + secondname.getLandingCode());
							//航班
							map.put("Text5",
									goFlightNum.substring(goFlightNum.indexOf(" ") + 1, goFlightNum.indexOf("//")));
							//日期
							map.put("Text9", df.format(ordertripjp.getGoDate()).toUpperCase());
							//起飞时间
							map.put("Text11",
									goFlightNum.substring(goFlightNum.lastIndexOf(" ") + 1,
											goFlightNum.lastIndexOf(" ") + 5).substring(0, 2)
											+ ":"
											+ goFlightNum.substring(goFlightNum.lastIndexOf(" ") + 1,
													goFlightNum.lastIndexOf(" ") + 5).substring(2, 4));
							//到达时间
							map.put("Text13",
									goFlightNum.substring(goFlightNum.lastIndexOf("//") - 4,
											goFlightNum.lastIndexOf("//")).substring(0, 2)
											+ ":"
											+ goFlightNum.substring(goFlightNum.lastIndexOf("//") - 4,
													goFlightNum.lastIndexOf("//")).substring(2, 4));
							//有效期
							Date addDay = DateUtil.addDay(ordertripjp.getGoDate(), 8);
							map.put("Text15", df.format(addDay).toUpperCase());

							secondStr = "2."
									+ goFlightNum.substring(goFlightNum.indexOf(" ") + 1, goFlightNum.indexOf("//"))
									+ " Y "
									+ df.format(ordertripjp.getGoDate()).toUpperCase()
									+ " "
									+ firstname.getTakeOffCode()
									+ secondname.getLandingCode()
									+ " HK"
									+ applyinfo.size()
									+ " "
									+ goFlightNum.substring(goFlightNum.lastIndexOf(" ") + 1,
											goFlightNum.lastIndexOf(" ") + 5)
									+ "/"
									+ goFlightNum.substring(goFlightNum.lastIndexOf("//") - 4,
											goFlightNum.lastIndexOf("//"));

							//第二行
							//始发地/目的地
							map.put("Text3", secondname.getLandingCode() + "/" + lastname.getLandingCode());
							//航班
							map.put("Text6",
									goFlightNum.substring(goFlightNum.indexOf("//") + 2, goFlightNum.lastIndexOf(" ")));
							//日期
							map.put("Text10", df.format(ordertripjp.getGoDate()).toUpperCase());
							//起飞时间
							map.put("Text12",
									goFlightNum.substring(goFlightNum.lastIndexOf("//") + 2,
											goFlightNum.lastIndexOf("/")).substring(0, 2)
											+ ":"
											+ goFlightNum.substring(goFlightNum.lastIndexOf("//") + 2,
													goFlightNum.lastIndexOf("/")).substring(2, 4));
							//到达时间
							map.put("Text14", goFlightNum.substring(goFlightNum.lastIndexOf("/") + 1).substring(0, 2)
									+ ":" + goFlightNum.substring(goFlightNum.lastIndexOf("/") + 1).substring(2, 4));
							//有效期
							map.put("Text16", df.format(addDay).toUpperCase());
							thirdStr = "3."
									+ goFlightNum
											.substring(goFlightNum.indexOf("//") + 2, goFlightNum.lastIndexOf(" "))
									+ " Y "
									+ df.format(ordertripjp.getGoDate()).toUpperCase()
									+ " "
									+ secondname.getLandingCode()
									+ lastname.getLandingCode()
									+ " HK"
									+ applyinfo.size()
									+ " "
									+ goFlightNum.substring(goFlightNum.lastIndexOf("//") + 2,
											goFlightNum.lastIndexOf("/")) + "/"
									+ goFlightNum.substring(goFlightNum.lastIndexOf("/") + 1);

						} else {//直飞
							//只需占一行
							//第一个机场
							String firstflight = goFlightNum.substring(0,
									goFlightNum.indexOf("-", goFlightNum.indexOf("-")));
							//第二个机场 
							String secondflight = goFlightNum.substring(goFlightNum.indexOf("-") + 1,
									goFlightNum.indexOf(" "));

							TFlightEntity firstname = dbDao.fetch(TFlightEntity.class,
									Cnd.where("takeOffName", "=", firstflight));
							TFlightEntity secondname = dbDao.fetch(TFlightEntity.class,
									Cnd.where("landingName", "=", secondflight));

							//起飞时间
							String takeofftime = goFlightNum.substring(goFlightNum.lastIndexOf(" ") + 1,
									goFlightNum.indexOf("/"));
							//降落时间
							String landingtime = goFlightNum.substring(goFlightNum.indexOf("/") + 1);
							//始发地/目的地
							map.put("Text2", firstname.getTakeOffCode() + "/" + secondname.getLandingCode());
							//起飞航班号
							map.put("Text5",
									goFlightNum.substring(goFlightNum.indexOf(" ") + 1, goFlightNum.lastIndexOf(" ")));
							//起飞时间 
							map.put("Text11", takeofftime.substring(0, 2) + ":" + takeofftime.substring(2, 4));
							//降落时间
							map.put("Text13", landingtime.substring(0, 2) + ":" + landingtime.substring(2, 4));

							//出发航班日期
							map.put("Text9", df.format(ordertripjp.getGoDate()).toUpperCase());
							//有效期（出发日期+8天）
							Date addDay = DateUtil.addDay(ordertripjp.getGoDate(), 8);
							map.put("Text15", df.format(addDay).toUpperCase());

							secondStr = "2."
									+ goFlightNum.substring(goFlightNum.indexOf(" ") + 1, goFlightNum.lastIndexOf(" "))
									+ " Y " + df.format(ordertripjp.getGoDate()).toUpperCase() + " "
									+ firstname.getTakeOffCode() + secondname.getLandingCode() + " HK"
									+ applyinfo.size() + " " + takeofftime + "/" + landingtime;
						}
					}

					//出境航班
					if (!Util.isEmpty(ordertripjp.getReturnFlightNum())) {
						//需要根据入境航班是转机还是直飞来区分
						String goFlightNum = ordertripjp.getGoFlightNum();
						String returnFlightNum = ordertripjp.getReturnFlightNum();
						//如果去程是转机，则返程从模板第三行开始，否则从第二行开始
						if (!goFlightNum.contains("//")) {//直飞，从第二行开始
							if (returnFlightNum.contains("//")) {//转机
								//第一个机场名 
								String firstflight = returnFlightNum.substring(0,
										returnFlightNum.indexOf("-", returnFlightNum.indexOf("-")));

								//第二个机场 
								String secondflight = returnFlightNum.substring(returnFlightNum.indexOf("-") + 1,
										returnFlightNum.lastIndexOf("-"));

								//最后一个机场名 
								String lastflight = returnFlightNum.substring(returnFlightNum.lastIndexOf("-") + 1,
										returnFlightNum.indexOf(" "));

								TFlightEntity firstname = dbDao.fetch(TFlightEntity.class,
										Cnd.where("takeOffName", "=", firstflight));
								TFlightEntity secondname = dbDao.fetch(TFlightEntity.class,
										Cnd.where("landingName", "=", secondflight));
								TFlightEntity lastname = dbDao.fetch(TFlightEntity.class,
										Cnd.where("landingName", "=", lastflight));
								//第一行 共2行
								//始发地/目的地
								map.put("Text3", firstname.getTakeOffCode() + "/" + secondname.getLandingCode());
								//航班
								map.put("Text6",
										returnFlightNum.substring(returnFlightNum.indexOf(" ") + 1,
												returnFlightNum.indexOf("//")));
								//日期
								map.put("Text10", df.format(ordertripjp.getReturnDate()).toUpperCase());
								//起飞时间
								map.put("Text12",
										returnFlightNum.substring(returnFlightNum.lastIndexOf(" ") + 1,
												returnFlightNum.lastIndexOf(" ") + 5).substring(0, 2)
												+ ":"
												+ returnFlightNum.substring(returnFlightNum.lastIndexOf(" ") + 1,
														returnFlightNum.lastIndexOf(" ") + 5).substring(2, 4));
								//到达时间
								map.put("Text14",
										returnFlightNum.substring(returnFlightNum.lastIndexOf("//") - 4,
												returnFlightNum.lastIndexOf("//")).substring(0, 2)
												+ ":"
												+ returnFlightNum.substring(returnFlightNum.lastIndexOf("//") - 4,
														returnFlightNum.lastIndexOf("//")).substring(2, 4));
								//有效期
								Date addDay = DateUtil.addDay(ordertripjp.getReturnDate(), 8);
								map.put("Text16", df.format(addDay).toUpperCase());

								thirdStr = "3."
										+ returnFlightNum.substring(returnFlightNum.indexOf(" ") + 1,
												returnFlightNum.indexOf("//"))
										+ " Y "
										+ df.format(ordertripjp.getReturnDate()).toUpperCase()
										+ " "
										+ firstname.getTakeOffCode()
										+ secondname.getLandingCode()
										+ " HK"
										+ applyinfo.size()
										+ " "
										+ returnFlightNum.substring(returnFlightNum.lastIndexOf(" ") + 1,
												returnFlightNum.lastIndexOf(" ") + 5)
										+ "/"
										+ returnFlightNum.substring(returnFlightNum.lastIndexOf("//") - 4,
												returnFlightNum.lastIndexOf("//"));

								//第二行
								//始发地/目的地
								map.put("Text4", secondname.getLandingCode() + "/" + lastname.getLandingCode());
								//航班
								map.put("Text21",
										returnFlightNum.substring(returnFlightNum.indexOf("//") + 2,
												returnFlightNum.lastIndexOf(" ")));
								//日期
								map.put("Text23", df.format(ordertripjp.getReturnDate()).toUpperCase());
								//起飞时间
								map.put("Text24",
										returnFlightNum.substring(returnFlightNum.lastIndexOf("//") + 2,
												returnFlightNum.lastIndexOf("/")).substring(0, 2)
												+ ":"
												+ returnFlightNum.substring(returnFlightNum.lastIndexOf("//") + 2,
														returnFlightNum.lastIndexOf("/")).substring(2, 4));
								//到达时间
								map.put("Text25",
										returnFlightNum.substring(returnFlightNum.lastIndexOf("/") + 1).substring(0, 2)
												+ ":"
												+ returnFlightNum.substring(returnFlightNum.lastIndexOf("/") + 1)
														.substring(2, 4));
								//有效期
								map.put("Text26", df.format(addDay).toUpperCase());

								map.put("Text22", "Y");
								map.put("Text27", "OK");
								map.put("Text28", applyinfo.size() + "PC");
								fourthStr = "4."
										+ returnFlightNum.substring(returnFlightNum.indexOf("//") + 2,
												returnFlightNum.lastIndexOf(" "))
										+ " Y "
										+ df.format(ordertripjp.getReturnDate()).toUpperCase()
										+ " "
										+ secondname.getLandingCode()
										+ lastname.getLandingCode()
										+ " HK"
										+ applyinfo.size()
										+ " "
										+ returnFlightNum.substring(returnFlightNum.lastIndexOf("//") + 2,
												returnFlightNum.lastIndexOf("/")) + "/"
										+ returnFlightNum.substring(returnFlightNum.lastIndexOf("/") + 1);

							} else {//直飞，从第二行开始，只占一行
								//第一个机场
								String firstflight = returnFlightNum.substring(0,
										returnFlightNum.indexOf("-", returnFlightNum.indexOf("-")));
								//第二个机场 
								String secondflight = returnFlightNum.substring(returnFlightNum.indexOf("-") + 1,
										returnFlightNum.indexOf(" "));

								TFlightEntity firstname = dbDao.fetch(TFlightEntity.class,
										Cnd.where("takeOffName", "=", firstflight));
								TFlightEntity secondname = dbDao.fetch(TFlightEntity.class,
										Cnd.where("landingName", "=", secondflight));
								//始发地/目的地
								map.put("Text3", firstname.getTakeOffCode() + "/" + secondname.getLandingCode());
								//起飞时间 
								map.put("Text12",
										returnFlightNum.substring(returnFlightNum.lastIndexOf(" ") + 1,
												returnFlightNum.indexOf("/")).substring(0, 2)
												+ ":"
												+ returnFlightNum.substring(returnFlightNum.lastIndexOf(" ") + 1,
														returnFlightNum.indexOf("/")).substring(2, 4));
								//降落时间
								map.put("Text14",
										returnFlightNum.substring(returnFlightNum.indexOf("/") + 1).substring(0, 2)
												+ ":"
												+ returnFlightNum.substring(returnFlightNum.indexOf("/") + 1)
														.substring(2, 4));
								//航班号
								map.put("Text6",
										returnFlightNum.substring(returnFlightNum.indexOf(" ") + 1,
												returnFlightNum.lastIndexOf(" ")));

								//返回航班日期
								map.put("Text10", df.format(ordertripjp.getReturnDate()).toUpperCase());
								//有效期（返回日期+6天）
								Date addDay2 = DateUtil.addDay(ordertripjp.getReturnDate(), 6);
								map.put("Text16", df.format(addDay2).toUpperCase());

								thirdStr = "3."
										+ returnFlightNum.substring(returnFlightNum.indexOf(" ") + 1,
												returnFlightNum.lastIndexOf(" "))
										+ " Y "
										+ df.format(ordertripjp.getReturnDate()).toUpperCase()
										+ " "
										+ firstname.getTakeOffCode()
										+ secondname.getLandingCode()
										+ " HK"
										+ applyinfo.size()
										+ " "
										+ returnFlightNum.substring(returnFlightNum.lastIndexOf(" ") + 1,
												returnFlightNum.indexOf("/")) + "/"
										+ returnFlightNum.substring(returnFlightNum.indexOf("/") + 1);
							}

						} else {//转机，从第三行开始
							if (returnFlightNum.contains("//")) {//转机
								//第一个机场名 
								String firstflight = returnFlightNum.substring(0,
										returnFlightNum.indexOf("-", returnFlightNum.indexOf("-")));

								//第二个机场 
								String secondflight = returnFlightNum.substring(returnFlightNum.indexOf("-") + 1,
										returnFlightNum.lastIndexOf("-"));

								//最后一个机场名 
								String lastflight = returnFlightNum.substring(returnFlightNum.lastIndexOf("-") + 1,
										returnFlightNum.indexOf(" "));

								TFlightEntity firstname = dbDao.fetch(TFlightEntity.class,
										Cnd.where("takeOffName", "=", firstflight));
								TFlightEntity secondname = dbDao.fetch(TFlightEntity.class,
										Cnd.where("landingName", "=", secondflight));
								TFlightEntity lastname = dbDao.fetch(TFlightEntity.class,
										Cnd.where("landingName", "=", lastflight));
								//第一行 共2行
								//始发地/目的地
								map.put("Text4", firstname.getTakeOffCode() + "/" + secondname.getLandingCode());
								//航班
								map.put("Text21",
										returnFlightNum.substring(returnFlightNum.indexOf(" ") + 1,
												returnFlightNum.indexOf("//")));
								//日期
								map.put("Text23", df.format(ordertripjp.getReturnDate()).toUpperCase());
								//起飞时间
								map.put("Text24",
										returnFlightNum.substring(returnFlightNum.lastIndexOf(" ") + 1,
												returnFlightNum.lastIndexOf(" ") + 5).substring(0, 2)
												+ ":"
												+ returnFlightNum.substring(returnFlightNum.lastIndexOf(" ") + 1,
														returnFlightNum.lastIndexOf(" ") + 5).substring(2, 4));
								//到达时间
								map.put("Text25",
										returnFlightNum.substring(returnFlightNum.lastIndexOf("//") - 4,
												returnFlightNum.lastIndexOf("//")).substring(0, 2)
												+ ":"
												+ returnFlightNum.substring(returnFlightNum.lastIndexOf("//") - 4,
														returnFlightNum.lastIndexOf("//")).substring(2, 4));
								//有效期
								Date addDay = DateUtil.addDay(ordertripjp.getReturnDate(), 8);
								map.put("Text26", df.format(addDay).toUpperCase());

								fourthStr = "4."
										+ returnFlightNum.substring(returnFlightNum.indexOf(" ") + 1,
												returnFlightNum.indexOf("//"))
										+ " Y "
										+ df.format(ordertripjp.getReturnDate()).toUpperCase()
										+ " "
										+ firstname.getTakeOffCode()
										+ secondname.getLandingCode()
										+ " HK"
										+ applyinfo.size()
										+ " "
										+ returnFlightNum.substring(returnFlightNum.lastIndexOf(" ") + 1,
												returnFlightNum.lastIndexOf(" ") + 5)
										+ "/"
										+ returnFlightNum.substring(returnFlightNum.lastIndexOf("//") - 4,
												returnFlightNum.lastIndexOf("//"));

								//第二行
								//始发地/目的地
								map.put("Text29", secondname.getLandingCode() + "/" + lastname.getLandingCode());
								//航班
								map.put("Text30",
										returnFlightNum.substring(returnFlightNum.indexOf("//") + 2,
												returnFlightNum.lastIndexOf(" ")));
								//日期
								map.put("Text32", df.format(ordertripjp.getReturnDate()).toUpperCase());
								//起飞时间
								map.put("Text33",
										returnFlightNum.substring(returnFlightNum.lastIndexOf("//") + 2,
												returnFlightNum.lastIndexOf("/")).substring(0, 2)
												+ ":"
												+ returnFlightNum.substring(returnFlightNum.lastIndexOf("//") + 2,
														returnFlightNum.lastIndexOf("/")).substring(2, 4));
								//到达时间
								map.put("Text34",
										returnFlightNum.substring(returnFlightNum.lastIndexOf("/") + 1).substring(0, 2)
												+ ":"
												+ returnFlightNum.substring(returnFlightNum.lastIndexOf("/") + 1)
														.substring(2, 4));
								//有效期
								map.put("Text35", df.format(addDay).toUpperCase());
								fifthStr = "5."
										+ returnFlightNum.substring(returnFlightNum.indexOf("//") + 2,
												returnFlightNum.lastIndexOf(" "))
										+ " Y "
										+ df.format(ordertripjp.getReturnDate()).toUpperCase()
										+ " "
										+ secondname.getLandingCode()
										+ lastname.getLandingCode()
										+ " HK"
										+ applyinfo.size()
										+ " "
										+ returnFlightNum.substring(returnFlightNum.lastIndexOf("//") + 2,
												returnFlightNum.lastIndexOf("/")) + "/"
										+ returnFlightNum.substring(returnFlightNum.lastIndexOf("/") + 1);

							} else {//直飞
								//第一个机场
								String firstflight = returnFlightNum.substring(0,
										returnFlightNum.indexOf("-", returnFlightNum.indexOf("-")));
								//第二个机场 
								String secondflight = returnFlightNum.substring(returnFlightNum.indexOf("-") + 1,
										returnFlightNum.indexOf(" "));

								TFlightEntity firstname = dbDao.fetch(TFlightEntity.class,
										Cnd.where("takeOffName", "=", firstflight));
								TFlightEntity secondname = dbDao.fetch(TFlightEntity.class,
										Cnd.where("landingName", "=", secondflight));
								//始发地/目的地
								map.put("Text4", firstname.getTakeOffCode() + "/" + secondname.getLandingCode());
								//起飞时间 
								map.put("Text24",
										returnFlightNum.substring(returnFlightNum.lastIndexOf(" ") + 1,
												returnFlightNum.indexOf("/")).substring(0, 2)
												+ ":"
												+ returnFlightNum.substring(returnFlightNum.lastIndexOf(" ") + 1,
														returnFlightNum.indexOf("/")).substring(2, 4));
								//降落时间
								map.put("Text25",
										returnFlightNum.substring(returnFlightNum.indexOf("/") + 1).substring(0, 2)
												+ ":"
												+ returnFlightNum.substring(returnFlightNum.indexOf("/") + 1)
														.substring(2, 4));
								//航班号
								map.put("Text21",
										returnFlightNum.substring(returnFlightNum.indexOf(" ") + 1,
												returnFlightNum.lastIndexOf(" ")));

								//返回航班日期
								map.put("Text23", df.format(ordertripjp.getReturnDate()).toUpperCase());
								//有效期（返回日期+6天）
								Date addDay2 = DateUtil.addDay(ordertripjp.getReturnDate(), 6);
								map.put("Text26", df.format(addDay2).toUpperCase());

								fourthStr = "4."
										+ returnFlightNum.substring(returnFlightNum.indexOf(" ") + 1,
												returnFlightNum.lastIndexOf(" "))
										+ " Y "
										+ df.format(ordertripjp.getReturnDate()).toUpperCase()
										+ " "
										+ firstname.getTakeOffCode()
										+ secondname.getLandingCode()
										+ " HK"
										+ applyinfo.size()
										+ " "
										+ returnFlightNum.substring(returnFlightNum.lastIndexOf(" ") + 1,
												returnFlightNum.indexOf("/")) + "/"
										+ returnFlightNum.substring(returnFlightNum.indexOf("/") + 1);
							}

						}

					}
				} else if (ordertripjp.getTripType().equals(2)) {
					//多程处理
					if (!Util.isEmpty(mutiltrip)) {
						//多程第一程为出发日期
						TOrderTripMultiJpEntity entrytrip = mutiltrip.get(0);
						if (!Util.isEmpty(entrytrip.getDepartureDate())) {
							map.put("Text9", df.format(entrytrip.getDepartureDate()));
						}
						//入境航班
						if (!Util.isEmpty(entrytrip.getFlightNum())) {
							//							TFlightEntity goflight = flightViewService.fetch(entrytrip.getFlightNum().longValue());
							TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,
									Cnd.where("flightnum", "=", entrytrip.getFlightNum()));
							//起飞机场
							map.put("Text2", goflight.getTakeOffName());
							//起飞时间 
							map.put("Text11", goflight.getTakeOffTime().substring(0, 2) + ":"
									+ goflight.getTakeOffTime().substring(2, 4));
							//降落时间
							map.put("Text13", goflight.getLandingTime().substring(0, 2) + ":"
									+ goflight.getLandingTime().substring(2, 4));
						}
						map.put("Text5", entrytrip.getFlightNum());
						//最后一程作为返回日期
						TOrderTripMultiJpEntity returntrip = mutiltrip.get(mutiltrip.size() - 1);
						if (!Util.isEmpty(returntrip.getDepartureDate())) {
							map.put("Text10", df.format(returntrip.getDepartureDate()));
						}
						if (!Util.isEmpty(returntrip.getFlightNum())) {
							//出境航班
							TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,
									Cnd.where("flightnum", "=", returntrip.getFlightNum()));
							//返回机场
							map.put("Text3", goflight.getTakeOffName());
							//起飞时间 
							map.put("Text12", goflight.getTakeOffTime().substring(0, 2) + ":"
									+ goflight.getTakeOffTime().substring(2, 4));
							//降落时间
							map.put("Text14", goflight.getLandingTime().substring(0, 2) + ":"
									+ goflight.getLandingTime().substring(2, 4));
							map.put("Text4", goflight.getLandingName());
						}
						map.put("Text6", returntrip.getFlightNum());
					}
				}
			}

			{
				Paragraph p2 = new Paragraph(secondStr, font);
				p2.setSpacingBefore(5);
				p2.setIndentationLeft(20);
				p2.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(p2);
				Paragraph p3 = new Paragraph(thirdStr, font);
				p3.setSpacingBefore(5);
				p3.setIndentationLeft(20);
				p3.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(p3);
				Paragraph p4 = new Paragraph(fourthStr, font);
				p4.setSpacingBefore(5);
				p4.setIndentationLeft(20);
				p4.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(p4);
				Paragraph p5 = new Paragraph(fifthStr, font);
				p5.setSpacingBefore(5);
				p5.setIndentationLeft(20);
				p5.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(p5);
			}
			document.close();
			IOUtils.closeQuietly(stream);
			return stream;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stream;
	}

	public Image getSeal1(String address, int n) throws IOException, BadElementException {
		if (!Util.isEmpty(address)) {

			URL url = new URL(address);
			//添加盖章
			//Image img = Image.getInstance(getClass().getClassLoader().getResource(getPrefix() + "sealnew.jpg"));
			Image img = Image.getInstance(url);
			img.setAlignment(Image.RIGHT);
			//		img.scaleToFit(400, 200);//大小
			img.scaleToFit(200, 100);//大小
			//img.setIndentationRight(200);
			img.setRotation(800);
			if (n <= 1) {

				img.setAbsolutePosition(400, 530);
			} else {

				img.setAbsolutePosition(400, 530 - 34 * (n - 1));
			}
			img.setAlignment(Paragraph.ALIGN_RIGHT);
			return img;
		}
		return null;
	}

	/**
	 * 査 証 申 請 人 名 簿
	 */
	public ByteArrayOutputStream book(Map<String, Object> tempdata) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			Map<String, String> map = new HashMap<String, String>();
			//公司信息
			TCompanyEntity company = (TCompanyEntity) tempdata.get("company");
			//出行信息
			TOrderTripJpEntity ordertripjp = (TOrderTripJpEntity) tempdata.get("ordertripjp");
			//多程信息
			List<TOrderTripMultiJpEntity> mutiltrip = (List<TOrderTripMultiJpEntity>) tempdata.get("mutiltrip");
			//申请人信息
			List<Record> applyinfo = (List<Record>) tempdata.get("applyinfo");
			//日本订单信息
			TOrderJpEntity orderjp = (TOrderJpEntity) tempdata.get("orderjp");
			//行程安排
			List<TOrderTravelplanJpEntity> ordertravelplans = (List<TOrderTravelplanJpEntity>) tempdata
					.get("ordertravelplan");
			//PDF操作开始
			Document document = new Document(PageSize.A4, 0, 0, 36, 36);
			PdfWriter.getInstance(document, stream);
			document.open();
			TtfClassLoader ttf = new TtfClassLoader();
			Font font = ttf.getFont();
			font.setSize(15);
			{
				Paragraph p = new Paragraph("査証申請人名簿", font);
				p.setSpacingBefore(5);
				p.setSpacingAfter(5);
				p.setAlignment(Paragraph.ALIGN_CENTER);
				document.add(p);
			}
			font.setSize(10);
			//日期格式化
			//String pattern = "yy年MM月dd日";
			SimpleDateFormat tableformat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat pattern = new SimpleDateFormat("yy年MM月dd日");
			String pingcheng = "yy年MM月dd日";
			String applyname = "";
			int nansize = 0;
			int nvsize = 0;
			int totalsize = 0;
			if (!Util.isEmpty(applyinfo)) {
				Record record = applyinfo.get(0);
				applyname += record.getString("firstname");
				applyname += record.getString("lastname");
				totalsize = applyinfo.size();
				for (Record apply : applyinfo) {
					String sexStr = (String) apply.get("sex");
					if (Util.eq(sexStr, "男")) {
						nansize++;
					} else {
						nvsize++;
					}
				}
			}
			//副标题1
			{
				String text = applyname + " , 他 " + (totalsize - 1) + " 名(男" + nansize + " / 女" + nvsize + ")";
				Paragraph p = new Paragraph(text, font);
				p.setSpacingAfter(5);
				p.setIndentationRight(20);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			//副标题2
			String godatestr = "";
			String returndatestr = "";
			if (!Util.isEmpty(ordertripjp)) {
				if (ordertripjp.getTripType().equals(1)) {
					if (!Util.isEmpty(ordertripjp.getGoDate())) {
						//godatestr = pattern.format(ordertripjp.getGoDate());
						godatestr = format(ordertripjp.getGoDate(), pingcheng);
					}
					if (!Util.isEmpty(ordertripjp.getReturnDate())) {
						//returndatestr = pattern.format(ordertripjp.getReturnDate());
						returndatestr = format(ordertripjp.getReturnDate(), pingcheng);
					}
				} else if (ordertripjp.getTripType().equals(2)) {
					if (!Util.isEmpty(mutiltrip)) {
						//多程第一程为出发日期
						TOrderTripMultiJpEntity entrytrip = mutiltrip.get(0);
						if (!Util.isEmpty(entrytrip.getDepartureDate())) {
							//godatestr = pattern.format(entrytrip.getDepartureDate());
							godatestr = format(entrytrip.getDepartureDate(), pingcheng);
						}
						//最后一程作为返回日期
						TOrderTripMultiJpEntity returntrip = mutiltrip.get(mutiltrip.size() - 1);
						if (!Util.isEmpty(returntrip.getDepartureDate())) {
							//returndatestr = pattern.format(returntrip.getDepartureDate());
							returndatestr = format(returntrip.getDepartureDate(), pingcheng);
						}
					}
				}
			}
			{
				//				String subtitle = "（平成" + godatestr + "から平成" + returndatestr + "）";
				String subtitle = "（平成" + godatestr + " - 平成" + returndatestr + ")";
				Paragraph p = new Paragraph(subtitle, font);
				p.setSpacingBefore(5);
				p.setSpacingAfter(5);
				p.setIndentationRight(20);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				//添加副标题1
				document.add(p);
			}

			//这里是表格********************************
			float[] columns = { 1, 3, 4, 1, 3, 3, 2.5f, 3 };
			PdfPTable table = new PdfPTable(columns);
			table.setWidthPercentage(95);
			table.setTotalWidth(PageSize.A4.getWidth());

			//设置表头
			String titles[] = { "番号", "氏名（中文）", "氏名（英文）", "性別", "生年月日", "旅券番号", "居住地域", "职业", };
			for (int i = 0; i < titles.length; i++) {
				String title = titles[i];
				PdfPCell cell = new PdfPCell(new Paragraph(title, font));
				cell.setFixedHeight(30);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}
			//表格体
			int count = 0;
			for (Record record : applyinfo) {
				//职业
				String careerstatus = "";
				/*if (!Util.isEmpty(record.get("careerstatus"))) {
					for (JobStatusEnum jobstatusenum : JobStatusEnum.values()) {
						if (record.getInt("careerstatus") == jobstatusenum.intKey()) {
							careerstatus = jobstatusenum.value();
						}
					}
				}*/
				if (!Util.isEmpty(record.get("position"))) {
					careerstatus = (String) record.get("position");
				}
				count++;
				String[] data = {
						String.valueOf(count),
						//姓名
						(!Util.isEmpty(record.get("firstname")) ? record.getString("firstname") : "")
								+ (!Util.isEmpty(record.get("lastname")) ? record.getString("lastname") : ""),
						//姓名英文
						((!Util.isEmpty(record.get("firstnameen")) ? record.getString("firstnameen") : "") + " " + (!Util
								.isEmpty(record.get("lastnameen")) ? record.getString("lastnameen") : ""))
								.toUpperCase(), !Util.isEmpty(record.get("sex")) ? record.getString("sex") : "",
						!Util.isEmpty(record.get("birthday")) ? tableformat.format((Date) record.get("birthday")) : "",
						record.getString("passportno"), record.getString("province"), careerstatus };
				for (String tablecell : data) {
					PdfPCell cell = new PdfPCell(new Paragraph(tablecell, font));
					cell.setFixedHeight(30);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
				}
			}
			document.add(table);
			//底部*********************************************
			{
				Paragraph p = new Paragraph("公司名称：" + (!Util.isEmpty(company.getName()) ? company.getName() : ""), font);
				p.setSpacingBefore(5);
				p.setIndentationLeft(20);
				p.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(p);
			}
			{
				Paragraph p = new Paragraph("担当者：" + (!Util.isEmpty(company.getLinkman()) ? company.getLinkman() : ""),
						font);
				p.setSpacingBefore(5);
				p.setIndentationLeft(20);
				p.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(p);
			}
			{
				Paragraph p = new Paragraph("電  話：" + (!Util.isEmpty(company.getMobile()) ? company.getMobile() : ""),
						font);
				p.setSpacingBefore(5);
				p.setIndentationLeft(20);
				p.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(p);
			}
			//表格的高度
			float totalHeight = table.getTotalHeight();
			//PDF的高度
			float height = document.getPageSize().getHeight();
			//底部
			//公章地址
			String sealUrl = "";
			if (!Util.isEmpty(orderjp.getGroundconnectid())) {
				TCompanyEntity dijiecompany = dbDao.fetch(TCompanyEntity.class, orderjp.getGroundconnectid()
						.longValue());
				sealUrl = dijiecompany.getSeal();
			}
			if (!Util.isEmpty(sealUrl)) {
				URL url = new URL(sealUrl);
				//添加盖章
				//Image img = Image.getInstance(getClass().getClassLoader().getResource(getPrefix() + "sealnew.jpg"));
				Image img = Image.getInstance(url);
				img.setAlignment(Image.RIGHT);
				//		img.scaleToFit(400, 200);//大小
				img.scaleToFit(280, 180);//大小
				//img.setIndentationRight(200);
				img.setRotation(800);

				img.setAbsolutePosition(350, height - totalHeight - 300);
				img.setAlignment(Paragraph.ALIGN_RIGHT);

				document.add(img);

			}
			document.close();
			IOUtils.closeQuietly(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stream;
	}

	public ByteArrayOutputStream returnhome(Map<String, Object> tempdata) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		//公司信息
		TCompanyEntity company = (TCompanyEntity) tempdata.get("company");
		//出行信息
		TOrderTripJpEntity ordertripjp = (TOrderTripJpEntity) tempdata.get("ordertripjp");
		//申请人信息
		List<Record> applyinfo = (List<Record>) tempdata.get("applyinfo");
		//日本订单信息
		TOrderJpEntity orderjp = (TOrderJpEntity) tempdata.get("orderjp");
		//订单信息
		TOrderEntity orderinfo = (TOrderEntity) tempdata.get("orderinfo");
		//地接社名称
		String dijie = "";
		//地接社联系人
		String dijielinkman = "";
		//地接社电话
		String dijiephone = "";
		//公章地址
		String dijieDes = "";
		if (!Util.isEmpty(orderjp.getGroundconnectid())) {
			TCompanyEntity dijiecompany = dbDao.fetch(TCompanyEntity.class, orderjp.getGroundconnectid().longValue());
			dijie = dijiecompany.getName();
			dijielinkman = dijiecompany.getLinkman();
			dijiephone = dijiecompany.getMobile();
			dijieDes = dijiecompany.getCdesignNum();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("acceptDesign", orderjp.getAcceptDesign());
		map.put("dijiename", dijie);
		map.put("dijietel", dijiephone);
		map.put("dijieDes", dijieDes);
		map.put("dijiefuze", dijielinkman);
		map.put("songqianname", company.getName());
		map.put("songqiantel", company.getMobile());
		map.put("songqiandes", company.getCdesignNum());
		map.put("songqianfuze", company.getLinkman());
		//时间
		map.put("text1", new SimpleDateFormat("yyyy").format(new Date()));
		map.put("text2", new SimpleDateFormat("MM").format(new Date()));
		map.put("text3", new SimpleDateFormat("dd").format(new Date()));
		DateFormat traveldateformat = new SimpleDateFormat("MM/dd");
		Date goTripDate = orderinfo.getGoTripDate();
		Date backTripDate = orderinfo.getBackTripDate();
		String godatestr = "";
		if (!Util.isEmpty(goTripDate)) {
			godatestr += traveldateformat.format(goTripDate);
		}
		godatestr += "~";
		if (!Util.isEmpty(backTripDate)) {
			godatestr += traveldateformat.format(backTripDate);
		}
		map.put("traveltime", godatestr);
		int count = 1;
		for (Record record : applyinfo) {
			//姓名
			String applyname = (!Util.isEmpty(record.get("firstname")) ? record.getString("firstname") : "")
					+ (!Util.isEmpty(record.get("lastname")) ? record.getString("lastname") : "");
			//职业
			String careerstatus = "";
			if (!Util.isEmpty(record.get("careerstatus"))) {
				for (JobStatusEnum jobstatusenum : JobStatusEnum.values()) {
					if (record.getInt("careerstatus") == jobstatusenum.intKey()) {
						careerstatus = jobstatusenum.value();
					}
				}
			}
			map.put("applyname" + count, applyname);
			map.put("gender" + count, !Util.isEmpty(record.get("sex")) ? record.getString("sex") : "");
			map.put("province" + count, !Util.isEmpty(record.get("province")) ? record.getString("province") : "");
			map.put("birthday" + count,
					!Util.isEmpty(record.get("birthday")) ? dateformat.format((Date) record.get("birthday")) : "");
			map.put("passport" + count, !Util.isEmpty(record.get("passportno")) ? record.getString("passportno") : "");
			map.put("remark" + count, careerstatus);
			count++;
		}

		//获取模板文件
		URL resource = getClass().getClassLoader().getResource("japanfile/liaoningwanda/returnhome.pdf");
		TemplateUtil templateUtil = new TemplateUtil();
		stream = templateUtil.pdfTemplateStream(resource, map);
		return stream;
	}
}
