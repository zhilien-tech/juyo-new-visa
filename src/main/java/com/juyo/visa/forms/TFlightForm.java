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
public class TFlightForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键id*/
	private Integer id;

	/**航班号*/
	private String flightnum;

	/**航空公司*/
	private String airlinecomp;

	/**起飞机场*/
	private String takeOffName;

	/**起飞机场三字代码*/
	private String takeOffCode;

	/**降落机场*/
	private String landingName;

	/**降落机场三字代码*/
	private String landingCode;

	/**起飞城市id*/
	private Integer takeOffCityId;

	/**降落城市id*/
	private Integer landingCityId;

	/**起飞时间*/
	private String takeOffTime;

	/**降落时间*/
	private String landingTime;

	private String flytime;

	private Integer relationflight;

	/**起飞航站楼*/
	private String takeOffTerminal;

	/**降落航站楼*/
	private String landingTerminal;

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
		String sqlString = sqlManager.get("platformFlight_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(searchStr)) {
			SqlExpressionGroup expg = new SqlExpressionGroup();
			expg.and("f.flightnum", "LIKE", "%" + searchStr + "%").or("f.airlinecomp", "LIKE", "%" + searchStr + "%")
					.or("f.takeOffName", "LIKE", "%" + searchStr + "%")
					.or("f.landingName", "LIKE", "%" + searchStr + "%");
			cnd.and(expg);
		}
		cnd.orderBy("updateTime", "DESC");
		cnd.orderBy("createTime", "DESC");
		return cnd;
	}
}