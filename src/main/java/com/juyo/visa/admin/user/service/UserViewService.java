package com.juyo.visa.admin.user.service;

import com.uxuexi.core.web.base.service.BaseService;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TUserForm;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

@IocBean
public class UserViewService extends BaseService<TUserEntity> {
	private static final Log log = Logs.get();
	
	public Object listData(TUserForm queryForm) {
		return listPage4Datatables(queryForm);
	}
   
}