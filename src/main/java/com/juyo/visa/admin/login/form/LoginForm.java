package com.juyo.visa.admin.login.form;

import lombok.Data;

@Data
public class LoginForm {

	/**登录名*/
	private String loginName;

	/**密码*/
	private String password;

	/**
	 * 验证码
	 */
	private String validateCode;

	/**错误消息*/
	private String errMsg;

	/**登录成功的返回页*/
	private String returnUrl;
	/**判断是否为手机端登录*/
	private String flag;
}
