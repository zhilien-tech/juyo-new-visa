package com.juyo.visa.admin.user.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.testng.collections.Maps;

import com.google.common.collect.Lists;
import com.juyo.visa.admin.user.form.ApplicantUser;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.UserLoginEnum;
import com.juyo.visa.common.enums.UserStatusEnum;
import com.juyo.visa.entities.TDepartmentEntity;
import com.juyo.visa.entities.TFunctionEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.entities.TUserJobEntity;
import com.juyo.visa.forms.TUserAddForm;
import com.juyo.visa.forms.TUserForm;
import com.juyo.visa.forms.TUserUpdateForm;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
public class UserViewService extends BaseService<TUserEntity> {
	private static final Log log = Logs.get();

	public Object listData(TUserForm queryForm) {
		return listPage4Datatables(queryForm);
	}

	public Object toAddUserPage() {
		Map<String, Object> result = Maps.newHashMap();
		List<TDepartmentEntity> departments = dbDao.query(TDepartmentEntity.class, null, null);

		return result;
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
		return user;
	}

	/**
	 * TODO 登录查找用户
	 * <p>
	 * TODO 登录时通过用户名和密码进行查找用户
	 *
	 * @param loginName
	 * @param passwd
	 * @return TODO loginName 用户名 或手机号   passwd 加密后的密码 isTourist 标志是否为游客
	 */
	public TUserEntity findUser(String loginName, String passwd, int isTourist) {
		Cnd cnd = Cnd.NEW();
		SqlExpressionGroup exp = new SqlExpressionGroup();
		exp.and("name", "=", loginName).or("mobile", "=", loginName);
		cnd.and(exp).and("password", "=", passwd);
		//判断是否为游客
		if (isTourist == IsYesOrNoEnum.YES.intKey()) {
			cnd.and("userType", "=", UserLoginEnum.TOURIST_IDENTITY.intKey());
		} else {
			cnd.and("userType", "!=", UserLoginEnum.TOURIST_IDENTITY.intKey());
		}
		TUserEntity user = dbDao.fetch(TUserEntity.class, cnd);

		/*if (Util.isEmpty(user)) {
			user = dbDao.fetch(
					TUserEntity.class,
					Cnd.where("mobile", "=", loginName).and("password", "=", passwd)
							.and("isDisable", "=", UserStatusEnum.VALID.intKey()));
		}*/

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

	/**
	 * TODO 添加申请人
	 * <p>
	 * TODO 添加申请人（游客）到用户表   以便登录
	 *
	 * @param applyuser
	 * @return TODO 添加申请人
	 */
	public Object addApplicantUser(ApplicantUser applyuser) {
		//用户信息
		TUserEntity user = new TUserEntity();
		user.setComId(CommonConstants.COMPANY_TOURIST_ID);
		user.setName(applyuser.getUsername());
		user.setPassword(applyuser.getPassword());
		user.setDepartmentId(CommonConstants.DEPARTMENT_TOURIST_ID);
		user.setIsDisable(IsYesOrNoEnum.YES.intKey());
		user.setJobId(CommonConstants.JOB_TOURIST_ID);
		user.setOpId(applyuser.getOpid());
		user.setUserType(UserLoginEnum.TOURIST_IDENTITY.intKey());
		user.setCreateTime(new Date());
		//添加用户
		TUserEntity insertuser = dbDao.insert(user);
		//用户就职信息
		TUserJobEntity userjob = new TUserJobEntity();
		userjob.setEmpId(insertuser.getId());
		userjob.setComJobId(CommonConstants.COMPANY_JOB_ID);
		userjob.setStatus(IsYesOrNoEnum.YES.intKey());
		dbDao.insert(userjob);
		return null;
	}
}
