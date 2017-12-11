package com.juyo.visa.admin.receptionJp.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReceptionJpForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**订单id*/
	private Integer orderId;

	/**收件地址id*/
	private Integer receiveAddressId;

	/**快递方式*/
	private Integer expressType;

	/**收件人*/
	private String receiver;

	/**电话*/
	private String telephone;

	/**收件地址*/
	private String expressAddress;

	/**操作人*/
	private Integer opId;

	/**创建时间*/
	private Date createTime;

	private Integer status;

	/**更新时间*/
	private Date updateTime;

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
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = sqlManager.get("reception_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(status)) {
			cnd.and("tr.status", "=", status);
		}
		if (!Util.isEmpty(searchStr)) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tr.orderNum", "like", "%" + searchStr + "%").or("taj.mail", "like", "%" + searchStr + "%")
					.or("taj.phone", "like", "%" + searchStr + "%")
					.or("taj.applicantName", "like", "%" + searchStr + "%");
			cnd.and(exp);
		}
		/*if (!Util.isEmpty(goTripDate)) {
			cnd.and("tr.goTripDate", ">=", goTripDate);
		}
		if (!Util.isEmpty(backTripDate)) {
			cnd.and("tr.goTripDate", "<=", backTripDate);
		}*/
		cnd.and("tr.status", ">=", JPOrderStatusEnum.SEND_ADDRESS.intKey());
		//cnd.and("tr.status", "<=", JPOrderStatusEnum.TRANSFER_VISA.intKey());
		if (userid.equals(adminId)) {
			//公司管理员
		} else {
			//普通的操作员
		}
		cnd.and("tr.comId", "=", companyid);
		cnd.orderBy("tr.updatetime", "DESC");
		cnd.orderBy("tr.createtime", "DESC");
		return cnd;
	}
}