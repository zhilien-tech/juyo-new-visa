/**
 * RecognizeData.java
 * org.zxc.ocr.bean
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.common.ocr;

import java.util.List;

import lombok.Data;

import com.google.common.collect.Lists;

/**
 * @author   朱晓川
 * @Date	 2017年9月15日 	 
 */
@Data
public class RecognizeData {
	private List<Input> inputs = Lists.newArrayList();
}
