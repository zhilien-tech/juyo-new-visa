package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffCompanioninfoUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**人员id*/
	private Integer staffid;
		
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