package com.juyo.visa.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.juyo.visa.entities.TApplicantWealthJpEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TApplicantWealthJpForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**申请人id*/
	private Integer applicantId;

	/**财产类型*/
	private String type;

	private String bankflowfree;

	private String vehiclefree;

	private String housePropertyfree;

	private String financialfree;

	private String certificatefree;

	private String depositfree;

	private String taxbillfree;

	private String taxprooffree;

	private String readstudentfree;

	private String graduatefree;

	private Integer sequence;

	/**财产明细*/
	private String details;

	/**操作人*/
	private Integer opId;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TApplicantWealthJpEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		cnd.orderBy("id", "DESC");
		return cnd;
	}
}