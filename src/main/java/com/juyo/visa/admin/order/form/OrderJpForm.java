package com.juyo.visa.admin.order.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.uxuexi.core.common.util.Util;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderJpForm extends OrderForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**订单id*/
	private Integer orderId;

	/**签证类型*/
	private Integer visaType;

	/**客户来源*/
	private Integer source;

	/**签证县*/
	private String visaCounty;

	/**过去三年是否访问*/
	private Integer isVisit;

	/**过去三年访问过的县*/
	private String threeCounty;

	/**检索条件*/
	private String searchStr;

	//页码
	private Integer pageNumber = 1;

	//每页多少条
	private Integer pageSize = 10;

	private Date start_time;
	private Date end_time;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = sqlManager.get("orderJp_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(searchStr)) {
			SqlExpressionGroup expg = new SqlExpressionGroup();
			expg.and("o.orderNum", "LIKE", "%" + searchStr + "%").or("ap.passport", "LIKE", "%" + searchStr + "%")
					.or("tc.shortName", "LIKE", "%" + searchStr + "%").or("c.linkman", "LIKE", "%" + searchStr + "%")
					.or("c.email", "LIKE", "%" + searchStr + "%").or("c.mobile", "LIKE", "%" + searchStr + "%")
					.or("aj.applicants", "LIKE", "%" + searchStr + "%");
			cnd.and(expg);
		}
		if (!Util.isEmpty(start_time) && !Util.isEmpty(end_time)) {
			SqlExpressionGroup e1 = Cnd.exps("o.createtime", ">=", start_time).and("vncoj.createtime", "<=", end_time);
			cnd.and(e1);
		} else if (Util.isEmpty(start_time) && !Util.isEmpty(end_time)) {
			SqlExpressionGroup e1 = Cnd.exps("o.createtime", "<=", end_time);
			cnd.and(e1);
		} else if (!Util.isEmpty(start_time) && Util.isEmpty(end_time)) {
			SqlExpressionGroup e1 = Cnd.exps("o.createtime", ">=", start_time);
			cnd.and(e1);
		}
		if (!Util.isEmpty(getStatus())) {
			cnd.and("o.status", "=", getStatus());
		}
		if (!Util.isEmpty(source)) {
			cnd.and("c.source", "=", source);
		}
		if (!Util.isEmpty(visaType)) {
			cnd.and("oj.visaType", "=", visaType);
		}
		cnd.orderBy("o.createtime", "DESC");
		cnd.orderBy("o.updatetime", "DESC");
		return cnd;
	}
}