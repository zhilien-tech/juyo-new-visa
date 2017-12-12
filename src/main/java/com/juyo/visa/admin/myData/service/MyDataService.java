package com.juyo.visa.admin.myData.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.enums.PassportTypeEnum;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * 我的资料Service
 * <p>
 *
 * @author   
 * @Date	 2017年12月08日 	 
 */
@IocBean
public class MyDataService extends BaseService<TOrderJpEntity> {

	public Object getBasicInfo(HttpSession session) {
		Map<String, Object> result = MapUtil.map();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class,
				Cnd.where("userId", "=", loginUser.getId()));
		TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantEntity.getId()));
		TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId()
				.longValue());
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderJpEntity.getOrderId().longValue());
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		if (!Util.isEmpty(applicantEntity.getBirthday())) {
			Date birthday = applicantEntity.getBirthday();
			String birthdayStr = sdf.format(birthday);
			result.put("birthday", birthdayStr);
		}
		if (!Util.isEmpty(applicantEntity.getValidStartDate())) {
			Date validStartDate = applicantEntity.getValidStartDate();
			String validStartDateStr = sdf.format(validStartDate);
			result.put("validStartDate", validStartDateStr);
		}
		if (!Util.isEmpty(applicantEntity.getValidEndDate())) {
			Date validEndDate = applicantEntity.getValidEndDate();
			String validEndDateStr = sdf.format(validEndDate);
			result.put("validEndDate", validEndDateStr);
		}

		if (!Util.isEmpty(applicantEntity.getFirstNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicantEntity.getFirstNameEn());
			result.put("firstNameEn", sb.toString());
		}

		if (!Util.isEmpty(applicantEntity.getLastNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(applicantEntity.getLastNameEn());
			result.put("lastNameEn", sb.toString());
		}
		result.put("applicantId", applicantEntity.getId());
		result.put("orderid", orderEntity.getId());
		result.put("applicant", applicantEntity);
		return result;
	}

	public Object getPassportInfo(HttpSession session) {
		Map<String, Object> result = MapUtil.map();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TApplicantEntity applicantEntity = dbDao.fetch(TApplicantEntity.class,
				Cnd.where("userId", "=", loginUser.getId()));
		TApplicantOrderJpEntity applicantOrderJpEntity = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantEntity.getId()));
		TOrderJpEntity orderJpEntity = dbDao.fetch(TOrderJpEntity.class, applicantOrderJpEntity.getOrderId()
				.longValue());
		TOrderEntity orderEntity = dbDao.fetch(TOrderEntity.class, orderJpEntity.getOrderId().longValue());
		String passportSqlstr = sqlManager.get("orderJp_list_passportInfo_byApplicantId");
		Sql passportSql = Sqls.create(passportSqlstr);
		passportSql.setParam("id", applicantEntity.getId());
		Record passport = dbDao.fetch(passportSql);
		//格式化日期
		DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		if (!Util.isEmpty(passport.get("birthday"))) {
			Date goTripDate = (Date) passport.get("birthday");
			passport.put("birthday", format.format(goTripDate));
		}
		if (!Util.isEmpty(passport.get("validEndDate"))) {
			Date goTripDate = (Date) passport.get("validEndDate");
			passport.put("validEndDate", format.format(goTripDate));
		}
		if (!Util.isEmpty(passport.get("issuedDate"))) {
			Date goTripDate = (Date) passport.get("issuedDate");
			passport.put("issuedDate", format.format(goTripDate));
		}
		if (!Util.isEmpty(passport.get("firstNameEn"))) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(passport.get("firstNameEn"));
			result.put("firstNameEn", sb.toString());
		}

		if (!Util.isEmpty(passport.get("lastNameEn"))) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(passport.get("lastNameEn"));
			result.put("lastNameEn", sb.toString());
		}
		result.put("passport", passport);
		result.put("passportType", EnumUtil.enum2(PassportTypeEnum.class));
		result.put("applicantId", applicantEntity.getId());
		result.put("orderid", orderEntity.getId());
		return result;
	}
}
