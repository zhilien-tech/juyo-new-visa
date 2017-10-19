package com.juyo.visa.forms;

import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.juyo.visa.entities.TApplicantPassportEntity;
import com.uxuexi.core.web.form.DataTablesParamForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TApplicantPassportForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**申请人id*/
	private Integer applicantId;
	
	/**姓*/
	private String firstName;
	
	/**姓(拼音)*/
	private String firstNameEn;
	
	/**名*/
	private String lastName;
	
	/**名(拼音)*/
	private String lastNameEn;
	
	/**类型*/
	private String type;
	
	/**护照号*/
	private String passport;
	
	/**性别*/
	private String sex;
	
	/**性别(拼音)*/
	private String sexEn;
	
	/**出生地点*/
	private String birthAddress;
	
	/**出生地点(拼音)*/
	private String birthAddressEn;
	
	/**出生日期*/
	private Date birthday;
	
	/**签发地点*/
	private String issuedPlace;
	
	/**签发地点(拼音)*/
	private String issuedPlaceEn;
	
	/**签发日期*/
	private Date issuedDate;
	
	/**有效期始*/
	private Date validStartDate;
	
	/**有效类型*/
	private Integer validType;
	
	/**有效期至*/
	private Date validEndDate;
	
	/**签发机关*/
	private String issuedOrganization;
	
	/**签发机关(拼音)*/
	private String issuedOrganizationEn;
	
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
		String sqlString = EntityUtil.entityCndSql(TApplicantPassportEntity.class);
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