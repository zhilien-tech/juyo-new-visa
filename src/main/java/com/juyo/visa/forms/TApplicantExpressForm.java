package com.juyo.visa.forms;

import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.juyo.visa.entities.TApplicantExpressEntity;
import com.uxuexi.core.web.form.DataTablesParamForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TApplicantExpressForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**申请人id*/
	private Integer applicantId;
	
	/**快递号*/
	private String expressNum;
	
	/**快递方式*/
	private Integer expressType;
	
	/**资料来源*/
	private Integer source;
	
	/**快递地址*/
	private String expressAddress;
	
	/**联系人*/
	private String linkman;
	
	/**电话*/
	private String telephone;
	
	/**发票项目内容*/
	private String invoiceContent;
	
	/**发票抬头*/
	private String invoiceHead;
	
	/**税号*/
	private String taxNum;
	
	/**备注*/
	private String remark;
	
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
		String sqlString = EntityUtil.entityCndSql(TApplicantExpressEntity.class);
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