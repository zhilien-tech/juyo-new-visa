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
 * @Date	 2017年12月23日 	 
 */
@Data
public class DijieOrderListForm implements SQLParamForm {

	//状态
	private Integer status;
	//送签时间
	private String zhaobaotime;
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

		String sqlString = sqlManager.get("get_Japan_dijie_list_data");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		cnd.and("toj.groundconnectid", "=", companyid);
		if (!Util.isEmpty(searchStr)) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tr.orderNum", "like", "%" + searchStr + "%").or("tc.linkman", "like", "%" + searchStr + "%")
					.or("tc.mobile", "like", "%" + searchStr + "%").or("tc.email", "like", "%" + searchStr + "%")
					.or("taj.applyname", "like", "%" + searchStr + "%")
					.or("taj.applynameen", "like", "%" + searchStr + "%");
			cnd.and(exp);
		}
		if (!Util.isEmpty(status)) {
			cnd.and("tr.status", "=", status);
		}
		//发招保时间
		if (!Util.isEmpty(zhaobaotime)) {
			//cnd.and("tr.sendVisaDate", ">=", sendSignDate);
			String[] split = zhaobaotime.split(" - ");
			Date sendSignDate = DateUtil.string2Date(split[0], DateUtil.FORMAT_YYYY_MM_DD);
			Date outSignDate = DateUtil.string2Date(split[1], DateUtil.FORMAT_YYYY_MM_DD);
			//SqlExpressionGroup exp = new SqlExpressionGroup();
			cnd.and("toj.zhaobaotime", ">=", sendSignDate).and("toj.zhaobaotime", "<=", outSignDate);
		}
		cnd.orderBy("toj.zhaobaotime", "desc");
		return cnd;
	}
}
