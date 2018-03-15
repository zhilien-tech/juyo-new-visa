package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffTravelcompanionAddForm extends AddForm implements Serializable{
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
		
	/**同伴的姓*/
	private String firstname;
		
	/**同伴的姓(英文)*/
	private String firstnameen;
		
	/**同伴的名*/
	private String lastname;
		
	/**同伴的名(英文)*/
	private String lastnameen;
		
	/**与你的关系*/
	private Integer relationship;
		
	/**与你的关系(英文)*/
	private Integer relationshipen;
		
}