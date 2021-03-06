package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_applicant_order_jp")
public class TApplicantOrderJpEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("日本订单id")
	private Integer orderId;

	@Column
	@Comment("申请人id")
	private Integer applicantId;

	@Column
	@Comment("是否为统一联系人")
	private Integer isSameLinker;

	@Column
	@Comment("是否分享消息")
	private Integer isShareSms;

	@Column
	@Comment("结婚证/离婚证地址")
	private String marryUrl;

	@Column
	@Comment("结婚状况")
	private Integer marryStatus;

	@Column
	@Comment("是否是主申请人")
	private Integer isMainApplicant;

	@Column
	@Comment("与主申请人的关系")
	private String mainRelation;

	@Column
	@Comment("与主申请人关系备注")
	private String relationRemark;

	@Column
	@Comment("出行信息是否同主")
	private Integer sameMainTrip;

	@Column
	@Comment("财富信息是否同主")
	private Integer sameMainWealth;

	@Column
	@Comment("工作信息是否同主")
	private Integer sameMainWork;

	@Column
	@Comment("视频地址")
	private String videoUrl;

	@Column
	@Comment("基本信息是否填写完毕")
	private Integer baseIsCompleted;

	@Column
	@Comment("护照信息是否填写完毕")
	private Integer passIsCompleted;

	@Column
	@Comment("签证信息是否填写完毕")
	private Integer visaIsCompleted;

	@Column
	@Comment("上次赴日时间")
	private Date laststartdate;

	@Column
	@Comment("上次停留天数")
	private Integer laststayday;

	@Column
	@Comment("上次返回时间")
	private Date lastreturndate;

}
