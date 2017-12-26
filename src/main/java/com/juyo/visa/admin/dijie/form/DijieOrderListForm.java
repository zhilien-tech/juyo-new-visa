/**
 * DijieOrderListForm.java
 * com.juyo.visa.admin.dijie.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.dijie.form;

import java.util.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.web.form.SQLParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年12月23日 	 
 */
@Data
public class DijieOrderListForm implements SQLParamForm {

	//状态
	private Integer status;
	//送签时间
	private String sendStartDate;
	//出签时间
	private Date sendEndDate;
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

		String sqlString = sqlManager.get("");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		return cnd;
	}
}
