/**
 * package-info.java
 * com.juyo.visa.common.baidu
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   刘旭利
 * @Date	 2018年12月11日
 */

package com.juyo.visa.common.baidu;

import java.io.Serializable;

import lombok.Data;

@Data
public class BaidutranslateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//原文
	private String src;

	//译文
	private String dst;

}
