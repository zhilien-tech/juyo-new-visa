/**
 * DijieOrderListForm.java
 * com.juyo.visa.admin.dijie.form
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.dijie.form;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
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
	//送签社
	private String songqianshe;
	//员工
	private String employee;
	//送签时间
	private String sendSignDate;
	//下单时间
	private String orderDate;
	//签证类型
	private Integer visatype;

	private String sendenddate;
	private String sendstartdate;
	private String orderstartdate;
	private String orderenddate;

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
			exp.and("tr.orderNum", "like", "%" + searchStr + "%")
					.or("tc.linkman", "like", "%" + searchStr + "%")
					.or("tc.mobile", "like", "%" + searchStr + "%")
					.or("tc.email", "like", "%" + searchStr + "%")
					//.or("taj.applyname", "like", "%" + searchStr + "%")
					.or("toj.acceptDesign", "like", "%" + searchStr + "%")
					//.or("taj.passport", "like", "%" + searchStr + "%")
					.or("(SELECT GROUP_CONCAT(CONCAT(ta.firstName,ta.lastName) SEPARATOR 'төл') applyname FROM t_applicant ta INNER JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id LEFT JOIN t_order_jp toj ON taoj.orderId = toj.id LEFT JOIN t_order tor ON toj.orderId = tor.id WHERE tor.id = tr.id GROUP BY toj.orderId)",
							"like", "%" + searchStr + "%")
					.or("(SELECT tap.passport FROM t_applicant ta INNER JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id LEFT JOIN t_order_jp toj ON taoj.orderId = toj.id LEFT JOIN t_order tor ON toj.orderId = tor.id WHERE tor.id = tr.id GROUP BY toj.orderId)",
							"like", "%" + searchStr + "%");
			cnd.and(exp);
		}
		if (!Util.isEmpty(status)) {
			if (Util.eq(status, JPOrderStatusEnum.DISABLED.intKey())) {
				cnd.and("tr.isDisabled", "=", IsYesOrNoEnum.YES.intKey());
			} else {

				if (Util.eq(22, status)) {
					SqlExpressionGroup e1 = Cnd.exps("tr.status", "=", status).or("tr.status", "=", 35)
							.and("tr.isDisabled", "=", IsYesOrNoEnum.NO.intKey());
					cnd.and(e1);
				} else if (Util.eq(19, status)) {
					SqlExpressionGroup e1 = Cnd.exps("tr.status", "=", status).or("tr.status", "=", 34)
							.and("tr.isDisabled", "=", IsYesOrNoEnum.NO.intKey());
					cnd.and(e1);
				} else {

					SqlExpressionGroup e1 = Cnd.exps("tr.status", "=", status).and("tr.isDisabled", "=",
							IsYesOrNoEnum.NO.intKey());
					cnd.and(e1);
				}
			}
		}
		if (!Util.isEmpty(sendstartdate) && !Util.isEmpty(sendenddate)) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tr.sendVisaDate", "between", new Object[] { sendstartdate, sendenddate });
			cnd.and(exp);
		}
		if (!Util.isEmpty(orderstartdate) && !Util.isEmpty(orderenddate)) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			orderstartdate = orderstartdate.replace("Z", " UTC");
			exp.and("tr.createTime", "between", new Object[] { orderstartdate, orderenddate });
			cnd.and(exp);
		}
		if (!Util.isEmpty(songqianshe)) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tr.comId", "=", songqianshe);
			cnd.and(exp);
		}
		if (!Util.isEmpty(visatype)) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("toj.visatype", "=", visatype);
			cnd.and(exp);
		}
		/*cnd.orderBy("tr.isDisabled", "ASC");
		cnd.orderBy("tr.updatetime", "desc");*/
		//发招保时间
		/*if (!Util.isEmpty(zhaobaotime)) {
			//cnd.and("tr.sendVisaDate", ">=", sendSignDate);
			String[] split = zhaobaotime.split(" - ");
			Date sendSignDate = DateUtil.string2Date(split[0], DateUtil.FORMAT_YYYY_MM_DD);
			Date outSignDate = DateUtil.string2Date(split[1], DateUtil.FORMAT_YYYY_MM_DD);
			//SqlExpressionGroup exp = new SqlExpressionGroup();
			cnd.and("toj.zhaobaotime", ">=", sendSignDate).and("toj.zhaobaotime", "<=", outSignDate);
		}*/
		cnd.orderBy("toj.zhaobaotime", "desc");
		return cnd;
	}
}
