package com.juyo.visa.forms;

import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.juyo.visa.entities.TAppStaffFormerspouseEntity;
import com.uxuexi.core.web.form.DataTablesParamForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffFormerspouseForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**人员id*/
	private Integer staffid;
	
	/**前妻的数量*/
	private Integer formerspousecount;
	
	/**前妻的姓*/
	private String firstname;
	
	/**前妻的名*/
	private String lastname;
	
	/**前妻的生日*/
	private Date birthday;
	
	/**前妻的国籍*/
	private Integer nationality;
	
	/**前妻的出生城市*/
	private String city;
	
	/**是否知道前妻的出生城市*/
	private Integer isknowcity;
	
	/**前妻的出生国家*/
	private Integer country;
	
	/**结婚日期*/
	private Date marrieddate;
	
	/**离婚日期*/
	private Date divorcedate;
	
	/**离婚原因*/
	private String divorceexplain;
	
	/**离婚国家*/
	private Integer divorce;
	
	/**前妻的数量(英文)*/
	private Integer formerspousecounten;
	
	/**前妻的姓(英文)*/
	private String firstnameen;
	
	/**前妻的名(英文)*/
	private String lastnameen;
	
	/**前妻的生日(英文)*/
	private Date birthdayen;
	
	/**前妻的国籍(英文)*/
	private Integer nationalityen;
	
	/**前妻的出生城市(英文)*/
	private String cityen;
	
	/**是否知道前妻的出生城市(英文)*/
	private Integer isknowcityen;
	
	/**前妻的出生国家(英文)*/
	private Integer countryen;
	
	/**结婚日期(英文)*/
	private Date marrieddateen;
	
	/**离婚日期(英文)*/
	private Date divorcedateen;
	
	/**离婚原因(英文)*/
	private String divorceexplainen;
	
	/**离婚国家(英文)*/
	private Integer divorceen;
	
	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TAppStaffFormerspouseEntity.class);
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