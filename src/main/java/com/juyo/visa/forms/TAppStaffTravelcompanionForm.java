package com.juyo.visa.forms;

import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.juyo.visa.entities.TAppStaffTravelcompanionEntity;
import com.uxuexi.core.web.form.DataTablesParamForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffTravelcompanionForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**人员id*/
	private String staffid;
	
	/**是否与其他人一起旅行*/
	private Integer istravelwithother;
	
	/**是否与其他人一起旅行(英文)*/
	private Integer istravelwithotheren;
	
	/**是否作为团队或组织的一部分*/
	private Integer ispart;
	
	/**是否作为团队或组织的一部分(英文)*/
	private Integer isparten;
	
	/**团队名称*/
	private String groupname;
	
	/**团队名称(英文)*/
	private String groupnameen;
	
	/**同伴的姓*/
	private String firstname;
	
	/**同伴的姓(英文)*/
	private String firstnameen;
	
	/**同伴的名*/
	private String lastname;
	
	/**同伴的名(英文)*/
	private String lastnameen;
	
	/**与你的关系*/
	private Integer relationship;
	
	/**与你的关系(英文)*/
	private Integer relationshipen;
	
	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TAppStaffTravelcompanionEntity.class);
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