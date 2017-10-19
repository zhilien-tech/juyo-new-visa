package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TComBusinessscopeAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**经营范围*/
	private Integer countryId;
		
	/**公司id*/
	private Integer comId;
		
}