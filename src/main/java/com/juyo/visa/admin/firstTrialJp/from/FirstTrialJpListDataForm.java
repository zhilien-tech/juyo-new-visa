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

import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.TrialApplicantStatusEnum;
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
	//检索框
	private String searchStr;
	//页码
	private Integer pageNumber = 1;
	//每页多少条
	private Integer pageSize = 10;
	//总页数
	private Integer pageTotal;
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
		if (!Util.isEmpty(status)) {
			if (status == TrialApplicantStatusEnum.unqualified.intKey()) {
				//申请人不合格
				cnd.and("taj.applicantStatus", "like", "%" + status + "%");
			} else {
				//订单+申请人状态cnd.and("CONCAT( CAST( tr.STATUS AS CHAR ), 'төл', taj.applicantStatus )", "like", "%" + status + "%");
				cnd.and("tr.STATUS", "like", "%" + status + "%");
			}
		}
		if (!Util.isEmpty(searchStr)) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tr.orderNum", "like", "%" + searchStr + "%").or("taj.passportNum", "like", "%" + searchStr + "%")
					.or("taj.phone", "like", "%" + searchStr + "%")
					.or("taj.applicantName", "like", "%" + searchStr + "%");
			cnd.and(exp);
		}
		/*if (!Util.isEmpty(goTripDate)) {
			cnd.and("tr.goTripDate", ">=", goTripDate);
		}
		if (!Util.isEmpty(backTripDate)) {
			cnd.and("tr.goTripDate", "<=", backTripDate);
		}*/
		//初审 看到的订单为漏斗形式， 初审状态以后的订单都可以看到
		cnd.and("tr.status", ">=", JPOrderStatusEnum.FIRSTTRIAL_ORDER.intKey());
		//cnd.and("tr.status", "<=", JPOrderStatusEnum.SEND_ADDRESS.intKey());
		if (userid.equals(adminId)) {
			//公司管理员
		} else {
			//普通的操作员
		}
		cnd.and("tr.comId", "=", companyid);
		cnd.orderBy("tr.updatetime", "DESC");

		return cnd;
	}

}
