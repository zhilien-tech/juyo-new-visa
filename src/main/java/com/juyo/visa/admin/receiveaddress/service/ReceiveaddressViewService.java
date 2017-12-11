package com.juyo.visa.admin.receiveaddress.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TReceiveaddressEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TReceiveaddressAddForm;
import com.juyo.visa.forms.TReceiveaddressForm;
import com.juyo.visa.forms.TReceiveaddressUpdateForm;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
public class ReceiveaddressViewService extends BaseService<TReceiveaddressEntity> {
	private static final Log log = Logs.get();

	public Object listData(TReceiveaddressForm queryForm, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		queryForm.setComId(loginCompany.getId());
		queryForm.setUserId(loginUser.getId());
		queryForm.setUserType(loginUser.getUserType());
		return listPage4Datatables(queryForm);
	}

	/**
	 * 
	 * TODO 添加
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param addForm
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addReceiveaddress(TReceiveaddressAddForm addForm, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		addForm.setComId(loginCompany.getId());
		addForm.setUserId(loginUser.getId());
		addForm.setOpId(loginUser.getId());
		addForm.setCreateTime(new Date());
		addForm.setUpdateTime(new Date());
		this.add(addForm);
		return JsonResult.success("添加成功");
	}

	public Object fetchAddress(long id) {
		return dbDao.fetch(TReceiveaddressEntity.class, id);
	}

	/**
	 * 
	 * TODO 更新
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param updateForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateReceiveaddress(TReceiveaddressUpdateForm updateForm, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		TReceiveaddressEntity receiveaddress = this.fetch(updateForm.getId());
		updateForm.setOpId(loginUser.getId());
		updateForm.setUserId(loginUser.getId());
		updateForm.setComId(loginCompany.getId());
		updateForm.setCreateTime(receiveaddress.getCreateTime());
		updateForm.setUpdateTime(new Date());
		this.update(updateForm);
		return JsonResult.success("修改成功");
	}

	public Object checkMobile(String mobile, String adminId) {
		Map<String, Object> result = new HashMap<String, Object>();
		int count = 0;
		if (Util.isEmpty(adminId)) {
			count = nutDao.count(TReceiveaddressEntity.class, Cnd.where("mobile", "=", mobile));
		} else {
			count = nutDao
					.count(TReceiveaddressEntity.class, Cnd.where("mobile", "=", mobile).and("id", "!=", adminId));
		}
		result.put("valid", count <= 0);
		return result;
	}

}