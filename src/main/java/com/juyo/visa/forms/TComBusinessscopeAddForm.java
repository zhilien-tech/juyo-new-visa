package com.juyo.visa.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TComBusinessscopeAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**经营范围*/
	private Integer countryId;

	/**公司id*/
	private Integer comId;

	/**指定番号(日本)*/
	private String designatedNum;

}