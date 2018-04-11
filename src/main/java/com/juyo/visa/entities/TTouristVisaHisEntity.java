package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_tourist_visa_his")
public class TTouristVisaHisEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("用户id")
	private Integer userId;

	@Column
	@Comment("订单id")
	private Integer orderId;

	@Column
	@Comment("用户名")
	private String userName;

	@Column
	@Comment("婚姻状况")
	private String marryStatus;

	@Column
	@Comment("是否是主申请人")
	private Integer isMainApplicant;

	@Column
	@Comment("与主申请人关系备注")
	private String relationRemark;

	@Column
	@Comment("签证类型")
	private String visaType;

	@Column
	@Comment("签证县")
	private String visaCounty;

	@Column
	@Comment("过去三年是否访问")
	private String isVisit;

	@Column
	@Comment("过去三年访问过的县")
	private String threeCounty;

	@Column
	@Comment("上次赴日时间")
	private Date laststartdate;

	@Column
	@Comment("上次停留天数")
	private Integer laststayday;

	@Column
	@Comment("上次返回时间")
	private Date lastreturndate;

	@Column
	@Comment("我的职业")
	private String occupation;

	@Column
	@Comment("单位名称")
	private String name;

	@Column
	@Comment("单位电话")
	private String telephone;

	@Column
	@Comment("单位地址")
	private String address;

	@Column
	@Comment("父母/配偶单位名称")
	private String unitName;

	@Column
	@Comment("银行存款")
	private String deposit;

	@Column
	@Comment("银行存款金额")
	private String depositAmount;

	@Column
	@Comment("车产")
	private String vehicle;

	@Column
	@Comment("车辆型号")
	private String vehicleModels;

	@Column
	@Comment("房产")
	private String houseProperty;

	@Column
	@Comment("房产面积")
	private String houseArea;

	@Column
	@Comment("理财")
	private String moneyManagement;

	@Column
	@Comment("理财金额")
	private String moneyAmount;

	@Column
	@Comment("在日担保人姓名")
	private String vouchname;

	@Column
	@Comment("担保人电话")
	private String vouchphone;

	@Column
	@Comment("担保人地址")
	private Integer vouchaddress;

	@Column
	@Comment("担保人出生日期")
	private String vouchbirth;

	@Column
	@Comment("担保人性别")
	private String vouchsex;

	@Column
	@Comment("担保人职业")
	private String vouchjob;

	@Column
	@Comment("担保人国籍")
	private String vouchcountry;

	@Column
	@Comment("邀请人姓名")
	private String inviteName;

	@Column
	@Comment("邀请人电话")
	private String invitephone;

	@Column
	@Comment("邀请人地址")
	private String inviteaddress;

	@Column
	@Comment("邀请人出生日期")
	private String invitebirth;

	@Column
	@Comment("邀请人性别")
	private String invitesex;

	@Column
	@Comment("邀请人与主申请人关系")
	private String invitemainrelation;

	@Column
	@Comment("邀请人职业")
	private String invitejob;

	@Column
	@Comment("财产明细")
	private String details;

	@Column
	@Comment("财富信息是否同主")
	private String sameMainWealth;

	@Column
	@Comment("工作信息是否同主")
	private String sameMainWork;

	@Column
	@Comment("财产类型")
	private String type;

	@Column
	@Comment("职业状况")
	private String careerStatus;

	@Column
	@Comment("结婚证/离婚证地址")
	private String marryUrl;

	@Column
	@Comment("主申请人id")
	private Integer mainId;

	@Column
	@Comment("与主申请人的关系")
	private String mainRelation;

	@Column
	@Comment("签证信息是否填写完毕 ")
	private String visaIsCompleted;

	@Column
	@Comment("旅行社意见 ")
	private String traveladvice;

	@Column
	@Comment("操作人")
	private Integer opId;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("更新时间")
	private Date updateTime;

}