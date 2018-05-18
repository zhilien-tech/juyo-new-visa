package com.juyo.visa.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.juyo.visa.entities.TCompanyOfCustomerEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCompanyOfCustomerForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**公司id*/
	private Integer comid;
	
	/**送签社公司id*/
	private Integer sendcomid;

	/**公司名称*/
	private String fullname;

	/**公司简称*/
	private String shortname;

	/**指定番号*/
	private String designatedNum;

	/**联系人*/
	private String linkman;

	/**电话*/
	private String mobile;

	/**邮箱*/
	private String email;

	/**地址*/
	private String address;

	/**操作人*/
	private Integer opId;

	/**创建时间*/
	private Date createTime;

	/**修改时间*/
	private Date updateTime;

	//公司信息id
	private Integer comInfoId;

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
		String sqlString = sqlManager.get("companyInfo_list_sendcompany");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		cnd.and("tcoc.comid", "=", comid);
		cnd.orderBy("tc.updateTime", "DESC");
		return cnd;
	}
}