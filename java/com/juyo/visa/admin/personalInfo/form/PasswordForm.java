package com.juyo.visa.admin.personalInfo.form;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

/**
 * 个人信息 SqlForm
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年11月10日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PasswordForm extends AddForm implements Serializable {

	private static final long serialVersionUID = 1L;

	//旧密码
	private String password;
	//新密码
	private String newPass;
	//新密码
	private String repeatPass;

}
