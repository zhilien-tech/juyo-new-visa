package com.juyo.visa.admin.receiveaddress.service;

import java.util.Date;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.juyo.visa.entities.TReceiveaddressEntity;
import com.juyo.visa.forms.TReceiveaddressAddForm;
import com.juyo.visa.forms.TReceiveaddressForm;
import com.juyo.visa.forms.TReceiveaddressUpdateForm;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
public class ReceiveaddressViewService extends BaseService<TReceiveaddressEntity> {
	private static final Log log = Logs.get();

	public Object listData(TReceiveaddressForm queryForm) {
		return listPage4Datatables(queryForm);
	}

	public Object addReceiveaddress(TReceiveaddressAddForm addForm) {
		addForm.setCreateTime(new Date());
		this.add(addForm);
		return JsonResult.success("添加成功");
	}

	public Object updateReceiveaddress(TReceiveaddressUpdateForm updateForm) {
		updateForm.setUpdateTime(new Date());
		TReceiveaddressEntity receiveaddress = this.fetch(updateForm.getId());
		updateForm.setCreateTime(receiveaddress.getCreateTime());
		this.update(updateForm);
		return JsonResult.success("修改成功");
	}

}