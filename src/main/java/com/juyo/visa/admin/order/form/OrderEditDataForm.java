/**
 * OrderEditDataForm.java
 * com.juyo.visa.admin.order.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.order.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

import com.juyo.visa.entities.TOrderBackmailEntity;

@Data
public class OrderEditDataForm implements Serializable {
	private Integer id;

	private String appId;

	private String ordernum;

	private Integer userid;

	private Integer comid;

	private Integer status;

	private Integer source;

	private String name;

	private String shortname;

	private String mobile;

	private Integer number;

	private Integer cityid;

	private Integer urgenttype;

	private Integer urgentday;

	private Integer travel;

	private Integer paytype;

	private Double money;

	private Date gotripdate;

	private Integer stayday;

	private Date backtripdate;

	private Date sendvisadate;

	private Date outvisadate;

	private String realreceiveremark;

	private Integer customerId;

	private Integer isdirectcus;

	private String cusLinkman;

	private String cusEmail;

	private Integer orderid;

	private Integer visatype;

	private String visacounty;

	private Integer isvisit;

	private String threecounty;

	private Date createtime;

	private Date updatetime;

	//回邮信息
	private List<TOrderBackmailEntity> backMailInfos;
}
