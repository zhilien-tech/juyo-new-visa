/**
 * FirstTrialJpListDataForm.java
 * com.juyo.visa.admin.firstTrialJp.from
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.myVisa.form;

import java.sql.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.SQLParamForm;

/**
 * 办理中签证form
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年11月23日 	 
 */
@Data
public class MyVisaListDataForm implements SQLParamForm {

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
	//日本订单id
	private Integer orderjpid;
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

	//是否是统一联系人
	private Integer isMainLink;

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("myvisa_inProcessVisa_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		cnd.and("torj.orderjpid", "=", orderjpid);
		if (Util.eq(isMainLink, IsYesOrNoEnum.NO.intKey())) {
			cnd.and("ta.userId", "=", userid);
		}
		return cnd;
	}
}
