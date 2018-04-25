package com.juyo.visa.admin.baoying.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffMixInfoForm extends DataTablesParamForm {

	/**人员id*/
	private Integer staffid;

	/**订单id*/
	private Integer orderid;

	/**订单号*/
	private String ordernumber;

	/**订单状态*/
	private Integer orderstatus;

	/**面试领区*/
	private Integer cityid;

	/**公司id*/
	private Integer comid;

	/**用户id（登录用户id）*/
	private Integer userid;

	/**姓*/
	private String firstname;

	/**姓(拼音)*/
	private String firstnameen;

	private Integer isfirst;

	/**名*/
	private String lastname;

	/**名(拼音)*/
	private String lastnameen;

	/**手机号*/
	private String telephone;

	/**邮箱*/
	private String email;

	/**面试时间*/
	private Date interviewdate;

	/**姓名*/
	private String name;

	/**检索字段*/
	private String searchStr;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = sqlManager.get("baoying_staff_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//订单状态检索
		if (!Util.isEmpty(orderstatus)) {
			cnd.and("tou.status", "like", "%" + orderstatus + "%");
		}
		//面试领区检索
		if (!Util.isEmpty(cityid)) {
			cnd.and("tou.cityid", "like", "%" + cityid + "%");
		}
		//姓名、手机、邮箱、订单号 检索
		if (!Util.isEmpty(searchStr)) {
			SqlExpressionGroup expg = new SqlExpressionGroup();
			expg.and("CONCAT( tasb.firstname, tasb.lastname )", "LIKE", "%" + searchStr + "%")
					.or("tasb.email", "LIKE", "%" + searchStr + "%")
					.or("tasb.telephone", "LIKE", "%" + searchStr + "%")
					.or("tou.ordernumber", "LIKE", "%" + searchStr + "%");
			cnd.and(expg);
		}
		cnd.and("tou.isdisable", "!=", IsYesOrNoEnum.YES.intKey());
		cnd.and("tasb.comId", "=", comid);
		cnd.orderBy("tou.updateTime", "DESC");
		return cnd;
	}
}
