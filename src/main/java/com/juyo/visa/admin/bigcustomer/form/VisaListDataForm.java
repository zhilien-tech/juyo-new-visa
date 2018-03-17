/**
 * VisaListDataForm.java
 * com.juyo.visa.admin.visajp.form
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.bigcustomer.form;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.web.form.SQLParamForm;

/**
 * 美国
 * <p>
 * PC端签证列表页
 *
 *  
 */
@Data
public class VisaListDataForm implements SQLParamForm {

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

		String sqlString = sqlManager.get("bigCustomer_staff_visa_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		cnd.and("tasb.userid", "=", userid);
		cnd.groupBy("tos.id");
		cnd.orderBy("tr.createtime", "desc");
		return cnd;
	}

}
