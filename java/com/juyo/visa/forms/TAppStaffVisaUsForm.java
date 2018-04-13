package com.juyo.visa.forms;

import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.juyo.visa.entities.TAppStaffVisaUsEntity;
import com.uxuexi.core.web.form.DataTablesParamForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffVisaUsForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**申请人id*/
	private Integer staffid;
	
	/**签发编号*/
	private String visanum;
	
	/**签发地*/
	private String visaaddress;
	
	/**签证类型*/
	private Integer visatype;
	
	/**年限(年)*/
	private Integer visayears;
	
	/**真实资料*/
	private String realinfo;
	
	/**签发时间*/
	private Date visadate;
	
	/**有效期至*/
	private Date validdate;
	
	/**停留时间(天)*/
	private Integer staydays;
	
	/**操作人*/
	private Integer opid;
	
	/**创建时间*/
	private Date createtime;
	
	/**更新时间*/
	private Date updatetime;
	
	/**签证国家*/
	private String visacountry;
	
	/**上传签证图片URL*/
	private String picurl;
	
	/**签证录入时间*/
	private Date visaentrytime;
	
	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TAppStaffVisaUsEntity.class);
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