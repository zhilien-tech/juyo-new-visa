package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TComBusinessscopeUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**经营范围*/
	private Integer countryId;
		
	/**公司id*/
	private Integer comId;
		
}