package com.juyo.visa.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.juyo.visa.entities.TCityEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCityForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键id*/
	private Integer id;

	/**国家*/
	private String country;

	private String code;

	/**省/州/县*/
	private String province;

	/**城市*/
	private String city;

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
		String sqlString = EntityUtil.entityCndSql(TCityEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(searchStr)) {
			SqlExpressionGroup expg = new SqlExpressionGroup();
			expg.and("country", "LIKE", "%" + searchStr + "%").or("province", "LIKE", "%" + searchStr + "%")
					.or("city", "LIKE", "%" + searchStr + "%");
			cnd.and(expg);
		}
		if (!Util.isEmpty(country)) {
			cnd.and("country", "=", country);
		}
		if (!Util.isEmpty(province)) {
			cnd.and("province", "=", province);
		}
		if (!Util.isEmpty(city)) {
			cnd.and("city", "=", city);
		}
		cnd.orderBy("updateTime", "DESC");
		cnd.orderBy("createTime", "DESC");
		return cnd;
	}
}