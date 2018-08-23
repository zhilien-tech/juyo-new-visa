/**
 * LiaoNingWanDaService.java
 * com.juyo.visa.admin.visajp.service
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.juyo.visa.admin.city.service.CityViewService;
import com.juyo.visa.admin.flight.service.FlightViewService;
import com.juyo.visa.admin.hotel.service.HotelViewService;
import com.juyo.visa.admin.scenic.service.ScenicViewService;
import com.juyo.visa.admin.visajp.util.TemplateUtil;
import com.juyo.visa.admin.visajp.util.TtfClassLoader;
import com.juyo.visa.common.base.QrCodeService;
import com.juyo.visa.common.enums.JobStatusEnum;
import com.juyo.visa.common.enums.MarryStatusEnum;
import com.juyo.visa.common.enums.SimpleVisaTypeEnum;
import com.juyo.visa.common.util.FormatDateUtil;
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
public class JinqiaoService extends BaseService<TOrderJpEntity> {

	@Inject
	private FlightViewService flightViewService;
	@Inject
	private HotelViewService hotelViewService;
	@Inject
	private ScenicViewService scenicViewService;
	@Inject
	private CityViewService cityViewService;
	@Inject
	private QrCodeService qrCodeService;

	private static DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

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
		cnd.orderBy("ta.id", "ASC");
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
		//申请人信息（赴日签证申请表）
		int count = 1;
		for (Record record : applyinfo) {
			ByteArrayOutputStream apply = applyinfo(record, tempdata, request, count);
			pdffiles.add(apply);
			count++;
		}
		//电子客票行程单
		ByteArrayOutputStream flightinfo = flightinfo(tempdata);
		pdffiles.add(flightinfo);

		/*ByteArrayOutputStream airticket = airticket(tempdata);
		pdffiles.add(airticket);*/
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
		DateFormat dateFormat = new SimpleDateFormat("MM 月  dd 日");
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
		if (!Util.isEmpty(orderjp.getGroundconnectid())) {
			TCompanyEntity dijiecompany = dbDao.fetch(TCompanyEntity.class, orderjp.getGroundconnectid().longValue());
			dijie = dijiecompany.getName();
		}
		String companyname = "";
		if (!Util.isEmpty(company.getName())) {
			companyname = company.getName();
		}
		//受理号为送签编号
		String sendVisaNum = orderinfo.getSendVisaNum();
		content.append("　　" + companyname).append("根据与").append(dijie).append("的合同约定，组织").append(applyinfo.size())
				.append("人访日个人旅游（受理号为").append(sendVisaNum).append("），请协助办理赴日").append(visatypestr).append("签证。");

		map.put("Text2", "1");
		map.put("Text1", content.toString());
		map.put("Text11", company.getName());
		map.put("Text3", company.getName());
		if (!Util.isEmpty(ordertripjp)) {
			if (ordertripjp.getTripType().equals(1)) {
				if (!Util.isEmpty(ordertripjp.getGoDate())) {
					map.put("Text4", dateFormat.format(ordertripjp.getGoDate()));
				}
				//入境航班
				if (!Util.isEmpty(ordertripjp.getGoFlightNum())) {
					String goFlightNum = ordertripjp.getGoFlightNum();

					map.put("Text5",
							goFlightNum.substring(goFlightNum.indexOf("-", goFlightNum.lastIndexOf("-")) + 1,
									goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")))
									+ "、 "
									+ goFlightNum.substring(goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")) + 1,
											goFlightNum.indexOf(" ", goFlightNum.indexOf(" ") + 1)));
				}
				//天数
				if (!Util.isEmpty(ordertripjp.getGoDate()) && !Util.isEmpty(ordertripjp.getReturnDate())) {

					map.put("Text6",
							String.valueOf(DateUtil.daysBetween(ordertripjp.getGoDate(), ordertripjp.getReturnDate()) + 1)
									+ "天");
				}
				//去除*号
				//map.put("Text13", ordertripjp.getGoFlightNum().replace("*", ""));
				if (!Util.isEmpty(ordertripjp.getReturnDate())) {
					map.put("Text7", dateFormat.format(ordertripjp.getReturnDate()));
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
					map.put("Text8",
							goFlightNum.substring(0, goFlightNum.indexOf("-", goFlightNum.indexOf("-")))
									+ "、 "
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
		map.put("Text9", company.getLinkman());
		map.put("Text10", company.getMobile());
		Date sendVisaDate = orderinfo.getSendVisaDate();
		if (!Util.isEmpty(sendVisaDate)) {
			map.put("Text12", dateFormat1.format(sendVisaDate));
		} else {
			map.put("Text12", "");
		}
		//获取模板文件
		URL resource = getClass().getClassLoader().getResource("japanfile/jinqiao/note.pdf");
		TemplateUtil templateUtil = new TemplateUtil();
		stream = templateUtil.pdfTemplateStream(resource, map);
		return stream;
	}

	/**
	 * 申请人信息
	 */
	private ByteArrayOutputStream applyinfo(Record record, Map<String, Object> tempdata, HttpServletRequest request,
			int count) {
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
			/*String province = (String) record.getString("province");
			if (province.endsWith("省") || province.endsWith("市")) {
				province = province.substring(0, province.length() - 1);
			}
			map.put("address", province);*/
			//map.put("address", record.getString("address"));
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
						if (goFlightNum.contains("//")) {//转机
							//入境口岸
							map.put("goArrivedCity",
									goFlightNum.substring(goFlightNum.lastIndexOf("-") + 1, goFlightNum.indexOf(" ")));
							//航空公司
							map.put("goFlightNum",
									goFlightNum.substring(goFlightNum.indexOf(" ") + 1, goFlightNum.indexOf("//")));
						} else {//直飞
							//入境口岸
							map.put("goArrivedCity",
									goFlightNum.substring(goFlightNum.indexOf("-") + 1, goFlightNum.indexOf(" ")));
							//航空公司
							map.put("goFlightNum",
									goFlightNum.substring(goFlightNum.indexOf(" ") + 1, goFlightNum.lastIndexOf(" ")));
						}
					}
					/*//入境口岸
					if (!Util.isEmpty(ordertripjp.getGoArrivedCity())) {
						TCityEntity goarrivecirtentity = cityViewService.fetch(ordertripjp.getGoArrivedCity());
						if (!Util.isEmpty(goarrivecirtentity)) {
							map.put("goArrivedCity", goarrivecirtentity.getCity());
						}
					}
					//航空公司.0
					TFlightEntity goflightentity = dbDao.fetch(TFlightEntity.class,
							Cnd.where("flightnum", "=", ordertripjp.getGoFlightNum()));
					if (!Util.isEmpty(goflightentity)) {
						map.put("goFlightNum", ordertripjp.getGoFlightNum());
					}*/
					//					if (!Util.isEmpty(entrytrip.getFlightNum())) {
					//						TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,
					//								Cnd.where("flightnum", "=", entrytrip.getFlightNum()));
					//						map.put("fill_22", goflight.getFlightnum());
					//					}
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

			map.put("hotelname", record.getString("hotelname"));
			map.put("hotelphone", record.getString("hotelphone"));
			map.put("hoteladdress", record.getString("hoteladdress"));
			//上次赴日日期及停留时间
			String lastinfo = "";
			/*if (!Util.isEmpty(orderjp.getLaststartdate()) && !Util.isEmpty(orderjp.getLastreturndate())) {
				lastinfo += dateFormat1.format(orderjp.getLaststartdate());
				lastinfo += "~";
				lastinfo += dateFormat1.format(orderjp.getLastreturndate());
				lastinfo += "      " + (Util.isEmpty(orderjp.getLaststayday()) ? "" : orderjp.getLaststayday());
			}*/
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
			map.put("danbaoname", record.getString("vouchname"));
			int lastIndexOf = record.getString("vouchname").lastIndexOf("-");
			/*map.put("danbaonameen",
					record.getString("vouchname").substring(lastIndexOf + 1, record.getString("vouchname").length()));*/
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
			/*map.put("homeaddress",
					(!Util.isEmpty(record.get("province")) ? record.getString("province") : " ")
							+ (!Util.isEmpty(record.get("city")) ? record.getString("city") : " ")
							+ (!Util.isEmpty(record.get("detailedaddress")) ? record.getString("detailedaddress") : " "));*/
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
			map.put("homeEmail", "无");
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
			//编写二维码
			if (!Util.isEmpty(map)) {
				insertQrCode(request, map, count);
			}
			URL resource = getClass().getClassLoader().getResource("japanfile/apply.pdf");
			TemplateUtil templateUtil = new TemplateUtil();
			stream = templateUtil.pdfTemplateStream(resource, map);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return stream;
	}

	/**
	 * 将图片写入PDF模版
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public void insertQrCode(HttpServletRequest request, Map<String, String> map, int count) throws Exception {
		// 模板文件路径
		URL resource = getClass().getClassLoader().getResource("japanfile/apply.pdf");
		String templatePath = null;
		String targetPath = null;
		if (!Util.isEmpty(resource)) {
			templatePath = resource.toString().replace("file:", "");
			// 生成的文件路径
			targetPath = resource.toString().replace("file:", "");
		}
		// 书签名
		//        String fieldName = "field";
		// 图片路径
		String imagePath = getImageUrl(request, map, count);
		// 读取模板文件
		InputStream input = null;
		if (!Util.isEmpty(targetPath)) {
			input = new FileInputStream(new File(templatePath));
		}
		PdfReader reader = null;
		if (!Util.isEmpty(input)) {
			reader = new PdfReader(input);
		}
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(targetPath));
		// 提取pdf中的表单
		AcroFields form = stamper.getAcroFields();
		// 通过域名获取所在页和坐标，左下角为起点
		//        int pageNo = form.getFieldPositions(fieldName).get(0).page;
		//        Rectangle signRect = form.getFieldPositions(fieldName).get(0).position;
		//        float x = signRect.getLeft();
		//        float y = signRect.getBottom();
		// 读图片
		Image image = null;
		if (!Util.isEmpty(imagePath)) {
			image = Image.getInstance(imagePath);
		}
		// 获取操作的页面
		PdfContentByte under = stamper.getOverContent(1);
		// 根据域的大小缩放图片
		image.scaleToFit(65, 60);
		// 添加图片
		image.setAbsolutePosition(70, 670);
		under.addImage(image);
		stamper.close();
		reader.close();

	}

	/***
	 * 生成二维码
	 * 
	 */
	public String getImageUrl(HttpServletRequest request, Map<String, String> map, int count) {
		//姓
		String firstNameEn = map.get("firstNameEn");
		//名
		String lastNameEn = map.get("lastNameEn");
		//性别
		String sex = null;
		if (!Util.isEmpty(map.get("boy"))) {
			if (map.get("boy").equals("0")) {
				sex = "M";
			}
		}
		if (!Util.isEmpty(map.get("gril"))) {
			if (map.get("gril").equals("0")) {
				sex = "F";
			}
		}
		//出生日期
		String birthday = null;
		if (!Util.isEmpty(map.get("birthday"))) {
			birthday = map.get("birthday");
		}
		//年份
		String year = birthday.substring(6, 10);
		String month = birthday.substring(3, 5);
		String day = birthday.substring(0, 2);
		birthday = year + month + day;
		//护照号
		String passport = map.get("passport");
		//护照有效期
		String validEndDate = null;
		if (!Util.isEmpty(map.get("validEndDate"))) {
			validEndDate = map.get("validEndDate");
		}
		String year1 = validEndDate.substring(6, 10);
		String month1 = validEndDate.substring(3, 5);
		String day1 = validEndDate.substring(0, 2);
		validEndDate = year1 + month1 + day1;
		//身份证号
		String cardId = map.get("cardId");
		//二维码显示内容  序号，护照号，护照截止日期，姓 ，名，性别，出生日期，固定CHN，身份证号
		String passporturl = count + "," + passport + "," + validEndDate + "," + firstNameEn + "," + lastNameEn + ","
				+ sex + "," + birthday + "," + "CHN" + "," + cardId + "," + " " + "," + " ";
		//生成二维码
		String qrCode = qrCodeService.encodeQrCode(request, passporturl);
		return qrCode;
	}

	/**
	 * 酒店信息
	 */
	public ByteArrayOutputStream hotelInfo(Map<String, Object> tempdata) {
		DateFormat hoteldateformat = new SimpleDateFormat("yyyy-MM-dd");
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
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

			//第一个表格
			float[] firstcolumns = { 13 };
			PdfPTable titletable = new PdfPTable(firstcolumns);
			titletable.setWidthPercentage(95);
			titletable.setTotalWidth(PageSize.A4.getWidth());

			PdfPCell titlecell = new PdfPCell(new Paragraph("手配確認書", font));
			titlecell.setFixedHeight(50);
			titlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
			titlecell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			titletable.addCell(titlecell);

			document.add(titletable);

			//第二个表格，空表格
			float[] kongcolumns = { 13 };
			PdfPTable kongtable = new PdfPTable(kongcolumns);
			kongtable.setWidthPercentage(95);
			kongtable.setTotalWidth(PageSize.A4.getWidth());
			font.setSize(8);

			PdfPCell kongcell = new PdfPCell(new Paragraph("", font));
			kongcell.setFixedHeight(20);
			kongcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			kongcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			kongcell.setBorder(0);
			kongtable.addCell(kongcell);

			document.add(kongtable);

			//第三个表格
			float[] secondcolumns = { 5, 8 };

			PdfPTable secondtable = new PdfPTable(secondcolumns);
			secondtable.setWidthPercentage(95);
			secondtable.setTotalWidth(PageSize.A4.getWidth());
			font.setSize(14.33f);

			PdfPCell secondcell = new PdfPCell(new Paragraph("宿泊者（氏名）", font));
			secondcell.setFixedHeight(30);
			secondcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			secondcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			secondtable.addCell(secondcell);

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
				}
			}
			int applysize = applyinfo.size() - 1;
			strb.append(" 他 ").append(applysize).append(" 名");

			secondcell = new PdfPCell(new Paragraph(strb.toString(), font));
			secondcell.setFixedHeight(30);
			secondcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			secondcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			secondtable.addCell(secondcell);

			document.add(secondtable);

			//第四个表格，最主要数据
			float[] maincolumns = { 2, 3, 4, 2, 1, 1 };
			PdfPTable maintable = new PdfPTable(maincolumns);
			maintable.setWidthPercentage(95);
			maintable.setTotalWidth(PageSize.A4.getWidth());

			//设置表头
			String titles[] = { "项目", "日時", "ホテル名", "部屋タイプ", "部屋數", "回答", };
			font.setSize(7.96f);
			for (String title : titles) {
				PdfPCell cell = new PdfPCell(new Paragraph(title, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(30);
				maintable.addCell(cell);
			}

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

					/*if(ordertravelplanList.get(0).getCityId() == ordertravelplanList.get(ordertravelplanList.size() - 1).getCityId()){//第一个城市和最后一个城市相同，需要特殊处理
						if(i == cityidList.size() - 1){//返回城市的第一天
							outDate = query.get(2).getOutDate();
						}else{
							outDate = fetch.getOutDate();
						}
					}else{
						outDate = fetch.getOutDate();
					}*/
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

			//第五个表格
			float[] lastcolumns = { 2, 11 };
			PdfPTable lasttable = new PdfPTable(lastcolumns);
			lasttable.setWidthPercentage(95);
			lasttable.setTotalWidth(PageSize.A4.getWidth());

			String lasttitles[] = { "備考", "", };
			font.setSize(7.96f);
			for (String title : lasttitles) {
				PdfPCell cell = new PdfPCell(new Paragraph(title, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(30);
				lasttable.addCell(cell);
			}

			document.add(lasttable);

			//表格的高度
			float totalHeight = titletable.getTotalHeight() + kongtable.getTotalHeight() + secondtable.getTotalHeight()
					+ maintable.getTotalHeight() + lasttable.getTotalHeight();
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
			return stream;
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*Map<String, String> map = new HashMap<String, String>();
		if (!Util.isEmpty(ordertripjp)) {
			if (ordertripjp.getTripType().equals(1)) {
				if (!Util.isEmpty(ordertripjp.getGoDate())) {
					map.put("Text22", hoteldateformat.format(ordertripjp.getGoDate()));
				}
				//逗留时间
				if (!Util.isEmpty(ordertripjp.getGoDate()) && !Util.isEmpty(ordertripjp.getReturnDate())) {
					int stayday = DateUtil.daysBetween(ordertripjp.getGoDate(), ordertripjp.getReturnDate());
					map.put("Text24", String.valueOf(stayday) + "泊朝食");
				}
			} else if (ordertripjp.getTripType().equals(2)) {
				if (!Util.isEmpty(mutiltrip)) {
					//逗留时间
					if (!Util.isEmpty(ordertripjp.getGoDate()) && !Util.isEmpty(ordertripjp.getReturnDate())) {
						int stayday = DateUtil.daysBetween(ordertripjp.getGoDate(), ordertripjp.getReturnDate());
						map.put("Text24", String.valueOf(stayday) + "泊朝食");
					}
					//多程第一程为出发日期
					TOrderTripMultiJpEntity entrytrip = mutiltrip.get(0);
					//入住日期（出发日期）
					if (!Util.isEmpty(entrytrip.getDepartureDate())) {
						map.put("Text22", hoteldateformat.format(entrytrip.getDepartureDate()));
					}
				}
			}
		}
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
			}
		}
		int applysize = applyinfo.size() - 1;
		strb.append(" 他 ").append(applysize).append(" 名");
		map.put("Text21", strb.toString());
		String room = null;
		if (applyinfo.size() > 0) {
			if (applyinfo.size() % 2 == 1) {

				int c = applyinfo.size() / 2 + 1;
				map.put("Text25", c + "");
			} else {
				int c = applyinfo.size() / 2;
				room = "TWN " + c + " 室";
				map.put("Text25", c + "");
			}
		}
		//酒店信息
		if (!Util.isEmpty(ordertravelplan)) {
			TOrderTravelplanJpEntity travelplanEntity = ordertravelplan.get(0);
			if (!Util.isEmpty(travelplanEntity.getHotel())) {
				THotelEntity hotelinfo = hotelViewService.fetch(travelplanEntity.getHotel());
				map.put("Text23",
						"\n" + hotelinfo.getNamejp() + "\n" + hotelinfo.getAddressjp() + "\n"
								+ hotelinfo.getMobile());
			}
		}
		//获取模板文件
		URL resource = getClass().getClassLoader().getResource("japanfile/huanyu/hotel.pdf");
		TemplateUtil templateUtil = new TemplateUtil();
		stream = templateUtil.pdfTemplateStream(resource, map);
		} catch (Exception e) {
		e.printStackTrace();
		}*/
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
			font.setSize(8.25f);
			Font font1 = ttf.getFont();
			font1.setFamily("宋体");
			font1.setSize(17);
			PdfPTable table1 = new PdfPTable(1); //表格一列
			table1.setWidthPercentage(95);
			table1.setHorizontalAlignment(Element.ALIGN_CENTER); //垂直居中
			table1.setTotalWidth(PageSize.A4.rotate().getWidth());
			PdfPCell cell11 = new PdfPCell();
			Paragraph paragraph = new Paragraph("签证申请人名单", font);
			paragraph.setAlignment(Element.ALIGN_LEFT);
			cell11.addElement(paragraph);
			cell11.setBorder(0);
			table1.addCell(cell11);
			table1.getDefaultCell().setBorderWidth(0);
			document.add(table1);

			float[] columns = { 2, 4, 4, 2, 4, 3, 4, 3, 3, 4, 2, 3, 4, 3, 2, 4, };
			PdfPTable table = new PdfPTable(columns);
			table.setWidthPercentage(95);
			table.setTotalWidth(PageSize.A4.rotate().getWidth());

			//设置表头
			String titles[] = { "编号", "姓名(中文)", "姓名(英文)", "性别", "护照发行地", "居住地点", "出生年月日", "护照号", "职业", "出境记录", "婚姻",
					"身份确认", "经济能力确认", "金额", "备注", "旅行社意见", };
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
				/*if (!Util.isEmpty(record.get("careerstatus"))) {
					for (JobStatusEnum jobstatusenum : JobStatusEnum.values()) {
						if (record.getInt("careerstatus") == jobstatusenum.intKey()) {
							careerstatus = jobstatusenum.value();
						}
					}
				}*/
				String marryStatus = "";
				if (!Util.isEmpty(record.get("marrystatus"))) {
					for (MarryStatusEnum marrystatusenum : MarryStatusEnum.values()) {
						if (marrystatusenum.intKey() == record.getInt("marrystatus")) {
							marryStatus = marrystatusenum.value();
						}
					}
				}
				//护照号
				String passportNo = "";
				if (!Util.isEmpty(record.get("passportno"))) {
					passportNo = (String) record.get("passportno");
				}
				List<TApplicantWealthJpEntity> wealthjpinfo = (List<TApplicantWealthJpEntity>) record
						.get("wealthjpinfo");
				/*boolean flag = false;
				if (wealthjpinfo.size() > 0) {
					flag = true;
				}*/
				//
				PdfPCell cell;
				//序号
				cell = new PdfPCell(new Paragraph(String.valueOf(count), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				/*if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}*/
				table.addCell(cell);
				//中文姓名
				cell = new PdfPCell(new Paragraph(
						(!Util.isEmpty(record.get("firstname")) ? record.getString("firstname") : "")
								+ (!Util.isEmpty(record.get("lastname")) ? record.getString("lastname") : ""), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				/*if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}*/
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
				/*if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}*/
				table.addCell(cell);
				//性别
				cell = new PdfPCell(new Paragraph((!Util.isEmpty(record.get("sex")) ? record.getString("sex") : ""),
						font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				/*if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}*/
				table.addCell(cell);
				//护照签发地
				cell = new PdfPCell(new Paragraph(
						(!Util.isEmpty(record.get("issuedplace")) ? record.getString("issuedplace") : ""), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				/*if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}*/
				table.addCell(cell);
				//居住地
				//				cell = new PdfPCell(new Paragraph((!Util.isEmpty(record.get("province")) ? record.getString("province")
				//						: "")
				//						+ (!Util.isEmpty(record.get("city")) ? record.getString("city") : "")
				//						+ (!Util.isEmpty(record.get("detailedaddress")) ? record.getString("detailedaddress") : ""),
				//						font));
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
				/*if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}*/
				table.addCell(cell);
				//出生日期
				cell = new PdfPCell(new Paragraph(birthdaystr, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				/*if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}*/
				table.addCell(cell);
				//护照号
				cell = new PdfPCell(new Paragraph(passportNo, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				/*if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}*/
				table.addCell(cell);
				//职业
				cell = new PdfPCell(new Paragraph(position, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				/*if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}*/
				table.addCell(cell);
				//出境记录
				String outboundrecord = "良好";
				if (!Util.isEmpty(record.get("outboundrecord"))) {
					outboundrecord = (String) record.get("outboundrecord");
				}
				cell = new PdfPCell(new Paragraph(outboundrecord, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				/*if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}*/
				table.addCell(cell);
				//婚姻
				cell = new PdfPCell(new Paragraph(marryStatus, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				/*if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}*/
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
				/*if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}*/
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
				/*if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}*/
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
			PdfPCell cell2 = new PdfPCell();
			Paragraph paragraph2 = new Paragraph(company.getName(), font);
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
			//多程信息
			List<TOrderTripMultiJpEntity> mutiltrip = (List<TOrderTripMultiJpEntity>) tempdata.get("mutiltrip");
			DateFormat df = new SimpleDateFormat("ddMMM", Locale.ENGLISH);
			Map<String, String> map = new HashMap<String, String>();
			StringBuffer strb = new StringBuffer("");

			for (Record record : applyinfo) {
				if (!Util.isEmpty(record.get("firstnameen"))) {
					strb.append(record.getString("firstnameen"));
				}
				if (!Util.isEmpty(record.get("lastnameen"))) {
					strb.append("/" + record.getString("lastnameen"));
				}
				strb.append("；");
			}
			//旅客姓名
			map.put("Text1", strb.toString().toUpperCase());
			//座位等级
			map.put("Text7", "Y");
			map.put("Text8", "Y");

			//客票状态
			map.put("Text18", "OK");
			map.put("Text17", "OK");

			//行李
			map.put("Text19", applyinfo.size() + "PC");
			map.put("Text20", applyinfo.size() + "PC");

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

								map.put("Text22", "Y");
								map.put("Text31", "Y");
								map.put("Text27", "OK");
								map.put("Text36", "OK");
								map.put("Text28", applyinfo.size() + "PC");
								map.put("Text37", applyinfo.size() + "PC");

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

								map.put("Text22", "Y");
								map.put("Text27", "OK");
								map.put("Text28", applyinfo.size() + "PC");
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

			//获取模板文件
			URL resource = getClass().getClassLoader().getResource("japanfile/huanyu/flight.pdf");
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
			font.setSize(17.55f);
			font.setFamily("宋体");
			{
				Paragraph p = new Paragraph("滞在予定表", font);
				p.setSpacingBefore(5);
				p.setSpacingAfter(5);
				p.setAlignment(Paragraph.ALIGN_CENTER);
				document.add(p);
				Paragraph np = new Paragraph("", font);
				np.setSpacingBefore(5);
				np.setSpacingAfter(5);
				np.setAlignment(Paragraph.ALIGN_CENTER);
				document.add(np);
			}
			font.setSize(7.98f);
			//日期格式化
			String pattern = "yy年MM月dd日";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
					if (Integer.valueOf(day) != ordertravelplans.size()) {//不是最后一天
						THotelEntity hotelentity = hotelViewService.fetch(ordertravelplan.getHotel());
						hotel = hotelentity.getNamejp() + "\n" + hotelentity.getAddressjp() + "\n"
								+ hotelentity.getMobile();
					} else {
						hotel = " ";
					}
				} else {
					String day = ordertravelplan.getDay();
					if (Integer.valueOf(day) != ordertravelplans.size()) {//不是最后一天
						TOrderTravelplanJpEntity fetch = dbDao.fetch(
								TOrderTravelplanJpEntity.class,
								Cnd.where("orderId", "=", ordertravelplan.getOrderId()).and("day", "=",
										Integer.valueOf(day) - 1));
						THotelEntity hotelentity = hotelViewService.fetch(fetch.getHotel());
						hotel = hotelentity.getNamejp() + "\n" + hotelentity.getAddressjp() + "\n"
								+ hotelentity.getMobile();
					} else {
						hotel = " ";
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
					Paragraph p = new Paragraph(count + "." + record.getString("firstnameen") + "/"
							+ record.getString("lastnameen") + genderstr, font);
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
			/*{
				Paragraph p = new Paragraph(count + ".SHE/T SHE/T024-2709064/JIN CHENG TKT AGENCY/JIAHUI XU ABCDEFG",
						font);
				count++;
				p.setSpacingBefore(5);
				p.setIndentationLeft(20);
				p.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(p);
			}
			{
				Paragraph p = new Paragraph(count + ".TL/2010/18MAR/SHE104", font);
				count++;
				p.setSpacingBefore(5);
				p.setIndentationLeft(20);
				p.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(p);
			}
			{
				Paragraph p = new Paragraph(count + ".SHE104", font);
				count++;
				p.setSpacingBefore(5);
				p.setIndentationLeft(20);
				p.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(p);
			}*/
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
			img.scaleToFit(280, 180);//大小
			//img.setIndentationRight(200);
			img.setRotation(800);
			if (n <= 1) {

				img.setAbsolutePosition(350, 530);
			} else {

				img.setAbsolutePosition(350, 530 - 34 * (n - 1));
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
			font.setSize(13.56f);
			{
				Paragraph p = new Paragraph("  （别纸）査証申請人名簿", font);
				p.setSpacingBefore(5);
				p.setSpacingAfter(5);
				p.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(p);

				Paragraph np = new Paragraph("", font);
				np.setSpacingBefore(5);
				np.setSpacingAfter(5);
				np.setAlignment(Paragraph.ALIGN_LEFT);
				document.add(np);
			}
			font.setSize(7.98f);
			//日期格式化
			SimpleDateFormat tableformat = new SimpleDateFormat("yyyy-MM-dd");
			//这里是表格********************************
			float[] columns = { 1, 3, 3, 1, 2.5f, 3, 2, 3 };
			PdfPTable table = new PdfPTable(columns);
			table.setWidthPercentage(95);
			table.setTotalWidth(PageSize.A4.getWidth());

			//设置表头
			String titles[] = { "番号", "氏名（简体）", "氏名（ピンイン）", "性別", "居住地域", "生年月日", "旅券番号", "職業", };
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
				String[] data = {
						String.valueOf(count),
						//姓名
						(!Util.isEmpty(record.get("firstname")) ? record.getString("firstname") : "")
								+ (!Util.isEmpty(record.get("lastname")) ? record.getString("lastname") : ""),
						//姓名英文
						((!Util.isEmpty(record.get("firstnameen")) ? record.getString("firstnameen") : "") + " " + (!Util
								.isEmpty(record.get("lastnameen")) ? record.getString("lastnameen") : ""))
								.toUpperCase(), !Util.isEmpty(record.get("sex")) ? record.getString("sex") : "",
						province,
						!Util.isEmpty(record.get("birthday")) ? tableformat.format((Date) record.get("birthday")) : "",
						record.getString("passportno"), careerstatus };
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
			/*//公章地址
			String sealUrl = "";
			if (!Util.isEmpty(orderjp.getGroundconnectid())) {
				TCompanyEntity dijiecompany = dbDao.fetch(TCompanyEntity.class, orderjp.getGroundconnectid()
						.longValue());
				sealUrl = dijiecompany.getSeal();
			}
			if (!Util.isEmpty(sealUrl)) {
				document.add(getSeal1(sealUrl, 1));
			}*/
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
