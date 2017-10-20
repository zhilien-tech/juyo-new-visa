/**
 * THotelSearchForm.java
 * com.juyo.visa.forms
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年10月20日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class THotelSearchForm extends DataTablesParamForm {
	/**名称中文*/
	private String name;
	/**名称原文*/
	private String nameJp;
	/**电话*/
	private String mobile;
	/**所属城市id*/
	private Integer cityId;
	/**检索字段*/
	private String hotelSearch;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = sqlManager.get("platformHotel_search");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(hotelSearch)) {
			SqlExpressionGroup expg = new SqlExpressionGroup();
			expg.and("h.name", "LIKE", "%" + hotelSearch + "%").or("h.nameJp", "LIKE", "%" + hotelSearch + "%")
					.or("h.mobile", "LIKE", "%" + hotelSearch + "%").or("h.cityId", "LIKE", "%" + hotelSearch + "%");
			cnd.and(expg);
		}
		cnd.orderBy("h.createTime", "DESC");
		return cnd;
	}

}
