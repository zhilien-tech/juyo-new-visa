package com.juyo.visa.forms;

import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.juyo.visa.entities.TAppStaffConscientiousEntity;
import com.uxuexi.core.web.form.DataTablesParamForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffConscientiousForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**人员id*/
	private Integer staffid;
	
	/**服兵役国家*/
	private Integer militarycountry;
	
	/**服务分支*/
	private String servicebranch;
	
	/**排名*/
	private String rank;
	
	/**军事专业*/
	private String militaryspecialty;
	
	/**服兵役开始时间*/
	private Date servicestartdate;
	
	/**服兵役结束时间*/
	private Date serviceenddate;
	
	/**服兵役国家(英文)*/
	private Integer militarycountryen;
	
	/**服务分支(英文)*/
	private String servicebranchen;
	
	/**排名(英文)*/
	private String ranken;
	
	/**军事专业(英文)*/
	private String militaryspecialtyen;
	
	/**服兵役开始时间(英文)*/
	private Date servicestartdateen;
	
	/**服兵役结束时间(英文)*/
	private Date serviceenddateen;
	
	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TAppStaffConscientiousEntity.class);
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