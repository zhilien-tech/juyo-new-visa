package com.juyo.visa.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TFunctionForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**上级功能id*/
	private Integer parentId;

	/**功能名称*/
	private String funName;

	/**访问地址*/
	private String url;

	/**功能等级，是指在功能树中所处的层级*/
	private Integer level;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

	/**备注*/
	private String remark;

	/**序号*/
	private Integer sort;

	/**菜单栏图标*/
	private String portrait;

	/**功能id*/
	private String funId;

	/**检索字段*/
	private String searchStr;

	/**公司类型*/
	private Integer comType;

	/**经营范围*/
	private Integer bScope;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		//String sqlString = EntityUtil.entityCndSql(TFunctionEntity.class);
		String sqlString = sqlManager.get("function_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(searchStr)) {
			cnd.and("f.funName", "like", "%" + searchStr + "%");
		}
		if (!Util.isEmpty(funId) && !Util.eq("-1", funId)) {
			cnd.and("f.id", "=", funId);
		}
		cnd.orderBy("f.sort", "ASC");
		return cnd;
	}
}