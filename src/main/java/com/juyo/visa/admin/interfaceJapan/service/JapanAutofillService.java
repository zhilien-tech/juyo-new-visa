/**
 * SendZhaobaoService.java
 * com.juyo.visa.admin.interfaceJapan.service
 * Copyright (c) 2019, 北京科技有限公司版权所有.
 */

package com.juyo.visa.admin.interfaceJapan.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.interfaceJapan.entity.ApplicantInfo;
import com.juyo.visa.admin.interfaceJapan.form.AutofillDataForm;
import com.juyo.visa.admin.order.service.OrderJpViewService;
import com.juyo.visa.admin.simple.service.SimpleVisaService;
import com.juyo.visa.admin.visajp.service.DownLoadVisaFileService;
import com.juyo.visa.admin.visajp.util.TemplateUtil;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.JpOrderSimpleEnum;
import com.juyo.visa.common.enums.TrialApplicantStatusEnum;
import com.juyo.visa.common.util.ResultObject;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TApplicantPassportEntity;
import com.juyo.visa.entities.TApplicantVisaOtherInfoEntity;
import com.juyo.visa.entities.TApplicantWorkJpEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderLogsEntity;
import com.juyo.visa.entities.TOrderTripJpEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2019年4月2日 	 
 */
@IocBean
public class JapanAutofillService extends BaseService<TOrderEntity> {

	@Inject
	private SimpleVisaService simpleVisaService;

	@Inject
	private OrderJpViewService orderJpViewService;

	@Inject
	private DownLoadVisaFileService downLoadVisaFileService;

	@Inject
	private UploadService qiniuUpService;

	public Object sendZhaobao(AutofillDataForm form) {
		System.out.println("访问到了~~~~~");
		System.out.println("传过来的数据为:" + form);

		//信息验证(身份，是否满足发招宝所需数据)

		String resultstr = infoValidate(form);
		if (!Util.isEmpty(resultstr)) {
			resultstr = resultstr.substring(0, resultstr.length() - 1);
			if (!resultstr.endsWith("正确")) {
				resultstr += "不能为空";
			}
			return ResultObject.fail(resultstr);
		}

		//待补充

		//领区，送签社，地接社

		String userName = form.getUserName();
		TUserEntity loginUser = dbDao.fetch(TUserEntity.class, Cnd.where("mobile", "=", userName));
		if (Util.isEmpty(loginUser)) {
			return ResultObject.fail("查无此人");
		}
		TCompanyEntity loginCompany = dbDao.fetch(TCompanyEntity.class, Cnd.where("adminId", "=", loginUser.getId()));
		if (Util.isEmpty(loginCompany)) {
			return ResultObject.fail("没有这个公司");
		}

		//验证通过后，把数据入库
		Map<String, Object> insertInfo = insertInfo(form, loginUser, loginCompany);
		TOrderEntity orderinfo = (TOrderEntity) insertInfo.get("orderinfo");
		TOrderJpEntity orderjpinfo = (TOrderJpEntity) insertInfo.get("orderjpinfo");

		//生成excel

		//申请人信息
		Map<String, Object> tempdata = new HashMap<String, Object>();
		String applysqlstr = sqlManager.get("get_applyinfo_from_filedown_by_orderid_jp");
		Sql applysql = Sqls.create(applysqlstr);
		Cnd cnd = Cnd.NEW();
		cnd.and("taoj.orderId", "=", orderjpinfo.getId());
		cnd.orderBy("taoj.isMainApplicant", "DESC").orderBy("taoj.id", "ASC");
		List<Record> applyinfo = dbDao.query(applysql, cnd, null);
		tempdata.put("applyinfo", applyinfo);
		//excel导出
		ByteArrayOutputStream excelExport = downLoadVisaFileService.excelExport(tempdata);
		//生成excel临时文件
		TemplateUtil templateUtil = new TemplateUtil();
		File excelfile = templateUtil.createTempFile(excelExport);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(excelfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String qiniuurl = qiniuUpService.uploadImage(fileInputStream, "xlsx", null);
		//返回上传后七牛云的路径
		String fileqiniupath = CommonConstants.IMAGES_SERVER_ADDR + qiniuurl;

		orderjpinfo.setZhaobaotime(new Date());
		//保存生成的七牛excel路径
		orderjpinfo.setExcelurl(fileqiniupath);
		dbDao.update(orderjpinfo);
		//更新订单状态为发招保中或准备提交大使馆，此时发招宝就会开始，所以必须在准备工作之后，即orderjp相关的操作和excel完成之后

		Integer userId = loginUser.getId();
		orderinfo.setVisaOpid(userId);

		//改变订单状态为发招宝
		orderinfo.setStatus(JPOrderStatusEnum.READYCOMMING.intKey());

		dbDao.update(orderinfo);
		return ResultObject.success(orderinfo.getId());
	}

	//信息验证
	public String infoValidate(AutofillDataForm form) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer resultstrbuf = new StringBuffer("");
		StringBuffer datebuf = new StringBuffer("");
		int count = 1;
		if (Util.isEmpty(form.getGoDate())) {
			resultstrbuf.append("出发日期、");
		} else {
			try {
				sdf.parse(form.getGoDate());
			} catch (ParseException e) {
				datebuf.append("出发日期、");
				e.printStackTrace();

			}
		}
		if (Util.isEmpty(form.getReturnDate())) {
			resultstrbuf.append("返回日期、");
		} else {
			try {
				sdf.parse(form.getReturnDate());
			} catch (ParseException e) {
				datebuf.append("返回日期、");
				e.printStackTrace();

			}
		}
		if (Util.isEmpty(form.getUserName())) {
			resultstrbuf.append("公司用户名、");
		}
		if (Util.isEmpty(form.getVisaType())) {
			resultstrbuf.append("签证类型、");
		}
		if (Util.isEmpty(form.getApplicantsList())) {
			resultstrbuf.append("申请人、");
		} else {
			for (ApplicantInfo applicant : form.getApplicantsList()) {
				if (Util.isEmpty(applicant.getFirstname())) {
					resultstrbuf.append("申请人" + count + "的姓、");
				}
				if (Util.isEmpty(applicant.getFirstnameEn())) {
					resultstrbuf.append("申请人" + count + "的姓拼音、");
				}
				if (Util.isEmpty(applicant.getLastname())) {
					resultstrbuf.append("申请人" + count + "的名、");
				}
				if (Util.isEmpty(applicant.getLastnameEn())) {
					resultstrbuf.append("申请人" + count + "的名拼音、");
				}
				if (Util.isEmpty(applicant.getBirthday())) {
					resultstrbuf.append("申请人" + count + "的出生日期、");
				} else {
					try {
						sdf.parse(applicant.getBirthday());
					} catch (ParseException e) {
						datebuf.append("申请人" + count + "的出生日期、");
						e.printStackTrace();

					}
				}
				if (Util.isEmpty(applicant.getIsMainApplicant())) {
					resultstrbuf.append("申请人" + count + "是否是主申请人、");
				}
				if (Util.isEmpty(applicant.getPassportNo())) {
					resultstrbuf.append("申请人" + count + "的护照号、");
				}
				if (Util.isEmpty(applicant.getProvince())) {
					resultstrbuf.append("申请人" + count + "的现居住地、");
				}
				if (Util.isEmpty(applicant.getSex())) {
					resultstrbuf.append("申请人" + count + "的性别、");
				}
				count++;
			}
		}
		String datestr = datebuf.toString();
		if (!Util.isEmpty(datestr)) {
			datebuf.deleteCharAt(datebuf.length() - 1).append("格式不正确，").append(resultstrbuf);
		} else {
			datebuf.append(resultstrbuf);
		}

		return datebuf.toString();
	}

	public Map<String, Object> insertInfo(AutofillDataForm form, TUserEntity loginUser, TCompanyEntity loginCompany) {
		Map<String, Object> result = Maps.newTreeMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//根据身份信息先创建订单
		Map<String, Integer> generrateorder = simpleVisaService.generrateorder(loginUser, loginCompany);
		Integer orderid = generrateorder.get("orderid");
		Integer orderjpid = generrateorder.get("orderjpid");
		insertLogs(orderid, JpOrderSimpleEnum.PLACE_ORDER.intKey(), loginUser);

		TOrderJpEntity orderjpinfo = dbDao.fetch(TOrderJpEntity.class, orderjpid.longValue());
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderid.longValue());

		if (!Util.isEmpty(form.getGoDate())) {
			try {
				orderinfo.setGoTripDate(sdf.parse(form.getGoDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (!Util.isEmpty(form.getReturnDate())) {
			try {
				orderinfo.setBackTripDate(sdf.parse(form.getReturnDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		Integer visatype = form.getVisaType();
		if (visatype == 7) {
			orderjpinfo.setVisaCounty("冲绳县");
		} else if (visatype == 8) {
			orderjpinfo.setVisaCounty("宫城县");
		} else if (visatype == 9) {
			orderjpinfo.setVisaCounty("福岛县");
		} else if (visatype == 10) {
			orderjpinfo.setVisaCounty("岩手县");
		} else if (visatype == 11) {
			orderjpinfo.setVisaCounty("青森县");
		} else if (visatype == 12) {
			orderjpinfo.setVisaCounty("秋田县");
		} else if (visatype == 13) {
			orderjpinfo.setVisaCounty("山形县");
		} else {
			orderjpinfo.setVisaCounty("");
		}
		orderjpinfo.setVisaType(visatype);

		//出行信息
		TOrderTripJpEntity orderjptrip = new TOrderTripJpEntity();

		orderjptrip.setTripPurpose("旅游");
		orderjptrip.setTripType(1);
		if (!Util.isEmpty(form.getGoDate())) {
			try {
				orderjptrip.setGoDate(sdf.parse(form.getGoDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (!Util.isEmpty(form.getReturnDate())) {
			try {
				orderjptrip.setReturnDate(sdf.parse(form.getReturnDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		orderjptrip.setOrderId(orderjpid);

		dbDao.insert(orderjptrip);

		//创建申请人
		List<ApplicantInfo> applicantsList = form.getApplicantsList();
		for (ApplicantInfo applicantinfo : applicantsList) {
			//新建申请人表
			TApplicantEntity apply = new TApplicantEntity();
			apply.setOpId(loginUser.getId());
			apply.setIsSameInfo(IsYesOrNoEnum.YES.intKey());
			apply.setIsPrompted(IsYesOrNoEnum.NO.intKey());
			apply.setStatus(TrialApplicantStatusEnum.FIRSTTRIAL.intKey());
			apply.setFirstName(applicantinfo.getFirstname());
			apply.setFirstNameEn(applicantinfo.getFirstnameEn());
			apply.setLastName(applicantinfo.getLastname());
			apply.setLastNameEn(applicantinfo.getLastnameEn());
			apply.setSex(applicantinfo.getSex());
			if (!Util.isEmpty(applicantinfo.getBirthday())) {
				try {
					apply.setBirthday(sdf.parse(applicantinfo.getBirthday()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			apply.setProvince(applicantinfo.getProvince());

			apply.setCreateTime(new Date());
			TApplicantEntity insertApply = dbDao.insert(apply);
			int applyid = insertApply.getId();

			if (Util.eq(1, applicantinfo.getIsMainApplicant())) {
				apply.setMainId(applyid);
				dbDao.update(apply);
			}

			//新建护照信息表
			TApplicantPassportEntity passport = new TApplicantPassportEntity();
			passport.setApplicantId(applyid);
			passport.setFirstName(applicantinfo.getFirstname());
			passport.setFirstNameEn(applicantinfo.getFirstnameEn());
			passport.setLastName(applicantinfo.getLastname());
			passport.setLastNameEn(applicantinfo.getLastnameEn());
			passport.setSex(applicantinfo.getSex());
			if (!Util.isEmpty(applicantinfo.getBirthday())) {
				try {
					passport.setBirthday(sdf.parse(applicantinfo.getBirthday()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			passport.setPassport(applicantinfo.getPassportNo());
			passport.setIssuedOrganization("公安部出入境管理局");
			passport.setIssuedOrganizationEn("MPS Exit&Entry Adiministration");
			dbDao.insert(passport);

			//新建日本申请人表
			TApplicantOrderJpEntity applicantjp = new TApplicantOrderJpEntity();

			applicantjp.setOrderId(orderjpid);
			applicantjp.setApplicantId(applyid);
			applicantjp.setBaseIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setPassIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setVisaIsCompleted(IsYesOrNoEnum.NO.intKey());
			applicantjp.setIsMainApplicant(applicantinfo.getIsMainApplicant());
			TApplicantOrderJpEntity insertappjp = dbDao.insert(applicantjp);

			//日本工作信息
			TApplicantWorkJpEntity workJp = new TApplicantWorkJpEntity();
			workJp.setApplicantId(insertappjp.getId());
			workJp.setCreateTime(new Date());
			workJp.setOpId(loginUser.getId());
			dbDao.insert(workJp);

			TApplicantVisaOtherInfoEntity visaother = new TApplicantVisaOtherInfoEntity();
			visaother.setApplicantid(insertappjp.getId());
			visaother.setHotelname("参照'赴日予定表'");
			visaother.setVouchname("参照'身元保证书'");
			visaother.setInvitename("同上");
			visaother.setTraveladvice("推荐");
			dbDao.insert(visaother);

		}
		result.put("orderinfo", orderinfo);
		result.put("orderjpinfo", orderjpinfo);
		return result;
	}

	//添加日志
	public void insertLogs(Integer orderid, Integer status, TUserEntity loginUser) {
		TOrderLogsEntity logs = new TOrderLogsEntity();
		logs.setCreateTime(new Date());
		logs.setOpId(loginUser.getId());
		logs.setOrderId(orderid);
		logs.setOrderStatus(status);
		logs.setUpdateTime(new Date());
		dbDao.insert(logs);
	}

	public Object search() {
		return "ok";
	}
}
