package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffLanguageAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**人员id*/
	private Integer staffid;
		
	/**使用的语言名称*/
	private String languagename;
		
	/**使用的语言名称(英文)*/
	private String languagenameen;
		
}