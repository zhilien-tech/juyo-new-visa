/**
 * VisaListDataForm.java
 * com.juyo.visa.admin.visajp.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp.form;

import java.util.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.SQLParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年10月30日 	 
 */
@Data
public class VisaListDataForm implements SQLParamForm {

	//状态
	private Integer status;
	//送签时间
	private Date sendSignDate;
	//出签时间
	private Date signOutDate;
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

		String sqlString = sqlManager.get("get_japan_visa_list_data");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(searchStr)) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tr.orderNum", "like", "%" + searchStr + "%").or("tc.linkman", "like", "%" + searchStr + "%")
					.or("tc.mobile", "like", "%" + searchStr + "%").or("tc.email", "like", "%" + searchStr + "%")
					.or("taj.applyname", "like", "%" + searchStr + "%");
			cnd.and(exp);
		}
		cnd.and("tr.comId", "=", companyid);
		cnd.and("tr.status", "=", JPOrderStatusEnum.VISA_ORDER.intKey());
		if (!Util.isEmpty(sendSignDate)) {
			cnd.and("tr.sendVisaDate", ">=", sendSignDate);
		}
		if (!Util.isEmpty(signOutDate)) {
			cnd.and("tr.outVisaDate", "<=", signOutDate);
		}
		if (userid.equals(adminId)) {
			//公司管理员
		} else {
			//普通的操作员
		}
		return cnd;
	}

}
