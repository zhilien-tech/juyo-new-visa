/**
 * AutofillInterface.java
 * com.juyo.visa.admin.interfaceJapan.module
 * Copyright (c) 2019, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.interfaceJapan.module;

import javax.servlet.http.HttpServletRequest;

import com.juyo.visa.admin.interfaceJapan.form.ParamDataForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2019年4月3日 	 
 */
public interface AutofillInterface {
	Object toAutofill(String token, ParamDataForm form, HttpServletRequest request);

	Object search(String token, String encrypt, HttpServletRequest request);
}
