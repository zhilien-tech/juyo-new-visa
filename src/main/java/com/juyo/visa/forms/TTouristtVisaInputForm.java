package com.juyo.visa.forms;

import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.juyo.visa.entities.TTouristtVisaInputEntity;
import com.uxuexi.core.web.form.DataTablesParamForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TTouristtVisaInputForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**申请人id*/
	private Integer applicantId;
	
	/**用户id*/
	private Integer userId;
	
	/**签发编号*/
	private String visaNum;
	
	/**签发地*/
	private String visaAddress;
	
	/**签证类型*/
	private Integer visaType;
	
	/**年限(年)*/
	private Integer visaYears;
	
	/**真实资料*/
	private String realInfo;
	
	/**签发时间*/
	private Date visaDate;
	
	/**有效期至*/
	private Date validDate;
	
	/**停留时间(天)*/
	private Integer stayDays;
	
	/**操作人*/
	private Integer opId;
	
	/**创建时间*/
	private Date createTime;
	
	/**更新时间*/
	private Date updateTime;
	
	/**签证国家*/
	private String visaCountry;
	
	/**上传签证图片URL*/
	private String picUrl;
	
	/**签证录入时间*/
	private Date visaEntryTime;
	
	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TTouristtVisaInputEntity.class);
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