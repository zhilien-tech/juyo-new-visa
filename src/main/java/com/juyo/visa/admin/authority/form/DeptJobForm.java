/**
 * TAuthorityAddForm.java
 * com.linyun.airline.admin.authority.authoritymanage.form
 * Copyright (c) 2016, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.authority.form;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   彭辉
 * @Date	 2017年10月24日 	 
 */
@Data
public class DeptJobForm {
	//主键
	private long id;

	//部门id/
	private long deptId;

	//部门名称
	private String deptName;

	//职位名称和权限
	private String jobJson;

	//职位id
	private long jobId;

	//功能id
	private String functionIds;
}
