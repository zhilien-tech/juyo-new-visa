package com.juyo.visa.admin.customer.service;

import java.util.Date;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.juyo.visa.entities.TCustomerEntity;
import com.juyo.visa.forms.TCustomerAddForm;
import com.juyo.visa.forms.TCustomerForm;
import com.juyo.visa.forms.TCustomerUpdateForm;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
public class CustomerViewService extends BaseService<TCustomerEntity> {
	private static final Log log = Logs.get();

	public Object listData(TCustomerForm queryForm) {
		return listPage4Datatables(queryForm);
	}

	public Object addCustomer(TCustomerAddForm addForm) {
		addForm.setCreateTime(new Date());
		this.add(addForm);
		return JsonResult.success("添加成功");
	}

	public Object updateCustomer(TCustomerUpdateForm updateForm) {
		updateForm.setUpdateTime(new Date());
		TCustomerEntity customer = this.fetch(updateForm.getId());
		updateForm.setCreateTime(customer.getCreateTime());
		this.update(updateForm);
		return JsonResult.success("修改成功");
	}

}