package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffDriverinfoAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**人员id*/
	private Integer staffid;
		
	/**驾照号*/
	private String driverlicensenumber;
		
	/**是否知道驾照号*/
	private Integer isknowdrivernumber;
		
	/**哪个州的驾照*/
	private Integer witchstateofdriver;
		
	/**驾照号(英文)*/
	private String driverlicensenumberen;
		
	/**是否知道驾照号(英文)*/
	private Integer isknowdrivernumberen;
		
	/**哪个州的驾照(英文)*/
	private Integer witchstateofdriveren;
		
}