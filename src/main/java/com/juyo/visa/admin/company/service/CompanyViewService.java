package com.juyo.visa.admin.company.service;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.juyo.visa.common.enums.CompanyTypeEnum;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.forms.TCompanyForm;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class CompanyViewService extends BaseService<TCompanyEntity> {
	private static final Log log = Logs.get();

	public Object listData(TCompanyForm queryForm) {
		return listPage4Datatables(queryForm);
	}

	//跳转到添加页面
	public Object toAddCompanyPage() {
		Map<String, Object> obj = MapUtil.map();
		obj.put("companyTypeEnum", EnumUtil.enum2(CompanyTypeEnum.class));
		return obj;
	}

	//跳转到编辑页面
	public Map<String, Object> getCompanyPageInfo(long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		TCompanyEntity companyEntity = this.fetch(id);
		//公司数据
		obj.put("company", companyEntity);
		//用户名
		//obj.put("telephone", dbDao.fetch(TUserEntity.class, companyEntity.getAdminId()).getUserName());
		//公司类型
		obj.put("companyTypeEnum", EnumUtil.enum2(CompanyTypeEnum.class));
		return obj;
	}

}