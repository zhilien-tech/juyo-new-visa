package com.juyo.visa.forms;

import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.juyo.visa.entities.TCompanyEntity;
import com.uxuexi.core.web.form.DataTablesParamForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCompanyForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**公司名称*/
	private String name;
	
	/**公司简称*/
	private String shortName;
	
	/**管理员账号id*/
	private Integer adminId;
	
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
	
	/**操作人*/
	private Integer opId;
	
	/**创建时间*/
	private Date createTime;
	
	/**修改时间*/
	private Date updateTime;
	
	/**删除标识*/
	private Integer deletestatus;
	
	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TCompanyEntity.class);
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