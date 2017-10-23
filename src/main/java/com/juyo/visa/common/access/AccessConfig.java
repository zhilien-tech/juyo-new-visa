package com.juyo.visa.common.access;

/**
 * web访问请求配置
 * @author 朱晓川
 *
 */
public class AccessConfig {

	/**存取请求参数中的URL请求地址的key*/
	public static final String URL_KEY = "url";

	/**终端签名秘钥*/
	public static final String terminal_secret = "visa-terminal-qazWSXasdf#456";

	/**平台签名秘钥*/
	public static final String platform_secret = "visa-platform";

	/**管理后台密码签名秘钥,一旦创建用户以后该值不可更改*/
	public static final String password_secret = "visa-qwerASDF#123";

	/**字符编码格式 */
	public static final String INPUT_CHARSET = "utf-8";

	/**签名方式*/
	public static final String sign_type = "MD5";

}
