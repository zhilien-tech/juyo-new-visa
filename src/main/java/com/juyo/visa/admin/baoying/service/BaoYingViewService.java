package com.juyo.visa.admin.baoying.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.entity.Record;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.baoying.form.TAppStaffMixInfoForm;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JapanPrincipalChangeEnum;
import com.juyo.visa.common.enums.orderUS.DistrictEnum;
import com.juyo.visa.common.enums.orderUS.USOrderListStatusEnum;
import com.juyo.visa.common.enums.visaProcess.YesOrNoEnum;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class BaoYingViewService extends BaseService<TAppStaffBasicinfoEntity> {
	private static final Log log = Logs.get();

	/**日/月/年格式*/
	public static final String FORMAT_DD_MM_YYYY = "dd/MM/yyyy";

	@Inject
	private UploadService qiniuUploadService;//文件上传


	private final static Integer DEFAULT_IS_NO = YesOrNoEnum.NO.intKey();
	private final static Integer US_YUSHANG_COMID = 65;

	/**
	 * 列表页准备内容
	 */
	public Object toList(HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("searchStatus", EnumUtil.enum2(USOrderListStatusEnum.class));
		result.put("cityid", EnumUtil.enum2(DistrictEnum.class));
		result.put("isDisable", IsYesOrNoEnum.YES.intKey());//详情页不可编辑
		return result;
	}

	/**
	 * 
	 * 葆婴 人员管理列表页
	 *
	 * @param queryForm
	 * @param session
	 * @return
	 */
	public Object listData(TAppStaffMixInfoForm queryForm, HttpSession session) {

		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//葆婴 可以看到誉尚的订单信息
		queryForm.setComid(US_YUSHANG_COMID);

		Map<String, Object> map = listPage4Datatables(queryForm);
		List<Record> records = (List<Record>) map.get("data");
		for (Record record : records) {
			Object interviewdateStr = record.get("interviewdate");
			if(!Util.isEmpty(interviewdateStr)) {
				Date interviewdate = (Date)interviewdateStr;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				record.set("interviewdate", sdf.format(interviewdate));
			}
			
			Object cityObj = record.get("cityid");
			if (!Util.isEmpty(cityObj)) {
				int cityid = (int) cityObj;
				for (DistrictEnum district : DistrictEnum.values()) {
					if (cityid == district.intKey()) {
						record.set("cityid", district.value());
					}
				}
			}
			int orderStatus = (int) record.get("orderstatus");
			for (USOrderListStatusEnum statusEnum : USOrderListStatusEnum.values()) {
				if (!Util.isEmpty(orderStatus) && orderStatus == statusEnum.intKey()) {
					record.set("orderstatus", statusEnum.value());
				} else if (orderStatus == JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.intKey()) {
					record.set("orderstatus", JapanPrincipalChangeEnum.CHANGE_PRINCIPAL_OF_ORDER.value());
				}
			}
			
		}
		
		return map;
	}

}
