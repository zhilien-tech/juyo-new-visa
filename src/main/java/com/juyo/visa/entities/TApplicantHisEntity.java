/**
 * TApplicantHisEntity.java
 * com.juyo.visa.entities
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2018年4月9日 	 
 */
@Data
@Table("t_applicant_his")
public class TApplicantHisEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("主申请人id")
	private Integer mainId;

	@Column
	@Comment("申请人状态")
	private String status;

	@Column
	@Comment("用户id（登录用户id）")
	private Integer userId;

	@Column
	@Comment("用户名称（登录用户名称）")
	private String userName;

	@Column
	@Comment("姓")
	private String firstName;

	@Column
	@Comment("姓(拼音)")
	private String firstNameEn;

	@Column
	@Comment("名")
	private String lastName;

	@Column
	@Comment("名(拼音)")
	private String lastNameEn;

	@Column
	@Comment("曾用姓")
	private String otherFirstName;

	@Column
	@Comment("曾用名")
	private String otherLastName;

	@Column
	@Comment("曾用姓(拼音)")
	private String otherFirstNameEn;

	@Column
	@Comment("曾用名(拼音)")
	private String otherLastNameEn;

	@Column
	@Comment("紧急联系人姓名")
	private String emergencyLinkman;

	@Column
	@Comment("紧急联系人手机")
	private String emergencyTelephone;

	@Column
	@Comment("紧急联系人地址")
	private String emergencyaddress;

	@Column
	@Comment("是否另有国籍")
	private String hasOtherNationality;

	@Column
	@Comment("国籍")
	private String nationality;

	@Column
	@Comment("是否有曾用名")
	private String hasOtherName;

	@Column
	@Comment("现居住地是否与身份证相同")
	private String addressIsSameWithCard;

	@Column
	@Comment("结婚证/离婚证地址")
	private String marryUrl;

	@Column
	@Comment("结婚状况")
	private String marryStatus;

	@Column
	@Comment("身份证省份")
	private String cardProvince;

	@Column
	@Comment("身份证城市")
	private String cardCity;

	@Column
	@Comment("手机号")
	private String telephone;

	@Column
	@Comment("邮箱")
	private String email;

	@Column
	@Comment("性别")
	private String sex;

	@Column
	@Comment("民族")
	private String nation;

	@Column
	@Comment("出生日期")
	private Date birthday;

	@Column
	@Comment("住址")
	private String address;

	@Column
	@Comment("身份证号")
	private String cardId;

	@Column
	@Comment("身份证正面")
	private String cardFront;

	@Column
	@Comment("身份证反面")
	private String cardBack;

	@Column
	@Comment("签发机关")
	private String issueOrganization;

	@Column
	@Comment("有效期始")
	private Date validStartDate;

	@Column
	@Comment("有效期至")
	private Date validEndDate;

	@Column
	@Comment("现居住地址省份")
	private String province;

	@Column
	@Comment("现居住地址城市")
	private String city;

	@Column
	@Comment("详细地址")
	private String detailedAddress;

	@Column
	@Comment("操作人")
	private Integer opId;

	@Column
	@Comment("更新时间")
	private Date updateTime;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("婚姻状况证件类型")
	private String marryurltype;

	@Column
	@Comment("信息是否一致")
	private String isSameInfo;

	@Column
	@Comment("是否已提示")
	private String isPrompted;

}
