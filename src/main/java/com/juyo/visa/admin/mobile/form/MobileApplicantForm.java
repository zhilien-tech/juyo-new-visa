/**
 * MobileApplicantForm.java
 * com.juyo.visa.admin.mobile.form
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.mobile.form;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年12月11日 	 
 */
@Data
public class MobileApplicantForm {

	private Integer comid;

	private Integer userid;

	private Integer orderid;

	private Integer applicantid;

	private Integer messagetype;
	
	private String mainRelation;

	/**二维码PC端的sessionid*/
	private String sessionid;
}
