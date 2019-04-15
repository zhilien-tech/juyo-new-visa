/**
 * SimpleVisaService.java
 * com.juyo.visa.admin.simple.service
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.simple.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.upload.TempFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import com.juyo.visa.admin.changePrincipal.service.ChangePrincipalViewService;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.order.form.VisaEditDataForm;
import com.juyo.visa.admin.order.service.OrderJpViewService;
import com.juyo.visa.admin.simple.entity.StatisticsEntity;
import com.juyo.visa.admin.simple.entity.WealthEntity;
import com.juyo.visa.admin.simple.form.AddOrderForm;
import com.juyo.visa.admin.simple.form.AddSimpleHotelForm;
import com.juyo.visa.admin.simple.form.BasicinfoForm;
import com.juyo.visa.admin.simple.form.GenerrateTravelForm;
import com.juyo.visa.admin.simple.form.ListDataForm;
import com.juyo.visa.admin.user.form.ApplicantUser;
import com.juyo.visa.admin.user.service.UserViewService;
import com.juyo.visa.admin.visajp.form.FlightSelectParam;
import com.juyo.visa.admin.visajp.service.TripAirlineService;
import com.juyo.visa.admin.visajp.service.VisaJapanService;
import com.juyo.visa.admin.weixinToken.service.WeXinTokenViewService;
import com.juyo.visa.common.base.QrCodeService;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.ApplicantInfoTypeEnum;
import com.juyo.visa.common.enums.ApplicantJpWealthEnum;
import com.juyo.visa.common.enums.BoyOrGirlEnum;
import com.juyo.visa.common.enums.CollarAreaEnum;
import com.juyo.visa.common.enums.CompanyTypeEnum;
import com.juyo.visa.common.enums.CustomerTypeEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderProcessTypeEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.JobStatusEnum;
import com.juyo.visa.common.enums.JobStatusFreeEnum;
import com.juyo.visa.common.enums.JobStatusPreschoolEnum;
import com.juyo.visa.common.enums.JobStatusRetirementEnum;
import com.juyo.visa.common.enums.JobStatusStudentEnum;
import com.juyo.visa.common.enums.JobStatusWorkingEnum;
import com.juyo.visa.common.enums.JpOrderSimpleEnum;
import com.juyo.visa.common.enums.MainApplicantRelationEnum;
import com.juyo.visa.common.enums.MainApplicantRemarkEnum;
import com.juyo.visa.common.enums.MainOrViceEnum;
import com.juyo.visa.common.enums.MainSalePayTypeEnum;
import com.juyo.visa.common.enums.MainSaleTripTypeEnum;
import com.juyo.visa.common.enums.MainSaleUrgentEnum;
import com.juyo.visa.common.enums.MainSaleUrgentTimeEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.common.enums.MarryStatusEnum;
import com.juyo.visa.common.enums.PassportTypeEnum;
import com.juyo.visa.common.enums.SimpleVisaTypeEnum;
import com.juyo.visa.common.enums.TrialApplicantStatusEnum;
import com.juyo.visa.common.msgcrypt.AesException;
import com.juyo.visa.common.msgcrypt.WXBizMsgCrypt;
import com.juyo.visa.common.newairline.ResultflyEntity;
import com.juyo.visa.common.ocr.HttpUtils;
import com.juyo.visa.common.util.HttpUtil;
import com.juyo.visa.common.util.MapUtil;
import com.juyo.visa.common.util.SpringContextUtil;
import com.juyo.visa.common.util.TokenUtil;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantFrontPaperworkJpEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TApplicantPassportEntity;
import com.juyo.visa.entities.TApplicantUnqualifiedEntity;
import com.juyo.visa.entities.TApplicantVisaJpEntity;
import com.juyo.visa.entities.TApplicantVisaOtherInfoEntity;
import com.juyo.visa.entities.TApplicantVisaPaperworkJpEntity;
import com.juyo.visa.entities.TApplicantWealthJpEntity;
import com.juyo.visa.entities.TApplicantWorkJpEntity;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TCompanyOfCustomerEntity;
import com.juyo.visa.entities.TCustomerEntity;
import com.juyo.visa.entities.TCustomerVisainfoEntity;
import com.juyo.visa.entities.TFlightEntity;
import com.juyo.visa.entities.THotelEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderLogsEntity;
import com.juyo.visa.entities.TOrderTravelplanJpEntity;
import com.juyo.visa.entities.TOrderTripJpEntity;
import com.juyo.visa.entities.TScenicEntity;
import com.juyo.visa.entities.TUncommoncharacterEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TApplicantForm;
import com.juyo.visa.forms.TApplicantPassportForm;
import com.juyo.visa.websocket.VisaInfoWSHandler;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年1月22日 	 
 */
@IocBean
public class SimpleVisaService extends BaseService<TOrderJpEntity> {

	private static final Log log = Logs.get();
	//基本信息连接websocket的地址
	private static final String SIMPLE_WEBSOCKET_ADDR = "simpleinfowebsocket";

	@Inject
	private QrCodeService qrCodeService;
	@Inject
	private TripAirlineService tripAirlineService;
	@Inject
	private UserViewService userViewService;
	@Inject
	private OrderJpViewService orderJpViewService;
	@Inject
	private VisaJapanService visaJapanService;
	@Inject
	private ChangePrincipalViewService changePrincipalViewService;
	@Inject
	private WeXinTokenViewService weXinTokenViewService;
	@Inject
	private UploadService qiniuUploadService;//文件上传

	private VisaInfoWSHandler visaInfoWSHandler = (VisaInfoWSHandler) SpringContextUtil.getBean("myVisaInfoHander",
			VisaInfoWSHandler.class);

	private static final String VISAINFO_WEBSPCKET_ADDR = "visainfowebsocket";
	private static String WX_B_CODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN"; //不限次数 scene长度为32个字符

	private final static String ENCODINGAESKEY = "jllZTM3ZWEzZGI1NGQ5NGI3MTc4NDNhNzAzODE5NTYt";
	private final static String TOKEN = "ODBiOGIxNDY4NjdlMzc2Yg==";
	private final static String APPID = "jhhMThiZjM1ZGQ2Y";

	private final static String HOST = "https://192.168.2.138:443";

	private Lock lock = new ReentrantLock();

	public Object toList(HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		JSONArray ja = new JSONArray();

		//送签社下拉
		if (loginCompany.getComType().equals(CompanyTypeEnum.SONGQIAN.intKey())
				|| loginCompany.getComType().equals(CompanyTypeEnum.SONGQIANSIMPLE.intKey())
				|| loginCompany.getComType().equals(CompanyTypeEnum.ORDERSIMPLE.intKey())) {
			//如果公司自己有指定番号，说明有送签资质，也需要出现在下拉中
			if (!Util.isEmpty(loginCompany.getCdesignNum())) {
				ja.add(loginCompany);
			}
			List<TCompanyOfCustomerEntity> list = dbDao.query(TCompanyOfCustomerEntity.class,
					Cnd.where("comid", "=", loginCompany.getId()), null);
			for (TCompanyOfCustomerEntity tCompanyOfCustomerEntity : list) {
				JSONObject jo = new JSONObject();
				Integer sendcomid = tCompanyOfCustomerEntity.getSendcomid();

				TCompanyEntity sendCompany = dbDao.fetch(TCompanyEntity.class,
						Cnd.where("id", "=", sendcomid).and("cdesignNum", "!=", ""));

				ja.add(sendCompany);
			}
		}
		result.put("songqianlist", ja);

		//员工下拉
		Integer comid = loginCompany.getId();
		Integer adminId = loginCompany.getAdminId();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer ordertype = loginUser.getOrdertype();
		result.put("ordertype", ordertype);

		Integer userType = loginUser.getUserType();//当前登录用户类型

		//查询拥有某个权限模块的工作人员
		String sqlstr = sqlManager.get("logs_user_select_list");
		Sql sql = Sqls.create(sqlstr);
		Cnd cnd = Cnd.NEW();
		cnd.and("tcf.comid", "=", comid);
		cnd.and("tu.id", "!=", adminId);
		for (JPOrderProcessTypeEnum typeEnum : JPOrderProcessTypeEnum.values()) {
			if (1 == typeEnum.intKey()) {
				cnd.and(" tf.funName", "=", typeEnum.value());
			}
		}
		List<Record> employees = dbDao.query(sql, cnd, null);
		Cnd usercnd = Cnd.NEW();
		usercnd.and("comId", "=", loginCompany.getId());
		if (!loginCompany.getAdminId().equals(loginUser.getId())) {
			usercnd.and("id", "!=", loginCompany.getAdminId());
		}
		if (loginCompany.getComType().equals(CompanyTypeEnum.SONGQIANSIMPLE.intKey())
				|| loginCompany.getComType().equals(CompanyTypeEnum.ORDERSIMPLE.intKey())) {

			List<TUserEntity> companyuser = dbDao.query(TUserEntity.class, usercnd, null);
			List<Record> companyusers = Lists.newArrayList();
			for (TUserEntity tUserEntity : companyuser) {
				Record record = new Record();
				record.put("userid", tUserEntity.getId());
				record.put("username", tUserEntity.getName());
				companyusers.add(record);
			}
			employees = companyusers;
		}

		result.put("employees", employees);
		result.put("mainsalevisatypeenum", EnumUtil.enum2(SimpleVisaTypeEnum.class));
		result.put("orderstatus", EnumUtil.enum2(JpOrderSimpleEnum.class));
		String localAddr = request.getServerName();
		int localPort = request.getServerPort();
		result.put("localAddr", localAddr);
		result.put("localPort", localPort);
		result.put("websocketaddr", VISAINFO_WEBSPCKET_ADDR);
		return result;
	}

	/**
	 *列表页数据展示
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object ListData(ListDataForm form, HttpServletRequest request) {

		long startTime = System.currentTimeMillis();
		HttpSession session = request.getSession();
		//获取当前公司
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//获取当前用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		loginUser.getId();
		form.setUserid(loginUser.getId());
		form.setCompanyid(loginCompany.getId());
		form.setAdminId(loginCompany.getAdminId());
		Map<String, Object> result = Maps.newHashMap();

		result.put("ordertype", loginUser.getOrdertype());
		Sql sql = form.sql(sqlManager);

		Integer pageNumber = form.getPageNumber();
		Integer pageSize = form.getPageSize();

		Pager pager = new OffsetPager((pageNumber - 1) * pageSize, pageSize);

		Sql paperSql = getPaperSql(form);

		pager.setRecordCount((int) Daos.queryCount(nutDao, paperSql.toString()));

		long middleTime = System.currentTimeMillis();
		System.out.println("paper所用时间:" + (middleTime - startTime) + "ms");

		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		long middleTime2 = System.currentTimeMillis();
		System.out.println("查询所用时间:" + (middleTime2 - middleTime) + "ms");

		@SuppressWarnings("unchecked")
		//主sql数据
		List<Record> totallist = (List<Record>) sql.getResult();
		int orderscount = totallist.size();
		int peopletotal = 0;
		int zhaobaoorder = 0;
		int zhaobaopeople = 0;
		for (Record record : totallist) {
			//收费单子，人数
			if (Util.eq(1, record.get("zhaobaoupdate")) && Util.eq(0, record.get("isDisabled"))) {
				zhaobaoorder++;
				if (!Util.eq(0, record.get("peoplenumber"))) {
					zhaobaopeople += record.getInt("peoplenumber");
				}
			}
			//单子人数
			if (!Util.eq(0, record.get("peoplenumber"))) {
				peopletotal += record.getInt("peoplenumber");
			}
		}

		long middleTime3 = System.currentTimeMillis();
		System.out.println("循环所用时间:" + (middleTime3 - middleTime2) + "ms");
		sql.setPager(pager);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		long middleTime4 = System.currentTimeMillis();
		System.out.println("上头数据所用时间:" + (middleTime4 - middleTime3) + "ms");

		@SuppressWarnings("unchecked")
		//主sql数据
		List<Record> list = (List<Record>) sql.getResult();

		for (Record record : list) {

			if (!Util.isEmpty(record.get("errorMsg"))) {
				String errorMsg = (String) record.get("errorMsg");
				if (errorMsg.contains("氏名")) {
					record.set("errormsg", "第" + (Integer.valueOf(errorMsg.substring(0, 1)) - 1) + "个人姓名填写错误");
				}
				if (errorMsg.contains("居住地域")) {
					record.set("errormsg", "第" + (Integer.valueOf(errorMsg.substring(0, 1)) - 1) + "个人居住地填写错误");
				}
			}

			if (!Util.isEmpty(record.get("visatype"))) {
				Integer visatype = (Integer) record.get("visatype");
				for (SimpleVisaTypeEnum visatypenum : SimpleVisaTypeEnum.values()) {
					if (visatype == visatypenum.intKey()) {
						record.set("visatype", visatypenum.value());
					}
				}
			}

			if (!Util.isEmpty(record.get("comshortname"))) {
				record.set("comshortname", record.get("comshortname"));
			} else if (!Util.isEmpty(record.get("customershotname"))) {
				record.set("comshortname", record.get("customershotname"));
			} else {
				record.set("comshortname", "");
			}

			Integer orderid = (Integer) record.get("id");
			String sqlStr = sqlManager.get("get_simplelist_data_apply");
			Sql applysql = Sqls.create(sqlStr);
			List<Record> query = dbDao.query(
					applysql,
					Cnd.where("taoj.orderId", "=", orderid).orderBy("taoj.isMainApplicant", "DESC")
							.orderBy("ta.id", "ASC"), null);
			record.put("everybodyInfo", query);
			//签证状态
			Integer visastatus = record.getInt("japanState");
			for (JPOrderStatusEnum visaenum : JPOrderStatusEnum.values()) {
				if (visaenum.intKey() == visastatus) {
					record.put("visastatus", visaenum.value());
				}
			}
		}

		/*int orderscount = 0;
		int peopletotal = 0;
		int zhaobaoorder = 0;
		int zhaobaopeople = 0;
		//别的查询
		Map<String, Integer> informationSearch = informationSearch(form, 1);
		orderscount = informationSearch.get("orderscount");
		peopletotal = informationSearch.get("peopletotal");
		zhaobaoorder = informationSearch.get("zhaobaoorder");
		zhaobaopeople = informationSearch.get("zhaobaopeople");*/

		StatisticsEntity entity = new StatisticsEntity();
		entity.setOrderscount(orderscount);
		entity.setPeopletotal(peopletotal);
		entity.setZhaobaoorder(zhaobaoorder);
		entity.setZhaobaopeople(zhaobaopeople);

		if (Util.eq(form.getStatus(), JPOrderStatusEnum.DISABLED.intKey())) {//筛选条件为作废时，单人招宝成功为0
			entity.setSingleperson(0);
			entity.setMultiplayer(0);
		} else {
			//查询单组单人
			List<Record> singleperson = getSingleperson(form, 1);

			entity.setSingleperson(singleperson.size());
			entity.setMultiplayer(zhaobaoorder - singleperson.size());
		}

		result.put("entity", entity);
		result.put("pagetotal", pager.getPageCount());
		result.put("visaJapanData", list);
		long endTime = System.currentTimeMillis();
		System.out.println("下头所用时间为：" + (endTime - middleTime4) + "ms");
		System.out.println("方法所用时间为：" + (endTime - startTime) + "ms");
		return result;
	}

	public Sql getPaperSql(ListDataForm form) {
		String singlesqlStr = sqlManager.get("get_simplelist_data_paper");
		Sql singlesql = Sqls.create(singlesqlStr);
		Cnd singlecnd = Cnd.NEW();
		singlecnd = commonCondition(form, singlecnd);

		singlesql.setCondition(singlecnd);
		return singlesql;
	}

	public Map<String, Integer> informationSearch(ListDataForm form, int type) {
		Map<String, Integer> result = Maps.newHashMap();

		long startTime = System.currentTimeMillis();
		String singlesqlStr = sqlManager.get("get_japan_visa_list_data");
		Sql singlesql = Sqls.create(singlesqlStr);
		Cnd singlecnd = Cnd.NEW();
		singlecnd = commonCondition(form, singlecnd);

		singlesql.setCondition(singlecnd);
		List<Record> totallist = dbDao.query(singlesql, singlecnd, null);

		int orderscount = totallist.size();
		int peopletotal = 0;
		int zhaobaoorder = 0;
		int zhaobaopeople = 0;
		/*for (Record record : totallist) {
			//收费单子，人数
			if (Util.eq(1, record.get("zhaobaoupdate")) && Util.eq(0, record.get("isDisabled"))) {
				zhaobaoorder++;
				if (!Util.eq(0, record.get("peoplenumber"))) {
					zhaobaopeople += record.getInt("peoplenumber");
				}
			}
			//单子人数
			if (!Util.eq(0, record.get("peoplenumber"))) {
				peopletotal += record.getInt("peoplenumber");
			}
		}*/

		long endTime = System.currentTimeMillis();
		System.out.println("其他统计所用时间为：" + (endTime - startTime) + "ms");
		result.put("zhaobaoorder", zhaobaoorder);
		result.put("zhaobaopeople", zhaobaopeople);
		result.put("peopletotal", peopletotal);
		result.put("orderscount", orderscount);
		return result;
	}

	/**
	 * 查询单人单组数据
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public List<Record> getSingleperson(ListDataForm form, int type) {
		long startTime = System.currentTimeMillis();
		String singlesqlStr = sqlManager.get("new_getSingleperson");
		Sql singlesql = Sqls.create(singlesqlStr);
		Cnd singlecnd = Cnd.NEW();
		singlecnd = commonCondition(form, singlecnd);

		singlecnd.and("tr.zhaobaoupdate", "=", 1);
		//singlecnd.groupBy("tr.orderNum").having(Cnd.wrap("ct = 1"));

		singlesql.setCondition(singlecnd);
		List<Record> singleperson = dbDao.query(singlesql, singlecnd, null);
		long endTime = System.currentTimeMillis();
		System.out.println("查询单组单人所用时间为：" + (endTime - startTime) + "ms");
		return singleperson;
	}

	/**
	 * 共用的搜索条件cnd
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param singlecnd
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Cnd commonCondition(ListDataForm form, Cnd singlecnd) {
		singlecnd.and("tr.comId", "=", form.getCompanyid());
		if (!Util.isEmpty(form.getSearchStr())) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tr.orderNum", "like", "%" + form.getSearchStr() + "%")
					.or("tc.linkman", "like", "%" + form.getSearchStr() + "%")
					.or("tc.mobile", "like", "%" + form.getSearchStr() + "%")
					.or("tc.email", "like", "%" + form.getSearchStr() + "%")
					//.or("taj.applyname", "like", "%" + form.getSearchStr() + "%")
					.or("toj.acceptDesign", "like", "%" + form.getSearchStr() + "%")
					//.or("taj.passport", "like", "%" + form.getSearchStr() + "%")
					.or("(SELECT GROUP_CONCAT(CONCAT(ta.firstName,ta.lastName) SEPARATOR 'төл') applyname FROM t_applicant ta INNER JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id LEFT JOIN t_order_jp toj ON taoj.orderId = toj.id LEFT JOIN t_order tor ON toj.orderId = tor.id WHERE tor.id = tr.id GROUP BY toj.orderId)",
							"like", "%" + form.getSearchStr() + "%")
					.or("(SELECT tap.passport FROM t_applicant ta INNER JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id LEFT JOIN t_order_jp toj ON taoj.orderId = toj.id LEFT JOIN t_order tor ON toj.orderId = tor.id WHERE tor.id = tr.id GROUP BY toj.orderId)",
							"like", "%" + form.getSearchStr() + "%");
			singlecnd.and(exp);
		}

		if (!Util.isEmpty(form.getSendstartdate()) && !Util.isEmpty(form.getSendenddate())) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tr.sendVisaDate", "between", new Object[] { form.getSendstartdate(), form.getSendenddate() });
			singlecnd.and(exp);
		}

		if (!Util.isEmpty(form.getOrderstartdate()) && !Util.isEmpty(form.getOrderenddate())) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tr.createTime", "between", new Object[] { form.getOrderstartdate(), form.getOrderenddate() });
			singlecnd.and(exp);
		}
		if (!Util.isEmpty(form.getStatus())) {
			if (Util.eq(form.getStatus(), JPOrderStatusEnum.DISABLED.intKey())) {
				singlecnd.and("tr.isDisabled", "=", IsYesOrNoEnum.YES.intKey());
			} else if (Util.eq(form.getStatus(), 22)) {
				SqlExpressionGroup e1 = Cnd.exps("tr.status", "=", form.getStatus()).or("tr.status", "=", 35)
						.and("tr.isDisabled", "=", IsYesOrNoEnum.NO.intKey());
				singlecnd.and(e1);
			} else if (Util.eq(19, form.getStatus())) {
				SqlExpressionGroup e1 = Cnd.exps("tr.status", "=", form.getStatus()).or("tr.status", "=", 34)
						.and("tr.isDisabled", "=", IsYesOrNoEnum.NO.intKey());
				singlecnd.and(e1);
			} else {
				SqlExpressionGroup e1 = Cnd.exps("tr.status", "=", form.getStatus()).and("tr.isDisabled", "=",
						IsYesOrNoEnum.NO.intKey());
				singlecnd.and(e1);
			}
		} else {
			SqlExpressionGroup e1 = Cnd.exps("tr.isDisabled", "=", IsYesOrNoEnum.NO.intKey());
			singlecnd.and(e1);
		}

		if (!Util.isEmpty(form.getSongqianshe())) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("toj.sendsignid", "=", form.getSongqianshe());
			singlecnd.and(exp);
		}

		if (!Util.isEmpty(form.getEmployee())) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tuser.id", "=", form.getEmployee());
			singlecnd.and(exp);
		}

		if (!Util.isEmpty(form.getVisatype())) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("toj.visatype", "=", form.getVisatype());
			singlecnd.and(exp);
		}

		if (form.getUserid().equals(form.getAdminId())) {
			//公司管理员
			singlecnd.and("tr.comId", "=", form.getCompanyid());
		} else {
			//普通的操作员
			singlecnd.and("tr.salesOpid", "=", form.getUserid());
		}

		//singlecnd.orderBy("tr.isDisabled", "ASC");
		//singlecnd.orderBy("tr.updatetime", "desc");
		//singlecnd.orderBy("tr.id", "desc");

		return singlecnd;
	}

	/**
	 * 跳转到添加订单页面
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addOrder(HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		HttpSession session = request.getSession();
		result.put("collarAreaEnum", EnumUtil.enum2(CollarAreaEnum.class));
		result.put("customerTypeEnum", EnumUtil.enum2(CustomerTypeEnum.class));
		result.put("mainSaleUrgentEnum", EnumUtil.enum2(MainSaleUrgentEnum.class));
		result.put("mainSaleUrgentTimeEnum", EnumUtil.enum2(MainSaleUrgentTimeEnum.class));
		result.put("mainSaleTripTypeEnum", EnumUtil.enum2(MainSaleTripTypeEnum.class));
		result.put("mainSalePayTypeEnum", EnumUtil.enum2(MainSalePayTypeEnum.class));
		result.put("mainSaleVisaTypeEnum", EnumUtil.enum2(SimpleVisaTypeEnum.class));

		return result;
	}

	/**
	 * 获取景点
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param scenicname
	 * @param cityid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getScenicSelect(String scenicname, int cityid, int planid, int visatype) {
		List<TScenicEntity> scenics = Lists.newArrayList();
		TOrderTravelplanJpEntity plan = dbDao.fetch(TOrderTravelplanJpEntity.class, planid);
		Integer orderid = plan.getOrderId();
		List<TOrderTravelplanJpEntity> planlist = dbDao.query(TOrderTravelplanJpEntity.class,
				Cnd.where("orderId", "=", orderid).orderBy("outDate", "ASC"), null);
		int days = 0;
		if (planlist.size() % 2 == 0) {//为2的倍数，则最后是三天，否则为两天
			days = planlist.size() - 3;
		} else {
			days = planlist.size() - 2;
		}

		String contains = isContains(visatype);

		if (planlist.get(1).getCityId() == planlist.get(2).getCityId()) {//第二天和第三天是同一个城市，说明出行抵达城市和返回出发城市一致，这时只刷新景点
			//获取城市所有的景区
			Cnd cnd = Cnd.NEW();
			/*cnd.and("name", "like", "%" + Strings.trim(scenicname) + "%");
			if (!Util.isEmpty(cityid)) {
				cnd.and("cityId", "=", cityid);
			}*/
			cnd.and("cityId", "=", cityid);
			scenics = dbDao.query(TScenicEntity.class, cnd, null);
		} else {

			TOrderTravelplanJpEntity formerPlan = dbDao.fetch(TOrderTravelplanJpEntity.class,
					Cnd.where("orderId", "=", orderid).and("day", "=", Integer.valueOf(plan.getDay()) - 1));
			if (plan.getCityId() == formerPlan.getCityId()) {//如果城市一样，说明没去别的地方，刷新景点
				//获取城市所有的景区
				Cnd cnd = Cnd.NEW();
				cnd.and("cityId", "=", cityid);
				scenics = dbDao.query(TScenicEntity.class, cnd, null);
			} else {
				List<TOrderTravelplanJpEntity> nowplanList = dbDao.query(TOrderTravelplanJpEntity.class,
						Cnd.where("orderId", "=", orderid).and("cityId", "=", plan.getCityId()), null);
				List<TOrderTravelplanJpEntity> lastplanList = dbDao.query(
						TOrderTravelplanJpEntity.class,
						Cnd.where("orderId", "=", orderid).and("cityId", "=",
								planlist.get(planlist.size() - 1).getCityId()), null);

				if (nowplanList.size() == 1) {
					scenics = visaJapanService.countryAirline(formerPlan.getCityId(), plan.getCityId(), 1);

				} else if (lastplanList.size() == 2 && Integer.valueOf(plan.getDay()) == planlist.size() - 1) {
					scenics = visaJapanService.countryAirline(formerPlan.getCityId(), plan.getCityId(), 1);

				} else {
					scenics = visaJapanService.countryAirline(formerPlan.getCityId(), plan.getCityId(), 2);

				}

			}

		}

		return scenics;
	}

	public String isContains(int visatype) {
		String result = "";
		ArrayList<Integer> visatypeList = new ArrayList<>();
		visatypeList.add(3);
		visatypeList.add(4);
		visatypeList.add(5);
		visatypeList.add(8);
		visatypeList.add(9);
		visatypeList.add(10);
		visatypeList.add(11);
		visatypeList.add(12);
		visatypeList.add(13);
		if (visatypeList.contains(visatype)) {
			result = "true";
		} else {
			result = "false";
		}
		return result;
	}

	/**
	 * 获取酒店
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param scenicname
	 * @param cityid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getHotelSelect(String hotelname, int cityid) {
		//Cnd cnd = Cnd.NEW();

		Cnd cnd = Cnd.where(
				Cnd.exps("address", "like", "%" + Strings.trim(hotelname) + "%")
						.or("addressjp", "like", "%" + Strings.trim(hotelname) + "%")
						.or("mobile", "like", "%" + Strings.trim(hotelname) + "%")).and("cityId", "=", cityid);

		/*SqlExpressionGroup exp = new SqlExpressionGroup();
		//exp.and("cityId", "=", cityid);
		exp.and("address", "like", "%" + Strings.trim(hotelname) + "%")
				.or("mobile", "like", "%" + Strings.trim(hotelname) + "%")
				.or("addressjp", "like", "%" + Strings.trim(hotelname) + "%");
		cnd.and(exp);

		exp = new SqlExpressionGroup();
		exp.and("cityId", "=", cityid);
		cnd.and(exp);*/

		List<THotelEntity> hotels = dbDao.query(THotelEntity.class, cnd, null);
		return hotels;
	}

	public Object addHotel(Integer planid, int visatype) {
		Map<String, Object> result = Maps.newHashMap();
		TOrderTravelplanJpEntity plan = dbDao.fetch(TOrderTravelplanJpEntity.class, planid.longValue());
		result.put("travelplan", plan);
		return result;

	}

	public Object addsimplehotel(AddSimpleHotelForm form) {
		THotelEntity hotel = new THotelEntity();
		hotel.setAddress(form.getAddress());
		hotel.setAddressjp(form.getAddressjp());
		hotel.setCityId(form.getCityId());
		hotel.setCreateTime(new Date());
		hotel.setMobile(form.getMobile());
		hotel.setName(form.getName());
		hotel.setNamejp(form.getNamejp());
		hotel.setUpdateTime(new Date());
		THotelEntity insertHotel = dbDao.insert(hotel);
		Integer travelplanid = form.getTravelplanid();
		TCityEntity city = dbDao.fetch(TCityEntity.class, form.getCityId().longValue());
		TOrderTravelplanJpEntity travelplan = dbDao.fetch(TOrderTravelplanJpEntity.class, travelplanid.longValue());
		travelplan.setHotel(insertHotel.getId());
		travelplan.setCityId(form.getCityId());
		travelplan.setCityName(city.getCity());
		//travelplan.setIsupdatecity(IsYesOrNoEnum.YES.intKey());
		dbDao.update(travelplan);
		return null;
	}

	/**
	 * 生成行程安排
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param request
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	/*public Object generateTravelPlan(HttpServletRequest request, GenerrateTravelForm form) {
		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Map<String, Object> result = Maps.newHashMap();
		if (Util.isEmpty(form.getGoDepartureCity())) {
			result.put("message", "请选择出发城市");
			return result;
		}
		if (Util.isEmpty(form.getGoArrivedCity())) {
			result.put("message", "请选择抵达城市");
			return result;
		}
		if (Util.isEmpty(form.getGoDate())) {
			result.put("message", "请选择出发日期");
			return result;
		}
		if (Util.isEmpty(form.getReturnDate())) {
			result.put("message", "请选择返回日期");
			return result;
		}
		if (Util.isEmpty(form.getGoFlightNum())) {
			result.put("message", "请选择出发航班号");
			return result;
		}
		if (Util.isEmpty(form.getReturnFlightNum())) {
			result.put("message", "请选择返回航班号");
			return result;
		}
		if (Util.isEmpty(form.getVisatype())) {
			result.put("message", "请选择签证类型");
			return result;
		}
		int daysBetween = DateUtil.daysBetween(form.getGoDate(), form.getReturnDate());
		if (daysBetween < 4) {
			result.put("message", "停留天数必须大于4天");
			return result;
		}
		//返回时的出发城市
		Integer returnDepartureCity = form.getReturnDepartureCity();
		TCityEntity returngoCity = dbDao.fetch(TCityEntity.class, returnDepartureCity.longValue());

		//出发城市
		Integer goDepartureCity = form.getGoDepartureCity();
		TCityEntity goCity = dbDao.fetch(TCityEntity.class, goDepartureCity.longValue());
		String province = goCity.getProvince();
		if (province.endsWith("省") || province.endsWith("市")) {
			province = province.substring(0, province.length() - 1);
		}
		//出发航班
		String goFlightNum = form.getGoFlightNum();
		String firstday = " "
				+ province
				+ "から"
				+ goFlightNum.substring(goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")) + 1,
						goFlightNum.indexOf(" ", goFlightNum.indexOf(" ") + 1))
				+ "便にて"
				+ goFlightNum.substring(goFlightNum.indexOf("-", goFlightNum.lastIndexOf("-")) + 1,
						goFlightNum.indexOf(" ", goFlightNum.indexOf(" "))) + "へ" + "\n 到着後、ホテルへ";

		//返回航班
		String returnFlightNum = form.getReturnFlightNum();
		String lastday = " "
				+ returnFlightNum.substring(0, returnFlightNum.indexOf("-", returnFlightNum.indexOf("-")))
				+ "から"
				+ returnFlightNum.substring(returnFlightNum.indexOf(" ", returnFlightNum.indexOf(" ")) + 1,
						returnFlightNum.indexOf(" ", returnFlightNum.indexOf(" ") + 1)) + "便にて帰国";

		FlightSelectParam param = null;
		//根据签证类型来决定前两天的城市
		Integer visatype = form.getVisatype();

		//获取前两天城市
		TCityEntity city = dbDao.fetch(TCityEntity.class, form.getGoArrivedCity().longValue());
		//获取前两天城市所有的酒店
		List<THotelEntity> hotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", form.getGoArrivedCity()),
				null);
		//获取前两天城市所有的景区
		List<TScenicEntity> scenics = dbDao.query(TScenicEntity.class,
				Cnd.where("cityId", "=", form.getGoArrivedCity()), null);
		if (scenics.size() < 2) {
			result.put("message", "没有更多的景区");
			return result;
		}
		//获取后两天城市
		TCityEntity lastcity = dbDao.fetch(TCityEntity.class, form.getReturnDepartureCity().longValue());
		//获取后两天酒店
		List<THotelEntity> lasthotels = dbDao.query(THotelEntity.class,
				Cnd.where("cityId", "=", form.getReturnDepartureCity()), null);
		//获取后两天景区
		List<TScenicEntity> lastscenics = dbDao.query(TScenicEntity.class,
				Cnd.where("cityId", "=", form.getReturnDepartureCity()), null);
		if (lastscenics.size() < 2) {
			result.put("message", "没有更多的景区");
			return result;
		}
		Integer orderjpid = form.getOrderid();
		Integer orderid = null;
		if (Util.isEmpty(orderjpid)) {
			//如果订单不存在，创建订单
			Map<String, Integer> generrateorder = generrateorder(loginUser, loginCompany);
			orderid = generrateorder.get("orderid");
			orderjpid = generrateorder.get("orderjpid");
		} else {
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
			orderid = orderjp.getOrderId();
		}
		//需要生成的travelplan
		List<TOrderTravelplanJpEntity> travelplans = Lists.newArrayList();
		//生成行程安排历史信息
		//		List<TOrderTravelplanHisJpEntity> travelplansHis = Lists.newArrayList();

		//在一个城市只住一家酒店
		Random random = new Random();
		int hotelindex = random.nextInt(hotels.size());
		int lasthotelindex = random.nextInt(lasthotels.size());

		if (visatype == 6 || visatype == 1 || visatype == 14 || visatype == 2 || visatype == 7) {//除去东北六县
			//如果去程抵达城市和返回出发城市一样，则什么都不需要分
			if (form.getGoArrivedCity() == form.getReturnDepartureCity()) {
				for (int i = 0; i <= daysBetween; i++) {
					TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
					travelplan.setCityId(form.getGoArrivedCity());
					travelplan.setDay(String.valueOf(i + 1));
					travelplan.setOrderId(orderjpid);
					travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
					travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
					travelplan.setCityName(city.getCity());
					travelplan.setCreateTime(new Date());

					//酒店
					if (i != daysBetween) {
						THotelEntity hotel = hotels.get(hotelindex);
						travelplan.setHotel(hotel.getId());
					}
					if (i > 0 && i != daysBetween) {
						//景区
						if (scenics.size() == 0) {
							scenics = dbDao.query(TScenicEntity.class,
									Cnd.where("cityId", "=", form.getGoArrivedCity()), null);
						}
						int scenicindex = random.nextInt(scenics.size());
						TScenicEntity scenic = scenics.get(scenicindex);
						scenics.remove(scenic);
						travelplan.setScenic(scenic.getName());
					}
					if (i == 0) {//第一天
						travelplan.setScenic(firstday);
					}
					if (i == daysBetween) {//最后一天
						travelplan.setScenic(lastday);
					}
					travelplans.add(travelplan);
				}
			} else {
				//为什么要<=，因为最后一天也要玩
				//if (visatype == 6 || visatype == 1 || visatype == 14) {//普通三年多次，日本单次和普通五年多次一样
				//前两天
				for (int i = 0; i < 2; i++) {
					TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
					//			TOrderTravelplanHisJpEntity travelPlanHis = new TOrderTravelplanHisJpEntity();
					travelplan.setCityId(form.getGoArrivedCity());
					travelplan.setCityName(city.getCity());
					travelplan.setDay(String.valueOf(i + 1));
					travelplan.setOrderId(orderjpid);
					travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
					travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());

					travelplan.setCreateTime(new Date());

					if (i != daysBetween) {
						THotelEntity hotel = hotels.get(hotelindex);
						travelplan.setHotel(hotel.getId());
					}
					if (i == 0) {
						travelplan.setScenic(firstday);
					}
					if (i > 0 && i != daysBetween) {
						//景区
						int scenicindex = random.nextInt(scenics.size());
						TScenicEntity scenic = scenics.get(scenicindex);
						scenics.remove(scenic);
						travelplan.setScenic(scenic.getName());
					}
					//第三天，去返回时即第二行的出发城市，通过日本国内航线或新干线
					if (i == 2) {
						travelplan.setScenic("");
						THotelEntity hotel = lasthotels.get(lasthotelindex);
						travelplan.setHotel(hotel.getId());
					}
					travelplans.add(travelplan);
				}
				//除去开始的前两天和最后四天，如果天数为2的倍数，则中间多2的倍数个随机城市，有余数则最后变为4天
				int subday = daysBetween - 4;
				int[] randomArray = new int[20];
				if (subday % 2 == 0) {
					int totalstyle = subday / 2;
					//intArray为所有有景点的城市并且出去东北六县的Id组成的数组
					int[] intArray = generrateCityArray();
					intArray = getCitysArray(intArray, form.getGoArrivedCity(), form.getReturnDepartureCity());
					//randomArray为获取的不重复随机数
					if (intArray.length < totalstyle) {
						result.put("message", "没有更多的城市");
						return result;
					} else {
						randomArray = getRandomArray(intArray, totalstyle);
					}

					//去程时抵达城市为冲绳时数据处理
					int[] csArray = { 51, 22, 85, 58, 66 };
					int[] citysArray = getCitysArray(csArray, form.getGoArrivedCity(), form.getReturnDepartureCity());
					int[] csrandomArray = getRandomArray(citysArray, 1);
					int cscityid = csrandomArray[0];
					TCityEntity cscity = dbDao.fetch(TCityEntity.class, cscityid);
					List<TScenicEntity> csScenics = dbDao.query(TScenicEntity.class,
							Cnd.where("cityId", "=", cscityid), null);
					if (csScenics.size() < 1) {
						result.put("message", "没有更多的景点");
						return result;
					}
					List<THotelEntity> csHotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", cscityid),
							null);
					if (csHotels.size() < 1) {
						result.put("message", "没有更多的酒店");
						return result;
					}
					int cshotel = random.nextInt(csHotels.size());

					for (int i = 2; i < daysBetween - 2; i++) {

						int firstcityid = randomArray[((i - 2) / 2)];
						int lastcityid = 0;
						if (i >= 4) {
							lastcityid = randomArray[((i - 4) / 2)];
						}
						TCityEntity fcity = dbDao.fetch(TCityEntity.class, firstcityid);
						TCityEntity lcity = null;
						if (lastcityid != 0) {
							lcity = dbDao.fetch(TCityEntity.class, lastcityid);
						}

						List<THotelEntity> fhotels = dbDao.query(THotelEntity.class,
								Cnd.where("cityId", "=", firstcityid), null);
						if (fhotels.size() == 0) {
							result.put("message", "没有更多的酒店");
							return result;
						}
						List<TScenicEntity> fscenics = dbDao.query(TScenicEntity.class,
								Cnd.where("cityId", "=", firstcityid), null);
						int fhotelindex = random.nextInt(fhotels.size());

						//tripAirlineService.getTripAirlineSelect(param);

						//第三天
						if (i < 4) {
							TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
							if (form.getGoArrivedCity() == 77) {//如果去程的抵达城市为冲绳，则第三天所去的城市在固定的五个城市中随机:大阪，东京，名古屋，广岛，长崎
								travelplan.setCityId(cscityid);
								travelplan.setCityName(cscity.getCity());

								//酒店和航班取返程即第二行的出发城市
								//酒店
								if (i == 2) {
									THotelEntity hotel = csHotels.get(cshotel);
									travelplan.setHotel(hotel.getId());
									//酒店历史信息
									//				travelPlanHis.setHotel(hotel.getName());
								}
								if (i == 2) {
									String countryAirline = countryAirline(form.getGoArrivedCity(), cscityid, 1);
									travelplan.setScenic(countryAirline);
								}
								if (i == 3) {
									//景区
									int scenicindex = random.nextInt(csScenics.size());
									TScenicEntity scenic = csScenics.get(scenicindex);
									//csScenics.remove(scenic);
									travelplan.setScenic(scenic.getName());
								}
							} else {
								travelplan.setCityId(firstcityid);
								travelplan.setCityName(fcity.getCity());
								//酒店和航班取返程即第二行的出发城市
								//酒店
								if (i == 2) {
									THotelEntity hotel = fhotels.get(fhotelindex);
									travelplan.setHotel(hotel.getId());
									//酒店历史信息
									//				travelPlanHis.setHotel(hotel.getName());
								}
								if (i == 2) {
									String countryAirline = countryAirline(form.getGoArrivedCity(), firstcityid, 2);
									travelplan.setScenic(countryAirline);
								}
								if (i == 3) {
									//景区
									int scenicindex = random.nextInt(csScenics.size());
									TScenicEntity scenic = csScenics.get(scenicindex);
									csScenics.remove(scenic);
									travelplan.setScenic(scenic.getName());
								}

							}
							travelplan.setDay(String.valueOf(i + 1));
							travelplan.setOrderId(orderjpid);
							travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
							travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
							travelplan.setCreateTime(new Date());
							travelplans.add(travelplan);
						} else {
							TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
							travelplan.setCityId(firstcityid);
							travelplan.setDay(String.valueOf(i + 1));
							travelplan.setOrderId(orderjpid);
							travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
							travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
							travelplan.setCityName(fcity.getCity());
							travelplan.setCreateTime(new Date());
							//酒店和航班取返程即第二行的出发城市
							//酒店
							if (i % 2 == 0) {
								THotelEntity hotel = fhotels.get(fhotelindex);
								travelplan.setHotel(hotel.getId());
								//酒店历史信息
								//				travelPlanHis.setHotel(hotel.getName());
							}
							if (i % 2 == 0) {
								if (i == 4) {
									if (form.getGoArrivedCity() == 77) {
										String countryAirline = countryAirline(cscityid, firstcityid, 2);
										travelplan.setScenic(countryAirline);
									} else {
										String countryAirline = countryAirline(lcity.getId(), firstcityid, 2);
										travelplan.setScenic(countryAirline);
									}
								}
							} else {
								//景区
								if (fscenics.size() == 0) {//如果随机完所有的，则重新查一次
									fscenics = dbDao.query(TScenicEntity.class, Cnd.where("cityId", "=", firstcityid),
											null);
								}
								int scenicindex = random.nextInt(fscenics.size());
								TScenicEntity scenic = fscenics.get(scenicindex);
								fscenics.remove(scenic);
								travelplan.setScenic(scenic.getName());
							}
							travelplans.add(travelplan);
						}
					}

					//最后四天
					for (int i = daysBetween - 2; i <= daysBetween; i++) {
						TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
						travelplan.setCityId(form.getReturnDepartureCity());
						travelplan.setDay(String.valueOf(i + 1));
						travelplan.setOrderId(orderjpid);
						travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
						travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
						travelplan.setCityName(returngoCity.getCity());
						travelplan.setCreateTime(new Date());
						//酒店和航班取返程即第二行的出发城市
						//酒店
						if (i != daysBetween) {
							THotelEntity hotel = lasthotels.get(lasthotelindex);
							travelplan.setHotel(hotel.getId());
							//酒店历史信息
							//				travelPlanHis.setHotel(hotel.getName());
						}
						if (i == daysBetween) {
							travelplan.setScenic(lastday);
						} else if (i == daysBetween - 2) {
							if (daysBetween == 4) {//如果是4则说明中间没有随机城市
								if (form.getGoArrivedCity() == 77) {
									String countryAirline = countryAirline(form.getGoArrivedCity(),
											form.getReturnDepartureCity(), 1);
									travelplan.setScenic(countryAirline);
								} else {
									String countryAirline = countryAirline(form.getGoArrivedCity(),
											form.getReturnDepartureCity(), 2);
									travelplan.setScenic(countryAirline);
								}
							} else if (daysBetween == 6) {
								String countryAirline = countryAirline(cscityid, form.getReturnDepartureCity(), 2);
								travelplan.setScenic(countryAirline);
							} else {
								String countryAirline = countryAirline(randomArray[totalstyle - 1],
										form.getReturnDepartureCity(), 2);
								travelplan.setScenic(countryAirline);
							}
						} else {
							//景区
							if (scenics.size() == 0) {
								scenics = dbDao.query(TScenicEntity.class,
										Cnd.where("cityId", "=", form.getGoArrivedCity()), null);
							}
							int scenicindex = random.nextInt(scenics.size());
							TScenicEntity scenic = scenics.get(scenicindex);
							scenics.remove(scenic);
							travelplan.setScenic(scenic.getName());
						}
						travelplans.add(travelplan);
					}
				} else {
					int totalstyle = subday / 2;
					//intArray为所有有景点的城市并且出去东北六县的Id组成的数组
					int[] intArray = generrateCityArray();
					intArray = getCitysArray(intArray, form.getGoArrivedCity(), form.getReturnDepartureCity());
					//randomArray为获取的不重复随机数
					if (intArray.length < totalstyle) {
						result.put("message", "没有更多的城市");
						return result;
					} else {
						randomArray = getRandomArray(intArray, totalstyle);
					}
					//去程时抵达城市为冲绳时数据处理
					int[] csArray = { 51, 22, 85, 58, 66 };
					int[] citysArray = getCitysArray(csArray, form.getGoArrivedCity(), form.getReturnDepartureCity());
					int[] csrandomArray = getRandomArray(citysArray, 1);
					int cscityid = csrandomArray[0];
					TCityEntity cscity = dbDao.fetch(TCityEntity.class, cscityid);
					List<TScenicEntity> csScenics = dbDao.query(TScenicEntity.class,
							Cnd.where("cityId", "=", cscityid), null);
					if (csScenics.size() < 1) {
						result.put("message", "没有更多的景点");
						return result;
					}
					List<THotelEntity> csHotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", cscityid),
							null);
					if (csHotels.size() < 1) {
						result.put("message", "没有更多的酒店");
						return result;
					}
					int cshotel = random.nextInt(csHotels.size());

					for (int i = 2; i < daysBetween - 3; i++) {

						int firstcityid = randomArray[((i - 2) / 2)];
						TCityEntity lcity = null;
						if (i > 3) {
							int lastcityid = randomArray[((i - 4) / 2)];
							lcity = dbDao.fetch(TCityEntity.class, lastcityid);
						}
						TCityEntity fcity = dbDao.fetch(TCityEntity.class, firstcityid);

						List<THotelEntity> fhotels = dbDao.query(THotelEntity.class,
								Cnd.where("cityId", "=", firstcityid), null);
						if (fhotels.size() == 0) {
							result.put("message", "没有更多的酒店");
							return result;
						}
						List<TScenicEntity> fscenics = dbDao.query(TScenicEntity.class,
								Cnd.where("cityId", "=", firstcityid), null);
						int fhotelindex = random.nextInt(fhotels.size());

						//tripAirlineService.getTripAirlineSelect(param);

						//第三天
						if (i < 4) {
							TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
							if (form.getGoArrivedCity() == 77) {//如果去程的抵达城市为冲绳，则第三天所去的城市在固定的五个城市中随机:大阪，东京，名古屋，广岛，长崎

								travelplan.setCityId(cscityid);
								travelplan.setCityName(cscity.getCity());

								//酒店和航班取返程即第二行的出发城市
								//酒店
								if (i == 2) {
									THotelEntity hotel = csHotels.get(cshotel);
									travelplan.setHotel(hotel.getId());
									//酒店历史信息
									//				travelPlanHis.setHotel(hotel.getName());
								}
								if (i == 2) {
									String countryAirline = countryAirline(form.getGoArrivedCity(), cscityid, 1);
									travelplan.setScenic(countryAirline);
								}
								if (i == 3) {
									//景区
									int scenicindex = random.nextInt(csScenics.size());
									TScenicEntity scenic = csScenics.get(scenicindex);
									//csScenics.remove(scenic);
									travelplan.setScenic(scenic.getName());
								}
							} else {
								travelplan.setCityId(firstcityid);
								travelplan.setCityName(fcity.getCity());
								//酒店和航班取返程即第二行的出发城市
								//酒店
								if (i == 2) {
									THotelEntity hotel = fhotels.get(fhotelindex);
									travelplan.setHotel(hotel.getId());
									//酒店历史信息
									//				travelPlanHis.setHotel(hotel.getName());
								}
								if (i == 2) {
									String countryAirline = countryAirline(form.getGoArrivedCity(), firstcityid, 2);
									travelplan.setScenic(countryAirline);
								}
								if (i == 3) {
									//景区
									int scenicindex = random.nextInt(fscenics.size());
									TScenicEntity scenic = fscenics.get(scenicindex);
									fscenics.remove(scenic);
									travelplan.setScenic(scenic.getName());
								}
							}

							travelplan.setDay(String.valueOf(i + 1));
							travelplan.setOrderId(orderjpid);
							travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
							travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
							travelplan.setCreateTime(new Date());

							travelplans.add(travelplan);
						} else {
							TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
							travelplan.setCityId(fcity.getId());
							travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
							travelplan.setDay(String.valueOf(i + 1));
							travelplan.setOrderId(orderjpid);
							travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
							travelplan.setCityName(fcity.getCity());
							travelplan.setCreateTime(new Date());
							//酒店和航班取返程即第二行的出发城市
							//酒店
							if (i % 2 == 0) {
								THotelEntity hotel = fhotels.get(fhotelindex);
								travelplan.setHotel(hotel.getId());
								//酒店历史信息
								//				travelPlanHis.setHotel(hotel.getName());
							}
							if (i % 2 == 0) {
								if (i == 4) {
									if (form.getGoArrivedCity() == 77) {
										String countryAirline = countryAirline(cscityid, firstcityid, 2);
										travelplan.setScenic(countryAirline);
									} else {
										String countryAirline = countryAirline(lcity.getId(), firstcityid, 2);
										travelplan.setScenic(countryAirline);
									}
								}
							} else {
								//景区
								if (fscenics.size() == 0) {
									fscenics = dbDao.query(TScenicEntity.class, Cnd.where("cityId", "=", firstcityid),
											null);
								}
								int scenicindex = random.nextInt(fscenics.size());
								TScenicEntity scenic = fscenics.get(scenicindex);
								fscenics.remove(scenic);
								travelplan.setScenic(scenic.getName());
							}
							travelplans.add(travelplan);
						}
					}

					//最后三天
					for (int i = daysBetween - 3; i <= daysBetween; i++) {
						TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
						//			TOrderTravelplanHisJpEntity travelPlanHis = new TOrderTravelplanHisJpEntity();
						travelplan.setCityId(form.getReturnDepartureCity());
						travelplan.setDay(String.valueOf(i + 1));
						travelplan.setOrderId(orderjpid);
						travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
						travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
						travelplan.setCityName(returngoCity.getCity());
						travelplan.setCreateTime(new Date());
						//酒店和航班取返程即第二行的出发城市
						//酒店
						if (i != daysBetween) {
							THotelEntity hotel = lasthotels.get(lasthotelindex);
							travelplan.setHotel(hotel.getId());
							//酒店历史信息
							//				travelPlanHis.setHotel(hotel.getName());
						}
						if (i == daysBetween) {
							travelplan.setScenic(lastday);
						} else if (i == daysBetween - 3) {
							if (daysBetween == 5) {//如果是5的话，则说明中间没有随机城市
								if (form.getGoArrivedCity() == 77) {
									String countryAirline = countryAirline(form.getGoArrivedCity(),
											form.getReturnDepartureCity(), 1);
									travelplan.setScenic(countryAirline);
								} else {
									String countryAirline = countryAirline(form.getGoArrivedCity(),
											form.getReturnDepartureCity(), 2);
									travelplan.setScenic(countryAirline);
								}
							} else if (daysBetween == 7) {
								String countryAirline = countryAirline(cscityid, form.getReturnDepartureCity(), 2);
								travelplan.setScenic(countryAirline);
							} else {
								String countryAirline = countryAirline(randomArray[totalstyle - 1],
										form.getReturnDepartureCity(), 2);
								travelplan.setScenic(countryAirline);
							}
						} else {
							//景区
							int scenicindex = random.nextInt(scenics.size());
							TScenicEntity scenic = scenics.get(scenicindex);
							scenics.remove(scenic);
							travelplan.setScenic(scenic.getName());
						}
						travelplans.add(travelplan);
					}
				}

			}
		} else {//东北六县第三天要去对应的签证类型城市，不管去程抵达城市和返程出发城市是否一样，中间都随机
				//前两天
			for (int i = 0; i < 2; i++) {
				TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
				//			TOrderTravelplanHisJpEntity travelPlanHis = new TOrderTravelplanHisJpEntity();
				travelplan.setCityId(form.getGoArrivedCity());
				travelplan.setCityName(city.getCity());
				travelplan.setDay(String.valueOf(i + 1));
				travelplan.setOrderId(orderjpid);
				travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
				travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());

				travelplan.setCreateTime(new Date());

				if (i != daysBetween) {
					THotelEntity hotel = hotels.get(hotelindex);
					travelplan.setHotel(hotel.getId());
				}
				if (i == 0) {
					travelplan.setScenic(firstday);
				}
				if (i > 0 && i != daysBetween) {
					//景区
					int scenicindex = random.nextInt(scenics.size());
					TScenicEntity scenic = scenics.get(scenicindex);
					scenics.remove(scenic);
					travelplan.setScenic(scenic.getName());
				}
				//第三天，去返回时即第二行的出发城市，通过日本国内航线或新干线
				if (i == 2) {
					travelplan.setScenic("");
					THotelEntity hotel = lasthotels.get(lasthotelindex);
					travelplan.setHotel(hotel.getId());
				}
				travelplans.add(travelplan);
			}
			//除去开始的前两天和最后四天，如果天数为2的倍数，则中间多2的倍数个随机城市，有余数则最后变为4天
			int subday = daysBetween - 3;
			int[] randomArray = new int[20];

			//第三天去的城市签证类型城市
			int threeCityid = 0;
			if (visatype == 3 || visatype == 8) {//宫城
				threeCityid = 91;
			}
			if (visatype == 4 || visatype == 10) {//岩手
				threeCityid = 92;
			}
			if (visatype == 5 || visatype == 9) {//福岛
				threeCityid = 30;
			}
			if (visatype == 11) {//青森
				threeCityid = 25;
			}
			if (visatype == 12) {//秋田
				threeCityid = 612;
			}
			if (visatype == 13) {//山形
				threeCityid = 613;
			}

			TCityEntity threeCity = dbDao.fetch(TCityEntity.class, threeCityid);
			List<THotelEntity> threeHotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", threeCityid),
					null);
			if (threeHotels.size() < 1) {
				result.put("message", "没有更多的酒店");
				return result;
			}
			List<TScenicEntity> threeScenics = dbDao.query(TScenicEntity.class, Cnd.where("cityId", "=", threeCityid),
					null);
			if (threeScenics.size() < 1) {
				result.put("message", "没有更多的景点");
				return result;
			}
			int threehotel = random.nextInt(threeHotels.size());

			if (subday % 2 == 0) {
				int totalstyle = subday / 2;
				//intArray为所有有景点的城市并且除去东北六县的Id组成的数组
				int[] intArray = generrateCityArray();
				intArray = getCitysArray(intArray, form.getGoArrivedCity(), form.getReturnDepartureCity());
				//randomArray为获取的不重复随机数
				if (intArray.length < totalstyle) {
					result.put("message", "没有更多的城市");
					return result;
				} else {
					if (totalstyle == 0) {
						totalstyle = 1;
					}
					randomArray = getRandomArray(intArray, totalstyle);
				}

				for (int i = 2; i < daysBetween - 2; i++) {

					int firstcityid = 0;
					int lastcityid = 0;
					if (threeCityid == form.getGoArrivedCity()) {
						firstcityid = randomArray[((i - 2) / 2)];
						if (i >= 4) {
							lastcityid = randomArray[((i - 4) / 2)];
						}
					} else {
						if (i >= 3) {
							firstcityid = randomArray[((i - 3) / 2)];
						}
						if (i >= 5) {
							lastcityid = randomArray[((i - 5) / 2)];
						}
					}

					TCityEntity fcity = null;
					List<THotelEntity> fhotels = null;
					List<TScenicEntity> fscenics = null;
					int fhotelindex = 0;
					if (firstcityid != 0) {
						fcity = dbDao.fetch(TCityEntity.class, firstcityid);
						fhotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", firstcityid), null);
						if (fhotels.size() == 0) {
							result.put("message", "没有更多的酒店");
							return result;
						}
						fscenics = dbDao.query(TScenicEntity.class, Cnd.where("cityId", "=", firstcityid), null);
						fhotelindex = random.nextInt(fhotels.size());
					}
					TCityEntity lcity = null;
					if (lastcityid != 0) {
						lcity = dbDao.fetch(TCityEntity.class, lastcityid);
					}

					//tripAirlineService.getTripAirlineSelect(param);

					//第三天
					if (i < 4) {
						TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
						if (threeCityid == form.getGoArrivedCity()) {
							travelplan.setCityId(firstcityid);
							travelplan.setCityName(fcity.getCity());
						} else {
							if (i == 2) {
								travelplan.setCityId(threeCityid);
								travelplan.setCityName(threeCity.getCity());
							} else {
								travelplan.setCityId(firstcityid);
								travelplan.setCityName(fcity.getCity());
							}
						}
						//酒店和航班取返程即第二行的出发城市
						if (i == 2) {
							//酒店
							if (threeCityid == form.getGoArrivedCity()) {
								THotelEntity hotel = fhotels.get(fhotelindex);
								travelplan.setHotel(hotel.getId());
							} else {
								THotelEntity hotel = threeHotels.get(threehotel);
								travelplan.setHotel(hotel.getId());
							}

							//景区
							if (form.getGoArrivedCity() != threeCityid) {
								String countryAirline = countryAirline(form.getGoArrivedCity(), threeCityid, 2);
								int nextInt = random.nextInt(threeScenics.size());
								TScenicEntity scenic = threeScenics.get(nextInt);
								countryAirline = countryAirline + "。" + scenic.getName();
								travelplan.setScenic(countryAirline);
							} else {
								String countryAirline = countryAirline(form.getGoArrivedCity(), firstcityid, 2);
								travelplan.setScenic(countryAirline);
							}
						}
						if (i == 3) {
							//景区
							if (form.getGoArrivedCity() != threeCityid) {
								String countryAirline = countryAirline(threeCityid, firstcityid, 2);
								travelplan.setScenic(countryAirline);

							} else {
								int scenicindex = random.nextInt(fscenics.size());
								TScenicEntity scenic = fscenics.get(scenicindex);
								fscenics.remove(scenic);
								travelplan.setScenic(scenic.getName());
							}

							//酒店
							if (form.getGoArrivedCity() != threeCityid) {
								THotelEntity hotel = fhotels.get(fhotelindex);
								travelplan.setHotel(hotel.getId());
							} else {
								THotelEntity hotel = fhotels.get(fhotelindex);
								travelplan.setHotel(hotel.getId());
							}
						}

						travelplan.setDay(String.valueOf(i + 1));
						travelplan.setOrderId(orderjpid);
						travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
						travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
						travelplan.setCreateTime(new Date());
						travelplans.add(travelplan);
					} else {
						TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
						travelplan.setCityId(firstcityid);
						travelplan.setDay(String.valueOf(i + 1));
						travelplan.setOrderId(orderjpid);
						travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
						travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
						travelplan.setCityName(fcity.getCity());
						travelplan.setCreateTime(new Date());
						//酒店和航班取返程即第二行的出发城市
						//酒店
						if (i % 2 == 1) {
							THotelEntity hotel = fhotels.get(fhotelindex);
							travelplan.setHotel(hotel.getId());
							//酒店历史信息
							//				travelPlanHis.setHotel(hotel.getName());
						}
						if (i % 2 == 1) {
							if (i == 3) {
								String countryAirline = countryAirline(threeCityid, firstcityid, 2);
								travelplan.setScenic(countryAirline);
							} else {
								String countryAirline = countryAirline(lastcityid, firstcityid, 2);
								travelplan.setScenic(countryAirline);
							}
						} else {
							//景区
							if (fscenics.size() == 0) {//如果随机完所有的，则重新查一次
								fscenics = dbDao
										.query(TScenicEntity.class, Cnd.where("cityId", "=", firstcityid), null);
							}
							int scenicindex = random.nextInt(fscenics.size());
							TScenicEntity scenic = fscenics.get(scenicindex);
							fscenics.remove(scenic);
							travelplan.setScenic(scenic.getName());
						}
						travelplans.add(travelplan);
					}
				}

				//最后三天
				for (int i = daysBetween - 2; i <= daysBetween; i++) {
					TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
					travelplan.setCityId(form.getReturnDepartureCity());
					travelplan.setDay(String.valueOf(i + 1));
					travelplan.setOrderId(orderjpid);
					travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
					travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
					travelplan.setCityName(returngoCity.getCity());
					travelplan.setCreateTime(new Date());
					//酒店和航班取返程即第二行的出发城市
					//酒店
					if (i != daysBetween) {
						THotelEntity hotel = lasthotels.get(lasthotelindex);
						travelplan.setHotel(hotel.getId());
						//酒店历史信息
						//				travelPlanHis.setHotel(hotel.getName());
					}
					if (i == daysBetween) {
						travelplan.setScenic(lastday);
					} else if (i == daysBetween - 2) {
						if (daysBetween == 5) {//如果是5则说明中间只有签证类型城市没有随机城市
							String countryAirline = countryAirline(threeCityid, form.getReturnDepartureCity(), 2);
							int nextInt = random.nextInt(threeScenics.size());
							TScenicEntity scenic = threeScenics.get(nextInt);
							countryAirline = countryAirline + "。" + scenic.getName();
							travelplan.setScenic(countryAirline);
						} else if (daysBetween == 3) {
							String countryAirline = countryAirline(form.getGoArrivedCity(),
									form.getReturnDepartureCity(), 2);
							travelplan.setScenic(countryAirline);
						} else {
							String countryAirline = countryAirline(randomArray[totalstyle - 2],
									form.getReturnDepartureCity(), 2);
							travelplan.setScenic(countryAirline);
						}
					} else {
						//景区
						if (scenics.size() == 0) {
							scenics = dbDao.query(TScenicEntity.class,
									Cnd.where("cityId", "=", form.getGoArrivedCity()), null);
						}
						int scenicindex = random.nextInt(scenics.size());
						TScenicEntity scenic = scenics.get(scenicindex);
						scenics.remove(scenic);
						travelplan.setScenic(scenic.getName());
					}
					travelplans.add(travelplan);
				}
			} else {
				int totalstyle = subday / 2;
				//intArray为所有有景点的城市并且出去东北六县的Id组成的数组
				int[] intArray = generrateCityArray();
				intArray = getCitysArray(intArray, form.getGoArrivedCity(), form.getReturnDepartureCity());
				//randomArray为获取的不重复随机数
				if (intArray.length < totalstyle) {
					result.put("message", "没有更多的城市");
					return result;
				} else {
					if (totalstyle == 0) {
						totalstyle = 1;
					}
					randomArray = getRandomArray(intArray, totalstyle);
				}

				for (int i = 2; i < daysBetween - 1; i++) {

					int firstcityid = 0;
					int lastcityid = 0;
					if (threeCityid == form.getGoArrivedCity()) {
						firstcityid = randomArray[((i - 2) / 2)];
						if (i >= 4) {
							lastcityid = randomArray[((i - 4) / 2)];
						}
					} else {
						if (i >= 3) {
							firstcityid = randomArray[((i - 3) / 2)];
						}
						if (i >= 5) {
							lastcityid = randomArray[((i - 5) / 2)];
						}
					}

					TCityEntity fcity = null;
					List<THotelEntity> fhotels = null;
					List<TScenicEntity> fscenics = null;
					int fhotelindex = 0;
					if (firstcityid != 0) {
						fcity = dbDao.fetch(TCityEntity.class, firstcityid);
						fhotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", firstcityid), null);
						if (fhotels.size() == 0) {
							result.put("message", "没有更多的酒店");
							return result;
						}
						fscenics = dbDao.query(TScenicEntity.class, Cnd.where("cityId", "=", firstcityid), null);
						fhotelindex = random.nextInt(fhotels.size());
					}
					TCityEntity lcity = null;
					if (lastcityid != 0) {
						lcity = dbDao.fetch(TCityEntity.class, lastcityid);
					}

					//第三天
					if (i < 4) {
						TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
						if (threeCityid == form.getGoArrivedCity()) {
							travelplan.setCityId(firstcityid);
							travelplan.setCityName(fcity.getCity());
						} else {
							if (i == 2) {
								travelplan.setCityId(threeCityid);
								travelplan.setCityName(threeCity.getCity());
							} else {
								travelplan.setCityId(firstcityid);
								travelplan.setCityName(fcity.getCity());
							}
						}
						//酒店和航班取返程即第二行的出发城市
						if (i == 2) {
							//酒店
							if (threeCityid == form.getGoArrivedCity()) {
								THotelEntity hotel = fhotels.get(fhotelindex);
								travelplan.setHotel(hotel.getId());
							} else {
								THotelEntity hotel = threeHotels.get(threehotel);
								travelplan.setHotel(hotel.getId());
							}

							//景区
							if (form.getGoArrivedCity() != threeCityid) {
								String countryAirline = countryAirline(form.getGoArrivedCity(), threeCityid, 2);
								int nextInt = random.nextInt(threeScenics.size());
								TScenicEntity scenic = threeScenics.get(nextInt);
								countryAirline = countryAirline + "。" + scenic.getName();
								travelplan.setScenic(countryAirline);
							} else {
								String countryAirline = countryAirline(form.getGoArrivedCity(), firstcityid, 2);
								travelplan.setScenic(countryAirline);
							}
						}
						if (i == 3) {
							//景区
							if (form.getGoArrivedCity() != threeCityid) {
								String countryAirline = countryAirline(threeCityid, firstcityid, 2);
								travelplan.setScenic(countryAirline);

							} else {
								int scenicindex = random.nextInt(fscenics.size());
								TScenicEntity scenic = fscenics.get(scenicindex);
								fscenics.remove(scenic);
								travelplan.setScenic(scenic.getName());
							}

							//酒店
							if (form.getGoArrivedCity() != threeCityid) {
								THotelEntity hotel = fhotels.get(fhotelindex);
								travelplan.setHotel(hotel.getId());
							} else {
								THotelEntity hotel = fhotels.get(fhotelindex);
								travelplan.setHotel(hotel.getId());
							}
						}

						travelplan.setDay(String.valueOf(i + 1));
						travelplan.setOrderId(orderjpid);
						travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
						travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
						travelplan.setCreateTime(new Date());
						travelplans.add(travelplan);
					} else {
						TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
						travelplan.setCityId(fcity.getId());
						travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
						travelplan.setDay(String.valueOf(i + 1));
						travelplan.setOrderId(orderjpid);
						travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
						travelplan.setCityName(fcity.getCity());
						travelplan.setCreateTime(new Date());
						//酒店和航班取返程即第二行的出发城市
						//酒店
						if (i % 2 == 1) {
							THotelEntity hotel = fhotels.get(fhotelindex);
							travelplan.setHotel(hotel.getId());
							//酒店历史信息
							//				travelPlanHis.setHotel(hotel.getName());
						}
						if (i % 2 == 1) {
							if (i == 3) {
								String countryAirline = countryAirline(threeCityid, firstcityid, 2);
								travelplan.setScenic(countryAirline);
							} else {
								String countryAirline = countryAirline(lastcityid, firstcityid, 2);
								travelplan.setScenic(countryAirline);
							}
						} else {
							//景区
							if (fscenics.size() == 0) {//如果随机完所有的，则重新查一次
								fscenics = dbDao
										.query(TScenicEntity.class, Cnd.where("cityId", "=", firstcityid), null);
							}
							int scenicindex = random.nextInt(fscenics.size());
							TScenicEntity scenic = fscenics.get(scenicindex);
							fscenics.remove(scenic);
							travelplan.setScenic(scenic.getName());
						}
						travelplans.add(travelplan);
					}
				}

				//最后两天
				for (int i = daysBetween - 1; i <= daysBetween; i++) {
					TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
					//			TOrderTravelplanHisJpEntity travelPlanHis = new TOrderTravelplanHisJpEntity();
					travelplan.setCityId(form.getReturnDepartureCity());
					travelplan.setDay(String.valueOf(i + 1));
					travelplan.setOrderId(orderjpid);
					travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
					travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
					travelplan.setCityName(returngoCity.getCity());
					travelplan.setCreateTime(new Date());
					//酒店和航班取返程即第二行的出发城市
					//酒店
					if (i != daysBetween) {
						THotelEntity hotel = lasthotels.get(lasthotelindex);
						travelplan.setHotel(hotel.getId());
						//酒店历史信息
						//				travelPlanHis.setHotel(hotel.getName());
					}
					if (i == daysBetween) {
						travelplan.setScenic(lastday);
					} else if (i == daysBetween - 3) {
						if (daysBetween == 5) {//如果是5的话，则说明中间没有随机城市
							if (form.getGoArrivedCity() == form.getReturnDepartureCity()) {
								if (scenics.size() == 0) {
									scenics = dbDao.query(TScenicEntity.class,
											Cnd.where("cityId", "=", form.getGoArrivedCity()), null);
								}
								int scenicindex = random.nextInt(scenics.size());
								TScenicEntity scenic = scenics.get(scenicindex);
								scenics.remove(scenic);
								travelplan.setScenic(scenic.getName());
							} else {
								String countryAirline = countryAirline(form.getGoArrivedCity(),
										form.getReturnDepartureCity(), 2);
								travelplan.setScenic(countryAirline);
							}

						} else {
							String countryAirline = countryAirline(randomArray[totalstyle - 1],
									form.getReturnDepartureCity(), 2);
							travelplan.setScenic(countryAirline);
						}
					} else {
						if (daysBetween == 4) {
							if (i == 3) {
								String countryAirline = countryAirline(threeCityid, form.getReturnDepartureCity(), 2);
								int nextInt = random.nextInt(lastscenics.size());
								TScenicEntity scenic = lastscenics.get(nextInt);
								countryAirline = countryAirline + "。" + scenic.getName();
								travelplan.setScenic(countryAirline);
							}
						} else {
							//景区
							String countryAirline = countryAirline(randomArray[totalstyle - 1],
									form.getReturnDepartureCity(), 2);
							int nextInt = random.nextInt(lastscenics.size());
							TScenicEntity scenic = lastscenics.get(nextInt);
							countryAirline = countryAirline + "。" + scenic.getName();
							travelplan.setScenic(countryAirline);

						}
					}
					travelplans.add(travelplan);
				}
			}
		}

		List<TOrderTravelplanJpEntity> before = dbDao.query(TOrderTravelplanJpEntity.class,
				Cnd.where("orderid", "=", orderjpid), null);
		//更新行程安排
		dbDao.updateRelations(before, travelplans);
		//更新历史行程安排
		//		dbDao.updateRelations(beforeHis, travelplansHis);
		result.put("status", "success");
		result.put("orderid", orderjpid);
		result.put("data", getTravelPlanByOrderId(orderjpid));
		result.put("orderjpid", orderjpid);
		return result;
	}*/

	/**
	 * 查询第一天，最后一天喝返回时的出发城市
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Map<String, Object> getFirstdayAndLastday(GenerrateTravelForm form) {
		Map<String, Object> result = Maps.newHashMap();
		//返回时的出发城市
		TCityEntity returngoCity = null;
		//出发城市
		TCityEntity goCity = null;
		//省份
		String province = "";
		//出发航班
		String goFlightNum = "";
		//返回航班
		String returnFlightNum = "";
		//第一天
		String firstday = "";
		//最后一天
		String lastday = "";
		if (form.getCityid() > 2) {//重庆
			//出发城市
			int gotransferdeparturecity = 0;
			if (!Util.isEmpty(form.getNewgodeparturecity())) {//第一行出发城市不为空，说明为转机
				gotransferdeparturecity = form.getNewgodeparturecity();
			} else {
				gotransferdeparturecity = form.getGotransferdeparturecity();
			}
			goCity = dbDao.fetch(TCityEntity.class, gotransferdeparturecity);
			province = goCity.getProvince();
			if (province.endsWith("省") || province.endsWith("市")) {
				province = province.substring(0, province.length() - 1);
			}
			if (province.length() > 3 && province.endsWith("自治区")) {
				province = province.substring(0, province.length() - 3);
			}
			//返回时的出发城市
			Integer returnDepartureCity = form.getNewreturndeparturecity();
			returngoCity = dbDao.fetch(TCityEntity.class, returnDepartureCity.longValue());
			//出发航班
			if (!Util.isEmpty(form.getGotransferflightnum())) {//有第一行航班,说明是转机
				String gotransferflightnum = form.getGotransferflightnum();
				String newgoflightnum = form.getNewgoflightnum();

				StringBuffer stringBuilder = new StringBuffer(gotransferflightnum.substring(
						gotransferflightnum.indexOf(" ") + 1, gotransferflightnum.lastIndexOf(" ")));
				stringBuilder.append("//"
						+ newgoflightnum.substring(newgoflightnum.indexOf(" ") + 1, newgoflightnum.lastIndexOf(" ")));

				//第一天
				firstday = " "
						+ province
						+ "から"
						+ stringBuilder.toString()
						+ "便にて"
						+ newgoflightnum.substring(newgoflightnum.indexOf("-", newgoflightnum.lastIndexOf("-")) + 1,
								newgoflightnum.indexOf(" ", newgoflightnum.indexOf(" "))) + "へ" + "\n 到着後、ホテルへ";
			} else {
				String newgoflightnum = form.getNewgoflightnum();
				//第一天
				firstday = " "
						+ province
						+ "から"
						+ newgoflightnum.substring(newgoflightnum.indexOf(" ", newgoflightnum.indexOf(" ")) + 1,
								newgoflightnum.indexOf(" ", newgoflightnum.indexOf(" ") + 1))
						+ "便にて"
						+ newgoflightnum.substring(newgoflightnum.indexOf("-", newgoflightnum.lastIndexOf("-")) + 1,
								newgoflightnum.indexOf(" ", newgoflightnum.indexOf(" "))) + "へ" + "\n 到着後、ホテルへ";
			}

			if (!Util.isEmpty(form.getNewreturnflightnum())) {
				String returntransferflightnum = form.getReturntransferflightnum();
				String newreturnflightnum = form.getNewreturnflightnum();

				StringBuffer stringBuilder = new StringBuffer(returntransferflightnum.substring(
						returntransferflightnum.indexOf(" ") + 1, returntransferflightnum.lastIndexOf(" ")));
				stringBuilder.append("//"
						+ newreturnflightnum.substring(newreturnflightnum.indexOf(" ") + 1,
								newreturnflightnum.lastIndexOf(" ")));

				//最后一天
				lastday = " "
						+ returntransferflightnum.substring(0,
								returntransferflightnum.indexOf("-", returntransferflightnum.indexOf("-"))) + "から"
						+ stringBuilder.toString() + "便にて帰国";
			} else {
				String returntransferflightnum = form.getReturntransferflightnum();
				//最后一天
				lastday = " "
						+ returntransferflightnum.substring(0,
								returntransferflightnum.indexOf("-", returntransferflightnum.indexOf("-")))
						+ "から"
						+ returntransferflightnum.substring(
								returntransferflightnum.indexOf(" ", returntransferflightnum.indexOf(" ")) + 1,
								returntransferflightnum.indexOf(" ", returntransferflightnum.indexOf(" ") + 1))
						+ "便にて帰国";
			}

		} else {//北京,上海
			//返回时的出发城市
			Integer returnDepartureCity = form.getReturnDepartureCity();
			returngoCity = dbDao.fetch(TCityEntity.class, returnDepartureCity.longValue());
			//出发城市
			Integer goDepartureCity = form.getGoDepartureCity();
			goCity = dbDao.fetch(TCityEntity.class, goDepartureCity.longValue());
			province = goCity.getProvince();
			if (province.endsWith("省") || province.endsWith("市")) {
				province = province.substring(0, province.length() - 1);
			}
			if (province.length() > 3 && province.endsWith("自治区")) {
				province = province.substring(0, province.length() - 3);
			}
			//出发航班
			goFlightNum = form.getGoFlightNum();
			//返回航班
			returnFlightNum = form.getReturnFlightNum();
			//第一天
			firstday = " "
					+ province
					+ "から"
					+ goFlightNum.substring(goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")) + 1,
							goFlightNum.indexOf(" ", goFlightNum.indexOf(" ") + 1))
					+ "便にて"
					+ goFlightNum.substring(goFlightNum.indexOf("-", goFlightNum.lastIndexOf("-")) + 1,
							goFlightNum.indexOf(" ", goFlightNum.indexOf(" "))) + "へ" + "\n 到着後、ホテルへ";

			//最后一天
			lastday = " "
					+ returnFlightNum.substring(0, returnFlightNum.indexOf("-", returnFlightNum.indexOf("-")))
					+ "から"
					+ returnFlightNum.substring(returnFlightNum.indexOf(" ", returnFlightNum.indexOf(" ")) + 1,
							returnFlightNum.indexOf(" ", returnFlightNum.indexOf(" ") + 1)) + "便にて帰国";
		}

		result.put("firstday", firstday);
		result.put("lastday", lastday);
		result.put("returngocity", returngoCity);
		return result;
	}

	public Object generateTravelPlan(HttpServletRequest request, GenerrateTravelForm form) {
		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Map<String, Object> result = Maps.newHashMap();

		result = validateIsfull(result, form);
		if (!Util.isEmpty(result.get("message"))) {
			return result;
		}
		//签证类型
		Integer visatype = form.getVisatype();
		int cityid = 0;
		int lastcityid = 0;
		if (form.getCityid() > 2) {//重庆
			cityid = form.getNewgoarrivedcity();
			lastcityid = form.getNewreturndeparturecity();
		} else {//北京，上海
			cityid = form.getGoArrivedCity();
			lastcityid = form.getReturnDepartureCity();
		}
		int daysBetween = DateUtil.daysBetween(form.getGoDate(), form.getReturnDate());
		if (visatype == 6 || visatype == 1 || visatype == 14 || visatype == 2 || visatype == 7) {//除去东北六县
			//如果去程抵达城市和返回出发城市一样，则什么都不需要分
			if (lastcityid != cityid) {
				if (daysBetween < 3) {
					result.put("message", "停留天数必须大于3天");
					return result;
				}
			} else {
				if (daysBetween < 1) {
					result.put("message", "停留天数必须大于1天");
					return result;
				}
			}
		} else {
			if (daysBetween < 4) {
				result.put("message", "停留天数必须大于4天");
				return result;
			}
		}

		long first = System.currentTimeMillis();

		/*//随机出景点和方位list
		Map<String, Object> reasonable = getReasonabletripplan(cityid, daysBetween);
		String message = (String) reasonable.get("message");
		if (!Util.isEmpty(message)) {
			result.put("message", message);
			return result;
		}

		Map<String, Object> reasonabletripplan = (Map<String, Object>) reasonable.get("getsomeCount");

		//景点
		ArrayList<String> scenicsList = (ArrayList<String>) reasonabletripplan.get("scenics");
		System.out.println("scenics:" + scenicsList);
		//酒店
		ArrayList<Integer> hotelsList = (ArrayList<Integer>) reasonabletripplan.get("hotels");
		System.out.println("hotels:" + hotelsList);*/

		long last = System.currentTimeMillis();
		System.out.println("所用时间：" + (last - first) + "ms");

		Map<String, Object> firstdayAndLastday = getFirstdayAndLastday(form);
		String firstday = (String) firstdayAndLastday.get("firstday");
		String lastday = (String) firstdayAndLastday.get("lastday");
		TCityEntity returngoCity = (TCityEntity) firstdayAndLastday.get("returngocity");
		//获取前两天城市
		TCityEntity city = dbDao.fetch(TCityEntity.class, cityid);
		//获取前两天城市所有的酒店
		List<THotelEntity> hotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", cityid), null);
		//获取前两天城市所有的景区
		List<TScenicEntity> scenics = dbDao.query(TScenicEntity.class, Cnd.where("cityId", "=", cityid), null);
		if (scenics.size() < 2) {
			result.put("message", "没有更多的景区");
			return result;
		}
		//获取后两天酒店
		List<THotelEntity> lasthotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", lastcityid), null);
		//获取后两天景区
		List<TScenicEntity> lastscenics = dbDao.query(TScenicEntity.class, Cnd.where("cityId", "=", lastcityid), null);
		if (lastscenics.size() < 2) {
			result.put("message", "没有更多的景区");
			return result;
		}
		Integer orderjpid = form.getOrderid();
		Integer orderid = null;
		if (Util.isEmpty(orderjpid)) {
			//如果订单不存在，创建订单
			Map<String, Integer> generrateorder = generrateorder(loginUser, loginCompany);
			orderid = generrateorder.get("orderid");
			orderjpid = generrateorder.get("orderjpid");
		} else {
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
			orderid = orderjp.getOrderId();
		}
		//需要生成的travelplan
		List<TOrderTravelplanJpEntity> travelplans = Lists.newArrayList();
		//在一个城市只住一家酒店
		Random random = new Random();
		int hotelindex = random.nextInt(hotels.size());
		int lasthotelindex = random.nextInt(lasthotels.size());

		ArrayList<String> scenicsList = new ArrayList();
		ArrayList<Integer> hotelsList = new ArrayList();

		if (visatype == 6 || visatype == 1 || visatype == 14 || visatype == 2 || visatype == 7) {//除去东北六县

			//如果去程抵达城市和返回出发城市一样，则什么都不需要分
			if (lastcityid == cityid) {

				//冲绳和北海道时，按方位随机
				if (cityid == 77 || cityid == 86) {
					//随机出景点和方位list
					Map<String, Object> reasonable = getReasonabletripplan(cityid, daysBetween);
					String message = (String) reasonable.get("message");
					if (!Util.isEmpty(message)) {
						result.put("message", message);
						return result;
					}

					Map<String, Object> reasonabletripplan = (Map<String, Object>) reasonable.get("getsomeCount");

					//景点
					scenicsList = (ArrayList<String>) reasonabletripplan.get("scenics");
					System.out.println("scenics:" + scenicsList);
					//酒店
					hotelsList = (ArrayList<Integer>) reasonabletripplan.get("hotels");
					System.out.println("hotels:" + hotelsList);
				}

				for (int i = 0; i <= daysBetween; i++) {
					TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
					travelplan.setCityId(cityid);
					travelplan.setDay(String.valueOf(i + 1));
					travelplan.setOrderId(orderjpid);
					travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
					travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
					travelplan.setCityName(city.getCity());
					travelplan.setCreateTime(new Date());
					//酒店
					if (i != daysBetween) {

						if (cityid == 77 || cityid == 86) {
							travelplan.setHotel(hotelsList.get(i));
						} else {
							THotelEntity hotel = hotels.get(hotelindex);
							travelplan.setHotel(hotel.getId());

						}
					}
					if (i > 0 && i != daysBetween) {
						//景区
						if (scenics.size() == 0) {
							scenics = dbDao.query(TScenicEntity.class,
									Cnd.where("cityId", "=", form.getGoArrivedCity()), null);
						}

						if (cityid == 77 || cityid == 86) {
							travelplan.setScenic(scenicsList.get(i));
						} else {
							int scenicindex = random.nextInt(scenics.size());
							TScenicEntity scenic = scenics.get(scenicindex);
							scenics.remove(scenic);
							travelplan.setScenic(scenic.getName());

						}
					}
					if (i == 0) {//第一天
						travelplan.setScenic(firstday);
					}
					if (i == daysBetween) {//最后一天
						travelplan.setScenic(lastday);
					}
					travelplans.add(travelplan);
				}
			} else {
				//为什么要<=，因为最后一天也要玩
				//前两天

				//冲绳和北海道时，按方位随机
				if (cityid == 77 || cityid == 86) {
					//随机出景点和方位list
					Map<String, Object> reasonable = getReasonabletripplan(cityid, 2);
					String message = (String) reasonable.get("message");
					if (!Util.isEmpty(message)) {
						result.put("message", message);
						return result;
					}

					Map<String, Object> reasonabletripplan = (Map<String, Object>) reasonable.get("getsomeCount");

					//景点
					scenicsList = (ArrayList<String>) reasonabletripplan.get("scenics");
					System.out.println("scenics:" + scenicsList);
					//酒店
					hotelsList = (ArrayList<Integer>) reasonabletripplan.get("hotels");
					System.out.println("hotels:" + hotelsList);
				}

				int k = 0;
				for (int i = 0; i < 2; i++) {
					TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
					travelplan.setCityId(cityid);
					travelplan.setCityName(city.getCity());
					travelplan.setDay(String.valueOf(i + 1));
					travelplan.setOrderId(orderjpid);
					travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
					travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
					travelplan.setCreateTime(new Date());

					if (i != daysBetween) {

						if (cityid == 77 || cityid == 86) {
							travelplan.setHotel(hotelsList.get(k));
						} else {
							THotelEntity hotel = hotels.get(hotelindex);
							travelplan.setHotel(hotel.getId());

						}
					}
					if (i == 0) {
						travelplan.setScenic(firstday);
					}
					if (i > 0 && i != daysBetween) {
						//景区

						if (cityid == 77 || cityid == 86) {
							travelplan.setScenic(scenicsList.get(k));
						} else {
							int scenicindex = random.nextInt(scenics.size());
							TScenicEntity scenic = scenics.get(scenicindex);
							scenics.remove(scenic);
							travelplan.setScenic(scenic.getName());

						}
					}
					travelplans.add(travelplan);
					k++;
				}
				//除去开始的前两天和最后两天天，如果天数为2的倍数，则中间多2的倍数个随机城市，有余数则最后变为3天
				int subday = 0;
				if (daysBetween % 2 == 0) {
					subday = daysBetween - 4;
				} else {
					subday = daysBetween - 3;
				}
				Map<String, List<Integer>> citysandDates = null;
				List<Integer> citysList = null;
				List<Integer> datesList = null;
				//最多随机几个城市
				int totalstyle = subday / 2;
				//获取指定城市的ID数组
				int[] intArray = generrateCityArray();
				//获取去除抵达城市和返回城市的指定城市的ID数组
				intArray = getCitysArray(intArray, cityid, lastcityid);
				//randomArray为获取的不重复随机数
				int j = 0;
				if (subday != 0) {
					//获取随机城市和天数
					citysandDates = getRandomCity(intArray, totalstyle, subday);
					datesList = citysandDates.get("days");
					citysList = citysandDates.get("citys");

					for (int i = 2; i < 2 + subday; i++) {
						TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
						if (i == getNum(datesList, j) + 2) {
							j++;
							if (i == 2) {
								String countryAirline = countryAirline(cityid, citysList.get(j - 1), 1);
								travelplan.setScenic(countryAirline);
							} else {
								String countryAirline = countryAirline(citysList.get(j - 2), citysList.get(j - 1), 1);
								travelplan.setScenic(countryAirline);
							}

							List<THotelEntity> nowhotels = dbDao.query(THotelEntity.class,
									Cnd.where("cityId", "=", citysList.get(j - 1)), null);
							int scenicindex = random.nextInt(nowhotels.size());
							THotelEntity hotel = nowhotels.get(scenicindex);
							travelplan.setHotel(hotel.getId());

						} else {
							List<TScenicEntity> nowscenics = dbDao.query(TScenicEntity.class,
									Cnd.where("cityId", "=", citysList.get(j - 1)), null);
							int scenicindex = random.nextInt(nowscenics.size());
							TScenicEntity scenic = nowscenics.get(scenicindex);
							nowscenics.remove(scenic);
							travelplan.setScenic(scenic.getName());

						}
						TCityEntity nowcity = dbDao.fetch(TCityEntity.class, citysList.get(j - 1).longValue());
						travelplan.setCityId(citysList.get(j - 1));
						travelplan.setCityName(nowcity.getCity());
						travelplan.setDay(String.valueOf(i + 1));
						travelplan.setOrderId(orderjpid);
						travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
						travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
						travelplan.setCreateTime(new Date());
						travelplans.add(travelplan);
					}
				} else {//为0时说明是4天
					j++;
					citysList = new ArrayList<>();
					citysList.add(cityid);
				}
				if (daysBetween % 2 == 0) {

					//冲绳和北海道时，按方位随机
					if (lastcityid == 77 || lastcityid == 86) {
						//随机出景点和方位list
						Map<String, Object> reasonable = getReasonabletripplan(lastcityid, 3);
						String message = (String) reasonable.get("message");
						if (!Util.isEmpty(message)) {
							result.put("message", message);
							return result;
						}

						Map<String, Object> reasonabletripplan = (Map<String, Object>) reasonable.get("getsomeCount");

						//景点
						scenicsList = (ArrayList<String>) reasonabletripplan.get("scenics");
						System.out.println("scenics:" + scenicsList);
						//酒店
						hotelsList = (ArrayList<Integer>) reasonabletripplan.get("hotels");
						System.out.println("hotels:" + hotelsList);
					}

					//最后三天
					int k1 = 0;
					for (int i = daysBetween - 2; i <= daysBetween; i++) {
						TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
						travelplan.setCityId(lastcityid);
						travelplan.setDay(String.valueOf(i + 1));
						travelplan.setOrderId(orderjpid);
						travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
						travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
						travelplan.setCityName(returngoCity.getCity());
						travelplan.setCreateTime(new Date());
						//酒店和航班取返程即第二行的出发城市
						if (i != daysBetween) {

							if (lastcityid == 77 || lastcityid == 86) {
								travelplan.setHotel(hotelsList.get(k1));
							} else {
								THotelEntity hotel = lasthotels.get(lasthotelindex);
								travelplan.setHotel(hotel.getId());

							}
						}
						if (i == daysBetween) {
							travelplan.setScenic(lastday);
						} else if (i == daysBetween - 2) {
							if (daysBetween == 4) {
								String countryAirline = countryAirline(cityid, lastcityid, 2);
								travelplan.setScenic(countryAirline);
							} else {
								String countryAirline = countryAirline(citysList.get(j - 1), lastcityid, 2);
								travelplan.setScenic(countryAirline);
							}
						} else {
							//景区

							if (lastcityid == 77 || lastcityid == 86) {
								travelplan.setScenic(scenicsList.get(k1));
							} else {
								if (scenics.size() == 0) {
									scenics = dbDao.query(TScenicEntity.class, Cnd.where("cityId", "=", cityid), null);
								}
								int scenicindex = random.nextInt(scenics.size());
								TScenicEntity scenic = scenics.get(scenicindex);
								scenics.remove(scenic);
								travelplan.setScenic(scenic.getName());

							}
						}
						travelplans.add(travelplan);
						k1++;
					}
				} else {
					//最后两天

					//冲绳和北海道时，按方位随机
					if (lastcityid == 77 || lastcityid == 86) {
						//随机出景点和方位list
						Map<String, Object> reasonable = getReasonabletripplan(lastcityid, 2);
						String message = (String) reasonable.get("message");
						if (!Util.isEmpty(message)) {
							result.put("message", message);
							return result;
						}

						Map<String, Object> reasonabletripplan = (Map<String, Object>) reasonable.get("getsomeCount");

						//景点
						scenicsList = (ArrayList<String>) reasonabletripplan.get("scenics");
						System.out.println("scenics:" + scenicsList);
						//酒店
						hotelsList = (ArrayList<Integer>) reasonabletripplan.get("hotels");
						System.out.println("hotels:" + hotelsList);
					}

					int k1 = 0;
					for (int i = daysBetween - 1; i <= daysBetween; i++) {
						TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
						travelplan.setCityId(lastcityid);
						travelplan.setDay(String.valueOf(i + 1));
						travelplan.setOrderId(orderjpid);
						travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
						travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
						travelplan.setCityName(returngoCity.getCity());
						travelplan.setCreateTime(new Date());
						//酒店和航班取返程即第二行的出发城市
						//酒店
						if (i != daysBetween) {

							if (lastcityid == 77 || lastcityid == 86) {
								travelplan.setHotel(hotelsList.get(k1));
							} else {
								THotelEntity hotel = lasthotels.get(lasthotelindex);
								travelplan.setHotel(hotel.getId());

							}
						}
						if (i == daysBetween) {
							travelplan.setScenic(lastday);
						} else {
							String countryAirline = countryAirline(citysList.get(j - 1), lastcityid, 2);
							if (lastcityid == 77 || lastcityid == 86) {
								countryAirline = countryAirline + "。" + scenicsList.get(k1);
							} else {

								int nextInt = random.nextInt(lastscenics.size());
								countryAirline = countryAirline + "。" + lastscenics.get(nextInt).getName();
							}

							travelplan.setScenic(countryAirline);
						}
						travelplans.add(travelplan);
						k1++;
					}
				}
			}
		} else {//东北六县第三天要去对应的签证类型城市，不管去程抵达城市和返程出发城市是否一样，中间都随机

			//冲绳和北海道时，按方位随机
			if (cityid == 77 || cityid == 86) {
				//随机出景点和方位list
				Map<String, Object> reasonable = getReasonabletripplan(cityid, 2);
				String message = (String) reasonable.get("message");
				if (!Util.isEmpty(message)) {
					result.put("message", message);
					return result;
				}

				Map<String, Object> reasonabletripplan = (Map<String, Object>) reasonable.get("getsomeCount");

				//景点
				scenicsList = (ArrayList<String>) reasonabletripplan.get("scenics");
				System.out.println("scenics:" + scenicsList);
				//酒店
				hotelsList = (ArrayList<Integer>) reasonabletripplan.get("hotels");
				System.out.println("hotels:" + hotelsList);
			}

			//前两天
			int k = 0;
			for (int i = 0; i < 2; i++) {
				TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
				travelplan.setCityId(cityid);
				travelplan.setCityName(city.getCity());
				travelplan.setDay(String.valueOf(i + 1));
				travelplan.setOrderId(orderjpid);
				travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
				travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
				travelplan.setCreateTime(new Date());

				if (i != daysBetween) {

					if (cityid == 77 || cityid == 86) {
						travelplan.setHotel(hotelsList.get(k));
					} else {
						THotelEntity hotel = hotels.get(hotelindex);
						travelplan.setHotel(hotel.getId());

					}
				}
				if (i == 0) {
					travelplan.setScenic(firstday);
				}
				if (i > 0 && i != daysBetween) {
					//景区

					if (cityid == 77 || cityid == 86) {
						travelplan.setScenic(scenicsList.get(i));
					} else {
						int scenicindex = random.nextInt(scenics.size());
						TScenicEntity scenic = scenics.get(scenicindex);
						scenics.remove(scenic);
						travelplan.setScenic(scenic.getName());

					}
				}
				travelplans.add(travelplan);
				k++;
			}

			//第三天去的城市签证类型城市
			int threeCityid = thirddayCity(visatype);

			TCityEntity threeCity = dbDao.fetch(TCityEntity.class, threeCityid);
			List<THotelEntity> threeHotels = dbDao.query(THotelEntity.class, Cnd.where("cityId", "=", threeCityid),
					null);
			if (threeHotels.size() < 1) {
				result.put("message", "没有更多的酒店");
				return result;
			}
			List<TScenicEntity> threeScenics = dbDao.query(TScenicEntity.class, Cnd.where("cityId", "=", threeCityid),
					null);
			if (threeScenics.size() < 1) {
				result.put("message", "没有更多的景点");
				return result;
			}
			int threehotel = random.nextInt(threeHotels.size());
			//第三天
			TOrderTravelplanJpEntity thravelplan = new TOrderTravelplanJpEntity();
			thravelplan.setCityId(threeCityid);
			thravelplan.setCityName(threeCity.getCity());
			thravelplan.setDay(String.valueOf(3));
			thravelplan.setOrderId(orderjpid);
			thravelplan.setOutDate(DateUtil.addDay(form.getGoDate(), 2));
			thravelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
			thravelplan.setCreateTime(new Date());
			//酒店
			THotelEntity thotel = threeHotels.get(threehotel);
			thravelplan.setHotel(thotel.getId());
			//景区
			String threeScenic = countryAirline(cityid, threeCityid, 1);
			int threeScenicIndex = random.nextInt(threeScenics.size());
			TScenicEntity tScenicEntity = threeScenics.get(threeScenicIndex);
			threeScenic = threeScenic + "。" + tScenicEntity.getName();
			thravelplan.setScenic(threeScenic);
			travelplans.add(thravelplan);

			//除去开始的前两天和最后两天天，如果天数为2的倍数，则中间多2的倍数个随机城市，有余数则最后变为3天
			int subday = 0;
			if (daysBetween % 2 == 0) {
				subday = daysBetween - 4;
			} else {
				subday = daysBetween - 5;
			}
			Map<String, List<Integer>> citysandDates = null;
			List<Integer> citysList = null;
			List<Integer> datesList = null;
			//最多随机几个城市
			int totalstyle = subday / 2;
			//intArray为所有有景点的城市并且出去东北六县的Id组成的数组
			int[] intArray = generrateCityArray();
			intArray = getCitysArray(intArray, cityid, lastcityid);
			//randomArray为获取的不重复随机数
			//随机城市和天数
			int j = 0;
			if (subday != 0) {
				citysandDates = getRandomCity(intArray, totalstyle, subday);
				datesList = citysandDates.get("days");
				citysList = citysandDates.get("citys");
				for (int i = 3; i < 3 + subday; i++) {
					TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
					if (i == getNum(datesList, j) + 3) {
						j++;
						if (i == 3) {
							String countryAirline = countryAirline(threeCityid, citysList.get(j - 1), 1);
							travelplan.setScenic(countryAirline);
						} else {
							String countryAirline = countryAirline(citysList.get(j - 2), citysList.get(j - 1), 1);
							travelplan.setScenic(countryAirline);
						}
						List<THotelEntity> nowhotels = dbDao.query(THotelEntity.class,
								Cnd.where("cityId", "=", citysList.get(j - 1)), null);
						int nowhotelindex = random.nextInt(nowhotels.size());
						THotelEntity hotel = nowhotels.get(nowhotelindex);
						travelplan.setHotel(hotel.getId());
					} else {
						List<TScenicEntity> nowscenics = dbDao.query(TScenicEntity.class,
								Cnd.where("cityId", "=", citysList.get(j - 1)), null);
						int scenicindex = random.nextInt(nowscenics.size());
						TScenicEntity scenic = nowscenics.get(scenicindex);
						nowscenics.remove(scenic);
						travelplan.setScenic(scenic.getName());
					}

					TCityEntity nowcity = dbDao.fetch(TCityEntity.class, citysList.get(j - 1).longValue());
					travelplan.setCityId(citysList.get(j - 1));
					travelplan.setCityName(nowcity.getCity());
					travelplan.setDay(String.valueOf(i + 1));
					travelplan.setOrderId(orderjpid);
					travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
					travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
					travelplan.setCreateTime(new Date());
					travelplans.add(travelplan);
				}
			}

			if (daysBetween % 2 == 1) {

				//冲绳和北海道时，按方位随机
				if (lastcityid == 77 || lastcityid == 86) {
					//随机出景点和方位list
					Map<String, Object> reasonable = getReasonabletripplan(lastcityid, 3);
					String message = (String) reasonable.get("message");
					if (!Util.isEmpty(message)) {
						result.put("message", message);
						return result;
					}

					Map<String, Object> reasonabletripplan = (Map<String, Object>) reasonable.get("getsomeCount");

					//景点
					scenicsList = (ArrayList<String>) reasonabletripplan.get("scenics");
					System.out.println("scenics:" + scenicsList);
					//酒店
					hotelsList = (ArrayList<Integer>) reasonabletripplan.get("hotels");
					System.out.println("hotels:" + hotelsList);
				}

				//最后三天
				int k1 = 0;
				for (int i = daysBetween - 2; i <= daysBetween; i++) {
					TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
					travelplan.setCityId(lastcityid);
					travelplan.setDay(String.valueOf(i + 1));
					travelplan.setOrderId(orderjpid);
					travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
					travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
					travelplan.setCityName(returngoCity.getCity());
					travelplan.setCreateTime(new Date());
					//酒店和航班取返程即第二行的出发城市
					if (i != daysBetween) {

						if (lastcityid == 77 || lastcityid == 86) {
							travelplan.setHotel(hotelsList.get(k1));
						} else {
							THotelEntity hotel = lasthotels.get(lasthotelindex);
							travelplan.setHotel(hotel.getId());

						}
					}
					if (i == daysBetween) {
						travelplan.setScenic(lastday);
					} else if (i == daysBetween - 2) {
						if (daysBetween == 5) {
							String countryAirline = countryAirline(threeCityid, lastcityid, 2);
							travelplan.setScenic(countryAirline);

						} else {
							String countryAirline = countryAirline(citysList.get(j - 1), lastcityid, 2);
							travelplan.setScenic(countryAirline);
						}
					} else {
						//景区

						if (lastcityid == 77 || lastcityid == 86) {
							travelplan.setScenic(scenicsList.get(k1));
						} else {
							if (scenics.size() == 0) {
								scenics = dbDao.query(TScenicEntity.class, Cnd.where("cityId", "=", cityid), null);
							}
							int scenicindex = random.nextInt(scenics.size());
							TScenicEntity scenic = scenics.get(scenicindex);
							scenics.remove(scenic);
							travelplan.setScenic(scenic.getName());

						}
					}
					travelplans.add(travelplan);
					k1++;
				}
			} else {

				//冲绳和北海道时，按方位随机
				if (lastcityid == 77 || lastcityid == 86) {
					//随机出景点和方位list
					Map<String, Object> reasonable = getReasonabletripplan(lastcityid, 2);
					String message = (String) reasonable.get("message");
					if (!Util.isEmpty(message)) {
						result.put("message", message);
						return result;
					}

					Map<String, Object> reasonabletripplan = (Map<String, Object>) reasonable.get("getsomeCount");

					//景点
					scenicsList = (ArrayList<String>) reasonabletripplan.get("scenics");
					System.out.println("scenics:" + scenicsList);
					//酒店
					hotelsList = (ArrayList<Integer>) reasonabletripplan.get("hotels");
					System.out.println("hotels:" + hotelsList);
				}

				//最后两天
				int k1 = 0;
				for (int i = daysBetween - 1; i <= daysBetween; i++) {
					TOrderTravelplanJpEntity travelplan = new TOrderTravelplanJpEntity();
					travelplan.setCityId(lastcityid);
					travelplan.setDay(String.valueOf(i + 1));
					travelplan.setOrderId(orderjpid);
					travelplan.setIsupdatecity(IsYesOrNoEnum.NO.intKey());
					travelplan.setOutDate(DateUtil.addDay(form.getGoDate(), i));
					travelplan.setCityName(returngoCity.getCity());
					travelplan.setCreateTime(new Date());
					//酒店和航班取返程即第二行的出发城市
					if (i != daysBetween) {

						if (lastcityid == 77 || lastcityid == 86) {
							travelplan.setHotel(hotelsList.get(k1));
						} else {
							THotelEntity tHotelEntity = lasthotels.get(lasthotelindex);
							travelplan.setHotel(tHotelEntity.getId());

						}
					}
					if (i == daysBetween) {
						travelplan.setScenic(lastday);
					} else {
						if (daysBetween == 4) {
							String countryAirline = countryAirline(threeCityid, lastcityid, 2);
							if (lastcityid == 77 || lastcityid == 86) {
								countryAirline = countryAirline + "。" + scenicsList.get(k1);
							} else {
								int nextInt = random.nextInt(lastscenics.size());
								countryAirline = countryAirline + "。" + lastscenics.get(nextInt).getName();

							}
							travelplan.setScenic(countryAirline);
						} else {
							String countryAirline = countryAirline(citysList.get(j - 1), lastcityid, 2);
							if (lastcityid == 77 || lastcityid == 86) {
								int nextInt = random.nextInt(lastscenics.size());
								countryAirline = countryAirline + "。" + scenicsList.get(k1);
							} else {
								int nextInt = random.nextInt(lastscenics.size());
								countryAirline = countryAirline + "。" + lastscenics.get(nextInt).getName();
							}
							travelplan.setScenic(countryAirline);
						}
					}
					travelplans.add(travelplan);
					k1++;
				}
			}
		}

		List<TOrderTravelplanJpEntity> before = dbDao.query(TOrderTravelplanJpEntity.class,
				Cnd.where("orderid", "=", orderjpid), null);
		//更新行程安排
		dbDao.updateRelations(before, travelplans);

		//房间数

		/*TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
		if (Util.isEmpty(orderjp.getRoomcount())) {
			int roomCount = getRoomCount(orderjpid);
			orderjp.setRoomcount(roomCount);
			dbDao.update(orderjp, "roomcount");
		}*/

		result.put("status", "success");
		result.put("orderid", orderjpid);
		result.put("data", getTravelPlanByOrderId(orderjpid));
		result.put("orderjpid", orderjpid);
		return result;
	}

	public Map<String, Object> getReasonabletripplan(int cityid, int days) {
		Map<String, Object> result = Maps.newHashMap();

		List<Integer> list = new ArrayList<>();

		//查询所选城市的景区一共分几个方位,positionSize
		String scenicString = sqlManager.get("simpleJP_getScenicinfo");
		Sql scenicsql = Sqls.create(scenicString);
		scenicsql.setParam("cityid", cityid);
		List<Record> scenics = dbDao.query(scenicsql, null, null);
		int positionSize = scenics.size();

		ArrayList<ArrayList<String>> arrayList = new ArrayList();

		ArrayList<Integer> intList = new ArrayList();

		ArrayList<String> regionList = new ArrayList();
		//所选城市的景区最多可以看几天
		int scenicDays = 0;
		for (Record record : scenics) {

			regionList.add(record.getString("region"));

			int scenicCount = record.getInt("sceniccount");
			intList.add(scenicCount);
			String region = record.getString("region");
			String scenicString2 = sqlManager.get("simpleJP_getScenicnamebyregion");
			Sql scenicsql2 = Sqls.create(scenicString2);
			scenicsql2.setParam("cityid", cityid);
			scenicsql2.setParam("region", region);
			List<Record> scenicnames = dbDao.query(scenicsql2, null, null);

			ArrayList<String> strList = new ArrayList();
			for (Record record2 : scenicnames) {
				strList.add(record2.getString("name"));
			}
			arrayList.add(strList);

			//北海道时，每个方位最多呆3天
			/*if (cityid == 86) {
				if (scenicCount > 3) {
					scenicCount = 3;
				}
			}*/
			scenicDays += scenicCount;

		}
		System.out.println("days:" + days);
		System.out.println("scenicDays:" + scenicDays);
		String message = "";
		if (days > scenicDays) {
			message = "没有更多的景区";
			result.put("message", message);
			return result;
		}

		Map<String, Object> getsomeCount = getsomeCount(cityid, intList, days, arrayList, regionList);
		if (!Util.isEmpty(getsomeCount.get("message"))) {
			result.put("message", getsomeCount.get("message"));
			return result;
		}

		result.put("getsomeCount", getsomeCount);
		return result;
	}

	public Map<String, Object> getsomeCount(int cityid, ArrayList<Integer> intlist, int size,
			ArrayList<ArrayList<String>> arrayList4, ArrayList<String> regionList) {

		Map<String, Object> result = Maps.newHashMap();
		String message = "";

		try {
			//从所有方位中随机出用几个方位
			Random random2 = new Random();
			int nextInt = random2.nextInt(intlist.size()) + 1;
			System.out.println("1:" + nextInt);

			//随机出给定数组中的具体的哪几个方位的下标
			ArrayList<Integer> arrayList2 = new ArrayList();
			//冲绳机场所在方位(南)，放在第一个
			if (cityid == 77) {
				arrayList2.add(2);
			}
			//北海道机场所在方位(西),放在第一个
			if (cityid == 86) {
				arrayList2.add(3);
			}
			//所有方位都有
			if (nextInt == intlist.size()) {
				for (int i = 0; i < nextInt; i++) {
					if (cityid == 77) {
						if (i != 2) {
							arrayList2.add(i);
						}
					}
					if (cityid == 86) {
						if (i != 3) {
							arrayList2.add(i);
						}
					}
				}
			} else {
				for (int i = 0; i < nextInt - 1; i++) {

					//arrayList2.add(i);

					Random random3 = new Random();
					int nextInt2 = random3.nextInt(intlist.size());

					while (arrayList2.contains(nextInt2)) {
						nextInt2 = random3.nextInt(intlist.size());
					}

					arrayList2.add(nextInt2);

					/*if (!arrayList2.contains(nextInt2)) {
					arrayList2.add(nextInt2);
					}*/
				}
			}

			//判断下所选取的方位个数和随机出来的是否一致，如果不一致，重新随机
			/*if (arrayList2.size() != nextInt) {
				return getsomeCount(cityid, intlist, size, arrayList4, regionList);
			}*/

			//按从小到大顺序排序
			//Collections.sort(arrayList2);
			if (cityid == 86) {
				for (int i = 0; i < arrayList2.size(); i++) {
					//是否包含东，东在查询的List中排在第一位
					if (arrayList2.get(i) == 0) {
						//如果东不在最后一位,把东挪到最后一位
						if (i != arrayList2.size() - 1) {
							arrayList2.remove(i);
							arrayList2.add(arrayList2.size(), 0);
						}
					}
					//是否包含西南，东在查询的List中排在第四位
					if (arrayList2.get(i) == 4) {
						//如果西南不在第二位,把西南挪到第二位
						if (i != 1) {
							arrayList2.remove(i);
							arrayList2.add(1, 4);
						}
					}
				}
			}

			if (cityid == 77) {
				for (int i = 0; i < arrayList2.size(); i++) {
					//是否包含北，北在查询的List中排在第二位
					if (arrayList2.get(i) == 1) {
						//如果北不在最后一位,把北挪到最后一位
						if (i != arrayList2.size() - 1) {
							arrayList2.remove(i);
							arrayList2.add(arrayList2.size(), 1);
						}
					}
				}
			}

			System.out.println("2:" + arrayList2);

			//随机出的方位
			ArrayList<String> regions = new ArrayList();

			System.out.println("regionList:" + regionList);
			for (int i = 0; i < arrayList2.size(); i++) {
				regions.add(regionList.get(arrayList2.get(i)));
			}

			/*//list转数组
			int[] d = new int[arrayList2.size()];
			for (int i = 0; i < arrayList2.size(); i++) {
				d[i] = arrayList2.get(i);
			}*/

			//把随机出的具体方位查出来
			ArrayList<Integer> arrayList3 = new ArrayList();
			for (int i = 0; i < arrayList2.size(); i++) {
				int j = intlist.get(arrayList2.get(i));
				//北海道时，如果某个方位只有一天并且随机到的方位小于3个时，重新随机
				if (cityid == 86 && arrayList2.size() < 3 && j == 1) {
					return getsomeCount(cityid, intlist, size, arrayList4, regionList);
				}
				arrayList3.add(intlist.get(arrayList2.get(i)));

			}
			System.out.println("3:" + arrayList3);

			//从随机的每个方位中，再随机出几个景点
			//count1随机出的总景点个数
			int count = 0;
			ArrayList<Integer> arrayList = new ArrayList();
			for (int i = 0; i < arrayList3.size(); i++) {
				int count1 = 0;
				Random random = new Random();

				count1 = random.nextInt(arrayList3.get(i)) + 1;
				//限制每个方位呆几天
				//北海道,每个方位最多三天
				/*if (cityid == 86) {
					if (arrayList3.get(i) > 3) {
						count1 = random.nextInt(3) + 1;
					}
				}*/

				/*if (cityid == 86) {
					if (count1 > 3) {
						return getsomeCount(cityid, intlist, size, arrayList4, regionList);
					}
				}*/
				System.out.println("4:" + count1);

				arrayList.add(count1);
				count += count1;

			}
			System.out.println("arrayList:" + arrayList);
			System.out.println("count:" + count);

			if (count != size) {
				return getsomeCount(cityid, intlist, size, arrayList4, regionList);
			}

			//从随机的每个方位，随机出酒店
			System.out.println("regions:" + regions);
			ArrayList<Integer> hotelsList = new ArrayList();
			for (int i = 0; i < regions.size(); i++) {

				Integer integer = arrayList.get(i);

				List<THotelEntity> query = dbDao.query(THotelEntity.class,
						Cnd.where("cityId", "=", cityid).and("region", "=", regions.get(i)), null);
				Random random = new Random();
				int nextInt2 = random.nextInt(query.size());
				THotelEntity tHotelEntity = query.get(nextInt2);
				for (int j = 0; j < integer; j++) {
					hotelsList.add(tHotelEntity.getId());
				}
			}

			ArrayList<String> arrayList8 = new ArrayList();

			for (int i = 0; i < arrayList2.size(); i++) {
				//随机出来的每个方位的数据
				ArrayList<String> arrayList9 = arrayList4.get(arrayList2.get(i));
				//需要随机的个数
				Integer integer2 = arrayList.get(i);
				for (int j = 0; j < integer2; j++) {
					Random random = new Random();
					int nextInt2 = random.nextInt(arrayList9.size());
					String string = arrayList9.get(nextInt2);
					arrayList8.add(string);
					arrayList9.remove(string);
				}

			}
			System.out.println("5:" + arrayList8);
			System.out.println("last:" + arrayList);
			result.put("scenics", arrayList8);
			result.put("hotels", hotelsList);
		} catch (Exception e) {
			e.printStackTrace();
			message = "请重试";
			result.put("message", message);
		}

		return result;
	}

	/*public int getRoomCount(int orderjpid) {
		int roomCount = 0;
		List<TApplicantOrderJpEntity> allCount = dbDao.query(TApplicantOrderJpEntity.class,
				Cnd.where("orderId", "=", orderjpid), null);
		List<TApplicantOrderJpEntity> mainCount = dbDao.query(TApplicantOrderJpEntity.class,
				Cnd.where("orderId", "=", orderjpid).and("isMainApplicant", "=", 1), null);
		int viceCount = allCount.size() - mainCount.size();
		if (viceCount > 0) {
			if (viceCount % 2 == 1) {
				roomCount = viceCount / 2 + 1;
			} else {
				roomCount = viceCount / 2;
			}
		}
		roomCount += mainCount.size();

		return roomCount;
	}*/

	public int getRoomcount(int orderjpid) {
		int roomCount = 0;
		//所有人
		List<TApplicantOrderJpEntity> allCount = dbDao.query(TApplicantOrderJpEntity.class,
				Cnd.where("orderId", "=", orderjpid), null);
		//主申请人
		List<TApplicantOrderJpEntity> mainCount = dbDao.query(TApplicantOrderJpEntity.class,
				Cnd.where("orderId", "=", orderjpid).and("isMainApplicant", "=", 1), null);
		//副申请人数
		int viceCount = allCount.size() - mainCount.size();

		//规则：如果主申请人数大于副申请人数，则房间数为主申请人数，反之则两人一间
		if (mainCount.size() >= viceCount) {
			roomCount = mainCount.size();
		} else {
			if (allCount.size() > 0) {
				if (allCount.size() % 2 == 1) {
					roomCount = allCount.size() / 2 + 1;
				} else {
					roomCount = allCount.size() / 2;
				}
			}
		}

		return roomCount;
	}

	public int thirddayCity(int visatype) {
		int threeCityid = 0;
		if (visatype == 3 || visatype == 8) {//宫城
			threeCityid = 91;
		}
		if (visatype == 4 || visatype == 10) {//岩手
			threeCityid = 92;
		}
		if (visatype == 5 || visatype == 9) {//福岛
			threeCityid = 30;
		}
		if (visatype == 11) {//青森
			threeCityid = 25;
		}
		if (visatype == 12) {//秋田
			threeCityid = 612;
		}
		if (visatype == 13) {//山形
			threeCityid = 613;
		}
		return threeCityid;
	}

	/**
	 * 出行信息验证是否该填写的都填写了，如果填写不完整，提示
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param result
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Map<String, Object> validateIsfull(Map<String, Object> result, GenerrateTravelForm form) {
		if (Util.isEmpty(form.getCityid())) {
			result.put("message", "请选择领区");
			return result;
		}
		if (Util.isEmpty(form.getGoDate())) {
			result.put("message", "请选择出发日期");
			return result;
		}
		if (Util.isEmpty(form.getReturnDate())) {
			result.put("message", "请选择返回日期");
			return result;
		}
		if (Util.isEmpty(form.getVisatype())) {
			result.put("message", "请选择签证类型");
			return result;
		}
		if (form.getCityid() > 2) {//重庆
			if (Util.isEmpty(form.getGotransferdeparturecity())) {
				result.put("message", "请选择国际段出发城市");
				return result;
			}
			if (Util.isEmpty(form.getNewgoarrivedcity())) {
				result.put("message", "请选择国际段抵达城市");
				return result;
			}
			if (Util.isEmpty(form.getNewreturndeparturecity())) {
				result.put("message", "请选择国际段返回城市");
				return result;
			}
			if (Util.isEmpty(form.getReturntransferarrivedcity())) {
				result.put("message", "请选择国际段抵达城市");
				return result;
			}
			if (Util.isEmpty(form.getNewgoflightnum())) {
				result.put("message", "请选择国际段出发航班号");
				return result;
			}
			if (Util.isEmpty(form.getReturntransferflightnum())) {
				result.put("message", "请选择国际段返回航班号");
				return result;
			}
		} else {//北京，上海
			if (Util.isEmpty(form.getGoDepartureCity())) {
				result.put("message", "请选择出发城市");
				return result;
			}

			if (Util.isEmpty(form.getGoArrivedCity())) {
				result.put("message", "请选择抵达城市");
				return result;
			}
			if (Util.isEmpty(form.getGoFlightNum())) {
				result.put("message", "请选择出发航班号");
				return result;
			}
			if (Util.isEmpty(form.getReturnFlightNum())) {
				result.put("message", "请选择返回航班号");
				return result;
			}
		}
		result.put("message", "");
		return result;
	}

	//获取日本国内航班或新干线的第一天
	public String countryAirline(int gocityid, int arrcityid, int flag) {
		FlightSelectParam param = new FlightSelectParam();
		TCityEntity gocity = dbDao.fetch(TCityEntity.class, gocityid);
		TCityEntity arrcity = dbDao.fetch(TCityEntity.class, arrcityid);
		String firstday = gocity.getCity() + "から" + arrcity.getCity() + "まで新幹線で";
		/*param.setGocity((long) gocityid);
		param.setArrivecity((long) arrcityid);
		param.setDate("");
		param.setFlight("");
		List<ResultflyEntity> tripAirlineSelect = tripAirlineService.getTripAirlineSelect(param);
		Random random = new Random();
		String firstday = "";
		if (tripAirlineSelect.size() > 0) {
			int n = random.nextInt(tripAirlineSelect.size());
			ResultflyEntity resultflyEntity = tripAirlineSelect.get(n);
			firstday += gocity.getCity() + "から" + resultflyEntity.getFlightnum() + "便にて"
					+ resultflyEntity.getArrflightname() + "へ到着後、ホテルへ" + ",";
		}
		if (flag == 2) {
			firstday += gocity.getCity() + "から" + arrcity.getCity() + "まで新幹線で" + ",";
		}

		//随机获取一个
		String[] airlines = firstday.split(",");
		int num = (int) (Math.random() * airlines.length);
		return airlines[num];*/
		return firstday;
	}

	public Map<String, Integer> generrateorder(TUserEntity user, TCompanyEntity company) {

		lock.lock();
		Map<String, Integer> result = Maps.newHashMap();
		try {
			//如果订单不存在，则先创建订单
			TOrderEntity orderinfo = new TOrderEntity();
			orderinfo.setComId(company.getId());
			orderinfo.setUserId(user.getId());
			orderinfo.setOrderNum(generrateOrdernum());
			orderinfo.setStatus(JPOrderStatusEnum.PLACE_ORDER.intKey());
			orderinfo.setZhaobaocomplete(IsYesOrNoEnum.NO.intKey());
			orderinfo.setZhaobaoupdate(IsYesOrNoEnum.NO.intKey());
			orderinfo.setIsDisabled(IsYesOrNoEnum.NO.intKey());
			orderinfo.setCreateTime(new Date());
			orderinfo.setUpdateTime(new Date());
			TOrderEntity orderinsert = dbDao.insert(orderinfo);
			changePrincipalViewService.ChangePrincipal(orderinsert.getId(),
					JPOrderProcessTypeEnum.SALES_PROCESS.intKey(), user.getId());
			result.put("orderid", orderinsert.getId());
			TOrderJpEntity orderjp = new TOrderJpEntity();
			orderjp.setOrderId(orderinsert.getId());
			orderjp.setVisaType(MainSaleVisaTypeEnum.SINGLE.intKey());
			orderjp.setIsVisit(IsYesOrNoEnum.NO.intKey());
			TOrderJpEntity orderjpinsert = dbDao.insert(orderjp);
			Integer orderjpid = orderjpinsert.getId();
			result.put("orderjpid", orderjpid);
		} catch (Exception e) {

		} finally {
			lock.unlock();
		}

		return result;
	}

	public static int[] getRandomArray(int[] paramArray, int count) {
		if (paramArray.length < count) {
			return paramArray;
		}
		int[] newArray = new int[count];
		Random random = new Random();
		int temp = 0;//接收产生的随机数
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= count; i++) {
			temp = random.nextInt(paramArray.length);//将产生的随机数作为被抽数组的索引
			if (!(list.contains(temp))) {
				newArray[i - 1] = paramArray[temp];
				list.add(temp);
			} else {
				i--;
			}
		}
		return newArray;
	}

	//生成不重复的随机数，并除去去程的抵达城市和返程的出发城市
	public Map<String, List<Integer>> getRandomCity(int[] paramArray, int count, int days) {
		Map<String, List<Integer>> result = Maps.newHashMap();
		Random random = new Random();
		if (count == 0) {
			count = 1;
		}
		int newCount = 0;
		if (count <= paramArray.length) {
			newCount = random.nextInt(count) + 1;
		} else {
			newCount = random.nextInt(paramArray.length) + 1;

		}
		int[] newArray = new int[newCount];

		int temp = 0;//接收产生的随机数
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= newCount; i++) {
			temp = random.nextInt(paramArray.length);//将产生的随机数作为被抽数组的索引
			if (!(list.contains(temp))) {
				newArray[i - 1] = paramArray[temp];
				list.add(temp);
			} else {
				i--;
			}
		}

		List<Integer> randomDates = getRandomDates(newArray, days);

		List<Integer> cityidList = Ints.asList(newArray);

		for (int i = 0; i < cityidList.size(); i++) {
			List<TScenicEntity> scenics = dbDao.query(TScenicEntity.class, Cnd.where("cityId", "=", cityidList.get(i)),
					null);
			if (randomDates.size() > 0) {
				if (randomDates.get(i) > scenics.size()) {
					randomDates.clear();
					return getRandomCity(paramArray, count, days);
				}
			}
		}
		result.put("citys", cityidList);
		result.put("days", randomDates);
		return result;
	}

	//随机天数
	public List<Integer> getRandomDates(int[] paramArray, int days) {
		Random random = new Random();
		List<Integer> numbers = new ArrayList<Integer>();
		int sum = 0;
		while (true) {
			int n = random.nextInt(days - 1) + 2;
			sum += n;
			numbers.add(n);

			if (numbers.size() > paramArray.length || sum > days) {
				numbers.clear();
				sum = 0;
			}

			if (numbers.size() == paramArray.length && sum == days) {
				break;
			}
		}
		System.out.println(numbers);

		return numbers;
	}

	public int getNum(List<Integer> datesList, int n) {
		int num = 0;
		if (datesList.size() >= 1) {
			for (int i = 0; i < n; i++) {
				num += datesList.get(i);
			}
		}
		return num;
	}

	//获取城市id数组
	/*public int[] generrateCityArray() {
		String sixCityid = "";
		ArrayList<String> cityList = new ArrayList<>();
		cityList.add("冲绳");
		cityList.add("宫城");
		cityList.add("福岛");
		cityList.add("岩手");
		cityList.add("青森");
		cityList.add("秋田");
		cityList.add("山形");
		for (String string : cityList) {
			TCityEntity city = dbDao.fetch(TCityEntity.class, Cnd.where("city", "like", "%" + string + "%"));
			sixCityid += city.getId() + ",";
		}
		List<TScenicEntity> scenics = dbDao.query(TScenicEntity.class,
				Cnd.where("cityId", "not in", sixCityid.substring(0, sixCityid.length() - 1)).groupBy("cityId"), null);
		int[] cityArray = new int[scenics.size()];
		for (int i = 0; i < scenics.size(); i++) {
			cityArray[i] = scenics.get(i).getCityId();
		}
		return cityArray;
	}*/
	public int[] generrateCityArray() {
		int[] cityArray = { 22, 51, 38, 50, 85, 53, 37 };
		/*String sixCityid = "";
		ArrayList<String> cityList = new ArrayList<>();
		cityList.add("冲绳");
		cityList.add("宫城");
		cityList.add("福岛");
		cityList.add("岩手");
		cityList.add("青森");
		cityList.add("秋田");
		cityList.add("山形");
		for (String string : cityList) {
			TCityEntity city = dbDao.fetch(TCityEntity.class, Cnd.where("city", "like", "%" + string + "%"));
			sixCityid += city.getId() + ",";
		}
		List<TScenicEntity> scenics = dbDao.query(TScenicEntity.class,
				Cnd.where("cityId", "not in", sixCityid.substring(0, sixCityid.length() - 1)).groupBy("cityId"), null);
		int[] cityArray = new int[scenics.size()];
		for (int i = 0; i < scenics.size(); i++) {
			cityArray[i] = scenics.get(i).getCityId();
		}*/
		return cityArray;
	}

	//生成附近城市的Id数组,要去除掉去程的抵达城市、返程的出发城市以及东北六县
	public static int[] getCitysArray(int[] paramArray, int arrcityid, int returngocityid) {

		for (int i = 0; i < paramArray.length; i++) {
			if (paramArray[i] == arrcityid) {
				paramArray = ArrayUtils.remove(paramArray, i);
			}
		}
		for (int i = 0; i < paramArray.length; i++) {
			if (paramArray[i] == returngocityid) {
				paramArray = ArrayUtils.remove(paramArray, i);
			}
		}
		return paramArray;
	}

	private String generrateOrdernum() {

		//生成订单号
		SimpleDateFormat smf = new SimpleDateFormat("yyMMdd");
		String format = smf.format(new Date());
		//String sqlString = sqlManager.get("orderJp_ordernum");
		String sqlString = sqlManager.get("test_getOrdernum");
		Sql sql = Sqls.create(sqlString);
		List<Record> query = dbDao.query(sql, null, null);
		int sum = 1;
		if (!Util.isEmpty(query) && query.size() > 0) {
			String string = query.get(0).getString("orderNum");
			int a = Integer.valueOf(string.substring(9, string.length()));
			sum += a;
		}
		String sum1 = "";
		if (sum / 10 == 0) {
			sum1 = "000" + sum;
		} else if (sum / 100 == 0) {
			sum1 = "00" + sum;

		} else if (sum / 1000 == 0) {
			sum1 = "0" + sum;
		} else {
			sum1 = "" + sum;

		}

		return format + "-JP" + sum1;
	}

	/**
	 * 获取行程安排数据
	 * <p>
	 * TODO 通过订单id获取行程安排数据
	 *
	 * @param orderid
	 * @return TODO 获取行程安排数据
	 * @throws ParseException 
	 */
	public Object getTravelPlanByOrderId(Integer orderid) {
		String sqlString = sqlManager.get("get_travel_plan_by_orderid");
		Sql sql = Sqls.create(sqlString);
		sql.setParam("orderid", orderid);
		List<Record> travelplans = dbDao.query(sql, null, null);
		DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		int count = 1;
		String prehotelname = "";
		for (Record record : travelplans) {
			Date outdate = (Date) record.get("outdate");
			record.put("outdate", format.format(outdate));
			//酒店名字
			if (!Util.isEmpty(record.get("hotelname"))) {
				String hotelname = (String) record.get("hotelname");
				if (count > 1) {
					if (hotelname.equals(prehotelname)) {
						record.put("hotelname", "連泊");
						record.put("hoteladdress", "");
						record.put("hotelmobile", "");
					}
				}
				prehotelname = hotelname;
			}
			//hotelid
			if (Util.isEmpty(record.get("hotel"))) {
				record.get("day");
				TOrderTravelplanJpEntity fetch = dbDao.fetch(TOrderTravelplanJpEntity.class,
						Cnd.where("orderId", "=", orderid)
								.and("day", "=", Long.valueOf((String) record.get("day")) - 1));
				TOrderTravelplanJpEntity plan = dbDao.fetch(TOrderTravelplanJpEntity.class,
						Cnd.where("orderId", "=", orderid).and("day", "=", Long.valueOf((String) record.get("day"))));
				plan.setHotel(fetch.getHotel());
				dbDao.update(plan);
			}
			count++;
		}
		return travelplans;
	}

	/**
	 * 跳转到添加申请人页面
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid
	 * @param request 
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addApplicant(Integer orderid, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String, Object> result = Maps.newHashMap();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		result.put("boyOrGirlEnum", EnumUtil.enum2(BoyOrGirlEnum.class));
		result.put("orderid", orderid);
		String localAddr = request.getServerName();
		int localPort = request.getServerPort();
		result.put("localAddr", localAddr);
		result.put("localPort", localPort);
		result.put("websocketaddr", SIMPLE_WEBSOCKET_ADDR);
		String qrurl = "http://" + localAddr + ":" + localPort + "/simplemobile/info.html";
		if (Util.isEmpty(orderid)) {
			qrurl += "?comid=" + loginCompany.getId() + "&userid=" + loginUser.getId() + "&sessionid="
					+ session.getId();
		} else {
			qrurl += "?comid=" + loginCompany.getId() + "&userid=" + loginUser.getId() + "&orderid=" + orderid
					+ "&sessionid=" + session.getId();
		}
		String qrCode = qrCodeService.encodeQrCode(request, qrurl);
		result.put("qrCode", qrCode);
		result.put("sessionid", session.getId());
		return result;
	}

	/**
	 * 保存下单信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveAddOrderinfo(AddOrderForm form, HttpServletRequest request) {
		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer orderjpid = form.getOrderid();
		Integer orderid = null;
		//获取订单id信息
		if (Util.isEmpty(orderjpid)) {
			Map<String, Integer> generrateorder = generrateorder(loginUser, loginCompany);
			orderid = generrateorder.get("orderid");
			orderjpid = generrateorder.get("orderjpid");
			orderJpViewService.insertLogs(orderid, JpOrderSimpleEnum.PLACE_ORDER.intKey(), session);

		} else {
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
			orderid = orderjp.getOrderId();
			//根据订单ID查询
			TOrderLogsEntity logs = dbDao.fetch(TOrderLogsEntity.class, Cnd.where("orderid", "=", orderid.longValue()));
			if (Util.isEmpty(logs)) {
				orderJpViewService.insertLogs(orderid, JpOrderSimpleEnum.PLACE_ORDER.intKey(), session);
			}
			if (JpOrderSimpleEnum.PLACE_ORDER.intKey() != 1) {
				orderJpViewService.insertLogs(orderid, JpOrderSimpleEnum.PLACE_ORDER.intKey(), session);
			}
		}
		TOrderJpEntity orderjpinfo = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderid.longValue());
		//创建客户历史信息实体
		//保存客户信息
		if (!Util.isEmpty(form.getCustomerType())) {
			if (form.getCustomerType().equals(CustomerTypeEnum.ZHIKE.intKey())) {
				if (!Util.isEmpty(form.getZhikecustomid())) {
					TCustomerEntity customer = dbDao.fetch(TCustomerEntity.class, form.getZhikecustomid().longValue());
					customer.setName(form.getCompName2());
					customer.setShortname(form.getComShortName2());
					customer.setPayType(form.getPayType());
					customer.setSource(CustomerTypeEnum.ZHIKE.intKey());
					dbDao.update(customer);
				} else {
					TCustomerEntity customer = new TCustomerEntity();
					customer.setName(form.getCompName2());
					customer.setShortname(form.getComShortName2());
					customer.setPayType(form.getPayType());
					customer.setSource(CustomerTypeEnum.ZHIKE.intKey());
					TCustomerEntity insertcustom = dbDao.insert(customer);
					List<TCustomerVisainfoEntity> customervisas = Lists.newArrayList();
					for (MainSaleVisaTypeEnum mainSaleVisaTypeEnum : MainSaleVisaTypeEnum.values()) {
						TCustomerVisainfoEntity cusvisa = new TCustomerVisainfoEntity();
						cusvisa.setVisatype(mainSaleVisaTypeEnum.key());
						cusvisa.setCustomerid(insertcustom.getId());
						if (!Util.isEmpty(form.getVisatype()) && form.getVisatype().equals(mainSaleVisaTypeEnum.key())) {
							cusvisa.setAmount(form.getAmount());
						}
						customervisas.add(cusvisa);
					}
					//dbDao.query(TCustomerVisainfoEntity.class, Cnd.where("customerid", "=", insertcustom), null);
					orderinfo.setCustomerId(insertcustom.getId());
				}
			} else {
				orderinfo.setCustomerId(form.getCustomerid());
			}
		}
		orderinfo.setCityId(form.getCityid());
		orderinfo.setUrgentType(form.getUrgentType());
		orderinfo.setUrgentDay(form.getUrgentDay());
		orderinfo.setSendVisaDate(form.getSendvisadate());
		/*String sendvisadate = form.getSendvisadate();
		if (!Util.isEmpty(sendvisadate)) {
			String[] split = sendvisadate.split(" - ");
			orderinfo.setSendVisaDate(DateUtil.string2Date(split[0], DateUtil.FORMAT_YYYY_MM_DD));
			orderinfo.setSendvisaenddate(DateUtil.string2Date(split[1], DateUtil.FORMAT_YYYY_MM_DD));
		} else {
			orderinfo.setSendVisaDate(null);
			orderinfo.setSendvisaenddate(null);
		}*/
		orderinfo.setOutVisaDate(form.getOutvisadate());
		/*String outvisadate = form.getOutvisadate();
		if (!Util.isEmpty(outvisadate)) {
			String[] split = outvisadate.split(" - ");
			orderinfo.setOutVisaDate(DateUtil.string2Date(split[0], DateUtil.FORMAT_YYYY_MM_DD));
			orderinfo.setOutvisaenddate(DateUtil.string2Date(split[1], DateUtil.FORMAT_YYYY_MM_DD));
		} else {
			orderinfo.setOutVisaDate(null);
			orderinfo.setOutvisaenddate(null);
		}*/
		orderinfo.setGoTripDate(form.getGoDate());
		orderinfo.setStayDay(form.getStayday());
		orderinfo.setBackTripDate(form.getReturnDate());
		orderinfo.setSendVisaNum(form.getSendvisanum());

		orderinfo.setIsDisabled(IsYesOrNoEnum.NO.intKey());
		orderinfo.setUpdateTime(new Date());

		dbDao.update(orderinfo);
		//更新日本订单表
		orderjpinfo.setVisaType(form.getVisatype());
		orderjpinfo.setAmount(form.getAmount());
		dbDao.update(orderjpinfo);

		//出行信息---------------------------------------------------------------------------
		TOrderTripJpEntity orderjptrip = dbDao.fetch(TOrderTripJpEntity.class, Cnd.where("orderId", "=", orderjpid));
		if (Util.isEmpty(orderjptrip)) {
			orderjptrip = new TOrderTripJpEntity();
		}
		orderjptrip.setTripPurpose(form.getTripPurpose());
		orderjptrip.setTripType(form.getTriptype());
		orderjptrip.setGoDate(form.getGoDate());
		orderjptrip.setReturnDate(form.getReturnDate());
		orderjptrip.setGoDepartureCity(form.getGoDepartureCity());
		orderjptrip.setGoArrivedCity(form.getGoArrivedCity());
		orderjptrip.setGoFlightNum(form.getGoFlightNum());
		orderjptrip.setReturnDepartureCity(form.getReturnDepartureCity());
		orderjptrip.setReturnArrivedCity(form.getReturnArrivedCity());
		orderjptrip.setReturnFlightNum(form.getReturnFlightNum());

		orderjptrip.setNewgoarrivedcity(form.getNewgoarrivedcity());
		orderjptrip.setNewgodeparturecity(form.getNewgodeparturecity());
		orderjptrip.setNewgoflightnum(form.getNewgoflightnum());
		orderjptrip.setNewreturnarrivedcity(form.getNewreturnarrivedcity());
		orderjptrip.setNewreturndeparturecity(form.getNewreturndeparturecity());
		orderjptrip.setNewreturnflightnum(form.getNewreturnflightnum());

		orderjptrip.setGotransferarrivedcity(form.getGotransferarrivedcity());
		orderjptrip.setGotransferdeparturecity(form.getGotransferdeparturecity());
		orderjptrip.setGotransferflightnum(form.getGotransferflightnum());
		orderjptrip.setReturntransferarrivedcity(form.getReturntransferarrivedcity());
		orderjptrip.setReturntransferdeparturecity(form.getReturntransferdeparturecity());
		orderjptrip.setReturntransferflightnum(form.getReturntransferflightnum());

		orderjptrip.setOrderId(orderjpid);
		if (!Util.isEmpty(orderjptrip.getId())) {
			dbDao.update(orderjptrip);
		} else {
			dbDao.insert(orderjptrip);
		}

		//消息通知
		/*try {
			visaInfoWSHandler.broadcast(new TextMessage(""));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return null;
	}

	/**
	 * 保存招宝订单信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveZhaobaoOrderinfo(AddOrderForm form, HttpServletRequest request) {
		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer orderjpid = form.getOrderid();
		Integer orderid = null;
		//获取订单id信息
		if (Util.isEmpty(orderjpid)) {
			Map<String, Integer> generrateorder = generrateorder(loginUser, loginCompany);
			orderid = generrateorder.get("orderid");
			orderjpid = generrateorder.get("orderjpid");
			orderJpViewService.insertLogs(orderid, JpOrderSimpleEnum.PLACE_ORDER.intKey(), session);

		} else {
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
			orderid = orderjp.getOrderId();
			//根据订单ID查询
			TOrderLogsEntity logs = dbDao.fetch(TOrderLogsEntity.class, Cnd.where("orderid", "=", orderid.longValue()));
			if (Util.isEmpty(logs)) {
				orderJpViewService.insertLogs(orderid, JpOrderSimpleEnum.PLACE_ORDER.intKey(), session);
			}
			if (JpOrderSimpleEnum.PLACE_ORDER.intKey() != 1) {
				orderJpViewService.insertLogs(orderid, JpOrderSimpleEnum.PLACE_ORDER.intKey(), session);
			}
		}
		TOrderJpEntity orderjpinfo = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderid.longValue());
		orderinfo.setCityId(form.getCityid());
		orderinfo.setGoTripDate(form.getGoDate());
		orderinfo.setStayDay(form.getStayday());
		orderinfo.setBackTripDate(form.getReturnDate());

		orderinfo.setIsDisabled(IsYesOrNoEnum.NO.intKey());
		orderinfo.setUpdateTime(new Date());

		dbDao.update(orderinfo);
		//更新日本订单表
		orderjpinfo.setVisaType(form.getVisatype());
		dbDao.update(orderjpinfo);

		//出行信息---------------------------------------------------------------------------
		TOrderTripJpEntity orderjptrip = dbDao.fetch(TOrderTripJpEntity.class, Cnd.where("orderId", "=", orderjpid));
		if (Util.isEmpty(orderjptrip)) {
			orderjptrip = new TOrderTripJpEntity();
		}
		orderjptrip.setGoDate(form.getGoDate());
		orderjptrip.setReturnDate(form.getReturnDate());

		orderjptrip.setOrderId(orderjpid);
		if (!Util.isEmpty(orderjptrip.getId())) {
			dbDao.update(orderjptrip);
		} else {
			dbDao.insert(orderjptrip);
		}

		//消息通知
		/*try {
			visaInfoWSHandler.broadcast(new TextMessage(""));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return null;
	}

	/**
	 * 跳转到编辑订单页面
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param request
	 * @param orderid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object editOrder(HttpServletRequest request, Integer orderid) {

		Map<String, Object> result = Maps.newHashMap();
		TOrderJpEntity orderjpinfo = dbDao.fetch(TOrderJpEntity.class, orderid.longValue());
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderjpinfo.getOrderId().longValue());
		TCustomerEntity customerinfo = new TCustomerEntity();
		if (!Util.isEmpty(orderinfo.getCustomerId())) {
			customerinfo = dbDao.fetch(TCustomerEntity.class, orderinfo.getCustomerId().longValue());
		}
		TOrderTripJpEntity tripinfo = dbDao.fetch(TOrderTripJpEntity.class, Cnd.where("orderid", "=", orderid));
		List<TCityEntity> citylist = Lists.newArrayList();
		List<TCityEntity> newcitylist = Lists.newArrayList();
		List<TFlightEntity> flightlist = Lists.newArrayList();
		List<ResultflyEntity> gotripAirlineSelect = Lists.newArrayList();
		List<ResultflyEntity> returntripAirlineSelect = Lists.newArrayList();

		if (!Util.isEmpty(tripinfo)) {
			Integer[] cityids = { tripinfo.getGoDepartureCity(), tripinfo.getGoArrivedCity(),
					tripinfo.getReturnDepartureCity(), tripinfo.getReturnArrivedCity() };
			citylist = dbDao.query(TCityEntity.class, Cnd.where("id", "in", cityids), null);

			Integer[] newcityids = { tripinfo.getGotransferarrivedcity(), tripinfo.getGotransferdeparturecity(),
					tripinfo.getReturntransferarrivedcity(), tripinfo.getReturntransferdeparturecity(),
					tripinfo.getNewgoarrivedcity(), tripinfo.getNewgodeparturecity(),
					tripinfo.getNewreturnarrivedcity(), tripinfo.getNewreturndeparturecity() };
			newcitylist = dbDao.query(TCityEntity.class, Cnd.where("id", "in", newcityids), null);
		}
		String orderstatus = "";
		for (JPOrderStatusEnum orderenum : JPOrderStatusEnum.values()) {
			if (orderenum.intKey() == orderinfo.getStatus()) {
				orderstatus = orderenum.value();
				break;
			}
		}
		DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		//送签时间
		String sendvisadatestr = "";
		Date sendVisaDate = orderinfo.getSendVisaDate();
		Date sendvisaenddate = orderinfo.getSendvisaenddate();
		if (!Util.isEmpty(sendVisaDate) && !Util.isEmpty(sendvisaenddate)) {
			String sendvisastr = format.format(sendVisaDate);
			String sendvisaendstr = format.format(sendvisaenddate);
			sendvisadatestr = sendvisastr + " - " + sendvisaendstr;
		}
		String outvisadatestr = "";
		Date outVisaDate = orderinfo.getOutVisaDate();
		Date outvisaenddate = orderinfo.getOutvisaenddate();
		if (!Util.isEmpty(outVisaDate) && !Util.isEmpty(outvisaenddate)) {
			String outvisastr = format.format(outVisaDate);
			String outvisaendstr = format.format(outvisaenddate);
			outvisadatestr = outvisastr + " - " + outvisaendstr;
		}
		result.put("sendvisadatestr", sendvisadatestr);
		result.put("outvisadatestr", outvisadatestr);
		result.put("orderstatus", orderstatus);
		//result.put("flightlist", flightlist);
		result.put("collarAreaEnum", EnumUtil.enum2(CollarAreaEnum.class));
		result.put("customerTypeEnum", EnumUtil.enum2(CustomerTypeEnum.class));
		result.put("mainSaleUrgentEnum", EnumUtil.enum2(MainSaleUrgentEnum.class));
		result.put("mainSaleUrgentTimeEnum", EnumUtil.enum2(MainSaleUrgentTimeEnum.class));
		result.put("mainSaleTripTypeEnum", EnumUtil.enum2(MainSaleTripTypeEnum.class));
		result.put("mainSalePayTypeEnum", EnumUtil.enum2(MainSalePayTypeEnum.class));
		result.put("mainSaleVisaTypeEnum", EnumUtil.enum2(SimpleVisaTypeEnum.class));
		result.put("citylist", citylist);
		result.put("newcitylist", newcitylist);
		result.put("orderjpinfo", orderjpinfo);
		result.put("orderinfo", orderinfo);
		result.put("tripinfo", tripinfo);
		result.put("customerinfo", customerinfo);

		result.put("returntripAirlineSelect", returntripAirlineSelect);
		result.put("gotripAirlineSelect", gotripAirlineSelect);

		return result;
	}

	/**
	 *获取客户信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param customerid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getCustomerinfoById(Long customerid) {
		TCustomerEntity customerinfo = dbDao.fetch(TCustomerEntity.class, customerid);
		return customerinfo;
	}

	public Object disabled(int orderid) {
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid);
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
		orderEntity.setIsDisabled(IsYesOrNoEnum.YES.intKey());
		orderEntity.setUpdateTime(new Date());
		dbDao.update(orderEntity);
		return null;
	}

	public Object undisabled(int orderid) {
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid);
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
		orderEntity.setIsDisabled(IsYesOrNoEnum.NO.intKey());
		orderEntity.setUpdateTime(new Date());
		dbDao.update(orderEntity);
		return null;
	}

	/**
	 * 获取客户金额
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param customerid
	 * @param visatype
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getCustomerAmount(Integer customerid, Integer visatype) {
		TCustomerVisainfoEntity customerVisa = dbDao.fetch(TCustomerVisainfoEntity.class,
				Cnd.where("customerid", "=", customerid).and("visatype", "=", visatype));
		return customerVisa;
	}

	/**
	 * 保存游客基本信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param request
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveApplicantInfo(HttpServletRequest request, TApplicantForm form) {
		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Map<String, Object> result = Maps.newHashMap();
		Integer orderjpid = form.getOrderid();
		Integer orderid = null;
		TApplicantEntity applicant = new TApplicantEntity();
		TApplicantOrderJpEntity applicantjp = new TApplicantOrderJpEntity();
		applicantjp.setMainRelation(form.getMainRelation());
		//修改
		if (!Util.isEmpty(form.getId())) {
			applicant = dbDao.fetch(TApplicantEntity.class, form.getId().longValue());
			applicantjp = dbDao.fetch(TApplicantOrderJpEntity.class, Cnd.where("applicantId", "=", applicant.getId()));
			result.put("applicantjpid", applicantjp.getId());
			result.put("applicantid", applicant.getId());
			applicantjp.setMainRelation(form.getMainRelation());
			dbDao.update(applicantjp);
		}
		applicant.setOpId(loginUser.getId());
		applicant.setIsSameInfo(IsYesOrNoEnum.YES.intKey());
		applicant.setIsPrompted(IsYesOrNoEnum.NO.intKey());
		applicant.setAddress(form.getAddress());
		applicant.setCardId(form.getCardId());
		applicant.setCity(form.getCity());
		applicant.setDetailedAddress(form.getDetailedAddress());
		applicant.setEmail(form.getEmail());
		if (!Util.isEmpty(form.getOtherLastNameEn())) {
			applicant.setOtherLastNameEn(form.getOtherLastNameEn().substring(1));
		}
		if (!Util.isEmpty(form.getOtherFirstNameEn())) {
			applicant.setOtherFirstNameEn(form.getOtherFirstNameEn().substring(1));
		}
		applicant.setNationality(form.getNationality());
		applicant.setHasOtherName(form.getHasOtherName());
		applicant.setHasOtherNationality(form.getHasOtherNationality());
		applicant.setOtherFirstName(form.getOtherFirstName());
		applicant.setOtherLastName(form.getOtherLastName());
		applicant.setAddressIsSameWithCard(form.getAddressIsSameWithCard());
		applicant.setCardProvince(form.getCardProvince());
		applicant.setCardCity(form.getCardCity());
		applicant.setIssueOrganization(form.getIssueOrganization());
		applicant.setNation(form.getNation());
		applicant.setProvince(form.getProvince());
		//applicant.setSex(form.getSex());
		applicant.setTelephone(form.getTelephone());
		applicant.setValidEndDate(form.getValidEndDate());
		applicant.setValidStartDate(form.getValidStartDate());
		applicant.setCardFront(form.getCardFront());
		applicant.setCardBack(form.getCardBack());
		applicant.setStatus(TrialApplicantStatusEnum.FIRSTTRIAL.intKey());
		applicant.setCreateTime(new Date());
		applicant.setEmergencyLinkman(form.getEmergencyLinkman());
		applicant.setEmergencyTelephone(form.getEmergencyTelephone());
		applicant.setEmergencyaddress(form.getEmergencyaddress());
		//游客登录
		ApplicantUser applicantUser = new ApplicantUser();
		applicantUser.setMobile(applicant.getTelephone());
		applicantUser.setOpid(applicant.getOpId());
		applicantUser.setPassword("000000");
		applicantUser.setUsername(applicant.getFirstName() + applicant.getLastName());
		/*if (!Util.isEmpty(applicant.getTelephone())) {
			TUserEntity userEntity = dbDao.fetch(TUserEntity.class, Cnd.where("simplemobile", "=", applicant.getTelephone())
					.and("userType", "=", UserLoginEnum.TOURIST_IDENTITY.intKey()));
			if (Util.isEmpty(userEntity)) {
				TUserEntity tUserEntity = userViewService.addApplicantUser(applicantUser);
				applicant.setUserId(tUserEntity.getId());
			} else {
				userEntity.setName(applicantUser.getUsername());
				userEntity.setMobile(applicant.getTelephone());
				userEntity.setPassword(applicantUser.getPassword());
				userEntity.setOpId(applicantUser.getOpid());
				userEntity.setUpdateTime(new Date());
				applicant.setUserId(userEntity.getId());
				dbDao.update(userEntity);
			}
		}*/
		Integer applicantid = form.getId();
		if (!Util.isEmpty(form.getId())) {
			dbDao.update(applicant);

		} else {

			TApplicantEntity insertapplicant = dbDao.insert(applicant);
			applicantid = insertapplicant.getId();
			result.put("applicantid", applicantid);
			//如果订单不存在，则先创建订单
			if (Util.isEmpty(form.getOrderid())) {
				Map<String, Integer> generrateorder = generrateorder(loginUser, loginCompany);
				orderid = generrateorder.get("orderid");
				orderjpid = generrateorder.get("orderjpid");
			} else {
				TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
				orderid = orderjp.getOrderId();
				applicantjp.setOrderId(orderid);
			}
			//新增日本订单基本信息
			applicantjp.setMainRelation(form.getMainRelation());
			dbDao.insert(applicantjp);
			applicantjp.setOrderId(orderjpid);
			applicantjp.setApplicantId(applicantid);
			applicantjp.setBaseIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setPassIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setVisaIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setMainRelation(form.getMainRelation());
			TApplicantOrderJpEntity insertappjp = dbDao.insert(applicantjp);
			result.put("applicantjpid", insertappjp.getId());
			//日本工作信息
			TApplicantWorkJpEntity workJp = new TApplicantWorkJpEntity();
			workJp.setApplicantId(insertappjp.getId());
			workJp.setCreateTime(new Date());
			workJp.setOpId(loginUser.getId());
			dbDao.insert(workJp);
			//护照信息
			TApplicantPassportEntity passport = new TApplicantPassportEntity();
			passport.setSex(form.getSex());
			passport.setFirstName(form.getFirstName());
			passport.setLastName(form.getLastName());
			if (!Util.isEmpty(form.getFirstNameEn())) {
				passport.setFirstNameEn(form.getFirstNameEn().substring(1));
			}
			if (!Util.isEmpty(form.getLastNameEn())) {
				passport.setLastNameEn(form.getLastNameEn().substring(1));
			}
			passport.setIssuedOrganization("公安部出入境管理局");
			passport.setIssuedOrganizationEn("MPS Exit&Entry Adiministration");
			passport.setApplicantId(applicantid);
			dbDao.insert(passport);
		}
		result.put("orderjpid", orderjpid);
		//创建日本申请人 信息
		return result;
	}

	/**
	 * 跳转到护照信息页面
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param applicantid
	 * @param orderid
	 * @param request 
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object passportInfo(Integer applicantid, Integer orderid, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String, Object> result = Maps.newHashMap();
		result.put("orderid", orderid);
		result.put("applicantid", applicantid);
		TApplicantPassportEntity passport = dbDao.fetch(TApplicantPassportEntity.class,
				Cnd.where("applicantId", "=", applicantid));
		if (!Util.isEmpty(passport.getIssuedPlaceEn())) {
			if (!passport.getIssuedPlaceEn().startsWith("/")) {
				passport.setIssuedPlaceEn("/" + passport.getIssuedPlaceEn());
			}
		}
		if (!Util.isEmpty(passport.getBirthAddressEn())) {
			if (!passport.getBirthAddressEn().startsWith("/")) {
				passport.setBirthAddressEn("/" + passport.getBirthAddressEn());
			}
		}
		result.put("passport", passport);
		if (!Util.isEmpty(passport.getFirstNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(passport.getFirstNameEn());
			result.put("firstNameEn", sb.toString());
		}
		if (!Util.isEmpty(passport.getLastNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(passport.getLastNameEn());
			result.put("lastNameEn", sb.toString());
		}
		//格式化日期
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		if (!Util.isEmpty(passport.getBirthday())) {
			Date birthday = passport.getBirthday();
			result.put("birthday", sdf.format(birthday));
		}
		if (!Util.isEmpty(passport.getIssuedDate())) {
			Date issuedDate = passport.getIssuedDate();
			result.put("issuedDate", sdf.format(issuedDate));
		}
		if (!Util.isEmpty(passport.getValidEndDate())) {
			Date validEndDate = passport.getValidEndDate();
			result.put("validEndDate", sdf.format(validEndDate));
		}
		result.put("passportType", EnumUtil.enum2(PassportTypeEnum.class));
		//所访问的ip地址
		String localAddr = request.getServerName();
		result.put("localAddr", localAddr);
		//所访问的端口
		int localPort = request.getServerPort();
		result.put("localPort", localPort);
		//websocket地址
		result.put("websocketaddr", SIMPLE_WEBSOCKET_ADDR);
		//生成二维码的URL
		String passporturl = "http://" + localAddr + ":" + localPort + "/simplemobile/passport.html?applicantid="
				+ applicantid + "&orderid=" + orderid;
		//生成二维码
		String qrCode = qrCodeService.encodeQrCode(request, passporturl);
		result.put("qrCode", qrCode);
		return result;
	}

	public Object toNewfilminginfo(int applyid, int orderid, HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("orderid", orderid);
		String applyurl = " ";
		String passurl = " ";
		if (!Util.isEmpty(applyid)) {
			result.put("applyid", applyid);
			TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyid);
			if (!Util.isEmpty(apply.getCardFront())) {
				applyurl = apply.getCardFront();
			}

			TApplicantPassportEntity passport = dbDao.fetch(TApplicantPassportEntity.class,
					Cnd.where("applicantId", "=", applyid));
			if (!Util.isEmpty(passport.getPassportUrl())) {
				passurl = passport.getPassportUrl();
			}

		}
		result.put("applyurl", applyurl);
		result.put("passurl", passurl);
		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();
		result.put("userdi", userid);
		//所访问的ip地址
		String localAddr = request.getServerName();
		result.put("localAddr", localAddr);
		//所访问的端口
		int localPort = request.getServerPort();
		result.put("localPort", localPort);
		//websocket地址
		result.put("websocketaddr", SIMPLE_WEBSOCKET_ADDR);
		//生成二维码
		if (Util.isEmpty(applyid)) {
			applyid = 0;
		}
		if (Util.isEmpty(orderid)) {
			orderid = 0;
		}
		String qrCode = dataUpload(applyid, orderid, request);
		result.put("qrCode", qrCode);
		return result;
	}

	public Object basicInfo(Integer applicantid, Integer orderid, HttpServletRequest request) {

		Map<String, Object> result = Maps.newHashMap();

		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		result.put("ordertype", loginUser.getOrdertype());

		result.put("orderid", orderid);
		result.put("applicantid", applicantid);

		//护照信息
		TApplicantPassportEntity passport = dbDao.fetch(TApplicantPassportEntity.class,
				Cnd.where("applicantId", "=", applicantid));
		if (!Util.isEmpty(passport.getIssuedPlaceEn())) {
			if (!passport.getIssuedPlaceEn().startsWith("/")) {
				passport.setIssuedPlaceEn("/" + passport.getIssuedPlaceEn());
			}
		}
		if (!Util.isEmpty(passport.getBirthAddressEn())) {
			if (!passport.getBirthAddressEn().startsWith("/")) {
				passport.setBirthAddressEn("/" + passport.getBirthAddressEn());
			}
		}
		result.put("passport", passport);
		if (!Util.isEmpty(passport.getFirstNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(passport.getFirstNameEn());
			result.put("firstNameEn", sb.toString());
		}
		if (!Util.isEmpty(passport.getLastNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(passport.getLastNameEn());
			result.put("lastNameEn", sb.toString());
		}
		//格式化日期
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		if (!Util.isEmpty(passport.getBirthday())) {
			Date birthday = passport.getBirthday();
			result.put("birthday", sdf.format(birthday));
		}
		if (!Util.isEmpty(passport.getIssuedDate())) {
			Date issuedDate = passport.getIssuedDate();
			result.put("issuedDate", sdf.format(issuedDate));
		}
		if (!Util.isEmpty(passport.getValidEndDate())) {
			Date validEndDate = passport.getValidEndDate();
			result.put("validEndDate", sdf.format(validEndDate));
		}
		result.put("passportType", EnumUtil.enum2(PassportTypeEnum.class));

		//基本信息
		TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, applicantid.longValue());
		result.put("applicant", applicant);
		TApplicantOrderJpEntity orderjp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantid));
		result.put("orderjp", orderjp);
		if (!Util.isEmpty(applicant.getBirthday())) {
			Date birthday = applicant.getBirthday();
			String birthdayStr = sdf.format(birthday);
			result.put("birthday", birthdayStr);
		}
		if (!Util.isEmpty(applicant.getValidStartDate())) {
			Date validStartDate = applicant.getValidStartDate();
			String validStartDateStr = sdf.format(validStartDate);
			result.put("validStartDate", validStartDateStr);
		}
		if (!Util.isEmpty(applicant.getValidEndDate())) {
			Date validEndDate = applicant.getValidEndDate();
			String validEndDateStr = sdf.format(validEndDate);
			result.put("validEndDate", validEndDateStr);
		}

		if (!Util.isEmpty(applicant.getFirstNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicant.getFirstNameEn());
			result.put("firstNameEn", sb.toString());
		}
		if (!Util.isEmpty(applicant.getOtherFirstNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicant.getOtherFirstNameEn());
			result.put("otherFirstNameEn", sb.toString());
		}

		if (!Util.isEmpty(applicant.getLastNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicant.getLastNameEn());
			result.put("lastNameEn", sb.toString());
		}

		if (!Util.isEmpty(applicant.getOtherLastNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicant.getOtherLastNameEn());
			result.put("otherLastNameEn", sb.toString());
		}
		//婚姻状况下拉选项
		result.put("marryStatusEnum", EnumUtil.enum2(MarryStatusEnum.class));

		return result;
	}

	public Object saveBasicinfo(BasicinfoForm form, HttpServletRequest request) {

		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		//基本信息
		TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, form.getApplicantid().longValue());
		//日本申请人信息
		TApplicantOrderJpEntity applicantjp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicant.getId()));
		applicantjp.setMainRelation(form.getMainRelation());
		dbDao.update(applicantjp);

		TApplicantWorkJpEntity workjp = dbDao.fetch(TApplicantWorkJpEntity.class,
				Cnd.where("applicantId", "=", applicantjp.getId()));
		//根据出生日期设置签证信息的工作类型
		if (!Util.isEmpty(form.getBirthday()) && Util.isEmpty(workjp.getCareerStatus())) {
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
			int years = orderJpViewService.accordingbirtday(sdf.format(form.getBirthday()));
			if (years >= 55) {
				workjp.setCareerStatus(2);
			}
			if (years <= 5) {
				workjp.setCareerStatus(5);
			}
			dbDao.update(workjp);
		}

		applicant.setOpId(loginUser.getId());
		applicant.setIsSameInfo(IsYesOrNoEnum.YES.intKey());
		applicant.setIsPrompted(IsYesOrNoEnum.NO.intKey());
		//marryurltype是闲置字段，用来作为姓名checkbox字段
		applicant.setMarryurltype(form.getIsnamedisabled());
		applicant.setAddress(form.getAddress());
		applicant.setCardId(form.getCardId());
		if (Util.isEmpty(form.getJuzhudicheckbox())) {
			applicant.setAddressIsSameWithCard(IsYesOrNoEnum.NO.intKey());
		} else {
			applicant.setAddressIsSameWithCard(form.getJuzhudicheckbox());
		}
		applicant.setCity(form.getCity());
		applicant.setDetailedAddress(form.getDetailedAddress());
		applicant.setEmail(form.getEmail());
		if (!Util.isEmpty(form.getOtherLastNameEn())) {
			applicant.setOtherLastNameEn(form.getOtherLastNameEn().substring(1));
		} else {
			applicant.setOtherLastNameEn(form.getOtherLastNameEn());
		}
		if (!Util.isEmpty(form.getOtherFirstNameEn())) {
			applicant.setOtherFirstNameEn(form.getOtherFirstNameEn().substring(1));
		} else {
			applicant.setOtherFirstNameEn(form.getOtherFirstNameEn());
		}
		applicant.setNationality(form.getNationality());
		applicant.setHasOtherName(form.getHasOtherName());
		applicant.setHasOtherNationality(form.getHasOtherNationality());
		applicant.setOtherFirstName(form.getOtherFirstName());
		applicant.setOtherLastName(form.getOtherLastName());
		applicant.setCardProvince(form.getCardProvince());
		applicant.setCardCity(form.getCardCity());
		applicant.setIssueOrganization(form.getIssueOrganization());
		applicant.setNation(form.getNation());
		applicant.setProvince(form.getProvince());
		applicant.setTelephone(form.getTelephone());
		applicant.setValidEndDate(form.getValidEndDate());
		applicant.setValidStartDate(form.getValidStartDate());
		//applicant.setCardFront(form.getCardFront());
		//applicant.setCardBack(form.getCardBack());
		applicant.setStatus(TrialApplicantStatusEnum.FIRSTTRIAL.intKey());
		applicant.setCreateTime(new Date());
		applicant.setEmergencyLinkman(form.getEmergencyLinkman());
		applicant.setEmergencyTelephone(form.getEmergencyTelephone());
		applicant.setEmergencyaddress(form.getEmergencyaddress());
		applicant.setMarryStatus(form.getMarryStatus());
		applicant.setFirstName(form.getFirstName());
		if (!Util.isEmpty(form.getLastName())) {
			applicant.setLastName(form.getLastName());
		} else {
			applicant.setLastName("");
		}
		if (!Util.isEmpty(form.getFirstNameEn())) {
			applicant.setFirstNameEn(form.getFirstNameEn().substring(1));
		} else {
			applicant.setFirstNameEn("");
		}
		if (!Util.isEmpty(form.getLastNameEn())) {
			applicant.setLastNameEn(form.getLastNameEn().substring(1));
		} else {
			applicant.setLastNameEn("");
		}
		applicant.setSex(form.getSex());
		applicant.setBirthday(form.getBirthday());

		dbDao.update(applicant);

		//护照信息
		TApplicantPassportEntity passport = dbDao.fetch(TApplicantPassportEntity.class,
				Cnd.where("applicantId", "=", form.getApplicantid()));
		passport.setOpId(loginUser.getId());

		passport.setFirstName(form.getFirstName());
		if (!Util.isEmpty(form.getFirstNameEn())) {
			passport.setFirstNameEn(form.getFirstNameEn().substring(1));
		}
		passport.setLastName(form.getLastName());
		if (!Util.isEmpty(form.getLastNameEn())) {
			passport.setLastNameEn(form.getLastNameEn().substring(1));
		}
		//passport.setPassportUrl(form.getPassportUrl());
		passport.setOCRline1(form.getOCRline1());
		passport.setOCRline2(form.getOCRline2());
		passport.setBirthAddress(form.getBirthAddress());
		passport.setBirthAddressEn(form.getBirthAddressEn());
		passport.setBirthday(form.getBirthday());
		passport.setFirstName(form.getFirstName());
		passport.setIssuedDate(form.getIssuedDate());
		passport.setIssuedOrganization(form.getIssuedOrganization());
		passport.setIssuedOrganizationEn(form.getIssuedOrganizationEn());
		passport.setIssuedPlace(form.getIssuedPlace());
		passport.setIssuedPlaceEn(form.getIssuedPlaceEn());
		if (!Util.isEmpty(form.getLastName())) {
			passport.setLastName(form.getLastName());
		} else {
			passport.setLastName("");
		}
		passport.setPassport(form.getPassport());
		passport.setSex(form.getSex());
		passport.setSexEn(form.getSexEn());
		passport.setType(form.getType());
		passport.setValidEndDate(form.getValidEndDate());
		passport.setValidStartDate(form.getValidStartDate());
		passport.setValidType(form.getValidType());
		passport.setUpdateTime(new Date());
		if (!Util.isEmpty(form.getFirstNameEn())) {
			passport.setFirstNameEn(form.getFirstNameEn().substring(1));
		} else {
			passport.setFirstNameEn("");
		}
		if (!Util.isEmpty(form.getLastNameEn())) {
			passport.setLastNameEn(form.getLastNameEn().substring(1));
		} else {
			passport.setLastNameEn("");
		}

		dbDao.update(passport);

		return null;
	}

	/**
	 * 保存护照信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveEditPassport(TApplicantPassportForm form, HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TApplicantPassportEntity passport = new TApplicantPassportEntity();
		/*if (isCNChar(form.getFirstNameEn()) || isCNChar(form.getLastNameEn())) {
			//输入非法
			result.put("msg", "姓名拼音不能包含中文");
			return result;

		}*/
		if (!Util.isEmpty(form.getId())) {
			passport = dbDao.fetch(TApplicantPassportEntity.class, form.getId().longValue());
			/*TApplicantOrderJpEntity applicantorder = dbDao.fetch(TApplicantOrderJpEntity.class, passport
					.getApplicantId().longValue());*/
			TApplicantOrderJpEntity applicantorder = dbDao.fetch(TApplicantOrderJpEntity.class,
					Cnd.where("applicantId", "=", passport.getApplicantId().longValue()));
			result.put("applicantjpid", applicantorder.getApplicantId());
			result.put("applicantid", applicantorder.getApplicantId());
			result.put("orderid", applicantorder.getOrderId());

		}
		//TApplicantPassportEntity passport = dbDao.fetch(TApplicantPassportEntity.class, form.getId().longValue());
		passport.setOpId(loginUser.getId());

		passport.setFirstName(form.getFirstName());
		if (!Util.isEmpty(form.getFirstNameEn())) {
			passport.setFirstNameEn(form.getFirstNameEn().substring(1));
		}
		passport.setLastName(form.getLastName());
		if (!Util.isEmpty(form.getLastNameEn())) {
			passport.setLastNameEn(form.getLastNameEn().substring(1));
		}
		passport.setPassportUrl(form.getPassportUrl());
		passport.setOCRline1(form.getOCRline1());
		passport.setOCRline2(form.getOCRline2());
		passport.setBirthAddress(form.getBirthAddress());
		passport.setBirthAddressEn(form.getBirthAddressEn());
		passport.setBirthday(form.getBirthday());
		//passport.setFirstName(form.getFirstName());
		//passport.setFirstNameEn(passportForm.getFirstNameEn().substring(1));
		passport.setIssuedDate(form.getIssuedDate());
		passport.setIssuedOrganization(form.getIssuedOrganization());
		passport.setIssuedOrganizationEn(form.getIssuedOrganizationEn());
		passport.setIssuedPlace(form.getIssuedPlace());
		passport.setIssuedPlaceEn(form.getIssuedPlaceEn());
		//passport.setLastName(form.getLastName());
		//passport.setLastNameEn(passportForm.getLastNameEn().substring(1));
		passport.setPassport(form.getPassport());
		passport.setSex(form.getSex());
		passport.setSexEn(form.getSexEn());
		passport.setType(form.getType());
		passport.setValidEndDate(form.getValidEndDate());
		passport.setValidStartDate(form.getValidStartDate());
		passport.setValidType(form.getValidType());
		passport.setUpdateTime(new Date());
		if (!Util.isEmpty(form.getId())) {
			dbDao.update(passport);
			TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, passport.getApplicantId().longValue());
			applicant.setFirstName(form.getFirstName());
			applicant.setLastName(form.getLastName());
			if (!Util.isEmpty(form.getFirstNameEn())) {
				applicant.setFirstNameEn(form.getFirstNameEn().substring(1));
			}
			if (!Util.isEmpty(form.getLastNameEn())) {
				applicant.setLastNameEn(form.getLastNameEn().substring(1));
			}
			applicant.setSex(form.getSex());
			applicant.setBirthday(form.getBirthday());
			dbDao.update(applicant);
		} else {
			Integer orderjpid = form.getOrderid();
			if (Util.isEmpty(orderjpid)) {
				Map<String, Integer> generrateorder = generrateorder(loginUser, loginCompany);
				orderjpid = generrateorder.get("orderjpid");
			}
			TApplicantEntity applicantEntity = new TApplicantEntity();
			applicantEntity.setFirstName(form.getFirstName());
			//applicantEntity.setFirstNameEn(form.getFirstNameEn());
			applicantEntity.setLastName(form.getLastName());
			if (!Util.isEmpty(form.getFirstNameEn())) {
				applicantEntity.setFirstNameEn(form.getFirstNameEn().substring(1));
			}
			if (!Util.isEmpty(form.getLastNameEn())) {
				applicantEntity.setLastNameEn(form.getLastNameEn().substring(1));
			}
			//applicantEntity.setLastNameEn(form.getLastNameEn());
			applicantEntity.setSex(form.getSex());
			applicantEntity.setBirthday(form.getBirthday());
			TApplicantEntity insertapplicant = dbDao.insert(applicantEntity);
			TApplicantOrderJpEntity applicantjp = new TApplicantOrderJpEntity();
			applicantjp.setApplicantId(insertapplicant.getId());
			applicantjp.setOrderId(orderjpid);
			applicantjp.setBaseIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setPassIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setVisaIsCompleted(IsYesOrNoEnum.NO.intKey());
			//设置主申请人信息
			List<TApplicantOrderJpEntity> orderapplicant = dbDao.query(TApplicantOrderJpEntity.class,
					Cnd.where("orderId", "=", orderjpid), null);
			if (!Util.isEmpty(orderapplicant) && orderapplicant.size() >= 1) {

				applicantjp.setIsMainApplicant(IsYesOrNoEnum.NO.intKey());
			} else {
				//设置为主申请人
				applicantjp.setIsMainApplicant(IsYesOrNoEnum.YES.intKey());
				insertapplicant.setMainId(insertapplicant.getId());
				dbDao.update(insertapplicant);
			}
			TApplicantOrderJpEntity insertappjp = dbDao.insert(applicantjp);
			TApplicantWorkJpEntity workJp = new TApplicantWorkJpEntity();
			workJp.setApplicantId(insertappjp.getId());
			workJp.setCreateTime(new Date());
			workJp.setOpId(loginUser.getId());
			dbDao.insert(workJp);
			passport.setApplicantId(insertapplicant.getId());
			dbDao.insert(passport);
			TApplicantVisaOtherInfoEntity visaother = new TApplicantVisaOtherInfoEntity();
			visaother.setApplicantid(insertappjp.getId());
			visaother.setHotelname("参照'赴日予定表'");
			visaother.setVouchname("参照'身元保证书'");
			visaother.setInvitename("同上");
			visaother.setTraveladvice("推荐");
			dbDao.insert(visaother);
			result.put("applicantjpid", applicantjp.getApplicantId());
			result.put("applicantid", applicantjp.getApplicantId());
			result.put("orderid", applicantjp.getOrderId());

		}
		//保存历史信息
		//		savaOrUpdatePassport(form, request);
		//int update = dbDao.update(passport);
		return result;
	}

	public static boolean isCNChar(String s) {
		boolean booleanValue = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c > 128) {
				booleanValue = true;
				break;
			}
		}
		return booleanValue;
	}

	public boolean isChineseStr(String str) {
		String reg = "[\\u4e00-\\u9fa5]+";
		boolean isChinese = str.matches(reg);
		return isChinese;
	}

	/***
	 * 新增or更新申请人护照信息
	 * 
	 * 
	 * 
	 */
	//	public void savaOrUpdatePassport(TApplicantPassportForm form, HttpServletRequest request) {
	//		HttpSession session = request.getSession();
	//		TUserEntity loginUser = LoginUtil.getLoginUser(session);
	//		//根据日本订单Id查询订单Id
	//		Integer orderid = form.getOrderid();
	//		TOrderJpEntity orderJp = dbDao.fetch(TOrderJpEntity.class, Cnd.where("id", "=", orderid));
	//		//根据订单Id查询申请人护照信息历史信息
	//		TApplicantPassportHisEntity passportHis = dbDao.fetch(TApplicantPassportHisEntity.class,
	//				Cnd.where("orderId", "=", orderJp.getId()));
	//		//判断是否为空
	//		if (Util.isEmpty(passportHis)) {
	//			passportHis = new TApplicantPassportHisEntity();
	//		}
	//		//订单Id
	//		passportHis.setOrderId(orderJp.getId());
	//		//姓
	//		passportHis.setFirstName(form.getFirstName());
	//		//姓(拼音)
	//		if (!Util.isEmpty(form.getFirstNameEn())) {
	//			passportHis.setFirstNameEn(form.getFirstNameEn().substring(1));
	//		}
	//		//名
	//		passportHis.setLastName(form.getLastName());
	//		//名(拼音)
	//		if (!Util.isEmpty(form.getLastNameEn())) {
	//			passportHis.setLastNameEn(form.getLastNameEn().substring(1));
	//		}
	//		//护照类型
	//		passportHis.setType(form.getType());
	//		//护照号
	//		passportHis.setPassport(form.getPassport());
	//		//性别
	//		passportHis.setSex(form.getSex());
	//		//性别(拼音)
	//		passportHis.setSexEn(form.getSexEn());
	//		//出生地点
	//		passportHis.setBirthAddress(form.getBirthAddress());
	//		//出生地点(拼音)
	//		passportHis.setBirthAddressEn(form.getBirthAddressEn());
	//		//出生日期
	//		passportHis.setBirthday(form.getBirthday());
	//		//签发地点
	//		passportHis.setIssuedPlace(form.getIssuedPlace());
	//		//签发地点(拼音)
	//		passportHis.setIssuedPlaceEn(form.getIssuedPlaceEn());
	//		//签发日期
	//		passportHis.setIssuedDate(form.getIssuedDate());
	//		//有效期始 没用
	//		passportHis.setValidStartDate(form.getValidStartDate());
	//		//有效期至
	//		passportHis.setValidEndDate(form.getValidEndDate());
	//		//有效类型
	//		if (!Util.isEmpty(form.getValidType())) {
	//			for (IssueValidityEnum issueValidityEnum : IssueValidityEnum.values()) {
	//				if (!Util.isEmpty(form.getValidType()) && form.getValidType().equals(issueValidityEnum.key())) {
	//					passportHis.setValidType(issueValidityEnum.value());
	//				}
	//			}
	//		}
	//		//签发机关
	//		passportHis.setIssuedOrganization("公安部出入境管理局");
	//		//签发机关(拼音)
	//		passportHis.setIssuedOrganizationEn(form.getIssuedOrganizationEn());
	//		//护照地址
	//		passportHis.setPassportUrl(form.getPassportUrl());
	//		//操作人Id
	//		passportHis.setOpId(loginUser.getId());
	//		//OCR识别码第一行
	//		passportHis.setOCRline1(form.getOCRline1());
	//		//OCR识别码第二行
	//		passportHis.setOCRline2(form.getOCRline2());
	//		//新增or更新
	//		if (!Util.isEmpty(passportHis.getId())) {
	//			passportHis.setUpdateTime(new Date());
	//			dbDao.update(passportHis);
	//		} else {
	//			passportHis.setCreateTime(new Date());
	//			dbDao.insert(passportHis);
	//		}
	//	}

	/**
	 * 跳转到编辑基本信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param applicantid
	 * @param orderid
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateApplicant(Integer applicantid, Integer orderid, HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, applicantid.longValue());
		result.put("applicant", applicant);
		TApplicantOrderJpEntity orderjp = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantid));
		result.put("orderjp", orderjp);
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		if (!Util.isEmpty(applicant.getBirthday())) {
			Date birthday = applicant.getBirthday();
			String birthdayStr = sdf.format(birthday);
			result.put("birthday", birthdayStr);
		}
		if (!Util.isEmpty(applicant.getValidStartDate())) {
			Date validStartDate = applicant.getValidStartDate();
			String validStartDateStr = sdf.format(validStartDate);
			result.put("validStartDate", validStartDateStr);
		}
		if (!Util.isEmpty(applicant.getValidEndDate())) {
			Date validEndDate = applicant.getValidEndDate();
			String validEndDateStr = sdf.format(validEndDate);
			result.put("validEndDate", validEndDateStr);
		}

		if (!Util.isEmpty(applicant.getFirstNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicant.getFirstNameEn());
			result.put("firstNameEn", sb.toString());
		}
		if (!Util.isEmpty(applicant.getOtherFirstNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicant.getOtherFirstNameEn());
			result.put("otherFirstNameEn", sb.toString());
		}

		if (!Util.isEmpty(applicant.getLastNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicant.getLastNameEn());
			result.put("lastNameEn", sb.toString());
		}

		if (!Util.isEmpty(applicant.getOtherLastNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicant.getOtherLastNameEn());
			result.put("otherLastNameEn", sb.toString());
		}
		//执行保存到历史表
		//		saveOrUpdateApplicant(applicantid, orderid, request);
		String localAddr = request.getServerName();
		int localPort = request.getServerPort();
		result.put("localAddr", localAddr);
		result.put("localPort", localPort);
		result.put("websocketaddr", SIMPLE_WEBSOCKET_ADDR);
		//生成二维码
		String qrurl = "http://" + request.getServerName() + ":" + localPort + "/simplemobile/info.html?applicantid="
				+ applicantid + "&orderid=" + orderid;
		String qrCode = qrCodeService.encodeQrCode(request, qrurl);
		result.put("qrCode", qrCode);
		result.put("applicantid", applicant.getId());
		result.put("orderid", orderid);
		return result;
	}

	/***
	 * 新增or更新申请人基本信息
	 */
	//	public void saveOrUpdateApplicant(Integer applicantid, Integer orderid, HttpServletRequest request) {
	//		Map<String, Object> result = Maps.newHashMap();
	//		HttpSession session = request.getSession();
	//		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
	//		TUserEntity loginUser = LoginUtil.getLoginUser(session);
	//		//根据申请人ID获取申请人基本信息
	//		TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, applicantid.longValue());
	//		//生成申请人基础信息历史实体
	//		TApplicantHisEntity applicantHis = dbDao.fetch(TApplicantHisEntity.class, orderid.longValue());
	//		if (!Util.isEmpty(applicantHis)) {
	//			applicantHis = new TApplicantHisEntity();
	//		}
	//		//主申请人ID
	//		if (!Util.isEmpty(applicant.getMainId())) {
	//			applicantHis.setMainId(applicant.getMainId());
	//		}
	//		//登陆人ID
	//		applicantHis.setId(loginUser.getId());
	//		//登陆人
	//		applicantHis.setUserName(loginUser.getName());
	//		//申请人状态
	//		Integer status = applicant.getStatus();
	//		if (!Util.isEmpty(applicant.getStatus())) {
	//
	//			for (TrialApplicantStatusEnum stEnum : TrialApplicantStatusEnum.values())
	//				if (!Util.isEmpty(status) && status == stEnum.intKey()) {
	//					applicantHis.setStatus(stEnum.value());
	//					break;
	//				}
	//		}
	//		//姓
	//		if (!Util.isEmpty(applicant.getFirstName())) {
	//			applicantHis.setFirstName(applicant.getFirstName());
	//		}
	//		//性(拼音)
	//		if (!Util.isEmpty(applicant.getFirstNameEn())) {
	//			StringBuffer sb = new StringBuffer();
	//			sb.append("/").append(applicant.getFirstNameEn());
	//			applicantHis.setFirstNameEn(sb.toString());
	//		}
	//		//名
	//		if (!Util.isEmpty(applicant.getLastName())) {
	//			applicantHis.setLastName(applicant.getLastName());
	//		}
	//		//名(拼音)
	//		if (!Util.isEmpty(applicant.getLastNameEn())) {
	//			StringBuffer sb = new StringBuffer();
	//			sb.append("/").append(applicant.getLastNameEn());
	//			applicantHis.setLastNameEn(sb.toString());
	//		}
	//		//email
	//		if (!Util.isEmpty(applicant.getEmail())) {
	//			applicantHis.setEmail(applicant.getEmail());
	//		}
	//		//性别
	//		if (!Util.isEmpty(applicant.getSex())) {
	//			applicantHis.setSex(applicant.getSex());
	//		}
	//		//民族
	//		if (!Util.isEmpty(applicant.getNation())) {
	//			applicantHis.setNation(applicant.getNation());
	//		}
	//		//出生日期
	//		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
	//		if (!Util.isEmpty(applicant.getBirthday())) {
	//			Date birthday = applicant.getBirthday();
	//			String birthdayStr = sdf.format(birthday);
	//			applicantHis.setBirthday(birthday);
	//		}
	//		//手机号
	//		if (!Util.isEmpty(applicant.getTelephone())) {
	//			applicantHis.setTelephone(applicant.getTelephone());
	//		}
	//		//住址
	//		if (!Util.isEmpty(applicant.getAddress())) {
	//			applicantHis.setAddress(applicant.getAddress());
	//		}
	//		//身份证号
	//		if (!Util.isEmpty(applicant.getCardId())) {
	//			applicantHis.setCardId(applicant.getCardId());
	//		}
	//		//身份证正面
	//		if (!Util.isEmpty(applicant.getCardFront())) {
	//			applicantHis.setCardFront(applicant.getCardFront());
	//		}
	//		//身份证反面
	//		if (!Util.isEmpty(applicant.getCardBack())) {
	//			applicantHis.setCardBack(applicant.getCardBack());
	//		}
	//		//签发机关
	//		if (!Util.isEmpty(applicant.getIssueOrganization())) {
	//			applicantHis.setIssueOrganization(applicant.getIssueOrganization());
	//		}
	//		//有效期始
	//		if (!Util.isEmpty(applicant.getValidStartDate())) {
	//			Date validStartDate = applicant.getValidStartDate();
	//			String validStartDateStr = sdf.format(validStartDate);
	//			applicantHis.setValidStartDate(validStartDate);
	//		}
	//		//有效期至
	//		if (!Util.isEmpty(applicant.getValidEndDate())) {
	//			Date validEndDate = applicant.getValidEndDate();
	//			String validEndDateStr = sdf.format(validEndDate);
	//			applicantHis.setValidEndDate(validEndDate);
	//		}
	//		//现居住地址省份
	//		if (!Util.isEmpty(applicant.getProvince())) {
	//			applicantHis.setProvince(applicant.getProvince());
	//		}
	//		//现居住地址城市
	//		if (!Util.isEmpty(applicant.getCity())) {
	//			applicantHis.setCity(applicant.getCity());
	//		}
	//		//详细地址
	//		if (!Util.isEmpty(applicant.getDetailedAddress())) {
	//			applicantHis.setDetailedAddress(applicant.getDetailedAddress());
	//		}
	//		//曾用姓
	//		if (!Util.isEmpty(applicant.getOtherFirstName())) {
	//			applicantHis.setOtherFirstName(applicant.getOtherFirstName());
	//		}
	//		//曾用姓(拼音)
	//		if (!Util.isEmpty(applicant.getOtherFirstNameEn())) {
	//			StringBuffer sb = new StringBuffer();
	//			sb.append("/").append(applicant.getOtherFirstNameEn());
	//			applicantHis.setOtherFirstNameEn(sb.toString());
	//		}
	//		//曾用名
	//		if (!Util.isEmpty(applicant.getOtherLastName())) {
	//			applicantHis.setOtherLastName(applicant.getOtherLastName());
	//		}
	//		//曾用名(拼音)
	//		if (!Util.isEmpty(applicant.getOtherLastNameEn())) {
	//			StringBuffer sb = new StringBuffer();
	//			sb.append("/").append(applicant.getOtherLastNameEn());
	//			applicantHis.setOtherLastNameEn(sb.toString());
	//		}
	//		//紧急联系人姓名
	//		if (!Util.isEmpty(applicant.getEmergencyLinkman())) {
	//			applicantHis.setEmergencyLinkman(applicant.getEmergencyLinkman());
	//		}
	//		//紧急联系人手机
	//		if (!Util.isEmpty(applicant.getEmergencyTelephone())) {
	//			applicantHis.setEmergencyTelephone(applicant.getEmergencyTelephone());
	//		}
	//		//身份证省份
	//		if (!Util.isEmpty(applicant.getCardProvince())) {
	//			applicantHis.setCardProvince(applicant.getCardProvince());
	//		}
	//		//身份证城市
	//		if (!Util.isEmpty(applicant.getCardCity())) {
	//			applicantHis.setCardCity(applicant.getCardCity());
	//		}
	//		//是否另有国籍
	//
	//		Integer hasOtherNationality = applicant.getHasOtherNationality();
	//		if (!Util.isEmpty(hasOtherNationality)) {
	//			for (IsHasOrderOrNotEnum stEnum : IsHasOrderOrNotEnum.values())
	//				if (!Util.isEmpty(hasOtherNationality) && hasOtherNationality == stEnum.intKey()) {
	//					applicantHis.setHasOtherNationality(stEnum.value());
	//					break;
	//				}
	//		}
	//		//是否有曾用名
	//		Integer hasOtherName = applicant.getHasOtherName();
	//		for (IsHasOrderOrNotEnum stEnum : IsHasOrderOrNotEnum.values())
	//			if (!Util.isEmpty(hasOtherName) && hasOtherName == stEnum.intKey()) {
	//				applicantHis.setHasOtherName(stEnum.value());
	//				break;
	//			}
	//		//结婚证/离婚证地址
	//		if (!Util.isEmpty(applicant.getMarryUrl())) {
	//			applicantHis.setMarryUrl(applicant.getMarryUrl());
	//		}
	//		//结婚状况
	//		Integer marryStatus = applicant.getMarryStatus();
	//		for (MarryStatusEnum stEnum : MarryStatusEnum.values())
	//			if (!Util.isEmpty(marryStatus) && marryStatus == stEnum.intKey()) {
	//				applicantHis.setMarryStatus(stEnum.value());
	//				break;
	//			}
	//		//国籍
	//		if (!Util.isEmpty(applicant.getNationality())) {
	//			applicantHis.setNationality(applicant.getNationality());
	//		}
	//		//现居住地址是否与身份证相同
	//		Integer addressIsSameWithCard = applicant.getAddressIsSameWithCard();
	//		for (IsHasOrderOrNotEnum stEnum : IsHasOrderOrNotEnum.values())
	//			if (!Util.isEmpty(addressIsSameWithCard) && addressIsSameWithCard == stEnum.intKey()) {
	//				applicantHis.setAddressIsSameWithCard(stEnum.value());
	//				break;
	//			}
	//
	//		//操作人
	//		if (!Util.isEmpty(applicant.getOpId())) {
	//			applicantHis.setOpId(applicant.getOpId());
	//		}
	//		if (!Util.isEmpty(applicantHis.getId())) {
	//			applicantHis.setUpdateTime(new Date());
	//			dbDao.update(applicantHis);
	//		} else {
	//			applicantHis.setCreateTime(new Date());
	//			dbDao.insert(applicantHis);
	//		}
	//	}

	/**
	 * 跳转到签证信息页面
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param applicantid
	 * @param orderid
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object visaInfo(Integer applicantid, Integer orderid, HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		HttpSession session = request.getSession();

		System.out.println("跳转到签证信息页的applicantid:" + applicantid + "===============");
		result.put("applicantid", applicantid);
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userType = loginUser.getUserType();
		result.put("userType", userType);
		TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applicantid.longValue());
		TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantid));
		TOrderJpEntity jporderinfo = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId().longValue());
		result.put("jporderinfo", jporderinfo);
		result.put("applyorderinfo", applicantOrderJpEntity);
		result.put("marryStatus", apply.getMarryStatus());
		result.put("marryUrl", apply.getMarryUrl());
		result.put("outboundrecord", apply.getOutboundrecord());
		TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId()
				.longValue());
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderJpEntity.getOrderId().longValue());

		if (!Util.isEmpty(orderid)) {
			result.put("orderid", orderid);
		} else {
			result.put("orderid", orderJpEntity.getOrderId());
		}
		result.put("marryStatusEnum", EnumUtil.enum2(MarryStatusEnum.class));
		result.put("mainOrVice", EnumUtil.enum2(MainOrViceEnum.class));
		result.put("isOrNo", EnumUtil.enum2(IsYesOrNoEnum.class));
		result.put("applicantRelation", EnumUtil.enum2(MainApplicantRelationEnum.class));
		result.put("applicantRemark", EnumUtil.enum2(MainApplicantRemarkEnum.class));
		result.put("jobStatusEnum", EnumUtil.enum2(JobStatusEnum.class));
		result.put("mainsalevisatypeenum", EnumUtil.enum2(MainSaleVisaTypeEnum.class));
		result.put("isyesornoenum", EnumUtil.enum2(IsYesOrNoEnum.class));
		String visaInfoSqlstr = sqlManager.get("visaInfo_byApplicantId");
		Sql visaInfoSql = Sqls.create(visaInfoSqlstr);
		visaInfoSql.setParam("id", applicantid);
		Record visaInfo = dbDao.fetch(visaInfoSql);
		//获取申请人
		TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class, new Long(applicantid).intValue());
		result.put("applicant", applicantEntity);
		if (!Util.isEmpty(applicantEntity.getMainId())) {
			TApplicantEntity mainApplicant = dbDao.fetch(TApplicantEntity.class, new Long(applicantEntity.getMainId()));
			if (!Util.isEmpty(mainApplicant)) {
				result.put("mainApplicant", mainApplicant);
			}
		}
		TApplicantUnqualifiedEntity unqualifiedEntity = dbDao.fetch(TApplicantUnqualifiedEntity.class,
				Cnd.where("applicantId", "=", applicantid));
		if (!Util.isEmpty(unqualifiedEntity)) {
			result.put("unqualified", unqualifiedEntity);
		}
		//获取订单主申请人
		String sqlStr = sqlManager.get("mainApplicant_byOrderId");
		Sql applysql = Sqls.create(sqlStr);
		List<Record> records = dbDao.query(
				applysql,
				Cnd.where("oj.orderId", "=", orderJpEntity.getOrderId()).and("aoj.isMainApplicant", "=",
						IsYesOrNoEnum.YES.intKey()), null);
		//获取日本申请人信息
		result.put("orderStatus", orderEntity.getStatus());
		result.put("orderJpId", orderJpEntity.getId());
		result.put("orderJp", applicantOrderJpEntity);
		result.put("infoType", ApplicantInfoTypeEnum.VISA.intKey());
		//获取财产信息
		String wealthsqlStr = sqlManager.get("wealth_byApplicantId");
		Sql wealthsql = Sqls.create(wealthsqlStr);
		wealthsql.setParam("id", applicantOrderJpEntity.getId());
		List<Record> wealthJp = dbDao.query(wealthsql, null, null);

		/*List<TApplicantWealthJpEntity> wealthJp = dbDao.query(TApplicantWealthJpEntity.class,
				Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()), null);*/
		result.put("wealthJp", wealthJp);
		//获取工作信息
		TApplicantWorkJpEntity applicantWorkJpEntity = dbDao.fetch(TApplicantWorkJpEntity.class,
				Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()));
		if (Util.isEmpty(applicantWorkJpEntity.getUnitName())) {
			result.put("unitName", "无");
		} else {
			result.put("unitName", applicantWorkJpEntity.getUnitName());
		}
		result.put("workJp", applicantWorkJpEntity);
		result.put("mainApply", records);
		result.put("visaInfo", visaInfo);
		TApplicantVisaOtherInfoEntity visaother = dbDao.fetch(TApplicantVisaOtherInfoEntity.class,
				Cnd.where("applicantid", "=", applicantOrderJpEntity.getId()));
		if (Util.isEmpty(visaother)) {
			visaother = new TApplicantVisaOtherInfoEntity();
		}
		result.put("visaother", visaother);
		//获取所访问的ip地址
		String localAddr = request.getServerName();
		//所访问的端口
		int localPort = request.getServerPort();
		result.put("localAddr", localAddr);
		result.put("localPort", localPort);
		result.put("websocketaddr", SIMPLE_WEBSOCKET_ADDR);
		return result;
	}

	/**
	 * 保存签证信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveEditVisa(VisaEditDataForm form, HttpServletRequest request) {
		long startTime = System.currentTimeMillis();//获取当前时间
		System.out.println("进入请求了========");
		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		try {
			System.out.println("applicant:" + form.getApplicantId());
			//日本申请人
			if (!Util.isEmpty(form.getApplicantId())) {
				System.out.println("有applicantid==========");
				//日本申请人
				TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
						Cnd.where("applicantId", "=", form.getApplicantId()));
				System.out.println("查询到了日本申请人信息=======");
				TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId()
						.longValue());
				orderjp.setVisaType(form.getVisatype());
				orderjp.setVisaCounty(form.getVisacounty());
				orderjp.setIsVisit(form.getIsVisit());
				orderjp.setThreeCounty(form.getThreecounty());
				//orderjp.setLaststartdate(form.getLaststartdate());
				//orderjp.setLaststayday(form.getLaststayday());
				//orderjp.setLastreturndate(form.getLastreturndate());
				applicantOrderJpEntity.setLaststartdate(form.getLaststartdate());
				applicantOrderJpEntity.setLaststayday(form.getLaststayday());
				applicantOrderJpEntity.setLastreturndate(form.getLastreturndate());
				dbDao.update(orderjp);
				System.out.println("更新日本订单信息完毕========");
				//申请人
				TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class,
						new Long(applicantOrderJpEntity.getApplicantId()).intValue());
				//applicantEntity.setMarryStatus(form.getMarryStatus());
				applicantEntity.setMarryUrl(form.getMarryUrl());
				//applicantEntity.setMarryurltype(form.getMarryStatus());
				applicantEntity.setOutboundrecord(form.getOutboundrecord());
				//更新申请人信息
				if (Util.eq(form.getApplicant(), MainOrViceEnum.YES.intKey())) {//是主申请人
					applicantEntity.setMainId(applicantEntity.getId());
					dbDao.update(applicantEntity);
					applicantOrderJpEntity.setIsMainApplicant(MainOrViceEnum.YES.intKey());
				} else {
					applicantEntity.setMainId(null);
					dbDao.update(applicantEntity);
					applicantOrderJpEntity.setIsMainApplicant(MainOrViceEnum.NO.intKey());
				}
				System.out.println("更新申请人信息完毕========");

				//更新日本申请人信息
				if (!Util.isEmpty(form.getSameMainTrip())) {
					applicantOrderJpEntity.setSameMainTrip(form.getSameMainTrip());
				}
				if (!Util.isEmpty(form.getSameMainWealth())) {
					applicantOrderJpEntity.setSameMainWealth(form.getSameMainWealth());
					//如果申请人跟主申请人的财产信息一样，把主申请人的财产信息保存到申请人财产信息中
					if (Util.eq(form.getSameMainWealth(), IsYesOrNoEnum.YES.intKey())) {
						List<TApplicantWealthJpEntity> beforeList = dbDao.query(TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()), null);
						if (beforeList.size() > 0) {
							dbDao.delete(beforeList);
						}
						System.out.println("副申请人财产信息同主申请人========");

					} else {
						//添加财产信息
						long wealthTime = System.currentTimeMillis();
						List<TApplicantWealthJpEntity> beforeList = dbDao.query(TApplicantWealthJpEntity.class,
								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()), null);
						if (beforeList.size() > 0) {
							dbDao.delete(beforeList);
						}

						List<Map<String, WealthEntity>> wealthInfoList = form.getWealthInfoObject();
						if (wealthInfoList.size() > 0) {
							for (Map<String, WealthEntity> map : wealthInfoList) {
								for (String sequence : map.keySet()) {
									WealthEntity wealthEntity = map.get(sequence);
									String wealthtitle = wealthEntity.getWealthtitle();
									String wealthvalue = wealthEntity.getWealthvalue();
									String wealthtype = wealthEntity.getWealthtype();
									String wealthname = wealthEntity.getWealthname();

									/*if (Util.isEmpty(wealthtype)
											|| (!Util.isEmpty(wealthtype) && !Util.isEmpty(wealthvalue))) {*/
									TApplicantWealthJpEntity wealthjp = new TApplicantWealthJpEntity();
									wealthjp.setSequence(Integer.valueOf(sequence));
									wealthjp.setBankflowfree(wealthtitle);
									wealthjp.setDetails(wealthvalue);
									wealthjp.setType(wealthtype);
									wealthjp.setApplicantId(applicantOrderJpEntity.getId());
									wealthjp.setCreateTime(new Date());
									dbDao.insert(wealthjp);
									//}
								}
							}

						}

						//insertorupdateWealthinfo(form, applicantOrderJpEntity, loginUser);
						long wealthTime2 = System.currentTimeMillis();
						System.out.println("财产信息所用时间：" + (wealthTime2 - wealthTime) + "ms");
					}
				}
				TApplicantVisaOtherInfoEntity otherinfo = dbDao.fetch(TApplicantVisaOtherInfoEntity.class,
						Cnd.where("applicantid", "=", applicantOrderJpEntity.getId()));
				if (Util.isEmpty(otherinfo)) {
					otherinfo = new TApplicantVisaOtherInfoEntity();
				}
				otherinfo.setApplicantid(applicantOrderJpEntity.getId());
				otherinfo.setHotelname(form.getHotelname());
				otherinfo.setHotelphone(form.getHotelphone());
				otherinfo.setHoteladdress(form.getHoteladdress());
				otherinfo.setVouchname(form.getVouchname());
				otherinfo.setIsname(form.getIsname());
				otherinfo.setVouchnameen(form.getVouchnameen());
				otherinfo.setVouchphone(form.getVouchphone());
				otherinfo.setVouchaddress(form.getVouchaddress());
				otherinfo.setVouchbirth(form.getVouchbirth());
				otherinfo.setVouchsex(form.getVouchsex());
				otherinfo.setVouchmainrelation(form.getVouchmainrelation());
				otherinfo.setVouchjob(form.getVouchjob());
				otherinfo.setVouchcountry(form.getVouchcountry());
				otherinfo.setInvitename(form.getInvitename());
				otherinfo.setInvitephone(form.getInvitephone());
				otherinfo.setInviteaddress(form.getInviteaddress());
				otherinfo.setInvitebirth(form.getInvitebirth());
				otherinfo.setInvitesex(form.getInvitesex());
				otherinfo.setInvitemainrelation(form.getInvitemainrelation());
				otherinfo.setInvitejob(form.getInvitejob());
				otherinfo.setInvitecountry(form.getInvitecountry());
				otherinfo.setTraveladvice(form.getTraveladvice());
				otherinfo.setIsyaoqing(form.getIsyaoqing());
				if (!Util.isEmpty(otherinfo.getId())) {
					dbDao.update(otherinfo);
				} else {
					dbDao.insert(otherinfo);
				}
				System.out.println("其他信息完毕========");
				//更新工作信息
				TApplicantWorkJpEntity applicantWorkJpEntity = dbDao.fetch(TApplicantWorkJpEntity.class,
						Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()));
				Integer careerStatus = form.getCareerStatus();
				if (!Util.isEmpty(careerStatus)) {
					long wealthTime = System.currentTimeMillis();
					toUpdateWorkJp(careerStatus, applicantWorkJpEntity, applicantOrderJpEntity, session);
					long wealthTime2 = System.currentTimeMillis();
					System.out.println("资料类型所用时间：" + (wealthTime2 - wealthTime) + "ms");
				} else {
					Integer applicantJpId = applicantOrderJpEntity.getId();
					List<TApplicantFrontPaperworkJpEntity> frontListDB = dbDao.query(
							TApplicantFrontPaperworkJpEntity.class, Cnd.where("applicantId", "=", applicantJpId), null);
					List<TApplicantVisaPaperworkJpEntity> visaListDB = dbDao.query(
							TApplicantVisaPaperworkJpEntity.class, Cnd.where("applicantId", "=", applicantJpId), null);
					if (!Util.isEmpty(frontListDB)) {//如果库中有数据，则删掉
						dbDao.delete(frontListDB);
					}
					if (!Util.isEmpty(visaListDB)) {
						dbDao.delete(visaListDB);
					}
					applicantWorkJpEntity.setPrepareMaterials(null);
				}
				applicantWorkJpEntity.setUnitName(form.getUnitName());
				applicantWorkJpEntity.setPosition(form.getPosition());
				applicantWorkJpEntity.setCareerStatus(form.getCareerStatus());
				applicantWorkJpEntity.setName(form.getName());
				applicantWorkJpEntity.setAddress(form.getAddress());
				applicantWorkJpEntity.setTelephone(form.getTelephone());
				applicantWorkJpEntity.setUpdateTime(new Date());
				dbDao.update(applicantWorkJpEntity);
				System.out.println("工作信息完毕========");
				if (!Util.isEmpty(form.getMainRelation())) {
					applicantOrderJpEntity.setMainRelation(form.getMainRelation());
				} else {
					applicantOrderJpEntity.setMainRelation(null);
				}
				if (!Util.isEmpty(form.getRelationRemark())) {
					applicantOrderJpEntity.setRelationRemark(form.getRelationRemark());
				} else {
					applicantOrderJpEntity.setRelationRemark(null);
				}
				//applicantOrderJpEntity.setMarryStatus(visaForm.getMarryStatus());
				//applicantOrderJpEntity.setMarryUrl(visaForm.getMarryUrl());
				dbDao.update(applicantOrderJpEntity);

			}
			long endTime = System.currentTimeMillis();
			System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
			System.out.println("-------------------------");
		} catch (Exception e) {
			String simplename = e.getClass().getSimpleName();
			if ("ClientAbortException".equals(simplename)) {
				System.out.println("错误出现了！");
			} else {
				e.printStackTrace();
			}

			return null;
		}
		System.out.println("*****************");
		return null;
	}

	public Object insertorupdateWealthinfo(VisaEditDataForm form, TApplicantOrderJpEntity applicantOrderJpEntity,
			TUserEntity loginUser) {
		//银行流水
		if (!Util.isEmpty(form.getBankflow())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "银行流水"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getBankflow());
				applicantWealthJpEntity.setBankflowfree(form.getBankflowfree());
				applicantWealthJpEntity.setSequence(1);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getBankflow());
				wealthJp.setBankflowfree(form.getBankflowfree());
				wealthJp.setType("银行流水");
				wealthJp.setSequence(1);
				wealthJp.setCreateTime(new Date());
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "银行流水"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("银行流水完毕========");
		//车产
		if (!Util.isEmpty(form.getVehicle())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
					TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
							ApplicantJpWealthEnum.CAR.value()));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getVehicle());
				applicantWealthJpEntity.setVehiclefree(form.getVehiclefree());
				applicantWealthJpEntity.setSequence(2);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getVehicle());
				wealthJp.setVehiclefree(form.getVehiclefree());
				wealthJp.setType(ApplicantJpWealthEnum.CAR.value());
				wealthJp.setCreateTime(new Date());
				wealthJp.setSequence(2);
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
					TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
							ApplicantJpWealthEnum.CAR.value()));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("车产完毕========");
		//房产
		if (!Util.isEmpty(form.getHouseProperty())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
					TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
							ApplicantJpWealthEnum.HOME.value()));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getHouseProperty());
				applicantWealthJpEntity.setHousePropertyfree(form.getHousePropertyfree());
				applicantWealthJpEntity.setSequence(3);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getHouseProperty());
				wealthJp.setHousePropertyfree(form.getHousePropertyfree());
				wealthJp.setType(ApplicantJpWealthEnum.HOME.value());
				wealthJp.setCreateTime(new Date());
				wealthJp.setSequence(3);
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
					TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
							ApplicantJpWealthEnum.HOME.value()));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("房产完毕========");
		//理财
		if (!Util.isEmpty(form.getFinancial())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
					TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
							ApplicantJpWealthEnum.LICAI.value()));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getFinancial());
				applicantWealthJpEntity.setFinancialfree(form.getFinancialfree());
				applicantWealthJpEntity.setSequence(4);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getFinancial());
				wealthJp.setFinancialfree(form.getFinancialfree());
				wealthJp.setType(ApplicantJpWealthEnum.LICAI.value());
				wealthJp.setCreateTime(new Date());
				wealthJp.setSequence(4);
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
					TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
							ApplicantJpWealthEnum.LICAI.value()));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("理财完毕========");
		//在职证明
		if (!Util.isEmpty(form.getCertificate())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "在职证明"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getCertificate());
				applicantWealthJpEntity.setCertificatefree(form.getCertificatefree());
				applicantWealthJpEntity.setSequence(5);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getCertificate());
				wealthJp.setCertificatefree(form.getCertificatefree());
				wealthJp.setType("在职证明");
				wealthJp.setCreateTime(new Date());
				wealthJp.setSequence(5);
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "在职证明"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("在职证明完毕========");
		//银行存款
		if (!Util.isEmpty(form.getDeposit())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "银行存款"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getDeposit());
				applicantWealthJpEntity.setDepositfree(form.getDepositfree());
				applicantWealthJpEntity.setSequence(6);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getDeposit());
				wealthJp.setDepositfree(form.getDepositfree());
				wealthJp.setType("银行存款");
				wealthJp.setSequence(6);
				wealthJp.setCreateTime(new Date());
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "银行存款"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("银行存款完毕========");
		//税单
		if (!Util.isEmpty(form.getTaxbill())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "税单"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getTaxbill());
				applicantWealthJpEntity.setTaxbillfree(form.getTaxbillfree());
				applicantWealthJpEntity.setSequence(7);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getTaxbill());
				wealthJp.setTaxbillfree(form.getTaxbillfree());
				wealthJp.setType("税单");
				wealthJp.setSequence(7);
				wealthJp.setCreateTime(new Date());
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "税单"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("税单完毕========");
		//完税证明
		if (!Util.isEmpty(form.getTaxproof())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "完税证明"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getTaxproof());
				applicantWealthJpEntity.setTaxprooffree(form.getTaxprooffree());
				applicantWealthJpEntity.setSequence(8);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getTaxproof());
				wealthJp.setTaxprooffree(form.getTaxprooffree());
				wealthJp.setType("完税证明");
				wealthJp.setSequence(8);
				wealthJp.setCreateTime(new Date());
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "完税证明"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("完税证明完毕========");
		//银行金卡
		if (!Util.isEmpty(form.getGoldcard())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "银行金卡"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getGoldcard());
				applicantWealthJpEntity.setGoldcardfree(form.getGoldcardfree());
				applicantWealthJpEntity.setSequence(9);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getGoldcard());
				wealthJp.setGoldcardfree(form.getGoldcardfree());
				wealthJp.setType("银行金卡");
				wealthJp.setSequence(9);
				wealthJp.setCreateTime(new Date());
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "银行金卡"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("银行金卡完毕========");
		//特定高校在读生
		if (!Util.isEmpty(form.getReadstudent())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "特定高校在读生"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getReadstudent());
				applicantWealthJpEntity.setReadstudentfree(form.getReadstudentfree());
				applicantWealthJpEntity.setSequence(10);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getReadstudent());
				wealthJp.setReadstudentfree(form.getReadstudentfree());
				wealthJp.setType("特定高校在读生");
				wealthJp.setSequence(10);
				wealthJp.setCreateTime(new Date());
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "特定高校在读生"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("在读生完毕========");
		//特定高校毕业生
		if (!Util.isEmpty(form.getGraduate())) {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "特定高校毕业生"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				applicantWealthJpEntity.setDetails(form.getGraduate());
				applicantWealthJpEntity.setGraduatefree(form.getGraduatefree());
				applicantWealthJpEntity.setSequence(11);
				dbDao.update(applicantWealthJpEntity);
			} else {
				TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
				wealthJp.setApplicantId(applicantOrderJpEntity.getId());
				wealthJp.setDetails(form.getGraduate());
				wealthJp.setGraduatefree(form.getGraduatefree());
				wealthJp.setType("特定高校毕业生");
				wealthJp.setSequence(11);
				wealthJp.setCreateTime(new Date());
				wealthJp.setOpId(loginUser.getId());
				dbDao.insert(wealthJp);
			}
		} else {
			TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(TApplicantWealthJpEntity.class,
					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=", "特定高校毕业生"));
			if (!Util.isEmpty(applicantWealthJpEntity)) {
				dbDao.delete(applicantWealthJpEntity);
			}
		}
		System.out.println("财产信息完毕=====");
		return null;
	}

	/***
	 * TODO(签证信息历史信息保存)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param careerStatus
	 * @param applicantWorkJpEntity
	 * @param applicantOrderJpEntity
	 * @param session TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public void saveOrUpdateVisa(VisaEditDataForm form, HttpServletRequest request) {

		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		//是否是主申请人
		//		if (!Util.isEmpty(form.getApplicantId())) {
		//			//根据订单Id获取签证信息历史
		//			TTouristVisaHisEntity tTouristVisaHisEntity = dbDao.fetch(TTouristVisaHisEntity.class,
		//					Cnd.where("orderId", "=", form.getOrderid()));
		//			if (Util.isEmpty(tTouristVisaHisEntity)) {
		//				tTouristVisaHisEntity = new TTouristVisaHisEntity();
		//			}
		//			//订单ID
		//			tTouristVisaHisEntity.setOrderId(form.getOrderid());
		//			//用户名
		//			tTouristVisaHisEntity.setUserName(loginUser.getName());
		//			//用户ID
		//			tTouristVisaHisEntity.setUserId(loginUser.getId());
		//			//婚姻状况
		//			Integer marryStatus = form.getMarryStatus();
		//			if (!Util.isEmpty(marryStatus)) {
		//				for (MarryStatusEnum marryEnum : MarryStatusEnum.values()) {
		//					if (!Util.isEmpty(marryStatus) && marryStatus.equals(marryEnum.key())) {
		//						tTouristVisaHisEntity.setMarryStatus(marryEnum.value());
		//					}
		//				}
		//			}
		//			//日本申请人
		//			TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
		//					Cnd.where("applicantId", "=", form.getApplicantId()));
		//			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId().longValue());
		//			//签证类型
		//			Integer visatype = form.getVisatype();
		//			if (!Util.isEmpty(visatype)) {
		//				for (IssueValidityEnum marryEnum : IssueValidityEnum.values()) {
		//					if (!Util.isEmpty(marryStatus) && marryStatus.equals(marryEnum.key())) {
		//						tTouristVisaHisEntity.setVisaType(marryEnum.value());
		//					}
		//				}
		//			}
		//			//签证县
		//			tTouristVisaHisEntity.setVisaCounty(form.getVisacounty());
		//			//过去三年是否访问
		//			if (!Util.isEmpty(form.getIsVisit())) {
		//				for (IssueValidityEnum marryEnum : IssueValidityEnum.values()) {
		//					if (!Util.isEmpty(form.getIsVisit()) && form.getIsVisit().equals(marryEnum.key())) {
		//						tTouristVisaHisEntity.setIsVisit(marryEnum.value());
		//					}
		//				}
		//			}
		//			//过去三年访问过的县
		//			tTouristVisaHisEntity.setThreeCounty(form.getThreecounty());
		//			//上次赴日时间
		//			tTouristVisaHisEntity.setLaststartdate(form.getLaststartdate());
		//			//上次停留天数
		//			tTouristVisaHisEntity.setLaststayday(form.getLaststayday());
		//			//上次返回时间
		//			tTouristVisaHisEntity.setLastreturndate(form.getLastreturndate());
		//			//结婚证/离婚证地址
		//			tTouristVisaHisEntity.setMarryUrl(form.getMarryUrl());
		//			if (!Util.isEmpty(form.getAddApply())) {
		//				if (Util.eq(form.getAddApply(), 2)) {
		//					tTouristVisaHisEntity.setStatus(TrialApplicantStatusEnum.FillCompleted.intKey());
		//				}
		//			}
		//			//主申请人
		//			if (!Util.isEmpty(tTouristVisaHisEntity.getMainId())) {
		//				TApplicantEntity mainApplicant = dbDao.fetch(TApplicantEntity.class,
		//						new Long(applicantEntity.getMainId()).intValue());
		//			}
		//			//更新申请人信息
		//			if (Util.eq(form.getApplicant(), MainOrViceEnum.YES.intKey())) {//是主申请人
		//				applicantEntity.setMainId(applicantEntity.getId());
		//				dbDao.update(applicantEntity);
		//			} else {
		//				if (!Util.isEmpty(form.getMainApplicant())) {
		//					applicantEntity.setMainId(form.getMainApplicant());
		//				}
		//			}
		//			if (Util.eq(applicantEntity.getId(), applicantEntity.getMainId())) {
		//				applicantOrderJpEntity.setIsMainApplicant(MainOrViceEnum.YES.intKey());
		//			} else {
		//				applicantOrderJpEntity.setIsMainApplicant(MainOrViceEnum.NO.intKey());
		//			}
		//
		//			//更新日本申请人信息
		//			if (!Util.isEmpty(form.getSameMainTrip())) {
		//				applicantOrderJpEntity.setSameMainTrip(form.getSameMainTrip());
		//			}
		//			if (!Util.isEmpty(form.getSameMainWealth())) {
		//				applicantOrderJpEntity.setSameMainWealth(form.getSameMainWealth());
		//				//如果申请人跟主申请人的财产信息一样，把主申请人的财产信息保存到申请人财产信息中
		//				if (Util.eq(form.getSameMainWealth(), IsYesOrNoEnum.YES.intKey())) {
		//					if (!Util.isEmpty(applicantEntity.getMainId())) {
		//						TApplicantEntity mainApplicant = dbDao.fetch(TApplicantEntity.class,
		//								new Long(applicantEntity.getMainId()).intValue());
		//						TApplicantOrderJpEntity mainAppyJp = dbDao.fetch(TApplicantOrderJpEntity.class,
		//								Cnd.where("applicantId", "=", mainApplicant.getId()));
		//						//获取主申请人的财产信息
		//						//银行流水
		//						TApplicantWealthJpEntity mainApplyWealthJp = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", mainAppyJp.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.BANK.value()));
		//						TApplicantWealthJpEntity applyWealthJp = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.BANK.value()));
		//						if (!Util.isEmpty(mainApplyWealthJp)) {
		//							if (!Util.isEmpty(applyWealthJp)) {//如果申请人有银行流水信息，则更新
		//								if (!Util.isEmpty(mainApplyWealthJp.getDetails())) {
		//									applyWealthJp.setDetails(mainApplyWealthJp.getDetails());
		//									applyWealthJp.setApplicantId(applicantOrderJpEntity.getId());
		//									applyWealthJp.setOpId(loginUser.getId());
		//									applyWealthJp.setUpdateTime(new Date());
		//									dbDao.update(applyWealthJp);
		//								}
		//							} else {//没有则添加
		//								TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
		//								applyWealth.setType("银行流水");
		//								applyWealth.setDetails(mainApplyWealthJp.getDetails());
		//								applyWealth.setApplicantId(applicantOrderJpEntity.getId());
		//								applyWealth.setOpId(loginUser.getId());
		//								applyWealth.setCreateTime(new Date());
		//								dbDao.insert(applyWealth);
		//							}
		//						}
		//						//车产
		//						TApplicantWealthJpEntity applicantWealthJpCar = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.CAR.value()));
		//						TApplicantWealthJpEntity mainApplyWealthJpCar = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", mainAppyJp.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.CAR.value()));
		//						if (!Util.isEmpty(mainApplyWealthJpCar)) {
		//							if (!Util.isEmpty(applicantWealthJpCar)) {//如果申请人有银行流水信息，则更新
		//								if (!Util.isEmpty(mainApplyWealthJpCar.getDetails())) {
		//									applicantWealthJpCar.setDetails(mainApplyWealthJpCar.getDetails());
		//									applicantWealthJpCar.setApplicantId(applicantOrderJpEntity.getId());
		//									applicantWealthJpCar.setOpId(loginUser.getId());
		//									applicantWealthJpCar.setUpdateTime(new Date());
		//									dbDao.update(applicantWealthJpCar);
		//								}
		//							} else {
		//								TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
		//								applyWealth.setType("车产");
		//								applyWealth.setDetails(mainApplyWealthJpCar.getDetails());
		//								applyWealth.setApplicantId(applicantOrderJpEntity.getId());
		//								applyWealth.setOpId(loginUser.getId());
		//								applyWealth.setCreateTime(new Date());
		//								dbDao.insert(applyWealth);
		//							}
		//						}
		//
		//						//房产
		//						TApplicantWealthJpEntity applicantWealthJpHome = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.HOME.value()));
		//						TApplicantWealthJpEntity mainApplyWealthJpHome = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", mainAppyJp.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.HOME.value()));
		//						if (!Util.isEmpty(mainApplyWealthJpHome)) {
		//							if (!Util.isEmpty(applicantWealthJpHome)) {//如果申请人有银行流水信息，则更新
		//								if (!Util.isEmpty(mainApplyWealthJpHome.getDetails())) {
		//									applicantWealthJpHome.setDetails(mainApplyWealthJpHome.getDetails());
		//									applicantWealthJpHome.setApplicantId(applicantOrderJpEntity.getId());
		//									applicantWealthJpHome.setOpId(loginUser.getId());
		//									applicantWealthJpHome.setUpdateTime(new Date());
		//									dbDao.update(applicantWealthJpHome);
		//								}
		//							} else {
		//								TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
		//								applyWealth.setType("房产");
		//								applyWealth.setDetails(mainApplyWealthJpHome.getDetails());
		//								applyWealth.setApplicantId(applicantOrderJpEntity.getId());
		//								applyWealth.setOpId(loginUser.getId());
		//								applyWealth.setCreateTime(new Date());
		//								dbDao.insert(applyWealth);
		//							}
		//						}
		//
		//						//理财
		//						TApplicantWealthJpEntity applicantWealthJpLi = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.LICAI.value()));
		//						TApplicantWealthJpEntity mainApplyWealthJpLi = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", mainAppyJp.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.LICAI.value()));
		//						if (!Util.isEmpty(mainApplyWealthJpLi)) {
		//							if (!Util.isEmpty(applicantWealthJpLi)) {//如果申请人有银行流水信息，则更新
		//								if (!Util.isEmpty(mainApplyWealthJpLi.getDetails())) {
		//									applicantWealthJpLi.setDetails(mainApplyWealthJpLi.getDetails());
		//									applicantWealthJpLi.setApplicantId(applicantOrderJpEntity.getId());
		//									applicantWealthJpLi.setOpId(loginUser.getId());
		//									applicantWealthJpLi.setUpdateTime(new Date());
		//									dbDao.update(applicantWealthJpLi);
		//								}
		//							} else {
		//								TApplicantWealthJpEntity applyWealth = new TApplicantWealthJpEntity();
		//								applyWealth.setType("理财");
		//								applyWealth.setDetails(mainApplyWealthJpLi.getDetails());
		//								applyWealth.setApplicantId(applicantOrderJpEntity.getId());
		//								applyWealth.setOpId(loginUser.getId());
		//								applyWealth.setCreateTime(new Date());
		//								dbDao.insert(applyWealth);
		//							}
		//						}
		//
		//					}
		//				} else {
		//					//添加财产信息
		//					TApplicantWealthJpEntity wealthJp = new TApplicantWealthJpEntity();
		//					wealthJp.setApplicantId(applicantOrderJpEntity.getId());
		//					//银行流水
		//					if (!Util.isEmpty(form.getDeposit())) {
		//						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.BANK.value()));
		//						if (!Util.isEmpty(applicantWealthJpEntity)) {
		//							applicantWealthJpEntity.setDetails(form.getDeposit());
		//							dbDao.update(applicantWealthJpEntity);
		//						} else {
		//							wealthJp.setDetails(form.getDeposit());
		//							wealthJp.setType(ApplicantJpWealthEnum.BANK.value());
		//							wealthJp.setCreateTime(new Date());
		//							wealthJp.setOpId(loginUser.getId());
		//							dbDao.insert(wealthJp);
		//						}
		//					} else {
		//						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.BANK.value()));
		//						if (!Util.isEmpty(applicantWealthJpEntity)) {
		//							dbDao.delete(applicantWealthJpEntity);
		//						}
		//					}
		//					//车产
		//					if (!Util.isEmpty(form.getVehicle())) {
		//						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.CAR.value()));
		//						if (!Util.isEmpty(applicantWealthJpEntity)) {
		//							applicantWealthJpEntity.setDetails(form.getVehicle());
		//							dbDao.update(applicantWealthJpEntity);
		//						} else {
		//							wealthJp.setDetails(form.getVehicle());
		//							wealthJp.setType(ApplicantJpWealthEnum.CAR.value());
		//							wealthJp.setCreateTime(new Date());
		//							wealthJp.setOpId(loginUser.getId());
		//							dbDao.insert(wealthJp);
		//						}
		//					} else {
		//						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.CAR.value()));
		//						if (!Util.isEmpty(applicantWealthJpEntity)) {
		//							dbDao.delete(applicantWealthJpEntity);
		//						}
		//					}
		//					//房产
		//					if (!Util.isEmpty(form.getHouseProperty())) {
		//						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.HOME.value()));
		//						if (!Util.isEmpty(applicantWealthJpEntity)) {
		//							applicantWealthJpEntity.setDetails(form.getHouseProperty());
		//							dbDao.update(applicantWealthJpEntity);
		//						} else {
		//							wealthJp.setDetails(form.getHouseProperty());
		//							wealthJp.setType(ApplicantJpWealthEnum.HOME.value());
		//							wealthJp.setCreateTime(new Date());
		//							wealthJp.setOpId(loginUser.getId());
		//							dbDao.insert(wealthJp);
		//						}
		//					} else {
		//						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.HOME.value()));
		//						if (!Util.isEmpty(applicantWealthJpEntity)) {
		//							dbDao.delete(applicantWealthJpEntity);
		//						}
		//					}
		//					//理财
		//					if (!Util.isEmpty(form.getFinancial())) {
		//						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.LICAI.value()));
		//						if (!Util.isEmpty(applicantWealthJpEntity)) {
		//							applicantWealthJpEntity.setDetails(form.getFinancial());
		//							dbDao.update(applicantWealthJpEntity);
		//						} else {
		//							wealthJp.setDetails(form.getFinancial());
		//							wealthJp.setType(ApplicantJpWealthEnum.LICAI.value());
		//							wealthJp.setCreateTime(new Date());
		//							wealthJp.setOpId(loginUser.getId());
		//							dbDao.insert(wealthJp);
		//						}
		//					} else {
		//						TApplicantWealthJpEntity applicantWealthJpEntity = dbDao.fetch(
		//								TApplicantWealthJpEntity.class,
		//								Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()).and("type", "=",
		//										ApplicantJpWealthEnum.LICAI.value()));
		//						if (!Util.isEmpty(applicantWealthJpEntity)) {
		//							dbDao.delete(applicantWealthJpEntity);
		//						}
		//					}
		//				}
		//			}
		//			TApplicantVisaOtherInfoEntity otherinfo = dbDao.fetch(TApplicantVisaOtherInfoEntity.class,
		//					Cnd.where("applicantid", "=", applicantOrderJpEntity.getId()));
		//			if (Util.isEmpty(otherinfo)) {
		//				otherinfo = new TApplicantVisaOtherInfoEntity();
		//			}
		//			otherinfo.setApplicantid(applicantOrderJpEntity.getId());
		//			otherinfo.setHotelname(form.getHotelname());
		//			otherinfo.setHotelphone(form.getHotelphone());
		//			otherinfo.setHoteladdress(form.getHoteladdress());
		//			otherinfo.setVouchname(form.getVouchname());
		//			otherinfo.setIsname(form.getIsname());
		//			otherinfo.setVouchnameen(form.getVouchnameen());
		//			otherinfo.setVouchphone(form.getVouchphone());
		//			otherinfo.setVouchaddress(form.getVouchaddress());
		//			otherinfo.setVouchbirth(form.getVouchbirth());
		//			otherinfo.setVouchsex(form.getVouchsex());
		//			otherinfo.setVouchmainrelation(form.getVouchmainrelation());
		//			otherinfo.setVouchjob(form.getVouchjob());
		//			otherinfo.setVouchcountry(form.getVouchcountry());
		//			otherinfo.setInvitename(form.getInvitename());
		//			otherinfo.setInvitephone(form.getInvitephone());
		//			otherinfo.setInviteaddress(form.getInviteaddress());
		//			otherinfo.setInvitebirth(form.getInvitebirth());
		//			otherinfo.setInvitesex(form.getInvitesex());
		//			otherinfo.setInvitemainrelation(form.getInvitemainrelation());
		//			otherinfo.setInvitejob(form.getInvitejob());
		//			otherinfo.setInvitecountry(form.getInvitecountry());
		//			otherinfo.setTraveladvice(form.getTraveladvice());
		//			otherinfo.setIsyaoqing(form.getIsyaoqing());
		//			if (!Util.isEmpty(otherinfo.getId())) {
		//				dbDao.update(otherinfo);
		//			} else {
		//				dbDao.insert(otherinfo);
		//			}
		//			//更新工作信息
		//			TApplicantWorkJpEntity applicantWorkJpEntity = dbDao.fetch(TApplicantWorkJpEntity.class,
		//					Cnd.where("applicantId", "=", applicantOrderJpEntity.getId()));
		//			Integer careerStatus = form.getCareerStatus();
		//			if (!Util.isEmpty(careerStatus)) {
		//				toUpdateWorkJp(careerStatus, applicantWorkJpEntity, applicantOrderJpEntity, session);
		//			} else {
		//				Integer applicantJpId = applicantOrderJpEntity.getId();
		//				List<TApplicantFrontPaperworkJpEntity> frontListDB = dbDao.query(
		//						TApplicantFrontPaperworkJpEntity.class, Cnd.where("applicantId", "=", applicantJpId), null);
		//				List<TApplicantVisaPaperworkJpEntity> visaListDB = dbDao.query(TApplicantVisaPaperworkJpEntity.class,
		//						Cnd.where("applicantId", "=", applicantJpId), null);
		//				if (!Util.isEmpty(frontListDB)) {//如果库中有数据，则删掉
		//					dbDao.delete(frontListDB);
		//				}
		//				if (!Util.isEmpty(visaListDB)) {
		//					dbDao.delete(visaListDB);
		//				}
		//				applicantWorkJpEntity.setPrepareMaterials(null);
		//			}
		//			applicantWorkJpEntity.setUnitName(form.getUnitName());
		//			applicantWorkJpEntity.setPosition(form.getPosition());
		//			applicantWorkJpEntity.setCareerStatus(form.getCareerStatus());
		//			applicantWorkJpEntity.setName(form.getName());
		//			applicantWorkJpEntity.setAddress(form.getAddress());
		//			applicantWorkJpEntity.setTelephone(form.getTelephone());
		//			applicantWorkJpEntity.setUpdateTime(new Date());
		//			dbDao.update(applicantWorkJpEntity);
		//			if (!Util.isEmpty(form.getMainRelation())) {
		//				applicantOrderJpEntity.setMainRelation(form.getMainRelation());
		//			} else {
		//				applicantOrderJpEntity.setMainRelation(null);
		//			}
		//			if (!Util.isEmpty(form.getRelationRemark())) {
		//				applicantOrderJpEntity.setRelationRemark(form.getRelationRemark());
		//			} else {
		//				applicantOrderJpEntity.setRelationRemark(null);
		//			}
		//			//applicantOrderJpEntity.setMarryStatus(visaForm.getMarryStatus());
		//			//applicantOrderJpEntity.setMarryUrl(visaForm.getMarryUrl());
		//			int update = dbDao.update(applicantOrderJpEntity);
		//
		//		}

	}

	public void toUpdateWorkJp(int careerStatus, TApplicantWorkJpEntity applicantWorkJpEntity,
			TApplicantOrderJpEntity applicantOrderJpEntity, HttpSession session) {

		Integer applicantJpId = applicantOrderJpEntity.getId();
		/*List<TApplicantFrontPaperworkJpEntity> frontListDB = dbDao.query(TApplicantFrontPaperworkJpEntity.class,
					Cnd.where("applicantId", "=", applicantJpId), null);*/
		List<TApplicantVisaPaperworkJpEntity> visaListDB = dbDao.query(TApplicantVisaPaperworkJpEntity.class,
				Cnd.where("applicantId", "=", applicantJpId), null);
		/*if (!Util.isEmpty(frontListDB)) {//如果库中有数据，则删掉
				dbDao.delete(frontListDB);
			}*/
		if (!Util.isEmpty(visaListDB)) {
			dbDao.delete(visaListDB);
		}
		if (Util.eq(careerStatus, JobStatusEnum.WORKING_STATUS.intKey())) {//在职
			//dbDao.insert(toInsertFrontJp(JobStatusEnum.WORKING_STATUS.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.WORKING_STATUS.intKey(), applicantJpId, session));

			StringBuilder sbWork = new StringBuilder();
			for (JobStatusWorkingEnum jobWorking : JobStatusWorkingEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
		if (Util.eq(careerStatus, JobStatusEnum.RETIREMENT_STATUS.intKey())) {//退休
			//dbDao.insert(toInsertFrontJp(JobStatusEnum.RETIREMENT_STATUS.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.RETIREMENT_STATUS.intKey(), applicantJpId, session));
			StringBuilder sbWork = new StringBuilder();
			for (JobStatusRetirementEnum jobWorking : JobStatusRetirementEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
		if (Util.eq(careerStatus, JobStatusEnum.FREELANCE_STATUS.intKey())) {//自由职业
			//dbDao.insert(toInsertFrontJp(JobStatusEnum.FREELANCE_STATUS.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.FREELANCE_STATUS.intKey(), applicantJpId, session));
			StringBuilder sbWork = new StringBuilder();
			for (JobStatusFreeEnum jobWorking : JobStatusFreeEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			int length = workStatus.length();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
		if (Util.eq(careerStatus, JobStatusEnum.student_status.intKey())) {//学生
			//dbDao.insert(toInsertFrontJp(JobStatusEnum.student_status.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.student_status.intKey(), applicantJpId, session));
			StringBuilder sbWork = new StringBuilder();
			for (JobStatusStudentEnum jobWorking : JobStatusStudentEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
		if (Util.eq(careerStatus, JobStatusEnum.Preschoolage_status.intKey())) {//学龄前
			//dbDao.insert(toInsertFrontJp(JobStatusEnum.Preschoolage_status.intKey(), applicantJpId, session));
			dbDao.insert(toInsertVisaJp(JobStatusEnum.Preschoolage_status.intKey(), applicantJpId, session));
			StringBuilder sbWork = new StringBuilder();
			for (JobStatusPreschoolEnum jobWorking : JobStatusPreschoolEnum.values()) {
				sbWork.append(jobWorking.intKey()).append(",");
			}
			String workStatus = sbWork.toString();
			applicantWorkJpEntity.setPrepareMaterials(workStatus.substring(0, workStatus.length() - 1));
		}
		System.out.println("职业相关处理完毕");
	}

	public List<TApplicantFrontPaperworkJpEntity> toInsertFrontJp(Integer workType, Integer applicantId,
			HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		List<TApplicantFrontPaperworkJpEntity> frontList = new ArrayList<>();
		if (Util.eq(workType, JobStatusEnum.WORKING_STATUS.intKey())) {
			for (JobStatusWorkingEnum jobStatusEnum : JobStatusWorkingEnum.values()) {
				TApplicantFrontPaperworkJpEntity frontJp = new TApplicantFrontPaperworkJpEntity();
				frontJp.setApplicantId(applicantId);
				frontJp.setOpId(loginUser.getId());
				frontJp.setRealInfo(jobStatusEnum.value());
				frontJp.setUpdateTime(new Date());
				frontJp.setCreateTime(new Date());
				frontJp.setType(workType);
				frontJp.setStatus(1);
				frontList.add(frontJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.RETIREMENT_STATUS.intKey())) {
			for (JobStatusRetirementEnum jobStatusEnum : JobStatusRetirementEnum.values()) {
				TApplicantFrontPaperworkJpEntity frontJp = new TApplicantFrontPaperworkJpEntity();
				frontJp.setApplicantId(applicantId);
				frontJp.setOpId(loginUser.getId());
				frontJp.setRealInfo(jobStatusEnum.value());
				frontJp.setUpdateTime(new Date());
				frontJp.setCreateTime(new Date());
				frontJp.setType(workType);
				frontJp.setStatus(1);
				frontList.add(frontJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.FREELANCE_STATUS.intKey())) {
			for (JobStatusFreeEnum jobStatusEnum : JobStatusFreeEnum.values()) {
				TApplicantFrontPaperworkJpEntity frontJp = new TApplicantFrontPaperworkJpEntity();
				frontJp.setApplicantId(applicantId);
				frontJp.setOpId(loginUser.getId());
				frontJp.setRealInfo(jobStatusEnum.value());
				frontJp.setUpdateTime(new Date());
				frontJp.setCreateTime(new Date());
				frontJp.setType(workType);
				frontJp.setStatus(1);
				frontList.add(frontJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.student_status.intKey())) {
			for (JobStatusStudentEnum jobStatusEnum : JobStatusStudentEnum.values()) {
				TApplicantFrontPaperworkJpEntity frontJp = new TApplicantFrontPaperworkJpEntity();
				frontJp.setApplicantId(applicantId);
				frontJp.setOpId(loginUser.getId());
				frontJp.setRealInfo(jobStatusEnum.value());
				frontJp.setUpdateTime(new Date());
				frontJp.setCreateTime(new Date());
				frontJp.setType(workType);
				frontJp.setStatus(1);
				frontList.add(frontJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.Preschoolage_status.intKey())) {
			for (JobStatusPreschoolEnum jobStatusEnum : JobStatusPreschoolEnum.values()) {
				TApplicantFrontPaperworkJpEntity frontJp = new TApplicantFrontPaperworkJpEntity();
				frontJp.setApplicantId(applicantId);
				frontJp.setOpId(loginUser.getId());
				frontJp.setRealInfo(jobStatusEnum.value());
				frontJp.setUpdateTime(new Date());
				frontJp.setCreateTime(new Date());
				frontJp.setType(workType);
				frontJp.setStatus(1);
				frontList.add(frontJp);
			}
		}

		return frontList;
	}

	public List<TApplicantVisaPaperworkJpEntity> toInsertVisaJp(Integer workType, Integer applicantId,
			HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		List<TApplicantVisaPaperworkJpEntity> visaList = new ArrayList<>();
		if (Util.eq(workType, JobStatusEnum.WORKING_STATUS.intKey())) {
			for (JobStatusWorkingEnum jobStatusEnum : JobStatusWorkingEnum.values()) {
				TApplicantVisaPaperworkJpEntity visaJp = new TApplicantVisaPaperworkJpEntity();
				visaJp.setApplicantId(applicantId);
				visaJp.setRealInfo(jobStatusEnum.value());
				visaJp.setOpId(loginUser.getId());
				visaJp.setUpdateTime(new Date());
				visaJp.setCreateTime(new Date());
				visaJp.setStatus(1);
				visaJp.setType(workType);
				visaList.add(visaJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.RETIREMENT_STATUS.intKey())) {
			for (JobStatusRetirementEnum jobStatusEnum : JobStatusRetirementEnum.values()) {
				TApplicantVisaPaperworkJpEntity visaJp = new TApplicantVisaPaperworkJpEntity();
				visaJp.setApplicantId(applicantId);
				visaJp.setRealInfo(jobStatusEnum.value());
				visaJp.setOpId(loginUser.getId());
				visaJp.setUpdateTime(new Date());
				visaJp.setCreateTime(new Date());
				visaJp.setStatus(1);
				visaJp.setType(workType);
				visaList.add(visaJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.FREELANCE_STATUS.intKey())) {
			for (JobStatusFreeEnum jobStatusEnum : JobStatusFreeEnum.values()) {
				TApplicantVisaPaperworkJpEntity visaJp = new TApplicantVisaPaperworkJpEntity();
				visaJp.setApplicantId(applicantId);
				visaJp.setRealInfo(jobStatusEnum.value());
				visaJp.setOpId(loginUser.getId());
				visaJp.setUpdateTime(new Date());
				visaJp.setCreateTime(new Date());
				visaJp.setStatus(1);
				visaJp.setType(workType);
				visaList.add(visaJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.student_status.intKey())) {
			for (JobStatusStudentEnum jobStatusEnum : JobStatusStudentEnum.values()) {
				TApplicantVisaPaperworkJpEntity visaJp = new TApplicantVisaPaperworkJpEntity();
				visaJp.setApplicantId(applicantId);
				visaJp.setRealInfo(jobStatusEnum.value());
				visaJp.setOpId(loginUser.getId());
				visaJp.setUpdateTime(new Date());
				visaJp.setCreateTime(new Date());
				visaJp.setStatus(1);
				visaJp.setType(workType);
				visaList.add(visaJp);
			}
		}
		if (Util.eq(workType, JobStatusEnum.Preschoolage_status.intKey())) {
			for (JobStatusPreschoolEnum jobStatusEnum : JobStatusPreschoolEnum.values()) {
				TApplicantVisaPaperworkJpEntity visaJp = new TApplicantVisaPaperworkJpEntity();
				visaJp.setApplicantId(applicantId);
				visaJp.setRealInfo(jobStatusEnum.value());
				visaJp.setOpId(loginUser.getId());
				visaJp.setUpdateTime(new Date());
				visaJp.setCreateTime(new Date());
				visaJp.setStatus(1);
				visaJp.setType(workType);
				visaList.add(visaJp);
			}
		}
		return visaList;
	}

	/**
	 * 取消订单
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object cancelOrder(Long orderid) {
		if (!Util.isEmpty(orderid)) {
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid);
			TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
			List<TOrderLogsEntity> logs = dbDao.query(TOrderLogsEntity.class,
					Cnd.where("orderId", "=", orderinfo.getId()), null);
			if (!Util.isEmpty(logs)) {
				dbDao.delete(logs);
			}

			List<TOrderTravelplanJpEntity> travelplan = dbDao.query(TOrderTravelplanJpEntity.class,
					Cnd.where("orderid", "=", orderid), null);
			if (!Util.isEmpty(travelplan)) {
				dbDao.delete(travelplan);
			}

			TOrderTripJpEntity tripjp = dbDao.fetch(TOrderTripJpEntity.class, Cnd.where("orderId", "=", orderid));
			if (!Util.isEmpty(tripjp)) {
				dbDao.delete(tripjp);
			}
			List<TApplicantOrderJpEntity> applicantjp = dbDao.query(TApplicantOrderJpEntity.class,
					Cnd.where("orderid", "=", orderid), null);
			if (!Util.isEmpty(applicantjp)) {
				Integer[] applicantids = new Integer[applicantjp.size()];
				Integer[] applicantidjps = new Integer[applicantjp.size()];
				int i = 0;
				for (TApplicantOrderJpEntity tApplicantOrderJpEntity : applicantjp) {
					applicantids[i] = tApplicantOrderJpEntity.getApplicantId();
					applicantidjps[i] = tApplicantOrderJpEntity.getId();
					i++;
				}
				//签证录入删除
				List<TApplicantVisaJpEntity> applicantvisajp = dbDao.query(TApplicantVisaJpEntity.class,
						Cnd.where("applicantId", "in", applicantidjps), null);
				if (!Util.isEmpty(applicantvisajp)) {
					dbDao.delete(applicantvisajp);
				}
				//删除前台实收
				List<TApplicantFrontPaperworkJpEntity> frontpaper = dbDao.query(TApplicantFrontPaperworkJpEntity.class,
						Cnd.where("applicantId", "in", applicantidjps), null);
				if (!Util.isEmpty(frontpaper)) {
					dbDao.delete(frontpaper);
				}
				//删除签证实收
				List<TApplicantVisaPaperworkJpEntity> visapaper = dbDao.query(TApplicantVisaPaperworkJpEntity.class,
						Cnd.where("applicantId", "in", applicantidjps), null);
				if (!Util.isEmpty(visapaper)) {
					dbDao.delete(visapaper);
				}
				//删除财产信息
				List<TApplicantWealthJpEntity> wealthjp = dbDao.query(TApplicantWealthJpEntity.class,
						Cnd.where("applicantId", "in", applicantidjps), null);
				if (!Util.isEmpty(wealthjp)) {
					dbDao.delete(wealthjp);
				}
				List<TApplicantWorkJpEntity> workjp = dbDao.query(TApplicantWorkJpEntity.class,
						Cnd.where("applicantId", "in", applicantidjps), null);
				if (!Util.isEmpty(workjp)) {
					dbDao.delete(workjp);
				}
				//删除护照信息
				List<TApplicantPassportEntity> passport = dbDao.query(TApplicantPassportEntity.class,
						Cnd.where("applicantId", "in", applicantids), null);
				if (!Util.isEmpty(passport)) {
					dbDao.delete(passport);
				}
				List<TApplicantEntity> applicant = dbDao.query(TApplicantEntity.class,
						Cnd.where("id", "in", applicantids), null);
				dbDao.delete(applicantjp);
				if (!Util.isEmpty(applicant)) {
					dbDao.delete(applicant);
				}
			}
			//删除日本订单表
			dbDao.delete(orderjp);
			//删除订单表
			dbDao.delete(orderinfo);

		}
		return null;
	}

	public Object addPassport(Integer orderid, HttpServletRequest request) {

		HttpSession session = request.getSession();
		Map<String, Object> result = Maps.newHashMap();
		String token = TokenUtil.getInstance().makeToken();//创建令牌
		System.out.println("在FormServlet中生成的token：" + token);
		request.getSession().setAttribute("token", token);
		//result.put("token", token);
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		result.put("userid", loginUser.getId());
		result.put("orderid", orderid);
		String localAddr = request.getServerName();
		request.getServerName();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		int localPort = request.getServerPort();
		result.put("localAddr", localAddr);
		result.put("localPort", localPort);
		result.put("websocketaddr", SIMPLE_WEBSOCKET_ADDR);
		//生成二维码
		if (Util.isEmpty(orderid)) {
			orderid = 0;
		}
		String qrCode = dataUpload(0, orderid, request);
		result.put("qrCode", qrCode);
		result.put("sessionid", session.getId());
		return result;

	}

	public Object changeVisatype(int orderid, int visatype) {
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid);
		if (visatype == 7) {
			orderjp.setVisaCounty("冲绳县");
		} else if (visatype == 8) {
			orderjp.setVisaCounty("宫城县");
		} else if (visatype == 9) {
			orderjp.setVisaCounty("福岛县");
		} else if (visatype == 10) {
			orderjp.setVisaCounty("岩手县");
		} else if (visatype == 11) {
			orderjp.setVisaCounty("青森县");
		} else if (visatype == 12) {
			orderjp.setVisaCounty("秋田县");
		} else if (visatype == 13) {
			orderjp.setVisaCounty("山形县");
		} else {
			orderjp.setVisaCounty("");
		}
		orderjp.setVisaType(visatype);
		dbDao.update(orderjp);
		return null;
	}

	public Object getUnitname(String searchstr, HttpServletRequest request) {

		long first = System.currentTimeMillis();

		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);

		List<String> nameList = new ArrayList<>();

		/*String searchResult = "";
		Result result1 = ToAnalysis.parse(searchstr);
		List<Term> terms = result1.getTerms();
		for (Term term : terms) {
			String name = term.getName();
			if (!Util.isEmpty(name)) {
				searchResult += name + " +";
			}
		}

		if (searchResult.contains("+")) {
			searchResult = Strings.trim(searchResult).substring(0, Strings.trim(searchResult).length() - 2);
		}
		System.out.println(searchResult);

		Sql sql = Sqls
				.create("SELECT name,telephone,address FROM t_applicant_work_jp  WHERE MATCH(namesplit) AGAINST(@searchstr IN boolean mode)");

		sql.setParam("searchstr", searchResult);
		// and tr.comId = @comId
		//sql.setParam("searchstr", searchResult).setParam("comId", loginCompany.getId());

		List<Record> names = dbDao.query(sql, null, null);*/

		String sqlStr = sqlManager.get("simpleJP_getUnitname");
		Sql sql = Sqls.create(sqlStr);
		Cnd cnd = Cnd.NEW();
		cnd.and("tawj.name", "like", "%" + Strings.trim(searchstr) + "%");
		cnd.and("tr.comId", "=", loginCompany.getId());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startDate = "";
		String endDate = "";
		try {
			Date nowDate = sdf.parse(sdf.format(new Date()));
			Date pluDate = pluDate(nowDate, 30);
			startDate = sdf.format(pluDate);
			endDate = sdf.format(nowDate);
		} catch (ParseException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		cnd.and("tawj.createTime", "between", new Object[] { startDate, endDate });

		List<Record> names = dbDao.query(sql, cnd, null);
		for (Record record : names) {
			String name = record.getString("name");
			String telephone = record.getString("telephone");
			String address = record.getString("address");
			String result = Strings.trim(name) + " " + Strings.trim(telephone) + " " + Strings.trim(address);
			if (!nameList.contains(result)) {
				nameList.add(result);
			}
			if (Util.eq(nameList.size(), 5)) {
				break;
			}
		}

		long last = System.currentTimeMillis();
		System.out.println("查询所用时间为:" + (last - first) + "ms");
		return nameList;
	}

	public Object getUnittelephone(String searchstr, HttpServletRequest request) {

		long first = System.currentTimeMillis();

		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);

		List<String> telephoneList = new ArrayList<>();

		/*String searchResult = "";
		Result result1 = ToAnalysis.parse(searchstr);
		List<Term> terms = result1.getTerms();
		for (Term term : terms) {
			String name = term.getName();
			if (!Util.isEmpty(name)) {
				searchResult += name + " +";
			}
		}

		if (searchResult.contains("+")) {
			searchResult = Strings.trim(searchResult).substring(0, Strings.trim(searchResult).length() - 2);
		}
		System.out.println(searchResult);

		Sql sql = Sqls
				.create("SELECT tawj.name,tawj.telephone,tawj.address FROM t_applicant_work_jp tawj LEFT JOIN t_applicant_order_jp taoj ON taoj.id = tawj.applicantId INNER JOIN t_order_jp toj ON toj.id = taoj.orderId LEFT JOIN t_order tr ON tr.id = toj.orderId WHERE MATCH(tawj.telephone) AGAINST(@searchstr IN boolean mode) and tr.comId = @comId");

		sql.setParam("searchstr", searchResult).setParam("comId", loginCompany.getId());

		List<Record> telephones = dbDao.query(sql, null, null);*/

		String sqlStr = sqlManager.get("simpleJP_getUnitname");
		Sql sql = Sqls.create(sqlStr);
		Cnd cnd = Cnd.NEW();

		//MATCH(telephone) AGAINST('国际' IN boolean mode)
		//cnd.and("match(tawj.telephone)", "against", "(" + searchResult + "in boolean mode)");
		cnd.and("tawj.telephone", "like", Strings.trim(searchstr) + "%");
		cnd.and("tr.comId", "=", loginCompany.getId());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startDate = "";
		String endDate = "";
		try {
			Date nowDate = sdf.parse(sdf.format(new Date()));
			Date pluDate = pluDate(nowDate, 30);
			startDate = sdf.format(pluDate);
			endDate = sdf.format(nowDate);
		} catch (ParseException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		cnd.and("tawj.createTime", "between", new Object[] { startDate, endDate });

		//cnd.limit(0, 500);
		List<Record> telephones = dbDao.query(sql, cnd, null);
		for (Record record : telephones) {
			String name = record.getString("name");
			String telephone = record.getString("telephone");
			String address = record.getString("address");
			String result = Strings.trim(name) + " " + Strings.trim(telephone) + " " + Strings.trim(address);
			if (!telephoneList.contains(result)) {
				telephoneList.add(result);
			}
			if (Util.eq(telephoneList.size(), 5)) {
				break;
			}
		}

		long last = System.currentTimeMillis();
		System.out.println("所用时间为:" + (last - first) + "ms");
		return telephoneList;
	}

	public static Date pluDate(Date date, long day) throws ParseException {
		long time = date.getTime(); // 得到指定日期的毫秒数
		day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
		//time += day; // 相加得到新的毫秒数
		time -= day; // 相减得到新的毫秒数
		return new Date(time); // 将毫秒数转换成日期
	}

	public Object getCustomerCitySelect(String cityname, String citytype, String exname, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Integer comid = loginCompany.getId();

		String sqlStr = "";

		if (Util.isEmpty(cityname)) {
			if (Util.eq("goDepartureCity", citytype)) {
				sqlStr = sqlManager.get("cityselectBygodeparturecity");
			} else if (Util.eq("newgodeparturecity", citytype)) {
				sqlStr = sqlManager.get("cityselectBynewgodeparturecity");
			} else if (Util.eq("goArrivedCity", citytype)) {
				sqlStr = sqlManager.get("cityselectBygoarrivedcity");
			} else if (Util.eq("newgoarrivedcity", citytype)) {
				sqlStr = sqlManager.get("cityselectBynewgoarrivedcity");
			} else if (Util.eq("newreturndeparturecity", citytype)) {
				sqlStr = sqlManager.get("cityselectBynewreturndeparturecity");
			} else if (Util.eq("newreturnarrivedcity", citytype)) {
				sqlStr = sqlManager.get("cityselectBynewreturnarrivedcity");
			} else if (Util.eq("returnDepartureCity", citytype)) {
				sqlStr = sqlManager.get("cityselectByreturndeparturecity");
			} else if (Util.eq("returnArrivedCity", citytype)) {
				sqlStr = sqlManager.get("cityselectByreturnarrivedcity");
			} else if (Util.eq("gotransferarrivedcity", citytype)) {
				sqlStr = sqlManager.get("cityselectBygotransferarrivedcity");
			} else if (Util.eq("gotransferdeparturecity", citytype)) {
				sqlStr = sqlManager.get("cityselectBygotransferdeparturecity");
			} else if (Util.eq("returntransferarrivedcity", citytype)) {
				sqlStr = sqlManager.get("cityselectByreturntransferarrivedcity");
			} else if (Util.eq("returntransferdeparturecity", citytype)) {
				sqlStr = sqlManager.get("cityselectByreturntransferdeparturecity");
			}
			Sql applysql = Sqls.create(sqlStr);
			Cnd cnd = Cnd.NEW();
			cnd.and("tr.comId", "=", comid);
			cnd.groupBy("tc.city");
			cnd.orderBy("count", "DESC");
			List<Record> infoList = dbDao.query(applysql, cnd, null);

			if (infoList.size() == 0) {
				List<TCityEntity> citySelect = new ArrayList<TCityEntity>();
				try {
					citySelect = dbDao.query(TCityEntity.class,
							Cnd.where("city", "like", Strings.trim(cityname) + "%"), null);
					//移除的城市
					TCityEntity exinfo = new TCityEntity();
					for (TCityEntity tCityEntity : citySelect) {
						if (!Util.isEmpty(exname) && tCityEntity.getCity().equals(exname)) {
							exinfo = tCityEntity;
						}
					}
					citySelect.remove(exinfo);
					if (citySelect.size() > 5) {
						citySelect = citySelect.subList(0, 5);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return citySelect;
			} else {
				if (infoList.size() > 4) {
					infoList = infoList.subList(0, 5);
				}
				return infoList;
			}

		} else {
			List<TCityEntity> citySelect = new ArrayList<TCityEntity>();
			try {
				citySelect = dbDao.query(TCityEntity.class, Cnd.where("city", "like", Strings.trim(cityname) + "%"),
						null);
				//移除的城市
				TCityEntity exinfo = new TCityEntity();
				for (TCityEntity tCityEntity : citySelect) {
					if (!Util.isEmpty(exname) && tCityEntity.getCity().equals(exname)) {
						exinfo = tCityEntity;
					}
				}
				citySelect.remove(exinfo);
				if (citySelect.size() > 5) {
					citySelect = citySelect.subList(0, 5);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return citySelect;

		}

	}

	public Object getInfobyOrdernum(String ordernum, int orderid, HttpSession session) {//orderid为orderjp的id
		Map<String, Object> result = Maps.newHashMap();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		String errMsg = "";
		int orderjpid = 0;
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, Cnd.where("orderNum", "=", ordernum));
		//判断订单是否存在
		if (!Util.isEmpty(orderinfo)) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//跟所查询订单相关的数据
			TOrderJpEntity orderjpinfo = dbDao
					.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", orderinfo.getId()));
			TOrderTripJpEntity tripjpinfo = dbDao.fetch(TOrderTripJpEntity.class,
					Cnd.where("orderId", "=", orderjpinfo.getId()));
			List<TOrderTravelplanJpEntity> travelplans = dbDao.query(TOrderTravelplanJpEntity.class,
					Cnd.where("orderId", "=", orderjpinfo.getId()), null);

			result.put("data", getTravelPlanByOrderId(orderjpinfo.getId()));
			result.put("orderjpinfo", orderjpinfo);

			Map<String, String> orderMap = MapUtil.obj2Map(orderinfo);
			if (!Util.isEmpty(orderinfo.getSendVisaDate())) {
				orderMap.put("sendVisaDate", sdf.format(orderinfo.getSendVisaDate()));
			}
			if (!Util.isEmpty(orderinfo.getOutVisaDate())) {
				orderMap.put("outVisaDate", sdf.format(orderinfo.getOutVisaDate()));
			}
			result.put("orderinfo", orderMap);

			Map<String, String> tripMap = MapUtil.obj2Map(tripjpinfo);
			if (!Util.isEmpty(tripjpinfo.getGoDate())) {
				tripMap.put("goDate", sdf.format(tripjpinfo.getGoDate()));
			}
			if (!Util.isEmpty(tripjpinfo.getReturnDate())) {
				tripMap.put("returnDate", sdf.format(tripjpinfo.getReturnDate()));
			}
			tripMap = getTripinfoCitys(tripMap, tripjpinfo);
			result.put("tripjpinfo", tripMap);

			//没有Orderid说明为下单，需保存订单
			if (Util.isEmpty(orderid) || Util.eq(0, orderid)) {
				Map<String, Integer> generrateorder = generrateorder(loginUser, loginCompany);
				orderid = generrateorder.get("orderid");
				orderjpid = generrateorder.get("orderjpid");
				orderJpViewService.insertLogs(orderid, JpOrderSimpleEnum.PLACE_ORDER.intKey(), session);
			} else {
				orderjpid = orderid;
				TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid);
				TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
				orderid = order.getId();
			}

			//跟自己相关的数据
			TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderid);
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", order.getId()));
			TOrderTripJpEntity selftripjpinfo = dbDao.fetch(TOrderTripJpEntity.class,
					Cnd.where("orderId", "=", orderjp.getId()));
			if (Util.isEmpty(selftripjpinfo)) {
				selftripjpinfo = new TOrderTripJpEntity();
				selftripjpinfo.setOrderId(orderjpid);
				selftripjpinfo.setCreateTime(new Date());
				selftripjpinfo.setUpdateTime(new Date());
				dbDao.insert(selftripjpinfo);
			}
			List<TOrderTravelplanJpEntity> selftravelplans = dbDao.query(TOrderTravelplanJpEntity.class,
					Cnd.where("orderId", "=", orderjp.getId()), null);

			//数据复制

			//客户信息
			/*TCustomerEntity customer = new TCustomerEntity();
			if (!Util.isEmpty(orderinfo.getCustomerId())) {
				customer = dbDao.fetch(TCustomerEntity.class, orderinfo.getCustomerId().longValue());
			}
			result.put("customer", customer);
			order.setCustomerId(orderinfo.getCustomerId());*/

			//订单信息
			order.setCityId(orderinfo.getCityId());
			order.setUrgentType(orderinfo.getUrgentType());
			order.setUrgentDay(orderinfo.getUrgentDay());
			order.setSendVisaDate(orderinfo.getSendVisaDate());
			order.setOutVisaDate(orderinfo.getOutVisaDate());
			order.setSendVisaNum(orderinfo.getSendVisaNum());
			order.setStayDay(orderinfo.getStayDay());
			dbDao.update(order);

			//日本订单信息
			orderjp.setVisaType(orderjpinfo.getVisaType());
			orderjp.setAmount(orderjpinfo.getAmount());
			dbDao.update(orderjp);

			//出行信息
			String tripjpSqlstr = sqlManager.get("simpleJP_copyordertripjp");
			Sql tripjpSql = Sqls.create(tripjpSqlstr);
			Cnd tripjpCnd = Cnd.NEW();
			tripjpCnd.and("totj2.id", "=", tripjpinfo.getId());//被复制的出行信息
			tripjpCnd.and("totj.id", "=", selftripjpinfo.getId());//自己的出行信息
			tripjpSql.setCondition(tripjpCnd);
			nutDao.execute(tripjpSql);

			//行程安排
			//dbDao.updateRelations(selftravelplans, travelplans);
			//先把原来的删掉
			if (!Util.isEmpty(selftravelplans)) {
				dbDao.delete(selftravelplans);
			}

			Sql sql = Sqls
					.create("INSERT INTO t_order_travelplan_jp(orderid,day,outDate,scenic,hotel,cityId,cityName,isupdatecity) SELECT @orderid,day,outDate,scenic,hotel,cityId,cityName,0 FROM t_order_travelplan_jp WHERE id=@planid");

			for (TOrderTravelplanJpEntity planjp : travelplans) {
				sql.setParam("orderid", orderjpid).setParam("planid", planjp.getId());
				sql.addBatch();
				System.out.println(planjp);
			}
			dbDao.execute(sql);

			result.put("orderid", orderjpid);

		} else {
			errMsg = "没有此订单号";
		}
		result.put("errMsg", errMsg);
		return result;
	}

	/**
	 * 根据订单号复制订单信息时，回显select2的城市名称
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param tripMap
	 * @param tripjp
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Map<String, String> getTripinfoCitys(Map<String, String> tripMap, TOrderTripJpEntity tripjp) {
		if (!Util.isEmpty(tripjp.getGoArrivedCity())) {
			tripMap.put("goarrivedcityname", getTripinfoCityname(tripjp.getGoArrivedCity()));
		} else {
			tripMap.put("goarrivedcityname", "");
		}
		if (!Util.isEmpty(tripjp.getGoDepartureCity())) {
			tripMap.put("godeparturecityname", getTripinfoCityname(tripjp.getGoDepartureCity()));
		} else {
			tripMap.put("godeparturecityname", "");
		}
		if (!Util.isEmpty(tripjp.getGotransferarrivedcity())) {
			tripMap.put("gotransferarrivedcityname", getTripinfoCityname(tripjp.getGotransferarrivedcity()));
		} else {
			tripMap.put("gotransferarrivedcityname", "");
		}
		if (!Util.isEmpty(tripjp.getGotransferdeparturecity())) {
			tripMap.put("gotransferdeparturecityname", getTripinfoCityname(tripjp.getGotransferdeparturecity()));
		} else {
			tripMap.put("gotransferdeparturecityname", "");
		}
		if (!Util.isEmpty(tripjp.getNewgoarrivedcity())) {
			tripMap.put("newgoarrivedcityname", getTripinfoCityname(tripjp.getNewgoarrivedcity()));
		} else {
			tripMap.put("newgoarrivedcityname", "");
		}
		if (!Util.isEmpty(tripjp.getNewgodeparturecity())) {
			tripMap.put("newgodeparturecityname", getTripinfoCityname(tripjp.getNewgodeparturecity()));
		} else {
			tripMap.put("newgodeparturecityname", "");
		}
		if (!Util.isEmpty(tripjp.getNewreturnarrivedcity())) {
			tripMap.put("newreturnarrivedcityname", getTripinfoCityname(tripjp.getNewreturnarrivedcity()));
		} else {
			tripMap.put("newreturnarrivedcityname", "");
		}
		if (!Util.isEmpty(tripjp.getNewreturndeparturecity())) {
			tripMap.put("newreturndeparturecityname", getTripinfoCityname(tripjp.getNewreturndeparturecity()));
		} else {
			tripMap.put("newreturndeparturecityname", "");
		}
		if (!Util.isEmpty(tripjp.getReturnArrivedCity())) {
			tripMap.put("returnarrivedcityname", getTripinfoCityname(tripjp.getReturnArrivedCity()));
		} else {
			tripMap.put("returnarrivedcityname", "");
		}
		if (!Util.isEmpty(tripjp.getReturnDepartureCity())) {
			tripMap.put("returndeparturecityname", getTripinfoCityname(tripjp.getReturnDepartureCity()));
		} else {
			tripMap.put("returndeparturecityname", "");
		}
		if (!Util.isEmpty(tripjp.getReturntransferarrivedcity())) {
			tripMap.put("returntransferarrivedcityname", getTripinfoCityname(tripjp.getReturntransferarrivedcity()));
		} else {
			tripMap.put("returntransferarrivedcityname", "");
		}
		if (!Util.isEmpty(tripjp.getReturntransferdeparturecity())) {
			tripMap.put("returntransferdeparturecityname", getTripinfoCityname(tripjp.getReturntransferdeparturecity()));
		} else {
			tripMap.put("returntransferdeparturecityname", "");
		}
		return tripMap;
	}

	public String getTripinfoCityname(int cityid) {
		String cityname = "";
		TCityEntity city = dbDao.fetch(TCityEntity.class, cityid);
		if (!Util.isEmpty(city)) {
			cityname = city.getCity();
		}
		return cityname;
	}

	/**
	 * 记录没有正确翻译的汉字
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param characterStr
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toRecordCharacters(String characterStr) {
		TUncommoncharacterEntity fetch = dbDao.fetch(TUncommoncharacterEntity.class,
				Cnd.where("hanzi", "=", characterStr));
		if (Util.isEmpty(fetch)) {
			TUncommoncharacterEntity entity = new TUncommoncharacterEntity();
			entity.setHanzi(characterStr);
			entity.setCreatetime(new Date());
			dbDao.insert(entity);
		}
		return null;
	}

	public String dataUpload(int applyid, int orderid, HttpServletRequest request) {
		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		String page = "pages/Japan/upload/index/index";
		String scene = "";
		//因为小程序参数最长为32，所以参数尽量简化，o是订单id,u是userid,a是申请人id
		scene = "o=" + orderid + "&u=" + loginUser.getId() + "&a=" + applyid;
		System.out.println("scene:" + scene + "--------------");
		String accessToken = (String) getAccessToken();
		System.out.println("accessToken:" + accessToken + "=================");
		String url = createBCode(accessToken, page, scene);
		System.out.println("url:" + url);
		return url;
	}

	//获取accessToken
	public Object getAccessToken() {
		/*Map<String, Object> kvConfigProperties = SystemProperties.getKvConfigProperties();
		String wxConfigStr = String.valueOf(kvConfigProperties.get("T_APP_STAFF_CONF_WX_ID"));
		Long T_APP_STAFF_CONF_WX_ID = Long.valueOf(wxConfigStr);
		TConfWxEntity wx = dbDao.fetch(TConfWxEntity.class, T_APP_STAFF_CONF_WX_ID);
		String WX_APPID = wx.getAppid();
		String WX_APPSECRET = wx.getAppsecret();
		String WX_TOKENKEY = wx.getAccesstokenkey();*/

		String WX_APPID = "wx17bf0dde91fec324";
		String WX_APPSECRET = "6cc0aa2089c4ba020297fb23af31081a";
		String WX_TOKENKEY = "WX_ACCESS_TOKEN_2018";

		String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
		String requestUrl = accessTokenUrl.replace("APPID", WX_APPID).replace("APPSECRET", WX_APPSECRET);
		JSONObject result = HttpUtil.doGet(requestUrl);
		//redis中设置 access_token
		accessTokenUrl = result.getString("access_token");

		return accessTokenUrl;
	}

	public String createBCode(String accessToken, String page, String scene) {
		String url = WX_B_CODE_URL.replace("ACCESS_TOKEN", accessToken);
		Map<String, Object> param = new HashMap<>();
		param.put("page", page);
		param.put("scene", scene);
		param.put("width", "100");
		param.put("auto_color", false);
		Map<String, Object> line_color = new HashMap<>();
		line_color.put("r", 0);
		line_color.put("g", 0);
		line_color.put("b", 0);
		param.put("line_color", line_color);
		JSONObject json = JSONObject.parseObject(JSON.toJSONString(param));
		System.out.println("json:" + json.toString());
		//JSONObject json = JSONObject.fromObject(param);
		InputStream inputStream = toPostRequest(json.toString(), accessToken);

		String imgurl = CommonConstants.IMAGES_SERVER_ADDR + qiniuUploadService.uploadImage(inputStream, "", "");
		/*try {
			String imageUrl = httpPostWithJSON2(url, json.toString(), "xxx.png");
			return imageUrl;
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return imgurl;
	}

	//返回图片地址
	public String httpPostWithJSON2(String url, String json, String imagePath) throws Exception {
		String result = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");

		StringEntity se = new StringEntity(json);
		se.setContentType("application/json");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "UTF-8"));
		httpPost.setEntity(se);
		HttpResponse response = httpClient.execute(httpPost);
		if (response != null) {
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				InputStream instreams = resEntity.getContent();
				//上传至资源服务器生成url
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				byte[] bs = new byte[1024];//缓冲数组
				int len = -1;
				while ((len = instreams.read(bs)) != -1) {
					byteArrayOutputStream.write(bs, 0, len);
				}
				byte b[] = byteArrayOutputStream.toByteArray();
				byteArrayOutputStream.close();
				instreams.close();
				//将byte字节数组上传至资源服务器返回图片地址
				// ......
			}
		}
		httpPost.abort();
		return result;
	}

	//发送POST请求
	public InputStream toPostRequest(String json, String accessToken) {
		String host = "https://api.weixin.qq.com";
		String path = "/wxa/getwxacodeunlimit?access_token=" + accessToken;
		String method = "POST";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		HttpResponse response;
		InputStream inputStream = null;
		try {
			response = HttpUtils.doPost(host, path, method, headers, querys, json);
			inputStream = response.getEntity().getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	/**
	 * 判断是否有申请人，没有的话新建，有的话修改
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param applyid
	 * @param orderid
	 * @param token
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object hasApplyInfo(int applyid, int orderid, String token, HttpServletRequest request) {

		boolean isRepeat = isRepeatSubmit(token, request);//判断用户是否是重复提交
		if (isRepeat == true) {
			System.out.println("请不要重复提交");
			return null;
		} else {
			request.getSession().removeAttribute("token");//移除session中的token
			System.out.println("处理用户提交请求！！");
			HttpSession session = request.getSession();
			TUserEntity loginUser = LoginUtil.getLoginUser(session);
			TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
			Map<String, Object> result = Maps.newHashMap();
			if (Util.isEmpty(applyid) || applyid == 0) {
				//新建申请人表
				TApplicantEntity apply = new TApplicantEntity();
				apply.setOpId(loginUser.getId());
				apply.setIsSameInfo(IsYesOrNoEnum.YES.intKey());
				apply.setIsPrompted(IsYesOrNoEnum.NO.intKey());
				apply.setStatus(TrialApplicantStatusEnum.FIRSTTRIAL.intKey());
				apply.setCreateTime(new Date());
				TApplicantEntity insertApply = dbDao.insert(apply);
				applyid = insertApply.getId();
				//新建日本申请人表
				TApplicantOrderJpEntity applicantjp = new TApplicantOrderJpEntity();
				if (Util.isEmpty(orderid) || orderid == 0) {
					//创建订单表
					Map<String, Integer> generrateorder = generrateorder(loginUser, loginCompany);
					orderid = generrateorder.get("orderjpid");
				}

				//设置主申请人信息
				List<TApplicantOrderJpEntity> orderapplicant = dbDao.query(TApplicantOrderJpEntity.class,
						Cnd.where("orderId", "=", orderid), null);
				if (!Util.isEmpty(orderapplicant) && orderapplicant.size() >= 1) {

					applicantjp.setIsMainApplicant(IsYesOrNoEnum.NO.intKey());
					TApplicantOrderJpEntity mainApply = dbDao.fetch(TApplicantOrderJpEntity.class,
							Cnd.where("orderId", "=", orderid).and("isMainApplicant", "=", IsYesOrNoEnum.YES.intKey()));
					if (!Util.isEmpty(mainApply)) {
						apply.setMainId(mainApply.getApplicantId());
					}
					dbDao.update(apply);
				} else {
					//设置为主申请人
					applicantjp.setIsMainApplicant(IsYesOrNoEnum.YES.intKey());
					apply.setMainId(applyid);
					dbDao.update(apply);
				}

				applicantjp.setOrderId(orderid);
				applicantjp.setApplicantId(applyid);
				applicantjp.setBaseIsCompleted(IsYesOrNoEnum.NO.intKey());
				applicantjp.setPassIsCompleted(IsYesOrNoEnum.NO.intKey());
				applicantjp.setVisaIsCompleted(IsYesOrNoEnum.NO.intKey());
				TApplicantOrderJpEntity insertappjp = dbDao.insert(applicantjp);

				//日本工作信息
				TApplicantWorkJpEntity workJp = new TApplicantWorkJpEntity();
				workJp.setApplicantId(insertappjp.getId());
				workJp.setCreateTime(new Date());
				workJp.setOpId(loginUser.getId());
				dbDao.insert(workJp);
				//护照信息
				TApplicantPassportEntity passport = new TApplicantPassportEntity();
				passport.setIssuedOrganization("公安部出入境管理局");
				passport.setIssuedOrganizationEn("MPS Exit&Entry Adiministration");
				passport.setApplicantId(applyid);
				dbDao.insert(passport);

				TApplicantVisaOtherInfoEntity visaother = new TApplicantVisaOtherInfoEntity();
				visaother.setApplicantid(insertappjp.getId());
				visaother.setHotelname("参照'赴日予定表'");
				visaother.setVouchname("参照'身元保证书'");
				visaother.setInvitename("同上");
				visaother.setTraveladvice("推荐");
				dbDao.insert(visaother);
			}
			result.put("applyid", applyid);
			result.put("orderid", orderid);
			return result;
		}

	}

	/**
	 * 判断是否重复提交
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param client_token
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public boolean isRepeatSubmit(String client_token, HttpServletRequest request) {

		System.out.println("client_token:" + client_token);
		//String client_token = request.getParameter("token");
		//1、如果用户提交的表单数据中没有token，则用户是重复提交了表单
		if (client_token == null) {
			return true;
		}
		//取出存储在Session中的token
		String server_token = (String) request.getSession().getAttribute("token");
		System.out.println("server_token:" + server_token);
		//2、如果当前用户的Session中不存在Token(令牌)，则用户是重复提交了表单
		if (server_token == null) {
			return true;
		}
		//3、存储在Session中的Token(令牌)与表单提交的Token(令牌)不同，则用户是重复提交了表单
		if (!client_token.equals(server_token)) {
			return true;
		}

		return false;
	}

	public Object isSamewithMainapply(int orderid) {
		Map<String, Object> result = Maps.newHashMap();
		String applicantSqlstr = sqlManager.get("getMainapplyinfo");
		Sql applicantSql = Sqls.create(applicantSqlstr);
		applicantSql.setParam("id", orderid);
		Record mainapplyinfo = dbDao.fetch(applicantSql);
		result.put("maininfo", mainapplyinfo);

		return result;
	}

	public Object autoCalculateStaydays(Date laststartdate, Date lastreturndate) {
		int daysBetween = DateUtil.daysBetween(laststartdate, lastreturndate);
		return daysBetween + 1;
	}

	/**
	 * 判断是否有主申请人
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid
	 * @param applicantid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object ishaveMainapply(int orderid, int applicantid) {
		String applicantSqlstr = sqlManager.get("ishaveMainapply");
		Sql applicantSql = Sqls.create(applicantSqlstr);
		applicantSql.setParam("id", orderid);
		List<Record> query = dbDao.query(applicantSql, null, null);
		if (query.size() > 1) {
			return 1;
		} else if (query.size() == 1) {
			int mainid = query.get(0).getInt("applicantid");
			if (mainid != applicantid) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	public Object saveSendandGround(int orderid, int sendsignid, int groundconnectid) {
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid);
		orderjp.setSendsignid(sendsignid);
		orderjp.setGroundconnectid(groundconnectid);
		dbDao.update(orderjp);
		return null;
	}

	/**
	 * 上传excel数据到数据库
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("resource")
	public Object importExcel(List<TempFile> tfs, HttpServletRequest request, HttpServletResponse response) {
		long firsttime = System.currentTimeMillis();

		String resultStr = "error";

		HttpSession session = request.getSession();
		//获取当前公司
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//当前登录人员
		TUserEntity loginUser = LoginUtil.getLoginUser(session);

		for (TempFile tempFile : tfs) {

			File file = tempFile.getFile();

			//for (int j = 0; j < 10000; j++) {

			//创建订单和日本订单
			Map<String, Integer> generrateorder = generrateorder(loginUser, loginCompany);
			Integer orderid = generrateorder.get("orderjpid");

			try {
				//Thread.sleep(1000);
			} catch (Exception e2) {

				// TODO Auto-generated catch block
				e2.printStackTrace();

			}

			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid.longValue());
			TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());

			long secondtime = System.currentTimeMillis();
			System.out.println("创建订单所用时间：" + (secondtime - firsttime) + "ms");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
			Workbook workbook = null;

			try {
				//fis = new FileInputStream(file);
				workbook = WorkbookFactory.create(file);
			} catch (Exception e1) {
				if (!Util.isEmpty(orderjp)) {
					dbDao.delete(orderjp);
				}
				if (!Util.isEmpty(order)) {
					dbDao.delete(order);
				}
				e1.printStackTrace();
			}

			try {
				//创建Excel，读取文件内容

				//XSSFWorkbook workbook = new XSSFWorkbook(file);
				//第一个sheet
				Sheet sheetAt = workbook.getSheetAt(0);
				for (int i = 6; i < 11; i++) {
					if (i == 7 || i == 8) {
						System.out.println("要跳过了，i为" + i);
						continue;
					}
					Row r = sheetAt.getRow(i);

					if (i == 6) {
						String visatype = r.getCell(2).getStringCellValue();
						if (visatype.contains("三年")) {
							if (visatype.contains("冲绳")) {
								orderjp.setVisaType(7);
								orderjp.setVisaCounty("冲绳");
							} else if (visatype.contains("宫城")) {
								orderjp.setVisaType(8);
								orderjp.setVisaCounty("宫城");
							} else if (visatype.contains("福岛")) {
								orderjp.setVisaType(9);
								orderjp.setVisaCounty("福岛");
							} else if (visatype.contains("岩手")) {
								orderjp.setVisaType(10);
								orderjp.setVisaCounty("岩手");
							} else if (visatype.contains("青森")) {
								orderjp.setVisaType(11);
								orderjp.setVisaCounty("青森");
							} else if (visatype.contains("秋田")) {
								orderjp.setVisaType(12);
								orderjp.setVisaCounty("秋田");
							} else if (visatype.contains("山形")) {
								orderjp.setVisaType(13);
								orderjp.setVisaCounty("山形");
							} else {
								orderjp.setVisaType(6);
							}
						} else {
							if (visatype.contains("冲绳")) {
								orderjp.setVisaType(2);
							} else if (visatype.contains("宫城")) {
								orderjp.setVisaType(3);
							} else if (visatype.contains("福岛")) {
								orderjp.setVisaType(5);
							} else if (visatype.contains("岩手")) {
								orderjp.setVisaType(4);
							} else {
								orderjp.setVisaType(1);
							}
						}
					}

					Date date = null;

					if (i == 9 || i == 10) {
						Cell cell = r.getCell(2);
						int cellType = cell.getCellType();
						System.out.println("文本格式为:" + cellType);
						if (Util.eq(Cell.CELL_TYPE_STRING, cellType)) {//文本格式
							String stringCellValue = cell.getStringCellValue();
							if (!Util.isEmpty(stringCellValue)) {
								date = sdf.parse(sdf.format(sdf2.parse(stringCellValue)));
							}
						} else if (Util.eq(Cell.CELL_TYPE_NUMERIC, cellType)) {//日期格式
							Date dateCellValue = cell.getDateCellValue();
							if (!Util.isEmpty(dateCellValue)) {
								date = sdf.parse(sdf2.format(dateCellValue));
							}
						}
						if (!Util.isEmpty(date)) {
							date = sdf.parse(sdf.format(date));
						}
						if (i == 9) {
							order.setGoTripDate(date);
						} else if (i == 10) {
							order.setBackTripDate(date);
						}
						System.out.println("date:" + date);
					}
				}
				dbDao.update(orderjp);
				order.setCityId(2);
				dbDao.update(order);

				TOrderTripJpEntity ordertripjp = new TOrderTripJpEntity();
				ordertripjp.setGoDate(order.getGoTripDate());
				ordertripjp.setReturnDate(order.getBackTripDate());
				ordertripjp.setOrderId(orderid);
				ordertripjp.setTripType(1);
				ordertripjp.setTripPurpose("旅游");
				dbDao.insert(ordertripjp);

				//第二个sheet
				Sheet sheetAt2 = workbook.getSheetAt(1);
				int lastRowNum = sheetAt2.getLastRowNum();
				int totalRow = 0;
				for (int i = 5; i < lastRowNum + 2; i++) {
					try {
						String stringCellValue = sheetAt2.getRow(i).getCell(1).getStringCellValue();
						if (Util.isEmpty(stringCellValue)) {
							totalRow = i;
							break;
						}
					} catch (Exception e) {
						totalRow = i;
						e.printStackTrace();
					}
				}
				System.out.println("lastRowNum:" + lastRowNum);
				for (int i = 5; i < totalRow; i++) {
					Row r = sheetAt2.getRow(i);

					//新建申请人表
					TApplicantEntity apply = new TApplicantEntity();
					apply.setOpId(loginUser.getId());
					apply.setIsSameInfo(IsYesOrNoEnum.YES.intKey());
					apply.setIsPrompted(IsYesOrNoEnum.NO.intKey());
					apply.setStatus(TrialApplicantStatusEnum.FIRSTTRIAL.intKey());
					apply.setCreateTime(new Date());
					apply.setFirstName(r.getCell(1).getStringCellValue());
					apply.setFirstNameEn(r.getCell(2).getStringCellValue());
					apply.setLastName(r.getCell(1).getStringCellValue());
					apply.setLastNameEn(r.getCell(2).getStringCellValue());
					apply.setSex(r.getCell(3).getStringCellValue());
					apply.setProvince(r.getCell(4).getStringCellValue());
					if (!Util.isEmpty(r.getCell(5).getStringCellValue())) {
						apply.setBirthday(sdf.parse(r.getCell(5).getStringCellValue()));
					} else {
						apply.setBirthday(null);
					}

					TApplicantEntity insertApply = dbDao.insert(apply);
					int applyid = insertApply.getId();

					//护照信息
					TApplicantPassportEntity passport = new TApplicantPassportEntity();
					passport.setIssuedOrganization("公安部出入境管理局");
					passport.setIssuedOrganizationEn("MPS Exit&Entry Adiministration");
					passport.setFirstName(r.getCell(1).getStringCellValue());
					passport.setFirstNameEn(r.getCell(2).getStringCellValue());
					passport.setLastName(r.getCell(1).getStringCellValue());
					passport.setLastNameEn(r.getCell(2).getStringCellValue());
					passport.setSex(r.getCell(3).getStringCellValue());
					if (!Util.isEmpty(r.getCell(5).getStringCellValue())) {
						passport.setBirthday(sdf.parse(r.getCell(5).getStringCellValue()));
					} else {
						passport.setBirthday(null);
					}
					passport.setPassport(r.getCell(6).getStringCellValue());
					passport.setApplicantId(applyid);
					dbDao.insert(passport);

					//新建日本申请人表
					TApplicantOrderJpEntity applicantjp = new TApplicantOrderJpEntity();

					//设置主申请人信息
					List<TApplicantOrderJpEntity> orderapplicant = dbDao.query(TApplicantOrderJpEntity.class,
							Cnd.where("orderId", "=", orderid), null);
					if (!Util.isEmpty(orderapplicant) && orderapplicant.size() >= 1) {

						applicantjp.setIsMainApplicant(IsYesOrNoEnum.NO.intKey());
						TApplicantOrderJpEntity mainApply = dbDao.fetch(
								TApplicantOrderJpEntity.class,
								Cnd.where("orderId", "=", orderid).and("isMainApplicant", "=",
										IsYesOrNoEnum.YES.intKey()));
						if (!Util.isEmpty(mainApply)) {
							apply.setMainId(mainApply.getApplicantId());
						}
						dbDao.update(apply);
					} else {
						//设置为主申请人
						applicantjp.setIsMainApplicant(IsYesOrNoEnum.YES.intKey());
						apply.setMainId(applyid);
						dbDao.update(apply);
					}

					applicantjp.setOrderId(orderid);
					applicantjp.setApplicantId(applyid);
					applicantjp.setBaseIsCompleted(IsYesOrNoEnum.NO.intKey());
					applicantjp.setPassIsCompleted(IsYesOrNoEnum.NO.intKey());
					applicantjp.setVisaIsCompleted(IsYesOrNoEnum.NO.intKey());
					TApplicantOrderJpEntity insertappjp = dbDao.insert(applicantjp);

					//日本工作信息
					TApplicantWorkJpEntity workJp = new TApplicantWorkJpEntity();
					workJp.setApplicantId(insertappjp.getId());
					workJp.setCreateTime(new Date());
					workJp.setOpId(loginUser.getId());
					workJp.setPosition(r.getCell(7).getStringCellValue());
					dbDao.insert(workJp);

					//其他信息
					TApplicantVisaOtherInfoEntity visaother = new TApplicantVisaOtherInfoEntity();
					visaother.setApplicantid(insertappjp.getId());
					visaother.setHotelname("参照'赴日予定表'");
					visaother.setVouchname("参照'身元保证书'");
					visaother.setInvitename("同上");
					visaother.setTraveladvice("推荐");
					dbDao.insert(visaother);
				}
				resultStr = "ok";

			} catch (Exception e) {
				if (!Util.isEmpty(orderjp)) {
					dbDao.delete(orderjp);
				}
				if (!Util.isEmpty(order)) {
					dbDao.delete(order);
				}
				resultStr = "error";
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			//}
		}

		return resultStr;
	}

	/**
	 * 列表页根据搜索条件下载excel
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object downloadOrder(ListDataForm form, HttpServletRequest request, HttpServletResponse response) {
		long firsttime = System.currentTimeMillis();

		HttpSession session = request.getSession();
		//获取当前公司
		//TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//当前登录人员
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		List<Record> downloadinfo = getDownloadinfo(form, request);
		long secondtime = System.currentTimeMillis();
		System.out.println("查询数据所用时间：" + (secondtime - firsttime) + "ms");

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {

			//1.创建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();
			//1.1创建合并单元格对象
			//日本签证处理系统
			CellRangeAddress callRangeAddress = new CellRangeAddress(0, 0, 0, 23);//起始行,结束行,起始列,结束列
			//时间
			CellRangeAddress time = new CellRangeAddress(1, 1, 0, 23);//起始行,结束行,起始列,结束列

			//基本信息
			CellRangeAddress basic = new CellRangeAddress(2, 2, 0, 8);//起始行,结束行,起始列,结束列
			//出行信息
			CellRangeAddress trip = new CellRangeAddress(2, 2, 9, 16);//起始行,结束行,起始列,结束列
			//备注信息
			CellRangeAddress remark = new CellRangeAddress(2, 2, 17, 23);//起始行,结束行,起始列,结束列

			//标题样式
			HSSFCellStyle titlecolStyle = createCellStyle(workbook, (short) 18, true, true, 2);
			//时间
			HSSFCellStyle timeStyle = createCellStyle(workbook, (short) 12, false, true, 1);
			//正文
			HSSFCellStyle colStyle = createCellStyle(workbook, (short) 12, false, true, 2);
			//2.创建工作表
			HSSFSheet sheet = workbook.createSheet("visa");
			//2.1加载合并单元格对象
			sheet.addMergedRegion(callRangeAddress);
			sheet.addMergedRegion(time);
			sheet.addMergedRegion(basic);
			sheet.addMergedRegion(trip);
			sheet.addMergedRegion(remark);

			//设置默认列宽
			sheet.setDefaultColumnWidth(15);
			//3.创建行
			//3.1创建头标题行;并且设置头标题
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);

			//加载单元格样式
			cell.setCellStyle(titlecolStyle);
			cell.setCellValue("日本签证处理系统");

			//加边框，如果不写的话，只有第一列有边框
			for (int i = 1; i < 24; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(colStyle);
			}

			HSSFRow secondrow = sheet.createRow(1);
			HSSFCell secondcell = secondrow.createCell(0);
			//加载单元格样式
			secondcell.setCellStyle(timeStyle);
			String orderstartdate = "";
			String orderenddate = "";

			if (!Util.isEmpty(downloadinfo) && !Util.isEmpty(downloadinfo.get(0).getString("createdate"))) {
				orderstartdate = downloadinfo.get(0).getString("createdate");
			}
			if (!Util.isEmpty(downloadinfo)
					&& !Util.isEmpty(downloadinfo.get(downloadinfo.size() - 1).getString("createdate"))) {
				orderenddate = downloadinfo.get(downloadinfo.size() - 1).getString("createdate");
			}

			secondcell.setCellValue("时间:" + orderstartdate + " 至 " + orderenddate);

			for (int i = 1; i < 24; i++) {
				HSSFCell createCell = secondrow.createCell(i);
				createCell.setCellStyle(timeStyle);
			}

			HSSFRow thirdrow = sheet.createRow(2);
			HSSFCell thirdcell = thirdrow.createCell(0);
			//加载单元格样式
			thirdcell.setCellStyle(colStyle);
			thirdcell.setCellValue("基本信息");
			HSSFCell thirdcell2 = thirdrow.createCell(9);
			//加载单元格样式
			thirdcell2.setCellStyle(colStyle);
			thirdcell2.setCellValue("出行信息");
			HSSFCell thirdcell3 = thirdrow.createCell(17);
			//加载单元格样式
			thirdcell3.setCellStyle(colStyle);
			thirdcell3.setCellValue("备注信息");

			HSSFCell thirdcell22 = thirdrow.createCell(23);
			thirdcell22.setCellStyle(colStyle);

			//3.2创建列标题;并且设置列标题
			HSSFRow row2 = sheet.createRow(3);
			String[] titles = { "序号", "订单号", "签证种类", "姓名", "姓名(拼音)", "性别", "护照号码", "居住地", "人数", "出发日期", "结束日期", "入境口岸",
					"出境口岸", "入境航班", "出境航班", "出发城市", "返回城市", "客户来源", "申请人备注", "备注", "客户备注", "送签时间", "创建人", "送签编号" };
			for (int i = 0; i < titles.length; i++) {
				HSSFCell cell2 = row2.createCell(i);
				//加载单元格样式
				cell2.setCellStyle(colStyle);
				cell2.setCellValue(titles[i]);
			}

			long thirdtime = System.currentTimeMillis();
			System.out.println("标题所用时间：" + (thirdtime - secondtime) + "ms");
			//正文内容
			int count = 1;
			for (int i = 0; i < downloadinfo.size(); i++) {

				//CellRangeAddress firstline = new CellRangeAddress(i + 3 + query.size(), 0, 0, 0);
				//sheet.addMergedRegion(firstline);

				HSSFRow row3 = sheet.createRow(i + 4);
				HSSFCell cell0 = row3.createCell(0);
				cell0.setCellStyle(colStyle);
				//判断跟上一行是否是同一个订单
				if (i > 0 && downloadinfo.get(i).getInt("id") == downloadinfo.get(i - 1).getInt("id")) {
					cell0.setCellValue(count - 1);

				} else {
					cell0.setCellValue(count++);
				}

				HSSFCell cell1 = row3.createCell(1);
				cell1.setCellStyle(colStyle);
				cell1.setCellValue(downloadinfo.get(i).getString("orderNum"));

				HSSFCell cell2 = row3.createCell(2);
				cell2.setCellStyle(colStyle);

				String visatypestr = "";
				if (!Util.isEmpty(downloadinfo.get(i).getString("visaType"))) {
					for (SimpleVisaTypeEnum typeEnum : SimpleVisaTypeEnum.values()) {
						if (downloadinfo.get(i).get("visaType").equals(typeEnum.intKey())) {
							visatypestr = typeEnum.value();
						}
					}
				}
				cell2.setCellValue(visatypestr);

				HSSFCell cell3 = row3.createCell(3);
				cell3.setCellStyle(colStyle);
				cell3.setCellValue(downloadinfo.get(i).getString("applyname"));

				HSSFCell cell4 = row3.createCell(4);
				cell4.setCellStyle(colStyle);
				cell4.setCellValue(downloadinfo.get(i).getString("applynameen"));

				HSSFCell cell5 = row3.createCell(5);
				cell5.setCellStyle(colStyle);
				cell5.setCellValue(downloadinfo.get(i).getString("sex"));

				HSSFCell cell6 = row3.createCell(6);
				cell6.setCellStyle(colStyle);
				cell6.setCellValue(downloadinfo.get(i).getString("passport"));

				HSSFCell cell7 = row3.createCell(7);
				cell7.setCellStyle(colStyle);

				String province = "";
				if (!Util.isEmpty(downloadinfo.get(i).getString("province"))) {
					province = downloadinfo.get(i).getString("province");
				}
				if (province.endsWith("省") || province.endsWith("市")) {
					province = province.substring(0, province.length() - 1);
				}
				if (province.length() > 3 && province.endsWith("自治区")) {
					province = province.substring(0, province.length() - 3);
				}

				cell7.setCellValue(province);

				HSSFCell cell8 = row3.createCell(8);
				cell8.setCellStyle(colStyle);
				cell8.setCellValue(downloadinfo.get(i).getInt("peopleNumber"));

				HSSFCell cell9 = row3.createCell(9);
				cell9.setCellStyle(colStyle);
				cell9.setCellValue(downloadinfo.get(i).getString("godate"));

				HSSFCell cell10 = row3.createCell(10);
				cell10.setCellStyle(colStyle);
				cell10.setCellValue(downloadinfo.get(i).getString("returndate"));

				HSSFCell cell11 = row3.createCell(11);
				cell11.setCellStyle(colStyle);

				int cityid = downloadinfo.get(i).getInt("cityId");
				//入境口岸
				String entryport = "";
				//出境口岸
				String leaveport = "";
				//入境航班
				String entryflight = downloadinfo.get(i).getString("goflight");
				//出境航班
				String leaveflight = downloadinfo.get(i).getString("returnflight");
				//入境日本城市
				String gocityName = "";
				if (!Util.isEmpty(downloadinfo.get(i).getString("goport"))) {
					gocityName = downloadinfo.get(i).getString("goport");
					if (gocityName.endsWith("市") || gocityName.endsWith("县") || gocityName.endsWith("府")) {
						gocityName = gocityName.substring(0, gocityName.length() - 1);
					}
				}
				//入境日本机场
				String goportname = "";
				if (!Util.isEmpty(downloadinfo.get(i).getString("goportname"))) {
					goportname = downloadinfo.get(i).getString("goportname");
				}
				entryport = gocityName + goportname;
				//出境日本城市
				String leavecityName = "";
				if (!Util.isEmpty(downloadinfo.get(i).getString("leaveport"))) {
					leavecityName = downloadinfo.get(i).getString("leaveport");
					if (leavecityName.endsWith("市") || leavecityName.endsWith("县") || leavecityName.endsWith("府")) {
						leavecityName = leavecityName.substring(0, leavecityName.length() - 1);
					}
				}
				//出境日本机场
				String leaveportname = "";
				if (!Util.isEmpty(downloadinfo.get(i).getString("leaveportname"))) {
					leaveportname = downloadinfo.get(i).getString("leaveportname");
				}
				leaveport = leavecityName + leaveportname;

				/*//重庆
				if (cityid > 2) {
					//入境
					if (!Util.isEmpty(downloadinfo.get(i).getString("newgoflightnum"))) {
						String goFlightNum = downloadinfo.get(i).getString("newgoflightnum");
						//入境机场名
						String lastnum = goFlightNum.substring(goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")) + 1,
								goFlightNum.indexOf(" ", goFlightNum.indexOf(" ") + 1));
						if (!Util.isEmpty(downloadinfo.get(i).getString("gotransferflightnum"))) {//有第一行，航班号要组合
							String gotransferflightnum = downloadinfo.get(i).getString("gotransferflightnum");
							StringBuffer stringBuffer = new StringBuffer(gotransferflightnum.substring(
									gotransferflightnum.indexOf(" ", gotransferflightnum.indexOf(" ")) + 1,
									gotransferflightnum.indexOf(" ", gotransferflightnum.indexOf(" ") + 1)));
							stringBuffer.append("//" + lastnum);
							lastnum = stringBuffer.toString();
						}

						entryflight = lastnum;

					}
					//出境
					if (!Util.isEmpty(downloadinfo.get(i).getString("returntransferflightnum"))) {
						String goFlightNum = downloadinfo.get(i).getString("returntransferflightnum");

						//出境机场名
						String lastnum = goFlightNum.substring(goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")) + 1,
								goFlightNum.indexOf(" ", goFlightNum.indexOf(" ") + 1));

						if (!Util.isEmpty(downloadinfo.get(i).getString("newreturnflightnum"))) {
							String newreturnflightnum = downloadinfo.get(i).getString("newreturnflightnum");
							String substring = newreturnflightnum.substring(
									newreturnflightnum.indexOf(" ", newreturnflightnum.indexOf(" ")) + 1,
									newreturnflightnum.indexOf(" ", newreturnflightnum.indexOf(" ") + 1));
							StringBuffer stringBuffer = new StringBuffer(lastnum);
							stringBuffer.append("//" + substring);
							lastnum = stringBuffer.toString();
						}

						leaveflight = lastnum;
					}

				} else {//北京，上海
					//入境
					if (!Util.isEmpty(downloadinfo.get(i).getString("goFlightNum"))) {

						String goFlightNum = downloadinfo.get(i).getString("goFlightNum");

						//入境机场名

						entryflight = goFlightNum.substring(goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")) + 1,
								goFlightNum.indexOf(" ", goFlightNum.indexOf(" ") + 1));
					}
					//出境
					if (!Util.isEmpty(downloadinfo.get(i).getString("returnFlightNum"))) {

						String goFlightNum = downloadinfo.get(i).getString("returnFlightNum");
						//出境机场名

						leaveflight = goFlightNum.substring(goFlightNum.indexOf(" ", goFlightNum.indexOf(" ")) + 1,
								goFlightNum.indexOf(" ", goFlightNum.indexOf(" ") + 1));
					}
				}*/

				cell11.setCellValue(entryport);

				HSSFCell cell12 = row3.createCell(12);
				cell12.setCellStyle(colStyle);
				cell12.setCellValue(leaveport);

				HSSFCell cell13 = row3.createCell(13);
				cell13.setCellStyle(colStyle);
				cell13.setCellValue(entryflight);

				HSSFCell cell14 = row3.createCell(14);
				cell14.setCellStyle(colStyle);
				cell14.setCellValue(leaveflight);

				HSSFCell cell15 = row3.createCell(15);
				cell15.setCellStyle(colStyle);
				String gocity = "";
				if (!Util.isEmpty(downloadinfo.get(i).getString("gocity"))) {
					gocity = downloadinfo.get(i).getString("gocity");

					if (gocity.endsWith("市") || gocity.endsWith("县")) {
						gocity = gocity.substring(0, gocity.length() - 1);
					}
					if (gocity.length() > 3 && gocity.endsWith("自治区")) {
						gocity = gocity.substring(0, gocity.length() - 3);
					}

				}
				cell15.setCellValue(gocity);

				HSSFCell cell16 = row3.createCell(16);
				cell16.setCellStyle(colStyle);
				String leavecity = "";
				if (!Util.isEmpty(downloadinfo.get(i).getString("leavecity"))) {
					leavecity = downloadinfo.get(i).getString("leavecity");
					if (leavecity.endsWith("市") || leavecity.endsWith("县")) {
						leavecity = leavecity.substring(0, leavecity.length() - 1);
					}
					if (leavecity.length() > 3 && leavecity.endsWith("自治区")) {
						leavecity = leavecity.substring(0, leavecity.length() - 3);
					}
				}
				cell16.setCellValue(leavecity);

				HSSFCell cell17 = row3.createCell(17);
				cell17.setCellStyle(colStyle);
				cell17.setCellValue(downloadinfo.get(i).getString("shortname"));

				HSSFCell cell18 = row3.createCell(18);
				cell18.setCellStyle(colStyle);
				String relation = "";

				//主申请人时取relationRemark
				if (downloadinfo.get(i).getInt("isMainApplicant") == 1) {
					if (!Util.isEmpty(downloadinfo.get(i).getString("relationRemark"))) {
						relation = downloadinfo.get(i).getString("relationRemark");
					}
				} else {
					if (!Util.isEmpty(downloadinfo.get(i).getString("mainRelation"))) {
						relation = downloadinfo.get(i).getString("mainRelation");
					}
				}
				cell18.setCellValue(relation);

				HSSFCell cell19 = row3.createCell(19);
				cell19.setCellStyle(colStyle);
				cell19.setCellValue("");

				HSSFCell cell20 = row3.createCell(20);
				cell20.setCellStyle(colStyle);
				cell20.setCellValue("");

				HSSFCell cell21 = row3.createCell(21);
				cell21.setCellStyle(colStyle);
				cell21.setCellValue(downloadinfo.get(i).getString("sendvisadate"));

				HSSFCell cell22 = row3.createCell(22);
				cell22.setCellStyle(colStyle);
				cell22.setCellValue(downloadinfo.get(i).getString("name"));

				HSSFCell cell23 = row3.createCell(23);
				cell23.setCellStyle(colStyle);
				cell23.setCellValue(downloadinfo.get(i).getString("sendVisaNum"));

				//i += query.size();

			}

			long fourthtime = System.currentTimeMillis();
			System.out.println("正文所用时间：" + (fourthtime - thirdtime) + "ms");

			CellRange(sheet, downloadinfo.size() + 4, 0, colStyle);
			long fifthtime = System.currentTimeMillis();
			System.out.println("合并单元格所用时间：" + (fifthtime - fourthtime) + "ms");

			//冻结窗格
			sheet.createFreezePane(2, 4, 2, 4);
			//sheet.createFreezePane(2, 4); 跟上边的一样，两者等价，但这个需要多跳一层到上边的方法

			//列宽度自适应，包括合并的单元格
			for (int i = 0; i < 24; i++) {
				sheet.autoSizeColumn(i, true);
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			workbook.write(baos);

			// 将文件进行编码
			String fileName = URLEncoder.encode("日本签证申请名单.xls", "UTF-8");
			// 设置下载的响应头
			//response.setContentType("application/zip");
			//通过response.reset()刷新可能存在一些未关闭的getWriter(),避免可能出现未关闭的getWriter()
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);// 设置文件名
			// 将字节流相应到浏览器（下载）
			IOUtils.write(baos.toByteArray(), response.getOutputStream());
			response.flushBuffer();
			workbook.close();
			IOUtils.closeQuietly(baos);

		} catch (Exception e) {

		}
		long lasttime = System.currentTimeMillis();
		System.out.println("下载所用时间：" + (lasttime - firsttime) + "ms");

		return stream;
	}

	/**
	 * 合并单元格
	 * @param rowcount 总行数
	 * @param colNum 合并哪一列
	 */
	private void CellRange(Sheet sheet, int rowcount, int colNum, CellStyle cs) {
		int currnetRow = 4;
		for (int p = 4; p < rowcount; p++) {
			Cell currentCell = sheet.getRow(p).getCell(colNum);
			String current = getStringCellValue(currentCell);
			Cell nextCell = null;
			String next = "";

			if (p < rowcount + 1) {
				Row nowRow = sheet.getRow(p + 1);
				if (nowRow != null) {
					nextCell = nowRow.getCell(colNum);
					next = getStringCellValue(nextCell);
				} else {
					next = "";
				}
			} else {
				next = "";
			}
			if (current.equals(next)) {
				continue;
			} else {

				if (p != currnetRow) {
					sheet.addMergedRegion(new CellRangeAddress(currnetRow, p, 0, 0));
					Cell nowCell = sheet.getRow(currnetRow).getCell(0);
					nowCell.setCellValue(Integer.valueOf(current.substring(0, current.indexOf("."))));
					nowCell.setCellStyle(cs);

					sheet.addMergedRegion(new CellRangeAddress(currnetRow, p, 1, 1));

					Cell currentCell1 = sheet.getRow(p).getCell(1);
					String current1 = getStringCellValue(currentCell1);

					Cell nowCell1 = sheet.getRow(currnetRow).getCell(1);
					nowCell1.setCellValue(current1);
					nowCell1.setCellStyle(cs);

					sheet.addMergedRegion(new CellRangeAddress(currnetRow, p, 2, 2));

					Cell currentCell2 = sheet.getRow(p).getCell(2);
					String current2 = getStringCellValue(currentCell2);

					Cell nowCell2 = sheet.getRow(currnetRow).getCell(2);
					nowCell2.setCellValue(current2);
					nowCell2.setCellStyle(cs);

					sheet.addMergedRegion(new CellRangeAddress(currnetRow, p, 8, 8));

					Cell currentCell8 = sheet.getRow(p).getCell(8);
					String current8 = getStringCellValue(currentCell8);
					if (current8.contains(".")) {
						current8 = current8.substring(0, current8.indexOf("."));
					}

					Cell nowCell8 = sheet.getRow(currnetRow).getCell(8);
					nowCell8.setCellValue(Integer.valueOf(current8));
					nowCell8.setCellStyle(cs);

					//人数需要用excel计算，所以只能留一个值
					for (int i = currnetRow + 1; i <= p; i++) {
						Cell cell = sheet.getRow(i).getCell(8);
						cell.setCellValue("");
					}

					sheet.addMergedRegion(new CellRangeAddress(currnetRow, p, 9, 9));

					Cell currentCell9 = sheet.getRow(p).getCell(9);
					String current9 = getStringCellValue(currentCell9);

					Cell nowCell9 = sheet.getRow(currnetRow).getCell(9);
					nowCell9.setCellValue(current9);
					nowCell9.setCellStyle(cs);

					sheet.addMergedRegion(new CellRangeAddress(currnetRow, p, 10, 10));

					Cell currentCell10 = sheet.getRow(p).getCell(10);
					String current10 = getStringCellValue(currentCell10);

					Cell nowCell10 = sheet.getRow(currnetRow).getCell(10);
					nowCell10.setCellValue(current10);
					nowCell10.setCellStyle(cs);

					sheet.addMergedRegion(new CellRangeAddress(currnetRow, p, 11, 11));

					Cell currentCell11 = sheet.getRow(p).getCell(11);
					String current11 = getStringCellValue(currentCell11);
					Cell nowCell11 = sheet.getRow(currnetRow).getCell(11);
					nowCell11.setCellValue(current11);
					nowCell11.setCellStyle(cs);

					sheet.addMergedRegion(new CellRangeAddress(currnetRow, p, 12, 12));

					Cell currentCell12 = sheet.getRow(p).getCell(12);
					String current12 = getStringCellValue(currentCell12);

					Cell nowCell12 = sheet.getRow(currnetRow).getCell(12);
					nowCell12.setCellValue(current12);
					nowCell12.setCellStyle(cs);

					sheet.addMergedRegion(new CellRangeAddress(currnetRow, p, 13, 13));

					Cell currentCell13 = sheet.getRow(p).getCell(13);
					String current13 = getStringCellValue(currentCell13);

					Cell nowCell13 = sheet.getRow(currnetRow).getCell(13);
					nowCell13.setCellValue(current13);
					nowCell13.setCellStyle(cs);

					sheet.addMergedRegion(new CellRangeAddress(currnetRow, p, 14, 14));

					Cell currentCell14 = sheet.getRow(p).getCell(14);
					String current14 = getStringCellValue(currentCell14);

					Cell nowCell14 = sheet.getRow(currnetRow).getCell(14);
					nowCell14.setCellValue(current14);
					nowCell14.setCellStyle(cs);

					sheet.addMergedRegion(new CellRangeAddress(currnetRow, p, 15, 15));

					Cell currentCell15 = sheet.getRow(p).getCell(15);
					String current15 = getStringCellValue(currentCell15);

					Cell nowCell15 = sheet.getRow(currnetRow).getCell(15);
					nowCell15.setCellValue(current15);
					nowCell15.setCellStyle(cs);

					sheet.addMergedRegion(new CellRangeAddress(currnetRow, p, 16, 16));

					Cell currentCell16 = sheet.getRow(p).getCell(16);
					String current16 = getStringCellValue(currentCell16);

					Cell nowCell16 = sheet.getRow(currnetRow).getCell(16);
					nowCell16.setCellValue(current16);
					nowCell16.setCellStyle(cs);

					sheet.addMergedRegion(new CellRangeAddress(currnetRow, p, 17, 17));

					Cell currentCell17 = sheet.getRow(p).getCell(17);
					String current17 = getStringCellValue(currentCell17);

					Cell nowCell17 = sheet.getRow(currnetRow).getCell(17);
					nowCell17.setCellValue(current17);
					nowCell17.setCellStyle(cs);

					sheet.addMergedRegion(new CellRangeAddress(currnetRow, p, 19, 19));

					Cell currentCell19 = sheet.getRow(p).getCell(19);
					String current19 = getStringCellValue(currentCell19);

					Cell nowCell19 = sheet.getRow(currnetRow).getCell(19);
					nowCell19.setCellValue(current19);
					nowCell19.setCellStyle(cs);

					sheet.addMergedRegion(new CellRangeAddress(currnetRow, p, 20, 20));

					Cell currentCell20 = sheet.getRow(p).getCell(20);
					String current20 = getStringCellValue(currentCell20);

					Cell nowCell20 = sheet.getRow(currnetRow).getCell(20);
					nowCell20.setCellValue(current20);
					nowCell20.setCellStyle(cs);

					sheet.addMergedRegion(new CellRangeAddress(currnetRow, p, 21, 21));

					Cell currentCell21 = sheet.getRow(p).getCell(21);
					String current21 = getStringCellValue(currentCell21);

					Cell nowCell21 = sheet.getRow(currnetRow).getCell(21);
					nowCell21.setCellValue(current21);
					nowCell21.setCellStyle(cs);

					sheet.addMergedRegion(new CellRangeAddress(currnetRow, p, 22, 22));

					Cell currentCell22 = sheet.getRow(p).getCell(22);
					String current22 = getStringCellValue(currentCell22);

					Cell nowCell22 = sheet.getRow(currnetRow).getCell(22);
					nowCell22.setCellValue(current22);
					nowCell22.setCellStyle(cs);

					sheet.addMergedRegion(new CellRangeAddress(currnetRow, p, 23, 23));

					Cell currentCell23 = sheet.getRow(p).getCell(23);
					String current23 = getStringCellValue(currentCell23);

					Cell nowCell23 = sheet.getRow(currnetRow).getCell(23);
					nowCell23.setCellValue(current23);
					nowCell23.setCellStyle(cs);

				}
				currnetRow = p + 1;

			}
		}
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param currentCell
	 * @return
	 */
	private String getStringCellValue(Cell currentCell) {
		String strCell = "";
		if (currentCell != null) {
			switch (currentCell.getCellType()) {
			case XSSFCell.CELL_TYPE_STRING:
				strCell = currentCell.getStringCellValue();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				strCell = String.valueOf(currentCell.getNumericCellValue());
				break;
			case XSSFCell.CELL_TYPE_BOOLEAN:
				strCell = String.valueOf(currentCell.getBooleanCellValue());
				break;
			case XSSFCell.CELL_TYPE_BLANK:
				strCell = "";
				break;
			default:
				strCell = "";
				break;
			}
			if (strCell.equals("") || strCell == null) {
				return "";
			}
			if (currentCell == null) {
				return "";
			}
		}
		return strCell;
	}

	private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontsize, boolean flag, boolean flag1,
			int count) {
		// TODO Auto-generated method stub
		HSSFCellStyle style = workbook.createCellStyle();

		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

		//是否水平居中
		if (flag1) {
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		}

		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		//创建字体
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		//是否加粗字体
		if (flag) {
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		}

		font.setFontHeightInPoints(fontsize);
		//加载字体
		style.setFont(font);
		//时间
		if (count == 1) {
			style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);//右对齐
		}
		return style;
	}

	public List<Record> getDownloadinfo(ListDataForm form, HttpServletRequest request) {
		HttpSession session = request.getSession();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);

		String singlesqlStr = sqlManager.get("simpleJP_downloadExcel");
		Sql singlesql = Sqls.create(singlesqlStr);

		Cnd singlecnd = Cnd.NEW();
		if (!Util.isEmpty(form.getSearchStr())) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tr.orderNum", "like", "%" + form.getSearchStr() + "%")
					.or("tcus.linkman", "like", "%" + form.getSearchStr() + "%")
					.or("tcus.mobile", "like", "%" + form.getSearchStr() + "%")
					.or("tcus.email", "like", "%" + form.getSearchStr() + "%")
					.or("CONCAT(ta.firstName,ta.lastName)", "like", "%" + form.getSearchStr() + "%")
					.or("toj.acceptDesign", "like", "%" + form.getSearchStr() + "%")
					.or("tap.passport", "like", "%" + form.getSearchStr() + "%");
			singlecnd.and(exp);
		}

		if (!Util.isEmpty(form.getSendstartdate()) && !Util.isEmpty(form.getSendenddate())) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tr.sendVisaDate", "between", new Object[] { form.getSendstartdate(), form.getSendenddate() });
			singlecnd.and(exp);
		}

		if (!Util.isEmpty(form.getOrderstartdate()) && !Util.isEmpty(form.getOrderenddate())) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tr.createTime", "between", new Object[] { form.getOrderstartdate(), form.getOrderenddate() });
			singlecnd.and(exp);
		}
		if (!Util.isEmpty(form.getStatus())) {
			if (Util.eq(form.getStatus(), JPOrderStatusEnum.DISABLED.intKey())) {
				singlecnd.and("tr.isDisabled", "=", IsYesOrNoEnum.YES.intKey());
			} else {
				SqlExpressionGroup e1 = Cnd.exps("tr.status", "=", form.getStatus()).and("tr.isDisabled", "=",
						IsYesOrNoEnum.NO.intKey());
				singlecnd.and(e1);
			}
		}

		if (!Util.isEmpty(form.getSongqianshe())) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("toj.sendsignid", "=", form.getSongqianshe());
			singlecnd.and(exp);
		}

		if (!Util.isEmpty(form.getEmployee())) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tu.id", "=", form.getEmployee());
			singlecnd.and(exp);
		}

		if (!Util.isEmpty(form.getVisatype())) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("toj.visatype", "=", form.getVisatype());
			singlecnd.and(exp);
		}

		if (loginUser.getId().equals(loginCompany.getAdminId())) {
			//公司管理员
			singlecnd.and("tr.comId", "=", loginCompany.getId());
		} else {
			//普通的操作员
			singlecnd.and("tr.salesOpid", "=", loginUser.getId());
		}
		singlecnd.and("tr.zhaobaoupdate", "=", 1);
		singlecnd.and("tr.isDisabled", "=", 0);

		singlesql.setCondition(singlecnd);
		List<Record> singleperson = dbDao.query(singlesql, singlecnd, null);
		return singleperson;
	}

	public Object getSomething() {

		/*List<TApplicantWorkJpEntity> query = dbDao.query(TApplicantWorkJpEntity.class, null, null);
		int count = 0;
		int mount = 0;
		for (TApplicantWorkJpEntity work : query) {
			count++;
			mount++;
			work.setName(String.valueOf(count));
			work.setTelephone(String.valueOf(mount));
			String aaa = "";
			if (!Util.isEmpty(work.getName())) {
				Result parse = ToAnalysis.parse(work.getName());
				List<Term> terms = parse.getTerms();
				for (Term term : terms) {
					String name = term.getName();
					aaa += name + " ";
				}
				work.setNamesplit(aaa);
				dbDao.update(work);
			}
			dbDao.update(work);
		}*/

		/*for (int i = 0; i < 1000000; i++) {
			TApplicantWorkJpEntity work = new TApplicantWorkJpEntity();
			work.setNamesplit("有限 公司");
			dbDao.insert(work);
		}*/

		for (int i = 4952; i < 38555; i++) {
			TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, i);
			if (!Util.isEmpty(apply) && !Util.isEmpty(apply.getFirstName())) {
				TApplicantPassportEntity fetch = dbDao.fetch(TApplicantPassportEntity.class,
						Cnd.where("applicantId", "=", i));
				if (Util.isEmpty(fetch)) {
					System.out.println("丢失了的申请人id：" + i);
				}
			}

			if (i % 1000 == 0) {
				System.out.println("申请人id:" + i);
			}
			if (i == 31825) {
				System.out.println("到最后一个了！！！");
			}
		}
		return null;
	}

	/**
	 * 测试toAutofill接口
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid
	 * @param action
	 * @param orderVoucher
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object testAutofill(int orderid, String action, String orderVoucher) {

		Map<String, Object> result = getData(orderid, action, orderVoucher);

		WXBizMsgCrypt pc;
		String resultStr = "";
		String json = "";
		//System.out.println("json:" + json);
		try {
			pc = new WXBizMsgCrypt(TOKEN, ENCODINGAESKEY, APPID);
			//对数据进行加密
			json = pc.encryptMsg(JsonUtil.toJson(result), getTimeStamp(), getRandomString());
			//发送POST请求
			JSONObject resultObj = toPostAutofill(json);
			String encrypt = (String) resultObj.get("encrypt");
			//对请求返回来的encrypt解密
			resultStr = pc.decrypt(encrypt);
			System.out.println("toGetEncrypt解密后明文: " + resultStr);

			org.json.JSONObject aacodeObj = new org.json.JSONObject(resultStr);
			int successStatus = (int) aacodeObj.get("success");
			String msg = "";
			try {
				msg = (String) aacodeObj.get("msg");
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (Util.isEmpty(msg)) {
				if (Util.eq("send", action)) {
					String data = aacodeObj.getString("code");
					System.out.println("订单识别码为:" + data);
				}
			}

		} catch (AesException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 发送POST请求
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param json
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public JSONObject toPostAutofill(String json) {

		String host = HOST;
		String path = "/visa/data/japan/toAutofill?token=ODBiOGIxNDY4NjdlMzc2Yg%3d%3d";
		String method = "POST";
		String entityStr = "";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		HttpResponse response;
		System.out.println("httpurl:" + (host + path));
		System.out.println("json:" + json);
		JSONObject parseObject = null;
		try {
			response = HttpUtils.doPost(host, path, method, headers, querys, json);
			entityStr = EntityUtils.toString(response.getEntity());
			System.out.println("POST请求返回的数据：" + entityStr);
			//entityStr = entityStr.substring(1, entityStr.length() - 1);
			//entityStr = entityStr.substring(1, entityStr.length() - 1).replaceAll("\\\\", "");
			//System.out.println("entityStr:" + entityStr);
			parseObject = JSONObject.parseObject(entityStr);
			System.out.println("parseObject:" + parseObject);
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return parseObject;
	}

	/**
	 * 获取时间戳方法
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public String getTimeStamp() {
		Date date = new Date();
		//样式：yyyy年MM月dd日HH时mm分ss秒SSS毫秒
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmss");
		String timeStampStr = simpleDateFormat.format(date);
		return timeStampStr;
	}

	/**
	 * 生成随机字符串
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public String getRandomString() {
		String str = "zxcvbnmlkjhgfdsaqwertyuiop1234567890";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		//长度为几就循环几次
		for (int i = 0; i < 8; ++i) {
			//产生0-61的数字
			int number = random.nextInt(str.length());
			//将产生的数字通过length次承载到sb中
			sb.append(str.charAt(number));
		}
		//将承载的字符转换成字符串
		return sb.toString();
	}

	/**
	 * 获取所需参数的值
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid
	 * @param action
	 * @param orderVoucher
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Map<String, Object> getData(int orderid, String action, String orderVoucher) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> result = Maps.newHashMap();
		ArrayList<Object> applicantsList = new ArrayList<>();

		TOrderJpEntity orderjpinfo = dbDao.fetch(TOrderJpEntity.class, orderid);
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderjpinfo.getOrderId().longValue());
		List<TApplicantOrderJpEntity> applyorderjpList = dbDao.query(TApplicantOrderJpEntity.class,
				Cnd.where("orderId", "=", orderjpinfo.getId()), null);
		for (TApplicantOrderJpEntity applyorderjp : applyorderjpList) {
			Map<String, Object> applicant = Maps.newHashMap();
			TApplicantEntity apply = dbDao.fetch(TApplicantEntity.class, applyorderjp.getApplicantId().longValue());
			TApplicantPassportEntity passport = dbDao.fetch(TApplicantPassportEntity.class,
					Cnd.where("applicantId", "=", apply.getId()));

			applicant.put("firstname", apply.getFirstName());
			applicant.put("firstnameEn", apply.getFirstNameEn());
			applicant.put("lastname", apply.getLastName());
			applicant.put("lastnameEn", apply.getLastNameEn());

			if (!Util.isEmpty(apply.getBirthday())) {
				applicant.put("birthday", sdf.format(apply.getBirthday()));
			} else {
				applicant.put("birthday", "");
			}
			applicant.put("sex", apply.getSex());
			applicant.put("province", apply.getProvince());
			applicant.put("passportNo", passport.getPassport());
			applicant.put("isMainApplicant", applyorderjp.getIsMainApplicant());

			applicantsList.add(applicant);

		}

		Date goTripDate = orderinfo.getGoTripDate();
		Date backTripDate = orderinfo.getBackTripDate();

		Integer visaType = orderjpinfo.getVisaType();

		String goDate = "";
		String returnDate = "";

		if (!Util.isEmpty(goTripDate)) {
			goDate = sdf.format(goTripDate);
		}
		if (!Util.isEmpty(backTripDate)) {
			returnDate = sdf.format(backTripDate);
		}

		result.put("userName", "zhiliren");
		result.put("goDate", goDate);
		result.put("returnDate", returnDate);
		result.put("action", action);
		result.put("orderVoucher", orderVoucher);
		result.put("designatedNum", "GTP-BJ-000-0");
		result.put("visaType", visaType);
		result.put("applicantsList", applicantsList);

		return result;
	}

	/**
	 * 测试search接口
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderVoucher
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object testSearch(String orderVoucher) {

		long first = System.currentTimeMillis();

		Map<String, Object> result = Maps.newHashMap();
		result.put("userName", "zhiliren");
		result.put("orderVoucher", orderVoucher);

		WXBizMsgCrypt pc = null;
		String resultStr = "";
		String json = "";
		try {
			pc = new WXBizMsgCrypt(TOKEN, ENCODINGAESKEY, APPID);
			//对数据进行加密
			json = pc.encryptMsg(JsonUtil.toJson(result), getTimeStamp(), getRandomString());
		} catch (AesException e2) {

			// TODO Auto-generated catch block
			e2.printStackTrace();

		}

		//将json字符串转成json对象
		org.json.JSONObject encryptObj = new org.json.JSONObject(json);
		String timestamp = (String) encryptObj.get("timeStamp");
		String signature = (String) encryptObj.get("msg_signature");
		String nonce = (String) encryptObj.get("nonce");
		json = (String) encryptObj.get("encrypt");
		//把json urlencode
		try {
			json = URLEncoder.encode(json, "utf-8");
		} catch (UnsupportedEncodingException e1) {

			// TODO Auto-generated catch block
			e1.printStackTrace();

		}

		long second = System.currentTimeMillis();
		System.out.println("发送请求之前所用时间：" + (second - first) + "ms");

		//发送GET请求
		String host = HOST;
		String path = "/visa/data/japan/search?token=ODBiOGIxNDY4NjdlMzc2Yg%3d%3d&timeStamp=" + timestamp
				+ "&msg_signature=" + signature + "&nonce=" + nonce + "&encrypt=" + json;
		String method = "GET";
		String entityStr = "";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		HttpResponse response;
		JSONObject parseObject = null;
		try {
			response = HttpUtils.doGet(host, path, method, headers, querys);

			long third = System.currentTimeMillis();
			System.out.println("发送请求所用时间：" + (third - second) + "ms");

			entityStr = EntityUtils.toString(response.getEntity());
			System.out.println("GET请求返回的数据：" + entityStr);
			//将返回的json字符串转成json对象
			parseObject = JSONObject.parseObject(entityStr);
			String encrypt = (String) parseObject.get("encrypt");

			//解密
			resultStr = pc.decrypt(encrypt);
			System.out.println("解密后明文: " + resultStr);
			//将解密后的json字符串转为json对象
			parseObject = JSONObject.parseObject(resultStr);

			String errMsg = parseObject.getString("msg");
			if (!Util.isEmpty(errMsg)) {
				resultStr = errMsg;
				System.out.println("出错了，错误信息为：" + errMsg);
			} else {
				String orderstatus = parseObject.getString("status");
				if (orderstatus.contains(",")) {
					System.out.println("订单状态为：" + orderstatus.substring(0, orderstatus.indexOf(",")));
					System.out.println("收付番号为：" + orderstatus.substring(orderstatus.indexOf(",") + 1));
				} else {
					System.out.println("订单状态为：" + orderstatus);
				}
				resultStr = orderstatus;
			}

		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		long last = System.currentTimeMillis();
		System.out.println("方法所用时间：" + (last - first) + "ms");
		return resultStr;
	}

}
