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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.common.enums.MarryStatusEnum;
import com.juyo.visa.common.util.FormatDateUtil;
import com.juyo.visa.entities.TApplicantEntity;
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
		List<Record> applyinfo = dbDao.query(applysql, cnd, null);
		for (Record record : applyinfo) {
			List<TApplicantWealthJpEntity> query = dbDao.query(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", record.get("applicatid")), null);
			record.put("wealthjpinfo", query);
		}
		Map<String, Object> tempdata = new HashMap<String, Object>();
		//行程安排
		List<TOrderTravelplanJpEntity> ordertravelplan = dbDao.query(TOrderTravelplanJpEntity.class,
				Cnd.where("orderId", "=", orderjp.getId()), null);
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
		//电子航票
		ByteArrayOutputStream airticket = airticket(tempdata);
		pdffiles.add(airticket);
		//酒店信息
		ByteArrayOutputStream hotelInfo = hotelInfo(tempdata);
		pdffiles.add(hotelInfo);
		//申请人信息
		for (Record record : applyinfo) {
			ByteArrayOutputStream apply = applyinfo(record, tempdata);
			pdffiles.add(apply);
		}
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
			for (MainSaleVisaTypeEnum visatypeEnum : MainSaleVisaTypeEnum.values()) {
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
					map.put("Text2", dateFormat.format(ordertripjp.getGoDate()));
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
					TCityEntity airCity = dbDao.fetch(TCityEntity.class, Cnd.where("city", "=", airportName));
					String aircode = "";
					if (!Util.isEmpty(airCity)) {
						aircode = airCity.getCode();
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
					map.put("Text4", dateFormat.format(ordertripjp.getReturnDate()));
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
					TCityEntity airCity = dbDao.fetch(TCityEntity.class, Cnd.where("city", "=", airportName));
					String aircode = "";
					if (!Util.isEmpty(airCity)) {
						aircode = airCity.getCode();
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
			map.put("Text9", dateFormat1.format(sendVisaDate));
		} else {
			map.put("Text9", "");
		}
		//获取模板文件
		URL resource = getClass().getClassLoader().getResource("japanfile/shanghaibaicheng/note.pdf");
		TemplateUtil templateUtil = new TemplateUtil();
		stream = templateUtil.pdfTemplateStream(resource, map);
		return stream;
	}

	/**
	 * 申请人信息
	 */
	private ByteArrayOutputStream applyinfo(Record record, Map<String, Object> tempdata) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		TOrderTripJpEntity ordertripjp = (TOrderTripJpEntity) tempdata.get("ordertripjp");
		//多程信息
		List<TOrderTripMultiJpEntity> mutiltrip = (List<TOrderTripMultiJpEntity>) tempdata.get("mutiltrip");
		List<TOrderTravelplanJpEntity> ordertravelplan = (List<TOrderTravelplanJpEntity>) tempdata
				.get("ordertravelplan");
		//日本订单信息
		TOrderJpEntity orderjp = (TOrderJpEntity) tempdata.get("orderjp");
		try {
			Map<String, String> map = new HashMap<String, String>();
			//中文姓
			map.put("fill_2", record.getString("firstname"));
			//英文姓
			map.put("fill_1", record.getString("firstnameen"));
			//中文名
			map.put("fill_4", record.getString("lastname"));
			//英文名
			map.put("fill_3", record.getString("lastnameen"));
			//曾用名
			String otherfirstnameen = !Util.isEmpty(record.get("otherfirstnameen")) ? record
					.getString("otherfirstnameen") : "";
			String otherlastnameen = !Util.isEmpty(record.get("otherlastnameen")) ? record.getString("otherlastnameen")
					: "";
			String otherfirstname = !Util.isEmpty(record.get("otherfirstname")) ? record.getString("otherfirstname")
					: "";
			String otherlastname = !Util.isEmpty(record.get("otherlastname")) ? record.getString("otherlastname") : "";
			//曾用名英文
			map.put("fill_5", otherfirstnameen + otherlastnameen);
			//曾用名中文
			map.put("fill_6", otherfirstname + otherlastname);
			//性别
			if ("男".equals(record.getString("sex"))) {
				map.put("toggle_1", "On");
			} else if ("女".equals(record.getString("sex"))) {
				map.put("toggle_2", "On");
			}
			//出生日期
			if (!Util.isEmpty(record.get("birthday"))) {
				map.put("fill_7", dateformat.format((Date) record.get("birthday")));
			}
			//出生地
			String province = (String) record.getString("province");
			if (province.endsWith("省") || province.endsWith("市")) {
				province = province.substring(0, province.length() - 1);
			}
			map.put("fill_8", province);
			//map.put("fill_8", record.getString("address"));
			//婚姻状况
			if (!Util.isEmpty(record.get("marrystatus"))) {
				Integer marrystatus = record.getInt("marrystatus");
				if (marrystatus.equals(MarryStatusEnum.DANSHEN.intKey())) {
					//已婚
					map.put("toggle_3", "On");
				} else if (marrystatus.equals(MarryStatusEnum.YIHUN.intKey())) {
					//单身
					map.put("toggle_4", "On");
				} else if (marrystatus.equals(MarryStatusEnum.LIYI.intKey())) {
					//离婚
					map.put("toggle_6", "On");
				} else if (marrystatus.equals(MarryStatusEnum.SANGOU.intKey())) {
					//丧偶
					map.put("toggle_5", "On");
				}
			}
			//国籍
			map.put("fill_9", "CHINA");
			//曾有的或另有的国际
			map.put("fill_10", record.getString("nationality"));
			//身份证号
			map.put("fill_11", record.getString("cardid"));
			//护照类别:普通
			map.put("toggle_9", "On");
			//护照号
			map.put("fill_12", record.getString("passportno"));
			//签发地点
			map.put("fill_13", record.getString("issuedplace"));
			//签发日期
			if (!Util.isEmpty(record.get("issuedDate"))) {
				map.put("fill_14", dateformat.format((Date) record.get("issuedDate")));
			}
			//签发机关
			map.put("fill_16", record.getString("issuedOrganization"));
			//有效期至
			if (!Util.isEmpty(record.get("passportenddate"))) {
				map.put("fill_15", dateformat.format((Date) record.get("passportenddate")));
			}
			if (!Util.isEmpty(ordertripjp)) {
				//赴日目的
				map.put("trippurpose", ordertripjp.getTripPurpose());
				if (ordertripjp.getTripType().equals(1)) {
					//出行时间
					if (!Util.isEmpty(ordertripjp.getGoDate())) {
						map.put("fill_18", dateformat.format(ordertripjp.getGoDate()));
					}
					//返回时间
					if (!Util.isEmpty(ordertripjp.getReturnDate())) {
						map.put("fill_19", dateformat.format(ordertripjp.getReturnDate()));
					}
					//逗留时间
					if (!Util.isEmpty(ordertripjp.getGoDate()) && !Util.isEmpty(ordertripjp.getReturnDate())) {
						int stayday = DateUtil.daysBetween(ordertripjp.getGoDate(), ordertripjp.getReturnDate());
						map.put("fill_20", String.valueOf(stayday) + "天");
					}
					//入境口岸
					if (!Util.isEmpty(ordertripjp.getGoArrivedCity())) {
						TCityEntity goarrivecirtentity = cityViewService.fetch(ordertripjp.getGoArrivedCity());
						if (!Util.isEmpty(goarrivecirtentity)) {
							map.put("fill_21", goarrivecirtentity.getCity());
						}
					}
					//航空公司.0
					TFlightEntity goflightentity = dbDao.fetch(TFlightEntity.class,
							Cnd.where("flightnum", "=", ordertripjp.getGoFlightNum()));
					if (!Util.isEmpty(goflightentity)) {
						map.put("fill_22", ordertripjp.getGoFlightNum());
					}
				} else if (ordertripjp.getTripType().equals(2)) {
					//多程处理
					if (!Util.isEmpty(mutiltrip)) {
						//多程第一程为出发日期
						TOrderTripMultiJpEntity entrytrip = mutiltrip.get(0);
						if (!Util.isEmpty(entrytrip.getDepartureDate())) {
							map.put("fill_18", dateformat.format(entrytrip.getDepartureDate()));
						}
						//入境口岸
						TCityEntity arrivecity = dbDao.fetch(TCityEntity.class, entrytrip.getArrivedCity().longValue());
						if (!Util.isEmpty(arrivecity)) {
							map.put("fill_21", arrivecity.getCity());
						}
						//航空公司
						if (!Util.isEmpty(entrytrip.getFlightNum())) {
							TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,
									Cnd.where("flightnum", "=", entrytrip.getFlightNum()));
							map.put("fill_22", entrytrip.getFlightNum());
						}
						//最后一程作为返回日期
						TOrderTripMultiJpEntity returntrip = mutiltrip.get(mutiltrip.size() - 1);
						if (!Util.isEmpty(returntrip.getDepartureDate())) {
							map.put("fill_19", dateformat.format(returntrip.getDepartureDate()));
						}
						//停留天数
						if (!Util.isEmpty(entrytrip.getDepartureDate()) && !Util.isEmpty(returntrip.getDepartureDate())) {
							int stayday = DateUtil.daysBetween(entrytrip.getDepartureDate(),
									returntrip.getDepartureDate());
							map.put("fill_20", String.valueOf(stayday) + "天");
						}
					}
				}
			}
			//酒店信息
			/*if (!Util.isEmpty(ordertravelplan)) {
			TOrderTravelplanJpEntity travelplanEntity = ordertravelplan.get(0);
			if (!Util.isEmpty(travelplanEntity.getHotel())) {
			THotelEntity hotelinfo = hotelViewService.fetch(travelplanEntity.getHotel());
			map.put("hotelname", hotelinfo.getName());
			map.put("hotelphone", hotelinfo.getMobile());
			map.put("hoteladdress", hotelinfo.getAddress());
			}
			}*/
			//酒店信息
			map.put("fill_23", record.getString("hotelname"));
			//电话
			map.put("fill_24", record.getString("hotelphone"));
			//地址
			map.put("fill_25", record.getString("hoteladdress"));
			String lastinfo = "";
			if (!Util.isEmpty(orderjp.getLaststartdate()) && !Util.isEmpty(orderjp.getLastreturndate())) {
				lastinfo += dateformat.format(orderjp.getLaststartdate());
				lastinfo += "~";
				lastinfo += dateformat.format(orderjp.getLastreturndate());
				lastinfo += "      " + (Util.isEmpty(orderjp.getLaststayday()) ? "" : orderjp.getLaststayday());
			}
			if (Util.isEmpty(lastinfo)) {
				map.put("fill_26", "无");
			} else {
				map.put("fill_26", lastinfo + "天");
			}
			//配偶职业
			map.put("fill_1_2", record.getString("unitname"));
			//在日担保人信息
			map.put("fill_2_2", record.getString("vouchname"));
			//木有英文
			//map.put("danbaonameen", record.getString("vouchnameen"));
			//电话
			map.put("fill_3_2", record.getString("vouchphone"));
			//地址
			map.put("fill_4_2", record.getString("vouchaddress"));
			if ("男".equals(record.getString("vouchnan"))) {
				map.put("toggle_1_2", "On");
			} else if ("女".equals(record.getString("vouchsex"))) {
				map.put("toggle_2_2", "On");
			}
			//出生日期
			if (!Util.isEmpty(record.get("vouchbirth"))) {
				map.put("fill_5_2", dateformat.format((Date) record.get("vouchbirth")));
			}
			//与主申请人的关系
			map.put("fill_6_2", record.getString("vouchmainrelation"));
			//工作
			map.put("fill_7_2", record.getString("vouchjob"));
			//国籍
			map.put("fill_8_2", record.getString("vouchcountry"));
			//在日邀请人
			//姓名
			map.put("fill_9_2", record.getString("invitename"));
			//电话
			map.put("fill_10_2", record.getString("invitephone"));
			//住址
			map.put("fill_11_2", record.getString("inviteaddress"));
			if ("男".equals(record.getString("invitesex"))) {
				map.put("toggle_3_2", "On");
			} else if ("女".equals(record.getString("invitesex"))) {
				map.put("toggle_4_2", "On");
			}
			//出生日期
			if (!Util.isEmpty(record.get("invitebirth"))) {
				map.put("fill_12_2", dateformat.format((Date) record.get("invitebirth")));
			}
			//职业或职务
			map.put("fill_14_2", record.getString("invitejob"));
			//与主申请人关系
			map.put("fill_13_2", record.getString("invitemainrelation"));
			//国籍
			map.put("fill_15_2", record.getString("invitecountry"));
			//家庭住址
			map.put("fill_27",
					(!Util.isEmpty(record.get("province")) ? record.getString("province") : " ")
							+ (!Util.isEmpty(record.get("city")) ? record.getString("city") : " ")
							+ (!Util.isEmpty(record.get("detailedaddress")) ? record.getString("detailedaddress") : " "));
			//家庭电话
			//map.put("homephone", record.getString("telephone"));
			map.put("fill_28", record.getString("telephone"));
			//电子邮箱（沈阳没有电邮）
			//map.put("homeEmail", record.getString("email"));
			//工作单位
			map.put("fill_30", record.getString("workname"));
			//电话
			map.put("fill_31", record.getString("workphone"));
			//地址
			map.put("fill_32", record.getString("workaddress"));
			//目前的职位
			map.put("fill_33", record.getString("position"));
			//map.put("danbaoname", "参照身元保证书");
			map.put("toggle_6_2", "On");
			map.put("toggle_8_2", "On");
			map.put("toggle_10_2", "On");
			map.put("toggle_12", "On");
			map.put("toggle_14", "On");
			map.put("toggle_16", "On");
			//申请日期
			map.put("fill_17", dateformat.format(new Date()));
			map.put("fill_18_2", record.getString("firstname") + record.getString("lastname"));
			//获取模板文件
			URL resource = getClass().getClassLoader().getResource("japanfile/liaoningwanda/apply.pdf");
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

			//PDF操作开始
			//五个表格组成
			Document document = new Document(PageSize.A4, 0, 0, 36, 36);
			PdfWriter.getInstance(document, stream);
			document.open();
			TtfClassLoader ttf = new TtfClassLoader();
			Font font = ttf.getFont();
			font.setSize(23.88f);
			font.setFamily("宋体");

			//第一个表格,空表格
			float[] firstcolumns = { 13 };
			PdfPTable titletable = new PdfPTable(firstcolumns);
			titletable.setWidthPercentage(95);
			titletable.setTotalWidth(PageSize.A4.getWidth());

			PdfPCell titlecell = new PdfPCell(new Paragraph("", font));
			titlecell.setFixedHeight(50);
			titlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
			titlecell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			titlecell.setBorder(0);
			titletable.addCell(titlecell);

			document.add(titletable);

			//第二个表格
			//地接社
			String dijie = "";
			if (!Util.isEmpty(orderjp.getGroundconnectid())) {
				TCompanyEntity dijiecompany = dbDao.fetch(TCompanyEntity.class, orderjp.getGroundconnectid()
						.longValue());
				dijie = dijiecompany.getName();
			}

			float[] kongcolumns = { 13 };
			PdfPTable kongtable = new PdfPTable(kongcolumns);
			kongtable.setWidthPercentage(95);
			kongtable.setTotalWidth(PageSize.A4.getWidth());
			font.setSize(8);

			PdfPCell kongcell = new PdfPCell(new Paragraph(dijie, font));
			kongcell.setFixedHeight(20);
			kongcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			kongcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			kongtable.addCell(kongcell);

			document.add(kongtable);

			//第三个表格
			float[] kongcolumns2 = { 13 };
			PdfPTable kongtable2 = new PdfPTable(kongcolumns2);
			kongtable2.setWidthPercentage(95);
			kongtable2.setTotalWidth(PageSize.A4.getWidth());
			font.setSize(8);

			PdfPCell kongcell2 = new PdfPCell(new Paragraph("ホテル予約確認書", font));
			kongcell2.setFixedHeight(20);
			kongcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			kongcell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			kongtable2.addCell(kongcell2);

			document.add(kongtable2);

			//第四个表格
			float[] kongcolumns3 = { 13 };
			PdfPTable kongtable3 = new PdfPTable(kongcolumns3);
			kongtable3.setWidthPercentage(95);
			kongtable3.setTotalWidth(PageSize.A4.getWidth());
			font.setSize(8);

			PdfPCell kongcell3 = new PdfPCell(new Paragraph("以下为预定内容：", font));
			kongcell3.setFixedHeight(20);
			kongcell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			kongcell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
			kongtable3.addCell(kongcell3);

			document.add(kongtable3);

			//第五个表格，表头

			float[] tcolumns = { 3, 3, 4, 3 };
			PdfPTable titable = new PdfPTable(tcolumns);
			titable.setWidthPercentage(95);
			titable.setTotalWidth(PageSize.A4.getWidth());

			//设置表头
			String titles[] = { "时间", "ホテル", "住所", "电话", };
			font.setSize(7.96f);
			for (String title : titles) {
				PdfPCell cell = new PdfPCell(new Paragraph(title, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(30);
				titable.addCell(cell);
			}

			float[] maincolumns = { 2, 3, 4, 2, 1, 1 };
			PdfPTable maintable = new PdfPTable(maincolumns);
			maintable.setWidthPercentage(95);
			maintable.setTotalWidth(PageSize.A4.getWidth());

			//数据处理
			if (!Util.isEmpty(ordertripjp)) {
				List<TOrderTravelplanJpEntity> ordertravelplanList = dbDao.query(TOrderTravelplanJpEntity.class, Cnd
						.where("orderId", "=", orderjp.getId()).orderBy("outDate", "ASC"), null);
				Integer visatype = orderjp.getVisaType();
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
							List<TOrderTravelplanJpEntity> query = dbDao
									.query(TOrderTravelplanJpEntity.class, Cnd.where("orderId", "=", orderjp.getId())
											.and("cityId", "=", cityidList.get(i)), null);
							TOrderTravelplanJpEntity fetch = dbDao.fetch(TOrderTravelplanJpEntity.class,
									Cnd.where("orderId", "=", orderjp.getId()).and("cityId", "=", cityidList.get(i)));
							PdfPCell cell;
							//第一列，宿泊
							cell = new PdfPCell(new Paragraph("宿泊", font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(50);
							maintable.addCell(cell);

							//第二列，时间
							Date outDate = fetch.getOutDate();
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
							String timeStampStr = simpleDateFormat.format(outDate);
							cell = new PdfPCell(new Paragraph(timeStampStr, font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(50);
							maintable.addCell(cell);

							//第三列，酒店信息
							String hotel = "";
							THotelEntity hotelinfo = hotelViewService.fetch(fetch.getHotel());
							hotel = hotelinfo.getNamejp() + "\n" + hotelinfo.getAddressjp() + "\n"
									+ hotelinfo.getMobile() + "\n";
							cell = new PdfPCell(new Paragraph(hotel, font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(50);
							maintable.addCell(cell);

							//第四列，停留几晚
							if (i == cityidList.size() - 1) {
								cell = new PdfPCell(new Paragraph((query.size() - 1) + "泊朝食", font));

							} else {
								cell = new PdfPCell(new Paragraph(query.size() + "泊朝食", font));

							}
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(50);
							maintable.addCell(cell);

							//第五列，需要几间房子
							String room = "";
							if (applyinfo.size() > 0) {
								if (applyinfo.size() % 2 == 1) {
									room = String.valueOf(applyinfo.size() / 2 + 1);
								} else {
									room = String.valueOf(applyinfo.size() / 2);
								}
							}
							cell = new PdfPCell(new Paragraph(room, font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(50);
							maintable.addCell(cell);

							//第六列，回答
							cell = new PdfPCell(new Paragraph("手配 OK", font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(50);
							maintable.addCell(cell);
						}

					} else {
						//第一行
						PdfPCell cell;
						//第一列，宿泊
						cell = new PdfPCell(new Paragraph("宿泊", font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(50);
						maintable.addCell(cell);

						//第二列，时间
						Date outDate = ordertravelplanList.get(0).getOutDate();
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						String timeStampStr = simpleDateFormat.format(outDate);
						cell = new PdfPCell(new Paragraph(timeStampStr, font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(50);
						maintable.addCell(cell);

						//第三列，酒店信息
						String hotel = "";
						THotelEntity hotelinfo = hotelViewService.fetch(ordertravelplanList.get(0).getHotel());
						hotel = hotelinfo.getNamejp() + "\n" + hotelinfo.getAddressjp() + "\n" + hotelinfo.getMobile()
								+ "\n";
						cell = new PdfPCell(new Paragraph(hotel, font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(50);
						maintable.addCell(cell);

						//第四列，停留几晚
						cell = new PdfPCell(new Paragraph("2泊朝食", font));

						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(50);
						maintable.addCell(cell);

						//第五列，需要几间房子
						String room = "";
						if (applyinfo.size() > 0) {
							if (applyinfo.size() % 2 == 1) {
								room = String.valueOf(applyinfo.size() / 2 + 1);
							} else {
								room = String.valueOf(applyinfo.size() / 2);
							}
						}
						cell = new PdfPCell(new Paragraph(room, font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(50);
						maintable.addCell(cell);

						//第六列，回答
						cell = new PdfPCell(new Paragraph("手配 OK", font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setFixedHeight(50);
						maintable.addCell(cell);

						//剩下的行数
						Integer firstcityid = cityidList.get(0);
						cityidList.remove(0);
						cityidList.add(firstcityid);
						for (int i = 0; i < cityidList.size(); i++) {
							List<TOrderTravelplanJpEntity> query = dbDao
									.query(TOrderTravelplanJpEntity.class, Cnd.where("orderId", "=", orderjp.getId())
											.and("cityId", "=", cityidList.get(i)), null);
							TOrderTravelplanJpEntity fetch = null;
							if (i == cityidList.size() - 1) {//最后一个
								fetch = query.get(2);
							} else {
								fetch = dbDao.fetch(TOrderTravelplanJpEntity.class,
										Cnd.where("orderId", "=", orderjp.getId())
												.and("cityId", "=", cityidList.get(i)));
							}
							//第一列，宿泊
							cell = new PdfPCell(new Paragraph("宿泊", font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(50);
							maintable.addCell(cell);

							//第二列，时间
							Date outDate1 = fetch.getOutDate();
							SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
							String timeStampStr1 = simpleDateFormat.format(outDate1);
							cell = new PdfPCell(new Paragraph(timeStampStr1, font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(50);
							maintable.addCell(cell);

							//第三列，酒店信息
							String hotel1 = "";
							THotelEntity hotelinfo1 = hotelViewService.fetch(fetch.getHotel());
							hotel1 = hotelinfo1.getNamejp() + "\n" + hotelinfo1.getAddressjp() + "\n"
									+ hotelinfo1.getMobile() + "\n";
							cell = new PdfPCell(new Paragraph(hotel1, font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(50);
							maintable.addCell(cell);

							//第四列，停留几晚
							if (i == cityidList.size() - 1) {
								cell = new PdfPCell(new Paragraph((query.size() - 3) + "泊朝食", font));

							} else {
								cell = new PdfPCell(new Paragraph(query.size() + "泊朝食", font));

							}
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(50);
							maintable.addCell(cell);

							//第五列，需要几间房子
							String room1 = "";
							if (applyinfo.size() > 0) {
								if (applyinfo.size() % 2 == 1) {
									room1 = String.valueOf(applyinfo.size() / 2 + 1);
								} else {
									room1 = String.valueOf(applyinfo.size() / 2);
								}
							}
							cell = new PdfPCell(new Paragraph(room1, font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(50);
							maintable.addCell(cell);

							//第六列，回答
							cell = new PdfPCell(new Paragraph("手配 OK", font));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setFixedHeight(50);
							maintable.addCell(cell);
						}

					}

				} else {//中间不随机城市
					PdfPCell cell;
					//第一列，宿泊
					cell = new PdfPCell(new Paragraph("宿泊", font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setFixedHeight(50);
					maintable.addCell(cell);

					//第二列，时间
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String timeStampStr = simpleDateFormat.format(ordertripjp.getGoDate());
					cell = new PdfPCell(new Paragraph(timeStampStr, font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setFixedHeight(50);
					maintable.addCell(cell);

					//第三列，酒店信息
					String hotel = "";
					TOrderTravelplanJpEntity travelplanEntity = ordertravelplanList.get(0);
					THotelEntity hotelinfo = hotelViewService.fetch(travelplanEntity.getHotel());
					hotel = hotelinfo.getNamejp() + "\n" + hotelinfo.getAddressjp() + "\n" + hotelinfo.getMobile()
							+ "\n";
					cell = new PdfPCell(new Paragraph(hotel, font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setFixedHeight(50);
					maintable.addCell(cell);

					//第四列，停留几晚
					int stayday = DateUtil.daysBetween(ordertripjp.getGoDate(), ordertripjp.getReturnDate());
					cell = new PdfPCell(new Paragraph(String.valueOf(stayday) + "泊朝食", font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setFixedHeight(50);
					maintable.addCell(cell);

					//第五列，需要几间房子
					String room = "";
					if (applyinfo.size() > 0) {
						if (applyinfo.size() % 2 == 1) {
							room = String.valueOf(applyinfo.size() / 2 + 1);
						} else {
							room = String.valueOf(applyinfo.size() / 2);
						}
					}
					cell = new PdfPCell(new Paragraph(room, font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setFixedHeight(50);
					maintable.addCell(cell);

					//第六列，回答
					cell = new PdfPCell(new Paragraph("手配 OK", font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setFixedHeight(50);
					maintable.addCell(cell);
				}
			}

			document.add(maintable);

			Map<String, String> map = new HashMap<String, String>();
			map.put("comNameBig", company.getName());
			//			map.put("comNameSmall", company.getName());
			map.put("address", company.getAddress());
			map.put("telephone", company.getMobile());
			map.put("linkman", company.getLinkman());
			if (!Util.isEmpty(ordertripjp)) {
				if (ordertripjp.getTripType().equals(1)) {
					if (!Util.isEmpty(ordertripjp.getGoArrivedCity())) {
						TCityEntity cityentity = cityViewService.fetch(ordertripjp.getGoArrivedCity());
						map.put("city", cityentity.getCity());
					}
					if (!Util.isEmpty(ordertripjp.getGoDate())) {
						map.put("checkInDate", hoteldateformat.format(ordertripjp.getGoDate()).substring(5));
					}
					if (!Util.isEmpty(ordertripjp.getReturnDate())) {
						map.put("checkOutDate", hoteldateformat.format(ordertripjp.getReturnDate()).substring(5));
					}
				} else if (ordertripjp.getTripType().equals(2)) {
					if (!Util.isEmpty(mutiltrip)) {
						//多程第一程为出发日期
						TOrderTripMultiJpEntity entrytrip = mutiltrip.get(0);
						TCityEntity arrivecity = cityViewService.fetch(entrytrip.getArrivedCity());
						if (!Util.isEmpty(arrivecity)) {
							map.put("city", arrivecity.getCity());
						}
						//入住日期（出发日期）
						if (!Util.isEmpty(entrytrip.getDepartureDate())) {
							map.put("checkInDate", hoteldateformat.format(entrytrip.getDepartureDate()));
						}
						//最后一程作为返回日期
						TOrderTripMultiJpEntity returntrip = mutiltrip.get(mutiltrip.size() - 1);
						if (!Util.isEmpty(returntrip.getDepartureDate())) {
							map.put("checkOutDate", hoteldateformat.format(returntrip.getDepartureDate()));
						}
					}
				}
			}
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
			String room = null;
			if (applyinfo.size() > 0) {
				if (applyinfo.size() % 2 == 1) {

					int c = applyinfo.size() / 2 + 1;
					room = "TWN " + c + " 室";
					map.put("room", room);
				} else {
					int c = applyinfo.size() / 2;
					room = "TWN " + c + " 室";
					map.put("room", room);
				}
			}
			//酒店信息
			if (!Util.isEmpty(ordertravelplan)) {
				TOrderTravelplanJpEntity travelplanEntity = ordertravelplan.get(0);
				if (!Util.isEmpty(travelplanEntity.getHotel())) {
					THotelEntity hotelinfo = hotelViewService.fetch(travelplanEntity.getHotel());
					map.put("hotel", hotelinfo.getName());
				}
			}
			//获取模板文件
			URL resource = getClass().getClassLoader().getResource("japanfile/hotel.pdf");
			TemplateUtil templateUtil = new TemplateUtil();
			stream = templateUtil.pdfTemplateStream(resource, map);
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
		DateFormat applydateformat = new SimpleDateFormat("yyyy/MM/dd");
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
			PdfPTable table1 = new PdfPTable(2); //表格两列
			table1.setWidthPercentage(95);
			table1.setHorizontalAlignment(Element.ALIGN_CENTER); //垂直居中
			table1.setTotalWidth(PageSize.A4.rotate().getWidth());
			float[] wid1 = { 0.5f, 0.5f }; //两列宽度的比例
			table1.setWidths(wid1);
			//			table1.getDefaultCell().setBorderWidth(0); //不显示边框
			PdfPCell cell11 = new PdfPCell();
			Paragraph paragraph = new Paragraph("签证申请人名单", font1);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(paragraph);
			cell11.setBorder(0);
			table1.addCell(cell11);
			table1.getDefaultCell().setBorderWidth(0);
			document.add(table1);

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

						if (!record.get("isMainApplicant").equals(1)) {//副申请人
							if (wealthType.indexOf("银行流水") == -1) {
								wealthType = "银行流水\n" + wealthType;
								detail = "\n" + detail;
							}
						}

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
					table.addCell(cell);
				} else {
					//主申请人姓名
					//					副申请人ID
					TApplicantEntity applicant = new TApplicantEntity();
					if (!Util.isEmpty(record.get("MainId"))) {
						applicant = dbDao.fetch(TApplicantEntity.class, Cnd.where("id", "=", record.get("MainId")));
					}
					cell = new PdfPCell(new Paragraph(
							(!Util.isEmpty(record.get("mainRelation")) ? applicant.getFirstName()
									+ applicant.getLastName() + "(" + record.getString("mainRelation") + ")" : ""),
							font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

				}

				//旅行社意见
				cell = new PdfPCell(new Paragraph(
						(!Util.isEmpty(record.get("traveladvice")) ? record.getString("traveladvice") : ""), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}
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
			String pattern = "yy年MM月dd日";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
						godatestr = format(ordertripjp.getGoDate(), pattern);
					}
					if (!Util.isEmpty(ordertripjp.getReturnDate())) {
						returndatestr = format(ordertripjp.getReturnDate(), pattern);
					}
				} else if (ordertripjp.getTripType().equals(2)) {
					if (!Util.isEmpty(mutiltrip)) {
						//多程第一程为出发日期
						TOrderTripMultiJpEntity entrytrip = mutiltrip.get(0);
						if (!Util.isEmpty(entrytrip.getDepartureDate())) {
							godatestr = format(entrytrip.getDepartureDate(), pattern);
						}
						//最后一程作为返回日期
						TOrderTripMultiJpEntity returntrip = mutiltrip.get(mutiltrip.size() - 1);
						if (!Util.isEmpty(returntrip.getDepartureDate())) {
							//截取月 日
							returndatestr = format(returntrip.getDepartureDate(), pattern);
						}
					}
				}
			}

			{
				//				String subtitle = "（平成" + godatestr + "から平成" + returndatestr + "）";
				String subtitle = "(平成" + godatestr + "-" + returndatestr.substring(3, 8) + ")";
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
			String titles[] = { "年月日", "行動予定", "滞在先", };
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
							if (ordertravelplan.getHotel() != beforeplan.getHotel()) {
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

				img.setAbsolutePosition(350, height - totalHeight - 250);
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
			//多程信息
			List<TOrderTripMultiJpEntity> mutiltrip = (List<TOrderTripMultiJpEntity>) tempdata.get("mutiltrip");
			//申请人信息
			List<Record> applyinfo = (List<Record>) tempdata.get("applyinfo");
			//日本订单信息
			TOrderJpEntity orderjp = (TOrderJpEntity) tempdata.get("orderjp");
			//PDF操作开始
			Document document = new Document(PageSize.A4, 0, 0, 36, 36);
			PdfWriter.getInstance(document, stream);
			document.open();
			TtfClassLoader ttf = new TtfClassLoader();
			Font font = ttf.getFont();
			font.setSize(15);
			{
				Paragraph p = new Paragraph("BCYL35", font);
				p.setSpacingBefore(5);
				p.setIndentationLeft(22);
				p.setSpacingAfter(0);
				p.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(p);
			}
			int count = 1;
			for (Record record : applyinfo) {
				{
					String genderstr = " ";
					if (!Util.isEmpty(record.get("sex")) && "男".equals(record.getString("sex"))) {
						genderstr = " MR";
					} else {
						genderstr = " MS";
					}
					Paragraph p = new Paragraph(count + "." + record.getString("firstnameen") + " "
							+ record.getString("lastnameen") + " " + genderstr, font);
					count++;
					p.setSpacingBefore(5);
					p.setIndentationLeft(20);
					p.setAlignment(Paragraph.ALIGN_LEFT);
					document.add(p);
				}

			}
			{
				TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,
						Cnd.where("flightnum", "=", ordertripjp.getGoFlightNum()));
				Paragraph p = new Paragraph(count + "." + ordertripjp.getGoFlightNum().replace("*", "") + " 1 "
						+ FormatDateUtil.dateToOrderDate(ordertripjp.getGoDate()) + " " + goflight.getTakeOffCode()
						+ goflight.getLandingCode() + " HK" + applyinfo.size() + " " + goflight.getTakeOffTime() + " "
						+ goflight.getLandingTime() + " SEAME", font);
				count++;
				p.setSpacingBefore(5);
				p.setIndentationLeft(20);
				p.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(p);
			}
			{
				TFlightEntity returnflight = dbDao.fetch(TFlightEntity.class,
						Cnd.where("flightnum", "=", ordertripjp.getReturnFlightNum()));
				Paragraph p = new Paragraph(count + "." + ordertripjp.getReturnFlightNum().replace("*", "") + " 1 "
						+ FormatDateUtil.dateToOrderDate(ordertripjp.getReturnDate()) + " "
						+ returnflight.getTakeOffCode() + returnflight.getLandingCode() + " HK" + applyinfo.size()
						+ " " + returnflight.getTakeOffTime() + " " + returnflight.getLandingTime() + " SEAME", font);
				count++;
				p.setSpacingBefore(5);
				p.setIndentationLeft(20);
				p.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(p);
			}
			document.close();
			IOUtils.closeQuietly(stream);
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
			String pattern = "yy年MM月dd日";
			SimpleDateFormat tableformat = new SimpleDateFormat("yyyy/MM/dd");
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
				p.setSpacingAfter(15);
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
						godatestr = format(ordertripjp.getGoDate(), pattern);
					}
					if (!Util.isEmpty(ordertripjp.getReturnDate())) {
						returndatestr = format(ordertripjp.getReturnDate(), pattern);
					}
				} else if (ordertripjp.getTripType().equals(2)) {
					if (!Util.isEmpty(mutiltrip)) {
						//多程第一程为出发日期
						TOrderTripMultiJpEntity entrytrip = mutiltrip.get(0);
						if (!Util.isEmpty(entrytrip.getDepartureDate())) {
							godatestr = format(entrytrip.getDepartureDate(), pattern);
						}
						//最后一程作为返回日期
						TOrderTripMultiJpEntity returntrip = mutiltrip.get(mutiltrip.size() - 1);
						if (!Util.isEmpty(returntrip.getDepartureDate())) {
							returndatestr = format(returntrip.getDepartureDate(), pattern);
						}
					}
				}
			}
			{
				//				String subtitle = "（平成" + godatestr + "から平成" + returndatestr + "）";
				String subtitle = "（平成" + godatestr + "-" + returndatestr.substring(3, 8) + "）";
				Paragraph p = new Paragraph(subtitle, font);
				p.setSpacingBefore(5);
				p.setIndentationRight(20);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				//添加副标题1
				document.add(p);
			}

			//这里是表格********************************
			float[] columns = { 1, 3, 3, 1, 3, 3, 2.5f, 3 };
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
						((!Util.isEmpty(record.get("firstnameen")) ? record.getString("firstnameen") : "") + "\n" + (!Util
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

				img.setAbsolutePosition(350, height - totalHeight - 250);
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
