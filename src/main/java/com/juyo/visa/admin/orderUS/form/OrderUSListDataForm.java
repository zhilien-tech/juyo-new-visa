/**
 * FirstTrialJpListDataForm.java
 * com.juyo.visa.admin.firstTrialJp.from
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.orderUS.form;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.UserLoginEnum;
import com.juyo.visa.common.enums.orderUS.USOrderListStatusEnum;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.SQLParamForm;

/**
 * 美国订单列表form
 * <p>
 *
 * @author   闫腾
 * @Date	 2018年4月2日 	 
 */
@Data
public class OrderUSListDataForm implements SQLParamForm {

	//状态
	private Integer status;
	//领区
	private Integer cityid;
	//是否付款
	private Integer ispayed;
	//检索框
	private String searchStr;
	//订单权限
	private String orderAuthority;

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
	//用户类型
	private Integer userType;

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("orderUS_listData");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//订单状态检索
		if (!Util.isEmpty(status)) {
			if (Util.eq(status, USOrderListStatusEnum.DISABLED.intKey())) {
				cnd.and("tou.isdisable", "=", IsYesOrNoEnum.YES.intKey());
			} else {
				SqlExpressionGroup e1 = Cnd.exps("tou.status", "=", status).and("tou.isdisable", "=",
						IsYesOrNoEnum.NO.intKey());
				cnd.and(e1);
			}
		}
		/*if (!Util.isEmpty(status)) {
			cnd.and("tou.status", "like", "%" + status + "%");
		}*/
		//领区检索
		if (!Util.isEmpty(cityid)) {
			cnd.and("tou.cityid", "like", "%" + cityid + "%");
		}
		//是否付款检索
		if (!Util.isEmpty(ispayed)) {
			cnd.and("tou.ispayed", "like", "%" + ispayed + "%");
		}
		if (!Util.isEmpty(searchStr)) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tou.ordernumber", "like", "%" + searchStr + "%").or("taj.aacode", "like", "%" + searchStr + "%")
					.or("taj.passport", "like", "%" + searchStr + "%")
					.or("taj.applicantName", "like", "%" + searchStr + "%")
					.or("taj.telephone", "like", "%" + searchStr + "%").or("taj.email", "like", "%" + searchStr + "%");
			cnd.and(exp);
		}
		//订单权限
		if (Util.isEmpty(orderAuthority)) {
			orderAuthority = "allOrder";
		}
		if (orderAuthority.equals("allOrder")) {
			//全部
			if (userid.equals(adminId)) {
				//公司管理员
				cnd.and("tou.comId", "=", companyid);
			} else {
				//普通的操作员
				cnd.and("tu.usertype", "=", UserLoginEnum.BIG_TOURIST_IDENTITY.intKey());
				cnd.and("tou.comId", "=", companyid);
			}
		} else {
			//我的
			cnd.and("tou.opid", "=", userid);
		}

		/*cnd.and("tr.comId", "=", companyid);*/

		cnd.orderBy("tou.updatetime", "DESC");

		return cnd;
	}

}
