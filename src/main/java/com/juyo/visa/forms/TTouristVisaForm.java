package com.juyo.visa.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.juyo.visa.entities.TTouristVisaEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TTouristVisaForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**用户id*/
	private Integer userId;

	/**我的职业*/
	private String occupation;

	/**单位名称*/
	private String name;

	/**单位电话*/
	private String telephone;

	/**预准备资料*/
	private String prepareMaterials;

	/**单位地址*/
	private String address;

	/**财产明细*/
	private String details;

	/**财富信息是否同主*/
	private Integer sameMainWealth;

	/**工作信息是否同主*/
	private Integer sameMainWork;

	/**财产类型*/
	private String type;

	/**职业状况*/
	private Integer careerStatus;

	/**婚姻状况*/
	private Integer marryStatus;

	/**结婚证/离婚证地址*/
	private String marryUrl;

	/**主申请人id*/
	private Integer mainId;

	/**是否是主申请人*/
	private Integer isMainApplicant;

	/**与主申请人的关系*/
	private String mainRelation;

	/**与主申请人关系备注*/
	private String relationRemark;

	/**签证信息是否填写完毕*/
	private Integer visaIsCompleted;

	/**操作人*/
	private Integer opId;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

	private String financial;

	private Integer applyId;

	private Integer contact;

	private String houseProperty;

	private String deposit;

	private String vehicle;
	//是主申请人还是副申请人
	private Integer applicant;
	//申请人ID
	private Integer applicantId;
	//主申请人ID
	private Integer mainApplicant;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TTouristVisaEntity.class);
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