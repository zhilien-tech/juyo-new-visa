package com.juyo.visa.admin.user.service;

import java.util.Date;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TUserAddForm;
import com.juyo.visa.forms.TUserForm;
import com.juyo.visa.forms.TUserUpdateForm;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.juyo.visa.common.enums.UserStatusEnum;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TUserForm;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class UserViewService extends BaseService<TUserEntity> {
	private static final Log log = Logs.get();

	public Object listData(TUserForm queryForm) {
		return listPage4Datatables(queryForm);
	}

	public Object addUser(TUserAddForm addForm) {
		addForm.setOpId(0);
		addForm.setCreateTime(new Date());
		this.add(addForm);
		return JsonResult.success("添加成功");
	}

	public Object updateUser(TUserUpdateForm updateForm) {
		updateForm.setUpdateTime(new Date());
		TUserEntity tUser = this.fetch(updateForm.getId());
		updateForm.setCreateTime(tUser.getCreateTime());
		this.update(updateForm);
		return JsonResult.success("修改成功");
	}

	public TUserEntity findUser(String loginName, String passwd) {
		TUserEntity user = dbDao.fetch(TUserEntity.class, Cnd.where("name", "=", loginName)
				.and("password", "=", passwd).and("isDisable", "=", UserStatusEnum.VALID.intKey()));

		if (Util.isEmpty(user)) {
			user = dbDao.fetch(
					TUserEntity.class,
					Cnd.where("mobile", "=", loginName).and("password", "=", passwd)
							.and("isDisable", "=", UserStatusEnum.VALID.intKey()));
		}
		return user;
	}
}
