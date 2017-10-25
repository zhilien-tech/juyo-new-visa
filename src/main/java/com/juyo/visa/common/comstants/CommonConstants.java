/**
 * CommonConstants.java
 * com.juyo.visa.common
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.common.comstants;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年10月19日 	 
 */
public class CommonConstants {

	/**
	 * 验证码-session key
	 */
	public static final String CONFIRMCODE = "confirmcode";

	/**数据状态:@see DataStatusEnum*/
	/**数据状态-启用中*/
	public static final Integer DATA_STATUS_VALID = 1;

	/**数据状态-冻结*/
	public static final Integer DATA_STATUS_INVALID = 0;
	/**超级管理员*/
	public static final String SUPER_ADMIN = "xhh";
	/**游客公司职位id*/
	public static final int COMPANY_JOB_ID = 2;
	/**游客所在公司id*/
	public static final int COMPANY_TOURIST_ID = 2;
	/**游客所在部门id*/
	public static final int DEPARTMENT_TOURIST_ID = 2;
	/**游客所在职位id*/
	public static final int JOB_TOURIST_ID = 2;
}
