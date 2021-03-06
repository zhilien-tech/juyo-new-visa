package com.juyo.visa.forms;

import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.juyo.visa.entities.TOrderUsTravelinfoEntity;
import com.uxuexi.core.web.form.DataTablesParamForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TOrderUsTravelinfoForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**订单id*/
	private Integer orderid;
	
	/**出行目的*/
	private Integer travelpurpose;
	
	/**指定*/
	private Integer specify;
	
	/**赞助团体/组织*/
	private String sponsorshipgroup;
	
	/**联系人姓*/
	private String firstname;
	
	/**联系人名*/
	private String lastname;
	
	/**美国地址*/
	private String address;
	
	/**市*/
	private String city;
	
	/**州*/
	private Integer state;
	
	/**邮政编码*/
	private String zipcode;
	
	/**电话号码*/
	private String telephone;
	
	/**申请编号*/
	private String petitionnumber;
	
	/**是否有具体的旅行计划*/
	private Integer hastripplan;
	
	/**预计出发日期*/
	private Date godate;
	
	/**抵达美国日期*/
	private Date arrivedate;
	
	/**停留天数*/
	private Integer staydays;
	
	/**停留天数单位*/
	private Integer stayunit;
	
	/**离开美国日期*/
	private Date leavedate;
	
	/**出发城市(去程)*/
	private Integer godeparturecity;
	
	/**抵达城市(去程)*/
	private Integer goArrivedCity;
	
	/**航班号(去程)*/
	private String goFlightNum;
	
	/**出发城市(返程)*/
	private Integer returnDepartureCity;
	
	/**返回城市(返程)*/
	private Integer returnArrivedCity;
	
	/**航班号(返程)*/
	private String returnFlightNum;
	
	/**计划去美国的地点(州)*/
	private Integer planstate;
	
	/**计划去美国的地点(市)*/
	private String plancity;
	
	/**计划去美国的地点(地址)*/
	private String planaddress;
	
	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TOrderUsTravelinfoEntity.class);
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