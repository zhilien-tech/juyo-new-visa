package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TVcodeAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**验证码图片*/
	private String vcodeurl;
		
	/**验证码*/
	private String vcode;
		
}