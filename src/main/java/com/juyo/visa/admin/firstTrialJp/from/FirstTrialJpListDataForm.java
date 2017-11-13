/**
 * FirstTrialJpListDataForm.java
 * com.juyo.visa.admin.firstTrialJp.from
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.firstTrialJp.from;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.SQLParamForm;

/**
 * 初审列表form
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年11月9日 	 
 */
@Data
public class FirstTrialJpListDataForm implements SQLParamForm {

	//状态
	private Integer status;
	//出行时间
	//private Date goTripDate;
	//返回时间
	//private Date backTripDate;
	//检索框
	private String searchStr;
	//页码
	private Integer pageNumber = 1;
	//每页多少条
	private Integer pageSize = 10;
	//公司id
	private Integer companyid;
	//用户id
	private Integer userid;
	//公司管理员id
	private Integer adminId;

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("firstTrialJp_list_data");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(searchStr)) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tr.orderNum", "like", "%" + searchStr + "%").or("taj.passportNum", "like", "%" + searchStr + "%")
					.or("tc.mobile", "like", "%" + searchStr + "%")
					.or("taj.applicantName", "like", "%" + searchStr + "%");
			cnd.and(exp);
		}
		/*if (!Util.isEmpty(goTripDate)) {
			cnd.and("tr.goTripDate", ">=", goTripDate);
		}
		if (!Util.isEmpty(backTripDate)) {
			cnd.and("tr.goTripDate", "<=", backTripDate);
		}*/
		if (userid.equals(adminId)) {
			//公司管理员
		} else {
			//普通的操作员
		}
		return cnd;
	}

}
