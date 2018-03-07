package com.juyo.visa.admin.bigcustomer.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.forms.TAppEventsForm;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class AppEventsViewService extends BaseService<TAppStaffBasicinfoEntity> {
	private static final Log log = Logs.get();

	/**
	 * 
	 * 大客户 首页 列表页
	 *
	 * @param queryForm
	 * @param session
	 * @return
	 */
	public Object listData(TAppEventsForm queryForm, HttpSession session) {

		//当前登录公司Id
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Integer comId = loginCompany.getId();//当前登录公司id

		Map<String, Object> map = listPage4Datatables(queryForm);
		return map;
	}

}