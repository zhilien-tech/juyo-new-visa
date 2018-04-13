package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TOrderJpAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**订单id*/
	private Integer orderId;
		
	/**签证类型*/
	private Integer visaType;
		
	/**签证县*/
	private String visaCounty;
		
	/**过去三年是否访问*/
	private Integer isVisit;
		
	/**过去三年访问过的县*/
	private String threeCounty;
		
	/**受付番号*/
	private String acceptDesign;
		
	/**签证状态*/
	private Integer visastatus;
		
	/**备注*/
	private String remark;
		
	/**归国报告文件地址*/
	private String returnHomeFileUrl;
		
	/**递送交接单时间*/
	private Date deliveryDataTime;
		
}