package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffImmediaterelativesAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**人员id*/
	private Integer staffid;
		
	/**亲属的姓*/
	private String relativesfirstname;
		
	/**亲属的名*/
	private String relativeslastname;
		
	/**与你的关系*/
	private Integer relationship;
		
	/**亲属的身份*/
	private Integer relativesstatus;
		
	/**亲属的姓(英文)*/
	private String relativesfirstnameen;
		
	/**亲属的名(英文)*/
	private String relativeslastnameen;
		
	/**与你的关系(英文)*/
	private Integer relationshipen;
		
	/**亲属的身份(英文)*/
	private Integer relativesstatusen;
		
}