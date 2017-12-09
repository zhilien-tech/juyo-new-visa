/**
 * FirstTrialJpListDataForm.java
 * com.juyo.visa.admin.firstTrialJp.from
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.myData.form;

import java.sql.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.web.form.SQLParamForm;

/**
 * 我的资料form
 * <p>
 *
 * @author   
 * @Date	 2017年12月08日 	 
 */
@Data
public class MyDataListForm implements SQLParamForm {

	//用户id
	private Integer userid;
	//公司管理员id
	private Integer adminId;
	//公司id
	private Integer companyid;

	//页码
	private Integer pageNumber = 1;
	//每页多少条
	private Integer pageSize = 10;

	//申请人id
	private Integer applicatid;
	//申请人姓名
	private String applicantname;
	//订单id
	private Integer orderid;
	//订单号
	private String ordernum;
	//手机号
	private String telephone;
	//护照号
	private String passport;
	//订单状态
	private String orderstatus;
	//送签时间
	private Date sendVisaDate;
	//出签时间
	private Date outVisaDate;

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("myvisa_inProcessVisa_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		cnd.and("ta.userId", "=", userid);
		return cnd;
	}

}
