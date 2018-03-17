package com.juyo.visa.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffBasicinfoForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**公司id*/
	private Integer comid;

	/**用户id（登录用户id）*/
	private Integer userid;

	/**姓*/
	private String firstname;

	/**姓(拼音)*/
	private String firstnameen;

	/**名*/
	private String lastname;

	/**名(拼音)*/
	private String lastnameen;

	/**微信Token*/
	private String wechattoken;

	/**状态*/
	private Integer status;

	/**手机号*/
	private String telephone;

	/**手机号(英文)*/
	private String telephoneen;

	/**邮箱*/
	private String email;

	/**邮箱(英文)*/
	private String emailen;

	/**性别*/
	private String sex;

	/**部门*/
	private String department;

	/**职位*/
	private String job;

	/**民族*/
	private String nation;

	/**民族(英文)*/
	private String nationen;

	/**出生日期*/
	private Date birthday;

	/**住址*/
	private String address;

	/**身份证号*/
	private String cardnum;

	/**身份证号*/
	private String cardId;

	/**公民身份证号(英文)*/
	private String cardIden;

	/**身份证正面*/
	private String cardfront;

	/**二寸免冠照*/
	private String twoinchphoto;

	/**身份证反面*/
	private String cardback;

	/**签发机关*/
	private String issueorganization;

	/**有效期始*/
	private Date validstartdate;

	/**有效期至*/
	private Date validenddate;

	/**现居住地址省份*/
	private String province;

	/**现居住地址省份(英文)*/
	private String provinceen;

	/**现居住地址城市*/
	private String city;

	/**现居住地址城市(英文)*/
	private String cityen;

	/**详细地址*/
	private String detailedaddress;

	/**详细地址(英文)*/
	private String detailedaddressen;

	/**身份证省份*/
	private String cardprovince;

	/**身份证省份(英文)*/
	private String cardprovinceen;

	/**身份证城市*/
	private String cardcity;

	/**身份证城市(英文)*/
	private String cardcityen;

	/**电子代码姓*/
	private String telecodefirstname;

	/**电子代码姓(拼音)*/
	private String telecodefirstnameEn;

	/**电子代码名*/
	private String telecodelastname;

	/**电子代码名(拼音)*/
	private String telecodelastnameEn;

	/**曾用姓*/
	private String otherfirstname;

	/**曾用姓(拼音)*/
	private String otherfirstnameen;

	/**曾用名*/
	private String otherlastname;

	/**曾用名(拼音)*/
	private String otherlastnameen;

	/**紧急联系人姓名*/
	private String emergencylinkman;

	/**紧急联系人手机*/
	private String emergencytelephone;

	/**是否另有国籍*/
	private Integer hasothernationality;

	/**是否另有国籍(英文)*/
	private Integer hasothernationalityen;

	/**是否有其他国家的护照*/
	private Integer hasotherpassport;

	/**是否有其他国家的护照(英文)*/
	private Integer hasotherpassporten;

	/**其他国家护照号码*/
	private String otherpassportnumber;

	/**其他国家护照号码(英文)*/
	private String otherpassportnumberen;

	/**是否是其他国家的永久居民*/
	private Integer isothercountrypermanentresident;

	/**是否是其他国家的永久居民(英文)*/
	private Integer isothercountrypermanentresidenten;

	/**是否有代表姓名的电子代码*/
	private Integer hastelecode;

	/**是否有代表姓名的电子代码(英文)*/
	private Integer hastelecodeEn;

	/**是否有曾用名*/
	private Integer hasothername;

	/**是否有曾用名(英文)*/
	private Integer hasothernameen;

	/**结婚证/离婚证地址*/
	private String marryurl;

	/**结婚状况*/
	private Integer marrystatus;

	/**结婚状况(英文)*/
	private Integer marrystatusen;

	/**婚姻状况说明*/
	private String marryexplain;

	/**婚姻状况说明(英文)*/
	private String marryexplainen;

	/**婚姻状况证件类型*/
	private Integer marryurltype;

	/**其他国家永久居民*/
	private String othercountry;

	/**其他国家永久居民(英文)*/
	private String othercountryen;

	/**国籍*/
	private String nationality;

	/**国籍(英文)*/
	private String nationalityen;

	/**国家注册码*/
	private String nationalidentificationnumber;

	/**国家注册码(英文)*/
	private String nationalidentificationnumberen;

	/**注册码是否适用*/
	private Integer isidentificationnumberapply;

	/**注册码是否适用(英文)*/
	private Integer isidentificationnumberapplyen;

	/**美国社会安全号码*/
	private String socialsecuritynumber;

	/**美国社会安全号码(英文)*/
	private String socialsecuritynumberen;

	/**安全码是否适用*/
	private Integer issecuritynumberapply;

	/**安全码是否适用(英文)*/
	private Integer issecuritynumberapplyen;

	/**美国纳税人证件号*/
	private String taxpayernumber;

	/**美国纳税人证件号(英文)*/
	private String taxpayernumberen;

	/**纳税人证件号是否适用*/
	private Integer istaxpayernumberapply;

	/**纳税人证件号是否适用(英文)*/
	private Integer istaxpayernumberapplyen;

	/**现居住地是否与身份证相同*/
	private Integer addressIssamewithcard;

	/**现居住地是否与身份证相同(英文)*/
	private Integer addressIssamewithcarden;

	/**操作人*/
	private Integer opid;

	/**创建时间*/
	private Date createtime;

	/**更新时间*/
	private Date updatetime;

	/**姓名*/
	private String name;

	/**检索字段*/
	private String searchStr;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		/*String sqlString = EntityUtil.entityCndSql(TAppStaffBasicinfoEntity.class);*/
		String sqlString = sqlManager.get("bigCustomer_staff_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(searchStr)) {
			SqlExpressionGroup expg = new SqlExpressionGroup();
			expg.and("CONCAT( tasb.firstname, tasb.lastname )", "LIKE", "%" + searchStr + "%")
					.or("tasb.telephone", "LIKE", "%" + searchStr + "%")
					.or("tasb.department", "LIKE", "%" + searchStr + "%").or("tasb.job", "LIKE", "%" + searchStr + "%");
			cnd.and(expg);
		}
		cnd.and("tasb.comId", "=", comid);
		cnd.orderBy("tasb.updateTime", "DESC");
		return cnd;
	}
}