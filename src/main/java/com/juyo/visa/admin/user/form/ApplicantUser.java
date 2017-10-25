/**
 * ApplicantUser.java
 * com.juyo.visa.admin.user.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.user.form;

import lombok.Data;

import com.juyo.visa.common.access.AccessConfig;
import com.juyo.visa.common.access.sign.MD5;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年10月24日 	 
 */
@Data
public class ApplicantUser {

	private String username;

	private String password;

	private int opid;

	public void setPassword(String password) {
		this.password = MD5.sign(password, AccessConfig.password_secret, AccessConfig.INPUT_CHARSET);
	}
}
