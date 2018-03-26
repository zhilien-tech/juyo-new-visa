package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffTravelcompanionUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**人员id*/
	private String staffid;
		
	/**是否与其他人一起旅行*/
	private Integer istravelwithother;
		
	/**是否与其他人一起旅行(英文)*/
	private Integer istravelwithotheren;
		
	/**是否作为团队或组织的一部分*/
	private Integer ispart;
		
	/**是否作为团队或组织的一部分(英文)*/
	private Integer isparten;
		
	/**团队名称*/
	private String groupname;
		
	/**团队名称(英文)*/
	private String groupnameen;
		
}