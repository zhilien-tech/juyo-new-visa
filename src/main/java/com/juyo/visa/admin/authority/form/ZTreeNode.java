/**
 * ZTreeNode.java
 * com.linyun.airline.admin.authority.authoritymanage.form
 * Copyright (c) 2016, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.authority.form;

import lombok.Data;

/**
 * ZTree节点
 * @author   彭辉
 * @Date	 2017年10月24日 	 
 */
@Data
public class ZTreeNode {

	private long id;

	private long pId;

	private String name;

	private boolean open;

	private String checked;

}
