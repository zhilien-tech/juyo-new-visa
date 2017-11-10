package com.juyo.visa.admin.personalInfo.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * 个人信息 SqlForm
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年11月10日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PersonalInfoSqlForm extends DataTablesParamForm {

	private static final long serialVersionUID = 1L;

	//用户id
	private Integer userid;
	//公司id
	private Integer comId;
	//联系QQ
	private String qq;
	//电子邮箱
	private String email;
	//用户姓名
	private String username;
	//用户名/手机号码
	private String mobile;
	//密码
	private String password;
	//用户类型
	private Integer userType;
	//所属部门
	private String department;
	//用户职位
	private String job;
	//用户是否禁用
	private Integer disableUserStatus;
	//状态
	private Integer status;

	@Override
	public Sql sql(SqlManager sqlManager) {

		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = sqlManager.get("personalInfo_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(userid)) {
			cnd.and("u.id", "=", userid);
		}
		cnd.and("u.isDisable", "=", IsYesOrNoEnum.YES.intKey());

		return cnd;
	}

}
