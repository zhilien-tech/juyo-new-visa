package com.juyo.visa.admin.orderUS.form;

import lombok.Data;

@Data
public class VcodeForm {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**验证码*/
	private String vcode;

}