package com.juyo.visa.admin.customer.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.enums.CustomerTypeEnum;
import com.juyo.visa.common.enums.NoZHIKECustomerTypeEnum;
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
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		sqlParamForm.setCompId(loginCompany.getId());
		sqlParamForm.setUserId(loginUser.getId());
		sqlParamForm.setUserType(loginUser.getUserType());
		return listPage4Datatables(sqlParamForm);
	}

	/**
	 * 
	 * TODO 添加页面加载时加载客户来源
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toAddCustomerPage() {
		Map<String, Object> obj = MapUtil.map();
		obj.put("customerTypeEnum", EnumUtil.enum2(NoZHIKECustomerTypeEnum.class));
		return obj;
	}

	/**
	 * 
	 * TODO 添加客户信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param addForm
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addCustomer(TCustomerAddForm addForm, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		addForm.setCompId(loginCompany.getId());
		addForm.setUserId(loginUser.getId());
		addForm.setCreateTime(new Date());
		this.add(addForm);
		return JsonResult.success("添加成功");
	}

	/**
	 * 
	 * TODO 加载更新页面时回显
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param id
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object fetchCustomer(final long id) {
		Map<String, Object> result = MapUtil.map();
		TCustomerEntity customer = dbDao.fetch(TCustomerEntity.class, new Long(id).intValue());
		String sourceType = String.valueOf(customer.getSource().intValue());
		Map<String, String> customerEnum = EnumUtil.enum2(CustomerTypeEnum.class);
		result.put("sourceType", customerEnum);
		result.put("customer", customer);
		result.put("customerTypeEnum", customerEnum);
		return result;
	}

	/**
	 * 
	 * TODO 更新客户信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param updateForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateCustomer(TCustomerUpdateForm updateForm) {
		updateForm.setUpdateTime(new Date());
		TCustomerEntity customer = this.fetch(updateForm.getId());
		updateForm.setCreateTime(customer.getCreateTime());
		this.update(updateForm);
		return JsonResult.success("修改成功");
	}

}