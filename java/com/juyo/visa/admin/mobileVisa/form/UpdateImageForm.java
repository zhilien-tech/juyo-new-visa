/**
 * updateImageForm.java
 * com.juyo.visa.admin.mobileVisa.form
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.mobileVisa.form;

import java.io.File;

import javax.servlet.http.HttpSession;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   马云鹏
 * @Date	 2018年3月30日 	 
 */
@Data
public class UpdateImageForm {

	private Integer staffid;

	private Integer type;

	private File file;

	private String url;

	private HttpSession session;
}
