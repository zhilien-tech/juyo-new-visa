package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffGousinfoAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**人员id*/
	private Integer staffid;
		
	/**抵达日期*/
	private Date arrivedate;
		
	/**停留时间*/
	private Integer staydays;
		
	/**日期单位*/
	private Integer dateunit;
		
	/**抵达日期(英文)*/
	private Date arrivedateen;
		
	/**停留时间(英文)*/
	private Integer staydaysen;
		
	/**日期单位(英文)*/
	private Integer dateuniten;
		
}