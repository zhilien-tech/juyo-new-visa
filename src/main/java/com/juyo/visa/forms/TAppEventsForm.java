package com.juyo.visa.forms;

import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.juyo.visa.entities.TAppEventsEntity;
import com.uxuexi.core.web.form.DataTablesParamForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppEventsForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**公司id*/
	private Integer comid;
	
	/**活动号*/
	private String eventsNum;
	
	/**活动名称*/
	private String eventsName;
	
	/**活动图片url*/
	private String pictureUrl;
	
	/**截止日期*/
	private Date dueDate;
	
	/**出发日期*/
	private Date departureDate;
	
	/**返回时间*/
	private Date returnDate;
	
	/**签证国家（关联签证流程）*/
	private Integer visaCountry;
	
	/**访问城市*/
	private String visitCity;
	
	/**注意事项*/
	private String attentions;
	
	/**说明*/
	private String descriptions;
	
	/**活动状态*/
	private Integer status;
	
	/**是否发布*/
	private Integer isPublish;
	
	/**是否作废*/
	private Integer isInvalid;
	
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
		String sqlString = EntityUtil.entityCndSql(TAppEventsEntity.class);
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