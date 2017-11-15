/**
 * JapanSimulatorForm.java
 * com.juyo.visa.admin.simulate.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.simulate.form;

import lombok.Data;

/**
 * 用于接收自动填表回传的参数
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年11月13日 	 
 */
@Data
public class JapanSimulatorForm {

	/**受付番号*/
	private String acceptanceNumber;

	/**代理下载文件地址*/
	private String fileUrl;
}
