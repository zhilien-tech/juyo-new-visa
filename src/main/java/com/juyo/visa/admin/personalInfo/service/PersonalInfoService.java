/**
 * PersonalInfoService.java
 * com.juyo.visa.admin.personalInfo.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.personalInfo.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.personalInfo.form.PasswordForm;
import com.juyo.visa.admin.personalInfo.form.PersonalInfoSqlForm;
import com.juyo.visa.admin.personalInfo.form.PersonalInfoUpdateForm;
import com.juyo.visa.common.access.AccessConfig;
import com.juyo.visa.common.access.sign.MD5;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.Pagination;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2017年11月10日 	 
 */
@IocBean
public class PersonalInfoService extends BaseService<TUserEntity> {

	//个人信息列表
	public Object personalList(PersonalInfoSqlForm form, final HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();
		form.setUserid(userid);
		Pagination list = this.listPage(form, new Pager());
		return list.getList().get(0);
	}

	/**
	 * @param userId
	 * @param session
	 * 个人信息编辑回显数据
	 */
	public Object toUpdatePersonal(HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();
		String sqlString = sqlManager.get("personalInfo_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(userid)) {
			cnd.and("u.id", "=", userid);
		}
		cnd.and("u.isDisable", "=", IsYesOrNoEnum.YES.intKey());
		sql.setCondition(cnd);
		Record record = dbDao.fetch(sql);
		return record;
	}

	//修改个人信息
	public Object updatePersonal(PersonalInfoUpdateForm updateForm) {
		long uid = updateForm.getId();
		String qq = updateForm.getQq();
		String email = updateForm.getEmail();
		TUserEntity user = dbDao.fetch(TUserEntity.class, uid);
		if (!Util.isEmpty(user)) {
			user.setQq(qq);
			user.setEmail(email);
		}
		return this.updateIgnoreNull(user);//更新用户表中的数据;
	}

	//校验用户密码
	public Object checkPassword(String password, HttpSession session) {

		//需校验的密码
		password = MD5.sign(password, AccessConfig.password_secret, AccessConfig.INPUT_CHARSET);//密码加密

		Map<String, Object> map = new HashMap<String, Object>();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		String userid = loginUser.getId() + "";
		TUserEntity user = dbDao.fetch(TUserEntity.class, Long.valueOf(userid));
		String passwordUser = user.getPassword();
		boolean eq = Util.eq(password, passwordUser);
		map.put("valid", eq);
		return map;
	}

	//校验两次输入是否一致
	public Object samePassword(@Param("newPass") final String newPass, @Param("repeatPass") final String repeatPass) {
		boolean isSame = false;
		Map<String, Object> map = new HashMap<String, Object>();
		if ((!Util.isEmpty(newPass)) && (!Util.isEmpty(repeatPass))) {
			boolean eq = Util.eq(newPass, repeatPass);
			if (eq) {
				isSame = true;
			}
		}
		map.put("valid", isSame);
		return map;
	}

	//更新密码
	public Object updatePassword(PasswordForm passForm, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		String repeatPass = passForm.getRepeatPass();
		String password = MD5.sign(repeatPass, AccessConfig.password_secret, AccessConfig.INPUT_CHARSET);//密码加密
		loginUser.setPassword(password);
		int update = nutDao.update(loginUser);
		return update > 0;
	}
}
