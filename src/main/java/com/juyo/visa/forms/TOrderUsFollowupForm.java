package com.juyo.visa.forms;

import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.juyo.visa.entities.TOrderUsFollowupEntity;
import com.uxuexi.core.web.form.DataTablesParamForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TOrderUsFollowupForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**订单id*/
	private Integer orderid;
	
	/**用户id*/
	private Integer userid;
	
	/**内容*/
	private String content;
	
	/**解决人*/
	private Integer solveid;
	
	/**解决时间*/
	private Date solvetime;
	
	/**状态*/
	private Integer status;
	
	/**创建时间*/
	private Date createtime;
	
	/**更新时间*/
	private Date updatetime;
	
	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TOrderUsFollowupEntity.class);
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