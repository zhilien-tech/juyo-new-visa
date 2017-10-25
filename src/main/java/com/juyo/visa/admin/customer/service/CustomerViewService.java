package com.juyo.visa.admin.customer.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.enums.CustomerTypeEnum;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TCustomerEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TCustomerAddForm;
import com.juyo.visa.forms.TCustomerForm;
import com.juyo.visa.forms.TCustomerUpdateForm;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
public class CustomerViewService extends BaseService<TCustomerEntity> {
	private static final Log log = Logs.get();

	public Object listData(TCustomerForm sqlParamForm, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		sqlParamForm.setUserType(loginUser.getUserType());
		return listPage4Datatables(sqlParamForm);
	}

	public Object toAddCustomerPage() {
		Map<String, Object> obj = MapUtil.map();
		obj.put("customerTypeEnum", EnumUtil.enum2(CustomerTypeEnum.class));
		return obj;
	}

	public Object addCustomer(TCustomerAddForm addForm, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		addForm.setCompId(loginCompany.getId());
		addForm.setUserId(loginUser.getId());
		addForm.setCreateTime(new Date());
		this.add(addForm);
		return JsonResult.success("添加成功");
	}

	public Object fetchCustomer(final long id) {
		Map<String, Object> result = MapUtil.map();
		TCustomerEntity customer = dbDao.fetch(TCustomerEntity.class, new Long(id).intValue());
		String sourceType = String.valueOf(customer.getSource().intValue());
		Map<String, String> customerEnum = EnumUtil.enum2(CustomerTypeEnum.class);
		if (customerEnum.containsKey(sourceType)) {
			String sourceTypeName = customerEnum.get(sourceType);
			customerEnum.remove(sourceType);
			result.put("sourceType", sourceTypeName);
		}

		result.put("customer", customer);
		result.put("customerTypeEnum", customerEnum);
		return result;
	}

	public Object updateCustomer(TCustomerUpdateForm updateForm) {
		updateForm.setUpdateTime(new Date());
		TCustomerEntity customer = this.fetch(updateForm.getId());
		updateForm.setCreateTime(customer.getCreateTime());
		this.update(updateForm);
		return JsonResult.success("修改成功");
	}

}