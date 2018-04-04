package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.juyo.visa.entities.TAppStaffContactpointEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffContactpointForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**人员id*/
	private Integer staffid;

	/**联系人姓*/
	private String firstname;

	/**联系人名*/
	private String lastname;

	/**是否知道姓名*/
	private Integer isknowname;

	/**组织名称*/
	private String organizationname;

	/**是否知道组织名称*/
	private Integer isknoworganizationname;

	/**与你的关系*/
	private Integer ralationship;

	/**美国街道地址*/
	private String address;

	/**美国街道地址(次选)*/
	private String secaddress;

	/**市*/
	private String city;

	/**州*/
	private Integer state;

	/**邮政编码*/
	private String zipcode;

	/**邮箱*/
	private String email;

	/**电话*/
	private String telephone;

	/**联系人姓(英文)*/
	private String firstnameen;

	/**联系人名(英文)*/
	private String lastnameen;

	/**是否知道姓名(英文)*/
	private Integer isknownameen;

	/**组织名称(英文)*/
	private String organizationnameen;

	/**是否知道组织名称(英文)*/
	private Integer isknoworganizationnameen;

	/**与你的关系(英文)*/
	private Integer ralationshipen;

	/**美国街道地址(英文)*/
	private String addressen;

	/**美国街道地址(次选)(英文)*/
	private String secaddressen;

	/**市(英文)*/
	private String cityen;

	/**州(英文)*/
	private Integer stateen;

	/**邮政编码(英文)*/
	private String zipcodeen;

	/**邮箱(英文)*/
	private String emailen;

	/**电话(英文)*/
	private String telephoneen;
	
	/**是否知道邮箱"*/
	private Integer isknowemail;
	
	/**是否知道邮箱(英文)*/
	private Integer isknowemailen;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TAppStaffContactpointEntity.class);
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