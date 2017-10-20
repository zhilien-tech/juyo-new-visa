package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TOrderTripJpAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**订单id*/
	private Integer orderId;
		
	/**行程类型*/
	private Integer tripType;
		
	/**出行目的*/
	private String tripPurpose;
		
	/**出发日期(去程)*/
	private Date goDate;
		
	/**出发城市(去程)*/
	private Integer goDepartureCity;
		
	/**抵达城市(去程)*/
	private Integer goArrivedCity;
		
	/**航班号(去程)*/
	private Integer goFlightNum;
		
	/**出发日期(返程)*/
	private Date returnDate;
		
	/**出发城市(返程)*/
	private Integer returnDepartureCity;
		
	/**返回城市(返程)*/
	private Integer returnArrivedCity;
		
	/**航班号(返程)*/
	private Integer returnFlightNum;
		
	/**操作人*/
	private Integer opId;
		
	/**创建时间*/
	private Date createTime;
		
	/**更新时间*/
	private Date updateTime;
		
}