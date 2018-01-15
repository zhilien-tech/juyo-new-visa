package com.juyo.visa.admin.visajp.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.changePrincipal.service.ChangePrincipalViewService;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.enums.AlredyVisaTypeEnum;
import com.juyo.visa.common.enums.JPOrderProcessTypeEnum;
import com.juyo.visa.entities.TApplicantVisaJpEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TApplicantVisaJpForm;
import com.juyo.visa.forms.TApplicantVisaJpUpdateForm;
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

	public Object visaInputAdd(Integer applicantId, Integer isvisa, Integer tourist) {

		Map<String, Object> result = Maps.newHashMap();
		result.put("applicantId", applicantId);
		result.put("visadatatypeenum", EnumUtil.enum2(AlredyVisaTypeEnum.class));
		result.put("isvisa", isvisa);
		result.put("tourist", tourist);
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

}