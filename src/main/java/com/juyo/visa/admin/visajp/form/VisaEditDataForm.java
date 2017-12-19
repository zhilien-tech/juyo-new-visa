/**
 * VisaEditDataForm.java
 * com.juyo.visa.admin.visajp.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp.form;

import java.util.Date;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2017年11月1日 	 
 */
@Data
public class VisaEditDataForm {

	private Integer id;

	private String ordernum;

	private Integer userid;

	private Integer comid;

	private String status;

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

	private String sendvisanum;

	private String realreceiveremark;

	private Integer customerId;

	private Integer isdirectcus;

	private String comName;

	private String comshortname;

	private String linkman;

	private String telephone;

	private String email;

	private Integer orderid;

	private Integer visatype;

	private String visacounty;

	private Integer isvisit;

	private String threecounty;

	private String remark;
}
