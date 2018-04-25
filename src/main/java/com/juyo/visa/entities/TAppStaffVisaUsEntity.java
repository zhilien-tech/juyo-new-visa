package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_app_staff_visa_us")
public class TAppStaffVisaUsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("申请人id")
	private Integer staffid;
	
	@Column
    @Comment("签发编号")
	private String visanum;
	
	@Column
    @Comment("签发地")
	private String visaaddress;
	
	@Column
    @Comment("签证类型")
	private Integer visatype;
	
	@Column
    @Comment("年限(年)")
	private Integer visayears;
	
	@Column
    @Comment("真实资料")
	private String realinfo;
	
	@Column
    @Comment("签发时间")
	private Date visadate;
	
	@Column
    @Comment("有效期至")
	private Date validdate;
	
	@Column
    @Comment("停留时间(天)")
	private Integer staydays;
	
	@Column
    @Comment("操作人")
	private Integer opid;
	
	@Column
    @Comment("创建时间")
	private Date createtime;
	
	@Column
    @Comment("更新时间")
	private Date updatetime;
	
	@Column
    @Comment("签证国家")
	private String visacountry;
	
	@Column
    @Comment("上传签证图片URL")
	private String picurl;
	
	@Column
    @Comment("签证录入时间")
	private Date visaentrytime;
	

}