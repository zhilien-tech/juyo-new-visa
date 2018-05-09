/**
 * DownLoadVisaFileService.java
 * com.juyo.visa.admin.visajp.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
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
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.IOUtils;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.joda.time.DateTime;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
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
import com.juyo.visa.common.base.QrCodeService;
import com.juyo.visa.common.enums.BusinessScopesEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JobStatusEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.common.enums.MarryStatusEnum;
import com.juyo.visa.entities.TApplicantWealthJpEntity;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.TComBusinessscopeEntity;
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
 * @Date	 2017年11月15日 	 
 */
@IocBean
public class DownLoadVisaFileService extends BaseService<TOrderJpEntity> {

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
	public ByteArrayOutputStream generateFile(TOrderJpEntity orderjp) {

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
		//
		for (Record record : applyinfo) {
			ByteArrayOutputStream apply = applyinfo(record, tempdata);
			pdffiles.add(apply);
		}
		//excel名称日期格式化
		DateFormat excelnameformat = new SimpleDateFormat("yy.MM.dd");
		//excel名称
		String excelname = "";
		if (!Util.isEmpty(ordertripjp)) {
			if (ordertripjp.getTripType().equals(1)) {
				if (!Util.isEmpty(ordertripjp.getGoDate())) {
					String godatestr = excelnameformat.format(ordertripjp.getGoDate());
					excelname += godatestr;
				}
				excelname += "-";
				if (!Util.isEmpty(ordertripjp.getReturnDate())) {
					String returndatestr = excelnameformat.format(ordertripjp.getReturnDate());
					excelname += returndatestr;
				}
			} else if (ordertripjp.getTripType().equals(2)) {
				if (!Util.isEmpty(mutiltrip)) {
					//多程第一程为出发日期
					TOrderTripMultiJpEntity entrytrip = mutiltrip.get(0);
					if (!Util.isEmpty(entrytrip.getDepartureDate())) {
						String godatestr = excelnameformat.format(entrytrip.getDepartureDate());
						excelname += godatestr;
					}
					excelname += "-";
					//最后一程作为返回日期
					TOrderTripMultiJpEntity returntrip = mutiltrip.get(mutiltrip.size() - 1);
					if (!Util.isEmpty(returntrip.getDepartureDate())) {
						String returndatestr = excelnameformat.format(returntrip.getDepartureDate());
						excelname += returndatestr;
					}
				}
			}
		}
		String applyname = "";
		if (!Util.isEmpty(applyinfo)) {
			Record record = applyinfo.get(0);
			applyname += record.getString("firstname");
			applyname += record.getString("lastname");
		}
		excelname += applyname;
		excelname += applyinfo.size() + "人.xlsx";
		//酒店信息
		ByteArrayOutputStream hotelInfo = hotelInfo(tempdata);
		pdffiles.add(hotelInfo);
		//申请人名单
		ByteArrayOutputStream applyList = applyList(tempdata);
		pdffiles.add(applyList);
		//航班信息
		ByteArrayOutputStream flightinfo = flightinfo(tempdata);
		pdffiles.add(flightinfo);
		//滞在予定表
		ByteArrayOutputStream tripInfo = tripInfo(tempdata);
		pdffiles.add(tripInfo);
		//査 証 申 請 人 名 簿
		ByteArrayOutputStream book = book(tempdata);
		pdffiles.add(book);

		ByteArrayOutputStream mergePdf = templateUtil.mergePdf(pdffiles);
		fileMap.put("照会.pdf", templateUtil.createTempFile(mergePdf));
		ByteArrayOutputStream bodyElement = bodyElement(tempdata);
		fileMap.put("身元.docx", templateUtil.createTempFile(bodyElement));
		//excel导出
		ByteArrayOutputStream excelExport = excelExport(tempdata);
		fileMap.put(excelname, templateUtil.createTempFile(excelExport));
		//fileMap.put("apply.pdf", templateUtil.createTempFile(apply));
		stream = templateUtil.mergeToZip(fileMap);
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
		//订单信息
		TOrderEntity order = dbDao.fetch(TOrderEntity.class,Cnd.where("id", "=", orderjp.getOrderId()));
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
		if (!Util.isEmpty(orderjp.getGroundconnectid())) {
			TCompanyEntity dijiecompany = dbDao.fetch(TCompanyEntity.class, orderjp.getGroundconnectid().longValue());
			dijie = dijiecompany.getName();
		}
		String companyname = "";
		if (!Util.isEmpty(company.getName())) {
			companyname = company.getName();
		}
		content.append("　　" + companyname).append("根据与").append(dijie).append("的合同约定，组织").append(applyinfo.size())
				.append("人访日个人旅游，请协助办理").append(visatypestr).append("往返赴日签证");
		map.put("content", content.toString());
		map.put("company", company.getName());
		//如果是制定的送签社
		if (!Util.isEmpty(company.getIsCustomer()) && company.getIsCustomer().equals(IsYesOrNoEnum.YES.intKey())) {
			map.put("id", company.getCdesignNum());
		} else {
			TComBusinessscopeEntity comBusiness = dbDao.fetch(TComBusinessscopeEntity.class,
					Cnd.where("comId", "=", company.getId()).and("countryId", "=", BusinessScopesEnum.JAPAN.intKey()));
			if (!Util.isEmpty(comBusiness)) {
				map.put("id", comBusiness.getDesignatedNum());
			}
		}
		if (!Util.isEmpty(ordertripjp)) {
			if (ordertripjp.getTripType().equals(1)) {
				if (!Util.isEmpty(ordertripjp.getGoDate())) {
					map.put("entryDate", dateFormat.format(ordertripjp.getGoDate()));
				}
				//入境航班
				/*if (!Util.isEmpty(ordertripjp.getGoFlightNum())) {
					TFlightEntity goflight = flightViewService.fetch(ordertripjp.getGoFlightNum().longValue());
				}*/
				//入境航班
				if (!Util.isEmpty(ordertripjp.getGoFlightNum())) {
					TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,Cnd.where("flightnum", "=", ordertripjp.getGoFlightNum()));
					map.put("entryPort", goflight.getLandingName()+":");
				}
				map.put("entryFlight", ordertripjp.getGoFlightNum().replace("*",""));
				if (!Util.isEmpty(ordertripjp.getReturnDate())) {
					map.put("departDate", dateFormat.format(ordertripjp.getReturnDate()));
				}
				//天数
				if (!Util.isEmpty(ordertripjp.getGoDate()) && !Util.isEmpty(ordertripjp.getReturnDate())) {

					map.put("stay", String.valueOf(DateUtil.daysBetween(ordertripjp.getGoDate(),
							ordertripjp.getReturnDate()) + 1)+"天");
				}
				/*if (!Util.isEmpty(ordertripjp.getReturnFlightNum())) {
					//出境航班
					TFlightEntity returnflight = flightViewService.fetch(ordertripjp.getReturnFlightNum().longValue());
					map.put("departFlight", returnflight.getFlightnum());
				}*/
				if (!Util.isEmpty(ordertripjp.getReturnFlightNum())) {
					//出境航班
//					TFlightEntity returnflight = flightViewService.fetch(ordertripjp.getReturnFlightNum().longValue());
//					map.put("departFlight", returnflight.getFlightnum());
					TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,Cnd.where("flightnum", "=", ordertripjp.getReturnFlightNum()));
					map.put("departPort", goflight.getLandingName()+":");
				}
				map.put("departFlight", ordertripjp.getReturnFlightNum().replace("*", ""));
			} else if (ordertripjp.getTripType().equals(2)) {
				//多程处理
				if (!Util.isEmpty(mutiltrip)) {
					//多程第一程为出发日期
					TOrderTripMultiJpEntity entrytrip = mutiltrip.get(0);
					if (!Util.isEmpty(entrytrip.getDepartureDate())) {
						map.put("entryDate", dateFormat.format(entrytrip.getDepartureDate()));
					}
					//入境航班
					/*if (!Util.isEmpty(entrytrip.getFlightNum())) {
						TFlightEntity goflight = flightViewService.fetch(entrytrip.getFlightNum().longValue());
					}*/
					//入境航班
					if (!Util.isEmpty(ordertripjp.getGoFlightNum())) {
						TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,Cnd.where("flightnum", "=", ordertripjp.getGoFlightNum()));
						map.put("entryPort", goflight.getLandingName()+":");
					}
					map.put("entryFlight", entrytrip.getFlightNum().replaceAll("*", ""));
					//最后一程作为返回日期
					TOrderTripMultiJpEntity returntrip = mutiltrip.get(mutiltrip.size() - 1);
					if (!Util.isEmpty(returntrip.getDepartureDate())) {
						map.put("departDate", dateFormat.format(returntrip.getDepartureDate()));
					}
					/*if (!Util.isEmpty(returntrip.getFlightNum())) {
						//出境航班
						TFlightEntity returnflight = flightViewService.fetch(returntrip.getFlightNum().longValue());
						map.put("departFlight", returnflight.getFlightnum());
					}*/
					if (!Util.isEmpty(ordertripjp.getReturnFlightNum())) {
						//出境航班
//						TFlightEntity returnflight = flightViewService.fetch(ordertripjp.getReturnFlightNum().longValue());
//						map.put("departFlight", returnflight.getFlightnum());
						TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,Cnd.where("flightnum", "=", ordertripjp.getReturnFlightNum()));
						map.put("departPort", goflight.getLandingName()+":");
					}
					map.put("departFlight", returntrip.getFlightNum());
					//停留天数
					if (!Util.isEmpty(entrytrip.getDepartureDate()) && !Util.isEmpty(returntrip.getDepartureDate())) {
						map.put("stay",
								String.valueOf(DateUtil.daysBetween(entrytrip.getDepartureDate(),
										returntrip.getDepartureDate())));
					}
				}
			}
		}
		map.put("linkman", company.getLinkman());
		map.put("telephone", company.getMobile());
		map.put("phone", company.getMobile());
		map.put("createDate", dateFormat.format(new Date()));
		//受理日和发给日
		map.put("startDate", dateFormat.format(order.getSendVisaDate()).substring(5, 11));
		map.put("endDate", dateFormat.format(order.getOutVisaDate()).substring(5, 11));
		//获取模板文件
		URL resource = getClass().getClassLoader().getResource("japanfile/note.pdf");
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
		TOrderJpEntity orderjp = (TOrderJpEntity) tempdata.get("orderjp");
		
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
			map.put("othernameen", otherfirstnameen + otherlastnameen);
			map.put("othername", otherfirstname + otherlastname);
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
			map.put("address", record.getString("address"));
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
			map.put("country", "中国");
			//曾有的或另有的国际
//			if(Util.isEmpty(record.getString("nationality"))) {
//				map.put("othernationality", "无");
//			}
			map.put("othernationality", record.getString("nationality"));
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
						map.put("goDate", dateformat.format(ordertripjp.getGoDate()));
					}
					//返回时间
					if (!Util.isEmpty(ordertripjp.getReturnDate())) {
						map.put("returnDate", dateformat.format(ordertripjp.getReturnDate()));
					}
					//逗留时间
					if (!Util.isEmpty(ordertripjp.getGoDate()) && !Util.isEmpty(ordertripjp.getReturnDate())) {
						int stayday = DateUtil.daysBetween(ordertripjp.getGoDate(), ordertripjp.getReturnDate());
						map.put("stayday", String.valueOf(stayday) + "天");
					}
					//入境口岸
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
						map.put("goFlightNum", goflightentity.getAirlinecomp());
					}
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
							map.put("goDate", dateformat.format(entrytrip.getDepartureDate()));
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
							map.put("goFlightNum", goflight.getFlightnum());
						}
						//最后一程作为返回日期
						TOrderTripMultiJpEntity returntrip = mutiltrip.get(mutiltrip.size() - 1);
						if (!Util.isEmpty(returntrip.getDepartureDate())) {
							map.put("returnDate", dateformat.format(returntrip.getDepartureDate()));
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
			String lastinfo = "";
			if (!Util.isEmpty(orderjp.getLaststartdate())) {
				lastinfo += dateformat.format(orderjp.getLaststartdate());
			}
			lastinfo += "~";
			if (!Util.isEmpty(orderjp.getLastreturndate())) {
				lastinfo += dateformat.format(orderjp.getLastreturndate());
			}
			lastinfo += "      " + (Util.isEmpty(orderjp.getLaststayday()) ? "" : orderjp.getLaststayday());
			map.put("fill_26", lastinfo+"天");
			//在日担保人信息
			map.put("danbaoname", record.getString("vouchname"));
			 int lastIndexOf = record.getString("vouchname").lastIndexOf("-");
			map.put("danbaonameen", record.getString("vouchname").substring(lastIndexOf+1,  record.getString("vouchname").length()));
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
			map.put("homeaddress",
					(!Util.isEmpty(record.get("province")) ? record.getString("province") : " ")
							+ (!Util.isEmpty(record.get("city")) ? record.getString("city") : " ")
							+ (!Util.isEmpty(record.get("detailedaddress")) ? record.getString("detailedaddress") : " "));
			//家庭电话
			//map.put("homephone", record.getString("telephone"));
			map.put("homemobile", record.getString("telephone"));
			//电子邮箱
			map.put("homeEmail", record.getString("email"));
			//工作单位
			map.put("workname", record.getString("workname"));
			map.put("workphone", record.getString("workphone"));
			map.put("workaddress", record.getString("workaddress"));
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
			URL resource = getClass().getClassLoader().getResource("japanfile/apply.pdf");
			TemplateUtil templateUtil = new TemplateUtil();
			stream = templateUtil.pdfTemplateStream(resource, map);
			//编写二维码
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

			Map<String, String> map = new HashMap<String, String>();
			map.put("comNameBig", company.getName());
			map.put("comNameSmall", company.getName());
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
			if(applyinfo.size()>0){
				if(applyinfo.size()%2 ==1) {
				
				int c = applyinfo.size()/2+1;
			   room = "TWN "+c+" 室";
					map.put("room", room);
			}else {
				int c = applyinfo.size()/2;
				   room = "TWN "+c+" 室";
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
			map.put("breakfast", "無");
			map.put("dinner", "無");
			
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
		try {
//			Document document = new Document(PageSize.A4.rotate(), 0, 0, 36, 36);
//			TtfClassLoader ttf = new TtfClassLoader();
//			PdfWriter.getInstance(document, stream);
//			document.open();
//
//			Font font = ttf.getFont();
//
//			Paragraph p = new Paragraph("签证申请人名单", font);
//			p.setSpacingBefore(5);
//			p.setSpacingAfter(5);
//			p.setIndentationLeft(50);
//			p.setIndentationRight(50);
//			document.add(p);
			Document document = new Document(PageSize.A4.rotate(), 0, 0, 36, 36);
			TtfClassLoader ttf = new TtfClassLoader();
			PdfWriter.getInstance(document, stream);
			document.open();
			Font font = ttf.getFont();
			font.setFamily("宋体");
			Font font1 = ttf.getFont();
			font1.setFamily("宋体");
			font1.setSize(17);
//			Paragraph p = new Paragraph();
//			Chunk chunk1 = new Chunk("签证申请人名单", font1);
//			Chunk chunk3 = new Chunk("                                                                                            ", font);
//			Chunk chunk2= new Chunk(company.getName(), font1);
//			p.add(chunk1);
//			p.add(chunk3);
//			p.add(chunk2);
//			p.setSpacingBefore(5);
//			p.setSpacingAfter(5);
//			p.setIndentationLeft(20);
//			p.setIndentationRight(20);
//			document.add(p);
			 PdfPTable table1 = new PdfPTable(2); //表格两列
			 table1.setWidthPercentage(95);
			table1.setHorizontalAlignment(Element.ALIGN_CENTER); //垂直居中
			table1.setTotalWidth(PageSize.A4.rotate().getWidth());
			float[] wid1 ={0.5f,0.5f}; //两列宽度的比例
			table1.setWidths(wid1); 
			PdfPCell cell11 = new PdfPCell(); 
			PdfPCell cell12 = new PdfPCell(); 
			Paragraph paragraph = new Paragraph("签证申请人名单",font1);
			Paragraph paragraph2 = new Paragraph(company.getName(), font1);
			paragraph2.setAlignment(Element.ALIGN_RIGHT);
			paragraph.setAlignment(Element.ALIGN_LEFT);
			cell11.addElement(paragraph);
			cell11.setBorder(0);
			cell12.addElement(paragraph2);
			cell12.setBorder(0);
			 table1.addCell(cell11);
			 table1.addCell(cell12);
			 table1.getDefaultCell().setBorderWidth(0);
			 document.add(table1);

			float[] columns = { 2, 3, 4, 2, 3, 3, 3, 3, 3, 2, 3, 4, 3, 2, 4, };
			PdfPTable table = new PdfPTable(columns);
			table.setWidthPercentage(95);
			table.setTotalWidth(PageSize.A4.rotate().getWidth());

			//设置表头
			String titles[] = { "编号", "姓名(中文)", "姓名(英文)", "性别", "出生日期", "护照发行地", "职业", "居住地点", "出境记录", "婚姻", "身份确认",
					"经济能力确认", "内容", "备注", "旅行社意见", };
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
				String careerstatus = "";
				if (!Util.isEmpty(record.get("careerstatus"))) {
					for (JobStatusEnum jobstatusenum : JobStatusEnum.values()) {
						if (record.getInt("careerstatus") == jobstatusenum.intKey()) {
							careerstatus = jobstatusenum.value();
						}
					}
				}
				String marryStatus = "";
				if (!Util.isEmpty(record.get("marrystatus"))) {
					for (MarryStatusEnum marrystatusenum : MarryStatusEnum.values()) {
						if (marrystatusenum.intKey() == record.getInt("marrystatus")) {
							marryStatus = marrystatusenum.value();
						}
					}
				}
//				String[] datas = {
//						"1-" + count,
//						(!Util.isEmpty(record.get("firstname")) ? record.getString("firstname") : "")
//								+ (!Util.isEmpty(record.get("lastname")) ? record.getString("lastname") : ""),
//						(!Util.isEmpty(record.get("firstnameen")) ? record.getString("firstnameen") : "")
//								+ (!Util.isEmpty(record.get("lastnameen")) ? record.getString("lastnameen") : ""),
//						(!Util.isEmpty(record.get("sex")) ? record.getString("sex") : ""),
//						birthdaystr,
//						(!Util.isEmpty(record.get("issuedorganization")) ? record.getString("issuedorganization") : ""),
//						careerstatus,
//						(!Util.isEmpty(record.get("province")) ? record.getString("province") : "")
//								+ (!Util.isEmpty(record.get("city")) ? record.getString("city") : "")
//								+ (!Util.isEmpty(record.get("detailedaddress")) ? record.getString("detailedaddress")
//										: ""), "无", marryStatus, "身份证\n户口本",
//						(!Util.isEmpty(record.get("wealthtype")) ? record.getString("wealthtype") : ""),
//						(!Util.isEmpty(record.get("wealthcontent")) ? record.getString("wealthcontent") : ""),
//						(!Util.isEmpty(record.get("relationremark")) ? record.getString("relationremark") : ""),
//						(!Util.isEmpty(record.get("traveladvice")) ? record.getString("traveladvice") : "") };
//				for (String data : datas) {
//					PdfPCell cell = new PdfPCell(new Paragraph(data, font));
//					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					table.addCell(cell);
//				}
				List<TApplicantWealthJpEntity> wealthjpinfo = new ArrayList<TApplicantWealthJpEntity>();
				if(!Util.isEmpty(record.get("wealthjpinfo"))) {
				 wealthjpinfo = (List<TApplicantWealthJpEntity>) record
							.get("wealthjpinfo");
				}
				boolean flag = false;
				if (wealthjpinfo.size() > 0) {
					flag = true;
				}
				//
				PdfPCell cell;
				//序号
				cell = new PdfPCell(new Paragraph("1-" + count, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}
				table.addCell(cell);
				//中文姓名
				cell = new PdfPCell(new Paragraph(
						(!Util.isEmpty(record.get("firstname")) ? record.getString("firstname") : "")
								+ (!Util.isEmpty(record.get("lastname")) ? record.getString("lastname") : ""), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}
				table.addCell(cell);
				//英文姓名
				cell = new PdfPCell(
						new Paragraph((!Util.isEmpty(record.get("firstnameen")) ? record.getString("firstnameen") : "")
								+ (!Util.isEmpty(record.get("lastnameen")) ? record.getString("lastnameen") : ""), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}
				table.addCell(cell);
				//性别
				cell = new PdfPCell(new Paragraph((!Util.isEmpty(record.get("sex")) ? record.getString("sex") : ""),
						font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}
				table.addCell(cell);
				//护照签发地
				cell = new PdfPCell(new Paragraph(
						(!Util.isEmpty(record.get("issuedplace")) ? record.getString("issuedplace") : ""), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}
				table.addCell(cell);
				//居住地
				cell = new PdfPCell(new Paragraph((!Util.isEmpty(record.get("province")) ? record.getString("province")
						: "")
						+ (!Util.isEmpty(record.get("city")) ? record.getString("city") : "")
						+ (!Util.isEmpty(record.get("detailedaddress")) ? record.getString("detailedaddress") : ""),
						font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}
				table.addCell(cell);
				//出生日期
				cell = new PdfPCell(new Paragraph(birthdaystr, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}
				table.addCell(cell);
				//职业
				cell = new PdfPCell(new Paragraph(careerstatus, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}
				table.addCell(cell);
				//出境记录
				cell = new PdfPCell(new Paragraph("无", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}
				table.addCell(cell);
				//婚姻
				cell = new PdfPCell(new Paragraph(marryStatus, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}
				table.addCell(cell);
				//身份确认
				cell = new PdfPCell(new Paragraph("身份证\n户口本", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}
				table.addCell(cell);
				//如果财产信息不为空
				if (flag) {
					TApplicantWealthJpEntity tApplicantWealthJpEntity = wealthjpinfo.get(0);
					cell = new PdfPCell(new Paragraph(tApplicantWealthJpEntity.getType(), font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
					String detail = tApplicantWealthJpEntity.getDetails();
					if ("银行存款".equals(tApplicantWealthJpEntity.getType())) {
						detail += "万";
					} else if ("理财".equals(tApplicantWealthJpEntity.getType())) {
						detail += "万";
					} else if ("房产".equals(tApplicantWealthJpEntity.getType())) {
						detail += "平米";
					}
					cell = new PdfPCell(new Paragraph(detail, font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(new Paragraph("", font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph("", font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
				}
				//备注
				cell = new PdfPCell(new Paragraph(
						(!Util.isEmpty(record.get("relationremark")) ? record.getString("relationremark") : ""), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}
				table.addCell(cell);
				//旅行社意见
				cell = new PdfPCell(new Paragraph(
						(!Util.isEmpty(record.get("traveladvice")) ? record.getString("traveladvice") : ""), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (flag) {
					cell.setRowspan(wealthjpinfo.size());
				}
				table.addCell(cell);
				//其他财产
				if (wealthjpinfo.size() > 1) {
					for (int i = 1; i < wealthjpinfo.size(); i++) {
						TApplicantWealthJpEntity tApplicantWealthJpEntity = wealthjpinfo.get(i);
						cell = new PdfPCell(new Paragraph(tApplicantWealthJpEntity.getType(), font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell);
						String detail = tApplicantWealthJpEntity.getDetails();
						if ("银行存款".equals(tApplicantWealthJpEntity.getType())) {
							detail += "万";
						} else if ("理财".equals(tApplicantWealthJpEntity.getType())) {
							detail += "万";
						} else if ("房产".equals(tApplicantWealthJpEntity.getType())) {
							detail += "平米";
						}
						cell = new PdfPCell(new Paragraph(detail, font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell);
					}
				}
			}
			document.add(table);
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
			DateFormat df = new SimpleDateFormat("dd MMMMM, yyyy", Locale.ENGLISH);
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
			if (!Util.isEmpty(ordertripjp)) {
				if (ordertripjp.getTripType().equals(1)) {
					//入境航班
					if (!Util.isEmpty(ordertripjp.getGoFlightNum())) {
						TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,Cnd.where("flightnum", "=", ordertripjp.getGoFlightNum()));
						//起飞机场
						map.put("takeOffName", goflight.getTakeOffName());
						//起飞时间 
						map.put("file_7", goflight.getTakeOffTime());
						//降落时间
						map.put("file_9", goflight.getLandingTime());
					}
						//起飞航班号
					map.put("file_3", ordertripjp.getGoFlightNum().replace("*",""));
					
					//出境航班
					if (!Util.isEmpty(ordertripjp.getReturnFlightNum())) {
						TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,Cnd.where("flightnum", "=", ordertripjp.getReturnFlightNum()));
						//返回机场
						map.put("takeOffName1", goflight.getTakeOffName());
						//起飞时间 
						map.put("file_8", goflight.getTakeOffTime());
						//降落时间
						map.put("file_10", goflight.getLandingTime());
						map.put("LandingName", goflight.getLandingName());
					}
						//航班号
					map.put("file_4", ordertripjp.getReturnFlightNum().replace("*", ""));
						//出发航班日期
					map.put("file_5", df.format(ordertripjp.getGoDate()));
						//返回航班日期
					map.put("file_6", df.format(ordertripjp.getReturnDate()));
				} else if (ordertripjp.getTripType().equals(2)) {
					//多程处理
					if (!Util.isEmpty(mutiltrip)) {
						//多程第一程为出发日期
						TOrderTripMultiJpEntity entrytrip = mutiltrip.get(0);
						if (!Util.isEmpty(entrytrip.getDepartureDate())) {
							map.put("file_5", df.format(entrytrip.getDepartureDate()));
						}
						//入境航班
						if (!Util.isEmpty(entrytrip.getFlightNum())) {
//							TFlightEntity goflight = flightViewService.fetch(entrytrip.getFlightNum().longValue());
							TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,Cnd.where("flightnum", "=",entrytrip.getFlightNum()));
							//起飞机场
							map.put("file_1", goflight.getTakeOffName());
							//起飞时间 
							map.put("file_7", goflight.getTakeOffTime());
							//降落时间
							map.put("file_9", goflight.getLandingTime());
						}
						map.put("file_3", entrytrip.getFlightNum());
						//最后一程作为返回日期
						TOrderTripMultiJpEntity returntrip = mutiltrip.get(mutiltrip.size() - 1);
						if (!Util.isEmpty(returntrip.getDepartureDate())) {
							map.put("file_6", df.format(returntrip.getDepartureDate()));
						}
						if (!Util.isEmpty(returntrip.getFlightNum())) {
							//出境航班
							TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,Cnd.where("flightnum", "=", returntrip.getFlightNum()));
							//返回机场
							map.put("file_2", goflight.getTakeOffName());
							//起飞时间 
							map.put("file_8", goflight.getTakeOffTime());
							//降落时间
							map.put("file_10", goflight.getLandingTime());
							map.put("file_22", goflight.getLandingName());
						}
						map.put("file_4", returntrip.getFlightNum());
					}
				}
			}
			
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
			//副标题1
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
				String subtitle = "(平成" + godatestr + "～" + returndatestr.substring(3, 8) + ")";
				Paragraph p = new Paragraph(subtitle, font);
//				p.setSpacingBefore(5);
				p.setIndentationRight(13);
				p.setSpacingAfter(5);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				//添加副标题1
				document.add(p);
			}
			String applyname = "";
			int dengsize = 0;
			int totalsize = 0;
			if (!Util.isEmpty(applyinfo)) {
				Record record = applyinfo.get(0);
				applyname += record.getString("firstname");
				applyname += record.getString("lastname");
				dengsize = applyinfo.size() - 1;
				totalsize = applyinfo.size();
			}
			//副标题2
			{
				String text = "（旅行参加者 " + applyname + " 他" + dengsize + "名、計" + totalsize + "名）";
				Paragraph p = new Paragraph(text, font);
				p.setSpacingAfter(15);
				p.setIndentationRight(20);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			//表格
			float[] columns = { 1, 3, 5, 5, };
			PdfPTable table = new PdfPTable(columns);
			table.setWidthPercentage(95);
			table.setTotalWidth(PageSize.A4.getWidth());

			//设置表头
			String titles[] = { "日数", "年月日", "行動予定", "宿泊先", };
			font.setSize(12);
			for (String title : titles) {
				PdfPCell cell = new PdfPCell(new Paragraph(title, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			} 
			font.setSize(10);
			//格式化为日本的日期
//			String pointpattren = "yy.MM.dd";
			String pointpattren = "平成yy年MM月dd日";
			int count = 0;
			for (TOrderTravelplanJpEntity ordertravelplan : ordertravelplans) {
				count++;
				//行程安排
				String scenic = "";
				if (count == 1) {
					if (ordertripjp.getTripType().equals(1)) {
						TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,
								Cnd.where("flightnum", "=", ordertripjp.getGoFlightNum()));
						scenic = goflight.getFlightnum() + "：" + goflight.getTakeOffName() + "->"
								+ goflight.getLandingName();
					} else if (ordertripjp.getTripType().equals(2)) {
						//多程出发航班
						if (!Util.isEmpty(mutiltrip)) {
							//多程第一程为出发日期
							TOrderTripMultiJpEntity entrytrip = mutiltrip.get(0);
							TFlightEntity goflight = dbDao.fetch(TFlightEntity.class,
									Cnd.where("flightnum", "=", entrytrip.getFlightNum()));
							scenic = goflight.getFlightnum() + "：" + goflight.getTakeOffName() + "->"
									+ goflight.getLandingName();
						}
					}
				} else if (count == ordertravelplans.size()) {
					if (ordertripjp.getTripType().equals(1)) {
						TFlightEntity returnflight = dbDao.fetch(TFlightEntity.class,
								Cnd.where("flightnum", "=", ordertripjp.getReturnFlightNum()));
						scenic = returnflight.getFlightnum() + "：" + returnflight.getTakeOffName() + "->"
								+ returnflight.getLandingName();
					} else if (ordertripjp.getTripType().equals(2)) {
						//多程出发航班
						if (!Util.isEmpty(mutiltrip)) {
							//最后一程作为返回日期
							TOrderTripMultiJpEntity returntrip = mutiltrip.get(mutiltrip.size() - 1);
							TFlightEntity returnflight = dbDao.fetch(TFlightEntity.class,
									Cnd.where("flightnum", "=", returntrip.getFlightNum()));
							scenic = returnflight.getFlightnum() + "：" + returnflight.getTakeOffName() + "->"
									+ returnflight.getLandingName();
						}
					}
				} else {
					scenic = ordertravelplan.getScenic();
				}
				//酒店信息
				String hotel = "";
				if (!Util.isEmpty(ordertravelplan.getHotel())) {
					THotelEntity hotelentity = hotelViewService.fetch(ordertravelplan.getHotel());
					hotel = hotelentity.getNamejp() + "\n" + hotelentity.getAddressjp() + "\n"
							+ hotelentity.getMobile();
				} else {
					hotel = " ";
				}
				String[] datas = { String.valueOf(count), format(ordertravelplan.getOutDate(), pointpattren), scenic,
						hotel };
				for (String title : datas) {
					PdfPCell cell = new PdfPCell(new Paragraph(title, font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
				}
			}
			document.add(table);
			//底部
			//地接社名称
			String dijie = "";
			//地接社地址
			String dijieAddr = "";
			//地接社联系人
			String dijielinkman = "";
			//地接社电话
			String dijiephone = "";
			//公章地址
			String sealUrl = "";
			if (!Util.isEmpty(orderjp.getGroundconnectid())) {
				TCompanyEntity dijiecompany = dbDao.fetch(TCompanyEntity.class, orderjp.getGroundconnectid()
						.longValue());
				dijie = dijiecompany.getName();
				dijieAddr = dijiecompany.getAddress();
				dijielinkman = dijiecompany.getLinkman();
				dijiephone = dijiecompany.getMobile();
				sealUrl = dijiecompany.getSeal();
			}
			{
				Paragraph p = new Paragraph("保証会社：" + dijie, font);
				p.setSpacingBefore(5);
				p.setIndentationRight(100);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			{
				Paragraph p = new Paragraph("住  所：" + dijieAddr, font);
				p.setSpacingBefore(5);
				p.setIndentationRight(100);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			{
				Paragraph p = new Paragraph("担当者：" + dijielinkman, font);
				p.setSpacingBefore(5);
				p.setIndentationRight(100);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			{
				Paragraph p = new Paragraph("電  話：" + dijiephone, font);
				p.setSpacingBefore(5);
				p.setIndentationRight(100);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			if (!Util.isEmpty(sealUrl)) {
				document.add(getSeal1(sealUrl, 0));
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
	 * excel模板导出
	 */
	public ByteArrayOutputStream excelExport(Map<String, Object> tempdata) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		TemplateUtil templateUtil = new TemplateUtil();
		DateFormat exceldateformat = new SimpleDateFormat("yyyy/M/d");
		//申请人信息
		List<Record> applyinfo = (List<Record>) tempdata.get("applyinfo");
		//导出人员名单的电子表格
		List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
		entity.add(new ExcelExportEntity("氏名", "name"));
		entity.add(new ExcelExportEntity("ピンイン", "name_en"));
		entity.add(new ExcelExportEntity("性別", "gender"));
		entity.add(new ExcelExportEntity("居住地域", "city"));
		entity.add(new ExcelExportEntity("生年月日", "birthday"));
		entity.add(new ExcelExportEntity("旅券番号", "passport"));
		entity.add(new ExcelExportEntity("備考", "remark"));
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (Record record : applyinfo) {
			String birthdaystr = "";
			if (!Util.isEmpty(record.get("birthday"))) {
				birthdaystr = exceldateformat.format((Date) record.get("birthday"));
			}
			Map<String, String> map = new HashMap<String, String>();
			//氏名
			map.put("name",
					(!Util.isEmpty(record.get("firstname")) ? record.getString("firstname") : "")
							+ (!Util.isEmpty(record.get("lastname")) ? record.getString("lastname") : ""));
			//ピンイン
			map.put("name_en", (!Util.isEmpty(record.get("firstnameen")) ? record.getString("firstnameen") : " ")
					+ (!Util.isEmpty(record.get("lastnameen")) ? record.getString("lastnameen") : " "));
			//性别
			String sex = "";
			if (!Util.isEmpty(record.get("sex"))) {
				if ("男".equals(record.getString("sex"))) {
					sex = "1";
				} else {
					sex = "2";
				}
			}
			map.put("gender", sex);
			//居住地域
			map.put("city", !Util.isEmpty(record.get("city")) ? record.getString("city") : "");
			//生年月日
			map.put("birthday", birthdaystr);
			//旅券番号
			map.put("passport", !Util.isEmpty(record.get("passportno")) ? record.getString("passportno") : " ");
			//備考
			map.put("remark", " ");
			list.add(map);
		}
		stream = templateUtil.createExcel(entity, list);
		return stream;
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
				Paragraph p = new Paragraph("査 証 申 請 人 名 簿", font);
				p.setSpacingBefore(5);
				p.setSpacingAfter(5);
				p.setAlignment(Paragraph.ALIGN_CENTER);
				document.add(p);
			}
			font.setSize(10);
			//日期格式化
			String pattern = "yy年MM月dd日";
			SimpleDateFormat tableformat = new SimpleDateFormat("yyyy/MM/dd");
			//副标题1
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
				String subtitle = "（平成" + godatestr + "から平成" + returndatestr + "）";
				Paragraph p = new Paragraph(subtitle, font);
				p.setSpacingBefore(5);
				p.setIndentationRight(20);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				//添加副标题1
				document.add(p);
			}
			String applyname = "";
			int dengsize = 0;
			int totalsize = 0;
			if (!Util.isEmpty(applyinfo)) {
				Record record = applyinfo.get(0);
				applyname += record.getString("firstname");
				applyname += record.getString("lastname");
				dengsize = applyinfo.size() - 1;
				totalsize = applyinfo.size();
			}
			//副标题2
			{
				String text = "（旅行参加者 " + applyname + " 他" + dengsize + "名、計" + totalsize + "名）";
				Paragraph p = new Paragraph(text, font);
				p.setSpacingAfter(15);
				p.setIndentationRight(20);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			//这里是表格********************************
			float[] columns = { 1, 3, 3, 1, 2.5f, 3, 2, 3 };
			PdfPTable table = new PdfPTable(columns);
			table.setWidthPercentage(95);
			table.setTotalWidth(PageSize.A4.getWidth());

			//设置表头
			String titles[] = { "", "氏名（中文）", "（英文）", "性別", "生年月日", "职业", "発行地", "旅券番号", };
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
				if (!Util.isEmpty(record.get("careerstatus"))) {
					for (JobStatusEnum jobstatusenum : JobStatusEnum.values()) {
						if (record.getInt("careerstatus") == jobstatusenum.intKey()) {
							careerstatus = jobstatusenum.value();
						}
					}
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
						careerstatus, record.getString("province"), record.getString("passportno") };
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
			//地接社名称
			String dijie = "";
			//地接社地址
			String dijieAddr = "";
			//地接社联系人
			String dijielinkman = "";
			//地接社电话
			String dijiephone = "";
			//公章地址
			String sealUrl = "";
			if (!Util.isEmpty(orderjp.getGroundconnectid())) {
				TCompanyEntity dijiecompany = dbDao.fetch(TCompanyEntity.class, orderjp.getGroundconnectid()
						.longValue());
				dijie = dijiecompany.getName();
				dijieAddr = dijiecompany.getAddress();
				dijielinkman = dijiecompany.getLinkman();
				dijiephone = dijiecompany.getMobile();
				sealUrl = dijiecompany.getSeal();
			}
			{
				Paragraph p = new Paragraph("保証会社：" + dijie, font);
				p.setSpacingBefore(5);
				p.setIndentationRight(100);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			{
				Paragraph p = new Paragraph("住  所：" + dijieAddr, font);
				p.setSpacingBefore(5);
				p.setIndentationRight(100);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			{
				Paragraph p = new Paragraph("担当者：" + dijielinkman, font);
				p.setSpacingBefore(5);
				p.setIndentationRight(100);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			{
				Paragraph p = new Paragraph("電  話：" + dijiephone, font);
				p.setSpacingBefore(5);
				p.setIndentationRight(100);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			if (!Util.isEmpty(sealUrl)) {
				document.add(getSeal1(sealUrl, 0));
			}
			document.close();
			IOUtils.closeQuietly(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stream;
	}

	/***
	 * 获取申请人数据
	 * @param address
	 * @param n
	 * @return
	 * @throws IOException
	 * @throws BadElementException
	 */
	
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

				img.setAbsolutePosition(350, 530);
			} else {

				img.setAbsolutePosition(350, 510 - 34 * (n - 1));
			}
			img.setAlignment(Paragraph.ALIGN_RIGHT);
			return img;
		}
		return null;
	}
}
