/**
 * DownLoadVisaFileService.java
 * com.juyo.visa.admin.visajp.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
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
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TFlightEntity;
import com.juyo.visa.entities.THotelEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderTravelplanJpEntity;
import com.juyo.visa.entities.TOrderTripJpEntity;
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
		//公司信息
		TCompanyEntity company = new TCompanyEntity();
		company = dbDao.fetch(TCompanyEntity.class, orderinfo.getComId().longValue());
		//申请人信息
		String applysqlstr = sqlManager.get("get_applyinfo_from_filedown_by_orderid_jp");
		Sql applysql = Sqls.create(applysqlstr);
		Cnd cnd = Cnd.NEW();
		cnd.and("taoj.orderId", "=", orderjp.getId());
		List<Record> applyinfo = dbDao.query(applysql, cnd, null);
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
		//准备合并的PDF文件
		List<ByteArrayOutputStream> pdffiles = Lists.newArrayList();
		//准备封皮信息
		ByteArrayOutputStream note = note(tempdata);
		pdffiles.add(note);
		for (Record record : applyinfo) {
			ByteArrayOutputStream apply = applyinfo(record, tempdata);
			pdffiles.add(apply);
		}
		//excel名称日期格式化
		DateFormat excelnameformat = new SimpleDateFormat("yy.MM.dd");
		//excel名称
		String excelname = "";
		if (!Util.isEmpty(ordertripjp.getGoDate())) {
			String godatestr = excelnameformat.format(ordertripjp.getGoDate());
			excelname += godatestr;
		}
		excelname += "-";
		if (!Util.isEmpty(ordertripjp.getReturnDate())) {
			String returndatestr = excelnameformat.format(ordertripjp.getReturnDate());
			excelname += returndatestr;
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
		return stream;
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

		String visatypestr = "";
		Integer visaType = orderjp.getVisaType();
		for (MainSaleVisaTypeEnum visatypeEnum : MainSaleVisaTypeEnum.values()) {
			if (visatypeEnum.intKey() == visaType) {
				visatypestr = visatypeEnum.value();
			}
		}
		//准备PDF模板数据
		Map<String, String> map = new HashMap<String, String>();
		StringBuffer content = new StringBuffer();
		content.append("　　" + company.getName()).append("根据与津東通商株式会社的合同约定，组织").append(applyinfo.size())
				.append("人访日个人旅游，请协助办理").append(visatypestr).append("往返赴日签证");
		map.put("content", content.toString());
		map.put("company", company.getName());
		map.put("entryDate", dateFormat.format(ordertripjp.getGoDate()));
		//入境航班
		TFlightEntity goflight = flightViewService.fetch(ordertripjp.getGoFlightNum().longValue());
		map.put("entryFlight", goflight.getFlightnum());
		map.put("departDate", dateFormat.format(ordertripjp.getReturnDate()));
		//天数
		map.put("stay", String.valueOf(DateUtil.daysBetween(ordertripjp.getGoDate(), ordertripjp.getReturnDate())));
		//出境航班
		TFlightEntity returnflight = flightViewService.fetch(ordertripjp.getReturnFlightNum().longValue());
		map.put("departFlight", returnflight.getFlightnum());
		map.put("linkman", company.getLinkman());
		map.put("telephone", company.getMobile());
		map.put("phone", company.getMobile());
		map.put("createDate", dateFormat.format(new Date()));

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
		List<TOrderTravelplanJpEntity> ordertravelplan = (List<TOrderTravelplanJpEntity>) tempdata
				.get("ordertravelplan");
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("firstName", record.getString("firstname"));
			map.put("firstNameEn", record.getString("firstnameen"));
			map.put("lastName", record.getString("lastname"));
			map.put("lastNameEn", record.getString("lastnameen"));
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

			//国籍
			map.put("country", "中国");
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
			//赴日目的
			map.put("tripPurpose", ordertripjp.getTripPurpose());
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
			TCityEntity goarrivecirtentity = cityViewService.fetch(ordertripjp.getGoArrivedCity());
			map.put("goArrivedCity", goarrivecirtentity.getCity());
			//航空公司.0
			TFlightEntity goflightentity = flightViewService.fetch(ordertripjp.getGoFlightNum());
			map.put("goFlightNum", goflightentity.getFlightnum());
			//酒店信息
			if (!Util.isEmpty(ordertravelplan)) {
				TOrderTravelplanJpEntity travelplanEntity = ordertravelplan.get(0);
				if (!Util.isEmpty(travelplanEntity.getHotel())) {
					THotelEntity hotelinfo = hotelViewService.fetch(travelplanEntity.getHotel());
					map.put("hotelname", hotelinfo.getName());
					map.put("hotelphone", hotelinfo.getMobile());
					map.put("hoteladdress", hotelinfo.getAddress());
				}
			}

			//家庭住址
			map.put("homeaddress",
					(!Util.isEmpty(record.get("province")) ? record.getString("province") : " ")
							+ (!Util.isEmpty(record.get("city")) ? record.getString("city") : " ")
							+ (!Util.isEmpty(record.get("detailedaddress")) ? record.getString("detailedaddress") : " "));
			//家庭电话
			map.put("homephone", record.getString("telephone"));
			//电子邮箱
			map.put("homeEmail", record.getString("email"));
			//工作单位
			map.put("workname", record.getString("workname"));
			map.put("workphone", record.getString("workphone"));
			map.put("workaddress", record.getString("workaddress"));
			map.put("occupation", record.getString("occupation"));
			map.put("danbaoname", "参照身元保证书");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stream;
	}

	/**
	 * 酒店信息
	 */
	public ByteArrayOutputStream hotelInfo(Map<String, Object> tempdata) {
		DateFormat hoteldateformat = new SimpleDateFormat("yyyy/MM/dd");
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
			//行程安排
			List<TOrderTravelplanJpEntity> ordertravelplan = (List<TOrderTravelplanJpEntity>) tempdata
					.get("ordertravelplan");

			Map<String, String> map = new HashMap<String, String>();
			map.put("comNameBig", company.getName());
			map.put("comNameSmall", company.getName());
			map.put("address", company.getAddress());
			map.put("telephone", company.getMobile());
			map.put("linkman", company.getLinkman());
			TCityEntity cityentity = cityViewService.fetch(ordertripjp.getGoArrivedCity());
			map.put("city", cityentity.getCity());
			if (!Util.isEmpty(ordertripjp.getGoDate())) {
				map.put("checkInDate", hoteldateformat.format(ordertripjp.getGoDate()));
			}
			if (!Util.isEmpty(ordertripjp.getReturnDate())) {
				map.put("checkOutDate", hoteldateformat.format(ordertripjp.getReturnDate()));
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
		try {
			Document document = new Document(PageSize.A4.rotate(), 0, 0, 36, 36);
			TtfClassLoader ttf = new TtfClassLoader();
			PdfWriter.getInstance(document, stream);
			document.open();

			Font font = ttf.getFont();

			Paragraph p = new Paragraph("签证申请人名单", font);
			p.setSpacingBefore(5);
			p.setSpacingAfter(5);
			p.setIndentationLeft(50);
			p.setIndentationRight(50);
			document.add(p);

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

				String[] datas = { "1-" + count, record.getString("firstname") + record.getString("lastname"),
						record.getString("firstnameen") + record.getString("lastnameen"), record.getString("sex"),
						birthdaystr, record.getString("issuedorganization"), record.getString("occupation"),
						record.getString("province") + record.getString("city") + record.getString("detailedaddress"),
						"无", "", "", "", "", "", "推介" };
				for (String data : datas) {
					PdfPCell cell = new PdfPCell(new Paragraph(data, font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
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
				Paragraph p = new Paragraph("滞　在　予　定　表", font);
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
			if (!Util.isEmpty(ordertripjp.getGoDate())) {
				godatestr = format(ordertripjp.getGoDate(), pattern);
			}
			String returndatestr = "";
			if (!Util.isEmpty(ordertripjp.getReturnDate())) {
				returndatestr = format(ordertripjp.getReturnDate(), pattern);
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
			//表格
			float[] columns = { 1, 3, 5, 5, };
			PdfPTable table = new PdfPTable(columns);
			table.setWidthPercentage(95);
			table.setTotalWidth(PageSize.A4.getWidth());

			//设置表头
			String titles[] = { "日数", "年月日", "行　動　予　定", "宿　泊　先", };
			font.setSize(12);
			for (String title : titles) {
				PdfPCell cell = new PdfPCell(new Paragraph(title, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}
			font.setSize(10);
			//格式化为日本的日期
			String pointpattren = "yy.MM.dd";
			int count = 0;
			for (TOrderTravelplanJpEntity ordertravelplan : ordertravelplans) {
				count++;
				//行程安排
				String scenic = "";
				if (count == 1) {
					TFlightEntity goflight = flightViewService.fetch(ordertripjp.getGoFlightNum());
					scenic = goflight.getFlightnum() + "：" + goflight.getTakeOffName() + "->"
							+ goflight.getLandingName();
				} else if (count == ordertravelplans.size()) {
					TFlightEntity returnflight = flightViewService.fetch(ordertripjp.getReturnFlightNum());
					scenic = returnflight.getFlightnum() + "：" + returnflight.getTakeOffName() + "->"
							+ returnflight.getLandingName();
				} else {
					scenic = ordertravelplan.getScenic();
				}
				//酒店信息
				String hotel = "";
				if (!Util.isEmpty(ordertravelplan.getHotel())) {
					THotelEntity hotelentity = hotelViewService.fetch(ordertravelplan.getHotel());
					hotel = hotelentity.getName();
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
			{
				Paragraph p = new Paragraph("保証会社：株事会金通商事", font);
				p.setSpacingBefore(5);
				p.setIndentationRight(100);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			{
				Paragraph p = new Paragraph("住  所：東京都千代田区霞が関３-３-３ 全日通霞ヶ関ビル3F", font);
				p.setSpacingBefore(5);
				p.setIndentationRight(100);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			{
				Paragraph p = new Paragraph("担当者：", font);
				p.setSpacingBefore(5);
				p.setIndentationRight(100);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			{
				Paragraph p = new Paragraph("電  話：", font);
				p.setSpacingBefore(5);
				p.setIndentationRight(100);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
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
		String sYear = bodydateformat.format(new DateTime(ordertripjp.getGoDate()).plusYears(12).toDate());
		int sMonth = DateUtil.getMonth(ordertripjp.getGoDate());
		int sDay = DateUtil.getDay(ordertripjp.getGoDate());
		String eYear = bodydateformat.format(new DateTime(ordertripjp.getReturnDate()).plusYears(12).toDate());
		int eMonth = DateUtil.getMonth(ordertripjp.getReturnDate());
		int eDay = DateUtil.getDay(ordertripjp.getReturnDate());
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
			map.put("gender", !Util.isEmpty(record.get("sex")) ? record.getString("sex") : "");
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
			if (!Util.isEmpty(ordertripjp.getGoDate())) {
				godatestr = format(ordertripjp.getGoDate(), pattern);
			}
			String returndatestr = "";
			if (!Util.isEmpty(ordertripjp.getReturnDate())) {
				returndatestr = format(ordertripjp.getReturnDate(), pattern);
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
						record.getString("occupation"), record.getString("province"), record.getString("passportno") };
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
				Paragraph p = new Paragraph("保証会社：株事会金通商事", font);
				p.setSpacingBefore(5);
				p.setIndentationRight(100);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			{
				Paragraph p = new Paragraph("住  所：東京都千代田区霞が関３-３-３ 全日通霞ヶ関ビル3F", font);
				p.setSpacingBefore(5);
				p.setIndentationRight(100);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			{
				Paragraph p = new Paragraph("担当者：", font);
				p.setSpacingBefore(5);
				p.setIndentationRight(100);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			{
				Paragraph p = new Paragraph("電  話：", font);
				p.setSpacingBefore(5);
				p.setIndentationRight(100);
				p.setAlignment(Paragraph.ALIGN_RIGHT);
				document.add(p);
			}
			document.close();
			IOUtils.closeQuietly(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stream;
	}
}
