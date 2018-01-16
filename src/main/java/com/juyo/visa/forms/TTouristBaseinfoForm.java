package com.juyo.visa.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.juyo.visa.entities.TTouristBaseinfoEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TTouristBaseinfoForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	//常用联系人传过来的申请人ID
	private Integer applyId;

	private Integer contact;

	private Integer applicantId;

	private Integer orderid;

	private Integer updateIsPrompted;

	private Integer saveIsPrompted;

	private Integer updateIsOrNot;

	private Integer saveIsOrNot;

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

	/**曾用姓*/
	private String otherFirstName;

	/**曾用姓(拼音)*/
	private String otherFirstNameEn;

	/**曾用名*/
	private String otherLastName;

	/**曾用名(拼音)*/
	private String otherLastNameEn;

	/**紧急联系人姓名*/
	private String emergencyLinkman;

	/**紧急联系人手机*/
	private String emergencyTelephone;

	/**'是否另有国籍*/
	private Integer hasOtherNationality;

	private String cardProvince;

	private String cardCity;

	/**是否有曾用名*/
	private Integer hasOtherName;

	/**结婚证/离婚证地址*/
	private String marryUrl;

	/**结婚状况*/
	private Integer marryStatus;

	/**婚姻状况证件类型*/
	private Integer marryurltype;

	/**国籍*/
	private String nationality;

	/**现居住地是否与身份证相同*/
	private Integer addressIsSameWithCard;

	/**基本备注*/
	private String baseRemark;

	/**信息是否一致*/
	private Integer isSameInfo;

	/**操作人*/
	private Integer opId;

	/**更新时间*/
	private Date updateTime;

	/**创建时间*/
	private Date createTime;

	/**基本信息是否填写完毕*/
	private Integer baseIsCompleted;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TTouristBaseinfoEntity.class);
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