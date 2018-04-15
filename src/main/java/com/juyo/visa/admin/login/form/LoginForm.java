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
	/**判断 是工作人员还是游客登录*/
	private int isTourist;
	/**短信登录错误消息*/
	private String messageErrMsg;
	/**主页面的URL地址*/
	private String mainurl;
	
	/**用户类型*/
	private Integer userType;
}
