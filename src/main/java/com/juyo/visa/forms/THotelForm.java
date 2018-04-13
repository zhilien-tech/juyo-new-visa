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
public class THotelForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键id*/
	private Integer id;

	/**酒店名称(中文)*/
	private String name;

	/**酒店名称(原文)*/
	private String namejp;

	/**酒店地址(中文)*/
	private String address;

	/**酒店地址(原文)*/
	private String addressjp;

	/**电话*/
	private String mobile;

	/**所属城市id*/
	private Integer cityId;

	/**所属城市*/
	private String cityName;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

	/**检索字段*/
	private String hotelSearch;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = sqlManager.get("platformHotel_list");
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
					.or("h.mobile", "LIKE", "%" + hotelSearch + "%").or("c.city", "LIKE", "%" + hotelSearch + "%");
			cnd.and(expg);
		}
		cnd.orderBy("updateTime", "DESC");
		cnd.orderBy("createTime", "DESC");
		return cnd;
	}
}