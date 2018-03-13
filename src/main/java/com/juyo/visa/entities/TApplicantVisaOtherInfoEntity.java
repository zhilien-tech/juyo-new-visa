package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_applicant_visa_other_info")
public class TApplicantVisaOtherInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("申请人id")
	private Integer applicantid;

	@Column
	@Comment("酒店名称")
	private String hotelname;

	@Column
	@Comment("酒店电话")
	private String hotelphone;

	@Column
	@Comment("酒店地址")
	private String hoteladdress;

	@Column
	@Comment("在日担保人姓名")
	private String vouchname;

	@Column
	@Comment("在日担保人姓名拼音")
	private String vouchnameen;

	@Column
	@Comment("担保人电话")
	private String vouchphone;

	@Column
	@Comment("担保人地址")
	private String vouchaddress;

	@Column
	@Comment("担保人出生日期")
	private Date vouchbirth;

	@Column
	@Comment("担保人性别")
	private String vouchsex;

	@Column
	@Comment("担保人与主申请人的关系")
	private String vouchmainrelation;

	@Column
	@Comment("担保人职业")
	private String vouchjob;

	@Column
	@Comment("担保人国籍")
	private String vouchcountry;

	@Column
	@Comment("邀请人姓名")
	private String invitename;

	@Column
	@Comment("邀请人电话")
	private String invitephone;

	@Column
	@Comment("邀请人地址")
	private String inviteaddress;

	@Column
	@Comment("邀请人出生日期")
	private Date invitebirth;

	@Column
	@Comment("邀请人性别")
	private String invitesex;

	@Column
	@Comment("邀请人与主申请人的关系")
	private String invitemainrelation;

	@Column
	@Comment("邀请人职业")
	private String invitejob;

	@Column
	@Comment("邀请人国籍")
	private String invitecountry;

	@Column
	@Comment("旅行社意见")
	private String traveladvice;

	@Column
	@Comment("是否是姓名")
	private String isname;

}