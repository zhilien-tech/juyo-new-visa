package com.juyo.visa.admin.visajp.service;

import java.util.Date;
import java.util.Map;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Maps;
import com.juyo.visa.common.enums.AlredyVisaTypeEnum;
import com.juyo.visa.entities.TApplicantVisaJpEntity;
import com.juyo.visa.forms.TApplicantVisaJpForm;
import com.juyo.visa.forms.TApplicantVisaJpUpdateForm;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class ApplicantvisaViewService extends BaseService<TApplicantVisaJpEntity> {
	private static final Log log = Logs.get();

	public Object listData(TApplicantVisaJpForm queryForm) {
		return listPage4Datatables(queryForm);
	}

	public Object visaInputAdd(Integer applicantId, Integer isvisa) {

		Map<String, Object> result = Maps.newHashMap();
		result.put("applicantId", applicantId);
		result.put("visadatatypeenum", EnumUtil.enum2(AlredyVisaTypeEnum.class));
		result.put("isvisa", isvisa);
		return result;
	}

	public Object updatedata(TApplicantVisaJpUpdateForm updateForm) {

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
		return dbDao.update(fetch);
	}

}