package com.juyo.visa.forms;

import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.juyo.visa.entities.TAppStaffBeforeeducationEntity;
import com.uxuexi.core.web.form.DataTablesParamForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffBeforeeducationForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**人员id*/
	private Integer staffid;
	
	/**机构名称*/
	private String institution;
	
	/**机构地址*/
	private String institutionaddress;
	
	/**机构地址次选*/
	private String secinstitutionaddress;
	
	/**机构所在市*/
	private String institutioncity;
	
	/**机构所在省*/
	private String institutionprovince;
	
	/**机构所在省是否适用*/
	private Integer isinstitutionprovinceapply;
	
	/**机构邮政编码*/
	private String institutionzipcode;
	
	/**机构邮政编码是否适用*/
	private Integer isinstitutionzipcodeapply;
	
	/**机构所在国家*/
	private Integer institutioncountry;
	
	/**学科*/
	private String course;
	
	/**课程开始时间*/
	private Date coursestartdate;
	
	/**课程结束时间*/
	private Date courseenddate;
	
	/**机构名称(英文)*/
	private String institutionen;
	
	/**机构地址(英文)*/
	private String institutionaddressen;
	
	/**机构地址次选(英文)*/
	private String secinstitutionaddressen;
	
	/**机构所在市(英文)*/
	private String institutioncityen;
	
	/**机构所在省(英文)*/
	private String institutionprovinceen;
	
	/**机构所在省是否适用(英文)*/
	private Integer isinstitutionprovinceapplyen;
	
	/**机构邮政编码(英文)*/
	private String institutionzipcodeen;
	
	/**机构邮政编码是否适用(英文)*/
	private Integer isinstitutionzipcodeapplyen;
	
	/**机构所在国家(英文)*/
	private Integer institutioncountryen;
	
	/**学科(英文)*/
	private String courseen;
	
	/**课程开始时间(英文)*/
	private Date coursestartdateen;
	
	/**课程结束时间(英文)*/
	private Date courseenddateen;
	
	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TAppStaffBeforeeducationEntity.class);
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