package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TOrderUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**订单号*/
	private String orderNum;
		
	/**用户id*/
	private Integer userId;
		
	/**公司id*/
	private Integer comId;
		
	/**订单状态*/
	private Integer status;
		
	/**人数*/
	private Integer number;
		
	/**领区*/
	private Integer cityId;
		
	/**加急类型*/
	private Integer urgentType;
		
	/**加急天数*/
	private Integer urgentDay;
		
	/**行程*/
	private Integer travel;
		
	/**付款方式*/
	private Integer payType;
		
	/**金额*/
	private Double money;
		
	/**出行时间*/
	private Date goTripDate;
		
	/**停留天数*/
	private Integer stayDay;
		
	/**返回时间*/
	private Date backTripDate;
		
	/**送签时间*/
	private Date sendVisaDate;
		
	/**出签时间*/
	private Date outVisaDate;
		
	/**实收备注*/
	private String realReceiveRemark;
		
	/**客户管理id*/
	private Integer customerId;
		
	/**客户来源是否为直客*/
	private Integer isDirectCus;
		
	/**公司全称*/
	private String comName;
		
	/**公司简称*/
	private String comShortName;
		
	/**联系人*/
	private String linkman;
		
	/**手机*/
	private String telephone;
		
	/**邮箱*/
	private String email;
		
	/**创建时间*/
	private Date createTime;
		
	/**更新时间*/
	private Date updateTime;
		
}