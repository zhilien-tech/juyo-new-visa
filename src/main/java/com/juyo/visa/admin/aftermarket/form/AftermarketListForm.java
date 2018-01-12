/**
 * AftermarketListForm.java
 * com.juyo.visa.admin.aftermarket.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.aftermarket.form;

import java.util.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.SQLParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年12月4日 	 
 */
@Data
public class AftermarketListForm implements SQLParamForm {

	//送出签时间
	private String signDateStr;
	//检索框内容
	private String searchstr;
	//订单权限
	private String orderAuthority;
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
	//订单状态
	private Integer status;

	@Override
	public Sql sql(SqlManager sqlManager) {

		String sqlstring = sqlManager.get("get_aftermarket_list_data");
		Sql sql = Sqls.create(sqlstring);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		cnd.and("tr.status", ">=", JPOrderStatusEnum.AFTERMARKET_ORDER.intKey());
		if (!Util.isEmpty(signDateStr)) {
			String[] split = signDateStr.split(" - ");
			Date sendSignDate = DateUtil.string2Date(split[0], DateUtil.FORMAT_YYYY_MM_DD);
			Date outSignDate = DateUtil.string2Date(split[1], DateUtil.FORMAT_YYYY_MM_DD);
			//SqlExpressionGroup exp = new SqlExpressionGroup();
			cnd.and("sendVisaDate", ">=", sendSignDate).and("outVisaDate", "<=", outSignDate);
			//cnd.and(exp);
		}
		if (!Util.isEmpty(searchstr)) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tr.orderNum", "like", "%" + searchstr + "%").or("tc.linkman", "like", "%" + searchstr + "%")
					.or("tc.mobile", "like", "%" + searchstr + "%").or("tc.email", "like", "%" + searchstr + "%")
					.or("taj.applyname", "like", "%" + searchstr + "%");
			cnd.and(exp);

		}
		if (!Util.isEmpty(status)) {
			cnd.and("tr.status", "=", status);
		}
		/*if (userid.equals(adminId)) {
			//公司管理员
			cnd.and("tr.comId", "=", companyid);
		} else {
			//普通的操作员
			cnd.and("tr.userId", "=", userid);
		}*/

		//订单权限
		if (Util.isEmpty(orderAuthority)) {
			orderAuthority = "allOrder";
		}
		if (orderAuthority.equals("allOrder")) {
			//全部
			cnd.and("tr.receptionOpid", "IS", null);
		} else {
			//我的
			cnd.and("tr.receptionOpid", "=", userid);
		}

		cnd.and("tr.comId", "=", companyid);

		cnd.orderBy("tr.createtime", "desc");
		return cnd;
	}
}
