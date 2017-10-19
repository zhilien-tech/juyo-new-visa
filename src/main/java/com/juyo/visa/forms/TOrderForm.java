package com.juyo.visa.forms;

import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.juyo.visa.entities.TOrderEntity;
import com.uxuexi.core.web.form.DataTablesParamForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TOrderForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**订单号*/
	private String orderNum;
	
	/**用户id*/
	private Integer userId;
	
	/**公司id*/
	private Integer comId;
	
	/**订单状态*/
	private Integer status;
	
	/**人数*/
	private Integer number;
	
	/**领区*/
	private Integer cityId;
	
	/**加急类型*/
	private Integer urgentType;
	
	/**加急天数*/
	private Integer urgentDay;
	
	/**行程*/
	private Integer travel;
	
	/**付款方式*/
	private Integer payType;
	
	/**金额*/
	private Double money;
	
	/**出行时间*/
	private Date goTripDate;
	
	/**停留天数*/
	private Integer stayDay;
	
	/**返回时间*/
	private Date backTripDate;
	
	/**送签时间*/
	private Date sendVisaDate;
	
	/**出签时间*/
	private Date outVisaDate;
	
	/**实收备注*/
	private String realReceiveRemark;
	
	/**客户管理id*/
	private Integer customerId;
	
	/**客户来源是否为直客*/
	private Integer isDirectCus;
	
	/**公司全称*/
	private String comName;
	
	/**公司简称*/
	private String comShortName;
	
	/**联系人*/
	private String linkman;
	
	/**手机*/
	private String telephone;
	
	/**邮箱*/
	private String email;
	
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
		String sqlString = EntityUtil.entityCndSql(TOrderEntity.class);
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