package com.juyo.visa.forms;

import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.juyo.visa.entities.TOrderRecipientEntity;
import com.uxuexi.core.web.form.DataTablesParamForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TOrderRecipientForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**订单id*/
	private Integer orderId;
	
	/**收件地址id*/
	private Integer receiveAddressId;
	
	/**快递方式*/
	private Integer expressType;
	
	/**快递分享方式*/
	private Integer shareType;
	
	/**收件人*/
	private String receiver;
	
	/**电话*/
	private String telephone;
	
	/**收件地址*/
	private String expressAddress;
	
	/**分享申请人*/
	private String shareMans;
	
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
		String sqlString = EntityUtil.entityCndSql(TOrderRecipientEntity.class);
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