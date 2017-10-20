package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TOrderJpUpdateForm extends ModForm implements Serializable{
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
		
}