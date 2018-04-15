package com.juyo.visa.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TUserForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**公司id*/
	private Integer comId;

	/**用户姓名*/
	private String name;

	/**用户名/手机号码*/
	private String mobile;

	/**联系QQ*/
	private String qq;

	/**电子邮箱*/
	private String email;

	/**所属部门id*/
	private Integer departmentId;

	/**用户职位id*/
	private Integer jobId;

	/**用户是否禁用*/
	private Integer isDisable;

	/**密码*/
	private String password;

	/**用户类型*/
	private Integer userType;

	/**上次登陆时间*/
	private Date lastLoginTime;

	/**创建时间*/
	private Date createTime;

	/**修改时间*/
	private Date updateTime;

	/**操作人id*/
	private Integer opId;

	private Integer userId;

	/**检索条件*/
	private String searchStr;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = sqlManager.get("userManager_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(searchStr)) {
			SqlExpressionGroup expg = new SqlExpressionGroup();
			expg.and("u.name", "LIKE", "%" + searchStr + "%").or("u.mobile", "LIKE", "%" + searchStr + "%")
					.or("d.deptName", "LIKE", "%" + searchStr + "%").or("j.jobName", "LIKE", "%" + searchStr + "%");
			cnd.and(expg);
		}

		cnd.and("u.comId", "=", comId);

		cnd.and("u.userType", "!=", 5);
		cnd.and("u.userType", "!=", 7);
		cnd.and("u.userType", "!=", 8);//特指 美国大客户公司
		cnd.orderBy("updateTime", "DESC");
		cnd.orderBy("createTime", "DESC");
		return cnd;
	}
}