package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffOrganizationUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**人员id*/
	private Integer staffid;
		
	/**组织名称*/
	private String organizationname;
		
	/**组织名称(英文)*/
	private String organizationnameen;
		
}