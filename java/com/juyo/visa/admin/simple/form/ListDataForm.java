/**
 * ListDataForm.java
 * com.juyo.visa.admin.simple.form
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.simple.form;

import java.util.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.SQLParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年1月22日 	 
 */
@Data
public class ListDataForm implements SQLParamForm {

	//状态
	private Integer status;
	//送签时间
	private String sendSignDate;
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

		/*SqlExpressionGroup statusexp = new SqlExpressionGroup();
		statusexp.and("tr.status", ">=", JPOrderStatusEnum.SEND_ADDRESS.intKey());
		cnd.and(statusexp);*/
		if (!Util.isEmpty(sendSignDate)) {
			//cnd.and("tr.sendVisaDate", ">=", sendSignDate);
			String[] split = sendSignDate.split(" - ");
			Date sendSignDate = DateUtil.string2Date(split[0], DateUtil.FORMAT_YYYY_MM_DD);
			Date outSignDate = DateUtil.string2Date(split[1], DateUtil.FORMAT_YYYY_MM_DD);
			//SqlExpressionGroup exp = new SqlExpressionGroup();
			cnd.and("sendVisaDate", ">=", sendSignDate).and("outVisaDate", "<=", outSignDate);
		}
		if (!Util.isEmpty(status)) {
			cnd.and("tr.status", "=", status);
		}

		if (userid.equals(adminId)) {
			//公司管理员
			cnd.and("tr.comId", "=", companyid);
		} else {
			//普通的操作员
			cnd.and("tr.userId", "=", userid);
		}
		cnd.orderBy("orderstatus", "desc").orderBy("tr.updatetime", "desc");
		return cnd;
	}

}
