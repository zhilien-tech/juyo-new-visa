package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffEventsUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**人员id*/
	private Integer staffId;
		
	/**活动id*/
	private Integer eventsId;
		
}