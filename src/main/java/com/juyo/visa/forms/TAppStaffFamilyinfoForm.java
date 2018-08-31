package com.juyo.visa.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.juyo.visa.entities.TAppStaffFamilyinfoEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffFamilyinfoForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**人员id*/
	private Integer staffid;

	/**父亲的姓*/
	private String fatherfirstname;

	/**是否知道父亲的姓*/
	private Integer isknowfatherfirstname;

	/**父亲的名*/
	private String fatherlastname;

	/**是否知道父亲的名*/
	private Integer isknowfatherlastname;

	/**父亲的出生日期*/
	private Date fatherbirthday;

	/**是否知道父亲的出生日期*/
	private Integer isknowfatherbirthday;

	/**父亲是否在美国*/
	private Integer isfatherinus;

	/**父亲在美国的身份*/
	private Integer fatherstatus;

	/**母亲的姓*/
	private String motherfirstname;

	/**是否知道母亲的姓*/
	private Integer isknowmotherfirstname;

	/**母亲的名*/
	private String motherlastname;

	/**是否知道母亲的名*/
	private Integer isknowmotherlastname;

	/**母亲的出生日期*/
	private Date motherbirthday;

	/**是否知道母亲的出生日期*/
	private Integer isknowmotherbirthday;

	/**母亲是否在美国*/
	private Integer ismotherinus;

	/**母亲在美国的身份*/
	private Integer motherstatus;

	/**在美国除了父母是否还有直系亲属*/
	private Integer hasimmediaterelatives;

	/**是否还有别的亲属*/
	private Integer hasotherrelatives;

	/**配偶的姓*/
	private String spousefirstname;

	/**配偶的名*/
	private String spouselastname;

	/**配偶的生日*/
	private Date spousebirthday;

	/**配偶的国籍*/
	private Integer spousenationality;

	/**配偶的出生城市*/
	private String spousecity;

	/**是否知道配偶的出生城市*/
	private Integer isknowspousecity;

	/**配偶的出生国家*/
	private Integer spousecountry;

	/**配偶的联系地址*/
	private Integer spouseaddress;

	/**街道地址(首选)*/
	private String firstaddress;

	/**街道地址(次选)*/
	private String secondaddress;

	/**市*/
	private String city;

	/**省*/
	private String province;

	/**省是否适用*/
	private Integer isprovinceapply;

	/**邮政编码*/
	private String zipcode;

	/**邮政编码是否适用*/
	private Integer iszipcodeapply;

	/**国家*/
	private Integer country;

	/**父亲的姓(英文)*/
	private String fatherfirstnameen;

	/**是否知道父亲的姓(英文)*/
	private Integer isknowfatherfirstnameen;

	/**父亲的名(英文)*/
	private String fatherlastnameen;

	/**是否知道父亲的名(英文)*/
	private Integer isknowfatherlastnameen;

	/**父亲的出生日期(英文)*/
	private Date fatherbirthdayen;

	/**是否知道父亲的出生日期(英文)*/
	private Integer isknowfatherbirthdayen;

	/**父亲是否在美国(英文)*/
	private Integer isfatherinusen;

	/**父亲在美国的身份(英文)*/
	private Integer fatherstatusen;

	/**母亲的姓(英文)*/
	private String motherfirstnameen;

	/**是否知道母亲的姓(英文)*/
	private Integer isknowmotherfirstnameen;

	/**母亲的名(英文)*/
	private String motherlastnameen;

	/**是否知道母亲的名(英文)*/
	private Integer isknowmotherlastnameen;

	/**母亲的出生日期(英文)*/
	private Date motherbirthdayen;

	/**是否知道母亲的出生日期(英文)*/
	private Integer isknowmotherbirthdayen;

	/**母亲是否在美国(英文)*/
	private Integer ismotherinusen;

	/**母亲在美国的身份(英文)*/
	private Integer motherstatusen;

	/**在美国除了父母是否还有直系亲属(英文)*/
	private Integer hasimmediaterelativesen;

	/**是否还有别的亲属(英文)*/
	private Integer hasotherrelativesen;

	/**配偶的姓(英文)*/
	private String spousefirstnameen;

	/**配偶的名(英文)*/
	private String spouselastnameen;

	/**配偶的生日(英文)*/
	private Date spousebirthdayen;

	/**配偶的国籍(英文)*/
	private Integer spousenationalityen;

	/**配偶的出生城市(英文)*/
	private String spousecityen;

	/**是否知道配偶的出生城市(英文)*/
	private Integer isknowspousecityen;

	/**配偶的出生国家(英文)*/
	private Integer spousecountryen;

	/**配偶的联系地址(英文)*/
	private Integer spouseaddressen;

	/**街道地址(首选)(英文)*/
	private String firstaddressen;

	/**街道地址(次选)(英文)*/
	private String secondaddressen;

	/**市(英文)*/
	private String cityen;

	/**省(英文)*/
	private String provinceen;

	/**省是否适用(英文)*/
	private Integer isprovinceapplyen;

	/**邮政编码(英文)*/
	private String zipcodeen;

	/**邮政编码是否适用(英文)*/
	private Integer iszipcodeapplyen;

	/**国家(英文)*/
	private Integer countryen;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TAppStaffFamilyinfoEntity.class);
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