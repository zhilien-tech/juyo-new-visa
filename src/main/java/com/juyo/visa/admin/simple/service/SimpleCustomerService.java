/**
 * SimpleCustomerService.java
 * com.juyo.visa.admin.simple.service
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.simple.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.MainSalePayTypeEnum;
import com.juyo.visa.common.enums.MainSaleVisaTypeEnum;
import com.juyo.visa.common.enums.NoZHIKECustomerTypeEnum;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TCustomerEntity;
import com.juyo.visa.entities.TCustomerVisainfoEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TCustomerAddForm;
import com.juyo.visa.forms.TCustomerForm;
import com.juyo.visa.forms.TCustomerUpdateForm;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年1月24日 	 
 */
@IocBean
public class SimpleCustomerService extends BaseService<TCustomerEntity> {

	private static final Log log = Logs.get();

	public Object listData(TCustomerForm sqlParamForm, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Integer payType = sqlParamForm.getPayType();

		sqlParamForm.setCompId(loginCompany.getId());
		sqlParamForm.setUserId(loginUser.getId());
		sqlParamForm.setUserType(loginUser.getUserType());
		Map<String, Object> map = listPage4Datatables(sqlParamForm);
		List<Record> object = (List<Record>) map.get("data");
		for (Record record : object) {
			if (!Util.isEmpty(record.get("payType"))) {
				int type = (int) record.get("payType");
				for (MainSalePayTypeEnum payEnum : MainSalePayTypeEnum.values()) {
					if (type == payEnum.intKey()) {
						record.set("payTypeStr", payEnum.value());
					}
				}
			}
		}
		return map;
	}

	/**
	 * 
	 * TODO 添加页面加载时加载客户来源
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toAddCustomerPage(int isCustomerAdd) {
		Map<String, Object> obj = MapUtil.map();
		obj.put("isCustomerAdd", isCustomerAdd);
		obj.put("customerTypeEnum", EnumUtil.enum2(NoZHIKECustomerTypeEnum.class));
		obj.put("payTypeEnum", EnumUtil.enum2(MainSalePayTypeEnum.class));
		obj.put("mainSaleVisaTypeEnum", EnumUtil.enum2(MainSaleVisaTypeEnum.class));
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
		if (Util.eq(addForm.getIsCustomerAdd(), IsYesOrNoEnum.YES.intKey())) {
			addForm.setIsCustomerAdd(1);
		} else {
			addForm.setIsCustomerAdd(0);
		}
		addForm.setCreateTime(new Date());
		addForm.setUpdateTime(new Date());
		TCustomerEntity add = this.add(addForm);
		String visatype = addForm.getVisatype();
		List<TCustomerVisainfoEntity> customervisainfos = JsonUtil.fromJsonAsList(TCustomerVisainfoEntity.class,
				visatype);
		for (TCustomerVisainfoEntity tCustomerVisainfoEntity : customervisainfos) {
			tCustomerVisainfoEntity.setCustomerid(add.getId());
		}
		dbDao.insert(customervisainfos);
		//return JsonResult.success("添加成功");
		return null;
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
		Map<String, String> customerEnum = EnumUtil.enum2(NoZHIKECustomerTypeEnum.class);
		result.put("sourceType", customerEnum);
		result.put("customer", customer);
		result.put("payType", EnumUtil.enum2(MainSalePayTypeEnum.class));
		result.put("customerTypeEnum", customerEnum);
		result.put("mainSaleVisaTypeEnum", EnumUtil.enum2(MainSaleVisaTypeEnum.class));
		List<TCustomerVisainfoEntity> customervisainfo = dbDao.query(TCustomerVisainfoEntity.class,
				Cnd.where("customerid", "=", id), null);
		result.put("customervisainfo", customervisainfo);
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
	public Object updateCustomer(TCustomerUpdateForm updateForm, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		updateForm.setUpdateTime(new Date());
		updateForm.setCompId(loginCompany.getId());
		updateForm.setUserId(loginUser.getId());
		TCustomerEntity customer = this.fetch(updateForm.getId());
		updateForm.setIsCustomerAdd(1);
		updateForm.setCreateTime(customer.getCreateTime());
		this.update(updateForm);
		String visatype = updateForm.getVisatype();
		List<TCustomerVisainfoEntity> after = JsonUtil.fromJsonAsList(TCustomerVisainfoEntity.class, visatype);
		for (TCustomerVisainfoEntity tCustomerVisainfoEntity : after) {
			tCustomerVisainfoEntity.setCustomerid(new Long(updateForm.getId()).intValue());
		}
		List<TCustomerVisainfoEntity> before = dbDao.query(TCustomerVisainfoEntity.class,
				Cnd.where("customerid", "=", updateForm.getId()), null);
		dbDao.updateRelations(before, after);
		return JsonResult.success("修改成功");
	}
}
