package com.juyo.visa.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffGocountryUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**人员id*/
	private Integer staffid;

	/**去旅游的国家*/
	private String traveledcountry;

	/**去旅游的国家(英文)*/
	private String traveledcountryen;

}