package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TBusinessscopeFunctionAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/***/
	private Integer countryId;
		
	/***/
	private Integer compType;
		
	/***/
	private Integer funId;
		
}