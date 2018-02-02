package com.juyo.visa.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.juyo.visa.entities.TCustomerEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCustomerForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键id*/
	private Integer id;

	/**用户id*/
	private Integer userId;

	/**用户类型*/
	private Integer userType;

	private Integer payType;

	private String payTypeStr;

	private Integer isCustomerAdd;

	/**公司id*/
	private Integer compId;

	/**公司名称*/
	private String name;

	/**公司简称*/
	private String shortname;

	/**客户来源*/
	private Integer source;

	/**联系人*/
	private String linkman;

	/**手机*/
	private String mobile;

	/**邮箱*/
	private String email;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

	/**检索条件*/
	private String searchStr;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TCustomerEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(searchStr)) {
			SqlExpressionGroup expg = new SqlExpressionGroup();
			expg.and("name", "LIKE", "%" + searchStr + "%").or("mobile", "LIKE", "%" + searchStr + "%")
					.or("linkman", "LIKE", "%" + searchStr + "%");
			cnd.and(expg);
		}
		if (userType == 5 || userType == 7) {
			cnd.and("compId", "=", compId);
		}
		if (userType == 1) {
			cnd.and("userId", "=", userId);
		}
		//cnd.and("isCustomerAdd", "=", IsYesOrNoEnum.YES.intKey());
		cnd.orderBy("updateTime", "DESC");
		cnd.orderBy("createTime", "DESC");
		return cnd;
	}
}