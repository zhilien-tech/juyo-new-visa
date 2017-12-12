/**
 * MobileService.java
 * com.juyo.visa.admin.mobile.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.mobile.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.mobile.form.MobileApplicantForm;
import com.juyo.visa.admin.user.form.ApplicantUser;
import com.juyo.visa.admin.user.service.UserViewService;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.TrialApplicantStatusEnum;
import com.juyo.visa.common.util.MapUtil;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantLowerEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TApplicantPassportEntity;
import com.juyo.visa.entities.TApplicantPassportLowerEntity;
import com.juyo.visa.entities.TApplicantWorkJpEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * 手机端接口service
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年12月11日 	 
 */
@IocBean
public class MobileService extends BaseService<TApplicantEntity> {

	@Inject
	private UserViewService userViewService;

	/**
	 *手机端申请人页面数据接口
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object applicatinfo(MobileApplicantForm form) {
		Map<String, Object> result = Maps.newHashMap();
		TApplicantEntity applicant = new TApplicantEntity();
		if (!Util.isEmpty(form.getApplicantid())) {
			applicant = dbDao.fetch(TApplicantEntity.class, form.getApplicantid().longValue());
		}
		Map<String, String> applicantmap = MapUtil.obj2Map(applicant);
		if (!Util.isEmpty(form.getComid())) {
			applicantmap.put("comid", form.getComid().toString());
		}
		if (!Util.isEmpty(form.getUserid())) {
			applicantmap.put("userid", form.getUserid().toString());
		}
		if (!Util.isEmpty(form.getOrderid())) {
			applicantmap.put("orderid", form.getOrderid().toString());
		}
		if (!Util.isEmpty(form.getApplicantid())) {
			applicantmap.put("applicantid", form.getApplicantid().toString());
		}
		SimpleDateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		//出生日期渲染
		if (!Util.isEmpty(applicant.getBirthday())) {
			String birthday = format.format(applicant.getBirthday());
			applicantmap.put("birthday", birthday);
		}
		//签发日期渲染
		if (!Util.isEmpty(applicant.getValidStartDate())) {
			String validstartdate = format.format(applicant.getValidStartDate());
			applicantmap.put("validstartdate", validstartdate);
		}
		//有效日期渲染
		if (!Util.isEmpty(applicant.getValidEndDate())) {
			String validenddate = format.format(applicant.getValidEndDate());
			applicantmap.put("validenddate", validenddate);
		}
		//创建日期渲染
		if (!Util.isEmpty(applicant.getCreateTime())) {
			String createtime = format.format(applicant.getCreateTime());
			applicantmap.put("createtime", createtime);
		}
		//更新日期渲染
		if (!Util.isEmpty(applicant.getUpdateTime())) {
			String updatetime = format.format(applicant.getUpdateTime());
			applicantmap.put("updatetime", updatetime);
		}
		result.put("applicatdata", applicantmap);
		return result;
	}

	/**
	 * 保存基本信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param applicantEntity
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveApplicatinfo(MobileApplicantForm form, TApplicantLowerEntity applicant) {

		//编辑申请人信息
		if (!Util.isEmpty(applicant.getId())) {
			dbDao.update(applicant);
		} else {
			//在用户表添加信息
			ApplicantUser applicantUser = new ApplicantUser();
			applicantUser.setMobile(applicant.getTelephone());
			applicantUser.setOpid(form.getUserid());
			applicantUser.setPassword("000000");
			applicantUser.setUsername(applicant.getFirstname() + applicant.getLastname());
			TUserEntity tUserEntity = userViewService.addApplicantUser(applicantUser);

			applicant.setUserid(tUserEntity.getId());
			applicant.setStatus(TrialApplicantStatusEnum.FIRSTTRIAL.intKey());
			applicant.setCreatetime(new Date());
			TApplicantLowerEntity applicatinsert = dbDao.insert(applicant);
			//设置申请人id，在编辑护照信息时候使用
			form.setApplicantid(applicatinsert.getId());
			Integer orderjpid = null;
			//如果订单存在则保存订单
			if (!Util.isEmpty(form.getOrderid())) {
				//查询日本订单表信息
				TOrderJpEntity orderjp = dbDao
						.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", form.getOrderid()));
				orderjpid = orderjp.getId();

			} else {
				//如果订单不存在，则先创建订单
				TOrderEntity orderinfo = new TOrderEntity();
				orderinfo.setComId(form.getComid());
				orderinfo.setUserId(form.getUserid());
				orderinfo.setOrderNum(generrateOrdernum());
				orderinfo.setStatus(JPOrderStatusEnum.PLACE_ORDER.intKey());
				orderinfo.setCreateTime(new Date());
				TOrderEntity orderinsert = dbDao.insert(orderinfo);
				TOrderJpEntity orderjp = new TOrderJpEntity();
				orderjp.setOrderId(orderinsert.getId());
				TOrderJpEntity orderjpinsert = dbDao.insert(orderjp);
				orderjpid = orderjpinsert.getId();
			}
			//保存日本申请人信息
			TApplicantOrderJpEntity applicantjp = new TApplicantOrderJpEntity();
			applicantjp.setOrderId(orderjpid);
			applicantjp.setApplicantId(applicatinsert.getId());
			TApplicantOrderJpEntity applicantjpinsert = dbDao.insert(applicantjp);
			Integer applicantJpId = applicantjpinsert.getId();
			//日本工作信息
			TApplicantWorkJpEntity workJp = new TApplicantWorkJpEntity();
			workJp.setApplicantId(applicantJpId);
			workJp.setCreateTime(new Date());
			workJp.setOpId(form.getUserid());
			dbDao.insert(workJp);
			//护照信息
			TApplicantPassportEntity passport = new TApplicantPassportEntity();
			passport.setSex(applicant.getSex());
			passport.setFirstName(applicant.getFirstname());
			passport.setLastName(applicant.getLastname());
			if (!Util.isEmpty(applicant.getFirstnameen())) {
				passport.setFirstNameEn(applicant.getFirstnameen().substring(1));
			}
			if (!Util.isEmpty(applicant.getLastnameen())) {
				passport.setLastNameEn(applicant.getLastnameen().substring(1));
			}
			passport.setIssuedOrganization("公安部出入境管理局");
			passport.setIssuedOrganizationEn("MPS Exit&Entry Adiministration");
			passport.setApplicantId(applicatinsert.getId());
			dbDao.insert(passport);
		}
		return form;

	}

	private String generrateOrdernum() {
		//生成订单号
		SimpleDateFormat smf = new SimpleDateFormat("yyMMdd");
		String format = smf.format(new Date());
		String sqlString = sqlManager.get("orderJp_ordernum");
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
	 * 护照信息回显
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object passportinfo(MobileApplicantForm form) {
		Map<String, Object> result = Maps.newHashMap();
		TApplicantPassportEntity passportinfo = dbDao.fetch(TApplicantPassportEntity.class,
				Cnd.where("applicantId", "=", form.getApplicantid()));
		Map<String, String> passportmap = MapUtil.obj2Map(passportinfo);
		SimpleDateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		SimpleDateFormat fullformat = new SimpleDateFormat(DateUtil.FORMAT_FULL_PATTERN);
		//出生日期渲染
		if (!Util.isEmpty(passportinfo.getBirthday())) {
			String birthday = format.format(passportinfo.getBirthday());
			passportmap.put("birthday", birthday);
		}
		//签发日期渲染
		if (!Util.isEmpty(passportinfo.getIssuedDate())) {
			String issueddate = format.format(passportinfo.getIssuedDate());
			passportmap.put("issueddate", issueddate);
		}
		//有效期始渲染
		if (!Util.isEmpty(passportinfo.getValidStartDate())) {
			String validstartdate = format.format(passportinfo.getValidStartDate());
			passportmap.put("validstartdate", validstartdate);
		}
		//有效期至渲染
		if (!Util.isEmpty(passportinfo.getValidEndDate())) {
			String validenddate = format.format(passportinfo.getValidEndDate());
			passportmap.put("validenddate", validenddate);
		}
		//创建时间渲染
		if (!Util.isEmpty(passportinfo.getCreateTime())) {
			String createtime = fullformat.format(passportinfo.getCreateTime());
			passportmap.put("createtime", createtime);
		}
		//更新时间渲染
		if (!Util.isEmpty(passportinfo.getUpdateTime())) {
			String updatetime = fullformat.format(passportinfo.getUpdateTime());
			passportmap.put("updatetime", updatetime);
		}
		result.put("passportdata", passportmap);
		return result;

	}

	/***
	 * 保存护照信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param passportinfo
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object savePassportInfo(TApplicantPassportLowerEntity passportinfo) {
		return dbDao.update(passportinfo);
	}
}
