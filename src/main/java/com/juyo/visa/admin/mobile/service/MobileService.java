/**
 * MobileService.java
 * com.juyo.visa.admin.mobile.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.mobile.service;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.mobile.form.MobileApplicantForm;
import com.juyo.visa.common.util.MapUtil;
import com.juyo.visa.entities.TApplicantEntity;
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
		//出生日期渲染
		if (!Util.isEmpty(applicant.getValidStartDate())) {
			String validstartdate = format.format(applicant.getValidStartDate());
			applicantmap.put("validstartdate", validstartdate);
		}
		//出生日期渲染
		if (!Util.isEmpty(applicant.getValidEndDate())) {
			String validenddate = format.format(applicant.getValidEndDate());
			applicantmap.put("validenddate", validenddate);
		}
		result.put("applicatdata", applicantmap);
		return result;
	}
}
