package com.juyo.visa.forms;

import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.juyo.visa.entities.TAppStaffBeforeworkEntity;
import com.uxuexi.core.web.form.DataTablesParamForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffBeforeworkForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**人员id*/
	private Integer staffid;
	
	/**雇主姓名*/
	private String employername;
	
	/**雇主街道地址*/
	private String employeraddress;
	
	/**雇主街道地址(次选)*/
	private String employeraddressSec;
	
	/**雇主所在市*/
	private String employercity;
	
	/**雇主所在省*/
	private String employerprovince;
	
	/**雇主所在省是否适用*/
	private Integer isemployerprovinceapply;
	
	/**雇主邮政编码*/
	private String employerzipcode;
	
	/**雇主邮政编码是否适用*/
	private Integer isemployerzipcodeapply;
	
	/**雇主所在国家*/
	private Integer employercountry;
	
	/**雇主电话*/
	private String employertelephone;
	
	/**职称*/
	private String jobtitle;
	
	/**主管的姓*/
	private String supervisorfirstname;
	
	/**是否知道主管的姓*/
	private Integer isknowsupervisorfirstname;
	
	/**主管的名*/
	private String supervisorlastname;
	
	/**是否知道主管的名*/
	private Integer isknowsupervisorlastname;
	
	/**入职时间*/
	private Date employstartdate;
	
	/**离职时间*/
	private Date employenddate;
	
	/**以前的工作职责*/
	private String previousduty;
	
	/**雇主姓名(英文)*/
	private String employernameen;
	
	/**雇主街道地址(英文)*/
	private String employeraddressen;
	
	/**雇主街道地址(次选)(英文)*/
	private String employeraddressSecen;
	
	/**雇主所在市(英文)*/
	private String employercityen;
	
	/**雇主所在省(英文)*/
	private String employerprovinceen;
	
	/**雇主所在省是否适用(英文)*/
	private Integer isemployerprovinceapplyen;
	
	/**雇主邮政编码(英文)*/
	private String employerzipcodeen;
	
	/**雇主邮政编码是否适用(英文)*/
	private Integer isemployerzipcodeapplyen;
	
	/**雇主所在国家(英文)*/
	private Integer employercountryen;
	
	/**雇主电话(英文)*/
	private String employertelephoneen;
	
	/**职称(英文)*/
	private String jobtitleen;
	
	/**主管的姓(英文)*/
	private String supervisorfirstnameen;
	
	/**是否知道主管的姓(英文)*/
	private Integer isknowsupervisorfirstnameen;
	
	/**主管的名(英文)*/
	private String supervisorlastnameen;
	
	/**是否知道主管的名(英文)*/
	private Integer isknowsupervisorlastnameen;
	
	/**入职时间(英文)*/
	private Date employstartdateen;
	
	/**离职时间(英文)*/
	private Date employenddateen;
	
	/**以前的工作职责(英文)*/
	private String previousdutyen;
	
	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TAppStaffBeforeworkEntity.class);
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