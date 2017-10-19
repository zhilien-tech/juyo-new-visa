package com.juyo.visa.forms;

import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.juyo.visa.entities.TOrderTripJpEntity;
import com.uxuexi.core.web.form.DataTablesParamForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TOrderTripJpForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**订单id*/
	private Integer orderId;
	
	/**行程类型*/
	private Integer tripType;
	
	/**出行目的*/
	private String tripPurpose;
	
	/**出发日期(去程)*/
	private Date goDate;
	
	/**出发城市(去程)*/
	private Integer goDepartureCity;
	
	/**抵达城市(去程)*/
	private Integer goArrivedCity;
	
	/**航班号(去程)*/
	private Integer goFlightNum;
	
	/**出发日期(返程)*/
	private Date returnDate;
	
	/**出发城市(返程)*/
	private Integer returnDepartureCity;
	
	/**返回城市(返程)*/
	private Integer returnArrivedCity;
	
	/**航班号(返程)*/
	private Integer returnFlightNum;
	
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
		String sqlString = EntityUtil.entityCndSql(TOrderTripJpEntity.class);
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