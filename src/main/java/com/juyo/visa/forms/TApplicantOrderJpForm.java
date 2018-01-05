package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TApplicantOrderJpForm extends DataTablesParamForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**日本订单id*/
	private Integer orderId;

	/**申请人id*/
	private Integer applicantId;

	private Integer marryStatus;

	private Integer baseIsCompleted;

	private Integer passIsCompleted;

	private Integer visaIsCompleted;

	private String marryUrl;

	/**是否为统一联系人*/
	private Integer isSameLinker;

	/**是否分享消息*/
	private Integer isShareSms;

	/**是否是主申请人*/
	private Integer isMainApplicant;

	/**与主申请人的关系*/
	private String mainRelation;

	/**与主申请人关系备注*/
	private String relationRemark;

	/**出行信息是否同主*/
	private Integer sameMainTrip;

	/**财富信息是否同主*/
	private Integer sameMainWealth;

	/**工作信息是否同主*/
	private Integer sameMainWork;

	/**视频地址*/
	private String videoUrl;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TApplicantOrderJpEntity.class);
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