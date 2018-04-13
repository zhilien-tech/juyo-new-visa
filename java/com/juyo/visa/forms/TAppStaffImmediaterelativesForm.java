package com.juyo.visa.forms;

import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.juyo.visa.entities.TAppStaffImmediaterelativesEntity;
import com.uxuexi.core.web.form.DataTablesParamForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffImmediaterelativesForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**人员id*/
	private Integer staffid;
	
	/**亲属的姓*/
	private String relativesfirstname;
	
	/**亲属的名*/
	private String relativeslastname;
	
	/**与你的关系*/
	private Integer relationship;
	
	/**亲属的身份*/
	private Integer relativesstatus;
	
	/**亲属的姓(英文)*/
	private String relativesfirstnameen;
	
	/**亲属的名(英文)*/
	private String relativeslastnameen;
	
	/**与你的关系(英文)*/
	private Integer relationshipen;
	
	/**亲属的身份(英文)*/
	private Integer relativesstatusen;
	
	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TAppStaffImmediaterelativesEntity.class);
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