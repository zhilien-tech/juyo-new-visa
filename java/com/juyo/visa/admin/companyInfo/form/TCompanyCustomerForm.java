package com.juyo.visa.admin.companyInfo.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCompanyCustomerForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**公司名称*/
	private String name;

	/**公司简称*/
	private String shortName;

	/**管理员账号id*/
	private Integer adminId;

	/**用户名*/
	private String adminLoginName;

	/**联系人*/
	private String linkman;

	/**联系人手机号*/
	private String mobile;

	/**邮箱*/
	private String email;

	/**地址*/
	private String address;

	/**公司类型*/
	private Integer comType;

	/**营业执照*/
	private String license;

	/**是否是客户"*/
	private Integer isCustomer;

	/**客户指定番号*/
	private String cDesignNum;

	/**经营范围*/
	private String comScopes;

	/**操作人*/
	private Integer opId;

	/**创建时间*/
	private Date createTime;

	/**修改时间*/
	private Date updateTime;

	/**删除标识*/
	private Integer deletestatus;

	/**检索字段*/
	private String searchStr;

	//页码
	private Integer pageNumber = 1;
	//每页多少条
	private Integer pageSize = 10;
	//总页数
	private Integer pageTotal;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		/*String sqlString = EntityUtil.entityCndSql(TCompanyEntity.class);*/
		String sqlString = sqlManager.get("companyInfo_list_company");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		cnd.and("adminId", "=", adminId);
		cnd.and("c.comType", "=", "1"); //表示：送签社
		cnd.and("c.isCustomer", "=", "1"); //表示：客户信息
		cnd.orderBy("c.updateTime", "DESC");
		return cnd;
	}
}