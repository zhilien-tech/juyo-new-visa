package com.juyo.visa.admin.scenic.service;

import java.util.Date;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.juyo.visa.entities.TScenicEntity;
import com.juyo.visa.forms.TScenicAddForm;
import com.juyo.visa.forms.TScenicForm;
import com.juyo.visa.forms.TScenicUpdateForm;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
public class ScenicViewService extends BaseService<TScenicEntity> {
	private static final Log log = Logs.get();

	public Object listData(TScenicForm queryForm) {
		return listPage4Datatables(queryForm);
	}

	public Object addScenic(TScenicAddForm addForm) {
		addForm.setCreateTime(new Date());
		this.add(addForm);
		return JsonResult.success("添加成功");
	}

	public Object updateScenic(TScenicUpdateForm updateForm) {
		updateForm.setUpdateTime(new Date());
		TScenicEntity scenic = this.fetch(updateForm.getId());
		updateForm.setCreateTime(scenic.getCreateTime());
		this.update(updateForm);
		return JsonResult.success("修改成功");
	}

}