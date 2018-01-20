package com.juyo.visa.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.juyo.visa.entities.TApplicantEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TApplicantForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**主申请人id*/
	private Integer mainId;

	/**申请人状态*/
	private Integer status;

	/**用户id（登录用户id）*/
	private Integer userId;

	/**姓*/
	private String firstName;

	/**姓(拼音)*/
	private String firstNameEn;

	/**名*/
	private String lastName;

	/**名(拼音)*/
	private String lastNameEn;

	private String baseRemark;

	private Integer tourist;

	private String otherFirstName;

	private String otherFirstNameEn;

	private String otherLastName;

	private Integer isPrompted;

	private Integer addApply;

	private String otherLastNameEn;

	private String emergencyLinkman;

	private String emergencyTelephone;

	private Integer hasOtherNationality;

	private Integer hasOtherName;

	private Integer addressIsSameWithCard;

	private String cardProvince;

	private String cardCity;

	private String marryUrl;

	private Integer marryStatus;

	private String nationality;

	private Integer isSameInfo;

	/**手机号*/
	private String telephone;

	/**邮箱*/
	private String email;

	/**性别*/
	private String sex;

	/**民族*/
	private String nation;

	/**出生日期*/
	private Date birthday;

	/**住址*/
	private String address;

	/**身份证号*/
	private String cardId;

	/**身份证正面*/
	private String cardFront;

	/**身份证反面*/
	private String cardBack;

	/**签发机关*/
	private String issueOrganization;

	/**有效期始*/
	private Date validStartDate;

	/**有效期至*/
	private Date validEndDate;

	/**现居住地址省份*/
	private String province;

	/**现居住地址城市*/
	private String city;

	/**详细地址*/
	private String detailedAddress;

	/**操作人*/
	private Integer opId;

	/**更新时间*/
	private Date updateTime;

	/**创建时间*/
	private Date createTime;

	private Integer orderid;

	private Integer isTrailOrder;

	private Integer userType;

	/**订单操作枚举*/
	private Integer orderProcessType;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TApplicantEntity.class);
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