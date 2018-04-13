package com.juyo.visa.admin.visajp.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.juyo.visa.admin.changePrincipal.service.ChangePrincipalViewService;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.enums.AlredyVisaTypeEnum;
import com.juyo.visa.common.enums.JPOrderProcessTypeEnum;
import com.juyo.visa.entities.TApplicantVisaJpEntity;
import com.juyo.visa.entities.TCountryEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TApplicantVisaJpForm;
import com.juyo.visa.forms.TApplicantVisaJpUpdateForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class ApplicantvisaViewService extends BaseService<TApplicantVisaJpEntity> {
	private static final Log log = Logs.get();

	private static final Integer VISA_PROCESS = JPOrderProcessTypeEnum.VISA_PROCESS.intKey();

	@Inject
	private ChangePrincipalViewService changePrincipalViewService;

	public Object listData(TApplicantVisaJpForm queryForm) {
		return listPage4Datatables(queryForm);
	}

	public Object visaInputAdd(Integer applicantId, Integer orderid, Integer isvisa, Integer tourist) {

		Map<String, Object> result = Maps.newHashMap();
		result.put("applicantId", applicantId);
		result.put("visadatatypeenum", EnumUtil.enum2(AlredyVisaTypeEnum.class));
		result.put("isvisa", isvisa);
		result.put("tourist", tourist);
		result.put("orderid", orderid);
		return result;
	}

	public Object updatedata(TApplicantVisaJpUpdateForm updateForm, HttpSession session) {

		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();

		TApplicantVisaJpEntity fetch = this.fetch(updateForm.getId());
		fetch.setStayDays(updateForm.getStayDays());
		fetch.setUpdateTime(new Date());
		fetch.setValidDate(updateForm.getValidDate());
		fetch.setVisaAddress(updateForm.getVisaAddress());
		fetch.setVisaCountry(updateForm.getVisaCountry());
		fetch.setVisaDate(updateForm.getVisaDate());
		fetch.setVisaNum(updateForm.getVisaNum());
		fetch.setVisaType(updateForm.getVisaType());
		fetch.setVisaYears(updateForm.getVisaYears());
		fetch.setPicUrl(updateForm.getPicUrl());
		if (!Util.isEmpty(updateForm.getIsvisa())) {
			fetch.setVisaEntryTime(new Date());
		}

		//变更订单负责人
		Integer orderid = updateForm.getOrderid();
		changePrincipalViewService.ChangePrincipal(orderid.intValue(), VISA_PROCESS, userId);

		return dbDao.update(fetch);
	}

	/**
	 * 获取国家下拉
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param searchstr
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getNationalSelect(String searchstr) {
		List<TCountryEntity> result = Lists.newArrayList();
		List<TCountryEntity> query = dbDao.query(TCountryEntity.class,
				Cnd.where("chinesename", "like", "%" + searchstr.trim() + "%"), null);
		if (!Util.isEmpty(query)) {
			if (query.size() > 5) {
				result = query.subList(0, 5);
			} else {
				result = query;
			}
		}
		return result;
	}

	/**
	 * 自动计算有效期
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param visaDate
	 * @param visaYears
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public String autoCalcToDate(String visaDate, Integer visaYears) {
		Date visadatedate = DateUtil.string2Date(visaDate, DateUtil.FORMAT_YYYY_MM_DD);
		Date addYear = DateUtil.addYear(visadatedate, visaYears);
		Date addDay = DateUtil.addDay(addYear, -1);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(addDay);
		return result;
	}
}