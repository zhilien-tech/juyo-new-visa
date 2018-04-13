package com.juyo.visa.forms;

import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.juyo.visa.entities.TAppStaffPassportEntity;
import com.uxuexi.core.web.form.DataTablesParamForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffPassportForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**人员id*/
	private Integer staffid;
	
	/**姓*/
	private String firstname;
	
	/**姓(拼音)*/
	private String firstnameen;
	
	/**名*/
	private String lastname;
	
	/**名(拼音)*/
	private String lastnameen;
	
	/**类型*/
	private String type;
	
	/**护照号*/
	private String passport;
	
	/**性别*/
	private String sex;
	
	/**性别(拼音)*/
	private String sexen;
	
	/**出生地点*/
	private String birthaddress;
	
	/**出生地点(拼音)*/
	private String birthaddressen;
	
	/**出生日期*/
	private Date birthday;
	
	/**签发地点*/
	private String issuedplace;
	
	/**签发地点(拼音)*/
	private String issuedplaceen;
	
	/**签发日期*/
	private Date issueddate;
	
	/**有效期始*/
	private Date validstartdate;
	
	/**有效类型*/
	private Integer validtype;
	
	/**有效期至*/
	private Date validenddate;
	
	/**签发机关*/
	private String issuedorganization;
	
	/**签发机关(拼音)*/
	private String issuedorganizationen;
	
	/**操作人*/
	private Integer opid;
	
	/**创建时间*/
	private Date createtime;
	
	/**更新时间*/
	private Date updatetime;
	
	/**护照地址*/
	private String passporturl;
	
	/**OCR识别码第一行*/
	private String oCRline1;
	
	/**OCR识别码第二行*/
	private String oCRline2;
	
	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TAppStaffPassportEntity.class);
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