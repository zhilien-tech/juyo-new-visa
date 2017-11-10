/**
 * PersonalInfoService.java
 * com.juyo.visa.admin.personalInfo.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.personalInfo.service;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;

import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.personalInfo.form.PersonalInfoSqlForm;
import com.juyo.visa.admin.personalInfo.form.PersonalInfoUpdateForm;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.Pagination;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2017年11月10日 	 
 */
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
	public Object toUpdatePersonal(Long userId, HttpSession session) {
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
		long uid = updateForm.getUserid();
		String qq = updateForm.getQq();
		String email = updateForm.getEmail();
		TUserEntity user = dbDao.fetch(TUserEntity.class, uid);
		if (!Util.isEmpty(user)) {
			user.setQq(qq);
			user.setEmail(email);
		}
		return this.updateIgnoreNull(user);//更新用户表中的数据;
	}
}
