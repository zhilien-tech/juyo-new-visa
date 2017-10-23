package com.juyo.visa.admin.user.service;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Lists;
import com.juyo.visa.common.enums.UserStatusEnum;
import com.juyo.visa.entities.TFunctionEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TUserForm;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class UserViewService extends BaseService<TUserEntity> {
	private static final Log log = Logs.get();

	public Object listData(TUserForm queryForm) {
		return listPage4Datatables(queryForm);
	}

	/**
	 * TODO 登录查找用户
	 * <p>
	 * TODO 登录时通过用户名和密码进行查找用户
	 *
	 * @param loginName
	 * @param passwd
	 * @return TODO loginName 用户名 或手机号   passwd 加密后的密码
	 */
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

	/**
	 * TODO 查找用户功能
	 * <p>
	 * TODO 通过用户id 查找用户拥有的功能
	 *
	 * @param id
	 * @return TODO id 用户表id
	 */
	public List<TFunctionEntity> getUserFunctions(final int id) {
		List<TFunctionEntity> functions = Lists.newArrayList();

		String sqlStr = sqlManager.get("select_function_by_userid");
		Sql sql = Sqls.create(sqlStr);
		sql.setParam("userid", id);
		functions = DbSqlUtil.query(dbDao, TFunctionEntity.class, sql, null);
		return functions;
	}
}