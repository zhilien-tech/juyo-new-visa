package com.juyo.visa.admin.authority.service;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.juyo.visa.admin.authority.form.TAuthoritySqlForm;
import com.juyo.visa.entities.TDepartmentEntity;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class AuthorityViewService extends BaseService<TDepartmentEntity> {
	private static final Log log = Logs.get();

	public Object listData(TAuthoritySqlForm sqlForm) {
		/*TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		sqlForm.setComId(companyId);*/
		return listPage4Datatables(sqlForm);
	}

}